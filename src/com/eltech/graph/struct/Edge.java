package com.eltech.graph.struct;

import java.awt.Color;

/**
 * Simple Edge realization.
 * Used in Graph class
 * 
 * @author Artem Popov
 *
 */

public class Edge {
	
	private int firstVertexIndex;
	private int secondVertexIndex;
	private int weight;
	
	private int color;
	
	private Edge next; 		
	
	/**
	 * Simple constructor.
	 * Construct edge with black color.
	 * 
	 * @param u - firstVertexIndex
	 * @param v - secondVertexIndex
	 * @param weight 
	 */
	public Edge(int u, int v, int weight) {
		
		firstVertexIndex = u;
		secondVertexIndex = v;
		this.weight = weight;
		color = 0;
		next = null;
		
	}
		
	public void setColor(int newColor) {
		this.color = newColor;
	}
	
	public Edge getNextEdge() {
		return next;
	}
	
	public int getFirstVertex() {
		return firstVertexIndex;
	}
	
	public int getSecondVertex() {
		return secondVertexIndex;
	}
	
	public void setNextEdge(Edge newNext) {
		this.next = newNext;
	}

	public int getColor() {
		return color;
	}
	
	public int getWeight() {
		return weight;
	}
	

}
