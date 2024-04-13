import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)


public class SettingsWorld extends CursorWorld
{
    private static int goatCount;
    private static int wolfCount;
    private static int rabbitCount;
    private static int vultureCount;
    private static int deerEnergy;
    private static int wolfEnergy;
    private static int rabbitEnergy;
    private static int vultureEnergy;
    private ButtonIncrement animalCounter1;
    private ButtonIncrement animalCounter2;
    private Button leaveButton;
    private AnimalSelector animalSelect;
    private Slider slider;
    public SettingsWorld()
    {    
        super();
        addObject(cursor, 0, 0);
        setBackground(new GreenfootImage("images/settingsbg.png"));
        goatCount = 0;
        wolfCount = 0;
        rabbitCount = 0;


        GreenfootImage buttonImg1 = new GreenfootImage("images/incrementLeft.png");
        GreenfootImage buttonImg2 = new GreenfootImage("images/incrementRight.png");

        animalSelect = new AnimalSelector();
        addObject(animalSelect, getWidth()/2, getHeight()/2 - 50);
        
        leaveButton = new Button(() -> onClick(), 100, 50);
        addObject(leaveButton, getWidth()/2, getHeight() - 100);
    }
    private void onClick(){

        rabbitCount = animalSelect.getNumOfDeer();
        deerCount = animalSelect.getNumOfRabbits();
        vultureCount = animalSelect.getNumOfVultures();
        wolfCount = animalSelect.getNumOfWolves();
        rabbitEnergy = animalSelect.getEnergyOfRabbits();
        deerEnergy = animalSelect.getEnergyOfDeer();
        vultureEnergy = animalSelect.getEnergyOfVultures();
        wolfEnergy = animalSelect.getEnergyOfWolves();
        Greenfoot.setWorld(new DrawWorld());
    }
    public static int getNumOfGoatToSpawn(){
        return goatCount;
    }
    public static int getNumOfRabbitToSpawn(){
        return rabbitCount;
    }
    public static int getNumOfWolfToSpawn(){
        return wolfCount;
    }
    public static int getNumOfVultureToSpawn(){
        return vultureCount;
    }
    public static int getStartEnergyOfRabbit(){
        return rabbitEnergy;
    }
    public static int getStartEnergyOfWolf(){
        return wolfEnergy;
    }
    public static int getStartEnergyOfDeer(){
        return deerEnergy;
    }
    public static int getStartEnergyOfVulture(){
        return vultureEnergy;
    }
    
}
