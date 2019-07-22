package model;

public class Room {
	
	private Wumpus wump;
	private Pit pit;
	private Blood blood;
	private Slime slime;
	private Goop goop;
	private Hunter player;
	private boolean visible;
	
	/**
	 * A Room is an element on the 12 by 12 grid that is the cave.  A room can 
	 * have many different things as shown by the instance variables.
	 */
	public Room() {
		
		wump = null;
		pit = null;
		blood = null;
		slime = null;
		goop = null;
		player = null;
		visible = false;
		
	}
	
	/**
	 * Returns true if room has a wumpus
	 * @return
	 */
	public boolean hasWumpus() {
		if(wump != null) {
			return true;
		}
		return false;
	}
	
	/**
	 * returns true if the room has a pit
	 * @return
	 */
	public boolean hasPit() {
		if(pit != null) {
			return true;
		}
		return false;
	}
	
	/**
	 * returns true if the room has goop
	 * @return
	 */
	public boolean hasGoop() {
		if(goop != null) {
			return true;
		}
		return false;
	}
	
	/**
	 * returns true if the room has blood
	 * @return
	 */
	public boolean hasBlood() {
		if(blood != null) {
			return true;
		}
		return false;
	}
	
	/**
	 * returns true if the room has slime
	 * @return
	 */
	public boolean hasSlime() {
		if(slime != null) {
			return true;
		}
		return false;
	}
	
	/**
	 * returns true if the room has the player
	 * @return
	 */
	public boolean hasPlayer() {
		if(player != null) {
			return true;
		}
		return false;
	}
	
	/**
	 * returns true if the room is visible
	 * @return
	 */
	public boolean isVisible() {
		return visible;
	}
	
	/**
	 * makes the room visible
	 */
	public void setVisible() {
		visible = true;
	}

	/**
	 * sets the room's wumpus reference to newWump
	 * @param newWump
	 */
	public void addWumpus(Wumpus newWump) {
		// TODO Auto-generated method stub
		wump = newWump;
		
	}

	/**
	 * Adds a blood object to the room
	 */
	public void addBlood() {
		// TODO Auto-generated method stub
		blood = new Blood();
		
	}

	/**
	 * adds a pit object to the room
	 */
	public void addPit() {
		// TODO Auto-generated method stub
		pit = new Pit();
	}
	
	/**
	 * Turns a room into a string for the console game.
	 */
	public String toString() {
	    
		if(!visible) {
			return "X";
		}
		else if(player != null) {
			return player.toString();
		}
		else if(wump != null) {
			return wump.toString();
		}
		else if(pit != null) {
			return pit.toString();
		}
		else if(goop != null) {
			return goop.toString();
		}
		else if(blood != null) {
			return blood.toString();
		}
		else if(slime != null) {
			return slime.toString();
		}
		return " ";
	}

	/**
	 * Adds slime to the room, and if blood is already in the room, then
	 * add some goop.
	 */
	public void addSlime() {
		// TODO Auto-generated method stub
		slime = new Slime();
		if(blood != null) {
			goop = new Goop();
		}
	}

	/**
	 * set the room's player reference to player and make the room visible
	 * @param player
	 */
	public void addPlayer(Hunter player) {
		// TODO Auto-generated method stub
		this.player = player;
		visible = true;
	}
	
	/**
	 * set the room's player reference to null as there is no longer a 
	 * player in the room.
	 */
	public void removePlayer() {
		this.player = null;
	}

}
