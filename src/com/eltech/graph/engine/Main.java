package com.eltech.graph.engine;

import java.awt.Dimension;
import java.awt.Toolkit;
import javax.swing.*;

import com.eltech.graph.gui.*;

public class Main extends JFrame {
	
	//constants 
	
	/**
	 * Screen size for full screen mode
	 */
	public static final int SCREEN_WIDTH = 
			Toolkit.getDefaultToolkit().getScreenSize().width;
	
	public static final int SCREEN_HEIGHT = 
			Toolkit.getDefaultToolkit().getScreenSize().height;
	
	/**
	 * Screen size for windowed mode
	 */
	public static final int WINDOW_WIDTH = SCREEN_WIDTH * 60/100;
	public static final int WINDOW_HEIGHT = SCREEN_HEIGHT * 60/100;
	
	//
	
	private boolean isFullScreen;
	
	
	/**
	 * GUIManager utilize all gui routine 
	 */
	private GUIManager gui;
	
	
	/**
	 * Initialization routine
	 * 
	 * @param title - window title
	 */
	public Main(String title) {
		super(title);
		
		isFullScreen = false;
		
		setupWindow();
		
		gui = new GUIManager(this);
		
	}
	
	private void setupWindow() {
		if (isFullScreen) {
			
			throw new UnsupportedOperationException("Full screen mode is unavailable, please use window mode");
		
		} else {
			
			// system look and feel
			
			try {
				javax.swing.UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
			} catch (ClassNotFoundException | InstantiationException | IllegalAccessException
					| UnsupportedLookAndFeelException e) {
				
				e.printStackTrace();
			}
			
			this.setSize(new Dimension(WINDOW_WIDTH, WINDOW_HEIGHT));
			this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			this.setResizable(false);
			this.setVisible(true);
			
		}
	}
	
	public static void main(String[] args) {
		
		new Main("Алгоритм на графах");
		
	}

}
