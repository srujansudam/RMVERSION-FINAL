package com.cg.ibs.rm.service;

import java.math.BigInteger;
import java.util.List;
import java.util.regex.Pattern;

import com.cg.ibs.rm.bean.Beneficiary;
import com.cg.ibs.rm.dao.BeneficiaryDAOImpl;
import com.cg.ibs.rm.exception.RmExceptions;

public class BeneficiaryAccountServiceImpl implements BeneficiaryAccountService {

	BeneficiaryDAOImpl beneficiaryDao;
	Beneficiary beneficiary = new Beneficiary();
	List<Beneficiary> beneficiaryList;
	

	@Override
	public List<Beneficiary> showBeneficiaryAccount(String uci) {
		beneficiaryList = beneficiaryDao.getDetails(uci);
		return beneficiaryList;
	}

	@Override
	public boolean validateBeneficiaryAccountNumber(BigInteger accountNumber) {
		boolean validNumber = true;
		if (accountNumber.compareTo(new BigInteger("99999999999")) == -1
				|| accountNumber.compareTo(new BigInteger("10000000000000")) == 1)
			validNumber = false;
		return validNumber;
	}

	@Override
	public boolean validateBeneficiaryAccountNameOrBankName(String name) {
		boolean validName = false;
		if (Pattern.matches("^[a-zA-Z]*S", name) && (null != name))
			validName = true;
		return validName;
	}

	@Override
	public boolean validateBeneficiaryIfscCode(String ifsc) {
		boolean validIfsc = false;
		if (ifsc.length() == 11)
			validIfsc = true;
		return validIfsc;
	}

	@Override
	public boolean modifyBeneficiaryAccountDetails(int choice, String uci, String changeValue, BigInteger accountNumber)
			throws RmExceptions {
		beneficiary = beneficiaryDao.getBeneficiary(uci, accountNumber);
		boolean validModify = false;
		switch (choice) {
		case 1:
			beneficiary.setAccountName(changeValue);
			break;

		case 2:
			beneficiary.setIfscCode(changeValue);
			break;

		case 3:
			beneficiary.setBankName(changeValue);
			break;

		default:
			throw new RmExceptions("Invalid choice");
		}

		validModify = beneficiaryDao.updateDetails(uci, beneficiary);
		return validModify;
	}

	@Override
	public boolean deleteBeneficiaryAccountDetails(String uci, BigInteger accountNumber) throws RmExceptions {
		return beneficiaryDao.deleteDetails(uci, accountNumber);
	}

	@Override
	public boolean saveBeneficiaryAccountDetails(String uci, Beneficiary beneficiary) throws RmExceptions {
		beneficiaryDao.copyDetails(uci, beneficiary);
		return true;
	}

}
