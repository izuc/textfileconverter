package system;

/**
 * The interface allows for multiple ways of accessing the API.
 * It contains one method which must be implemented to ensure the
 * API can correspond to what ever way a programmer wants to access
 * the API (ie GUI, CLI or web access).
 * @author: JAVA Alliance * 
 * @date:
 * @filename: TextFilePlug.java
 * @version: 1.1
 */
public interface Viewable {

    /**
     * This is the only implementation method required for this interface.
     */
    public void init();
}


