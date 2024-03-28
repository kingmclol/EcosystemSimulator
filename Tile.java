import greenfoot.*;
/**
 * Write a description of class Tile here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public abstract class Tile extends SuperActor
{
    public static final int size = 64;
    protected Vector tilePosition;
    private static final boolean drawBorders = false;
    public Tile(GreenfootImage image) {
        setTile(image);
    }
    public void addedToWorld(World w) {
        tilePosition = Board.getTilePosition(getPosition());
    }
    public Tile(boolean a) {
        setTile(Color.GRAY);
    }
    public Tile() {
        switch(Greenfoot.getRandomNumber(4)) {
            case 0:
                //setTile(Color.BLUE);
                setTile(new GreenfootImage("tile_water.png"));
                break;
            case 1:
                //setTile(Color.RED);
                setTile(new GreenfootImage("tile_grass.png"));
                break;
            case 2:
                //setTile(Color.GREEN);
                setTile(new GreenfootImage("tile_berries.png"));
                break;
            case 3:
                setTile(new GreenfootImage("tile_trees.png"));
                break;
        }
    }
    public void setTile(Color c) {
        GreenfootImage img = new GreenfootImage(size, size);
        img.setColor(c);
        img.fill();
        setImage(img);
        if (drawBorders) drawBorder();
    }
    public void setTile(GreenfootImage img) {
        img.scale(size, size);
        setImage(img);
        if (drawBorders) drawBorder();
    }
    public void drawBorder(){
        getImage().setColor(Color.BLACK);
        getImage().drawRect(0,0,size, size);
    }
    public Vector getTilePosition() {
        return tilePosition;
    }
    public void replaceMe(Tile t) {
        getWorld().addObject(t, getX(), getY());
        getWorld().removeObject(this);
    }
}
