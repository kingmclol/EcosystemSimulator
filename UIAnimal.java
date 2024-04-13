import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * Write a description of class animal here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class UIAnimal extends UI
{
    private GreenfootImage img;
    private boolean moving;
    private int currentAct;
    private GreenfootImage [] walkingAnimationRight;
    private int indexAnimation;
    public UIAnimal(String animal){
        walkingAnimationRight = new GreenfootImage[4];
        if(animal.equals("rabbit")){
            img = new GreenfootImage("images/Rabbit/Walking/Right/Rabbit_WalkingRight1.png");
            for(int i = 0; i<4; i++)
            {
        
                walkingAnimationRight[i] = new GreenfootImage("images/Rabbit/Walking/Right/Rabbit_WalkingRight" + (i+1) + ".png");
                walkingAnimationRight[i].scale(walkingAnimationRight[i].getWidth()*2, walkingAnimationRight[i].getHeight()*2);
            }
        }
        else if(animal.equals("deer")){
            img = new GreenfootImage("images/camel.png");
        }
        
        img.scale(img.getWidth()*2, img.getHeight()*2);
        setImage(img);
        moving = false;

    }
    public void setMoving(boolean moving)
    {
        this.moving = moving;
    }
    public void act()
    {
        currentAct++;
        
        //animate();
    }
    
    public void animate()
    {
        setImage(walkingAnimationRight[indexAnimation]);
        if(currentAct%15 == 0) // change animation every 45 acts
        {
            indexAnimation = (indexAnimation + 1)%(walkingAnimationRight.length);
        }
       
    }
}
