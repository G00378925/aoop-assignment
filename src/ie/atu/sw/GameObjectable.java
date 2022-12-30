package ie.atu.sw;

/**
 * Interface for a {@link GameObject}, implement the methods below,
 * and you can be a {@link GameObject} too!
 * 
 * @author Declan Kelly (g00378925@atu.ie)
 */
public interface GameObjectable {
	/**
	 * Initialises this GameObject.
	 * This method is called once when the {@link GameObject} is first created.
	 */
	public void start();
	
	/**
	 * Updates this {@link GameObject}.
	 * This method is called once per frame.
	 */
	public void update();
}
