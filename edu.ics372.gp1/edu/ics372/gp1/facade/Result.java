package edu.ics372.gp1.facade;

/**
 * This class is used for returning many of the results of the GroceryStore
 * system's business logic to user interface.
 * 
 * The Result object returns an int resultCode, plus values of selected fields
 * of Book and Member. They are the book title, id, borrower id, due date,
 * member name, member phone, and member id.
 * 
 * 
 * @author Joseph Jackels
 *
 */
public class Result extends DataTransfer {
	public static final int OPERATION_COMPLETED = 0;
	public static final int OPERATION_FAILED = 1;
	public static final int MEMBER_NOT_FOUND = 2;
	public static final int PRODUCT_NAME_INVALID = 3;
	public static final int PRODUCT_NOT_FOUND = 4;
	public static final int PRODUCT_OUT_OF_STOCK = 5;
	public static final int PRODUCT_REORDERED = 6;
	public static final int PRODUCT_ALREADY_ORDERED = 7;
	public static final int INVALID_DATES = 8;
	public static final int ORDER_NOT_FOUND = 9;
	public static final int INCORRECT_RECEIVED_QUANTITY = 10;
	public static final int PRODUCT_ALREADY_REORDERED = 11;

	private int resultCode;

	public int getResultCode() {
		return resultCode;
	}

	public void setResultCode(int resultCode) {
		this.resultCode = resultCode;
	}
}
