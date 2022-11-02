package com.example.demo.model;

public class Compra {

	private Product product;
	private Payment payment;
	
	public Compra(Product product, Payment payment) {
		this.product = product;
		this.payment = payment;
	}
	
	public Product getProduct() {
		return product;
	}
	
	public void setProduct(Product product) {
		this.product = product;
	}
	
	public Payment getPayment() {
		return payment;
	}
	
	public void setPayment(Payment payment) {
		this.payment = payment;
	}

}
