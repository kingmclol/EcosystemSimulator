import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * Write a description of class Rain here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class Rain extends Effect
{
    private int actsLeft;

    public Rain() {
        image = Utility.drawRain(1024, 768, 50);
        setImage(image);

        actsLeft = 300;
    }
    
    public void addedToWorld (World w){
        setLocation (w.getWidth()/ 2, w.getHeight()/2);
    }
    /**
     * Act - do whatever the Rain wants to do. This method is called whenever
     * the 'Act' or 'Run' button gets pressed in the environment.
     */
    public void act()
    {
        actsLeft--;
        fade (actsLeft, 120);

        if (actsLeft == 0){
            getWorld().removeObject(this);
        }
    }
}
