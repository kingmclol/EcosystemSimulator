import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)
import java.util.ArrayList;

/**
 * Write a description of class Snowstorm here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class Snowstorm extends Effect
{
    private static GreenfootSound snowSound = new GreenfootSound("snowstorm.mp3");
    public Snowstorm () {
        super(500, 300);
    }

    public void addedToWorld (World w){
        super.addedToWorld(w);
        GreenfootImage image = new GreenfootImage("snow.png");
        image.setTransparency(0);
        setImage(image);
        setLocation (w.getWidth()/ 2, w.getHeight()/2);
        snowSound.setVolume(100);
        snowSound.play(); 
    }

    /**
     * Act - do whatever the Snowstorm wants to do. This method is called whenever
     * the 'Act' or 'Run' button gets pressed in the environment.
     */
    public void act()
    {
        super.act(); 
    }
    public void startEffect() {
        slowAnimals();
    }
    public void stopEffect() {
        world.setSnowing(false); 
        Animal.setSnowing(false);
        stopSnowSound();
    }
    public void slowAnimals()
    {
        Animal.setSnowing(true);
        return;
    }
    public static void stopSnowSound() {
        snowSound.stop(); 
    }
}
