import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * Write a description of class TextBox here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class TextBox extends UI
{
    /**
     * Act - do whatever the TextBox wants to do. This method is called whenever
     * the 'Act' or 'Run' button gets pressed in the environment.
     */
    private int initialTransparency;
    protected GreenfootImage img;
    protected FadeState state;
    protected int size, step;
    protected Color textColor, backgroundColor;
    protected boolean removeWhenTransparent;
    public TextBox(String text, int size, Color textColor, Color backgroundColor, int step, int initialTransparency) {
        this(size, textColor, backgroundColor, step, initialTransparency);
        display(text);
    }
    public TextBox(int size, Color textColor, Color backgroundColor, int step, int initialTransparency) {
        this.size = size;
        this.textColor = textColor;
        this.backgroundColor = backgroundColor;
        this.initialTransparency = initialTransparency;
        this.step = Math.abs(step); // Please do not give a negative value for step. But now it doesn't amtter i guess
        removeWhenTransparent = false;
    }
    /**
     * Sets this TextBox to display the string of text given.
     */
    public void display(String text) {
        img = new GreenfootImage(text, size, textColor, backgroundColor);
        img.setTransparency(initialTransparency);
        setImage(img);
        state = FadeState.IN;
    }
    public void act()
    {
        if (state == FadeState.IN) { // Fading in.
            fade(step); // So I fade... in.
            if (img.getTransparency() >= 255) { // Max transparency.
                state = FadeState.VISIBLE; // Update accordingly.
            }
        }
        else if (state == FadeState.OUT) { // Fading out.
            fade(step * -1); // So I fade... out. wow.
            if (img.getTransparency() <= 0) { // transparent.
                state = FadeState.TRANSPARENT; // Update accordingly.
                if (removeWhenTransparent) {
                    getWorld().removeObject(this);
                }
            }
        }
    }
    /**
     * Fade (change the transparency of the image) by the given amount. You can fade out by giving a negative value.
     * @param step The change in transparency. Positive for increase, negative for decrease.
     */
    protected void fade(int step) {
        int nextTransparency = img.getTransparency() + step;
        nextTransparency = Utility.clamp(nextTransparency, 0, 255); // Avoid giving invalid transparencies.
        img.setTransparency(nextTransparency);
        setImage(img);
    }
    /**
     * Directly set the transparency of the textbox.
     * @param transparency The transparency to set to.
     */
    protected void setTransparency(int transparency) {
        int nextTransparency = Utility.clamp(transparency, 0, 255);
        img.setTransparency(nextTransparency);
        setImage(img);
    }
    /**
     * Returns true if, and only if, the TextBox is completely opaque (transparency = 255).
     */
    public boolean isVisible() {
        return state == FadeState.VISIBLE;
    }
    /**
     * Returns if, and only if, the TextBox is completely transparent (transparency = 0).
     */
    public boolean isInvisible() {
        return state == FadeState.TRANSPARENT;
    }
    /**
     * Makes the textbox begin to fade out, removing itself once fully transparent.
     */
    public void fadeOut() {
        state = FadeState.OUT;
        removeWhenTransparent = true;
    }
}
