package pizzaOrdering;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.LinkedList;
import java.util.Queue;


public class DBInterface {

	private static Connection con;
	private static Statement stmt;
	
	String url = "jdbc:mysql://10.140.230.135:3306/pizza";
	String dbUser = "newuser";
	String usrPass = "12345";
	
	/**
	 * Open the DB connection
	 */
	public boolean openDB(String url, String dbUser, String usrPass) {
		try {
			Class.forName("com.mysql.jdbc.Driver");
			con = DriverManager.getConnection(url, dbUser, usrPass);
			System.out.println("Connected to database\n");
			return true;

		}
		catch(Exception ex) {
			System.out.println("Error connecting to database\n");
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
			System.out.println("Closed connection to database\n");
			return true;
		}
		catch(Exception ex) {
			System.out.println("Error disconnecting from database\n");
			ex.printStackTrace();
			return false;
		}
	}

	
	private Queue<Order> getOrders() {
		
		try {
			stmt = con.createStatement();
			String sql = "Select * from orders";
			ResultSet rs = stmt.executeQuery(sql);

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
		
		
		closeDB();
		
		
	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		new DBInterface();
		
	}

}
