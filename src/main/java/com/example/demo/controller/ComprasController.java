package com.example.demo.controller;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.model.Compra;
import com.example.demo.model.Installment;

@RestController
@RequestMapping("/comprar")
public class ComprasController {
	
	Compra compra;
	Installment installment;
	double taxa = 0, valorTaxa = 0;
	
	@PostMapping
	public @ResponseBody List<Installment> comprar(@RequestBody Compra comp) {
		compra = comp;
		List<Installment> lista = new ArrayList<Installment>();
		
		// SE HOUVER ENTRADA, DESCONTA DO PREÇO TOTAL DO PRODUTO
		if (compra.getPayment().getEntry() > 0) {
			compra.getProduct().setPrice(compra.getProduct().getPrice() - compra.getPayment().getEntry());
		}

		// SE A COMPRA TIVER MAIS DE 6 PARCELAS, O PREÇO DE CADA PARCELA É ACRESCIDO COM AS TAXAS SELIC
		if (compra.getPayment().getInstallments() > 6) {
			
			// ======================= LÊ A URL QUE INFORMA AS TAXAS ======================= //
			JSONParser parser = new JSONParser();
	
		    try {        
		        URL url = new URL("https://api.bcb.gov.br/dados/serie/bcdata.sgs.11/dados?formato=json");
		        URLConnection yc = url.openConnection();
		        BufferedReader in = new BufferedReader(new InputStreamReader(yc.getInputStream()));          
	            DateTimeFormatter formato = DateTimeFormatter.ofPattern("dd/MM/yyyy"); 
	            LocalDate lastDate = LocalDate.now();
	            LocalDate firstDate;
	            String data1, valor, data2;
	            Duration diff;
	            long dias;
		        String inputLine;
		        
		        while ((inputLine = in.readLine()) != null) {     
		        	JSONArray array = (JSONArray) parser.parse(inputLine);	
		            for (Object obj : array) {
		            	
		            	// PEGA O VALOR DAS TAXAS DOS ÚLTIMOS 30 DIAS E FAZ A SOMA TOTAL
		            	// OS DIAS SÃO CALCULADOS A PARTIR DA 'DATA ATUAL -(MENOS) 30 DIAS' ATRAS, CONTANDO COM FINS DE SEMANA E FERIADOS
		            	// PORÉM AS TAXAS SELIC SÃO APENAS COBRADAS EM DIAS ÚTEIS
		            	
		                JSONObject taxas = (JSONObject) obj;	
		                data1 = (String) taxas.get("data");
		                valor = (String) taxas.get("valor");
		                valorTaxa = Double.parseDouble(valor);
		                firstDate = LocalDate.parse(data1, formato);       
		                diff = Duration.between(firstDate.atStartOfDay(), lastDate.atStartOfDay());
	            		dias = diff.toDays();
	            		data2 = lastDate.toString();
		                
	                	if (dias <= 30) {
	                		// EXIBE OS VALORES NO CONSOLE DA IDE PARA FINS DE CONSULTA
	                		System.out.println("DIAS: " + dias);
	                		System.out.println("DATA JSON: " + data1);
	                		System.out.println("DATA ATUAL: " + data2);
	                		System.out.println("VALOR: " + valor);
	                		taxa = taxa + valorTaxa;
	                	}            	
		            }
		        }
		        in.close();
		    } catch (FileNotFoundException e) {
		        e.printStackTrace();
		    } catch (IOException e) {
		        e.printStackTrace();
		    } catch (ParseException e) {
				e.printStackTrace();
		    } 
		    
		    // CRIA A PARCELA E INSERE NA LISTA
		    installment = new Installment(compra.getPayment().getInstallments(), compra.getProduct().getPrice() / compra.getPayment().getInstallments(), taxa);
			installment.setPrice(installment.getPrice() + installment.getTax());
			lista.add(installment); 
			taxa = 0;
		
	    // SE HOUVER 6 OU MENOS PARCELAS
		} else if (compra.getPayment().getInstallments() <= 6) {
			taxa = 0;
		    installment = new Installment(compra.getPayment().getInstallments(), compra.getProduct().getPrice() / compra.getPayment().getInstallments(), taxa);
			installment.setPrice(installment.getPrice() + installment.getTax());
			lista.add(installment); 
		}
		
		return lista; 
	}

}
