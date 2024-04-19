import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * Vulture subclass
 * Vultures will eat dead animals
 * 
 * @author Osmond Lin 
 * @version (a version number or a date)
 */
public class Vulture extends Animal
{
    private Animal targetAnimal;
    private static int numOfVultures = 0;
    private double huntSpeed;
    //Arrays that store vulture's animation images:
    private int indexAnimation = 0;
    private GreenfootImage[] eatingAnimationUp = new GreenfootImage[3];
    private GreenfootImage[] eatingAnimationDown = new GreenfootImage[3];
    private GreenfootImage[] eatingAnimationLeft = new GreenfootImage[3];
    private GreenfootImage[] eatingAnimationRight = new GreenfootImage[3];

    private GreenfootImage[] walkingAnimationUp = new GreenfootImage[3];
    private GreenfootImage[] walkingAnimationDown = new GreenfootImage[3];
    private GreenfootImage[] walkingAnimationLeft = new GreenfootImage[3];
    private GreenfootImage[] walkingAnimationRight = new GreenfootImage[3];
    //https://www.deviantart.com/lostchild14000/art/Animal-Sprite-Sheet-654707851
    
<<<<<<< HEAD
    /**
     * Vulture constructor that takes in a parameter
     * 
     * @param isBaby  boolean that determines if vulture is a baby
     */
=======
    private GreenfootSound vultureSound; 
>>>>>>> c194f10d8a6c03110801fdbdb34897a96b1511be
    public Vulture(boolean isBaby) {
        super(isBaby);
        energy = 3500;
        defaultSpeed = ((double)Greenfoot.getRandomNumber(21)/100.0) + 0.6;
        currentSpeed = defaultSpeed;
        huntSpeed = 1.2 * defaultSpeed;
        wantToEat = false;
        viewRadius = SettingsWorld.getStartEnergyOfVulture();
        walkHeight = 3;
        breedingThreshold = 3500;
        numOfVultures++;
        
        vultureSound = new GreenfootSound("vulture.mp3"); 
        for(int i = 0; i<3; i++)
        {
           walkingAnimationUp[i] = new GreenfootImage("images/Vulture/Walking/Up/Up" + (i+1) + ".png");
           walkingAnimationDown[i] = new GreenfootImage("images/Vulture/Walking/Down/Down" + (i+1) + ".png");
           walkingAnimationLeft[i] = new GreenfootImage("images/Vulture/Walking/Left/Left" + (i+1) + ".png");
           walkingAnimationRight[i] = new GreenfootImage("images/Vulture/Walking/Right/Right" + (i+1) + ".png");
           
           eatingAnimationUp[i] = new GreenfootImage("images/Vulture/Eating/Up/Up" + (i+1) + ".png");
           eatingAnimationDown[i] = new GreenfootImage("images/Vulture/Eating/Down/Down" + (i+1) + ".png");
           eatingAnimationLeft[i] = new GreenfootImage("images/Vulture/Eating/Left/Left" + (i+1) + ".png");
           eatingAnimationRight[i] = new GreenfootImage("images/Vulture/Eating/Right/Right" + (i+1) + ".png");
        }
    }
    
    /**
     * Default vulture constructor
     */
    public Vulture() {
        super(false);
        energy = 3500;
        defaultSpeed = ((double)Greenfoot.getRandomNumber(21)/100.0) + 0.6;
        currentSpeed = defaultSpeed;
        huntSpeed = 1.2 * defaultSpeed;
        wantToEat = false;
        viewRadius = SettingsWorld.getStartEnergyOfVulture();
        walkHeight = 3;
        breedingThreshold = 3500;
        numOfVultures++;
        
        vultureSound = new GreenfootSound("vulture.mp3"); 
        for(int i = 0; i<3; i++)
        {
           walkingAnimationUp[i] = new GreenfootImage("images/Vulture/Walking/Up/Up" + (i+1) + ".png");
           walkingAnimationDown[i] = new GreenfootImage("images/Vulture/Walking/Down/Down" + (i+1) + ".png");
           walkingAnimationLeft[i] = new GreenfootImage("images/Vulture/Walking/Left/Left" + (i+1) + ".png");
           walkingAnimationRight[i] = new GreenfootImage("images/Vulture/Walking/Right/Right" + (i+1) + ".png");
           
           eatingAnimationUp[i] = new GreenfootImage("images/Vulture/Eating/Up/Up" + (i+1) + ".png");
           eatingAnimationDown[i] = new GreenfootImage("images/Vulture/Eating/Down/Down" + (i+1) + ".png");
           eatingAnimationLeft[i] = new GreenfootImage("images/Vulture/Eating/Left/Left" + (i+1) + ".png");
           eatingAnimationRight[i] = new GreenfootImage("images/Vulture/Eating/Right/Right" + (i+1) + ".png");
           
           walkingAnimationUp[i].scale(walkingAnimationUp[i].getWidth()*3/2, walkingAnimationUp[i].getHeight()*3/2);
           walkingAnimationDown[i].scale(walkingAnimationDown[i].getWidth()*3/2, walkingAnimationDown[i].getHeight()*3/2);
           walkingAnimationRight[i].scale(walkingAnimationRight[i].getWidth()*3/2, walkingAnimationRight[i].getHeight()*3/2);
           walkingAnimationLeft[i].scale(walkingAnimationLeft[i].getWidth()*3/2, walkingAnimationLeft[i].getHeight()*3/2);
           
           eatingAnimationUp[i].scale(eatingAnimationUp[i].getWidth()*3/2, eatingAnimationUp[i].getHeight()*3/2);
           eatingAnimationDown[i].scale(eatingAnimationDown[i].getWidth()*3/2, eatingAnimationDown[i].getHeight()*3/2);
           eatingAnimationRight[i].scale(eatingAnimationRight[i].getWidth()*3/2, eatingAnimationRight[i].getHeight()*3/2);
           eatingAnimationDown[i].scale(eatingAnimationDown[i].getWidth()*3/2, eatingAnimationDown[i].getHeight()*3/2);
        }
    }

