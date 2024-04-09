import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * Deer subclass that will eat berries
 * 
 * @author (Osmond Lin) 
 */
public class Deer extends Animal
{
    private BushTile targetBush;
    private boolean beingEaten;

    public Deer() {
        super();
        beingEaten = false;
        defaultSpeed = 1.0;
        currentSpeed = defaultSpeed;
        sprintSpeed = 1.2 * defaultSpeed;
        waterSpeed = 0.7 * defaultSpeed;
        wantToEat = false;
        viewRadius = 400;
    }

    /**
     * Act - do whatever the Deer wants to do. This method is called whenever
     * the 'Act' or 'Run' button gets pressed in the environment.
     */
    public void act()
    {
        super.act();
        actsSinceLastBreeding++;
        if(actsSinceLastBreeding >= BREEDING_THRESHOLD && alive){
            ableToBreed = true;
            if(!wantToEat && !wantToDrink){
                breed();
            }
        }else{
            ableToBreed = false;
        }

        if((targetBush == null) || targetBush.getWorld() == null || (getWorld() != null && !(distanceFrom(targetBush) < 5))){
            eating = false;
        }else{
            eating = true;
        }

        if(alive && !beingEaten && !breeding && !drinking){
            if(wantToEat){
                full = false;
                findBerriesAndEat();
            }else{
                targetBush = null;
                full = true;
            }
        }
    }

    public void breed() {
        // Find another deer nearby
        partner = (Deer) getClosestInRange(this.getClass(), viewRadius, d -> !((Deer)d).isAbleToBreed() || !((Deer)d).isAlive()); // Adjust range as needed
        if(partner != null){
            if(distanceFrom(partner) < 40){
                breeding = true;
                breedingCounter++;
                if(breedingCounter > BREEDING_DELAY){
                    // Add the baby to the world
                    getWorld().addObject(new Deer(), getX(), getY());
                    ableToBreed = false;
                    partner.setAbleToBreed(false);
                    breeding = false;
                    partner.setIsBreeding(false);
                    breedingCounter = 0;
                    partner = null;
                    actsSinceLastBreeding = 0;

                }
            }else{
                moveTowards(partner, currentSpeed);
            }
        }else{
            moveRandomly();
            move(currentSpeed);
        }
    }

    public void animate() {
        return;
    }

    public void findBerriesAndEat() {
        if(targetBush == null || targetBush.getWorld() == null || !targetBush.berriesAvailable()){
            targetBush = (BushTile)getClosestInRange(BushTile.class, viewRadius/4, b -> !((BushTile)b).berriesAvailable());
            if(targetBush == null) {
                targetBush = (BushTile)getClosestInRange(BushTile.class, viewRadius/2, b -> !((BushTile)b).berriesAvailable());
            }
            if(targetBush == null) {
                targetBush = (BushTile)getClosestInRange(BushTile.class, viewRadius, b -> !((BushTile)b).berriesAvailable());
            }
        }

        if(targetBush != null) {
            moveTowards(targetBush, currentSpeed);
            if(distanceFrom(targetBush) < 5){
                targetBush.nibble(4);
                eat(7);
            }
        }else{
            move(currentSpeed);
            moveRandomly();
        }
    }

    public boolean isBeingEaten() {
        return beingEaten;
    }

    public void setBeingEaten(boolean eaten) {
        beingEaten = eaten;
    }
}
