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

    public Vulture() {
        super();
        defaultSpeed = 1.3;
        currentSpeed = defaultSpeed;
        sprintSpeed = 1.2 * defaultSpeed;
        waterSpeed = 0.7 * defaultSpeed;
        wantToEat = false;
        viewRadius = 400;
        walkHeight = 3;
    }

    /**
     * Act - do whatever the Vulture wants to do. This method is called whenever
     * the 'Act' or 'Run' button gets pressed in the environment.
     */
    public void act()
    {
        super.act();
        if (!alive) return; // dead.
        
        if(((targetAnimal == null) || targetAnimal.getWorld() == null || (getWorld() != null && !(distanceFrom(targetAnimal) < 5)))){
            eating = false;
        }else{
            eating = true;
        }
    }

    public void breed() {} // vultures cannot breed.

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
            else if(distanceFrom(targetAnimal) < 5){ // Close so eat
                targetAnimal.decreaseTransparency(1); // ?
                eat(4);
            }
            else { // far so move closer.
                moveTowards(targetAnimal, currentSpeed, walkHeight);
            }
        } else {
            moveRandomly(); // no food so i guess move random
        }
    }

    public void animate()
    {

    }
}
