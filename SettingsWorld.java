import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)


public class SettingsWorld extends CursorWorld
{
    private GreenfootSound settingsWorldMusic;
    private static int goatCount;
    private static int wolfCount;
    private static int rabbitCount;
    private static int vultureCount;
    private static int goatEnergy;
    private static int wolfEnergy;
    private static int rabbitEnergy;
    private static int vultureEnergy;
    private static int simulationLength;
    private Slider dayCounter;
    
    private Button leaveButton;
    private AnimalSelector animalSelect;
    
    public SettingsWorld()
    {    
        super();
        addObject(cursor, 0, 0);
        setBackground(new GreenfootImage("images/settingsbg.png"));
        goatCount = 0;
        wolfCount = 0;
        rabbitCount = 0;
    
        settingsWorldMusic = new GreenfootSound("Settingworld.mp3");
        settingsWorldMusic.setVolume(50);
        settingsWorldMusic.playLoop();
        UI.init();
        GreenfootImage leftButtonImg = new GreenfootImage("images/incrementLeft.png");
        GreenfootImage leftButtonImg1 = new GreenfootImage("images/incrementLeftHovered.png");
        GreenfootImage leftButtonImg2 = new GreenfootImage("images/incrementLeftPressed.png");
    
        GreenfootImage rightButtonImg = new GreenfootImage("images/incrementRight.png");
        GreenfootImage rightButtonImg1 = new GreenfootImage("images/incrementRightHovered.png");
        GreenfootImage rightButtonImg2 = new GreenfootImage("images/incrementRightPressed.png");
        
        GreenfootImage [] leftButtonImgs = {leftButtonImg, leftButtonImg1, leftButtonImg2};
        GreenfootImage [] rightButtonImgs = {rightButtonImg, rightButtonImg1, rightButtonImg2};
        
        dayCounter = new Slider(200, 1, 50, "# of Days");
        addObject(dayCounter, 150, getHeight()/2);
        GreenfootImage leaveButtonImg1 = new GreenfootImage("images/goButton.png");
        GreenfootImage leaveButtonImg2 = new GreenfootImage("images/goButtonHovered.png");
        GreenfootImage leaveButtonImg3 = new GreenfootImage("images/goButtonPressed.png");
        animalSelect = new AnimalSelector();
        addObject(animalSelect, getWidth()/2, getHeight()/2 - 30);
        
        leaveButton = new Button(() -> onClick(), 100, 50, leaveButtonImg1, leaveButtonImg2, leaveButtonImg3);
        
        addObject(leaveButton, getWidth()/2, getHeight() - 100);
    }
    public void stopped() {
        settingsWorldMusic.pause(); 
    }
    public void started() {
        settingsWorldMusic.playLoop();
    }
    private void onClick(){
        rabbitCount = animalSelect.getNumOfRabbits();
        goatCount = animalSelect.getNumOfGoats();
        vultureCount = animalSelect.getNumOfVultures();
        wolfCount = animalSelect.getNumOfWolves();
        rabbitEnergy = animalSelect.getEnergyOfRabbits();
        goatEnergy = animalSelect.getEnergyOfGoat();
        vultureEnergy = animalSelect.getEnergyOfVultures();
        wolfEnergy = animalSelect.getEnergyOfWolves();
        simulationLength = dayCounter.getValue();
        Greenfoot.setWorld(new DrawWorld());
        
        settingsWorldMusic.stop();
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
    public static int getStartEnergyOfGoat(){
        return goatEnergy;
    }
    public static int getStartEnergyOfVulture(){
        return vultureEnergy;
    }
    public static int getSimulationLength(){
        return simulationLength;
    }
}
