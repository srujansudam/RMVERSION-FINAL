package com.cg.ibs.rm.service;

import java.math.BigInteger;
import java.util.Set;

import com.cg.ibs.rm.bean.CreditCard;
import com.cg.ibs.rm.exception.RmExceptions;

public interface CreditCardService {
	public Set<CreditCard> showCardDetails(String UCI);

	public boolean validateCardNumber(BigInteger creditCardNumber);

	public boolean validateDateOfExpiry(String creditDateOfExpiry);

	public boolean validateNameOnCard(String nameOnCreditCard);

	public boolean deleteCardDetails(String UCI, BigInteger creditCardNumber) throws RmExceptions;

	public void saveCardDetails(String UCI, CreditCard card) throws RmExceptions;
}
