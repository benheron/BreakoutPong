package com.benheron.game.main;

import java.applet.Applet;
import java.applet.AudioClip;
import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URL;

import javax.imageio.ImageIO;


public class Resources {
	
	public static BufferedImage splash, icon;
	public static Color darkBlue;
	public Resources()
	{
		
	}
	
	public static void load()
	{
		splash = loadImg("splash4.png");
		icon = loadImg("iconImg.png");
		darkBlue = new Color(25,83,105);
	}
	
	private static AudioClip loadAudio(String fn)
	{
		URL fileName = Resources.class.getResource("/resources/" + fn);
		return Applet.newAudioClip(fileName);
	}
	
	private static BufferedImage loadImg(String fn)
	{
		BufferedImage theImg = null;
		try {
			theImg = ImageIO.read(Resources.class.getResourceAsStream("/resources/" + fn));
		} catch (IOException e) {
			System.out.println("Oh, it seems we can't find the file: " + fn);
			e.printStackTrace();
		}
		return theImg;
	}
}
