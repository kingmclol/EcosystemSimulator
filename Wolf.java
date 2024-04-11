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
    private Animal target;
    //https://i.pinimg.com/originals/20/92/d0/2092d0d2b2b3f7d473adf10353959c1a.jpg
    public Wolf() {
        super();
        defaultSpeed = ((double)Greenfoot.getRandomNumber(11)/100.0) + 0.7;
        currentSpeed = defaultSpeed;
        waterSpeed = 0.7 * defaultSpeed;
        wantToEat = false;
        viewRadius = 500;
        walkHeight = 1;
    }

    /**
     * Act - do whatever the Wolf wants to do. This method is called whenever
     * the 'Act' or 'Run' button gets pressed in the environment.
     */
    public void act() {
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
        
        if(target == null || target.getWorld() == null || distanceFrom(target) > 5){
            eating = false;
        }else{
            eating = true;
        }
        
        if(alive && !breeding){
            if(wantToEat){
                findPreyAndEat();
            }else{
                target = null;
            }
        }
    }

    public void breed() {
        // Find another wolf nearby
        partner = (Wolf) getClosestInRange(this.getClass(), viewRadius, w -> !((Wolf)w).isAbleToBreed() || !((Wolf)w).isAlive()); // Adjust range as needed
        if(partner != null){
            findingPartner = true;
            if(distanceFrom(partner) < 40){
                breeding = true;
                breedingCounter++;
                if(breedingCounter > BREEDING_DELAY){
                    // Add the baby to the world
                    getWorld().addObject(new Wolf(), getX(), getY());
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

    public void findPreyAndEat() {
        Predicate<Animal> filter = a -> (!a.isAlive() || a instanceof Wolf || a instanceof Vulture);
        if (target == null || target.getWorld() == null || !target.isAlive()) {
            target = (Animal) getClosestInRange(Animal.class, viewRadius/4, filter);
            if (target == null){
                target = (Animal) getClosestInRange(Animal.class, viewRadius/2, filter);
            }
            if (target == null) {
                target = (Animal) getClosestInRange(Animal.class, viewRadius, filter);
            }
        }
        
        if (target != null) {
            if (distanceFrom(target) < 5) {
                target.takeDamage(8);
                if (target.getEnergy() <= 0) {
                    target.disableStaticRotation();
                    target.setRotation(90);
                }
                eat(12);
            }
            else {
                moveTowards(target, currentSpeed, walkHeight);
            }
        }
    }

    public void animate()
    {

    }
}
