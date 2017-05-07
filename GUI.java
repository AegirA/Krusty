import java.lang.reflect.InvocationTargetException;

import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.TabPane;
import javafx.scene.control.TabPane.TabClosingPolicy;
import javafx.scene.layout.BorderPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

public class GUI extends Application {
	
	ShowCustomersTab scp;
	ShowStorageTab stb;
	ShowNewOrderTab snot;
	ShowOrdersTab sot;
	ShowPalletsTab spt;
	ShowTrackPalletTab stpt;
	TabPane tabPane;
	Stage primaryStage;
	Scene scene;
	Group root;
	
  public static void main(String[] args) {
	  Application.launch(args);
    
	
	
  }
  @Override
  public void start(Stage primaryStage) {
	  
	  // Connection
	  Database db = new Database();
	  db.openConnection();
	  
	  
	  // Set up stage
	  this.primaryStage = primaryStage;
	  primaryStage.setTitle("Krusty");
	  root = new Group();
	  scene = new Scene(root, 1000, 600, Color.WHITE);
	  tabPane = new TabPane();
	  BorderPane borderPane = new BorderPane();
	  tabPane.setTabClosingPolicy(TabClosingPolicy.UNAVAILABLE);
	  
	  	  
	  // Show New Order Tab
	  snot = new ShowNewOrderTab(db, tabPane, root, this);
	  snot.fillTab();
	  
	  // Show Orders tab
	  sot = new ShowOrdersTab(db, tabPane);
	  sot.fillTab();
	  
	  // Show PalletlsTab
	  spt = new ShowPalletsTab(db, tabPane);
	  spt.fillTab();
	  
	  // Show TrackPalletsTab
	  stpt = new ShowTrackPalletTab(db, tabPane);
	  stpt.fillTab();
	  
	  // Show Storage Tab
	  stb = new ShowStorageTab(db, tabPane);
	  stb.fillTab();
	  
	  // Show Customer Tab
	  scp = new ShowCustomersTab(db, tabPane);
	  scp.fillTab();
	 
	  // bind to take available space
	  borderPane.prefHeightProperty().bind(scene.heightProperty());
	  borderPane.prefWidthProperty().bind(scene.widthProperty());

	  borderPane.setCenter(tabPane);
	  root.getChildren().add(borderPane);
	  primaryStage.setScene(scene);
	  primaryStage.show();
    
	 
	  spt.lockScrollBars();
	  stpt.lockScrollBars();

    
  }

}