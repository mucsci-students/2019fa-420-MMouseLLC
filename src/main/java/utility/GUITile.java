package utility;

import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.TextAlignment;

/**
 * @author eric
 * 
 * GUITile is the graphical representation of an UMLItem in the environment
 * It keeps track of all the things that makes an UMLItem and builds a usuable 
 * pane for the GUI interface. 
 * 
 */
public class GUITile extends Pane {
	TextField textBox = new TextField("Enter Text");
	TextField nameBox = new TextField("EnterName");
	Label nameLabel = new Label(this.nameBox.getText());
	TextArea textArea = new TextArea();
	/** Square holding everything in GUI */
	public Pane pane = new Pane();
	Button add = new Button("Add Class");
	Button edit = new Button("Edit Name");
	Button remove = new Button("x");
	//Button addAttr = new Button("Add Attribute");
	Button addChild = new Button("Add Child");
	Button move = new Button("M");
	//Button removeAttr = new Button("Remove Attribute");
	Label displayAttr = new Label("");
	Label displayFieldType = new Label("");
	Label displayFieldVar = new Label("");
	Label displayFunctionType = new Label("");
	Label displayFunctionVar = new Label("");
	Button field = new Button("+");
	Button function = new Button("+");
	Label ffDivider = new Label("---------------");
	Button removeField = new Button("-");
	Button removeFunction = new Button("-");
	Label fieldsLabel = new Label("Fields");
	Label functionsLabel = new Label("Functions");
	Button editField = new Button("e");
	Button editFunction = new Button("e");
	
	boolean hasAttr = false;

	/** Coordinates for where offsets will be in GUI pane */
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
		//buildAddAttrButton();
		//buildRemAttrButton();
		//buildAttrLabel();
		buildEditButton();
		buildAddButton();
		buildRemoveButton();
		buildChildButton();
		
