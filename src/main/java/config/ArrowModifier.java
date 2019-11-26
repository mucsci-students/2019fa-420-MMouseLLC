package config;

import java.util.Optional;

import data.Arrow;
import data.GUIEnvironment;
import data.Relationship;
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
	/**
	 * 
	 * @param parent
	 * @param child
	 */
	public ArrowModifier(UMLItem parent, UMLItem child) {
		this.parent = parent;
		this.child = child;
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
		//TODO
		//child.addParent(parent);
		//parent.addChild(child);
		
		env.addRelationship(new Relationship(parent, child));
		//if child is on the right of the parent
		if((parentTile.pane.getLayoutX() + parentTile.pane.getWidth()) < childTile.pane.getLayoutX()) {
			//If child is above and to the right do this arrow draw:
			if(childTile.pane.getLayoutY() + childTile.pane.getHeight() < parentTile.pane.getLayoutY()) {
				double[] parentCoords = { parentTile.pane.getLayoutX() + parentTile.pane.getWidth(), parentTile.pane.getLayoutY() };
				double[] childCoords = { childTile.pane.getLayoutX(), childTile.pane.getLayoutY() + childTile.pane.getHeight() };
				return new Arrow(parentCoords[0], parentCoords[1], childCoords[0], childCoords[1], 5);
				// if child is on right and below parent
			} else if (childTile.pane.getLayoutY() > parentTile.pane.getHeight() + parentTile.pane.getLayoutY()) {
				double[] parentCoords = { parentTile.pane.getLayoutX() + parentTile.pane.getWidth(),
						parentTile.pane.getLayoutY() + parentTile.pane.getHeight() };
				double[] childCoords = { childTile.pane.getLayoutX(), childTile.pane.getLayoutY() };
				
				return new Arrow(parentCoords[0], parentCoords[1], childCoords[0], childCoords[1], 5);
				// if child is in between on the right
			} else {
				double[] parentCoords = { parentTile.pane.getLayoutX() + parentTile.pane.getWidth(),
						parentTile.pane.getLayoutY() + parentTile.pane.getHeight() / 2 };
				double[] childCoords = { childTile.pane.getLayoutX(),
						childTile.pane.getLayoutY() + childTile.pane.getHeight() / 2 };
				
				return new Arrow(parentCoords[0], parentCoords[1], childCoords[0], childCoords[1], 5);
			}

		} // if child is on the left of the parent
		else if (parentTile.pane.getLayoutX() > childTile.pane.getLayoutX() + childTile.pane.getWidth()) {
			// child is above
			if (childTile.pane.getLayoutY() + childTile.pane.getHeight() < parentTile.pane.getLayoutY()) {
				double[] parentCoords = { parentTile.pane.getLayoutX(), parentTile.pane.getLayoutY() };
				double[] childCoords = { childTile.pane.getLayoutX() + childTile.pane.getWidth(),
						childTile.pane.getLayoutY() + childTile.pane.getHeight() };
				
				return new Arrow(parentCoords[0], parentCoords[1], childCoords[0], childCoords[1], 5);
				// child is below
			} else if (childTile.pane.getLayoutY() > parentTile.pane.getHeight() + parentTile.pane.getLayoutY()) {
				double[] parentCoords = { parentTile.pane.getLayoutX(),
						parentTile.pane.getLayoutY() + parentTile.pane.getHeight() };
				double[] childCoords = { childTile.pane.getLayoutX() + childTile.pane.getWidth(),
						childTile.pane.getLayoutY() };
				
				return new Arrow(parentCoords[0], parentCoords[1], childCoords[0], childCoords[1], 5);
				// child is in between on left
			} else {
				double[] parentCoords = { parentTile.pane.getLayoutX(),
						parentTile.pane.getLayoutY() + parentTile.pane.getHeight() / 2 };
				double[] childCoords = { childTile.pane.getLayoutX() + childTile.pane.getWidth(),
						childTile.pane.getLayoutY() + childTile.pane.getHeight() / 2 };
				
				return new Arrow(parentCoords[0], parentCoords[1], childCoords[0], childCoords[1], 5);
			}
		} // Parent is below and in between left and right
		else if ((parentTile.pane.getLayoutY() > childTile.pane.getLayoutY() + childTile.pane.getHeight())) {
			double[] parentCoords = { parentTile.pane.getLayoutX(), parentTile.pane.getLayoutY() };
			double[] childCoords = { childTile.pane.getLayoutX(), childTile.pane.getLayoutY() };

			parentCoords[0] += parentTile.pane.getWidth() / 2.0;
			childCoords[0] += childTile.pane.getWidth() / 2.0;
			childCoords[1] += childTile.pane.getHeight();
			

			return new Arrow(parentCoords[0], parentCoords[1], childCoords[0], childCoords[1], 5);
		} // Parent is above and in between left and right
		else {
			double[] parentCoords = { parentTile.pane.getLayoutX(), parentTile.pane.getLayoutY() };
			double[] childCoords = { childTile.pane.getLayoutX(), childTile.pane.getLayoutY() };

			parentCoords[0] += parentTile.pane.getWidth() / 2.0;
			parentCoords[1] += parentTile.pane.getHeight();
			childCoords[0] += childTile.pane.getWidth() / 2.0;
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
					
					return new Arrow(parentCoords[0], parentCoords[1], childCoords[0], childCoords[1], 5);
				} // If child is below and to the right do this arrow draw:
				else if (childTile.getVirtualY() > parentTile.pane.getHeight() + parentTile.getVirtualY()) {
					double[] parentCoords = {
							parentTile.pane.getTranslateX() + parentTile.layoutX + parentTile.pane.getWidth(),
							parentTile.pane.getTranslateY() + parentTile.layoutY + parentTile.pane.getHeight() };
					double[] childCoords = { childTile.pane.getTranslateX() + childTile.layoutX,
							childTile.pane.getTranslateY() + childTile.layoutY };
					

					return new Arrow(parentCoords[0], parentCoords[1], childCoords[0], childCoords[1], 5);
				} // If child is to right and in between up and down
				else {
					double[] parentCoords = {
							parentTile.pane.getTranslateX() + parentTile.layoutX + parentTile.pane.getWidth(),
							parentTile.pane.getTranslateY() + parentTile.layoutY + parentTile.pane.getHeight() / 2 };
					double[] childCoords = { childTile.pane.getTranslateX() + childTile.layoutX,
							childTile.pane.getTranslateY() + childTile.layoutY + childTile.pane.getHeight() / 2 };
					

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
					

					return new Arrow(parentCoords[0], parentCoords[1], childCoords[0], childCoords[1], 5);
					// child is below
				} else if (childTile.getVirtualY() > parentTile.pane.getHeight() + parentTile.getVirtualY()) {
					double[] parentCoords = { parentTile.pane.getTranslateX() + parentTile.layoutX,
							parentTile.pane.getTranslateY() + parentTile.layoutY + parentTile.pane.getHeight() };
					double[] childCoords = {
							childTile.pane.getTranslateX() + childTile.layoutX + childTile.pane.getWidth(),
							childTile.pane.getTranslateY() + childTile.layoutY };
					

					return new Arrow(parentCoords[0], parentCoords[1], childCoords[0], childCoords[1], 5);
					// child is in between up and down on left
				} else {
					double[] parentCoords = { parentTile.pane.getTranslateX() + parentTile.layoutX,
							parentTile.pane.getTranslateY() + parentTile.layoutY + parentTile.pane.getHeight() / 2 };
					double[] childCoords = {
							childTile.pane.getTranslateX() + childTile.layoutX + childTile.pane.getWidth(),
							childTile.pane.getTranslateY() + childTile.layoutY + childTile.pane.getHeight() / 2 };

					return new Arrow(parentCoords[0], parentCoords[1], childCoords[0], childCoords[1], 5);
				}
			} // Parent is below and in between left and right
			else if ((parentTile.getVirtualY() > childTile.getVirtualY() + childTile.pane.getHeight())) {
				double[] parentCoords = { parentTile.pane.getTranslateX() + parentTile.layoutX,
						parentTile.pane.getTranslateY() + parentTile.layoutY };
				double[] childCoords = { childTile.pane.getTranslateX() + childTile.layoutX,
						childTile.pane.getTranslateY() + childTile.layoutY };

				parentCoords[0] += parentTile.pane.getWidth() / 2.0;
				childCoords[0] += childTile.pane.getWidth() / 2.0;
				childCoords[1] += childTile.pane.getHeight();
				
				return new Arrow(parentCoords[0], parentCoords[1], childCoords[0], childCoords[1], 5);
			} // Parent is above and in between left and right
			else if ((parentTile.getVirtualY() < childTile.getVirtualY())) {
				double[] parentCoords = { parentTile.pane.getTranslateX() + parentTile.layoutX,
						parentTile.pane.getTranslateY() + parentTile.layoutY };
				double[] childCoords = { childTile.pane.getTranslateX() + childTile.layoutX,
						childTile.pane.getTranslateY() + childTile.layoutY };

				parentCoords[0] += parentTile.pane.getWidth() / 2.0;
				parentCoords[1] += parentTile.pane.getHeight();
				childCoords[0] += childTile.pane.getWidth() / 2.0;

				return new Arrow(parentCoords[0], parentCoords[1], childCoords[0], childCoords[1], 5);
			}
		}

		return new Arrow(0, 0, 0, 0, 0);
	}
}
