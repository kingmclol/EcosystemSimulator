import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)
import java.util.ArrayList;
import java.util.List;
import java.util.Collections;

/**
 * Animal Superclass
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
 * @author Osmond Lin
 * @version 2024-04-14
 */

public abstract class Animal extends SuperActor {
    protected enum AnimalObjective {
        NONE,
        FIND_FOOD,
        FIND_MATE
    }
    protected AnimalObjective objective;

    protected static boolean isSnowing = false;

    protected int hp;
    protected int energy;
    protected boolean baby;
    protected int walkHeight;

    protected int viewRadius; //How far animals can detect other animals
    protected int currentViewRadius;
    protected int loweredViewRadius;

    protected double defaultSpeed;
    protected double currentSpeed;
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
    protected int breedingThreshold;
    public static final int BREEDING_DELAY = 150;
    protected Tile currentTile;

    protected int actsAsBaby;
    protected SuperActor target; //Either a tile, or animal being targetted

    protected int actsSinceDeath;
    //https://static.vecteezy.com/system/resources/thumbnails/011/411/862/small/pixel-game-life-bar-sign-filling-red-hearts-descending-pixel-art-8-bit-health-heart-bar-flat-style-vector.jpg
    /**
     * Constructor for Animal, that takes in a parameter
     * 
     * @param isBaby   boolean that determines if the animal is a baby
     */
    public Animal(boolean isBaby) {
        if(isBaby){
            baby = true;
            actsAsBaby = 0;
        }else{
            baby = false;
        }
        hp = 1000;
        actsSinceDeath = 0;
        transparency = 255;
        swimming = false;
        alive = true;
        eating = false;
        energy = 2000;
        actsSinceLastBreeding = 0;
        ableToBreed = false;
        breeding = false;
        breedingCounter = 0;
        objective = AnimalObjective.NONE;
        enableStaticRotation();
    }


    public void act() {
        if (!alive) { // Animal is not alive, so no need to do anything other than disappear after a while
            if (currentTile instanceof WaterTile) { // died on a water tile.
                decreaseTransparency(1);
            }
            actsSinceDeath++;
            if(actsSinceDeath >= 1500){ // Animal decomposes after a while
                decreaseTransparency(1);
            }
            return; // Nothing else to do.
        }
        
        getFacing(); //gets the direction animal is facing to manage animation
        inTree(); // manages animal's transparency when they are on a tree tile

        // Increment counters.
        currentAct++;
        if(!baby){ // to prevent newly spawned animals to breed until they become an adult
            actsSinceLastBreeding++;
        }

        currentTile = Board.getTile(getPosition()); //gets the current tile the animal is on

        if(currentTile instanceof WaterTile && !(this instanceof Vulture)){ // If the animal is on a water tile right now
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
            return;
        }

        if(!breeding)
        {
            animate();
        }


        if (wantToEat && !breeding) { // If want to eat, and not breeding
            objective = AnimalObjective.FIND_FOOD; // Find something to eat.
        } 
        else if (ableToBreed && !wantToEat) { // If animal can breed, and is not hungry,
            objective = AnimalObjective.FIND_MATE; // Find a mate.
        }
        else { // Otherwise,
            objective = AnimalObjective.NONE; // Move randomly. (no objective in particular),
        }

        // Depending on the animal's objective, determine what they whould be doing.
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
    
        if(!wantToEat){ 
            eating = false;
        }

        if(baby){ // manages how long an animal is a baby
            actsAsBaby++;
            if(actsAsBaby >= 1200){
                baby = false;
            }
        }
        
        if(isSnowing){//animals cannot see as far when it is snowing
            currentViewRadius = loweredViewRadius;
        }else{
            currentViewRadius = viewRadius;
        }
    }
    
    /**
     * Abstract method for the animation of animals
     * 
     * Each animal has its own unique animations
     */
    protected abstract void animate();
    
    /**
     * Getter method for whether the animal is alive
     * 
     * @return alive  boolean for if the animal is alive or not
     */
    public boolean isAlive() {
        return alive;
    }

    /**
     * Getter method for whether the animal is eating
     * 
     * @return eating  boolean for if the animal is eating or not
     */
    public boolean isEating() {
        return eating;
    }
    
    /**
     * Method for animals to gain energy when they eat
     * 
     * @param energyGain  the specific value that they gain each act when they eat
     */
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
     * Method for when animals die
     */
    public void die() {
        if(this instanceof Rabbit){
            Rabbit.setNumOfRabbits(Rabbit.getNumOfRabbits() - 1);
        }else if(this instanceof Goat){
            Goat.setNumOfGoats(Goat.getNumOfGoats() - 1);
        }else if(this instanceof Vulture){
            Vulture.setNumOfVultures(Vulture.getNumOfVultures() - 1);
        }else if(this instanceof Wolf){
            Wolf.setNumOfWolves(Wolf.getNumOfWolves() - 1);
        }
        alive = false;
        disableStaticRotation();
        setRotation(90);
    }
    
    /**
     * Method for animals to lose hp, specifically when they are being eaten
     * 
     * @param dmg  the amount of hp they lose
     */
    protected void takeDamage(int dmg) {
        hp = hp - dmg;
    }

    /**
     * Getter method to access energy value
     * 
     * @return hp   current hp value of animal
     */
    public int getHp() {
        return hp;
    }

    /**
     * Getter method to access if the animal is currently breeding
     * 
     * @return breeding  boolean that determines if animal is breeding or not
     */
    public boolean isBreeding() {
        return breeding;
    }
    
    /**
     * Getter method to access if the animal is able to breed
     * 
     * @return ableToBreed   boolean that determines if the animal is capable of breeding
     */
    public boolean isAbleToBreed() {
        return ableToBreed;
    }

    /**
     * Setter method to change whether the animal is able to breed
     * 
     * @param able  the new boolean of the animal's capability to breed
     */
    public void setAbleToBreed(boolean able) {
        ableToBreed = able;
    }

    /**
     * Setter method to change whether the animal is breeding
     * 
     * @param isBreeding   the new boolean that determines if the animal is breeding or not
     */
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
    
    /**
     * Method that gets the direction the animal is facing
     * 
     * @return facing  the specific direction animal is facing
     */
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
        else if(rotation > 225 && rotation <= 315)
        {
            facing = "up";
        }
        return facing;
    }
    
    /**
     * Method to decrease transparency of objects
     * 
     * @param specific value of decrease 
     */
    public void decreaseTransparency(int value) {
        transparency = transparency - value;
        getImage().setTransparency(transparency);
        if(transparency == 0){
            getWorld().removeObject(this);
        }
    }

    
    /**
     * Method checks for animals on tree tiles, and decreases their
     * transparency if they are on one
     */
    public void inTree()
    {
        List<Tile> currentTiles = getIntersectingObjects(Tile.class);
        boolean isOnTreeTile = false;
        for(Tile t : currentTiles)
        {
            if(t instanceof TreeTile && distanceFrom(t) < 18)
            {
                isOnTreeTile = true;
                break;
            }
        }
        if(isOnTreeTile)
        {
            GreenfootImage temp = this.getImage();
            temp.setTransparency(255/2);
            setImage(temp);
        }
        else
        {
            GreenfootImage temp = this.getImage();
            temp.setTransparency(255);
            setImage(temp);
        }
    }
    
    /**
     * Setter method for snowing variable
     * 
     * @param snowing  boolean for if it is currently snowing
     */
    public static void setSnowing(boolean snowing) {
        isSnowing = snowing; 
    }
}
