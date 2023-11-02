import bagel.Image;
import bagel.Keys;
import bagel.util.Point;
import java.util.Random;

/**
 * Class that creates the Green Ghost object
 * and its characteristics
 */
public class GreenGhost extends Ghost {
    private final Image GREEN_GHOST = new Image("res/ghostGreen.png");
    private double NORMAL_SPEED = 4;
    private final int NUM_OF_DIRECTIONS = 2;
    private boolean isHorizontal;

    private boolean justStarting = true;
    private final Keys[] directions = {Keys.DOWN, Keys.RIGHT};

    /**
     * Constructor for green ghost
     *
     * @param initialX
     * @param initialY
     */
    public GreenGhost(int initialX, int initialY) {
        super(initialX, initialY);
        setSpeed(NORMAL_SPEED);
    }

    /**
     * Method that updates the green ghost per frame in the game
     *
     * @param gameObject
     */
    public void update(ShadowPac gameObject) {
        Point position = getPosition();
        gameObject.checkGhostCollisions(this);
        /*If first frame, then randomly choose either
        * horizontal or vertical to travel in*/
        if (justStarting) {
            randomDirection();
            justStarting = false;
        }
        move();
        /*If in frenzy mode*/
        if (gameObject.isIsFrenzy()) {
            if (isActive()) {
                drawFrenzyGhost();
                frenzySpeed(NORMAL_SPEED);
            }
        } else {
            GREEN_GHOST.drawFromTopLeft(position.x, position.y);
            resetSpeed(NORMAL_SPEED);
        }
    }

    /**
     * Method that randomly chooses whether green ghost moves
     * vertically or horizontally
     */
    public void randomDirection() {
        Random random = new Random();
        int position = random.nextInt(NUM_OF_DIRECTIONS);
        if (directions[position] == Keys.RIGHT) {
            isHorizontal = true;
        } else {
            isHorizontal = false;
        }
    }

    /**
     * Method that moves the green ghost in the desired
     * direction
     */
    public void move() {
        Point position = getPosition();
        if (isHorizontal) {
            setPosition(new Point(position.x + getSpeed(), position.y));
        } else {
            setPosition(new Point(position.x, position.y + getSpeed()));
        }
    }
}

