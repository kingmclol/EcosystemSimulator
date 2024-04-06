import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)


public class SettingsWorld extends CursorWorld
{
    
 
    private ButtonIncrement animalCounter1;
    private ButtonIncrement animalCounter2;
    public SettingsWorld()
    {    
        super();
        addObject(cursor, 0, 0);
        
        animalCounter1 = new ButtonIncrement(400, 50, 300, 50);
        animalCounter2 = new ButtonIncrement(400, 50, 300, 50);
        addObject(animalCounter1, getWidth()/2, 150);
        addObject(animalCounter2, getWidth()/2, 400);
    }
    
}
