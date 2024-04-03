import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)
import java.util.ArrayList;
import java.util.Collections;
/**
 * <p>The Board is a class that is used to manage the tilemap for the World. It represents the map as a 
 * 2D array, and is the thing that manages building and adding each tile to the World assigned to the Board class
 * as well as other utilities, such as finding the Tile coordinate depending on a real coordinate.<p>
 * 
 * <p>Boards should created using loadBoard().</p>
 * 
 * <p>Boards can also be "exported" by using printBoardString(). By creating a Board based on that special String,
 * the Board can remake itself based on that String.</p>
 * 
 * <p>Also, if you're wondering why everything is static, me in my infinite Wisdom (that is, sleep deprivity) couldn't think of a
 * easy solution to access a Board reference stored in a World. This way, you can <i>always</i> access this Board, no matter what
 * World you are currently using!!!1!!1!!</p>
 * 
 * @author Freeman Wang 
 * @version 2023-03-31
 */
public class Board
{
    private static Tile[][] map = new Tile[0][0]; // make map not null so destroyBoard() will not error on first run.
    private static NodeGrid nodeGrid;
    private static int tileSize;  
    private static World w;
    private static int width, height;
    /**
     * Act - do whatever the Board wants to do. This method is called whenever
     * the 'Act' or 'Run' button gets pressed in the environment.
     */
    private Board() {}
    /**
     * Creates a Board, given the Tile size, and the World the Board will exist in. The
     * length and width of the Board will be determined automatically.
     * @param World The World to draw the Board on.
     * @param tileSize The side length of each tile.
     */
    public static void loadBoard(World w, int tileSize) {
        loadBoard(w, w.getWidth()/tileSize, w.getHeight()/tileSize, tileSize);
    }
    /**
     * Creates a Board, given the dimensions (in terms of Tiles) and how large the Tiles should be.
     * @param width The width, in Tiles, the Board should be.
     * @param World The World to draw the Board on.
     * @param height The height, in Tiles, the board should be.
     */
    public static void loadBoard(World w, int width, int height, int tileSize) {
        destroyBoard(); // clear previous board, if exists
        
        map = new Tile[height][width];
        
        nodeGrid = new NodeGrid(width, height, tileSize);
        
        Board.width = width;
        Board.height = height;
        Board.tileSize = tileSize;
        Tile.setSize(tileSize);
        Board.w = w;
        initBoard(); // initialize Board
        
        drawBoard(); // draw board on world
    }
    /**
     * Creates a Board given its buildString. You should only use the String that is outputed when you
     * run the printBuildString() method.
     * @see printBuildString();
     * @param World The World to draw the Board on.
     * @param buildString The build string to follow when constructing the board.
     */
    public static void loadBoard(World w, String buildString) {        
        destroyBoard(); // clear previous board, if exists
        
        String[] chunks = buildString.split("~");
        Board.width = Integer.parseInt(chunks[0]);
        Board.height = Integer.parseInt(chunks[1]);
        map = new Tile[height][width];
        tileSize = Integer.parseInt(chunks[2]);
        Tile.setSize(tileSize);
        nodeGrid = new NodeGrid(width, height, tileSize);
        initBoard(chunks[3]);
        
        drawBoard(); // draw board on world
    }
    /**
     * Swaps the World the Board should be in
     * @param w The new World for this World
     */
    public static void setWorld(World w){
        destroyBoard(); // clear previous board, if exists
        Board.w = w;
        drawBoard(); // draw board on the new world
    }
    /**
     * Removes all Tiles from the World, essentially destroying the Board. However,
     * references to each tile is still kept internally within Board, so it can be re-added by running
     * drawBoard()
     * @see drawBoard()
     */
    public static void destroyBoard() {
        // If map is null means Board not initialized. Nothing to remove. Expected to occur when loading the first board, after compliing.
        if (map == null) {
            System.out.println("warn: Attempted to destroy board before creating one. This is fine if this warning only appears once after compilation.");
            return; 
        }
        for (Tile[] row : map) {
            for (Tile t : row) {
                w.removeObject(t);
            }
        }
    }
    /**
     * Draws the Board onto the World that Board is assigned.
     */
    public static void drawBoard() {
        if (w == null) { // Can't add anything to a null world...
            System.out.println("err: Attempted to draw board when no World exists!");
            return;
        }
        destroyBoard(); // Just to be safe, remove the previous Board from the World
        for (int i = 0; i < map.length; i++) {
            for (int j = 0; j < map[i].length; j++) {
                w.addObject(map[i][j],(j)*tileSize+tileSize/2, i*tileSize+tileSize/2);
            }
        }
    }
    /**
     * Initializes (builds) the map based on the given String.
     * @param boardString The String representing a Board, given from printBuildString().
     */
    private static void initBoard(String boardString) {
        int index = 0;
        for (int i = 0; i < map.length; i++) {
            for (int j = 0; j < map[i].length; j++) {
                Tile t = charToTile(boardString.charAt(index++));
                map[i][j] = t;
            }
        }
    }
    /**
     * Initializes (builds) the map by filling it in with EmptyTiles.
     */
    private static void initBoard() {
        for (int i = 0; i < map.length; i++) {
            for (int j = 0; j < map[i].length; j++) {
                map[i][j] = new EmptyTile();
            }
        }
    }
    /**
     * Given a position, <strong>in terms of Tiles</strong>, assign that specific Tile present in the Board as the given Tile. Please note that
     * this <strong>does not</strong> manage adding the Tile to the World. It only handles modifying the 2D array representation.
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
    private static Tile getTile(int x, int y) {
        if (x < 0 || x >= map[0].length) return null;
        else if (y < 0 || y >= map.length) return null;
        return map[y][x];
    }
    /**
     * Temporray
     */
    public static Node getNodeWithRealPosition(Vector realPosition) {
        return nodeGrid.getNode(nodeGrid.getNodePosition(realPosition));
    }
    /**
     * Given a position relative to the World, retorn the Tile that the position is residing in.
     * @param realPosition The position in terms of pixels.
     * @return The Tile at the given position.
     */
    public static Tile getTile(Vector realPosition) {
        int tileX = (int) realPosition.getX()/tileSize;
        int tileY = (int) realPosition.getY()/tileSize;
        return getTile(tileX, tileY);
    }
    /**
     * Given a real position, convert it into a position in terms of tiles.
     * @param realPostion The position in the world.
     * @return A position, in terms of tiles.
     */
    public static Vector convertRealToTilePosition(Vector realPosition) {
        int tileX = (int) realPosition.getX()/tileSize;
        int tileY = (int) realPosition.getY()/tileSize;
        return new Vector (tileX, tileY);
    }
    /**
     * Get neighbouring tiles in terms of grid coordinates.
     */
    public static ArrayList<Tile> getNeighbouringTiles(Vector tilePosition) {
        ArrayList<Tile> adjacent = new ArrayList();
        int tileX = (int)Math.round(tilePosition.getX());
        int tileY = (int)Math.round(tilePosition.getY());
        for (int y = -1; y <=1; y++) { // Iterate through possible y values (top row, current row, bottom row)
            for (int x = -1; x <= -1; x++) { // Iterate through possible x vslues (left row, current row, right row)
                if (x == 0 && y == 0) continue;
                
                int checkX = tileX + x;
                int checkY = tileY + y;
                
                Tile t = getTile(checkX, checkY);
                if (t != null) adjacent.add(t);
            }
        }
        return adjacent;
    }
    /**
     * Prints out a String that represents a Board, which can be loaded with loadBoard(String), for easier
     * access in the Terminal.
     */
    public static void printBuildString() {
        System.out.println(getBuildString());
    }
    /**
     * Returns a String that represents the current Board, which can then be loaded.
     * @return the build string that represents this Board,
     */
    public static String getBuildString() {
        String boardString = width + "~" + height + "~" + tileSize + "~";
        for (Tile[] row : map) {
            for (Tile t : row) {
                boardString+=tileToChar(t);
            }
        }
        return boardString;
    }
    /**
     * Converts Tile types into a char representation.
     * <ul>
     *   <li>EmptyTiles are represented as 'e'
     *   <li>GrassTiles are represented as 'g'
     *   <li>TreeTiles are represented as 't'
     *   <li>BushTiles are represented as 'b'
     *   <li>WaterTiles are represented as 'w'
     *   <li>MountainTiles are represented as 'm'
     *  </ul>
     * @param t The Tile to analyze.
     * @return The char representation of the Tile.
     */
    public static char tileToChar(Tile t) {
        if (t instanceof EmptyTile) return 'e';
        else if (t instanceof GrassTile) return 'g';
        else if (t instanceof TreeTile) return 't';
        else if (t instanceof BushTile) return 'b';
        else if (t instanceof WaterTile) return 'w';
        else if (t instanceof MountainTile) return 'm';
        System.out.println("err: given unknown tile type.");
        return '?';
    }
    /**
     * Converts the given char into its corresponding Tile.
     * @param c The char to analyze.
     * @return An instance of that char's corresponding Tile.
     */
    public static Tile charToTile(char c) {
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
            case 'm':
                return new MountainTile();
        }
        System.out.println("err: unexpected char while parsing Build string.");
        return new EmptyTile();
    }
    /**
     * Returns whether the Board is ready for play. That is, there are no EmptyTiles that exist.
     * @return true if there are no EmptyTiles.
     */
    public static boolean isReady(){
        for (Tile[] row : map) {
            for (Tile t : row) {
                if (t instanceof EmptyTile) return false;
            }
        }
        return true;
    }
    /**
     * In terms of world coordinates.
     * @param start The start position, in world coordinates
     * @param end the end position, in world coordinates
     * @param maxTileHeight The maximum tile height to be considered in pathfinding.
     */
    public static ArrayList<Node> findPath(Vector start, Vector end, int maxTileHeight) {
        Node startNode = getNodeWithRealPosition(start);
        Node endNode = getNodeWithRealPosition(end);
        startNode.setWalkable(true); // assume start tile is walkable. how would it be there otherwise?
        endNode.setWalkable(checkIfWalkable(endNode.getX(), endNode.getY(), maxTileHeight)); // end node may or may not be walkable
        return nodeGrid.findPath(startNode, endNode, maxTileHeight);
    }
    /**
     * Given x and y coordinates in terms of the grid, and a maximum height, compare to see if can pathfind over that tile at
     * tat pint
     */
    public static boolean checkIfWalkable(int x, int y, int maxTileHeight) {
        Tile t = getTile(x,y);
        if (t.getHeightLevel() > maxTileHeight) {
            return false;
        }
        return true;
    }
    /**
     * Find a path, given the nodes to start and end on.
     * @param startNode the starting node
     * @param endNode the ending node
     * @param the maximum tile height to be considered in pathfinding.
     */
    public static ArrayList<Node> findPath(Node startNode, Node endNode, int maxTileHeight) {
        return nodeGrid.findPath(startNode, endNode, maxTileHeight);
    }
    /**
     * Given a list of nodes, color them to show that path. Not meant for normal use, only quick debugging when
     * visualization of the path is required. I'm so sorry everyone for my trash code.
     * @param path The path to visualize.
     * @param c The color of the path. Start will always be orange.
     */
    public static void displayPath(ArrayList<Node> path, Color c) {
        if (path == null) {
            System.out.println("warn: attempted to display a path, but path was null");
            return;
        }
        ArrayList<Node> existingNodes = (ArrayList<Node>)w.getObjects(Node.class);
        for (Node node : existingNodes) {
            w.removeObject(node);
        }
        Node start = path.get(0);
        start.setImage(Color.ORANGE, tileSize);
        w.addObject(start, nodeGrid.getTrueX(start.getX()), nodeGrid.getTrueY(start.getY()));
        for (int i = 1; i < path.size(); i++) {
            Node n = path.get(i);
            n.setImage(c, tileSize);
            w.addObject(n, nodeGrid.getTrueX(n.getX()), nodeGrid.getTrueY(n.getY()));
        }
    }
}
