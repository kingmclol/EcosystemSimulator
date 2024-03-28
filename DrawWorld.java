import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

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
    //private static Board board;
    private static Vector previousTilePos, currentTilePos;
    private static int mouseDrawType;
    private static boolean drawing;
    private static Cursor cursor = new Cursor();
    private static Board board;
    private String preset1 = "16~12~64~wwwwwwwwwwwwwwwwwwwbtgwtgwggbwwwwwwttgggggggggwwwwgggbgggbtgtggwwwbgggttgggggtbwwwtggggttgtbgggwwwggbgbgtggggwgwwwwggggggbbggwwwwgwwtgggtgggtgwwwtgwttbggbgttgwwwwwwwwgwwwgggwwwwwwwwwwwwwwwwwww";
    private String preset2 = "16~12~64~tttttttttttttttttggggggttggggggttggggggttggggggttggggbbbbbbggggttggggbwwwwbggggttgbggbwbbwbggbgttgbggbwbbwbggbgttggggbwwwwbggggttggggbbbbbbggggttggggggttggggggttggggggttggggggttttttttttttttttt";
    /**
     * Constructor for objects of class MyWorld.
     * 
     */
    public DrawWorld()
    {    
        // Create a new world with 600x400 cells with a cell size of 1x1 pixels.
        super(1024, 768, 1); 
        setPaintOrder(Cursor.class, Tile.class);
        
        board = new Board(64, this);
        //board = new Board(preset1);
        addObject(board, 0, 0);
        mouseDrawType = 0;
        drawing = false;
        addObject(cursor, 0,0);
        previousTilePos = new Vector(-1, -1);
        currentTilePos = new Vector(-1, -1);
        Tile.setTimeFlow(false);
    }
    public void act() {
        checkMouseState();
        
        // NEEDS REWORK
        if (drawing) {
            Actor a = cursor.getHoveredActor();
            if (a instanceof Tile) {
                currentTilePos = Board.getTilePosition(cursor.getPosition());
                // Only draw on a tile IF the user is drawing on a new tile. This way,
                // will not draw on the same tile multiple times.
                if (!currentTilePos.equals(previousTilePos)) {
                    ((Tile) a).replaceMe(getDrawnTile());
                }
            }
            previousTilePos = currentTilePos;
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
        else if ("e".equals(key)) {
            mouseDrawType = 4;
            previousTilePos = new Vector(-1, -1);
        }
        else if ("[".equals(key)) {
            System.out.println("a");
            board.loadBoard(preset1);
        }
        else if ("]".equals(key)) {
            board.loadBoard(preset2);
        }
        else if ("l".equals(key)) { // submit
            if (board.isReady()) {
                Greenfoot.setWorld(new SimulationWorld(board));
            }
            else System.out.println("There are still empty Tiles on the Board!");
        }
        else if ("p".equals(key)) { // prints out the board
            System.out.println(board);
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
        }
        return new EmptyTile(); // Some thing went wrong so give EmptyTile
    }
}
