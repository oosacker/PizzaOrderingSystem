package pizzaOrdering;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;


public class DBInterface {

	private static Connection con;
	private static Statement stmt;
	
	//String url = "jdbc:mysql://10.140.230.135:3306/pizza";
	//String dbUser = "newuser";
	//String usrPass = "12345";
	
	// for local host
	String url = "jdbc:mysql://localhost:3306/pizza";
	String dbUser = "newuser";
	String usrPass = "1234";
	
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

	
	
	
	public ArrayList<Customer> getCustomers() {
		try {
			stmt = con.createStatement();
			String sql = "Select * from customers";
			ResultSet rs = stmt.executeQuery(sql);

			ArrayList<Customer> customers = new ArrayList<>();
			
			while(rs.next()){
				
				int customer_id = rs.getInt("customers.id");
				String customer_name = rs.getString("customers.name");
				String customer_address = rs.getString("customers.address");
				int customer_phone = rs.getInt("customers.cellphone");
				String customer_email = rs.getString("customers.email");
				String customer_dicount_code = rs.getString("customers.discount_code");
				
				Customer myCustomer = new Customer
						(
							customer_id, 
							customer_name, 
							customer_address, 
							customer_phone, 
							customer_email,
							customer_dicount_code
						);
				
				customers.add(myCustomer);
				
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
	 * Add a new pizza order to the database
	 * @param newOrder
	 * @return
	 */
//	public boolean addNewOrder(Order newOrder) {
//		try {
//			stmt = con.createStatement();
//			String sql = "Select * from orders";
//			ResultSet rs = stmt.executeQuery(sql);
//
//			
//			
//			rs.close();
//			stmt.close();
//
//			return true;
//			
//		}
//		catch(Exception ex) {
//			System.out.println("Error reading from database\n");
//			ex.printStackTrace();
//			return false;
//		}
//		
//	}
	
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
	
	public Queue<Order> getOrders() {
		
		try {
			stmt = con.createStatement();
			
			// ONLY DOWNLOAD IF STATUS IS 0 OR 1 (WAITING OR COOKING)
			String sql = "Select * from orders where orders.status=? or orders.status=?";
			
			PreparedStatement prep = con.prepareStatement(sql);
			prep.setInt(1, 0);
			prep.setInt(2, 1);
			
			ResultSet rs = prep.executeQuery();

			Queue<Order> orders = new LinkedList<>();
			
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
				
				orders.offer(myOrder);
				
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

	
	public DBInterface() {
		// TODO Auto-generated constructor stub
		
		openDB(url, dbUser, usrPass);

		Queue<Order> orders = getOrders();
		for(Order order : orders) {
			System.out.println(order.toString());
		}
		
		
		ArrayList<Customer> cust = getCustomers();
		for(Customer customer : cust) {
			System.out.println(customer.toString());
		}
		
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
