import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * The indicator that tells the viewer whether if it is night time.
 * 
 * @author Tony Lin
 * @version Mon April 22, 2024
 */
public class Moon extends SuperActor
{
    //Instance Variables:
    private GreenfootImage moon;
    /**
     * Constructor to initialize instance variables.
     * Also, set the image of the crescent shape facing right, to fit it being on the
     * left part of the screen.
     */
    public Moon()
    {
        moon = new GreenfootImage("images/Moon.png");
        moon.scale(250*2/4, 250*2/4);
        moon.mirrorHorizontally();
        moon.setTransparency(0);
        setImage(moon);
    }
    /**
     * Act - do whatever the Moon wants to do. This method is called whenever
     * the 'Act' or 'Run' button gets pressed in the environment.
     */
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
