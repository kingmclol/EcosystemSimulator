import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)
import java.util.Collections;
import java.util.ArrayList;
/**
 * <p>TreeTiles are tiles with... trees on them. They have the ability to drop seeds onto nearby GrassTiles.</p>
 * 
 * <p>TreeTiles also have a heightLevel of 2, making it less traversable. Larger animals might struggle walking through these!<p>
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class TreeTile extends Tile
{
    /**
     * Act - do whatever the TreeTile wants to do. This method is called whenever
     * the 'Act' or 'Run' button gets pressed in the environment.
     */
    private static int GROW_TIME_MIN = 600;
    private static int GROW_TIME_MAX = 1600;
    private static double PROBABILITY_DROP_SEED = 1/20000d;
    private static int LIFESPAN_MIN = 3600;
    private static int LIFESPAN_MAX = 10800;
    private int lifespan;
    public TreeTile() {
        super(new GreenfootImage("tile_trees.png"));
        heightLevel = 2;
        lifespan = getLifespan();
    }
    public void act()
    {
        if (!timeFlowing) return;
        if (shouldDropSeed()) {
            dropSeed();
        }
        if (--lifespan <= 0) { // The tree is old and ded now
            replaceMe(new GrassTile());
        }
    }
    private int getLifespan() {
        return LIFESPAN_MIN + Greenfoot.getRandomNumber(LIFESPAN_MAX-LIFESPAN_MIN);
    }
    private boolean shouldDropSeed() {
        return Greenfoot.getRandomNumber((int) Math.round(1/PROBABILITY_DROP_SEED)) == 0;
    }
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
    public static int getGrowTime() {
        return GROW_TIME_MIN + Greenfoot.getRandomNumber(GROW_TIME_MAX - GROW_TIME_MIN);
    }
    
    public String toString() {
        return "TreeTile";
    }
}
