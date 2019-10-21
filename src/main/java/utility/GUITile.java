package utility;

import javafx.scene.control.*;
import javafx.scene.layout.*;

@SuppressWarnings({ "restriction" })
public class GUITile extends Pane {

	//TilePane tile = new TilePane();
	TextField textBox = new TextField("Enter Text");
	TextField nameBox = new TextField("EnterName");
	Label nameLabel = new Label(this.nameBox.getText());
	TextArea textArea = new TextArea();
	Pane pane = new Pane();
	Button add = new Button("Add Class");
    Button edit = new Button("Edit Name");
    Button remove = new Button("Remove Class");
    Button addAttr = new Button("Add Attribute");
    Button removeAttr = new Button("Remove Attribute");
    Label displayAttr = new Label("");
    boolean hasAttr = false;
	
	public GUITile() {
            buildPane();
            buildNameBox();
            buildNamePanel();
            //buildTextArea();
            buildAddAttrButton();
            buildRemAttrButton();
            buildAttrLabel();
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
	
	public void buildAddAttrButton() {
		this.addAttr.setLayoutX(10);
		this.addAttr.setLayoutY(70);
		this.addAttr.setVisible(false);
        this.addAttr.setMaxSize(120, 30);
        this.addAttr.setMinSize(120, 30);
		this.pane.getChildren().add(this.addAttr);
	}
	
	public void buildRemAttrButton() {
		this.removeAttr.setLayoutX(10);
		this.removeAttr.setLayoutY(110);
		this.removeAttr.setVisible(false);
        this.removeAttr.setMaxSize(120, 30);
        this.removeAttr.setMinSize(120, 30);
		this.pane.getChildren().add(this.removeAttr);
	}
	
	public void buildAttrLabel() {
		this.displayAttr.setLayoutX(10);
		this.displayAttr.setLayoutY(45);
		this.displayAttr.setVisible(true);
		this.pane.getChildren().add(this.displayAttr);
	}
	
	private void buildEditButton() {
		this.edit.setLayoutY(150);
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
		this.remove.setLayoutY(190);
        this.remove.setLayoutX(10);
        this.remove.setMaxSize(120, 30);
        this.remove.setMinSize(120, 30);
        this.remove.setVisible(false);
        this.pane.getChildren().add(this.remove);
	}
	
}
