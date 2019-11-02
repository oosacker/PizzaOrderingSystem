package application;


public class Ingredient_old {

	
	private String ingredient = null;
	private String Qty = null;
	
	@Override
	public String toString() {
		return "Ingredient [ingredient=" + ingredient + ", Qty=" + Qty + "]";
	}
	public Ingredient_old(String ingredient, String qty) {
		super();
		this.ingredient = ingredient;
		Qty = qty;
	}
	public String getIngredient() {
		return ingredient;
	}
	public void setIngredient(String ingredient) {
		this.ingredient = ingredient;
	}
	public String getQty() {
		return Qty;
	}
	public void setQty(String qty) {
		Qty = qty;
	}
	
	
  

   

	

    }


