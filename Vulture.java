import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * Vulture subclass
 * Vultures will eat dead animals
 * 
 * @author (Osmond Lin) 
 * @version (a version number or a date)
 */
public class Vulture extends Animal
{
    private Animal targetAnimal;

    public Vulture(boolean isBaby) {
        super(isBaby);
        defaultSpeed = ((double)Greenfoot.getRandomNumber(11)/100.0) + 0.7;
        currentSpeed = defaultSpeed;
        wantToEat = false;
        viewRadius = 400;
        walkHeight = 3;
        breedingThreshold = 3000;
    }
    
    public Vulture() {
        super(false);
        defaultSpeed = ((double)Greenfoot.getRandomNumber(11)/100.0) + 0.7;
        currentSpeed = defaultSpeed;
        wantToEat = false;
        viewRadius = 400;
        walkHeight = 3;
        breedingThreshold = 3000;
    }

    /**
     * Act - do whatever the Vulture wants to do. This method is called whenever
     * the 'Act' or 'Run' button gets pressed in the environment.
     */
    public void act()
    {
        super.act();
        if (!alive) return; // dead.
        
        if(((target == null) || target.getWorld() == null || (getWorld() != null && !(distanceFrom(target) < 5)))){
            eating = false;
        }else{
            eating = true;
        }
    }

    public void breed() {
        // Find another vulture nearby
        partner = (Vulture) getClosestInRange(this.getClass(), viewRadius, v -> !((Vulture)v).isAbleToBreed() || !((Vulture)v).isAlive()); // Adjust range as needed
        if(partner != null){
            findingPartner = true;
            if(distanceFrom(partner) < 40){
                breeding = true;
                breedingCounter++;
                if(breedingCounter > BREEDING_DELAY){
                    // Add the baby to the world
                    getWorld().addObject(new Vulture(true), getX(), getY());
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

    public void findOrEatFood() {
        if (!(target instanceof Animal)) { // force the target to be of correct type
            SuperActor search = (Animal)getClosestInRange(Animal.class, viewRadius/4, a -> ((Animal)a).isAlive());
            if(search == null) {
                search = (Animal)getClosestInRange(Animal.class, viewRadius/2, a -> ((Animal)a).isAlive());
            }
            if(search == null) {
                search = (Animal)getClosestInRange(Animal.class, viewRadius, a-> ((Animal)a).isAlive());
            }
            
            if (search != null) {
                target = search; // found a target, assign it as so.
            }
        }
        
        if (target instanceof Animal){ // if target exists
            Animal targetPrey = (Animal) target; // cast as type Anial
            
            if (targetPrey.getWorld() == null) { // check for retargeting required
                target = null; // need new target
                return; // nothing else to do.
            }
            else if(distanceFrom(targetPrey) < 5){ // Close so eat
                targetPrey.decreaseTransparency(1); // ?
                eat(4);
            }
            else { // far so move closer.
                moveTowards(targetPrey, currentSpeed, walkHeight);
            }
        } else {
            moveRandomly(); // no food so i guess move random
        }
    }

    public void animate()
    {

    }
}
