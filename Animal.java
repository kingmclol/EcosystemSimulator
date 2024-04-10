import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)
import java.util.ArrayList;

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

    protected int walkHeight;
    protected ArrayList<Vector> currentPath;

    protected int viewRadius;

    protected double defaultSpeed;
    protected double currentSpeed;
    protected double sprintSpeed;
    protected double waterSpeed;
    protected String facing = "left";
    
    protected int currentAct = 0;
    protected boolean alive;
    protected boolean eating;
    protected boolean drinking;
    protected boolean full;
    protected boolean wantToEat;
    protected boolean wantToDrink;
    protected boolean swimming;
    protected boolean runningAway;

    protected int transparency;
    protected Animal partner;
    protected boolean ableToBreed;
    protected boolean breeding;
    protected int actsSinceLastBreeding;
    protected int breedingCounter;
    public static final int BREEDING_THRESHOLD = 2000;
    public static final int BREEDING_DELAY = 150;
    protected Tile currentTile;
    
//https://static.vecteezy.com/system/resources/thumbnails/011/411/862/small/pixel-game-life-bar-sign-filling-red-hearts-descending-pixel-art-8-bit-health-heart-bar-flat-style-vector.jpg
  
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
        actsSinceLastBreeding = 0;
        ableToBreed = false;
        breeding = false;
        breedingCounter = 0;
        enableStaticRotation();
    }

    protected abstract void animate();
    public void act() {
        currentAct++;
        currentTile = Board.getTile(getPosition());
        if(currentTile instanceof WaterTile){
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
        }else if(energy >= 2000){
            wantToEat = false;
        }

        if(hydration < 1200){
            wantToDrink = true;
        }else if(hydration >= 2800){
            wantToDrink = false;
        }

        if(wantToDrink && !eating && alive && !breeding && hydration <= energy){
            findAndDrinkWater();

        }else if(alive){
            targetWater = null;
        }
      
        if(!drinking && !eating && alive && !breeding){
            energy--;
            hydration--;
        }
        getFacing();
        if(currentTile instanceof WaterTile && (energy <= 0 || hydration <= 0)){
            die();
            drown();
        }else if(energy <= 0 || hp <= 0 || hydration <= 0){
            die();
        }

        if(currentPath == null && !eating && !drinking && alive){
            moveRandomly();
            move(currentSpeed);
        }
        /*
        if(!wantToDrink && !wantToEat && alive && !breeding){
            move(currentSpeed);
            moveRandomly();
        }
        */
        
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

    protected abstract void breed();

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

    protected void pathfindToTile(Tile targetTile,int stopDistance){
        if(currentPath == null){
            Vector startPos = new Vector(getX(), getY());
            Vector endPos = new Vector(targetTile.getX(), targetTile.getY());
            ArrayList<Node> pathNodes = Board.findPath(startPos, endPos, walkHeight);
            if(pathNodes != null){
                
                Board.displayPath(pathNodes, Color.BLACK);
                currentPath = new ArrayList<Vector>();
                for (Node node : pathNodes){
                    currentPath.add(Board.getRealPositionWithNode(node));
                }
            }

            
        }
        else{
            if(currentPath.size() == 1){
                Vector finalTile = currentPath.get(0);
                moveTowards(finalTile, currentSpeed);
  
            }
            else{
                Vector nextTile = currentPath.get(1);
                if((Board.getTile(nextTile)).getHeightLevel() > walkHeight){
                    currentPath = null;
                }
                else{
                    moveTowards(nextTile, currentSpeed);
    
                    if(distanceFrom(nextTile) < 12){
                        currentPath.remove(0);
        
                    }
                }
            }
        }
    }

    public void moveRandomly() {
        if (Greenfoot.getRandomNumber (60) == 50 && (currentAct%60 == 0)) {
            int angle = Greenfoot.getRandomNumber(360);
            turn (angle);
        }
    }

    public String getFacing()
    {
        int rotation = this.getRotation()%360;
        System.out.println(rotation);
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

            if(isTouching(WaterTile.class)){
                drinking = true;
                drinkWater(4);
            }else{
                drinking = false;
            }
        }else{
            drinking = false;
 
        }
    }

    public void drinkWater(int waterAmount) {
        hydration = hydration + waterAmount;
    }

    public void drown() {
        transparency--;
        getImage().setTransparency(transparency);
        if(transparency == 0){
            getWorld().removeObject(this);
        }
    }
}
