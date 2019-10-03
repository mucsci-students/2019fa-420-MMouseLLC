import javafx.application.Application;
import javafx.event.*;
import javafx.scene.Scene;
import javafx.stage.*;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.geometry.*; 

public class GUI extends Application {

	int size = 0;
	
	public static void main(String[] args) {
		launch(args);
	}

	@Override
	public void start(Stage primary) throws Exception {
		buildWindow(primary);
	}
	
	public void buildWindow(Stage primary) {
		int winLength = 1024;
		int winHeight = 720;
		primary.setTitle("MMouse UML Editor");
		Button button = new Button();
		button.setText("Add Class");
		Pane layout = new Pane();
		
		//action event 
        EventHandler<ActionEvent> clickAddEvent = new EventHandler<ActionEvent>() { 
            public void handle(ActionEvent e) 
            { 
                layout.getChildren().add(new GUITile().pane);
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
