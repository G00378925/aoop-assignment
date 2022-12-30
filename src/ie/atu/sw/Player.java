package ie.atu.sw;

/**
 * Class for the {@link Player} character in the game.
 * The player can move around, this class will handle events such as moving, redrawing.
 * 
 * @author Declan Kelly (g00378925@atu.ie)
 */
public class Player extends GameObject {
	private static final String SPRITE_LOCATION = "./resources/images/sprites/default";
	
	private static final AudioClip MOVE_SOUND = 
			AudioClip.loadAudioClip("./resources/sounds/move.wav");
	
	private static final AudioClip VICTORY_SOUND = 
			AudioClip.loadAudioClip("./resources/sounds/victory.wav");
	
	private boolean playerWon = false;
	private TileMatrix objectMatrix;
	
	/**
	 * Construct a new {@link Player} object.
	 * 
	 * @param playerLocation {@link Position} object containing the player position.
	 * @param objectMatrix {@link TileMatrix} object containing the objects matrix.
	 * @throws Exception thrown when sprite file are not found.
	 */
	public Player(Position playerLocation, TileMatrix objectMatrix) throws Exception {
		super(playerLocation, SPRITE_LOCATION);
		this.objectMatrix = objectMatrix;
	}
	
	public void move() {
		super.move();
		MOVE_SOUND.play();
	}
	
	/** Called on {@link GameObject} initialisation. */
	public void start() {
	} // Ignore. The CharacterEntityable interface contracts some implementation (ISP).
	
	/**
	 * Call once per a frame.
	 */
	public void update() {
		int currentObjectID = this.objectMatrix.getSpriteID(this.getPosition());

		if (!this.playerWon && currentObjectID == TileMatrix.TREASURE_CHEST_ID) {
			VICTORY_SOUND.play();
			this.playerWon = true;
		}
	}
}
