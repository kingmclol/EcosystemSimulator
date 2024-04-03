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
    Rabbit targetRabbit;
    Wolf targetWolf;
    
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
        if(alive){
            if(((targetRabbit == null) || targetRabbit.getWorld() == null || !(distanceFrom(targetRabbit) < 5))||((targetWolf == null) || targetRabbit.getWorld() == null || !(distanceFrom(targetWolf) < 5))){
                eating = false;
            }else{
                eating = true;
            }
            
            if(wantToEat){
                full = false;
                findDeadAnimalsAndEat();
            }else{
                targetRabbit = null;
                targetWolf = null;
                full = true;
                move(currentSpeed);
                moveRandomly();
            }
        }
    }
    
    public void findDeadAnimalsAndEat() {
        if(targetRabbit == null || targetRabbit.isAlive() || targetRabbit.getWorld() == null){
            targetRabbit = (Rabbit)getClosestInRange(Rabbit.class, 100, r -> ((Rabbit)r).isAlive() && ((Rabbit)r).getWorld() == null);
            if(targetRabbit == null) {
                targetRabbit = (Rabbit)getClosestInRange(Rabbit.class, 180, r -> ((Rabbit)r).isAlive() && ((Rabbit)r).getWorld() == null);
            }
            if(targetRabbit == null) {
                targetRabbit = (Rabbit)getClosestInRange(Rabbit.class, 250, r-> ((Rabbit)r).isAlive() && ((Rabbit)r).getWorld() == null);
            }
        }
        
        if(targetWolf == null || targetWolf.isAlive() || targetWolf.getWorld() == null){
            targetWolf = (Wolf)getClosestInRange(Wolf.class, 100, w -> ((Wolf)w).isAlive() && ((Wolf)w).getWorld() == null);
            if(targetWolf == null) {
                targetWolf = (Wolf)getClosestInRange(Wolf.class, 180, w -> ((Wolf)w).isAlive() && ((Wolf)w).getWorld() == null);
            }
            if(targetWolf == null) {
                targetWolf = (Wolf)getClosestInRange(Wolf.class, 250, w-> ((Wolf)w).isAlive() && ((Wolf)w).getWorld() == null);
            }
        }
        
        if(targetRabbit != null) {
            moveTowards(targetRabbit, 1.3);
            if(distanceFrom(targetRabbit) < 5){
                targetRabbit.decreaseTransparency(1);
                eat(4);
            }
        }else if(targetWolf != null){
            moveTowards(targetWolf, 1.3);
            if(distanceFrom(targetWolf) < 5){
                targetWolf.decreaseTransparency(1);
                eat(4);
            }
        }else{
            move(currentSpeed);
            moveRandomly();
        }
    }
}
