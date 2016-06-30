package com.eltech.graph.struct;

import java.awt.Graphics;

/**
 * Realization of Graph structure.
 * 
 * @author Artem Popov
 * @version 1.0
 *
 */

public class Graph {
	
	private static final int INF = 1400;
	private static final int MAX_VERTEXES = 50;
	private static final int VERTEX_RADIUS = 20;
	
	private int vertexCount;
	private int edgesCount;
	private int k,u,v;
	private int tempMarks[];
	private int currentEdge;
	private int originVertex;
	private boolean any, alg;
	private boolean changes;
	
	private Edge edgeListStart;
	
	private Vertex vert[];
	
	
	/**
	 * Returns edge with index i.
	 * If there is no edges with such index, return null.
	 * 
	 * @param i - index of edge
	 * @return - null or edge with index i
	 */
	public Edge getEdge(int i) {
		
		int j = 0;
		
		Edge current = edgeListStart;
		
		while ((j < i) && (current != null)) {
			j++;
			current = current.getNextEdge();
		}
		
		return current;
		
	}
	
	
	
	
	public void update(Graphics g) {
		
	}

}
