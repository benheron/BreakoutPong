package com.benheron.game.model;

public class tile extends worldObject {
	boolean dest;
	
	public tile(int x, int y)
	{
		xPos = x;
		yPos = y;
		oWidth = 15;
		oHeight = 40;
	}
	
	public void setDest(boolean d)
	{
		dest = d;
	}
	
	public boolean isDest()
	{
		return dest;
	}
	

}
