package com.cg.ibs.rm.service;

import java.math.BigInteger;
import java.util.Set;

import com.cg.ibs.rm.bean.AutoPayment;
import com.cg.ibs.rm.bean.ServiceProvider;
import com.cg.ibs.rm.exception.RmExceptions;

public interface AutoPaymentService {
	public Set<ServiceProvider> showIBSServiceProviders();
	
	public Set<AutoPayment> showAutopaymentDetails(String uci);
	
	public boolean autoDeduction(String uci, AutoPayment autoPayment, String dateOfStart) throws RmExceptions;
	
	public boolean updateRequirements(String uci, BigInteger spi) throws RmExceptions;
}
