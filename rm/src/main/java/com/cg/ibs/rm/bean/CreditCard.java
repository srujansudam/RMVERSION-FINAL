package com.cg.ibs.rm.bean;

import java.math.BigInteger;
import java.time.LocalDate;

public class CreditCard {
	private BigInteger creditCardNumber;
	private String nameOnCreditCard;
	private LocalDate creditDateOfExpiry;

	public CreditCard() {
		super();
	}

	public CreditCard(BigInteger creditCardNumber, String nameOnCreditCard, LocalDate creditDateOfExpiry) {
		super();
		this.creditCardNumber = creditCardNumber;
		this.nameOnCreditCard = nameOnCreditCard;
		this.creditDateOfExpiry = creditDateOfExpiry;
	}

	@Override
	public String toString() {
		return "CreditCard [creditCardNumber=" + creditCardNumber + ", nameOnCreditCard=" + nameOnCreditCard
				+ ", creditDateOfExpiry=" + creditDateOfExpiry + "]";
	}

	public BigInteger getcreditCardNumber() {
		return creditCardNumber;
	}

	public void setcreditCardNumber(BigInteger creditCardNumber) {
		this.creditCardNumber = creditCardNumber;
	}

	public String getnameOnCreditCard() {
		return nameOnCreditCard;
	}

	public void setnameOnCreditCard(String nameOnCreditCard) {
		this.nameOnCreditCard = nameOnCreditCard;
	}

	public LocalDate getcreditDateOfExpiry() {
		return creditDateOfExpiry;
	}

	public void setcreditDateOfExpiry(LocalDate creditDateOfExpiry) {
		this.creditDateOfExpiry = creditDateOfExpiry;
	}

}
