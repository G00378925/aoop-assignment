package ie.atu.sw;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

/**
 * This class is dedicated to rendering stuff to the screen,
 * you need to provide your own {@code Graphics2D} context.
 * 
 * @author Declan Kelly (g00378925@atu.ie)
 *
 */
public class Renderer {
	/** Height and width of the tiles */
	private static final int TILE_WIDTH = 128;
	private static final int TILE_HEIGHT = 64;
	
	/** These are the colours to be used in 2D mode */
	private static Color[] COLOURS = {
		Color.WHITE, Color.GREEN, Color.GRAY,
		Color.DARK_GRAY, Color.ORANGE, Color.CYAN,
		Color.YELLOW, Color.PINK, Color.BLACK
	};

	/**
	 * Converts the X coordinate from cartesian plane to isometric coordinates.
	 *
	 * @param x the X coordinate in the cartesian plane
	 * @param y the Y coordinate in the cartesian plane
	 * @return the isometric X coordinate
	 */
	private static int getIsoX(int x, int y) {
		/* Pan camera to right */
		int rshift = (GameView.DEFAULT_VIEW_SIZE / 2) - (TILE_WIDTH / 2) + (x - y);
		return (x - y) * (TILE_WIDTH / 2) + rshift;
	}

	/**
	 * Converts the Y coordinate from cartesian plane to isometric coordinates.
	 *
	 * @param x the X coordinate in the cartesian plane
	 * @param y the Y coordinate in the cartesian plane
	 * @return the isometric Y coordinate
	 */
	private static int getIsoY(int x, int y) {
		return (x + y) * (TILE_HEIGHT / 2);
	}

	/**
	 * Converts the given cartesian coordinates to isometric coordinates.
	 *
	 * @param x an X coordinate in the cartesian plane
	 * @param y an Y coordinate in the cartesian plane
	 * @return the isometric coordinates as a {@link Position} object
	 */
	private static Position getIso(int x, int y) {
		return new Position(getIsoX(x, y), getIsoY(x, y));
	}

	/**
	 * This method will render the {@link TileMatrix} to the screen.
	 * 
	 * @param graphics2DContext Current Graphics2D context
	 * @param ground {@link TileMatrix} of ground tiles to be rendered
	 * @param objects {@link TileMatrix} of objects to be rendered
	 * @param isIsometric true if you want render in pseudo-3D mode
	 */
	public static void renderMatrices(Graphics2D graphics2DContext, TileMatrix ground,
			TileMatrix objects, boolean isIsometric) {
		
		int x = 0, y = 0;

		// Loop over the view-port and paint the game
		for (int row = 0; row < ground.getMatrixLength(); row++) {
			for (int col = 0; col < ground.getMatrixLength(); col++) {
				BufferedImage groundImage = ground.getSprite(row, col).getImage();
				BufferedImage objectImage = objects.getSprite(row, col).getImage();

				if (isIsometric) { // Paint the ground tiles
					x = Renderer.getIsoX(col, row);
					y = Renderer.getIsoY(col, row);
					graphics2DContext.drawImage(groundImage, x, y, null);
				} else { // Paint 2D
					x = col * Renderer.TILE_WIDTH;
					y = row * Renderer.TILE_HEIGHT;
					graphics2DContext.setColor(COLOURS[1 + ground.getSpriteID(row, col)]);
					graphics2DContext.fillRect(x, y, Renderer.TILE_WIDTH, Renderer.TILE_WIDTH);
				}

				graphics2DContext.drawImage(objectImage, x, y, null);
			}
		}
	}
	
	/**
	 * Use this method to render your {@link GameObject} to the screen
	 * 
	 * @param graphics2DContext Current Graphics2D context
	 * @param gameObject The {@link GameObject} you want to be rendered to the screen
	 */
	public static void renderGameObject(Graphics2D graphics2DContext, GameObject gameObject) {
		int x = gameObject.getPosition().getX();
		int y = gameObject.getPosition().getY();
		Position screenPosition = getIso(x, y);

		graphics2DContext.drawImage(gameObject.getCurrentSprite(),
				screenPosition.getX(), screenPosition.getY(), null);
	}

}
