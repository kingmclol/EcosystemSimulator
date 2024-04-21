import greenfoot.*;
/**
 * Random stuff, added as needed.
 * 
 * @author Freeman Wang
 * @version 2024-03-01
 */
public class Utility  
{
    /**
     * Rounds the value. Does not work with rounding negative numbers.
     */
    public static int round(double val) {
        return (int) (val + 0.5);
    }
    /**
     * Probably rounds the given double val to a given decimal point accuracy.
     * @param val the double to round.
     * @param how many decimal points to round to.
     */
    public static double roundToPrecision(double val, int decimalPoints) {
        return Math.round(val * Math.pow(10, decimalPoints)) / Math.pow(10, decimalPoints);
    }
    /**
     * Restricts val to be inbetween min and max only
     * @param val The value to clamp.
     * @param min the minimum value that val can be.
     * @param max the maximum value that val can be.
     */
    public static int clamp(int val, int min, int max) {
        return Math.max(min, Math.min(val, max));
    }
    
    public static GreenfootImage drawSnow(int width, int height, int density){
        GreenfootImage temp = new GreenfootImage (width, height);
        for (int i = 0; i < density * 50; i++){
            for (int j = 0; j < 100; j++){ // draw 100 circles
                int randSize;
                temp.setColor (Color.WHITE);

                int randX = Greenfoot.getRandomNumber (width);
                int randY = Greenfoot.getRandomNumber (height);

                int tempVal = Greenfoot.getRandomNumber(250);
                if (tempVal >= 1){
                    temp.drawRect (randX, randY, 0, 0);
                }else{
                    randSize = Greenfoot.getRandomNumber (2) + 2;
                    temp.fillOval (randX, randY, randSize, randSize);
                }
            }
        }
        return temp;
    }
    public static GreenfootImage drawRain(int width, int height, int density){
        GreenfootImage temp = new GreenfootImage (width, height);
        for (int i = 0; i < density * 50; i++){
            for (int j = 0; j < 100; j++){ // draw 100 circles
                int randSize;
                temp.setColor (Color.BLUE);

                int randX = Greenfoot.getRandomNumber (width);
                int randY = Greenfoot.getRandomNumber (height);

                int tempVal = Greenfoot.getRandomNumber(250);
                if (tempVal >= 1){
                    temp.drawRect (randX, randY, 0, 0);
                }else{
                    randSize = Greenfoot.getRandomNumber (2) + 2;
                    temp.fillOval (randX, randY, randSize, randSize);
                }
            }
        }
        return temp;
    }
    /**
     * Given a base string, the file extension, and maximum int, return an GreenfootImage[] array that contains
     * the resulting animation.
     * @param baseString the file path. E.g. "images/animation/tile_grass"
     * @param fileExtension the extension of the file. E.g. ".png"
     * @param numFrames The number of frames in the animation
     * 
     * @return A GreenfootImage[] that contains the greenfoot Images in the animation
     */
    public static GreenfootImage[] createAnimation(String baseString, String fileExtension, int numFrames) {
        GreenfootImage[] animation = new GreenfootImage[numFrames];
        for (int i = 1; i <= numFrames; i++) {
            animation[i-1] = new GreenfootImage(baseString + i + fileExtension);
        }
        return animation;
    }
    /**
     * Given a base string, the file extension, and maximum int, scaleWidth and scale height, return an GreenfootImage[] array that contains
     * the resulting animation.
     * @param baseString the file path. E.g. "images/animation/tile_grass"
     * @param fileExtension the extension of the file. E.g. ".png"
     * @param numFrames The number of frames in the animation
     * @param scaleWidth the width to scale each image to
     * @param scaleHeight the height to scale each image to
     * 
     * @return A GreenfootImage[] that contains the greenfoot Images in the animation
     */
    public static GreenfootImage[] createAnimation(String baseString, String fileExtension, int numFrames, int scaleWidth, int scaleHeight) {
        GreenfootImage[] animation = new GreenfootImage[numFrames];
        for (int i = 1; i <= numFrames; i++) {
            animation[i-1] = new GreenfootImage(baseString + i + fileExtension);
            animation[i-1].scale(scaleWidth, scaleHeight);
        }
        return animation;
    }
    /**
     * Returns a random double from min to max.
     * @param min The minimum value.
     * @param max the maximum value.
     * @return a random double from min to max.
     */
    public static double randomDouble(double min, double max) {
        return Math.random() * (max-min) + min;
    }
    /**
     * Return the given double as positive or negative, randomly.
     * @param a the value to consier.
     * @return a, but either positive or negative (50% chance of each)
     */
    public static double randomSign(double a) {
        return Greenfoot.getRandomNumber(2) == 0 ? a * -1 : a;
    }
    /**
     * Return a Vector whose components are within the magnitude of min and max, and can be positive or negative.
     * @param min The min value for a component.
     * @param max the max value for a component.
     * @return a vector with x and y components within the range, randomly + or -.
     */
    public static Vector randomVector(double min, double max) {
        return new Vector(randomSign(randomDouble(min, max)), randomSign(randomDouble(min, max)));
    }
    /**
     * Return a Vector whose x and y components are within the magnitude of their respective x and y values, pos/neg randomly.
     * @param xMin the minimum value for x component.
     * @param xMax the maximum value for x component.
     * @param yMin the minium value for y component.
     * @param yMax the maximum value for y component.
     * @return the random generated vector.
     */
    public static Vector randomVector(double xMin, double xMax, double yMin, double yMax) {
        return new Vector(randomSign(randomDouble(xMin, xMax)), randomSign(randomDouble(yMin, yMax)));
    }
}
