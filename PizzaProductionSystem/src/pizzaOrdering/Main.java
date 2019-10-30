package pizzaOrdering;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;



public class Main {

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
	
	/**
	 * Read all players from the DB and print out
	 */
	public int readDatabase() {

		try {
			stmt = con.createStatement();
			String sql = "Select * from orders";
			ResultSet rs = stmt.executeQuery(sql);
			int count = 0;


			while(rs.next()){
				
				count++;
				int id = rs.getInt("orders.id");
				String order = rs.getString("orders.name");
				System.out.println(id + " " +order);
					
			}
			rs.close();
			stmt.close();
			System.out.println("Fetched " +count + " orders from database\n");
			return count;
		}
		catch(Exception ex) {
			System.out.println("Error reading from database\n");
			ex.printStackTrace();
			return -1;
		}
	}
	
	public Main() {
		// TODO Auto-generated constructor stub
		
		openDB(url, dbUser, usrPass);
		
		readDatabase();
		
		closeDB();
		
		
	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		new Main();
		
	}

}
