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
    private boolean beingEaten;

    public Rabbit() {
        super();
        beingEaten = false;
        speed = 1.0;
        wantToEat = false;
    }

    /**
     * Act - do whatever the Rabbit wants to do. This method is called whenever
     * the 'Act' or 'Run' button gets pressed in the environment.
     */
    public void act() {
        super.act();
        if(timeFlowing == false){
            return;
        }

        if(alive && !beingEaten){
            if((targetGrass == null) || !(distanceFrom(targetGrass) < 5)){
                eating = false;
            }else{
                eating = true;
            }

            if(wantToEat){
                full = false;
                findGrassAndEat();
            }else{
                targetGrass = null;
                full = true;
                move(speed);
                moveRandomly();
            }
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
            moveTowards(targetGrass, 1.0);
            if(distanceFrom(targetGrass) < 5){
                targetGrass.nibble(4);
                eat(4);
            }
        }else{
            move(speed);
            moveRandomly();
        }
    }

    public void takeDamage(int dmg) {
        hp = hp - dmg;
    }

    public int getHp() {
        return hp;
    }

    public boolean isBeingEaten() {
        return beingEaten;
    }
    
    public void setBeingEaten(boolean eaten) {
        beingEaten = eaten;
    }

}
