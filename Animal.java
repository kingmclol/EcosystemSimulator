import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)
import java.util.ArrayList;

import java.util.Collections;

/**
 * Write a description of class Animal here.
 * 


 * <p>Animals are an SuperActor that moves around the ecosyteem, and has unique interactions with each other. Animals constantly
 * lose energy every act, and they must ensure that their energy must stay above zero to survive.</p>
 * 
 * <p>Animals also have the ability to breed, so their species would not die out.</p>
 * 
 * <p>Animals have three main states for what they want to do:</p>
 * <ul>
 *  <li>Nothing, where the animal just moves around. Occurs when the animal is not hungry, and does not want to breed.
 *  <li>Finding food, where the animal attempts to find some food for it to eat to increase its energy.
 *  <li>Finding a mate, where if the animal is not hungry and is able to breed, it would attempt to mate with another eligble animal.
 * </ul>
 * 
 * <p>Animals are also very dependent on ther SuperActor target, as the target is what the Animal should be currently
 * be moving towards; whether it is another animal, or a tile.</p>
 *
 * 
 * 
 * @author (Osmond Lin) 
 */

public abstract class Animal extends SuperActor {
    protected enum AnimalObjective {
        NONE,
        FIND_FOOD,
        FIND_MATE
    }
    protected AnimalObjective objective;

    protected static boolean isSnowing = false;
    
    protected int energy;
    protected int hp;

    protected int walkHeight;
    // protected ArrayList<Vector> currentPath;

    protected int viewRadius;

    protected double defaultSpeed;
    protected double currentSpeed;
    protected double sprintSpeed;
    protected double waterSpeed;
    protected String facing = "left";
    
    protected int currentAct = 0;
    protected boolean alive;
    protected boolean eating;
    protected boolean wantToEat;
    protected boolean swimming;

    protected int transparency;

    protected boolean ableToBreed;
    protected boolean breeding;
    protected int actsSinceLastBreeding;
    protected int breedingCounter;
    public static final int BREEDING_THRESHOLD = 2000;
    public static final int BREEDING_DELAY = 150;
    protected Tile currentTile;
    protected boolean beingEaten;
    protected SuperActor target;
//https://static.vecteezy.com/system/resources/thumbnails/011/411/862/small/pixel-game-life-bar-sign-filling-red-hearts-descending-pixel-art-8-bit-health-heart-bar-flat-style-vector.jpg

    public Animal() {
        transparency = 255;
        swimming = false;
        alive = true;
        eating = false;
        energy = 2000;
        hp = 1000;
        actsSinceLastBreeding = 0;
        ableToBreed = false;
        breeding = false;
        breedingCounter = 0;
        objective = AnimalObjective.NONE;
        enableStaticRotation();
    }

