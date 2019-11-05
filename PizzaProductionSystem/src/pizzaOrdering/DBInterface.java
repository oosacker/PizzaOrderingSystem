package pizzaOrdering;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;


public class DBInterface {

	private static Connection con;
	//private static Statement stmt;

	/* For remote host */
	//	String url = "jdbc:mysql://10.140.230.135:3306/pizza";
	//	String dbUser = "newuser";
	//	String usrPass = "12345";
	//	
	/* For local host */
	String url = "jdbc:mysql://localhost:3306/pizza";
	String dbUser = "newuser";
	String usrPass = "12345";

	// to return to the GUI
//	ArrayList<Order> orderList = new ArrayList<>();
//	ArrayList<Topping> toppingList = new ArrayList<>();
//	ArrayList<Sauce> sauceList = new ArrayList<>();
//	ArrayList<Cheese> cheeseList = new ArrayList<>();



	// lookup tables for the ingredients
	HashMap<Integer, Order> orderMap = new HashMap<>();
	HashMap<String, Topping> toppingMap = new HashMap<>();
	HashMap<String, Sauce> sauceMap = new HashMap<>();
	HashMap<String, Cheese> cheeseMap = new HashMap<>();


	/**
	 * 
	 * Open the DB connection
	 */
	private boolean openDB(String url, String dbUser, String usrPass) {
		try {
			Class.forName("com.mysql.jdbc.Driver");
			con = DriverManager.getConnection(url, dbUser, usrPass);
			System.out.println("Connected to database");
			return true;

		}
		catch(Exception ex) {
			System.out.println("Error connecting to database");
			ex.printStackTrace();
			return false;
		}
	}



	/**
	 * Close the DB connection
	 */
	private boolean closeDB() {
		try {
			con.close();
			System.out.println("Closed connection to database");
			return true;
		}
		catch(Exception ex) {
			System.out.println("Error disconnecting from database");
			ex.printStackTrace();
			return false;
		}
	}


	/**
	 * Check if username and password exist on database
	 * @param userName_input
	 * @param userPass_input
	 * @return
	 */
	public boolean verifyStaffLogin(String userName_input, String userPass_input) {

		try {

			openDB(url, dbUser, usrPass);
			String sql = "Select * from staff_accounts where staff_accounts.id = ? and staff_accounts.password = ?";
			PreparedStatement prep = con.prepareStatement(sql);

			boolean user_verified = false;

			prep.setString(1, userName_input);
			prep.setString(2, userPass_input);

			ResultSet rs = prep.executeQuery();

			if(rs.next()){
				user_verified = true;
			}

			else {
				user_verified = false;
			}

			rs.close();
			closeDB();
			return user_verified;
		}

		catch(Exception ex) {
			System.out.println("Error reading from database");
			ex.printStackTrace();
			closeDB();
			return false;
		}

	}



	/**
	 * Change the state of an order on the database
	 * @param state
	 * @return
	 */
	public boolean updateOrderState(Order order, String newStatus) {
		try {

			openDB(url, dbUser, usrPass);

			order.setPizza_status(newStatus);
			int order_id = order.getOrder_id();
			String query = "UPDATE `pizza`.`orders` SET `status` = ? WHERE (`id` = ?);";

			PreparedStatement prep = con.prepareStatement(query);
			prep.setString(1, newStatus);
			prep.setInt(2, order_id);
			prep.execute();

			closeDB();
			return true;
		
		}
		catch(Exception ex) {
			ex.printStackTrace();
			closeDB();
			return false;
		}
	}


	/**
	 * Change the stock level of Ingredient to 100 on database
	 * @param ingredient
	 * @return
	 */
	public boolean updateIngredient(Ingredient ingredient) {

		try {

			openDB(url, dbUser, usrPass);

			String sql = "";

			if(ingredient instanceof Topping) {
				sql = "UPDATE `pizza`.`toppings` SET `stock_level` = ? WHERE `id` = ?;";
			}

			else if(ingredient instanceof Sauce) {
				sql = "UPDATE `pizza`.`sauces` SET `stock_level` = ? WHERE `id` = ?;";
			}

			else if(ingredient instanceof Cheese) {
				sql = "UPDATE `pizza`.`cheeses` SET `stock_level` = ? WHERE `id` = ?;";
			}

			PreparedStatement prep = con.prepareStatement(sql);
			prep.setInt(1, 100);
			prep.setInt(2, ingredient.getId());
			int res = prep.executeUpdate();

			closeDB();
			return true;

		}

		catch(Exception ex) {
			ex.printStackTrace();
			closeDB();
			return false;
		}


	}