    public void act()
    {
        super.act();
        if (!alive) return; // dead.
        
        if(actsSinceLastBreeding >= breedingThreshold && alive && !baby){
            ableToBreed = true;
        }else{
            ableToBreed = false;
        }
        
        if(!wantToEat){
            currentSpeed = defaultSpeed;
        }
        if (Greenfoot.getRandomNumber(8000) == 0) {
            vultureSound.play();
        }
    }

    /**
     * Method for vultures to search for a breeding partner when they are ready
     * If partner exists, they will proceed to breed
     */
    public void breed() {
        // Find another vulture nearby
        if (!(target instanceof Vulture)) { // attempt to find a Vulture eligble
            SuperActor search = (Vulture) getClosestInRange(this.getClass(), viewRadius, v -> !((Vulture)v).isAbleToBreed() || !((Vulture)v).isAlive()); // Adjust range as needed
            
            if (search != null) { // found a Vulture!
                target = search;
            }
        }
        
        if (target instanceof Vulture) { // Vulture found
            Vulture targetVulture = (Vulture) target; // cast to target
            
            // Check if retargeting needed.
            if (targetVulture.getWorld() == null || !targetVulture.isAlive() || !targetVulture.isAbleToBreed()) {
                target = null; // neccessiate retargeting
                return; // nothing else to do
            }
            else if(distanceFrom(target) < 40){ // close to target Vulture! breed
                breeding = true;
                breedingCounter++;
                if(breedingCounter > BREEDING_DELAY){
                    // Add the baby to the world
                    getWorld().addObject(new Vulture(true), getX(), getY());
                    ableToBreed = false;
                    targetVulture.setAbleToBreed(false);
                    breeding = false;
                    targetVulture.setIsBreeding(false);
                    breedingCounter = 0;
                    target = null;
                    actsSinceLastBreeding = 0;
                }
            }else{ // move closer
                moveTowards(targetVulture, currentSpeed, walkHeight);
            }
        }
    }

    /**
     * Method for vultures to find and eat food
     */
    public void findOrEatFood() {
        if (!(target instanceof Animal)) { // force the target to be of correct type
            SuperActor search = (Animal)getClosestInRange(Animal.class, viewRadius/4, a -> ((Animal)a).isAlive());
            if(search == null) {
                search = (Animal)getClosestInRange(Animal.class, viewRadius/2, a -> ((Animal)a).isAlive());
            }
            if(search == null) {
                search = (Animal)getClosestInRange(Animal.class, viewRadius, a-> ((Animal)a).isAlive());
            }
            
            if (search != null) {
                target = search; // found a target, assign it as so.
            }
        }
        
        if (target instanceof Animal){ // if target exists
            Animal targetPrey = (Animal) target; // cast as type Anial
            currentSpeed = huntSpeed;
            
            if (targetPrey.getWorld() == null) { // check for retargeting required
                eating = false;
                target = null; // need new target
                return; // nothing else to do.
            }
            else if(distanceFrom(targetPrey) < 5){ // Close so eat
                eating = true;
                targetPrey.decreaseTransparency(1); // ?
                eat(4);
            }
            else { // far so move closer.
                eating = false;
                moveTowards(targetPrey, currentSpeed, walkHeight);
            }
        } else {
            moveRandomly(); // no food so i guess move random
        }
    }

    /**
     * Method that animates vultures
     */
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
    
    /**
     * Method that gets the number of vultures alive in world
     * 
     * @return numOfVultures  the number of vultures alive in world
     */
    public static int getNumOfVultures() {
        return numOfVultures;
    }
    
    /**
     * Method sets number of vultures
     * 
     * @param num  the new number of vultures
     */
    public static void setNumOfVultures(int num){
        numOfVultures = num;
    }
}
