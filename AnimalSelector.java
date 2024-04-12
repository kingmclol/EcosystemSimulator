import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * Write a description of class AnimalSelector here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class AnimalSelector extends UI
{
    private Button decrementButton;
    private Button incrementButton;
    public AnimalSelector(){
        GreenfootImage leftButtonImg = new GreenfootImage("images/incrementLeft.png");
        GreenfootImage rightButtonImg = new GreenfootImage("images/incrementRight.png");
        GreenfootImage img = new GreenfootImage(50, 50);
        img.setColor(new Color(0, 0, 0, 0));
        img.fill();
        setImage(img);
        leftButtonImg.scale(100, 100);
        rightButtonImg.scale(100, 100);
        decrementButton = new Button(this::decrementValue, 100, 100, leftButtonImg, leftButtonImg, leftButtonImg);
        incrementButton = new Button(this::incrementValue,100, 100, rightButtonImg, rightButtonImg, rightButtonImg);
    }
    public void addedToWorld(World w){
        w.addObject(new UIAnimal(new Rabbit()), getX(), getY());
    }
    private void incrementValue(){
    }
    private void decrementValue(){
    }
    public void act()
    {
        // Add your action code here.
    }
}
