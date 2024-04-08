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
        
        if(alive && !drinking){
            if(wantToEat){
                full = false;
                findDeadAnimalsAndEat();
            }else{
                targetAnimal = null;
                full = true;
                
            }
        }
    }

    public void breed() {}

    public void findDeadAnimalsAndEat() {
        if(targetAnimal == null || targetAnimal.isAlive() || targetAnimal.getWorld() == null){
            targetAnimal = (Animal)getClosestInRange(Animal.class, 100, a -> ((Animal)a).isAlive());
            if(targetAnimal == null) {
                targetAnimal = (Animal)getClosestInRange(Animal.class, 180, a -> ((Animal)a).isAlive());
            }
            if(targetAnimal == null) {
                targetAnimal = (Animal)getClosestInRange(Animal.class, 250, a-> ((Animal)a).isAlive());
            }
        }

        if(targetAnimal != null) {
            moveTowards(targetAnimal, currentSpeed);
            if(distanceFrom(targetAnimal) < 5){
                targetAnimal.decreaseTransparency(1);
                eat(4);
            }
        }else{
            move(currentSpeed);
            moveRandomly();
        }
    }

    public void animate()
    {

    }
}
