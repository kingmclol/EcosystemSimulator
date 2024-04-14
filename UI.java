import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * <p>UI is an abstract class that is used for all the User Interface and visual elements in the World. Any element that 
 * the user interacts with directly using the mouse inherits from this class.<p>
 * 

 * 
 * @author Neelan Thurairajah
 * @version
 */
public abstract class UI extends SuperActor
{

    protected Cursor cursor;
    /**
     * Stores the cursor in a variable upon a UI element being added to the world.
     * @param w The current world the object is being added to.
     */
    protected void addedToWorld(World w){
        cursor = getCursor();
    }
    /**
     * Retrieves the cursor object using the static method from the world when called.
     * @return The cursor object
     */
    protected Cursor getCursor()
    {
        return CursorWorld.getCursor();
    }

}
