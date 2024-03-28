import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * Write a description of class SimulationWorld here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class SimulationWorld extends World
{

    /**
     * Constructor for objects of class SimulationWorld.
     * 
     */
    private Board board;
    public SimulationWorld(Board board)
    {    
        // Create a new world with 600x400 cells with a cell size of 1x1 pixels.
        super(1024, 768, 1); 
        this.board = board;
        addObject(board, 0, 0);
        Tile.setTimeFlow(true);
    }
}
