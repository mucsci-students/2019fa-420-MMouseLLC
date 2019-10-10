package utility;

import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import data.UMLEnvironment;
import data.UMLItem;
import javafx.application.Application;
import javafx.event.*;
import javafx.scene.Scene;
import javafx.stage.*;
import javafx.scene.control.*;
import javafx.scene.layout.*;
/*
 * @author eric 
 * @author grant
 * GUI extends Application to create a window for user to make UML with 
 * graphical user interface. It keeps track of a local Environment and also
 * a size of how many windows are open in the app. Not all windows will be saved 
 * to environment, which is why two sizes are needed. 
 */
@SuppressWarnings("restriction")
public class GUI extends Application {

	int size = 0;
	UMLEnvironment env;
	boolean removed = false;
	
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
	
	private void setSize(int size) {
		this.size = size;
	}
	
	private boolean isRemoved() {
		return this.removed;
	}
	
	private void setRemoved(boolean removed) {
		this.removed = removed;
	}
	
	/*
	 * @author eric
	 * build window sets up the size of the window and the event listeners 
	 * needed to function inside the environment. Add, edit, delete should all be
	 * made on their respected buttons for each individual item inside the gui
	 */
	@SuppressWarnings({ "restriction"})
	public void buildWindow(Stage primary) {
		this.env = new UMLEnvironment();
		int winLength = 1271;
		int winHeight = 920;
		primary.setTitle("MMouse UML Editor");
		Button addButton = new Button();
		Button resetAll = new Button("Start Over");
		addButton.setText("Add Class");
		Pane layout = new Pane();
		
		/*
		 * @author eric
		 * this event listener is for the add button in the main menu
		 * the purpose is to create a new potential item to be added to the environment
		 * Note: the items added to the gui are not in the environment right away.
		 * The add event creates handlers on each tile so the tile can be added or edited
		 * based on which tile is selected.
		 */
        EventHandler<ActionEvent> clickAddEvent = new EventHandler<ActionEvent>() { 
            
			public void handle(ActionEvent e) 
            { 
            	GUITile t = new GUITile();
            	if(getSize() == 0) {
            		t.pane.setLayoutX(0);
            		t.pane.setStyle("-fx-background-color: cyan");
            		t.pane.setStyle("-fx-border-color: black");
            	}else {
                	t.pane.setLayoutX(getSize() * 160);
            		t.pane.setStyle("-fx-background-color: cyan");
            		t.pane.setStyle("-fx-border-color: black");
            	}
            	layout.getChildren().add(t.pane);
            	increaseSize();
            	
            	/*
            	 * @author eric
            	 * t.add button event is a handler for saving an individual item into the 
            	 * environment. it will check if adding was successful or not. if success a 
            	 * confirmation window will appear. if fail an error window will appear. 
            	 */
            	t.add.setOnAction((event) -> {
            		String[] nameTest = t.nameBox.getText().split(" ");
            		
            		if(nameTest.length > 1) {
            			Alert a = new Alert(Alert.AlertType.ERROR, "Name cannot contain spaces.\nExample: New Class should be NewClass");
            			a.show();
            			return;
            		}
            		Pattern pattern = Pattern.compile("\\s");
            		Matcher matcher = pattern.matcher(t.nameBox.getText());
            		boolean found = matcher.find();
            		boolean isWhitespace = t.nameBox.getText().matches("^\\s*$");
            		if(isWhitespace) {
            			Alert a = new Alert(Alert.AlertType.ERROR, "Name cannot be only whitespace.");
            			a.show();
            			return;
            		}
            		
            		if(AddClass.addClass(env, t.nameBox.getText()))
                	{
                		Alert a = new Alert(Alert.AlertType.CONFIRMATION, t.nameBox.getText() + " added successfully!");
                		a.show();
                		t.nameBox.setVisible(false);
                		t.nameLabel.setVisible(true);
                		t.nameLabel.setText(t.nameBox.getText());
                		t.edit.setVisible(true);
                		t.remove.setVisible(true);
                        t.pane.setMaxHeight(190);
                        t.pane.setMinHeight(190);
                        t.pane.getChildren().remove(t.add);
                        
                	} else
                	{
                		Alert b = new Alert(Alert.AlertType.ERROR, t.nameBox.getText() + " could not be added. Name already exists");
                		b.show();
                	}
            	});
            	
            	/*
            	 * @author eric
            	 * t.edit button event will appear after the confirmation of the add class.
            	 * the edit button will take in the old name of the selected class and ask in a 
            	 * dialog box for a new name for the class. after input is entered the new name will
            	 * be checked if it exists, if it does not the class will be changed to that name, if
            	 * it does exist the user will be prompted to enter a new name instead. 
            	 */
            	
            	t.edit.setOnAction((event) -> {
            		TextInputDialog input = new TextInputDialog();
            		input.setHeaderText("Enter New name for \""  + t.nameBox.getText() + "\": ");
            		input.setHeight(50);
            		input.setWidth(120);
            		Optional<String> answer = input.showAndWait();
            		String[] nameTest = answer.toString().split(" ");
            		
            		if(nameTest.length > 1) {
            			Alert a = new Alert(Alert.AlertType.ERROR, "Name cannot contain spaces.\nExample: New Class should be NewClass");
            			a.show();
            			return;
            		}
            		
            		if (answer.isPresent()) {
            			if(AddClass.editItem(env, t.nameBox.getText(), answer.get())) {
            				Alert a = new Alert(Alert.AlertType.CONFIRMATION, t.nameBox.getText() + " successsfully changed to " + answer.get());
            				a.show();
            				t.nameBox.setText(answer.get());
            				t.nameLabel.setText(answer.get());
            			} else {
            				Alert b = new Alert(Alert.AlertType.ERROR, answer.get() + " already exists. Please choose another name.");
            				b.show();
            			}
            		} else {
            			Alert c = new Alert(Alert.AlertType.ERROR, "New name field cannot be blank.");
            			c.show();
            		}
            		
            	});
            	/*
            	 * @author eric
            	 * t.remove button event is set to remove an item from the environment and also from the gui's
            	 * main display when remove button is pressed on a specific uml item. 
            	 */
            	t.remove.setOnAction((event) -> {
            		Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Delete " + t.nameBox.getText() + "? ", ButtonType.YES, ButtonType.NO);
            		alert.showAndWait();
            		if(alert.getResult().getButtonData() == ButtonBar.ButtonData.YES) {
            			t.pane.setVisible(false);
            			layout.getChildren().remove(t.pane);
            			UMLItem remove = new UMLItem(t.nameBox.getText());
            			env.removeItem(remove);
            			setSize(getSize() - 1);
            			for(int i = 2; i < layout.getChildren().size(); i++)
            			{
            				layout.getChildren().get(i).relocate((i-2) * 160, layout.getChildren().get(i).getLayoutY());
            			}
            		}
            	});
            } 
        }; 
        
        resetAll.setOnAction((event) -> {
        	this.env = new UMLEnvironment();
        	
        	for(int i = 2; i < layout.getChildren().size(); i++) {
        		layout.getChildren().remove(i);
        	}
        	layout.getChildren().add(addButton);
        	layout.getChildren().add(resetAll);
        });
        
        /*
         * @author eric
         * This bottom section sets the stage size, add button layout and what is added to the stage
         * In the end it shows everything that was added to the stage before loading. 
         * A scene, we are calling layout, is the current screen we are looking at and primary is the 
         * stage holding the scene.
         */
        
        addButton.setLayoutX(10);
        addButton.setLayoutY(10);
        addButton.setOnAction(clickAddEvent);
        resetAll.setLayoutX(110);
        resetAll.setLayoutY(10);
		primary.setMinHeight(winHeight);
		primary.setMaxHeight(winHeight);
		primary.setMinWidth(winLength);
		primary.setMaxWidth(winLength);
		layout.getChildren().add(addButton);
		layout.getChildren().add(resetAll);
		Scene scene = new Scene(layout, winLength, winHeight);
		primary.setScene(scene);
		primary.show();
	}
	
}
