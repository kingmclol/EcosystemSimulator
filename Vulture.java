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
        defaultSpeed = ((double)Greenfoot.getRandomNumber(11)/100.0) + 0.7;
        currentSpeed = defaultSpeed;
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

    public void breed() {}

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
