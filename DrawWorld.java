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
public class DrawWorld extends World
{
    
    private static Vector previousTilePos, currentTilePos;
    private static int mouseDrawType;
    private static boolean drawing;
    private static Node pathStart, pathEnd;
    private static Cursor cursor = new Cursor();
    private static final String preset1 = "16~12~64~wwwwwwwwwwwwwwwwwwwbtgwtgwggbwwwwwwttgggggggggwwwwgggbgggbtgtggwwwbgggttgggggtbwwwtggggttgtbgggwwwggbgbgtggggwgwwwwggggggbbggwwwwgwwtgggtgggtgwwwtgwttbggbgttgwwwwwwwwgwwwgggwwwwwwwwwwwwwwwwwww";
    private static final String preset2 = "16~12~64~tttttttttttttttttggggggttggggggttggggggttggggggttggggbbbbbbggggttggggbwwwwbggggttgbggbwbbwbggbgttgbggbwbbwbggbgttggggbwwwwbggggttggggbbbbbbggggttggggggttggggggttggggggttggggggttttttttttttttttt";
    private static final String preset3 = "16~12~64~gggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggg";
    //private static final String preset3 = "32~24~32~eeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeteeeeeeeweeeeeeeeeeeeeeeeeeeeettbeeeeeeweeeeeeeeeeewwweeeeeeettebeeeeeeweeeeeeeewwweeeeeeeteetebeeeeeeeeeeeeeeewweeeeeeeeeteeetbeeeeeeweeeeeeeettettttteeetteetbeeeeeeweeeeeeetwweeeeeteeettttteeeeeeeeeeeeeteeeweeeeeteeeteebeeeeeeeeweeeeteeeeewweeeteeetewteeeeeeeeweeeteeeeeeeteeeteeetwwtteeeeeeeweebbeeeeeeteeeeteeewbetteeeeeeewwebtbbbeeteeeeetewwbeeeeeeeeeeeewwbettbbbbeeeettwweeeeeeeeeeeeeeewbeeeeeebbbbbbwweeeeeewwweeeeeeeewweeeeeeeeeeeweeeeewweeweeeeeeeeeewwebebebebewwwewweeeeweeeeeeeeeeeewwweeeeeeeeeeeeeeewweeeeeeeeeeeeeeewweeeeeeeeeeeeweeeeeeeeeeeeeeeeeeeweweeeeeeeewweeeeeeeeeeeeeeeeeeeeeewewweweweeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeee";
    /**
     * Constructor for objects of class MyWorld.
     * 
     */
    public DrawWorld()
    {    
        super(1024, 768, 1); 

        setPaintOrder(Cursor.class, UI.class, Node.class, Animal.class, Tile.class);
        Board.loadBoard(this, 64);
        mouseDrawType = 0;
        drawing = false;
        addObject(cursor, 0,0);
        addObject(new TileSelector(), getWidth() - 125, getHeight()/2);
        previousTilePos = new Vector(-1, -1);
        currentTilePos = new Vector(-1, -1);
        Tile.setTimeFlow(false);
    }
    public void act() {
        checkMouseState();
        
        // NEEDS REWORK
        if (drawing) {
            Tile tileHovered = null;
            ArrayList<Actor> hoveredActors = (ArrayList<Actor>)cursor.getHoveredActors();
            for(Actor a : hoveredActors){
                if ((a instanceof TileSelector && ((TileSelector)a).getState()) || (!(a instanceof TileSelector) && a instanceof UI )) {
                    
                    tileHovered = null;
                    break;
                }
                else if (a instanceof Tile) {
                    
                    tileHovered = (Tile)a;
                    
                }
            }
            if (tileHovered != null) {
                System.out.println("test2");
                currentTilePos = Board.convertRealToTilePosition(cursor.getPosition());
                // Only draw on a tile IF the user is drawing on a new tile. This way,
                // will not draw on the same tile multiple times.
                if (!currentTilePos.equals(previousTilePos)) {
                    tileHovered.replaceMe(getDrawnTile());
                }
            }
            previousTilePos = currentTilePos;
            /*
            Actor a = cursor.getHoveredActor();
            if (a instanceof Tile) {
                currentTilePos = Board.convertRealToTilePosition(cursor.getPosition());
                // Only draw on a tile IF the user is drawing on a new tile. This way,
                // will not draw on the same tile multiple times.
                if (!currentTilePos.equals(previousTilePos)) {
                    ((Tile) a).replaceMe(getDrawnTile());
                }
            }
            previousTilePos = currentTilePos;
            */
        }
        
        manageKeyInput();
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
            drawing = true;
        }
        else if (Greenfoot.mouseClicked(null)) { // Mouse has went from pressed to not pressed.
            drawing = false;
            previousTilePos = new Vector(-1, -1); // Reset the previous tile position.
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
        return new EmptyTile(); // Some thing went wrong so give EmptyTile
    }
    public static Cursor getCursor(){
        return cursor;
        
    }
}
