package com.eltech.graph.engine;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

import com.eltech.graph.gui.*;

/**
 * Control all mouse actions
 * 
 * @author Artem Popov
 *
 */
public class MouseHandler implements MouseListener, MouseMotionListener{

	private Main main;

	public MouseHandler(Main main) {
		this.main = main;
		
		main.addMouseListener(this);
	}
	
	public void mouseDragged(MouseEvent arg0) {
	}
	public void mouseMoved(MouseEvent arg0) {
		
		GUIManager gui = main.getGUI();
		
		main.getGraph().update(gui.getDrawGraphics());
		
	}
	public void mouseClicked(MouseEvent arg0) {
	}
	public void mouseEntered(MouseEvent arg0) {
	}
	public void mouseExited(MouseEvent arg0) {
	}
	public void mousePressed(MouseEvent arg0) {
	}
	public void mouseReleased(MouseEvent arg0) {
	}

}
