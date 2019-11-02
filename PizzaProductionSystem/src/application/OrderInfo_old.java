package application;


public class OrderInfo_old {

	
	private String customerId = null;
	private String size = null;
    private String topping = null;
    private String sauces = null;
    private String cheese = null;
    private String time = null;
    private String status = null;
    private String price = null;

    public OrderInfo_old() {
    }
    
    public OrderInfo_old(String customerId, String size, String topping, String sauces, String cheese, String time
    		, String price, String status) {
		super();
		this.customerId = customerId;
		this.size = size;
		this.topping = topping;
		this.sauces = sauces;
		this.cheese = cheese;
		this.time = time;
		this.price = price;
		this.status = status;
		
	}

	@Override
	public String toString() {
		return "Ingredient [customerId=" + customerId + ", size=" + size + ", topping=" + topping + ", sauces=" + sauces
				+ ", cheese=" + cheese + ", time=" + time + ", status=" + status + ", price=" + price + "]";
	}

	public String getCustomerId() {
		return customerId;
	}

	public void setCustomerId(String customerId) {
		this.customerId = customerId;
	}

	public String getSize() {
		return size;
	}

	public void setSize(String size) {
		this.size = size;
	}

	public String getCheese() {
		return cheese;
	}

	public void setCheese(String cheese) {
		this.cheese = cheese;
	}

	public String getTime() {
		return time;
	}

	public void setTime(String time) {
		this.time = time;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getPrice() {
		return price;
	}

	public void setPrice(String price) {
		this.price = price;
	}

	public OrderInfo_old(String topping, String sauces) {
		
		this.topping = topping;
		this.sauces = sauces;
	}

	public String getTopping() {
		return topping;
	}

	public void setTopping(String topping) {
		this.topping = topping;
	}

	public String getSauces() {
		return sauces;
	}

	public void setSauces(String sauces) {
		this.sauces = sauces;
	}

	

    }


