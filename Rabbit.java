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
        for(int i = 0; i<4; i++)
        {
            //eating Animation:
            /*
            eatingAnimationUp[i] = new GreenfootImage("images/Rabbit Animation/Walking/Up/Rabbit_WalkingUp" + (i+1) + ".png");
            eatingAnimationDown[i] = new GreenfootImage("images/Rabbit Animation/Walking/Up/Rabbit_WalkingUp" + (i+1) + ".png");
            eatingAnimationRight[i] = new GreenfootImage("images/Rabbit Animation/Walking/Up/Rabbit_WalkingUp" + (i+1) + ".png");
            eatingAnimationUp[i] = new GreenfootImage("images/Rabbit Animation/Walking/Up/Rabbit_WalkingUp" + (i+1) + ".png");
             */
            //Walking Animation:
            walkingAnimationUp[i] = new GreenfootImage("images/Rabbit Animation/Walking/Up/Rabbit_WalkingUp" + (i+1) + ".png");
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
        
         if(actsSinceLastBreeding >= BREEDING_THRESHOLD){
            ableToBreed = true;
            breed();
        }else{
            ableToBreed = false;
        }
        
        if(alive && !beingEaten && !breeding){
            if((targetGrass == null) || targetGrass.getWorld() == null || !(distanceFrom(targetGrass) < 5)){
                eating = false;
            }else{
                eating = true;
            }
            animate();
            if(wantToEat){
                full = false;
                findGrassAndEat();
            }else if(!drinking){
                targetGrass = null;
                full = true;
                move(currentSpeed);
                moveRandomly();
            }
        }

    }

    public void breed() {
        // Find another rabbit nearby
        partner = (Rabbit) getClosestInRange(this.getClass(), 300, r -> !((Rabbit)r).isAbleToBreed() && ((Rabbit)r).isAlive()); // Adjust range as needed
        if(partner != null && !isTouching(Rabbit.class)){
            moveTowards(partner, currentSpeed);
        }

        if (partner != null && partner.isAlive() && partner.isAbleToBreed() && isTouching(Rabbit.class)) {
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

    public void animate()
    {
        if(eating)
        {
            /*
            if(facing.equals("right"))
            {
            setImage(eatingAnimationRight[indexAnimation]);
            indexAnimation = (indexAnimation+1)%(eatingAnimationRight.length);
            }
            else if(facing.equals("left"))
            {
            setImage(eatingAnimationLeft[indexAnimation]);
            indexAnimation = (indexAnimation+1)%(eatingAnimationRight.length);
            }
            else if(facing.equals("up"))
            {
            setImage(eatingAnimationUp[indexAnimation]);
            indexAnimation = (indexAnimation+1)%(eatingAnimationRight.length);
            }
            else
            {
            setImage(eatingAnimationDown[indexAnimation]);
            indexAnimation = (indexAnimation+1)%(eatingAnimationRight.length);
            }
             */
        }
        else
        {
            if(facing.equals("right"))
            {
                setImage(walkingAnimationRight[indexAnimation]);
                indexAnimation = (indexAnimation + 1)%(eatingAnimationRight.length);
            }
            else if(facing.equals("left"))
            {
                setImage(walkingAnimationLeft[indexAnimation]);
                indexAnimation = (indexAnimation + 1)%(eatingAnimationRight.length);
            }
            else if(facing.equals("up"))
            {
                setImage(walkingAnimationUp[indexAnimation]);
                indexAnimation = (indexAnimation + 1)%(eatingAnimationRight.length);
            }
            else // Down
            {
                setImage(walkingAnimationDown[indexAnimation]);
                indexAnimation = (indexAnimation + 1)%(eatingAnimationRight.length);
            }
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
