import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)
import java.util.function.Predicate;
/**
 * Wolf subclass
 * Wolves will prey on rabbits
 * 
 * @author (Osmond Lin) 
 */
public class Wolf extends Animal
{
    //https://i.pinimg.com/originals/20/92/d0/2092d0d2b2b3f7d473adf10353959c1a.jpg
    
    public Wolf(boolean isBaby) {
        super(isBaby);
        defaultSpeed = ((double)Greenfoot.getRandomNumber(11)/100.0) + 0.7;
        currentSpeed = defaultSpeed;
        waterSpeed = 0.7 * defaultSpeed;
        wantToEat = false;
        viewRadius = 500;
        walkHeight = 1;
        breedingThreshold = 2000;
    }
    
    public Wolf() {
        super(false);
        defaultSpeed = ((double)Greenfoot.getRandomNumber(11)/100.0) + 0.7;
        currentSpeed = defaultSpeed;
        waterSpeed = 0.7 * defaultSpeed;
        wantToEat = false;
        viewRadius = 500;
        walkHeight = 1;
        breedingThreshold = 2000;
    }

    /**
     * Act - do whatever the Wolf wants to do. This method is called whenever
     * the 'Act' or 'Run' button gets pressed in the environment.
     */
    public void act() {
        super.act();
        if (!alive) return;  // am dead. nothing to do.

        if(actsSinceLastBreeding >= breedingThreshold && alive && !baby){
            ableToBreed = true;
        }else{
            ableToBreed = false;
        }
         // I guess eating is managed here. dunno how it works so im not touching it.
        if(target == null || target.getWorld() == null || distanceFrom(target) > 5){
            eating = false;
        }else{
            eating = true;
        }
        
    }

    public void breed() {
        // Find another wolf nearby
        if (!(target instanceof Wolf)) { // attempt to find a Wolf eligble
            SuperActor search = (Wolf) getClosestInRange(this.getClass(), viewRadius, w -> !((Wolf)w).isAbleToBreed() || !((Wolf)w).isAlive()); // Adjust range as needed
            
            if (search != null) { // found a wolf!
                target = search;
            }
        }
        
        if (target instanceof Wolf) { // wolf found
            Wolf targetWolf = (Wolf) target; // cast to target
            
            // Check if retargeting needed.
            if (targetWolf.getWorld() == null || !targetWolf.isAlive() || !targetWolf.isAbleToBreed()) {
                target = null; // neccessiate retargeting
                return; // nothign else t do
            }
            else if(distanceFrom(target) < 40){ // close to target wolf! breed
                breeding = true;
                breedingCounter++;
                if(breedingCounter > BREEDING_DELAY){
                    // Add the baby to the world
                    getWorld().addObject(new Wolf(true), getX(), getY());
                    ableToBreed = false;
                    targetWolf.setAbleToBreed(false);
                    breeding = false;
                    targetWolf.setIsBreeding(false);
                    breedingCounter = 0;
                    target = null;
                    actsSinceLastBreeding = 0;
                }
            }else{ // move closer
                moveTowards(targetWolf, currentSpeed, walkHeight);
            }
        }
    }

    public void findOrEatFood() {
        // Attempt to find some prey, so target would be of corret type
        if (!(target instanceof Animal)) {
            // Create an more advanced filter to find an eligble food (animal)
            // Read as: Remove if animal is alive, or the animal is a wolf, or animal is a vulture.
            Predicate<Animal> filter = a -> (!a.isAlive() || a instanceof Wolf || a instanceof Vulture);
            SuperActor search = (Animal) getClosestInRange(Animal.class, viewRadius/4, filter);
            if (search == null){
                search = (Animal) getClosestInRange(Animal.class, viewRadius/2, filter);
            }
            if (search == null) {
                search = (Animal) getClosestInRange(Animal.class, viewRadius, filter);
            }
            
            if (search != null) {
                target = search; // found one.
            }
        }
        

        if (target instanceof Animal) {
            Animal targetPrey = (Animal) target; // cast into ANimal to access instance methods.
            
            // check if retarget required.
            if (!targetPrey.isAlive() || targetPrey.getWorld() == null) {
                target = null;
                return;
            }
            else if (distanceFrom(targetPrey) < 5) { // close enough, eat it
                targetPrey.takeDamage(10);
                if (targetPrey.getEnergy() <= 0) {
                    targetPrey.disableStaticRotation();
                    targetPrey.setRotation(90);
                }
                eat(12);
            }
            else { // too far, move closer.
                moveTowards(targetPrey, currentSpeed, walkHeight);
            }
        }
        else {
            moveRandomly(); // move randomly this act.
        }
    }
    public void animate()
    {
        // no animation rn,
    }
}
