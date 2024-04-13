import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * Write a description of class Slider here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class Slider extends UI
{
    private GreenfootImage img;
    private Button button;
    private int width;
    private int sliderVal = 0;
    private int originX;
    private SuperTextBox labelText;
   
    private double incrementValue;
    private SuperTextBox textBox;
    public Slider(int width, int maxVal){
        this.width = width;
        this.incrementValue = (double )maxVal / width;
        img = new GreenfootImage(width, 20);
        
        img.fill();
        setImage(img);
        labelText = null;
        button = new Button(() -> onDrag(), () -> onDrag(), 25, 25);
        textBox = new SuperTextBox(String.valueOf(maxVal/2), Color.WHITE, Color.BLACK, new Font(24), true, width/2, 0, Color.BLACK);
    }
    public Slider(int width, int maxVal, String label){
        this.width = width;
        this.incrementValue = (double )maxVal / width;
        
        img = new GreenfootImage(width, 20);
        img.fill();
        setImage(img);
        button = new Button(() -> onDrag(), () -> onDrag(), 25, 25);
        labelText = new SuperTextBox(label, new Font(24), 150);
        textBox = new SuperTextBox(String.valueOf(maxVal/2), Color.WHITE, Color.BLACK, new Font(24), true, width/2, 0, Color.BLACK);
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
    private void onDrag(){
        sliderVal = cursor.getX();
        if(sliderVal > getX() + width/2){
            sliderVal = Math.min(cursor.getX(), getX() + width/2);
        }
        else if (sliderVal < getX() - width/2){
            sliderVal = Math.max(getX() - width/2, cursor.getX());
        }
        
        
        button.setLocation(sliderVal, getY());
        textBox.update(String.valueOf(getValue()));
    }
    public int getValue(){
        return (int)((incrementValue) * (sliderVal - originX));
    }
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
