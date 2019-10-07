package com.cg.ibs.rm.service;

import java.math.BigInteger;
import java.util.List;

import com.cg.ibs.rm.bean.Beneficiary;
import com.cg.ibs.rm.exception.RmExceptions;

public interface BeneficiaryAccountService {
	public List<Beneficiary> showBeneficiaryAccount(String uci);

	public boolean validateBeneficiaryAccountNumber(BigInteger accountNumber);

	public boolean validateBeneficiaryAccountNameOrBankName(String name);

	public boolean validateBeneficiaryIfscCode(String Ifsc);

	public boolean modifyBeneficiaryAccountDetails(int choice, String uci, String changeValue, BigInteger accountNumber)
			throws RmExceptions;

	public boolean deleteBeneficiaryAccountDetails(String uci, BigInteger accountNumber) throws RmExceptions;

	public boolean saveBeneficiaryAccountDetails(String uci, Beneficiary beneficiary) throws RmExceptions;
}
