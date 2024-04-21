import greenfoot.*;
/**
 * <p>Animal Selector allows for a selector with buttons to switch between animals and 
 * various settings in the SettingsWorld.</p> 
 * <p>There are 2 buttons that are used to cycle between the 4 animals
 *  in the simulation to adjust their respective settings.</p>
 * <p>There is a ButtonIncrement object for each animal type used for changing and storing values 
 * related to starting numbers for the simulation. There is also a Slider object for each animal type for
 * changing the starting energy for the simulation.</p>
 */
public class AnimalSelector extends UI
{
    private UIAnimal[] animals = {new UIAnimal("rabbit"), new UIAnimal("goat"), new UIAnimal("wolf"), new UIAnimal("vulture")};
    private Button decrementButton;
    private Button incrementButton;
    private GreenfootImage img;
    private ButtonIncrement[] buttonIncrementers;
    private Slider[] sliders;
    private int animalIndex;
    
    public AnimalSelector(){
        GreenfootImage leftButtonImg = new GreenfootImage("images/incrementLeft.png");
        GreenfootImage leftButtonImg1 = new GreenfootImage("images/incrementLeftHovered.png");
        GreenfootImage leftButtonImg2 = new GreenfootImage("images/incrementLeftPressed.png");
    
        GreenfootImage rightButtonImg = new GreenfootImage("images/incrementRight.png");
        GreenfootImage rightButtonImg1 = new GreenfootImage("images/incrementRightHovered.png");
        GreenfootImage rightButtonImg2 = new GreenfootImage("images/incrementRightPressed.png");
        GreenfootImage [] leftButtonImgs = {leftButtonImg, leftButtonImg1, leftButtonImg2};
        GreenfootImage [] rightButtonImgs = {rightButtonImg, rightButtonImg1, rightButtonImg2};

        img = new GreenfootImage(50, 50);
        img.setColor(new Color(0, 0, 0, 0));
        img.fill();
        setImage(img);
        animalIndex = 0;
        buttonIncrementers = new ButtonIncrement[4];
        sliders = new Slider[4];
        for (int i = 0; i < buttonIncrementers.length; i++){
            buttonIncrementers[i] = new ButtonIncrement(225, 50, 125, 15, "Starting Amount", leftButtonImgs, rightButtonImgs);
            
        }
        for(int i = 0; i < 3; i ++){
            sliders[i] = new Slider(250, 100, 500, "View Distance");
            leftButtonImgs[i] = new GreenfootImage(leftButtonImgs[i]);
            leftButtonImgs[i].scale(75, 75);
            rightButtonImgs[i] = new GreenfootImage(rightButtonImgs[i]);
            rightButtonImgs[i].scale(75, 75);
        }
        sliders[3] = new Slider(250, 300, 1100, "View Distance");
        decrementButton = new Button(this::decrementValue, 100, 100, leftButtonImgs[0], leftButtonImgs[1], leftButtonImgs[2]);
        incrementButton = new Button(this::incrementValue,100, 100, rightButtonImgs[0], rightButtonImgs[1], rightButtonImgs[2]);
    }

    public void addedToWorld(World w){
        
        w.addObject(animals[animalIndex], getX(), getY() - 10);
        w.addObject(buttonIncrementers[animalIndex], getX(), getY() - 125);
        w.addObject(sliders[animalIndex], getX(), getY() + 150);
        w.addObject(decrementButton, getX() - 100, getY());
        w.addObject(incrementButton, getX() + 100, getY());
    }
    /**
     * Cycles forward through the types of animals, switching the animal type to the next one. 
     */
    private void incrementValue(){
        // gets the world for removing objects
        World w = getWorld();
        // removes all current objects related to specific animal type
        w.removeObject(animals[animalIndex]);
        sliders[animalIndex].removeObject();
        buttonIncrementers[animalIndex].removeObject();
        // cycles through the animal indexes to the next one, going back to 0 if it is 3
        animalIndex = (animalIndex+1) % 4;
        // adds the new objects for that specific animal type
        w.addObject(sliders[animalIndex], getX(), getY() + 150);
        w.addObject(animals[animalIndex],getX(), getY() - 10);
        w.addObject(buttonIncrementers[animalIndex], getX(), getY() - 125);
    }
    /**
     * Cycles backward through the types of animals, switching the animal type to the last one. 
     */
    private void decrementValue(){ 
        // gets the world for removing objects
        World w = getWorld();
        // removes all current objects related to specific animal type
        w.removeObject(animals[animalIndex]);
        sliders[animalIndex].removeObject();
        buttonIncrementers[animalIndex].removeObject();
        // cycles through the animal indexes to the previous one, going  to 3 if it is 0
        animalIndex = (animalIndex+ 3) % 4;
        // adds the new objects for that specific animal type
        w.addObject(sliders[animalIndex], getX(), getY() + 150);
        w.addObject(animals[animalIndex], getX(), getY() - 10);
        w.addObject(buttonIncrementers[animalIndex],  getX(), getY() - 125);
    }
    /**
     * Gets the number of rabbits to spawn at the beginning of the simulation. 
     * @return Number of rabbits.
     */
    public int getNumOfRabbits(){
       return buttonIncrementers[0].getValue();
    }
    /**
     * Gets the number of goats to spawn at the beginning of the simulation. 
     * @return Number of goats.
     */
    public int getNumOfGoats(){
        return buttonIncrementers[1].getValue();
    }
    /**
     * Gets the number of wolves to spawn at the beginning of the simulation. 
     * @return Number of wolves.
     */
    public int getNumOfWolves(){
        return buttonIncrementers[2].getValue();
    }
    /**
     * Gets the number of vultures to spawn at the beginning of the simulation. 
     * @return Number of vultures.
     */
    public int getNumOfVultures(){
        return buttonIncrementers[3].getValue();
    }
    /**
     * Gets the energy value of rabbits to spawn with at the beginning of the simulation. 
     * @return Starting energy value of a vulture.
     */
    public int getEnergyOfRabbits(){
        return sliders[0].getValue();
    }
    
    /**
     * Gets the energy value of goats to spawn with at the beginning of the simulation. 
     * @return Starting energy value of a goat.
     */
    public int getEnergyOfGoat(){
        return sliders[1].getValue();
    }
    /**
     * Gets the energy value of wolves to spawn with at the beginning of the simulation. 
     * @return Starting energy value of a wolf.
     */
    public int getEnergyOfWolves(){
        return sliders[2].getValue();
    }
    /**
     * Gets the energy value of vultures to spawn with at the beginning of the simulation. 
     * @return Starting energy value of a vulture.
     */
    public int getEnergyOfVultures(){
        return sliders[3].getValue();
    }
}