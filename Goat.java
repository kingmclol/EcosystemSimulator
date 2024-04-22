import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * Goat subclass that will eat berries
 * 
 * @author (Osmond Lin) 
 */
public class Goat extends Animal
{   
    //https://www.pinterest.com/pin/387028161720848437/
    // Arrays that store goat animation images
    private GreenfootImage[] animationUp = new GreenfootImage[3];
    private GreenfootImage[] animationDown = new GreenfootImage[3];
    private GreenfootImage[] animationLeft = new GreenfootImage[3];
    private GreenfootImage[] animationRight = new GreenfootImage[3];
    private int indexAnimation = 0;
    private static int numOfGoats = 0;
    
    private GreenfootSound goatSound; 
    
    /**
     * Goat constructor that takes in a parameter
     * 
     * @param  boolean that determines if goat is a baby
     */
    public Goat(boolean isBaby) {
        super(isBaby);
        defaultSpeed = ((double)Greenfoot.getRandomNumber(21)/100.0) + 0.5;
        currentSpeed = defaultSpeed;
        waterSpeed = 0.7 * defaultSpeed;
        wantToEat = false;
        viewRadius = SettingsWorld.getStartViewOfGoat();
        currentViewRadius = viewRadius;
        loweredViewRadius = (int)(0.8 * viewRadius);
        walkHeight = 2;
        breedingThreshold = 2000;
        goatSound = new GreenfootSound("goatsound.mp3");
        goatSound.setVolume(50);
        for(int i = 0; i<3; i++)
        {
            animationUp[i] = new GreenfootImage("images/Goat/Up/Up" + (i+1) + ".png");
            animationDown[i] = new GreenfootImage("images/Goat/Down/Down" + (i+1) + ".png");
            animationRight[i] = new GreenfootImage("images/Goat/Right/Right" + (i+1) + ".png");
            animationLeft[i] = new GreenfootImage("images/Goat/Left/Left" + (i+1) + ".png");
        }
        setImage(animationRight[indexAnimation]);
        numOfGoats++;
    }

    /**
     * Default constructor for goat
     */
    public Goat() {
        super(false);
        defaultSpeed = ((double)Greenfoot.getRandomNumber(21)/100.0) + 0.5;
        currentSpeed = defaultSpeed;
        waterSpeed = 0.7 * defaultSpeed;
        wantToEat = false;
        viewRadius = SettingsWorld.getStartViewOfGoat();
        currentViewRadius = viewRadius;
        loweredViewRadius = (int)(0.8 * viewRadius);
        walkHeight = 2;
        breedingThreshold = 2000;
        goatSound = new GreenfootSound("goatsound.mp3");
        goatSound.setVolume(50);
        for(int i = 0; i < 3; i++)
        {
            animationUp[i] = new GreenfootImage("images/Goat/Up/Up" + (i+1) + ".png");
            animationDown[i] = new GreenfootImage("images/Goat/Down/Down" + (i+1) + ".png");
            animationRight[i] = new GreenfootImage("images/Goat/Right/Right" + (i+1) + ".png");
            animationLeft[i] = new GreenfootImage("images/Goat/Left/Left" + (i+1) + ".png");
        }
        setImage(animationRight[indexAnimation]);
        numOfGoats++;
    }

    public void act()
    {
        super.act();
        if (!alive) return; // There has to be a better way than doing this, right? right?????
        if(actsSinceLastBreeding >= breedingThreshold && alive && !baby){
            ableToBreed = true;
        }else{
            ableToBreed = false;
        }
        if (Greenfoot.getRandomNumber(12000) == 0) {
            goatSound.play();
        }
    }

