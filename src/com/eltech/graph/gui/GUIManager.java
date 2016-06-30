package com.eltech.graph.gui;
import com.eltech.graph.engine.*;
import com.eltech.graph.struct.Graph;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
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
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.border.Border;
import javax.swing.table.DefaultTableModel;

/**
 * Main GUI class.
 * Initialization of GUI, behavior control
 *
 * @author ������ ������
 * @version 1.0
 */





//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//




public class GUIManager {




	static Graph g;
	static int select1 = 0;
	static int select2 = 0;
	static int select3 = 0;
	static boolean buttonRFFPressed = true;

	public GUIManager() {
	}

	public static void main(String[] args) throws ClassNotFoundException, IOException, UnsupportedLookAndFeelException, InstantiationException, IllegalAccessException {
		UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		GUIManager.myFrame jf = new GUIManager.myFrame();
		jf.createFrame();
	}

	public static class myFrame {
		JFrame mainFrame = new JFrame("Алгоритм Форда - Беллмана");
		String labelStr = new String("1");
		JTextArea textAr = new JTextArea();
		DefaultTableModel model = new DefaultTableModel();
		JTable table = new JTable();
		JPanel leftPanel = new JPanel();
		JPanel rightPanel = new JPanel();
		JToolBar upperPanel = new JToolBar();
		JButton b_nextStep = new JButton("Старт");
		JButton buttonDownload = new JButton("Загрузить");
		JButton buttonDraw = new JButton("Нарисовать");
		JButton buttonFAQ = new JButton("Справка");
		JButton buttonReset = new JButton("Сбросить");
		Border bord;

		public myFrame() {
			this.bord = BorderFactory.createLineBorder(Color.black, 1);
		}

		public void createFrame() {
			this.mainFrame.setResizable(true);
			this.mainFrame.setDefaultCloseOperation(3);
			this.mainFrame.setLayout(new BorderLayout());
			this.mainFrame.setVisible(true);
			this.addMainPanels();
			this.textAr.setFont(new Font("Arial", 0, 14));
			this.textAr.setEditable(false);
			this.textAr.setCursor((Cursor)null);
			this.textAr.setOpaque(false);
			this.textAr.setFocusable(false);
			GraphicsDevice gd = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice();
			int width = gd.getDisplayMode().getWidth();
			int height = gd.getDisplayMode().getHeight();
			this.mainFrame.setSize(width, height-25); //- menu pusk
		}

		public void addMainPanels() {
			this.leftPanel.setBackground(Color.white);
			this.leftPanel.addMouseListener(new GUIManager.myFrame.CustomListener());
			this.leftPanel.addMouseMotionListener(new GUIManager.myFrame.CustomListener2());
			this.rightPanel.setPreferredSize(new Dimension(350, this.mainFrame.getHeight()));
			this.rightPanel.setBackground(Color.white);
			this.addRightPanels();
			this.addUpperPanels();
			this.mainFrame.getContentPane().add(this.upperPanel, "North");
			this.mainFrame.getContentPane().add(this.leftPanel, "Center");
			this.mainFrame.getContentPane().add(this.rightPanel, "West");
		}

		public void addUpperPanels() {
			GUIManager.myFrame.ButtonRFF RFF = new GUIManager.myFrame.ButtonRFF();
			this.buttonDownload.addActionListener(RFF);
			GUIManager.myFrame.ButtonID ID = new GUIManager.myFrame.ButtonID();
			this.buttonDraw.addActionListener(ID);
			GUIManager.myFrame.ButtonHELP HELP = new GUIManager.myFrame.ButtonHELP();
			this.buttonFAQ.addActionListener(HELP);
			GUIManager.myFrame.ButtonClear Clear = new GUIManager.myFrame.ButtonClear();
			this.buttonReset.addActionListener(Clear);
			this.upperPanel.add(this.buttonDownload);
			this.upperPanel.addSeparator(new Dimension(8, 0));
			this.upperPanel.add(this.buttonDraw);
			this.upperPanel.addSeparator(new Dimension(8, 0));
			this.upperPanel.add(this.buttonFAQ);
			this.upperPanel.addSeparator(new Dimension(8, 0));
			this.upperPanel.add(this.buttonReset);
		}

		public void addRightPanels() {
			this.rightPanel.setLayout(new BoxLayout(this.rightPanel, 1));
			this.table = new JTable(this.model);
			JScrollPane scroll_text = new JScrollPane(this.textAr, 20, 30);
			JScrollPane scroll_table = new JScrollPane(this.table, 20, 30);
			scroll_table.setBorder(this.bord);
			scroll_text.setBorder(this.bord);
			GUIManager.myFrame.ButtonAction AL = new GUIManager.myFrame.ButtonAction();
			this.b_nextStep.addActionListener(AL);
			this.rightPanel.add(scroll_text);
			this.rightPanel.add(scroll_table);
			this.mainFrame.add(this.b_nextStep, "South");
		}

