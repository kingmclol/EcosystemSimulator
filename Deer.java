import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * Deer subclass that will eat berries
 * 
 * @author (Osmond Lin) 
 */
public class Deer extends Animal
{
    private BushTile targetBush;

    public Deer() {
        super();
        defaultSpeed = ((double)Greenfoot.getRandomNumber(11)/100.0) + 0.5;
        currentSpeed = defaultSpeed;
        waterSpeed = 0.7 * defaultSpeed;
        wantToEat = false;
        viewRadius = 400;
        walkHeight = 2;
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
            if(!wantToEat){
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

        if(alive && !breeding){
            if(wantToEat){
                findBerriesAndEat();
            }else{
                targetBush = null;
            }
        }
    }

    public void breed() {
        // Find another deer nearby
        partner = (Deer) getClosestInRange(this.getClass(), viewRadius, d -> !((Deer)d).isAbleToBreed() || !((Deer)d).isAlive()); // Adjust range as needed
        if(partner != null){
            findingPartner = true;
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
                moveTowards(partner, currentSpeed, walkHeight);
            }
        }else{
            findingPartner = false;
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
            targetTile = targetBush;
            if(distanceFrom(targetBush) < 10){
                targetBush.nibble(4);
                eat(7);
            }
            else {
                moveTowards(targetBush, currentSpeed, walkHeight);
            }
        }
    }
}
