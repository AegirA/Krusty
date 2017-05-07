	
import java.util.ArrayList;
import java.util.List;

import javafx.beans.InvalidationListener;
import javafx.beans.Observable;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.ScrollBar;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.layout.HBox;
	
public class ShowPalletsTab {


		
		Database db;
		TabPane tabPane;
		HBox newPalletsBox;
		Tab newPalletsTab;
		ListView <String> palletNbrList;
		ListView <String> orderNbrList;
		ListView <String> prodDateList;
		ListView <String> prodTimeList;
		ListView <String> blockedList;
		ListView <String> productNameList;
		ScrollBar sb1;
		ScrollBar sb2;
		ScrollBar sb3;
		ScrollBar sb4;
		ScrollBar sb5;
		ScrollBar sb6;
		
		
		
		public ShowPalletsTab(Database db, TabPane tabPane){
			this.db = db;
			this.tabPane=tabPane;
			newPalletsBox = new HBox();
			newPalletsTab = new Tab();
			newPalletsTab.setText("Show Pallets in stock");
			
			
		}

		public void fillTab(){
			
			  // Show Pallets Tab
			
			tabPane.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<Tab>(){

				@Override
				public void changed(ObservableValue<? extends Tab> observable, Tab oldValue, Tab newValue) {
					newPalletsBox.getChildren().clear();
					update();
					
				}
				
				
				
			});
			
			palletNbrList = new ListView<String>();
			orderNbrList = new ListView<String>();
			prodDateList = new ListView<String>();
			prodTimeList = new ListView<String>();
			blockedList = new ListView<String>();
			productNameList = new ListView<String>();
			
			
			
			 fillList("PalletNbr", palletNbrList);
			 fillList("ProductName", productNameList);
			 fillList("OrderNbr", orderNbrList);
			 fillList("ProdDate", prodDateList);
			 fillList("ProdTime", prodTimeList);
			 fillList("Blocked", blockedList);
			 
			
			 
			 newPalletsBox.setAlignment(Pos.TOP_LEFT);
			 newPalletsTab.setContent(newPalletsBox);
			 tabPane.getTabs().add(newPalletsTab);
			  
			 
			
		}
		
		private void update(){
			fillList("PalletNbr", palletNbrList);
			fillList("ProductName", productNameList);
			fillList("OrderNbr", orderNbrList);
			fillList("ProdDate", prodDateList);
			fillList("ProdTime", prodTimeList);
			fillList("Blocked", blockedList);
		}
		public void lockScrollBars(){

			sb1 = (ScrollBar) palletNbrList.lookup(".scroll-bar:vertical");
			sb2 = (ScrollBar) orderNbrList.lookup(".scroll-bar:vertical");
			sb3 = (ScrollBar) prodDateList.lookup(".scroll-bar:vertical");
			sb4 = (ScrollBar) prodTimeList.lookup(".scroll-bar:vertical");
			sb5 = (ScrollBar) blockedList.lookup(".scroll-bar:vertical");
			sb6 = (ScrollBar) productNameList.lookup(".scroll-bar:vertical");
			
			
			sb1.valueProperty().bindBidirectional(sb2.valueProperty());
			sb1.valueProperty().bindBidirectional(sb3.valueProperty());
			sb1.valueProperty().bindBidirectional(sb4.valueProperty());
			sb1.valueProperty().bindBidirectional(sb5.valueProperty());
			sb1.valueProperty().bindBidirectional(sb6.valueProperty());
			
			
		}
		private void fillList(String cell, ListView<String> lw){
			lw.getItems().clear();
			lw.getItems().add(cell);
			lw.getItems().addAll(db.getPallets(cell));
			newPalletsBox.getChildren().add(lw);
		}

		
	

}
