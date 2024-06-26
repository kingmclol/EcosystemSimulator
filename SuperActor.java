import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)
import java.util.ArrayList;
import java.util.function.Predicate;

/**
 * <strong>SuperActor should extend SuperSmoothMover by Mr. Cohen.</strong>  
 * 
 * <p>A SuperActor is an Actor that provides new functionality, namely compatability with using Vectors as positions. 
 * SuperActors can do more than normal Actors, such as move towards a point while keeping its rotation unchanged.
 * Well, that was before I learned about the existence of static rotation from SuperSmoothMover... whatever. I guess
 * I'm just reinventing the wheel at this point. Should've looked at the Library of Code first...</p>
 * 
 * <blockquote><i>I intended for SuperActor to make my life easier while working with Greenfoot. It did not.</i></blockquote>
 * 
 * TODO: make speed as instance variable, add constructor for SuperActor. Will likely not do during this project.
 * Additionally, potentially create an ArrayList of Events, so any Events created will be tied to its SuperActor
 * owner.
 * 
 * @author Freeman Wang
 * @version 2024-04-07
 */

public abstract class SuperActor extends SuperSmoothMover
{
    private Node targetNodePrev;
    private double rotation;
    /*
    Not to be added during the vehicle simulator, but here so I won't forget. May or may not be neglected later on.
    ArrayList <Vector> path;
    ArrayList <Event> myEvents;
    private double speed;
    private boolean staticRotation;
    public SuperActor(double speed) {
        this.speed = speed;
    }
    Remove all "double distance" parameters in the methods, as will be managed 
    by instance variable speed. Or overload them instead.
    */
    // /** 
     // * Creates a given event by adding it into the world.
     // */
    // protected void createEvent(Event e){
        // getWorld().addObject(e, 0 ,0 );
    // 
    private ArrayList<Vector> path = new ArrayList<Vector>();
    private static final boolean SHOW_PATHFINDING_DEBUG = false;
    /**
     * Returns the current position as a Vector.
     */
    public Vector getPosition() {
        return new Vector(getPreciseX(), getPreciseY());
    }
    /** 
     * Move towards a SuperActor.
     * @param target The SuperActor to move towards.
     * @param The distance the SuperActor should travel. Also can be seen as the "speed."
     */
    protected void moveTowards(SuperActor target, double distance) {
        displace(getDisplacement(target, distance));
    }
    /** 
     * Move towards a SuperActor. This time, with pathfinding involved.
     * @param target The SuperActor to move towards.
     * @param The distance the SuperActor should travel. Also can be seen as the "speed."
     * @param maxTileHeight the maximum tile height that the superactor can walk on.
     */
    protected void moveTowards(SuperActor target, double distance, int maxTileHeight) {
        if (target == null) {
            return; // Can't do anything about this.
        }
        
        // Get nodes from the nodeGrid for comparison
        Node targetNode = Board.getNodeWithRealPosition(target.getPosition());
        Node currentNode = Board.getNodeWithRealPosition(getPosition());
        
        if (targetNode == currentNode) { // Same node as target!!!
            moveTowards(target, distance); // Within same node (no need to pathfind), so just move towards the target directly.
            return; // nothing else to do.
        }
        else if (targetNode != targetNodePrev) { // target position is different from what was originally. Either new target, or the target moved somewhere else.
            if(SHOW_PATHFINDING_DEBUG) System.out.println("target:" + target + " | " + " new target node! new: " + targetNode + " vs. prev: " + targetNodePrev);
            targetNodePrev = targetNode; // store the target's node for comparison later on.
            path = null; // need new path to the target.
        }
        
        if (path == null) { // no path, or target node changed (target moving), or target changed
            if (SHOW_PATHFINDING_DEBUG) {
                ArrayList<Node> oldDisplay  = (ArrayList<Node>) getWorld().getObjects(Node.class);
                for (Node n : oldDisplay) {
                    getWorld().removeObject(n);
                }
            }
            
            
            Tile currentTile = Board.getTile(getPosition()); // to validate my current position.
            if (currentTile.getHeightLevel() > maxTileHeight) { // Somehow, am at a tile i shouldn't be in... possibly, at a corner between two walkable tiles.
                for (int i = 0; i < 5; i++) { // Attempt three times to find a proper position to be in.
                    Vector extraDisplacement = new Vector(Greenfoot.getRandomNumber(5), Greenfoot.getRandomNumber(5));
                    currentNode = Board.getNodeWithRealPosition(getPosition().add(extraDisplacement)); // nudge my position around slightly, to attempt to find a valid position.
                    currentTile = Board.getTile(Board.getRealPositionWithNode(currentNode));
                    if (currentTile.getHeightLevel() <= maxTileHeight) { // now starting from a valid tile! yay!
                        break;
                    }
                }
            }
            
            
            // Pathfind to target.
            ArrayList<Node> nodes = Board.findPath(currentNode, targetNode, maxTileHeight);
            if (nodes != null) {
                path = new ArrayList<Vector>(); // Convert nodes into a list of positions
                for (Node n : nodes) {
                    path.add(Board.getRealPositionWithNode(n));
                }
                if (SHOW_PATHFINDING_DEBUG) Board.displayPath(nodes, Color.YELLOW);
            }
        }
        
        if (path != null) {
            if (path.size() > 0) { // If there is still a positino to go to,
                Vector nextPos = path.get(0); // get that position
                if (Board.getTile(nextPos).getHeightLevel() > maxTileHeight) { // obstruction.
                    path = null; // the current path is blocked. set path to null; find a new path later.
                }
                else {
                    if (SHOW_PATHFINDING_DEBUG) System.out.println(this + " moving to " + nextPos);
                    moveTowards(nextPos, distance); // Move towards the next positino.
                }
                
                if (distanceFrom(nextPos) < 1) { // If close to the target position
                    if (SHOW_PATHFINDING_DEBUG) System.out.println(this + " removed " + nextPos);
                    path.remove(0); // remove from list of positions to move to.
                }
            }
            else { // There are no more positinos to move to (At destination)
                path = null; // set path as null. No more to move.
                if (SHOW_PATHFINDING_DEBUG) {
                    System.out.println(this + " has completed pathfinding.");
                    ArrayList<Node> oldDisplay  = (ArrayList<Node>) getWorld().getObjects(Node.class);
                    for (Node n : oldDisplay) {
                        getWorld().removeObject(n);
                    }
                }
                return;
            }
        }
    }
    /**
     * Moves towards towards a position.
     * @param targetPos The target position to head towards. 
     * @param distance The distance the SuperActor should travel. Also can be seen as the "speed."
     */
    protected void moveTowards(Vector targetPos, double distance) {
        //displace(getPosition().distanceFrom(v).scaleTo(distance));
        displace(getDisplacement(targetPos, distance));
    }
    /**
     * Apply the given displacement vector to the SuperActor's current position. Can be thought
     * of as "moving" the SuperActor.
     * @param displacement The displacement the SuperActor should have.
     */
    public void displace(Vector displacement) {
        setLocation(getPreciseX()+displacement.getX(), getPreciseY()+displacement.getY());
        
        // Keep track of rotation.
        double rad = Math.atan2(displacement.getX(), displacement.getY());
        double degreesCCW = Math.toDegrees(rad);
        rotation = (360 - degreesCCW + 90)%360; // make CW;
    }
    /**
     * Returns the displacement Vector pointing from the current position to the SuperActor's
     * position.
     * @param target The SuperActor to go to.
     * @param distance The amount of distance to move.
     * @return The potential displacement Vector this SuperActor would move by.
     */
    protected Vector getDisplacement(SuperActor target, double distance) {
        return getDisplacement(target.getPosition(), distance);
    }
    /**
     * Returns the displacement Vector pointing from the current position to a target position.
     * @param target The position to go to.
     * @param distance The amount of distance to move.
     * @return The potential displacement Vector this SuperActor would move by.
     */
    protected Vector getDisplacement(Vector target, double distance) {
        /*
        First, gets the Vector pointing from this SuperActor to the target position, and scale to the
        speed of the SuperActor (in this case, it is equal to the distance travelled, as time = 1 act)
        Then, limit (cap) the magnitude of the displacement to the ACTUAL distance from the target, so it would
        not go past the target position, in case that scenario will occur.
        */
        return getPosition().distanceFrom(target).scaleTo(distance).limitMagnitude(distanceFrom(target));
    }
    /**
     * Turns the SuperActor towards the given position.
     * @param position The position the SuperActor should turn towards.
     */
    protected void turnTowards(Vector position) {
        turnTowards(Utility.round(position.getX()), Utility.round(position.getY()));
    }
    /**
     * Returns the magnitude of the distance from this SuperActor and the target SuperActor.
     * @param other The target SuperActor.
     * @return The distance between this SuperActor and the target SuperActor.
     */
    protected double distanceFrom(SuperActor other) {
        //return getPosition().distanceFrom(other.getPosition()).getMagnitude();
        return distanceFrom(other.getPosition());
    }
    /**
     * Returns the magnitude of the distance from this SuperActor to the target position.
     * @param targetPos The target position.
     * @return the distance between this SuperActor and the target position.
     */
    protected double distanceFrom(Vector targetPos) {
        return getPosition().distanceFrom(targetPos).getMagnitude();
    }
    /**
     * Returns true if the this SuperActor exists in a World.
     * @return Whether this SuperActor exists in a World.
     */
    public boolean exists() {
        return (getWorld() != null);
    }
    /**
     * Set the SuperActor's location to the given position.
     * @param position The location the SuperActor should be at.
     */
    public void setLocation(Vector position) {
        setLocation(position.getX(), position.getY());
    }
    /**
     * Get the closest SuperActor of a given class and radius. Inspired by Mr. Cohen's
     * implementation.
     * @param c The Class of the SuperActor to look for.
     * @param range The radius around to search in.
     * @return The closest SuperActor found. Returns null if none found.
     */
    protected SuperActor getClosestInRange(Class c, int range) {
        ArrayList<SuperActor> targets = (ArrayList<SuperActor>)getObjectsInRange(range, c);
        return getClosest(targets);
    }
    /** 
     * Get the closest SuperActor in a given ArrayList of SuperActors. Inspired
     * by Mr.Cohen's implementation.
     * @param targets The ArrayList to find the closest SuperActor from.
     * @return The closest SuperActor found. Returns null if none found.
     */
    protected SuperActor getClosest(ArrayList<SuperActor> targets) {
        double closestTargetDistance = 0;
        double distanceToActor;
        SuperActor target = null;
        if (targets.size() > 0) { //if there are potential targets
            target = targets.get(0); // get first target
            closestTargetDistance = distanceFrom(target);
            for (SuperActor a : targets) { // for each potential target
                distanceToActor = distanceFrom(a); //get distance from this SuperActor
                if (distanceToActor < closestTargetDistance) // if they're closer,
                {
                    target = a; // set them as the closest target.
                    closestTargetDistance = distanceToActor;
                }
            }
        }
        return target;
    }
    /**
     * 
     * <p>Returns the closest SuperActor of a given class within a the given radius, filtered by the given Predicate.</p>
     * <blockquote><i>I barely understand how I managed to make this, but eh. I did it, though!</i></blockquote>
     * @param c The class to look for.
     * @param range The radius of the search.
     * @param filter The condition to apply to remove ineligible targets (basically a function)
     */
    protected SuperActor getClosestInRange(Class c, int range, Predicate filter){
        ArrayList<SuperActor> targets = (ArrayList<SuperActor>)getObjectsInRange(range, c);
        targets.removeIf(filter);
        return getClosest(targets);
    }
    @Override
    /**
     * Returns the rotation of this actor.
     * Because when you use movement from the displace() you do not do any rotation manipulation
     * so it is kept track within the superactor itself now
     * @return the rotation of the superactkr
     */
    public int getRotation(){
        //System.out.println(rotation);
        return (int)Math.round(rotation);
    }
    
}