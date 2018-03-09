/**
 * 
 */

/**
 * @author phong
 *
 */
import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.io.IOException;

import javax.swing.*;
import javax.swing.UIManager.LookAndFeelInfo;
import javax.swing.filechooser.FileFilter;
import javax.swing.filechooser.FileNameExtensionFilter;

import javafx.util.Pair;
 
/** Testing menu-bar of JFrame */
public class P_TopicModeling extends JFrame {
 
	// A menu-bar contains menus. A menu contains menu-items (or sub-Menu)
	JMenuBar menuBar; // the menu-bar
	JMenu menu; // each menu in the menu-bar
	JMenuItem menuItem; // an item in a menu
	Container cp;
	JTextArea console;
	JMenuItem menuItem_Save_Graph;
	JMenuItem menuItem_Save_Graph_as;
	JMenu menu_Graph;
	JMenu menu_Breadth_First_Search;
	JMenu menu_Ford_Fulkerson;
	JMenu menu_Circulation;
	JMenuItem menuItem_Set_source_node;
	JMenuItem menuItem_Set_sink_node;
	JFileChooser openFileChooser;

	private static final long serialVersionUID = 1L;

	// Constructor
	P_TopicModeling(){
		GUI_Init_MainWindow();
	}
	public void GUI_Init_MainWindow() { /** Init setup the GUI - Run only 1 time */
		cp = getContentPane();
		cp.setLayout(new FlowLayout());

		console = new JTextArea(30, 70);
		console.setText(" Output console text: \n ");
		JScrollPane scrollPane = new JScrollPane(console);
		// textArea.setEditable(false);
		cp.add(scrollPane);

		openFileChooser = new JFileChooser(); // Create a file chooser
		FileFilter txtFileFilter = new FileNameExtensionFilter("text data file", "txt");
		openFileChooser.setFileFilter(txtFileFilter);

		menuBar = new JMenuBar();

		// File Menu
		menu = new JMenu(" File ");
		menu.setMnemonic(KeyEvent.VK_F); // alt short-cut key
		menuBar.add(menu); // the menu-bar adds this menu

		menuItem = new JMenuItem("New Graph", KeyEvent.VK_N);
		menu.add(menuItem); // the menu adds this item
		menuItem.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {

			}
		});
		menuItem = new JMenuItem("Check number of vertices in a graph", KeyEvent.VK_C);
		menu.add(menuItem); // the menu adds this item
		menuItem.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {

			}
		});
		menuItem = new JMenuItem("Open Graph", KeyEvent.VK_O);
		menu.add(menuItem); // the menu adds this item
		menuItem.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				Open_Graph_Clicked();

			}
		});
		menuItem_Save_Graph = new JMenuItem("Save Graph", KeyEvent.VK_S);
		menu.add(menuItem_Save_Graph); // the menu adds this item
		menuItem_Save_Graph.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {

			}
		});
		menuItem_Save_Graph_as = new JMenuItem("Save Graph as ...", KeyEvent.VK_A);
		menu.add(menuItem_Save_Graph_as); // the menu adds this item
		menuItem_Save_Graph_as.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {

			}
		});

		menuItem = new JMenuItem("Close", KeyEvent.VK_C);
		menu.add(menuItem); // the menu adds this item
		menuItem.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				Out("Current graph has been closed. Please create a new one by openning new input file. \n");
				Refresh_Menu();
			}
		});
		menu.addSeparator();
		menuItem = new JMenuItem("Exit", KeyEvent.VK_X);
		menu.add(menuItem); // the menu adds this item
		menuItem.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				System.exit(1);
			}
		});

		// Console Menu
		menu = new JMenu(" Console ");
		menu.setMnemonic(KeyEvent.VK_C); // alt short-cut key
		menuBar.add(menu); // the menu-bar adds this menu

		menuItem = new JMenuItem("Clear console window", KeyEvent.VK_X);
		menu.add(menuItem); // the menu adds this item
		menuItem.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
			}
		});

		// Graph Menu
		menu_Graph = new JMenu("Graph");
		menu_Graph.setMnemonic(KeyEvent.VK_G); // short-cut key
		menuBar.add(menu_Graph); // the menu bar adds this menu

		menuItem = new JMenuItem("Print Graph", KeyEvent.VK_P);
		menu_Graph.add(menuItem); // the menu adds this item
		menuItem.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
			}
		});

		menuItem = new JMenuItem("Show Graph visualization image", KeyEvent.VK_V);
		menu_Graph.add(menuItem); // the menu adds this item
		menuItem.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
			}
		});

		menu_Graph.addSeparator();

		menuItem = new JMenuItem("Add Edge", KeyEvent.VK_A);
		menu_Graph.add(menuItem); // the menu adds this item
		menuItem.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {

			}
		});
		menuItem = new JMenuItem("Remove Edge", KeyEvent.VK_R);
		menu_Graph.add(menuItem); // the menu adds this item
		menuItem.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {

			}
		});
		menuItem = new JMenuItem("Change Edge", KeyEvent.VK_C);
		menu_Graph.add(menuItem); // the menu adds this item
		menuItem.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {

			}
		});
		menuItem = new JMenuItem("Check Source Sink", KeyEvent.VK_K);
		menu_Graph.add(menuItem); // the menu adds this item
		menuItem.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {

			}
		});
		menu_Graph.addSeparator();
		menuItem = new JMenuItem("Options...", KeyEvent.VK_O);
		menu_Graph.add(menuItem); // the menu adds this item
		menuItem.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
			}
		});

		// Breadth-First Search Menu
		menu_Breadth_First_Search = new JMenu("Breadth-First Search");
		menu_Breadth_First_Search.setMnemonic(KeyEvent.VK_B); // short-cut key
		menuBar.add(menu_Breadth_First_Search); // the menu bar adds this menu

		menuItem = new JMenuItem("Print BFS (NoWeight)", KeyEvent.VK_P);
		menu_Breadth_First_Search.add(menuItem); // the menu adds this item
		menuItem.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
			}
		});

		menuItem = new JMenuItem("BFS (NoWeight) from a source to all the vertices", KeyEvent.VK_N);
		menu_Breadth_First_Search.add(menuItem); // the menu adds this item

		menuItem.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
			}
		});
		menuItem = new JMenuItem("BFS (NoWeight) from a source to a sink", KeyEvent.VK_N);
		menu_Breadth_First_Search.add(menuItem); // the menu adds this item
		menuItem.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
			}
		});

		menu_Breadth_First_Search.addSeparator();
		
		menuItem = new JMenuItem("Print BFS (Included the weight of every edges)", KeyEvent.VK_I);
		menu_Breadth_First_Search.add(menuItem); // the menu adds this item
		menuItem.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
			}
		});

		menuItem = new JMenuItem("BFS from a source to all the vertices", KeyEvent.VK_A);
		menu_Breadth_First_Search.add(menuItem); // the menu adds this item
		menuItem.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
			}
		});
		menuItem = new JMenuItem("BFS from a source to a sink", KeyEvent.VK_S);
		menu_Breadth_First_Search.add(menuItem); // the menu adds this item
		menuItem.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
			}
		});
		menu_Breadth_First_Search.addSeparator();

		menuItem = new JMenuItem("Options...", KeyEvent.VK_O);
		menu_Breadth_First_Search.add(menuItem); // the menu adds this item
		menuItem.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
			}
		});

		// Generate graphs Menu
		menu = new JMenu("Generate graphs");
		menu.setMnemonic(KeyEvent.VK_G); // alt short-cut key
		menuBar.add(menu); // the menu-bar adds this menu

		menuItem = new JMenuItem("Generate random edges", KeyEvent.VK_R);
		menu.add(menuItem); // the menu adds this item
		menuItem.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
			}
		});

		menu.addSeparator();

		menuItem = new JMenuItem("Set Max-Weight: 5", KeyEvent.VK_W);
		menu.add(menuItem); // the menu adds this item
		menuItem.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
			}
		});

		menuItem = new JMenuItem("Make a Flow Network", KeyEvent.VK_F);
		menu.add(menuItem); // the menu adds this item
		menuItem.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
			}
		});
		menuItem = new JMenuItem("Save to Adjacency List Files", KeyEvent.VK_S);
		menu.add(menuItem); // the menu adds this item
		menuItem.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
			}
		});
		menuItem = new JMenuItem("Save to Gephi CVS Files", KeyEvent.VK_G);
		menu.add(menuItem); // the menu adds this item
		menuItem.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				// display.setText(count + "");
			}
		});
		menuItem = new JMenuItem("Make a Possible Path from S to T", KeyEvent.VK_P);
		menu.add(menuItem); // the menu adds this item
		menuItem.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				// display.setText(count + "");
			}
		});
		menuItem = new JMenuItem("Make all vertices reachable FROM a vertex", KeyEvent.VK_V);
		menu.add(menuItem); // the menu adds this item
		menuItem.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
			}
		});
		menuItem = new JMenuItem("Make all vertices reachable TO a vertex", KeyEvent.VK_T);
		menu.add(menuItem); // the menu adds this item
		menuItem.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
			}
		});
		menuItem = new JMenuItem("Remove all outgoing edges from a vertex", KeyEvent.VK_O);
		menu.add(menuItem); // the menu adds this item
		menuItem.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
			}
		});
		menuItem = new JMenuItem("Remove all incoming edges to a vertex", KeyEvent.VK_I);
		menu.add(menuItem); // the menu adds this item
		menuItem.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
			}
		});
		menu.addSeparator();
		menuItem = new JMenuItem("Options...", KeyEvent.VK_L);
		menu.add(menuItem); // the menu adds this item
		menuItem.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
			}
		});

		// BFS runtime Menu
		menu = new JMenu("BFS runtime");
		menu.setMnemonic(KeyEvent.VK_T); // alt short-cut key
		menuBar.add(menu); // the menu-bar adds this menu

		menuItem = new JMenuItem("Start generating ... ", KeyEvent.VK_G);
		menu.add(menuItem); // the menu adds this item
		menuItem.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
			}
		});
		menu.addSeparator();
		menuItem = new JMenuItem("Set the maximum number of vertices: nMax=10000", KeyEvent.VK_V);
		menu.add(menuItem); // the menu adds this item
		menuItem.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
			}
		});

		menuItem = new JMenuItem("Set the maximum number of edges: eMax=100000", KeyEvent.VK_E);
		menu.add(menuItem); // the menu adds this item
		menuItem.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
			}
		});
		menuItem = new JMenuItem("Save the Runtime Analysis result", KeyEvent.VK_S);
		menu.add(menuItem); // the menu adds this item
		menuItem.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
			}
		});
		menuItem = new JMenuItem("Set power of #edges to #vetices: pow = 1.15", KeyEvent.VK_P);
		menu.add(menuItem); // the menu adds this item
		menuItem.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
			}
		});
		menu.addSeparator();
		menuItem = new JMenuItem("Options...", KeyEvent.VK_O);
		menu.add(menuItem); // the menu adds this item
		menuItem.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
			}
		});

		// Circulation Menu
		menu_Circulation = new JMenu("Circulation");
		menu_Circulation.setMnemonic(KeyEvent.VK_C); // alt short-cut key
		menuBar.add(menu_Circulation); // the menu-bar adds this menu

		menuItem = new JMenuItem("Testing to the circulation problem", KeyEvent.VK_T);
		menu_Circulation.add(menuItem); // the menu adds this item
		menuItem.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
			}
		});
		menu_Circulation.addSeparator();
		menuItem = new JMenuItem("Options...", KeyEvent.VK_O);
		menu_Circulation.add(menuItem); // the menu adds this item
		menuItem.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
			}
		});

		// Help Menu
		menu = new JMenu("Help");
		menu.setMnemonic(KeyEvent.VK_H); // alt short-cut key
		menuBar.add(menu); // the menu-bar adds this menu

		menuItem = new JMenuItem("Get help", KeyEvent.VK_G);
		menu.add(menuItem); // the menu adds this item
		menuItem.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
			}
		});
		menu.addSeparator();
		menuItem = new JMenuItem("About...", KeyEvent.VK_A);
		menu.add(menuItem); // the menu adds this item
		menuItem.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				String message = "Topic Modeling:\n"
						+ "Topic modeling application.   \n" + "\n"
						+ "______________________________________________________________________________\n" + "\n"
						+ "Version: 1.0\n" + "Build in 2-24-2018\n"
						+ "(c) Copyright by Phong Nguyen, Lolla 2017-2018.  All rights reserved. \n";
				JOptionPane.showMessageDialog(null, message, "About " + getTitle(), JOptionPane.INFORMATION_MESSAGE);
			}
		});
		setJMenuBar(menuBar); // "this" JFrame sets its menu-bar

		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setTitle("P TopicModeling");
		setIconImage(Toolkit.getDefaultToolkit().getImage("images/icon2.png"));

		setSize(800, 600);

		// Set position of the panel in the middle of the screen
		GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
		GraphicsDevice defaultScreen = ge.getDefaultScreenDevice();
		Rectangle rect = defaultScreen.getDefaultConfiguration().getBounds();
		int x = (int) (rect.getMaxX() - getWidth()) / 2;
		int y = (int) (rect.getMaxY() - getHeight()) / 2;
		setLocation(x, y);

		setVisible(true);
		setResizable(false);
	}

	public void Refresh_Menu() { /** display refresh many time to update the status change of the menu items */
		if (true)
			menuItem_Save_Graph.setEnabled(false);
		else
			menuItem_Save_Graph.setEnabled(true);
		if (true)
			menuItem_Save_Graph_as.setEnabled(false);
		else
			menuItem_Save_Graph_as.setEnabled(true);
		if (true)
			menu_Graph.setEnabled(false);
		else
			menu_Graph.setEnabled(true);
		if (true)
			menu_Breadth_First_Search.setEnabled(false);
		else
			menu_Breadth_First_Search.setEnabled(true);
		if (true)
			menu_Ford_Fulkerson.setEnabled(false);
		else
			menu_Ford_Fulkerson.setEnabled(true);
		if (true)
			menu_Circulation.setEnabled(false);
		else
			menu_Circulation.setEnabled(true);

		if (true)
			return; // if Graph is NULL then we dont check all the code bellow this line:

		if (true)
			menuItem_Set_source_node.setText("Set source node (Start vertex): S = NULL");
		if (true)
			menuItem_Set_sink_node.setText("Set sink node (Target vertex): T = NULL");
	}


	public void Out(String s) {
		console.setText(console.getText() + s);
	}

	public void Open_Graph_Clicked() {
		File selectDirectory = new File(System.getProperty("user.dir") + "\\input_files");
		if (selectDirectory.exists())
			openFileChooser.setCurrentDirectory(selectDirectory);
		else
			openFileChooser.setCurrentDirectory(new File(System.getProperty("user.dir")));

		int returnVal = openFileChooser.showOpenDialog(getParent());
		if (returnVal == JFileChooser.APPROVE_OPTION) {
			File file = openFileChooser.getSelectedFile();

			Out("Opening file : " + file.getName() + " ... \n");

			Refresh_Menu(); // reset to update the status of the inactive menu
		} else {
			// log.append("Open command cancelled by user." + newline);
		}

	}

	/** The entry main() method */
	public static void main(String[] args) {

		if (args.length < 1) {
			try {
				for (LookAndFeelInfo info : UIManager.getInstalledLookAndFeels()) {
					if ("Nimbus".equals(info.getName())) {
						UIManager.setLookAndFeel(info.getClassName());
						System.out.println(info.getClassName());
						break;
					}
				}
			} catch (Exception e) {
				// If Nimbus is not available, you can set the GUI to another look and feel.
				
			}

			// Run the GUI codes on the event-dispatching thread for thread safety
			SwingUtilities.invokeLater(new Runnable() {
				@Override
				public void run() {
					new P_TopicModeling();
				}
			});

		} else { // run in command line mode
			String fileName = args[1];

			if (args[0].equals("-b") || args[0].equals("–b")) {
			}
		}

	}

}
