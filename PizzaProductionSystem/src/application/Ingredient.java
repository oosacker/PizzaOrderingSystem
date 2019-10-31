package application;

public class Ingredient {
	private String ingName;
	private String quantity;


	 Ingredient(String ingName, String quantity) {
		super();
		this.ingName = ingName;
		this.quantity = quantity;
	}

	public String getIngName() {
		return ingName;
	}

	public void setIngName(String ingName) {
		this.ingName = ingName;
	}

	public String getQuantity() {
		return quantity;
	}

	public void setQuantity(String quantity) {
		this.quantity = quantity;
	}

	
}
