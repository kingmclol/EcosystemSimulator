import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * <p>GrassTiles are tiles with grass, perfect for any small herbivores to eat. GrassTiles can also have seeds
 * planted within them, and once those seeds mature, the GrassTile can be replaced with another type of Tile.</p>
 * 
 * <p>In addtion to having seeds given from other Tiles, the GrassTile may self-seed itself at a low chance.</p>
 * 
 * <p>GrassTiles have a heightLevel of 0.</p>
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class GrassTile extends Tile
{
    /**
     * Act - do whatever the GrassTile wants to do. This method is called whenever
     * the 'Act' or 'Run' button gets pressed in the environment.
     */
    private static final int MAX_GRASS = 1000;
    private int actPassed = 0;
    private int grassAmount = 800;
    private int growSpeed;

    private boolean grassAvailable;

    private static double PROBABILITY_SEED_SELF = 1/50000d;
    private int growAct, growTime;
    private Tile mySeed = null;
    private GreenfootImage[] animation = new GreenfootImage[2];
    private GreenfootImage[] noGrass = new GreenfootImage[2];
    private int index = 0;

    public GrassTile() {
        super(new GreenfootImage("tile_grass.png"));
        growSpeed = 1;
        heightLevel = 1;
        grassAvailable = true;
        for(int i = 0; i<animation.length; i++)
        {
            animation[i] = new GreenfootImage("images/tile_grass/tile_grass" + (i+1) + ".png");
            noGrass[i] = new GreenfootImage("images/tile_grass_empty/tile_grass_empty" + (i+1) + ".png");
            animation[i].scale(48,48);
        }
    }

    /**
     * Eat the grass in this GrassTile. Returns the amount of grass eaten.
     * @param value How much grass to eat.
     * @return The amount of grass consumed.
     */
    public int nibble(int value) {
        if (grassAmount < value) { // Not enough grass to eat... Return what was remaining.
            grassAmount = 0;
            grassAvailable = false;
            setTile(new GreenfootImage("tile_grass_empty.png"));
            return grassAmount;
        }
        grassAmount = Math.max(0, grassAmount-value); 
        return value; // Enough grass, return amount eaten.
    }

    public void act()
    {
        
        if (!timeFlowing) return;
        actPassed++;
        animate();
        grow();
        
        if(shouldSelfSeed()) {
            if (Greenfoot.getRandomNumber(2) == 0) {
                setSeed(new BushTile(), BushTile.getGrowTime());
            }
            else setSeed(new TreeTile(), TreeTile.getGrowTime());
        }
    }

    private boolean shouldSelfSeed() {
        return mySeed == null && Greenfoot.getRandomNumber((int)Math.round(1/PROBABILITY_SEED_SELF)) == 0;
    }

    /**
     * Set the "seed" of this GrassTile to a given tile. Please do stuff that makes sense.
     * @param t The "seed" tile that the GrassTile should turn into after a short delay.
     * @param growTime The number of acts it takes for this GrassTile to finish growing its "seed."
     */
    public void setSeed(Tile t, int growTime) {
        if (mySeed != null) return; // Only one seed at a time.
        mySeed = t;
        growAct = 0;
        this.growTime = growTime;
    }

    /**
     * Returns the amount of grass current grass tile has
     */
    public int getGrassAmount() {
        return grassAmount;
    }

    /**
     * Returns whether the grass has availalbe grass.
     */
    public boolean grassAvailable() {
        return grassAvailable;
    }

    /**
     * Grows grass and the seed, if it exists.
     */
    private void grow() {
        grassAmount = Math.min(MAX_GRASS, grassAmount+growSpeed);
        if (!grassAvailable && grassAmount >= 500) { // If regenning grass and now has enough again
            grassAvailable = true;
            setTile(new GreenfootImage("tile_grass.png"));
        }

        if (mySeed != null) {
            if (++growAct >= growTime) { // Seed is ready.
                // To prevent trapping any animals in a tile (e.g. tree tile spawns over a rabbit)
                // Currently, it only checks for any ANIMALS. dead animals count too, so still wouldn't spawn the seed.
                if (!isTouching(Animal.class)) {
                    replaceMe(mySeed);
                }
            }
        }
    }

    public void animate()
    {
        if(actPassed%30 == 0)
        {
            if(grassAvailable)
            {
                setTile(new GreenfootImage(animation[index]));
            }
            else
            {
                setTile(new GreenfootImage(noGrass[index]));
            }
            
            index = (index+1)%(animation.length); 
        }

    }

    public String toString() {
        return "GrassTile";
    }
}
