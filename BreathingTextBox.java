import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * <p>The BreathingTextBox is a TextBox specifically made to cycle through transparency and opacity (opaqueity?).</p>
 * 
 * <p>When added to the world You will see it periodicly cycling transparnecy.</p>
 * 
 * <p>As such, some methods inherited from TextBox (such as isVisible() or isInvisible()) <strong>WILL NOT WORK.</stong></p>
 * 
 * @author Freeman Wang
 * @version 2024-04-06
 */
public class BreathingTextBox extends TextBox
{
    private int x; // x as in f(x)
    private double k; // Horizontal stretch
    /**
     * Act - do whatever the BreathingTextBox wants to do. This method is called whenever
     * the 'Act' or 'Run' button gets pressed in the environment.
     */
    /**
     * Creates a Breathing text box.
     * @param text The text to display.
     * @param size the font size.
     * @param textColor the color of the text.
     * @param backgroundColor The background color of the text. null for transparent.
     * @param period the period of each transparency cycle, in acts.
     */
    public BreathingTextBox(String text, int size, Color textColor, Color backgroundColor, int period) {
        this(size, textColor, backgroundColor, period);
        display(text);
    }
    /**
     * Creates a Breathing text box. Text not initialized at first.
     * @param size the font size.
     * @param textColor the color of the text.
     * @param backgroundColor The background color of the text. null for transparent.
     * @param period the period of each transparency cycle, in acts.
     */
    public BreathingTextBox(int size, Color textColor, Color backgroundColor, int period) {
        // The -1 for step is not used anywhere. just showing that it is useles
        super(size, textColor, backgroundColor, -1, 0); 
        x = 0;
        k = Math.PI/(period/2);
    }
    public void addedToWorld(World w) {
        resetCycle();
    }
    public void act()
    {
        /*
        Okay, so I want this textbox to "breathe." That is, it is cylcing through fading in and fading out.
        Sure, I can do it linearly, but nah. I want to do something unique and use a sinusoidal function to model how the
        transparency should change over time.
        
        Using the number of acts as the x variable, I can use the function
        
        255/2 * -cos(kx) + 255/2
        
        Factoring out 255/2,
        
        255/2 ( -cos(kx) + 1)
        To get a transparency that would fluctuate through 0 and 255 periodically.
        And to set the period, I need to change "k", since in this case, period = 2pi/k
        
        so for a period of 40 acts I set k = pi/20. Changing 20 into a variable, it is period/2.
        
        
         */
        x++;
        int nextTransparency = (int) Math.round(255/2d * (-1 * Math.cos(k * x) + 1));
        setTransparency(nextTransparency);
    }
    /**
     * Overrides to return true, since always visible basically.
     * Does not work as intended.
     */
    public boolean isVisible() {
        return true;
    }
    /**
     * Overrides to return false, since alwase visible basically. Does not work as intended.
     */
    public boolean isInvisible() {
        return false;
    }
    /**
     * Resets the cycle of the breathing.
     */
    public void resetCycle() {
        x = 0;
        setTransparency(0);
    }
}
