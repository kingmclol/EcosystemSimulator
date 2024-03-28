import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)
import java.util.Arrays;
/**
 * <p>The Board is an actor that is used like a data structure to
 * manage the TileMap for the World. It represents the map as a 
 * 2D array, and is the thing that manages building (and adding
 * each tile to the world, as well as other utility, such as finding
 * the Tile coordinate depending on the real coordinate.<p>
 * 
 * <p>Boards should be initialized, then added somewhere inconspicious in the World, such as 0, 0.</p>
 * 
 * <p><strong>There should only be one Board that exists at a time.</strong></p>
 * 
 * <p>Boards can also be "exported" by printing them out. By passing the resulting String output into
 * one of the constructors, the Board can reconstruct itself depending on the String given.</p>
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class Board extends Actor
{
    private static Tile[][] map;
    private static int tileSize;   
    /**
     * Act - do whatever the Board wants to do. This method is called whenever
     * the 'Act' or 'Run' button gets pressed in the environment.
     */
    /**
     * Creates a Board, given the Tile size, and the World the Board will exist in. The
     * length and width of the Board will be determined automatically.
     * @param tileSize The side length of each tile.
     * @param w The world to analyze.
     */
    public Board(int tileSize, World w) {
        this(w.getWidth()/tileSize, w.getHeight()/tileSize, tileSize);
    }
    /**
     * Creates a Board, given the dimensions (in terms of Tiles) and how large the Tiles should be.
     * @param width The width, in Tiles, the Board should be.
     * @param height The height, in Tiles, the board should be.
     */
    public Board(int width, int height, int tileSize) {
        map = new Tile[height][width];
        this.tileSize = tileSize;
        initBoard();
    }
    /**
     * Creates a Board given its buildString. You should only use the String that is outputed when you
     * run Board's toString() method.
     * @see toString();
     * @param buildString The build string to follow when constructing the board.
     */
    public Board (String buildString) {
        String[] chunks = buildString.split("~");
        map = new Tile[Integer.parseInt(chunks[1])][Integer.parseInt(chunks[0])];
        tileSize = Integer.parseInt(chunks[2]);
        initBoard(chunks[3]);
    }
    /**
     * Given a buildString, load it.
     * @param buildString the Board to load.
     */
    public void loadBoard(String buildString) {
        String[] chunks = buildString.split("~");
        map = new Tile[Integer.parseInt(chunks[1])][Integer.parseInt(chunks[0])];
        tileSize = Integer.parseInt(chunks[2]);
        initBoard(chunks[3]);
        drawBoard(getWorld());
    }
    public void addedToWorld(World w) {
        drawBoard(w);
    }
    private void drawBoard(World w) {
        for (int i = 0; i < map.length; i++) {
            for (int j = 0; j < map[i].length; j++) {
                w.addObject(map[i][j],(j)*tileSize+tileSize/2, i*tileSize+tileSize/2);
            }
        }
    }
    private void initBoard(String boardString) {
        int index = 0;
        for (int i = 0; i < map.length; i++) {
            for (int j = 0; j < map[i].length; j++) {
                Tile t = charToTile(boardString.charAt(index++));
                map[i][j] = t;
            }
        }
    }
    private void initBoard() {
        for (int i = 0; i < map.length; i++) {
            for (int j = 0; j < map[i].length; j++) {
                map[i][j] = new EmptyTile();
            }
        }
    }
    /**
     * Given a position, in terms of Tiles, assign that specific index in the Board as the given Tile.
     * @param tilePosition The position of the Tile, in terms of Tiles.
     * @param t The Tile to set in the given Position.
     */
    public static void setTileAt(Vector tilePosition, Tile t) {
        int tileX = (int)Math.round(tilePosition.getX());
        int tileY = (int)Math.round(tilePosition.getY());
        
        map[tileY][tileX] = t;
    }
    /**
     * Given the x and y coordinates, in Tiles, return the Tile that resides at that point.
     * @param x The x coordinate of the Tile on the Board.
     * @param y The y coordinate of the Tile on the Board.
     * @return The Tile that resides at that coordinate. Returns <code>null</code> if out of range.
     */
    public Tile getTile(int x, int y) {
        if (x < 0 || x >= map[0].length) return null;
        else if (y < 0 || y >= map.length) return null;
        return map[y][x];
    }
    /**
     * Converts a real Position into a position in terms of Tiles.
     * @param realPosition The position in terms of pixels.
     * @return The position in terms of Tiles.
     */
    public static Vector getTilePosition(Vector realPosition) {
        int tileX = (int)Math.round((realPosition.getX()-tileSize/2)/tileSize);
        int tileY = (int)Math.round((realPosition.getY()-tileSize/2)/tileSize);
        return new Vector(tileX, tileY);
    }
    /**
     * <p>Returns the adjacent Tiles from a given position in terms of Tiles.</p>
     * <p>The returned array is read as follows:</p>
     * <ul>
     *   <li>Index 0 is the top Tile.
     *   <li>Index 1 is the right Tile.
     *   <li>Index 2 is the bottom Tile.
     *   <li>Index 3 is the left Tile.
     *  </ul>
     *  <p>May also return null, if that given tile does not exist.</p>
     *  @param tilePosition The position, in terms of Tiles, to check around.
     *  @return An array that stores the adjacent Tiles.
     */
    public Tile[] getAdjacentTiles(Vector tilePosition) {
        int tileX = (int)Math.round(tilePosition.getX());
        int tileY = (int)Math.round(tilePosition.getY());
        Tile[] adjacentTiles = new Tile[4];
        adjacentTiles[0] = getTile(tileX, tileY-1);
        adjacentTiles[1] = getTile(tileX+1, tileY);
        adjacentTiles[2] = getTile(tileX, tileY+1);
        adjacentTiles[3] = getTile(tileX-1, tileY);
        return adjacentTiles;
    }
    /**
     * Runs when one prints out a Board. Returns the building String used to construct Boards.
     * @return A string representation of this Board that can be used to reconstruct this board.
     */
    public String toString() {
        int width = map[0].length;
        int height = map.length;
        String boardString = width + "~" + height + "~" + tileSize + "~";
        for (Tile[] row : map) {
            for (Tile t : row) {
                boardString+=tileToChar(t);
            }
        }
        return boardString;
    }
    private static char tileToChar(Tile t) {
        if (t instanceof EmptyTile) return 'e';
        else if (t instanceof GrassTile) return 'g';
        else if (t instanceof TreeTile) return 't';
        else if (t instanceof BushTile) return 'b';
        else if (t instanceof WaterTile) return 'w';
        
        System.out.println("err: given unknown tile type?");
        return '?';
    }
    private static Tile charToTile(char c) {
        switch (c) {
            case 'e':
                return new EmptyTile();
            case 'g':
                return new GrassTile();
            case 't':
                return new TreeTile();
            case 'b':
                return new BushTile();
            case 'w':
                return new WaterTile();
        }
        System.out.println("err: unexpected char while parsing Build string.");
        return null;
    }
    /**
     * Returns whether the Board is ready for play. That is, there are no EmptyTiles that exist.
     * @return true if there are no EmptyTiles.
     */
    public boolean isReady(){
        for (Tile[] row : map) {
            for (Tile t : row) {
                if (t instanceof EmptyTile) return false;
            }
        }
        return true;
    }
    // public static void drawBorders(boolean visible) {
        // if (!visible) return;
        // for (Tile[] row : map) {
            // for (Tile t : row) {
                // t.drawBorder();
            // }
        // }
    // }
}
