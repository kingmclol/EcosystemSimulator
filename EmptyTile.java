import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * <p>EmptyTiles are essentially null Tiles. They do not represent anything other than an uninitialized Tile, and should not
 * be present in the map where the game plays on.</p>
 * 
 * <p>They have a heightLevel of -1, which is invalid.</p>
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class EmptyTile extends Tile
{
    /**
     * Act - do whatever the EmptyTile wants to do. This method is called whenever
     * the 'Act' or 'Run' button gets pressed in the environment.
     */
    public EmptyTile() {
        super(Color.GRAY);
        heightLevel = -1;
    }
    public void act()
    {
        // Add your action code here.
    }
}
