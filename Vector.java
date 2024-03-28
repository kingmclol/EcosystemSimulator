/**
 * Vectors. I initially created this class to hopefully make developing on Greenfoot easier, but it seems like
 * it did the exact opposite than intended. I really should have looked that the Library of Code offered first...
 * 
 * @author Freeman Wang
 * @version 2024-02-27
 */
public class Vector  
{
    private double x, y; // The x and y components for the Vector.
    /**
     * Creates a Vector with magnitude of 0.
     */
    public Vector() {
        x = 0; 
        y = 0;
    }
    /**
     * Create a Vector given its components.
     * @param x The x-component of the Vector.
     * @param y The y-component of the Vector.
     */
    public Vector(double x, double y)
    {
        this.x = x;
        this.y = y;
    }
    /** 
     * Vector subtraction, returning a Vector pointing towards the target.
     * @param target The target position.
     * @return A Vector that points towards the target position.
     */
    public Vector distanceFrom(Vector target) {
        return new Vector(target.getX()-x, target.getY()-y);
    }
    /**
     * Vector addition, returning a Vector that is the sum of this Vector and the other Vector.
     * @param other The Vector to add.
     * @return A Vector that is the sum of this and the other Vector.
     */
    public Vector add(Vector other) {
        return new Vector(x+other.getX(), y+other.getY());
    }
    /**
     * Vector subtraction, returning a Vector that is the difference of this Vector and the other Vector.
     * @param other The Vector to subtract.
     * @return A Vector that is the difference of this and the other Vector.
     */
    public Vector subtract(Vector other) {
        return new Vector(x-other.getX(), y-other.getY());
    }
    /**
     * Prints out a String representation of the Vector.
     */
    public String toString() {
        //return String.format("{%f, %f}", x, y); 
        return "{" + x + ", " + y + "}";
    }
    /**
     * Returns the x component of the Vector.
     * @return The x-component of the Vector.
     */
    public double getX() {
        return x;
    }
    /**
     * Returns the y component of the Vector.
     * @return The y-component of the Vector.
     */
    public double getY() {
        return y;
    }
    /**
     * Returns the magnitude of this Vector.
     * @return The magnitude of the Vector.
     */
    public double getMagnitude() {
        return Math.hypot(x, y);
    }
    /**
     * Returns this Vector, but forced to a magnitude of 1.
     * @return This Vector with a magnitude of 1.
     */
    public Vector normalize() {
        double mag = getMagnitude();
        if (mag == 0) {
            //System.out.println("Cannot normalize Vector with magnitude 0.");
            return this;
        }
        return new Vector(x/mag, y/mag);
    }
    /**
     * Returns this Vector, scaled based on the given scale.
     * @param scalar The scale to scale this Vector by.
     * @return This vector, scaled by the given scalar.
     */
    public Vector scale(double scalar) {
        return new Vector(x*scalar, y*scalar);
    }
    /**
     * Returns this Vector, scaled to the given magnitude.
     * @param magnitude The magnitude for the Vector.
     * @return This Vector, scaled to the given magnitude.
     */
    public Vector scaleTo(double magnitude){
        return this.normalize().scale(magnitude);
    }
    /**
     * Returns a Vector with a magnitude that will not exceed the given magnitude.
     * If the Vector's magnitude is less than the given magnitude, return the original Vector.
     * @param magnitude The magnitude to limit to.
     * @return This Vector, with magnitude capped to the given magnitude.
     */
    public Vector limitMagnitude(double magnitude) {
        if (this.getMagnitude() > magnitude) {
            return this.scaleTo(magnitude);
        }
        return this;
    }
    // public Vector limitMagnitudeMin(double magnitude) {
        // if (this.getMagnitude() < magnitude) {
            // return this.scaleTo(magnitude);
        // }
        // return this;
    // }
    /**
     * Returns this Vector, but reversed.
     * @return This Vector, but reversed.
     */
    public Vector reverse(){
        return this.scale(-1);
    }
    /**
     * Checks if this Vector is equal to the compared Vector.
     * @return true if x components are the same, false if not.
     */
    public boolean equals(Vector other) {
        if (x == other.getX() && y == other.getY()) return true;
        return false;
    }
}
