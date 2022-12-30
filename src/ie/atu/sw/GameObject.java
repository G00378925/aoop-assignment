package ie.atu.sw;

import java.awt.image.BufferedImage;

/**
 * This is the {@link GameObject} for entity's such as the {@link Player} or {@link Spider},
 * game engines such as Unity take a similar approach,
 * with each {@link GameObject} in the game having a start and update method
 * 
 * @author Declan Kelly (g00378925@atu.ie)
 *
 */
abstract public class GameObject implements GameObjectable {
	private Direction currentDirection = Direction.DOWN; // The current orientation of the character
	private int currentImageIndex = 0; // The current image index.
	private Position currentPosition; // The current x, y position
	private Sprite[][] spriteSheet; // The images used in the animation
	
	/**
	 * Set the current spritesheet for this {@link GameObject}.
	 * 
	 * @param spriteSheet contain an array of {@link Sprite} for this {@link GameObject}.
	 */
	protected void setSpritesheet(Sprite[][] spriteSheet) {
		this.spriteSheet = spriteSheet;
	}

	/** Go to the sprite for the current direction in the spritesheet */
	protected void nextSprite() {
		this.currentImageIndex++;
		this.currentImageIndex %= spriteSheet[currentDirection.getOrientation()].length;
	}

	/**
	 * This method allows you to change the colour of all the sprites in the {@link GameObject} spritesheet.
	 * @param newColour you want your spritesheet to be.
	 */
	protected void changeColour(int newColour) {
		Sprite[][] spriteSheet = this.spriteSheet;
		new Thread() {
			public void run() {
		        for (int i = 0; i < spriteSheet.length; i++) {
			        for (int j = 0; j < spriteSheet[i].length; j++) {
			            spriteSheet[i][j].changeColour(newColour);
			        }
                }
		    }
		}.start();
	}
	
	/**
	 * Construct a new {@link GameObject}, you need a {@link Position} and String location of sprites
	 * 
	 * @param characterLocation {@link Position} object containing X and Y location.
	 * @param spriteLocation String location of the sprites for the current {@link GameObject}
	 * @throws Exception thrown when can't find sprite files.
	 */
	public GameObject(Position characterLocation, String spriteLocation) throws Exception {
		this.currentPosition = characterLocation;
		Sprite[] spriteList = Sprite.loadSpriteList(spriteLocation);
		this.setSpritesheet(Sprite.generateSpriteSheet(spriteList));
	}

	/**
	 * Fetch current sprite for this {@link GameObject}.
	 * 
	 * @return a BufferedImage that is the current sprite.
	 */
	public BufferedImage getCurrentSprite() {
		int orientationValue = currentDirection.getOrientation();
		return spriteSheet[orientationValue][this.currentImageIndex].getImage();
	}

	/**
	 * Set a direction for this {@link GameObject}.
	 * @param direction {@link Direction} object for this {@link GameObject}.
	 */
	public void setDirection(Direction direction) {
		currentDirection = direction;
	}
	
	/**
	 * Returns the current positon.
	 * @return {@link Position} for this {@link GameObject}.
	 */
	public Position getPosition() {
		return this.currentPosition;
	}

	/**
	 * Move the {@link GameObject} into its new position
	 */
	public void move() {
		int oldX = currentPosition.getX(), oldY = currentPosition.getY();
		
		switch (currentDirection.getOrientation()) { // Update the Position
		    case 1 -> currentPosition.setY(currentPosition.getY() + 1); // UP
		    case 2 -> currentPosition.setX(currentPosition.getX() - 1); // DOWN
		    case 3 -> currentPosition.setX(currentPosition.getX() + 1); // LEFT
		    default -> currentPosition.setY(currentPosition.getY() - 1); // RIGHT
		}
		
		int newX = currentPosition.getX(), newY = currentPosition.getY();
		
		if (newX < -3 || newY < -3 || newX >= 10 || newY >= 10) {
			// If the player or the spiders go out of bounds, revert
			currentPosition.setX(oldX);
			currentPosition.setY(oldY);
		}
	}
	
	/**
	 * Classes that inherit from the class, must implement this method,
	 * it is called on {@link GameObject} initialisation.
	 */
	abstract public void start();
	
	/**
	 * Classes that inherit from the class, must implement this method,
	 * it is called once per a frame.
	 */
	abstract public void update();
}