		public class ButtonAction implements ActionListener {
			public ButtonAction() {
			}

			public void actionPerformed(ActionEvent e) {
				String name = myFrame.this.b_nextStep.getText();
				int i;
				if(name.equals("Старт")) {
					myFrame.this.model.addColumn("Шаг");

					for(i = 0; i < GUIManager.g.getN(); ++i) {
						myFrame.this.model.addColumn("" + (i + 1));
					}

					i = Integer.parseInt(JOptionPane.showInputDialog("Введите исходную вершину"));
					if(GUIManager.g.isNegativeCycle(i)) {
						JOptionPane.showMessageDialog((Component)null, "В графе есть отрицательный цикл\n Будет выполнено n-1 шагов", "Загрузка", 1);
					}

					GUIManager.g.Ford_Bellman(i);
					myFrame.this.buttonDownload.setEnabled(false);
					myFrame.this.buttonDraw.setEnabled(false);
				}

				boolean var8 = false;
				int[] dl = GUIManager.g.get_table();
				String[] _dl = new String[dl.length + 1];
				myFrame.this.textAr.replaceRange("", 0, myFrame.this.textAr.getSelectionEnd());
				myFrame.this.textAr.insert("Шаг " + myFrame.this.labelStr + "\n", myFrame.this.textAr.getSelectionEnd());
				myFrame.this.textAr.insert("Рассматриваемое ребро: " + GUIManager.g.getEdges() + "\n", myFrame.this.textAr.getSelectionEnd());
				myFrame.this.textAr.insert("Список ребер:\n", myFrame.this.textAr.getSelectionEnd());
				i = Integer.valueOf(myFrame.this.labelStr).intValue();
				_dl[0] = Integer.toString(i - 1);

				for(int model = 0; model < dl.length; ++model) {
					if(dl[model] == 1400) {
						_dl[model + 1] = "INF";
					} else {
						_dl[model + 1] = Integer.toString(dl[model]);
					}
				}

				myFrame.this.textAr.insert(GUIManager.g.getEdge(), myFrame.this.textAr.getSelectionEnd());
				DefaultTableModel var9 = (DefaultTableModel)myFrame.this.table.getModel();
				var9.addRow(_dl);
				if(GUIManager.g.step()) {
					int j;
					for(j = 0; j < GUIManager.g.getM(); ++j) {
						GUIManager.g.setEdgeColor(j, 0);
					}

					for(j = 1; j <= GUIManager.g.getN(); ++j) {
						GUIManager.g.setColor(j, 0);
					}

					myFrame.this.b_nextStep.setText("Конец алгоритма");
					myFrame.this.b_nextStep.setEnabled(false);
				} else {
					myFrame.this.b_nextStep.setText("Следующий шаг");
				}

				++i;
				myFrame.this.labelStr = Integer.toString(i);
				GUIManager.g.drawGraph(myFrame.this.leftPanel.getGraphics());
			}
		}

		public class ButtonClear implements ActionListener {
			public ButtonClear() {
			}

			public void actionPerformed(ActionEvent e) {
				GUIManager.g = null;
				myFrame.this.textAr.replaceRange("", 0, myFrame.this.textAr.getSelectionEnd());

				while(myFrame.this.model.getRowCount() > 0) {
					myFrame.this.model.removeRow(0);
				}

				myFrame.this.model.setColumnCount(0);
				myFrame.this.labelStr = "1";
				myFrame.this.b_nextStep.setEnabled(true);
				myFrame.this.b_nextStep.setText("Старт");
				myFrame.this.buttonDownload.setEnabled(true);
				myFrame.this.buttonDraw.setEnabled(true);
				GUIManager.buttonRFFPressed = false;
				myFrame.this.leftPanel.repaint();
			}
		}

		public class ButtonHELP implements ActionListener {
			public ButtonHELP() {
			}

			public void actionPerformed(ActionEvent e) {
				String HELP = "Кнопка \"Загрузить\" загружает введеные заранее данные;\n\nКнопка \"Нарисовать\"предоставляет возможность Вам вручную задать граф;\n\nКнопка \"Старт\" начинает выполнение алгоритма.";
				JOptionPane.showMessageDialog((Component)null, HELP, "Справка", 1);
			}
		}

		public class ButtonID implements ActionListener {
			public ButtonID() {
			}

