import greenfoot.*;
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
            buttonIncrementers[i] = new ButtonIncrement(225, 50, 125, 50, "Num", leftButtonImgs, rightButtonImgs);
            sliders[i] = new Slider(250, 1000, "Energy");
        }
        for(int i = 0; i < 3; i ++){
            leftButtonImgs[i] = new GreenfootImage(leftButtonImgs[i]);
            leftButtonImgs[i].scale(75, 75);
            rightButtonImgs[i] = new GreenfootImage(rightButtonImgs[i]);
            rightButtonImgs[i].scale(75, 75);
        }

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
    private void incrementValue(){
        World w = getWorld();
        w.removeObject(animals[animalIndex]);
        sliders[animalIndex].removeObject();
        buttonIncrementers[animalIndex].removeObject();
        animalIndex = (animalIndex+1) % 4;
        w.addObject(sliders[animalIndex], getX(), getY() + 150);
        w.addObject(animals[animalIndex],getX(), getY() - 10);
        w.addObject(buttonIncrementers[animalIndex], getX(), getY() - 125);
    }
    private void decrementValue(){ 
        World w = getWorld();
        w.removeObject(animals[animalIndex]);
        sliders[animalIndex].removeObject();
        buttonIncrementers[animalIndex].removeObject();
        animalIndex = (animalIndex+ 3) % 4;
        w.addObject(sliders[animalIndex], getX(), getY() + 150);
        w.addObject(animals[animalIndex], getX(), getY() - 10);
        w.addObject(buttonIncrementers[animalIndex],  getX(), getY() - 125);
    }
    public int getNumOfRabbits(){
       return buttonIncrementers[0].getValue();
    }
    public int getNumOfGoats(){
        return buttonIncrementers[1].getValue();
    }
    public int getNumOfWolves(){
        return buttonIncrementers[2].getValue();
    }
    public int getNumOfVultures(){
        return buttonIncrementers[3].getValue();
    }
    public int getEnergyOfRabbits(){
        return sliders[0].getValue();
    }
    public int getEnergyOfGoat(){
        return sliders[1].getValue();
    }
    public int getEnergyOfWolves(){
        return sliders[2].getValue();
    }
    public int getEnergyOfVultures(){
        return sliders[3].getValue();
    }
}