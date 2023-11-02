import bagel.Image;
import bagel.util.Point;
import bagel.util.Rectangle;

/**
 * Class that creates the wall object
 * and its characteristics
 */
public class Wall {
    private final Image WALL = new Image("res/wall.png");
    private final Point position;

    /**
     * Constructor for wall
     * @param initialX
     * @param initialY
     */
    public Wall(int initialX, int initialY){
        this.position = new Point(initialX, initialY);
    }

    /**
     * Method that performs state update
     */
    public void update() {
        WALL.drawFromTopLeft(this.position.x, this.position.y);
    }

    /**
     * Method that returns the rectangle boundary box of the wall
     * @return Rectangle
     */
    public Rectangle getBoundingBox(){
        return new Rectangle(position, WALL.getWidth(), WALL.getHeight());
    }
}