    protected abstract void animate();
    public void act() {
        if (!alive) { // Am not alive, so no need to do anything here other than potentially drown.
            if (currentTile instanceof WaterTile) { // died on a water tile.
                drown();
            }
            return; // Nothing else to do.
        }
        
        // Increment counters.
        currentAct++;
        actsSinceLastBreeding++;
        
        currentTile = Board.getTile(getPosition());
        
        if(currentTile instanceof WaterTile){ // If the animal is on a water tile right now
            swimming = true; // it is swmming.
            currentSpeed = waterSpeed; // adjust speed accordingly.
        }else{ // otherwise, it is moving normally.
            swimming = false;
            currentSpeed = defaultSpeed;
        }
        
        // Determining if the animal is hungry or not.
        if(energy < 1000){
            wantToEat = true;
        }else if(energy >= 2000){
            wantToEat = false;
        }
        
        
        if(!eating && !breeding){ // If not busy with anything, energy decreases.
            energy--;
        }
        
        // Manage death.
        if (energy <= 0 || hp <= 0) { // No energy or no health.
            die(); // this animal has died.
            return; // This animal has died. what else should it do? tap dance?
        }
        
        getFacing();
        
        

        if (wantToEat && !breeding && !beingEaten) { // If want to eat, and am not in any spicy situations...
            objective = AnimalObjective.FIND_FOOD; // Find something to eat.
        } 
        else if (ableToBreed && !beingEaten && !wantToEat) { // If I can breed, and am not being eaten, and am not hungry,
            objective = AnimalObjective.FIND_MATE; // Find a mate.
        }
        else { // Otherwise,
            objective = AnimalObjective.NONE; // Move randomly. (no objective in particular),
        }
        
        
        // Depending on the animal's objective, determine what tehy whould be doing.
        switch(objective) {
            case NONE:
                moveRandomly();
                break;
            case FIND_FOOD:
                findOrEatFood();
                break;
            case FIND_MATE:
                breed();
                break;
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
    /**
     * Method for the animal to breed. Have an implementation made here where the animal
     * finds another eligble mate and does its stuff.
     * 
     * Implementation should be where you force the target to be of the proper type, then safely cast it
     * to whatever you need it to be and do stuff with it.
     */
    protected abstract void breed();
    /**
     * Method for the animal to find or eat food. Make an implementaiotn where the animal attempts to find or eat... foood. yeah
     * 
     * Implementation should be where you force the target to be of the proper type, then safely cast it
     * to whatever you need it to be and do stuff with it.
     */
    protected abstract void findOrEatFood();
    /**
     * Dies
     */
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

    public boolean isBreeding() {
        return breeding;
    }

    public boolean isAbleToBreed() {
        return ableToBreed;
    }

    public void setAbleToBreed(boolean able) {
        ableToBreed = able;
    }

    public void setIsBreeding(boolean breed){
        breeding = breed;
    }
    /**
     * Makes the animal move randomly, selecting a nearby tile that is walkable and is not a water tile within half
     * their vision radius.
     */
    public void moveRandomly() {
        if (Greenfoot.getRandomNumber(4) == 0 && currentAct%60 == 0) { // randomly...
            target = null; // reset target tile
            ArrayList<Tile> tiles = (ArrayList<Tile>) getObjectsInRange(viewRadius/2, Tile.class); // get tiles within a certain radius
            Collections.shuffle(tiles); // randomize
            for (Tile t : tiles) {
                if (t == currentTile || t instanceof WaterTile) { // don't want to move onto own tile (lol) or end up on a water tile.
                    continue;
                }
                if (t.getHeightLevel() <= walkHeight){ // If the tile is applicable (walkable), seleect as target.
                    target = t;
                }
            }
        }
        
        if (target != null) { // if a target tile exists...
            moveTowards(target, currentSpeed, walkHeight); // pathfind towards it.
        }
    }

    public String getFacing()
    {
        int rotation = this.getRotation()%360;
        if((rotation >= 0 && rotation <= 45) || (rotation > 315 && rotation < 360))
        {
            facing = "right";
        }
        else if(rotation > 45 && rotation <= 135)//between 45-135 && between 135 and 225
        {
            facing = "down";
        }
        else if(rotation > 135 && rotation <= 225)//135 and 180, 180 to 225
        {
            facing = "left";
        }
        else if(rotation > 225 && rotation < 315)
        {
            facing = "up";
        }
        return facing;
    }
    public void decreaseTransparency(int value) {
        transparency = transparency - value;
        getImage().setTransparency(transparency);
        if(transparency == 0){
            getWorld().removeObject(this);
        }
    }

    public boolean isBeingEaten() {
        return beingEaten;
    }

    public void setBeingEaten(boolean eaten) {
        beingEaten = eaten;
    }

    public void drown() {
        transparency--;
        getImage().setTransparency(transparency);
        if(transparency == 0){
            getWorld().removeObject(this);
        }
    }
    
    public static void setSnowing(boolean snowing) {
        isSnowing = snowing; 
    }
    
}
