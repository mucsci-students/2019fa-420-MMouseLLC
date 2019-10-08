package utility;

import java.awt.Color;

import data.UMLEnvironment;
import javafx.application.Application;
import javafx.event.*;
import javafx.scene.Scene;
import javafx.stage.*;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.geometry.*;
import javafx.fxml.*;
/*
 * @author eric 
 * @author grant
 * GUI extends Application to create a window for user to make UML with 
 * graphical user interface. It keeps track of a local Environment and also
 * a size of how many windows are open in the app. Not all windows will be saved 
 * to environment, which is why two sizes are needed. 
 */
public class GUI extends Application {

	int size = 0;
	UMLEnvironment env;
	/*
	 * @author eric
	 * main calls the built in application function "launch"
	 * to create the initial window
	 */
	public static void main(String[] args) {
		launch(args);
	}

	/*
	 * @author eric
	 * start is built in but here is overridden so we can call our own
	 * start routine when the gui is created. 
	 */
	@Override
	public void start(Stage primary) throws Exception {
		buildWindow(primary);
	}
	/*
	 * @author eric
	 * increase size is adding the size of open windows inside the gui area.
	 * these are not all necessarily saved items in the environment.
	 */
	private void increaseSize() {
		this.size++;
	}
	/*
	 * @author eric
	 * get size returns the amount of open windows in the gui window.
	 * these are not all necessarily saved in the environment.
	 */
	private int getSize() {
		return this.size;
	}
	/*
	 * @author eric
	 * build window sets up the size of the window and the event listeners 
	 * needed to function inside the environment. Add, edit, delete should all be
	 * made on their respected buttons for each individual item inside the gui
	 */
	public void buildWindow(Stage primary) {
		this.env = new UMLEnvironment();
		int winLength = 1024;
		int winHeight = 720;
		primary.setTitle("MMouse UML Editor");
		Button button = new Button();
		button.setText("Add Class");
		Pane layout = new Pane();
		
		/*
		 * @author eric
		 * this event listener is for the add button in the main menu
		 * the purpose is to create a new potential item to be added to the environment
		 *  
		 */
        EventHandler<ActionEvent> clickAddEvent = new EventHandler<ActionEvent>() { 
            public void handle(ActionEvent e) 
            { 
            	GUITile t = new GUITile();
            	if(getSize() == 0) {
            		t.pane.setLayoutX(0);
            		t.pane.setStyle("-fx-border-color: black");
            	}else {

                	t.pane.setLayoutX(getSize() * 150);
            		t.pane.setStyle("-fx-border-color: black");
            	}
            	increaseSize();
            	/*
            	 * @author eric
            	 * save button event is a handler for saving an individual item into the 
            	 * environment. it will check if adding was successful or not. if success a 
            	 * confirmation window will appear. if fail a warning will appear. 
            	 */
                EventHandler<ActionEvent> saveButtonEvent = new EventHandler<ActionEvent>() { 
                    public void handle(ActionEvent e) 
                    { 
                    	if(AddClass.addClass(env, t.nameBox.getText()))
                    	{
                    		Alert a = new Alert(Alert.AlertType.CONFIRMATION, t.nameBox.getText() + " added successfully!");
                    		a.show();
                    		
                    	} else
                    	{
                    		Alert b = new Alert(Alert.AlertType.WARNING, t.nameBox.getText() + " could not be added. Name already exists");
                    		b.show();
                    	}
                    } 
                };
                t.save.setOnAction(saveButtonEvent);
                layout.getChildren().add(t.pane);
            } 
        }; 
        
		button.setLayoutX(10);
		button.setLayoutY(10);
		button.setOnAction(clickAddEvent);
		primary.setMinHeight(winHeight);
		primary.setMaxHeight(winHeight);
		primary.setMinWidth(winLength);
		primary.setMaxWidth(winLength);
		layout.getChildren().add(button);
		Scene scene = new Scene(layout, winLength, winHeight);
		primary.setScene(scene);
		primary.show();
	}
	
}
