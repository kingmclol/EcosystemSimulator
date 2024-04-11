import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)
/**
 * Write a description of class SimulationWorld here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class SimulationWorld extends World
{
    private int actCount, actsPassed = 0; 
    private boolean isNight; 
    /**
     * Constructor for objects of class SimulationWorld.
     * 
     */
    public SimulationWorld()
    {    
        // Create a new world with 600x400 cells with a cell size of 1x1 pixels.
        super(1008, 768, 1); 
        setPaintOrder(UI.class,BreedingEffect.class, Animal.class, Node.class, Tile.class);
        Board.setWorld(this);
        Tile.setTimeFlow(true);

        actCount = 0; 
        isNight = false;
    }
    
    public void act()
    {
        actCount++; 
        spawn();
        actsPassed++;
    }
    public void spawn() 
    {
        if (actCount >= 2400) {
            System.out.println("ADFA ADSFD F");
            addObject(new Night(), 0, 0);
            isNight = true; 
            actCount = 0;
        }
        
    }
    public int getActs()
    {
        return actsPassed;
    }
    public void setNight(boolean newNight)
    {
        isNight = newNight; 
    }
    
}
