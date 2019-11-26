package utility;

import java.util.ArrayList;
import java.util.Optional;
import java.util.function.BiConsumer;
import config.ArrowModifier;
import data.Arrow;
import data.GUIEnvironment;
import data.ParentChildPair;
import data.Relationship;
import data.UMLItem;
import javafx.application.Application;
import javafx.event.*;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.stage.*;
import javafx.scene.control.*;
import javafx.scene.layout.*;

/**
 * @author eric 
 * @author grant
 * @author Matt
 * GUI extends Application to create a window for user to make UML with 
 * graphical user interface. It keeps track of a local Environment and also
 * a size of how many windows are open in the app. Not all windows will be saved 
 * to environment, which is why two sizes are needed. 
 */
public class GUI extends Application {

	private int size = 0;
	private GUIEnvironment env;
	private final int ADD_ATTR_OFFSET = 17;
	private Pane mainLayout = new Pane();
	private Group layout = new Group();
	private Group arrowLayout = new Group();
	private ToggleButton displayMode = new ToggleButton("Display Mode");
	private ToggleButton editMode = new ToggleButton("Edit Mode");

	/**
	 * @author eric main calls the built in application function "launch" to create
	 * the initial window
	 */
	public static void main(String[] args) {
		launch(args);
	}

	/**
	 * @author eric start is built in but here is overridden so we can call our own
	 * start routine when the gui is created.
	 */
	@Override
	public void start(Stage primary) throws Exception {
		buildWindow(primary);
	}

	/**
	 * @author eric increase size is adding the size of open windows inside the gui
	 * area. these are not all necessarily saved items in the environment.
	 */
	private void increaseSize() {
		this.size++;
	}

