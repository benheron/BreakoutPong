package com.benheron.game.state;

import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;

import com.benheron.game.main.Resources;

public class MenuState extends State {

	@Override
	public void init() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void update() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void render(Graphics g) {
		// TODO Auto-generated method stub
		g.drawImage(Resources.splash,  0,  0, null);
		
	}

	@Override
	public void onClick(MouseEvent e) {
		setCurState(new PlayState());
		
	}

	@Override
	public void onKeyPress(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onKeyRelease(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}

    @Override
    public void onMouseMoved(MouseEvent e) {
        // TODO Auto-generated method stub
    }


}
