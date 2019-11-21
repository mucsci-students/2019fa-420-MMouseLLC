package config;

import java.util.Optional;

import data.Arrow;
import data.GUIEnvironment;
import data.UMLItem;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.TextInputDialog;
import utility.GUITile;

/**
 * @author eric
 * 
 *         ArrowModifer is an object that takes in a parent and a child to be
 *         created. This modifier represents the changes happening to the arrow
 *         that connects the inputed parent and child. The functions
 *         makeNewArrow does the math for the creation of an arrow when none
 *         exists. The other function updateArrow takes advantage of each tile's
 *         "virtual x and y" which is where they are while dragging or after
 *         rendering when created or modified. This virtual address is how the
 *         arrows can be updated while dragging and dropping and rendering of
 *         the new heights and widths.
 */
public class ArrowModifier {

	UMLItem parent = new UMLItem();
	UMLItem child = new UMLItem();
	Label quantLabel = new Label();
	Button editQuant = new Button("edit quantifier");
	Button quantButton = new Button("add quantifier");
	TextField quantBox = new TextField("quantifier");

	/**
	 * 
	 * @param parent
	 * @param child
	 */
	public ArrowModifier(UMLItem parent, UMLItem child) {
		this.parent = parent;
		this.child = child;
	}

	public Label removeQuant(Group layout) {
		Label dummyLabel = new Label();
		quantLabel.setText("");
		layout.getChildren().remove(quantLabel);
		layout.getChildren().remove(editQuant);
		layout.getChildren().remove(quantBox);
		layout.getChildren().remove(quantButton);
		return dummyLabel;
	}

