import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)
import java.util.ArrayList;
import java.util.Collections;
/**
 * <p>The NodeGrid is basically a 2D array of Nodes. Solely used for pathfinding. Please do not ask me how it works.</p>
 * 
 * <p>I tried my best.</p>
 * 
 * @author Freeman Wang 
 * @version 2023-03-31
 */
public class NodeGrid
{
    private Node[][] grid;
    private int width, height;
    private int tileSize;
    /**
     * Creates a NodeGrid, for pathfinding.
     * @param width The width of the grid
     * @param height the height of the grid
     * @param tileSize the size of each tile.
     */
    public NodeGrid(int width, int height, int tileSize) {
        grid = new Node[height][width];
        this.width = width;
        this.height = height;
        this.tileSize = tileSize;
        initGrid();
    }
    /**
     * Returns the true X cooridinate, should the node be in the World, given a X coordinate in terms of the grid.
     */
    public int getTrueX(int nodeX) {
        // half tile size + tileSize * nodeX
        // | a | b | c | d |
        return tileSize/2 + tileSize*nodeX;
    }
    /**
     * Returns the true Y coordinate, should the node be in the World, given a Y coordinate in terms of the grid.
     */
    public int getTrueY(int nodeY) {
        return tileSize/2 + tileSize * nodeY;
    }
    /**
     * Initialize the grid with some basic nodes
     */
    private void initGrid() {
        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                grid[y][x] = new Node(x, y);
            }
        }
    }
    /**
     * Given a position in terms of the grid, return the node at that point.
     */
    public Node getNode(Vector position) {
        int x = (int)Math.round(position.getX());
        int y = (int)Math.round(position.getY());
        return getNode(x, y);
    }
    /**
     * Given a position in terms of the World, return the node at that point.
     * @param realPosition The position in terms of pixels.
     * @return The position in terms of Nodes.
     */
    public Vector getNodePosition(Vector realPosition) {
        int x = (int)Math.round((realPosition.getX()-tileSize/2)/tileSize);
        int y = (int)Math.round((realPosition.getY()-tileSize/2)/tileSize);
        return new Vector(x, y);
    }
    /**
     * Given the x and y positions in terms of the grid, return the node at that point.
     */
    public Node getNode(int x, int y) {
        return grid[y][x];
    }
    /**
     * Returns the adjacent nodes to the given node. Diagonals included.
     */
    public ArrayList<Node> getNeighbours(Node n, int maxTileHeight) {
        ArrayList<Node> neighbours = new ArrayList<Node>();
        int nodeX = n.getX();
        int nodeY = n.getY();
        for (int y = -1; y <=1; y++) { // Iterate through possible y values (top row, current row, bottom row)
            for (int x = -1; x <= 1; x++) { // Iterate through possible x vslues (left row, current row, right row)
                if (x == 0 && y == 0) continue; // If center (current tile), skip.
                
                int checkX = nodeX + x;
                int checkY = nodeY + y;
                
                // If the coordinate is within the bounds of the grid,
                if (checkX >= 0 && checkX < width && checkY >= 0 && checkY < height) {
                    Node neighbour = getNode(checkX, checkY);
                    
                    neighbour.setWalkable(Board.checkIfWalkable(checkX, checkY, maxTileHeight));
                    
                    neighbours.add(neighbour); // add it to be returned
                }
            }
        }
        return neighbours;
    }
    
    /**
     * A* pathfinding algorithm.
     * @param startPos the starting position to path from, relative to grid.
     * @param targetPos the ending position to be at, relative to grid
     * @param maxTileHeight hte maximum tile height can be walked over
     */
    public ArrayList<Node> findPath(Node start, Node end, int maxTileHeight) {
        // Create nodes based on the given positions.
        
        Node startNode = start;
        Node endNode = end;
        
        // Create two lists.
        ArrayList<Node> openSet = new ArrayList<Node>(); // Holds unexplored, accessbile nodes?
        ArrayList<Node> closedSet = new ArrayList<Node>(); // Holds explored nodes?
        
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
            if (currentNode == endNode) { // If the current node is equal to the end node (path found);
                return retracePath(startNode, currentNode); // Return the path found.
            }
            
            for (Node neighbour : getNeighbours(currentNode, maxTileHeight)) { // For each adjacent tile to the current node,
                if (!neighbour.isWalkable() || closedSet.contains(neighbour)) { // If this neighbouring tile is not walkable, or has been cheked already,
                    continue; // skip.
                }
                // Calculate the cost to get to the neighbouring node.
                int moveCostToNeighbour = currentNode.gCost() + Node.getDistance(currentNode, neighbour);
                
                // If the move cost to the neighbour is more efficient, and it is not already existing.
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
        System.out.println("warn: no path was found in a pathfinding attempt");
        return null;
    } 
    /**
     * Retraces the path taken, given a starting node and ending node.
     * @param startNode the starting node
     * @param endNode the ending node.
     * @return The actual path from start node to end node, in order.
     */
    private ArrayList<Node> retracePath(Node startNode, Node endNode) {
        ArrayList<Node> path = new ArrayList<Node>();
        Node currentNode = endNode; // Start with the ending node.
        path.add(currentNode); // Add it to the path.
        while (currentNode != startNode) { // While the current node is not the starting node (still need to backtrack). Same issue, currentNode != startNode is much better
            currentNode = currentNode.getParent(); // get the parent of the current node, and
            path.add(currentNode); // add it to the path.
        }
        Collections.reverse(path); // Reverse the path, so it goes from start -> end instead of end -> start.
        return path;
    }
}
