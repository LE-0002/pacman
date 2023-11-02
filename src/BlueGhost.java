import bagel.Image;
import bagel.util.Point;

/**
 * Class that creates the Blue Ghost object
 * and its characteristics
 */
public class BlueGhost extends Ghost{
    private final Image BLUE_GHOST = new Image("res/ghostBlue.png");
    private final double NORMAL_SPEED = 2;

    /**
     * Constructor for Blue Ghost
     * @param initialX
     * @param initialY
     */
    public BlueGhost(int initialX, int initialY){
        super(initialX, initialY);
        setSpeed(NORMAL_SPEED);
    }

    /**
     * Method used to update the status of the blue ghost per frame in the game
     * @param gameObject
     */
    public void update(ShadowPac gameObject) {
        Point position = getPosition();
        gameObject.checkGhostCollisions(this);
        /*Ghost only moves in level 1*/
        if (ShadowPac.getLevel()==gameObject.LEVEL1) {
            move();
        }
        if (gameObject.isIsFrenzy()) {
            if (isActive()) {
                drawFrenzyGhost();
                frenzySpeed(NORMAL_SPEED);
            }
        } else {
            BLUE_GHOST.drawFromTopLeft(position.x, position.y);
            resetSpeed(NORMAL_SPEED);
        }

    }

    /**
     * Method that moves the ghost in the desired direction
     */
    public void move() {
        Point position = getPosition();
        setPosition(new Point(position.x, position.y + getSpeed()));
    }

}
