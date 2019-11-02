package application;
	
import java.util.ArrayList;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import pizzaOrdering.Cheese;
import pizzaOrdering.Order;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
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
        Scene scene = new Scene(new Group());
        stage.setTitle("Prodiction Line");
        stage.setWidth(1100);
        stage.setHeight(600);
        stage.initStyle(StageStyle.DECORATED);
//        stage.setX(200);
//        stage.setY(50);
        

        final Label stoke = new Label("Stoke");
        stoke.setFont(new Font("Arial", 20));
        
        final Label orders = new Label("Orders");
        orders.setFont(new Font("Arial", 20));
 
        orderTable.setEditable(true);
        stokeTable.setEditable(true);
//        buyCol.setVisible(false);
        
//        TableColumn ingredientsCol = new TableColumn("Ingredients");
//        ingredientsCol.setMinWidth(100);
//        ingredientsCol.setCellValueFactory(
//                new PropertyValueFactory<>("Ingredients"));
//        
//        TableColumn QTYCol = new TableColumn("QTY");
//        QTYCol.setMinWidth(100);
//        QTYCol.setCellValueFactory(
//                new PropertyValueFactory<>("QTY"));
//        
//      
//      TableColumn toppingCol = new TableColumn("topping");
//      toppingCol.setMinWidth(100);
//      toppingCol.setCellValueFactory(
//              new PropertyValueFactory<>("topping"));
//      
//      TableColumn saucesCol = new TableColumn("sauces");
//      saucesCol.setMinWidth(100);
//      saucesCol.setCellValueFactory(
//              new PropertyValueFactory<>("sauces"));
//        
//      TableColumn customerIdCol = new TableColumn("Customer id");
//      customerIdCol.setMinWidth(100);
//      customerIdCol.setCellValueFactory(
//              new PropertyValueFactory<>("Customer id"));
       
//        table.getColumns().addAll(ingredientsCol, QTYCol);
//   
        
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
        
        TableColumn<String, OrderInfo> toppingCol = new TableColumn<>("topping");
        toppingCol.setMinWidth(100);
        toppingCol.setCellValueFactory(new PropertyValueFactory<>("topping"));

        TableColumn<String, OrderInfo> saucesCol = new TableColumn<>("sauces");
        saucesCol.setMinWidth(100);
        saucesCol.setCellValueFactory(new PropertyValueFactory<>("sauces"));

        TableColumn<String, Cheese> cheeseCol = new TableColumn<>("Cheese");
        cheeseCol.setMinWidth(100);
        cheeseCol.setCellValueFactory(new PropertyValueFactory<>("Cheese"));
        
        TableColumn<String, OrderInfo> timeCol = new TableColumn<>("Time");
        timeCol.setMinWidth(100);
        timeCol.setCellValueFactory(new PropertyValueFactory<>("Time"));
        
        TableColumn<String, OrderInfo> priceCol = new TableColumn<>("Price");
        priceCol.setMinWidth(100);
        priceCol.setCellValueFactory(new PropertyValueFactory<>("Price"));
        
        TableColumn<String, OrderInfo> statusCol = new TableColumn<>("Status");
        statusCol.setMinWidth(100);
        statusCol.setCellValueFactory(new PropertyValueFactory<>("Status"));

        orderTable.getColumns().add(customerIdCol);
        orderTable.getColumns().add(sizegCol);
        orderTable.getColumns().add(toppingCol);
        orderTable.getColumns().add(saucesCol);
        orderTable.getColumns().add(cheeseCol);
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
        orderTable.getColumns().addAll(customerIdCol, sizegCol, toppingCol,saucesCol, cheeseCol, timeCol,priceCol,statusCol, IngredientsCol, QTYCol);
 
// 
//      stoke vBox
        final VBox stokebox = new VBox();
        stokebox.setSpacing(5);
        stokebox.setPadding(new Insets(10, 0, 0, 10));
        stokebox.getChildren().addAll(stoke, stokeTable);
        
//      orders vBox
        final VBox ordersbox = new VBox();
        ordersbox.setSpacing(5);
        ordersbox.setPadding(new Insets(10, 0, 0, 300));
        ordersbox.getChildren().addAll(orders, orderTable);
 
        ((Group) scene.getRoot()).getChildren().addAll(ordersbox, stokebox);
 
        stage.setScene(scene);
//        stage.setFullScreen(true);
        stage.show();
    }

}

