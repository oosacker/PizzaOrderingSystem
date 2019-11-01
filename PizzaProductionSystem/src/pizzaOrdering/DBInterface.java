package pizzaOrdering;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Queue;


public class DBInterface {

	private static Connection con;
	private static Statement stmt;
	
	/* For remote host */
	String url = "jdbc:mysql://10.140.230.135:3306/pizza";
	String dbUser = "newuser";
	String usrPass = "12345";
	
	/* For local host */
//	String url = "jdbc:mysql://localhost:3306/pizza";
//	String dbUser = "newuser";
//	String usrPass = "1234";
	
	
	
	/**
	 * Open the DB connection
	 */
	public boolean openDB(String url, String dbUser, String usrPass) {
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
	public boolean closeDB() {
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
			
			stmt = con.createStatement();
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
			stmt.close();
			
			return user_verified;
		}
		
		catch(Exception ex) {
			System.out.println("Error reading from database");
			ex.printStackTrace();
			return false;
		}

	}
	
	
	/**
	 * Find cheese on database based on id; return null if not found
	 * @return
	 */
	public Cheese findCheese(int cheese_id_input) {
		
		try {
			
			Cheese myCheese = null;
			
			stmt = con.createStatement();
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
			stmt.close();
			
			return myCheese;
		}
		
		catch(Exception ex) {
			System.out.println("Error reading from database");
			ex.printStackTrace();
			return null;
		}
	}
	

	/**
	 * Find sauce on database based on id; return null if not found
	 * @return
	 */
	public Sauce findSauce(int sauce_id_input) {
		
		try {
			
			Sauce mySauce = null;
			
			stmt = con.createStatement();
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
			stmt.close();
			
			return mySauce;
		}
		
		catch(Exception ex) {
			System.out.println("Error reading from database");
			ex.printStackTrace();
			return null;
		}
	}
	
	
	/**
	 * Find topping on database based on id; return null if not found
	 * @return
	 */
	public Topping findTopping(int topping_id_input) {
		
		try {
			
			Topping myTopping = null;
			
			stmt = con.createStatement();
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
			stmt.close();
			
			return myTopping;
		}
		
		catch(Exception ex) {
			System.out.println("Error reading from database");
			ex.printStackTrace();
			return null;
		}
	}
	
	/**
	 * Find customer on database based on id; return null if not found
	 * @return
	 */
	public Customer findCustomer(int customer_id_input) {
		
		try {
			
			Customer myCustomer = null;
			
			stmt = con.createStatement();
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
			stmt.close();
			
			return myCustomer;
			
			
		}
		catch(Exception ex) {
			System.out.println("Error reading from database");
			ex.printStackTrace();
			return null;
		}
	}
	
	
	/**
	 * Find order on database based on id; return null if not found
	 * @return
	 */
	public Order findOrder(int order_id_input) {
		
		try {
			
			Order myOrder = null;
			
			stmt = con.createStatement();
			String sql = "Select * from orders where orders.id = ?";
			PreparedStatement prep = con.prepareStatement(sql);
			
			prep.setInt(1, order_id_input);
			
			ResultSet rs = prep.executeQuery();
			
			if(rs.next()){
				
				int order_id = rs.getInt("orders.id");
				int customer_id = rs.getInt("orders.customer_id");
				int pizza_status = rs.getInt("orders.status");
				int pizza_size = rs.getInt("orders.size");
				
				int pizza_topping_0_id = rs.getInt("orders.topping_0_id");
				int pizza_topping_1_id = rs.getInt("orders.topping_1_id");
				int pizza_topping_2_id = rs.getInt("orders.topping_0_id");
				
				int pizza_sauce_0_id = rs.getInt("orders.sauce_0_id");
				int pizza_sauce_1_id = rs.getInt("orders.sauce_1_id");
				
				int pizza_cheese_0_id = rs.getInt("orders.cheese_0_id");
				int pizza_cheese_1_id = rs.getInt("orders.cheese_1_id");
				
				Timestamp order_time = rs.getTimestamp("orders.date_time");
				
				double pizza_price = rs.getDouble("orders.price");
				
				myOrder = new Order(
							order_id, 
							pizza_size, 
							customer_id, 
							order_time, 
							pizza_topping_0_id, 
							pizza_topping_1_id, 
							pizza_topping_2_id, 
							pizza_sauce_0_id, 
							pizza_sauce_1_id, 
							pizza_cheese_0_id, 
							pizza_cheese_1_id, 
							pizza_status, 
							pizza_price
						);
				
			}

			rs.close();
			stmt.close();
			return myOrder;
			
			
		}
		catch(Exception ex) {
			System.out.println("Error reading from database");
			ex.printStackTrace();
			return null;
		}
	}
	
	
	/**
	 * Fetch all ingredients 
	 */
	
	
	
	
	
