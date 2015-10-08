package com.benheron.game.state;
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;

import com.benheron.game.main.GMain;

public abstract class State {
	
	public abstract void init();
	
	public abstract void update();
	public abstract void render(Graphics g);
	public abstract void onClick(MouseEvent e);
	public abstract void onKeyPress(KeyEvent e);
	public abstract void onKeyRelease(KeyEvent e);
    public abstract void onMouseMoved(MouseEvent e);
	
	public void setCurState(State nState)
	{
		GMain.tGame.setCurState(nState);
	}

}