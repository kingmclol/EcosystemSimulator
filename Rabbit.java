import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * Rabbit subclass
 * Rabbits will eat grass and be eaten by other predators
 * 
 * @author (Osmond Lin)
 */
public class Rabbit extends Animal
{
    //Instance Variables:    

    //Animation
    private int indexAnimation = 0;
    private boolean spawnOne = true;
    
    //Arrays that store rabbit animation images
    private GreenfootImage[] eatingAnimationUp = new GreenfootImage[4];
    private GreenfootImage[] eatingAnimationDown = new GreenfootImage[4];
    private GreenfootImage[] eatingAnimationLeft = new GreenfootImage[4];
    private GreenfootImage[] eatingAnimationRight = new GreenfootImage[4];

    private GreenfootImage[] walkingAnimationUp = new GreenfootImage[4];
    private GreenfootImage[] walkingAnimationDown = new GreenfootImage[4];
    private GreenfootImage[] walkingAnimationLeft = new GreenfootImage[4];
    private GreenfootImage[] walkingAnimationRight = new GreenfootImage[4];
    //https://opengameart.org/content/reorganised-lpc-rabbit
    private static int numOfRabbits = 0;
    
    /**
     * Constructor for rabbit that takes in a parameter
     * 
     * @param  boolean that determines if rabbit is a baby
     */
    public Rabbit(boolean isBaby) {
        super(isBaby);
        facing = "right";
        walkHeight = 1;
        for(int i = 0; i<4; i++)
        {
            //Walking Animation:
            walkingAnimationUp[i] = new GreenfootImage("images/Rabbit/Walking/Up/Up" + (i+1) + ".png");
            walkingAnimationDown[i] = new GreenfootImage("images/Rabbit/Walking/Down/Rabbit_WalkingDown" + (i+1) + ".png");
            walkingAnimationRight[i] = new GreenfootImage("images/Rabbit/Walking/Right/Rabbit_WalkingRight" + (i+1) + ".png");
            walkingAnimationLeft[i] = new GreenfootImage("images/Rabbit/Walking/Left/Rabbit_WalkingLeft" + (i+1) + ".png");

            eatingAnimationUp[i] = new GreenfootImage("images/Rabbit/Eating/Up/Eating" + (i+1) + ".png");
            eatingAnimationDown[i] = new GreenfootImage("images/Rabbit/Eating/Up/Eating" + (i+1) + ".png");
            eatingAnimationRight[i] = new GreenfootImage("images/Rabbit/Eating/Up/Eating" + (i+1) + ".png");
            eatingAnimationLeft[i] = new GreenfootImage("images/Rabbit/Eating/Up/Eating" + (i+1) + ".png");
        }
        defaultSpeed = ((double)Greenfoot.getRandomNumber(21)/100.0) + 0.5;
        currentSpeed = defaultSpeed;
        waterSpeed = 0.7 * defaultSpeed;
        wantToEat = false;
        viewRadius = SettingsWorld.getStartEnergyOfRabbit();
        breedingThreshold = 2000;
        numOfRabbits++;
    }
    
    /**
     * Default constructor for rabbit
     */
    public Rabbit() {
        super(false);
        facing = "right";
        walkHeight = 1;
        for(int i = 0; i<4; i++)
        {
            //Walking Animation:
            walkingAnimationUp[i] = new GreenfootImage("images/Rabbit/Walking/Up/Up" + (i+1) + ".png");
            walkingAnimationDown[i] = new GreenfootImage("images/Rabbit/Walking/Down/Rabbit_WalkingDown" + (i+1) + ".png");
            walkingAnimationRight[i] = new GreenfootImage("images/Rabbit/Walking/Right/Rabbit_WalkingRight" + (i+1) + ".png");
            walkingAnimationLeft[i] = new GreenfootImage("images/Rabbit/Walking/Left/Rabbit_WalkingLeft" + (i+1) + ".png");

            eatingAnimationUp[i] = new GreenfootImage("images/Rabbit/Eating/Up/Eating" + (i+1) + ".png");
            eatingAnimationDown[i] = new GreenfootImage("images/Rabbit/Eating/Up/Eating" + (i+1) + ".png");
            eatingAnimationRight[i] = new GreenfootImage("images/Rabbit/Eating/Up/Eating" + (i+1) + ".png");
            eatingAnimationLeft[i] = new GreenfootImage("images/Rabbit/Eating/Up/Eating" + (i+1) + ".png");
        }
        defaultSpeed = ((double)Greenfoot.getRandomNumber(21)/100.0) + 0.5;
        currentSpeed = defaultSpeed;
        waterSpeed = 0.7 * defaultSpeed;
        wantToEat = false;
        viewRadius = SettingsWorld.getStartEnergyOfRabbit();
        breedingThreshold = 2000;
        numOfRabbits++;
    }

    public void act() {
        super.act();
        
        if (!alive) return; // it is dead. dead. as in, not alive.
        
        if(actsSinceLastBreeding >= breedingThreshold && alive && !baby){
            ableToBreed = true;
        }else{
            ableToBreed = false;
        }
    }

