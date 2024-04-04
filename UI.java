import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

 
public abstract class UI extends SuperActor
{
    protected Cursor cursor;

    protected void addedToWorld(World w){
        cursor = getCursor(w);
    }

    protected Cursor getCursor(World w)
    {
        if (w instanceof IntroWorld){
            cursor = IntroWorld.getCursor();
        }
        else{
            cursor = DrawWorld.getCursor();
        }
        return cursor;
    }

}
