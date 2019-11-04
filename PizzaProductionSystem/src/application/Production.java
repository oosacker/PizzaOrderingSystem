package application;


import java.util.HashMap;
import java.util.Map;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import pizzaOrdering.Cheese;
import pizzaOrdering.Customer;
import pizzaOrdering.DBInterface;
import pizzaOrdering.Ingredient;
import pizzaOrdering.Order;
import pizzaOrdering.Sauce;
import pizzaOrdering.Topping;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;


public class Production extends Application {

	HashMap<Integer, Order> orderlist;
	HashMap<Integer, Customer> customerlist;
	HashMap<Integer, Topping> toppinglist;
	HashMap<Integer, Cheese> cheeselist;
	HashMap<Integer, Sauce> saucelist;
	
	
//	String url = "jdbc:mysql://10.140.230.135:3306/pizza";
//	String dbUser = "newuser";
//	String usrPass = "12345";

	String url = "jdbc:mysql://localhost:3306/pizza";
	String dbUser = "newuser";
	String usrPass = "12345";
	
	DBInterface dbi = new DBInterface();
	
	@SuppressWarnings("rawtypes")
	private TableView orderTable = new TableView();
	@SuppressWarnings("rawtypes")
	private TableView stockTable = new TableView();
	

	public static void main(String[] args) {
		launch(args);

	}
	

	public void Login(Stage primaryStage) throws Exception {

//				String url = "jdbc:mysql://10.140.230.135:3306/pizza";
//				String dbUser = "newuser";
//				String usrPass = "12345";

////				String url = "jdbc:mysql://10.140.230.135:3306/pizza";
////				String dbUser = "newuser";
////				String usrPass = "12345";


////		String url = "jdbc:mysql://localhost:3306/pizza";
////		String dbUser = "newuser";
////		String usrPass = "1234";

		DBInterface dbi = new DBInterface();

		dbi.openDB(url, dbUser, usrPass);
		
		orderlist = dbi.getAllOrders();
		customerlist = dbi.getAllCustomers();
		toppinglist = dbi.getAllToppings();
		cheeselist = dbi.getAllCheeses();
		saucelist = dbi.getAllSauces();
		
		//dbi.closeDB();

		
		 primaryStage.setTitle("Staff Login");
	        GridPane grid = new GridPane();
	        grid.setAlignment(Pos.CENTER);
	        grid.setHgap(10);
	        grid.setVgap(10);
	        grid.setPadding(new Insets(25, 25, 25, 25));

	        Text scenetitle = new Text("Staff Login");
	        scenetitle.setFont(Font.font("Tahoma", FontWeight.NORMAL, 20));
	        grid.add(scenetitle, 0, 0, 2, 1);

	        Label userName = new Label("User Name:");
	        grid.add(userName, 0, 1);

	        TextField userTextField = new TextField();
	        grid.add(userTextField, 1, 1);

	        Label pw = new Label("Password:");
	        grid.add(pw, 0, 2);

	        PasswordField pwBox = new PasswordField();
	        grid.add(pwBox, 1, 2);

	        Button btn = new Button("Sign in");
	        HBox hbBtn = new HBox(10);
	        hbBtn.setAlignment(Pos.BOTTOM_RIGHT);
	        hbBtn.getChildren().add(btn);
	        grid.add(hbBtn, 1, 4);

	        final Text actiontarget = new Text();
	        grid.add(actiontarget, 1, 6);
	        
	        btn.setOnAction(new EventHandler<ActionEvent>() {
	            @Override
	            public void handle(ActionEvent event) {
	        
	                  actiontarget.setFill(Color.FIREBRICK);
	                  actiontarget.setText("Sign in button pressed");
	                  
	                  String name = userName.getText();
	                  String pass = pwBox.getText();
	                  
	                  boolean verified =  dbi.verifyStaffLogin(name, pass);
	                  
	                  if (verified) {
	  	                start();

	                  	System.out.println("login successful");
	                  }
	                  
	            }
	        });
	        
	        Scene scene = new Scene(grid, 300, 275);
	        primaryStage.setScene(scene);
	        primaryStage.show();
	    }
	


