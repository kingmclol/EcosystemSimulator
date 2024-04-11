import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)


public class SettingsWorld extends CursorWorld
{
    private static int deerCount;
    private static int wolfCount;
    private static int rabbitCount;
    private ButtonIncrement animalCounter1;
    private ButtonIncrement animalCounter2;
    private Button leaveButton;
    
    public SettingsWorld()
    {    
        super();
        addObject(cursor, 0, 0);
        deerCount = 0;
        wolfCount = 0;
        rabbitCount = 0;
        SuperTextBox animalText1 = new SuperTextBox("Rabbit", new Font(24), 150);
        SuperTextBox animalText2 = new SuperTextBox("Deer", new Font(24), 150);
        animalCounter1 = new ButtonIncrement(400, 50, 300, 50);
        animalCounter2 = new ButtonIncrement(400, 50, 300, 50);
        addObject(animalCounter1, getWidth()/2, 150);
        addObject(animalCounter2, getWidth()/2, 400);
        addObject(animalText1, getWidth()/2, 100);
        addObject(animalText2, getWidth()/2, 350);
        
        leaveButton = new Button(() -> onClick(), 100, 50);
        addObject(leaveButton, getWidth()/2, getHeight() - 100);
    }
    private void onClick(){
        rabbitCount = animalCounter1.getValue();
        deerCount = animalCounter2.getValue();
        Greenfoot.setWorld(new DrawWorld());
    }
    public static int getNumOfDeerToSpawn(){
        return deerCount;
    }
    public static int getNumOfRabbitToSpawn(){
        return rabbitCount;
    }
    
}
