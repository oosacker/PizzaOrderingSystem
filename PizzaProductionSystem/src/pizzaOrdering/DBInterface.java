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
	String url = "jdbc:mysql://10.140.230.135:3306/pizza";
	String dbUser = "newuser";
	String usrPass = "12345";
	
	/* For local host */
//	String url = "jdbc:mysql://localhost:3306/pizza";
//	String dbUser = "newuser";
//	String usrPass = "12345";
	
	// to return to the GUI
	ArrayList<Order> orderList = new ArrayList<>();
	ArrayList<Topping> toppingList = new ArrayList<>();
	ArrayList<Sauce> sauceList = new ArrayList<>();
	ArrayList<Cheese> cheeseList = new ArrayList<>();
	

	
	// lookup tables for the ingredients
	//HashMap<String, Order> orderMap = new HashMap<>();
	HashMap<String, Topping> toppingMap = new HashMap<>();;
	HashMap<String, Sauce> sauceMap = new HashMap<>();;
	HashMap<String, Cheese> cheeseMap = new HashMap<>();;

	
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
			
			//stmt = con.createStatement();
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
			//stmt.close();
			
			
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
			
			///if ( orderExists(order) ) {	
				
				order.setPizza_status(newStatus);
				
				int order_id = order.getOrder_id();
				
				String query = "UPDATE `pizza`.`orders` SET `status` = ? WHERE (`id` = ?);";
				
				PreparedStatement prep = con.prepareStatement(query);
				prep.setString(1, newStatus);
				prep.setInt(2, order_id);
				prep.execute();
				//stmt.close();
				
				closeDB();
				return true;
			//}
			//else {
			//	closeDB();
			//	return false;
			//}
		}
		catch(Exception ex) {
			ex.printStackTrace();
			closeDB();
			return false;
		}
	}
	
	
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
	
	public boolean consumeIngredient(Ingredient ingredient) {
		
		try {
		
			openDB(url, dbUser, usrPass);
			
			String ingredient_name = ingredient.getName();
			
			System.out.println("select ingredient "+ingredient.toString());
			
			String sql = "";
			
			if(ingredient instanceof Topping) {
				sql = "UPDATE `pizza`.`toppings` SET `stock_level` = `stock_level` - 1 where `name` = \""+ingredient_name+"\";";
			}
			
			else if(ingredient instanceof Sauce) {
				sql = "UPDATE `pizza`.`sauces` SET `stock_level` = `stock_level` - 1 where `name` = \""+ingredient_name+"\";";
			}
			
			else if(ingredient instanceof Cheese) {
				sql = "UPDATE `pizza`.`cheeses` SET `stock_level` = `stock_level` - 1 where `name` = \""+ingredient_name+"\";";
			}
			System.out.println(sql);
			Statement stmt = con.createStatement();
			
			
			int rs = stmt.executeUpdate(sql);
			
			closeDB();
			return true;
			
		}
		
		catch(Exception ex) {
			ex.printStackTrace();
			closeDB();
			return false;
		}
		
		
	}
	
	
	public boolean updateAllToppings(int new_stock_level) {
		
		try {
			
			openDB(url, dbUser, usrPass);
			
			Statement stmt = con.createStatement();
			
			String query = 	"select count(*) as topping_count from toppings;";
			ResultSet rs = stmt.executeQuery(query);
			
			
			if(rs.next()) {
				
				int rowNum = rs.getInt("topping_count");
				//System.out.println(rowNum);
				
				query = "";
				
				for(int i=0; i<rowNum; i++) {
					query = "UPDATE `pizza`.`toppings` SET `stock_level` = ? WHERE (`id` = ?);";
					//System.out.println(query);
	
					PreparedStatement prep = con.prepareStatement(query);
					
					prep.setInt(1, new_stock_level);
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
	
	
	
	public boolean updateAllSauces(int new_stock_level) {
		
		try {
			openDB(url, dbUser, usrPass);
			
			Statement stmt = con.createStatement();
			
			String query = 	"select count(*) as sauce_count from sauces;";
			ResultSet rs = stmt.executeQuery(query);
			
			
			if(rs.next()) {
				
				int rowNum = rs.getInt("sauce_count");
				//System.out.println(rowNum);
				
				query = "";
				
				for(int i=0; i<rowNum; i++) {
					query = "UPDATE `pizza`.`sauces` SET `stock_level` = ? WHERE (`id` = ?);";
					//System.out.println(query);
	
					PreparedStatement prep = con.prepareStatement(query);
					
					prep.setInt(1, new_stock_level);
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
	
	
	
	public boolean updateAllCheeses(int new_stock_level) {
		
		try {
			
			openDB(url, dbUser, usrPass);
			
			Statement stmt = con.createStatement();
			
			String query = 	"select count(*) as cheese_count from cheeses;";
			ResultSet rs = stmt.executeQuery(query);
			
			
			if(rs.next()) {
				
				int rowNum = rs.getInt("cheese_count");
				//System.out.println(rowNum);
				
				query = "";
				
				for(int i=0; i<rowNum; i++) {
					query = "UPDATE `pizza`.`cheeses` SET `stock_level` = ? WHERE (`id` = ?);";
					//System.out.println(query);
	
					PreparedStatement prep = con.prepareStatement(query);
					
					prep.setInt(1, new_stock_level);
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
	 * Check if an order exists in the database
	 * @param order
	 * @return
	 */
//	public boolean orderExists(Order order) {
//		try {
//			
//			openDB(url, dbUser, usrPass);
//			
//			boolean orderFound = false;
//			
//			//stmt = con.createStatement();
//			
//			String sql = "Select * from orders where orders.id=?";
//			
//			PreparedStatement prep = con.prepareStatement(sql);
//			
//			prep.setInt(1, order.getOrder_id());
//			
//			ResultSet rs = prep.executeQuery();
//			
//			if(rs.next()){
//				orderFound = true;
//				System.out.println("Order found");
//			}
//			
//			else if(!orderFound) {
//				System.out.println("Order not found");
//			}
//			
//			rs.close();
//			//stmt.close();
//			
//			closeDB();
//			return orderFound;
//		}
//		catch(Exception ex) {
//			ex.printStackTrace();
//			closeDB();
//			return false;
//		}
//	}
	
	
	
	/**
	 * Fetch the current orders from database -- CALL AFTER constructor!!!!
	 * @return
	 */
	//public ArrayList<Order> getOrders() {
	public ArrayList<Order> getAllOrders() {
		
		try {
			//stmt = con.createStatement();
			
			//System.out.println("called ");
			
			orderList.clear();
			
			openDB(url, dbUser, usrPass);
			
			// ONLY DOWNLOAD IF STATUS IS 0
			String sql = "Select * from orders where orders.status=? or orders.status=? or orders.status=?";
			PreparedStatement prep = con.prepareStatement(sql);
			prep.setString(1, "Waiting");
			prep.setString(2, "Cooking");
			prep.setString(3, "Ready");
			
			ResultSet rs = prep.executeQuery();

			//ArrayList<Order> orders = new ArrayList<>();
			//HashMap<Integer, Order> orders = new HashMap<>();
			
			while(rs.next()){
				
				int order_id = rs.getInt("orders.id");
				String customer_phone = rs.getString("orders.phone");
				String pizza_status = rs.getString("orders.status");
				String pizza_size = rs.getString("orders.size");
				
				String pizza_topping_0 = rs.getString("orders.topping_0");
				String pizza_topping_1 = rs.getString("orders.topping_1");
				String pizza_topping_2 = rs.getString("orders.topping_0");
				
				String pizza_sauce_0 = rs.getString("orders.sauce_0");
				//int pizza_sauce_1_id = rs.getInt("orders.sauce_1_id");
				
				String pizza_cheese_0 = rs.getString("orders.cheese_0");
				//int pizza_cheese_1_id = rs.getInt("orders.cheese_1_id");
				
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
				
				//orders.add(myOrder);
				//orders.put(order_id, myOrder);
				orderList.add(myOrder);
				//System.out.println("test "+myOrder.toString());
				}
			
			rs.close();
			//stmt.close();\
			
			
			//System.out.println("getallorders "+orderList);
			
			
			return orderList;
			
		}
		catch(Exception ex) {
			System.out.println("Error reading from database\n");
			ex.printStackTrace();
			return null;
		}
	}
	
	/**
	 * Fetch all toppings from database
	 * @return
	 */
	//public ArrayList<Order> getOrders() {
	public ArrayList<Topping> getAllToppings() {
		
		try {
			
			openDB(url, dbUser, usrPass);
			
			toppingList.clear();
			
			Statement stmt = con.createStatement();
			String sql = "Select * from toppings";
			ResultSet rs = stmt.executeQuery(sql);

			//ArrayList<Customer> customers = new ArrayList<>();
			//HashMap<Integer, Topping> toppings = new HashMap<>();
			
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
				
				//customers.add(myCustomer);
				//toppings.put(topping_id, myTopping);
				toppingList.add(myTopping);
				
			}
			
			rs.close();
			stmt.close();
			
			closeDB();
			
			return toppingList;
			
		}
		catch(Exception ex) {
			System.out.println("Error reading from database");
			ex.printStackTrace();
			closeDB();
			return null;
		}

	}

	
	public HashMap<String, Topping> getAllToppingsMap() {
		
		try {
			
			toppingMap = new HashMap<>();
			
			openDB(url, dbUser, usrPass);
			
			//toppingList.clear();
			
			Statement stmt = con.createStatement();
			String sql = "Select * from toppings";
			ResultSet rs = stmt.executeQuery(sql);

			//ArrayList<Customer> customers = new ArrayList<>();
			//HashMap<Integer, Topping> toppings = new HashMap<>();
			
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
				
				//customers.add(myCustomer);
				toppingMap.put(topping_name, myTopping);
				//toppingList.add(myTopping);
				
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
	 * Fetch all cheese from database
	 * @return
	 */
	//public ArrayList<Order> getOrders() {
	public ArrayList<Cheese> getAllCheeses() {
		
		try {
			
			openDB(url, dbUser, usrPass);
			
			cheeseList.clear();
			
			Statement stmt = con.createStatement();
			String sql = "Select * from cheeses";
			ResultSet rs = stmt.executeQuery(sql);

			//ArrayList<Customer> customers = new ArrayList<>();
			//HashMap<Integer, Cheese> cheeses = new HashMap<>();
			
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
				
				//customers.add(myCustomer);
				//cheeses.put(cheese_id, myCheese);
				cheeseList.add(myCheese);
				
			}
			
			rs.close();
			stmt.close();
			
			closeDB();
			
			return cheeseList;
			
		}
		catch(Exception ex) {
			System.out.println("Error reading from database");
			ex.printStackTrace();
			
			closeDB();
			return null;
		}

	}

	
	
	public HashMap<String, Cheese> getAllCheesesMap() {
		
		try {
			
			cheeseMap = new HashMap<>();
			
			openDB(url, dbUser, usrPass);
			
			//cheeseList.clear();
			
			Statement stmt = con.createStatement();
			String sql = "Select * from cheeses";
			ResultSet rs = stmt.executeQuery(sql);

			//ArrayList<Customer> customers = new ArrayList<>();
			//HashMap<Integer, Cheese> cheeses = new HashMap<>();
			
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
				
				//customers.add(myCustomer);
				cheeseMap.put(cheese_name, myCheese);
				//cheeseMap.add(cheese_name, myCheese);
				
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
	 * Fetch all sauces from database
	 * @return
	 */
	public ArrayList<Sauce> getAllSauces() {
		
		try {
			openDB(url, dbUser, usrPass);
			
			sauceList.clear();
			
			Statement stmt = con.createStatement();
			String sql = "Select * from sauces";
			ResultSet rs = stmt.executeQuery(sql);

			//HashMap<Integer, Sauce> sauces = new HashMap<>();
			
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
				
				//sauces.put(sauce_id, mySauce);
				sauceList.add(mySauce);
				
			}
			
			rs.close();
			stmt.close();
			closeDB();
			return sauceList;
			
		}
		catch(Exception ex) {
			System.out.println("Error reading from database");
			ex.printStackTrace();
			closeDB();
			return null;
		}

	}
	
	
	
	public HashMap<String, Sauce> getAllSaucesMap() {
		
		try {
			sauceMap = new HashMap<>();
			
			openDB(url, dbUser, usrPass);
			
			//sauceList.clear();
			
			Statement stmt = con.createStatement();
			String sql = "Select * from sauces";
			ResultSet rs = stmt.executeQuery(sql);

			//HashMap<Integer, Sauce> sauces = new HashMap<>();
			
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
				//sauceList.add(mySauce);
				
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
		// TODO Auto-generated constructor stub
		
		//openDB(url, dbUser, usrPass);

		//consumeIngredient(new Sauce(0, "Extra-Virgin Olive Oil", 0));
		
//		updateAllToppings(0);
//		updateAllCheeses(0);
//		updateAllSauces(0);
//		
//		printToppings(getAllToppings());
//		printCheeses(getAllCheeses());
//		printSauces(getAllSauces());
//		printOrders(getAllOrders());
		
		//toppingMap = getAllToppings();
		//sauceMap = getAllSauces();
		//cheeseMap = getAllCheeses();
		//customerMap = getAllCustomers();
		//orderMap = getAllOrders();	// this MUST be called after all 4 !!!!!
		
		
		
//		printAllCustomers(getAllCustomers());
//		
//		printAllToppings(getAllToppings());
//		
//		printAllCheeses(getAllCheeses());
//		
//		printAllSauces(getAllSauces());
//		
//		printAllOrders(getAllOrders());
//		
//		
//		updateAllToppings(100);
//		
//		
//		updateAllCheeses(100);
//		
//		
//		updateAllSauces(100);
		
		//closeDB();
		
		
	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		new DBInterface();
		
	}

}
