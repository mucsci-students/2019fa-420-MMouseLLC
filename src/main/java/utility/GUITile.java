package utility;

import javafx.scene.control.*;
import javafx.scene.layout.*;

public class GUITile extends Pane {
	/*
	 * @author eric
	 * 
	 * GUITile is the graphical representation of an UMLItem in the environment
	 * It keeps track of all the things that makes an UMLItem and builds a usuable 
	 * pane for the GUI interface. 
	 * 
	 */
	TextField textBox = new TextField("Enter Text");
	TextField nameBox = new TextField("EnterName");
	Label nameLabel = new Label(this.nameBox.getText());
	TextArea textArea = new TextArea();
	public Pane pane = new Pane();
	Button add = new Button("Add Class");
	Button edit = new Button("Edit Name");
	Button remove = new Button("X");
	Button addAttr = new Button("Add Attribute");
	Button addChild = new Button("Add Child");
	Button move = new Button("M");
	Button removeAttr = new Button("Remove Attribute");
	Label displayAttr = new Label("");
	boolean hasAttr = false;

	public double sceneX, sceneY, layoutX, layoutY;
	/**
	 * @author eric
	 * 
	 * This is the initial constructor to build a tile. 
	 * calling all the functions needed to create each part of the tile
	 * serperately. 
	 * 
	 */
	public GUITile() {
		buildPane();
		buildNameBox();
		buildNamePanel();
		buildAddAttrButton();
		buildRemAttrButton();
		buildAttrLabel();
		buildEditButton();
		buildAddButton();
		buildRemoveButton();
		buildChildButton();
	}
	/**
	 * @author eric
	 * get virtual x keeps track of the current translate and layout which is while
	 * it is being rendered in the environment. 
	 */
	public double getVirtualX() {
		return this.pane.getLayoutX() + this.pane.getTranslateX();
	}
	/**
	 * @author eric
	 * get virtual y keeps track of the current translate and layout which is while
	 * it is being rendered in the environment. 
	 */
	public double getVirtualY() {
		return this.pane.getLayoutY() + this.pane.getTranslateY();
	}
	/**
	 * @author eric
	 * buildpane makes the make pane that hold the name, attr, buttons etc
	 */
	public void buildPane() {
		this.pane.setMaxWidth(150);
		this.pane.setMinWidth(150);
		this.pane.setMaxHeight(150);
		this.pane.setMinHeight(150);
		this.pane.setLayoutY(100);
		this.pane.setStyle("-fx-background-color: #eee; -fx-border-color: #000; -fx-border-width: 1;");
	}
	/**
	 * @author eric
	 * build name box is the box needed to enter the name initially when creating a tile
	 */
	public void buildNameBox() {
		this.nameBox.setLayoutX(10);
		this.nameBox.setLayoutY(20);
		this.nameBox.setMaxWidth(120);
		this.nameBox.setMinWidth(120);
		this.pane.getChildren().add(this.nameBox);
	}
	/**
	 * @author eric
	 * build name panel is the panel created after the add button is hit.
	 * it takes the name out of the name box, sets its own name.
	 */
	public void buildNamePanel() {
		this.nameLabel.setLayoutX(10);
		this.nameLabel.setLayoutY(20);
		this.nameLabel.setMaxWidth(120);
		this.nameLabel.setMinWidth(120);
		this.nameLabel.setVisible(false);
		this.pane.getChildren().add(this.nameLabel);
	}
	/**
	 * @author eric
	 * build attr button makes the add attribute button for adding attributes
	 * to the main pane
	 */
	public void buildAddAttrButton() {
		this.addAttr.setLayoutX(10);
		this.addAttr.setLayoutY(70);
		this.addAttr.setVisible(false);
		this.addAttr.setMaxSize(120, 30);
		this.addAttr.setMinSize(120, 30);
		this.pane.getChildren().add(this.addAttr);
	}
	/**
	 * @author eric
	 * build remove attr button makes the remove attribute button for removing attributes
	 * from the main pane
	 */
	public void buildRemAttrButton() {
		this.removeAttr.setLayoutX(10);
		this.removeAttr.setLayoutY(110);
		this.removeAttr.setVisible(false);
		this.removeAttr.setMaxSize(120, 30);
		this.removeAttr.setMinSize(120, 30);
		this.pane.getChildren().add(this.removeAttr);
	}
	/**
	 * @author eric
	 * build attr label makes the label for attributes after adding attributes
	 * to the main pane. this displays them after adding. 
	 */
	public void buildAttrLabel() {
		this.displayAttr.setLayoutX(10);
		this.displayAttr.setLayoutY(45);
		this.displayAttr.setVisible(true);
		this.pane.getChildren().add(this.displayAttr);
	}
	/**
	 * @author eric
	 * build edit button makes the edit button for editting the name of the tile
	 */
	private void buildEditButton() {
		this.edit.setLayoutY(150);
		this.edit.setLayoutX(10);
		this.edit.setMaxSize(120, 30);
		this.edit.setMinSize(120, 30);
		this.edit.setVisible(false);
		this.pane.getChildren().add(this.edit);
	}
	/**
	 * @author eric
	 * build add button makes the ad button for adding the tile to the environment 
	 */
	public void buildAddButton() {
		this.add.setLayoutY(110);
		this.add.setLayoutX(10);
		this.add.setMaxSize(120, 30);
		this.add.setMinSize(120, 30);
		this.pane.getChildren().add(this.add);
	}
	/**
	 * @author eric
	 * build remove button makes the remove button for removing the tile from the 
	 * environment
	 */
	public void buildRemoveButton() {
		this.remove.setLayoutY(1);
		this.remove.setLayoutX(1);
		this.remove.setMaxSize(15, 15);
		this.remove.setMinSize(15, 15);
		this.remove.setVisible(true);
		this.remove.setStyle("-fx-background-color: DD0000;");
		this.pane.getChildren().add(this.remove);
	}

	public void buildChildButton() {
		this.addChild.setLayoutY(190);
		this.addChild.setLayoutX(10);
		this.addChild.setMaxSize(120, 30);
		this.addChild.setMinSize(120, 30);
		this.addChild.setVisible(false);
		this.pane.getChildren().add(this.addChild);
	}

}