import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * The indicator that tells the viewer whether if it is day time.
 * 
 * @author Tony Lin 
 * @version Mon April 22, 2024
 */
public class Sun extends SuperActor
{
    //Instance Variables:
    private GreenfootImage sun;
    /**
     * Constructor to initialize instance variables.
     */
    public Sun()
    {
        sun = new GreenfootImage("images/Sun.png");
        sun.scale(250*2/4, 250*2/4);
        sun.setTransparency(180);
        setImage(sun);
    }
    /**
     * Act - do whatever the sun wants to do. This method is called whenever
     * the 'Act' or 'Run' button gets pressed in the environment.
     */
    public void act()
    {
        // Add your action code here.
        SimulationWorld world = (SimulationWorld)getWorld();
        if(world.getNight() == true) // if world is nighttime
        {
            if(getY() < 800) // if not yet at the bottom of the screen
            {
                //keep moving down
                this.setLocation(getX(), getY()+(60/12));
                if(this.getY() > 700) // when its close to the bottom of the screen, make it invisible
                {
                    sun.setTransparency(0); // transparency to 0
                    setImage(sun);
                }
                
            }
        }
        else // it should be daytime
        {
            if(getY() > 150) // if its not near the top of the screen,
            {
                /*
                 * move up, make it visible(set its current image transparency val of 180) 
                 */
                this.setLocation(getX(), getY()-(50/12));
                sun.setTransparency(180);
                setImage(sun);
            }
        }
    }
}
