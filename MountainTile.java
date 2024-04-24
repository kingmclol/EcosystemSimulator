import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * The MountainTile is a tile that solely exists as an obstacle. Does not
 * do anything else, really. Has a heightLevel of 3.
 * 
 * @author Freeman Wang
 * @version 2024-04-20
 */
public class MountainTile extends Tile
{
    public MountainTile() {
        super(new GreenfootImage("tile_mountain.png"));
        heightLevel = 3;
    }
    public void act()
    {
        // Add your action code here.
    }
    public void animate()
    {
        return;
    }
}
