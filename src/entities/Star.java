package entities;

import java.awt.*;
import java.util.Random;

public class Star{
    private final double gravConst = 6.673e-11;

    private double x;
    private double y;
    private double vx;
    private double vy;
    private double fx;
    private double fy;
    private double mass;
    private Color color;
    private double radius;


    public Star(double x, double y, double vx, double vy, double mass){
        this.x = x;
        this.y = y;
        this.vx = vx;
        this.vy = vy;
        this.radius = 10;
        this.mass = mass;

        //this.canvasY = y - Toolkit.getDefaultToolkit().getScreenSize().getHeight();

        int first = rngForColor();
        int second = rngForColor();
        int third = rngForColor();
        this.color = Color.getHSBColor(first, second, third);
    }

    public double getX(){
        return this.x;
    }

    public double getY(){
        return this.y;
    }

    public double getVx(){
        return this.vx;
    }

    public double getVy(){
        return this.vy;
    }

    public double getMass(){
        return this.mass;
    }

    public void setX(double newPositionX){
        this.x = newPositionX;
        //super.setCenterX(newPositionX);
    }

    public void setY(double newPositionY){
        this.y = newPositionY;
        //super.setCenterY(newPositionY);
    }


    public void setVx(double newVelocityX){
        this.vx = newVelocityX;
    }

    public void setVy(double newVelocityY){
        this.vy = newVelocityY;
    }

    public void setMass(int newMass){
        this.mass = newMass;
    }


    // Generate a number from 50 to 255
    private int rngForColor(){
        Random num = new Random();
        return num.nextInt(206) + 50;
    }

    public void draw(Graphics2D g, double ratio){
        g.setColor(color);
        g.fillOval((int)(x/ratio-radius), (int)(y/ratio-radius), (int)(2*radius), (int)(2*radius));
    }

    public void update(int dt){
        // Euler's forward method
        double ax = fx / mass;
        double ay = fy / mass;
        vx += ax * dt;
        vy += ay * dt;
        x  += vx * dt;
        y  += vy * dt;
    }

    /*=================================================================================================*/

    public void resetForce(){
        this.fx = 0;
        this.fy = 0;
    }

    public void addForce(Star otherStar){
        double epsilon = 1e3;   //Softening parameter

        double distanceX = otherStar.getX() - x;
        double distanceY = otherStar.getY() - y;
        double distance = Math.sqrt( Math.pow(distanceX, 2) + Math.pow(distanceY, 2) );

        double M1 = otherStar.getMass();
        double M2 = this.mass;
        double F = (gravConst * M1 * M2)/(Math.pow(distance, 2) + Math.pow(epsilon, 2));

        this.fx += F * distanceX/distance;  //cosine theta
        this.fy += F * distanceY/distance;  //sine theta
    }

    /*=============================================================================================*/
    public void decideRadiusScale(int smallestMagnitude){
        int currentMagnitude = (int) Math.log10(mass);
        this.radius = 5 +   (currentMagnitude - smallestMagnitude);
    }

}
