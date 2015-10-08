package com.benheron.game.main;

import javax.swing.JFrame;

public class GMain {
	private static final int windowWidth = 800;
	private static final int windowHeight = 450;
	
	public static Game tGame;
	
	public static void main(String[] args) {
		JFrame frame = new JFrame("Mini Assignment 4");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setResizable(false);
		
		tGame = new Game(windowWidth, windowHeight);
		frame.add(tGame);
		frame.pack();
		frame.setVisible(true);

	}

}
