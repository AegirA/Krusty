import java.util.ArrayList;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.text.Text;

public class ShowNewOrderTab {

	Database db;
	TabPane tabPane;
	Group root;
	String selectedProduct;
	String selectedOrderNbr;
	String nbrOfPallets;
	GUI gui;
	Alert alert;
	Alert confirmation;
	ArrayList<String> products;
	ArrayList<String> orderNbrs;
	ComboBox<String> orderCombo;
	ComboBox<String> productsCombo;
	Button okButton;
	TextField amountField;
	GridPane gp;
	Tab orderTab;
	Label company;

	public ShowNewOrderTab(Database db, TabPane tabPane, Group root, GUI gui) {

		this.db = db;
		this.tabPane = tabPane;
		this.root = root;
		selectedProduct = "";
		selectedOrderNbr = "";
		nbrOfPallets = "0";
		this.gui = gui;
		alert = new Alert(AlertType.ERROR);
		alert.setTitle("Production error");
		alert.setHeaderText(null);
		alert.setContentText("Fill upp the warehouse before trying again!");
		confirmation = new Alert(AlertType.INFORMATION);
		confirmation.setTitle("Order placed");
		confirmation.setHeaderText(null);
		confirmation.setContentText("Order placed, click ok to continue");
		orderTab = new Tab();
		orderTab.setText("New Order");

	}

	public void fillTab() {

		// Orders Tab

		orderCombo = new ComboBox<String>();
		productsCombo = new ComboBox<String>();
		okButton = new Button("Ok");
		amountField = new TextField();
		gp = new GridPane();
		company = new Label("");

		gp.setVgap(4);
		gp.setHgap(10);
		gp.setPadding(new Insets(5, 5, 5, 5));
		gp.add(new Label("Product: "), 0, 2);
		gp.add(productsCombo, 1, 2);
		gp.add(new Label("Ordernumber: "), 0, 3);
		gp.add(orderCombo, 1, 3);
		gp.add(company, 2, 3);
		gp.add(new Label("Number of pallets: "), 0, 4);
		gp.add(amountField, 1, 4);
		gp.add(okButton, 0, 5);

		products = db.getProducts();
		orderNbrs = db.getOrderNbrs();

		for (int i = 0; i < orderNbrs.size(); i++) {
			orderCombo.getItems().add(orderNbrs.get(i));
		}

		for (int k = 0; k < products.size(); k++) {
			productsCombo.getItems().add(products.get(k));
		}

		productsCombo.setPromptText("Select product");
		productsCombo.valueProperty().addListener(new ChangeListener<String>() {
			@Override
			public void changed(ObservableValue observable, String oldValue, String newValue) {
				selectedProduct = newValue;
			}

		});

		orderCombo.setPromptText("Select order number");
		orderCombo.valueProperty().addListener(new ChangeListener<String>() {
			@Override
			public void changed(ObservableValue observable, String oldValue, String newValue) {
				selectedOrderNbr = newValue;
				company.setText("Selected company: " + db.getCustomerName(newValue));
			}
		});

		amountField.setPromptText("Amount");
		amountField.textProperty().addListener(new ChangeListener<String>() {

			@Override
			public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
				nbrOfPallets = newValue;

			}

		});

		okButton.setOnAction((event) -> {
			GetDate gd = new GetDate();
			
			if (selectedProduct.equals("")) {
				alert.setContentText("No product selected, select a product and try again");
				alert.show();
				

			} else if (nbrOfPallets.equals("0")) {
				alert.setContentText("No amount, type in a value and try again");
				alert.show();
				
			}else{
				int nbrInStock = db.getPalletsInStock(selectedProduct);
				if (selectedOrderNbr.equals("")) {
					for (int k = 0; k < Integer.parseInt(nbrOfPallets); k++) {
						if (!db.producePallet(selectedProduct, gd.getDateFormat(), gd.getTimeFormat())) {
							alert.setContentText("There is not enough rawmaterials in stock, fill upp the warehouse before trying again!");
							alert.show();

						} else {
							confirmation.show();
						}

					}
				} else if (nbrInStock < Integer.parseInt(nbrOfPallets) && !selectedOrderNbr.equals("")) {
					int times = (Integer.parseInt(nbrOfPallets) - nbrInStock);
					for (int i = 0; i < times; i++) {
						if (!db.producePallet(selectedProduct, gd.getDateFormat(), gd.getTimeFormat())) {
							alert.setContentText("There is not enough rawmaterials in stock, fill upp the warehouse before trying again!");
							alert.show();
						} else {
							confirmation.show();
						}
					}
					db.reservPallets(selectedOrderNbr, selectedProduct, nbrOfPallets);
					confirmation.show();
				} else {
					db.reservPallets(selectedOrderNbr, selectedProduct, nbrOfPallets);
					confirmation.show();
				}
			}
			
			
			update();
		});

		root.getChildren().add(gp);
		orderTab.setContent(gp);
		tabPane.getTabs().add(orderTab);

	}

	public void update() {
		products.clear();
		orderNbrs.clear();
		orderCombo.getItems().clear();
		productsCombo.getItems().clear();
		amountField.clear();
		selectedProduct = "";
		selectedOrderNbr = "";
		nbrOfPallets = "0";
		

		products = db.getProducts();
		orderNbrs = db.getOrderNbrs();

		for (int i = 0; i < orderNbrs.size(); i++) {
			orderCombo.getItems().add(orderNbrs.get(i));
		}

		for (int k = 0; k < products.size(); k++) {
			productsCombo.getItems().add(products.get(k));
		}
		
		productsCombo.setPromptText("Select product");
		amountField.setPromptText("Amount");
		orderCombo.setPromptText("Select order number");
		
		

		orderTab.setContent(gp);
	}

}
