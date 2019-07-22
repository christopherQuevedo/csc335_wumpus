//Chris Quevedo

package views;

import java.util.Observable;
import java.util.Observer;

import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.layout.BorderPane;
import javafx.scene.paint.Color;
import model.Map;
import model.Room;

/**
 * This class represents the view that will be made with the images.  The view will be rooted
 * as a borderpane.  A canvas being the only node in the pane will be our area to draw
 * upon.  Simply a game/board is 144 squares that hold an image, or possibly a few images.  Moving the
 * arrow keys will move the hunter and will illuminate the rooms he has visited.  The buttons remain at 
 * the bottom of the screen to shoot arrow.
 */

public class ImageView extends BorderPane implements Observer {
	
	private Map game;
	private Canvas canvas;
	private GraphicsContext gc;
	private Image blood;
	private Image goop;
	private Image hunter;
	private Image pit;
	private Image slime;
	private Image wumpus;
	private Image ground;
	
	/**
	 * ImageView creates a new ImageView.  It initializes the drawing
	 * for the game to be played.
	 * @param newGame is the game in which the view observes
	 */
	public ImageView(Map newGame) {
		game = newGame;
		canvas = new Canvas(480, 480);
		gc = canvas.getGraphicsContext2D();
		gc.setFill(Color.BLACK);
		this.setTop(canvas);
		makeImages();
		updateDrawing();
	}
	
	/**
	 * makeImages takes the png files from the images folder and makes
	 * Image objects for each file
	 */
	public void makeImages() {
		blood = new Image("file:images/Blood.png", 40, 40, false, false);
		goop = new Image("file:images/Goop.png", 40, 40, false, false);
		hunter = new Image("file:images/TheHunter.png", 40, 40, false, false);
		pit = new Image("file:images/SlimePit.png", 40, 40, false, false);
		slime = new Image("file:images/Slime.png", 40, 40, false, false);
		wumpus = new Image("file:images/Wumpus.png", 40, 40, false, false);
		ground = new Image("file:images/Ground.png", 40, 40, false, false);
	}
	
	/**
	 * update will update the drawing when the state of the game changes.
	 * generally this is when the player moves or shoots the arrow
	 */
	@Override
	public void update(Observable arg0, Object arg1) {
		// TODO Auto-generated method stub
		updateDrawing();
	}

	/**
	 * updateDrawing will use the char array for the game/board/cave and 
	 * draw the game board as it is right now.
	 */
	private void updateDrawing() {
		Room[][] temp = game.getCave();
		for(int i = 0; i < 12; i++) {
			for(int j = 0; j < 12; j++) {
				int y = i * 40;
				int x = j * 40;
				gc.fillRect(x, y, 40, 40);
				if(!temp[i][j].isVisible()) {
					gc.drawImage(ground, x, y);
					if(game.isOver()) {
						if(temp[i][j].hasWumpus()) {
							gc.drawImage(wumpus, x, y);
						}
						else if(temp[i][j].hasPit()) {
							gc.drawImage(pit, x, y);
						}
						else if(temp[i][j].hasGoop()) {
							gc.drawImage(goop, x, y);
						}
						else if(temp[i][j].hasBlood()) {
							gc.drawImage(blood, x, y);
						}
						else if(temp[i][j].hasSlime()) {
							gc.drawImage(slime, x, y);
						}
					}
				}
				else {
					if(temp[i][j].hasWumpus()) {
						gc.drawImage(wumpus, x, y);
					}
					else if(temp[i][j].hasPit()) {
						gc.drawImage(pit, x, y);
					}
					else if(temp[i][j].hasGoop()) {
						gc.drawImage(goop, x, y);
					}
					else if(temp[i][j].hasBlood()) {
						gc.drawImage(blood, x, y);
					}
					else if(temp[i][j].hasSlime()) {
						gc.drawImage(slime, x, y);
					}
					if(temp[i][j].hasPlayer()) {
						gc.drawImage(hunter, x, y);
					}
				}
			}
		}
	}

}
