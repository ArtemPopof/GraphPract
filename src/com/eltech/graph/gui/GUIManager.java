package com.eltech.graph.gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JToolBar;
import javax.swing.border.Border;
import javax.swing.table.DefaultTableModel;

import com.eltech.graph.struct.*;
import com.eltech.graph.engine.*;

/**
 * Main GUI class. 
 * Initialization of GUI, behavior control
 * 
 * @author Сергей Павлюк
 * @version 1.0
 */

public class GUIManager {
	
	private Graph g;
	
	private int select1 = 0;
	private int select2 = 0;
	private int select3 = 0;
	
	private boolean buttonRFFPressed = true;
	
	private JFrame mainFrame;
    private String labelstr;
    private JTextArea textAr;
       

    private DefaultTableModel model;
    private JTable table;
    private JPanel mainPanel;
    private JPanel rightPanel;
    private JToolBar upperPanel; 
    private JButton b_nextStep; 
    private JButton button1; 
    private JButton button2; 
    private JButton button3; 
    private JButton button4; 
       
    private Border bord;
	

	
	private Main main;
	
	public GUIManager(Main main) {
		this.main = main;
		
		mainFrame = new JFrame("Алгоритм Форда - Беллмана");
		labelstr = new String("1");
		textAr = new JTextArea();
		model = new DefaultTableModel();
		table = new JTable();
		mainPanel = new JPanel();
		rightPanel = new JPanel();
		upperPanel = new JToolBar();
		b_nextStep = new JButton("Старт");
		button1 = new JButton("Загрузить");
		button2 = new JButton("Нарисовать");
		button3 = new JButton("Справка");
		button4 = new JButton("Сбросить");
		bord =  BorderFactory.createLineBorder(Color.black, 1);  
		
		addMainPanels();
	}
	
	private void addMainPanels() {
		mainPanel.setBackground(Color.white);
        mainPanel.addMouseListener(main.getMouseHandler());
        mainPanel.addMouseMotionListener(main.getMouseHandler());
        mainPanel.requestFocus();
        rightPanel.setPreferredSize(new Dimension(350, 950));
        rightPanel.setBackground(Color.white);

        addRightPanels();
        addUpperPanels();
        main.getContentPane().add(upperPanel, BorderLayout.NORTH);
        main.getContentPane().add(mainPanel, BorderLayout.CENTER);
        main.getContentPane().add(rightPanel, BorderLayout.WEST);
	}
	
	public void addUpperPanels(){
	
            //Read from file
            
            button1.addActionListener(new ActionListener() {

				@Override
				public void actionPerformed(ActionEvent arg0) {
					
					try{
            		
            		JFileChooser fileopen = new JFileChooser();
            		int ret = fileopen.showDialog(null, "Открыть файл");
            		if (ret == JFileChooser.APPROVE_OPTION) {
	            		File file = fileopen.getSelectedFile();
	            		
	            		g = new Graph( file );
	            		
	            		JOptionPane.showMessageDialog(null, "Данные загружены.", "Загрузка", JOptionPane.INFORMATION_MESSAGE);
	                    buttonRFFPressed = true;
	                    button2.setEnabled(false);
	                    
	                    textAr.setFont(new Font("ARIAL", 0, 25));
	                    textAr.insert("Список ребер:\n", textAr.getSelectionEnd());
	                    textAr.insert(main.getGraph().getEdgeListAsString(), textAr.getSelectionEnd());
            		}
                } catch (FileNotFoundException e1) {
                    JOptionPane.showMessageDialog(null, "Файл не найден.", "Загрузка", JOptionPane.WARNING_MESSAGE);

                }
                catch (IOException e1) {
                    e1.printStackTrace();
                }
            	
					
				}
            
            });
            
            //Input date
            
            button2.addActionListener(new ActionListener() {

				@Override
				public void actionPerformed(ActionEvent e) {
					
			    JOptionPane.showMessageDialog(null, "Чтобы нарисовать вершину кликните два раза по белой области\n"
            			+ "Чтобы соединить вершины выберите две вершины\n"
            			+ "Выбрать вершину можно кликнув по ней, она окраситься в синий цвет", "Помощь",JOptionPane.INFORMATION_MESSAGE );
                button1.setEnabled(false);
                buttonRFFPressed = false;
				}
            
            });
            
            //HELP
            
            button3.addActionListener(new ActionListener() {

				@Override
				public void actionPerformed(ActionEvent e) {
				String HELP = "Кнопка \"Загрузить\" загружает введеные заранее данные;\n\nКнопка \"Нарисовать\"предоставляет возможность Вам вручную задать граф;\n\nКнопка \"Старт\" начинает выполнение алгоритма.";
                JOptionPane.showMessageDialog(null, HELP, "Справка", JOptionPane.INFORMATION_MESSAGE);
				}
            
            });
            
            button4.addActionListener(new ActionListener() {
            
            

				@Override
				public void actionPerformed(ActionEvent e) {
					
				g = null;
            	textAr.replaceRange("", 0, textAr.getSelectionEnd());
            	while (model.getRowCount() > 0)  model.removeRow(0);
            	model.setColumnCount(0);
            	labelstr = "1";
            	b_nextStep.setEnabled(true);
            	b_nextStep.setText("Старт");
            	button1.setEnabled(true);
            	button2.setEnabled(true);
            	buttonRFFPressed = false;
            	mainPanel.repaint();
					
				}
            
            });
            upperPanel.add(button1);
            upperPanel.addSeparator(new Dimension(8, 0));
            upperPanel.add(button2);
            upperPanel.addSeparator(new Dimension(8, 0));
            upperPanel.add(button3);
            upperPanel.addSeparator(new Dimension(8, 0));
            upperPanel.add(button4);
            
    }
        
