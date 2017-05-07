import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.geometry.Pos;
import javafx.scene.control.ListView;
import javafx.scene.control.ScrollBar;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.layout.HBox;

public class ShowTrackPalletTab {

	Database db;
	TabPane tabPane;
	HBox trackPalletBox;
	Tab trackPalletTab;
	ListView <String> palletNbrList;
	ListView <String> blockedList;
	ListView <String> prodDateList;
	ListView <String> prodTimeList;
	ListView <String> outDelivDateList;
	ListView <String> outDelivTimeList;
	ListView <String> arrivalDateList;
	ListView <String> arrivalTimeList;
	ListView <String> productName;
	ScrollBar sb1;
	ScrollBar sb2;
	ScrollBar sb3;
	ScrollBar sb4;
	ScrollBar sb5;
	ScrollBar sb6;
	ScrollBar sb7;
	ScrollBar sb8;
	
	ListView <String> productNameList;

	public ShowTrackPalletTab(Database db, TabPane tabPane) {
		this.db = db;
		this.tabPane = tabPane;
		trackPalletBox = new HBox();
		trackPalletTab = new Tab();
		trackPalletTab.setText("Track pallet");
	}

	public void fillTab() {

		// Show Track Pallet Tab
		
		
		tabPane.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<Tab>(){

			@Override
			public void changed(ObservableValue<? extends Tab> observable, Tab oldValue, Tab newValue) {
				trackPalletBox.getChildren().clear();
				update();
				
			}	
			
		});
		
		
		palletNbrList = new ListView<String>();
		blockedList = new ListView<String>();
		productNameList = new ListView<String>();
		prodDateList = new ListView<String>();
		prodTimeList = new ListView<String>();
		outDelivDateList = new ListView<String>();
		outDelivTimeList = new ListView<String>();
		arrivalDateList = new ListView<String>();
		arrivalTimeList = new ListView<String>();

		fillList("PalletNbr", palletNbrList);
		fillList("Blocked", blockedList);
		fillList("ProductName", productNameList);
		fillList("ProdDate", prodDateList);
		fillList("ProdTime", prodTimeList);
		fillList("OutDelivDate", outDelivDateList);
		fillList("OutDelivTime", outDelivTimeList);
		fillList("ArrivalDate", arrivalDateList);
		fillList("ArrivalTime", arrivalTimeList);


		trackPalletBox.setAlignment(Pos.TOP_LEFT);
		trackPalletTab.setContent(trackPalletBox);
		tabPane.getTabs().add(trackPalletTab);

	}
	
	private void update(){
		fillList("PalletNbr", palletNbrList);
		fillList("Blocked", blockedList);
		fillList("ProductName", productNameList);
		fillList("ProdDate", prodDateList);
		fillList("ProdTime", prodTimeList);
		fillList("OutDelivDate", outDelivDateList);
		fillList("OutDelivTime", outDelivTimeList);
		fillList("ArrivalDate", arrivalDateList);
		fillList("ArrivalTime", arrivalTimeList);
		
	}
	
	public void lockScrollBars(){

		sb1 = (ScrollBar) palletNbrList.lookup(".scroll-bar:vertical");
		sb2 = (ScrollBar) blockedList.lookup(".scroll-bar:vertical");
		sb3 = (ScrollBar) prodDateList.lookup(".scroll-bar:vertical");
		sb4 = (ScrollBar) prodTimeList.lookup(".scroll-bar:vertical");
		sb5 = (ScrollBar) outDelivDateList.lookup(".scroll-bar:vertical");
		sb6 = (ScrollBar) outDelivTimeList.lookup(".scroll-bar:vertical");
		sb7 = (ScrollBar) arrivalDateList.lookup(".scroll-bar:vertical");
		sb8 = (ScrollBar) arrivalTimeList.lookup(".scroll-bar:vertical");
		
		
		sb1.valueProperty().bindBidirectional(sb2.valueProperty());
		sb1.valueProperty().bindBidirectional(sb3.valueProperty());
		sb1.valueProperty().bindBidirectional(sb4.valueProperty());
		sb1.valueProperty().bindBidirectional(sb5.valueProperty());
		sb1.valueProperty().bindBidirectional(sb6.valueProperty());
		sb1.valueProperty().bindBidirectional(sb7.valueProperty());
		sb1.valueProperty().bindBidirectional(sb8.valueProperty());
		
	}

	private void fillList(String cell, ListView<String> lw) {
		lw.getItems().clear();
		lw.getItems().add(cell);
		lw.getItems().addAll(db.getTrack(cell));
		trackPalletBox.getChildren().add(lw);
	}

}
