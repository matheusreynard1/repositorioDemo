package com.example.demo.model;

public class Installment {
	
	private int installmentsNum;
	private double price;
	private double tax;
	
	public Installment(int installmentsNum, double price, double tax) {
		this.installmentsNum = installmentsNum;
		this.price = price;
		this.tax = tax;
	}
	
	public int getInstallmentsNum() {
		return installmentsNum;
	}

	public void setInstallmentsNum(int installmentsNum) {
		this.installmentsNum = installmentsNum;
	}

	public double getPrice() {
		return price;
	}

	public void setPrice(double price) {
		this.price = price;
	}

	public double getTax() {
		return tax;
	}

	public void setTax(double tax) {
		this.tax = tax;
	}

}
