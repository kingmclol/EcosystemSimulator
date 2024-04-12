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
        
        if(((targetAnimal == null) || targetAnimal.getWorld() == null || (getWorld() != null && !(distanceFrom(targetAnimal) < 5)))){
            eating = false;
        }else{
            eating = true;
        }
        
        if(alive){
            if(wantToEat){
                findDeadAnimalsAndEat();
            }else{
                targetAnimal = null;
            }
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

    public void findDeadAnimalsAndEat() {
        if(targetAnimal == null || targetAnimal.isAlive() || targetAnimal.getWorld() == null){
            targetAnimal = (Animal)getClosestInRange(Animal.class, viewRadius/4, a -> ((Animal)a).isAlive());
            if(targetAnimal == null) {
                targetAnimal = (Animal)getClosestInRange(Animal.class, viewRadius/2, a -> ((Animal)a).isAlive());
            }
            if(targetAnimal == null) {
                targetAnimal = (Animal)getClosestInRange(Animal.class, viewRadius, a-> ((Animal)a).isAlive());
            }
        }

        if(targetAnimal != null) {
            if(distanceFrom(targetAnimal) < 5){
                targetAnimal.decreaseTransparency(1);
                eat(4);
            }
            else {
                moveTowards(targetAnimal, currentSpeed, walkHeight);
            }
        }
    }

    public void animate()
    {

    }
}
