import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * Write a description of class Button here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class Button extends Actor
{
    private int width;
    private int height;
    private GreenfootImage img;
    
    private GreenfootImage hoverImg;
    private GreenfootImage pressedImg;
 
    public Button(int width, int height){
        this.width = width;
        this.height = height;
       
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
       

    }
}
