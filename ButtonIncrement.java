import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * ButtonIncrement generates an object composed of a text box that displays an 
 * integer value and 2 buttons for changing that integer value. There are 2 buttons that can increment that values on either side of the text box.
 * 
 * @author Neelan Thurairajah
 * @version
 */
public class ButtonIncrement extends UI
{
    private int maxVal;
   
    private int width;
    private int height;
    private int textBoxWidth;
    
    private int value;
    private Button decrementButton;
    private Button incrementButton;
    private GreenfootImage img;
    private World world;
    private SuperTextBox textBox;
    private SuperTextBox labelText;
    /**
     * Simple constructor with no images
     * @param width         Total width of the entire object(buttons + textbox)
     * @param height        Height of the object
     * @param textBoxWidth  Width of the text box
     */
    public ButtonIncrement(int width, int height, int textBoxWidth, int maxVal){
        this.width = width;
        this.height = height;
        this.textBoxWidth = textBoxWidth;
        img = new GreenfootImage(width, height);
        img.setTransparency(0);
        img.setColor(Color.BLACK);
        setImage(img);
        decrementButton = new Button(this::decrementValue, (width - textBoxWidth)/2, height);
        incrementButton = new Button(this::incrementValue,(width - textBoxWidth)/2, height);
        value = 0;
        this.maxVal = maxVal;
        textBox = new SuperTextBox(String.valueOf(value), Color.WHITE, Color.BLACK, new Font(24), true, textBoxWidth, 0, Color.BLACK);
        
    }
    public ButtonIncrement(int width, int height, int textBoxWidth, int maxVal, String label, GreenfootImage leftButtonImg, GreenfootImage rightButtonImg){
        
        this.width = width;
        this.height = height;
        this.textBoxWidth = textBoxWidth;
        labelText = new SuperTextBox(label, new Font(24), 150);
  
        img = new GreenfootImage(width, height + 100);
        img.setTransparency(0);
        img.setColor(Color.BLACK);
        img.fill();
        setImage(img);
        decrementButton = new Button(this::decrementValue, (width - textBoxWidth)/2, height, leftButtonImg, leftButtonImg, leftButtonImg);
        incrementButton = new Button(this::incrementValue,(width - textBoxWidth)/2, height, rightButtonImg, rightButtonImg, rightButtonImg);
        value = 0;
        this.maxVal = maxVal;
        textBox = new SuperTextBox(String.valueOf(value), Color.WHITE, Color.BLACK, new Font(24), true, textBoxWidth, 0, Color.BLACK);
        
    }
    public ButtonIncrement(int width, int height, int textBoxWidth, int maxVal, String label, GreenfootImage[] leftButtonImg, GreenfootImage[] rightButtonImg){
        
        this.width = width;
        this.height = height;
        this.textBoxWidth = textBoxWidth;
        labelText = new SuperTextBox(label, new Font(24), 150);
  
        img = new GreenfootImage(width, height + 100);
        img.setTransparency(0);
        img.setColor(Color.BLACK);
        img.fill();
        setImage(img);
        decrementButton = new Button(this::decrementValue, (width - textBoxWidth)/2, height, leftButtonImg[0], leftButtonImg[1], leftButtonImg[2]);
        incrementButton = new Button(this::incrementValue,(width - textBoxWidth)/2, height, rightButtonImg[0], rightButtonImg[1], rightButtonImg[2]);
        value = 0;
        this.maxVal = maxVal;
        textBox = new SuperTextBox(String.valueOf(value), Color.WHITE, Color.BLACK, new Font(24), true, textBoxWidth, 0, Color.BLACK);
        
    }
    public void addedToWorld(World w){
        cursor = getCursor();
        w.addObject(decrementButton, (getX() - width/2) + (width - textBoxWidth)/4, getY());
        w.addObject(incrementButton, (getX() + width/2) - (width - textBoxWidth)/4, getY());
        w.addObject(textBox, getX(), getY());
        w.addObject(labelText, getX(), getY() - 60);
    }
    /**
     * Gets the integer value of the ButtonIncrement.
     * @return Integer value stored .
     */
    public int getValue(){
        return value;
    }
    /**
     * Increases the integer value by 1, as long as it is below the max value set.
     * 
     */
    public void incrementValue() {

        value = Math.min(maxVal, value + 1);

        textBox.update(String.valueOf(value));
        playButtonSound();
    }
    /**
     * Removes the object and all its related objects.
     */
    public void removeObject() {
        World w = getWorld();
        w.removeObject(decrementButton);
        w.removeObject(incrementButton);
        w.removeObject(textBox);
        w.removeObject(labelText);
        w.removeObject(this);
    }
    /**
     * Reduces the integer value by 1, as long as it is above 0.
     */
    
    public void decrementValue() {
        value = Math.max(0, value -1);
        textBox.update(String.valueOf(value));
        playButtonSound();
    }
    public void act()
    {

    }
}
