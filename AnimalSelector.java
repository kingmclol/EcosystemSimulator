import greenfoot.*;
public class AnimalSelector extends UI
{
    private UIAnimal[] animals = {new UIAnimal("rabbit"), new UIAnimal("rabbit"), new UIAnimal("rabbit"), new UIAnimal("rabbit")};
    private Button decrementButton;
    private Button incrementButton;
    private GreenfootImage img;
    private ButtonIncrement[] buttonIncrementers;
    private Slider[] sliders;
    private int animalIndex;
    public AnimalSelector(){
        GreenfootImage leftButtonImg = new GreenfootImage("images/incrementLeft.png");
        GreenfootImage rightButtonImg = new GreenfootImage("images/incrementRight.png");
        img = new GreenfootImage(50, 50);
        img.setColor(new Color(0, 0, 0, 0));
        img.fill();
        setImage(img);
        animalIndex = 0;
        buttonIncrementers = new ButtonIncrement[4];
        sliders = new Slider[4];
        for (int i = 0; i < buttonIncrementers.length; i++){
            buttonIncrementers[i] = new ButtonIncrement(300, 50, 200, 50, "Num", leftButtonImg, rightButtonImg);
            sliders[i] = new Slider(250, 1000, "Energy");
        }
        
        leftButtonImg = new GreenfootImage(leftButtonImg);
        leftButtonImg.scale(75, 75);
        rightButtonImg = new GreenfootImage(rightButtonImg);
        rightButtonImg.scale(75, 75);
        
        decrementButton = new Button(this::decrementValue, 100, 100, leftButtonImg, leftButtonImg, leftButtonImg);
        incrementButton = new Button(this::incrementValue,100, 100, rightButtonImg, rightButtonImg, rightButtonImg);
    }
    public void addedToWorld(World w){
        
        w.addObject(animals[animalIndex], getX(), getY() - 10);
        w.addObject(buttonIncrementers[animalIndex], getX(), getY() - 125);
        w.addObject(sliders[animalIndex], getX(), getY() + 125);
        w.addObject(decrementButton, getX() - 100, getY());
        w.addObject(incrementButton, getX() + 100, getY());
    }
    private void incrementValue(){
        World w = getWorld();
        w.removeObject(animals[animalIndex]);
        sliders[animalIndex].removeObject();
        buttonIncrementers[animalIndex].removeObject();
        animalIndex = (animalIndex+1) % 4;
        w.addObject(sliders[animalIndex], getX(), getY() + 125);
        w.addObject(animals[animalIndex],getX(), getY() - 10);
        w.addObject(buttonIncrementers[animalIndex], getX(), getY() - 125);
    }
    private void decrementValue(){ 
        World w = getWorld();
        w.removeObject(animals[animalIndex]);
        sliders[animalIndex].removeObject();
        buttonIncrementers[animalIndex].removeObject();
        animalIndex = (animalIndex+ 3) % 4;
        w.addObject(sliders[animalIndex], getX(), getY() + 125);
        w.addObject(animals[animalIndex], getX(), getY() - 10);
        w.addObject(buttonIncrementers[animalIndex],  getX(), getY() - 125);
    }
    public int getNumOfRabbits(){
       return buttonIncrementers[0].getValue();
    }
    public int getNumOfDeer(){
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
    public int getEnergyOfDeer(){
        return sliders[1].getValue();
    }
    public int getEnergyOfWolves(){
        return sliders[2].getValue();
    }
    public int getEnergyOfVultures(){
        return sliders[3].getValue();
    }
}