	/**
	 * @author eric get size returns the amount of open windows in the gui window.
	 * these are not all necessarily saved in the environment.
	 */
	private int getSize() {
		return this.size;
	}
	/**
	 * @author eric build window sets up the size of the window and the event
	 * listeners needed to function inside the environment. Add, edit, delete should
	 * all be made on their respected buttons for each individual item inside the
	 * gui
	 */
	public void buildWindow(Stage primary) {
		this.env = new GUIEnvironment();
		int winLength = 1271;
		int winHeight = 920;
		primary.setTitle("MMouse UML Editor");
		Button addButton = new Button();
		Button resetAll = new Button("Start Over");
		addButton.setText("Add Class");
		ScrollPane sp = new ScrollPane();
		sp.setContent(mainLayout);

		// Allows for the display/edit modes buttons
		ToggleGroup group = new ToggleGroup();
		displayMode.setToggleGroup(group);
		editMode.setToggleGroup(group);
		displayMode.setLayoutX(100);
		displayMode.setLayoutY(10);
		editMode.setLayoutX(210);
		editMode.setLayoutY(10);
		layout.getChildren().addAll(displayMode, editMode);
		// default in edit mode when starting GUI
		editMode.setSelected(true);

		/*
		 * @author lauren
		 * 
		 * editMode on action is an event listener for the edit mode button to be clicked.
		 * when clicked it will go through everything in the environment and re-render it into what
		 * the edit mode requires and shows. 
		 */
		editMode.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent clickEditButton) {
				if (!editMode.isSelected()) {
					Alert a = new Alert(Alert.AlertType.ERROR, "Already in Edit Mode");
					a.show();
					displayMode.setSelected(false);
					editMode.setSelected(true);
					return;
				} else {
					// update tiles to show buttons
					if (editMode.isSelected()) {
						for (UMLItem i : env.getItems()) {
							GUITile tile = env.getTileFor(i);
							tile.nameBox.setVisible(false);
							tile.nameLabel.setVisible(true);
							tile.nameLabel.setText(tile.nameBox.getText());
							tile.edit.setVisible(true);
							tile.remove.setVisible(true);
							tile.addAttr.setVisible(true);
							tile.addChild.setVisible(true);
							tile.move.setVisible(true);
							int tileSize = i.getAttributes().size();
							tile.pane.setMaxHeight(250 + (tileSize * ADD_ATTR_OFFSET));
							tile.pane.setMinHeight(250 + (tileSize * ADD_ATTR_OFFSET));
							tile.pane.getChildren().remove(tile.add);
							env.createMappingFor(i, tile);
							tile.removeAttr.setVisible(true);
							tile.pane.getChildren().remove(tile.add);
							setMoveTileAction(tile, layout);
							env.getRelationshipsFor(i).forEach(updateArrowWithParent());
						}
						
						for(UMLItem j : env.getItems()) {
							env.getRelationshipsFor(j).forEach(updateArrowWithParent());
						}
						
						displayMode.setSelected(false);
						editMode.setSelected(true);
						addButton.setDisable(false);
						return;
					}
				}
			}
		});
		/*
		 * @author lauren
		 * 
		 * displayMode on action is an event listener for the display mode button to be clicked.
		 * when clicked it will go through everything in the environment and re-render it into what
		 * the display mode requires and shows. 
		 */
		displayMode.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent clickDisplayButton) {
				if (!displayMode.isSelected()) {
					Alert a = new Alert(Alert.AlertType.ERROR, "Already in Display Mode");
					a.show();
					displayMode.setSelected(true);
					editMode.setSelected(false);
					return;
				} else {
					// update tiles to hide buttons
					if (displayMode.isSelected()) {
						for (UMLItem i : env.getItems()) {
							GUITile tile = env.getTileFor(i);
							tile.nameBox.setVisible(false);
							tile.nameLabel.setVisible(true);
							tile.nameLabel.setText(tile.nameBox.getText());
							tile.edit.setVisible(false);
							tile.remove.setVisible(false);
							tile.addAttr.setVisible(false);
							tile.addChild.setVisible(false);
							tile.move.setVisible(false);
							int tileSize = i.getAttributes().size();
							tile.pane.setMaxHeight(50 + (tileSize * ADD_ATTR_OFFSET));
							tile.pane.setMinHeight(50 + (tileSize * ADD_ATTR_OFFSET));
							tile.pane.getChildren().remove(tile.add);
							env.createMappingFor(i, tile);
							tile.removeAttr.setVisible(false);
							tile.pane.getChildren().remove(tile.add);
							
						}
						
						for(UMLItem j : env.getItems()) {
							env.getRelationshipsFor(j).forEach(updateArrowWithParent());
						}
						

						// reset the main buttons to be appropriate
						displayMode.setSelected(true);
						editMode.setSelected(false);
						addButton.setDisable(true);
						return;
					}
				}
			}

		});


		/*
		 * @author eric this event listener is for the add button in the main menu the
		 * purpose is to create a new potential item to be added to the environment
		 * Note: the items added to the gui are not in the environment right away. The
		 * add event creates handlers on each tile so the tile can be added or edited
		 * based on which tile is selected.
		 */
		EventHandler<ActionEvent> clickAddEvent = new EventHandler<ActionEvent>() {
			
			public void handle(ActionEvent e) {
				GUITile t = new GUITile();
				if (getSize() == 0) {
					t.pane.setTranslateX(0);
					t.pane.setTranslateY(0);
					t.layoutX = t.pane.getLayoutX();
					t.layoutY = t.pane.getLayoutY();
				} else {
					t.pane.setTranslateX(0);
					t.pane.setTranslateY(0);
					t.layoutX = t.pane.getLayoutX();
					t.layoutY = t.pane.getLayoutY();
				}
				layout.getChildren().add(t.pane);
				increaseSize();

				setAddButtonAction(t);
				setEditButtonAction(t);
				setRemoveButtonAction(t, layout);
				setAddAttributeAction(t, layout);
				setAddChildButtonAction(t, layout);
				setMoveTileAction(t, layout);
				setRemoveAttrButton(t, layout);
				sp.setContent(mainLayout);

			}
		};

		resetAll.setOnAction((event) -> {
			this.env = new GUIEnvironment();

			for (int i = 2; i < layout.getChildren().size(); i++) {
				layout.getChildren().remove(i);
			}
			layout.getChildren().add(addButton);

			layout.getChildren().add(resetAll);
		});

		/*
		 * @author eric This bottom section sets the stage size, add button layout and
		 * what is added to the stage In the end it shows everything that was added to
		 * the stage before loading. A scene, we are calling layout, is the current
		 * screen we are looking at and primary is the stage holding the scene.
		 */

		addButton.setLayoutX(10);
		addButton.setLayoutY(10);
		addButton.setOnAction(clickAddEvent);
		resetAll.setLayoutX(110);
		resetAll.setLayoutY(10);
		resetAll.setVisible(false);
		primary.setMinHeight(winHeight);
		primary.setMinWidth(winLength);
		layout.getChildren().add(addButton);
		layout.getChildren().add(resetAll);
		mainLayout.getChildren().add(arrowLayout);
		mainLayout.getChildren().add(layout);
		Scene scene = new Scene(sp, winLength, winHeight);
		primary.setScene(scene);
		primary.show();
	}
	
	/**
	 * @author eric t.add button event is a handler for saving an individual item
	 * into the environment. it will check if adding was successful or not. if
	 * success a confirmation window will appear. if fail an error window will
	 * appear.
	 */
	public void setAddButtonAction(GUITile t) {
		
		t.add.setOnAction((event) -> {
			String[] nameTest = t.nameBox.getText().split(" ");

			if (nameTest.length > 1) {
				Alert a = new Alert(Alert.AlertType.ERROR,
						"Name cannot contain spaces.\nExample: New Class should be NewClass");
				a.show();
				return;
			}

			boolean isWhitespace = t.nameBox.getText().matches("^\\s*$");

			if (isWhitespace) {
				Alert a = new Alert(Alert.AlertType.ERROR, "Name cannot be only whitespace.\nInput example: NewClass");
				a.show();

				return;
			}
			if (env.findItem(t.nameBox.getText()) != null) {
				Alert a = new Alert(Alert.AlertType.ERROR, t.nameBox.getText() + " is already added.");
				a.show();
				return;
			}
			env.addItem(new UMLItem(t.nameBox.getText()));
			if (env.findItem(t.nameBox.getText()) != null) {

				// Update the name and add buttons
				/*
				 * Checking whether edit or display is currently activated and lets buttons be
				 * visible accordingly
				 */
				if (editMode.isSelected()) {
					t.nameBox.setVisible(false);
					t.nameLabel.setVisible(true);
					t.nameLabel.setText(t.nameBox.getText());
					if(t.nameLabel.getText().toString().length() > 17) {
						t.pane.setMinWidth(t.pane.getWidth() + (t.nameLabel.getText().toString().length() - 19) * 8);
						t.nameLabel.setMinWidth(t.pane.getWidth() + (t.nameLabel.getText().toString().length() - 19) * 8);
					}
					t.edit.setVisible(true);
					t.remove.setVisible(true);
					t.addAttr.setVisible(true);
					t.addChild.setVisible(true);
					t.move.setVisible(true);
					t.pane.setMaxHeight(250);
					t.pane.setMinHeight(250);
					t.pane.getChildren().remove(t.add);
					UMLItem item = env.findItem(t.nameBox.getText());
					env.createMappingFor(item, t);
					t.removeAttr.setVisible(true);
					t.pane.getChildren().remove(t.add);
				} else {
					t.nameBox.setVisible(false);
					t.nameLabel.setVisible(true);
					t.nameLabel.setText(t.nameBox.getText());
					if(t.nameLabel.getText().toString().length() > 17) {
						t.pane.setMinWidth(t.pane.getWidth() + (t.nameLabel.getText().toString().length() - 19) * 8);
						t.nameLabel.setMinWidth(t.pane.getWidth() + (t.nameLabel.getText().toString().length() - 19) * 8);
					}
					t.edit.setVisible(false);
					t.remove.setVisible(false);
					t.addAttr.setVisible(false);
					t.addChild.setVisible(false);
					t.move.setVisible(false);
					t.pane.setMaxHeight(50);
					t.pane.setMinHeight(50);
					t.pane.getChildren().remove(t.add);
					UMLItem item = env.findItem(t.nameBox.getText());
					env.createMappingFor(item, t);
					t.removeAttr.setVisible(false);
					t.pane.getChildren().remove(t.add);
				}

			} else {
				Alert b = new Alert(Alert.AlertType.ERROR,
						t.nameBox.getText() + " could not be added. Name already exists.\nPlease choose another name.");
				b.show();
			}
			t.pane.setLayoutX(t.getVirtualX());
			t.pane.setLayoutY(t.getVirtualY());
			t.layoutX = t.pane.getLayoutX();
			t.layoutY = t.pane.getLayoutY();

			t.pane.setTranslateX(0);
			t.pane.setTranslateY(0);
			//t.pane.setLayoutX(10);
			//t.pane.setLayoutY(110);
		});
	}

	/**
	 * @author eric t.edit button event will appear after the confirmation of the
	 * add class. the edit button will take in the old name of the selected class
	 * and ask in a dialog box for a new name for the class. after input is entered
	 * the new name will be checked if it exists, if it does not the class will be
	 * changed to that name, if it does exist the user will be prompted to enter a
	 * new name instead.
	 */
	public void setEditButtonAction(GUITile t) {

		t.edit.setOnAction((event) -> {
			TextInputDialog input = new TextInputDialog();
			input.setHeaderText("Enter New name for \"" + t.nameBox.getText() + "\": ");
			input.setHeight(50);
			input.setWidth(120);
			Optional<String> answer = input.showAndWait();
			String[] nameTest = answer.toString().split(" ");

			if (nameTest.length > 1) {
				Alert a = new Alert(Alert.AlertType.ERROR,
						"Name cannot contain spaces.\nExample: New Class should be NewClass");
				a.show();
				return;
			}

			boolean isWhitespace = t.nameBox.getText().matches("^\\s*$"); // checks if name entered is only
																			// whitespace.

			if (isWhitespace) {
				Alert a = new Alert(Alert.AlertType.ERROR, "Name cannot be only whitespace.\nInput example: NewClass");
				a.show();
				return;
			}

			if (answer.isPresent()) {
				env.editItem(t.nameBox.getText(), answer.get(), env.findItem(t.nameBox.getText()));

				if (env.findItem(answer.get()) != null) {
					if(answer.get().toString().length() < t.nameBox.getText().toString().length()) {
						t.pane.setMaxWidth(t.pane.getWidth() + (answer.get().toString().length() - 17) * 8);
					}
					t.nameBox.setText(answer.get());
					t.nameLabel.setText(answer.get());
					UMLItem found = env.findItem(t.nameLabel.getText());
					ArrayList<String> testArr = new ArrayList<String>(found.getAttributes());
					String newAttr = "";
					for (int i = 0; i < testArr.size(); i++) {
						newAttr += "\u2022" + testArr.get(i).toString() + "\n";
					}
					int maxAttr = 0;
					for(int i = 0; i < testArr.size(); i++) {
						if(testArr.get(i).toString().length() > 17 && testArr.get(i).toString().length() > maxAttr) {
							maxAttr = testArr.get(i).toString().length();
						}
					}
					if(maxAttr > t.nameBox.getText().toString().length()) {
						t.pane.setMaxWidth(t.pane.getWidth() + (maxAttr - 17) * 8);
					}
				} else {
					Alert b = new Alert(Alert.AlertType.ERROR,
							answer.get() + " already exists. Please choose another name.");
					b.show();
				}
			} else {
				Alert c = new Alert(Alert.AlertType.ERROR, "New name field cannot be blank.");
				c.show();
			}

		});
	}

	/**
	 * @author eric t.remove button event is set to remove an item from the
	 * environment and also from the gui's main display when remove button is
	 * pressed on a specific uml item.
	 */
	public void setRemoveButtonAction(GUITile t, Group layout) {
		
		t.remove.setOnAction((event) -> {
			Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Delete " + t.nameBox.getText() + "? ",
					ButtonType.YES, ButtonType.NO);
			alert.showAndWait();
			if (alert.getResult().getButtonData() == ButtonBar.ButtonData.YES) {
				UMLItem item = env.findItem(t.nameBox.getText());
				System.out.println(env.getRelationshipsFor(item).size());
				env.getRelationshipsFor(item).forEach(removeArrow());
				layout.getChildren().remove(t.pane);
				env.removeItemGUI(item);
				env.removeItem(item);
				System.out.println(env.getRelationshipsFor(item).size());
			}

		});
	}
	
	/**
	 * @author eric
	 * 
	 * setAddAttributeAction is an action listener on the add attr button in the tile. This button
	 * will take in a value and then update the list of attributes on a tile after it has been added. 
	 * 
	 */
	public void setAddAttributeAction(GUITile t, Group layout) {
		// Adds an attribute as text to the tile clicked
		t.addAttr.setOnAction((event) -> {
			TextInputDialog input = new TextInputDialog();
			input.setHeaderText("Enter attribute to add for " + t.nameBox.getText()
					+ ":\nMust be one word no spaces\nExample: NewAttribute");
			input.setHeight(50);
			input.setWidth(120);
			Optional<String> answer = input.showAndWait();
			String[] attrTest = answer.toString().split(" ");

			if (attrTest.length > 1) {
				Alert a = new Alert(Alert.AlertType.ERROR,
						"Attribute cannot contain spaces.\nExample: New Attr should be NewAttr");
				a.show();
				return;
			}

			boolean isWhitespace = answer.get().matches("^\\s*$"); // checks if name entered is only whitespace.

			if (isWhitespace) {
				Alert a = new Alert(Alert.AlertType.ERROR, "Attribute cannot be only whitespace.\nExample: NewAttr");
				a.show();
				return;
			}
			if (answer.isPresent()) {
				UMLItem found = env.findItem(t.nameBox.getText());
				if (found != null) {
					ArrayList<String> testName = new ArrayList<String>(found.getAttributes());
					for (int i = 0; i < testName.size(); i++) {
						if (answer.get().equals(testName.get(i))) {
							Alert a = new Alert(Alert.AlertType.ERROR, "Attribute " + testName.get(i).toString()
									+ " already exists. Please enter an original name.");
							a.show();
							return;
						}
					}
					t.displayAttr.setText("");
					String newAttr = "";
					found.addAttribute(answer.get());
					ArrayList<String> test = new ArrayList<>(found.getAttributes());
					for (int i = 0; i < test.size(); i++) {
						newAttr = t.displayAttr.getText() + "\u2022" + test.get(i).toString() + "\n";
						t.displayAttr.setText(newAttr);
					}
					int maxAttr = 0;
					for(int i = 0; i < test.size(); i++) {
						if(test.get(i).toString().length() > 17 && test.get(i).toString().length() > maxAttr) {
							maxAttr = test.get(i).toString().length();
						}
					}
					if(maxAttr > t.nameBox.getText().toString().length()) {
						t.pane.setMaxWidth(t.pane.getWidth() + (maxAttr - 17) * 8);
					}
					t.pane.setMinHeight(t.pane.getHeight() + ADD_ATTR_OFFSET);
					t.pane.setMaxHeight(t.pane.getHeight() + ADD_ATTR_OFFSET);
					t.edit.setLayoutY(t.edit.getLayoutY() + ADD_ATTR_OFFSET);
					t.addAttr.setLayoutY(t.addAttr.getLayoutY() + ADD_ATTR_OFFSET);
					t.removeAttr.setLayoutY(t.removeAttr.getLayoutY() + ADD_ATTR_OFFSET);
					t.addChild.setLayoutY(t.addChild.getLayoutY() + ADD_ATTR_OFFSET);

					newAttr = t.displayAttr.getText() + "\u2022" + answer.get() + "\n";
				} else {
					Alert a = new Alert(Alert.AlertType.ERROR, "Something went wrong finding the class.");
					a.show();
				}
			} else {
				Alert a = new Alert(Alert.AlertType.ERROR, "Attribute cannot be blank.");
				a.show();
			}

		});
	}
	
	/**
	 * @author matt and eric
	 * 
	 * setAddChildAction sets an action listener to the tile for the add child button.
	 * This essentially adds the relationship to the environment and then draws the arrow
	 * in the GUI based on each tile's positions at the current time. 
	 * 
	 */
	public void setAddChildButtonAction(GUITile t, Group layout) {
		// Moves the child specified under tile t and links with arrow
		t.addChild.setOnAction((event) -> {
			// prompt search for text input
			// locate the panel containing that text
			// move that panel down under the currentPanel
			TextInputDialog input = new TextInputDialog();
			input.setHeaderText("Enter name of Child Class");
			input.setHeight(50);
			input.setWidth(120);
			Optional<String> answer = input.showAndWait();
			String[] nameTest = answer.get().split(" ");

			if (nameTest.length > 1) {
				Alert a = new Alert(Alert.AlertType.ERROR,
						"Name cannot contain spaces.\nExample: New Class should be NewClass");
				a.show();
				return;
			}
			boolean isWhitespace = t.nameBox.getText().matches("^\\s*$");

			if (isWhitespace) {
				Alert a = new Alert(Alert.AlertType.ERROR, "Name cannot be only whitespace.\nInput example: NewClass");
				a.show();
				return;
			}
			// text in prompt matches our specs

			UMLItem child = env.findItem(nameTest[0]);
			UMLItem parent = env.findItem(t.nameBox.getText());
			ArrowModifier mod = new ArrowModifier(parent, child);
			Arrow arr = mod.makeNewArrow(env, layout);
			arrowLayout.getChildren().add(arr);
			arrowLayout.getChildren().add(arr.editQuant);
			arrowLayout.getChildren().add(arr.quantLabel);
			arrowLayout.getChildren().add(arr.quantBox);
			arrowLayout.getChildren().add(arr.quantButton);
			env.addArrow(parent, child, arr);
			if (child == null) {
				Alert a = new Alert(Alert.AlertType.ERROR, nameTest[0] + " does not exist.");
				a.show();
				return;
			}
			if (child == parent) {
				Alert a = new Alert(Alert.AlertType.ERROR, "A parent cannot be it's own child.");
				a.show();
				return;
			}

		});
	}
	
	/**
	 * @author eric
	 * 
	 * setMoveTileAction sets and event listener onto a tile that will allow is to 
	 * listen for the tile being clicked on and the tile being dragged and released.
	 * This updates the tiles position and re-renders the arrows to and from it while being
	 * clicked and dragged and released. 
	 * 
	 */
	public void setMoveTileAction(GUITile t, Group layout) {

		t.pane.setOnMousePressed(e -> {
			layout.getChildren().remove(t.pane);
			layout.getChildren().add(t.pane);
			t.sceneX = e.getSceneX();
			t.sceneY = e.getSceneY();
			t.layoutX = t.pane.getLayoutX();
			t.layoutY = t.pane.getLayoutY();
		});
		
		t.pane.setOnMouseDragged(event -> {
			double offsetX = event.getSceneX() - t.sceneX;
			double offsetY = event.getSceneY() - t.sceneY;
			t.pane.setTranslateX(offsetX);
			t.pane.setTranslateY(offsetY);

			UMLItem item = env.findItem(t.nameBox.getText());
			env.getRelationshipsFor(item).forEach(updateArrowWithParent());
		});

		t.pane.setOnMouseReleased(event -> {
			t.pane.setLayoutX(t.layoutX + t.pane.getTranslateX());
			t.pane.setLayoutY(t.layoutY + t.pane.getTranslateY());
			t.sceneX = event.getSceneX();
			t.sceneY = event.getSceneY();
			t.layoutX = t.pane.getLayoutX();
			t.layoutY = t.pane.getLayoutY();
			t.pane.setTranslateX(0);
			t.pane.setTranslateY(0);
			UMLItem item = env.findItem(t.nameBox.getText());
			env.getRelationshipsFor(item).forEach(updateArrowWithParent());
		});
	}
	
	/**
	 * @author eric
	 * 
	 * setRemoveAttrButton sets an event listener to listen for when the remove attribute button is 
	 * going to be clicked. When clicked it takes in an input and re-renders the tile being clicked
	 * to the appropriate size and amount of attributes. 
	 * 
	 */
	public void setRemoveAttrButton(GUITile t, Group layout) {
		// Removes attribute from text field in tile t
		t.removeAttr.setOnAction((event) -> {
			TextInputDialog input = new TextInputDialog();
			input.setHeaderText("Enter attribute to remove for " + t.nameBox.getText() + ".");
			input.setHeight(50);
			input.setWidth(120);
			Optional<String> answer = input.showAndWait();
			String[] attrTest = answer.toString().split(" ");

			if (attrTest.length > 1) {
				Alert a = new Alert(Alert.AlertType.ERROR,
						"Attribute cannot contain spaces.\nExample: New Attr should be NewAttr");
				a.show();
				return;
			}
			boolean isWhitespace = answer.get().matches("^\\s*$"); // checks if name entered is only whitespace.

			if (isWhitespace) {
				Alert a = new Alert(Alert.AlertType.ERROR, "Attribute cannot be only whitespace.\nExample: NewAttr");
				a.show();
				return;
			}
			if (answer.isPresent()) {
				UMLItem found = env.findItem(t.nameBox.getText());
				if (found != null) {
					ArrayList<String> testName = new ArrayList<String>(found.getAttributes());
					boolean isFound = false;
					for (int i = 0; i < testName.size(); i++) {
						if (answer.get().equals(testName.get(i))) {
							isFound = true;
							found.removeAttribute(answer.get());
							break;
						}
					}
					if (!isFound) {
						Alert a = new Alert(Alert.AlertType.ERROR,
								"Attribute " + answer.get() + " not found in list of attributes.");
						a.show();
						return;
					}
					t.displayAttr.setText("");
					ArrayList<String> testArr = new ArrayList<String>(found.getAttributes());
					String newAttr = "";
					for (int i = 0; i < testArr.size(); i++) {
						newAttr += "\u2022" + testArr.get(i).toString() + "\n";
					}
					int maxAttr = 0;
					for(int i = 0; i < testArr.size(); i++) {
						if(testArr.get(i).toString().length() > 17 && testArr.get(i).toString().length() > maxAttr) {
							maxAttr = testArr.get(i).toString().length();
						}
					}
					if(maxAttr > t.nameBox.getText().toString().length()) {
						t.pane.setMaxWidth(t.pane.getWidth() + (maxAttr - 17) * 8);
					}
					t.displayAttr.setText(newAttr);
					t.pane.setMinHeight(t.pane.getHeight() - ADD_ATTR_OFFSET);
					t.pane.setMaxHeight(t.pane.getHeight() - ADD_ATTR_OFFSET);
					t.edit.setLayoutY(t.edit.getLayoutY() - ADD_ATTR_OFFSET);
					t.addAttr.setLayoutY(t.addAttr.getLayoutY() - ADD_ATTR_OFFSET);
					t.removeAttr.setLayoutY(t.removeAttr.getLayoutY() - ADD_ATTR_OFFSET);
					t.addChild.setLayoutY(t.addChild.getLayoutY() - ADD_ATTR_OFFSET);
				}
			}
		});
	}
	/*
	 * @author eric
	 * 
	 * updateArrowWithParent takes in a map of parent and child to an arrow they share.
	 * this function essentially calls the arrow modifier to translate where the arrow needs
	 * to render in relation to the modifications happening to either parent or child. 
	 * 
	 */
	private BiConsumer<? super ParentChildPair, ? super Arrow> updateArrowWithParent() {
		return (ParentChildPair pair, Arrow arrow) -> {

			UMLItem parent = pair.getParent();
			UMLItem child = pair.getChild();
			GUITile parentTile = env.getTileFor(parent);
			GUITile childTile = env.getTileFor(child);
			
			
			ArrowModifier mod = new ArrowModifier(parent, child);
			
			Arrow newArrow = mod.updateArrow(env, layout);
			if(arrow.quantAdded) {
				newArrow.quantAdded = true;
				newArrow.quantLabel.setText(arrow.quantLabel.getText());
				newArrow.quantLabel.setVisible(true);
				newArrow.editQuant.setVisible(true);
				newArrow.quantBox.setVisible(false);
				newArrow.quantButton.setVisible(false);
			}
			if(arrow.isRemoved) {
				newArrow.isRemoved = true;
				newArrow.quantLabel.setVisible(false);
				newArrow.editQuant.setVisible(false);
				newArrow.quantBox.setVisible(false);
				newArrow.quantButton.setVisible(false);
			}
			arrowLayout.getChildren().remove(arrow);
			arrowLayout.getChildren().remove(arrow.editQuant);
			arrowLayout.getChildren().remove(arrow.quantLabel);
			arrowLayout.getChildren().remove(arrow.quantBox);
			arrowLayout.getChildren().remove(arrow.quantButton);
			arrowLayout.getChildren().remove(arrow);
			env.replaceArrow(pair, newArrow);
			arrowLayout.getChildren().add(newArrow);
			arrowLayout.getChildren().add(newArrow.editQuant);
			arrowLayout.getChildren().add(newArrow.quantLabel);
			arrowLayout.getChildren().add(newArrow.quantBox);
			arrowLayout.getChildren().add(newArrow.quantButton);

		};
	}
	/*
	 * @author eric
	 * 
	 * removeArrow takes in a map of parent child pairs to arrows and removes the arrow
	 * and relationship from the pair and the arrow. This renders out to the GUI and also
	 * updates the back end environment
	 */
	private BiConsumer<? super ParentChildPair, ? super Arrow> removeArrow() {
		return (ParentChildPair pair, Arrow arrow) -> {
			arrow.isRemoved = true;
			arrow.quantLabel.setVisible(false);
			arrow.editQuant.setVisible(false);
			arrow.quantBox.setVisible(false);
			arrow.quantButton.setVisible(false);
			arrowLayout.getChildren().remove(arrow);
			arrowLayout.getChildren().remove(arrow.editQuant);
			arrowLayout.getChildren().remove(arrow.quantLabel);
			arrowLayout.getChildren().remove(arrow.quantBox);
			arrowLayout.getChildren().remove(arrow.quantButton);
			env.removeArrow(pair.getParent(), pair.getChild());
			env.removeArrow(pair.getChild(), pair.getParent());
			//TODO remove these comments
			//pair.getChild().removeParent(pair.getParent());
			//pair.getParent().removeChild(pair.getChild());
			Relationship r = env.findRelationship(pair.getParent(), pair.getChild());
			env.removeRelationship(r);
			
		};
	}
}