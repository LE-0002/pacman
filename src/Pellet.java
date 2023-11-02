import bagel.Image;
import bagel.util.Point;
import bagel.util.Rectangle;

/**
 * Class that creates the pellet object
 * and its characteristics
 */
public class Pellet implements Eatable {
    private final Image PELLET = new Image("res/pellet.png");
    private final Point position;
    private boolean isActive;

    /**
     * Constructor for pellet
     * @param initialX
     * @param initialY
     */
    public Pellet(int initialX, int initialY) {
        this.position = new Point(initialX, initialY);
        this.isActive = true;
    }

    /**
     * Method that updates the status of the pellet per frame
     * in the game
     */
    public void update() {
        if (isActive){
            PELLET.drawFromTopLeft(position.x, position.y);
        }
    }

    /**
     * Method that returns the rectangular boundary box of the pellet
     * @return
     */
    public Rectangle getBoundingBox() {
        return new Rectangle(position, PELLET.getWidth(), PELLET.getHeight());
    }

    /**
     * Method that returns the value of boolean isActive
     * @return
     */
    public boolean isActive() {
        return isActive;
    }

    /**
     * Method that sets the value of boolean isActive
     * @param active
     */
    public void setActive(boolean active) {
        isActive = active;
    }


}
