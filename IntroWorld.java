import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * ==================== ECOSYSTEM SIMULATOR ====================
 * [story description]
 * ======== INSTRUCTIONS =========
 * [what to do]
 * ======== FEATURES ========
 * [cool stuff]
 * ======= KNOWN BUGS =======
 * [everything]
 * 
 * 
 * ==================================
 * The IntroWorld is the first World that the player sees. It's only use is to look cool before the Player goes to the next World.
 * 
 * @author Freeman Wang
 * @version 2024-04-20
 */
public class IntroWorld extends CursorWorld
{
    private GreenfootSound introWorldMusic; 
    BreathingTextBox promptBox;
    TextBox title;
    private int actCount;
    // Images from /u/Voidentir at https://old.reddit.com/r/DigitalArt/comments/1akfavq/my_old_landscape_artworks/
    private static String[] backgroundImages = new String[] {
        "landscape1.png",
        "landscape2.png",
        "landscape3.png"
    };
    private Picture currentImage;
    private Picture nextImage;
    private int index;
    //private Vector titleAnchor, promptAnchor, worldCenter;
    //private static final double DELTA_FACTOR = 0.1; // Scrapped, for making textboxs move with cursor.
    /**
     * Create an intro world where the music <strong>will not</strong> start automatically
     */
    public IntroWorld()
    {
        super();
        promptBox = new BreathingTextBox("PRESS (L) TO START", 52, Color.RED, null, 120);
        //promptAnchor = new Vector(getWidth()/2, getHeight()/2 + 300);
        addObject(promptBox, getWidth()/2, getHeight()/2+300);
        
        title = new TextBox("ECOSYSTEM SIMULATOR", 86, Color.BLACK, null, 2, 0);
        //titleAnchor = new Vector(getWidth()/2, 80);
        addObject(title, getWidth()/2, 80);
        
        
        introWorldMusic = new GreenfootSound("Introworld.mp3");
        introWorldMusic.setVolume(30);
        
        actCount = 0;
        index = 0;
        //worldCenter = new Vector(getWidth()/2, getHeight()/2);
        
        currentImage = new Picture(backgroundImages[index]);
        currentImage.setTranslation(Utility.randomVector(0.5, 1, 0.2, 0.5));
        addObject(currentImage, getWidth()/2, getHeight()/2);
        
        nextImage = new Picture(backgroundImages[nextIndex()]);
        nextImage.setTranslation(Utility.randomVector(0.5, 1, 0.2, 0.5));
        nextImage.setTransparency(0); // hidden for now.
        
        Greenfoot.setSpeed(50); // Control speed to 50.
    }
    /**
     * <p>Create an Introworld where music <strong>will</strong> start automatically.</p>
     * <p>The boolean passed has no effect. Do whatever you want.</p>
     * <p>Basically, the normal constructor without any parameters will not play the music first, so Greenfoot can
     * construct the World normally with the music starting only when the player presses the Run button. However,
     * when returning to the intro world from another world, the Run button is never pressed (since it's already running),
     * thus the started() would never run, leading to a music-less Intro world.</p>
     * <p>So, I simply made another constructor to overload it and then simply use that constructor instead, which would run the music
     * automatically<p>
     * @param overloadingConstructorSinceNoTimeForBetterSolution This is one of the parameters of all time.
     */
    public IntroWorld(boolean overloadingConstructorSinceNoTimeForBetterSolution) {
        this(); // Run the real constructor.
        introWorldMusic.playLoop(); // Run this one (1) line of code.
    }
    public void act() {
        // parallax effect for text boxes, unused.
        // if (getMousePos() != null) { // if the mouse is null, do not update their positions.
            // promptBox.setLocation(promptAnchor.add(getMouseDelta().scale(DELTA_FACTOR)));
            // title.setLocation(titleAnchor.add(getMouseDelta().scale(DELTA_FACTOR)));
        // }
        if (++actCount >= 600) { // every 10 seconds, change background image.
            switchBackgroundImage();
            actCount = 0;
        }
        if ("l".equals(Greenfoot.getKey())) { // once L is pressed, move to the next world.
            Greenfoot.setWorld(new StoryWorld());
            introWorldMusic.stop(); 
        }
    }
    /**
     * Switches the background image in a cool way.
     */
    private void switchBackgroundImage() {
        currentImage.fadeOut(1); // make the current image begin to disappear.
        addObject(nextImage,getWidth()/2, getHeight()/2); // add the next image.
        nextImage.fadeIn(1);
        // update reference of current image to the next image, as the current image will remove itself fro the world
        // by itself, so need to keep it anymore.
        currentImage = nextImage;
        nextImage = new Picture(backgroundImages[nextIndex()]); // have next image prepared.
        nextImage.setTransparency(0); // set its transparency to 0 so it can fade in.
        nextImage.setTranslation(Utility.randomVector(0.5, 1, 0.2, 0.5));
    }
    /**
     * Returns the next index for the next picture to use.
     */
    private int nextIndex() {
        index = (index + 1)%backgroundImages.length;
        return index;
    }
    public void started() {
        introWorldMusic.playLoop();
    }
    public void stopped() {
        introWorldMusic.pause(); 
    }
    // /**
     // * Gets the position of the mouse cursor. Returns null if mouse is null.
     // * @return the positino of the cursor.
     // */
    // private Vector getMousePos() {
        // MouseInfo mouse = Greenfoot.getMouseInfo();
        // if (mouse == null) return null;
        // else return new Vector(mouse.getX(), mouse.getY());
    // }
    // /**
     // * Returns a vector pointing from the world center to the mouse position. Returns a new vector of 
     // * zero magnitude if mouse is null.
     // */
    // private Vector getMouseDelta() {
        // Vector mousePos = getMousePos();
        // if (mousePos == null) return new Vector(0,0); // no mouse, return zero magnitude vector
        // return worldCenter.distanceFrom(getMousePos()); // return vector pointing from world center to mouse.
    // }
}
