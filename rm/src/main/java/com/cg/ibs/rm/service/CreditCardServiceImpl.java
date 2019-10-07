package com.cg.ibs.rm.service;

import java.math.BigInteger;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Set;
import java.util.regex.Pattern;

import com.cg.ibs.rm.bean.CreditCard;
import com.cg.ibs.rm.dao.CreditCardDAOImpl;
import com.cg.ibs.rm.exception.RmExceptions;

public class CreditCardServiceImpl implements CreditCardService {

	CreditCardDAOImpl creditCardDao;
	Set<CreditCard> creditCardList; 
	
	@Override
	public Set<CreditCard> showCardDetails(String uci) {
		creditCardList = creditCardDao.getDetails(uci);
		return creditCardList;
	}

	@Override
	public boolean validateCardNumber(BigInteger creditCardNumber) {
		boolean validNumber = true;
		if (creditCardNumber.compareTo(new BigInteger("999999999999999")) == -1
				|| creditCardNumber.compareTo(new BigInteger("10000000000000000")) == 1)
			validNumber = false;
		return validNumber;
	}

	@Override
	public boolean validateDateOfExpiry(String creditDateOfExpiry) {
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM/YY");
		LocalDate creditCardExpiry = LocalDate.parse(creditDateOfExpiry, formatter);
		LocalDate today = LocalDate.now();
		boolean validDate = true;
		if (creditCardExpiry.isBefore(today))
			validDate = false;
		return validDate;
	}

	@Override
	public boolean validateNameOnCard(String nameOnCreditCard) {
		boolean validName = false;
		if (Pattern.matches("^[a-zA-Z]*S", nameOnCreditCard) && (nameOnCreditCard != null))
			validName = true;
		return validName;
	}

	@Override
	public boolean deleteCardDetails(String UCI, BigInteger creditCardNumber) throws RmExceptions {
		return creditCardDao.deleteDetails(UCI, creditCardNumber);
	}

	@Override
	public void saveCardDetails(String UCI, CreditCard card) throws RmExceptions {
		creditCardDao.copyDetails(UCI, card);

	}
}
