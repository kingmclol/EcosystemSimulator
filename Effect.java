import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * Write a description of class Effect here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class Effect extends SuperSmoothMover
{
    protected GreenfootImage image;
    
    protected void fade (int timeLeft, int totalFadeTime){
        double percent = (double)timeLeft / totalFadeTime;
        if (percent > 1.0) return;
        int newTransparency = (int)(percent * 255);
        image.setTransparency(newTransparency);
    }
    
}
