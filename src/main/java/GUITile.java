import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.shape.*;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;
import org.w3c.dom.Text;
import javafx.geometry.*; 

@SuppressWarnings("serial")
public class GUITile extends Pane {

	//TilePane tile = new TilePane();
	TextField textBox = new TextField("Enter Text");
	TextField nameBox = new TextField("Enter Name");
	TextArea textArea = new TextArea();
	Pane pane = new Pane();
	
	public GUITile() {
		this.pane.setMaxWidth(100);
		this.pane.setMinWidth(100);
		this.pane.setMaxHeight(300);
		this.pane.setMinHeight(300);
		this.pane.setLayoutX(100);
		this.pane.setLayoutY(100);
		
		
		this.nameBox.setLayoutX(10);
		this.nameBox.setLayoutY(10);
		this.nameBox.setMaxWidth(120);
        this.nameBox.setMinWidth(120);
        this.pane.getChildren().add(this.nameBox);
        
		this.textArea.setLayoutY(35);
		this.textArea.setLayoutX(10);
        this.textArea.setPrefRowCount(2);
        this.textArea.setMaxWidth(120);
        this.textArea.setMinWidth(120);
        this.textArea.setText("Test");
        this.pane.getChildren().add(this.textArea);
	}
}
