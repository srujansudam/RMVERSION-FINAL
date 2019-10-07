package com.cg.ibs.rm.dao;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import com.cg.ibs.rm.ui.Type;
import com.cg.ibs.rm.bean.Beneficiary;
import com.cg.ibs.rm.bean.FinalCustomer;
import com.cg.ibs.rm.bean.TemporaryCustomer;
import com.cg.ibs.rm.exception.RmExceptions;

public class BeneficiaryDAOImpl implements BeneficiaryDAO {
	private static Map<String, TemporaryCustomer> tempMap = new HashMap<>();
	private static Map<String, FinalCustomer> finalMap = new HashMap<>();
	private static List<Beneficiary> savedBeneficiaries = new ArrayList<>();
	private static List<Beneficiary> unapprovedBeneficiaries = new ArrayList<>();
	private static TemporaryCustomer tempCustomer = new TemporaryCustomer();
	Iterator<Beneficiary> it;
	private static Beneficiary beneficiary1, beneficiary2, beneficiary3, beneficiary4;
	List<Beneficiary> beneficiaryList;

	static {
		beneficiary1.setAccountName("Ramesh Ranjan");
		beneficiary1.setAccountNumber(new BigInteger("3498256715894562"));
		beneficiary1.setBankName("HDFC");
		beneficiary1.setIfscCode("HDFC5478321");
		beneficiary1.setType(Type.SELFINOTHERS);
		savedBeneficiaries.add(beneficiary1);
		tempCustomer.setUCI("12345");
		tempMap.put("12345", tempCustomer);
		tempCustomer.setUnapprovedBeneficiaries(savedBeneficiaries);

		beneficiary2.setAccountName("Suraj Lohar");
		beneficiary2.setAccountNumber(new BigInteger("5479254792074527"));
		beneficiary2.setBankName("IDFC");
		beneficiary2.setIfscCode("IDFC2435612");
		beneficiary1.setType(Type.SELFINOTHERS);
		savedBeneficiaries.add(beneficiary2);
		tempCustomer.setUCI("123456");
		tempMap.put("123456", tempCustomer);
		tempCustomer.setUnapprovedBeneficiaries(savedBeneficiaries);

		beneficiary3.setAccountName("Shivani Rathore");
		beneficiary3.setAccountNumber(new BigInteger("1243567892345617"));
		beneficiary3.setBankName("CITI");
		beneficiary3.setIfscCode("CITI8573456");
		beneficiary1.setType(Type.OTHERSINOTHERS);
		savedBeneficiaries.add(beneficiary3);
		tempCustomer.setUCI("1234567");
		tempMap.put("1234567", tempCustomer);
		tempCustomer.setUnapprovedBeneficiaries(savedBeneficiaries);

		beneficiary4.setAccountName("Adharsh Sharma");
		beneficiary4.setAccountNumber(new BigInteger("8794361278956743"));
		beneficiary4.setBankName("Kotak Mahindra");
		beneficiary4.setIfscCode("KKBK3425167");
		beneficiary1.setType(Type.OTHERSINOTHERS);
		savedBeneficiaries.add(beneficiary4);
		tempCustomer.setUCI("12345678");
		tempMap.put("12345678", tempCustomer);
		tempCustomer.setUnapprovedBeneficiaries(savedBeneficiaries);
	}

	@Override
	public List<Beneficiary> getDetails(String uci) {
		beneficiaryList = finalMap.get(uci).getSavedBeneficiaries();
		return beneficiaryList;
	}

	@Override
	public void copyDetails(String uci, Beneficiary beneficiary) throws RmExceptions {
		if (finalMap.get(uci).getSavedBeneficiaries().contains(beneficiary)) {
			throw new RmExceptions("Beneficiary already added");
		} else {
			unapprovedBeneficiaries.add(beneficiary);
			tempCustomer.setUnapprovedBeneficiaries(unapprovedBeneficiaries);
			tempMap.put(uci, tempCustomer);
		}

	}

	public Beneficiary getBeneficiary(String uci, BigInteger accountNumber) {
		Beneficiary beneficiary1 = null;
		it = finalMap.get(uci).getSavedBeneficiaries().iterator();
		while (it.hasNext()) {
			Beneficiary beneficiary = it.next();
			if (beneficiary.getAccountNumber().equals(accountNumber)) {
				beneficiary1 = beneficiary;

			}
		}
		return beneficiary1;
	}

	@Override
	public boolean updateDetails(String uci, Beneficiary beneficiary1) throws RmExceptions {

		boolean result = false;

		if (!(finalMap.get(uci).getSavedBeneficiaries().contains(beneficiary1))) {
			throw new RmExceptions("Beneficiary doesn't exist");

		}
		it = finalMap.get(uci).getSavedBeneficiaries().iterator();
		while (it.hasNext()) {
			Beneficiary beneficiary = it.next();
			if (beneficiary.getAccountNumber().equals(beneficiary1.getAccountNumber())) {
				savedBeneficiaries.remove(beneficiary);
				result = true;
			}
		}
		unapprovedBeneficiaries.add(beneficiary1);
		tempCustomer.setUnapprovedBeneficiaries(unapprovedBeneficiaries);
		tempMap.put(uci, tempCustomer);

		return result;

	}

	@Override
	public boolean deleteDetails(String uci, BigInteger accountNumber) throws RmExceptions {
		boolean result = false;
		int count = 0;
		for (Beneficiary beneficiary : finalMap.get(uci).getSavedBeneficiaries()) {
			if (beneficiary.getAccountNumber().equals(accountNumber)) {
				count++;
			}
		}
		if (0 == count) {
			throw new RmExceptions("Beneficiary doesn't exist");
		} else {
			it = finalMap.get(uci).getSavedBeneficiaries().iterator();
			while (it.hasNext()) {
				Beneficiary beneficiary = it.next();
				if (beneficiary.getAccountNumber().equals(accountNumber)) {
					savedBeneficiaries.remove(beneficiary);
					result = true;
				}
			}
		}
		return result;
	}

}
