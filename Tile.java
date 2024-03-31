import greenfoot.*;
/**
 * Tiles are the basic building block of the Board (or more accurately, the World). Tiles are simply a square
 * Actor with an image, but can have more specific behaviours in their subclasses.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public abstract class Tile extends SuperActor
{
    private static int size;
    protected GreenfootImage[] animationImages;
    protected int animationIndex = 0;
    /**
     * The height level of a tile is basically how tall it is. Used to determine
     * Whether animals should be able to walk through it. 0 is sea level.
     */
    protected int heightLevel;
    /**
     * The tilePosition is the current Position of this specific tile on the Board.
     */
    protected Vector tilePosition;
    private static final boolean drawBorders = true;
    /**
     * timeFlowing determines whether Tiles should act on their own.
     */
    protected static boolean timeFlowing = false;
    // public static void setBorderVisibility(boolean visible) {
        // drawBorders = visible;
        // Board.drawBorders(visible);
    // }
    public int getHeightLevel() {
        return heightLevel;
    }
    /**
     * Sets the tileSize for Tiles to adhere to.
     * @param newSize The new Size for tiles.
     */
    public static void setSize(int newSize) {
        size = newSize;
    }
    /**
     * Sets whether time should "flow" for tiles. In simpler terms, sets whether tiles should be able to act.
     */
    public static void setTimeFlow(boolean flow) {
        timeFlowing = flow;
    }
    /**
     * Creates a Tile, given an image.
     */
    public Tile(GreenfootImage image) {
        setTile(image);
    }
    /**
     * Creates a Tile, given a Color to use.
     */
    public Tile(Color c) {
        setTile(c);
    }
    public void addedToWorld(World w) {
        tilePosition = Board.getTilePosition(getPosition());
    }
    /**
     * Sets the Tile's appearance based on the given Color.
     * @param color The color for this Tile to take.
     */
    public void setTile(Color c) {
        GreenfootImage img = new GreenfootImage(size, size);
        img.setColor(c);
        img.fill();
        setImage(img);
        drawBorder();
    }
    /**
     * Sets the Tile's appearance based on the given image.
     * @param img The image for this Tile to take.
     */
    public void setTile(GreenfootImage img) {
        img.scale(size, size);
        setImage(img);
        drawBorder();
    }
    /**
     * Draws the border (edge) for the Tile.
     */
    public void drawBorder(){
        if (!drawBorders) return;
        getImage().setColor(Color.BLACK);
        getImage().drawRect(0,0,size, size);
    }
    /**
     * Returns the position of this Tile on the Board.
     * @return A Vector representing the coordinates of this Tile on the Board.
     */
    public Vector getTilePosition() {
        return tilePosition;
    }
    /**
     * Replaces this Tile with the given Tile. Will updated the Board automagically.
     * @param t The Tile to replace this Tile with.
     */
    public void replaceMe(Tile t) {
        Board.setTileAt(tilePosition, t);
        getWorld().addObject(t, getX(), getY());
        getWorld().removeObject(this);
    }
}
 

