package utility;

import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;

/**
 * @author eric
 * 
 *         GUITile is the graphical representation of an UMLItem in the
 *         environment It keeps track of all the things that makes an UMLItem
 *         and builds a usable pane for the GUI interface.
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
	Button addChild = new Button("Add Child");
	Button move = new Button("M");
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
	 *         This is the initial constructor to build a tile. calling all the
	 *         functions needed to create each part of the tile serperately.
	 * 
	 */
	public GUITile() {
		buildPane();
		buildNameBox();
		buildNamePanel();
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
	 * @author eric get virtual x keeps track of the current translate and layout
	 *         which is while it is being rendered in the environment.
	 */
	public double getVirtualX() {
		return this.pane.getLayoutX() + this.pane.getTranslateX();
	}

	/**
	 * @author eric get virtual y keeps track of the current translate and layout
	 *         which is while it is being rendered in the environment.
	 */
	public double getVirtualY() {
		return this.pane.getLayoutY() + this.pane.getTranslateY();
	}

	/**
	 * @author eric buildpane makes the make pane that hold the name, attr, buttons
	 *         etc
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
	 * @author eric build name box is the box needed to enter the name initially
	 *         when creating a tile
	 */
	public void buildNameBox() {
		this.nameBox.setLayoutX(10);
		this.nameBox.setLayoutY(30);
		this.nameBox.setMaxWidth(120);
		this.nameBox.setMinWidth(120);
		this.pane.getChildren().add(this.nameBox);
	}

	/**
	 * @author eric build name panel is the panel created after the add button is
	 *         hit. it takes the name out of the name box, sets its own name.
	 */
	public void buildNamePanel() {
		this.nameLabel.setLayoutX(10);
		this.nameLabel.setLayoutY(30);
		this.nameLabel.setMaxWidth(120);
		this.nameLabel.setMinWidth(120);
		this.nameLabel.setVisible(false);
		this.pane.getChildren().add(this.nameLabel);
	}

	/**
	 * @author lauren build ff divide panel is the label divider that separates the
	 *         field and function areas in the gui tile
	 */
	public void buildffDividePanel() {
		this.ffDivider.setLayoutX(30);
		this.ffDivider.setLayoutY(95);
		this.ffDivider.setMaxWidth(120);
		this.ffDivider.setMinWidth(120);
		this.ffDivider.setVisible(false);
		this.pane.getChildren().add(this.ffDivider);
	}

	/**
	 * @author lauren build f1 label makes the header label for fields colored brown
	 */
	public void buildf1Label() {
		this.fieldsLabel.setTextFill(Color.web("6F3800"));
		this.fieldsLabel.setLayoutX(30);
		this.fieldsLabel.setLayoutY(50);
		this.fieldsLabel.setMaxWidth(120);
		this.fieldsLabel.setMinWidth(120);
		this.fieldsLabel.setVisible(false);
		this.pane.getChildren().add(this.fieldsLabel);
	}

	/**
	 * @author lauren build f2 label makes the header label for functions colored
	 *         brown
	 */
	public void buildf2Label() {
		this.functionsLabel.setTextFill(Color.web("6F3800"));
		this.functionsLabel.setLayoutX(30);
		this.functionsLabel.setLayoutY(110);
		this.functionsLabel.setMaxWidth(120);
		this.functionsLabel.setMinWidth(120);
		this.functionsLabel.setVisible(false);
		this.pane.getChildren().add(this.functionsLabel);
	}

	/**
	 * @author lauren build field buttons makes the button to add fields colored
	 *         green
	 */
	public void buildFieldButton() {
		this.field.setLayoutX(1);
		this.field.setLayoutY(70);
		this.field.setMaxSize(26, 26);
		this.field.setMinSize(26, 26);
		this.field.setVisible(false);
		this.field.setStyle("-fx-background-color: A6F1A6;");
		this.pane.getChildren().add(this.field);
	}

	/**
	 * @author lauren build field type label makes the label to identify fields type
	 *         colored purple
	 */
	public void buildFieldTypeLabel() {
		this.displayFieldType.setLayoutX(35);
		this.displayFieldType.setLayoutY(70);
		this.displayFieldType.setVisible(true);
		this.displayFieldType.setTextFill(Color.web("8565C4"));
		this.pane.getChildren().add(this.displayFieldType);
	}

	/**
	 * @author lauren build field var label makes the label to identify fields name
	 *         colored orange
	 */
	public void buildFieldVarLabel() {
		this.displayFieldVar.setLayoutX(80);
		this.displayFieldVar.setLayoutY(70);
		this.displayFieldVar.setVisible(true);
		this.displayFieldVar.setTextFill(Color.web("FFA82E"));
		this.pane.getChildren().add(this.displayFieldVar);
	}

	/**
	 * @author lauren build remove field makes the button to remove a field colored
	 *         red
	 */
	public void buildRemoveField() {
		this.removeField.setLayoutX(50);
		this.removeField.setLayoutY(70);
		this.removeField.setMaxSize(26, 26);
		this.removeField.setMinSize(26, 26);
		this.removeField.setStyle("-fx-background-color: FF6961;");
		this.removeField.setVisible(false);
		this.pane.getChildren().add(this.removeField);
	}

	/**
	 * @author lauren build edit field makes the button to edit a field colored blue
	 */
	public void buildEditField() {
		this.editField.setLayoutX(100);
		this.editField.setLayoutY(70);
		this.editField.setMaxSize(26, 26);
		this.editField.setMinSize(26, 26);
		this.editField.setStyle("-fx-background-color: A3D1FF;");
		this.editField.setVisible(false);
		this.pane.getChildren().add(this.editField);
	}

	/**
	 * @author lauren build function buttons makes the button to add functions
	 *         colored green
	 */
	public void buildFunctionButton() {
		this.function.setLayoutX(1);
		this.function.setLayoutY(130);
		this.function.setMaxSize(26, 26);
		this.function.setMinSize(26, 26);
		this.function.setVisible(false);
		this.function.setStyle("-fx-background-color: A6F1A6;");
		this.pane.getChildren().add(this.function);
	}

	/**
	 * @author lauren build function type label makes the label to identify
	 *         functions type colored purple
	 */
	public void buildFunctionTypeLabel() {
		this.displayFunctionType.setLayoutX(35);
		this.displayFunctionType.setLayoutY(130);
		this.displayFunctionType.setVisible(true);
		this.displayFunctionType.setTextFill(Color.web("8565C4"));
		this.pane.getChildren().add(this.displayFunctionType);
	}

	/**
	 * @author lauren build function var label makes the label to identify functions
	 *         name colored orange
	 */
	public void buildFunctionVarLabel() {
		this.displayFunctionVar.setLayoutX(80);
		this.displayFunctionVar.setLayoutY(130);
		this.displayFunctionVar.setVisible(true);
		this.displayFunctionVar.setTextFill(Color.web("FFA82E"));
		this.pane.getChildren().add(this.displayFunctionVar);
	}

	/**
	 * @author lauren build remove function makes the button to remove a function
	 *         colored red
	 */
	public void buildRemoveFunction() {
		this.removeFunction.setLayoutX(50);
		this.removeFunction.setLayoutY(130);
		this.removeFunction.setMaxSize(26, 26);
		this.removeFunction.setMinSize(26, 26);
		this.removeFunction.setStyle("-fx-background-color: FF6961;");
		this.removeFunction.setVisible(false);
		this.pane.getChildren().add(this.removeFunction);
	}

	/**
	 * @author lauren build edit function makes the button to edit a function
	 *         colored blue
	 */
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
	 * @author eric build edit button makes the edit button for editting the name of
	 *         the tile
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
	 * @author eric build add button makes the ad button for adding the tile to the
	 *         environment
	 */
	public void buildAddButton() {
		this.add.setLayoutY(110);
		this.add.setLayoutX(10);
		this.add.setMaxSize(120, 30);
		this.add.setMinSize(120, 30);
		this.pane.getChildren().add(this.add);
	}

	/**
	 * @author eric build remove button makes the remove button for removing the
	 *         tile from the environment
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