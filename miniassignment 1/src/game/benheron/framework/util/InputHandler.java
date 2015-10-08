package game.benheron.framework.util;

import java.awt.event.*;


import com.benheron.game.state.State;

public class InputHandler implements KeyListener, MouseListener, MouseMotionListener {

	private State curState;
	
	public void setCurState(State curState)
	{
		this.curState = curState;
	}
	
	@Override
	public void mouseClicked(MouseEvent e) {
		curState.onClick(e);
		
	}

	@Override
	public void mouseEntered(MouseEvent me) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseExited(MouseEvent me) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mousePressed(MouseEvent me) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseReleased(MouseEvent me) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void keyPressed(KeyEvent e) {
		curState.onKeyPress(e);
		
	}

	@Override
	public void keyReleased(KeyEvent e) {
		curState.onKeyRelease(e);
		
	}

	@Override
	public void keyTyped(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}

    @Override
    public void mouseMoved(MouseEvent e) {
        curState.onMouseMoved(e);

    }

    @Override
    public void mouseDragged(MouseEvent e) {
        // TODO Auto-generated method stub
    }

}
