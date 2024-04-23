import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * Buttons are simple objects that run a body of code or method when clicked upon.
 * 
 * @author Neelan Thurairajah
 * @version 2024/04/20
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
    /**
     * Basic constructor, with only width and height options
     * @param function  The function that runs when the button is pressed
     * @param width     The width of the button 
     * @param height    The height of the button
     *
     */
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
    /**
     * Detailed Constructor with drag action, press action, width, height, and image options
     * @param function      The function that runs when the button is pressed
     * @param dragFunction  The function that runs when the button is pressed and held  
     * @param width         The width of the button 
     * @param height        The height of the button
     * @param defaultImage  Image for the button when not being pressed or hovered
     * @param hoverImage    Image for the button when being hovered
     * @param clickedImage  Image for the button when it is pressed
     */
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
    /**
     * Primary Constructor with press action, width, height, and image options
     * @param function      The function that runs when the button is pressed
     * @param dragFunction  The function that runs when the button is pressed and held  
     * @param width         The width of the button 
     * @param height        The height of the button
     * @param defaultImage  Image for the button when not being pressed or hovered
     * @param hoverImage    Image for the button when being hovered
     * @param clickedImage  Image for the button when it is pressed
     */
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
    
    /**
     * Changes the behaivour of the button when it is clicked, hovered, or dragged.
     */
    private void mouseEffect(){
        // Runs the drag function code if a drag function is created with the constructor
        if (dragFunction != null && clicked){
            dragFunction.run();
        }
        // Checks if the mouse is pressed and the cursor is over the button
        if(Greenfoot.mousePressed(null) && cursor.getHoveredActors().contains(this)){
            setImage(pressedImg); // changes image
            clicked = true;
            playButtonSound(); 
            function.run(); // runs the function
        }
        // Checks when the cursor is over the button, but not pressed
        else if(!clicked && cursor.getHoveredActors().contains(this)){
            setImage(hoverImg);
            
        }
        // Checks for any click(button is unpressed) whether on the button or not
        else if(Greenfoot.mouseClicked(null)){
            //Checks if its on the button
            if(cursor.getHoveredActors().contains(this)){

                setImage(hoverImg); // sets the image back to hovered
            }
            clicked = false;
        }

        else if (!clicked){
            
            setImage(img); //  image goes back to default image
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
