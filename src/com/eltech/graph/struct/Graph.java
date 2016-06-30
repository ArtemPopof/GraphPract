package com.eltech.graph.struct;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Polygon;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

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
	private boolean any, isAlgTurnedOn;
	private boolean changes;
	
	private Edge edgeListStart;
	
	private Vertex vert[];
	
	/**
	 * Constructor of the graph object;
	 * 
	 * Setup some initial things
	 */
	public Graph() {
		
		vertexCount = 0;
		edgesCount = 0;
		
		tempMarks = new int [MAX_VERTEXES];
		
		vert = new Vertex[MAX_VERTEXES];
		
		for (int i = 0; i < MAX_VERTEXES; i++) {
			vert[i] = new Vertex();
		}
		
		edgeListStart = null;
		
		isAlgTurnedOn = false;
		
		changes = false;
	}
	
	/**
	 * Construct graph from file
	 * 
	 * @param file
	 */
	public Graph (File file) throws IOException {
		
		int x, y, z;
		
		BufferedReader fin = new BufferedReader (new FileReader(file));
		
		vertexCount = Integer.parseInt(fin.readLine());
		
		int edges = Integer.parseInt( fin.readLine() ); //������� ����� �����
		
		tempMarks = new int[MAX_VERTEXES];	//������� ������ �������
		
		vert = new Vertex[MAX_VERTEXES];	//������ �� ��������� ������
		
		for (int i = 0; i < MAX_VERTEXES; i++)
			vert[i] = new Vertex();
		
		edgeListStart = null; 		//������ �����
		
		isAlgTurnedOn = false; 	//�������� �� �������
		
		changes = false;
		
		String tmp = "";
		
		String val = "";
		
		for (int i = 0; i < edges; i++) //���������� ������ �����
		{
			tmp = fin.readLine();
			int len = tmp.length();
			int j1 = 0;
			int j2 = 0;
			
			while ((tmp.charAt(j2) != ' ') && (j2 < len-1))
			{
				j2++;
			}
			
			//j1 = ������ �����
			//j2 = ������ �������-1
			val = tmp.substring(0, j2);
			
			x = Integer.parseInt(val);		//������ �������
			
			j1 = ++j2;
			//���������� ������ �������
			while ((tmp.charAt(j2) != ' ') && (j2 < len-1))
			{
				j2++;
			}
			val = tmp.substring(j1, j2);
			y = Integer.parseInt(val);		//������ �������
			
			j1 = ++j2;
			//����������� ����
			while ((j2 < len) && (tmp.charAt(j2) != ' '))
			{
				j2++;
			}
			val = tmp.substring(j1, j2);
			z = Integer.parseInt(val);		//���
			
			addEdge(x, y, z);	//���������� �����
		}
		int centrX=250, centrY=250;
		double angle = 6.2831853/vertexCount; //��������� ������������ ������
		double nextangle = 0;
		int R = 200;
		int x_c, y_c;
		
		for(int i = 0; i<vertexCount; i++){
			x_c = (int) (centrX + Math.cos(nextangle)*R);
			y_c = (int) (centrY + Math.sin(nextangle)*R);
			nextangle += angle;
			vert[i].setPosition(x_c,y_c);
		}
		fin.close();
	}
	
	/**
	 * add not initialized vertex to graph
	 */
	public void addVertex() {
		
		if (vertexCount >= MAX_VERTEXES) return;
		
		tempMarks[vertexCount] = INF;
		
		vertexCount++;
	}
	
	/**
	 * add vertex to graph
	 * 
	 * @param x - coords.
	 * @param y
	 */
	public void addVertex(int x, int y) {
		
		if (vertexCount >= MAX_VERTEXES) return;
		
		tempMarks[vertexCount] = INF;
		
		vert[vertexCount].setPosition(x, y);
		vert[vertexCount].setColor(0);
		
		changes = true;
		
		vertexCount++;
		
	}
	
	
	/**
	 * Add edge to graph.
	 * 
	 */
	public void addEdge(int firstPoint, int secondPoint, int weight) {
		
		if (firstPoint == secondPoint) return;
		
		firstPoint--; secondPoint--; // ������� ��������� ���������� � 1
		
		changes = true;
		
		// ���� ��������� ������ �����
		
		if (edgeListStart == null) {
			
			edgeListStart = new Edge(firstPoint, secondPoint, weight);
			
			edgesCount++;
			return;
		}
		
		Edge newNode = edgeListStart;
		
		//�������� ���� ������
		
		while (newNode.getNextEdge() != null) {
			
			if (newNode.getFirstVertex() == firstPoint &&
					newNode.getSecondVertex() == secondPoint) return;
			
			newNode = newNode.getNextEdge();
		}
		
		if (newNode.getNextEdge() == null) {
			
			newNode.setNextEdge(new Edge(firstPoint, secondPoint, weight));
			edgesCount++;
			
		}
		
		
	}
	
	/**
	 * Print marks to the console
	 */
	public void printMarks() {
		
		for (int i = 0; i < vertexCount; i++) {
			if (tempMarks[i] < INF)
				System.out.println(tempMarks[i] + "  ");
			else 
				System.out.println(" inf ");
		}
	}
	
	/**
	 * Paint graph to the panel
	 * 
	 * @param g - graphics from panel
	 */
	public void paintGraph(Graphics g1) {
		
		g1.setColor(Color.WHITE);
		if (changes)
			g1.fillRect(0, 0, 1100-350, 900);
		
		changes = false;
		Graphics2D g = (Graphics2D)g1;
		
		Font myFont = new Font("Arial", Font.BOLD, 16);
		
		g.setFont(myFont); 			//������������� �����
		g.setStroke(new BasicStroke(3) );
		
		String myStr = "";
		
		/*���������� ������*/
		for (int i = 0; i < vertexCount; i++)
		{
			int x = vert[i].getPosition().x - VERTEX_RADIUS;
			int y = vert[i].getPosition().y - VERTEX_RADIUS;
			myStr = Integer.toString(i+1);
			
			switch(vert[i].getColor())
			{
			case 1:		//�������� �����
				g.setColor(Color.BLUE);
				break;
			case 2:		//�������� �������
				g.setColor(Color.RED);
				break;
			case 3:		//�������� �������
				g.setColor(Color.GREEN);
				break;
			default: //�������� ������
				g.setColor(Color.BLACK);
				break;
			}
			
			g.drawOval(x, y, 2*VERTEX_RADIUS, 2*VERTEX_RADIUS);			//��������� � ������� �� �������� � 2 �������
			g.drawString(myStr, x + 15 , y + 20 );	//������ ������ ����� �������
			
			if (isAlgTurnedOn)		//���� �������� �������
			{//������ ������� ��� ���������
				g.setColor(Color.RED);
				if (tempMarks[i] < INF) myStr = Integer.toString(tempMarks[i]);
				else myStr = "inf";
				g.drawString(myStr, x + 15, y - 10);
			}
		}
		/*���������� �����*/
		for (int j = 0; j < edgesCount; j++)
		{
			int x1,y1,x2,y2;
			/*1 ����� (p1)*/
			x1 = vert[ getEdge(j).getFirstVertex() ].getPosition().x;
			y1 = vert[ getEdge(j).getFirstVertex() ].getPosition().y;
			
			/*2 ����� (p2)*/
			x2 = vert[ getEdge(j).getSecondVertex() ].getPosition().x;
			y2 = vert[ getEdge(j).getSecondVertex() ].getPosition().y;
			
			//����������� ����� ������ � ����� �����
			if ( x1 <= x2) //p1 ����� �� p2
			{
				if (y1 <= y2) //p1 ���� p2
				{
					y1 += VERTEX_RADIUS;
					x2 -= VERTEX_RADIUS;
				}
				else
				{//(p1.Y > p2.Y)   //p1 ���� p2
					y1 -= VERTEX_RADIUS;
					x2 -= VERTEX_RADIUS;
				}
			}
			else
			// (p1.X > p2.X) //p1 ������ �� p2
			{
				if (y1 < y2)    //p1 ���� p2
				{
					y1 += VERTEX_RADIUS;
					x2 += VERTEX_RADIUS;
				}
				else  
				{//(p1.Y > p2.Y)   //p1 ���� p2
					y1 -= VERTEX_RADIUS;
					x2 += VERTEX_RADIUS;
				}
			}
			switch( getEdge(j).getColor())
			{
			case 1:		//�������� �����
				g.setColor(Color.CYAN);
				break;
			case 2:		//�������� �������
				g.setColor(Color.RED);
				break;
			case 3:		//�������� �������
				g.setColor(Color.GREEN);
				break;
			default: //�������� ������
				g.setColor(Color.BLACK);
				break;
			}
			g.drawLine(x1, y1, x2, y2); //���������� �������
			myStr = Integer.toString(getEdge(j).getWeight());
			int x = Math.abs(x2 + x1)/2;
			int y = Math.abs(y2 + y1)/2;
			g.drawString(myStr, x + 15, y + 15);
			/*�������*/
			Polygon a = new Polygon();
			a.addPoint(x2, y2);
			double beta = Math.atan2(y1 - y2, x2 - x1); 
			double alfa = Math.PI/10;
			int len = 15; //�����
			x = (int)Math.round(x2 - len*Math.cos(beta+alfa));
			y = (int)Math.round(y2 + len*Math.sin(beta+alfa));
			//g.drawLine(x2, y2, x, y);
			a.addPoint(x, y);
			x = (int)Math.round(x2 - len* Math.cos(beta - alfa));
			y = (int)Math.round(y2 + len* Math.sin(beta - alfa));
			a.addPoint(x, y);
			
			g.fillPolygon(a);
			

			
		}
	}
	
	
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
	
	public String getEdgeListAsString()			//���������� ������ ����� � ���� ������
	{
		String res = "";
		Edge ptr = edgeListStart;
		while (ptr != null)
		{
			res += "[ " + (ptr.getFirstVertex() + 1) + ", " + (ptr.getSecondVertex() + 1) + " ], ���=" + ptr.getWeight() + "\n"; 
			ptr = ptr.getNextEdge();
		}
		return res;
	}
	
	public int getVertexCount() {
		return vertexCount;
	}
	
	public boolean isNegativeCycle(int source)		//true - � ����� ������������� ���� 
	{
		/*�������������*/
		for (int i = 0; i < vertexCount; i++)
			tempMarks[i] = INF;
		tempMarks[source - 1] = 0;					
		
		/*��������� �������� �� n-1 ���*/
		for (int i = 0; i < vertexCount-1; i++)
		{
			for (int j = 0; j < edgesCount; j++)
				if (tempMarks[ getEdge(j).getFirstVertex()] < INF )
				{
					tempMarks[ getEdge(j).getSecondVertex() ] =  
					min( tempMarks[ getEdge(j).getSecondVertex() ], (tempMarks [ getEdge(j).getFirstVertex() ] + getEdge(j).getWeight())); // ����������
					//������ �������� any !!�� �������!!
				}
		}
		/*��������� �������� �� n-�� ���, ���� ���-�� ����������, �� � ����� ������������� ����*/
		any = false;
		for (int j = 0; j < edgesCount; j++)
			if (tempMarks[ getEdge(j).getFirstVertex()] < INF )
			{
				tempMarks[getEdge(j).getSecondVertex()] =  
				min( tempMarks[ getEdge(j).getSecondVertex()], (tempMarks [getEdge(j).getFirstVertex() ] + getEdge(j).getWeight())); // ����������
				//������ �������� any !!�� �������!!
			}
		return any; //any == true, ����� ���� ���������.
	}

	private int min(int a, int b)
	{
		if (a <= b) return a;
		any = true;
		return b; 
	}
	
	public void Ford_Bellman(int source)
	{
		changes = true;
		any = true;
		isAlgTurnedOn = true;
		originVertex = --source;
		k = 0;
		v = 0;
		for (int i = 0; i < vertexCount; i++)
			tempMarks[i] = INF;
		
		for (int i = 0; i < edgesCount; i++)
			if (getEdge(i).getFirstVertex() == originVertex) tempMarks[ getEdge(i).getSecondVertex() ] = getEdge(i).getWeight();

		vert[originVertex].setColor(1);
		tempMarks[source] = 0;
	}

	public int[] getTempMarks() {
		return tempMarks;
	}

	public boolean step() {
		
		changes = true;
		vert[ getEdge(currentEdge).getSecondVertex() ].setColor(0);
		getEdge(currentEdge).setColor(0);
		currentEdge = v; //��������� ����� ���������������� �����
		vert[ getEdge(v).getSecondVertex() ].setColor(2);
		getEdge(v).setColor(2);	//�������� � ������� ����
		
		if (v < edgesCount) 
		{
			if (tempMarks[ getEdge(v).getFirstVertex()] < INF )
			{
				
				tempMarks[ getEdge(v).getSecondVertex()] =  
				min( tempMarks[ getEdge(v).getSecondVertex() ], (tempMarks [ getEdge(v).getFirstVertex() ] + getEdge(v).getWeight())); // ����������
				//������ �������� any !!�� �������!!
			}
			v++;
			if (v >= edgesCount)
			{//������� ���������� �� ���� ������ (��������� ���)
				
				k++;
				v= 0;	
				if (any && (k < vertexCount-1)) 
				{//any = true -> ���-�� ����������, �������� �� ��������
					any = false;
					return false;
				}
				v = 0;
				return true;
				//���� any = false -> ��������� �����������, �������� ����� �����
			}
		}
		return false;
	}

	public int getEdgesCount() {
	
		return edgesCount;
	}
	
	public boolean isToClose(int X, int Y)		//true - ����� ������� ������ � ������ �������
	{
		if (vertexCount == 0) return false;
		double dist = 0;
		for (int i = 0; i < vertexCount; i++)
		{
			dist = Math.sqrt(Math.pow( (vert[i].getPosition().x - X) ,2) + Math.pow( (vert[i].getPosition().y - Y),2 ));
			if ( (int)dist < 70 )
			{
				return true;
			}
		}
		return false;
	}
	
	public int isInVert(int X, int Y)			//���������� ����� ��������� �������
	{
		double dist = 0;
		for (int i = 0; i < vertexCount; i++)
		{
			dist = Math.sqrt(Math.pow( (vert[i].getPosition().x - X) ,2) + Math.pow( (vert[i].getPosition().y - Y),2));
			if ( (int)dist < VERTEX_RADIUS )
			{
				return i+1;
			}
		}
		return 0;
	}
	
	public boolean isRunning() {
		return isAlgTurnedOn;
	}

	public void setEdgeColor(int v, int color) {
		
		changes = true;
		v--;
		vert[v].setColor(color);
		
	}
}
