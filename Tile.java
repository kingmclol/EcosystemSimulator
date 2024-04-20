import greenfoot.*;
/**
 * Tiles are the basic building block of the Board (or more accurately, the World). Tiles are simply a square
 * Actor with an image, but can have more specific behaviours in their subclasses.
 * 
 * @author Freeman Wang
 * @version 2024-04-20
 */
public abstract class Tile extends SuperActor
{
    protected static boolean isRaining = false;
    
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
    private static final boolean drawBorders = false; // determine whether to draw borders on the tiles.
    /**
     * timeFlowing determines whether Tiles should act on their own.
     */
    protected static boolean timeFlowing = false;
    // public static void setBorderVisibility(boolean visible) {
        // drawBorders = visible;
        // Board.drawBorders(visible);
    // }
    /**
     * Creates a Tile, given an image.
     * @param image The image for the tile to take on.
     */
    public Tile(GreenfootImage image) {
        setTile(image);
    }
    /**
     * Creates a Tile, given a Color to use. For debugging, but unused now
     * @param c The color for the tile to take on.
     */
    public Tile(Color c) {
        setTile(c);
    }
    /**
     * Set whether it is currently raining in the world. I know this is weird place to have this but whatever.
     * @param raining whether it is raining or not.
     */
    public static void setRaining(boolean raining) {
        isRaining = raining; 
    }
    /**
     * Returns the height level of this tile, for determing whether an animal can walk over this tile or not
     * @return the heightLevel of this tile.
     */
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
    public void addedToWorld(World w) {
        tilePosition = Board.convertRealToTilePosition(getPosition());
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
    protected abstract void animate();
    /**
     * Draws the border (edge) for the Tile. Only does stuff if drawBorders is on.
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
    /**
     * Sets the image of this tile as the given transparency;
     * @param x Transparency to set to, [0-255]
     */
    public void setTransparency(int x) {
        getImage().setTransparency(x);
    }
}
 

