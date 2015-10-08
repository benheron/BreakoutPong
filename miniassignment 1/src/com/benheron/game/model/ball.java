package com.benheron.game.model;

public class ball extends worldObject {
	private int speed;
	private float xSpeed;
	private float ySpeed;
	
	public ball()
	{
		xSpeed = 3;
		ySpeed = -3;
		
		xPos = 50;
		yPos = 65;
		
		oWidth = 10;
		oHeight = 10;
	}
	
	public void updateBall()
	{
		xPos += xSpeed;
		yPos += ySpeed;
		
		if (xPos < 0 || xPos > 500)
		{
			xSpeed *=-1;
		}
		if (yPos < 0)
		{
			ySpeed *=-1;
		}
	}
	
	public void setXSpeed(float xd)
	{
		xSpeed = xd;
	}
	
	public float getXSpeed()
	{
		return xSpeed;
	}
	
	public void setYSpeed(float yd)
	{
		ySpeed = yd;
	}
	
	public float getYSpeed()
	{
		return ySpeed;
	}
}

