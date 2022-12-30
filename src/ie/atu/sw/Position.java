package ie.atu.sw;

/**
 * {@link Position} represents a position in a 2D coordinate system.
 * 
 * @author Declan Kelly (g00378925@atu.ie)
 */
public class Position {
	/** Internal x, y for the {@link Position} object */
	private int x, y;
	
	/**
	 * Construct a new {@link Position} object,
	 * using X and Y coordinates.
	 * 
	 * @param x the X coordinate
	 * @param y the Y coordinate
	 */
	public Position(int x, int y) {
		this.x = x;
		this.y = y;
	}
	
	/**
	 * Get X current coordinate
	 * @return current X coordinate
	 */
	int getX() {
		return this.x;
	}
	
	/**
	 * Set the X coordinate
	 * @param x new X coordinate
	 */
	void setX(int x) {
		this.x = x;
	}

	/**
	 * Get Y current coordinate
	 * @return current Y coordinate
	 */
	int getY() {
		return this.y;
	}
	
	/**
	 * Set the Y coordinate
	 * @param y new Y coordinate
	 */
	void setY(int y) {
		this.y = y;
	}
}