	/**
	 * Reduces the stock_level of specified ingredient by 3, 2, 1 (depending on pizza size) on the database
	 * @param ingredient
	 * @return
	 */
	public boolean consumeIngredient(Ingredient ingredient, String pizza_size) {

		try {

			if(ingredient == null) {
				System.out.println("null ingredient!!!!!");
			}

			openDB(url, dbUser, usrPass);

			String ingredient_name = ingredient.getName();
			
			int consume_amt = 0;
			
			if (pizza_size.equalsIgnoreCase("Large"))
				consume_amt = 3;
			else if (pizza_size.equalsIgnoreCase("Medium"))
				consume_amt = 2;
			else if(pizza_size.equalsIgnoreCase("Small"))
				consume_amt = 1;
			else
				System.out.println("Error in consumeIngredient");
			

			System.out.println("select ingredient "+ingredient.toString());

			String sql = "";

			if(ingredient instanceof Topping) {
				sql = "UPDATE `pizza`.`toppings` SET `stock_level` = `stock_level` - " +consume_amt+ " where `name` = \""+ingredient_name+"\";";
			}

			else if(ingredient instanceof Sauce) {
				sql = "UPDATE `pizza`.`sauces` SET `stock_level` = `stock_level` - " +consume_amt+ " where `name` = \""+ingredient_name+"\";";
			}

			else if(ingredient instanceof Cheese) {
				sql = "UPDATE `pizza`.`cheeses` SET `stock_level` = `stock_level` - " +consume_amt+ " where `name` = \""+ingredient_name+"\";";
			}
			System.out.println(sql);
			Statement stmt = con.createStatement();

			int rs = stmt.executeUpdate(sql);

			System.out.println("consumed "+ingredient_name);

			closeDB();
			return true;

		}

		catch(Exception ex) {
			ex.printStackTrace();
			closeDB();
			return false;
		}

	}


	/**
	 * Set stock_level of all toppings to 100 on database
	 * @return
	 */
	public boolean updateAllToppings() {

		try {

			openDB(url, dbUser, usrPass);

			Statement stmt = con.createStatement();

			String query = 	"select count(*) as topping_count from toppings;";
			ResultSet rs = stmt.executeQuery(query);

			if(rs.next()) {

				int rowNum = rs.getInt("topping_count");
				query = "";

				for(int i=0; i<rowNum; i++) {
					query = "UPDATE `pizza`.`toppings` SET `stock_level` = ? WHERE (`id` = ?);";
					PreparedStatement prep = con.prepareStatement(query);

					prep.setInt(1, 100);
					prep.setInt(2, i);

					int res = prep.executeUpdate();

					prep.close();
				}

			}

			stmt.close();
			closeDB();
			return true;
		}

		catch(Exception ex) {
			ex.printStackTrace();
			closeDB();
			return false;
		}
	}
	
	
	/**
	 * Set stock_level of all sauces to 100 on database
	 * @return
	 */
	public boolean updateAllSauces() {
		
		try {
			openDB(url, dbUser, usrPass);
			
			Statement stmt = con.createStatement();
			
			String query = 	"select count(*) as sauce_count from sauces;";
			ResultSet rs = stmt.executeQuery(query);
			
			if(rs.next()) {
				
				int rowNum = rs.getInt("sauce_count");
				query = "";
				
				for(int i=0; i<rowNum; i++) {
					
					query = "UPDATE `pizza`.`sauces` SET `stock_level` = ? WHERE (`id` = ?);";
					PreparedStatement prep = con.prepareStatement(query);
					prep.setInt(1, 100);
					prep.setInt(2, i);
					
					int res = prep.executeUpdate();
					
					prep.close();
				}

			}
			
			stmt.close();
			closeDB();
			return true;
		}
		
		catch(Exception ex) {
			ex.printStackTrace();
			closeDB();
			return false;
		}
	}

	
	/**
	 * Set stock_level of all cheeses to 100 on database
	 * @return
	 */
	public boolean updateAllCheeses() {
		
		try {
			
			openDB(url, dbUser, usrPass);
			
			Statement stmt = con.createStatement();
			
			String query = 	"select count(*) as cheese_count from cheeses;";
			ResultSet rs = stmt.executeQuery(query);
			
			if(rs.next()) {
				
				int rowNum = rs.getInt("cheese_count");
				query = "";
				
				for(int i=0; i<rowNum; i++) {
					
					query = "UPDATE `pizza`.`cheeses` SET `stock_level` = ? WHERE (`id` = ?);";
					PreparedStatement prep = con.prepareStatement(query);
					prep.setInt(1, 100);
					prep.setInt(2, i);
					
					int res = prep.executeUpdate();
					
					prep.close();
				}

			}
			
			stmt.close();
			closeDB();
			return true;
		}
		
		catch(Exception ex) {
			ex.printStackTrace();
			closeDB();
			return false;
		}
	}
	

