package ie.atu.sw;

/**
 * The enum {@link Direction} defines the 4 directions of movement in the game,
 * a number is assigned to each direction.
 */
public enum Direction {
	/** Represents the UP direction */
	UP(0),
	/** Represents the DOWN direction */
	DOWN(1),
	/** Represents the LEFT direction */
	LEFT(2), 
	/** Represents the DOWN direction */
	RIGHT(3);

	private final int orientation; // integer value of the direction
	
	/**
	 * Create an new Direction object, using a orientation integer.
	 * 
	 * @param orientation Integer value of the orientation.
	 */
	private Direction(int orientation) {
		this.orientation = orientation;
	}
	
	/**
	 * Get the current orientation integer.
	 * 
	 * @return Returns the current orientation integer value.
	 */
	public int getOrientation() {
		return this.orientation;
	}
}