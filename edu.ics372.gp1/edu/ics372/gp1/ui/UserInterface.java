package edu.ics372.gp1.ui;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.Iterator;
import java.util.List;
import java.util.StringTokenizer;

import edu.ics372.gp1.facade.GroceryStore;
import edu.ics372.gp1.facade.Request;
import edu.ics372.gp1.facade.Result;

public class UserInterface {
	private static UserInterface userInterface;
	private static GroceryStore groceryStore;

	private BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

	private static final int EXIT = 0;
	private static final int ADD_MEMBER = 1;
	private static final int REMOVE_MEMBER = 2;
	private static final int GET_MEMBER_INFO = 3;
	private static final int ADD_PRODUCT = 4;
	private static final int CHECKOUT_MEMBER = 5;
	private static final int GET_PRODUCT_INFO = 6;
	private static final int PROCESS_SHIPMENT = 7;
	private static final int CHANGE_PRICE = 8;
	private static final int PRINT_TRANSACTIONS = 9;
	private static final int LIST_ALL_MEMBERS = 10;
	private static final int LIST_ALL_PRODUCTS = 11;
	private static final int LIST_OUTSTANDING_ORDERS = 12;
	private static final int SAVE = 13;
	private static final int RETRIEVE = 14;
	private static final int HELP = 15;

	private UserInterface() {
		if (yesOrNo("Looke for saved data and use it?")) {
			retrieve();
		} else {
			groceryStore = groceryStore.instance();
		}
	}

	public static UserInterface instance() {
		if (userInterface == null) {
			userInterface = new UserInterface();
		}
		return userInterface;
	}

	private boolean yesOrNo(String prompt) {
		String more = getToken(prompt + " (Y|y)[es] or anything else for no");
		if (more.charAt(0) != 'y' && more.charAt(0) != 'Y') {
			return false;
		}
		return true;
	}

	public String getToken(String prompt) {
		do {
			try {
				System.out.println(prompt);
				String line = reader.readLine();
				StringTokenizer tokenizer = new StringTokenizer(line, "\n\r\f");
				if (tokenizer.hasMoreTokens()) {
					return tokenizer.nextToken();
				}
			} catch (IOException ioe) {
				System.exit(0);
			}
		} while (true);
	}

	public String getName(String prompt) {
		do {
			try {
				System.out.println(prompt);
				String line = reader.readLine();
				return line;
			} catch (IOException ioe) {
				System.exit(0);
			}
		} while (true);

	}

	public int getNumber(String prompt) {
		do {
			try {
				String item = getToken(prompt);
				Integer number = Integer.valueOf(item);
				return number.intValue();
			} catch (NumberFormatException nfe) {
				System.out.println("Please input a number ");
			}
		} while (true);
	}

	public double getDouble(String prompt) {
		do {
			try {
				String item = getToken(prompt);
				Double number = Double.valueOf(item);
				return number.doubleValue();
			} catch (NumberFormatException nfe) {
				System.out.println("Please input a double ");
			}
		} while (true);
	}

	public Calendar getDate(String prompt) {
		do {
			try {
				Calendar date = new GregorianCalendar();
				String item = getToken(prompt);
				DateFormat dateFormat = SimpleDateFormat.getDateInstance(DateFormat.SHORT);
				date.setTime(dateFormat.parse(item));
				return date;
			} catch (Exception fe) {
				System.out.println("Please input a date as mm/dd/yy");
			}
		} while (true);
	}

	public int getCommand() {
		do {
			try {
				int value = Integer.parseInt(getToken("Enter command:" + HELP + " for help"));
				if (value >= EXIT && value <= HELP) {
					return value;
				}
			} catch (NumberFormatException nfe) {
				System.out.println("Enter a number");
			}
		} while (true);
	}

