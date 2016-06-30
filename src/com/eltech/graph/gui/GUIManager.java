package com.eltech.graph.gui;

import java.awt.Graphics;

import com.eltech.graph.engine.*;

/**
 * Main GUI class. 
 * Initialization of GUI, behavior control
 * 
 * @author Сергей Павлюк
 * @version 1.0
 */

public class GUIManager {
	
	private Main main;
	
	public GUIManager(Main main) {
		this.main = main;
		
	}
	
	
	public Graphics getDrawGraphics() {
		return mainPanel.getGraphics();
	}

}
