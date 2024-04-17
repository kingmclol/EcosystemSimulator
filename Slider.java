import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * Slider is an object that has a button that can be dragged along a bar to change a integer value.
 * 
 * 
 * @author Neelan Thurairajah
 * @version (a version number or a date)
 */
public class Slider extends UI
{
    private GreenfootImage img;
    private Button button;
    private int value;
    private int width;
    private int sliderVal = 0;
    private int originX;
    private SuperTextBox labelText;
    private int minVal;
    private double incrementValue;
    private SuperTextBox textBox;
    public Slider(int width, int maxVal){
        this.width = width;
        this.incrementValue = (double )maxVal / width;
        img = new GreenfootImage(width, 15);
        GreenfootImage button1 = new GreenfootImage("images/sliderButton.png");
        GreenfootImage button2 = new GreenfootImage("images/sliderButtonHovered.png");
        GreenfootImage button3 = new GreenfootImage("images/sliderButtonPressed.png");
        img.fill();
        setImage(img);
        labelText = null;
        button = new Button(() -> onDrag(), () -> onDrag(), 25, 25, button1, button2, button3);
        textBox = new SuperTextBox(String.valueOf(maxVal/2), Color.WHITE, Color.BLACK, new Font(24), true, width/2, 0, Color.BLACK);
    }
    public Slider(int width, int minVal, int maxVal, String label){
        this.width = width;
        this.incrementValue = (double )(maxVal- minVal) / width;
        this.minVal = minVal;
        GreenfootImage button1 = new GreenfootImage("images/sliderButton.png");
        GreenfootImage button2 = new GreenfootImage("images/sliderButtonHovered.png");
        GreenfootImage button3 = new GreenfootImage("images/sliderButtonPressed.png");
        img = new GreenfootImage(width, 15);
        img.fill();
        setImage(img);
        button = new Button(() -> onDrag(), () -> onDrag(), 25, 25, button1, button2, button3);
        labelText = new SuperTextBox(label, new Font(20), 150);
        value = (maxVal-minVal)/2 + minVal;
        textBox = new SuperTextBox(String.valueOf(value), Color.WHITE, Color.BLACK, new Font(24), true, width/2, 0, Color.BLACK);
    }
    public void addedToWorld(World w){
        cursor = getCursor();
        w.addObject(textBox, getX(), getY() + 50);
        if(sliderVal != 0){
            w.addObject(button, sliderVal, getY());
        }
        else{
            w.addObject(button, getX(), getY());
        }
        w.addObject(button, getX(), getY());
        if(labelText != null){
            w.addObject(labelText, getX(), getY() - 50);
        }
        
        originX = getX() - width/2;
    }
    /**
     * Upon the button being dragged, the position of the button and integer value changes.
     */
    private void onDrag(){
        sliderVal = cursor.getX();
        if(sliderVal > getX() + width/2){
            sliderVal = Math.min(cursor.getX(), getX() + width/2);
        }
        else if (sliderVal < getX() - width/2){
            sliderVal = Math.max(getX() - width/2, cursor.getX());
        }
        
        
        button.setLocation(sliderVal, getY());
        value = (int)((incrementValue) * (sliderVal - originX)) + minVal;
        textBox.update(String.valueOf(getValue()));
        
    }
    /**
     * Gets the value stored in the slider.
     * @return The integer value of the slider.
     */
    public int getValue(){
        
        return value;
    }
    /**
     * Removes the object and all of its related objects.
     */
    public void removeObject(){
        World w = getWorld();
        w.removeObject(button);
        w.removeObject(textBox);
        if(labelText != null){
            w.removeObject(labelText);
        }
        
        
        w.removeObject(this);
    }
    public void act()
    {
        // Add your action code here.
    }
}