	/**
	 * Change the state of an order on the database
	 * @param state
	 * @return
	 */
	public boolean setOrderState(Order order, int newStatus) {
		try {
			if ( orderExists(order) ) {	
				
				order.setPizza_status(newStatus);
				
				int order_id = order.getOrder_id();
				
				String query = "UPDATE `pizza`.`orders` SET `status` = ? WHERE (`id` = ?);";
				
				PreparedStatement prep = con.prepareStatement(query);
				prep.setInt(1, newStatus);
				prep.setInt(2, order_id);
				prep.execute();
				stmt.close();
				
				return true;
			}
			else {
				return false;
			}
		}
		catch(Exception ex) {
			ex.printStackTrace();
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
			
			boolean orderFound = false;
			
			stmt = con.createStatement();
			
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
			stmt.close();
			return orderFound;
		}
		catch(Exception ex) {
			ex.printStackTrace();
			return false;
		}
	}
	
	
	
	/**
	 * Fetch the current orders from database
	 * @return
	 */
	//public ArrayList<Order> getOrders() {
	public HashMap<Integer, Order> getAllOrders() {
		
		try {
			stmt = con.createStatement();
			
			// ONLY DOWNLOAD IF STATUS IS 0 OR 1 (WAITING OR COOKING)
			String sql = "Select * from orders where orders.status=? or orders.status=?";
			
			PreparedStatement prep = con.prepareStatement(sql);
			prep.setInt(1, 0);
			prep.setInt(2, 1);
			
			ResultSet rs = prep.executeQuery();

			//ArrayList<Order> orders = new ArrayList<>();
			HashMap<Integer, Order> orders = new HashMap<>();
			
			while(rs.next()){
				
				int order_id = rs.getInt("orders.id");
				int customer_id = rs.getInt("orders.customer_id");
				int pizza_status = rs.getInt("orders.status");
				int pizza_size = rs.getInt("orders.size");
				
				int pizza_topping_0_id = rs.getInt("orders.topping_0_id");
				int pizza_topping_1_id = rs.getInt("orders.topping_1_id");
				int pizza_topping_2_id = rs.getInt("orders.topping_0_id");
				
				int pizza_sauce_0_id = rs.getInt("orders.sauce_0_id");
				int pizza_sauce_1_id = rs.getInt("orders.sauce_1_id");
				
				int pizza_cheese_0_id = rs.getInt("orders.cheese_0_id");
				int pizza_cheese_1_id = rs.getInt("orders.cheese_1_id");
				
				Timestamp order_time = rs.getTimestamp("orders.date_time");
				
				double pizza_price = rs.getDouble("orders.price");
				
				Order myOrder = new Order
						(
							order_id, 
							pizza_size, 
							customer_id, 
							order_time, 
							pizza_topping_0_id, 
							pizza_topping_1_id, 
							pizza_topping_2_id, 
							pizza_sauce_0_id, 
							pizza_sauce_1_id, 
							pizza_cheese_0_id, 
							pizza_cheese_1_id, 
							pizza_status, 
							pizza_price
						);
				
				//orders.add(myOrder);
				orders.put(order_id, myOrder);
				
				}
			
			rs.close();
			stmt.close();
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
			stmt = con.createStatement();
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
			return customers;
			
		}
		catch(Exception ex) {
			System.out.println("Error reading from database");
			ex.printStackTrace();
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
			stmt = con.createStatement();
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
			return toppings;
			
		}
		catch(Exception ex) {
			System.out.println("Error reading from database");
			ex.printStackTrace();
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
			stmt = con.createStatement();
			String sql = "Select * from toppings";
			ResultSet rs = stmt.executeQuery(sql);

			//ArrayList<Customer> customers = new ArrayList<>();
			HashMap<Integer, Cheese> cheeses = new HashMap<>();
			
			while(rs.next()){
				
				int cheese_id = rs.getInt("toppings.id");
				String cheese_name = rs.getString("toppings.name");
				int cheese_stock_level = rs.getInt("toppings.stock_level");
				
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
			return cheeses;
			
		}
		catch(Exception ex) {
			System.out.println("Error reading from database");
			ex.printStackTrace();
			return null;
		}

	}

	
	
	
	
	public DBInterface() {
		// TODO Auto-generated constructor stub
		
		openDB(url, dbUser, usrPass);

//		ArrayList<Order> orders = getOrders();
//		for(Order order : orders) {
//			System.out.println(order.toString());
//		}
//		
		
		System.out.println(verifyStaffLogin("staff","password"));
		
//		ArrayList<Customer> cust = getCustomers();
//		for(Customer customer : cust) {
//			System.out.println(customer.toString());
//		}
		
//		System.out.println(findCustomer(1).toString());
//		System.out.println(findOrder(1).toString());
//		setOrderState(
//				new Order(
//						0, 
//						0, 
//						0, 
//						null,
//						0,
//						0,
//						0,
//						0,
//						0,
//						0,
//						0,
//						0,
//						0), 99);
		
		
		closeDB();
		
		
	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		new DBInterface();
		
	}

}
