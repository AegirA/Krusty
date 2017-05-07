import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.layout.HBox;
import javafx.scene.text.Text;

public class ShowCustomersTab {
	Database db;
	TabPane tabPane;
	Tab customerTab;
	HBox customerBox;
	
	public ShowCustomersTab(Database db, TabPane tabPane){
		this.db = db;
		this.tabPane=tabPane;
		customerBox = new HBox();
	}

	public void fillTab(){
		
		  // Show Customer Tab
		  customerTab = new Tab();
		  customerTab.setText("Customers");
		  
		 fillList("Name");
		 fillList("City");
		
		 
		  customerBox.setAlignment(Pos.TOP_LEFT);
		  customerTab.setContent(customerBox);
		  tabPane.getTabs().add(customerTab);
		  
		
	}

	
	
	private void fillList(String cell){
		ListView<String> lw = new ListView<String>();
		lw.getItems().add(cell);
		lw.getItems().addAll(db.getCustomers(cell));
		customerBox.getChildren().add(lw);
	}
		
	
}
