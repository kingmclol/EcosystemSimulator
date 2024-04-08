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
        img = new GreenfootImage("tileSelector.png");
        
        
       
        
        
        setImage(img);
        menuButton = new Button(24, 80);
        closed = true;
        menuWidth = 0;
    }
    public void addedToWorld(World w){
        cursor = getCursor();
        w.addObject(menuButton, w.getWidth() - 12, w.getHeight()/2);
    }
    public boolean getState(){
        return transition;
    }
    public boolean getClosed(){
        return closed;
    }
    public void act()
    {
        
        if(transition){
            if (!closed && menuWidth < 200){
                menuWidth+= 10;
                
                menuButton.setLocation(menuButton.getX() - 10, getY());
                
                
                setLocation(getX() - 10, getY());
                
            }
            
            else if(menuWidth > 0 && closed){
                menuWidth-= 10;
                menuButton.setLocation(menuButton.getX() + 10, getY());
                setLocation(getX() + 10, getY());
                
                
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