    /**
     * Method for goats to search for a breeding partner when they are ready
     * If partner exists, they will proceed to breed
     */
    public void breed() {
        // Find another goat nearby
        if (!(target instanceof Goat)) { // Find a goat if the target is null, or not a goat.
            SuperActor search = (Goat) getClosestInRange(this.getClass(), currentViewRadius, g -> !((Goat)g).isAbleToBreed() || !((Goat)g).isAlive()); // Adjust range as needed
            if (search != null){ // found one!
                target = search; // set it as the target.
            }
        }

        if (target instanceof Goat) { // check for null or if target is not a goat.
            Goat targetGoat = (Goat) target; // cast target into goat object

            // check for need for retargeting.
            if (targetGoat.getWorld() == null || !targetGoat.isAlive() || !targetGoat.isAbleToBreed()) {
                target = null;
                return;
            }
            else if(distanceFrom(target) < 40){ // close enough, breed
                breeding = true;
                breedingCounter++;
                if(breedingCounter > BREEDING_DELAY){
                    // Add the baby to the world
                    getWorld().addObject(new Goat(true), getX(), getY());
                    ableToBreed = false;
                    targetGoat.setAbleToBreed(false);
                    breeding = false;
                    targetGoat.setIsBreeding(false);
                    breedingCounter = 0;
                    target = null; // no target anymore.
                    actsSinceLastBreeding = 0;
                }
            }else{ // far, move closer
                moveTowards(target, currentSpeed, walkHeight);
            }
        } else {
            moveRandomly(); // no target goat. move randomly instead :(
        }
    }

    /**
     * Method to animate goats
     */
    public void animate() {
        if(eating)
        {
            if(facing.equals("right"))
            {
                setImage(animationRight[2]);
            }
            else if(facing.equals("left"))
            {
                setImage(animationLeft[2]);
            }
            else if(facing.equals("up"))
            {
                setImage(animationUp[2]);
            }
            else // Down
            {
                setImage(animationDown[2]);
            }
        }
        else
        {
            if(facing.equals("right"))
            {
                setImage(animationRight[indexAnimation]);
            }
            else if(facing.equals("left"))
            {
                setImage(animationLeft[indexAnimation]);
            }
            else if(facing.equals("up"))
            {
                setImage(animationUp[indexAnimation]);
            }
            else // Down
            {
                setImage(animationDown[indexAnimation]);
            }
        }
        if(currentAct%20 == 0) // change animation every 20 acts
        {
            indexAnimation = (indexAnimation + 1)%(animationRight.length);
        }
    }
    
    /**
     * Method for goats to find and eat food
     */
    public void findOrEatFood() {
        if (!(target instanceof BushTile)) { // force the target to be a bush tile
            SuperActor search = (BushTile)getClosestInRange(BushTile.class, currentViewRadius/4, b -> !((BushTile)b).berriesAvailable());
            if(search == null) {
                search = (BushTile)getClosestInRange(BushTile.class, currentViewRadius/2, b -> !((BushTile)b).berriesAvailable());
            }
            if(search == null) {
                search = (BushTile)getClosestInRange(BushTile.class, currentViewRadius, b -> !((BushTile)b).berriesAvailable());
            }

            if (search != null) { // found one!
                target = search; // set the target as the found one.
            }
        }

        if (target instanceof BushTile) { // not null, is bush tile
            BushTile targetBush = (BushTile) target; // Cast into a BushTile.

            // check if retargeting is required.
            if (targetBush.getWorld() == null || !targetBush.berriesAvailable()) {
                eating = false;
                target = null; // need new target.
                return;
            }
            else if(distanceFrom(targetBush) < 10){ // close, so eat.
                eating = true;
                targetBush.nibble(4);
                eat(7);
            }
            else { // far, so move closer.
                eating = false;
                moveTowards(targetBush, currentSpeed, walkHeight);
            }
        }
        else { // nothing gound.
            moveRandomly(); 
        }
    }
    
    /**
     * Getter method for number of goats
     * 
     * @return  the number of goats alive in the world right now
     */
    public static int getNumOfGoats() {
        return numOfGoats;
    }
    
    /**
     * Method sets number of goats
     * 
     * @param num  the new number of goats
     */
    public static void setNumOfGoats(int num){
        numOfGoats = num;
    }
}