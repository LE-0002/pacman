import bagel.Image;
import bagel.util.Point;

/**
 * Class that creates the red ghost object
 * and its characteristics
 */
public class RedGhost extends Ghost {
    private final Image RED_GHOST = new Image("res/ghostRed.png");
    private final double NORMAL_SPEED = 1;

    /**
     * Constructor for red ghost
     * @param initialX
     * @param initialY
     */
    public RedGhost(int initialX, int initialY) {
        super(initialX, initialY);
        setSpeed(NORMAL_SPEED);
    }

    /**
     * Method that updates the status of the red ghost
     * per frame in the game
     * @param gameObject
     */
    public void update(ShadowPac gameObject) {
        Point position = getPosition();
        /*If in frenzy mode*/
        if (gameObject.isIsFrenzy()) {
            if (isActive()) {
                drawFrenzyGhost();
                frenzySpeed(NORMAL_SPEED);
            }
        } else {
            RED_GHOST.drawFromTopLeft(position.x, position.y);
            resetSpeed(NORMAL_SPEED);
        }
        gameObject.checkGhostCollisions(this);
        /*Double checks that it is level 1 before moving*/
        if (ShadowPac.getLevel()==ShadowPac.LEVEL1) {
            move();
        }
    }

    /**
     * Method that moves the red ghost in the desired direction
     */
    public void move() {
        Point position = getPosition();
        setPosition(new Point(position.x + getSpeed(), position.y));
    }


}

