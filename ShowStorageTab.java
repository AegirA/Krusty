import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.layout.HBox;
import javafx.scene.text.Text;

public class ShowStorageTab {

	
	Database db;
	TabPane tabPane;
	Tab storageTab;
	HBox storageBox;
	
	public ShowStorageTab(Database db, TabPane tabPane ){
		this.db = db;
		this.tabPane=tabPane;
		storageBox = new HBox();
	}

	public void fillTab(){
		
		  // StorageTab
		  storageTab = new Tab();
		  storageTab.setText("Storage");
		  
		 fillList("RawMat");
		 fillList("AmountInStock");
		 fillList("InDelivAmount");
		 fillList("Unit");
		 fillList("InDelivDate");
		 
		
		 
		  storageBox.setAlignment(Pos.TOP_LEFT);
		  storageTab.setContent(storageBox);
		  tabPane.getTabs().add(storageTab);
		  
		
	}

	
	
	private void fillList(String cell){
		ListView<String> lw = new ListView<String>();
		lw.getItems().add(cell);
		lw.getItems().addAll(db.getStorage(cell));
		storageBox.getChildren().add(lw);
	}
	
	
}
