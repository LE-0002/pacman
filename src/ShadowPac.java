import bagel.*;
import bagel.util.Rectangle;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

/**
 * Code for SWEN20003 Project 2, Semester 1, 2023
 *
 * Please enter your name below
 * David Le
 */

/**
 * Class for Shadow Pac
 */
public class ShadowPac extends AbstractGame  {
    private final static int WINDOW_WIDTH = 1024;
    private final static int WINDOW_HEIGHT = 768;
    private final static String GAME_TITLE = "SHADOW PAC";
    private final static String WORLD_FILE0 = "res/level0.csv";
    private final static String WORLD_FILE1 = "res/level1.csv";
    private final Image BACKGROUND_IMAGE = new Image("res/background0.png");
    private final static int TITLE_FONT_SIZE = 64;
    private final static int INSTRUCTION_FONT_SIZE = 24;
    private final static int LEVEL1_FONT_SIZE = 40;
    private final static int TITLE_X = 260;
    private final static int TITLE_Y = 250;
    private final static int INS_X_OFFSET = 60;
    private final static int INS_Y_OFFSET = 190;
    private final static int LEVEL1_INSTRUCTION_X = 200;
    private final static int LEVEL1_INSTRUCTION_Y = 350;
    private final static int LEVEL0 = 0;
    public final static int LEVEL1 = 1;
    private final static String INSTRUCTION_MESSAGE = "PRESS SPACE TO START\nUSE ARROW KEYS TO MOVE";
    private final static String PELLET_MESSAGE = "\nEAT THE PELLET TO ATTACK";
    private final static String END_MESSAGE = "GAME OVER!";
    private final static String WIN_MESSAGE = "WELL DONE!";
    private final static String END_LEVEL_MESSAGE = "LEVEL COMPLETE!";
    private final Font TITLE_FONT = new Font("res/FSO8BITR.ttf", TITLE_FONT_SIZE);
    private final Font INSTRUCTION_FONT = new Font("res/FSO8BITR.ttf", INSTRUCTION_FONT_SIZE);
    private final Font LEVEL1_FONT = new Font("res/FSO8BITR.ttf", LEVEL1_FONT_SIZE);
    private final static int DOT_ARRAY_SIZE = 121;
    private final static int PASS_LEVEL0_SCORE = DOT_ARRAY_SIZE* Dot.POINTS;
    private final static int MAX_TIME = 300;
    private final static int WIN_SCORE = 800;
    private static ArrayList<Wall> walls = new ArrayList<Wall>();
    private static ArrayList<Ghost> ghosts = new ArrayList<Ghost>();
    private static ArrayList<Eatable> eatables = new ArrayList<Eatable>();
    private Player player;
    private boolean hasStarted;
    private boolean gameOver;
    private boolean playerWin;
    private boolean level0Completed;
    private static int time = 0;
    private static int frenzy_frames = 0;
    private final int MAX_FRENZY_FRAMES = 1000;
    private static boolean isFrenzy = false;
    private static int level;

    /**
     * Constructor for ShadowPac
     */
    public ShadowPac(){
        super(WINDOW_WIDTH, WINDOW_HEIGHT, GAME_TITLE);
        level = LEVEL0;
        hasStarted = false;
        gameOver = false;
        playerWin = false;
        level0Completed = false;
        readCSV();
    }

    /**
     * Method used to read file and create objects
     */
    private void readCSV() {
        clearAll();
        String fileName;
        if (level==LEVEL0) {
            fileName = WORLD_FILE0;
        } else {
            fileName = WORLD_FILE1;
        }
        try (BufferedReader reader = new BufferedReader(new FileReader(fileName))){
            String line;
            while((line = reader.readLine()) != null) {
                String[] sections = line.split(",");
                int xCoord = Integer.parseInt(sections[1]);
                int yCoord = Integer.parseInt(sections[2]);

                switch (sections[0]) {
                    case "Player":
                        player = new Player(xCoord, yCoord);
                        break;
                    case "Ghost":
                        ghosts.add(new Ghost(xCoord, yCoord));
                        break;
                    case "Dot":
                        eatables.add(new Dot(xCoord, yCoord));
                        break;
                    case "Wall":
                        walls.add(new Wall(xCoord, yCoord));
                        break;
                    case "Pellet":
                        eatables.add(new Pellet(xCoord, yCoord));
                        break;
                    case "Cherry":
                        eatables.add(new Cherry(xCoord, yCoord));
                        break;
                    case "GhostRed":
                        ghosts.add(new RedGhost(xCoord, yCoord));
                        break;
                    case "GhostGreen":
                        ghosts.add(new GreenGhost(xCoord, yCoord));
                        break;
                    case "GhostPink":
                        ghosts.add(new PinkGhost(xCoord, yCoord));
                        break;
                    case "GhostBlue":
                        ghosts.add(new BlueGhost(xCoord, yCoord));
                        break;
                }
            }
        } catch (IOException e){
            e.printStackTrace();
            System.exit(-1);
        }
    }

    /**
     * Main method
     * @param args
     */
    public static void main(String[] args) {
        ShadowPac game = new ShadowPac();
        game.run();
    }

