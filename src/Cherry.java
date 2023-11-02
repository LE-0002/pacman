import bagel.Image;
import bagel.util.Point;
import bagel.util.Rectangle;

/**
 * Class that creates the cherry object and its characteristics
 */
public class Cherry implements Eatable {
    /**
     * Public attribute that stores the number of points
     * for eating a cherry
     */
    public final static int POINTS = 20;
    private final Image CHERRY = new Image("res/cherry.png");
    private final Point position;
    private boolean isActive;

    /**
     * Constructor of cherry
     * @param initialX
     * @param initialY
     */
    public Cherry (int initialX, int initialY) {
        this.position = new Point(initialX, initialY);
        this.isActive = true;
    }

    /**
     * Method that performs state update
     */
    public void update() {
        if (isActive){
            CHERRY.drawFromTopLeft(position.x, position.y);
        }
    }

    /**
     * Method that returns the rectangle boundary box
     * @return Rectangle
     */
    public Rectangle getBoundingBox() {
        return new Rectangle(position, CHERRY.getWidth(), CHERRY.getHeight());
    }

    /**
     * Method that returns whether the cherry is still active or not
     * @return isActive
     */
    public boolean isActive() {
        return isActive;
    }

    /**
     * Method that sets the value of active for the cherry
     * @param active
     */
    public void setActive(boolean active) {
        isActive = active;
    }

}
