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
 * @version 2023-03-29
 */
public class Board
{
    private static Tile[][] map = new Tile[0][0]; // make map not null so destroyBoard() will not error on first run.
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
     * Given a position, in terms of Tiles, assign that specific index in the Board as the given Tile. Please note that
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
    public static Tile getTile(int x, int y) {
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
    public static ArrayList<Node> getNeighbours(Node n) {
        ArrayList<Node> neighbours = new ArrayList();
        int nodeX = (int)Math.round(n.getPosition().getX());
        int nodeY = (int)Math.round(n.getPosition().getY());
        for (int y = -1; y <=1; y++) { // Iterate through possible y values (top row, current row, bottom row)
            for (int x = -1; x <= 1; x++) { // Iterate through possible x vslues (left row, current row, right row)
                if (x == 0 && y == 0) continue; // If center (current tile), skip.
                
                int checkX = nodeX + x;
                int checkY = nodeY + y;
                
                // If the coordinate is within the bounds of the grid,
                if (checkX >= 0 && checkX < width && checkY >= 0 && checkY < height) {
                    // THIS SHOULD NOT GIVE NEW NODES; USE AN ALTERNATIVE WAY TO STORE NODES SOMEWHERE
                    // AS LONG AS I DONT RETURN ANY NEW NODES SHOULD BE MUCH BETTER
                    neighbours.add(new Node(true, new Vector(checkX, checkY))); // add it to be returned
                }
            }
        }
        return neighbours;
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
    /**
     * Converts Tile types into a char representation.
     * <ul>
     *   <li>EmptyTiles are represented as 'e'
     *   <li>GrassTiles are represented as 'g'
     *   <li>TreeTiles are represented as 't'
     *   <li>BushTiles are represented as 'b'
     *   <li>WaterTiles are represented as 'w'
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
        
        System.out.println("err: given unknown tile type.");
        return '?';
    }
    /**
     * Converts the given char into its corresponding Tile.
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
     * A* pathfinding algorithm.
     * @param startPos the starting position to path from, relative to grid.
     * @param targetPost the ending position to be at, relative to grid
     */
    public static ArrayList<Node> findPath(Vector startPos, Vector targetPos) {
        // Create nodes based on the given positions.
        Node startNode = new Node(true, startPos);
        Node endNode = new Node(true, targetPos);
        
        // Create two lists.
        ArrayList<Node> openSet = new ArrayList(); // Holds unexplored, accessbile nodes?
        ArrayList<Node> closedSet = new ArrayList(); // Holds explored nodes?
        
        openSet.add(startNode); // Begin with exploring aroud the start node.
        
        while (openSet.size() > 0) { // While there are still nodes to explore,
            Node currentNode = openSet.get(0); // Get the 0th node.
            for (int i = 1; i < openSet.size(); i++) { // Iterate through the other open nodes.
                Node n = openSet.get(i);
                // If n is more efficent to get to the ending node, then use that node instead.
                if (n.fCost() < currentNode.fCost() || n.fCost() == currentNode.fCost() && n.hCost() < currentNode.hCost()) {
                    currentNode = openSet.get(i);
                }
            }
            
            // Remove the current node from the open set (currently is exploring from it)
            openSet.remove(currentNode);
            closedSet.add(currentNode); // Add the node into the closed set (since after were done we have finished exploring
            
            // if (currentNode == endNode) { // uses references instead, MUCH BETTER
            if (currentNode.equals(endNode)) { // If the current node is equal to the end node (path found);
                return retracePath(startNode, currentNode); // Return the path found.
            }
            
            for (Node neighbour : getNeighbours(currentNode)) { // For each adjacent tile to the current node,
                if (!neighbour.isWalkable() || closedSet.contains(neighbour)) { // If this neighbouring tile is not walkable, or has been cheked already,
                    continue; // skip.
                }
                // Calculate the cost to get to the neighbouring node.
                int moveCostToNeighbour = currentNode.gCost() + Node.getDistance(currentNode, neighbour);
                
                // If the move cost to the neighbour is more efficient, and it is not already existing.
                // THERE MAY BE AN ERROR HERE. BECAUSE I RETURN NEW NODES FROM getNeighbours(), neighbour.gCost() MAY NOT HAVE ANYTHING.
                // AGAIN, THIS CAN BE FIXED BY HOLDING A 2D ARRAY OF NODES, AND RETURNING THOSE REFERENCES INSTEAD OF NEWING ONE.
                if (moveCostToNeighbour < neighbour.gCost() || !openSet.contains(neighbour)) {
                    neighbour.setGCost(moveCostToNeighbour);
                    neighbour.setHCost(Node.getDistance(neighbour, endNode));
                    neighbour.setParent(currentNode);
                    
                    if (!openSet.contains(neighbour)) {
                        openSet.add(neighbour);
                    }
                }
            }
        }
        return null;
    } 
    /**
     * Retraces the path taken, given a starting node and ending node.
     * @param startNode the starting node
     * @param endNode the ending node.
     * @return The actual path from start node to end node, in order.
     */
    private static ArrayList<Node> retracePath(Node startNode, Node endNode) {
        ArrayList<Node> path = new ArrayList<Node>();
        Node currentNode = endNode; // Start with the ending node.
        path.add(currentNode); // Add it to the path.
        while (!currentNode.equals(startNode)) { // While the current node is not the starting node (still need to backtrack). Same issue, currentNode != startNode is much better
            currentNode = currentNode.getParent(); // get the parent of the current node, and
            path.add(currentNode); // add it to the pat.
        }
        Collections.reverse(path); // Reverse the path, so it goes from start -> end instead of end -> start.
        return path;
    }
    /**
     * Given a list of nodes, color them to show that path.
     */
    public static void displayPath(ArrayList<Node> path, Color c) {
        Tile start = getTile(path.get(0).getX(), path.get(0).getY());
        start.setTile(Color.ORANGE);
        for (int i = 1; i < path.size(); i++) {
            Node n = path.get(i);
            Tile t = getTile(n.getX(), n.getY());
            t.setTile(c);
        }
    }
}
