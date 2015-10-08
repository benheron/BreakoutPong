package com.benheron.game.model;

import java.awt.Image;
import java.awt.Rectangle;

public class worldObject {
	protected double xPos;
	protected double yPos;
	protected int oWidth;
	protected int oHeight;
	protected Image oImage;
	
	
	public void setX(double x)
	{
		xPos = x;
	}
	
	public double getX()
	{
		return xPos;
	}
	
	public void setY(double y)
	{
		yPos = y;
	}
	
	public double getY()
	{
		return yPos;
	}
	
	public void setWidth(int w)
	{
		oWidth = w;
	}
	
	public int getWidth()
	{
		return oWidth;
	}
	
	public void setHeight(int h)
	{
		oHeight = h;
	}
	
	public int getHeight()
	{
		return oHeight;
	}
	
	public Image getImage()
	{
		return oImage;
	}
	
}
