import java.util.ArrayList;
import java.util.List;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.layout.HBox;

public class ShowOrdersTab {
	
	Database db;
	TabPane tabPane;
	HBox newOrderBox;
	Tab newOrderTab;
	
	public ShowOrdersTab(Database db, TabPane tabPane){
		this.db = db;
		this.tabPane=tabPane;
		newOrderBox = new HBox();
	}

	public void fillTab(){
		
		  // Show Orders Tab
		  newOrderTab = new Tab();
		  newOrderTab.setText("Orders");
		  
		  
		
		 fillList("Name");
		 fillList("OrderNbr");
		 fillList("OrderDate");
		 fillList("DelDate");
		 fillList("OutDelivDate");
		 fillList("OutDelivTime");
		 fillList("ArrivalDate");
		 fillList("ArrivalTime");
		 
		 
		 
		  newOrderBox.setAlignment(Pos.TOP_LEFT);
		  newOrderTab.setContent(newOrderBox);
		  tabPane.getTabs().add(newOrderTab);
		  
		
	}

	
	
	private void fillList(String cell){
		ListView<String> lw = new ListView<String>();
		lw.getItems().add(cell);
		lw.getItems().addAll(db.getOrders(cell));
		newOrderBox.getChildren().add(lw);
	}
	

	
	
}
