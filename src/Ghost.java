import bagel.Image;
import bagel.util.Point;
import bagel.util.Rectangle;

/**
 * Class that creates the ghost object
 * and its characteristics
 */
public class Ghost {
    private final Image GHOST = new Image("res/ghostRed.png");
    private final Image FRENZY_GHOST = new Image("res/ghostFrenzy.png");
    /**
     * Constant for number of points added to score
     * when ghost is eaten
     */
    public final static int POINTS = 30;
    private final static double SLOWED_BY = 0.5;
    /**
     * Constant to reverse direction that ghost is
     * travelling in
     */
    public final static double REVERSE_FACTOR = -1;
    private double speed;
    private boolean isActive;
    private Point position;
    private Point initialPosition;

    /**
     * Constructor for ghost
     * @param initialX
     * @param initialY
     */
    public Ghost(int initialX, int initialY){
        this.position = new Point(initialX, initialY);
        this.initialPosition = position;
        isActive = true;
    }

    /**
     * Method that performs state update
     */
    public void update(ShadowPac gameObject) {
        GHOST.drawFromTopLeft(position.x, position.y);
        gameObject.checkGhostCollisions(this);
    }

    /**
     * Method that returns the rectangle boundary box of the ghost
     * @return
     */
    public Rectangle getBoundingBox(){
        return new Rectangle(position, GHOST.getWidth(), GHOST.getHeight());
    }

    /**
     * Abstract method for reversing/changing direction of ghost
     */
    public void reverse() {
        speed *= REVERSE_FACTOR;
    }

    /**
     * Method that draws the frenzy ghost image
     */
    public void drawFrenzyGhost() {
        FRENZY_GHOST.drawFromTopLeft(position.x, position.y);
    }

    /**
     * Method that sets the initial speed of the ghost
     * @param speed
     */
    public void setSpeed(double speed) {
        this.speed = speed;
    }

    /**
     * Method that sets the speed to its frenzy speed
     * @param normalSpeed
     */
    public void frenzySpeed(double normalSpeed) {
        if (speed > 0) {
            speed = normalSpeed - SLOWED_BY;
        } else if (speed < 0) {
            speed = SLOWED_BY - normalSpeed;
        }
    }

    /**
     * Method that resets the speed to non-frenzy, but
     * keeps the current direction of the ghost
     * @param normalSpeed
     */
    public void resetSpeed(double normalSpeed) {
        if (speed > 0) {
            speed = normalSpeed;
        } else if (speed < 0) {
            speed = REVERSE_FACTOR*normalSpeed;
        }
    }

    /**
     * Getter method that returns the speed of the ghost
     * @return speed
     */
    public double getSpeed() {
        return speed;
    }

    /**
     * Getter method that returns the value of variable isActive
     * @return isActive
     */
    public boolean isActive() {
        return isActive;
    }

    /**
     * Setter method that sets the value of variable active
     * @param active
     */
    public void setActive(boolean active) {
        isActive = active;
    }

    /**
     * Getter method that returns the position of the ghost
     * @return
     */
    public Point getPosition() {
        return position;
    }

    /**
     * Method that sets the position of the ghost
     * @param position
     */
    public void setPosition(Point position) {
        this.position = position;
    }

    /**
     * Resets the position of the ghost to initial
     * starting point
     */
    public void resetPosition() {
        this.position = initialPosition;
    }

}