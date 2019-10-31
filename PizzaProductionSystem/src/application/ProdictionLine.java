package application;
	
import java.util.ArrayList;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.stage.Stage;
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
	
//	private ArrayList <Ingredient> ingredients = new ArrayList <Ingredient> ();

	
	private TableView table = new TableView();
	
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
	
	
//	public void addIng (Ingredient g) {
//		ingredients.add(g);
//	}
	
	
	@Override
    public void start(Stage stage) {
        Scene scene = new Scene(new Group());
        stage.setTitle("Prodiction Line");
        stage.setWidth(600);
        stage.setHeight(500);
 
        final Label label = new Label("Stoke");
        label.setFont(new Font("Arial", 20));
 
        table.setEditable(true);
//        buyCol.setVisible(false);
        
        TableColumn ingredientsCol = new TableColumn("Ingredients");
        ingredientsCol.setMinWidth(100);
        ingredientsCol.setCellValueFactory(
                new PropertyValueFactory<Ingredient, String>("Ingredients"));
        
        TableColumn QTYCol = new TableColumn("QTY");
        QTYCol.setMinWidth(100);
        QTYCol.setCellValueFactory(
                new PropertyValueFactory<Ingredient, String>("QTY"));
        
       
//        table.getColumns().addAll(ingredientsCol, QTYCol);
        
//        TableColumn ingredientsCol = new TableColumn("Ingredients");
//        TableColumn QTYCol = new TableColumn("QTY");
//        TableColumn buyCol = new TableColumn("Buy");
     
//        table.setItems(data);
        table.getColumns().addAll(ingredientsCol, QTYCol);
      
 
        final VBox vbox = new VBox();
        vbox.setSpacing(5);
        vbox.setPadding(new Insets(10, 0, 0, 10));
        vbox.getChildren().addAll(label, table);
 
        ((Group) scene.getRoot()).getChildren().addAll(vbox);
 
        stage.setScene(scene);
        stage.show();
    }

}

