import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)
import java.util.ArrayList;

/**
 * Write a description of class Cursor here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class Cursor extends SuperActor
{
    /**
     * Act - do whatever the Cursor wants to do. This method is called whenever
     * the 'Act' or 'Run' button gets pressed in the environment.
     */
    private GreenfootImage img;
    public Cursor() {
        //getImage().clear();
        img = new GreenfootImage(getImage());
    }
    public void act()
    {
        MouseInfo mouse = Greenfoot.getMouseInfo();
        if (mouse != null) {
            setImage(img);
            setLocation(mouse.getX(), mouse.getY());
        }
        else {
            setImage(new GreenfootImage(1,1));
        }
    }
    public Actor getHoveredActor() {
        return getOneObjectAtOffset(0,0,Actor.class);
    }
    public ArrayList<Actor> getHoveredActors() {
        return (ArrayList<Actor>) getObjectsAtOffset(0, 0, Actor.class);
    }

}
