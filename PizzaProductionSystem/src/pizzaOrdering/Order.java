package pizzaOrdering;

public class Order {

	private int order_id;		// auto generated
	private String pizza_size; 	// small, medium, large
	
	private String customer_phone;	// auto generated
	
	private String order_time;	// 
	
	//private int pizza_topping_0_id;
	//private int pizza_topping_1_id;
	//private int pizza_topping_2_id;
	
	private String pizza_topping_0;
	private String pizza_topping_1;
	private String pizza_topping_2;
	
	//private int pizza_sauce_0_id;
	//private int pizza_sauce_1_id;
	
	private String pizza_sauce_0;
	
	//private int pizza_cheese_0_id;
	//private int pizza_cheese_1_id;
	private String pizza_cheese_0;
	
	
	private String pizza_status;	// waiting, cooking, ready, picked up
	
	private double pizza_price;


	public Order(
				int order_id, 
				String pizza_size, 
				String customer_phone, 
				String order_time, 
				String pizza_topping_0,
				String pizza_topping_1, 
				String pizza_topping_2, 
				String pizza_sauce_0, 
				String pizza_cheese_0, 
				String pizza_status,
				double pizza_price
			) {
		this.order_id = order_id;
		this.pizza_size = pizza_size;
		this.customer_phone = customer_phone;
		this.order_time = order_time;
		this.pizza_topping_0 = pizza_topping_0;
		this.pizza_topping_1 = pizza_topping_1;
		this.pizza_topping_2 = pizza_topping_2;
		this.pizza_sauce_0 = pizza_sauce_0;
		this.pizza_cheese_0 = pizza_cheese_0;
		this.pizza_status = pizza_status;
		this.pizza_price = pizza_price;
	}

	public int getOrder_id() {
		return order_id;
	}

	public String getPizza_size() {
		return pizza_size;
	}

	public String getCustomer_phone() {
		return customer_phone;
	}

	public String getOrder_time() {
		return order_time;
	}

	public String getPizza_topping_0() {
		return pizza_topping_0;
	}

	public String getPizza_topping_1() {
		return pizza_topping_1;
	}

	public String getPizza_topping_2() {
		return pizza_topping_2;
	}

	public String getPizza_sauce_0() {
		return pizza_sauce_0;
	}


	public String getPizza_cheese_0() {
		return pizza_cheese_0;
	}


	public String getPizza_status() {
		return pizza_status;
	}
	
	public void changePizzaStatus() {
		
		if (this.pizza_status.equalsIgnoreCase("Waiting")) {
			this.pizza_status = "Cooking";
		}
		
		else if (this.pizza_status.equalsIgnoreCase("Cooking")) {
			this.pizza_status = "Ready";
		}
		
		else if (this.pizza_status.equalsIgnoreCase("Ready")) {
			this.pizza_status = "Picked up";
		}
		
		else if (this.pizza_status.equalsIgnoreCase("Picked up")){
			// do nothing
		}
		
		else {
			System.out.println("Error in changePizzaStatus");
		}
	}
	
	public void setPizza_status(String pizza_status) {
		this.pizza_status = pizza_status;
	}
	
	public double getPizza_price() {
		return pizza_price;
	}

	@Override
	public String toString() {
		return "order_id= " + order_id + "\n" +
				"pizza_size= " + pizza_size + "\n" +
				"customer_phone= " + customer_phone + "\n" +
				"order_time= " + order_time + "\n" +
				"pizza_topping_0= " + pizza_topping_0 + "\n" +
				"pizza_topping_1= " + pizza_topping_1 + "\n" +
				"pizza_topping_2= " + pizza_topping_2 + "\n" +
				"pizza_sauce_0= " + pizza_sauce_0 + "\n" +
				"pizza_cheese_0= " + pizza_cheese_0 + "\n" +
				"pizza_status= " + pizza_status + "\n" +
				"pizza_price= " + pizza_price;
	}


}













