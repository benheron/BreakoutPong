package com.benheron.game.model;

public class paddle extends worldObject {
    double acceleration;
    int maxSpeed;
    double velX;
    double velY;
    double speedX;
    double speedY;

    public paddle() {
        xPos = 20;
        yPos = 40;
        oWidth = 10;
        oHeight = 50;
        speedX = 0;
        speedY = 0;
        velX=0;
        velY = 0;

        acceleration = 0.7;
        maxSpeed = 7;
    }

    public void update() {

    }

    public void setAcceleration(double a)
    {
        acceleration = a;
    }

    public double getAcceleration()
    {
        return acceleration;
    }

    public void setMaxSpeed(int ms)
    {
        maxSpeed = ms;
    }

    public int getMaxSpeed()
    {
        return maxSpeed;
    }

    public void setSpeedX(double s)
    {
        speedX = s;
    }

    public double getSpeedX()
    {
        return speedX;
    }

    public void setSpeedY(double s)
    {
        speedY = s;
    }

    public double getSpeedY()
    {
        return speedY;
    }

    public void setVelX(double v)
    {
        velX = v;
        speedX = (Math.abs(v));
    }

    public double getVelX()
    {
        return velX;
    }

    public void setVelY(double v)
    {
        velY = v;
        speedY = (Math.abs(v));
    }

    public double getVelY()
    {
        return velY;
    }

}