	/**
	 * Make new arrow for the modifier's child and parent. Takes in an environment
	 * which the parent and child have been created and creates the first arrow made
	 * in between them both. Based on the position of the tiles this function
	 * automatically figures out from where on each tile it should be drawn to and
	 * from.
	 * 
	 * @author eric
	 * 
	 * @return new arrow to be used after rendering
	 */
	public Arrow makeNewArrow(GUIEnvironment env, Group layout) {
		GUITile childTile = env.getTileFor(child);
		GUITile parentTile = env.getTileFor(parent);
		child.addParent(parent);
		parent.addChild(child);
		quantBox.setMaxWidth(120);
		layout.getChildren().add(quantButton);
		layout.getChildren().add(quantBox);
		// if child is on the right of the parent
		if ((parentTile.pane.getLayoutX() + parentTile.pane.getWidth()) < childTile.pane.getLayoutX()) {
			// If child is above and to the right do this arrow draw:
			if (childTile.pane.getLayoutY() + childTile.pane.getHeight() < parentTile.pane.getLayoutY()) {
				double[] parentCoords = { parentTile.pane.getLayoutX() + parentTile.pane.getWidth(),
						parentTile.pane.getLayoutY() };
				double[] childCoords = { childTile.pane.getLayoutX(),
						childTile.pane.getLayoutY() + childTile.pane.getHeight() };

				quantButton.setLayoutX((parentCoords[0] + childCoords[0]) / 2);
				quantButton.setLayoutY(((parentCoords[1] + childCoords[1]) / 2) + 40);
				quantBox.setLayoutX((parentCoords[0] + childCoords[0]) / 2);
				quantBox.setLayoutY(((parentCoords[1] + childCoords[1]) / 2) + 10);
				quantButton.setOnAction(new EventHandler<ActionEvent>() {

					public void handle(ActionEvent clickQuantButton) {
						quantLabel = new Label(quantBox.getText());
						quantLabel.setMinWidth(120);
						quantLabel.setLayoutX((parentCoords[0] + childCoords[0]) / 2);
						quantLabel.setLayoutY((parentCoords[1] + childCoords[1]) / 2);
						editQuant.setLayoutX((parentCoords[0] + childCoords[0]) / 2);
						editQuant.setLayoutY(((parentCoords[1] + childCoords[1]) / 2) + 20);
						quantLabel.setMaxWidth(120);
						layout.getChildren().add(quantLabel);
						layout.getChildren().add(editQuant);
						layout.getChildren().remove(quantBox);
						layout.getChildren().remove(quantButton);
					}
				});

				editQuant.setOnAction(new EventHandler<ActionEvent>() {
					public void handle(ActionEvent clickEditEvent) {
						TextInputDialog input = new TextInputDialog();
						input.setHeaderText("Enter New quantifier: ");
						input.setHeight(50);
						input.setWidth(120);
						Optional<String> answer = input.showAndWait();
						quantLabel.setText(answer.get());
					}

				});

				return new Arrow(parentCoords[0], parentCoords[1], childCoords[0], childCoords[1], 5);
				// if child is on right and below parent
			} else if (childTile.pane.getLayoutY() > parentTile.pane.getHeight() + parentTile.pane.getLayoutY()) {
				double[] parentCoords = { parentTile.pane.getLayoutX() + parentTile.pane.getWidth(),
						parentTile.pane.getLayoutY() + parentTile.pane.getHeight() };
				double[] childCoords = { childTile.pane.getLayoutX(), childTile.pane.getLayoutY() };

				quantButton.setLayoutX((parentCoords[0] + childCoords[0]) / 2);
				quantButton.setLayoutY(((parentCoords[1] + childCoords[1]) / 2) + 40);
				quantBox.setLayoutX((parentCoords[0] + childCoords[0]) / 2);
				quantBox.setLayoutY(((parentCoords[1] + childCoords[1]) / 2) + 10);
				quantButton.setOnAction(new EventHandler<ActionEvent>() {

					public void handle(ActionEvent clickQuantButton) {
						quantLabel = new Label(quantBox.getText());
						quantLabel.setMinWidth(120);
						quantLabel.setLayoutX((parentCoords[0] + childCoords[0]) / 2);
						quantLabel.setLayoutY((parentCoords[1] + childCoords[1]) / 2);
						editQuant.setLayoutX((parentCoords[0] + childCoords[0]) / 2);
						editQuant.setLayoutY(((parentCoords[1] + childCoords[1]) / 2) + 20);
						quantLabel.setMaxWidth(120);
						layout.getChildren().add(quantLabel);
						layout.getChildren().add(editQuant);
						layout.getChildren().remove(quantBox);
						layout.getChildren().remove(quantButton);
					}
				});
				editQuant.setOnAction(new EventHandler<ActionEvent>() {
					public void handle(ActionEvent clickEditEvent) {
						TextInputDialog input = new TextInputDialog();
						input.setHeaderText("Enter New quantifier: ");
						input.setHeight(50);
						input.setWidth(120);
						Optional<String> answer = input.showAndWait();
						quantLabel.setText(answer.get());
					}

				});
				return new Arrow(parentCoords[0], parentCoords[1], childCoords[0], childCoords[1], 5);
				// if child is in between on the right
			} else {
				double[] parentCoords = { parentTile.pane.getLayoutX() + parentTile.pane.getWidth(),
						parentTile.pane.getLayoutY() + parentTile.pane.getHeight() / 2 };
				double[] childCoords = { childTile.pane.getLayoutX(),
						childTile.pane.getLayoutY() + childTile.pane.getHeight() / 2 };

				quantButton.setLayoutX((parentCoords[0] + childCoords[0]) / 2);
				quantButton.setLayoutY(((parentCoords[1] + childCoords[1]) / 2) + 40);
				quantBox.setLayoutX((parentCoords[0] + childCoords[0]) / 2);
				quantBox.setLayoutY(((parentCoords[1] + childCoords[1]) / 2) + 10);
				quantButton.setOnAction(new EventHandler<ActionEvent>() {

					public void handle(ActionEvent clickQuantButton) {
						quantLabel = new Label(quantBox.getText());
						quantLabel.setMinWidth(120);
						quantLabel.setLayoutX((parentCoords[0] + childCoords[0]) / 2);
						quantLabel.setLayoutY((parentCoords[1] + childCoords[1]) / 2);
						editQuant.setLayoutX((parentCoords[0] + childCoords[0]) / 2);
						editQuant.setLayoutY(((parentCoords[1] + childCoords[1]) / 2) + 20);
						quantLabel.setMaxWidth(120);
						layout.getChildren().add(quantLabel);
						layout.getChildren().add(editQuant);
						layout.getChildren().remove(quantBox);
						layout.getChildren().remove(quantButton);
					}
				});
				editQuant.setOnAction(new EventHandler<ActionEvent>() {
					public void handle(ActionEvent clickEditEvent) {
						TextInputDialog input = new TextInputDialog();
						input.setHeaderText("Enter New quantifier: ");
						input.setHeight(50);
						input.setWidth(120);
						Optional<String> answer = input.showAndWait();
						quantLabel.setText(answer.get());
					}

				});
				return new Arrow(parentCoords[0], parentCoords[1], childCoords[0], childCoords[1], 5);
			}

		} // if child is on the left of the parent
		else if (parentTile.pane.getLayoutX() > childTile.pane.getLayoutX() + childTile.pane.getWidth()) {
			// child is above
			if (childTile.pane.getLayoutY() + childTile.pane.getHeight() < parentTile.pane.getLayoutY()) {
				double[] parentCoords = { parentTile.pane.getLayoutX(), parentTile.pane.getLayoutY() };
				double[] childCoords = { childTile.pane.getLayoutX() + childTile.pane.getWidth(),
						childTile.pane.getLayoutY() + childTile.pane.getHeight() };

				quantButton.setLayoutX((parentCoords[0] + childCoords[0]) / 2);
				quantButton.setLayoutY(((parentCoords[1] + childCoords[1]) / 2) + 40);
				quantBox.setLayoutX((parentCoords[0] + childCoords[0]) / 2);
				quantBox.setLayoutY(((parentCoords[1] + childCoords[1]) / 2) + 10);
				quantButton.setOnAction(new EventHandler<ActionEvent>() {

					public void handle(ActionEvent clickQuantButton) {
						quantLabel = new Label(quantBox.getText());
						quantLabel.setMinWidth(120);
						quantLabel.setLayoutX((parentCoords[0] + childCoords[0]) / 2);
						quantLabel.setLayoutY((parentCoords[1] + childCoords[1]) / 2);
						editQuant.setLayoutX((parentCoords[0] + childCoords[0]) / 2);
						editQuant.setLayoutY(((parentCoords[1] + childCoords[1]) / 2) + 20);
						quantLabel.setMaxWidth(120);
						layout.getChildren().add(quantLabel);
						layout.getChildren().add(editQuant);
						layout.getChildren().remove(quantBox);
						layout.getChildren().remove(quantButton);
					}
				});
				editQuant.setOnAction(new EventHandler<ActionEvent>() {
					public void handle(ActionEvent clickEditEvent) {
						TextInputDialog input = new TextInputDialog();
						input.setHeaderText("Enter New quantifier: ");
						input.setHeight(50);
						input.setWidth(120);
						Optional<String> answer = input.showAndWait();
						quantLabel.setText(answer.get());
					}

				});
				return new Arrow(parentCoords[0], parentCoords[1], childCoords[0], childCoords[1], 5);
				// child is below
			} else if (childTile.pane.getLayoutY() > parentTile.pane.getHeight() + parentTile.pane.getLayoutY()) {
				double[] parentCoords = { parentTile.pane.getLayoutX(),
						parentTile.pane.getLayoutY() + parentTile.pane.getHeight() };
				double[] childCoords = { childTile.pane.getLayoutX() + childTile.pane.getWidth(),
						childTile.pane.getLayoutY() };

				quantButton.setLayoutX((parentCoords[0] + childCoords[0]) / 2);
				quantButton.setLayoutY(((parentCoords[1] + childCoords[1]) / 2) + 40);
				quantBox.setLayoutX((parentCoords[0] + childCoords[0]) / 2);
				quantBox.setLayoutY(((parentCoords[1] + childCoords[1]) / 2) + 10);
				quantButton.setOnAction(new EventHandler<ActionEvent>() {

					public void handle(ActionEvent clickQuantButton) {
						quantLabel = new Label(quantBox.getText());
						quantLabel.setMinWidth(120);
						quantLabel.setLayoutX((parentCoords[0] + childCoords[0]) / 2);
						quantLabel.setLayoutY((parentCoords[1] + childCoords[1]) / 2);
						editQuant.setLayoutX((parentCoords[0] + childCoords[0]) / 2);
						editQuant.setLayoutY(((parentCoords[1] + childCoords[1]) / 2) + 20);
						quantLabel.setMaxWidth(120);
						layout.getChildren().add(quantLabel);
						layout.getChildren().add(editQuant);
						layout.getChildren().remove(quantBox);
						layout.getChildren().remove(quantButton);
					}
				});
				editQuant.setOnAction(new EventHandler<ActionEvent>() {
					public void handle(ActionEvent clickEditEvent) {
						TextInputDialog input = new TextInputDialog();
						input.setHeaderText("Enter New quantifier: ");
						input.setHeight(50);
						input.setWidth(120);
						Optional<String> answer = input.showAndWait();
						quantLabel.setText(answer.get());
					}

				});
				return new Arrow(parentCoords[0], parentCoords[1], childCoords[0], childCoords[1], 5);
				// child is in between on left
			} else {
				double[] parentCoords = { parentTile.pane.getLayoutX(),
						parentTile.pane.getLayoutY() + parentTile.pane.getHeight() / 2 };
				double[] childCoords = { childTile.pane.getLayoutX() + childTile.pane.getWidth(),
						childTile.pane.getLayoutY() + childTile.pane.getHeight() / 2 };

				quantButton.setLayoutX((parentCoords[0] + childCoords[0]) / 2);
				quantButton.setLayoutY(((parentCoords[1] + childCoords[1]) / 2) + 40);
				quantBox.setLayoutX((parentCoords[0] + childCoords[0]) / 2);
				quantBox.setLayoutY(((parentCoords[1] + childCoords[1]) / 2) + 10);
				quantButton.setOnAction(new EventHandler<ActionEvent>() {

					public void handle(ActionEvent clickQuantButton) {
						quantLabel = new Label(quantBox.getText());
						quantLabel.setMinWidth(120);
						quantLabel.setLayoutX((parentCoords[0] + childCoords[0]) / 2);
						quantLabel.setLayoutY((parentCoords[1] + childCoords[1]) / 2);
						editQuant.setLayoutX((parentCoords[0] + childCoords[0]) / 2);
						editQuant.setLayoutY(((parentCoords[1] + childCoords[1]) / 2) + 20);
						quantLabel.setMaxWidth(120);
						layout.getChildren().add(quantLabel);
						layout.getChildren().add(editQuant);
						layout.getChildren().remove(quantBox);
						layout.getChildren().remove(quantButton);
					}
				});
				editQuant.setOnAction(new EventHandler<ActionEvent>() {
					public void handle(ActionEvent clickEditEvent) {
						TextInputDialog input = new TextInputDialog();
						input.setHeaderText("Enter New quantifier: ");
						input.setHeight(50);
						input.setWidth(120);
						Optional<String> answer = input.showAndWait();
						quantLabel.setText(answer.get());
					}

				});
				return new Arrow(parentCoords[0], parentCoords[1], childCoords[0], childCoords[1], 5);
			}
		} // Parent is below and in between left and right
		else if ((parentTile.pane.getLayoutY() > childTile.pane.getLayoutY() + childTile.pane.getHeight())) {
			double[] parentCoords = { parentTile.pane.getLayoutX(), parentTile.pane.getLayoutY() };
			double[] childCoords = { childTile.pane.getLayoutX(), childTile.pane.getLayoutY() };

			parentCoords[0] += parentTile.pane.getWidth() / 2.0;
			childCoords[0] += parentTile.pane.getWidth() / 2.0;
			childCoords[1] += childTile.pane.getHeight();

			quantButton.setLayoutX((parentCoords[0] + childCoords[0]) / 2);
			quantButton.setLayoutY(((parentCoords[1] + childCoords[1]) / 2) + 40);
			quantBox.setLayoutX((parentCoords[0] + childCoords[0]) / 2);
			quantBox.setLayoutY(((parentCoords[1] + childCoords[1]) / 2) + 10);
			quantButton.setOnAction(new EventHandler<ActionEvent>() {

				public void handle(ActionEvent clickQuantButton) {
					quantLabel = new Label(quantBox.getText());
					quantLabel.setMinWidth(120);
					quantLabel.setLayoutX((parentCoords[0] + childCoords[0]) / 2);
					quantLabel.setLayoutY((parentCoords[1] + childCoords[1]) / 2);
					editQuant.setLayoutX((parentCoords[0] + childCoords[0]) / 2);
					editQuant.setLayoutY(((parentCoords[1] + childCoords[1]) / 2) + 20);
					quantLabel.setMaxWidth(120);
					layout.getChildren().add(quantLabel);
					layout.getChildren().add(editQuant);
					layout.getChildren().remove(quantBox);
					layout.getChildren().remove(quantButton);
				}
			});
			editQuant.setOnAction(new EventHandler<ActionEvent>() {
				public void handle(ActionEvent clickEditEvent) {
					TextInputDialog input = new TextInputDialog();
					input.setHeaderText("Enter New quantifier: ");
					input.setHeight(50);
					input.setWidth(120);
					Optional<String> answer = input.showAndWait();
					quantLabel.setText(answer.get());
				}

			});

			return new Arrow(parentCoords[0], parentCoords[1], childCoords[0], childCoords[1], 5);
		} // Parent is above and in between left and right
		else {
			double[] parentCoords = { parentTile.pane.getLayoutX(), parentTile.pane.getLayoutY() };
			double[] childCoords = { childTile.pane.getLayoutX(), childTile.pane.getLayoutY() };

			parentCoords[0] += parentTile.pane.getWidth() / 2.0;
			parentCoords[1] += parentTile.pane.getHeight();
			childCoords[0] += parentTile.pane.getWidth() / 2.0;

			quantButton.setLayoutX((parentCoords[0] + childCoords[0]) / 2);
			quantButton.setLayoutY(((parentCoords[1] + childCoords[1]) / 2) + 40);
			quantBox.setLayoutX((parentCoords[0] + childCoords[0]) / 2);
			quantBox.setLayoutY(((parentCoords[1] + childCoords[1]) / 2) + 10);
			quantButton.setOnAction(new EventHandler<ActionEvent>() {

				public void handle(ActionEvent clickQuantButton) {
					quantLabel = new Label(quantBox.getText());
					quantLabel.setMinWidth(120);
					quantLabel.setLayoutX((parentCoords[0] + childCoords[0]) / 2);
					quantLabel.setLayoutY((parentCoords[1] + childCoords[1]) / 2);
					editQuant.setLayoutX((parentCoords[0] + childCoords[0]) / 2);
					editQuant.setLayoutY(((parentCoords[1] + childCoords[1]) / 2) + 20);
					quantLabel.setMaxWidth(120);
					layout.getChildren().add(quantLabel);
					layout.getChildren().add(editQuant);
					layout.getChildren().remove(quantBox);
					layout.getChildren().remove(quantButton);
				}
			});
			editQuant.setOnAction(new EventHandler<ActionEvent>() {
				public void handle(ActionEvent clickEditEvent) {
					TextInputDialog input = new TextInputDialog();
					input.setHeaderText("Enter New quantifier: ");
					input.setHeight(50);
					input.setWidth(120);
					Optional<String> answer = input.showAndWait();
					quantLabel.setText(answer.get());
				}

			});
			return new Arrow(parentCoords[0], parentCoords[1], childCoords[0], childCoords[1], 5);
		}
	}

