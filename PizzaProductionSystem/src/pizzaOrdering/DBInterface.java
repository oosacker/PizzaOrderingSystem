package pizzaOrdering;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
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
	ObservableList<Order> orderList;
	ObservableList<Topping> toppingList;
	ObservableList<Sauce> sauceList;
	ObservableList<Cheese> cheeseList;
	
	// lookup tables for the ingredients
	HashMap<Integer, Order> orderMap;
	HashMap<Integer, Topping> toppingMap;
	HashMap<Integer, Sauce> sauceMap;
	HashMap<Integer, Cheese> cheeseMap;
	HashMap<Integer, Customer> customerMap;
	
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
	 * Find cheese on database based on id; return null if not found
	 * @return
	 */
	public Cheese findCheese(int cheese_id_input) {
		
		try {
			
			openDB(url, dbUser, usrPass);
			
			Cheese myCheese = null;
			
			//stmt = con.createStatement();
			String sql = "Select * from cheeses where cheeses.id = ?";
			PreparedStatement prep = con.prepareStatement(sql);
			
			prep.setInt(1, cheese_id_input);
			
			ResultSet rs = prep.executeQuery();
			
			if(rs.next()){
				
				int cheese_id = rs.getInt("cheeses.id");
				String cheese_name = rs.getString("cheeses.name");
				int cheese_stock_level = rs.getInt("cheeses.stock_level");
				
				myCheese = new Cheese(cheese_id, cheese_name, cheese_stock_level);
				
			}

			rs.close();
			//stmt.close();
			closeDB();
			return myCheese;
		}
		
		catch(Exception ex) {
			System.out.println("Error reading from database");
			ex.printStackTrace();
			closeDB();
			return null;
		}
	}
	

	/**
	 * Find sauce on database based on id; return null if not found
	 * @return
	 */
	public Sauce findSauce(int sauce_id_input) {
		
		try {
			
			openDB(url, dbUser, usrPass);
			
			Sauce mySauce = null;
			
			//stmt = con.createStatement();
			String sql = "Select * from sauces where sauces.id = ?";
			PreparedStatement prep = con.prepareStatement(sql);
			
			prep.setInt(1, sauce_id_input);
			
			ResultSet rs = prep.executeQuery();
			
			if(rs.next()){
				
				int sauce_id = rs.getInt("toppings.id");
				String sauce_name = rs.getString("toppings.name");
				int sauce_stock_level = rs.getInt("toppings.stock_level");
				
				mySauce = new Sauce(sauce_id, sauce_name, sauce_stock_level);
				
			}

			rs.close();
			//stmt.close();
			closeDB();
			return mySauce;
		}
		
		catch(Exception ex) {
			System.out.println("Error reading from database");
			ex.printStackTrace();
			closeDB();
			return null;
		}
	}
	
	
	/**
	 * Find topping on database based on id; return null if not found
	 * @return
	 */
	public Topping findTopping(int topping_id_input) {
		
		try {
			openDB(url, dbUser, usrPass);
			
			Topping myTopping = null;
			
			//stmt = con.createStatement();
			String sql = "Select * from toppings where toppings.id = ?";
			PreparedStatement prep = con.prepareStatement(sql);
			
			prep.setInt(1, topping_id_input);
			
			ResultSet rs = prep.executeQuery();
			
			if(rs.next()){
				
				int topping_id = rs.getInt("toppings.id");
				String topping_name = rs.getString("toppings.name");
				int topping_stock_level = rs.getInt("toppings.stock_level");
				
				myTopping = new Topping(topping_id, topping_name, topping_stock_level);
				
			}

			rs.close();
			//stmt.close();
			closeDB();
			return myTopping;
		}
		
		catch(Exception ex) {
			System.out.println("Error reading from database");
			ex.printStackTrace();
			closeDB();
			return null;
		}
	}
	
	/**
	 * Find customer on database based on id; return null if not found
	 * @return
	 */
	public Customer findCustomer(int customer_id_input) {
		
		try {
			
			openDB(url, dbUser, usrPass);

			
			Customer myCustomer = null;
			
			//stmt = con.createStatement();
			String sql = "Select * from customers where customers.id = ?";
			PreparedStatement prep = con.prepareStatement(sql);
			
			prep.setInt(1, customer_id_input);
			
			ResultSet rs = prep.executeQuery();
			
			if(rs.next()){
				
				int customer_id = rs.getInt("customers.id");
				String customer_name = rs.getString("customers.name");
				String customer_address = rs.getString("customers.address");
				int customer_phone = rs.getInt("customers.cellphone");
				String customer_email = rs.getString("customers.email");
				int customer_discount_code = rs.getInt("customers.discount_code");
				
				myCustomer = new Customer(customer_id, customer_name, customer_address, customer_phone, customer_email, customer_discount_code);
				
			}

			rs.close();
			//stmt.close();
			closeDB();
			return myCustomer;
			
			
		}
		catch(Exception ex) {
			System.out.println("Error reading from database");
			ex.printStackTrace();
			closeDB();
			return null;
		}
	}
	
	
	/**
	 * Find order on database based on id; return null if not found
	 * @return
	 */
