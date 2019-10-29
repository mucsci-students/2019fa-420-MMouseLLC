package utility;

import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.layout.*;

public class GUITile extends Pane {

	//TilePane tile = new TilePane();
	TextField textBox = new TextField("Enter Text");
	TextField nameBox = new TextField("EnterName");
	Label nameLabel = new Label(this.nameBox.getText());
	TextArea textArea = new TextArea();
	Pane pane = new Pane();Button add = new Button("Add Class");
    Button edit = new Button("Edit Name");
    Button remove = new Button("Remove Class");
    Button addAttr = new Button("Add Attribute");
    Button addChild = new Button("Add Child");
    Button move = new Button("M");
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
            buildChildButton();
            buildMoveButton();
	}
	
	public void buildPane() {
		this.pane.setMaxWidth(150);
        this.pane.setMinWidth(150);
        this.pane.setMaxHeight(150);
        this.pane.setMinHeight(150);
        this.pane.setLayoutY(100);
	}
	
	public void buildNameBox() {
		this.nameBox.setLayoutX(10);
        this.nameBox.setLayoutY(20);
        this.nameBox.setMaxWidth(120);
        this.nameBox.setMinWidth(120);
        this.pane.getChildren().add(this.nameBox);
	}
	
	public void buildNamePanel() {
		this.nameLabel.setLayoutX(10);
        this.nameLabel.setLayoutY(20);
        this.nameLabel.setMaxWidth(120);
        this.nameLabel.setMinWidth(120);
        this.nameLabel.setVisible(false);
        this.pane.getChildren().add(this.nameLabel);
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
	
	public void buildAddButton() {
		this.add.setLayoutY(110);
        this.add.setLayoutX(10);
        this.add.setMaxSize(120, 30);
        this.add.setMinSize(120, 30);
        this.pane.getChildren().add(this.add);
	}
	
	public void buildRemoveButton() {
		this.remove.setLayoutY(190);
        this.remove.setLayoutX(10);
        this.remove.setMaxSize(120, 30);
        this.remove.setMinSize(120, 30);
        this.remove.setVisible(false);
        this.pane.getChildren().add(this.remove);
	}
	
	public void buildChildButton() {
		this.addChild.setLayoutY(230);
        this.addChild.setLayoutX(10);
        this.addChild.setMaxSize(120, 30);
        this.addChild.setMinSize(120, 30);
        this.addChild.setVisible(false);
        this.pane.getChildren().add(this.addChild);
	}
	
	public void buildMoveButton() {
		this.move.setLayoutY(1);
        this.move.setLayoutX(1);
        this.move.setMaxSize(15, 15);
        this.move.setMinSize(15, 15);
        this.move.setVisible(true);
        this.pane.getChildren().add(this.move);
	}
	
}
