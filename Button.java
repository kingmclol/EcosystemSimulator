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
    
    private GreenfootImage hoverImg;
    private GreenfootImage pressedImg;
    private boolean clicked;
    private Runnable function;
    private Runnable dragFunction;
    public Button(Runnable function, int width, int height){
        this.width = width;
        this.height = height;
        clicked = false;
        this.function = function;
       
        img = new GreenfootImage(width, height);
        img.setColor(Color.BLUE);
        img.fill();
        hoverImg = new GreenfootImage(width, height);
        hoverImg.setColor(Color.RED);
        hoverImg.fill();
        pressedImg = new GreenfootImage(width, height);
        pressedImg.setColor(Color.GREEN);
        pressedImg.fill();
        setImage(img);
    }
    public Button(Runnable function,Runnable dragFunction, int width, int height, GreenfootImage defaultImg, GreenfootImage hoverImg, GreenfootImage pressedImg){
        this.width = width;
        this.height = height;
        clicked = false;
        this.function = function;
        this.dragFunction = dragFunction;
        img = defaultImg;
        this.hoverImg = hoverImg;
        this.pressedImg = pressedImg;
        setImage(img);
    }
    public Button(Runnable function, int width, int height, GreenfootImage defaultImg, GreenfootImage hoverImg, GreenfootImage pressedImg){
        this.width = width;
        clicked = false;
        this.function = function;
        this.height = height;
        img = defaultImg;
        this.hoverImg = hoverImg;
        this.pressedImg = pressedImg;
        setImage(img);
    }

    public void act()
    {
        mouseEffect();
    }
    
    private void mouseEffect(){
        if (dragFunction != null && clicked){
            dragFunction.run();
        }
                
        if(Greenfoot.mousePressed(null) && cursor.getHoveredActors().contains(this)){
            setImage(pressedImg);
            clicked = true;
			playButtonSound();
            function.run();
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
