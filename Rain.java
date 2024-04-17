import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * Write a description of class Rain here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class Rain extends Effect
{
    private static GreenfootSound rainSound = new GreenfootSound("Rain.mp3");    
    public Rain() {
        super(600, 100);
    }
    
    public void addedToWorld(World w) {
        super.addedToWorld(w);
        GreenfootImage image = Utility.drawRain(1024, 768, 50);
        image.setTransparency(0);
        setImage(image);
        setLocation(w.getWidth()/2, w.getHeight()/2);
        rainSound.playLoop(); 
    }
    /**
     * Act - do whatever the Rain wants to do. This method is called whenever
     * the 'Act' or 'Run' button gets pressed in the environment.
     */
    public void act()
    {
        super.act();
    }
    public void startEffect() {
        Tile.setRaining(true);
        return;
    }
    public void stopEffect() {
        rainSound.stop();
        Tile.setRaining(false); 
        world.setRaining(false);
        return;
    }
    public static void stopRainSound() {
        rainSound.stop();
    }
}
