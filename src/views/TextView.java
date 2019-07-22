//Chris Quevedo

package views;

import java.util.Observable;
import java.util.Observer;

import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import model.Map;

/**
 * This class is the view to represented with the character board.  It is rooted as a borderpane.
 * A canvas is in top of the pane.  Simply, the gameBoard will be drawn as text on the canvas in a 
 * boardlike fashion.  The buttons remain at the bottom to shoot the arrow.
 * @author Chris Quevedo
 */
public class TextView extends BorderPane implements Observer {
	
	private Map game;
	private Canvas canvas;
	private GraphicsContext gc;
	private Label message;

	/**
	 * TextView will create a new TextView connected to newGame.
	 * Sets up a canvas to draw the text into and has a label under to 
	 * represent the state of the game at that moment.
	 * @param newGame
	 */
	public TextView(Map newGame) {
		game = newGame;
		canvas = new Canvas(480,480);
		gc = canvas.getGraphicsContext2D();
		message = new Label("Safe for now");
		this.setTop(canvas);
		gc.setFill(Color.PINK);
		gc.fillRect(0, 0, 700, 700);
		this.setTop(canvas);
		this.setCenter(message);
		gc.setFill(Color.BLACK);
		updateText();
	}

	/**
	 * Draws the board char by char.
	 */
	private void updateText() {
		gc.setFill(Color.PINK);
		gc.fillRect(0, 0, 700, 700);
		gc.setFill(Color.BLACK);
		Font font = new Font("Verdana", 30.0);
		gc.setFont(font);
		String text = "";
		for(int i = 0; i < 12; i++) {
			for(int j = 0; j < 12; j++) {
				int x = j*40 + 10;
				int y = i*40 + 30;
				text = game.getCave()[i][j].toString();
				if(!game.isOver()) {
					gc.fillText(text, x, y);
				}
				else {
					if(text.equals("X")) {
						if(game.getCave()[i][j].hasWumpus()) {
							gc.fillText("W", x, y);
						}
						else if(game.getCave()[i][j].hasPit()) {
							gc.fillText("P", x, y);
						}
						else if(game.getCave()[i][j].hasGoop()) {
							gc.fillText("G", x, y);
						}
						else if(game.getCave()[i][j].hasBlood()) {
							gc.fillText("B", x, y);
						}
						else if(game.getCave()[i][j].hasSlime()) {
							gc.fillText("S", x, y);
						}
						else {
							gc.fillText("X", x, y);
						}
					}
					else {
						gc.fillText(text, x, y);
					}
				}
			}
		}
		
	}

	/**
	 * Calls updateText to update the board.  Also gets the state of the game
	 * to update the label.
	 */
	@Override
	public void update(Observable arg0, Object arg1) {
		updateText();
		int row = game.getPlayer().getRow();
		int col = game.getPlayer().getCol();
		if(game.didWin()) {
			message.setText("Your arrow hit the wumpus.  You win.");
			return;
		}
		if(game.isOver()) {
			message.setText("You shot yourself.  You lose.");
		}
		if(game.getCave()[row][col].hasWumpus()) {
			message.setText("You walked into the Wumpus.  You lose.");
		}
		else if(game.getCave()[row][col].hasPit()) {
			message.setText("You fell down a bottomless pit.  You lose.");
		}
		else if(game.getCave()[row][col].hasGoop()) {
			message.setText("I can hear the wind\nI smell something foul");
		}
		else if(game.getCave()[row][col].hasSlime()) {
			message.setText("I can hear the wind");
		}
		else if(game.getCave()[row][col].hasBlood()) {
			message.setText("I smell something foul");
		}
		else if(game.isOver()) {
			message.setText("You just shot yourself.  You lose.");
		}
		else {
			message.setText("Safe for now");
		}
	}

}
