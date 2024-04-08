import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * Rabbit subclass
 * Rabbits will eat grass and be eaten by other predators
 * 
 * @author (Osmond Lin)
 */
public class Rabbit extends Animal
{
    private GrassTile targetGrass;
    private boolean beingEaten;

    //Animation

    private int indexAnimation = 0;
    private int currentAct = 0;
    
    private static GreenfootImage[] eatingAnimationUp = new GreenfootImage[4];
    private static GreenfootImage[] eatingAnimationDown = new GreenfootImage[4];
    private static GreenfootImage[] eatingAnimationLeft = new GreenfootImage[4];
    private static GreenfootImage[] eatingAnimationRight = new GreenfootImage[4];

    private static GreenfootImage[] walkingAnimationUp = new GreenfootImage[4];
    private static GreenfootImage[] walkingAnimationDown = new GreenfootImage[4];
    private static GreenfootImage[] walkingAnimationLeft = new GreenfootImage[4];
    private static GreenfootImage[] walkingAnimationRight = new GreenfootImage[4];

    //https://opengameart.org/content/reorganised-lpc-rabbit
    public Rabbit() {
        super();
        facing = "right";
        
        for(int i = 0; i<4; i++)
        {
            //Walking Animation:
            walkingAnimationUp[i] = new GreenfootImage("images/Rabbit Animation/Walking/Up/Up" + (i+1) + ".png");
            walkingAnimationDown[i] = new GreenfootImage("images/Rabbit Animation/Walking/Down/Rabbit_WalkingDown" + (i+1) + ".png");
            walkingAnimationRight[i] = new GreenfootImage("images/Rabbit Animation/Walking/Right/Rabbit_WalkingRight" + (i+1) + ".png");
            walkingAnimationLeft[i] = new GreenfootImage("images/Rabbit Animation/Walking/Left/Rabbit_WalkingLeft" + (i+1) + ".png");
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

        actsSinceLastBreeding++;
        currentAct++;
        if(actsSinceLastBreeding >= BREEDING_THRESHOLD && alive){
            ableToBreed = true;
            breed();
        }else{
            ableToBreed = false;
        }
        
        if(alive && !beingEaten && !breeding && !drinking){
            if((targetGrass == null) || targetGrass.getWorld() == null || !(distanceFrom(targetGrass) < 5)){
                eating = false;
            }
            else{
                eating = true;
            }
            animate();
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

    public void breed() {
        // Find another rabbit nearby
        partner = (Rabbit) getClosestInRange(this.getClass(), 300, r -> !((Rabbit)r).isAbleToBreed() || !((Rabbit)r).isAlive()); // Adjust range as needed
        if(partner != null){
            if(distanceFrom(partner) < 40){
                breeding = true;
                breedingCounter++;
                if(breedingCounter > BREEDING_DELAY){
                    // Add the baby to the world
                    getWorld().addObject(new Rabbit(), getX(), getY());
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

    public void findGrassAndEat() {
        if(targetGrass == null || targetGrass.getWorld() == null || !targetGrass.grassAvailable()){
            targetGrass = (GrassTile)getClosestInRange(GrassTile.class, 100, g -> !((GrassTile)g).grassAvailable());
            if(targetGrass == null) {
                targetGrass = (GrassTile)getClosestInRange(GrassTile.class, 180, g -> !((GrassTile)g).grassAvailable());
            }
            if(targetGrass == null) {
                targetGrass = (GrassTile)getClosestInRange(GrassTile.class, 250, g -> !((GrassTile)g).grassAvailable());
            }
        }

        if(targetGrass != null) {
            moveTowards(targetGrass, currentSpeed);
            if(distanceFrom(targetGrass) < 12){
                targetGrass.nibble(7);
                eat(4);
            }
        }else{
            move(currentSpeed);
            moveRandomly();
        }
    }

    public void takeDamage(int dmg) {
        hp = hp - dmg;
    }
    public static void init()
    {
        for(int i = 0; i<4; i++)
        {
            //eating Animation:
            
            //Walking Animation:
            walkingAnimationUp[i] = new GreenfootImage("images/Rabbit Animation/Walking/Up/Up" + (i+1) + ".png");
            walkingAnimationDown[i] = new GreenfootImage("images/Rabbit Animation/Walking/Down/Rabbit_WalkingDown" + (i+1) + ".png");
            walkingAnimationRight[i] = new GreenfootImage("images/Rabbit Animation/Walking/Right/Rabbit_WalkingRight" + (i+1) + ".png");
            walkingAnimationLeft[i] = new GreenfootImage("images/Rabbit Animation/Walking/Left/Rabbit_WalkingLeft" + (i+1) + ".png");
        }
    }
    public void animate()
    {
        if(eating)
        {
            
        }
        else
        {
            if(facing.equals("right"))
            {
                setImage(walkingAnimationRight[indexAnimation]);
            }
            else if(facing.equals("left"))
            {
                setImage(walkingAnimationLeft[indexAnimation]);
            }
            else if(facing.equals("up"))
            {
                setImage(walkingAnimationUp[indexAnimation]);
            }
            else // Down
            {
                setImage(walkingAnimationDown[indexAnimation]);
            }
        }
        if(currentAct%20 == 0) // change animation every 45 acts
        {
            indexAnimation = (indexAnimation + 1)%(eatingAnimationRight.length);
        }
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
