import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * Rabbit subclass
 * Rabbits will eat grass and be eaten by other predators
 * 
 * @author (Osmond Lin)
 */
public class Rabbit extends Animal
{
    GrassTile targetGrass;
    private boolean alive;
    private boolean eating;
    private boolean beingEaten;
    private boolean full;

    public Rabbit() {
        alive = true;
        eating = false;
        beingEaten = false;
        full = true;
        energy = 2000;
        speed = 1.0;
    }

    /**
     * Act - do whatever the Rabbit wants to do. This method is called whenever
     * the 'Act' or 'Run' button gets pressed in the environment.
     */
    public void act()
    {
        if(timeFlowing == false){
            return;
        }
        /*if(alive) {
        move(speed);
        }*/

        energy--;
        if(energy < 1000){
            full = false;
            findGrassAndEat();
        }else if(!eating){
            full = true;
            move(speed);
            moveRandomly();
        }
        if(energy <= 0){
            disableStaticRotation();
            setRotation(90);
        }
    }

    public void findGrassAndEat() {
        if(targetGrass == null || targetGrass.getGrassAmount() <= 150){
            targetGrass = (GrassTile)getClosestInRange(GrassTile.class, 100, g -> ((GrassTile)g).getGrassAmount() < 250);
            if(targetGrass == null) {
                targetGrass = (GrassTile)getClosestInRange(GrassTile.class, 180, g -> ((GrassTile)g).getGrassAmount() < 250);
            }
            if(targetGrass == null) {
                targetGrass = (GrassTile)getClosestInRange(GrassTile.class, 250, g -> ((GrassTile)g).getGrassAmount() < 250);
            }
        }

        if(targetGrass != null) {
            //turnTowards(targetGrass.getX(), targetGrass.getY());
            moveTowards(targetGrass, 1.0);
        }
        if(targetGrass != null){
            if(distanceFrom(targetGrass) < 5){
                eating = true;
                targetGrass.nibble(200);
                eat(250);
            }else{
                eating = false;
            }
        }
    }

    public boolean isAlive() {
        return alive;
    }

    public boolean isEating() {
        return eating;
    }

    public boolean isBeingEaten() {
        return beingEaten;
    }

}
