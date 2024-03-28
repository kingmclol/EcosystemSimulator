import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)
import java.util.ArrayList;

/**
 * Write a description of class MyWorld here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class MyWorld extends World
{
    private static Board board;
    private static int mouseDrawType;
    private static boolean drawing;
    private static Cursor cursor = new Cursor();
    /**
     * Constructor for objects of class MyWorld.
     * 
     */
    public MyWorld()
    {    
        // Create a new world with 600x400 cells with a cell size of 1x1 pixels.
        super(1024, 768, 1); 
        board = new Board(16,12, 64);
        board.init(this);
        mouseDrawType = 0;
        drawing = false;
        addObject(cursor, 0,0);
    }
    public void act() {
        if (Greenfoot.mousePressed(null)) {
            drawing = true;
        }
        else if (Greenfoot.mouseClicked(null)) {
            drawing = false;
        }
        if (drawing) {
            Actor a = cursor.getHoveredActor();
            //ArrayList<Actor> tiles = cursor.getHoveredActors();
            // for (Actor a : tiles) {
                // if (a instanceof Tile) {
                    // Tile t = (Tile) a;
                    // drawNewTile(t);
                // }
            // }
            if (a instanceof Tile) {
                    Tile t = (Tile) a;
                    drawNewTile(t);
            }
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
    }
    private void drawNewTile(Tile t) {
        switch(mouseDrawType) {
            case 0:
                //setTile(Color.BLUE);
                t.setTile(new GreenfootImage("tile_water.png"));
                break;
            case 1:
                //setTile(Color.RED);
                t.setTile(new GreenfootImage("tile_grass.png"));
                break;
            case 2:
                //setTile(Color.GREEN);
                t.setTile(new GreenfootImage("tile_berries.png"));
                break;
            case 3:
                t.setTile(new GreenfootImage("tile_trees.png"));
                break;
            case 4:
                t.setTile(Color.GRAY);
        }
    }
}
