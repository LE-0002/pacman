import bagel.util.Rectangle;

/**
 * Interface for all objects that are "eatable"
 */
public interface Eatable {
    /**
     * Method that sets the active status of the object
     * @param active
     */
    public void setActive(boolean active);

    /**
     * Method that returns the value of boolean isActive
     * @return boolean
     */
    public boolean isActive();

    /**
     * Method that returns the rectangular boundary box
     * of the object
     * @return Rectangle
     */
    public Rectangle getBoundingBox();

    /**
     * Method that updates status of the object
     */
    public void update();

}
