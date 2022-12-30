package ie.atu.sw;

import java.util.HashMap;
import java.util.Collection;

/**
 * {@link GameObjectStore} is used to store the {@link GameObject} in the game.
 * 
 * You can then query the {@link GameObjectStore}, for the name of the {@link GameObject}
 * 
 * @author Declan Kelly (g00378925@atu.ie)
 */
public class GameObjectStore {
	/** Internal HashMap storing the {@link GameObjects} in the game */
    private HashMap<String, GameObjectable> gameObjectStore;

    /** Singleton as there should only be one {@link GameObjectStore} per game */
    private static GameObjectStore instance = new GameObjectStore();
    
    private GameObjectStore() {
    	this.gameObjectStore = new HashMap<String, GameObjectable>();
    }
    
    /**
     * Returns the singleton for the {@link GameObjectStore}
     * @return {@link GameObjectStore} singleton.
     */
    public static GameObjectStore getInstance() {
    	return instance;
    }
    
    /**
     * Returns all {@link GameObject} in the {@link GameObjectStore}.
     * @return Array contain all {@link GameObject} in game.
     */
    public Collection<GameObjectable> getGameObjectArray() {
    	return gameObjectStore.values();
    }
    
    /**
     * Add a new {@link GameObject} to the {@link GameObjectStore}.
     * 
     * @param gameObjectName String that contains the name of the {@link GameObject}.
     * @param newGameObject {@link GameObject} to be stored in the store.
     */
    public void add(String gameObjectName, GameObject newGameObject) {
    	gameObjectStore.put(gameObjectName, (GameObjectable) newGameObject);
    }
    
    /**
     * This method allows you to query the {@link GameObjectStore}.
     * 
     * @param gameObjectName String containing the name of the {@link GameObject}.
     * @return a {@link GameObject} that has the name being queried.
     */
    public GameObjectable get(String gameObjectName) {
    	return gameObjectStore.get(gameObjectName);
    }
}