	public void help() {
		System.out.println("Enter a number between 0 and 15 as explained below:");
		System.out.println(EXIT + " to Exit\n");
		System.out.println(ADD_MEMBER + " to add a member");
		System.out.println(REMOVE_MEMBER + " to remove a member");
		System.out.println(GET_MEMBER_INFO + " to  print info about a given member");
		System.out.println(ADD_PRODUCT + " to add a new product");
		System.out.println(CHECKOUT_MEMBER + " to checkout a member's cart");
		System.out.println(GET_PRODUCT_INFO + " to retrieve information about a product");
		System.out.println(PROCESS_SHIPMENT + " to process a received shipment of a product");
		System.out.println(CHANGE_PRICE + " to change the price of a product");
		System.out.println(PRINT_TRANSACTIONS + " to print transactions for a member on/between two dates");
		System.out.println(LIST_ALL_MEMBERS + " to print name, id, and address of each member");
		System.out.println(LIST_ALL_PRODUCTS + " to  print name, id, price, and reorder level for each product");
		System.out.println(LIST_OUTSTANDING_ORDERS + " to print each order that has not been receieved yet");
		System.out.println(SAVE + " to  save data");
		System.out.println(HELP + " for help");
	}

	public void process() {
		int command;
		help();

		while ((command = getCommand()) != EXIT) {
			switch (command) {
			case HELP:
				help();
				break;
			case ADD_MEMBER:
				addMember();
				break;
			case REMOVE_MEMBER:
				removeMember();
				break;
			case GET_MEMBER_INFO:
				getMemberInfo();
				break;
			case ADD_PRODUCT:
				addProduct();
				break;
			case CHECKOUT_MEMBER:
				checkoutMember();
				break;
			case GET_PRODUCT_INFO:
				getProductInfo();
				break;
			case PROCESS_SHIPMENT:
				processShipment();
				break;
			case CHANGE_PRICE:
				changePrice();
				break;
			case PRINT_TRANSACTIONS:
				printTransactions();
				break;
			case LIST_ALL_MEMBERS:
				listAllMembers();
				break;
			case LIST_ALL_PRODUCTS:
				listAllProducts();
				break;
			case LIST_OUTSTANDING_ORDERS:
				listOutstandingOrders();
				break;
			case SAVE:
				save();
				break;
			default:
				unkownCommand();
				break;
			}
		}
	}

	public void addMember() {
		Request.instance().setMemberName(getName("Enter a name"));
		Request.instance().setMemberAddress(getName("Enter an address"));
		Request.instance().setMemberPhoneNumber(getName("Enter a phone number"));
		Request.instance().setMemberFeePaid(Double.toString(getDouble("Enter fee paid")));

		Result result = groceryStore.addMember(Request.instance());

		if (result.getResultCode() != Result.OPERATION_COMPLETED) {
			System.out.println("Member could not be added");
		} else {
			System.out
					.println("Member: " + result.getMemberName() + " with ID: " + result.getMemberID() + " was added.");
		}
	}

	public void removeMember() {
		Request.instance().setMemberID(Integer.toString(getNumber("Enter ID of member to remove")));

		Result result = groceryStore.removeMember(Request.instance());

		switch (result.getResultCode()) {
		case Result.OPERATION_COMPLETED:
			System.out.println("Member: " + result.getMemberName() + " with ID: " + result.getMemberID()
					+ " successfully removed.");
			break;
		case Result.MEMBER_NOT_FOUND:
			System.out.println("Member " + Request.instance().getMemberID() + " not found.");
			break;
		// add other cases for other error codes?
		}
	}

	public void getMemberInfo() {
		Request.instance().setMemberName((getName("Enter name of member to get information on")));

		Iterator<Result> result = groceryStore.getMemberInfo(Request.instance());

		while (result.hasNext()) {
			Result memberResult = result.next();
			System.out
					.println("Member: " + memberResult.getMemberName() + " Address: " + memberResult.getMemberAddress()
							+ " Fee Paid: " + memberResult.getMemberFeePaid() + " ID: " + memberResult.getMemberID());
		}
		System.out.println("\nEnd of list of searched members.\n");
	}

