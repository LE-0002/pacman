import bagel.Image;
import bagel.Keys;
import bagel.util.Point;
import java.util.Random;
import java.lang.Math;

/**
 * Class that creates the Pink Ghost object
 * and its characteristics
 */
public class PinkGhost extends Ghost{
    private final Image PINK_GHOST = new Image("res/ghostPink.png");
    private final double NORMAL_SPEED = 3;
    private final int NUM_OF_ARROWS = 4;
    private boolean isHorizontal;
    private Point prevPosition;
    private boolean inCollision;
    private final Keys[] directions = {Keys.DOWN, Keys.UP, Keys.LEFT, Keys.RIGHT};

    /**
     * Constructor for PinkGhost
     * @param initialX
     * @param initialY
     */
    public PinkGhost(int initialX, int initialY){
        super(initialX, initialY);
        setSpeed(NORMAL_SPEED);
    }

    /**
     * Method that updates the status of the pink
     * ghost in the game per frame
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
            PINK_GHOST.drawFromTopLeft(position.x, position.y);
            resetSpeed(NORMAL_SPEED);
        }
        inCollision = false;
        gameObject.checkGhostCollisions(this);
        /*If currently in collision with wall*/
        if (inCollision) {
            moveBack();
            randomDirection();
        } else {
            move();
        }
    }

    /**
     * Method that chooses a new random direction for
     * the pink ghost to travel in after colliding with wall
     */
    public void randomDirection() {
        Random random = new Random();
        int position = random.nextInt(NUM_OF_ARROWS);
        if (directions[position]==Keys.RIGHT || directions[position]==Keys.LEFT) {
            isHorizontal = true;
        } else {
            isHorizontal = false;
        }
        double speed = getSpeed();
        if (directions[position]==Keys.LEFT || directions[position]==Keys.UP) {
            setSpeed(Math.abs(speed)*Ghost.REVERSE_FACTOR);
        } else {
            setSpeed(Math.abs(speed));
        }

    }

    /**
     * Method to move the pink ghost in the direction
     * that it is currently travelling in
     */
    public void move() {
        Point position = getPosition();
        prevPosition = position;
        if (isHorizontal) {
            setPosition(new Point(position.x + getSpeed(), position.y));
        } else {
            setPosition(new Point(position.x, position.y + getSpeed()));
        }
    }

    /**
     * Method that determines whether the pink ghost
     * needs to change direction or not
     */
    public void reverse() {
        inCollision = true;
    }

    /**
     * Method that moves the pink ghost back if it overlaps with wall
     */
    public void moveBack(){
        setPosition(prevPosition);
    }


}
