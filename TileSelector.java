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

    private int menuWidth;
    public TileSelector(){
        img = new GreenfootImage(250, 768);
        img.setColor(Color.WHITE);
        
        img.fillRect(0, 0, 0, 768);
        
        setImage(img);
        menuButton = new Button(24, 80);
        closed = true;
        menuWidth = 0;
    }
    public void addedToWorld(World w){
        cursor = getCursor(w);
        w.addObject(menuButton, w.getWidth() - 12, w.getHeight()/2);
    }
    public boolean getState(){
        return transition;
    }
    public void act()
    {
        
        if(transition){
            if (!closed && menuWidth < 200){
                menuWidth+= 4;
                
                menuButton.setLocation(menuButton.getX() - 4, getY());
                img.clear();
                
                img.fillRect(0, 0, menuWidth, 768);
                img.mirrorHorizontally();
            }
            
            else if(menuWidth > 0 && closed){
                menuWidth-= 4;
                menuButton.setLocation(menuButton.getX() + 4, getY());
                img.clear();
                img.fillRect(0, 0, menuWidth, 768);
                img.mirrorHorizontally();
            }
            else {
                transition = false;
            }
            
        }
        if(Greenfoot.mousePressed(null) && cursor.getHoveredActors().contains(menuButton)){
            transition = true;
            closed = !closed;
            
        }
    }
}