	public void addProduct() {
		Request.instance().setProductName(getName("Enter name of product"));
		Request.instance().setProductPrice(Double.toString(getDouble("Enter price of product e.x. 10.99")));
		Request.instance().setProductReorderLevel(Integer.toString(getNumber("Enter product reorder level")));

		Result result = groceryStore.addProduct(Request.instance());
		switch (result.getResultCode()) {
		case Result.PRODUCT_NAME_INVALID:
			System.out.println(
					"Product could not be added. Product name: " + result.getProductName() + " is already in use.");
			break;
		case Result.OPERATION_COMPLETED:
			System.out.println("Product successfully added. Name: " + result.getProductName() + " Price: "
					+ result.getProductPrice() + " Reorder level: " + result.getProductReorderLevel() + " ID: "
					+ result.getProductID());
			break;
		case Result.OPERATION_FAILED:
			System.out.println("Product could not be added.");
			break;
		}
	}

	public void checkoutMember() {
		String memberID = getName("Enter ID of member to checkout.");
		// create new checkout
		Request.instance().setMemberName(memberID);
		Result result = groceryStore.createNewCheckout(Request.instance());
		if (result.getResultCode() != Result.OPERATION_COMPLETED) {
			System.out.println("New checkout could not be created");
			if (result.getResultCode() == Result.MEMBER_NOT_FOUND) {
				System.out.println("Member with ID: " + memberID + " was not found.");
			}
		} else {

			// add products to checkout
			boolean continuing = true;

			while (continuing) {

				Request.instance().setMemberName(memberID);
				Request.instance().setProductID(Integer.toString(getNumber("Enter a product ID to add to checkout.")));
				Request.instance().setProductStock(
						Integer.toString(getNumber("Enter a quantity of product to add to checkout.")));
				result = groceryStore.addProductToCheckout(Request.instance());

				switch (result.getResultCode()) {
				case Result.PRODUCT_NOT_FOUND:
					System.out.println("Product was not found, could not be added to checkout.");
					break;
				case Result.PRODUCT_OUT_OF_STOCK:
					System.out.println(
							"Product does not have enough stock to add to checkout, was not added to checkout.");
					break;
				case Result.OPERATION_COMPLETED:
					System.out.println("Product was successfully added to cart.");
					break;
				}

				continuing = yesOrNo("Add another product?");
			}
			// complete checkout
			Request.instance().setMemberID(memberID);
			Iterator<Result> resultList = groceryStore.completeCheckout(Request.instance());
			Result productResult = new Result();
			List<String> reorderList = new LinkedList<String>();
			while (resultList.hasNext()) {
				productResult = resultList.next();
				double subTotal = Double.parseDouble(productResult.getProductPrice())
						* Integer.parseInt(productResult.getProductStock());
				System.out.println(productResult.getProductName() + "\t" + productResult.getProductStock() + "\t$"
						+ productResult.getProductPrice() + "\t$" + subTotal);
				if (productResult.getResultCode() == Result.PRODUCT_REORDERED) {
					reorderList.add(productResult.getProductName());
				}
			}
			System.out.println("Total\t\t\t$" + productResult.getTransactionTotalPrice());
			Iterator<String> reorderIterator = reorderList.iterator();
			while (reorderIterator.hasNext()) {
				System.out.println("Product: " + reorderIterator.next() + " has been reordered.");
			}

		}
	}

	public void getProductInfo() {
		Request.instance().setProductName(getName("Enter a product name."));
		Result result = groceryStore.getProductInfo(Request.instance());
		switch(result.getResultCode()) {
		case Result.PRODUCT_NOT_FOUND:
			System.out.println("Product: " + result.getProductName() + " was not found.");
			break;
		case Result.OPERATION_COMPLETED:
			System.out.printlmn("Product: " + result.getProductName() + " ID: " + result.getProductID() +
					"")
		}
	}

	public void processShipment() {

	}

	public void changePrice() {

	}

	public void printTransactions() {

	}

	public void listAllMembers() {

	}

	public void listAllProducts() {

	}

	public void listOutstandingOrders() {

	}

	public void save() {

	}

	public void retrieve() {

	}

	public void unkownCommand() {

	}

	public static void main(String[] args) {
		UserInterface.instance().process();
	}
}
