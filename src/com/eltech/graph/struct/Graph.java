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
		
		int edges = Integer.parseInt( fin.readLine() ); //считали число ребер
		
		tempMarks = new int[MAX_VERTEXES];	//создали массив пометок
		
		vert = new Vertex[MAX_VERTEXES];	//массив из координат вершин
		
		for (int i = 0; i < MAX_VERTEXES; i++)
			vert[i] = new Vertex();
		
		edgeListStart = null; 		//список ребер
		
		isAlgTurnedOn = false; 	//алгоритм не запущем
		
		changes = false;
		
		String tmp = "";
		
		String val = "";
		
		for (int i = 0; i < edges; i++) //считывание списка ребер
		{
			tmp = fin.readLine();
			int len = tmp.length();
			int j1 = 0;
			int j2 = 0;
			
			while ((tmp.charAt(j2) != ' ') && (j2 < len-1))
			{
				j2++;
			}
			
			//j1 = начало числа
			//j2 = индекс пробела-1
			val = tmp.substring(0, j2);
			
			x = Integer.parseInt(val);		//перва€ вершина
			
			j1 = ++j2;
			//считывание второй вершины
			while ((tmp.charAt(j2) != ' ') && (j2 < len-1))
			{
				j2++;
			}
			val = tmp.substring(j1, j2);
			y = Integer.parseInt(val);		//втора€ вершина
			
			j1 = ++j2;
			//считываение веса
			while ((j2 < len) && (tmp.charAt(j2) != ' '))
			{
				j2++;
			}
			val = tmp.substring(j1, j2);
			z = Integer.parseInt(val);		//вес
			
			addEdge(x, y, z);	//добавление ребра
		}
		int centrX=250, centrY=250;
		double angle = 6.2831853/vertexCount; //вычиление расположени€ вершин
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
		
		firstPoint--; secondPoint--; // внешн€€ нумераци€ начинаетс€ с 1
		
		changes = true;
		
		// если создаетс€ первое ребро
		
		if (edgeListStart == null) {
			
			edgeListStart = new Edge(firstPoint, secondPoint, weight);
			
			edgesCount++;
			return;
		}
		
		Edge newNode = edgeListStart;
		
		//проходим весь список
		
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
		
		g.setFont(myFont); 			//устанавливаем шрифт
		g.setStroke(new BasicStroke(3) );
		
		String myStr = "";
		
		/*прорисовка вершин*/
		for (int i = 0; i < vertexCount; i++)
		{
			int x = vert[i].getPosition().x - VERTEX_RADIUS;
			int y = vert[i].getPosition().y - VERTEX_RADIUS;
			myStr = Integer.toString(i+1);
			
			switch(vert[i].getColor())
			{
			case 1:		//рисовать синим
				g.setColor(Color.BLUE);
				break;
			case 2:		//рисовать красным
				g.setColor(Color.RED);
				break;
			case 3:		//рисовать зеленым
				g.setColor(Color.GREEN);
				break;
			default: //рисовать черным
				g.setColor(Color.BLACK);
				break;
			}
			
			g.drawOval(x, y, 2*VERTEX_RADIUS, 2*VERTEX_RADIUS);			//вписываем в квадрат со стороной в 2 радиуса
			g.drawString(myStr, x + 15 , y + 20 );	//рисуем внутри номер вершины
			
			if (isAlgTurnedOn)		//если алгоритм запущен
			{//рисуем пометки над вершинами
				g.setColor(Color.RED);
				if (tempMarks[i] < INF) myStr = Integer.toString(tempMarks[i]);
				else myStr = "inf";
				g.drawString(myStr, x + 15, y - 10);
			}
		}
		/*прорисовка ребер*/
		for (int j = 0; j < edgesCount; j++)
		{
			int x1,y1,x2,y2;
			/*1 точка (p1)*/
			x1 = vert[ getEdge(j).getFirstVertex() ].getPosition().x;
			y1 = vert[ getEdge(j).getFirstVertex() ].getPosition().y;
			
			/*2 точка (p2)*/
			x2 = vert[ getEdge(j).getSecondVertex() ].getPosition().x;
			y2 = vert[ getEdge(j).getSecondVertex() ].getPosition().y;
			
			//расстановка точек начала и конца ребер
			if ( x1 <= x2) //p1 слева от p2
			{
				if (y1 <= y2) //p1 ниже p2
				{
					y1 += VERTEX_RADIUS;
					x2 -= VERTEX_RADIUS;
				}
				else
				{//(p1.Y > p2.Y)   //p1 выше p2
					y1 -= VERTEX_RADIUS;
					x2 -= VERTEX_RADIUS;
				}
			}
			else
			// (p1.X > p2.X) //p1 справа от p2
			{
				if (y1 < y2)    //p1 ниже p2
				{
					y1 += VERTEX_RADIUS;
					x2 += VERTEX_RADIUS;
				}
				else  
				{//(p1.Y > p2.Y)   //p1 выше p2
					y1 -= VERTEX_RADIUS;
					x2 += VERTEX_RADIUS;
				}
			}
			switch( getEdge(j).getColor())
			{
			case 1:		//рисовать синим
				g.setColor(Color.CYAN);
				break;
			case 2:		//рисовать красным
				g.setColor(Color.RED);
				break;
			case 3:		//рисовать зеленым
				g.setColor(Color.GREEN);
				break;
			default: //рисовать черным
				g.setColor(Color.BLACK);
				break;
			}
			g.drawLine(x1, y1, x2, y2); //нарисовать стрелку
			myStr = Integer.toString(getEdge(j).getWeight());
			int x = Math.abs(x2 + x1)/2;
			int y = Math.abs(y2 + y1)/2;
			g.drawString(myStr, x + 15, y + 15);
			/*стрелка*/
			Polygon a = new Polygon();
			a.addPoint(x2, y2);
			double beta = Math.atan2(y1 - y2, x2 - x1); 
			double alfa = Math.PI/10;
			int len = 15; //длина
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
	
	public String getEdgeListAsString()			//возвращает список ребер в виде строки
	{
		String res = "";
		Edge ptr = edgeListStart;
		while (ptr != null)
		{
			res += "[ " + (ptr.getFirstVertex() + 1) + ", " + (ptr.getSecondVertex() + 1) + " ], вес=" + ptr.getWeight() + "\n"; 
			ptr = ptr.getNextEdge();
		}
		return res;
	}
	
	public int getVertexCount() {
		return vertexCount;
	}
	
	public boolean isNegativeCycle(int source)		//true - в графе отрицательный цикл 
	{
		/*»нициализаци€*/
		for (int i = 0; i < vertexCount; i++)
			tempMarks[i] = INF;
		tempMarks[source - 1] = 0;					
		
		/*¬ыполн€ем алгоритм ‘Ѕ n-1 раз*/
		for (int i = 0; i < vertexCount-1; i++)
		{
			for (int j = 0; j < edgesCount; j++)
				if (tempMarks[ getEdge(j).getFirstVertex()] < INF )
				{
					tempMarks[ getEdge(j).getSecondVertex() ] =  
					min( tempMarks[ getEdge(j).getSecondVertex() ], (tempMarks [ getEdge(j).getFirstVertex() ] + getEdge(j).getWeight())); // релаксаци€
					//¬Ќ”“–» ћ≈Ќя≈“—я any !!Ќ≈ “–ќ√ј“№!!
				}
		}
		/*¬ыполн€ем алгоритм ‘Ѕ n-ый раз, если что-то изменилось, то в графе отрицательный цикл*/
		any = false;
		for (int j = 0; j < edgesCount; j++)
			if (tempMarks[ getEdge(j).getFirstVertex()] < INF )
			{
				tempMarks[getEdge(j).getSecondVertex()] =  
				min( tempMarks[ getEdge(j).getSecondVertex()], (tempMarks [getEdge(j).getFirstVertex() ] + getEdge(j).getWeight())); // релаксаци€
				//¬Ќ”“–» ћ≈Ќя≈“—я any !!Ќ≈ “–ќ√ј“№!!
			}
		return any; //any == true, когда есть изменение.
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
		currentEdge = v; //сохранили номер рассматриваемого ребра
		vert[ getEdge(v).getSecondVertex() ].setColor(2);
		getEdge(v).setColor(2);	//окрасили в красный цвет
		
		if (v < edgesCount) 
		{
			if (tempMarks[ getEdge(v).getFirstVertex()] < INF )
			{
				
				tempMarks[ getEdge(v).getSecondVertex()] =  
				min( tempMarks[ getEdge(v).getSecondVertex() ], (tempMarks [ getEdge(v).getFirstVertex() ] + getEdge(v).getWeight())); // релаксаци€
				//¬Ќ”“–» ћ≈Ќя≈“—я any !!Ќ≈ “–ќ√ј“№!!
			}
			v++;
			if (v >= edgesCount)
			{//ѕровели релаксацию по всем ребрам (закончили шаг)
				
				k++;
				v= 0;	
				if (any && (k < vertexCount-1)) 
				{//any = true -> что-то помен€лось, алгоритм не закончен
					any = false;
					return false;
				}
				v = 0;
				return true;
				//если any = false -> состо€ние сохранилось, алгоритм нашел ответ
			}
		}
		return false;
	}

	public int getEdgesCount() {
	
		return edgesCount;
	}
	
	public boolean isToClose(int X, int Y)		//true - точка слишком близко к другой вершине
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
	
	public int isInVert(int X, int Y)			//возвращает номер выбранной вершины
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
