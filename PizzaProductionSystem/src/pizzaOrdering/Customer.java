package pizzaOrdering;

public class Customer {

	private String customer_name;
	private int customer_id;
	private String customer_address;
	private int customer_phone;
	private String customer_email;
	private String customer_dicount_code;

	public Customer(
			int customer_id, 
			String customer_name, 
			String customer_address, 
			int customer_phone,
			String customer_email,
			String customer_dicount_code
			) {
		this.customer_name = customer_name;
		this.customer_id = customer_id;
		this.customer_address = customer_address;
		this.customer_phone = customer_phone;
		this.customer_email = customer_email;
		this.customer_dicount_code = customer_dicount_code;
	}


	public String getCustomer_name() {
		return customer_name;
	}


	public int getCustomer_id() {
		return customer_id;
	}


	public String getCustomer_address() {
		return customer_address;
	}


	public int getCustomer_phone() {
		return customer_phone;
	}


	public String getCustomer_email() {
		return customer_email;
	}

}
