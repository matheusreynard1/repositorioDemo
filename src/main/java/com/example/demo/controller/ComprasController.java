package com.example.demo.controller;

import java.util.ArrayList;
import java.util.List;

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
	double taxa = 50;
	
	@PostMapping
	public @ResponseBody List<Installment> comprar(@RequestBody Compra comp) {
		compra = comp;
		List<Installment> lista = new ArrayList<Installment>();
		
		// SE HOUVER ENTRADA, DESCONTA DO PREÇO TOTAL DO PRODUTO
		if (compra.getPayment().getEntry() > 0) {
			compra.getProduct().setPrice(compra.getProduct().getPrice() - compra.getPayment().getEntry());
		}
		
		for(int i=1; i <= compra.getPayment().getInstallments(); i++) {
			installment = new Installment(i, compra.getProduct().getPrice() / compra.getPayment().getInstallments(), taxa);
			
			// SE A COMPRA TIVER MAIS DE 6 PARCELAS, O PREÇO DE CADA PARCELA É ACRESCIDO COM A TAXA
			if (compra.getPayment().getInstallments() > 6) {
				installment.setPrice(installment.getPrice() + installment.getTax());
			}
			
			lista.add(installment);
		}
		
		return lista; 
	}

}
