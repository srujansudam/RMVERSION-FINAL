package com.cg.ibs.rm.service;

import java.util.List;
import java.util.Set;

import com.cg.ibs.rm.bean.Beneficiary;
import com.cg.ibs.rm.bean.CreditCard;
import com.cg.ibs.rm.dao.BankRepresentativeDAO;
import com.cg.ibs.rm.dao.BankRepresentativeDAOImpl;
import com.cg.ibs.rm.exception.RmExceptions;

public class BankRepresentativeServiceImpl implements BankRepresentativeService {
	BankRepresentativeDAO bankRepresentativeDAO = new BankRepresentativeDAOImpl();
	List<Beneficiary> beneficiaryList;
	Set<String> idList;
	Set<CreditCard> creditCardList;

	@Override
	public Set<String> showRequests() {
		idList = bankRepresentativeDAO.getRequests();
		return idList;
	}

	@Override
	public Set<CreditCard> showUnapprovedCreditCards(String uci) throws RmExceptions {
		creditCardList = bankRepresentativeDAO.getCreditCardDetails(uci);
		return creditCardList;
	}

	@Override
	public List<Beneficiary> showUnapprovedBeneficiaries(String uci) throws RmExceptions {
		beneficiaryList = bankRepresentativeDAO.getBeneficiaryDetails(uci);
		return beneficiaryList;
	}

	@Override
	public void saveCreditCardDetails(String uci, CreditCard card) {
		bankRepresentativeDAO.copyCreditCardDetails(uci, card);

	}

	@Override
	public void saveBeneficiaryDetails(String uci, Beneficiary beneficiary) {
		bankRepresentativeDAO.copyBeneficiaryDetails(uci, beneficiary);
	}

	@Override
	public void removeTempCreditCardDetails(String uci, CreditCard card) {
		bankRepresentativeDAO.deleteTempCreditCardDetails(uci, card);
	}

	@Override
	public void removeTempBeneficiaryDetails(String uci, Beneficiary beneficiary) {
		bankRepresentativeDAO.deleteTempBeneficiaryDetails(uci, beneficiary);
	}

}