	/**
	 * Update arrow for the modifier's child and parent. Takes in an environment
	 * which the parent and child have been modified and creates the new arrow made
	 * in between them both. Based on the position of the tiles this function
	 * automatically figures out from where on each tile it should be drawn to and
	 * from.
	 * 
	 * @author eric
	 * 
	 * @return new arrow to be used during rendering
	 */
	public Arrow updateArrow(GUIEnvironment env, Group layout) {
		GUITile parentTile = env.getTileFor(parent);
		GUITile childTile = env.getTileFor(child);

		if (parentTile != null && childTile != null) {
			if ((parentTile.getVirtualX() + parentTile.pane.getWidth()) < childTile.getVirtualX()) {
				// If child is above and to the right do this arrow draw:
				if (childTile.getVirtualY() + childTile.pane.getHeight() < parentTile.getVirtualY()) {
					double[] parentCoords = {
							parentTile.pane.getTranslateX() + parentTile.layoutX + parentTile.pane.getWidth(),
							parentTile.pane.getTranslateY() + parentTile.layoutY };
					double[] childCoords = { childTile.pane.getTranslateX() + childTile.layoutX,
							childTile.pane.getTranslateY() + childTile.layoutY + childTile.pane.getHeight() };

					quantLabel.setLayoutX((parentTile.getVirtualX() + childTile.getVirtualX()) / 2);
					quantLabel.setLayoutY((parentTile.getVirtualY() + childTile.getVirtualY()) / 2);
					editQuant.setLayoutX((parentTile.getVirtualX() + childTile.getVirtualX()) / 2);
					editQuant.setLayoutY(((parentTile.getVirtualY() + childTile.getVirtualY()) / 2) + 20);
					editQuant.setOnAction(new EventHandler<ActionEvent>() {
						public void handle(ActionEvent clickEditEvent) {
							TextInputDialog input = new TextInputDialog();
							input.setHeaderText("Enter New quantifier: ");
							input.setHeight(50);
							input.setWidth(120);
							Optional<String> answer = input.showAndWait();
							quantLabel.setText(answer.get());
						}

					});

					return new Arrow(parentCoords[0], parentCoords[1], childCoords[0], childCoords[1], 5);
				} // If child is below and to the right do this arrow draw:
				else if (childTile.getVirtualY() > parentTile.pane.getHeight() + parentTile.getVirtualY()) {
					double[] parentCoords = {
							parentTile.pane.getTranslateX() + parentTile.layoutX + parentTile.pane.getWidth(),
							parentTile.pane.getTranslateY() + parentTile.layoutY + parentTile.pane.getHeight() };
					double[] childCoords = { childTile.pane.getTranslateX() + childTile.layoutX,
							childTile.pane.getTranslateY() + childTile.layoutY };

					quantLabel.setLayoutX((parentTile.getVirtualX() + childTile.getVirtualX()) / 2);
					quantLabel.setLayoutY((parentTile.getVirtualY() + childTile.getVirtualY()) / 2);
					editQuant.setLayoutX((parentTile.getVirtualX() + childTile.getVirtualX()) / 2);
					editQuant.setLayoutY(((parentTile.getVirtualY() + childTile.getVirtualY()) / 2) + 20);
					editQuant.setOnAction(new EventHandler<ActionEvent>() {
						public void handle(ActionEvent clickEditEvent) {
							TextInputDialog input = new TextInputDialog();
							input.setHeaderText("Enter New quantifier: ");
							input.setHeight(50);
							input.setWidth(120);
							Optional<String> answer = input.showAndWait();
							quantLabel.setText(answer.get());
						}

					});

					return new Arrow(parentCoords[0], parentCoords[1], childCoords[0], childCoords[1], 5);
				} // If child is to right and in between up and down
				else {
					double[] parentCoords = {
							parentTile.pane.getTranslateX() + parentTile.layoutX + parentTile.pane.getWidth(),
							parentTile.pane.getTranslateY() + parentTile.layoutY + parentTile.pane.getHeight() / 2 };
					double[] childCoords = { childTile.pane.getTranslateX() + childTile.layoutX,
							childTile.pane.getTranslateY() + childTile.layoutY + childTile.pane.getHeight() / 2 };

					quantLabel.setLayoutX((parentTile.getVirtualX() + childTile.getVirtualX()) / 2);
					quantLabel.setLayoutY((parentTile.getVirtualY() + childTile.getVirtualY()) / 2);
					editQuant.setLayoutX((parentTile.getVirtualX() + childTile.getVirtualX()) / 2);
					editQuant.setLayoutY(((parentTile.getVirtualY() + childTile.getVirtualY()) / 2) + 20);
					editQuant.setOnAction(new EventHandler<ActionEvent>() {
						public void handle(ActionEvent clickEditEvent) {
							TextInputDialog input = new TextInputDialog();
							input.setHeaderText("Enter New quantifier: ");
							input.setHeight(50);
							input.setWidth(120);
							Optional<String> answer = input.showAndWait();
							quantLabel.setText(answer.get());
						}

					});

					return new Arrow(parentCoords[0], parentCoords[1], childCoords[0], childCoords[1], 5);
				}
			} // if child is on the left
			else if (parentTile.getVirtualX() > childTile.getVirtualX() + childTile.pane.getWidth()) {
				// child is above
				if (childTile.getVirtualY() + childTile.pane.getHeight() < parentTile.getVirtualY()) {
					double[] parentCoords = { parentTile.pane.getTranslateX() + parentTile.layoutX,
							parentTile.pane.getTranslateY() + parentTile.layoutY };
					double[] childCoords = {
							childTile.pane.getTranslateX() + childTile.layoutX + childTile.pane.getWidth(),
							childTile.pane.getTranslateY() + childTile.layoutY + childTile.pane.getHeight() };

					quantLabel.setLayoutX((parentTile.getVirtualX() + childTile.getVirtualX()) / 2);
					quantLabel.setLayoutY((parentTile.getVirtualY() + childTile.getVirtualY()) / 2);
					editQuant.setLayoutX((parentTile.getVirtualX() + childTile.getVirtualX()) / 2);
					editQuant.setLayoutY(((parentTile.getVirtualY() + childTile.getVirtualY()) / 2) + 20);
					editQuant.setOnAction(new EventHandler<ActionEvent>() {
						public void handle(ActionEvent clickEditEvent) {
							TextInputDialog input = new TextInputDialog();
							input.setHeaderText("Enter New quantifier: ");
							input.setHeight(50);
							input.setWidth(120);
							Optional<String> answer = input.showAndWait();
							quantLabel.setText(answer.get());
						}

					});

					return new Arrow(parentCoords[0], parentCoords[1], childCoords[0], childCoords[1], 5);
					// child is below
				} else if (childTile.getVirtualY() > parentTile.pane.getHeight() + parentTile.getVirtualY()) {
					double[] parentCoords = { parentTile.pane.getTranslateX() + parentTile.layoutX,
							parentTile.pane.getTranslateY() + parentTile.layoutY + parentTile.pane.getHeight() };
					double[] childCoords = {
							childTile.pane.getTranslateX() + childTile.layoutX + childTile.pane.getWidth(),
							childTile.pane.getTranslateY() + childTile.layoutY };

					quantLabel.setLayoutX((parentTile.getVirtualX() + childTile.getVirtualX()) / 2);
					quantLabel.setLayoutY((parentTile.getVirtualY() + childTile.getVirtualY()) / 2);
					editQuant.setLayoutX((parentTile.getVirtualX() + childTile.getVirtualX()) / 2);
					editQuant.setLayoutY(((parentTile.getVirtualY() + childTile.getVirtualY()) / 2) + 20);
					editQuant.setOnAction(new EventHandler<ActionEvent>() {
						public void handle(ActionEvent clickEditEvent) {
							TextInputDialog input = new TextInputDialog();
							input.setHeaderText("Enter New quantifier: ");
							input.setHeight(50);
							input.setWidth(120);
							Optional<String> answer = input.showAndWait();
							quantLabel.setText(answer.get());
						}

					});

					return new Arrow(parentCoords[0], parentCoords[1], childCoords[0], childCoords[1], 5);
					// child is in between up and down on left
				} else {
					double[] parentCoords = { parentTile.pane.getTranslateX() + parentTile.layoutX,
							parentTile.pane.getTranslateY() + parentTile.layoutY + parentTile.pane.getHeight() / 2 };
					double[] childCoords = {
							childTile.pane.getTranslateX() + childTile.layoutX + childTile.pane.getWidth(),
							childTile.pane.getTranslateY() + childTile.layoutY + childTile.pane.getHeight() / 2 };

					quantLabel.setLayoutX((parentTile.getVirtualX() + childTile.getVirtualX()) / 2);
					quantLabel.setLayoutY((parentTile.getVirtualY() + childTile.getVirtualY()) / 2);
					editQuant.setLayoutX((parentTile.getVirtualX() + childTile.getVirtualX()) / 2);
					editQuant.setLayoutY(((parentTile.getVirtualY() + childTile.getVirtualY()) / 2) + 20);
					editQuant.setOnAction(new EventHandler<ActionEvent>() {
						public void handle(ActionEvent clickEditEvent) {
							TextInputDialog input = new TextInputDialog();
							input.setHeaderText("Enter New quantifier: ");
							input.setHeight(50);
							input.setWidth(120);
							Optional<String> answer = input.showAndWait();
							quantLabel.setText(answer.get());
						}

					});

					return new Arrow(parentCoords[0], parentCoords[1], childCoords[0], childCoords[1], 5);
				}
			} // Parent is below and in between left and right
			else if ((parentTile.getVirtualY() > childTile.getVirtualY() + childTile.pane.getHeight())) {
				double[] parentCoords = { parentTile.pane.getTranslateX() + parentTile.layoutX,
						parentTile.pane.getTranslateY() + parentTile.layoutY };
				double[] childCoords = { childTile.pane.getTranslateX() + childTile.layoutX,
						childTile.pane.getTranslateY() + childTile.layoutY };

				parentCoords[0] += parentTile.pane.getWidth() / 2.0;
				childCoords[0] += parentTile.pane.getWidth() / 2.0;
				childCoords[1] += childTile.pane.getHeight();

				quantLabel.setLayoutX((parentTile.getVirtualX() + childTile.getVirtualX()) / 2);
				quantLabel.setLayoutY((parentTile.getVirtualY() + childTile.getVirtualY()) / 2);
				editQuant.setLayoutX((parentTile.getVirtualX() + childTile.getVirtualX()) / 2);
				editQuant.setLayoutY(((parentTile.getVirtualY() + childTile.getVirtualY()) / 2) + 20);
				editQuant.setOnAction(new EventHandler<ActionEvent>() {
					public void handle(ActionEvent clickEditEvent) {
						TextInputDialog input = new TextInputDialog();
						input.setHeaderText("Enter New quantifier: ");
						input.setHeight(50);
						input.setWidth(120);
						Optional<String> answer = input.showAndWait();
						quantLabel.setText(answer.get());
					}

				});

				return new Arrow(parentCoords[0], parentCoords[1], childCoords[0], childCoords[1], 5);
			} // Parent is above and in between left and right
			else if ((parentTile.getVirtualY() < childTile.getVirtualY())) {
				double[] parentCoords = { parentTile.pane.getTranslateX() + parentTile.layoutX,
						parentTile.pane.getTranslateY() + parentTile.layoutY };
				double[] childCoords = { childTile.pane.getTranslateX() + childTile.layoutX,
						childTile.pane.getTranslateY() + childTile.layoutY };

				parentCoords[0] += parentTile.pane.getWidth() / 2.0;
				parentCoords[1] += parentTile.pane.getHeight();
				childCoords[0] += parentTile.pane.getWidth() / 2.0;

				quantLabel.setLayoutX((parentTile.getVirtualX() + childTile.getVirtualX()) / 2);
				quantLabel.setLayoutY((parentTile.getVirtualY() + childTile.getVirtualY()) / 2);
				editQuant.setLayoutX((parentTile.getVirtualX() + childTile.getVirtualX()) / 2);
				editQuant.setLayoutY(((parentTile.getVirtualY() + childTile.getVirtualY()) / 2) + 20);
				editQuant.setOnAction(new EventHandler<ActionEvent>() {
					public void handle(ActionEvent clickEditEvent) {
						TextInputDialog input = new TextInputDialog();
						input.setHeaderText("Enter New quantifier: ");
						input.setHeight(50);
						input.setWidth(120);
						Optional<String> answer = input.showAndWait();
						quantLabel.setText(answer.get());
					}

				});

				return new Arrow(parentCoords[0], parentCoords[1], childCoords[0], childCoords[1], 5);
			}
		}

		return new Arrow(0, 0, 0, 0, 0);
	}
}
