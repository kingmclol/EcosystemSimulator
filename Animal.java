import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * Animal superclass where subclasses will inherit traits
 * and behaviours from
 * 
 * @author (Osmond Lin) 
 */

public class Animal extends Actor {
    private int speed;
    private int energy;

    public Animal() {
        
    }

    public void act() {
        move(speed);
        energy--;
        if (energy <= 0) {
            die();
        }
    }

    public void eat(int energyGain) {
        energy += energyGain;
    }

    public void die() {
        getWorld().removeObject(this);
    }
}


