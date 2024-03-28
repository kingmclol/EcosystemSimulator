import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * Write a description of class GrassTile here.
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
    private int grassAmount = 100;
    private int growSpeed = 1;
    private int maxGrowth = Greenfoot.getRandomNumber(200) + 300;
    public GrassTile() {
        super(new GreenfootImage("tile_grass.png"));
        growSpeed = 1;
    }
    public int nibble(int value) {
        int grassAmountAfter = Math.max(0, grassAmount-value);
        int grassLoss = grassAmount - grassAmountAfter;
        
        grassAmount = grassAmountAfter;
        if (grassAmount <= 0) {
            replaceMe(new WaterTile());
        }
        return grassLoss;
    }
    public void act()
    {
        if (!timeFlowing) return;
        grassAmount += growSpeed;
        if (grassAmount >= maxGrowth) {
            replaceMe(new BushTile());
        }
    }
}
