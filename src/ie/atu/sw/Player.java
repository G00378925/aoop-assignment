package ie.atu.sw;

/**
 * Class for the {@link Player} character in the game.
 * The player can move around, this class will handle events such as moving, redrawing.
 * 
 * @author Declan Kelly (g00378925@atu.ie)
 */
public class Player extends GameObject {
	// Entity specific data is encapsulated inside the entity's GameObject class
	private static final String SPRITE_LOCATION = "./resources/images/sprites/default";
	
	private static final AudioClip MOVE_SOUND = 
			AudioClip.loadAudioClip("./resources/sounds/move.wav");
	
	private static final AudioClip VICTORY_SOUND = 
			AudioClip.loadAudioClip("./resources/sounds/victory.wav");
	
	private boolean playerWon = false; // Go to treasure chest to win!
	private TileMatrix objectMatrix; // Curent object matrix in the game.
	
	/**
	 * Construct a new {@link Player} object.
	 * 
	 * @param playerLocation {@link Position} object containing the player position.
	 * @param objectMatrix {@link TileMatrix} object containing the objects matrix.
	 * @throws Exception thrown when the sprite file can't be found.
	 */
	public Player(Position playerLocation, TileMatrix objectMatrix) throws Exception {
		super(playerLocation, SPRITE_LOCATION);
		this.objectMatrix = objectMatrix;
	}
	
	/**
	 * Move the {@link Player} into its new position
	 */
	public void move() {
		super.move();
		MOVE_SOUND.play();
	}
	
	/** Called on {@link GameObject} initialization. */
	public void start() {
	} // Ignore. The GameObjectable interface contracts some implementation (ISP).
	
	/**
	 * Called once per a frame.
	 */
	public void update() {
		int currentObjectID = this.objectMatrix.getSpriteID(this.getPosition());

		// Check if I already won, and check if standing on treasure chest
		if (!this.playerWon && currentObjectID == TileMatrix.TREASURE_CHEST_ID) {
			VICTORY_SOUND.play();
			this.playerWon = true;
		}
	}
}
