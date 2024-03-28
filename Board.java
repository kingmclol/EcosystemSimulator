import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * Write a description of class Board here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class Board extends Actor
{
    private Tile[][] map;
    private int tileSize;
    /**
     * Act - do whatever the Board wants to do. This method is called whenever
     * the 'Act' or 'Run' button gets pressed in the environment.
     */
    public Board(int width, int height, int tileSize) {
        map = new Tile[height][width];
        this.tileSize = tileSize;
    }
    public void init(World w) {
        for (int i = 0; i < map.length; i++) {
            for (int j = 0; j < map[i].length; j++) {
                map[i][j] = new Tile(false);
                //System.out.println(j + " " + i + " | " + ((j)*tileSize+32) + " | " + (i*tileSize+32));
                w.addObject(map[i][j],(j)*tileSize+tileSize/2, i*tileSize+tileSize/2);
            }
        }
    }
    public void act()
    {
        // Add your action code here.
    }
    public Tile getTile(int x, int y) {
        if (x < 0 || x >= map[0].length) return null;
        else if (y < 0 || y >= map.length) return null;
        return map[y][x];
    }
    public Tile[] getAdjacent(int x, int y) {
        Tile[] adjacent = new Tile[4];
        adjacent[0] = getTile(x, y-1);
        adjacent[1] = getTile(x+1, y);
        adjacent[2] = getTile(x, y+1);
        adjacent[3] = getTile(x-1, y);
        return adjacent;
    }
}
