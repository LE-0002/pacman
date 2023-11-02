import bagel.*;
import bagel.util.Point;

/**
 * Class that creates the player object and
 * its characeristics
 */
public class Player {
    private final static String PAC = "res/pac.png";
    private final static String PAC_OPEN = "res/pacOpen.png";
    private final static Image HEART = new Image("res/heart.png");
    private final static int NORMAL_MOVE_SIZE = 3;
    private final static int FRENZY_MOVE_SIZE = 4;
    private final static int MAX_LIVES = 3;
    private final static int SWITCH_FRAME = 15;
    private final static int FONT_SIZE = 20;
    private final static String SCORE_STRING = "SCORE ";
    private final static int SCORE_X = 25;
    private final static int SCORE_Y = 25;
    private final static int LIVES_X = 900;
    private final static int LIVES_Y = 10;
    private final static int LIVES_OFFSET = 30;
    private final Font FONT = new Font("res/FSO8BITR.ttf", FONT_SIZE);
    private final Point startingPosition;
    private int speed;
    private DrawOptions rotator = new DrawOptions();
    private int counter;
    private int score;
    private Point position;
    private Point prevPosition;
    private Image currentImage;
    private int lives;
    private boolean isOpen = false;

    /**
     * Constructor for player
     * @param initialX
     * @param initialY
     */
    public Player(int initialX, int initialY){
        this.position = new Point(initialX, initialY);
        this.startingPosition = position;
        this.currentImage = new Image(PAC);
        this.counter = SWITCH_FRAME;
        this.lives = MAX_LIVES;
        this.score = 0;
    }

    /**
     * Method that performs state update
     */
    public void update(Input input, ShadowPac gameObject){
        counter--;
        if (gameObject.isIsFrenzy()) {
            speed = FRENZY_MOVE_SIZE;
        } else {
            speed = NORMAL_MOVE_SIZE;
        }
        if (input.isDown(Keys.UP)){
            move(0, -speed);
            rotator.setRotation(-Math.PI/2);
        } else if (input.isDown(Keys.DOWN)){
            move(0, speed);
            rotator.setRotation(Math.PI/2);
        } else if (input.isDown(Keys.LEFT)){
            move(-speed,0);
            rotator.setRotation(Math.PI);
        } else if (input.isDown(Keys.RIGHT)) {
            move(speed,0);
            rotator.setRotation(0);
        }
        if (counter == 0) {
            // switching the image being rendered
            if (isOpen) {
                currentImage = new Image(PAC);
                isOpen = false;
            } else {
                currentImage = new Image(PAC_OPEN);
                isOpen = true;
            }
            counter = SWITCH_FRAME;
        }
        currentImage.drawFromTopLeft(position.x, position.y, rotator);
        gameObject.checkCollisions(this);
        renderScore();
        renderLives();
    }

    /**
     * Method that moves the player given the direction
     */
    private void move(double xMove, double yMove){
        prevPosition = position;
        position = new Point(position.x + xMove, position.y + yMove);
    }

    /**
     * Method that renders the player's score
     */
    private void renderScore(){
        FONT.drawString(SCORE_STRING + score, SCORE_X, SCORE_Y);
    }

    /**
     * Method that renders the player's lives
     */
    private void renderLives(){
        for (int i = 0; i < lives; i++){
            HEART.drawFromTopLeft(LIVES_X + (LIVES_OFFSET*i), LIVES_Y);
        }
    }

    /**
     * Method that resets the player's position to the starting location
     */
    public void resetPosition(){
        position = startingPosition;
        currentImage = new Image(PAC);
        rotator.setRotation(0);
    }

    /**
     * Method that prevents the player from colliding with the walls
     */
    public void moveBack(){
        position = prevPosition;
    }

    /**
     * Method that checks if the player has 0 lives
     */
    public boolean isDead() {
        return lives == 0;
    }

    /**
     * Method that checks if the player has reached the target score
     */
    public boolean reachedScore(int target){
        return score == target;
    }

    /**
     * Method that accepts a generic class and
     * @param generic
     * @param <T>
     */
    public <T> void incrementScore(T generic) {
        if (generic instanceof Dot) {
            score+=Dot.POINTS;
        } else if (generic instanceof Cherry) {
            score+=Cherry.POINTS;
        } else if (generic instanceof Ghost) {
            score+=Ghost.POINTS;
        }
    }

    /**
     * Method that reduces the number of lives of player
     */
    public void reduceLives() {
        lives--;
    }

    /**
     * Method that returns position of player
     * @return
     */
    public Point getPosition() {
        return position;
    }

    /**
     * Method that returns image of player
     * @return
     */
    public Image getCurrentImage() {
        return currentImage;
    }

    /**
     * Method that resets the score of the player
     */
    public void resetScore() {
        score = 0;
    }

}
