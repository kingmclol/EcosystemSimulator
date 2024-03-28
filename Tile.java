import greenfoot.*;
/**
 * Write a description of class Tile here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class Tile extends Actor
{
    private static int size = 64;
    public Tile(GreenfootImage image) {
        setImage(image);
        //drawBorder();
    }
    public Tile(boolean a) {
        setTile(Color.GRAY);
    }
    public Tile() {
        switch(Greenfoot.getRandomNumber(4)) {
            case 0:
                //setTile(Color.BLUE);
                setTile(new GreenfootImage("tile_water.png"));
                break;
            case 1:
                //setTile(Color.RED);
                setTile(new GreenfootImage("tile_grass.png"));
                break;
            case 2:
                //setTile(Color.GREEN);
                setTile(new GreenfootImage("tile_berries.png"));
                break;
            case 3:
                setTile(new GreenfootImage("tile_trees.png"));
                break;
        }
    }
    public void setTile(Color c) {
        GreenfootImage img = new GreenfootImage(size, size);
        img.setColor(c);
        img.fill();
        setImage(img);
        //drawBorder();
    }
    public void setTile(GreenfootImage img) {
        img.scale(size, size);
        setImage(img);
        //drawBorder();
    }
    public void drawBorder(){
        getImage().setColor(Color.BLACK);
        getImage().drawRect(0,0,size, size);
    }
}
