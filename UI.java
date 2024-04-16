import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * <p>UI is an abstract class that is used for all the User Interface and visual elements in the World. Any element that 
 * the user interacts with directly using the mouse inherits from this class.<p>
 * 

 * 
 * @author Neelan Thurairajah
 * @version
 */
public abstract class UI extends SuperActor
{


    protected Cursor cursor;
    private static GreenfootSound[] buttonSounds;
    private static int buttonSoundsIndex = 0;
    

    /**
     * Stores the cursor in a variable upon a UI element being added to the world.
     * @param w The current world the object is being added to.
     */
    protected void addedToWorld(World w){
        cursor = getCursor();
    }
    /**
     * Retrieves the cursor object using the static method from the world when called.
     * @return The cursor object
     */
    protected Cursor getCursor()
    {
        return CursorWorld.getCursor();
    }
    public void playButtonSound() {
        buttonSounds[buttonSoundsIndex].setVolume(85);
        buttonSounds[buttonSoundsIndex].play();
        buttonSoundsIndex++; 
        if (buttonSoundsIndex >= buttonSounds.length) {
            buttonSoundsIndex = 0; 
        }
    }
    public static void init() {
        buttonSoundsIndex = 0;
        buttonSounds = new GreenfootSound[48];
        for (int i = 0; i < buttonSounds.length; i++) {
            buttonSounds[i] = new GreenfootSound("Button.wav");
            buttonSounds[i].play();
            Greenfoot.delay(1);
            buttonSounds[i].stop(); 
        }
    }
}
