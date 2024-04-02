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

    public IntroWorld()
    {    
        
        super(1024, 768, 1);
        addObject(cursor, 0, 0);
    }
    
    
}
