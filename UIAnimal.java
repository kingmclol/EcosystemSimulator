import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * UI animals are used with the AnimalSelector to display animated images when selecting types of animals.
 * A basic image object with animations for each animal in the simulation.
 *
 * 
 * @author Neelan Thurairajah 
 * @version (a version number or a date)
 */
public class UIAnimal extends UI
{
    private GreenfootImage img;
    private boolean moving;
    private int currentAct;
    private GreenfootImage [] animation;
    private int indexAnimation;
    public UIAnimal(String animal){
        animation = new GreenfootImage[4];
        // Gets an animation array of the chosen animal and scales it up
        if(animal.equals("rabbit")){
            animation = new GreenfootImage[4];
            img = new GreenfootImage("images/Rabbit/Walking/Right/Rabbit_WalkingRight1.png");
            for(int i = 0; i<4; i++)
            {
        
               animation[i] = new GreenfootImage("images/Rabbit/Walking/Right/Rabbit_WalkingRight" + (i+1) + ".png");
               animation[i].scale(animation[i].getWidth()*2, animation[i].getHeight()*2);
            }
        }
        else if(animal.equals("goat")){
            animation = new GreenfootImage[3];
            for(int i = 0; i<3; i++)
            {
                animation[i] = new GreenfootImage("images/Goat/Right/Right" + (i+1) + ".png");
                animation[i].scale(animation[i].getWidth()*2, animation[i].getHeight()*2);

            }
        }
        else if(animal.equals("wolf")){
            animation = new GreenfootImage[5];
            for(int i = 0; i<5; i++)
            {
                animation[i] = new GreenfootImage("images/Wolf/Walking/Right/Right" + (i+1) + ".png");
                animation[i].scale(animation[i].getWidth()*2, animation[i].getHeight()*2);

            }
        }
        else if(animal.equals("vulture")){
            animation = new GreenfootImage[3];
            for(int i = 0; i<3; i++)
            {
                animation[i] = new GreenfootImage("images/Vulture/Walking/Right/Right" + (i+1) + ".png");
                animation[i].scale(animation[i].getWidth()*2, animation[i].getHeight()*2);
            }
        }
        setImage(animation[0]);
        moving = false;

    }
    

    public void act()
    {
        currentAct++;
        
        animate();
    }
    /**
     * Animates the image.
     */
    public void animate()
    {
        setImage(animation[indexAnimation]);
        if(currentAct%10 == 0) // change animation every 45 acts
        {
            indexAnimation = (indexAnimation + 1)%(animation.length);
        }
       
    }
}
