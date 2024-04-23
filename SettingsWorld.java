import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * This class is where the user changes the various settings of the game. Values are
 * stored in static variables for use in the simulation.
 * @author Neelan Thurairajah
 * @version 2024/04/20
 *
 */
public class SettingsWorld extends CursorWorld
{
   
    private GreenfootSound settingsWorldMusic;
    // Set up counter and settings variables
    private static int goatCount;
    private static int wolfCount;
    private static int rabbitCount;
    private static int vultureCount;
    private static int goatView;
    private static int wolfView;
    private static int rabbitView;
    private static int vultureView;
    private static int simulationLength;
    
    private Slider dayCounter;
    
    private Button leaveButton;
    private AnimalSelector animalSelect;
    /**
     * Basic constructor
     */
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
        // Initialize image variables
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
    /**
     * Pauses music when the simulation is paused.
     */
    public void stopped() {
        settingsWorldMusic.pause(); 
    }
    /**
     * Pauses music when the simulation is started or unpaused.
     */
    public void started() {
        settingsWorldMusic.playLoop();
    }
    /**
     * When the leave button is clicked, the settings are saved and world is switched.
     */
    private void onClick(){
        // Save all settings values into static variables;
        rabbitCount = animalSelect.getNumOfRabbits();
        goatCount = animalSelect.getNumOfGoats();
        vultureCount = animalSelect.getNumOfVultures();
        wolfCount = animalSelect.getNumOfWolves();
        rabbitView = animalSelect.getViewRadiusOfRabbits();
        goatView = animalSelect.getViewRadiusOfGoat();
        vultureView = animalSelect.getViewRadiusOfVultures();
        wolfView = animalSelect.getViewRadiusOfWolves();
        simulationLength = dayCounter.getValue();
        
        // Check for is all animal nums are zero. If so, then continue selecting until at least one animal chosen
        if (rabbitCount + goatCount + vultureCount + wolfCount == 0) {
            display("At least one animal must exist!");
            return;
        }
        
        // Move to next world
        Greenfoot.setWorld(new DrawWorld());
        
        settingsWorldMusic.stop();
    }
    /**
     * Gets the number of goats that are to be spawned at the beginning of the simulation.
     * @return Number of goats to be spawned
     */
    public static int getNumOfGoatToSpawn(){
        return goatCount;
    }
    /**
     * Gets the number of rabbits that are to be spawned at the beginning of the simulation.
     * @return Number of rabbits to be spawned
     */
    public static int getNumOfRabbitToSpawn(){
        return rabbitCount;
    }
    /**
     * Gets the number of wolves that are to be spawned at the beginning of the simulation.
     * @return Number of wolves to be spawned
     */
    public static int getNumOfWolfToSpawn(){
        return wolfCount;
    }
    /**
     * Gets the number of vultures that are to be spawned at the beginning of the simulation.
     * @return Number of vultures to be spawned
     */
    public static int getNumOfVultureToSpawn(){
        return vultureCount;
    }
    /**
     * Gets the starting view radius of rabbits that are to be spawned at the beginning of the simulation.
     * @return View radius of rabbits
     */
    public static int getStartViewOfRabbit(){
        return rabbitView;
    }
    /**
     * Gets the starting view radius of wolves that are to be spawned at the beginning of the simulation.
     * @return View radius of wolves
     */
    public static int getStartViewOfWolf(){
        return wolfView;
    }
    /**
     * Gets the starting view radius of goats that are to be spawned at the beginning of the simulation.
     * @return View radius of goats
     */
    public static int getStartViewOfGoat(){
        return goatView;
    }
    /**
     * Gets the starting view radius of vultures that are to be spawned at the beginning of the simulation.
     * @return View radius of vultures
     */
    public static int getStartViewOfVulture(){
        return vultureView;
    }
    /**
     * Gets the simulation length in days.
     * @return Number of days the simulation lasts
     */
    public static int getSimulationLength(){
        return simulationLength;
    }
    /**
     * Dispalys a given string onto the world.
     * @param text the text to display.
     */
    private void display(String text) {
        // quick way to prevent spamming to reduce performance. why would you do that though :(
        if (getObjects(TextBox.class).size() > 3) return;
        
        TextBox box = new TextBox(text, 48, Color.WHITE, null, 3, 255);
        box.fadeOut();
        addObject(box, getWidth()/2, 100);
    }
}