    public void addRightPanels() {

            rightPanel.setLayout(new BoxLayout(rightPanel, BoxLayout.Y_AXIS));
         
            table = new JTable(model);
            JScrollPane scroll_text = new JScrollPane(textAr,JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);

            JScrollPane scroll_table = new JScrollPane(table, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);

            scroll_table.setBorder(bord);
            scroll_text.setBorder(bord);
           // scroll_table.setSize(new Dimension(rightPanel.getWidth(), 0));

            b_nextStep.addActionListener(new ActionListener() {

				@Override
				public void actionPerformed(ActionEvent e) {
					
					String name = b_nextStep.getText();
            	if (name.equals("Старт"))
            	{
            		model.addColumn("Шаг");
                    for(int i= 0;i<g.getVertexCount();i++){
                    	model.addColumn(""+(i+1));
                    }
            		int source = Integer.parseInt(JOptionPane.showInputDialog("Введите исходную вершину"));
            		if (g.isNegativeCycle(source)) 
                		JOptionPane.showMessageDialog(null, "В графе есть отрицательный цикл"
                				+ "\n Будет выполнено n-1 шагов", "Загрузка", JOptionPane.INFORMATION_MESSAGE);	
            		g.Ford_Bellman(source);
            		button1.setEnabled(false);
            		button2.setEnabled(false);
            	}
                int i = 0;
                
                int[] dl = g.getTempMarks();
                String[] _dl= new String[dl.length+1];
                textAr.replaceRange("", 0, textAr.getSelectionEnd());
                textAr.insert("Шаг " + labelstr + "\n", textAr.getSelectionEnd());
                textAr.insert("Рассматриваемое ребро: "+g.getEdgeListAsString()+"\n",textAr.getSelectionEnd() );
                textAr.insert("Список ребер:\n", textAr.getSelectionEnd());
                i = Integer.valueOf(labelstr);
                _dl[0]= Integer.toString(i-1);
                for (int j = 0; j<dl.length; j++){
                	if(dl[j] == 1400) _dl[j+1] = "INF";
                	else
                	_dl[j+1]= Integer.toString(dl[j]); 
                }
                textAr.insert(g.getEdgeListAsString(), textAr.getSelectionEnd());
                DefaultTableModel model = (DefaultTableModel) table.getModel();
                model.addRow(_dl);
                
                //g.drawGraph(leftPanel.getGraphics());
                //table.add(_dl[i], null);
                if ( g.step()){
                	for (int j = 0; j < g.getEdgesCount(); j++)
                	{
                		g.setEdgeColor(j, 0);
                	}
                	for (int j = 1; j <= g.getVertexCount(); j++)
                		g.setEdgeColor(j, 0);
                	
                    b_nextStep.setText("Конец алгоритма");
                    b_nextStep.setEnabled(false);
                }
                else{
                    b_nextStep.setText("Следующий шаг");
                }
                labelstr = Integer.toString(++i);
                g.paintGraph(mainPanel.getGraphics());
					
				}
		
            });
           
            //b_nextStep.setPreferredSize(new Dimension(100, 30));

            rightPanel.add(scroll_text);
            rightPanel.add(scroll_table);
            main.add(b_nextStep, BorderLayout.SOUTH);

        }    
	
	
	public Graphics getDrawGraphics() {
		return mainPanel.getGraphics();
	}

	public void mouseClicked(MouseEvent e) {
	
		if (buttonRFFPressed)
		{
			g.paintGraph(mainPanel.getGraphics());
			return;
		}
		
		int x = e.getPoint().x - mainPanel.getX();
		int y = e.getPoint().y - mainPanel.getY() - 30;
		
		System.out.println("X: " + x);
		System.out.println("Y: " + y);
		int a = e.getClickCount();
		if ((g == null) && (a == 2)) 
		{
			g = new Graph();
			g.addVertex(x, y);
			g.paintGraph(mainPanel.getGraphics());
			return;
		}
		else if (g == null)
		{
			return;
		}
			
		int i = g.isInVert(x, y);
		
		if (e.getClickCount() == 2 && !g.isRunning())
		{//добавление новой вершины
			if (!g.isToClose(x, y))
				g.addVertex(x,y);
			else 
				JOptionPane.showMessageDialog(null, "Слишком близко к другим вершинам.", "Ввод", JOptionPane.INFORMATION_MESSAGE);
		}
		else if (i != 0)
		{
    		if (e.getButton() == MouseEvent.BUTTON1)
    		{//выбор вершины
    			if (select1 == 0)
    			{
    				//g.setColor(select3, 0);
    				select1 = i;
    				g.setEdgeColor(select1, 1);
    			}
    			else if (select2 == 0)
    			{
    				//g.setColor(select3, 0);
    				select2 = i;
    				g.setEdgeColor(select2, 1);
    				int w = 0;
    				
    				try
    				{
    					w = Integer.parseInt(JOptionPane.showInputDialog("Введите вес вершины"));
    				} 
    				catch (NumberFormatException exptn)
    				{
        				JOptionPane.showMessageDialog(null, "Нужно ввести число.", "Ошыбка", JOptionPane.INFORMATION_MESSAGE);	
        				g.setEdgeColor(select1, 0);
        				g.setEdgeColor(select2, 0);
        				select1 = select2 = 0;
        				return;
    				}     				
    				g.setEdgeColor(select1, 0);
    				g.setEdgeColor(select2, 0);
    				g.addEdge(select1, select2, w);
    				select1 = select2 = 0;
    			}
    			
    		}
    		else if (e.getButton() == MouseEvent.BUTTON2)
    		{
    			select1 = select2 = select3 = 0;
    		}
		}
		g.paintGraph(mainPanel.getGraphics());
		
	}

}
