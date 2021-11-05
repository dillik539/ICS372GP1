package edu.ics372.gp1.tests;

import java.util.Iterator;

import edu.ics372.gp1.entities.Member;
import edu.ics372.gp1.entities.Product;
import edu.ics372.gp1.facade.GroceryStore;
import edu.ics372.gp1.facade.Request;
import edu.ics372.gp1.facade.Result;

public class AutomatedTester {
	private GroceryStore groceryStore;
	private String[] names = { "n1", "n2", "n3" };
	private String[] addresses = { "a1", "a2", "a3" };
	private String[] phones = { "p1", "p2", "p3" };
	private double[] fees = { 1.0, 2.0, 3.0 };
	private Member[] members = new Member[3];
	private String[] productsName = { "Apple", "Pear", "Orange" };
	private String[] price = { "1.5", "2.5", "3.5" };
	private String[] reorderLevel = { "10", "11", "12" };
	
	public void testAll() {
		addMembersTest();
		getMemberInfoTest();
		addProductTest();
		checkoutTest();
		getProductInfoTest();
		processShipmentTest();
		changePriceTest();
		printTransactionsTest();
		listAllMembersTest();
		listAllProductsTest();
		listOutstandingOrdersTest();
		//i put remove member last since we need member ID to do checkout test.
		removeMembersTest(); 
	}

	public void addMembersTest() {
		System.out.println("Testing add members");
		for (int count = 0; count < members.length; count++) {
			Request.instance().setMemberAddress(addresses[count]);
			Request.instance().setMemberName(names[count]);
			Request.instance().setMemberPhoneNumber(phones[count]);
			Request.instance().setMemberFeePaid(Double.toString(fees[count]));
			Result result = GroceryStore.instance().addMember(Request.instance());
			assert result.getResultCode() == Result.OPERATION_COMPLETED;
			assert result.getMemberName().equals(names[count]);
			assert result.getMemberAddress().equals(addresses[count]);
			assert result.getMemberPhoneNumber().equals(phones[count]);
			assert result.getMemberFeePaid().equals(Double.toString(fees[count]));
			// System.out.println(result.getMemberID());
		}
	}

	public void removeMembersTest() {
		System.out.println("Testing remove member");
		for(int count = 0; count < members.length; count++) {
			Request.instance().setMemberID(members[count].getId());
			Result result = GroceryStore.instance().removeMember(Request.instance());
			assert result.getResultCode() == Result.OPERATION_COMPLETED;
			assert result.getMemberName().equals(names[count]);
			assert result.getMemberAddress().equals(addresses[count]);
			assert result.getMemberPhoneNumber().equals(phones[count]);
			assert result.getMemberFeePaid().equals(Double.toString(fees[count]));
		}
	}

	public void getMemberInfoTest() {
		System.out.println("Testing get member(s) by name");
		for (int count = 0; count < members.length; count++) {
			Request.instance().setMemberName(names[count]);
			Iterator<Result> results = GroceryStore.instance().getMemberInfo(Request.instance());
			while (results.hasNext()) {
				Result result = results.next();
				assert result.getMemberName().equals(names[count]);
			}
		}
	}

	public void addProductTest() {
		System.out.println("Testing add Product");
		for (int count = 0; count < 3; count++) {
			Request.instance().setProductName(productsName[count]);
			Request.instance().setProductReorderLevel(reorderLevel[count]);
			Request.instance().setProductPrice(price[count]);
			Result result = GroceryStore.instance().addProduct(Request.instance());
			assert result.getResultCode() == Result.OPERATION_COMPLETED;
			assert result.getProductName().equals(productsName[count]);
			assert result.getProductReorderLevel().equals(reorderLevel[count]);
			assert result.getProductPrice().equals(price[count]);
		}
	}

	public void checkoutTest() {
		// create checkout
		// add to checkout
		// complete checkout process
	}

	public void getProductInfoTest() {

	}

	public void processShipmentTest() {

	}

	public void changePriceTest() {

	}

	public void printTransactionsTest() {

	}

	public void listAllMembersTest() {

	}

	public void listAllProductsTest() {

	}

	public void listOutstandingOrdersTest() {

	}

	public static void main(String[] args) {
		// runs all tests

		/*
		 * Uncomment the line commented out below to make sure you have assertions
		 * enabled in eclipse, it should throw am AssetionError exception If it does
		 * not, go to Window->Preferences->installed JRES, click the JRE you are using,
		 * click edit, and add -ea as the Default VM arguments
		 * 
		 * If you aren't using eclipse, google it I guess?
		 */

		// assert false;
		new AutomatedTester().testAll();
	}

}