	/**
	 * Returns the hash map of current orders that have status "Waiting" "Cooking" "Ready" "Picked up" 
	 * does not include "Removed" as you don't want to display those orders
	 * @return
	 */
	public HashMap<Integer, Order> getAllOrdersMap() {

		try {

			orderMap = new HashMap<>();
			openDB(url, dbUser, usrPass);

			// these are the ONLY order states that are displayed (there's one more - "Removed") 
			String sql = "Select * from orders where orders.status=? or orders.status=? or orders.status=? or orders.status=?";
			PreparedStatement prep = con.prepareStatement(sql);
			prep.setString(1, "Waiting");
			prep.setString(2, "Cooking");
			prep.setString(3, "Ready");
			prep.setString(4, "Picked up");
			ResultSet rs = prep.executeQuery();

			while(rs.next()){

				int order_id = rs.getInt("orders.id");
				String customer_phone = rs.getString("orders.phone");
				String pizza_status = rs.getString("orders.status");
				String pizza_size = rs.getString("orders.size");
				String pizza_topping_0 = rs.getString("orders.topping_0");
				String pizza_topping_1 = rs.getString("orders.topping_1");
				String pizza_topping_2 = rs.getString("orders.topping_0");
				String pizza_sauce_0 = rs.getString("orders.sauce_0");
				String pizza_cheese_0 = rs.getString("orders.cheese_0");
				String order_time = rs.getString("orders.date_time");
				double pizza_price = rs.getDouble("orders.price");

				Order myOrder = new Order
							(
								order_id, 
								pizza_size, 
								customer_phone, 
								order_time, 
								pizza_topping_0, 
								pizza_topping_1, 
								pizza_topping_2, 
								pizza_sauce_0, 
								pizza_cheese_0, 
								pizza_status, 
								pizza_price
							);
				
				orderMap.put(order_id, myOrder);
			}

			rs.close();
			return orderMap;

		}
		catch(Exception ex) {
			System.out.println("Error reading from database\n");
			ex.printStackTrace();
			return null;
		}
	}


	/**
	 * Returns the hash map of all toppings on database, referenced by its name
	 * @return
	 */
	public HashMap<String, Topping> getAllToppingsMap() {

		try {

			toppingMap = new HashMap<>();
			openDB(url, dbUser, usrPass);
			Statement stmt = con.createStatement();
			String sql = "Select * from toppings";
			ResultSet rs = stmt.executeQuery(sql);

			while(rs.next()){

				int topping_id = rs.getInt("toppings.id");
				String topping_name = rs.getString("toppings.name");
				int topping_stock_level = rs.getInt("toppings.stock_level");

				Topping myTopping = new Topping
							(
								topping_id, 
								topping_name, 
								topping_stock_level
							);
				toppingMap.put(topping_name, myTopping);

			}

			rs.close();
			stmt.close();

			closeDB();

			return toppingMap;

		}
		catch(Exception ex) {
			System.out.println("Error reading from database");
			ex.printStackTrace();
			closeDB();
			return null;
		}

	}


	/**
	 * Returns hash map of all cheeses on database, referenced by its name
	 * @return
	 */
	public HashMap<String, Cheese> getAllCheesesMap() {

		try {

			cheeseMap = new HashMap<>();
			openDB(url, dbUser, usrPass);
			Statement stmt = con.createStatement();
			String sql = "Select * from cheeses";
			ResultSet rs = stmt.executeQuery(sql);

			while(rs.next()){

				int cheese_id = rs.getInt("cheeses.id");
				String cheese_name = rs.getString("cheeses.name");
				int cheese_stock_level = rs.getInt("cheeses.stock_level");

				Cheese myCheese = new Cheese
							(
								cheese_id, 
								cheese_name, 
								cheese_stock_level
							);

				cheeseMap.put(cheese_name, myCheese);
			}

			rs.close();
			stmt.close();

			closeDB();

			return cheeseMap;

		}
		catch(Exception ex) {
			System.out.println("Error reading from database");
			ex.printStackTrace();

			closeDB();
			return null;
		}

	}


	/**
	 * Returns hash map of all sauces on database, referenced by its name
	 * @return
	 */
	public HashMap<String, Sauce> getAllSaucesMap() {

		try {
			sauceMap = new HashMap<>();
			openDB(url, dbUser, usrPass);
			Statement stmt = con.createStatement();
			String sql = "Select * from sauces";
			ResultSet rs = stmt.executeQuery(sql);

			while(rs.next()){

				int sauce_id = rs.getInt("sauces.id");
				String sauce_name = rs.getString("sauces.name");
				int sauce_stock_level = rs.getInt("sauces.stock_level");

				Sauce mySauce = new Sauce
						(
								sauce_id, 
								sauce_name, 
								sauce_stock_level
						);

				sauceMap.put(sauce_name, mySauce);

			}

			rs.close();
			stmt.close();
			closeDB();
			return sauceMap;

		}
		catch(Exception ex) {
			System.out.println("Error reading from database");
			ex.printStackTrace();
			closeDB();
			return null;
		}

	}



	/**
	 * For testing 
	 * @param mylist
	 */
	private void printToppings(ArrayList<Topping> mylist) {
		for(Topping t : mylist) {
			System.out.println(t.toString());
		}
	}

	private void printCheeses(ArrayList<Cheese> mylist) {
		for(Cheese c : mylist) {
			System.out.println(c.toString());
		}
	}

	private void printSauces(ArrayList<Sauce> mylist) {
		for(Sauce s : mylist) {
			System.out.println(s.toString());
		}
	}

	private void printOrders(ArrayList<Order> mylist) {
		for(Order o : mylist) {
			System.out.println(o.toString());
		}
	}


	public DBInterface() {
		// add code here for unit testing~~
	}

	public static void main(String[] args) {
		new DBInterface();

	}

}
