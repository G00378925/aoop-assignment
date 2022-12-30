package ie.atu.sw;

import java.awt.*;
import java.awt.event.*;

import javax.swing.*;

/**
 * Class that implements the game view for the game,
 * handles key press events and game loop.
 */
public class GameView extends JPanel implements KeyListener {
	/** Size of the default game view */
	public static final int DEFAULT_VIEW_SIZE = 1280;
	
    private static final long serialVersionUID = 1L;

	/** Contain the current ground and objects TileMatrix objects. */
	private TileMatrix ground, objects;

	/** Controls the repaint interval. */
	private Timer timer;
	
	/** Toggle between 2D and Isometric (Z key) */
	private boolean isIsometric = true; // 

	/**
	 * Construct a new {@link GameView} object,
	 * using ground and objects {@link TileMatrix} objects, loaded by Runner.
	 * @param ground a {@link TileMatrix} object.
	 * @param objects a {@link TileMatrix} object.
	 */
	public GameView(TileMatrix ground, TileMatrix objects) {
		this.ground = ground;
		this.objects = objects;

		setBackground(Color.WHITE); // Really? All this config is multi-step and maybe a candidate for a builder.
		setDoubleBuffered(true); // Each image is buffered twice to avoid tearing / stutter

		timer = new Timer(100, this::actionPerformed); // calls the actionPerformed() method every 100ms
		timer.start(); // Start the timer
		
		Dimension d = new Dimension(DEFAULT_VIEW_SIZE, DEFAULT_VIEW_SIZE / 2);
		this.setPreferredSize(d);
		this.setMinimumSize(d);
		this.setMaximumSize(d);
		
		for (GameObjectable go : GameObjectStore.getInstance().getGameObjectArray()) {
			go.start();
		}
	}

	/**
	 * Allows switching between isometric and 2D views.
	 * The current view is toggled and the display is repainted.
	 */
	public void toggleView() {
		isIsometric = !isIsometric;
		this.repaint();
	}
	
	/**
	 * This is called each time the timer reaches zero
	 * @param actionEvent information about the current key press.
	 */
	public void actionPerformed(ActionEvent actionEvent) { 
		// Call update on all Entity's in the GameObjectStore
		for (GameObjectable go : GameObjectStore.getInstance().getGameObjectArray()) {
			go.update();
		}

		this.repaint();
	}

	/**
	 * Paints the component on the screen.
	 * This method is called automatically by the UI system and should not be called directly.
	 *
	 * @param graphics the graphics context to use for painting
	 */
	public void paintComponent(Graphics graphics) {
		super.paintComponent(graphics);
		// Graphics2D has more features to use
		Graphics2D graphics2D = (Graphics2D) graphics;
		
		
		Renderer.renderMatrices(graphics2D, ground, objects, isIsometric);

		for (GameObjectable go : GameObjectStore.getInstance().getGameObjectArray())
			Renderer.renderGameObject(graphics2D, (GameObject) go);
	}

	/**
	 * This KeyListener method enables us to plant an observer on GameView and
	 * listen for key-press events.
	 * 
	 * @param keyEvent information about current key press.
	 */
	public void keyPressed(KeyEvent keyEvent) { // Handle key-press events
		Player currentPlayer = ((Player) GameObjectStore.getInstance().get("Player"));
		
		switch (keyEvent.getKeyCode()) {
		    case KeyEvent.VK_RIGHT, KeyEvent.VK_D -> {
		    	currentPlayer.setDirection(Direction.RIGHT);
		    }
		    case KeyEvent.VK_LEFT, KeyEvent.VK_A -> {
		    	currentPlayer.setDirection(Direction.LEFT);
		    }
		    case KeyEvent.VK_UP, KeyEvent.VK_W -> {
		    	currentPlayer.setDirection(Direction.UP);
		    }
		    case KeyEvent.VK_DOWN, KeyEvent.VK_S -> {
		    	currentPlayer.setDirection(Direction.DOWN);
		    }
		    case KeyEvent.VK_Z -> toggleView();
		    case KeyEvent.VK_X -> currentPlayer.move();
		}
	}

	public void keyReleased(KeyEvent e) {
	} // Ignore. The KeyListener interface contracts some implementation (ISP).

	public void keyTyped(KeyEvent e) {
	} // Ignore. The KeyListener interface contracts some implementation (ISP).
	
	/**
	 * This class will help you building an instance of {@link GameView}.
	 */
	public static class Builder {
		private String windowTitle;
		private TileMatrix groundMatrix, objectsMatrix;
		private int windowSizeX, windowSizeY;
		
	    /**
	     * Sets the title of the window for the {@link GameView}.
	     *
	     * @param windowTitle the title of the window
	     * @return this {@code Builder} object
	     */
		public Builder setWindowTitle(String windowTitle) {
			this.windowTitle = windowTitle;
			return this;
		}
		
	    /**
	     * Sets the window size for the {@link GameView}.
	     *
	     * @param windowSizeX the width of the view window.
	     * @param windowSizeY the height of the view window.
	     * @return this {@code Builder} object
	     */
		public Builder setWindowSize(int windowSizeX, int windowSizeY) {
			this.windowSizeX = windowSizeX;
			this.windowSizeY = windowSizeY;
			return this;
		}
		
	    /**
	     * Sets the ground matrix for the {@link GameView}.
	     *
	     * @param groundMatrix the ground {@link TileMatrix}.
	     * @return this {@code Builder} object
	     */
		public Builder setGroundMatrix(TileMatrix groundMatrix) {
			this.groundMatrix = groundMatrix;
			return this;
		}
		
	    /**
	     * Sets the objects matrix for the {@link GameView}.
	     *
	     * @param objectsMatrix the objects {@link TileMatrix}.
	     * @return this {@code Builder} object
	     */
		public Builder setObjectsMatrix(TileMatrix objectsMatrix) {
			this.objectsMatrix = objectsMatrix;
			return this;
		}
		
	    /**
	     * Creates and returns a new instance of the {@link GameView} with the properties set in this {@code Builder}.
	     *
	     * @return a new instance of the {@link GameView}
	     */
		public GameView build() {
			GameView view = new GameView(this.groundMatrix, this.objectsMatrix);
			
			// Create an configure a window for rendering the view
			JFrame f = new JFrame(this.windowTitle);
			f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			f.getContentPane().setLayout(new FlowLayout());
			f.add(view);
			f.addKeyListener(view);
			f.setSize(this.windowSizeX, this.windowSizeY);
			f.setLocation(100, 100);
			f.pack();
			f.setVisible(true);
			return view;
		}
	}
}