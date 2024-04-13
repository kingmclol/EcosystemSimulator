import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)


public class SettingsWorld extends CursorWorld
{
    private static int deerCount;
    private static int wolfCount;
    private static int rabbitCount;
    private ButtonIncrement animalCounter1;
    private ButtonIncrement animalCounter2;
    private Button leaveButton;
    private AnimalSelector animalSelect;
    public SettingsWorld()
    {    
        super();
        addObject(cursor, 0, 0);
        setBackground(new GreenfootImage("images/settingsbg.png"));
        deerCount = 0;
        wolfCount = 0;
        rabbitCount = 0;
        SuperTextBox animalText1 = new SuperTextBox("Rabbit", new Font(24), 150);
        SuperTextBox animalText2 = new SuperTextBox("Deer", new Font(24), 150);
        GreenfootImage buttonImg1 = new GreenfootImage("images/incrementLeft.png");
        GreenfootImage buttonImg2 = new GreenfootImage("images/incrementRight.png");
        animalCounter1 = new ButtonIncrement(300, 50, 200, 50, "Rabbit", buttonImg1, buttonImg2);
        animalCounter2 = new ButtonIncrement(300, 50, 200, 50, "Deer", buttonImg1, buttonImg2);
        animalSelect = new AnimalSelector();
        addObject(animalCounter1, getWidth()/2, 150);
        addObject(animalCounter2, getWidth()/2, 550);
        addObject(animalSelect, getWidth()/2, getHeight()/2 - 50);
        
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
