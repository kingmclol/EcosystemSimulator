import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * Animal superclass where subclasses will inherit traits
 * and behaviours from
 * 
 * @author (Osmond Lin) 
 */

public abstract class Animal extends SuperActor {
    protected int energy;
    protected int hp;
    protected int hydration;

    protected double defaultSpeed;
    protected double currentSpeed;
    protected double sprintSpeed;
    protected double waterSpeed;

    protected boolean alive;
    protected boolean eating;
    protected boolean drinking;
    protected boolean full;
    protected boolean wantToEat;
    protected boolean wantToDrink;
    protected boolean swimming;
    protected boolean runningAway;

    protected int transparency;

    protected WaterTile targetWater;
    public Animal() {
        transparency = 255;
        runningAway = false;
        swimming = false;
        alive = true;
        eating = false;
        drinking = false;
        full = true;
        hydration = 3000;
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

        if(targetWater == null){
            drinking = false;
        }
        if(energy < 1000){
            wantToEat = true;
        }else if(energy >= 1800){
            wantToEat = false;
        }

        if(hydration < 1000){
            wantToDrink = true;
        }else if(hydration >= 2800){
            wantToDrink = false;
        }

        if(wantToDrink && !eating && alive){
            findAndDrinkWater();
        }else if(alive){
            targetWater = null;
            move(currentSpeed);
            moveRandomly();
        }

        if(!drinking && !eating && alive){
            energy--;
            hydration--;
        }
        
        if(currentTile instanceof WaterTile && energy <= 0){
            drown();
        }else if(energy <= 0 || hp <= 0 || hydration <= 0){
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

    public void takeDamage(int dmg) {
        hp = hp - dmg;
    }

    public int getHp() {
        return hp;
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

    public void findAndDrinkWater() {
        if(targetWater == null){
            targetWater = (WaterTile)getClosestInRange(WaterTile.class, 100);
            if(targetWater == null) {
                targetWater = (WaterTile)getClosestInRange(WaterTile.class, 180);
            }
            if(targetWater == null) {
                targetWater = (WaterTile)getClosestInRange(WaterTile.class, 250);
            }
        }

        if(targetWater != null) {
            if(!drinking){
                moveTowards(targetWater, currentSpeed);
            }
            System.out.println(distanceFrom(targetWater));
            if(isTouching(WaterTile.class)){
                drinking = true;
                drinkWater(4);
            }else{
                drinking = false;
            }
        }else{
            drinking = false;
            move(currentSpeed);
            moveRandomly();
        }
    }

    public void drinkWater(int waterAmount) {
        hydration = hydration + waterAmount;
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
