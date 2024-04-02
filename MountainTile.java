import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * The MountainTile is a tile that solely exists as an obstacle. Does not
 * do anything else, really. Has a heightLevel of 3.
 */
public class MountainTile extends Tile
{
    /**
     * Act - do whatever the MountainTile wants to do. This method is called whenever
     * the 'Act' or 'Run' button gets pressed in the environment.
     */
    public MountainTile() {
        super(Color.BLACK);
        heightLevel = 3;
    }
    public void act()
    {
        // Add your action code here.
    }
}
