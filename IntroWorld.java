import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * Write a description of class IntroWorld here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class IntroWorld extends CursorWorld
{
    private GreenfootSound introWorldMusic; 
    /**
     * Constructor for objects of class IntroWorld.
     * 
     */
    BreathingTextBox promptBox;
    public IntroWorld()
    {
        super();
        promptBox = new BreathingTextBox("PRESS (L) TO START", 52, Color.BLACK, null, 120);
        addObject(promptBox, getWidth()/2, getHeight()/2+250);
        introWorldMusic = new GreenfootSound("Introworld.mp3");
        introWorldMusic.setVolume(30);
    }
    public void act() {
        if ("l".equals(Greenfoot.getKey())) {
            Greenfoot.setWorld(new StoryWorld());
            introWorldMusic.stop(); 
        }
    }
    public void started() {
        introWorldMusic.playLoop();
    }
    public void stopped() {
        introWorldMusic.pause(); 
    }
}
