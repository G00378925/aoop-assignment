package ie.atu.sw;

import java.awt.image.*;
import java.io.File;
import java.util.Arrays;

import javax.imageio.ImageIO;

/**
 * This class represents a sprite, which is an image that can be rendered on the screen.
 * 
 * @author Declan Kelly (g00378925@atu.ie)
 */
public class Sprite {
	/** Number of current possible directions */
	private static final int DIRECTION_COUNT = 4;

	private BufferedImage internalSprite;
	
	/**
	 * Construct a new {@link Sprite} object,
	 * using a {@link BufferedImage} object.
	 * 
	 * @param bufferedImage you want the {@link Sprite} to be.
	 */
	public Sprite(BufferedImage bufferedImage) {
		this.internalSprite = bufferedImage;
	}
	
	/**
	 * Return the encapsulated BufferedImage sprite.
	 * 
	 * @return BufferedImage sprite.
	 */
	public BufferedImage getImage() {
		return this.internalSprite;
	}

	/**
	 * Set the internal colour to be a new colour
	 * 
	 * @param newColour Integer of the new colour
	 */
	public void changeColour(int newColour) {
		BufferedImage image = internalSprite;

		for (int y = 0; y < image.getHeight(); y++) { // Loop over the 2D image pixel-by-pixel
			for (int x = 0; x < image.getWidth(); x++) {
				// Set the pixel colour at (x, y) to red.
				int pixel = image.getRGB(x, y);
				int alpha = (pixel >> 24) & 0xff;
				if (alpha > 0) { // 0xAARRGGBB Alpha, Red, Green, Blue
					image.setRGB(x, y, newColour);
				}
			}
		}

	}

	/**
	 * 
	 * Factory that generates spritesheet matrix of {@link Sprite} objects
	 * 
	 * @param spriteList Array containg {@link Sprite} objects.
	 * @return a new matrix spritesheet.
	 */
	public static Sprite[][] generateSpriteSheet(Sprite[] spriteList) {
		Sprite spriteSheet[][] = new Sprite[DIRECTION_COUNT][spriteList.length / DIRECTION_COUNT];

		for (int i = 0; i < spriteList.length; i++) {
			int rowLength = spriteSheet[0].length;
			int row = Math.floorDiv(i, rowLength), col = i % rowLength;
			spriteSheet[row][col] = spriteList[i];
		}
		return spriteSheet;
	}

	/**
	 * Loads an array of {@link Sprite} from a directory.
	 * 
	 * @param directory String containing the directory to load {@link Sprite} from.
	 * @return an array of {@link Sprite} 
	 * @throws Exception if there is an error reading the {@link Sprite} from the directory.
	 */
	public static Sprite[] loadSpriteList(String directory) throws Exception {
		File d = new File(directory);
		File[] files = d.listFiles((f) -> f.getName().endsWith(".png")); // List of filtered files
		Arrays.sort(files, (s, t) -> s.getName().compareTo(t.getName()));

		Sprite[] img = new Sprite[files.length];
		for (int i = 0; i < files.length; i++)
			img[i] = new Sprite(ImageIO.read(files[i]));

		return img;
	}
}