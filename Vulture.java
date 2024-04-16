import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * Vulture subclass
 * Vultures will eat dead animals
 * 
 * @author Osmond Lin 
 * @version (a version number or a date)
 */
public class Vulture extends Animal
{
    private Animal targetAnimal;
    private static int numOfVultures = 0;
    
    public Vulture(boolean isBaby) {
        super(isBaby);
        energy = 3500;
        defaultSpeed = ((double)Greenfoot.getRandomNumber(11)/100.0) + 0.7;
        currentSpeed = defaultSpeed;
        wantToEat = false;
        viewRadius = SettingsWorld.getStartEnergyOfVulture();
        walkHeight = 3;
        breedingThreshold = 3500;
        numOfVultures++;
    }
    
    public Vulture() {
        super(false);
        energy = 3500;
        defaultSpeed = ((double)Greenfoot.getRandomNumber(11)/100.0) + 0.7;
        currentSpeed = defaultSpeed;
        wantToEat = false;
        viewRadius = SettingsWorld.getStartEnergyOfVulture();
        walkHeight = 3;
        breedingThreshold = 3500;
        numOfVultures++;
    }

    /**
     * Act - do whatever the Vulture wants to do. This method is called whenever
     * the 'Act' or 'Run' button gets pressed in the environment.
     */
    public void act()
    {
        super.act();
        if (!alive) return; // dead.
        
        if(actsSinceLastBreeding >= breedingThreshold && alive && !baby){
            ableToBreed = true;
        }else{
            ableToBreed = false;
        }
    }

    public void breed() {
        // Find another vulture nearby
        if (!(target instanceof Vulture)) { // attempt to find a Vulture eligble
            SuperActor search = (Vulture) getClosestInRange(this.getClass(), viewRadius, v -> !((Vulture)v).isAbleToBreed() || !((Vulture)v).isAlive()); // Adjust range as needed
            
            if (search != null) { // found a Vulture!
                target = search;
            }
        }
        
        if (target instanceof Vulture) { // Vulture found
            Vulture targetVulture = (Vulture) target; // cast to target
            
            // Check if retargeting needed.
            if (targetVulture.getWorld() == null || !targetVulture.isAlive() || !targetVulture.isAbleToBreed()) {
                target = null; // neccessiate retargeting
                return; // nothign else t do
            }
            else if(distanceFrom(target) < 40){ // close to target Vulture! breed
                breeding = true;
                breedingCounter++;
                if(breedingCounter > BREEDING_DELAY){
                    // Add the baby to the world
                    getWorld().addObject(new Vulture(true), getX(), getY());
                    ableToBreed = false;
                    targetVulture.setAbleToBreed(false);
                    breeding = false;
                    targetVulture.setIsBreeding(false);
                    breedingCounter = 0;
                    target = null;
                    actsSinceLastBreeding = 0;
                }
            }else{ // move closer
                moveTowards(targetVulture, currentSpeed, walkHeight);
            }
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
                eating = false;
                target = null; // need new target
                return; // nothing else to do.
            }
            else if(distanceFrom(targetPrey) < 5){ // Close so eat
                eating = true;
                targetPrey.decreaseTransparency(1); // ?
                eat(4);
            }
            else { // far so move closer.
                eating = false;
                moveTowards(targetPrey, currentSpeed, walkHeight);
            }
        } else {
            moveRandomly(); // no food so i guess move random
        }
    }

    public void animate()
    {

    }
    
    public static int getNumOfVultures() {
        return numOfVultures;
    }
    
    public static void decreaseNumOfVultures(){
        numOfVultures = numOfVultures - 1;
    }
}
