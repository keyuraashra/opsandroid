package mavinab.ops.pojo;

import java.io.Serializable;

public class OrderListPojo implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String itemName;
	private String itemQty;
	private String amount;

	public OrderListPojo(String itemName, String itemQty, String amount) {
		super();
		this.itemName = itemName;
		this.itemQty = itemQty;
		this.amount = amount;
	}

	/**
	 * @return the itemName
	 */
	public String getItemName() {
		return itemName;
	}

	/**
	 * @param itemName
	 *            the itemName to set
	 */
	public void setItemName(String itemName) {
		this.itemName = itemName;
	}

	/**
	 * @return the itemQty
	 */
	public String getItemQty() {
		return itemQty;
	}

	/**
	 * @param itemQty
	 *            the itemQty to set
	 */
	public void setItemQty(String itemQty) {
		this.itemQty = itemQty;
	}

	/**
	 * @return the amount
	 */
	public String getAmount() {
		return amount;
	}

	/**
	 * @param amount
	 *            the amount to set
	 */
	public void setAmount(String amount) {
		this.amount = amount;
	}

}
