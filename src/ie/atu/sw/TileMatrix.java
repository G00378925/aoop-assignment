package ie.atu.sw;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.LinkedList;
import java.util.Scanner;

/**
 * TileMatrix encapsulates the byte matrix, and array of {@link Sprite} for that tile matrix.
 * 
 * @author Declan Kelly (g00378925@atu.ie)
 */
public class TileMatrix {
	/** This is the ID of default/empty tile */
	public static final int DEFAULT_IMAGE_ID = 0;
	/** This is the ID of the treasure chest tile */
	public static final int TREASURE_CHEST_ID = 3;
	
	private byte matrix[][]; // Byte matrix containing the ID's of the tiles
	private Sprite spriteList[]; // Sprite array contain the sprites
	
	/**
	 * Construct new {@link TileMatrix} object,
	 * can't instantiate directly use loadMatrixFromFile inside this class.
	 * 
	 * @param matrix byte matrix contain the IDs of the tiles
	 * @param spriteList Array of sprite objects
	 */
	private TileMatrix(byte[][] matrix, Sprite[] spriteList) {
		this.matrix = matrix;
		this.spriteList = spriteList;
	}
	
	/**
	 * Fetch {@link Sprite} object for x and y position.
	 * 
	 * @param x X position on cartesian plane
	 * @param y Y position on cartesian plane
	 * @return {@link Sprite} object at that x and y position.
	 */
	public Sprite getSprite(int x, int y) {
		int spriteIndex = matrix[x][y];
		if (spriteIndex > DEFAULT_IMAGE_ID)
			return this.spriteList[matrix[x][y]];
		return this.spriteList[DEFAULT_IMAGE_ID];
	}
	
	/**
	 * Fetch sprite ID for x and y position.
	 * 
	 * @param x X position on cartesian plane
	 * @param y Y position on cartesian plane
	 * @return Sprite ID at that position
	 */
	public int getSpriteID(int x, int y) {
		if (x < 0 || y < 0 || x >= matrix.length || y >= matrix.length) return 0;
		return this.matrix[x][y];
	}
	
	/**
	 * Fetch sprite ID for that {@link Position} object.
	 * 
	 * @param position {@link Position} object contain the position
	 * @return Sprite ID at that position
	 */
	public int getSpriteID(Position position) {
		// Due to the Player sprite being double the size, I must add 2 to the X
		return getSpriteID(position.getY(), position.getX() + 2);
	}
	
	/**
	 * Returns length of matrix (width/height)
	 * @return length of the {@link TileMatrix}.
	 */
	public int getMatrixLength() {
		return this.matrix.length;
	}
	
	/**
	 * Loads a matrix of tiles from a file and returns a {@link TileMatrix} object.
	 * 
	 * @param fileName the name of the file to load the matrix from.
	 * @param spriteList an array of {@link Sprite} to use for the tiles in the matrix
	 * @return a {@link TileMatrix} object containing the matrix.
	 * @throws FileNotFoundException if the matrix file can't be found.
	 */
    public static TileMatrix loadMatrixFromFile(String fileName, Sprite[] spriteList) throws FileNotFoundException {
    	var matrixFile = new File(fileName);
    	var matrixScanner = new Scanner(matrixFile);
    	var matrixLL = new LinkedList<byte[]>();
    	
    	// Read the matrix in from the file, and put each row in the matrixLL linked list.
    	while (matrixScanner.hasNextLine()) {
    		String[] matrixRowStr = matrixScanner.nextLine().split(",");
    		byte[] matrixRow = new byte[matrixRowStr.length];
    		for (int i = 0; i < matrixRow.length; i++)
    			matrixRow[i] = (byte) Integer.parseInt(matrixRowStr[i].trim());
    		
    		matrixLL.add(matrixRow);
    	}
    	matrixScanner.close();
    	
    	// Convert the linked list containing the rows, to the matrix
    	byte[][] internalMatrix = new byte[matrixLL.size()][matrixLL.size()];
    	for (int i = 0; i < matrixLL.size(); i++) {
    		internalMatrix[i] = matrixLL.get(i);
    	}
    	
    	return new TileMatrix(internalMatrix, spriteList);
    }
}
