import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * Write a description of class BaseWorld here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class IntroWorld extends World
{
    
    private static Cursor cursor = new Cursor();
    private ButtonIncrement animalCounter1;
    private ButtonIncrement animalCounter2;
    public IntroWorld()
    {    
        super(1024, 768, 1);
        addObject(cursor, 0, 0);
        setPaintOrder(Cursor.class, UI.class,  Rabbit.class,  Tile.class);
        animalCounter1 = new ButtonIncrement(400, 50, 300, 50);
        animalCounter2 = new ButtonIncrement(400, 50, 300, 50);
        addObject(animalCounter1, getWidth()/2, 150);
        addObject(animalCounter2, getWidth()/2, 400);
    }
    public static Cursor getCursor(){
        return cursor;
        
    }
    
}
