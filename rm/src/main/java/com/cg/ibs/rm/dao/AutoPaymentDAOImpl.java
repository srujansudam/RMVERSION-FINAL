package com.cg.ibs.rm.dao;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import com.cg.ibs.rm.bean.AutoPayment;
import com.cg.ibs.rm.bean.FinalCustomer;
import com.cg.ibs.rm.bean.ServiceProvider;
import com.cg.ibs.rm.exception.RmExceptions;

public class AutoPaymentDAOImpl implements AutoPaymentDAO {
	private static Set<ServiceProvider> providers = new HashSet<>();
	private static FinalCustomer finalCustomer, finalCustomer1, finalCustomer2, finalCustomer3;
	private static Map<String, FinalCustomer> finalMap = new HashMap<>();
	private static Set<AutoPayment> savedAutoPaymentServices = new HashSet<>();
	private static Set<AutoPayment> savedAutoPaymentServices1 = new HashSet<>();
	private static Set<AutoPayment> savedAutoPaymentServices2 = new HashSet<>();
	Iterator<AutoPayment> it;
	BigDecimal bigDecimal;
	private static AutoPayment autoPayment1, autoPayment2, autoPayment3;
	private static ServiceProvider provider1, provider2, provider3, provider4;
	static {
		provider1.setSpi(new BigInteger("1"));
		provider1.setNameOfCompany("Airtel");
		providers.add(provider1);

		provider2.setSpi(new BigInteger("2"));
		provider2.setNameOfCompany("Reliance Jio");
		providers.add(provider2);

		provider3.setSpi(new BigInteger("3"));
		provider3.setNameOfCompany("Tata Sky");
		providers.add(provider3);

		provider4.setSpi(new BigInteger("4"));
		provider4.setNameOfCompany("MTS India");
		providers.add(provider4);

		autoPayment1.setAmount(new BigDecimal("500"));
		autoPayment1.setDateOfStart("12/12/2019");
		autoPayment1.setServiceProviderId(new BigInteger("1"));
		savedAutoPaymentServices1.add(autoPayment1);

		autoPayment2.setAmount(new BigDecimal("1000"));
		autoPayment2.setDateOfStart("10/01/2020");
		autoPayment2.setServiceProviderId(new BigInteger("1"));
		savedAutoPaymentServices1.add(autoPayment2);

		autoPayment3.setAmount(new BigDecimal("1500"));
		autoPayment3.setDateOfStart("20/03/2020");
		autoPayment3.setServiceProviderId(new BigInteger("2"));
		savedAutoPaymentServices2.add(autoPayment3);

		finalCustomer1.setUCI("12345");
		finalCustomer1.setCurrentBalance(new BigDecimal("100000"));
		finalCustomer1.setSavedAutoPaymentServices(savedAutoPaymentServices);
		finalCustomer2.setUCI("123456");
		finalCustomer2.setCurrentBalance(new BigDecimal("500000"));
		finalCustomer2.setSavedAutoPaymentServices(savedAutoPaymentServices1);

		finalCustomer3.setUCI("1234567");
		finalCustomer3.setCurrentBalance(new BigDecimal("50000"));

		finalMap.put("12345", finalCustomer1);
		finalMap.put("12345", finalCustomer2);
		finalMap.put("12345", finalCustomer3);

	}

	public Set<AutoPayment> getAutopaymentDetails(String uci) {
		savedAutoPaymentServices = finalMap.get(uci).getSavedAutoPaymentServices();
		return savedAutoPaymentServices;
	}

	@Override
	public void copyDetails(String uci, AutoPayment autoPayment) {
		savedAutoPaymentServices.add(autoPayment);
		finalCustomer.setSavedAutoPaymentServices(savedAutoPaymentServices);
		finalMap.put(uci, finalCustomer);
	}

	@Override
	public boolean deleteDetails(String uci, BigInteger serviceProviderId) throws RmExceptions {
		boolean result = false;
		int count = 0;
		for (AutoPayment autoPayment : finalMap.get(uci).getSavedAutoPaymentServices()) {
			if (autoPayment.getServiceProviderId().equals(serviceProviderId)) {
				count++;
			}
		}
		if (0 == count) {
			throw new RmExceptions("Auto payment service doesn't exist");
		} else {
			it = finalMap.get(uci).getSavedAutoPaymentServices().iterator();
			while (it.hasNext()) {
				AutoPayment autoPayment = it.next();
				if (autoPayment.getServiceProviderId().equals(serviceProviderId)) {
					savedAutoPaymentServices.remove(autoPayment);
					result = true;
				}
			}
			return result;
		}
	}

	public Set<ServiceProvider> showServiceProviderList() {
		System.out.println(providers.isEmpty());
		return providers;
	}

	@Override
	public BigDecimal getCurrentBalance(String uci) {
		bigDecimal = finalMap.get(uci).getCurrentBalance();
		return bigDecimal;
	}

	@Override
	public void setCurrentBalance(String uci, BigDecimal currentBalnce) {
		finalCustomer.setCurrentBalance(currentBalnce);
		finalMap.put(uci, finalCustomer);
	}

}
