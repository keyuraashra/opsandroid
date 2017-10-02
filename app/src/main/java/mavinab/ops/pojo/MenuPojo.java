package mavinab.ops.pojo;

import java.io.Serializable;

public class MenuPojo implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private String categoryId;
	private String menuId;
	private String menuItemName;
	private double menuPrice;
	private String menuItemPhoto;
	private String menuItemDesc;
	private String eta;
	/**
	 * @return the categoryId
	 */
	public String getCategoryId() {
		return categoryId;
	}
	/**
	 * @param categoryId the categoryId to set
	 */
	public void setCategoryId(final String categoryId) {
		this.categoryId = categoryId;
	}
	/**
	 * @return the menuId
	 */
	public String getMenuId() {
		return menuId;
	}
	/**
	 * @param menuId the menuId to set
	 */
	public void setMenuId(final String menuId) {
		this.menuId = menuId;
	}
	/**
	 * @return the menuItemName
	 */
	public String getMenuItemName() {
		return menuItemName;
	}
	/**
	 * @param menuItemName the menuItemName to set
	 */
	public void setMenuItemName(final String menuItemName) {
		this.menuItemName = menuItemName;
	}
	/**
	 * @return the menuPrice
	 */
	public double getMenuPrice() {
		return menuPrice;
	}
	/**
	 * @param menuPrice the menuPrice to set
	 */
	public void setMenuPrice(final double menuPrice) {
		this.menuPrice = menuPrice;
	}
	/**
	 * @return the menuItemPhoto
	 */
	public String getMenuItemPhoto() {
		return menuItemPhoto;
	}
	/**
	 * @param menuItemPhoto the menuItemPhoto to set
	 */
	public void setMenuItemPhoto(final String menuItemPhoto) {
		this.menuItemPhoto = menuItemPhoto;
	}
	/**
	 * @return the menuItemDesc
	 */
	public String getMenuItemDesc() {
		return menuItemDesc;
	}
	/**
	 * @param menuItemDesc the menuItemDesc to set
	 */
	public void setMenuItemDesc(final String menuItemDesc) {
		this.menuItemDesc = menuItemDesc;
	}
	/**
	 * @return the eta
	 */
	public String getEta() {
		return eta;
	}
	/**
	 * @param eta the eta to set
	 */
	public void setEta(final String eta) {
		this.eta = eta;
	}
	
	

}
