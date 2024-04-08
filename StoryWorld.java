import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * Write a description of class StoryWorld here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class StoryWorld extends CursorWorld
{
    private static String[] dialogue;
    private static TextBox dialogueBox;
    private static BreathingTextBox promptBox;
    private static Button nextWorldButton;
    private static int line;
    private static int visibleActCount;
    /**
     * Constructor for objects of class StoryWorld.
     * 
     */
    public StoryWorld()
    {    
        // Create a new world with 600x400 cells with a cell size of 1x1 pixels.
        super(); 
        visibleActCount = 0;
        GreenfootImage backgroundImage = new GreenfootImage(1024, 768);
        backgroundImage.setColor(Color.BLACK);
        backgroundImage.fill();
        
        setBackground(backgroundImage);
        
        dialogue = new String[]{
            "Hello.",
            "As you can see, this World is empty.",
            "Why? Well, I admit I have been neglecting my duties...",
            "But you cannot expect a GOD to be working forever! Where are my rights?",
            "I have anime to watch. Games to play. Friends to... nevermind...",
            "*Ahem.* The main point is, I'm going to take a break.",
            "Just for a little while, don't worry.",
            "How long? Lets say... a week or so.",
            "My job isn't the most difficult. Just do what I always do!",
            "Create a World, then simply watch it. Nothing else.",
            "It's easy. Trust me! It's just drawing stuff, and pressing some buttons.",
            "Well, I've talked enough, and I deem you ready.",
            "Make sure you do well. The shoes of a god are hard to fill.",
            "Not that I wear shoes anyway.",
            "When I leave, you will see a big button appear on the screen.",
            "You know what to do with buttons, right?",
            "Goodbye, and good luck. I expect to see good results, friend."
        };
        line = 0;
        
        dialogueBox = new TextBox(dialogue[line], 24, Color.WHITE, null, 1, 100);
        addObject(dialogueBox, getWidth()/2, getHeight()/2-100);
        
        promptBox = new BreathingTextBox("Click to continue.", 18, Color.WHITE, null, 240);
        nextWorldButton = new Button(() -> Greenfoot.setWorld(new SettingsWorld()), 200, 75);
    }
    public void act() {
        if (stillMoreDialogue() && Greenfoot.mousePressed(null)) {
            visibleActCount = 0;
            playDialogue(++line);
            if (promptBox.getWorld() != null) removeObject(promptBox);
        }
        if (dialogueBox.isVisible()) {
            visibleActCount++;
        }
        if (visibleActCount >= 180 && stillMoreDialogue() && promptBox.getWorld() == null) {
            addObject(promptBox, getWidth()/2, getWidth()/2 + 100);
        }
        
        if (!stillMoreDialogue() && nextWorldButton.getWorld() == null) {
            addObject(nextWorldButton, getWidth()/2, getHeight()/2 + 50);
        }
    }
    private void playDialogue(int line) {
        dialogueBox.display(dialogue[line]);
    }
    private Boolean stillMoreDialogue() {
        int nextLine = line + 1;
        return nextLine < dialogue.length;
    }
}
