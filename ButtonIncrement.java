import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * This class generates an object composed of a text box that displays an 
 * integer value. There are 2 buttons that can increment that values on either side.
 * 
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

    private World world;
    private SuperTextBox textBox;
    private GreenfootImage img;
    public ButtonIncrement(int width, int height, int textBoxWidth, int maxVal){
        this.width = width;
        this.height = height;
        this.textBoxWidth = textBoxWidth;
        decrementButton = new Button(this::decrementValue, (width - textBoxWidth)/2, height);
        incrementButton = new Button(this::incrementValue,(width - textBoxWidth)/2, height);
        value = 0;
        this.maxVal = maxVal;
        textBox = new SuperTextBox(String.valueOf(value), Color.WHITE, Color.BLACK, new Font(27), true, textBoxWidth, 0, Color.BLACK);
        
    }
    public void addedToWorld(World w){
        cursor = getCursor();
        w.addObject(decrementButton, (getX() - width/2) + (width - textBoxWidth)/4, getY());
        w.addObject(incrementButton, (getX() + width/2) - (width - textBoxWidth)/4, getY());
        w.addObject(textBox, getX(), getY());
    }
    public int getValue(){
        return value;
    }
    public void incrementValue() {
        textBox.update(String.valueOf(++value));
    }
    public void decrementValue() {
        value = Math.max(0, value -1);
        textBox.update(String.valueOf(value));
    }
    public void act()
    {
        // if(Greenfoot.mousePressed(null) && cursor.getHoveredActors().contains(decrementButton) && value > 0){
            // value--;
            // textBox.update(String.valueOf(value));
            
        // }
        // else if(Greenfoot.mousePressed(null) && cursor.getHoveredActors().contains(incrementButton) && value < 50){
            // value++;
            // textBox.update(String.valueOf(value));
        // }
    }
}
