import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * Write a description of class IntroWorld here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class IntroWorld extends CursorWorld
{

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
    }
    public void act() {
        if ("l".equals(Greenfoot.getKey())) {
            Greenfoot.setWorld(new StoryWorld());
        }
    }
}
