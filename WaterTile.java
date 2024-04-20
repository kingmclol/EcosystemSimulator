import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * <p>WaterTiles are tiles that represent Water. They have the lowest height level of 0, but
 * Animals need to swim, rather than walk, on these tiles. If they aren't careful, they might drown!</p>
 * 
 * @author Freeman Wang
 * @version 2024-04-20
 */
public class WaterTile extends Tile
{
    private int actsPassed = 0;
    private int index = 0;
    private GreenfootImage[] animation = new GreenfootImage[3];
    public WaterTile() {
        super(new GreenfootImage("tile_water.png"));
        heightLevel = 0;
        for(int i = 0; i<3; i++)
        {
            animation[i] = new GreenfootImage("images/tile_water/tile_water" + (i+1) + ".png");
        }
    }
    public void act()
    {
        // Add your action code here.
        if(!timeFlowing) return;
        actsPassed++;
        animate();
        
    }
    
    public String toString() {
        return "WaterTile";
    }
    public void animate()
    {
        if(actsPassed%30 == 0)
        {
            setTile(new GreenfootImage(animation[index]));
            index = (index+1)%(animation.length);
        }
    }
}
