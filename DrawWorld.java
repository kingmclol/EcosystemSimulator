import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)
import java.util.ArrayList;
/**
 * The DrawWorld is where the User is able to create the World Environment (the map). The user can swap between Tile types and
 * draw on the Board being displayed. Once satisfied, the user can submit this board to the SimulationWorld, where the simulation
 * begins.
 * 
 * @author Freeman Wang, Neelan
 * @version 2024-04-24
 */
public class DrawWorld extends CursorWorld
{
    private GreenfootSound drawWorldMusic;
    
    private Vector currentTilePos, previousTilePos;
    private static int mouseDrawType;
    private boolean drawing;
    // private static Node pathStart, pathEnd; // for testing
    

    private static final String preset1 = "21~16~48~wwwwwwwwwwwwwwwwwwwwwwwwwwwwgggggwwwwwwbwwwwtggggmtbggggwwwwgwwwwbggbgtgggwwwwwgggwwwggggggggwwwbbgggtgwwwggmtgbwwwgggmmggtgwwwbbmggwwgggtggggbgbwwwgggggwgbgggggwwggggwwgggbwwtggbgggwwgmmmwwwbgwwgggggmtggggggmwwwwwwgtgbgbgtggggtggwwwwggggmgggggtmgbggwwmbwgtgbggwggwgggggwwwtgwggwwgwwwwwwggttwwwwgwwwwwwwgbgwwwwgtwwwwwwwwwwwwwwwwwwwwwwww";
    private static final String preset2 = "21~16~48~ttttttttttttttttttttttbbbggggggmgggggggggttbwwggggggbbggggggggttbwwggggggggggggmbbgttgwwggggggmmggggmbbgttgwwgggwwwwwwwwgggggttggggggwtmbbmtwgggggttggggggwtmbbmtwgttttttttttggwtmbbmtwgggggttggggggwtmbbmtwgggggttggggggwwwwwwwwggwwgttgbbmgggggmmgggggwwgttgbbmggggggggggggwwbttgggggggggbbgggggwwbttggggggggggmgggggbbbtttttttttttttttttttttt";
    private static final String preset3 = "21~16~48~gggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggg";
    private boolean forceUpdateTransparency;
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
        
        TextBox phaseText = new TextBox("D R A W I N G   P H A S E", 62, Color.WHITE, null, 2, 255);
        TextBox subText = new TextBox("Create your World (or use a preset).", 32, Color.WHITE, null, 1, 255);
        phaseText.fadeOut();
        subText.fadeOut();
        addObject(phaseText, getWidth()/2, 50);
        addObject(subText, getWidth()/2, 100);
        
        addObject(new TileSelector(), getWidth() + 42, getHeight()/2);
        previousTilePos = new Vector(-1, -1);
        currentTilePos = new Vector(-1, -1);
        Tile.setTimeFlow(false); // Tiles added should not act right now
        
        drawWorldMusic = new GreenfootSound("drawworld.mp3");
        drawWorldMusic.setVolume(30); 
        drawWorldMusic.play();
    }
    public void act() {
        checkMouseState(); // Get whether the mouse is clicked/released to determine if the user is drawing
        keepInBounds(cursor);
        currentTilePos = Board.convertRealToTilePosition(cursor.getPosition()); // Get the current tile position of the cursor
        if (drawing) { // If the user is drawing (clicked/held down)
            ArrayList<Actor> hoveredActors = (ArrayList<Actor>)cursor.getHoveredActors();
            Tile tileHovered  = getCurrentTile(hoveredActors); // Get a tile that is being hovered on
            if (tileHovered != null) {
                tileHovered.replaceMe(getDrawnTile()); // Draw the selecetd tile type onto that tile
            }
        }
        
        // Manage the transparency highlighting current tile hovered.
        else if (!currentTilePos.equals(previousTilePos) || forceUpdateTransparency) { // mouse moved into another cell
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
        previousTilePos = currentTilePos; // set the previous tile position to the current itle position
        
        manageKeyInput(); // read key input for keybinds
    }
    public void started() {
        drawWorldMusic.play(); 
    }
    public void stopped() {
        drawWorldMusic.pause();
    }
    private Tile getCurrentTile(ArrayList<Actor> actors) {
        Tile tile = null;
        if (cursor.getX() > getWidth()- 160) { // Check if its within tile selector dimensions
            if((TileSelector.getState() || !TileSelector.getClosed())){
                return null;
            }
        }
        for(Actor a : actors){

            if (a instanceof UI && !(a instanceof TileSelector)){
                return null;
            }
            else if (a instanceof Tile) {
                tile = (Tile) a;
            }
        }
        return tile;
    }
    /**
     * While this does check for key inputs and stuff, it is mainly used for debugging purposes. A general user
     * is meant to use the tile selector.
     */
    private void manageKeyInput() {
        String key = Greenfoot.getKey();
        if ("5".equals(key)) {
            mouseDrawType = 0;
            forceUpdateTransparency = true;
        }
        else if ("1".equals(key)) {
            mouseDrawType = 1;
            forceUpdateTransparency = true;
        }
        else if ("2".equals(key)) {
            mouseDrawType = 2;
            forceUpdateTransparency = true;
        }
        else if ("3".equals(key)) {
            mouseDrawType = 3;
            forceUpdateTransparency = true;
        }
        else if ("4".equals(key)) {
            mouseDrawType = 5;
            forceUpdateTransparency = true;
        }
        else if ("e".equals(key)) {
            mouseDrawType = 4;
            forceUpdateTransparency = true;
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
                drawWorldMusic.stop(); 
            }
            else display("There are still empty Tiles on the Board!");
        }
        else if ("p".equals(key)) { // prints out the board
            Board.printBuildString();
        }
        // else if (",".equals(key)) {
            // pathStart = Board.getNodeWithRealPosition(cursor.getPosition());
        // }
        // else if (".".equals(key)) {
            // pathEnd = Board.getNodeWithRealPosition(cursor.getPosition());
            // if (pathStart != null && pathEnd != null) {
                // ArrayList<Node> path = Board.findPath(pathStart, pathEnd, 1);
                // Board.displayPath(path, Color.YELLOW);
            // }
            // else System.out.println("one of the nodes are not existing");
        // }
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
    /**
     * Depending on the current mouse draw type, return the tile that would be drawn (think of the tile instance as the ink of a pen)
     */
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

    public void startSimulation(){
        if (Board.isReady()) {
            Tile hoveredTile = Board.getTile(cursor.getPosition());
            hoveredTile.setTransparency(255); // make it opaque now (tiles no need to be hovered over)
            Greenfoot.setWorld(new SimulationWorld());
            drawWorldMusic.stop();
        }
        else display("There are still empty Tiles on the Board!");
    }
    /**
     * Set the mouse draw type based on the given int
     * @param type the type of tile to draw.
     */

    public static void setMouseDrawType(int type){
        if(type > -1 && type < 7){
            mouseDrawType = type;
        }
    }
    /**
     * Loads a preset board.
     */
    public  void loadPreset(int type){
        if(type == 1){
            Board.loadBoard(this, preset1);
        }
        else if (type == 2){
            Board.loadBoard(this, preset2);
        }
    }
    /**
     * Dispalys a given string onto the world.
     * @param text the text to display.
     */
    private void display(String text) {
        // quick way to prevent spamming to reduce performance. why would you do that though :(
        if (getObjects(TextBox.class).size() > 3) return;
        
        TextBox box = new TextBox(text, 48, Color.WHITE, null, 3, 255);
        box.fadeOut();
        addObject(box, getWidth()/2, 100);
    }
}
