import bagel.Image;
import bagel.util.Point;
import bagel.util.Rectangle;

/**
 * Class that creates the Dot object and its characteristics
 */
public class Dot implements Eatable {
    /**
     * Public attribute that stores the number of
     * points for eating a dot
     */
    public final static int POINTS = 10;
    private final Image DOT = new Image("res/dot.png");
    private final Point position;
    private boolean isActive;

    /**
     * Constructor for dot
     * @param initialX
     * @param initialY
     */
    public Dot(int initialX, int initialY){
        this.isActive = true;
        this.position = new Point(initialX, initialY);
    }

    /**
     * Method that performs state update
     */
    public void update() {
        if (isActive){
            DOT.drawFromTopLeft(position.x, position.y);
        }
    }

    /**
     * Method that returns the rectangle boundary box of the dot
     * @return Rectangle
     */
    public Rectangle getBoundingBox(){
        return new Rectangle(position, DOT.getWidth(), DOT.getHeight());
    }

    /**
     * This method is used to return the status of whether the dot is active.
     * @return isActive
     */
    public boolean isActive() {
        return isActive;
    }

    /**
     * This method is used to set the value of variable, active.
     * @param active
     */
    public void setActive(boolean active) {
        isActive = active;
    }

}
