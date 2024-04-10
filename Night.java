import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * Write a description of class Night here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class Night extends Effect
{
    public Night() 
    {
        super(1200, 180); 
    }
    public void addedToWorld(World w) {
        super.addedToWorld(w);
        GreenfootImage image = new GreenfootImage(w.getWidth(), w.getHeight());
        image.setColor(Color.BLACK);
        image.fill();
        image.setTransparency(0);
        setImage(image);
        setLocation(w.getWidth()/2, w.getHeight()/2);
    }
    public void startEffect() {
        SimulationWorld.setNight(true);
        return;
    }
    public void stopEffect() {
        SimulationWorld.setNight(false); 
        return;
    }
    /**
     * Act - do whatever the Night wants to do. This method is called whenever
     * the 'Act' or 'Run' button gets pressed in the environment.
     */
    public void act()
    {
        super.act();
    }
}
