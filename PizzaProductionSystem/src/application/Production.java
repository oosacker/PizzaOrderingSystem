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

	HashMap<Integer, Order> orderMap = new HashMap<>();
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

		orderMap = dbi.getAllOrdersMap();
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

		Stage stage = new Stage();

		Scene scene = new Scene(new Group());
		stage.setTitle("Production Line");
		stage.setWidth(1850);
		stage.setHeight(700);
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
			
			// update the database info
			dbi.updateAllToppings();
			dbi.updateAllCheeses();
			dbi.updateAllSauces();
			
			// update the tableview
			updateStockTable();
			
		});

		Button orderbtn = new Button();
		orderbtn.setText("Fetch new orders");
		orderbtn.setTextFill(Color.RED);
		
		orderbtn.setOnAction(e->{
			// 
			updateOrderTable();	
		});
		
		orderbtn.setLayoutX(20);
		orderbtn.setLayoutY(100);

		orderTable.setEditable(true);
		stockTable.setEditable(true);


		TableColumn<String, Ingredient> IngredientsCol = new TableColumn<>("Ingredients");
		IngredientsCol.setMinWidth(150);
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
                    
                }
            });
            return row ;
        });
		
		TableColumn<String, Order> corderIdCol = new TableColumn<>("Order ID");
		corderIdCol.setMinWidth(80);
		corderIdCol.setCellValueFactory(new PropertyValueFactory<>("order_id"));
		
		TableColumn<String, Order> customerIdCol = new TableColumn<>("Customer phone");
		customerIdCol.setMinWidth(150);
		customerIdCol.setCellValueFactory(new PropertyValueFactory<>("customer_phone"));


		TableColumn<String, Order> sizeCol = new TableColumn<>("Size");
		sizeCol.setMinWidth(100);
		sizeCol.setCellValueFactory(new PropertyValueFactory<>("pizza_size"));


		TableColumn<String, Order> topping0Col = new TableColumn<>("Topping 0");
		topping0Col.setMinWidth(150);
		topping0Col.setCellValueFactory(new PropertyValueFactory<>("pizza_topping_0"));

		TableColumn<String, Order> topping1Col = new TableColumn<>("Topping 1");
		topping1Col.setMinWidth(150);
		topping1Col.setCellValueFactory(new PropertyValueFactory<>("pizza_topping_1"));

		TableColumn<String, Order> topping2Col = new TableColumn<>("Topping 2");
		topping2Col.setMinWidth(150);
		topping2Col.setCellValueFactory(new PropertyValueFactory<>("pizza_topping_2"));


		TableColumn<String, Order> saucesCol = new TableColumn<>("Sauce");
		saucesCol.setMinWidth(150);
		saucesCol.setCellValueFactory(new PropertyValueFactory<>("pizza_sauce_0"));


		TableColumn<String, Order> cheeseCol = new TableColumn<>("Cheese");
		cheeseCol.setMinWidth(150);
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
                }
            });
            return row;
        });
		


		orderTable.getColumns().addAll(corderIdCol, customerIdCol, sizeCol, topping0Col, topping1Col, topping2Col, saucesCol, cheeseCol, timeCol, priceCol, statusCol);
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

		stockTable.getItems().clear();

		toppingMap = dbi.getAllToppingsMap();
		cheeseMap = dbi.getAllCheesesMap();
		sauceMap = dbi.getAllSaucesMap();
		
		for (Map.Entry<String, Topping> e : toppingMap.entrySet()) { 
			stockTable.getItems().add(e.getValue());
		}
		
		for (Map.Entry<String, Sauce> e : sauceMap.entrySet()) { 
			stockTable.getItems().add(e.getValue());
		}
		
		for (Map.Entry<String, Cheese> e : cheeseMap.entrySet()) { 
			stockTable.getItems().add(e.getValue());
		}
		
	}
	
	@SuppressWarnings("unchecked")
	private void updateOrderTable() {
		
		orderTable.getItems().clear();
		orderMap = dbi.getAllOrdersMap();
		
		for (Map.Entry<Integer, Order> e : orderMap.entrySet()) { 
			orderTable.getItems().add(e.getValue());
		}

	}

	
	private void restockIngredient(Ingredient ing) {
		
		String ingredient_name = ing.getName();
				
		if ( ing instanceof Topping ) {
			toppingMap.get(ingredient_name).setStock_level(100);
			dbi.updateIngredient(ing);
			
		}
		
		else if( ing instanceof Sauce ) {
			sauceMap.get(ingredient_name).setStock_level(100);
			dbi.updateIngredient(ing);
		}
		
		else if( ing instanceof Cheese ) {
			cheeseMap.get(ingredient_name).setStock_level(100);
			dbi.updateIngredient(ing);
		}
		
		updateTables();
	}
	
	private void updateOrderState(Order ord) {
		
		String current_status = ord.getPizza_status();
		
		// only consume ingredients first time!!!
		if(current_status.equalsIgnoreCase("Waiting")) {
			consumeIngredients(ord);
		}
		
		ord.changePizzaStatus();
		dbi.updateOrderState(ord, ord.getPizza_status());

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
		
		// pizza size = large then drops by 3 units, medium = 3, small = 1
		String pizza_size = ord.getPizza_size();
		
		dbi.consumeIngredient(topping_0, pizza_size);
		dbi.consumeIngredient(topping_1, pizza_size);
		dbi.consumeIngredient(topping_2, pizza_size);
		dbi.consumeIngredient(sauce_0, pizza_size);
		dbi.consumeIngredient(cheese_0, pizza_size);
		
		toppingMap = dbi.getAllToppingsMap();
		cheeseMap = dbi.getAllCheesesMap();
		sauceMap = dbi.getAllSaucesMap();
		
	}
	
	@Override
	public void start(Stage primaryStage) throws Exception {
		Login(primaryStage);

	}



}

