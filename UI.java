import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

 
public abstract class UI extends SuperActor
{
    protected Cursor cursor;

    protected void addedToWorld(World w){
        cursor = getCursor();
    }

    protected Cursor getCursor()
    {
        return CursorWorld.getCursor();
        
    }

}
