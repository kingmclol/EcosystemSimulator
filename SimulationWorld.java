import greenfoot.*; 
import java.util.ArrayList; // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)
import java.util.List;

/**
 * <p>The SimulationWorld will be where the main simulation takes place. 
 * It keeps track of various stats, such as the time, day, # of animals, etc.</p>.
 * 
 * <p>There are two "endings" what will set the world to an Ending world, with differnt conditions.</p>
 * <ul>
 *   <li>Good ending, where at least one animal survived until the alloted time lengh is done (from settings world)
 *   <li>Bad ending, where all animals are dead before the time limit
 * </ul>
 * 
 * @author Freeman Wang, Neelan, Ivan
 * @version 2024-04-24
 */
public class SimulationWorld extends World
{

    private static boolean isNight; 
    private static boolean isRaining;
    private static boolean isSnowing; 
    private int actCount; 


    private ArrayList<Tile> spawnableTiles; 

    private int dayCount; 
    private int hours; 
    private int time; 
    private int endingDay;
    private SuperDisplayLabel scoreBar; 
    private GreenfootSound simulationSound;
    private static ArrayList<String> dailyBoards;
    /**
     * Constructor for objects of class SimulationWorld.
     * 
     */
    public SimulationWorld()
    {    
        // Create a new world with 600x400 cells with a cell size of 1x1 pixels.
        super(1008, 768, 1); 

        setPaintOrder(UI.class, SuperDisplayLabel.class,Sun.class, Moon.class, Effect.class, BreedingEffect.class, Animal.class, Node.class, Tile.class);

        Board.setWorld(this);
        Tile.setTimeFlow(true);
        
        spawnableTiles = (ArrayList<Tile>)((ArrayList<?>)getObjects(GrassTile.class));
        spawnableTiles.addAll((ArrayList<Tile>)((ArrayList<?>)getObjects(BushTile.class)));
        spawnAnimals("Goat", SettingsWorld.getNumOfGoatToSpawn());
        spawnAnimals("Rabbit", SettingsWorld.getNumOfRabbitToSpawn());
        spawnAnimals("Vulture", SettingsWorld.getNumOfVultureToSpawn());
        spawnAnimals("Wolf", SettingsWorld.getNumOfWolfToSpawn());
        actCount = 1200; 
        
        dayCount = 0;
        time = 8;
        hours = 0;
        endingDay = SettingsWorld.getSimulationLength();
        simulationSound = new GreenfootSound("naturesounds.mp3");
        simulationSound.playLoop();
        scoreBar = new SuperDisplayLabel (Color.BLACK, Color.WHITE, new Font ("Trebuchet", true, false, 24), 48, " ");
        
        isRaining=  false;
        isNight = false;
        isSnowing = false; 
        
        scoreBar.setLabels(new String[] {"Day: ","Time: ", "Goat:", "Rabbit: ", "Vulture: ", "Wolf: "});
        addObject(scoreBar, 504, 24);
        addObject(new Sun(), 900, 200);
        addObject(new Moon(), 100, 810);
        
        dailyBoards = new ArrayList<String>();
        dailyBoards.add(Board.getBuildString()); // Zeroth day.
    }
    
    public void started() {
        Goat.setNumOfGoats(0);
        Rabbit.setNumOfRabbits(0);
        Wolf.setNumOfWolves(0);
        Vulture.setNumOfVultures(0);
        simulationSound.playLoop();
    }
    public void stopped() {
        simulationSound.pause();
    }
    public void act()
    {
        actCount++; 
        spawn();
        ArrayList<Animal> aliveAnimals = (ArrayList<Animal>)getObjects(Animal.class); // get all animals
        aliveAnimals.removeIf(a -> !a.isAlive()); // filter out dead ones
        if (aliveAnimals.size() == 0) { // If no alive animals are alive
            simulationSound.stop();
            Rain.stopRainSound();
            Snowstorm.stopSnowSound(); 
            Greenfoot.setWorld(new EndingWorld(false)); // Bad ending
        }
        else if (dayCount == endingDay) { // survived long enough!
            simulationSound.stop();
            Rain.stopRainSound();
            Snowstorm.stopSnowSound(); 
            Greenfoot.setWorld(new EndingWorld(true)); // good ending
        }
    }
    /**
     * Manages spawning the world effects, such as night, rain, etc.
     */
    public void spawn() 
    {
        if (actCount >= 2400) {
            addObject(new Night(), 0, 0);
            actCount = 0;
        }
        if (!isRaining && !isSnowing && Greenfoot.getRandomNumber(1200) == 0) {
            boolean temp = Greenfoot.getRandomNumber(2) == 0 ? true : false;
            if (temp) {
                addObject(new Rain(), 0, 0);
                isRaining = true;
            }
            else {
                addObject(new Snowstorm(), 0, 0);
                isSnowing = true; 
            }
        }

        statUpdates(); 
    }
    /**
     * Spawn all of the animals at initial setup
     */
    private void spawnAnimals(String animal, int num) 
    {
        for(int i = 0; i < num; i++){
            Tile tile = spawnableTiles.get(Greenfoot.getRandomNumber(spawnableTiles.size()));
            switch (animal){
                case "Goat":
                    addObject(new Goat(), tile.getX(), tile.getY());
                    break;
                case "Rabbit":
                    addObject(new Rabbit(), tile.getX(), tile.getY());
                    break;
                case "Wolf":
                    addObject(new Wolf(false), tile.getX(), tile.getY());
                    break;
                case "Vulture":
                    addObject(new Vulture(), tile.getX(), tile.getY());
                    break;
            }
            
        }
    }
    /**
     * Set whether it is currently nighttime.
     * @param newNight if it is nighttime or not
     */
    public static void setNight(boolean newNight)
    {
        isNight = newNight; 
    }
    /**
     * Get whether it is nighttime or not
     * @return whether it is nighttime or not
     */
    public static boolean getNight()
    {
        return isNight;
    }
    /**
     * Set whether it is currently snowing in the world
     * @param isSnowing whether it is snowing.
     */
    public static void setSnowing(boolean isSnowing) {
        SimulationWorld.isSnowing = isSnowing; 
    }
    /**
     * Set whether it is raining in the world.
     * @param isRaining whether it is raining.
     */
    public static void setRaining(boolean isRaining) {
        SimulationWorld.isRaining = isRaining;
    }
    /**
     * Update the stats display label.
     */
    public void statUpdates() {
        if (actCount % 100 == 0) {
            time += 1;
            hours += 1;
        }
        if (hours == 24) {
            hours = 0;
            dayCount += 1;
            dailyBoards.add(Board.getBuildString());
        }
        if (time == 24) {
            time = 0;
        }
        scoreBar.update(new int[]{dayCount, time, Goat.getNumOfGoats(), Rabbit.getNumOfRabbits(), Vulture.getNumOfVultures(), Wolf.getNumOfWolves()}); 
    }
    /**
     * Return the state of the world every day represented by build strings.
     */
    public static List<String> getDailyBoards() {
        return dailyBoards;
    }
}
