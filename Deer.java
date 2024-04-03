import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * Deer subclass that will eat berries
 * 
 * @author (Osmond Lin) 
 */
public class Deer extends Animal
{
    BushTile targetBush;
    private boolean beingEaten;
    
    public Deer() {
        super();
        beingEaten = false;
        defaultSpeed = 1.0;
        currentSpeed = defaultSpeed;
        sprintSpeed = 1.2 * defaultSpeed;
        waterSpeed = 0.7 * defaultSpeed;
        wantToEat = false;
    }
    /**
     * Act - do whatever the Deer wants to do. This method is called whenever
     * the 'Act' or 'Run' button gets pressed in the environment.
     */
    public void act()
    {
        super.act();
        
        if(alive && !beingEaten){
            if((targetBush == null) || targetBush.getWorld() == null || !(distanceFrom(targetBush) < 5)){
                eating = false;
            }else{
                eating = true;
            }

            if(wantToEat){
                full = false;
                findBerriesAndEat();
            }else{
                targetBush = null;
                full = true;
                move(currentSpeed);
                moveRandomly();
            }
        }
    }
    
    public void findBerriesAndEat() {
        if(targetBush == null || targetBush.getWorld() == null || targetBush.getBerryAmount() <= 150){
            targetBush = (BushTile)getClosestInRange(BushTile.class, 100, g -> ((BushTile)g).getBerryAmount() <= 150);
            if(targetBush == null) {
                targetBush = (BushTile)getClosestInRange(BushTile.class, 180, g -> ((BushTile)g).getBerryAmount() <= 150);
            }
            if(targetBush == null) {
                targetBush = (BushTile)getClosestInRange(BushTile.class, 250, g -> ((BushTile)g).getBerryAmount() <= 150);
            }
        }

        if(targetBush != null) {
            moveTowards(targetBush, currentSpeed);
            if(distanceFrom(targetBush) < 5){
                targetBush.nibble(7);
                eat(7);
            }
        }else{
            move(currentSpeed);
            moveRandomly();
        }
    }
}
