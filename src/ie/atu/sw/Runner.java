package ie.atu.sw;

/**
 * This class starts that game, creates an instance of {@link GameView}.
 * Loads the ground and objects {@link TileMatrix} from the files.
 */
public class Runner {
	/** Amount of spiders to be spawned in */
	private static final int SPIDER_COUNT = 10;

	/*
	 * This matrix is a representation of where objects (things) in the game that
	 * are placed on the ground, including plants etc.
	 */
	private static TileMatrix ground, objects;

	/**
	 * This method sets up a {@link GameView} for the game, configures it with a model and some
	 * objects, and then creates a window (a Swing JFrame) to display it in.
	 */
	private static void setupGameView() {
		/* Create and configure a view */
		new GameView.Builder()
		    .setWindowTitle("ATU - B.Sc. in Computing (Software Development)")
		    .setWindowSize(1000, 1000)
		    .setGroundMatrix(ground)
		    .setObjectsMatrix(objects)
		    .build();
	}

	/**
	 * This method is the main method for the application,
	 * doesn't accept any command-line parameters.
	 * 
	 * @param args there are no command-line parameters accepted.
	 * @throws Exception When can't find the tilemap files
	 */
	public static void main(String[] args) throws Exception {
		/* Read in ground and object tile matrices */
		ground = TileMatrix.loadMatrixFromFile("./resources/tilemaps/model.txt",
				Sprite.loadSpriteList("./resources/images/ground"));
		objects = TileMatrix.loadMatrixFromFile("./resources/tilemaps/objects.txt",
				Sprite.loadSpriteList("./resources/images/objects"));

		/* Spawning in the player entity, and adding them to the GameObjectStore */
		GameObjectStore.getInstance().add("Player", new Player(new Position(3, 3), objects));

		/* Spawning in the spiders, and adding them to the GameObjectStore */
		for (int i = 0; i < SPIDER_COUNT; i++) {
			GameObjectStore.getInstance().add("Spider " + i, new Spider(new Position(i, i)));
		}
		
		/*
		 * WARNING: Do not mess with the following unless you know what you are doing.
		 * Never run a GUI in the same thread as the main method... The mode of
		 * execution below is asynchronous:
		 */
		javax.swing.SwingUtilities.invokeLater(new Runnable() {
			public void run() { // Template method....
				/*
				 * ----------------------------------------
				 * Control Keys
				 * ----------------------------------------
				 * Use the arrow keys to move the
				 * player. Move Player: 'X' Toggle View: 'Z'
				 * ----------------------------------------
				 */
				setupGameView();
			}
		});
	}
}