		buildFieldButton();
		buildFunctionButton();
		buildffDividePanel();
		buildFieldTypeLabel();
		buildFieldVarLabel();
		buildFunctionTypeLabel();
		buildFunctionVarLabel();
		buildRemoveField();
		buildRemoveFunction();
		buildf1Label();
		buildf2Label();
		buildEditField();
		buildEditFunction();
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
		this.nameBox.setLayoutY(30);
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
		this.nameLabel.setLayoutY(30);
		this.nameLabel.setMaxWidth(120);
		this.nameLabel.setMinWidth(120);
		this.nameLabel.setVisible(false);
		this.pane.getChildren().add(this.nameLabel);
	}
	
	public void buildffDividePanel() {
		this.ffDivider.setLayoutX(30);
		this.ffDivider.setLayoutY(95);
		this.ffDivider.setMaxWidth(120);
		this.ffDivider.setMinWidth(120);
		this.ffDivider.setVisible(false);
		this.pane.getChildren().add(this.ffDivider);
	}
	
	public void buildf1Label() {
		this.fieldsLabel.setTextFill(Color.web("6F3800"));
		this.fieldsLabel.setLayoutX(30);
		this.fieldsLabel.setLayoutY(50);
		this.fieldsLabel.setMaxWidth(120);
		this.fieldsLabel.setMinWidth(120);
		this.fieldsLabel.setVisible(false);
		this.pane.getChildren().add(this.fieldsLabel);
	}
	public void buildf2Label() {
		this.functionsLabel.setTextFill(Color.web("6F3800"));
		this.functionsLabel.setLayoutX(30);
		this.functionsLabel.setLayoutY(110);
		this.functionsLabel.setMaxWidth(120);
		this.functionsLabel.setMinWidth(120);
		this.functionsLabel.setVisible(false);
		this.pane.getChildren().add(this.functionsLabel);
	}
	
	public void buildFieldButton() {
		this.field.setLayoutX(1);
		this.field.setLayoutY(70);
		this.field.setMaxSize(26, 26);
		this.field.setMinSize(26, 26);
		this.field.setVisible(false);
		this.field.setStyle("-fx-background-color: A6F1A6;");
		this.pane.getChildren().add(this.field);
	}
	
	public void buildFieldTypeLabel() {
		this.displayFieldType.setLayoutX(35);
		this.displayFieldType.setLayoutY(70);
		this.displayFieldType.setVisible(true);
		this.displayFieldType.setTextFill(Color.web("8565C4"));
		this.pane.getChildren().add(this.displayFieldType);
	}
	public void buildFieldVarLabel() {
		this.displayFieldVar.setLayoutX(80);
		this.displayFieldVar.setLayoutY(70);
		this.displayFieldVar.setVisible(true);
		this.displayFieldVar.setTextFill(Color.web("FFA82E"));
		this.pane.getChildren().add(this.displayFieldVar);
	}
	
	public void buildRemoveField() {
		this.removeField.setLayoutX(50);
		this.removeField.setLayoutY(70);
		this.removeField.setMaxSize(26, 26);
		this.removeField.setMinSize(26, 26);
		this.removeField.setStyle("-fx-background-color: FF6961;");
		this.removeField.setVisible(false);
		this.pane.getChildren().add(this.removeField);
	}
	
	public void buildEditField() {
		this.editField.setLayoutX(100);
		this.editField.setLayoutY(70);
		this.editField.setMaxSize(26, 26);
		this.editField.setMinSize(26, 26);
		this.editField.setStyle("-fx-background-color: A3D1FF;");
		this.editField.setVisible(false);
		this.pane.getChildren().add(this.editField);
	}
	
	public void buildFunctionButton() {
		this.function.setLayoutX(1);
		this.function.setLayoutY(130);
		this.function.setMaxSize(26, 26);
		this.function.setMinSize(26, 26);
		this.function.setVisible(false);
		this.function.setStyle("-fx-background-color: A6F1A6;");
		this.pane.getChildren().add(this.function);
	}
	
	public void buildFunctionTypeLabel() {
		this.displayFunctionType.setLayoutX(35);
		this.displayFunctionType.setLayoutY(130);
		this.displayFunctionType.setVisible(true);
		this.displayFunctionType.setTextFill(Color.web("8565C4"));
		this.pane.getChildren().add(this.displayFunctionType);
	}
	public void buildFunctionVarLabel() {
		this.displayFunctionVar.setLayoutX(80);
		this.displayFunctionVar.setLayoutY(130);
		this.displayFunctionVar.setVisible(true);
		this.displayFunctionVar.setTextFill(Color.web("FFA82E"));
		this.pane.getChildren().add(this.displayFunctionVar);
	}
	
	public void buildRemoveFunction() {
		this.removeFunction.setLayoutX(50);
		this.removeFunction.setLayoutY(130);
		this.removeFunction.setMaxSize(26, 26);
		this.removeFunction.setMinSize(26, 26);
		this.removeFunction.setStyle("-fx-background-color: FF6961;");
		this.removeFunction.setVisible(false);
		this.pane.getChildren().add(this.removeFunction);
	}
	public void buildEditFunction() {
		this.editFunction.setLayoutX(100);
		this.editFunction.setLayoutY(130);
		this.editFunction.setMaxSize(26, 26);
		this.editFunction.setMinSize(26, 26);
		this.editFunction.setStyle("-fx-background-color: A3D1FF;");
		this.editFunction.setVisible(false);
		this.pane.getChildren().add(this.editFunction);
	}
	/**
	 * @author eric
	 * build attr button makes the add attribute button for adding attributes
	 * to the main pane
	 */
	/*public void buildAddAttrButton() {
		this.addAttr.setLayoutX(10);
		this.addAttr.setLayoutY(70);
		this.addAttr.setVisible(false);
		this.addAttr.setMaxSize(120, 30);
		this.addAttr.setMinSize(120, 30);
		this.pane.getChildren().add(this.addAttr);
	}*/
	/**
	 * @author eric
	 * build remove attr button makes the remove attribute button for removing attributes
	 * from the main pane
	 */
	/*public void buildRemAttrButton() {
		this.removeAttr.setLayoutX(10);
		this.removeAttr.setLayoutY(110);
		this.removeAttr.setVisible(false);
		this.removeAttr.setMaxSize(120, 30);
		this.removeAttr.setMinSize(120, 30);
		this.pane.getChildren().add(this.removeAttr);
	}*/
	/**
	 * @author eric
	 * build attr label makes the label for attributes after adding attributes
	 * to the main pane. this displays them after adding. 
	 */
	/*public void buildAttrLabel() {
		this.displayAttr.setLayoutX(10);
		this.displayAttr.setLayoutY(45);
		this.displayAttr.setVisible(true);
		this.pane.getChildren().add(this.displayAttr);
	}*/
	/**
	 * @author eric
	 * build edit button makes the edit button for editting the name of the tile
	 */
	private void buildEditButton() {
		this.edit.setLayoutY(170);
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
		this.remove.setMaxSize(26, 26);
		this.remove.setMinSize(26, 26);
		this.remove.setVisible(true);
		this.remove.setStyle("-fx-background-color: FF6961;");
		this.pane.getChildren().add(this.remove);
	}

	/**
	 * Make button for removing child button from env
	 */
	public void buildChildButton() {
		this.addChild.setLayoutY(210);
		this.addChild.setLayoutX(10);
		this.addChild.setMaxSize(120, 30);
		this.addChild.setMinSize(120, 30);
		this.addChild.setVisible(false);
		this.pane.getChildren().add(this.addChild);
	}

}