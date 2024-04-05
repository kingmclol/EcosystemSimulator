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
            breed();
        }else{
            ableToBreed = false;
        }

        if(alive && !beingEaten && !breeding && !drinking){
            if((targetBush == null) || targetBush.getWorld() == null || !(distanceFrom(targetBush) < 5)){
                eating = false;
            }else{
                eating = true;
            }

            if(wantToEat){
                full = false;
                findBerriesAndEat();
            }else{
                targetBush = null;
                full = true;
                move(currentSpeed);
                moveRandomly();
            }
        }
    }

    public void breed() {
        // Find another deer nearby
        partner = (Deer) getClosestInRange(this.getClass(), 300, d -> !((Deer)d).isAbleToBreed() || !((Deer)d).isAlive()); // Adjust range as needed
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
        }
    }
    public void animate(){
    }
    public void findBerriesAndEat() {
        if(targetBush == null || targetBush.getWorld() == null || targetBush.getBerryAmount() <= 150){
            targetBush = (BushTile)getClosestInRange(BushTile.class, 100, b -> ((BushTile)b).getBerryAmount() <= 150);
            if(targetBush == null) {
                targetBush = (BushTile)getClosestInRange(BushTile.class, 180, b -> ((BushTile)b).getBerryAmount() <= 150);
            }
            if(targetBush == null) {
                targetBush = (BushTile)getClosestInRange(BushTile.class, 250, b -> ((BushTile)b).getBerryAmount() <= 150);
            }
        }

        if(targetBush != null) {
            moveTowards(targetBush, currentSpeed);
            if(distanceFrom(targetBush) < 5){
                targetBush.nibble(7);
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
