import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)
import java.util.ArrayList;
/**
 * The DrawWorld is where the User is able to create the World Environment (the map). The user can swap between Tile types and
 * draw on the Board being displayed. Once satisfied, the user can submit this board to the SimulationWorld, where the simulation
 * begins.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class DrawWorld extends CursorWorld
{
    
    private static Vector currentTilePos, previousTilePos;
    private static int mouseDrawType;
    private static boolean drawing;
    private static Node pathStart, pathEnd;
    

    //private static final String preset1 = "16~12~64~wwwwwwwwwwwwwwwwwwwbtgwtgwggbwwwwwwttgggggggggwwwwgggbgggbtgtggwwwbgggttgggggtbwwwtggggttgtbgggwwwggbgbgtggggwgwwwwggggggbbggwwwwgwwtgggtgggtgwwwtgwttbggbgttgwwwwwwwwgwwwgggwwwwwwwwwwwwwwwwwww";
    private static final String preset1 = "21~16~48~wwwwwwwwwwwwwwwwwwwwwwwwwwwwgggggwwwwwwbwwwwtggggmtbggggwwwwgwwwwbggbgtgggwwwwwgggwwwggggggggwwwbbgggtgwwwggmtgbwwwgggmmggtgwwwbbmggwwgggtggggbgbwwwgggggwgbgggggwwggggwwgggbwwtggbgggwwgmmmwwwbgwwgggggmtggggggmwwwwwwgtgbgbgtggggtggwwwwggggmgggggtmgbggwwmbwgtgbggwggwgggggwwwtgwggwwgwwwwwwggttwwwwgwwwwwwwgbgwwwwgtwwwwwwwwwwwwwwwwwwwwwwww";
    //private static final String preset2 = "16~12~64~tttttttttttttttttggggggttggggggttggggggttggggggttggggbbbbbbggggttggggbwwwwbggggttgbggbwbbwbggbgttgbggbwbbwbggbgttggggbwwwwbggggttggggbbbbbbggggttggggggttggggggttggggggttggggggttttttttttttttttt";
    private static final String preset2 = "21~16~48~ttttttttttttttttttttttbbbggggggmgggggggggttbwwggggggbbggggggggttbwwggggggggggggmbbgttgwwggggggmmggggmbbgttgwwgggwwwwwwwwgggggttggggggwtmbbmtwgggggttggggggwtmbbmtwgttttttttttggwtmbbmtwgggggttggggggwtmbbmtwgggggttggggggwwwwwwwwggwwgttgbbmgggggmmgggggwwgttgbbmggggggggggggwwbttgggggggggbbgggggwwbttggggggggggmgggggbbbtttttttttttttttttttttt";
    //private static final String preset3 = "16~12~64~gggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggg";
    private static final String preset3 = "21~16~48~gggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggg";
    //private static final String preset3 = "32~24~32~eeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeteeeeeeeweeeeeeeeeeeeeeeeeeeeettbeeeeeeweeeeeeeeeeewwweeeeeeettebeeeeeeweeeeeeeewwweeeeeeeteetebeeeeeeeeeeeeeeewweeeeeeeeeteeetbeeeeeeweeeeeeeettettttteeetteetbeeeeeeweeeeeeetwweeeeeteeettttteeeeeeeeeeeeeteeeweeeeeteeeteebeeeeeeeeweeeeteeeeewweeeteeetewteeeeeeeeweeeteeeeeeeteeeteeetwwtteeeeeeeweebbeeeeeeteeeeteeewbetteeeeeeewwebtbbbeeteeeeetewwbeeeeeeeeeeeewwbettbbbbeeeettwweeeeeeeeeeeeeeewbeeeeeebbbbbbwweeeeeewwweeeeeeeewweeeeeeeeeeeweeeeewweeweeeeeeeeeewwebebebebewwwewweeeeweeeeeeeeeeeewwweeeeeeeeeeeeeeewweeeeeeeeeeeeeeewweeeeeeeeeeeeweeeeeeeeeeeeeeeeeeeweweeeeeeeewweeeeeeeeeeeeeeeeeeeeeewewweweweeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeee";
    /**
     * Constructor for objects of class MyWorld.
     * 
     */
    public DrawWorld()
    {    

        super(); 
        Board.loadBoard(this, 48);

        mouseDrawType = 0;
        drawing = false;
        
        addObject(new TileSelector(), getWidth() + 75, getHeight()/2);
        previousTilePos = new Vector(-1, -1);
        currentTilePos = new Vector(-1, -1);
        Tile.setTimeFlow(false);
    }
    public void act() {
        checkMouseState();
        keepInBounds(cursor);
        currentTilePos = Board.convertRealToTilePosition(cursor.getPosition());
        if (drawing) {
            Tile tileHovered = null;
            ArrayList<Actor> hoveredActors = (ArrayList<Actor>)cursor.getHoveredActors();
            for(Actor a : hoveredActors){
                if (a instanceof TileSelector) {
                    if((((TileSelector)a).getState() || !((TileSelector)a).getClosed())){
                        tileHovered = null;
                        break;
                    }

                }
                else if (a instanceof UI){
                    tileHovered = null;
                    break;
                }
                else if (a instanceof Tile) {
                    
                    tileHovered = (Tile)a;
                    
                }
            }
            if (tileHovered != null) {
                tileHovered.replaceMe(getDrawnTile());
            }
        }
        
        // Manage the transparency highlighting current tile hovered.
        if (!currentTilePos.equals(previousTilePos)) { // mouse moved into another cell
            // Make previous tile at the previous position opaque (moved off that tile).
            Tile previousTile = Board.getTile((int)previousTilePos.getX(), (int)previousTilePos.getY());
            if (previousTile != null) previousTile.setTransparency(255);
            
            // Make new hovered tile slightly transparent (cooler effect)\
            if(cursor != null){
                Tile hoveredTile = Board.getTile(cursor.getPosition());
                if(hoveredTile != null){
                    hoveredTile.setTransparency(150);
                }
                
            }
            


            
        }
        previousTilePos = currentTilePos;
        
        manageKeyInput();
    }
    private Tile getCurrentTile(ArrayList<Actor> actors) {
        for(Actor a : actors){
            if (a instanceof TileSelector) {
                if((((TileSelector)a).getState() || !((TileSelector)a).getClosed())){
                    return null;
                }
            }
            else if (a instanceof UI){
                return null;
            }
            else if (a instanceof Tile) {
                return (Tile)a;
            }
        }
        return null;
    }
    private void manageKeyInput() {
        String key = Greenfoot.getKey();
        if ("0".equals(key)) {
            mouseDrawType = 0;
            previousTilePos = new Vector(-1, -1);
        }
        else if ("1".equals(key)) {
            mouseDrawType = 1;
            previousTilePos = new Vector(-1, -1);
        }
        else if ("2".equals(key)) {
            mouseDrawType = 2;
            previousTilePos = new Vector(-1, -1);
        }
        else if ("3".equals(key)) {
            mouseDrawType = 3;
            previousTilePos = new Vector(-1, -1);
        }
        else if ("4".equals(key)) {
            mouseDrawType = 5;
            previousTilePos = new Vector(-1, -1);
        }
        else if ("e".equals(key)) {
            mouseDrawType = 4;
            previousTilePos = new Vector(-1, -1);
        }
        else if ("[".equals(key)) {
            Board.loadBoard(this, preset1);
        }
        else if ("]".equals(key)) {
            Board.loadBoard(this, preset2);
        }
        else if ("\\".equals(key)) {
            Board.loadBoard(this, preset3);
        }
        else if ("l".equals(key)) { // submit
            if (Board.isReady()) {
                Tile hoveredTile = Board.getTile(cursor.getPosition());
                hoveredTile.setTransparency(255); // make it opaque now (tiles no need to be hovered over)
                Greenfoot.setWorld(new SimulationWorld());
            }
            else System.out.println("There are still empty Tiles on the Board!");
        }
        else if ("p".equals(key)) { // prints out the board
            Board.printBuildString();
        }
        else if (",".equals(key)) {
            pathStart = Board.getNodeWithRealPosition(cursor.getPosition());
        }
        else if (".".equals(key)) {
            pathEnd = Board.getNodeWithRealPosition(cursor.getPosition());
            if (pathStart != null && pathEnd != null) {
                ArrayList<Node> path = Board.findPath(pathStart, pathEnd, 1);
                Board.displayPath(path, Color.YELLOW);
            }
            else System.out.println("one of the nodes are not existing");
        }
    }
    private void checkMouseState() {
        if (Greenfoot.mousePressed(null)) { // Mouse has went not pressed to pressed.
            drawing = true; // Currently drawing, until mouse released.
            Tile hoveredTile = Board.getTile(cursor.getPosition());
            hoveredTile.setTransparency(255); // Just in case.
        }
        else if (Greenfoot.mouseClicked(null)) { // Mouse has went from pressed to not pressed.
            drawing = false; // Not drawing anymore.
            previousTilePos = new Vector(-1, -1);
        }
    }
    private void keepInBounds(Actor a){
        if(a.getX() >= getWidth()){
            a.setLocation(getWidth(), a.getY());
        }
        if(a.getX() <= 0){
            a.setLocation(0, a.getY());
        }
        if(a.getY() >= getHeight()){
            a.setLocation(a.getX(), getHeight());
        }
        if(a.getY() <= 0){
            a.setLocation(a.getX(), 0);
        }
    }
    private Tile getDrawnTile() {
        switch(mouseDrawType) {
            case 0:
                return new WaterTile();
            case 1:
                return new GrassTile();
            case 2:
                return new TreeTile();
            case 3:
                return new BushTile();
            case 4:
                return new EmptyTile();
            case 5:
                return new MountainTile();
        }
        System.out.println("err: tried to draw tile, but not cannot recognize mouseDrawType: " + mouseDrawType);
        return new EmptyTile(); // Some thing went wrong so give EmptyTile
    }

}
