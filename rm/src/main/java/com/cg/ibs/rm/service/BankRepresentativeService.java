package com.cg.ibs.rm.service;

import java.util.List;
import java.util.Set;

import com.cg.ibs.rm.bean.Beneficiary;
import com.cg.ibs.rm.bean.CreditCard;
import com.cg.ibs.rm.exception.RmExceptions;

public interface BankRepresentativeService {
	public Set<String> showRequests();
	
	public Set<CreditCard> showUnapprovedCreditCards(String uci) throws RmExceptions;

	public List<Beneficiary> showUnapprovedBeneficiaries(String uci) throws RmExceptions;

	public void saveCreditCardDetails(String uci, CreditCard card);

	public void saveBeneficiaryDetails(String uci, Beneficiary beneficiary);

	public void removeTempCreditCardDetails(String uci, CreditCard card);

	public void removeTempBeneficiaryDetails(String uci, Beneficiary beneficiary);
}

