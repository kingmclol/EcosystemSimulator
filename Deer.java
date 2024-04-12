import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * Deer subclass that will eat berries
 * 
 * @author (Osmond Lin) 
 */
public class Deer extends Animal
{
    public Deer() {
        super();
        beingEaten = false;
        defaultSpeed = 1.0;
        currentSpeed = defaultSpeed;
        sprintSpeed = 1.2 * defaultSpeed;
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
        if (!alive) return; // There has to be a better way than doing this, right? right?????
        actsSinceLastBreeding++;
        if(actsSinceLastBreeding >= BREEDING_THRESHOLD && alive){
            ableToBreed = true;
            if(!wantToEat){
                breed();
            }
        }else{
            ableToBreed = false;
        }

        if((target == null) || target.getWorld() == null || (getWorld() != null && !(distanceFrom(target) < 5))){
            eating = false;
        }else{
            eating = true;
        }
    }

    public void breed() {
        // Find another deer nearby
        if (!(target instanceof Deer)) { // Find a Deer if the target is null, or not a deer.
            SuperActor search = (Deer) getClosestInRange(this.getClass(), viewRadius, d -> !((Deer)d).isAbleToBreed() || !((Deer)d).isAlive()); // Adjust range as needed
            if (search != null){ // found one!
                target = search; // set it as the target.
            }
        }
        
        if (target instanceof Deer) { // check for null or if target is not a deer.
            Deer targetDeer = (Deer) target; // cast target into Deer object
            
            // check for need for retargeting.
            if (targetDeer.getWorld() == null || !targetDeer.isAlive() || !targetDeer.isAbleToBreed()) {
                target = null;
                return;
            }
            else if(distanceFrom(target) < 40){ // close enough, breed
                breeding = true;
                breedingCounter++;
                if(breedingCounter > BREEDING_DELAY){
                    // Add the baby to the world

                    
                    getWorld().addObject(new Deer(), getX(), getY());
                    ableToBreed = false;
                    targetDeer.setAbleToBreed(false);
                    breeding = false;
                    targetDeer.setIsBreeding(false);
                    breedingCounter = 0;
                    
                    target = null; // no target anymore.
                    actsSinceLastBreeding = 0;

                }
            }else{ // far, move closer
                moveTowards(target, currentSpeed, walkHeight);
            }
        } else {
            moveRandomly(); // no target deer. move randomly instead :(
        }
    }

    public void animate() {
        return;
    }

    public void findOrEatFood() {
        if (!(target instanceof BushTile)) { // force the target to be a bush tile
            SuperActor search = (BushTile)getClosestInRange(BushTile.class, viewRadius/4, b -> !((BushTile)b).berriesAvailable());
            if(search == null) {
                search = (BushTile)getClosestInRange(BushTile.class, viewRadius/2, b -> !((BushTile)b).berriesAvailable());
            }
            if(search == null) {
                search = (BushTile)getClosestInRange(BushTile.class, viewRadius, b -> !((BushTile)b).berriesAvailable());
            }
            
            if (search != null) { // found one!
                target = search; // set the target as the found one.
            }
        }
        
        if (target instanceof BushTile) { // not null, is bush tile
            BushTile targetBush = (BushTile) target; // Cast into a BushTile.
            
            // check if retargeting is required.
            if (targetBush.getWorld() == null || !targetBush.berriesAvailable()) {
                target = null; // need new target.
                return;
            }
            else if(targetBush != null) {
                if(distanceFrom(targetBush) < 10){ // close, so eat.
                    targetBush.nibble(4);
                    eat(7);
                }
                else { // far, so move closer.
                    moveTowards(targetBush, currentSpeed, walkHeight);
                }
            }
        }
        else { // nothinf gound.
            moveRandomly(); 
        }
    }
}
