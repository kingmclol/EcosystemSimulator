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
    /**
     * Simple Constructor with black color and default buttons
     * @param width     Width of the slider bar
     * @param maxValue  The maximum value the slider can reach
     */
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
    /**
     * Labelled Constructor with custom images for buttons and more value options
     * @param width     Width of the slider bar
     * @param minValue  The minimum value the slider can reach
     * @param maxValue  The maximum value the slider can reach
     * @param label     The text box label for the slider
     */
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
        if(sliderVal != 0){ // Check if the slider value is not the default value 
            w.addObject(button, sliderVal, getY()); // Adds the object with the button in the proper position
        }
        else{ // If slider has just been constructed and has default values
            w.addObject(button, getX(), getY()); // by default, button is at the center
        }
        
        if(labelText != null){ // Checks if the label text exists
            w.addObject(labelText, getX(), getY() - 50);
        }
        // The x-value of the slider at its lowest value (the leftmost value)
        originX = getX() - width/2; 
    }
    /**
     * Upon the button being dragged, the position of the button and integer value changes.
     */
    private void onDrag(){
        sliderVal = cursor.getX();
        // Restricts cursor x-values to be in range of the slider,
        // so if cursor goes past slider, the slider button will still be within the slider
        if(sliderVal > getX() + width/2){
            sliderVal = Math.min(cursor.getX(), getX() + width/2);
        }
        else if (sliderVal < getX() - width/2){
            sliderVal = Math.max(getX() - width/2, cursor.getX());
        }
        
        //Sets the button location to the position of cursor
        button.setLocation(sliderVal, getY());
        // Calculates the value based on the position of the cursor
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
