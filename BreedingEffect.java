import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * Write a description of class BreedingEffect here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class BreedingEffect extends SuperActor
{
    private int breedCount = 0;
    private int timer;
    private int index = 0;
    private GreenfootImage[] breedingAnimation = new GreenfootImage[5];
    /**
     * Act - do whatever the BreedingEffect wants to do. This method is called whenever
     * the 'Act' or 'Run' button gets pressed in the environment.
     */
    public BreedingEffect(int timeInSeconds)
    {
        timer = timeInSeconds;
        for(int i = 0; i<5; i++)
        {
            breedingAnimation[i] = new GreenfootImage("images/Breeding/breed" + (i+1)+".png");
            breedingAnimation[i].scale(getImage().getWidth()*4/5, getImage().getHeight()*4/5);
        }
        setImage(breedingAnimation[0]);
    }
    public void act()
    {
        // Add your action code here.
        breedCount++;
        if(breedCount <= (timer*60))
        {
            if(breedCount%(timer*60/6) == 0)
            {
              setImage(breedingAnimation[index]);
              index = (index+1)%(breedingAnimation.length);
            }
        }
        else
        {
            getWorld().removeObject(this);
        }
    }
}
