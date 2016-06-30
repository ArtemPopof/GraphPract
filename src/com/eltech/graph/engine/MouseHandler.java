package com.eltech.graph.engine;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

import javax.swing.JOptionPane;

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
		
		main.getGraph().paintGraph(gui.getDrawGraphics());
		
	}
	public void mouseClicked(MouseEvent arg0) {
		
		main.getGUI().mouseClicked(arg0);
		
		GUIManager gui = main.getGUI();
		
		gui.getDrawGraphics().dispose();
		
		main.getGraph().paintGraph(gui.getDrawGraphics());
		
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
