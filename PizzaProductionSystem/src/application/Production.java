package application;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import javafx.application.Application;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import pizzaOrdering.Cheese;
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
import javafx.scene.control.TableRow;
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

//	private ObservableList<Order> orderlist;
//	private ObservableList<Topping> toppinglist;
//	private ObservableList<Cheese> cheeselist;
//	private ObservableList<Sauce> saucelist;
	
	private ArrayList<Order> orderlist;
	private ArrayList<Topping> toppinglist;
	private ArrayList<Cheese> cheeselist;
	private ArrayList<Sauce> saucelist;
	
	//HashMap<String, Order> orderMap = new HashMap<>();
	HashMap<String, Topping> toppingMap = new HashMap<>();
	HashMap<String, Sauce> sauceMap = new HashMap<>();
	HashMap<String, Cheese> cheeseMap = new HashMap<>();

	private static DBInterface dbi = new DBInterface();

	@SuppressWarnings("rawtypes")
	private TableView orderTable = new TableView();
	@SuppressWarnings("rawtypes")
	private TableView stockTable = new TableView();


	public static void main(String[] args) {
		launch(args);
	}


	public void Login(Stage primaryStage) throws Exception {
		dbi = new DBInterface();
		
		orderlist = dbi.getAllOrders();
		
		System.out.println("login "+orderlist);
		
		toppinglist = dbi.getAllToppings();
		cheeselist = dbi.getAllCheeses();
		saucelist = dbi.getAllSauces();
		

		toppingMap = dbi.getAllToppingsMap();
		cheeseMap = dbi.getAllCheesesMap();
		sauceMap = dbi.getAllSaucesMap();
		
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

		Text actiontarget = new Text();
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
					primaryStage.close();
					
				}

			}
		});

		Scene scene = new Scene(grid, 400, 400);
		primaryStage.setScene(scene);
		primaryStage.show();
	}



	@SuppressWarnings("unchecked")
	public void start() {
		//System.out.println("sart "+saucelist);
		
		
		Stage stage = new Stage();

		Scene scene = new Scene(new Group());
		stage.setTitle("Production Line");
		stage.setWidth(1500);
		stage.setHeight(600);
		stage.initStyle(StageStyle.DECORATED);

		Label stock = new Label("Stock");
		stock.setFont(new Font("Arial", 20));

		Label orders = new Label("Orders");
		orders.setFont(new Font("Arial", 20));

		//      Stock update button
		Button stockbtn = new Button();
		stockbtn.setText("Replenish all");
		stockbtn.setTextFill(Color.RED);
		stockbtn.setLayoutX(20);
		stockbtn.setLayoutY(100);
		stockbtn.setOnAction(e->{
			dbi.updateAllToppings();
			dbi.updateAllCheeses();
			dbi.updateAllSauces();
			updateStockTable();
			
		});

		//      Order update button
		Button orderbtn = new Button();
		orderbtn.setText("Clear completed orders");
		orderbtn.setTextFill(Color.RED);
		
		orderbtn.setOnAction(e->{
			updateOrderTable();
			System.out.println("clicked");
		});
		
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


		stockTable.setRowFactory(tv -> {
            TableRow<Ingredient> row = new TableRow<>();
            row.setOnMouseClicked(event -> {
                if (event.getClickCount() == 2 && (! row.isEmpty()) ) {
                    Ingredient rowData = row.getItem();
                    
                    restockIngredient(rowData);
                    
                    System.out.println("Double click on: "+rowData.toString());
                }
            });
            return row ;
        });
		
		
		
		TableColumn<String, Order> customerIdCol = new TableColumn<>("Customer phone");
		customerIdCol.setMinWidth(150);
		customerIdCol.setCellValueFactory(new PropertyValueFactory<>("customer_phone"));


		TableColumn<String, Order> sizeCol = new TableColumn<>("Size");
		sizeCol.setMinWidth(100);
		sizeCol.setCellValueFactory(new PropertyValueFactory<>("pizza_size"));


		TableColumn<String, Order> topping0Col = new TableColumn<>("Topping 0");
		topping0Col.setMinWidth(100);
		topping0Col.setCellValueFactory(new PropertyValueFactory<>("pizza_topping_0"));

		TableColumn<String, Order> topping1Col = new TableColumn<>("Topping 1");
		topping1Col.setMinWidth(100);
		topping1Col.setCellValueFactory(new PropertyValueFactory<>("pizza_topping_1"));

		TableColumn<String, Order> topping2Col = new TableColumn<>("Topping 2");
		topping2Col.setMinWidth(100);
		topping2Col.setCellValueFactory(new PropertyValueFactory<>("pizza_topping_2"));


		TableColumn<String, Order> saucesCol = new TableColumn<>("Sauces");
		saucesCol.setMinWidth(100);
		saucesCol.setCellValueFactory(new PropertyValueFactory<>("pizza_sauce_0"));


		TableColumn<String, Order> cheeseCol = new TableColumn<>("Cheese");
		cheeseCol.setMinWidth(100);
		cheeseCol.setCellValueFactory(new PropertyValueFactory<>("pizza_cheese_0"));


		TableColumn<String, Order> timeCol = new TableColumn<>("Time");
		timeCol.setMinWidth(200);
		timeCol.setCellValueFactory(new PropertyValueFactory<>("order_time"));


		TableColumn<String, Order> priceCol = new TableColumn<>("Price");
		priceCol.setMinWidth(100);
		priceCol.setCellValueFactory(new PropertyValueFactory<>("pizza_price"));


		TableColumn<String, Order> statusCol = new TableColumn<>("Status");
		statusCol.setMinWidth(100);
		statusCol.setCellValueFactory(new PropertyValueFactory<>("pizza_status"));


		orderTable.setRowFactory(tv -> {
            TableRow<Order> row = new TableRow<>();
            row.setOnMouseClicked(event -> {
                if (event.getClickCount() == 2 && (! row.isEmpty()) ) {
                	Order rowData = row.getItem();
                	
                	
                	
                	
                	updateOrderState(rowData);
                	
                	
                	
                	
                    System.out.println("Double click on: "+rowData.toString());
                }
            });
            return row;
        });
		


		orderTable.getColumns().addAll(customerIdCol, sizeCol, topping0Col, topping1Col, topping2Col, saucesCol, cheeseCol, timeCol, priceCol, statusCol);
		stockTable.getColumns().addAll(IngredientsCol, QTYCol);
		
		updateTables();


		VBox stockbox = new VBox();
		stockbox.setSpacing(5);
		stockbox.setPadding(new Insets(10, 0, 0, 10));
		stockbox.getChildren().addAll(stock, stockTable, stockbtn);

		VBox ordersbox = new VBox();
		ordersbox.setSpacing(5);
		ordersbox.setPadding(new Insets(10, 0, 0, 300));
		ordersbox.getChildren().addAll(orders, orderTable, orderbtn);

		((Group) scene.getRoot()).getChildren().addAll(ordersbox, stockbox);

		stage.setScene(scene);
		stage.show();
	}

	private void updateTables() {
		updateStockTable();
		updateOrderTable();
	}
	
	@SuppressWarnings("unchecked")
	private void updateStockTable() {

		
		toppinglist = dbi.getAllToppings();
		cheeselist = dbi.getAllCheeses();
		saucelist = dbi.getAllSauces();
		
		stockTable.getItems().clear();
		
		System.out.println(toppinglist);
		stockTable.getItems().addAll(toppinglist);
		
		System.out.println(cheeselist);
		stockTable.getItems().addAll(cheeselist);
		
		System.out.println(saucelist);
		stockTable.getItems().addAll(saucelist);
//		for (Map.Entry<Integer, Topping> e : toppinglist.entrySet()) { 
//			stockTable.getItems().add(e.getValue());
//		}
//		for (Map.Entry<Integer, Sauce> e : saucelist.entrySet()) { 
//			stockTable.getItems().add(e.getValue());
//		}
//		for (Map.Entry<Integer, Cheese> e : cheeselist.entrySet()) { 
//			stockTable.getItems().add(e.getValue());
//		}
	}
	
	@SuppressWarnings("unchecked")
	private void updateOrderTable() {
		orderTable.getItems().clear();
		orderTable.getItems().addAll(orderlist);
//		for (Map.Entry<Integer, Order> e : orderlist.entrySet()) { 
//			orderTable.getItems().add(e.getValue());
//		}
	}

	
	private void restockIngredient(Ingredient ing) {
		int ingredient_id = ing.getId();
				
		if ( ing instanceof Topping ) {
			toppinglist.get(ingredient_id).setStock_level(100);
			dbi.updateIngredient(ing);
			
		}
		
		else if( ing instanceof Sauce ) {
			System.out.println("instanceof "+saucelist);
			
			saucelist.get(ingredient_id).setStock_level(100);
			dbi.updateIngredient(ing);
		}
		
		else if( ing instanceof Cheese ) {
			cheeselist.get(ingredient_id).setStock_level(100);
			dbi.updateIngredient(ing);
		}
		
		updateTables();
	}
	
	private void updateOrderState(Order ord) {

		
		int index = orderlist.indexOf(ord);
		orderlist.get(index).changePizzaStatus();
		dbi.updateOrderState(ord, orderlist.get(index).getPizza_status());
		consumeIngredients(ord);
		updateTables();
	}
	
	private void consumeIngredients(Order ord) {
		
		if(ord == null) {
			System.out.println("null order!!!!!");
		}
		
		Topping topping_0 = toppingMap.get(ord.getPizza_topping_0());
		Topping topping_1 = toppingMap.get(ord.getPizza_topping_1());
		Topping topping_2 = toppingMap.get(ord.getPizza_topping_2());
		Sauce sauce_0 = sauceMap.get(ord.getPizza_sauce_0());
		Cheese cheese_0 = cheeseMap.get(ord.getPizza_cheese_0());
		
		
		
		dbi.consumeIngredient(topping_0);
		dbi.consumeIngredient(topping_1);
		dbi.consumeIngredient(topping_2);
		dbi.consumeIngredient(sauce_0);
		dbi.consumeIngredient(cheese_0);
		
		toppinglist = dbi.getAllToppings();
		cheeselist = dbi.getAllCheeses();
		saucelist = dbi.getAllSauces();
		
		toppingMap = dbi.getAllToppingsMap();
		cheeseMap = dbi.getAllCheesesMap();
		sauceMap = dbi.getAllSaucesMap();
		
	}
	
	@Override
	public void start(Stage primaryStage) throws Exception {
		// TODO Auto-generated method stub

		Login(primaryStage);

	}



}

