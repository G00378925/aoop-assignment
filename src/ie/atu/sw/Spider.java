package ie.atu.sw;

import java.util.concurrent.ThreadLocalRandom;

/**
 * Class for the {@link Spider} in the game.
 * The spider can move around, this class will handle events such as moving, redrawing.
 * 
 * @author Declan Kelly (g00378925@atu.ie)
 */
public class Spider extends GameObject {
	/** Spider have their own sprites */
	private static final String SPRITE_LOCATION = "./resources/images/sprites/spiders";
	
	/**
	 * Construct a new {@link Spider} object.
	 * 
	 * @param spiderLocation containing the {@link Position} of the {@link Spider}.
	 * @throws Exception thrown when sprites can't be found.
	 */
	public Spider(Position spiderLocation) throws Exception {
		super(spiderLocation, SPRITE_LOCATION);
	}
	
	/** Called on {@link GameObject} initialization. */
	public void start() {
		int atuColours[] = {
				0xff001a79, 0xff7bb9cb, 0xff005b5e,
				0xffffe8d4, 0xffff791e
		};
		
		// The spider can decide their own colour randomly
		int randomColourIndex = ThreadLocalRandom.current().nextInt(atuColours.length);
		int randomColour = atuColours[randomColourIndex];
		this.changeColour(randomColour);
	}
	
	/**
	 * Called once per a frame.
	 */
	public void update() {
		Direction[] directionArray = Direction.values();
		Direction newDirection = directionArray[ThreadLocalRandom.current().nextInt(directionArray.length)];
		
		// Spider randomly pick a direction, and move to it.
		this.setDirection(newDirection);
		this.nextSprite();
		this.move();
	}
}
