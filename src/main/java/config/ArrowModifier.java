package config;

import data.Arrow;
import data.GUIEnvironment;
import data.UMLItem;
import utility.GUITile;

public class ArrowModifier {
	
	UMLItem parent = new UMLItem();
	UMLItem child = new UMLItem();

	public ArrowModifier(UMLItem parent , UMLItem child) {
		this.parent = parent;
		this.child = child;
	}
	
	public Arrow makeNewArrow(GUIEnvironment env) {
		
		GUITile childTile = env.getTileFor(child);
		GUITile parentTile = env.getTileFor(parent);
		child.addParent(parent);
		parent.addChild(child);
		
		//if child is on the right of the parent
		if((parentTile.pane.getLayoutX() + parentTile.pane.getWidth()) < childTile.pane.getLayoutX()) {
			//If child is above and to the right do this arrow draw:
			if(childTile.pane.getLayoutY() + childTile.pane.getHeight() < parentTile.pane.getLayoutY()) {
				double[] parentCoords = { parentTile.pane.getLayoutX() + parentTile.pane.getWidth(), parentTile.pane.getLayoutY() };
				double[] childCoords = { childTile.pane.getLayoutX(), childTile.pane.getLayoutY() + childTile.pane.getHeight() };

				return new Arrow(parentCoords[0], parentCoords[1], childCoords[0], childCoords[1], 5);
			//if child is on right and below parent
			} else if (childTile.pane.getLayoutY() > parentTile.pane.getHeight() + parentTile.pane.getLayoutY()) {
				double[] parentCoords = { parentTile.pane.getLayoutX() + parentTile.pane.getWidth(), parentTile.pane.getLayoutY() + parentTile.pane.getHeight() };
				double[] childCoords = { childTile.pane.getLayoutX(), childTile.pane.getLayoutY() };

				return new Arrow(parentCoords[0], parentCoords[1], childCoords[0], childCoords[1], 5);
			//if child is in between on the right
			} else {
				double[] parentCoords = { parentTile.pane.getLayoutX() + parentTile.pane.getWidth(), parentTile.pane.getLayoutY() + parentTile.pane.getHeight() / 2 };
				double[] childCoords = { childTile.pane.getLayoutX(), childTile.pane.getLayoutY() + childTile.pane.getHeight() / 2 };

				return new Arrow(parentCoords[0], parentCoords[1], childCoords[0], childCoords[1], 5);
			}
			
		} // if child is on the left of the parent
		else if(parentTile.pane.getLayoutX() > childTile.pane.getLayoutX() + childTile.pane.getWidth()) {
			//child is above
			if(childTile.pane.getLayoutY() + childTile.pane.getHeight() < parentTile.pane.getLayoutY()) {
				double[] parentCoords = { parentTile.pane.getLayoutX(), parentTile.pane.getLayoutY() };
				double[] childCoords = { childTile.pane.getLayoutX() + childTile.pane.getWidth(), childTile.pane.getLayoutY() + childTile.pane.getHeight() };

				return new Arrow(parentCoords[0], parentCoords[1], childCoords[0], childCoords[1], 5);
			//child is below
			} else if (childTile.pane.getLayoutY() > parentTile.pane.getHeight() + parentTile.pane.getLayoutY()) {
				double[] parentCoords = { parentTile.pane.getLayoutX(), parentTile.pane.getLayoutY() + parentTile.pane.getHeight() };
				double[] childCoords = { childTile.pane.getLayoutX() + childTile.pane.getWidth(), childTile.pane.getLayoutY() };

				return new Arrow(parentCoords[0], parentCoords[1], childCoords[0], childCoords[1], 5);
			//child is in between on left
			} else {
				double[] parentCoords = { parentTile.pane.getLayoutX(), parentTile.pane.getLayoutY() + parentTile.pane.getHeight() / 2 };
				double[] childCoords = { childTile.pane.getLayoutX() + childTile.pane.getWidth(), childTile.pane.getLayoutY() + childTile.pane.getHeight() / 2 };

				return new Arrow(parentCoords[0], parentCoords[1], childCoords[0], childCoords[1], 5);
			}
		} // Parent is below and in between left and right 
		else if((parentTile.pane.getLayoutY() > childTile.pane.getLayoutY() + childTile.pane.getHeight())) {
			double[] parentCoords = { parentTile.pane.getLayoutX(), parentTile.pane.getLayoutY() };
			double[] childCoords = { childTile.pane.getLayoutX(), childTile.pane.getLayoutY() };

			parentCoords[0] += parentTile.pane.getWidth() / 2.0;
			childCoords[0] += parentTile.pane.getWidth() / 2.0;
			childCoords[1] += childTile.pane.getHeight();

			return new Arrow(parentCoords[0], parentCoords[1], childCoords[0], childCoords[1], 5);
		} // Parent is above and in between left and right
		else {
			double[] parentCoords = { parentTile.pane.getLayoutX(), parentTile.pane.getLayoutY() };
			double[] childCoords = { childTile.pane.getLayoutX(), childTile.pane.getLayoutY() };

			parentCoords[0] += parentTile.pane.getWidth() / 2.0;
			parentCoords[1] += parentTile.pane.getHeight();
			childCoords[0] += parentTile.pane.getWidth() / 2.0;

			return new Arrow(parentCoords[0], parentCoords[1], childCoords[0], childCoords[1], 5);
		}
	}
	public Arrow updateArrow(GUIEnvironment env) {
		GUITile parentTile = env.getTileFor(parent);
		GUITile childTile = env.getTileFor(child);
		
		if(parentTile != null && childTile != null) {
			if((parentTile.pane.getLayoutX() + parentTile.pane.getWidth()) < childTile.pane.getLayoutX()) {
				//If child is above and to the right do this arrow draw:
				if(childTile.pane.getLayoutY() + childTile.pane.getHeight() < parentTile.pane.getLayoutY()) {
					double[] parentCoords = { parentTile.pane.getTranslateX() + parentTile.layoutX + parentTile.pane.getWidth(), parentTile.pane.getTranslateY() + parentTile.layoutY };
					double[] childCoords = { childTile.pane.getTranslateX() + childTile.layoutX, childTile.pane.getTranslateY() + childTile.layoutY + childTile.pane.getHeight() };

					return new Arrow(parentCoords[0], parentCoords[1], childCoords[0], childCoords[1], 5);
				} else if (childTile.pane.getLayoutY() > parentTile.pane.getHeight() + parentTile.pane.getLayoutY()) {
					double[] parentCoords = { parentTile.pane.getTranslateX() + parentTile.layoutX + parentTile.pane.getWidth(), parentTile.pane.getTranslateY() + parentTile.layoutY + parentTile.pane.getHeight() };
					double[] childCoords = { childTile.pane.getTranslateX() + childTile.layoutX, childTile.pane.getTranslateY() + childTile.layoutY };

					return new Arrow(parentCoords[0], parentCoords[1], childCoords[0], childCoords[1], 5);
				} else {
					double[] parentCoords = { parentTile.pane.getTranslateX() + parentTile.layoutX + parentTile.pane.getWidth(), parentTile.pane.getTranslateY() + parentTile.layoutY + parentTile.pane.getHeight() / 2 };
					double[] childCoords = { childTile.pane.getTranslateX() + childTile.layoutX, childTile.pane.getTranslateY() + childTile.layoutY + childTile.pane.getHeight() / 2 };

					return new Arrow(parentCoords[0], parentCoords[1], childCoords[0], childCoords[1], 5);
				}
			}else if(parentTile.pane.getLayoutX() > childTile.pane.getLayoutX() + childTile.pane.getWidth()) {
				//child is above
				if(childTile.pane.getLayoutY() + childTile.pane.getHeight() < parentTile.pane.getLayoutY()) {
					double[] parentCoords = { parentTile.pane.getTranslateX() + parentTile.layoutX, parentTile.pane.getTranslateY() + parentTile.layoutY };
					double[] childCoords = { childTile.pane.getTranslateX() + childTile.layoutX + childTile.pane.getWidth(), childTile.pane.getTranslateY() + childTile.layoutY + childTile.pane.getHeight() };

					return new Arrow(parentCoords[0], parentCoords[1], childCoords[0], childCoords[1], 5);
				//child is below
				} else if (childTile.pane.getLayoutY() > parentTile.pane.getHeight() + parentTile.pane.getLayoutY()) {
					double[] parentCoords = { parentTile.pane.getTranslateX() + parentTile.layoutX, parentTile.pane.getTranslateY() + parentTile.layoutY + parentTile.pane.getHeight() };
					double[] childCoords = { childTile.pane.getTranslateX() + childTile.layoutX + childTile.pane.getWidth(), childTile.pane.getTranslateY() + childTile.layoutY };

					return new Arrow(parentCoords[0], parentCoords[1], childCoords[0], childCoords[1], 5);
				//child is in between on left
				} else {
					double[] parentCoords = { parentTile.pane.getTranslateX() + parentTile.layoutX, parentTile.pane.getTranslateY() + parentTile.layoutY + parentTile.pane.getHeight() / 2 };
					double[] childCoords = { childTile.pane.getTranslateX() + childTile.layoutX + childTile.pane.getWidth(), childTile.pane.getTranslateY() + childTile.layoutY + childTile.pane.getHeight() / 2 };

					return new Arrow(parentCoords[0], parentCoords[1], childCoords[0], childCoords[1], 5);
				}
			}// Parent is below and in between left and right 
			else if((parentTile.pane.getLayoutY() > childTile.pane.getLayoutY() + childTile.pane.getHeight())) {
				double[] parentCoords = { parentTile.pane.getTranslateX() + parentTile.layoutX, parentTile.pane.getTranslateY() + parentTile.layoutY };
				double[] childCoords = { childTile.pane.getTranslateX() + childTile.layoutX, childTile.pane.getTranslateY() + childTile.layoutY  };

				parentCoords[0] += parentTile.pane.getWidth() / 2.0;
				childCoords[0] += parentTile.pane.getWidth() / 2.0;
				childCoords[1] += childTile.pane.getHeight();

				return new Arrow(parentCoords[0], parentCoords[1], childCoords[0], childCoords[1], 5);
			} // Parent is above and in between left and right
			else if((parentTile.pane.getLayoutY() < childTile.pane.getLayoutY())) {
				double[] parentCoords = { parentTile.pane.getTranslateX() + parentTile.layoutX, parentTile.pane.getTranslateY() + parentTile.layoutY };
				double[] childCoords = { childTile.pane.getTranslateX() + childTile.layoutX, childTile.pane.getTranslateY() + childTile.layoutY };

				parentCoords[0] += parentTile.pane.getWidth() / 2.0;
				parentCoords[1] += parentTile.pane.getHeight();
				childCoords[0] += parentTile.pane.getWidth() / 2.0;

				return new Arrow(parentCoords[0], parentCoords[1], childCoords[0], childCoords[1], 5);
			}
		}
		
		return new Arrow(0,0,0,0,0);
	}
}
