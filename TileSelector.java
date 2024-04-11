import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * This is the UI for selecting tiles before the simulation.
 * @author (your name) 
 * @version (a version number or a date)
 */
public class TileSelector extends UI
{
    private GreenfootImage img;
    private GreenfootImage buttonImg;
    private GreenfootImage buttonHoveredImg;
    private GreenfootImage buttonPressedImg;
    private Button menuButton;
    private static boolean closed;
    private static boolean transition;
    private int menuWidth;
    private Button [] tileButtons = new Button[5];
    public TileSelector(){
        img = new GreenfootImage("tileSelector.png");
       
        buttonImg = new GreenfootImage("tileSelectorButton.png");
        buttonHoveredImg = new GreenfootImage("tileSelectorButtonHovered.png");
        buttonPressedImg = new GreenfootImage("tileSelectorButtonPressed.png");
        setImage(img);
        menuButton = new Button(() -> onClick(), 24, 80, buttonImg, buttonHoveredImg, buttonPressedImg);
        closed = true;
        menuWidth = 0;
        GreenfootImage [] buttonImg = {new GreenfootImage("tile_berries.png"), 
                                        new GreenfootImage("tile_grass.png"), 
                                        new GreenfootImage("tile_mountain.png"), 
                                        new GreenfootImage("tile_water.png"), 
                                        new GreenfootImage("tile_trees.png")};

        
        tileButtons[0] = new Button(() -> onClickTileButton1(), 50, 50, buttonImg[0], buttonImg[0], buttonImg[0]);
        tileButtons[1] = new Button(() -> onClickTileButton2(), 50, 50, buttonImg[1], buttonImg[1], buttonImg[1]);
        tileButtons[2] = new Button(() -> onClickTileButton3(), 50, 50, buttonImg[2], buttonImg[2], buttonImg[2]);
        tileButtons[3] = new Button(() -> onClickTileButton4(), 50, 50, buttonImg[3], buttonImg[3], buttonImg[3]);
        tileButtons[4] = new Button(() -> onClickTileButton5(), 50, 50, buttonImg[4], buttonImg[4], buttonImg[4]);
    }
    public void addedToWorld(World w){
        cursor = getCursor();
        w.addObject(menuButton, w.getWidth() - 12, w.getHeight()/2);
        int offsetButton = 100;
        for(int i = 0; i < tileButtons.length; i++){
            w.addObject(tileButtons[i], getX() + 24,  offsetButton);
            offsetButton += 100;
            System.out.println(tileButtons[i]);
            
        }
    }
    private void onClick() {
        transition = true;
        closed = !closed;
    }
    private void onClickTileButton1() {
        DrawWorld.setMouseDrawType(3);
        

    }
    private void onClickTileButton2() {
        DrawWorld.setMouseDrawType(1);
        
    }
    private void onClickTileButton3() {
        DrawWorld.setMouseDrawType(5);
        
    }
    private void onClickTileButton4() {
        DrawWorld.setMouseDrawType(0);
        
    }
    private void onClickTileButton5() {
        DrawWorld.setMouseDrawType(2);
        
    }

    public static boolean getState(){
        return transition;
    }
    public static boolean getClosed(){
        return closed;
    }
    public void act()
    {
        
        if(transition){
            if (!closed && menuWidth < 124){
                menuWidth+= 6;
                
                menuButton.setLocation(menuButton.getX() - 6, getY());
                
                
                setLocation(getX() - 6, getY());
                for(int i = 0; i < tileButtons.length; i++){
                    tileButtons[i].setLocation(tileButtons[i].getX() - 6, tileButtons[i].getY());
                }
                if (menuWidth > 124){
                    menuWidth = 124;
                    menuButton.setLocation(getWorld().getWidth() - 136, getY());
                    setLocation(getWorld().getWidth()-82, getY());
                }
                
            }
            
            else if(menuWidth > 0 && closed){
                menuWidth-= 6;
                menuButton.setLocation(menuButton.getX() + 6, getY());
                setLocation(getX() + 6, getY());
                for(int i = 0; i < tileButtons.length; i++){
                    tileButtons[i].setLocation(tileButtons[i].getX() + 6, tileButtons[i].getY());
                }
                if (menuWidth < 0){
                    menuWidth = 0;
                    menuButton.setLocation(getWorld().getWidth()-12, getY());
                    setLocation(getWorld().getWidth() + 42, getY());
                }
                
            }
            else {
                transition = false;
            }
            
        }

    }
}
