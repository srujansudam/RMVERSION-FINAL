package com.cg.ibs.rm.ui;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.time.LocalDate;
import java.util.Iterator;
import java.util.Scanner;
import com.cg.ibs.rm.bean.AutoPayment;
import com.cg.ibs.rm.bean.Beneficiary;
import com.cg.ibs.rm.bean.CreditCard;
import com.cg.ibs.rm.exception.RmExceptions;
import com.cg.ibs.rm.service.AutoPaymentService;
import com.cg.ibs.rm.service.AutoPaymentServiceImpl;
import com.cg.ibs.rm.service.BankRepresentativeService;
import com.cg.ibs.rm.service.BankRepresentativeServiceImpl;
import com.cg.ibs.rm.service.BeneficiaryAccountService;
import com.cg.ibs.rm.service.BeneficiaryAccountServiceImpl;
import com.cg.ibs.rm.service.CreditCardService;
import com.cg.ibs.rm.service.CreditCardServiceImpl;

public class MainUi {
	static Scanner scanner;
	static Iterator<CreditCard> itCredit;
	static Iterator<Beneficiary> itBeneficiary;

	private CreditCardService cardService = new CreditCardServiceImpl();
	private BeneficiaryAccountService beneficiaryAccountService = new BeneficiaryAccountServiceImpl();
	private BankRepresentativeService bankRepresentativeService = new BankRepresentativeServiceImpl();
	private AutoPaymentService autopaymentservobj = new AutoPaymentServiceImpl();
	private String uci;

	void Start() throws Exception {
		MainMenu choice = null;
		while (MainMenu.QUIT != choice) {
			System.out.println("------------------------");
			System.out.println("Choose your identity from MENU:");
			System.out.println("------------------------");
			for (MainMenu menu : MainMenu.values()) {
				System.out.println((menu.ordinal()) + "\t" + menu);
			}
			System.out.println("Choice");
			int ordinal = scanner.nextInt();

			if (0 <= (ordinal) && MainMenu.values().length > ordinal) {
				choice = MainMenu.values()[ordinal];
				switch (choice) {
				case CUSTOMER:
					login();
					customerAction();
					break;
				case BANKREPRESENTATIVE:
					bankRepresentativeAction();
					break;
				case QUIT:
					System.out.println("Thankyou... Visit again!");
					break;
				}
			} else {
				System.out.println("Please enter a valid option.");
				choice = null;
			}
		}
	}

