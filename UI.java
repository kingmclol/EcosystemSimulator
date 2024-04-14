import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

 
public abstract class UI extends SuperActor
{
    protected Cursor cursor;
    
    private static GreenfootSound[] buttonSounds;
    private static int buttonSoundsIndex = 0;
    
    protected void addedToWorld(World w){
        cursor = getCursor();
    }

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
            buttonSounds[i] = new GreenfootSound("Button.mp3");
            buttonSounds[i].play();
            Greenfoot.delay(1);
            buttonSounds[i].stop(); 
        }
    }
}
