package utility;

import javafx.scene.control.*;
import javafx.scene.layout.*;

@SuppressWarnings({ "restriction" })
public class GUITile extends Pane {

	//TilePane tile = new TilePane();
	TextField textBox = new TextField("Enter Text");
	TextField nameBox = new TextField("Enter Name");
	Label nameLabel = new Label(this.nameBox.getText());
	TextArea textArea = new TextArea();
	Pane pane = new Pane();
	Button add = new Button("Add Class");
    Button edit = new Button("Edit");
    Button remove = new Button("Remove");
	
	public GUITile() {
            buildPane();
            buildNameBox();
            buildNamePanel();
            buildTextArea();
            buildEditButton();
            buildAddButton();
            buildRemoveButton();
	}
	
	private void buildPane() {
		this.pane.setMaxWidth(150);
        this.pane.setMinWidth(150);
        this.pane.setMaxHeight(150);
        this.pane.setMinHeight(150);
        this.pane.setLayoutY(100);
	}
	
	private void buildNameBox() {
		this.nameBox.setLayoutX(10);
        this.nameBox.setLayoutY(10);
        this.nameBox.setMaxWidth(120);
        this.nameBox.setMinWidth(120);
        this.pane.getChildren().add(this.nameBox);
	}
	
	private void buildNamePanel() {
		this.nameLabel.setLayoutX(10);
        this.nameLabel.setLayoutY(10);
        this.nameLabel.setMaxWidth(120);
        this.nameLabel.setMinWidth(120);
        this.nameLabel.setVisible(false);
        this.pane.getChildren().add(this.nameLabel);
	}
	
	private void buildTextArea() {
		this.textArea.setLayoutY(45);
        this.textArea.setLayoutX(10);
        this.textArea.setPrefRowCount(2);
        this.textArea.setMaxWidth(120);
        this.textArea.setMinWidth(120);
        this.textArea.setText("Test");
        this.pane.getChildren().add(this.textArea);
	}
	
	private void buildEditButton() {
		this.edit.setLayoutY(110);
        this.edit.setLayoutX(10);
        this.edit.setMaxSize(120, 30);
        this.edit.setMinSize(120, 30);
        this.edit.setVisible(false);
        this.pane.getChildren().add(this.edit);
	}
	
	private void buildAddButton() {
		this.add.setLayoutY(110);
        this.add.setLayoutX(10);
        this.add.setMaxSize(120, 30);
        this.add.setMinSize(120, 30);
        this.pane.getChildren().add(this.add);
	}
	
	private void buildRemoveButton() {
		this.remove.setLayoutY(150);
        this.remove.setLayoutX(10);
        this.remove.setMaxSize(120, 30);
        this.remove.setMinSize(120, 30);
        this.remove.setVisible(false);
        this.pane.getChildren().add(this.remove);
	}
	
}
