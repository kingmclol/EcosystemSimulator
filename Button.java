import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * Write a description of class Button here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class Button extends UI
{
    private int width;
    private int height;
    private GreenfootImage img;
    private Cursor cursor;
    private GreenfootImage hoverImg;
    private GreenfootImage pressedImg;
    private boolean clicked;
     
    public Button(int width, int height){
        this.width = width;
        this.height = height;
        clicked = false;

       
        img = new GreenfootImage(width, height);
        img.fill();
        hoverImg = new GreenfootImage(width, height);
        hoverImg.setColor(Color.RED);
        hoverImg.fill();
        pressedImg = new GreenfootImage(width, height);
        pressedImg.setColor(Color.GREEN);
        pressedImg.fill();
        setImage(img);
    }
    public void addedToWorld(World w){
        if (getWorldOfType(IntroWorld.class) != null){
            cursor = IntroWorld.getCursor();
        }
        else{
            cursor = DrawWorld.getCursor();
        }
    }
    public Button(int width, int height, GreenfootImage hoverImg, GreenfootImage pressedImg){
        this.width = width;
        this.height = height;
        
        setImage(img);
    }

    public void act()
    {
        
        mouseEffect();
    }
    
    private void mouseEffect(){
        System.out.println(cursor);
        
        if(Greenfoot.mousePressed(null) && cursor.getHoveredActors().contains(this)){
            setImage(pressedImg);
            clicked = true;
        }
        else if(!clicked && cursor.getHoveredActors().contains(this)){
            setImage(hoverImg);
            
        }
        else if(Greenfoot.mouseClicked(null)){
            if(cursor.getHoveredActors().contains(this)){
                setImage(hoverImg);
            }
            
            clicked = false;
        }

        else if (!clicked){
            setImage(img);
        }


       
        /*
        if(Greenfoot.mouseMoved(this)){
            setImage(hoverImg);
            
        }
        else if(Greenfoot.mouseMoved(null) || Greenfoot.mouseClicked(null)){
            setImage(img);
        }
        if(Greenfoot.mousePressed(this)){
            setImage(pressedImg);
            
        }
        if(Greenfoot.mouseClicked(this)){
            setImage(hoverImg);
            
        }
        */
       

    }
}
