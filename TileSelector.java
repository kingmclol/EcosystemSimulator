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
    public TileSelector(){
        img = new GreenfootImage(200, 768);
        img.setColor(Color.WHITE);
        img.fill();
        setImage(img);
        menuButton = new Button(50, 100);
        closed = true;
        
    }
    public void addedToWorld(World w){
        w.addObject(menuButton, w.getWidth() - 25, w.getHeight()/2);
    }
    public void act()
    {
        if(transition){
            
            if (closed && getX() > getWorld().getWidth() - 100){
                 setLocation(getX() - 2, getY());
            }
            else if(getX() < getWorld().getWidth() + 100 && !closed){
                setLocation(getX() + 2, getY());
            }
            else {
                transition = false;
                
                closed = !closed;
            }
            
        }
        if(Greenfoot.mousePressed(menuButton)){
            
            transition = true;
 
        }
    }
}
