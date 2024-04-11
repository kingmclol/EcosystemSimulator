import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * Write a description of class CursorWorld here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public abstract class CursorWorld extends World
{
    protected static Cursor cursor = new Cursor();
    /**
     * Constructor for objects of class CursorWorld.
     * 
     */
    public CursorWorld()
    {    
        // Create a new world with 600x400 cells with a cell size of 1x1 pixels.
        super(1008, 768, 1, false); 
        setPaintOrder(Cursor.class,UI.class, BreedingEffect.class, Node.class, Animal.class, Tile.class);
        addObject(cursor, 0,0);
    }
    public void act()
    {
        
    }
    protected static Cursor getCursor(){
        return cursor;
        
    }
}
