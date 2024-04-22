import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)
import java.util.ArrayList;

/**
 * The SummaryWorld will cycle through all the boards they existed per day.
 * This way, you get to see the progression of the environment.
 * 
 * @author Freeman Wang 
 * @version 2024-04-04
 */
public class SummaryWorld extends CursorWorld
{
    private ArrayList<String> boards;
    private int actCount;
    private BreathingTextBox prompt;
    private int day; // or index
    
    private GreenfootSound summaryWorldMusic;
    public SummaryWorld()
    {
        boards = (ArrayList<String>) SimulationWorld.getDailyBoards(); // recieve daily board data.
        prompt = new BreathingTextBox("PRESS [L] TO LEAVE", 40, Color.RED, null, 120);
        addObject(prompt, getWidth()/2, getHeight()-100);
        
        TextBox tempBox = new TextBox("Watch your World change!", 50, Color.WHITE, null, 1, 255);
        tempBox.fadeOut();
        addObject(tempBox, getWidth()/2, getHeight()/2);
        
        // make tiles animate, but time not flow (no accidental changes while viewing)
        Tile.setTimeFlow(false);
        Tile.setAlwaysAnimate(true); 
        
        actCount = 0;
        day = 0;
        
        Board.setWorld(this); // make the board use the summary world for adding/removing
        displayBoard(0); // display zeroth world
        
        summaryWorldMusic = new GreenfootSound("endworld.mp3");
        summaryWorldMusic.play();
    }
    public void act() {
        manageKeyInput();
        if (++actCount >= 180) { // every 3 seconds
            actCount = 0;
            day = nextDay();
            displayBoard(day);
        }
    }
    /**
     * Changes the board being displayed to the given day.
     * @param day The day whose board to display.
     */
    private void displayBoard(int day) {
        String buildString = boards.get(day);
        Board.loadBoard(this, buildString);
        display("DAY " + day);
    }
    private void manageKeyInput() {
        String key = Greenfoot.getKey();
        if ("l".equals(key)) {
            goToNextWorld();
        }
    }
    private void goToNextWorld() {
        Greenfoot.setWorld(new IntroWorld(true)); // use the other constructor so muci also plays 
        summaryWorldMusic.stop();
    }
    /**
     * Get the next day (cycles back to zero if out of the boards size limit)
     */
    private int nextDay() {
        int nextDay = (day + 1)%boards.size();
        return nextDay;
    }
    /**
     * Dispalys a given string onto the world.
     * @param text the text to display.
     */
    private void display(String text) {
        TextBox box = new TextBox(text, 52, Color.BLACK, null, 2, 255);
        box.fadeOut();
        addObject(box, getWidth()/2, 100);
    }
    public void started() {
        summaryWorldMusic.play(); 
    }
    public void stopped() {
        summaryWorldMusic.pause();
    }
}
