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

    //Animation
    private GreenfootImage[] eatingAnimationUp = new GreenfootImage[4];
    private GreenfootImage[] eatingAnimationDown = new GreenfootImage[4];
    private GreenfootImage[] eatingAnimationRight = new GreenfootImage[4];
    private GreenfootImage[] eatingAnimationLeft = new GreenfootImage[4];
    
    private GreenfootImage[] walkingAnimationUp = new GreenfootImage[4];
    private GreenfootImage[] walkingAnimationDown = new GreenfootImage[4];
    private GreenfootImage[] walkingAnimationLeft = new GreenfootImage[4];
    private GreenfootImage[] walkingAnimationRight = new GreenfootImage[4];
    //https://opengameart.org/content/reorganised-lpc-rabbit
    public Rabbit() {
        super();
        for(int i = 0; i<4; i++)
        {
            //eating Animation:
            eatingAnimationUp[i] = new GreenfootImage("images/Rabbit Animation/Walking/Up/Rabbit_WalkingUp" + (i+1) + ".png");
            eatingAnimationDown[i] = new GreenfootImage("images/Rabbit Animation/Walking/Up/Rabbit_WalkingUp" + (i+1) + ".png");
            eatingAnimationRight[i] = new GreenfootImage("images/Rabbit Animation/Walking/Up/Rabbit_WalkingUp" + (i+1) + ".png");
            eatingAnimationUp[i] = new GreenfootImage("images/Rabbit Animation/Walking/Up/Rabbit_WalkingUp" + (i+1) + ".png");
            
            //walking Animation:
            walkingAnimationUp[i] = new GreenfootImage("images/Rabbit Animation/Walking/Up/Rabbit_WalkingUp" + (i+1) + ".png");
            walkingAnimationUp[i] = new GreenfootImage("images/Rabbit Animation/Walking/Up/Rabbit_WalkingUp" + (i+1) + ".png");
            walkingAnimationUp[i] = new GreenfootImage("images/Rabbit Animation/Walking/Up/Rabbit_WalkingUp" + (i+1) + ".png");
            walkingAnimationUp[i] = new GreenfootImage("images/Rabbit Animation/Walking/Up/Rabbit_WalkingUp" + (i+1) + ".png");
        }
        beingEaten = false;
        defaultSpeed = 1.0;
        currentSpeed = defaultSpeed;
        sprintSpeed = 1.2 * defaultSpeed;
        waterSpeed = 0.7 * defaultSpeed;
        wantToEat = false;
    }

    /**
     * Act - do whatever the Rabbit wants to do. This method is called whenever
     * the 'Act' or 'Run' button gets pressed in the environment.
     */
    public void act() {
        super.act();
        
        if(alive && !beingEaten){
            if((targetGrass == null) || targetGrass.getWorld() == null || !(distanceFrom(targetGrass) < 5)){
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
                move(currentSpeed);
                moveRandomly();
            }
        }

    }

    public void findGrassAndEat() {
        if(targetGrass == null || targetGrass.getWorld() == null || targetGrass.getGrassAmount() <= 150){
            targetGrass = (GrassTile)getClosestInRange(GrassTile.class, 100, g -> !((GrassTile)g).grassAvailable());
            if(targetGrass == null) {
                targetGrass = (GrassTile)getClosestInRange(GrassTile.class, 180, g -> !((GrassTile)g).grassAvailable());
            }
            if(targetGrass == null) {
                targetGrass = (GrassTile)getClosestInRange(GrassTile.class, 250, g -> !((GrassTile)g).grassAvailable());
            }
        }

        if(targetGrass != null) {
            moveTowards(targetGrass, 1.0);
            if(distanceFrom(targetGrass) < 5){
                targetGrass.nibble(500);
                eat(50);
            }
        }else{
            move(currentSpeed);
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
