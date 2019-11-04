package pizzaOrdering;

public class Order {

	private int order_id;		// auto generated
	private String pizza_size; 	// small, medium, large
	private int customer_id;	// auto generated
	private String order_time;	// 
	
	private int pizza_topping_0_id;
	private int pizza_topping_1_id;
	private int pizza_topping_2_id;
	
	private int pizza_sauce_0_id;
	//private int pizza_sauce_1_id;
	
	private int pizza_cheese_0_id;
	//private int pizza_cheese_1_id;
	
	private String pizza_status;	// waiting, cooking, ready, picked up
	
	private double pizza_price;


	public Order(
				int order_id, 
				String pizza_size, 
				int customer_id, 
				String order_time, 
				int pizza_topping_0_id,
				int pizza_topping_1_id, 
				int pizza_topping_2_id, 
				int pizza_sauce_0_id, 
				int pizza_cheese_0_id, 
				String pizza_status,
				double pizza_price
			) {
		this.order_id = order_id;
		this.pizza_size = pizza_size;
		this.customer_id = customer_id;
		this.order_time = order_time;
		this.pizza_topping_0_id = pizza_topping_0_id;
		this.pizza_topping_1_id = pizza_topping_1_id;
		this.pizza_topping_2_id = pizza_topping_2_id;
		this.pizza_sauce_0_id = pizza_sauce_0_id;
		this.pizza_cheese_0_id = pizza_cheese_0_id;
		this.pizza_status = pizza_status;
		this.pizza_price = pizza_price;
	}

	public int getOrder_id() {
		return order_id;
	}

	public String getPizza_size() {
		return pizza_size;
	}

	public int getCustomer_id() {
		return customer_id;
	}

	public String getOrder_time() {
		return order_time;
	}

	public int getPizza_topping_0_id() {
		return pizza_topping_0_id;
	}

	public int getPizza_topping_1_id() {
		return pizza_topping_1_id;
	}

	public int getPizza_topping_2_id() {
		return pizza_topping_2_id;
	}

	public int getPizza_sauce_0_id() {
		return pizza_sauce_0_id;
	}


	public int getPizza_cheese_0_id() {
		return pizza_cheese_0_id;
	}


	public String getPizza_status() {
		return pizza_status;
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
				"customer_id= " + customer_id + "\n" +
				"order_time= " + order_time + "\n" +
				"pizza_topping_0_id= " + pizza_topping_0_id + "\n" +
				"pizza_topping_1_id= " + pizza_topping_1_id + "\n" +
				"pizza_topping_2_id= " + pizza_topping_2_id + "\n" +
				"pizza_sauce_0_id= " + pizza_sauce_0_id + "\n" +
				"pizza_cheese_0_id= " + pizza_cheese_0_id + "\n" +
				"pizza_status= " + pizza_status + "\n" +
				"pizza_price= " + pizza_price;
	}


}













