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
    protected int hp;
    protected int sprintSpeed;
    protected int waterSpeed;

    protected boolean alive;
    protected boolean eating;
    protected boolean full;
    protected boolean wantToEat;

    public Animal() {
        alive = true;
        eating = false;
        full = true;
        energy = 2000;
        hp = 1000;
        enableStaticRotation();
    }

    public void act() {
        Tile currentTile = Board.getTile(getPosition());
        
        if(currentTile instanceof WaterTile){
            energy--;
            swimming = true;
            currentSpeed = waterSpeed;
        }else{
            swimming = false;
            if(!runningAway){
                currentSpeed = defaultSpeed;
            }
        }
        
        if(energy < 1000){
            wantToEat = true;
        }else if(energy >= 1800){
            wantToEat = false;
        }
        if(!eating && alive){
            energy--;
        }
        if(energy <= 0 || hp <= 0){
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
        alive = false;
        disableStaticRotation();
        setRotation(90);
    }

    public void moveRandomly() {
        if (Greenfoot.getRandomNumber (60) == 50)
        {
            turn (Greenfoot.getRandomNumber(360));
        }
    }
}
