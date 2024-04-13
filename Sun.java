import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * Write a description of class sun here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class Sun extends SuperActor
{
    private GreenfootImage sun;
    /**
     * Act - do whatever the sun wants to do. This method is called whenever
     * the 'Act' or 'Run' button gets pressed in the environment.
     */
    public Sun()
    {
        sun = new GreenfootImage("images/Sun.png");
        sun.scale(250*3/4, 250*3/4);
        sun.setTransparency(180);
        setImage(sun);
    }
    public void act()
    {
        // Add your action code here.
        SimulationWorld world = (SimulationWorld)getWorld();
        if(world.getNight() == true)
        {
            if(getY() < 800)
            {
                this.setLocation(getX(), getY()+(60/12));
                //figure something out later maybe
                //int transparencyFactor = (int)(100*Math.pow(0.5, (getX()/200-1)));                   
                /*if(transparencyFactor < 0)
                {
                    sun.setTransparency(0);
                    setImage(sun);
                }
                else
                {
                    sun.setTransparency(180*transparencyFactor/100);
                    setImage(sun);
                }
                System.out.println(getX());
                System.out.println(Math.pow(0.5, (this.getX()/400-1)));*/
                if(this.getY() > 700)
                {
                    sun.setTransparency(0);
                    setImage(sun);
                }
                
            }
        }
        else
        {
            if(getY() > 150)
            {
                this.setLocation(getX(), getY()-(50/12));
                sun.setTransparency(180);
                setImage(sun);
            }
        }
    }
}
