import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)
import java.util.Collections;
import java.util.ArrayList;
/**
 * <p>BushTiles are tiles with bushes on them which can also grow berries for Animals to eat. They can also
 * drop seeds onto nearby GrassTiles.</p>
 * 
 * <p>BushTiles have a heightLevel of 1.</p>
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class BushTile extends Tile
{
    private static final int MAX_BERRIES = 1000;
    private int berryAmount = 500;
    private int berryGrowSpeed = 1;
    private boolean berriesAvailable;
    private static int GROW_TIME_MIN = 300;
    private static int GROW_TIME_MAX = 1000;
    private static double PROBABILITY_DROP_SEED = 1/2000d;
    private int actsPassed;
    private int index;
    private GreenfootImage[] animation = new GreenfootImage[3];
    private GreenfootImage[] noBerries = new GreenfootImage[3];
    /**
     * Act - do whatever the BushTile wants to do. This method is called whenever
     * the 'Act' or 'Run' button gets pressed in the environment.
     */
    public BushTile() {
        super(new GreenfootImage("tile_berries.png"));
        heightLevel = 1;
        actsPassed = 0;
        index = 0;
        berriesAvailable = true;
        animation = Utility.createAnimation("images/tile_berries/tile_berries", ".png", 3, 48, 48);
        noBerries = Utility.createAnimation("images/tile_berries_empty/tile_berries_empty", ".png", 3, 48, 48);
    }
    public void act()
    {
        if (!timeFlowing) return;
        growBerries();
        actsPassed++;
        animate();
        if (shouldDropSeed()) {
            dropSeed();
        }
    }
    /**
     * Eat the berrise in this BushTile. Returns the amount of berries eaten.
     * @param value How much berries to eat.
     * @return The amount of berries consumed.
     */
    public int nibble(int value) {
        if (!berriesAvailable) {
            return 0;
        }
        else if (berryAmount < value) { // Not enough berries to eat... Return what was remaining.
            int currentAmount = berryAmount; // This is how much was eaten.
            berryAmount = 0;
            berriesAvailable = false;
            return currentAmount;
        }
        berryAmount = Math.max(0, berryAmount-value); 
        return value; // Enough berries, return amount eaten.
    }
    /**
     * Grow berries.
     */
    private void growBerries() {
        berryAmount = Math.min(MAX_BERRIES, berryAmount+berryGrowSpeed);
        
        if (!berriesAvailable && berryAmount >= 350) {
            berriesAvailable = true;
            setTile(new GreenfootImage("tile_berries.png"));
        }
    }
    /**
     * Determine if this BushTile should attempt to drop its seeds on neighbouring grass tiles.
     */
    private boolean shouldDropSeed() {
        return Greenfoot.getRandomNumber((int) Math.round(1/PROBABILITY_DROP_SEED)) == 0;
    }
    /**
     * Attempt to drop a BushTile seed onto a neighboruing GrassTile.
     */
    private void dropSeed() {
        // Get adjacent tiles as an array
        ArrayList<Tile> neighbours = Board.getNeighbouringTiles(tilePosition);
        
        Collections.shuffle(neighbours);
        // Iterate through for eligble tiles on the array.
        for (Tile t : neighbours) {
            if (t instanceof GrassTile) {
                GrassTile g = (GrassTile) t;
                g.setSeed(new TreeTile(), getGrowTime());
                break;
            }
        }
    }
    /**
     * Calculates a random time to for a BushTile to grow from a seed.
     */
    public static int getGrowTime() {
        return GROW_TIME_MIN + Greenfoot.getRandomNumber(GROW_TIME_MAX - GROW_TIME_MIN);
    }
    /**
     * Return whether this BushTile has berries availale to eat. This does not return true if berries != 0.
     */
    public boolean berriesAvailable() {
        return berriesAvailable;
    }
    /**
     * Returns the amount of berries that this BushTile holds.
     */
    public int getBerryAmount() {
        return berryAmount;
    }
    
    public String toString() {
        return "BushTile";
    }
    
    public void animate()
    {
        if(actsPassed%30 == 0)
        {
            if(berriesAvailable)
            {
                setTile(new GreenfootImage(animation[index]));
            }
            else
            {
                setTile(new GreenfootImage(noBerries[index]));
            }
            
            index = (index+1)%(animation.length); 
        }
    }
}
