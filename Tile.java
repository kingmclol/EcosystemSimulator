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
    private static final boolean drawBorders = true;
    protected static boolean timeFlowing = false;
    public static void setTimeFlow(boolean flow) {
        timeFlowing = flow;
    }
    public Tile(GreenfootImage image) {
        setTile(image);
    }
    public Tile(Color c) {
        setTile(c);
    }
    public void addedToWorld(World w) {
        tilePosition = Board.getTilePosition(getPosition());
    }
    public void setTile(Color c) {
        GreenfootImage img = new GreenfootImage(size, size);
        img.setColor(c);
        img.fill();
        setImage(img);
        drawBorder();
    }
    public void setTile(GreenfootImage img) {
        img.scale(size, size);
        setImage(img);
        drawBorder();
    }
    public void drawBorder(){
        if (!drawBorders) return;
        getImage().setColor(Color.BLACK);
        getImage().drawRect(0,0,size, size);
    }
    public Vector getTilePosition() {
        return tilePosition;
    }
    public void replaceMe(Tile t) {
        Board.setTileAt(tilePosition, t);
        getWorld().addObject(t, getX(), getY());
        getWorld().removeObject(this);
    }
}
