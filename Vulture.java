import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * Vulture subclass
 * Vultures will eat dead animals
 * 
 * @author (Osmond Lin) 
 * @version (a version number or a date)
 */
public class Vulture extends Animal
{
    Rabbit targetRabbit;
    Wolf targetWolf;
    
    public Vulture() {
        super();
        speed = 1.3;
        wantToEat = false;
    }
    /**
     * Act - do whatever the Vulture wants to do. This method is called whenever
     * the 'Act' or 'Run' button gets pressed in the environment.
     */
    public void act()
    {
        super.act();
    }
    
    public void findDeadAnimalsAndEat() {
        
    }
}