    /**
     * Method that performs state update
     */
    @Override
    protected void update(Input input) {

        if (input.wasPressed(Keys.ESCAPE)){
            Window.close();
        } else if (input.wasPressed(Keys.W)) {
            level0Completed = true;
            level = LEVEL1;
            readCSV();
        }

        BACKGROUND_IMAGE.draw(Window.getWidth()/2.0, Window.getHeight()/2.0);
        /*If game hasn't started yet*/
        if(!hasStarted){
            drawStartScreen();
            if (input.wasPressed(Keys.SPACE)){
                hasStarted = true;
            }

        } else if (gameOver){
            drawMessage(END_MESSAGE);

        } else if (playerWin) {
            drawMessage(WIN_MESSAGE);

        } else if (level0Completed) {
            if (time!=MAX_TIME) {
                drawMessage(END_LEVEL_MESSAGE);
                time++;
            } else {
                drawLevel1StartScreen();
                if (input.wasPressed(Keys.SPACE)) {
                    level0Completed = false;
                }
            }

        } else {
            player.update(input, this);

            for (Ghost current: ghosts){
                current.update(this);
            }

            for(Wall current: walls){
                    current.update();
            }

            for (Eatable current: eatables) {
                current.update();
            }

            if (player.isDead()){
                gameOver = true;
            }

            if (player.reachedScore(PASS_LEVEL0_SCORE) && level==LEVEL0) {
                level0Completed = true;
                level = LEVEL1;
                player.resetScore();
                readCSV();
            }
            if (player.reachedScore(WIN_SCORE) && level==LEVEL1){
                playerWin = true;
            }
        }
    }

    /**
     * Method that checks for collisions between the player and the other entities, and performs
     * corresponding actions.
     */
    public void checkCollisions(Player player){
        Rectangle playerBox = new Rectangle(player.getPosition(), player.getCurrentImage().getWidth(),
                player.getCurrentImage().getHeight());

        /*If frenzy mode is over, reset to non-frenzy*/
        if (frenzy_frames==MAX_FRENZY_FRAMES) {
            frenzy_frames = 0;
            isFrenzy = false;
        }
        /*If currently in frenzy mode*/
        if (isFrenzy) {
            frenzy_frames++;
        }

        for (Ghost current: ghosts){
            Rectangle ghostBox = current.getBoundingBox();
            if (playerBox.intersects(ghostBox)){
                /*If not in frenzy mode*/
                if (!isFrenzy) {
                    current.setActive(true);
                    player.reduceLives();
                    player.resetPosition();
                    current.resetPosition();
                /*If in frenzy mode and ghost is active*/
                } else if (isFrenzy && current.isActive()) {
                    player.incrementScore(current);
                    current.setActive(false);
                /*If in frenzy mode and ghost is inactive*/
                }
            }
            /*Resets position of ghost, if ghost has already
            * been eaten*/
            if (isFrenzy && !current.isActive()) {
                current.resetPosition();
            }
        }

        for (Eatable current: eatables) {
            Rectangle dotBox = current.getBoundingBox();
            /*If player eats the dot, set the dot to inactive and
             * increase score of player */
            if (current.isActive() && playerBox.intersects(dotBox)) {
                current.setActive(false);
                if (current instanceof Dot || current instanceof Cherry) {
                    player.incrementScore(current);
                } else if (current instanceof Pellet) {
                    isFrenzy = true;
                }
            }
        }

        for (Wall current: walls){
            /*If player collides with wall, move the player back
            * so the player is not overlapping the wall*/
            Rectangle wallBox = current.getBoundingBox();
            if (playerBox.intersects(wallBox)){
                player.moveBack();
            }
        }
    }

    /**
     * Method that checks whether ghost has collided with a wall and
     * if it has, will change the direction of the ghost as desired
     * @param ghost
     */
    public void checkGhostCollisions(Ghost ghost) {
        Rectangle ghostBox = ghost.getBoundingBox();
        for (Wall current: walls) {
            Rectangle wallBox = current.getBoundingBox();
            /*If ghost collides with wall, change direction of
            * ghost*/
            if (ghostBox.intersects(wallBox)) {
                ghost.reverse();
            }
        }
    }

    /**
     * Method used to draw the start screen title and instructions
     */
    private void drawStartScreen(){
        TITLE_FONT.drawString(GAME_TITLE, TITLE_X, TITLE_Y);
        INSTRUCTION_FONT.drawString(INSTRUCTION_MESSAGE,TITLE_X + INS_X_OFFSET, TITLE_Y + INS_Y_OFFSET);
    }

    /**
    * Method used to draw the middle intermediate screen title and instructions
    * */
    private void drawLevel1StartScreen(){
        LEVEL1_FONT.drawString(INSTRUCTION_MESSAGE + PELLET_MESSAGE, LEVEL1_INSTRUCTION_X,LEVEL1_INSTRUCTION_Y);
    }
    /**
     * Method used to clear all arraylists
     */
    private void clearAll() {
        ghosts.clear();
        walls.clear();
        eatables.clear();
    }

    /**
     * Method used to draw end screen messages
     */
    private void drawMessage(String message){
        TITLE_FONT.drawString(message, (Window.getWidth()/2.0 - (TITLE_FONT.getWidth(message)/2.0)),
                (Window.getHeight()/2.0 + (TITLE_FONT_SIZE/2.0)));
    }

    /**
     *  Method that returns the value of the variable, isFrenzy
     * @return
     */
    public static boolean isIsFrenzy() {
        return isFrenzy;
    }

    /**
     * Method that returns the level that the game is currently at
     * @return
     */
    public static int getLevel() {
        return level;
    }
}