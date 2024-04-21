/**
 * unused class. should delete later.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class WorldParameters  
{
    private static Board board;
    private static int tileSize;
    
    public static void setBoard(Board b) {
        board = b;
    }
    public static Board getBoard() {
        return board;
    }
    
    public static void setTileSize(int value) {
        tileSize = value;
    }
    public static int getTileSize() {
        return tileSize;
    }
}
