package com.cg.ibs.rm.bean;

import java.util.List;
import java.util.Set;
public class TemporaryCustomer {
	private String uci;
	private Set<CreditCard> unapprovedCreditCards;
	private List<Beneficiary> unapprovedBeneficiaries;

	public TemporaryCustomer() {
		super();
	}

	public TemporaryCustomer(String uci, Set<CreditCard> unapprovedCreditCards,
			List<Beneficiary> unapprovedBeneficiaries) {
		super();
		this.uci = uci;
		this.unapprovedCreditCards = unapprovedCreditCards;
		this.unapprovedBeneficiaries = unapprovedBeneficiaries;
	}

	@Override
	public String toString() {
		return "TemporaryCustomer [UCI=" + uci + ", unapprovedCreditCards=" + unapprovedCreditCards
				+ ", unapprovedBeneficiaries=" + unapprovedBeneficiaries + "]";
	}

	public String getUCI() {
		return uci;
	}

	public void setUCI(String uci) {
		this.uci = uci;
	}

	public Set<CreditCard> getUnapprovedCreditCards() {
		return unapprovedCreditCards;
	}

	public void setUnapprovedCreditCards(Set<CreditCard> unapprovedCreditCards) {
		this.unapprovedCreditCards = unapprovedCreditCards;
	}

	public List<Beneficiary> getUnapprovedBeneficiaries() {
		return unapprovedBeneficiaries;
	}

	public void setUnapprovedBeneficiaries(List<Beneficiary> unapprovedBeneficiaries) {
		this.unapprovedBeneficiaries = unapprovedBeneficiaries;
	}

}
