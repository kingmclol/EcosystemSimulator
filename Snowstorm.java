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
    private int actsLeft;
    public Snowstorm () {
        super(500, 200);
    }

    public void addedToWorld (World w){
        super.addedToWorld(w);
        GreenfootImage image = Utility.drawSnow(w.getWidth(), w.getHeight(), 50);
        image.setTransparency(0);
        setImage(image);
        setLocation (w.getWidth()/ 2, w.getHeight()/2);
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
        Animal.setSnowing(false);
    }
    public void slowAnimals()
    {
        Animal.setSnowing(true);
        return;
    }
}