	@SuppressWarnings("unchecked")
	public void start() {
		Stage stage = new Stage();
	
				
		Scene scene = new Scene(new Group());
		stage.setTitle("Production Line");
		stage.setWidth(1500);
		stage.setHeight(600);
		stage.initStyle(StageStyle.DECORATED);



		final Label stock = new Label("Stock");
		stock.setFont(new Font("Arial", 20));

		final Label orders = new Label("Orders");
		orders.setFont(new Font("Arial", 20));
		
//      Stock update button
    	Button stockbtn = new Button();
    	stockbtn.setText("Update Stock");
    	stockbtn.setTextFill(Color.RED);
    	stockbtn.setLayoutX(20);
    	stockbtn.setLayoutY(100);
    	
//      Order update button
    	Button orderbtn = new Button();
    	orderbtn.setText("Update Oreder");
    	orderbtn.setTextFill(Color.RED);
//		btn.borderProperty();
    	orderbtn.setLayoutX(20);
    	orderbtn.setLayoutY(100);

		orderTable.setEditable(true);
		stockTable.setEditable(true);


		TableColumn<String, Ingredient> IngredientsCol = new TableColumn<>("Ingredients");
		IngredientsCol.setMinWidth(100);
		IngredientsCol.setCellValueFactory(new PropertyValueFactory<>("name"));

		
		TableColumn<String, Ingredient> QTYCol = new TableColumn<>("QTY");
		QTYCol.setMinWidth(100);
		QTYCol.setCellValueFactory(new PropertyValueFactory<>("stock_level"));


		TableColumn<String, Order> customerIdCol = new TableColumn<>("Customer ID");
		customerIdCol.setMinWidth(50);
		customerIdCol.setCellValueFactory(new PropertyValueFactory<>("customer_id"));

		
		TableColumn<String, Order> sizeCol = new TableColumn<>("Size");
		sizeCol.setMinWidth(50);
		sizeCol.setCellValueFactory(new PropertyValueFactory<>("pizza_size"));

		
		TableColumn<String, Order> topping0Col = new TableColumn<>("Topping 0");
		topping0Col.setMinWidth(100);
		topping0Col.setCellValueFactory(new PropertyValueFactory<>("name"));
		
		TableColumn<String, Order> topping1Col = new TableColumn<>("Topping 1");
		topping1Col.setMinWidth(100);
		topping1Col.setCellValueFactory(new PropertyValueFactory<>("name"));
		
		TableColumn<String, Order> topping2Col = new TableColumn<>("Topping 2");
		topping2Col.setMinWidth(100);
		topping2Col.setCellValueFactory(new PropertyValueFactory<>("name"));

		
		TableColumn<String, Order> saucesCol = new TableColumn<>("Sauces");
		saucesCol.setMinWidth(100);
		saucesCol.setCellValueFactory(new PropertyValueFactory<>("name"));

		
		TableColumn<String, Order> cheeseCol = new TableColumn<>("Cheese");
		cheeseCol.setMinWidth(100);
		cheeseCol.setCellValueFactory(new PropertyValueFactory<>("name"));

		
		TableColumn<String, Order> timeCol = new TableColumn<>("Time");
		timeCol.setMinWidth(100);
		timeCol.setCellValueFactory(new PropertyValueFactory<>("order_time"));

		
		TableColumn<String, Order> priceCol = new TableColumn<>("Price");
		priceCol.setMinWidth(100);
		priceCol.setCellValueFactory(new PropertyValueFactory<>("pizza_price"));

		
		TableColumn<String, Order> statusCol = new TableColumn<>("Status");
		statusCol.setMinWidth(100);
		statusCol.setCellValueFactory(new PropertyValueFactory<>("pizza_status"));

		

		
		orderTable.getColumns().addAll(customerIdCol, sizeCol, topping0Col,topping1Col,topping2Col, saucesCol, cheeseCol, timeCol, priceCol, statusCol);
		
		for (Map.Entry<Integer, Order> e : orderlist.entrySet()) { 
			orderTable.getItems().add(e.getValue());
		}
		
		
		stockTable.getColumns().addAll(IngredientsCol, QTYCol);
		for (Map.Entry<Integer, Topping> e : toppinglist.entrySet()) { 
			stockTable.getItems().add(e.getValue());
		}
		for (Map.Entry<Integer, Sauce> e : saucelist.entrySet()) { 
			stockTable.getItems().add(e.getValue());
		}
		for (Map.Entry<Integer, Cheese> e : cheeselist.entrySet()) { 
			stockTable.getItems().add(e.getValue());
		}



		final VBox stockbox = new VBox();
		stockbox.setSpacing(5);
		stockbox.setPadding(new Insets(10, 0, 0, 10));
		stockbox.getChildren().addAll(stock, stockTable, stockbtn);

		final VBox ordersbox = new VBox();
		ordersbox.setSpacing(5);
		ordersbox.setPadding(new Insets(10, 0, 0, 300));
		ordersbox.getChildren().addAll(orders, orderTable, orderbtn);

		((Group) scene.getRoot()).getChildren().addAll(ordersbox, stockbox);

		stage.setScene(scene);

		stage.show();
	}

	@Override
	public void start(Stage primaryStage) throws Exception {
		// TODO Auto-generated method stub
		
		Login(primaryStage);
		
	}



}

