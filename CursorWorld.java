import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * This is an abstract parent class which all worlds that have a cursor inherit from.
 * 
 * @author Neelan Thurairajah
 * @version 2024-04-14
 */
public abstract class CursorWorld extends World
{
    protected static Cursor cursor = new Cursor();
    /**
     * Default Constructor for objects of class CursorWorld.
     */
    public CursorWorld()
    {    
        // Create a new world with 600x400 cells with a cell size of 1x1 pixels.
        super(1008, 768, 1, false); 
        setPaintOrder(Cursor.class, TextBox.class, UI.class, BreedingEffect.class, Animal.class, Node.class, Tile.class);
        addObject(cursor, 0,0);
    }

    /**
     * Gets the cursor.
     * @return Cursor object
     */
    protected static Cursor getCursor(){
        return cursor;
    }
}
