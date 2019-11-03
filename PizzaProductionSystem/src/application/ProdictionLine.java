package application;
	
import java.util.ArrayList;
import java.util.HashMap;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import pizzaOrdering.Cheese;
import pizzaOrdering.Customer;
import pizzaOrdering.DBInterface;
import pizzaOrdering.Order;
import pizzaOrdering.Sauce;
import pizzaOrdering.Topping;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;


public class ProdictionLine extends Application {

	private TableView orderTable = new TableView();
	private TableView stokeTable = new TableView();
	
//	private final ObservableList<Ingredient> data =
//		        FXCollections.observableArrayList(
//		        		new Ingredient("Tomato", "50"),
//		        		new Ingredient("Onion", "50"),
//		        		new Ingredient("Cheese Cheddar", "50"),
//		        		new Ingredient("Cheese mozzarella", "50"),
//		        		new Ingredient("Chichen", "50"),
//		        		new Ingredient("Beef", "50")
//		        );
	
public static void main(String[] args) {
		
		launch(args);
		
}

	
	
	@Override
    public void start(Stage stage) {
		try {
		
		String url = "jdbc:mysql://10.140.230.135:3306/pizza";
		String dbUser = "newuser";
		String usrPass = "12345";
		
		DBInterface dbi = new DBInterface();
		dbi.openDB(url, dbUser, usrPass);
		
		HashMap<Integer, Order> orderlist = dbi.getAllOrders();
		HashMap<Integer, Customer> customerlist = dbi.getAllCustomers();
		HashMap<Integer, Topping> toppinglist = dbi.getAllToppings();
		HashMap<Integer, Cheese> cheeselist = dbi.getAllCheeses();
		HashMap<Integer, Sauce> saucelist = dbi.getAllSauces();
		
		
        Scene scene = new Scene(new Group());
        stage.setTitle("Prodiction Line");
        stage.setWidth(1100);
        stage.setHeight(600);
        stage.initStyle(StageStyle.DECORATED);
//        stage.setX(200);
//        stage.setY(50);
        
        
//      Stoke update button
    	Button stokebtn = new Button();
    	stokebtn.setText("Update Stoke");
    	stokebtn.setTextFill(Color.RED);
//		btn.borderProperty();
    	stokebtn.setLayoutX(20);
    	stokebtn.setLayoutY(100);
    	
//      Stoke update button
    	Button orderbtn = new Button();
    	orderbtn.setText("Update Oreder");
    	orderbtn.setTextFill(Color.RED);
//		btn.borderProperty();
    	orderbtn.setLayoutX(20);
    	orderbtn.setLayoutY(100);


        final Label stoke = new Label("Stoke");
        stoke.setFont(new Font("Arial", 20));
        
        final Label orders = new Label("Orders");
        orders.setFont(new Font("Arial", 20));
 
        orderTable.setEditable(true);
        stokeTable.setEditable(true);

        
        TableColumn<String, Ingredient> IngredientsCol = new TableColumn<>("Ingredients");
        IngredientsCol.setMinWidth(100);
        IngredientsCol.setCellValueFactory(new PropertyValueFactory<>("ingredient"));
        
        TableColumn<String, Ingredient> QTYCol = new TableColumn<>("QTY");
        QTYCol.setMinWidth(100);
        QTYCol.setCellValueFactory(new PropertyValueFactory<>("QTY"));
        
        
        TableColumn<String, OrderInfo> customerIdCol = new TableColumn<>("Customer ID");
        customerIdCol.setMinWidth(50);
        customerIdCol.setCellValueFactory(new PropertyValueFactory<>("customerId"));
        
        TableColumn<String, OrderInfo> sizegCol = new TableColumn<>("Size");
        sizegCol.setMinWidth(50);
        sizegCol.setCellValueFactory(new PropertyValueFactory<>("Size"));
        
        TableColumn<String, OrderInfo> topping1Col = new TableColumn<>("topping 1");
        topping1Col.setMinWidth(100);
        topping1Col.setCellValueFactory(new PropertyValueFactory<>("topping_1"));
        
        TableColumn<String, OrderInfo> topping2Col = new TableColumn<>("topping 2");
        topping2Col.setMinWidth(100);
        topping2Col.setCellValueFactory(new PropertyValueFactory<>("topping_2"));
        
        TableColumn<String, OrderInfo> topping3Col = new TableColumn<>("topping 3");
        topping3Col.setMinWidth(100);
        topping3Col.setCellValueFactory(new PropertyValueFactory<>("topping_3"));
        
        

        TableColumn<String, OrderInfo> sauces1Col = new TableColumn<>("sauces 1");
        sauces1Col.setMinWidth(50);
        sauces1Col.setCellValueFactory(new PropertyValueFactory<>("sauces_1"));
        
        TableColumn<String, OrderInfo> sauces2Col = new TableColumn<>("sauces 2");
        sauces2Col.setMinWidth(50);
        sauces2Col.setCellValueFactory(new PropertyValueFactory<>("sauces_2"));

        TableColumn<String, Cheese> cheese1Col = new TableColumn<>("Cheese 1");
        cheese1Col.setMinWidth(100);
        cheese1Col.setCellValueFactory(new PropertyValueFactory<>("Cheese_1"));
        
        TableColumn<String, Cheese> cheese2Col = new TableColumn<>("Cheese 2");
        cheese2Col.setMinWidth(100);	
        cheese2Col.setCellValueFactory(new PropertyValueFactory<>("Cheese_2"));
        
        TableColumn<String, OrderInfo> timeCol = new TableColumn<>("Time");
        timeCol.setMinWidth(50);
        timeCol.setCellValueFactory(new PropertyValueFactory<>("Time"));
        
        TableColumn<String, OrderInfo> priceCol = new TableColumn<>("Price");
        priceCol.setMinWidth(50);
        priceCol.setCellValueFactory(new PropertyValueFactory<>("Price"));
        
        TableColumn<String, OrderInfo> statusCol = new TableColumn<>("Status");
        statusCol.setMinWidth(50);
        statusCol.setCellValueFactory(new PropertyValueFactory<>("Status"));

        orderTable.getColumns().add(customerIdCol);
        orderTable.getColumns().add(sizegCol);
        orderTable.getColumns().add(topping1Col);
        orderTable.getColumns().add(topping2Col);
        orderTable.getColumns().add(topping3Col);
        orderTable.getColumns().add(sauces1Col);
        orderTable.getColumns().add(sauces2Col);
        orderTable.getColumns().add(cheese1Col);
        orderTable.getColumns().add(cheese2Col);
        orderTable.getColumns().add(timeCol);
        orderTable.getColumns().add(priceCol);
        orderTable.getColumns().add(statusCol);
        
        stokeTable.getColumns().add(IngredientsCol);
        stokeTable.getColumns().add(QTYCol);

        orderTable.getItems().add(new OrderInfo("5", "L", "Beef", "Tomato", "Chidder", "9", "15", "cooking"));
        stokeTable.getItems().add(new Ingredient( "Tomato", "100"));
        
        orderTable.getItems().add(new OrderInfo("4", "S", "Chicken", "Onion", "mozzarella", "12", "14", "Ready"));
        stokeTable.getItems().add(new Ingredient( "Onion", "99"));
        
        
        
////        stoke columns
//        TableColumn ingredientsCol = new TableColumn("Ingredients");
//        TableColumn QTYCol = new TableColumn("QTY");
        
//      orders columns
//        TableColumn customerIdCol = new TableColumn("Customer id");
//        TableColumn sizegCol = new TableColumn("Size");
//        TableColumn toppingCol = new TableColumn("Topping");
//        TableColumn saucesCol = new TableColumn("Sauces");
//        TableColumn cheeseCol = new TableColumn("Cheese");
//        TableColumn timeCol = new TableColumn("Time");
//        TableColumn priceCol = new TableColumn("Price");
//        TableColumn statusCol = new TableColumn("Status");
     
//        table.setItems(data);
        
//      adding columns to tables
        orderTable.getColumns().addAll(customerIdCol, sizegCol, topping1Col,topping2Col,topping3Col,sauces1Col,sauces2Col, cheese1Col, cheese2Col,timeCol,priceCol,statusCol, IngredientsCol, QTYCol);
 
// 
//      stoke vBox
        final VBox stokebox = new VBox();
        stokebox.setSpacing(5);
        stokebox.setPadding(new Insets(10, 0, 0, 10));
        stokebox.getChildren().addAll(stoke, stokeTable, stokebtn);
        
//      orders vBox
        final VBox ordersbox = new VBox();
        ordersbox.setSpacing(5);
        ordersbox.setPadding(new Insets(10, 0, 0, 300));
        ordersbox.getChildren().addAll(orders, orderTable,orderbtn);
 
        ((Group) scene.getRoot()).getChildren().addAll(ordersbox, stokebox);
 
        stage.setScene(scene);
//        stage.setFullScreen(true);
        stage.show();
    }
		catch(Exception e) {
			e.printStackTrace();
		}

}
}

