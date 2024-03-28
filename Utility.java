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
}
