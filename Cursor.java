import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)
import java.util.ArrayList;

/**
 * The cursor is an actor that follows the mouse position, if it exists.
 * 
 * @author Freeman Wang
 * @version 2024-04-21
 */
public class Cursor extends SuperActor
{
    /**
     * Act - do whatever the Cursor wants to do. This method is called whenever
     * the 'Act' or 'Run' button gets pressed in the environment.
     */
    private GreenfootImage img;
    public Cursor() {
        img = new GreenfootImage(getImage());
        hideCursor(); // invisible by default
    }
    public void act()
    {
        MouseInfo mouse = Greenfoot.getMouseInfo(); // Recieve mouse info
        if (mouse != null) { // if mouse exists,
            setImage(img);
            setLocation(mouse.getX(), mouse.getY()); // set the cursor at hte mouse position.
        }
        else { // if mouse does not exist (outside of the world), set as invisible image.
            setImage(new GreenfootImage(1,1));
        }
    }
    /**
     * Returns an actor that is being hovered.
     * @return the topmost(?) actor at the pixel where the mouse is
     */
    public Actor getHoveredActor() {
        return getOneObjectAtOffset(0,0,Actor.class);
    }
    /**
     * Returns the actors that are being hovered.
     * @return the actors at the pixel where the mouse is as an ArrayList
     */
    public ArrayList<Actor> getHoveredActors() {
        return (ArrayList<Actor>) getObjectsAtOffset(0, 0, Actor.class);
    }
    /**
     * Hides the cursor.
     */
    public void hideCursor() {
        img.setTransparency(0);
        setImage(img);
    }
    /**
     * Shows the cursor.
     */
    public void showCursor() {
        img.setTransparency(255);
        setImage(img);
    }
}
