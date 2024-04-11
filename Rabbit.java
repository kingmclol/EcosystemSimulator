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
    private GrassTile targetGrass;
    

    //Animation
    private int indexAnimation = 0;
    private int currentAct = 0;
    private boolean spawnOne = true;
    private GreenfootImage[] eatingAnimationUp = new GreenfootImage[4];
    private GreenfootImage[] eatingAnimationDown = new GreenfootImage[4];
    private GreenfootImage[] eatingAnimationLeft = new GreenfootImage[4];
    private GreenfootImage[] eatingAnimationRight = new GreenfootImage[4];

    private GreenfootImage[] walkingAnimationUp = new GreenfootImage[4];
    private GreenfootImage[] walkingAnimationDown = new GreenfootImage[4];
    private GreenfootImage[] walkingAnimationLeft = new GreenfootImage[4];
    private GreenfootImage[] walkingAnimationRight = new GreenfootImage[4];
    //https://opengameart.org/content/reorganised-lpc-rabbit
    public Rabbit() {
        super();
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
        beingEaten = false;
        defaultSpeed = 0.6;
        currentSpeed = defaultSpeed;
        sprintSpeed = 1.2 * defaultSpeed;
        waterSpeed = 0.7 * defaultSpeed;
        wantToEat = false;
        viewRadius = 400;
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
            if(!wantToEat){
                breed();
            }
        }else{
            ableToBreed = false;
        }

        if((targetGrass == null) || targetGrass.getWorld() == null || (getWorld() != null && !(distanceFrom(targetGrass) < 12))){
            eating = false;
        }
        else{
            eating = true;
        }

        if(alive && !beingEaten && !breeding){
            animate();
            if(wantToEat){

                findGrassAndEat();
            }else{
                targetGrass = null;
            }
        }
    }

    public void breed() {
        // Find another rabbit nearby
        partner = (Rabbit) getClosestInRange(this.getClass(), viewRadius, r -> !((Rabbit)r).isAbleToBreed() || !((Rabbit)r).isAlive()); // Adjust range as needed
        if(partner != null){
            findingPartner = true;
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
                System.out.println("find partner");
                moveTowards(partner, currentSpeed, walkHeight);
            }
        }else{
            findingPartner = false;
            //moveRandomly();
            //move(currentSpeed);
        }
    }

    public void findGrassAndEat() {
        if(targetGrass == null || targetGrass.getWorld() == null || !targetGrass.grassAvailable()){
            targetGrass = (GrassTile)getClosestInRange(GrassTile.class, viewRadius/4, g -> !((GrassTile)g).grassAvailable());
            if(targetGrass == null) {
                targetGrass = (GrassTile)getClosestInRange(GrassTile.class, viewRadius/2, g -> !((GrassTile)g).grassAvailable());
            }
            if(targetGrass == null) {
                targetGrass = (GrassTile)getClosestInRange(GrassTile.class, viewRadius, g -> !((GrassTile)g).grassAvailable());
            }
        }
        if(targetGrass != null){
            targetTile = targetGrass;
            if(distanceFrom(targetGrass) < 10){
                targetGrass.nibble(7);
                eat(4);
            }
            else{
                System.out.println("find grass");
                moveTowards(targetTile, currentSpeed, walkHeight);
            }
        }
    }

    public void takeDamage(int dmg) {
        hp = hp - dmg;
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
        if(currentAct%20 == 0) // change animation every 45 acts
        {
            indexAnimation = (indexAnimation + 1)%(eatingAnimationRight.length);
        }
    }

    public int getHp() {
        return hp;
    }

}
