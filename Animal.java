import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * Animal superclass where subclasses will inherit traits
 * and behaviours from
 * 
 * @author (Osmond Lin) 
 */

public abstract class Animal extends SuperActor {
    protected double speed;
    protected int energy;
    protected int stamina;
    protected int sprintSpeed;
    protected int waterSpeed;
    
    protected boolean alive;
    protected boolean eating;
    protected boolean full;
    /**
     * timeFlowing determines whether Tiles should act on their own.
     */
    protected static boolean timeFlowing = true;

    public Animal() {
        alive = true;
        eating = false;
        full = true;
        energy = 2000;
        enableStaticRotation();
    }

    public void act() {
        move(speed);
        energy--;
        if (energy <= 0) {
            die();
        }
    }

    public boolean isAlive() {
        return alive;
    }

    public boolean isEating() {
        return eating;
    }
    
    public void eat(int energyGain) {
        energy += energyGain;
    }

    public void die() {
        getWorld().removeObject(this);
    }
    
    public void moveRandomly() {
        if (Greenfoot.getRandomNumber (60) == 50)
        {
            turn (Greenfoot.getRandomNumber(360));
        }
    }
}
