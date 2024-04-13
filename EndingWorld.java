import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * Write a description of class EndingWorld here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class EndingWorld extends CursorWorld
{

    /**
     * Constructor for objects of class EndingWorld.
     * 
     */
    private static String[] dialogue;
    private static TextBox dialogueBox;
    private static BreathingTextBox promptBox;
    private static Button nextWorldButton;
    private static int line;
    private static int visibleActCount;
    private static int lineForButtonToAppear;
    private static int lineForEventPictureToAppear;
    private static boolean goodEnding;
    // private static Picture eventPicture; WIP
    /**
     * Creates an EndingWorld. The ending type is determined based on the boolean given/
     */
    public EndingWorld(boolean goodEnding)
    {
        super(); 
        this.goodEnding = goodEnding;
        visibleActCount = 0;
        GreenfootImage backgroundImage = new GreenfootImage(1024, 768);
        backgroundImage.setColor(Color.BLACK);
        backgroundImage.fill();
        
        setBackground(backgroundImage);
        if (goodEnding) { // Good ending sequence.
            lineForEventPictureToAppear = 9; // appears on line 9 (zero indexed);
            lineForButtonToAppear = 10; // appears on line 19 (zero indexed);
            dialogue = new String[]{
                "Ah. Sorry about that.",
                "Those who are above even *I* did not make transitions between Worlds.",
                "But if you haven't noticed, your seven days are over.",
                "I'm reluctant to come back, but I don't have a choice.",
                "The Voices speak of \"needing two endings.\"",
                "Now, let me see...",
                ". . .",
                "You know what, it's not bad.",
                "It's not the wasteland I was expecting when I left you on your own.",
                "As a token of my gratitude, here is a cute cat picture for you to appreciate.",
                ". . .",
                "I guess it's time to say goodbye.",
                "But before you press that button, and Reset everything, I'll just say something.",
                "Tell the creators of this game that they suck at coding.",
                "At the same time, tell whoever wrote my lines that they should never cook again.",
                "Haha! Seems like I'll get the last laugh this time.",
                "Go. Press the button. Click it, actually. You're probably using a mouse.",
                "Hey, what are you doing? Go on, there's nothing else here.",
                "Just do it already!",
                "I'm going to run out of lines!",
                "You've already won already. Look at the cat picture! This is the GOOD ending!",
                "There is absolutely nothing else for you to experience here.",
                "And also, requiring user input right now...",
                "Doesn't that violate the requirements of this project?",
                "It's supposed to be a *simulation*, not a game.",
                "Whatever.",
                "You seem determined.",
                "Well then, have fun with a BreathingTextBox then."                
            };
            
            //eventPicture = new Picture("cat.png");
        }
        else { // Bad ending sequence.
            lineForEventPictureToAppear = 6; // appears on line 6 (zero indexed);
            lineForButtonToAppear = 14; // appears on line 14 (zero indexed);
            dialogue = new String[]{
                "Ah. Sorry about that.",
                "Those who are above even *I* did not make transitions between Worlds.",
                "I'm reluctant to come back, but I don't have a choice.",
                "The Voices speak of \"needing two endings.\"",
                "Now, let me see...",
                ". . .",
                "Dude, what the hell happened here?",
                "I... what did you do to these poor animals?",
                "I know they're just a bunch of pixels, but how could you?",
                "And really, how did you manage to reach this ending?",
                "The balancing for this Simulation is so terrible that it shouldn't be possible.",
                "But look at you.",
                "You made the impossible, possible.",
                "And that's not a compliment.",
                "Just... restart at this point. You know, try again.",
                "I'm not going to talk with you anymore."
            };
            
            //picture = new Picture("scaredSonic.png");
        }
        line = 0;
        
        dialogueBox = new TextBox(dialogue[line], 24, Color.WHITE, null, 1, 100);
        addObject(dialogueBox, getWidth()/2, getHeight()/2-100);
        
        promptBox = new BreathingTextBox("Click to continue...", 18, Color.WHITE, null, 240);
        nextWorldButton = new Button(() -> Greenfoot.setWorld(new IntroWorld()), 200, 75);
    }
    public void act() {
        if (stillMoreDialogue() && Greenfoot.mousePressed(null)) { // Progresses the dialogue, if still exists.
            visibleActCount = 0;
            playDialogue(++line);
            if (promptBox.getWorld() != null) removeObject(promptBox);
        }
        
        if (dialogueBox.isVisible()) { // Counts how long the current line has existed.
            visibleActCount++;
        }
        
        // After 3 seconds, add the prompt to continue dialogue.
        if (visibleActCount >= 180 && stillMoreDialogue() && promptBox.getWorld() == null) {
            addObject(promptBox, getWidth()/2, getWidth()/2 + 100);
        }
        
        // If should add the next world button at this current line, do so.
        if (line == lineForButtonToAppear && nextWorldButton.getWorld() == null) {
            addObject(nextWorldButton, getWidth()/2, getHeight()/2 + 50);
        }
        // else if (line == lineForEventPictureToAppear && eventPicture.getWorld() == null) {
            // addObject(eventPicture, getWidth()/2, getHeight()/2);
        // }
        
        // After 3 seconds after the last line in the good ending, add the breathing text box instead.
        if (goodEnding && !stillMoreDialogue() && visibleActCount >= 180 && dialogueBox.getWorld() != null) {
            removeObject(dialogueBox); // no need for it anymore. replace with the breathing text box.
            addObject(new BreathingTextBox("YOU WIN", 76, Color.WHITE, null, 60), getWidth()/2, getHeight()/2-200);
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
