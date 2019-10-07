package com.cg.ibs.rm.dao;

import java.math.BigInteger;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import com.cg.ibs.rm.bean.CreditCard;
import com.cg.ibs.rm.bean.FinalCustomer;
import com.cg.ibs.rm.bean.TemporaryCustomer;
import com.cg.ibs.rm.exception.RmExceptions;

public class CreditCardDAOImpl implements CreditCardDAO {
	private static FinalCustomer customer = new FinalCustomer();
	private static Map<String, TemporaryCustomer> tempMap = new HashMap<>();
	private static Set<CreditCard> savedCreditCards = new HashSet<>();
	private static Map<String, FinalCustomer> finalMap = new HashMap<>();
	private static Set<CreditCard> unapprovedCreditCards = new HashSet<>();
	private static TemporaryCustomer tempCustomer = new TemporaryCustomer();
	private static TemporaryCustomer tempCustomer1 = new TemporaryCustomer();
	private static TemporaryCustomer tempCustomer2 = new TemporaryCustomer();
	Iterator<CreditCard> it;
	private static CreditCard creditCard1, creditCard2;
	Set<CreditCard> creditCardList;
	static{
		
		creditCard1.setcreditCardNumber(new BigInteger("235167231425"));
		creditCard1.setnameOnCreditCard("Shubham Gupta");
		creditCard1.setcreditDateOfExpiry(LocalDate.of(2022, 8, 10));
		unapprovedCreditCards.add(creditCard1);
		tempCustomer1.setUCI("12345");
		tempCustomer1.setUnapprovedCreditCards(unapprovedCreditCards);
		tempMap.put("12345", tempCustomer1);
		
		
		creditCard2.setcreditCardNumber(new BigInteger("561234567819"));
		creditCard2.setnameOnCreditCard("Ayush Kumar");
		creditCard2.setcreditDateOfExpiry(LocalDate.of(2024, 11, 15));
		unapprovedCreditCards.add(creditCard2);
		tempCustomer2.setUCI("123456");
		tempCustomer2.setUnapprovedCreditCards(unapprovedCreditCards);
		tempMap.put("123456", tempCustomer2);

		}
	@Override
	public Set<CreditCard> getDetails(String uci) {
		creditCardList = finalMap.get(uci).getSavedCreditCards();
		return creditCardList;
	}

	@Override
	public void copyDetails(String uci, CreditCard card) throws RmExceptions {
		if (finalMap.get(uci).getSavedCreditCards().contains(card)) {
			throw new RmExceptions("Credit card already added");
		} else {
			unapprovedCreditCards.add(card);
			customer.setSavedCreditCards(unapprovedCreditCards);
			tempMap.put(uci, tempCustomer);
		}
	}

	@Override
	public boolean deleteDetails(String uci, BigInteger cardNumber) throws RmExceptions {
		boolean result = false;

		int count = 0;
		for (CreditCard card : finalMap.get(uci).getSavedCreditCards()) {
			if (card.getcreditCardNumber().equals(cardNumber)) {
				count++;
			}
		}
		if (0 == count) {
			throw new RmExceptions("Credit card doesn't exist");
		} else {
			it = finalMap.get(uci).getSavedCreditCards().iterator();
			while (it.hasNext()) {
				CreditCard card = it.next();
				if (card.getcreditCardNumber().equals(cardNumber)) {
					savedCreditCards.remove(card);
					result = true;
				}
			}
			return result;
		}
	}
	
}
