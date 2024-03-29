import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * <p>WaterTiles are tiles that represent Water. They have the lowest height level of 0, but
 * Animals need to swim, rather than walk, on these tiles. If they aren't careful, they might drown!</p>
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class WaterTile extends Tile
{
    /**
     * Act - do whatever the WaterTile wants to do. This method is called whenever
     * the 'Act' or 'Run' button gets pressed in the environment.
     */
    public WaterTile() {
        super(new GreenfootImage("tile_water.png"));
        heightLevel = 0;
    }
    public void act()
    {
        // Add your action code here.
    }
}
