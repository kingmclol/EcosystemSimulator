import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * Animal superclass where subclasses will inherit traits
 * and behaviours from
 * 
 * @author (Osmond Lin) 
 */

public abstract class Animal extends SuperActor {
    protected double defaultSpeed;
    protected double currentSpeed;
    protected int energy;
    protected int hp;
    protected double sprintSpeed;
    protected double waterSpeed;

    protected boolean alive;
    protected boolean eating;
    protected boolean full;
    protected boolean wantToEat;
    protected boolean swimming;
    protected boolean runningAway;

    protected int transparency;
    public Animal() {
        transparency = 255;
        runningAway = false;
        swimming = false;
        alive = true;
        eating = false;
        full = true;
        energy = 2000;
        hp = 1000;
        enableStaticRotation();
    }

    public void act() {
        int animalX = getX();
        int animalY = getY();

        // Convert position to tile coordinates
        int tileX = animalX / 64;
        int tileY = animalY / 64;

        // Access the tile at the converted coordinates using Board.getTile()
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
        if(currentTile instanceof WaterTile && energy <= 0){
            drown();
        }else if(energy <= 0 || hp <= 0){
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
        if (Greenfoot.getRandomNumber (60) == 50) {
            turn (Greenfoot.getRandomNumber(360));
        }
    }
    
    public void decreaseTransparency(int value) {
        transparency = transparency - value;
        getImage().setTransparency(transparency);
        if(transparency == 0){
            getWorld().removeObject(this);
        }
    }
    
    public void drown() {
        alive = false;
        transparency--;
        getImage().setTransparency(transparency);
        if(transparency == 0){
            getWorld().removeObject(this);
        }
    }
}
