import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * Write a description of class Moon here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class Moon extends SuperActor
{
    private GreenfootImage moon;
    
    /**
     * Act - do whatever the Moon wants to do. This method is called whenever
     * the 'Act' or 'Run' button gets pressed in the environment.
     */
    public Moon()
    {
        moon = new GreenfootImage("images/Moon.png");
        moon.scale(250*2/4, 250*2/4);
        moon.mirrorHorizontally();
        moon.setTransparency(0);
        setImage(moon);
    }
    public void act()
    {
        // Add your action code here.
        SimulationWorld world = (SimulationWorld)getWorld();
        if(world.getNight() == false)
        {
            if(getY() < 810)
            {
                this.setLocation(getX(), getY()+(60/12));
            }
            if(getY() > 700)
            {
                moon.setTransparency(0);
                setImage(moon);
            }
        }
        else
        {
            if(getY() > 150)
            {
                this.setLocation(getX(), getY()-(60/12));
                moon.setTransparency(180);
                setImage(moon);
            }
        }
    }
}
