package application;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.function.Function;

import javafx.application.Application;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.beans.value.ObservableValue;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import pizzaOrdering.Cheese;
import pizzaOrdering.Customer;
import pizzaOrdering.DBInterface;
import pizzaOrdering.Ingredient;
import pizzaOrdering.Order;
import pizzaOrdering.Sauce;
import pizzaOrdering.Topping;

public class ProductionApp extends Application{

	String url = "jdbc:mysql://10.140.230.135:3306/pizza";
	String dbUser = "newuser";
	String usrPass = "12345";
	
	Scene scene1;
	
	DBInterface dbi;

	HashMap<Integer, Order> orderlist;
	HashMap<Integer, Customer> customerlist;
	HashMap<Integer, Topping> toppinglist;
	HashMap<Integer, Cheese> cheeselist;
	HashMap<Integer, Sauce> saucelist;
	

	public void init() {
		DBInterface dbi = new DBInterface();
		dbi.openDB(url, dbUser, usrPass);
	}
	
	public void start(Stage stage) {
		
		init();
		dbi.closeDB();
		
		scene1 = new Scene(new Group());
		stage.setTitle("Production Line");
		stage.setWidth(1100);
		stage.setHeight(600);
		stage.initStyle(StageStyle.DECORATED);
		
		Label stock = new Label("Stock");
		stock.setFont(new Font("Arial", 20));

		Label orders = new Label("Orders");
		orders.setFont(new Font("Arial", 20));
		
    	Button stockbtn = new Button();
    	stockbtn.setText("Update Stock");
    	stockbtn.setTextFill(Color.RED);
    	stockbtn.setLayoutX(20);
    	stockbtn.setLayoutY(100);
    	
    	Button orderbtn = new Button();
    	orderbtn.setText("Update Order");
    	orderbtn.setTextFill(Color.RED);

    	orderbtn.setLayoutX(20);
    	orderbtn.setLayoutY(100);

    	
    	 TableView<Ingredient> table = new TableView<>();
    	 
    	 
         table.setRowFactory(tv -> {
             TableRow<Ingredient> row = new TableRow<>();
             row.setOnMouseClicked(event -> {
                 if (event.getClickCount() == 2 && (! row.isEmpty()) ) {
                	 Ingredient rowData = row.getItem();
                     System.out.println("Double click on: "+rowData.getName());
                 }
             });
             return row ;
         });
         
         
         
         //table.getColumns().add(column("id",toppinglist.get(0).getId()));
         //table.getColumns().add(column("Value", Ingredient));
    	
         TableColumn col = new TableColumn<String, Integer>("ID", toppinglist.get(0).getId());
         table.getColumns().add(col);
    	
    	Scene scene = new Scene(table);
        stage.setScene(scene);
        stage.show();
    	
	}
	

	
	public static void main(String[] args) {
		launch(args);
	}

}














