import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)
import java.util.function.Predicate;
/**
 * Wolf subclass
 * Wolves will prey on rabbits
 * 
 * @author (Osmond Lin) 
 */
public class Wolf extends Animal
{
    private GreenfootImage[] eatingAnimationUp = new GreenfootImage[4];
    private GreenfootImage[] eatingAnimationDown = new GreenfootImage[4];
    
    private GreenfootImage[] walkingAnimationUp = new GreenfootImage[4];
    private GreenfootImage[] walkingAnimationDown = new GreenfootImage[4];
    private GreenfootImage[] walkingAnimationLeft = new GreenfootImage[5];
    private GreenfootImage[] walkingAnimationRight = new GreenfootImage[5];
    private int indexAnimation = 0;
    private boolean isVerticallyFacing = false;
    //https://i.pinimg.com/originals/20/92/d0/2092d0d2b2b3f7d473adf10353959c1a.jpg
    
    public Wolf(boolean isBaby) {
        super(isBaby);
        defaultSpeed = ((double)Greenfoot.getRandomNumber(11)/100.0) + 0.7;
        currentSpeed = defaultSpeed;
        waterSpeed = 0.7 * defaultSpeed;
        wantToEat = false;
        viewRadius = 500;
        walkHeight = 1;
        for(int i = 0; i<4; i++)
        {
            walkingAnimationUp[i] = new GreenfootImage("images/Wolf/Walking/Up/Up" + (i+1) + ".png");
            walkingAnimationDown[i] = new GreenfootImage("images/Wolf/Walking/Down/Down" + (i+1) + ".png");
            eatingAnimationUp[i] = new GreenfootImage("images/Wolf/Eating/Up/Up" + (i+1) + ".png");
            eatingAnimationDown[i] = new GreenfootImage("images/Wolf/Eating/Down/Down" + (i+1) + ".png");
        }
        for(int i = 0; i<5; i++)
        {
            walkingAnimationLeft[i] = new GreenfootImage("images/Wolf/Walking/Left/Left" + (i+1) + ".png");
            walkingAnimationRight[i] = new GreenfootImage("images/Wolf/Walking/Right/Right" + (i+1) + ".png");
        }

    }

    /**
     * Act - do whatever the Wolf wants to do. This method is called whenever
     * the 'Act' or 'Run' button gets pressed in the environment.
     */
    public void act() {
        super.act();
        if (!alive) return;  // am dead. nothing to do.

        if(actsSinceLastBreeding >= breedingThreshold && alive && !baby){
            ableToBreed = true;
        }else{
            ableToBreed = false;
        }
    }

    public void breed() {
        // Find another wolf nearby
        if (!(target instanceof Wolf)) { // attempt to find a Wolf eligble
            SuperActor search = (Wolf) getClosestInRange(this.getClass(), viewRadius, w -> !((Wolf)w).isAbleToBreed() || !((Wolf)w).isAlive()); // Adjust range as needed
            
            if (search != null) { // found a wolf!
                target = search;
            }
        }
        
        if (target instanceof Wolf) { // wolf found
            Wolf targetWolf = (Wolf) target; // cast to target
            
            // Check if retargeting needed.
            if (targetWolf.getWorld() == null || !targetWolf.isAlive() || !targetWolf.isAbleToBreed()) {
                target = null; // neccessiate retargeting
                return; // nothign else t do
            }
            else if(distanceFrom(target) < 40){ // close to target wolf! breed
                breeding = true;
                breedingCounter++;
                if(breedingCounter > BREEDING_DELAY){
                    // Add the baby to the world
                    getWorld().addObject(new Wolf(true), getX(), getY());
                    ableToBreed = false;
                    targetWolf.setAbleToBreed(false);
                    breeding = false;
                    targetWolf.setIsBreeding(false);
                    breedingCounter = 0;
                    target = null;
                    actsSinceLastBreeding = 0;
                }
            }else{ // move closer
                moveTowards(targetWolf, currentSpeed, walkHeight);
            }
        } else {
            moveRandomly(); // no target found, so wander instead.
        }
    }

    public void findOrEatFood() {
        // Attempt to find some prey, so target would be of corret type
        if (!(target instanceof Animal)) {
            // Create an more advanced filter to find an eligble food (animal)
            // Read as: Remove if animal is dead, or the animal is a wolf, or animal is a vulture.
            Predicate<Animal> filter = a -> (!a.isAlive() || a instanceof Wolf || a instanceof Vulture);
            SuperActor search = (Animal) getClosestInRange(Animal.class, viewRadius/4, filter);
            if (search == null){
                search = (Animal) getClosestInRange(Animal.class, viewRadius/2, filter);
            }
            if (search == null) {
                search = (Animal) getClosestInRange(Animal.class, viewRadius, filter);
            }
            
            if (search != null) {
                target = search; // found one.
            }
        }
        

        if (target instanceof Animal) {
            Animal targetPrey = (Animal) target; // cast into ANimal to access instance methods.
            
            // check if retarget required.
            if (!targetPrey.isAlive() || targetPrey.getWorld() == null) {
                eating = false;
                target = null;
                return;
            }
            else if (distanceFrom(targetPrey) < 5) { // close enough, eat it
                eating = true;
                targetPrey.takeDamage(15);
                if (targetPrey.getEnergy() <= 0) {
                    targetPrey.disableStaticRotation();
                    targetPrey.setRotation(90);
                }
                eat(12);
            }
            else { // too far, move closer.
                eating = false;
                moveTowards(targetPrey, currentSpeed, walkHeight);
            }
        }
        else {
            moveRandomly(); // move randomly this act.
        }
    }
    public void animate()
    {
        if(eating)
        {
            if(facing.equals("up"))
            {
                if(indexAnimation > 3)
                {
                    indexAnimation = 0;
                }
                isVerticallyFacing = true;
                setImage(eatingAnimationUp[indexAnimation]);
            }
            else if(facing.equals("down"))
            {
                if(indexAnimation > 3)
                {
                    indexAnimation = 0;
                }
                isVerticallyFacing = true;
                setImage(eatingAnimationDown[indexAnimation]);
            }
        }
        else
        {
            if(facing.equals("right"))
            {
                isVerticallyFacing = false;
                setImage(walkingAnimationRight[indexAnimation]);
            }
            else if(facing.equals("left"))
            {
                isVerticallyFacing = false;
                setImage(walkingAnimationLeft[indexAnimation]);
            }
            else if(facing.equals("up"))
            {
                if(indexAnimation > 3)
                {
                    indexAnimation = 3;
                }
                isVerticallyFacing = true;
                setImage(walkingAnimationUp[indexAnimation]);
            }
            else // Down
            {
                if(indexAnimation > 3)
                {
                    indexAnimation = 3;
                }
                isVerticallyFacing = true;
                setImage(walkingAnimationDown[indexAnimation]);
            }
        }
        if(currentAct%20 == 0) // change animation every 20 acts
        {
            if(isVerticallyFacing)
            {
                indexAnimation = (indexAnimation+1)%(walkingAnimationDown.length);
            }
            else
            {
                indexAnimation = (indexAnimation + 1)%(walkingAnimationRight.length);
            }
        }
    }
}
