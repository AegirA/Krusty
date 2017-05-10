import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;

public class ShowBlockPalletTab {

	Database db;
	TabPane tabPane;
	Tab blockPalletTab;
	GridPane gp;
	TextField blockPalletField;
	Button blockPalletButton;
	String palletNbr;
	
	public ShowBlockPalletTab(Database db, TabPane tabPane){
		this.db = db;
		this.tabPane=tabPane;
		gp = new GridPane();
		palletNbr = "";
	}

	public void fillTab(){
		

		blockPalletField = new TextField();
		blockPalletField.setPromptText("Pallet nbr");
		blockPalletButton = new Button("Block");
		
		
		gp.setVgap(5);
		gp.setHgap(5);
		gp.setPadding(new Insets(20, 20, 20, 20));
		gp.add(new Label ("Enter pallet nbr"), 0, 0);
		gp.add(blockPalletField, 1, 0);
		gp.add(blockPalletButton, 0, 1);
		
		Alert alert = new Alert(AlertType.ERROR);
		alert.setTitle("Error");
		alert.setHeaderText(null);
		alert.setContentText("Please type in a Pallet number");
		
		Alert confirmation = new Alert(AlertType.INFORMATION);
		confirmation.setTitle("Block successfull");
		confirmation.setHeaderText(null);
		
		
		
		
		blockPalletField.textProperty().addListener(new ChangeListener<String>() {

			@Override
			public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
				palletNbr = newValue;

			}

		});

		blockPalletButton.setOnAction((event) -> {
			if(palletNbr.equals("")){
				alert.setContentText("Please type in a Pallet number");
				alert.show();
				
			}else{
				if(db.blockPallet(palletNbr)){
					confirmation.setContentText("Pallet number " + palletNbr + " is blocked");
					confirmation.show();
				}else{
					alert.setContentText("The pallet number " + palletNbr + " is either not produced or is already shipped");
					alert.show();
				}
				
				
				
			}
			
			
			
		
			update();
		});
		
		
		
		  // Show Customer Tab
		  blockPalletTab = new Tab();
		  blockPalletTab.setText("Block Pallet");
		  
		
		 
		  gp.setAlignment(Pos.TOP_LEFT);
		  blockPalletTab.setContent(gp);
		  tabPane.getTabs().add(blockPalletTab);
		  
		
	}
	private void update(){
		blockPalletField.clear();
		palletNbr = "";
	}
	
}
