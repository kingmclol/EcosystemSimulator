/**
 * A Node is a specific coordinate on a given grid, used for A* pathfinding. It holds a parent node, and some variables
 * relating to its distance from the start and end node.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class Node  
{
    // instance variables - replace the example below with your own
    private Node parent;
    private boolean walkable;
    private Vector position;
    private int gCost;
    private int hCost;
    /**
     * Creates a Node that is accessable or inaccesible, and gives it a position (which would be relative
     * to the grid it is in).
     * @param walkable Whether this node should be considered as applicable to walk over in pathfinding.
     * @param position The position of this node on the grid.
     */
    public Node(boolean walkable, Vector position) {
        this.walkable = walkable;
        this.position = position;
    }
    /**
     * Sets the parent of this node.
     * @param node The parent of this node. Basically, to walk to this node, which node did you come from?
     */
    public void setParent(Node node) {
        parent = node;
    }
    /**
     * Returns the parent node.
     */
    public Node getParent() {
        return parent;
    }
    /**
     * Sets the H cost of this node. I don't know if it's the distance from the start or end node.
     */
    public void setHCost(int cost) {
        this.hCost = cost;
    }
    /**
     * Sets the G cost of this node. I don't know if it's the distance from the start or end node.
     */
    public void setGCost (int cost) {
        this.gCost = cost;
    }
    /**
     * Returns whether this node should be walkable.
     */
    public boolean isWalkable() {
        return walkable;
    }
    /**
     * Returns the H cost of this node.
     */
    public int hCost() {
        return hCost;
    }
    /**
     * Returns the G cost of this node.
     */
    public int gCost() {
        return gCost;
    }
    /**
     * Returns the F cost of this node, which is the sum of the G and H costs.
     */
    public int fCost() {
        return gCost + hCost;
    }
    /**
     * Returns the position of this Node. Relative to the grid, not the World.
     */
    public Vector getPosition() {
        return position;
    }
    /**
     * Get the X position of this Node. Relative to the grid.
     */
    public int getX() {
        return (int) Math.round(position.getX());
    }
    /**
     * Get the Y position of this node. Relative to the grid.
     */
    public int getY() {
        return (int) Math.round(position.getY());
    }
    /**
     * Returns the distance cost needed to travel from Node a to node b
     * @param a The starting node.
     * @param b the ending node.
     * @return The cost to travel from node A to node B
     */
    public static int getDistance(Node a, Node b) {
        int dX = Math.abs(a.getX() - b.getX());
        int dY = Math.abs(a.getY() - b.getY());
        if (dX > dY) {
            return 14*dY + 10*(dX - dY);
        }
        else return 14 * dX + 10*(dY - dX);
    }
    /**
     * Quickly implemented way to check if two nodes are equal, meaning that their positions are the same.
     * TODO: Rework how I get nodes, so reference comparisons can be used instead. That is, I should not 
     *       be creating new nodes in getNeighbours(). Maybe i can merge node functionality with Tiles? Or another
     *       2D array holding nodes only?
     * @param other The node to compare to.
     * @return true if they occupy the same position.
     */
    public boolean equals(Node other) {
        return getPosition().equals(other.getPosition());
    }
}
