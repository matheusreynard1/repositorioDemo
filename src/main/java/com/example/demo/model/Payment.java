package com.example.demo.model;

public class Payment {

	private double entry;
	private int installments;
	
	public Payment(double entry, int installments) {
		this.entry = entry;
		this.installments = installments;
	}
	
	public double getEntry() {
		return entry;
	}
	
	public void setEntry(double entry) {
		this.entry = entry;
	}
	
	public int getInstallments() {
		return installments;
	}
	
	public void setInstallments(int installments) {
		this.installments = installments;
	}
	
}