			public void actionPerformed(ActionEvent e) {
				JOptionPane.showMessageDialog((Component)null, "Чтобы нарисовать вершину кликните два раза по белой области\nЧтобы соединить вершины выберите две вершины\nВыбрать вершину можно кликнув по ней, она окраситься в синий цвет", "Помощь", 1);
				myFrame.this.buttonDownload.setEnabled(false);
				GUIManager.buttonRFFPressed = false;
			}
		}

		public class ButtonRFF implements ActionListener {
			public ButtonRFF() {
			}

			public void actionPerformed(ActionEvent e) {
				try {
					JFileChooser e1 = new JFileChooser();
					int ret = e1.showDialog((Component)null, "Открыть файл");
					if(ret == 0) {
						File file = e1.getSelectedFile();
						GUIManager.g = new Graph(file);
						JOptionPane.showMessageDialog((Component)null, "Данные загружены.", "Загрузка", 1);
						GUIManager.buttonRFFPressed = true;
						myFrame.this.buttonDraw.setEnabled(false);
						myFrame.this.textAr.setFont(new Font("ARIAL", 0, 14));
						myFrame.this.textAr.insert("Список ребер:\n", myFrame.this.textAr.getSelectionEnd());
						myFrame.this.textAr.insert(GUIManager.g.getEdge(), myFrame.this.textAr.getSelectionEnd());
					}
				} catch (FileNotFoundException var5) {
					JOptionPane.showMessageDialog((Component)null, "Файл не найден.", "Загрузка", 2);
				} catch (IOException var6) {
					var6.printStackTrace();
				}

			}
		}

		public class CustomListener implements MouseListener {
			public CustomListener() {
			}

			public void mouseClicked(MouseEvent e) {
				if(GUIManager.buttonRFFPressed) {
					GUIManager.g.drawGraph(myFrame.this.leftPanel.getGraphics());
				} else {
					int x = e.getX();
					int y = e.getY();
					int a = e.getClickCount();
					if(GUIManager.g == null && a == 2) {
						GUIManager.g = new Graph();
						GUIManager.g.addVert(x, y);
						GUIManager.g.drawGraph(myFrame.this.leftPanel.getGraphics());
					} else if(GUIManager.g != null) {
						int i = GUIManager.g.isInVert(x, y);
						if(e.getClickCount() == 2 && !GUIManager.g.alg) {
							if(!GUIManager.g.isToClose(x, y)) {
								GUIManager.g.addVert(x, y);
							} else {
								JOptionPane.showMessageDialog((Component)null, "Слишком близко к другим вершинам.", "Ввод", 1);
							}
						} else if(i != 0) {
							if(e.getButton() == 1) {
								if(GUIManager.select1 == 0) {
									GUIManager.select1 = i;
									GUIManager.g.setColor(GUIManager.select1, 1);
								} else if(GUIManager.select2 == 0) {
									GUIManager.select2 = i;
									GUIManager.g.setColor(GUIManager.select2, 1);
									boolean w = false;

									int w1;
									try {
										w1 = Integer.parseInt(JOptionPane.showInputDialog("Введите вес вершины"));
									} catch (NumberFormatException var8) {
										JOptionPane.showMessageDialog((Component)null, "Нужно ввести число.", "Ошыбка", 1);
										GUIManager.g.setColor(GUIManager.select1, 0);
										GUIManager.g.setColor(GUIManager.select2, 0);
										GUIManager.select2 = 0;
										GUIManager.select1 = 0;
										return;
									}

									GUIManager.g.setColor(GUIManager.select1, 0);
									GUIManager.g.setColor(GUIManager.select2, 0);
									GUIManager.g.addEdge(GUIManager.select1, GUIManager.select2, w1);
									GUIManager.select2 = 0;
									GUIManager.select1 = 0;
								}
							} else if(e.getButton() == 2) {
								GUIManager.select3 = 0;
								GUIManager.select2 = 0;
								GUIManager.select1 = 0;
							}
						}

						GUIManager.g.drawGraph(myFrame.this.leftPanel.getGraphics());
					}
				}
			}

			public void mouseEntered(MouseEvent e) {
			}

			public void mouseExited(MouseEvent e) {
			}

			public void mousePressed(MouseEvent e) {
			}

			public void mouseReleased(MouseEvent e) {
			}
		}

		public class CustomListener2 implements MouseMotionListener {
			public CustomListener2() {
			}

			public void mouseMoved(MouseEvent e) {
				GUIManager.g.drawGraph(myFrame.this.leftPanel.getGraphics());
			}

			public void mouseDragged(MouseEvent e) {
			}
		}
	}
	//	public Graphics getDrawGraphics() {
	//	return mainPanel.getGraphics();
	//}

}