//	public Order findOrder(int order_id_input) {
//		
//		try {
//			
//			openDB(url, dbUser, usrPass);
//			
//			Order myOrder = null;
//			
//			//stmt = con.createStatement();
//			String sql = "Select * from orders where orders.id = ?";
//			PreparedStatement prep = con.prepareStatement(sql);
//			
//			prep.setInt(1, order_id_input);
//			
//			ResultSet rs = prep.executeQuery();
//			
//			if(rs.next()){
//				
//				int order_id = rs.getInt("orders.id");
//				int customer_id = rs.getInt("orders.customer_id");
//				String pizza_status = rs.getString("orders.status");
//				String pizza_size = rs.getString("orders.size");
//				
//				int pizza_topping_0_id = rs.getInt("orders.topping_0_id");
//				int pizza_topping_1_id = rs.getInt("orders.topping_1_id");
//				int pizza_topping_2_id = rs.getInt("orders.topping_0_id");
//				
//				int pizza_sauce_0_id = rs.getInt("orders.sauce_0_id");
//				//int pizza_sauce_1_id = rs.getInt("orders.sauce_1_id");
//				
//				int pizza_cheese_0_id = rs.getInt("orders.cheese_0_id");
//				//int pizza_cheese_1_id = rs.getInt("orders.cheese_1_id");
//				
//				String order_time = rs.getString("orders.date_time");
//				
//				double pizza_price = rs.getDouble("orders.price");
//				
//				myOrder = new Order(
//							order_id, 
//							pizza_size, 
//							customer_id, 
//							order_time, 
//							pizza_topping_0_id, 
//							pizza_topping_1_id, 
//							pizza_topping_2_id, 
//							pizza_sauce_0_id, 
//							pizza_cheese_0_id, 
//							pizza_status, 
//							pizza_price
//						);
//				
//			}
//
//			rs.close();
//			//stmt.close();
//			return myOrder;
//			
//			
//		}
//		catch(Exception ex) {
//			System.out.println("Error reading from database");
//			ex.printStackTrace();
//			return null;
//		}
//	}

	
	/**
	 * Change the state of an order on the database
	 * @param state
	 * @return
	 */
	public boolean updateOrderState(Order order, String newStatus) {
		try {
			
			openDB(url, dbUser, usrPass);
			
			if ( orderExists(order) ) {	
				
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
			}
			else {
				closeDB();
				return false;
			}
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
	public boolean orderExists(Order order) {
		try {
			
			openDB(url, dbUser, usrPass);
			
			boolean orderFound = false;
			
			//stmt = con.createStatement();
			
			String sql = "Select * from orders where orders.id=?";
			
			PreparedStatement prep = con.prepareStatement(sql);
			
			prep.setInt(1, order.getOrder_id());
			
			ResultSet rs = prep.executeQuery();
			
			if(rs.next()){
				orderFound = true;
				System.out.println("Order found");
			}
			
			else if(!orderFound) {
				System.out.println("Order not found");
			}
			
			rs.close();
			//stmt.close();
			
			closeDB();
			return orderFound;
		}
		catch(Exception ex) {
			ex.printStackTrace();
			closeDB();
			return false;
		}
	}
	
	
	
	/**
	 * Fetch the current orders from database -- CALL AFTER constructor!!!!
	 * @return
	 */
	//public ArrayList<Order> getOrders() {
	public HashMap<Integer, Order> getAllOrders() {
		
		try {
			//stmt = con.createStatement();
			
			openDB(url, dbUser, usrPass);
			
			// ONLY DOWNLOAD IF STATUS IS 0
			String sql = "Select * from orders where orders.status=?";
			PreparedStatement prep = con.prepareStatement(sql);
			prep.setString(1, "Waiting");
			
			ResultSet rs = prep.executeQuery();

			//ArrayList<Order> orders = new ArrayList<>();
			HashMap<Integer, Order> orders = new HashMap<>();
			
			while(rs.next()){
				
				int order_id = rs.getInt("orders.id");
				String customer_email = rs.getString("orders.customer_email");
				String pizza_status = rs.getString("orders.status");
				String pizza_size = rs.getString("orders.size");
				
				int pizza_topping_0_id = rs.getInt("orders.topping_0_id");
				int pizza_topping_1_id = rs.getInt("orders.topping_1_id");
				int pizza_topping_2_id = rs.getInt("orders.topping_0_id");
				
				int pizza_sauce_0_id = rs.getInt("orders.sauce_0_id");
				//int pizza_sauce_1_id = rs.getInt("orders.sauce_1_id");
				
				int pizza_cheese_0_id = rs.getInt("orders.cheese_0_id");
				//int pizza_cheese_1_id = rs.getInt("orders.cheese_1_id");
				
				String order_time = rs.getString("orders.date_time");
				
				double pizza_price = rs.getDouble("orders.price");
				
				Order myOrder = new Order
						(
							order_id, 
							pizza_size, 
							customer_email, 
							order_time, 
							toppingMap.get(pizza_topping_0_id).getName(), 
							toppingMap.get(pizza_topping_1_id).getName(), 
							toppingMap.get(pizza_topping_2_id).getName(), 
							sauceMap.get(pizza_sauce_0_id).getName(), 
							cheeseMap.get(pizza_cheese_0_id).getName(), 
							pizza_status, 
							pizza_price
						);
				
				//orders.add(myOrder);
				orders.put(order_id, myOrder);
				
				}
			
			rs.close();
			//stmt.close();
			return orders;
			
		}
		catch(Exception ex) {
			System.out.println("Error reading from database\n");
			ex.printStackTrace();
			return null;
		}
	}
	
	
	/**
	 * Fetch all customers from the database
	 * @return
	 */
	//public ArrayList<Customer> getAllCustomers() {
	public HashMap<Integer, Customer> getAllCustomers() {
		
		try {
			
			openDB(url, dbUser, usrPass);
			
			Statement stmt = con.createStatement();
			String sql = "Select * from customers";
			ResultSet rs = stmt.executeQuery(sql);

			//ArrayList<Customer> customers = new ArrayList<>();
			HashMap<Integer, Customer> customers = new HashMap<>();
			
			while(rs.next()){
				
				int customer_id = rs.getInt("customers.id");
				String customer_name = rs.getString("customers.name");
				String customer_address = rs.getString("customers.address");
				int customer_phone = rs.getInt("customers.cellphone");
				String customer_email = rs.getString("customers.email");
				int customer_discount_code = rs.getInt("customers.discount_code");
				
				Customer myCustomer = new Customer
						(
							customer_id, 
							customer_name, 
							customer_address, 
							customer_phone, 
							customer_email,
							customer_discount_code
						);
				
				//customers.add(myCustomer);
				customers.put(customer_id, myCustomer);
				
			}
			
			rs.close();
			stmt.close();
			
			closeDB();
			
			return customers;
			
		}
		catch(Exception ex) {
			System.out.println("Error reading from database");
			ex.printStackTrace();
			closeDB();
			return null;
		}
	}


	
	/**
	 * Fetch all toppings from database
	 * @return
	 */
	//public ArrayList<Order> getOrders() {
	public HashMap<Integer, Topping> getAllToppings() {
		
		try {
			
			openDB(url, dbUser, usrPass);
			
			Statement stmt = con.createStatement();
			String sql = "Select * from toppings";
			ResultSet rs = stmt.executeQuery(sql);

			//ArrayList<Customer> customers = new ArrayList<>();
			HashMap<Integer, Topping> toppings = new HashMap<>();
			
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
				toppings.put(topping_id, myTopping);
				
			}
			
			rs.close();
			stmt.close();
			
			closeDB();
			
			return toppings;
			
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
	public HashMap<Integer, Cheese> getAllCheeses() {
		
		try {
			
			openDB(url, dbUser, usrPass);
			
			Statement stmt = con.createStatement();
			String sql = "Select * from cheeses";
			ResultSet rs = stmt.executeQuery(sql);

			//ArrayList<Customer> customers = new ArrayList<>();
			HashMap<Integer, Cheese> cheeses = new HashMap<>();
			
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
				cheeses.put(cheese_id, myCheese);
				
			}
			
			rs.close();
			stmt.close();
			
			closeDB();
			
			return cheeses;
			
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
	public HashMap<Integer, Sauce> getAllSauces() {
		
		try {
			openDB(url, dbUser, usrPass);
			
			Statement stmt = con.createStatement();
			String sql = "Select * from sauces";
			ResultSet rs = stmt.executeQuery(sql);

			HashMap<Integer, Sauce> sauces = new HashMap<>();
			
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
				
				sauces.put(sauce_id, mySauce);
				
			}
			
			rs.close();
			stmt.close();
			closeDB();
			return sauces;
			
		}
		catch(Exception ex) {
			System.out.println("Error reading from database");
			ex.printStackTrace();
			closeDB();
			return null;
		}

	}

	
	private void printAllOrders(HashMap<Integer, Order> orders) {
		System.out.println("~order list~");
		for (Map.Entry<Integer, Order> e : orders.entrySet()) { 
			System.out.println(e.getValue().toString());
		} 
	}
	
	private void printAllCustomers(HashMap<Integer, Customer> customers) {
		System.out.println("~customer list~");
		for (Map.Entry<Integer, Customer> e : customers.entrySet()) { 
			System.out.println(e.getValue().toString());
		} 
	}
	
	private void printAllToppings(HashMap<Integer, Topping> toppings) {
		System.out.println("~toppings list~");
		for (Map.Entry<Integer, Topping> e : toppings.entrySet()) { 
			System.out.println(e.getValue().toString());
		} 
	}
	
	private void printAllSauces(HashMap<Integer, Sauce> sauces) {
		System.out.println("~sauce list~");
		for (Map.Entry<Integer, Sauce> e : sauces.entrySet()) { 
			System.out.println(e.getValue().toString());
		} 
	}	
	
	private void printAllCheeses(HashMap<Integer, Cheese> cheeses) {
		System.out.println("~cheese list~");
		for (Map.Entry<Integer, Cheese> e : cheeses.entrySet()) { 
			System.out.println(e.getValue().toString());
		} 
	}	
	
	
	public ObservableList<Order> getOrderList(){
		
		ObservableList<Order> orderList = FXCollections.observableArrayList();
		
		for (Map.Entry<Integer, Order> e : orderMap.entrySet()) { 
			
			
			
		} 
		
		return orderList;
		
		
	}
	
	
	public DBInterface() {
		// TODO Auto-generated constructor stub
		
		//openDB(url, dbUser, usrPass);

		
		//orderList = FXCollections.observableArrayList();
		//toppingList = FXCollections.observableArrayList();
		//sauceList = FXCollections.observableArrayList();
		//cheeseList = FXCollections.observableArrayList();
		
		toppingMap = getAllToppings();
		sauceMap = getAllSauces();
		cheeseMap = getAllCheeses();
		customerMap = getAllCustomers();
		orderMap = getAllOrders();	// this MUST be called after all 4 !!!!!
		
		
		
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
