package pizzaOrdering;

import java.sql.Timestamp;

public class Order {

	private int order_id;
	
	private int pizza_size;
	
	private int customer_id;
	
	private Timestamp order_time;
	
	private int pizza_topping_0_id;
	private int pizza_topping_1_id;
	private int pizza_topping_2_id;
	
	private int pizza_sauce_0_id;
	private int pizza_sauce_1_id;
	
	private int pizza_cheese_0_id;
	private int pizza_cheese_1_id;
	
	private int pizza_status;
	
	private double pizza_price;


//	public Order()
//	{
//		
//	}
	public Order(
				int order_id, 
				int pizza_size, 
				int customer_id, 
				Timestamp order_time, 
				int pizza_topping_0_id,
				int pizza_topping_1_id, 
				int pizza_topping_2_id, 
				int pizza_sauce_0_id, 
				int pizza_sauce_1_id,
				int pizza_cheese_0_id, 
				int pizza_cheese_1_id, 
				int pizza_status,
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
		this.pizza_sauce_1_id = pizza_sauce_1_id;
		this.pizza_cheese_0_id = pizza_cheese_0_id;
		this.pizza_cheese_1_id = pizza_cheese_1_id;
		this.pizza_status = pizza_status;
		this.pizza_price = pizza_price;
	}

	public int getOrder_id() {
		return order_id;
	}

	public int getPizza_size() {
		return pizza_size;
	}

	public int getCustomer_id() {
		return customer_id;
	}

	public Timestamp getOrder_time() {
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

	public int getPizza_sauce_1_id() {
		return pizza_sauce_1_id;
	}

	public int getPizza_cheese_0_id() {
		return pizza_cheese_0_id;
	}

	public int getPizza_cheese_1_id() {
		return pizza_cheese_1_id;
	}

	public int getPizza_status() {
		return pizza_status;
	}
	
	public double getPizza_price() {
		return pizza_price;
	}

	@Override
	public String toString() {
		return "Order [order_id=" + order_id + ", pizza_size=" + pizza_size + ", customer_id=" + customer_id
				+ ", order_time=" + order_time + ", pizza_topping_0_id=" + pizza_topping_0_id + ", pizza_topping_1_id="
				+ pizza_topping_1_id + ", pizza_topping_2_id=" + pizza_topping_2_id + ", pizza_sauce_0_id="
				+ pizza_sauce_0_id + ", pizza_sauce_1_id=" + pizza_sauce_1_id + ", pizza_cheese_0_id="
				+ pizza_cheese_0_id + ", pizza_cheese_1_id=" + pizza_cheese_1_id + ", pizza_status=" + pizza_status
				+ ", pizza_price=" + pizza_price + "]";
	}


}
