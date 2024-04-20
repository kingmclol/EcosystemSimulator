import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)
import java.util.Collections;
import java.util.ArrayList;
/**
 * <p>TreeTiles are tiles with... trees on them. They have the ability to drop seeds onto nearby GrassTiles.</p>
 * 
 * <p>TreeTiles also have a heightLevel of 2, making it less traversable. Some might struggle walking through these!<p>
 * 
 * @author Freeman Wang
 * @version 2024-04-13
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
    private static int LIFESPAN_MIN = 3600; // 60 seconds
    private static int LIFESPAN_MAX = 10800; // 3 minutes
    private int lifespan;
    private boolean aboutToDie;
    //Animation:
    private int index = 0;
    private int actsPassed = 0;
    private GreenfootImage[] animationNormal = new GreenfootImage[3];
    private GreenfootImage[] animationDying = new GreenfootImage[3];
    public TreeTile() {
        super(new GreenfootImage("tile_trees.png"));
        heightLevel = 2;
        lifespan = getLifespan();
        aboutToDie = false;
        index = Greenfoot.getRandomNumber(3);
        for(int i = 0; i<3; i++)
        {
            animationNormal[i] = new GreenfootImage("images/tile_trees/tile_trees" + (i+1) + ".png");
            animationDying[i] = new GreenfootImage("images/tile_trees_dying/tile_trees_dying" + (i + 1) + ".png");
        }
    }
    public void act()
    {
        if (!timeFlowing) return;
        actsPassed++;
        lifespan--;
        animate();
        if (shouldDropSeed()) {
            dropSeed();
        }
        if (!aboutToDie && lifespan <= 600) { // 10 seconds before dying...
            aboutToDie = true; // I am about to die (oh no)
        }
        else if (lifespan <= 0) { // The tree is old and ded now
            replaceMe(new GrassTile());
        }
    }
    /**
     * Calculate the lifespan for this tree tile
     * @return the lifespan, in acts
     */
    private int getLifespan() {
        return LIFESPAN_MIN + Greenfoot.getRandomNumber(LIFESPAN_MAX-LIFESPAN_MIN);
    }
    /**
     * Determine whether the tree tile should drop a seed.
     */
    private boolean shouldDropSeed() {
        double probability = (!isRaining) ? PROBABILITY_DROP_SEED: PROBABILITY_DROP_SEED * 2;
        return Greenfoot.getRandomNumber((int) Math.round(1/probability)) == 0;
    }
    /**
     * Sets a seed onto a neighbouring grass tile
     */
    private void dropSeed() {
        // Get adjacent tiles as an arrayList
        ArrayList<Tile> neighbours = Board.getNeighbouringTiles(tilePosition);
        // randomize
        Collections.shuffle(neighbours);
        // Iterate through for eligble tiles on the List.
        for (Tile t : neighbours) {
            if (t instanceof GrassTile) {
                GrassTile g = (GrassTile) t;
                g.setSeed(new TreeTile(), getGrowTime());
                break;
            }
        }
    }
    /**
     * Calculate the time to grow a seed, in acts
     * @return how long a seed should take to grow
     */
    public static int getGrowTime() {
        return GROW_TIME_MIN + Greenfoot.getRandomNumber(GROW_TIME_MAX - GROW_TIME_MIN);
    }
    
    public String toString() {
        return "TreeTile";
    }
    
    public void animate()
    {
        if(actsPassed%30 == 0)
        {
            if (!aboutToDie) setTile(new GreenfootImage(animationNormal[index])); // normal
            else setTile(new GreenfootImage(animationDying[index])); // dying
            // If about to die (playing dying ver. of animation) modulo by that length; else modulo by normal animation length
            // actually, they're both three so that was unnecessary it seems
            index = (index+1)%(aboutToDie ? animationDying.length : animationNormal.length);
        }
    }
}
