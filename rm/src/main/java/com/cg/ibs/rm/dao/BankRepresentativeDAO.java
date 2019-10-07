package com.cg.ibs.rm.dao;

import java.util.List;
import java.util.Set;

import com.cg.ibs.rm.bean.Beneficiary;
import com.cg.ibs.rm.bean.CreditCard;
import com.cg.ibs.rm.exception.RmExceptions;

public interface BankRepresentativeDAO {
	public Set<String> getRequests();

	public Set<CreditCard> getCreditCardDetails(String uci) throws RmExceptions;

	public List<Beneficiary> getBeneficiaryDetails(String uci) throws RmExceptions;

	public void copyCreditCardDetails(String uci, CreditCard card);

	public void copyBeneficiaryDetails(String uci, Beneficiary beneficiary);

	public void deleteTempCreditCardDetails(String uci, CreditCard card);

	public void deleteTempBeneficiaryDetails(String uci, Beneficiary beneficiary);
}
