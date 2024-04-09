import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * Write a description of class Night here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class Night extends Effect
{
    private int actsLeft; 
    public Night() 
    {
        image = new GreenfootImage(1024, 768);
        image.setColor(Color.BLACK);
        image.fill(); 
        image.setTransparency(150); 
        
        actsLeft = 1200; 
    }
    /**
     * Act - do whatever the Night wants to do. This method is called whenever
     * the 'Act' or 'Run' button gets pressed in the environment.
     */
    public void act()
    {
        actsLeft--; 
        
        if (actsLeft == 0) {
            SimulationWorld sw = (SimulationWorld)getWorld();
            getWorld().removeObject(this); 
            
            sw.setNight(false);
        }
    }
}
