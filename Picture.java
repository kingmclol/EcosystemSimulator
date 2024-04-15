import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * Pictures are an Actor that simply exists to set its image as something. Thus, a picture.
 * 
 * @author Freeman Wang
 * @version 2024-04-14
 */
public class Picture extends UI
{
    /**
     * Create a Picture using the given file path, using the original image size.
     * @param filePath the file path to the image to use.
     */
    public Picture(String filePath) {
        setImage(new GreenfootImage(filePath));
    }
    /**
     * Create a Picture using the given file path, scaled to the width and height given.
     * @param filePath the file path to the image.
     * @param scaleWidth the width to scale the image to.
     * @param scaleHeight the height to scale the image to.
     */
    public Picture(String filePath, int scaleWidth, int scaleHeight) {
        GreenfootImage img = new GreenfootImage(filePath);
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
        GreenfootImage img = new GreenfootImage(filePath);
        int targetWidth = (int) Math.max(1, img.getWidth() * scaleWidthFactor); // don't let it go lower than 1 (may error if zero?)
        int targetHeight = (int) Math.max(1, img.getHeight() * scaleHeightFactor);
        img.scale(targetWidth, targetHeight);
        
        setImage(img);
    }
}
