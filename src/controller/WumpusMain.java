//Chris Quevedo

package controller;

import java.util.Observer;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import model.Map;
import views.ImageView;
import views.TextView;

/**
 * This class is the main function that runs the application.  Will create 
 * a stage and scene to hold an menubar with options.  The main layout is a borderpane
 * with the top being the menubar, the center being the changeable view, and the bottom 
 * being the buttons for shooting the arrow.
 */

public class WumpusMain extends Application {

	public static void main(String[] args) {
		launch(args);
	}
  
	private BorderPane pane;	
	private Map game;
	private MenuBar menuBar;
  
	private Observer curView;
	private Observer imageView;
	private Observer textView;
  
	private Button north;
	private Button south;
	private Button east;
	private Button west;
	
	
	/**
	 * start will set up the stage and scene.  Functions are called to setup
	 * the menus, buttons, views, and keylistener.
	 */
	@Override
	public void start(Stage stage) throws Exception {
		stage.setTitle("Hunt the Wumpus");
		pane = new BorderPane();
		Scene scene = new Scene(pane, 480, 600);
		game = new Map();
		
		//set menus
		setMenus();
		pane.setTop(menuBar);
		
		//set buttons
		setButtons();
	

	    // Set up the views
	    imageView = new ImageView(game);
	    textView = new TextView(game);
	    game.addObserver(imageView);
	    game.addObserver(textView);
	   
	    //setViewTo(imageView);
	    setViewTo(imageView);
	    
	    //set keylistener
	    KeyListener keyL = new KeyListener();
		scene.setOnKeyPressed(keyL);
		
		//show the game
		stage.setScene(scene);
		stage.show();
	}
	/**
	 * setMenus will make all of the menuitems and put them in appropriate menus.
	 * Then the options menu will be put into the menubar.  Sets the menus up to
	 * menuListeners
	 */
	private void setMenus() {
		MenuItem newGame = new MenuItem("New Game");
		MenuItem iView = new MenuItem("Images");
		MenuItem tView = new MenuItem("Text");
		
		Menu view = new Menu("View");
		view.getItems().addAll(iView, tView);
		Menu options = new Menu("Options");
		options.getItems().addAll(newGame, view);
		
		menuBar = new MenuBar();
		menuBar.getMenus().addAll(options);
		
		//add menulisteners
		MenuItemListener menuListener = new MenuItemListener();
		newGame.setOnAction(menuListener);
		iView.setOnAction(menuListener);
		tView.setOnAction(menuListener);
	}
	
	/**
	 * setButtons will make a gridpane to place the buttons. Sets the buttons up to
	 * buttonListeners
	 */
	public void setButtons() {
		GridPane bPane = new GridPane();
		north = new Button("N");
		south = new Button("S");
		east = new Button("E");
		west = new Button("W");
		bPane.add(north, 3, 0);
		bPane.add(west, 2, 1);
		bPane.add(east, 4, 1);
		bPane.add(south, 3, 2);
		pane.setBottom(bPane);
		ButtonHandler bH = new ButtonHandler();
		north.setOnAction(bH);
		south.setOnAction(bH);
		east.setOnAction(bH);
		west.setOnAction(bH);
	}
	
	/**
	 * sets the curView to a newView
	 * @param newView is the view to be changed to
	 */
	private void setViewTo(Observer newView) {
	    pane.setCenter(null);
	    curView = newView;
	    pane.setCenter((Node) curView);
	  }
	
	/**
	 * MenuItemListener will determine which menuitem was clicked and 
	 * act accordingly.  The choices are to start a new game or to change the view.
	 * @author Chris Quevedo
	 */
	private class MenuItemListener implements EventHandler<ActionEvent> {

		/**
		 * handle will get the source of the event and take action accordingly.  
		 * Will either start new game or change view.
		 */
	    @Override
	    public void handle(ActionEvent e) {
	    	// Find out the text of the JMenuItem that was just clicked
	    	String text = ((MenuItem) e.getSource()).getText();
	    	if (text.equals("New Game")) {
	    		game.newGame();
	    	}
	    	else if (text.equals("Images")) {
	    		setViewTo(imageView);
	    	}
	    	else if(text.equals("Text")) {
	    		setViewTo(textView);
	    	}
	   	}
	}
	
	/**
	 * KeyListener will respond to a key being pressed.  If the key
	 * is one of the direction arrows, the player will be moved in that direction.
	 * @author Chris Quevedo
	 */
	private class KeyListener implements EventHandler<KeyEvent>{

		/**
		 * handle will respond to a key being pressed.  If the key is 
		 * one of the direction arrows, the player will be moved that direction.
		 */
		@Override
		public void handle(KeyEvent event) {
			if(event.getCode() == KeyCode.UP) {
				game.moveHunter("n");
			}
			else if(event.getCode() == KeyCode.DOWN) {
				game.moveHunter("s");
			}
			else if(event.getCode() == KeyCode.RIGHT) {
				game.moveHunter("e");
			}
			else if(event.getCode() == KeyCode.LEFT) {
				game.moveHunter("w");
			}
		}
	}
	
	/**
	 * ButtonHandler will handle button presses.
	 * @author Chris Quevedo
	 */
	private class ButtonHandler implements EventHandler<ActionEvent>{

		/**
		 * handle will determine the source of the event.
		 * The event will come from one of four buttons signifying
		 * a direction to shoot the arrow.  
		 */
		@Override
		public void handle(ActionEvent event) {
			Button buttonClicked = (Button) event.getSource();
			if(buttonClicked == north) {
				game.shootArrow("n");
			}
			else if(buttonClicked == south) {
				game.shootArrow("s");
			}
			else if(buttonClicked == east) {
				game.shootArrow("e");
			}
			else if(buttonClicked == west) {
				game.shootArrow("w");
			}
		}
		
	}
}