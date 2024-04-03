import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * This is the UI for selecting tiles before the simulation.
 * @author (your name) 
 * @version (a version number or a date)
 */
public class TileSelector extends UI
{
    private GreenfootImage img;
    private Button menuButton;
    private boolean closed;
    private boolean transition;
    private Cursor cursor;
    private int menuWidth;
    public TileSelector(){
        img = new GreenfootImage(200, 768);
        img.setColor(Color.WHITE);
        img.fillRect(200, 0, 200, 768);
        setImage(img);
        menuButton = new Button(30, 80);
        closed = true;
        menuWidth = 200;
    }
    public void addedToWorld(World w){
        if (w instanceof IntroWorld){
            cursor = IntroWorld.getCursor();
        }
        else{
            cursor = DrawWorld.getCursor();
        }
        w.addObject(menuButton, w.getWidth() - 25, w.getHeight()/2);
    }
    public void act()
    {
        
        if(transition){
            if (!closed && menuWidth > 0){
                menuWidth-= 2;
                img.fillRect(menuWidth, 0, 200, 768);
            }
            else if(menuWidth < 200 && closed){
                menuWidth+= 2;
                img.clear();
                img.fillRect(menuWidth, 0, 200, 768);
            }
            else {
                transition = false;
            }
            
        }
        if(Greenfoot.mousePressed(null) && cursor.getHoveredActors().contains(this)){
            transition = true;
            closed = !closed;
             
        }
    }
}
