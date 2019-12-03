package data;

import java.util.Optional;

import javafx.event.*;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.TextInputDialog;
import javafx.scene.paint.Color;
import javafx.scene.shape.LineTo;
import javafx.scene.shape.MoveTo;
import javafx.scene.shape.Path;
import utility.GUITile;

/**
 * @author kn0412 on GitHub
 *         https://gist.github.com/kn0412/2086581e98a32c8dfa1f69772f14bca4
 * 
 *         Arrow drawing class for a GUI
 */
public class Arrow extends Path {
	private static final double defaultArrowHeadSize = 5.0;
	public Label quantLabel = new Label();
	public Button editQuant = new Button("edit quantifier");
	public Button quantButton = new Button("add quantifier");
	public TextField quantBox = new TextField("quantifier");
	public boolean quantAdded = false;
	public boolean isRemoved = false;
	/**
	 * Take origin x and y, destination x and y, and a double Size for the arrows
	 * head
	 * 
	 * @param startX
	 * @param startY
	 * @param endX
	 * @param endY
	 * @param arrowHeadSize
	 */
	public Arrow(double startX, double startY, double endX, double endY, double arrowHeadSize) {
		super();
		strokeProperty().bind(fillProperty());
		setFill(Color.BLACK);

		quantLabel.setLayoutX((startX + endX) / 2);
		quantLabel.setLayoutY((startY + endY) / 2);
		quantButton.setLayoutX((startX + endX) / 2);
		quantButton.setLayoutY(((startY + endY) / 2) + 40);
		quantBox.setLayoutX((startX + endX) / 2);
		quantBox.setLayoutY(((startY + endY) / 2) + 10);
		editQuant.setLayoutX((startX + endX) / 2);
		editQuant.setLayoutY(((startY + endY) / 2) + 20);
		editQuant.setVisible(false);
		quantLabel.setVisible(false);
		quantButton.setOnAction(new EventHandler<ActionEvent>() {

			public void handle(ActionEvent clickQuantButton) {
				quantLabel.setText(quantBox.getText());
				quantLabel.setMinWidth(120);
				quantLabel.setMaxWidth(120);
				quantLabel.setVisible(true);
				editQuant.setVisible(true);
				quantBox.setVisible(false);
				quantButton.setVisible(false);
				quantAdded = true;
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

		// Line
		getElements().add(new MoveTo(startX, startY));
		getElements().add(new LineTo(endX, endY));

		// ArrowHead
		double angle = Math.atan2((endY - startY), (endX - startX)) - Math.PI / 2.0;
		double sin = Math.sin(angle);
		double cos = Math.cos(angle);
		// point1
		double x1 = (-1.0 / 2.0 * cos + Math.sqrt(3) / 2 * sin) * arrowHeadSize + endX;
		double y1 = (-1.0 / 2.0 * sin - Math.sqrt(3) / 2 * cos) * arrowHeadSize + endY;
		// point2
		double x2 = (1.0 / 2.0 * cos + Math.sqrt(3) / 2 * sin) * arrowHeadSize + endX;
		double y2 = (1.0 / 2.0 * sin - Math.sqrt(3) / 2 * cos) * arrowHeadSize + endY;

		getElements().add(new LineTo(x1, y1));
		getElements().add(new LineTo(x2, y2));
		getElements().add(new LineTo(endX, endY));
		
	}
	/**
	 * Origin x and y and Destination x and y with default arrow head size 5.0
	 * 
	 * @param startX
	 * @param startY
	 * @param endX
	 * @param endY
	 */
	public Arrow(double startX, double startY, double endX, double endY) {
		this(startX, startY, endX, endY, defaultArrowHeadSize);
	}
}