	public void customerAction() {
		CustomerUi choice = null;
		System.out.println("------------------------");
		System.out.println("Choose a valid option");
		System.out.println("------------------------");
		for (CustomerUi menu : CustomerUi.values()) {
			System.out.println(menu.ordinal() + "\t" + menu);
		}
		System.out.println("Choices:");
		int ordinal = scanner.nextInt();

		if (0 <= ordinal && CustomerUi.values().length > ordinal) {
			choice = CustomerUi.values()[ordinal];
			switch (choice) {
			case CREDITCARD:
				cardService.showCardDetails(uci);
				try {
					addOrModifyCreditCard();
				} catch (RmExceptions e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				break;
			case BENEFICIARY:
				try {
					addOrModifyBeneficiary();
				} catch (RmExceptions e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				break;
			case AUTOPAYMENT:
				addOrRemoveAutopayments();
				break;
			case EXIT:
				System.out.println("BACK ON HOME PAGE!!");
				break;
			}
		} else {
			System.out.println("Please enter a valid option.");
			customerAction();
		}
	}

	public void login() {
		System.out.println("Customer id:");
		uci = scanner.next();
		System.out.println("Password");
		scanner.next();
	}

	public void addOrModifyCreditCard() throws RmExceptions {
		CreditCard card = new CreditCard();
		int creditCardOption;
		do {

			System.out.println("Enter 1 to add a credit card \n2 for delete a credit card");
			creditCardOption = scanner.nextInt();
			switch (creditCardOption) {
			case 1:
				System.out.println("Please Enter your CreditCard number (16 digits)");
				BigInteger cardNumber = scanner.nextBigInteger();
				boolean valid = cardService.validateCardNumber(cardNumber);
				if (valid == true) {
					card.setcreditCardNumber(cardNumber);
				} else {
					while (valid == false) {
						System.out.println("Enter correct details again");
						cardNumber = scanner.nextBigInteger();
						valid = cardService.validateCardNumber(cardNumber);
					}
					card.setcreditCardNumber(cardNumber);
				}

				System.out.println("Please enter the Name on your CreditCard (Case Sensitive)");
				String nameOnCard = scanner.next();
				boolean valid2 = cardService.validateNameOnCard(nameOnCard);

				if (valid2) {

					card.setnameOnCreditCard(nameOnCard);
				} else {
					while (valid2 == false) {
						System.out.println("Enter correct details again");
						nameOnCard = scanner.next();
						valid2 = cardService.validateNameOnCard(nameOnCard);
					}
					card.setnameOnCreditCard(nameOnCard);
				}

				System.out.println("Please enter the expiry date on your card number in (MM/YY) format");
				String expiryDate = scanner.next();
				LocalDate creditExpiry = LocalDate.parse(expiryDate);
				boolean valid3 = cardService.validateDateOfExpiry(expiryDate);
				if (valid3) {

					card.setcreditDateOfExpiry(creditExpiry);
				} else {
					while (valid3 == false) {
						System.out.println("Enter correct details again");
						expiryDate = scanner.next();
						valid3 = cardService.validateDateOfExpiry(expiryDate);
					}
					card.setcreditDateOfExpiry(creditExpiry);
				}
				break;
			case 2:
				System.out.println("Enter card number");
				BigInteger creditCardNumber = scanner.nextBigInteger();
				cardService.deleteCardDetails(uci, creditCardNumber);
				break;
			default:
				System.out.println("Please enter a valid choice");
				creditCardOption = 0;
			}
		} while (0 == creditCardOption);
	}

	public void addOrModifyBeneficiary() throws RmExceptions {
		int beneficiaryOption;
		do {
			System.out.println(
					"Enter \n1 to add a beneficiary \n2 for modifying a beneficiary \n3 for delete a beneficiary");
			beneficiaryOption = scanner.nextInt();
			switch (beneficiaryOption) {
			case 1:
				Type choice = null;
				System.out.println("------------------------");
				System.out.println("Choose a valid option");
				System.out.println("------------------------");
				for (Type menu : Type.values()) {
					System.out.println(menu.ordinal() + "\t" + menu);
				}
				System.out.println("Choices:");
				int ordinal = scanner.nextInt();

				if (0 <= ordinal && CustomerUi.values().length > ordinal) {
					choice = Type.values()[ordinal];
					switch (choice) {
					case SELFINSAME:
						addBeneficiary(choice);
						break;
					case SELFINOTHERS:
						addBeneficiary(choice);
					case OTHERSINOTHERS:
						addBeneficiary(choice);
						break;
					case OTHERSINSAME:
						addBeneficiary(choice);
						break;
					}
				} else {
					System.out.println("Please enter a valid option.");
					customerAction();
				}
				break;
			case 2:
				System.out.println("Enter account number");
				BigInteger accountNumberToModify = scanner.nextBigInteger();
				int choiceToModify;
				do {
					System.out.println(
							"Enter 1 to change Account Holder Name\n2 to change IFSC code\n3to change bank name");
					choiceToModify = scanner.nextInt();
					switch (choiceToModify) {
					case 1:
						System.out.println("Enter new account holder name");
						String nameInAccount = scanner.next();
						boolean validName = beneficiaryAccountService
								.validateBeneficiaryAccountNameOrBankName(nameInAccount);

						if (validName == true) {
							beneficiaryAccountService.modifyBeneficiaryAccountDetails(choiceToModify, uci,
									nameInAccount, accountNumberToModify);
						} else {
							while (validName == false) {
								System.out.println("Enter correct details again");
								nameInAccount = scanner.next();
								validName = beneficiaryAccountService
										.validateBeneficiaryAccountNameOrBankName(nameInAccount);
							}
							beneficiaryAccountService.modifyBeneficiaryAccountDetails(choiceToModify, uci,
									nameInAccount, accountNumberToModify);
						}
						break;
					case 2:
						System.out.println("Enter new IFSC code");
						String ifscNewValue = scanner.next();
						boolean validIfsc = beneficiaryAccountService.validateBeneficiaryIfscCode(ifscNewValue);

						if (validIfsc == true) {
							beneficiaryAccountService.modifyBeneficiaryAccountDetails(choiceToModify, uci, ifscNewValue,
									accountNumberToModify);
						} else {
							while (validIfsc == false) {
								System.out.println("Enter correct details again");
								ifscNewValue = scanner.next();
								validIfsc = beneficiaryAccountService
										.validateBeneficiaryAccountNameOrBankName(ifscNewValue);
							}
							beneficiaryAccountService.modifyBeneficiaryAccountDetails(choiceToModify, uci, ifscNewValue,
									accountNumberToModify);
						}
						break;
					case 3:
						System.out.println("Enter new bank name");
						String bankNameNewValue = scanner.next();
						boolean validbankName = beneficiaryAccountService
								.validateBeneficiaryAccountNameOrBankName(bankNameNewValue);

						if (validbankName == true) {
							beneficiaryAccountService.modifyBeneficiaryAccountDetails(choiceToModify, uci,
									bankNameNewValue, accountNumberToModify);
						} else {
							while (validbankName == false) {
								System.out.println("Enter correct details again");
								bankNameNewValue = scanner.next();
								validbankName = beneficiaryAccountService
										.validateBeneficiaryAccountNameOrBankName(bankNameNewValue);
							}
							beneficiaryAccountService.modifyBeneficiaryAccountDetails(choiceToModify, uci,
									bankNameNewValue, accountNumberToModify);
						}
						break;

					default:
						System.out.println("Wrong Input");
						choiceToModify = 0;
					}
				} while (0 == choiceToModify);
			case 3:
				System.out.println("Enter account number to delete");
				BigInteger deleteAccountNum = scanner.nextBigInteger();
				beneficiaryAccountService.deleteBeneficiaryAccountDetails(uci, deleteAccountNum);
				break;
			default:
				System.out.println("Please enter a valid choice");
				beneficiaryOption = 0;
			}
		} while (0 == beneficiaryOption);
	}

	public void addBeneficiary(Type type) {
		Beneficiary beneficiary = new Beneficiary();
		System.out.println("Please Enter your Account number (12 digits)");
		BigInteger accountNumber = scanner.nextBigInteger();
		boolean valid = beneficiaryAccountService.validateBeneficiaryAccountNumber(accountNumber);
		if (valid == true) {
			beneficiary.setAccountNumber(accountNumber);
		} else {
			while (valid == false) {
				System.out.println("Enter correct details again");
				accountNumber = scanner.nextBigInteger();
				valid = beneficiaryAccountService.validateBeneficiaryAccountNumber(accountNumber);
			}
			beneficiary.setAccountNumber(accountNumber);
		}

		System.out.println("Please enter the Account Holder Name (Case Sensitive)");
		String nameInAccount = scanner.next();
		boolean valid2 = beneficiaryAccountService.validateBeneficiaryAccountNameOrBankName(nameInAccount);

		if (valid2) {

			beneficiary.setAccountName(nameInAccount);
		} else {
			while (valid2 == false) {
				System.out.println("Enter correct details again");
				nameInAccount = scanner.next();
				valid2 = beneficiaryAccountService.validateBeneficiaryAccountNameOrBankName(nameInAccount);
			}
			beneficiary.setAccountName(nameInAccount);
		}

		System.out.println("Please enter IFSC code(11 characters)");
		String ifsc = scanner.next();
		boolean valid3 = beneficiaryAccountService.validateBeneficiaryIfscCode(ifsc);
		if (valid3) {

			beneficiary.setIfscCode(ifsc);
		} else {
			while (valid3 == false) {
				System.out.println("Enter correct details again");
				ifsc = scanner.next();
				valid3 = beneficiaryAccountService.validateBeneficiaryIfscCode(ifsc);
			}
			beneficiary.setIfscCode(ifsc);
		}

		System.out.println("Enter bank name (case sensitive)");
		String bankName = scanner.next();
		boolean valid4 = beneficiaryAccountService.validateBeneficiaryAccountNameOrBankName(bankName);

		if (valid4 == true) {

			beneficiary.setBankName(bankName);
		} else {
			while (valid4 == false) {
				System.out.println("Enter correct details again");
				bankName = scanner.next();
				valid4 = beneficiaryAccountService.validateBeneficiaryAccountNameOrBankName(bankName);
			}
			beneficiary.setBankName(bankName);
		}
		beneficiary.setType(type);
	}

	public void bankRepresentativeAction() {
		BankRepresentativeUi choice = null;
		System.out.println("------------------------");
		System.out.println("Choose a valid option");
		System.out.println("------------------------");
		for (BankRepresentativeUi menu : BankRepresentativeUi.values()) {
			System.out.println(menu.ordinal() + "\t" + menu);
		}
		System.out.println("Choices:");
		int ordinal = scanner.nextInt();

		if (0 <= ordinal && BankRepresentativeUi.values().length > ordinal) {
			choice = BankRepresentativeUi.values()[ordinal];
			switch (choice) {
			case VIEWREQUESTS:
				try {
					showRequests();
				} catch (RmExceptions e) {
					e.printStackTrace();
				}
				break;
			case EXIT:
				System.out.println("BACK ON HOME PAGE!!");
				break;
			}
		} else {
			System.out.println("Please enter a valid option.");
			bankRepresentativeAction();
		}
	}

	public void showRequests() throws RmExceptions {
		System.out.println(bankRepresentativeService.showRequests());
		String uci;
		System.out.println("Please enter the customer id to view individual requests \nEnter 0 to exit");
		uci = scanner.next();
		while (!(uci.equals("0"))) {
			System.out.println("Id entered by you is : " + uci);
			int choice1;
			do {
				System.out.println(
						"Enter 1 to view Creditcard requests \nEnter 2 to view Beneficiary Requests \nEnter 3 to go to the previous list ");
				choice1 = scanner.nextInt();
				switch (choice1) {
				case 1:
					itCredit = bankRepresentativeService.showUnapprovedCreditCards(uci).iterator();
					if (!(itCredit.hasNext())) {
						System.out.println("No more credit card requests");
						break;
					}
					while (itCredit.hasNext()) {
						CreditCard creditCard = itCredit.next();
						System.out.println(creditCard);
						int choice2;
						do {
							System.out.println(
									"Enter 1 to approve. \nEnter 2 to disapprove \n Enter 3 to Exit the section");
							choice2 = scanner.nextInt();
							switch (choice2) {
							case 1:
								bankRepresentativeService.saveCreditCardDetails(uci, creditCard);
								System.out.println("Card approved");
								break;
							case 2:
								bankRepresentativeService.removeTempCreditCardDetails(uci, creditCard);
								System.out.println("Card disapproved");
								break;
							default:
								System.out.println("Enter a valid choice of decision of credit card");
								choice2 = 0;
							}
						} while (0 == choice2);
					}
					break;
				case 2:
					itBeneficiary = bankRepresentativeService.showUnapprovedBeneficiaries(uci).iterator();
					if (!(itBeneficiary.hasNext())) {
						System.out.println("No more beneficiary requests");
						break;
					}
					while (itBeneficiary.hasNext()) {
						Beneficiary beneficiary = itBeneficiary.next();
						System.out.println(beneficiary);
						int choice2;
						do {
							System.out.println("Enter 1 to approve. \nEnter 2 to disapprove ");
							choice2 = scanner.nextInt();
							switch (choice2) {
							case 1:
								bankRepresentativeService.saveBeneficiaryDetails(uci, beneficiary);
								System.out.println("Beneficiary approved");
								break;
							case 2:
								bankRepresentativeService.removeTempBeneficiaryDetails(uci, beneficiary);
								System.out.println("Beneficiary disapproved");
								break;
							default:
								System.out.println("Enter a valid choice of decision of beneficiary");
								choice2 = 0;
							}
						} while (0 == choice2);
					}
					break;
				case 3:
					choice1 = 0;
					uci = "0";
					break;
				default:
					System.out.println("Enter a valid choice of action");
				}

			} while (0 != choice1);
			System.out.println("Please enter the customer id to view individual requests \nEnter 0 to exit");
			uci = scanner.next();
		}

	}

	public void addOrRemoveAutopayments() {
		AutoPaymentUi choice = null;
		System.out.println("------------------------");
		System.out.println("Choose a valid option");
		System.out.println("------------------------");
		for (AutoPaymentUi menu : AutoPaymentUi.values()) {
			System.out.println(menu.ordinal() + "\t" + menu);
		}
		System.out.println("Choices:");
		int ordinal = scanner.nextInt();

		if (0 <= ordinal && BankRepresentativeUi.values().length > ordinal) {
			choice = AutoPaymentUi.values()[ordinal];
			switch (choice) {
			case ADDAUTOPAYMENTS:
				try {
					addAutoPayment();
				} catch (RmExceptions e) {
					e.printStackTrace();
				}
				break;
			case REMOVEAUTOPAYMENTS:
				try {
					removeAutoPayment();
				} catch (RmExceptions e) {
					e.printStackTrace();
				}
				break;
			case EXIT:
				System.out.println("BACK ON HOME PAGE!!");
				break;
			default:
				System.out.println("Enter a valid choice");
				addOrRemoveAutopayments();
			}
		} else {
			System.out.println("Please enter a valid option.");
			addOrRemoveAutopayments();
		}
	}

	public void addAutoPayment() throws RmExceptions {
		BigInteger input;
		AutoPayment autoPayment = new AutoPayment();
		boolean check = false;
		System.out.println(autopaymentservobj.showIBSServiceProviders());
		System.out.println("Enter your Service provider Id");
		input = scanner.nextBigInteger();
		autoPayment.setServiceProviderId(input);
		System.out.println("Enter amount to be deducted");
		BigDecimal amount = scanner.nextBigDecimal();
		autoPayment.setAmount(amount);
		System.out.println("Enter your start date (in format dd/MM/yyyy)");
		String mydate = scanner.next();
		autoPayment.setDateOfStart(mydate);
		check = autopaymentservobj.autoDeduction(uci, autoPayment, mydate);

		if (check) {
			System.out.println("AutoPayment of service provider: " + input + " added and Rs. " + amount
					+ " will be deducted per month from the date of start");
		} else {
			System.out.println("Autopayment method not added");
		}
	}

	public void removeAutoPayment() throws RmExceptions {
		AutoPaymentService autopaymentservobj1 = new AutoPaymentServiceImpl();
		System.out.println(autopaymentservobj1.showAutopaymentDetails(uci));
		System.out.println("Enter your sevice provider id to be removed");
		BigInteger input3 = scanner.nextBigInteger();

		autopaymentservobj1.updateRequirements(uci, input3);
	}

	public static void main(String[] args) throws Exception {
		scanner = new Scanner(System.in);
		MainUi mainUii = new MainUi();
		mainUii.Start();
		scanner.close();
		System.out.println();
	}
}
