import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * The Story world introduces the simulation to the player. It is self-serving, and there should not be any need
 * to do something here somewhere else not in this world.
 * 
 * @author Freeman Wang
 * @version 2024-04-20
 */
public class StoryWorld extends CursorWorld
{
    private GreenfootSound storyWorldMusic;
    private String[] dialogue;
    private TextBox dialogueBox;
    private BreathingTextBox promptBox;
    private Button nextWorldButton;
    private int line;
    private int visibleActCount;
    private Button skipButton;
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
        UI.init();
        
        storyWorldMusic = new GreenfootSound("Storyworld.mp3");
        storyWorldMusic.playLoop();
        setBackground(backgroundImage);
        
        dialogue = new String[]{
            "Hello.",
            "As you can see, this World is empty.",
            "Why? Well, I admit I have been neglecting my duties...",
            "But you cannot expect a GOD to be working forever! Where are my rights?",
            "I have anime to watch. Games to play. Friends to... nevermind...",
            "*Ahem.* The main point is, I'm going to take a break.",
            "Just for a little while, don't worry.",
            "How long? Let's say... as long as you want.",
            "My job isn't the most difficult. Just do what I always do!",
            "Create a World, then simply watch it. Nothing else.",
            "It's easy. Trust me! It's just drawing stuff, and pressing some buttons.",
            "Well, I've talked enough, and I deem you ready.",
            "Make sure you do well. The shoes of a God are hard to fill.",
            "Not that I wear shoes anyway.",
            "When I leave, you will see a big button appear on the screen.",
            "You know what to do with buttons, right?",
            "Goodbye, and good luck. I expect to see good results, friend."
        };
        line = 0;
        
        dialogueBox = new TextBox(dialogue[line], 24, Color.WHITE, null, 1, 100);
        addObject(dialogueBox, getWidth()/2, getHeight()/2-100);
        
        skipButton = new Button(this::goToNextWorld, 120, 21, new GreenfootImage("skipbutton_idle_v2.png"), new GreenfootImage("skipbutton_hovered_v2.png"), new GreenfootImage("skipbutton_pressed_v2.png"));
        addObject(skipButton, getWidth()-60 - 10, 10 + 10);
        
        promptBox = new BreathingTextBox("Click to continue...", 18, Color.WHITE, null, 240);
        nextWorldButton = new Button(() -> goToNextWorld(), 200, 75, new GreenfootImage("goButton.png"), new GreenfootImage("goButtonHovered.png"), new GreenfootImage("goButtonPressed.png"));
    }
    public void started() {
        storyWorldMusic.playLoop();
    }
    public void stopped() {
        storyWorldMusic.pause();
    }
    public void act() {
        if (stillMoreDialogue() && Greenfoot.mousePressed(null)) { // progresses dialogue, if still exists.
            visibleActCount = 0;
            playDialogue(++line);
            if (promptBox.getWorld() != null) removeObject(promptBox);
        }
        if (dialogueBox.isVisible()) { // count acts since the dialogue box is max transparency
            visibleActCount++;
        }
        if (visibleActCount >= 180 && stillMoreDialogue() && promptBox.getWorld() == null) { // determine if need to add a prompt to click to continue
            addObject(promptBox, getWidth()/2, getWidth()/2 + 100);
        }
        
        if (!stillMoreDialogue() && nextWorldButton.getWorld() == null) { // once dialogue is exhausted, add the next world button.
            addObject(nextWorldButton, getWidth()/2, getHeight()/2 + 50);
        }
    }
    /**
     * Goes to the next world.
     */
    private void goToNextWorld() {
        storyWorldMusic.stop();
        Greenfoot.setWorld(new SettingsWorld());
    }
    /**
     * Progresses the main dialogue.
     */
    private void playDialogue(int line) {
        dialogueBox.display(dialogue[line]);
    }
    /**
     * Check whether all dialogue has been exhausted.
     * @return true if there still is more dialogue.
     */
    private Boolean stillMoreDialogue() {
        int nextLine = line + 1;
        return nextLine < dialogue.length;
    }
}
