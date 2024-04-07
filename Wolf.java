import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * Wolf subclass
 * Wolves will prey on rabbits
 * 
 * @author (Osmond Lin) 
 */
public class Wolf extends Animal
{
    private Rabbit targetRabbit;
    private Deer targetDeer;
    //https://i.pinimg.com/originals/20/92/d0/2092d0d2b2b3f7d473adf10353959c1a.jpg
    public Wolf() {
        super();
        defaultSpeed = 1.2;
        currentSpeed = defaultSpeed;
        sprintSpeed = 1.2 * defaultSpeed;
        waterSpeed = 0.7 * defaultSpeed;
        wantToEat = false;
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
            breed();
        }else{
            ableToBreed = false;
        }
        
        if(((targetRabbit == null) || (getWorld() != null && !(distanceFrom(targetRabbit) < 5))) || (targetDeer == null) || (getWorld() != null && !(distanceFrom(targetDeer) < 5))){
            eating = false;
        }else{
            eating = true;
        }
        
        if(alive && !breeding && !drinking){
            if(wantToEat){
                full = false;
                findPreyAndEat();
            }else if(!wantToDrink){
                targetDeer = null;
                targetRabbit = null;
                full = true;
                move(currentSpeed);
                moveRandomly();
            }
        }
    }

    public void breed() {
        // Find another wolf nearby
        partner = (Wolf) getClosestInRange(this.getClass(), 300, w -> !((Wolf)w).isAbleToBreed() || !((Wolf)w).isAlive()); // Adjust range as needed
        if(partner != null){
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
                moveTowards(partner, currentSpeed);
            }
        }else{
            move(currentSpeed);
            moveRandomly();
        }

    }

    public void findPreyAndEat() {
        if(targetRabbit == null || !targetRabbit.isAlive()){
            targetRabbit = (Rabbit)getClosestInRange(Rabbit.class, 100, r -> !((Rabbit)r).isAlive());
            if(targetRabbit == null) {
                targetRabbit = (Rabbit)getClosestInRange(Rabbit.class, 180, r -> !((Rabbit)r).isAlive());
            }
            if(targetRabbit == null) {
                targetRabbit = (Rabbit)getClosestInRange(Rabbit.class, 250, r -> !((Rabbit)r).isAlive());
            }
        }

        if(targetDeer == null || !targetDeer.isAlive()){
            targetDeer = (Deer)getClosestInRange(Deer.class, 100, d -> !((Deer)d).isAlive());
            if(targetDeer == null) {
                targetDeer = (Deer)getClosestInRange(Deer.class, 180, d -> !((Deer)d).isAlive());
            }
            if(targetDeer == null) {
                targetDeer = (Deer)getClosestInRange(Deer.class, 250, d -> !((Deer)d).isAlive());
            }
        }

        if(targetRabbit != null) {
            moveTowards(targetRabbit, currentSpeed);
            if(distanceFrom(targetRabbit) < 5){
                targetRabbit.takeDamage(10);
                targetRabbit.setBeingEaten(true);
                if(targetRabbit.getHp() < 400){
                    targetRabbit.disableStaticRotation();
                    targetRabbit.setRotation(90);
                }
                eat(5);
            }
        }else if(targetDeer != null){
            moveTowards(targetDeer, currentSpeed);
            if(distanceFrom(targetDeer) < 5){
                targetDeer.takeDamage(10);
                targetDeer.setBeingEaten(true);
                if(targetDeer.getHp() < 400){
                    targetDeer.disableStaticRotation();
                    targetDeer.setRotation(90);
                }
                eat(5);
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
