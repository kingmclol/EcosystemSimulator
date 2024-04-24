import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * ==================== ECOSYSTEM SIMULATOR ====================
 * What a catastrophe. The World's God has given up on their job and the player is tasked to 
 * take their place! Just for a little while, though.
 * 
 * With God leaving you to your devices, you are able to create an World for yourself! Are you going to be a 
 * sadistic person and create animals solely to let them die, or attempt to make a sustainable ecosystem that makes
 * everyone happy? The choice is yours.
 * ======== INSTRUCTIONS =========
 * After progressing through the introduction, you will be presented some settings.
 *  "# of Days": How long the simulation should last for. It is the target day number for the good ending.
 *  "[animal species]": The animal species being considered for the following settings:
 *      "Starting Amount": How many animals of the current species should spawn initially. Range from [0, 50]
 *      "View Radius": The radius of the circle, in pixels, that the current animal species can see.
 * 
 * - After setting your settings (and having a minimum of 1 animal starting), you will be in the 
 *   DrawWorld, where you are to draw out your desired environment.
 * - Use the tile selector (at the right side) to change your "ink" (tile type), use a preset, or start the simulation.
 * - If you are too lazy, or want to start from a preset, you can do so using the bottom two buttons in the tile selector.
 * - Once you are ready, and the board does not contain any empty (gray) tiles, press the start button.
 * ======== FEATURES ========
 * - Cool title screen
 * - "Storyline"
 * - A SKIP BUTTON YAY
 * - Ability to draw out your own environment
 * - Ability to use presets
 * - Two endings
 * - World events, NOT effects (rain, snowstorm, nights)
 *   - Nights are purely visual.
 *   - Rain doubles grow speed of berries, grass, and probability of seeds existing (through any method)
 *   - Snowstorms decrease the vision radius of animals.
 * - Summary world of the world's progression over time.
 * - Animal pathfinding (e.g. rabbits will not go on certain tiles.)
 * - Current stats tracking (# of animals, time, day num)
 * - Animated tiles and animals
 * 
 * The simulation focuses on observing interactions between the animals and the tiles.
 * - Goats eat berries in BushTiles. When BushTiles have 0 berries left, they enter a cooldown period before
 *   berries are available again. Additionally, BushTiles have a chance of turning into a GrassTile when berries depelted.
 * - Rabbits eat grass in GrassTiles.
 * - Wolves target Goats and Rabbits to eat them.
 * - Vultures only target dead animals.
 * - Bush tiles and tree tiles have a chance to drop a seed into a nearby grass tile. The seed, once
 *   fully grown, will turn the grass tile into the respective seed tile.
 * - Grass tiles have a chance to self-seed into a bush or tree tile.
 * ======= KNOWN BUGS =======
 * - Sound files may error.
 * - Animals may sometimes get stuck in the corner of a tile which is higher than what they can walk to.
 * - The top-left corner may be highlighted for no apparent reason during starting of DrawWorld.
 * ======= CREDITS ======
 * SuperSmoothMover, SuperDisplayLabel, SuperTextBox by Jordan Cohen.
 * IntroWorld images by from /u/Voidentir from https://old.reddit.com/r/DigitalArt/comments/1akfavq/my_old_landscape_artworks/
 * Traumatized sonic image from imgflip
 * Picture of a kitten from Wikipedia
 * Background for the Settings World from https://www.hiclipart.com/free-transparent-background-png-clipart-ytatg
 * Wood pixel art image for buttons and other UI elements from https://www.reddit.com/r/PixelArt/comments/1351ptn/looked_up_pixel_art_wood_texture_and_couldnt_find/
 * All Sounds:
 * IntroWorld music: https://www.youtube.com/watch?v=nW5bQ39Ldp8
 * StoryWorld and ending world music: https://www.youtube.com/watch?v=KoxxBeH7J3Q&t=1s
 * SettingsWorld music: https://www.youtube.com/watch?v=55TD9gnMt3Y
 * DrawWorld music: https://www.youtube.com/watch?v=DSWYAclv2I8
 * SimulationWorld music: https://www.youtube.com/watch?v=xNN7iTA57jM&t=4003s
 * SummaryWorld music: https://www.youtube.com/watch?v=waKumDkYrDY&list=PLw9AcE2IomDM_7XmIy0m2v9_QzbaE-Vrj&index=4 
 * Rain sound: https://www.youtube.com/watch?v=C-hzP3mOBGY
 * Snowstorm sound: https://www.youtube.com/watch?v=KqJ37MVf16Y
 * Goat sound: https://www.youtube.com/watch?v=WfMiMLA56z4
 * Vulture sound: https://www.youtube.com/watch?v=N5bXcr9OTwE
 * Wolf sound: https://www.youtube.com/watch?v=1BwXK3L7OaQ
 * Button sound: https://www.youtube.com/watch?v=BZvS2Bno8R4
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
        promptBox = new BreathingTextBox("PRESS [L] TO START", 52, Color.RED, null, 120);
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
            introWorldMusic.stop(); 
            Greenfoot.setWorld(new StoryWorld());
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
