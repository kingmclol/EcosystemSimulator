import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * Wolf subclass
 * Wolves will prey on rabbits
 * 
 * @author (Osmond Lin) 
 */
public class Wolf extends Animal
{
    Rabbit targetRabbit;

    public Wolf() {
        super();
        speed = 1.2;
        wantToEat = false;
    }

    /**
     * Act - do whatever the Wolf wants to do. This method is called whenever
     * the 'Act' or 'Run' button gets pressed in the environment.
     */
    public void act() {
        super.act();
        if(timeFlowing == false){
            return;
        }

        if(alive){
            if((targetRabbit == null) || !(distanceFrom(targetRabbit) < 5)){
                eating = false;
            }else{
                eating = true;
            }

            if(wantToEat){
                full = false;
                findPreyAndEat();
            }else{
                targetRabbit = null;
                full = true;
                move(speed);
                moveRandomly();
            }
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

        if(targetRabbit != null) {
            moveTowards(targetRabbit, 1.2);
            if(distanceFrom(targetRabbit) < 5){
                targetRabbit.takeDamage(10);
                targetRabbit.setBeingEaten(true);
                if(targetRabbit.getHp() < 400){
                    targetRabbit.disableStaticRotation();
                    targetRabbit.setRotation(90);
                }
                eat(5);
            }
        }else{
            move(speed);
            moveRandomly();
        }
    }
}
