import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)
import java.util.ArrayList;

/**
 * Write a description of class MyWorld here.
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
    private String preset1 = "16~12~64~eeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeewggeeweeeeeeeewwwgggwweeeeegeweewgeggwweeeegggeewgewwgweeeeegggewggwwegweeeeggeggwgweeegeeeeeggegggwwggeeeeeeeegggggggeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeee";
    /**
     * Constructor for objects of class MyWorld.
     * 
     */
    public DrawWorld()
    {    
        // Create a new world with 600x400 cells with a cell size of 1x1 pixels.
        super(1024, 768, 1); 
        setPaintOrder(Cursor.class, Tile.class);
        
        //board = new Board(64, this);
        board = new Board(preset1);
        addObject(board, 0, 0);
        mouseDrawType = 0;
        drawing = false;
        addObject(cursor, 0,0);
        previousTilePos = new Vector();
        currentTilePos = new Vector();
        Tile.setTimeFlow(false);
    }
    public void act() {
        checkMouseState();
        
        if (drawing) {
            Actor a = cursor.getHoveredActor();
            if (a instanceof Tile) {
                currentTilePos = Board.getTilePosition(cursor.getPosition());
                if (!currentTilePos.equals(previousTilePos)) {
                    ((Tile) a).replaceMe(getDrawnTile());
                }
            }
            previousTilePos = currentTilePos;
        }
        
        String key = Greenfoot.getKey();
        if ("0".equals(key)) {
            mouseDrawType = 0;
        }
        else if ("1".equals(key)) {
            mouseDrawType = 1;
        }
        else if ("2".equals(key)) {
            mouseDrawType = 2;
        }
        else if ("3".equals(key)) {
            mouseDrawType = 3;
        }
        else if ("e".equals(key)) {
            mouseDrawType = 4;
        }
        else if ("l".equals(key)) {
            Greenfoot.setWorld(new SimulationWorld(board));
        }
        else if ("p".equals(key)) {
            System.out.println(board);
        }
    }
    private void checkMouseState() {
        if (Greenfoot.mousePressed(null)) {
            drawing = true;
        }
        else if (Greenfoot.mouseClicked(null)) {
            drawing = false;
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
        return new EmptyTile();
    }
}
