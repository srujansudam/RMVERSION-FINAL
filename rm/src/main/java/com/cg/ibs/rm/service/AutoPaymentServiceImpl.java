package com.cg.ibs.rm.service;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Set;
import com.cg.ibs.rm.bean.AutoPayment;
import com.cg.ibs.rm.bean.ServiceProvider;
import com.cg.ibs.rm.dao.AutoPaymentDAO;
import com.cg.ibs.rm.dao.AutoPaymentDAOImpl;
import com.cg.ibs.rm.exception.RmExceptions;

public class AutoPaymentServiceImpl implements AutoPaymentService {
	AutoPaymentDAO autoPaymentDao;
	Set<AutoPayment> autoPayments;
	Set<ServiceProvider> providers1;
	
	
	@Override
	public Set<AutoPayment> showAutopaymentDetails(String uci) {
		autoPayments = autoPaymentDao.getAutopaymentDetails(uci);
		return autoPayments;
	}

	@Override
	public Set<ServiceProvider> showIBSServiceProviders() {
		System.out.println("1");
		providers1 = autoPaymentDao.showServiceProviderList();
		return providers1;
	}

	@Override
	public boolean autoDeduction(String uci, AutoPayment autoPayment, String dateOfStart) throws RmExceptions {
		LocalDate today = LocalDate.now();
		boolean validAutoDeduct = false;
		DateTimeFormatter dtFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
		LocalDate startOfAutoPayment = LocalDate.parse(dateOfStart, dtFormatter);
		if (startOfAutoPayment.isBefore(today)) {
			throw new RmExceptions("Enter a valid date");
		} else {
			if (today.equals(startOfAutoPayment)) {
				if (-1 != autoPaymentDao.getCurrentBalance(uci).compareTo(autoPayment.getAmount())) {
					BigDecimal balance = autoPaymentDao.getCurrentBalance(uci).subtract(autoPayment.getAmount());
					autoPaymentDao.setCurrentBalance(uci, balance);
					startOfAutoPayment.plusMonths(1);
				}
			}
			validAutoDeduct = true;
		}
		return validAutoDeduct;
	}

	@Override
	public boolean updateRequirements(String uci, BigInteger spi) throws RmExceptions {
		return autoPaymentDao.deleteDetails(uci, spi);
	}

}
