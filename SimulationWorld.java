import greenfoot.*; 
import java.util.ArrayList; // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)
/**
 * Write a description of class SimulationWorld here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class SimulationWorld extends World
{

    private static boolean isNight = false; 
    
    private int actCount; 


    private ArrayList<Tile> spawnableTiles; 

    private int dayCount; 
    private int hours; 
    private int time; 
    
    private SuperDisplayLabel scoreBar; 

    /**
     * Constructor for objects of class SimulationWorld.
     * 
     */
    public SimulationWorld()
    {    
        // Create a new world with 600x400 cells with a cell size of 1x1 pixels.
        super(1008, 768, 1); 

        setPaintOrder(UI.class, SuperDisplayLabel.class, Effect.class, BreedingEffect.class, Animal.class, Node.class, Tile.class);

        Board.setWorld(this);
        Tile.setTimeFlow(true);
        
        spawnableTiles = (ArrayList<Tile>)((ArrayList<?>)getObjects(GrassTile.class));
        spawnableTiles.addAll((ArrayList<Tile>)((ArrayList<?>)getObjects(BushTile.class)));
        spawnAnimals("Deer", SettingsWorld.getNumOfDeerToSpawn());
        spawnAnimals("Rabbit", SettingsWorld.getNumOfRabbitToSpawn());

        actCount = 1200; 
        
        dayCount = 0;
        time = 8;
        hours = 0; 
        
        scoreBar = new SuperDisplayLabel (Color.BLACK, Color.WHITE, new Font ("Trebuchet", true, false, 24), 48, ".");
        scoreBar.setLabels(new String[] {"Day: ","Time: "});
        addObject(scoreBar, 504, 24);
    }
    
    public void act()
    {
        actCount++; 
        spawn();
    }
    public void spawn() 
    {
        if (actCount >= 2400) {
            addObject(new Night(), 0, 0);
            actCount = 0;
        }

        statUpdates(); 
        scoreBar.update(new int[]{dayCount, time}); 
    }
    private void spawnAnimals(String animal, int num) 
    {
        for(int i = 0; i < num; i++){
            Tile tile = spawnableTiles.get(Greenfoot.getRandomNumber(spawnableTiles.size()));
            switch (animal){
                case "Deer":
                    addObject(new Deer(), tile.getX(), tile.getY());
                    break;
                case "Rabbit":
                    addObject(new Rabbit(), tile.getX(), tile.getY());
                    break;
                case "Wolf":
                    addObject(new Wolf(false), tile.getX(), tile.getY());
                    break;
            }
            
        }
    }
    public static void setNight(boolean newNight)
    {
        isNight = newNight; 
    }
    
    public void statUpdates() {
        if (actCount % 100 == 0) {
            time += 1;
            hours += 1;
        }
        if (hours == 24) {
            hours = 0;
            dayCount += 1;
        }
        if (time > 12) {
            time = 1;
        }
    }

}
