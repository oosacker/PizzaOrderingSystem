package pizzaOrdering;

public class Ingredient {
	
	private int id;
	private String name;
	private int stock_level;
	
	public Ingredient(int id, String name, int stock_level) {
		this.id = id;
		this.name = name;
		this.stock_level = stock_level;
	}

	public int getStock_level() {
		return stock_level;
	}

	public void setStock_level(int stock_level) {
		this.stock_level = stock_level;
	}

	public String getName() {
		return name;
	}

	public int getId() {
		return id;
	}
	
	@Override
	public String toString() {
		return "Ingredient [id=" + id + ", name=" + name + ", stock_level=" + stock_level + "]";
	}

}
