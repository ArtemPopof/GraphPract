package com.eltech.graph.struct;

import java.awt.Color;
import java.awt.Dimension;

/**
 * Graph vertex realization.
 * For more info see Graph class.
 * 
 * @author Artem Popov
 *
 */

public class Vertex {
	
	private static final Color DEFAULT_COLOR = Color.red;
	
	private int x, y; // coords.
	private Color color;
	
	/**
	 * Constructor with all settings
	 * 
	 * @param x - coords.
	 * @param y
	 * @param color
	 */
	public Vertex (int x, int y, Color color) {
		
		this.x = x;
		this.y = y;
		this.color = color;
		
	}
	
	/**
	 * Minimal constructor;
	 */
	
	public Vertex () {
		x = y = 0;
		
		color = DEFAULT_COLOR;
	}
	
	/**
	 * Sets new position for vertex;
	 *
	 * @param newX
	 * @param newY
	 */
	public void setPosition(int newX, int newY) {
		
		x = newX;
		y = newY;
	}
	
	/**
	 * Sets new color for vertex;
	 * 
	 * @param newColor
	 */
	public void setColor(Color newColor) {
		this.color = newColor;
	}
	
	/**
	 * Returns current position of the vertex;
	 * 
	 * @return
	 */
	public Dimension getPosition() {
		return new Dimension(x, y);
	}	
	

}