    /**
     * Method for rabbits to search for a breeding partner when they are ready
     * If partner exists, they will proceed to breed
     */
    public void breed() {
        // Find another rabbit nearby
        if (!(target instanceof Rabbit)) { // if target is null, or the target is currently not a rabbit.
            // find a rabbit as the target.
            SuperActor search = (Rabbit) getClosestInRange(this.getClass(), viewRadius, r -> !((Rabbit)r).isAbleToBreed() || !((Rabbit)r).isAlive()); // Adjust range as needed
            if (search != null){ // found one.
                target = search;
            }
        }
        
        if (target instanceof Rabbit) { // if target is not null, and is a rabbit,
            Rabbit targetRabbit = (Rabbit) target; // safely cast into a rabbit to access its instance methods
            
            // check if retarget is needed.
            if (!targetRabbit.isAbleToBreed() || targetRabbit.getWorld() == null || !targetRabbit.isAlive()) { 
                target = null; // NEED NEW TARGET.
                return; // nothing else to do.
            }
            
            // Getting here means the target is applicable.
            if(distanceFrom(target) < 40){
                breeding = true;
                breedingCounter++;
                if(breedingCounter > BREEDING_DELAY){
                    // Add the baby to the world
                    getWorld().addObject(new Rabbit(true), getX(), getY());
                    ableToBreed = false;
                    targetRabbit.setAbleToBreed(false);
                    breeding = false;
                    targetRabbit.setIsBreeding(false);
                    breedingCounter = 0;
                    target = null;
                    actsSinceLastBreeding = 0;
                    if(facing.equals("right"))
                    {
                        getWorld().addObject(new BreedingEffect(2), getX()+this.getImage().getWidth(), getY());  
                    }
                    else if(facing.equals("left"))
                    {
                        getWorld().addObject(new BreedingEffect(2), getX()-this.getImage().getWidth(), getY());
                    }
                    else if(facing.equals("up"))
                    {
                        getWorld().addObject(new BreedingEffect(2), getX(), getY()-this.getImage().getHeight());
                    }
                    else
                    {
                        getWorld().addObject(new BreedingEffect(2), getX(), getY()+this.getImage().getHeight());
                    }
                }
            }else{
                moveTowards(target, currentSpeed, walkHeight);
            }
        } else { // no elible partner so move randomly
            moveRandomly(); // Rip. no partner nearby. better luck next time.
        }
    }

    /**
     * Method for rabbits to find and eat food
     */
    public void findOrEatFood() {
        if(!(target instanceof GrassTile)) { // if target is null, or not a grasstile (forcing target to be grasstile only)
            //find a target
            SuperActor search = (GrassTile)getClosestInRange(GrassTile.class, viewRadius/4, g -> !((GrassTile)g).grassAvailable());
            if(search == null) {
                search = (GrassTile)getClosestInRange(GrassTile.class, viewRadius/2, g -> !((GrassTile)g).grassAvailable());
            }
            if(search == null) {
                search = (GrassTile)getClosestInRange(GrassTile.class, viewRadius, g -> !((GrassTile)g).grassAvailable());
            }
            
            if (search != null) { // found a grass tiel.
                //System.out.println("Grass found");
                target = search;
            }
        }
        
        
        if (target instanceof GrassTile) { // target is a grass tile, and exists (successfully found eligble food)
            GrassTile targetGrass = (GrassTile) target; // Create temp variable to access GrassTile instance methods
            // Check if a retargeting is required.
            if (!targetGrass.grassAvailable() || targetGrass.getWorld() == null) {
                eating = false;
                target = null;
                return;
            }
            else if(distanceFrom(target) < 10){ // if close enough.
                eating = true;
                targetGrass.nibble(7);
                eat(4);
            }
            else{ // far, move closer.
                eating = false;
                moveTowards(targetGrass, currentSpeed, walkHeight);
            }

        } else { // No grass tile found...
            //System.out.println("move random in eat method");
            moveRandomly(); // Should move randomly for this act instead...
        }
    }

    public void animate()
    {
        if(eating)
        {
            if(facing.equals("right"))
            {
                setImage(eatingAnimationRight[indexAnimation]);
            }
            else if(facing.equals("left"))
            {
                setImage(eatingAnimationLeft[indexAnimation]);
            }
            else if(facing.equals("up"))
            {
                setImage(eatingAnimationUp[indexAnimation]);
            }
            else // Down
            {
                setImage(eatingAnimationDown[indexAnimation]);
            }
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
        if(currentAct%10 == 0) // change animation every 20 acts
        {
            indexAnimation = (indexAnimation + 1)%(eatingAnimationRight.length);
        }
    }
    
    public static int getNumOfRabbits() {
        return numOfRabbits;
    }
    
    /**
     * Method sets number of rabbits
     * 
     * @param num  the new number of rabbits
     */
    public static void setNumOfRabbits(int num){
        numOfRabbits = num;
    }
    

}
