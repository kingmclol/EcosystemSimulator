import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * Write a description of class TreeTile here.
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
    public TreeTile() {
        super(new GreenfootImage("tile_trees.png"));
    }
    public void act()
    {
        if (!timeFlowing) return;
        if (Greenfoot.getRandomNumber(1000) == 0) {
            Tile[] adjacent = Board.getAdjacentTiles(tilePosition);
            for (Tile t : adjacent) {
                if (t instanceof GrassTile) {
                    t.replaceMe(new TreeTile());
                    break;
                }
            }
        }
    }
}
