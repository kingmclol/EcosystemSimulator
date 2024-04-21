import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * Pictures are an Actor that simply exists to set its image as something. Hm. Maybe I should say that it sets a picture
 * as its image. Wow!
 * 
 * @author Freeman Wang
 * @version 2024-04-14
 */
public class Picture extends UI
{
    private FadeState state;
    private GreenfootImage img;
    private int step;
    private Vector displacement;
    /**
     * Create a Picture using the given file path, using the original image size.
     * @param filePath the file path to the image to use.
     */
    public Picture(String filePath) {
        img = new GreenfootImage(filePath);
        setImage(img);
    }
    /**
     * Create a Picture using the given file path, scaled to the width and height given.
     * @param filePath the file path to the image.
     * @param scaleWidth the width to scale the image to.
     * @param scaleHeight the height to scale the image to.
     */
    public Picture(String filePath, int scaleWidth, int scaleHeight) {
        img = new GreenfootImage(filePath);
        img.scale(scaleWidth, scaleHeight);
        setImage(img);
    }
    /**
     * Create a picture using the given file path, where its original height and width are multiplied
     * by the given factors.
     * @param filePath the file path to the image.
     * @param scaleWidthFactor the factor to multiply the width by.
     * @param scaleHeightFactor the factor to multiply the height by.
     */
    public Picture(String filePath, double scaleWidthFactor, double scaleHeightFactor) {
        img = new GreenfootImage(filePath);
        int targetWidth = (int) Math.max(1, img.getWidth() * scaleWidthFactor); // don't let it go lower than 1 (may error if zero?)
        int targetHeight = (int) Math.max(1, img.getHeight() * scaleHeightFactor);
        img.scale(targetWidth, targetHeight);
        
        setImage(img);
    }
    public void act() {
        if (displacement != null) {
            displace(displacement);
        }
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
                getWorld().removeObject(this);
            }
        }
    }
    /**
     * Fade (change the transparency of the image) by the given amount. You can fade out by giving a negative value.
     * @param step The change in transparency. Positive for increase, negative for decrease.
     */
    protected void fade(int step) {
        int nextTransparency = getImage().getTransparency() + step;
        nextTransparency = Utility.clamp(nextTransparency, 0, 255); // Avoid giving invalid transparencies.
        img.setTransparency(nextTransparency);
        setImage(img);
    }
    /**
     * Directly set the transparency of the picture.
     * @param transparency The transparency to set to.
     */
    protected void setTransparency(int transparency) {
        int nextTransparency = Utility.clamp(transparency, 0, 255);
        img.setTransparency(nextTransparency);
        setImage(img);
    }
    /**
     * Makes the image attempt to fade into 255 transparency. Set the step for transparency change
     * per act. Make sure that you set the transparency of the image to 0 or something first.
     * @param step How much transparency to add per act.
     */
    public void fadeIn(int step) {
        state = FadeState.IN;
        this.step = step;
    }
    /**
     * Makes the image attempt to fade out into 0 transparency, removing itself from the world.
     * @param step how much transparency to lose per act.
     */
    public void fadeOut(int step) {
        state = FadeState.OUT;
        this.step = step;
    }
    /**
     * Makes the picture apply the displacement every act.
     * @param displacement The displacement to apply per act.
     */
    public void setTranslation(Vector displacement) {
        this.displacement = displacement;
    }
}
