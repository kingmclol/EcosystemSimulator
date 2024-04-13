import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * Deer subclass that will eat berries
 * 
 * @author (Osmond Lin) 
 */
public class Deer extends Animal
{   
    //https://www.pinterest.com/pin/387028161720848437/
    private GreenfootImage[] animationUp = new GreenfootImage[3];
    private GreenfootImage[] animationDown = new GreenfootImage[3];
    private GreenfootImage[] animationLeft = new GreenfootImage[3];
    private GreenfootImage[] animationRight = new GreenfootImage[3];
    private int indexAnimation = 0;
    public Deer(boolean isBaby) {
        super(isBaby);
        defaultSpeed = ((double)Greenfoot.getRandomNumber(11)/100.0) + 0.5;
        currentSpeed = defaultSpeed;
        waterSpeed = 0.7 * defaultSpeed;
        wantToEat = false;
        viewRadius = 400;
        walkHeight = 2;
        breedingThreshold = 2000;
        for(int i = 0; i<3; i++)
        {
            animationUp[i] = new GreenfootImage("images/Goat/Up/Up" + (i+1) + ".png");
            animationDown[i] = new GreenfootImage("images/Goat/Down/Down" + (i+1) + ".png");
            animationRight[i] = new GreenfootImage("images/Goat/Right/Right" + (i+1) + ".png");
            animationLeft[i] = new GreenfootImage("images/Goat/Left/Left" + (i+1) + ".png");
        }
        setImage(animationRight[indexAnimation]);
    }

    public Deer() {
        super(false);
        defaultSpeed = ((double)Greenfoot.getRandomNumber(11)/100.0) + 0.5;
        currentSpeed = defaultSpeed;
        waterSpeed = 0.7 * defaultSpeed;
        wantToEat = false;
        viewRadius = 400;
        walkHeight = 2;
        breedingThreshold = 2000;
    }

    /**
     * Act - do whatever the Deer wants to do. This method is called whenever
     * the 'Act' or 'Run' button gets pressed in the environment.
     */
    public void act()
    {
        super.act();
        if (!alive) return; // There has to be a better way than doing this, right? right?????
        actsSinceLastBreeding++;
        if(actsSinceLastBreeding >= breedingThreshold && alive && !baby){
            ableToBreed = true;
        }else{
            ableToBreed = false;
        }
    }

    public void breed() {
        // Find another deer nearby
        if (!(target instanceof Deer)) { // Find a Deer if the target is null, or not a deer.
            SuperActor search = (Deer) getClosestInRange(this.getClass(), viewRadius, d -> !((Deer)d).isAbleToBreed() || !((Deer)d).isAlive()); // Adjust range as needed
            if (search != null){ // found one!
                target = search; // set it as the target.
            }
        }

        if (target instanceof Deer) { // check for null or if target is not a deer.
            Deer targetDeer = (Deer) target; // cast target into Deer object

            // check for need for retargeting.
            if (targetDeer.getWorld() == null || !targetDeer.isAlive() || !targetDeer.isAbleToBreed()) {
                target = null;
                return;
            }
            else if(distanceFrom(target) < 40){ // close enough, breed
                breeding = true;
                breedingCounter++;
                if(breedingCounter > BREEDING_DELAY){
                    // Add the baby to the world

                    getWorld().addObject(new Deer(true), getX(), getY());
                    ableToBreed = false;
                    targetDeer.setAbleToBreed(false);
                    breeding = false;
                    targetDeer.setIsBreeding(false);
                    breedingCounter = 0;

                    target = null; // no target anymore.
                    actsSinceLastBreeding = 0;

                }
            }else{ // far, move closer
                moveTowards(target, currentSpeed, walkHeight);
            }
        } else {
            moveRandomly(); // no target deer. move randomly instead :(
        }
    }

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

    public void findOrEatFood() {
        if (!(target instanceof BushTile)) { // force the target to be a bush tile
            SuperActor search = (BushTile)getClosestInRange(BushTile.class, viewRadius/4, b -> !((BushTile)b).berriesAvailable());
            if(search == null) {
                search = (BushTile)getClosestInRange(BushTile.class, viewRadius/2, b -> !((BushTile)b).berriesAvailable());
            }
            if(search == null) {
                search = (BushTile)getClosestInRange(BushTile.class, viewRadius, b -> !((BushTile)b).berriesAvailable());
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
        else { // nothinf gound.
            moveRandomly(); 
        }
    }
}

