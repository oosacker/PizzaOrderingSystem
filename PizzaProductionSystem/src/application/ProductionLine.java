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
import pizzaOrdering.Ingredient;
import pizzaOrdering.Order;
import pizzaOrdering.Sauce;
import pizzaOrdering.Topping;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;


public class ProductionLine extends Application {

	private TableView orderTable = new TableView();
	private TableView stockTable = new TableView();

	public static void main(String[] args) {
		launch(args);

	}

	@Override
	public void start(Stage stage) {

		//		String url = "jdbc:mysql://10.140.230.135:3306/pizza";
		//		String dbUser = "newuser";
		//		String usrPass = "12345";

		String url = "jdbc:mysql://localhost:3306/pizza";
		String dbUser = "newuser";
		String usrPass = "1234";

		DBInterface dbi = new DBInterface();
		dbi.openDB(url, dbUser, usrPass);

		HashMap<Integer, Order> orderlist = dbi.getAllOrders();
		HashMap<Integer, Customer> customerlist = dbi.getAllCustomers();
		HashMap<Integer, Topping> toppinglist = dbi.getAllToppings();
		HashMap<Integer, Cheese> cheeselist = dbi.getAllCheeses();
		HashMap<Integer, Sauce> saucelist = dbi.getAllSauces();


		Scene scene = new Scene(new Group());
		stage.setTitle("Production Line");
		stage.setWidth(1100);
		stage.setHeight(600);
		stage.initStyle(StageStyle.DECORATED);



		final Label stock = new Label("Stock");
		stock.setFont(new Font("Arial", 20));

		final Label orders = new Label("Orders");
		orders.setFont(new Font("Arial", 20));

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

		
		TableColumn<String, Order> sizegCol = new TableColumn<>("Size");
		sizegCol.setMinWidth(50);
		sizegCol.setCellValueFactory(new PropertyValueFactory<>("pizza_size"));

		
		TableColumn<String, Order> toppingCol = new TableColumn<>("topping");
		toppingCol.setMinWidth(100);
		toppingCol.setCellValueFactory(new PropertyValueFactory<>("name"));

		
		TableColumn<String, Order> saucesCol = new TableColumn<>("sauces");
		saucesCol.setMinWidth(100);
		saucesCol.setCellValueFactory(new PropertyValueFactory<>("name"));

		
		TableColumn<String, Order> cheeseCol = new TableColumn<>("Cheese");
		cheeseCol.setMinWidth(100);
		cheeseCol.setCellValueFactory(new PropertyValueFactory<>("name"));

		
		TableColumn<String, Order> timeCol = new TableColumn<>("Time");
		timeCol.setMinWidth(100);
		timeCol.setCellValueFactory(new PropertyValueFactory<>("Time"));

		
		TableColumn<String, Order> priceCol = new TableColumn<>("Price");
		priceCol.setMinWidth(100);
		priceCol.setCellValueFactory(new PropertyValueFactory<>("pizza_price"));

		
		TableColumn<String, Order> statusCol = new TableColumn<>("Status");
		statusCol.setMinWidth(100);
		statusCol.setCellValueFactory(new PropertyValueFactory<>("pizza_status"));

		
		orderTable.getColumns().add(customerIdCol);
		orderTable.getColumns().add(sizegCol);
		orderTable.getColumns().add(toppingCol);
		orderTable.getColumns().add(saucesCol);
		orderTable.getColumns().add(cheeseCol);
		orderTable.getColumns().add(timeCol);
		orderTable.getColumns().add(priceCol);
		orderTable.getColumns().add(statusCol);

		stockTable.getColumns().add(IngredientsCol);
		stockTable.getColumns().add(QTYCol);

		orderTable.getItems().add(orderlist.get(0));
		stockTable.getItems().add(toppinglist.get(0));

		orderTable.getColumns().addAll(customerIdCol, sizegCol, toppingCol,saucesCol, cheeseCol, timeCol,priceCol,statusCol, IngredientsCol, QTYCol);


		final VBox stockbox = new VBox();
		stockbox.setSpacing(5);
		stockbox.setPadding(new Insets(10, 0, 0, 10));
		stockbox.getChildren().addAll(stock, stockTable);

		final VBox ordersbox = new VBox();
		ordersbox.setSpacing(5);
		ordersbox.setPadding(new Insets(10, 0, 0, 300));
		ordersbox.getChildren().addAll(orders, orderTable);

		((Group) scene.getRoot()).getChildren().addAll(ordersbox, stockbox);

		stage.setScene(scene);

		stage.show();
	}

}
