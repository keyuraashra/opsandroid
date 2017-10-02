/**
 * 
 */
package mavinab.ops.constants;

/**
 * Used to define Global Variables for URL and Constants
 * 
 * @author Keyur Ashra
 * 
 */
public class OPSGlobal {
	
	public static final String SUCCESS_CODE = "success";
	public static final String SUCCESS_VALUE = "1";
	public static final String EXIST_VALUE = "2";
	
	public static final int CONNECTION_TIME_OUT = 15000;
	
	public static final String INSERT_RESTAURANT = "http://riontech.com/ops/webservice/insert_restaurant.php?restaurant_name=";
	public static final String LOGIN = "http://riontech.com/ops/webservice/insert_customer.php?customer_name=";
	public static final String GET_CATEGORIES = "http://riontech.com/ops/webservice/get_all_categories.php?restaurant_id=";
	public static final String GET_MENUS = "http://riontech.com/ops/webservice/get_all_menus.php?category_id=";
	public static final String PLACE_ORDER = "";
	
	//JSON
	public static final String CATEGORY_ID = "category_id";
	public static final String CATEGORY_NAME = "category_name";
	
	public static final String MENU_ID = "menu_id";
	
}
