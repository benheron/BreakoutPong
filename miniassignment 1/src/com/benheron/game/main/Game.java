package com.benheron.game.main;

import game.benheron.framework.util.InputHandler;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;

import javax.swing.JPanel;

import com.benheron.game.state.State;
import com.benheron.game.state.LoadState;


public class Game extends JPanel implements Runnable{
	private int windWidth;
	private int windHeight;
	private Image gameImage;
	
	private Thread gameThread;
	private volatile boolean running;
	private volatile State curState;
	
	private InputHandler inputHandler;
	
	public Game(int w, int  h)
	{
		windWidth = w;
		windHeight = h;
		setPreferredSize(new Dimension(w,h));
		setBackground(Color.BLACK);
		setFocusable(true);
		requestFocus();
		
	}
	
	public void setCurState(State thisState)
	{
		System.gc();
		thisState.init();
		curState = thisState;
		inputHandler.setCurState(curState);
	}
	
	@Override
	public void addNotify()
	{
		super.addNotify();
		initInput();
		setCurState(new LoadState());
		initGame();
	}
	
	private void initGame()
	{
		running = true;
		gameThread = new Thread(this, "Game Thread");
		gameThread.start();
	}
	
	@Override
	public void run()
	{
		while(running)
		{
			curState.update();
			doubleBuffer();
			curState.render(gameImage.getGraphics());
			repaint();
			try {
				Thread.sleep(14);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		System.exit(0);
	}
	
	private void doubleBuffer()
	{
		if(gameImage == null)
		{
			gameImage = createImage(windWidth,windHeight);
		}
		Graphics g = gameImage.getGraphics();
		g.clearRect(0,0,windWidth,windHeight);
	}
	
	public void exit()
	{
		running = false;
	}
	
	@Override
	protected void paintComponent(Graphics g)
	{
		super.paintComponent(g);
		if (gameImage == null)
		{
			return;
		}
		g.drawImage(gameImage,  0,  0,  null);
		
	}
	
	private  void initInput()
	{
		inputHandler = new InputHandler();
		addKeyListener(inputHandler);
		addMouseListener(inputHandler);
	}
}
