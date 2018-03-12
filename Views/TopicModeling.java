package Views;

import java.awt.EventQueue;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import org.apache.tika.exception.TikaException;
import org.xml.sax.SAXException;

import cc.mallet.types.IDSorter;
import edu.mit.jwi.Dictionary;
import edu.mit.jwi.IDictionary;
import edu.mit.jwi.item.IIndexWord;
import edu.mit.jwi.item.ISynset;
import edu.mit.jwi.item.ISynsetID;
import edu.mit.jwi.item.IWordID;
import edu.mit.jwi.item.POS;
import edu.mit.jwi.item.Pointer;
import edu.mit.jwi.morph.WordnetStemmer;

import java.awt.Toolkit;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JMenuBar;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JSeparator;
import javax.swing.JTabbedPane;
import javax.swing.JScrollPane;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.JTextArea;
import javax.swing.RowSorter;
import javax.swing.JLayeredPane;
import javax.swing.JLabel;
import java.awt.Color;
import javax.swing.JButton;
import javax.swing.JList;
import javax.swing.AbstractListModel;
import javax.swing.DefaultListModel;

import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.MessageFormat;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Formatter;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.TreeSet;
import java.awt.event.ActionEvent;
import javax.swing.JProgressBar;
import javax.swing.ImageIcon;
import javax.swing.JTextField;
import javax.swing.JToggleButton;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;

import javax.swing.SwingConstants;
import javax.swing.event.ChangeListener;
import javax.swing.event.ChangeEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.awt.print.Book;
import java.awt.print.PageFormat;
import java.awt.print.Paper;
import java.awt.print.Printable;
import java.awt.print.PrinterException;
import java.awt.print.PrinterJob;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import javax.swing.JSpinner;
import javax.swing.SpinnerNumberModel;
import org.eclipse.wb.swing.FocusTraversalOnArray;
import java.awt.Component;
import javax.swing.JTable;
import javax.swing.JTable.PrintMode;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;
import javax.swing.SortOrder;
import javax.swing.ListModel;

public class TopicModeling extends JFrame {

	private static final long serialVersionUID = 1L;

	private JPanel contentPane;
	public static JButton btnExtractTextContents;
	public static JTextArea txtrCdata;
	public static JTextArea console;
	public static JProgressBar progressBar;
	public static createTextDataUsingTika process_createTextDataUsingTika;
	public static generateTopics process_GenerateTopics;
	public static printCompositionTable processPrintCompositionTable;
	public static boolean process_GenerateTopics_running = false;
	public static boolean processPrintCompositionTable_running = false;
	public static JButton btnXCancel;
	public static JButton btnGetFiles;
	@SuppressWarnings("rawtypes")
	public static JList list;

	@SuppressWarnings("rawtypes")
	public static DefaultListModel JListOfFiles;
	private JTextField txtDatatextfolder;
	private JToggleButton tglbtnUsedefaultdatatext;
	static JTextField txtTextdatafolder;
	private JLayeredPane layeredPane_1;
	static JTextField textAlphaSum;
	static JTextField textBeta;
	public static JButton btnEstimateTopics;
	static JButton btnCancelMallet;
	public static JProgressBar progressBarMallet;
	static JSpinner spinnerNumOfIterations;
	static JSpinner spinnerNumTopics;
	static JSpinner spinnerNumThreards;
	static JSpinner spinnerNumWordsInTopic;
	public static JTable table;
	@SuppressWarnings("rawtypes")
	public static JList listTopics;

	@SuppressWarnings("rawtypes")
	public static DefaultListModel JListOfTopics, JListOfTopicsWordnet, JListOfTopicsWordnetParent;
	public static DefaultTableModel compositionTable;
	public static JButton btnGetCompositionTable;
	private JMenuItem mntmRestartThisApplication;
	private JMenuItem mntmExit;
	private JMenuItem mntmPreferences;
	private JMenuItem mntmClose;
	private JMenuItem mntmSaveAs;
	private JMenuItem mntmSave;
	private JMenuItem mntmOpen;
	private JMenuItem mntmNew;
	private JMenuItem mntmClearConsole;
	private JMenuItem mntmTurnOff;
	private JMenuItem mntmExtractTextContents;
	private JMenuItem mntmOption;
	private JMenuItem mntmEstimateTopicsUsing;
	private JMenuItem mntmGetCompositionTable;
	private JMenuItem mntmOptions;
	private JMenuItem mntmGetHelp;
	private JMenuItem mntmUpdate;
	private JMenuItem mntmAbout;
	private JTabbedPane tabbedPane;
	private JMenuItem mntmPrintTheComposition;
	private JMenuItem mntmPrintTheList;
	private JMenuItem mntmAutomaticallyDetectAppropriate;
	private JButton btnAutoDetectNumber;
	private JTextField textFieldCompositionTable;
	private JScrollPane scrollPane_4;
	
	public static boolean NoConsoleOutput;

	public static processAutoDetectNumberOfTopics threadProcessAutoDetectNumberOfTopics;

	public static boolean ProcessAutoDetectNumberOfTopics_running;
	
	static int NumberOfTopic;

	static int SizeOfInstance;
	autoDetectNumTopics autoTopic;
	private JTextField textField;
	private JButton btnGetStemWords;
	private JSpinner spinnerNumWordTipicWordnet;
	private JList listWordnet;
	
	static IDictionary dict;
	public static final int MAX_DEPTH_OF_HIERARCHY = 16;
	private static WordnetStemmer wordnetStemmer;
	private JTextField txtDatatextstem;
	private JSpinner spinnerParentUpLevel;
	private JList listTopicsWordnetParent;
	private JSpinner spinner;
	private JSpinner spinner_1;

	public void InitWordnet(String wnhome) throws IOException {

		// construct the URL to the Wordnet dictionary directory
		String path = wnhome + File.separator + "dict";
		URL url = null;
		try {
			url = new URL("file", null, path);
		} catch (MalformedURLException e) {
			e.printStackTrace();
		}
		if (url == null)
			return;

		// construct the dictionary object and open it
		dict = new Dictionary(url);
		dict.open();
		wordnetStemmer = new WordnetStemmer(dict);

	}
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	private void initComponents() {
		setIconImage(Toolkit.getDefaultToolkit().getImage(TopicModeling.class.getResource("/Resources/icon2.png")));
		setTitle("P Topic Modeling");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 1200, 700);

		JMenuBar menuBar = new JMenuBar();
		setJMenuBar(menuBar);

		JMenu mnFile = new JMenu("File");
		menuBar.add(mnFile);

		mntmNew = new JMenuItem("New");
		mnFile.add(mntmNew);

		mntmOpen = new JMenuItem("Open");
		mnFile.add(mntmOpen);

		JMenu mnOpenRecently = new JMenu("Open recently");
		mnFile.add(mnOpenRecently);

		mntmSave = new JMenuItem("Save");
		mnFile.add(mntmSave);

		mntmSaveAs = new JMenuItem("Save as ...");
		mnFile.add(mntmSaveAs);

		mntmClose = new JMenuItem("Close");
		mnFile.add(mntmClose);

		JSeparator separator = new JSeparator();
		mnFile.add(separator);

		mntmPreferences = new JMenuItem("Preferences");
		mnFile.add(mntmPreferences);

		JSeparator separator_16 = new JSeparator();
		mnFile.add(separator_16);

		mntmRestartThisApplication = new JMenuItem("Restart this application ...");

		mnFile.add(mntmRestartThisApplication);

		JSeparator separator_12 = new JSeparator();
		mnFile.add(separator_12);

		mntmExit = new JMenuItem("Exit");

		mnFile.add(mntmExit);

		JMenu mnConsole = new JMenu("Console");
		menuBar.add(mnConsole);

		mntmClearConsole = new JMenuItem("Clear console");

		mnConsole.add(mntmClearConsole);

		mntmTurnOff = new JMenuItem("Turn off");

		mnConsole.add(mntmTurnOff);

		JMenu mnImportLectures = new JMenu("Extract documents");
		menuBar.add(mnImportLectures);

		mntmExtractTextContents = new JMenuItem("Extract text contents from folder");

		mnImportLectures.add(mntmExtractTextContents);

		JSeparator separator_15 = new JSeparator();
		mnImportLectures.add(separator_15);

		mntmOption = new JMenuItem("Option ...");
		mnImportLectures.add(mntmOption);

		JMenu mnEstimateTopics = new JMenu("Estimate topics");
		menuBar.add(mnEstimateTopics);
		
		mntmAutomaticallyDetectAppropriate = new JMenuItem("Automatically detect appropriate number of topics");
		
		mnEstimateTopics.add(mntmAutomaticallyDetectAppropriate);
		
		JSeparator separator_18 = new JSeparator();
		mnEstimateTopics.add(separator_18);

		mntmEstimateTopicsUsing = new JMenuItem("Estimate topics using Mallet");

		mnEstimateTopics.add(mntmEstimateTopicsUsing);

		mntmGetCompositionTable = new JMenuItem("Get new composition table from folder");

		mnEstimateTopics.add(mntmGetCompositionTable);

		JSeparator separator_14 = new JSeparator();
		mnEstimateTopics.add(separator_14);
		
		mntmPrintTheList = new JMenuItem("Print the list of topics (send to printer device)");
		
		mnEstimateTopics.add(mntmPrintTheList);

		mntmPrintTheComposition = new JMenuItem("Print the composition table result (send to printer devide)");

		mnEstimateTopics.add(mntmPrintTheComposition);

		JSeparator separator_17 = new JSeparator();
		mnEstimateTopics.add(separator_17);

		mntmOptions = new JMenuItem("Options ...");
		mnEstimateTopics.add(mntmOptions);
		
		JMenu mnLexicalDatabase = new JMenu("Lexical database");
		menuBar.add(mnLexicalDatabase);
		
		JMenu mnAutomateTask = new JMenu("Automate tasks");
		menuBar.add(mnAutomateTask);

		JMenu mnHelp = new JMenu("Help");
		menuBar.add(mnHelp);

		mntmGetHelp = new JMenuItem("Get help");
		mnHelp.add(mntmGetHelp);

		JSeparator separator_1 = new JSeparator();
		mnHelp.add(separator_1);

		mntmUpdate = new JMenuItem("Check for updates");
		mnHelp.add(mntmUpdate);

		JSeparator separator_2 = new JSeparator();
		mnHelp.add(separator_2);

		mntmAbout = new JMenuItem("About");
		mnHelp.add(mntmAbout);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);

		tabbedPane = new JTabbedPane(JTabbedPane.TOP);

		console = new JTextArea(6, 0);
		console.setText(" Output console text: \n ");

		JScrollPane scrollPane = new JScrollPane(console);

		GroupLayout gl_contentPane = new GroupLayout(contentPane);
		gl_contentPane.setHorizontalGroup(
			gl_contentPane.createParallelGroup(Alignment.TRAILING)
				.addGroup(gl_contentPane.createSequentialGroup()
					.addGroup(gl_contentPane.createParallelGroup(Alignment.TRAILING)
						.addComponent(scrollPane, Alignment.LEADING, GroupLayout.DEFAULT_SIZE, 1173, Short.MAX_VALUE)
						.addComponent(tabbedPane, GroupLayout.DEFAULT_SIZE, 1173, Short.MAX_VALUE))
					.addGap(1))
		);
		gl_contentPane.setVerticalGroup(
			gl_contentPane.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_contentPane.createSequentialGroup()
					.addComponent(tabbedPane, GroupLayout.PREFERRED_SIZE, 490, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.UNRELATED)
					.addComponent(scrollPane, GroupLayout.DEFAULT_SIZE, 109, Short.MAX_VALUE)
					.addGap(20))
		);

		JLayeredPane layeredPane = new JLayeredPane();
		tabbedPane.addTab("Extract documents using Tika", null, layeredPane, null);

		JLabel lblFilesAndDocuments = new JLabel("Files and documents");
		lblFilesAndDocuments.setFont(new Font("Tahoma", Font.BOLD, 11));

		JScrollPane scrollPane_1 = new JScrollPane();

		txtrCdata = new JTextArea();

		scrollPane_1.setViewportView(txtrCdata);
		txtrCdata.setBackground(Color.WHITE);
		txtrCdata.setToolTipText("Please input the the folder where you want to get documents and files");
		txtrCdata.setText("data");

		JButton btnNew = new JButton("New");

		JButton btnCheck = new JButton("Check");

		JButton btnBrowse = new JButton("Browse...");

		JButton btnSave = new JButton("Save");

		JScrollPane scrollPane_2 = new JScrollPane();

		JListOfFiles = new DefaultListModel();
		list = new JList(JListOfFiles);
		scrollPane_2.setViewportView(list);

		btnGetFiles = new JButton("Get files >>");

		btnExtractTextContents = new JButton("Extract text contents");

		progressBar = new JProgressBar();

		btnXCancel = new JButton("Cancel");
		btnXCancel.setEnabled(false);

		btnXCancel.setIcon(new ImageIcon(TopicModeling.class.getResource("/Resources/cancel6_16x16.png")));

		txtDatatextfolder = new JTextField();

		txtDatatextfolder.setEditable(false);
		txtDatatextfolder.setText("data_text");
		txtDatatextfolder.setColumns(10);

		tglbtnUsedefaultdatatext = new JToggleButton("");

		tglbtnUsedefaultdatatext.setSelected(true);

		JLabel lblDefaultOutputText = new JLabel("Ues default output text folder:");
		lblDefaultOutputText.setFont(new Font("Tahoma", Font.PLAIN, 10));
		lblDefaultOutputText.setHorizontalAlignment(SwingConstants.RIGHT);
		GroupLayout gl_layeredPane = new GroupLayout(layeredPane);
		gl_layeredPane.setHorizontalGroup(
			gl_layeredPane.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_layeredPane.createSequentialGroup()
					.addGap(10)
					.addComponent(lblFilesAndDocuments, GroupLayout.PREFERRED_SIZE, 121, GroupLayout.PREFERRED_SIZE)
					.addGap(11)
					.addComponent(lblDefaultOutputText, GroupLayout.PREFERRED_SIZE, 162, GroupLayout.PREFERRED_SIZE)
					.addGap(10)
					.addComponent(tglbtnUsedefaultdatatext, GroupLayout.PREFERRED_SIZE, 28, GroupLayout.PREFERRED_SIZE)
					.addGap(10)
					.addComponent(txtDatatextfolder, GroupLayout.PREFERRED_SIZE, 304, GroupLayout.PREFERRED_SIZE))
				.addGroup(gl_layeredPane.createSequentialGroup()
					.addGap(10)
					.addComponent(scrollPane_1, GroupLayout.PREFERRED_SIZE, 294, GroupLayout.PREFERRED_SIZE)
					.addGap(48)
					.addComponent(scrollPane_2, GroupLayout.PREFERRED_SIZE, 304, GroupLayout.PREFERRED_SIZE))
				.addGroup(gl_layeredPane.createSequentialGroup()
					.addGap(10)
					.addComponent(btnNew, GroupLayout.PREFERRED_SIZE, 72, GroupLayout.PREFERRED_SIZE)
					.addGap(20)
					.addComponent(btnCheck, GroupLayout.PREFERRED_SIZE, 72, GroupLayout.PREFERRED_SIZE)
					.addGap(41)
					.addComponent(btnBrowse, GroupLayout.PREFERRED_SIZE, 89, GroupLayout.PREFERRED_SIZE)
					.addGap(48)
					.addComponent(btnExtractTextContents, GroupLayout.PREFERRED_SIZE, 304, GroupLayout.PREFERRED_SIZE))
				.addGroup(gl_layeredPane.createSequentialGroup()
					.addGap(10)
					.addComponent(btnSave, GroupLayout.PREFERRED_SIZE, 72, GroupLayout.PREFERRED_SIZE)
					.addGap(123)
					.addComponent(btnGetFiles, GroupLayout.PREFERRED_SIZE, 99, GroupLayout.PREFERRED_SIZE))
				.addGroup(Alignment.TRAILING, gl_layeredPane.createSequentialGroup()
					.addComponent(progressBar, GroupLayout.DEFAULT_SIZE, 1073, Short.MAX_VALUE)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(btnXCancel, GroupLayout.PREFERRED_SIZE, 99, GroupLayout.PREFERRED_SIZE)
					.addContainerGap())
		);
		gl_layeredPane.setVerticalGroup(
			gl_layeredPane.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_layeredPane.createSequentialGroup()
					.addGroup(gl_layeredPane.createParallelGroup(Alignment.LEADING)
						.addComponent(lblFilesAndDocuments, GroupLayout.PREFERRED_SIZE, 26, GroupLayout.PREFERRED_SIZE)
						.addGroup(gl_layeredPane.createSequentialGroup()
							.addGap(7)
							.addComponent(lblDefaultOutputText, GroupLayout.PREFERRED_SIZE, 14, GroupLayout.PREFERRED_SIZE))
						.addGroup(gl_layeredPane.createSequentialGroup()
							.addGap(4)
							.addComponent(tglbtnUsedefaultdatatext, GroupLayout.PREFERRED_SIZE, 23, GroupLayout.PREFERRED_SIZE))
						.addGroup(gl_layeredPane.createSequentialGroup()
							.addGap(4)
							.addComponent(txtDatatextfolder, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)))
					.addGap(10)
					.addGroup(gl_layeredPane.createParallelGroup(Alignment.LEADING)
						.addComponent(scrollPane_1, GroupLayout.PREFERRED_SIZE, 321, GroupLayout.PREFERRED_SIZE)
						.addComponent(scrollPane_2, GroupLayout.PREFERRED_SIZE, 325, GroupLayout.PREFERRED_SIZE))
					.addGap(10)
					.addGroup(gl_layeredPane.createParallelGroup(Alignment.LEADING)
						.addComponent(btnNew)
						.addComponent(btnCheck)
						.addComponent(btnBrowse)
						.addGroup(gl_layeredPane.createSequentialGroup()
							.addGap(1)
							.addComponent(btnExtractTextContents)))
					.addGap(10)
					.addGroup(gl_layeredPane.createParallelGroup(Alignment.LEADING)
						.addComponent(btnSave)
						.addComponent(btnGetFiles))
					.addGap(11)
					.addGroup(gl_layeredPane.createParallelGroup(Alignment.LEADING)
						.addComponent(btnXCancel, GroupLayout.PREFERRED_SIZE, 23, GroupLayout.PREFERRED_SIZE)
						.addComponent(progressBar, GroupLayout.PREFERRED_SIZE, 23, GroupLayout.PREFERRED_SIZE))
					.addContainerGap())
		);
		layeredPane.setLayout(gl_layeredPane);

		layeredPane_1 = new JLayeredPane();

		tabbedPane.addTab("Estimate topics using Mallet", null, layeredPane_1, null);

		JLabel lblNewLabel = new JLabel("Text data folder:");
		lblNewLabel.setHorizontalAlignment(SwingConstants.RIGHT);

		txtTextdatafolder = new JTextField();
		
		txtTextdatafolder.setText("data_text");
		txtTextdatafolder.setColumns(10);

		JLabel lblNumberOfTopics = new JLabel("Number of topics:");
		lblNumberOfTopics.setHorizontalAlignment(SwingConstants.RIGHT);

		spinnerNumTopics = new JSpinner();
		spinnerNumTopics.setModel(new SpinnerNumberModel(10, 1, 5000, 1));

		JLabel lblParalleltopicmodel = new JLabel("Parallel Topic Model");
		lblParalleltopicmodel.setHorizontalAlignment(SwingConstants.RIGHT);

		JSeparator separator_3 = new JSeparator();

		JSeparator separator_4 = new JSeparator();

		JSeparator separator_5 = new JSeparator();
		separator_5.setOrientation(SwingConstants.VERTICAL);

		JSeparator separator_6 = new JSeparator();
		separator_6.setOrientation(SwingConstants.VERTICAL);

		JSeparator separator_11 = new JSeparator();

		JLabel lblThreads = new JLabel("# threads:");
		lblThreads.setFont(new Font("Tahoma", Font.PLAIN, 10));
		lblThreads.setHorizontalAlignment(SwingConstants.RIGHT);

		spinnerNumThreards = new JSpinner();
		spinnerNumThreards.setToolTipText("Example, if we use 2 parallel samplers, which each look at one half the corpus and combine");
		spinnerNumThreards.setModel(new SpinnerNumberModel(2, 1, 20, 1));

		JLabel lblAlphasum = new JLabel("alphaSum:");
		lblAlphasum.setFont(new Font("Tahoma", Font.PLAIN, 10));
		lblAlphasum.setHorizontalAlignment(SwingConstants.RIGHT);

		textAlphaSum = new JTextField();
		textAlphaSum.setToolTipText("parameter is passed as the sum over topics");
		textAlphaSum.setText("1.0");
		textAlphaSum.setColumns(10);

		JLabel lblBeta = new JLabel("beta:");
		lblBeta.setFont(new Font("Tahoma", Font.PLAIN, 10));
		lblBeta.setHorizontalAlignment(SwingConstants.RIGHT);

		textBeta = new JTextField();
		textBeta.setToolTipText("the parameter for a single dimension of the Dirichlet prior");
		textBeta.setText("0.01");
		textBeta.setColumns(10);

		JLabel lblNumIterations = new JLabel("Numbe of iterations:");
		lblNumIterations.setFont(new Font("Tahoma", Font.PLAIN, 12));
		lblNumIterations.setHorizontalAlignment(SwingConstants.RIGHT);

		spinnerNumOfIterations = new JSpinner();
		
		spinnerNumOfIterations.setToolTipText("for testing only: you could use about 50 iterations , for real applications: use 1000 to 2000 iterations");
		spinnerNumOfIterations.setModel(new SpinnerNumberModel(50, 1, 10000, 1));

		btnEstimateTopics = new JButton("Estimate topics");

		btnEstimateTopics.setFont(new Font("Tahoma", Font.BOLD, 12));

		progressBarMallet = new JProgressBar();

		btnCancelMallet = new JButton("Cancel");

		btnCancelMallet.setIcon(new ImageIcon(TopicModeling.class.getResource("/Resources/cancel6_16x16.png")));
		btnCancelMallet.setEnabled(false);

		JLabel lblNumberOfTop = new JLabel("Number of Top Words in a topic:");
		lblNumberOfTop.setFont(new Font("Tahoma", Font.PLAIN, 10));
		lblNumberOfTop.setHorizontalAlignment(SwingConstants.RIGHT);

		spinnerNumWordsInTopic = new JSpinner();

		spinnerNumWordsInTopic.setModel(new SpinnerNumberModel(5, 1, 20, 1));

		JSeparator separator_7 = new JSeparator();

		JSeparator separator_8 = new JSeparator();
		separator_8.setOrientation(SwingConstants.VERTICAL);

		JSeparator separator_9 = new JSeparator();

		JSeparator separator_10 = new JSeparator();
		separator_10.setOrientation(SwingConstants.VERTICAL);

		JLabel lblBuildPipe = new JLabel("Build the Serial Pipe");
		lblBuildPipe.setHorizontalAlignment(SwingConstants.CENTER);

		JScrollPane scrollPane_5 = new JScrollPane();

		JList list_1 = new JList();
		list_1.setFont(new Font("Tahoma", Font.PLAIN, 11));
		list_1.setModel(new AbstractListModel() {
			String[] values = new String[] { "Input2CharSequence(\"UTF-8\")", "CharSequence2TokenSequence(\"\\\\w+\")",
					"TokenSequenceLowercase()", "TokenSequenceRemoveStopwords( File(\"mallet/stoplists/en.txt\") ) ",
					"TokenSequence2FeatureSequence()", "Target2Label()" };

			public int getSize() {
				return values.length;
			}

			public Object getElementAt(int index) {
				return values[index];
			}
		});
		scrollPane_5.setViewportView(list_1);

		scrollPane_4 = new JScrollPane();

		compositionTable = new DefaultTableModel(new Object[][] {

		}, new String[] { "#", "Composition of the InstanceList:", "Topic 0" });
		table = new JTable(compositionTable);
		table.setCellSelectionEnabled(true);
		table.setColumnSelectionAllowed(true);
		scrollPane_4.setViewportView(table);

		table.getColumnModel().getColumn(0).setPreferredWidth(30);
		table.getColumnModel().getColumn(0).setMaxWidth(100);
		table.getColumnModel().getColumn(1).setPreferredWidth(250);
		table.getColumnModel().getColumn(1).setMinWidth(100);
		table.getColumnModel().getColumn(1).setMaxWidth(1000);
		table.getColumnModel().getColumn(2).setPreferredWidth(50);
		table.getColumnModel().getColumn(2).setMinWidth(10);
		table.getColumnModel().getColumn(2).setMaxWidth(100);

		JScrollPane scrollPane_3 = new JScrollPane();

		JListOfTopics = new DefaultListModel();
		listTopics = new JList(JListOfTopics);

		listTopics.setSelectedIndex(0);
		scrollPane_3.setViewportView(listTopics);

		btnGetCompositionTable = new JButton("Get composition table from new folder");
		btnGetCompositionTable.setFont(new Font("Tahoma", Font.PLAIN, 11));

		btnGetCompositionTable.setEnabled(false);

		JButton button = new JButton("");
		button.setIcon(new ImageIcon(TopicModeling.class.getResource("/Resources/Up4.png")));

		JButton button_1 = new JButton("");
		button_1.setIcon(new ImageIcon(TopicModeling.class.getResource("/Resources/down4.png")));
		
		btnAutoDetectNumber = new JButton("Auto detect");
		btnAutoDetectNumber.setFont(new Font("Tahoma", Font.PLAIN, 11));
		
		textFieldCompositionTable = new JTextField();
		textFieldCompositionTable.setText("data_text");
		textFieldCompositionTable.setColumns(10);
		
		JLabel lblOptimizeinterval = new JLabel("optimize-interval");
		lblOptimizeinterval.setHorizontalAlignment(SwingConstants.RIGHT);
		
		spinner = new JSpinner();
		spinner.setModel(new SpinnerNumberModel(10, 0, 1000, 1));
		spinner.setToolTipText("--optimize-interval [NUMBER]\u00A0This option turns on hyperparameter optimization, which allows the model to better fit the data by allowing some topics to be more prominent than others. Optimization every 10 iterations is reasonable.");
		
		spinner_1 = new JSpinner();
		spinner_1.setModel(new SpinnerNumberModel(20, 0, 2000, 1));
		spinner_1.setToolTipText("--optimize-burn-in [NUMBER]\u00A0The number of iterations before hyperparameter optimization begins. Default is twice the optimize interval.");
		
		JLabel lblBurnin = new JLabel("burn-in");
		lblBurnin.setHorizontalAlignment(SwingConstants.RIGHT);
		
		JButton button_2 = new JButton("...");
		button_2.setToolTipText("Full range of possible parameters of train-topics ");
		GroupLayout gl_layeredPane_1 = new GroupLayout(layeredPane_1);
		gl_layeredPane_1.setHorizontalGroup(
			gl_layeredPane_1.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_layeredPane_1.createSequentialGroup()
					.addGap(10)
					.addGroup(gl_layeredPane_1.createParallelGroup(Alignment.LEADING)
						.addGroup(gl_layeredPane_1.createSequentialGroup()
							.addComponent(lblNewLabel, GroupLayout.PREFERRED_SIZE, 121, GroupLayout.PREFERRED_SIZE)
							.addGap(10)
							.addComponent(txtTextdatafolder, GroupLayout.PREFERRED_SIZE, 205, GroupLayout.PREFERRED_SIZE))
						.addGroup(gl_layeredPane_1.createSequentialGroup()
							.addComponent(lblNumberOfTopics, GroupLayout.PREFERRED_SIZE, 121, GroupLayout.PREFERRED_SIZE)
							.addGap(11)
							.addComponent(spinnerNumTopics, GroupLayout.PREFERRED_SIZE, 66, GroupLayout.PREFERRED_SIZE)
							.addGap(10)
							.addComponent(btnAutoDetectNumber, GroupLayout.PREFERRED_SIZE, 128, GroupLayout.PREFERRED_SIZE))
						.addGroup(gl_layeredPane_1.createSequentialGroup()
							.addGroup(gl_layeredPane_1.createParallelGroup(Alignment.LEADING)
								.addComponent(lblThreads, GroupLayout.PREFERRED_SIZE, 76, GroupLayout.PREFERRED_SIZE)
								.addComponent(separator_6, GroupLayout.PREFERRED_SIZE, 13, GroupLayout.PREFERRED_SIZE))
							.addGap(1)
							.addComponent(spinnerNumThreards, GroupLayout.PREFERRED_SIZE, 35, GroupLayout.PREFERRED_SIZE)
							.addGap(5)
							.addGroup(gl_layeredPane_1.createParallelGroup(Alignment.LEADING)
								.addGroup(gl_layeredPane_1.createSequentialGroup()
									.addGap(190)
									.addComponent(button_2, GroupLayout.PREFERRED_SIZE, 13, GroupLayout.PREFERRED_SIZE))
								.addGroup(gl_layeredPane_1.createSequentialGroup()
									.addGap(132)
									.addComponent(spinner_1, GroupLayout.PREFERRED_SIZE, 45, GroupLayout.PREFERRED_SIZE))
								.addGroup(gl_layeredPane_1.createSequentialGroup()
									.addGap(14)
									.addComponent(separator_4, GroupLayout.PREFERRED_SIZE, 205, GroupLayout.PREFERRED_SIZE))
								.addGroup(gl_layeredPane_1.createSequentialGroup()
									.addGap(139)
									.addComponent(textBeta, GroupLayout.PREFERRED_SIZE, 35, GroupLayout.PREFERRED_SIZE))
								.addComponent(lblAlphasum, GroupLayout.PREFERRED_SIZE, 66, GroupLayout.PREFERRED_SIZE)
								.addGroup(gl_layeredPane_1.createSequentialGroup()
									.addGap(10)
									.addComponent(spinner, GroupLayout.PREFERRED_SIZE, 39, GroupLayout.PREFERRED_SIZE))
								.addGroup(gl_layeredPane_1.createSequentialGroup()
									.addGap(218)
									.addComponent(separator_5, GroupLayout.PREFERRED_SIZE, 13, GroupLayout.PREFERRED_SIZE))))
						.addGroup(gl_layeredPane_1.createSequentialGroup()
							.addGap(169)
							.addComponent(lblBurnin, GroupLayout.PREFERRED_SIZE, 76, GroupLayout.PREFERRED_SIZE))
						.addComponent(separator_11, GroupLayout.PREFERRED_SIZE, 13, GroupLayout.PREFERRED_SIZE)
						.addGroup(gl_layeredPane_1.createSequentialGroup()
							.addGap(221)
							.addComponent(lblBeta, GroupLayout.PREFERRED_SIZE, 35, GroupLayout.PREFERRED_SIZE))
						.addGroup(gl_layeredPane_1.createSequentialGroup()
							.addGap(27)
							.addComponent(lblOptimizeinterval, GroupLayout.PREFERRED_SIZE, 94, GroupLayout.PREFERRED_SIZE))
						.addGroup(gl_layeredPane_1.createSequentialGroup()
							.addGap(182)
							.addComponent(textAlphaSum, GroupLayout.PREFERRED_SIZE, 35, GroupLayout.PREFERRED_SIZE))
						.addGroup(gl_layeredPane_1.createSequentialGroup()
							.addGap(10)
							.addComponent(lblParalleltopicmodel, GroupLayout.PREFERRED_SIZE, 119, GroupLayout.PREFERRED_SIZE))
						.addGroup(gl_layeredPane_1.createSequentialGroup()
							.addGap(131)
							.addComponent(spinnerNumOfIterations, GroupLayout.PREFERRED_SIZE, 66, GroupLayout.PREFERRED_SIZE))
						.addComponent(lblNumIterations, GroupLayout.PREFERRED_SIZE, 121, GroupLayout.PREFERRED_SIZE)
						.addComponent(separator_3, GroupLayout.PREFERRED_SIZE, 336, GroupLayout.PREFERRED_SIZE)
						.addGroup(gl_layeredPane_1.createSequentialGroup()
							.addGroup(gl_layeredPane_1.createParallelGroup(Alignment.LEADING)
								.addComponent(btnEstimateTopics, GroupLayout.PREFERRED_SIZE, 129, GroupLayout.PREFERRED_SIZE)
								.addGroup(gl_layeredPane_1.createSequentialGroup()
									.addGap(117)
									.addComponent(lblNumberOfTop, GroupLayout.PREFERRED_SIZE, 174, GroupLayout.PREFERRED_SIZE)))
							.addComponent(spinnerNumWordsInTopic, GroupLayout.PREFERRED_SIZE, 45, GroupLayout.PREFERRED_SIZE)))
					.addGap(9)
					.addGroup(gl_layeredPane_1.createParallelGroup(Alignment.LEADING)
						.addGroup(gl_layeredPane_1.createSequentialGroup()
							.addGap(1)
							.addGroup(gl_layeredPane_1.createParallelGroup(Alignment.LEADING)
								.addGroup(gl_layeredPane_1.createSequentialGroup()
									.addGap(5)
									.addComponent(scrollPane_5, GroupLayout.PREFERRED_SIZE, 297, GroupLayout.PREFERRED_SIZE))
								.addGroup(gl_layeredPane_1.createSequentialGroup()
									.addGap(346)
									.addComponent(separator_10, GroupLayout.PREFERRED_SIZE, 13, GroupLayout.PREFERRED_SIZE))
								.addGroup(gl_layeredPane_1.createSequentialGroup()
									.addGap(124)
									.addComponent(separator_9, GroupLayout.PREFERRED_SIZE, 223, GroupLayout.PREFERRED_SIZE))
								.addComponent(lblBuildPipe, GroupLayout.PREFERRED_SIZE, 136, GroupLayout.PREFERRED_SIZE)
								.addGroup(gl_layeredPane_1.createSequentialGroup()
									.addGap(310)
									.addComponent(button, GroupLayout.PREFERRED_SIZE, 26, GroupLayout.PREFERRED_SIZE))
								.addComponent(separator_8, GroupLayout.PREFERRED_SIZE, 13, GroupLayout.PREFERRED_SIZE)
								.addComponent(separator_7, GroupLayout.PREFERRED_SIZE, 347, GroupLayout.PREFERRED_SIZE)
								.addGroup(gl_layeredPane_1.createSequentialGroup()
									.addGap(310)
									.addComponent(button_1, GroupLayout.PREFERRED_SIZE, 26, GroupLayout.PREFERRED_SIZE))))
						.addGroup(gl_layeredPane_1.createSequentialGroup()
							.addComponent(btnGetCompositionTable, GroupLayout.PREFERRED_SIZE, 230, GroupLayout.PREFERRED_SIZE)
							.addGap(10)
							.addComponent(textFieldCompositionTable, GroupLayout.PREFERRED_SIZE, 106, GroupLayout.PREFERRED_SIZE))))
				.addGroup(gl_layeredPane_1.createSequentialGroup()
					.addGap(10)
					.addComponent(scrollPane_3, GroupLayout.PREFERRED_SIZE, 336, GroupLayout.PREFERRED_SIZE)
					.addGap(21)
					.addComponent(scrollPane_4, GroupLayout.DEFAULT_SIZE, 791, Short.MAX_VALUE)
					.addGap(10))
				.addGroup(gl_layeredPane_1.createSequentialGroup()
					.addComponent(progressBarMallet, GroupLayout.DEFAULT_SIZE, 1069, Short.MAX_VALUE)
					.addComponent(btnCancelMallet, GroupLayout.PREFERRED_SIZE, 99, GroupLayout.PREFERRED_SIZE))
		);
		gl_layeredPane_1.setVerticalGroup(
			gl_layeredPane_1.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_layeredPane_1.createSequentialGroup()
					.addGap(8)
					.addGroup(gl_layeredPane_1.createParallelGroup(Alignment.LEADING)
						.addGroup(gl_layeredPane_1.createSequentialGroup()
							.addGroup(gl_layeredPane_1.createParallelGroup(Alignment.LEADING)
								.addGroup(gl_layeredPane_1.createSequentialGroup()
									.addGap(3)
									.addComponent(lblNewLabel))
								.addComponent(txtTextdatafolder, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
							.addGap(7)
							.addGroup(gl_layeredPane_1.createParallelGroup(Alignment.LEADING)
								.addGroup(gl_layeredPane_1.createSequentialGroup()
									.addGap(4)
									.addComponent(lblNumberOfTopics))
								.addGroup(gl_layeredPane_1.createSequentialGroup()
									.addGap(1)
									.addComponent(spinnerNumTopics, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
								.addComponent(btnAutoDetectNumber))
							.addGap(2)
							.addGroup(gl_layeredPane_1.createParallelGroup(Alignment.LEADING)
								.addGroup(gl_layeredPane_1.createSequentialGroup()
									.addGap(7)
									.addGroup(gl_layeredPane_1.createParallelGroup(Alignment.LEADING)
										.addGroup(gl_layeredPane_1.createSequentialGroup()
											.addGap(18)
											.addComponent(lblThreads, GroupLayout.PREFERRED_SIZE, 14, GroupLayout.PREFERRED_SIZE))
										.addComponent(separator_6, GroupLayout.PREFERRED_SIZE, 68, GroupLayout.PREFERRED_SIZE)))
								.addGroup(gl_layeredPane_1.createSequentialGroup()
									.addGap(22)
									.addComponent(spinnerNumThreards, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
								.addGroup(gl_layeredPane_1.createSequentialGroup()
									.addGap(7)
									.addGroup(gl_layeredPane_1.createParallelGroup(Alignment.LEADING)
										.addGroup(gl_layeredPane_1.createSequentialGroup()
											.addGap(14)
											.addComponent(button_2, GroupLayout.PREFERRED_SIZE, 43, GroupLayout.PREFERRED_SIZE))
										.addGroup(gl_layeredPane_1.createSequentialGroup()
											.addGap(41)
											.addComponent(spinner_1, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
										.addComponent(separator_4, GroupLayout.PREFERRED_SIZE, 7, GroupLayout.PREFERRED_SIZE)
										.addGroup(gl_layeredPane_1.createSequentialGroup()
											.addGap(15)
											.addComponent(textBeta, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
										.addGroup(gl_layeredPane_1.createSequentialGroup()
											.addGap(18)
											.addComponent(lblAlphasum, GroupLayout.PREFERRED_SIZE, 14, GroupLayout.PREFERRED_SIZE))
										.addGroup(gl_layeredPane_1.createSequentialGroup()
											.addGap(41)
											.addComponent(spinner, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
										.addComponent(separator_5, GroupLayout.PREFERRED_SIZE, 68, GroupLayout.PREFERRED_SIZE)))
								.addGroup(gl_layeredPane_1.createSequentialGroup()
									.addGap(50)
									.addComponent(lblBurnin))
								.addGroup(gl_layeredPane_1.createSequentialGroup()
									.addGap(7)
									.addComponent(separator_11, GroupLayout.PREFERRED_SIZE, 7, GroupLayout.PREFERRED_SIZE))
								.addGroup(gl_layeredPane_1.createSequentialGroup()
									.addGap(25)
									.addComponent(lblBeta, GroupLayout.PREFERRED_SIZE, 14, GroupLayout.PREFERRED_SIZE))
								.addGroup(gl_layeredPane_1.createSequentialGroup()
									.addGap(50)
									.addComponent(lblOptimizeinterval))
								.addGroup(gl_layeredPane_1.createSequentialGroup()
									.addGap(22)
									.addComponent(textAlphaSum, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
								.addComponent(lblParalleltopicmodel))
							.addGroup(gl_layeredPane_1.createParallelGroup(Alignment.LEADING)
								.addGroup(gl_layeredPane_1.createSequentialGroup()
									.addGap(7)
									.addComponent(spinnerNumOfIterations, GroupLayout.PREFERRED_SIZE, 25, GroupLayout.PREFERRED_SIZE))
								.addGroup(gl_layeredPane_1.createSequentialGroup()
									.addGap(7)
									.addComponent(lblNumIterations, GroupLayout.PREFERRED_SIZE, 25, GroupLayout.PREFERRED_SIZE))
								.addComponent(separator_3, GroupLayout.PREFERRED_SIZE, 14, GroupLayout.PREFERRED_SIZE))
							.addGap(11)
							.addGroup(gl_layeredPane_1.createParallelGroup(Alignment.LEADING)
								.addComponent(btnEstimateTopics)
								.addGroup(gl_layeredPane_1.createSequentialGroup()
									.addGap(9)
									.addComponent(lblNumberOfTop, GroupLayout.PREFERRED_SIZE, 14, GroupLayout.PREFERRED_SIZE))
								.addGroup(gl_layeredPane_1.createSequentialGroup()
									.addGap(1)
									.addComponent(spinnerNumWordsInTopic, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))))
						.addGroup(gl_layeredPane_1.createSequentialGroup()
							.addGap(3)
							.addGroup(gl_layeredPane_1.createParallelGroup(Alignment.LEADING)
								.addGroup(gl_layeredPane_1.createSequentialGroup()
									.addGap(27)
									.addComponent(scrollPane_5, GroupLayout.PREFERRED_SIZE, 116, GroupLayout.PREFERRED_SIZE))
								.addGroup(gl_layeredPane_1.createSequentialGroup()
									.addGap(4)
									.addComponent(separator_10, GroupLayout.PREFERRED_SIZE, 151, GroupLayout.PREFERRED_SIZE))
								.addGroup(gl_layeredPane_1.createSequentialGroup()
									.addGap(4)
									.addComponent(separator_9, GroupLayout.PREFERRED_SIZE, 11, GroupLayout.PREFERRED_SIZE))
								.addComponent(lblBuildPipe)
								.addGroup(gl_layeredPane_1.createSequentialGroup()
									.addGap(28)
									.addComponent(button, GroupLayout.PREFERRED_SIZE, 44, GroupLayout.PREFERRED_SIZE))
								.addGroup(gl_layeredPane_1.createSequentialGroup()
									.addGap(8)
									.addComponent(separator_8, GroupLayout.PREFERRED_SIZE, 146, GroupLayout.PREFERRED_SIZE))
								.addGroup(gl_layeredPane_1.createSequentialGroup()
									.addGap(154)
									.addComponent(separator_7, GroupLayout.PREFERRED_SIZE, 14, GroupLayout.PREFERRED_SIZE))
								.addGroup(gl_layeredPane_1.createSequentialGroup()
									.addGap(83)
									.addComponent(button_1, GroupLayout.PREFERRED_SIZE, 44, GroupLayout.PREFERRED_SIZE)))
							.addGroup(gl_layeredPane_1.createParallelGroup(Alignment.LEADING)
								.addComponent(btnGetCompositionTable)
								.addGroup(gl_layeredPane_1.createSequentialGroup()
									.addGap(1)
									.addComponent(textFieldCompositionTable, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)))))
					.addGap(19)
					.addGroup(gl_layeredPane_1.createParallelGroup(Alignment.LEADING)
						.addComponent(scrollPane_3, GroupLayout.PREFERRED_SIZE, 207, GroupLayout.PREFERRED_SIZE)
						.addComponent(scrollPane_4, GroupLayout.PREFERRED_SIZE, 207, GroupLayout.PREFERRED_SIZE))
					.addGap(11)
					.addGroup(gl_layeredPane_1.createParallelGroup(Alignment.LEADING)
						.addComponent(progressBarMallet, GroupLayout.PREFERRED_SIZE, 23, GroupLayout.PREFERRED_SIZE)
						.addComponent(btnCancelMallet, GroupLayout.PREFERRED_SIZE, 23, GroupLayout.PREFERRED_SIZE)))
		);
		layeredPane_1.setLayout(gl_layeredPane_1);
		layeredPane_1.setFocusTraversalPolicy(new FocusTraversalOnArray(new Component[] { txtTextdatafolder,
				spinnerNumTopics, spinnerNumThreards, textAlphaSum, textBeta, spinnerNumOfIterations,
				spinnerNumWordsInTopic, btnEstimateTopics, btnCancelMallet, btnGetCompositionTable, list_1 }));

		JLayeredPane layeredPane_2 = new JLayeredPane();
		tabbedPane.addTab("Formulate topics using lexical database Wordnet", null, layeredPane_2, null);

		JLabel lblGenerateTopicsUsing = new JLabel("Pre-process input text data using words stemming");
		lblGenerateTopicsUsing.setFont(new Font("Tahoma", Font.BOLD, 11));
		lblGenerateTopicsUsing.setHorizontalAlignment(SwingConstants.CENTER);
		
		JLabel lblInputDataFolder = new JLabel("Input data folder:");
		lblInputDataFolder.setFont(new Font("Tahoma", Font.PLAIN, 11));
		lblInputDataFolder.setHorizontalAlignment(SwingConstants.RIGHT);
		
		textField = new JTextField();
		textField.setText("data_text");
		textField.setColumns(10);
		
		JProgressBar progressBar_1 = new JProgressBar();
		
		JButton button_3 = new JButton("Cancel");
		button_3.setIcon(new ImageIcon(TopicModeling.class.getResource("/Resources/cancel6_16x16.png")));
		button_3.setEnabled(false);
		
		JButton btnExtractWordsSterming = new JButton("Extract words sterming");
		
		JLabel lblStemWordsIn = new JLabel("Stem words in topics");
		lblStemWordsIn.setHorizontalAlignment(SwingConstants.CENTER);
		lblStemWordsIn.setFont(new Font("Tahoma", Font.BOLD, 11));
		
		JLabel label_1 = new JLabel("Number of Top Words in a topic:");
		label_1.setHorizontalAlignment(SwingConstants.RIGHT);
		label_1.setFont(new Font("Tahoma", Font.PLAIN, 11));
		
		spinnerNumWordTipicWordnet = new JSpinner();
		spinnerNumWordTipicWordnet.setModel(new SpinnerNumberModel(5, 1, 20, 1));
		
		JScrollPane scrollPane_6 = new JScrollPane();
		
		JListOfTopicsWordnet = new DefaultListModel();
		
		listWordnet = new JList(JListOfTopicsWordnet);
		scrollPane_6.setViewportView(listWordnet);
		listWordnet.setSelectedIndex(0);
		
		btnGetStemWords = new JButton("Get stem words");
		
		JSeparator separator_13 = new JSeparator();
		
		JSeparator separator_19 = new JSeparator();
		separator_19.setOrientation(SwingConstants.VERTICAL);
		
		JSeparator separator_20 = new JSeparator();
		separator_20.setOrientation(SwingConstants.VERTICAL);
		
		JSeparator separator_21 = new JSeparator();
		
		JSeparator separator_22 = new JSeparator();
		
		JLabel lblOutputDataFolder = new JLabel("Output data folder:");
		lblOutputDataFolder.setFont(new Font("Tahoma", Font.PLAIN, 11));
		lblOutputDataFolder.setHorizontalAlignment(SwingConstants.RIGHT);
		
		txtDatatextstem = new JTextField();
		txtDatatextstem.setText("data_text_stem");
		txtDatatextstem.setColumns(10);
		
		JSeparator separator_23 = new JSeparator();
		
		JSeparator separator_24 = new JSeparator();
		separator_24.setOrientation(SwingConstants.VERTICAL);
		
		JSeparator separator_25 = new JSeparator();
		
		JSeparator separator_26 = new JSeparator();
		
		JSeparator separator_27 = new JSeparator();
		separator_27.setOrientation(SwingConstants.VERTICAL);
		
		JLabel lblFindTheCommon = new JLabel("Find the common parent in topics");
		lblFindTheCommon.setFont(new Font("Tahoma", Font.BOLD, 12));
		lblFindTheCommon.setHorizontalAlignment(SwingConstants.CENTER);
		
		JLabel lblFindStemWords = new JLabel("Find stem words in topics");
		lblFindStemWords.setFont(new Font("Tahoma", Font.BOLD, 12));
		lblFindStemWords.setHorizontalAlignment(SwingConstants.CENTER);
		
		JScrollPane scrollPane_7 = new JScrollPane();
		
		JListOfTopicsWordnetParent = new DefaultListModel();
		listTopicsWordnetParent = new JList(JListOfTopicsWordnetParent);
		scrollPane_7.setViewportView(listTopicsWordnetParent);
		listTopicsWordnetParent.setSelectedIndex(0);
		
		JLabel lblParentLevel = new JLabel("Parent Up-level:");
		lblParentLevel.setHorizontalAlignment(SwingConstants.RIGHT);
		lblParentLevel.setFont(new Font("Tahoma", Font.PLAIN, 11));
		
		spinnerParentUpLevel = new JSpinner();
		spinnerParentUpLevel.setModel(new SpinnerNumberModel(1, 0, 20, 1));
		GroupLayout gl_layeredPane_2 = new GroupLayout(layeredPane_2);
		gl_layeredPane_2.setHorizontalGroup(
			gl_layeredPane_2.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_layeredPane_2.createSequentialGroup()
					.addGap(20)
					.addGroup(gl_layeredPane_2.createParallelGroup(Alignment.LEADING)
						.addGroup(gl_layeredPane_2.createSequentialGroup()
							.addComponent(separator_22, GroupLayout.PREFERRED_SIZE, 142, GroupLayout.PREFERRED_SIZE)
							.addGap(3)
							.addComponent(lblGenerateTopicsUsing, GroupLayout.PREFERRED_SIZE, 314, GroupLayout.PREFERRED_SIZE)
							.addComponent(separator_21, GroupLayout.PREFERRED_SIZE, 208, GroupLayout.PREFERRED_SIZE))
						.addGroup(gl_layeredPane_2.createSequentialGroup()
							.addGap(23)
							.addGroup(gl_layeredPane_2.createParallelGroup(Alignment.LEADING)
								.addComponent(lblInputDataFolder, GroupLayout.PREFERRED_SIZE, 99, GroupLayout.PREFERRED_SIZE)
								.addGroup(gl_layeredPane_2.createSequentialGroup()
									.addGap(98)
									.addComponent(textField, GroupLayout.PREFERRED_SIZE, 107, GroupLayout.PREFERRED_SIZE)))
							.addGap(10)
							.addGroup(gl_layeredPane_2.createParallelGroup(Alignment.LEADING)
								.addComponent(lblOutputDataFolder, GroupLayout.PREFERRED_SIZE, 99, GroupLayout.PREFERRED_SIZE)
								.addGroup(gl_layeredPane_2.createSequentialGroup()
									.addGap(98)
									.addComponent(txtDatatextstem, GroupLayout.PREFERRED_SIZE, 107, GroupLayout.PREFERRED_SIZE)))
							.addGap(9)
							.addComponent(btnExtractWordsSterming, GroupLayout.PREFERRED_SIZE, 194, GroupLayout.PREFERRED_SIZE))
						.addComponent(separator_19, GroupLayout.PREFERRED_SIZE, 13, GroupLayout.PREFERRED_SIZE))
					.addComponent(separator_20, GroupLayout.PREFERRED_SIZE, 86, GroupLayout.PREFERRED_SIZE))
				.addGroup(gl_layeredPane_2.createSequentialGroup()
					.addGap(20)
					.addGroup(gl_layeredPane_2.createParallelGroup(Alignment.LEADING)
						.addGroup(gl_layeredPane_2.createSequentialGroup()
							.addComponent(separator_24, GroupLayout.PREFERRED_SIZE, 13, GroupLayout.PREFERRED_SIZE)
							.addGap(108)
							.addComponent(lblStemWordsIn, GroupLayout.PREFERRED_SIZE, 166, GroupLayout.PREFERRED_SIZE))
						.addComponent(separator_23, GroupLayout.PREFERRED_SIZE, 128, GroupLayout.PREFERRED_SIZE)
						.addGroup(gl_layeredPane_2.createSequentialGroup()
							.addGap(430)
							.addComponent(separator_27, GroupLayout.PREFERRED_SIZE, 37, GroupLayout.PREFERRED_SIZE))
						.addGroup(gl_layeredPane_2.createSequentialGroup()
							.addGap(184)
							.addComponent(spinnerNumWordTipicWordnet, GroupLayout.PREFERRED_SIZE, 45, GroupLayout.PREFERRED_SIZE))
						.addGroup(gl_layeredPane_2.createSequentialGroup()
							.addGap(10)
							.addComponent(label_1, GroupLayout.PREFERRED_SIZE, 174, GroupLayout.PREFERRED_SIZE))
						.addGroup(gl_layeredPane_2.createSequentialGroup()
							.addGap(239)
							.addComponent(btnGetStemWords, GroupLayout.PREFERRED_SIZE, 174, GroupLayout.PREFERRED_SIZE))
						.addGroup(gl_layeredPane_2.createSequentialGroup()
							.addGap(281)
							.addComponent(separator_26, GroupLayout.PREFERRED_SIZE, 149, GroupLayout.PREFERRED_SIZE))
						.addComponent(separator_13, GroupLayout.PREFERRED_SIZE, 667, GroupLayout.PREFERRED_SIZE)))
				.addGroup(gl_layeredPane_2.createSequentialGroup()
					.addGap(20)
					.addComponent(separator_25, GroupLayout.PREFERRED_SIZE, 430, GroupLayout.PREFERRED_SIZE))
				.addGroup(Alignment.TRAILING, gl_layeredPane_2.createSequentialGroup()
					.addGap(20)
					.addComponent(lblFindStemWords, GroupLayout.PREFERRED_SIZE, 430, GroupLayout.PREFERRED_SIZE)
					.addGap(22)
					.addGroup(gl_layeredPane_2.createParallelGroup(Alignment.LEADING)
						.addGroup(Alignment.TRAILING, gl_layeredPane_2.createSequentialGroup()
							.addGap(449)
							.addComponent(lblParentLevel, GroupLayout.PREFERRED_SIZE, 174, GroupLayout.PREFERRED_SIZE))
						.addGroup(gl_layeredPane_2.createSequentialGroup()
							.addComponent(lblFindTheCommon, GroupLayout.DEFAULT_SIZE, 552, Short.MAX_VALUE)
							.addGap(71)))
					.addComponent(spinnerParentUpLevel, GroupLayout.PREFERRED_SIZE, 45, GroupLayout.PREFERRED_SIZE)
					.addGap(28))
				.addGroup(gl_layeredPane_2.createSequentialGroup()
					.addGap(20)
					.addComponent(scrollPane_6, GroupLayout.PREFERRED_SIZE, 430, GroupLayout.PREFERRED_SIZE)
					.addGap(22)
					.addComponent(scrollPane_7, GroupLayout.DEFAULT_SIZE, 668, Short.MAX_VALUE)
					.addGap(28))
				.addGroup(gl_layeredPane_2.createSequentialGroup()
					.addComponent(progressBar_1, GroupLayout.DEFAULT_SIZE, 1069, Short.MAX_VALUE)
					.addComponent(button_3, GroupLayout.PREFERRED_SIZE, 99, GroupLayout.PREFERRED_SIZE))
		);
		gl_layeredPane_2.setVerticalGroup(
			gl_layeredPane_2.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_layeredPane_2.createSequentialGroup()
					.addGroup(gl_layeredPane_2.createParallelGroup(Alignment.LEADING)
						.addGroup(gl_layeredPane_2.createSequentialGroup()
							.addGroup(gl_layeredPane_2.createParallelGroup(Alignment.LEADING)
								.addGroup(gl_layeredPane_2.createSequentialGroup()
									.addGap(11)
									.addComponent(separator_22, GroupLayout.PREFERRED_SIZE, 14, GroupLayout.PREFERRED_SIZE))
								.addComponent(lblGenerateTopicsUsing, GroupLayout.PREFERRED_SIZE, 21, GroupLayout.PREFERRED_SIZE)
								.addGroup(gl_layeredPane_2.createSequentialGroup()
									.addGap(11)
									.addComponent(separator_21, GroupLayout.PREFERRED_SIZE, 14, GroupLayout.PREFERRED_SIZE)))
							.addGap(3)
							.addGroup(gl_layeredPane_2.createParallelGroup(Alignment.LEADING)
								.addGroup(gl_layeredPane_2.createSequentialGroup()
									.addGap(1)
									.addGroup(gl_layeredPane_2.createParallelGroup(Alignment.LEADING)
										.addGroup(gl_layeredPane_2.createSequentialGroup()
											.addGap(3)
											.addComponent(lblInputDataFolder))
										.addComponent(textField, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)))
								.addGroup(gl_layeredPane_2.createSequentialGroup()
									.addGap(1)
									.addGroup(gl_layeredPane_2.createParallelGroup(Alignment.LEADING)
										.addGroup(gl_layeredPane_2.createSequentialGroup()
											.addGap(3)
											.addComponent(lblOutputDataFolder))
										.addComponent(txtDatatextstem, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)))
								.addComponent(btnExtractWordsSterming)))
						.addGroup(gl_layeredPane_2.createSequentialGroup()
							.addGap(11)
							.addComponent(separator_19, GroupLayout.PREFERRED_SIZE, 49, GroupLayout.PREFERRED_SIZE))
						.addGroup(gl_layeredPane_2.createSequentialGroup()
							.addGap(11)
							.addComponent(separator_20, GroupLayout.PREFERRED_SIZE, 49, GroupLayout.PREFERRED_SIZE)))
					.addGroup(gl_layeredPane_2.createParallelGroup(Alignment.LEADING)
						.addGroup(gl_layeredPane_2.createSequentialGroup()
							.addGap(26)
							.addComponent(separator_24, GroupLayout.PREFERRED_SIZE, 49, GroupLayout.PREFERRED_SIZE))
						.addGroup(gl_layeredPane_2.createSequentialGroup()
							.addGap(11)
							.addComponent(lblStemWordsIn, GroupLayout.PREFERRED_SIZE, 29, GroupLayout.PREFERRED_SIZE))
						.addGroup(gl_layeredPane_2.createSequentialGroup()
							.addGap(26)
							.addComponent(separator_23, GroupLayout.PREFERRED_SIZE, 14, GroupLayout.PREFERRED_SIZE))
						.addGroup(gl_layeredPane_2.createSequentialGroup()
							.addGap(26)
							.addComponent(separator_27, GroupLayout.PREFERRED_SIZE, 49, GroupLayout.PREFERRED_SIZE))
						.addGroup(gl_layeredPane_2.createSequentialGroup()
							.addGap(38)
							.addComponent(spinnerNumWordTipicWordnet, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
						.addGroup(gl_layeredPane_2.createSequentialGroup()
							.addGap(43)
							.addComponent(label_1))
						.addGroup(gl_layeredPane_2.createSequentialGroup()
							.addGap(39)
							.addComponent(btnGetStemWords))
						.addGroup(gl_layeredPane_2.createSequentialGroup()
							.addGap(26)
							.addComponent(separator_26, GroupLayout.PREFERRED_SIZE, 14, GroupLayout.PREFERRED_SIZE))
						.addComponent(separator_13, GroupLayout.PREFERRED_SIZE, 14, GroupLayout.PREFERRED_SIZE))
					.addComponent(separator_25, GroupLayout.PREFERRED_SIZE, 14, GroupLayout.PREFERRED_SIZE)
					.addGap(3)
					.addGroup(gl_layeredPane_2.createParallelGroup(Alignment.LEADING)
						.addGroup(gl_layeredPane_2.createSequentialGroup()
							.addGap(2)
							.addComponent(lblFindStemWords, GroupLayout.PREFERRED_SIZE, 21, GroupLayout.PREFERRED_SIZE))
						.addGroup(gl_layeredPane_2.createSequentialGroup()
							.addGap(2)
							.addGroup(gl_layeredPane_2.createParallelGroup(Alignment.LEADING)
								.addGroup(gl_layeredPane_2.createSequentialGroup()
									.addGap(3)
									.addComponent(lblParentLevel))
								.addComponent(lblFindTheCommon, GroupLayout.PREFERRED_SIZE, 21, GroupLayout.PREFERRED_SIZE)))
						.addComponent(spinnerParentUpLevel, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
					.addGap(6)
					.addGroup(gl_layeredPane_2.createParallelGroup(Alignment.LEADING)
						.addComponent(scrollPane_6, GroupLayout.PREFERRED_SIZE, 247, GroupLayout.PREFERRED_SIZE)
						.addGroup(gl_layeredPane_2.createSequentialGroup()
							.addGap(2)
							.addComponent(scrollPane_7, GroupLayout.PREFERRED_SIZE, 245, GroupLayout.PREFERRED_SIZE)))
					.addGap(11)
					.addGroup(gl_layeredPane_2.createParallelGroup(Alignment.LEADING)
						.addComponent(progressBar_1, GroupLayout.PREFERRED_SIZE, 23, GroupLayout.PREFERRED_SIZE)
						.addComponent(button_3, GroupLayout.PREFERRED_SIZE, 23, GroupLayout.PREFERRED_SIZE)))
		);
		layeredPane_2.setLayout(gl_layeredPane_2);
		contentPane.setLayout(gl_contentPane);
		
		
		// Application start initiating  value ////////////////////////////////////////////////////////////////////////////////////////////////////////////////
		NoConsoleOutput = false;
		//console.setVisible(false);
		
		autoTopic = new autoDetectNumTopics(NumberFileInTextFolder() );

		updateAutoDetectTopicsForm() ;
		tabbedPane.setSelectedIndex(1);
	}


	private static int NumberFileInTextFolder() {
		
		int NumberFile=0;
		String InputFolder = txtTextdatafolder.getText();
		File[] files = new File(InputFolder).listFiles(); 		// If this pathname does not denote a directory, then listFiles() returns null.
		if (files != null) {
			NumberFile = files.length;
		} 
		
		return NumberFile;
	}
	
	private static int CheckNumberFileInTextFolder() {
		int n= NumberFileInTextFolder();
		if ( n <= 0 ) {
			JOptionPane.showMessageDialog(new JPanel(), "Input data folder: '" + txtTextdatafolder.getText() + 
					"' is empty. Please copy text data or generate it on 'Extract documents' tab", "Error", JOptionPane.ERROR_MESSAGE);
		};
		return n;
	}
	public static ISynset getSynset(String word, POS pos) {
		IIndexWord indexWord = dict.getIndexWord(word, pos);
		if (indexWord != null) {
			IWordID wordID = indexWord.getWordIDs().get(0); // use first WordID
			return dict.getWord(wordID).getSynset();
		}
		return null;
	}

	public static List<ISynsetID> getAncestors(ISynset synset) {
		List<ISynsetID> list = new ArrayList<ISynsetID>();
		list.addAll(synset.getRelatedSynsets(Pointer.HYPERNYM));
		list.addAll(synset.getRelatedSynsets(Pointer.HYPERNYM_INSTANCE));
		return list;
	}
	public static List<List<ISynset>> getPathsToRoot(ISynset synset) {
		List<List<ISynset>> pathsToRoot = null;
		List<ISynsetID> ancestors = getAncestors(synset);

		if (ancestors.isEmpty()) {
			pathsToRoot = new ArrayList<List<ISynset>>();
			List<ISynset> pathToRoot = new ArrayList<ISynset>();
			pathToRoot.add(synset);
			pathsToRoot.add(pathToRoot);

		} else if (ancestors.size() == 1) {
			pathsToRoot = getPathsToRoot(dict.getSynset(ancestors.get(0)));

			for (List<ISynset> pathToRoot : pathsToRoot) {
				pathToRoot.add(0, synset);
			}

		} else {
			pathsToRoot = new ArrayList<List<ISynset>>();
			for (ISynsetID ancestor : ancestors) {
				ISynset ancestorSynset = dict.getSynset(ancestor);
				List<List<ISynset>> pathsToRootLocal = getPathsToRoot(ancestorSynset);

				for (List<ISynset> pathToRoot : pathsToRootLocal) {
					pathToRoot.add(0, synset);
				}

				pathsToRoot.addAll(pathsToRootLocal);
			}
		}

		return pathsToRoot;
	}
	private static ISynset findClosestCommonParent(List<ISynset> pathToRoot1, List<ISynset> pathToRoot2) {
		int i = 0;
		int j = 0;

		if (pathToRoot1.size() > pathToRoot2.size()) {
			i = pathToRoot1.size() - pathToRoot2.size();
			j = 0;

		} else if (pathToRoot1.size() < pathToRoot2.size()) {
			i = 0;
			j = pathToRoot2.size() - pathToRoot1.size();
		}

		do {
			ISynset synset1 = pathToRoot1.get(i++);
			ISynset synset2 = pathToRoot2.get(j++);

			if (synset1.equals(synset2)) {
				return synset1;
			}

		} while (i < pathToRoot1.size());

		return null;
	}
	public static ISynset findClosestCommonParent(ISynset synset1, ISynset synset2) {
		if ((synset1 == null) || (synset2 == null)) {
			return null;
		}
		if (synset1.equals(synset2)) {
			return synset1;
		}

		List<List<ISynset>> pathsToRoot1 = getPathsToRoot(synset1);
		List<List<ISynset>> pathsToRoot2 = getPathsToRoot(synset2);
		ISynset resultSynset = null;
		int i = 0;

		for (List<ISynset> pathToRoot1 : pathsToRoot1) {
			for (List<ISynset> pathToRoot2 : pathsToRoot2) {

				ISynset synset = findClosestCommonParent(pathToRoot1, pathToRoot2);

				int j = pathToRoot1.size() - (pathToRoot1.indexOf(synset) + 1);
				if (j >= i) {
					i = j;
					resultSynset = synset;
				}
			}
		}

		return resultSynset;
	}

	/**
	 * maxDepth
	 * 
	 * @param synset
	 * @return The length of the longest hypernym path from this synset to the root.
	 */
	public static int maxDepth(ISynset synset) {
		if (synset == null) {
			return 0;
		}

		List<ISynsetID> ancestors = getAncestors(synset);
		if (ancestors.isEmpty()) {
			return 0;
		}

		int i = 0;
		for (ISynsetID ancestor : ancestors) {
			ISynset ancestorSynset = dict.getSynset(ancestor);
			int j = maxDepth(ancestorSynset);
			i = (i > j) ? i : j;
		}

		return i + 1;
	}
	
	public static Integer shortestPathDistance(ISynset synset1, ISynset synset2) {
		Integer distance = null;
		if (synset1.equals(synset2)) {
			return 0;
		}

		ISynset ccp = findClosestCommonParent(synset1, synset2);
		if (ccp != null) {
			distance = maxDepth(synset1) + maxDepth(synset2) - 2 * maxDepth(ccp);

			// Debug
			//String w1 = synset1.getWords().get(0).getLemma();
			//String w2 = synset2.getWords().get(0).getLemma();
			//String w3 = ccp.getWords().get(0).getLemma();
			//System.out.println("maxDepth(" + w1 + "): " + maxDepth(synset1));
			//System.out.println("maxDepth(" + w2 + "): " + maxDepth(synset2));
			//System.out.println("maxDepth(" + w3 + "): " + maxDepth(ccp));
			//System.out.println("distance(" + w1 + "," + w2 + "): " + distance);
		}
		return distance;
	}
	
	private void createEvents() {

		mntmRestartThisApplication.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					restartProgram.restart(null);
				} catch (IOException e1) {
					Outln(e1.toString());
					e1.printStackTrace();
				}
			}
		});

		/////////////////////////////////////////////// Formulate topics using lexical database Wordnet /////////////////////////////////////////////////////////////////////////

		listTopicsWordnetParent.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				int select = listTopicsWordnetParent.getSelectedIndex();
				if (select<0) return;
				
				listWordnet.setSelectedIndex(select);
				listWordnet.ensureIndexIsVisible(select);
			}
		});

		listWordnet.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				int select = listWordnet.getSelectedIndex();
				if (select<0) return;
				
				listTopicsWordnetParent.setSelectedIndex(select);
				listTopicsWordnetParent.ensureIndexIsVisible(select);
			}
		});
		
		btnGetStemWords.addActionListener(new ActionListener() {
			
			public void actionPerformed(ActionEvent e) {
				
				String forConsoleOutput="\nTopics list after steming:\n\n";
				Formatter outTopicsTextBox;
				
				// Delete the old value of this Topics list
				JListOfTopicsWordnet.clear();
				
				int NumWordTopicWordnet = (int) spinnerNumWordTipicWordnet.getValue();
				String [][] wordInTopicMatrix = new String [NumberOfTopic][NumWordTopicWordnet];
				POS[] stemmingPOS = { POS.NOUN, POS.VERB, POS.ADJECTIVE, POS.ADVERB };
				
				// Show top words in topics with proportions for the first document
				for (int topicID = 0; topicID < NumberOfTopic; topicID++) {
					Iterator<IDSorter> iterator = generateTopics.topicSortedWords.get(topicID).iterator();

					outTopicsTextBox = new Formatter(new StringBuilder(), Locale.US);
					outTopicsTextBox.format("%d: ", topicID);
					int rank = 0;
					
					
					while (iterator.hasNext() && rank < NumWordTopicWordnet) {
						IDSorter idCountPair = iterator.next();
						String word = (String) generateTopics.dataAlphabet.lookupObject(idCountPair.getID());
						wordInTopicMatrix[topicID][rank] = word;
						
						List<String> stemWord1 =  wordnetStemmer.findStems(word, stemmingPOS[0]) ;
						List<String> stemWord = new ArrayList<String>(stemWord1);
						try {
							for (int i = 1; i < stemmingPOS.length; i++) {
								List<String> stemWord2 = wordnetStemmer.findStems(word, stemmingPOS[i]);
								if (!stemWord2.isEmpty())
									for (String wordIterator : stemWord2) {
										if (!stemWord.contains(wordIterator))
											stemWord.add(wordIterator);
									}
							}
							
						} catch (Exception e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						}
						if (! stemWord.isEmpty()) { 
							int size = stemWord.size();
							if (size > 1)
								outTopicsTextBox.format("%s", "[");
							int i;
							for (i = 0; i < size-1; i++)
								outTopicsTextBox.format("%s. ", stemWord.get(i));
							outTopicsTextBox.format("%s", stemWord.get(i));
							if (size > 1)
								outTopicsTextBox.format("%s", "]");
							
						}
						else outTopicsTextBox.format("%s", "{" + word + "}" );
						if (rank < NumWordTopicWordnet-1)
							outTopicsTextBox.format("%s", ", ");
						
						rank++;
					}
					JListOfTopicsWordnet.add(topicID, outTopicsTextBox);
					forConsoleOutput += outTopicsTextBox + "\n";
				}
				listWordnet.repaint();
				forConsoleOutput += "\nTopic Distance Table Matrix:\n\n";
				
				// System.out.print("0->1:  \n " + topicDistance(wordInTopicMatrix, NumWordTopicWordnet, 0,1));	// for testing
				
				float [][] topicDistanceTable = new float [NumberOfTopic][NumberOfTopic];
				float minTopicDistance=100;
				int topic1Min=0, topic2Min=1;
				for (int topicID1 = 0; topicID1 < NumberOfTopic; topicID1++) {
					System.out.print("Distance from " + topicID1 + " : ");
					forConsoleOutput += "Distance from " + topicID1 + " : ";
					for (int topicID2 = topicID1+1; topicID2 < NumberOfTopic; topicID2++) {
						
						// Calculate the distance from topicID1 to topicID2
						topicDistanceTable[topicID1][topicID2] = topicDistance(wordInTopicMatrix, NumWordTopicWordnet, topicID1,topicID2);
						topicDistanceTable[topicID2][topicID1] = topicDistanceTable[topicID1][topicID2];
						
						if (minTopicDistance > topicDistanceTable[topicID1][topicID2]) {
							minTopicDistance = topicDistanceTable[topicID1][topicID2];
							topic1Min = topicID1;
							topic2Min = topicID2;
						}
						
						System.out.print(topicID2 + " ( " + topicDistanceTable[topicID1][topicID2] + " ) ");
						forConsoleOutput += topicID2 + " ( " + topicDistanceTable[topicID1][topicID2] + " ) ";
						
					}
					System.out.println();
					forConsoleOutput += "\n";
				}
				forConsoleOutput += "\nThe 2 most similar topics are: " + topic1Min + " and " + topic2Min + " with distance is: " + minTopicDistance;
				System.out.println("The 2 most similar topics are: " + topic1Min + " and " + topic2Min + " with distance is: " + minTopicDistance);
					
				///////////////////////////////////////// Find the common parent in topics  /////////////////////////////////////////////////////////////////////////////////////////////////////////////////
				// JListOfTopicsWordnetParent = new DefaultListModel();
				//listTopicsWordnetParent = new JList(JListOfTopicsWordnetParent);
				
				JListOfTopicsWordnetParent.clear();
				
				List<ISynset> topicISynset =new ArrayList<ISynset>();
				ISynset synset = null;
				String word;
				List<String> stemWord = new ArrayList<String>();
				for (int topicID = 0; topicID < NumberOfTopic; topicID++) {
					System.out.print("\nTopic "+topicID+" ISynset: ");
					topicISynset.clear();
					
					synset = null;
					for (int i = 0; i < NumWordTopicWordnet; i++) {
						word = wordInTopicMatrix[topicID][i];
						stemWord.clear();
						synset = null;
						stemWord = wordnetStemmer.findStems(word, POS.NOUN);
						if (stemWord.isEmpty()) {
							stemWord = wordnetStemmer.findStems(word, POS.VERB);
							if (stemWord.isEmpty()) {
								stemWord = wordnetStemmer.findStems(word, POS.ADJECTIVE);
								if (stemWord.isEmpty()) {
									stemWord = wordnetStemmer.findStems(word, POS.ADVERB);
									if (stemWord.isEmpty()) {
										
										// Cannot find the stem word
										System.out.print("{"+word+"}, ");
										
									} else
										synset = getSynset(stemWord.get(0), POS.ADVERB);
								} else
									synset = getSynset(stemWord.get(0), POS.ADJECTIVE);

							} else
								synset = getSynset(stemWord.get(0), POS.VERB);

						} else
							synset = getSynset(stemWord.get(0), POS.NOUN);

						if (synset != null) {
							topicISynset.add(synset);
							System.out.print("" + word + ", ");
						}

					}
					
					int parentUpLevel = (int) spinnerParentUpLevel.getValue();
					
					if (!topicISynset.isEmpty()) {
						// start finding the common parent in this topicISynset
						boolean keepDoing;
						ISynset parent;
						int i0,i1;
						do {
							keepDoing = false;
							outter: for (i0 = 0; i0 < topicISynset.size(); i0++)
								if (!keepDoing)
								for (i1 = i0 + 1; i1 < topicISynset.size(); i1++) {
										if (topicISynset.get(i0).getPOS().equals(topicISynset.get(i1).getPOS())) {
											parent = findClosestCommonParent(topicISynset.get(i0), topicISynset.get(i1));
											ISynset tempISynset0 = topicISynset.get(i0);
											ISynset tempISynset1 = topicISynset.get(i1);
											try {
												if ((shortestPathDistance(tempISynset0, parent) <= parentUpLevel)
														|| (shortestPathDistance(tempISynset1, parent) <= parentUpLevel)) {
													topicISynset.remove(i1);
													topicISynset.remove(i0);
													topicISynset.add(topicISynset.size(), parent);
													keepDoing = true;
													i0--;
													i1--;
													break outter;
												}
											} catch (Exception e1) {
												// TODO Auto-generated catch block
												e1.printStackTrace();
											}
										}
								}
							
						} while (keepDoing);

						System.out.print("\n" + topicID + ": ");
						outTopicsTextBox = new Formatter(new StringBuilder(), Locale.US);
						outTopicsTextBox.format("%d: ", topicID);
						for (int i = 0; i < topicISynset.size(); i++) {
							int numWord = topicISynset.get(i).getWords().size();
							if (numWord>1) { 
								System.out.print("[");
								outTopicsTextBox.format("%s", "[");
							}
							int j;
							for (j=0; j<numWord-1; j++) {
								System.out.print(topicISynset.get(i).getWords().get(j).getLemma() + ". ");
								outTopicsTextBox.format("%s", topicISynset.get(i).getWords().get(j).getLemma() + ". ");
							}
							System.out.print(topicISynset.get(i).getWords().get(j).getLemma());
							outTopicsTextBox.format("%s", topicISynset.get(i).getWords().get(j).getLemma());
							if (numWord>1) { 
								System.out.print("]");
								outTopicsTextBox.format("%s", "]");
							}
							System.out.print(", ");
							if (i < topicISynset.size() -1)
								outTopicsTextBox.format("%s", ", ");
						}
						System.out.println();
						JListOfTopicsWordnetParent.add(topicID, outTopicsTextBox);

					}
				}
				listTopicsWordnetParent.repaint();
				Outln(forConsoleOutput);
			}

			private float topicDistance(String[][] wordInTopicMatrix, int numWordTopicWordnet, int topicID1, int topicID2) {
				float distance = 100.0f;
				int pair = 0;
				int totalDistance =0;
				int d;
				String word1,word2;
				ISynset synset1, synset2;
				for (int wordID1 = 0; wordID1 < numWordTopicWordnet; wordID1++) {
					for (int wordID2 = 0; wordID2 < numWordTopicWordnet; wordID2++) {
						word1 = wordInTopicMatrix[topicID1][wordID1];
						word2 =  wordInTopicMatrix[topicID2][wordID2];
						//System.out.println("Word1: " + word1 + ". Word2: " + word2);
						
						d = nounWordDistance(word1,word2);
						
						if (d<100) {
							totalDistance += d;
							pair++;
							//System.out.println("totalDistance: " + totalDistance + ". pair: " + pair);
						}
						
					}
				}
				if (pair >0) distance = (float) totalDistance / pair;
				return distance;
			}
			private int nounWordDistance(String word1, String word2) {
				if (word1.equals(word2)) return 0;
				int distance = 100;
				int d;
				ISynset synset1, synset2;
				//System.out.println("Word1: " + word1 + ". Word2: " + word2);

				List<String> stemWord1 = wordnetStemmer.findStems(word1, POS.NOUN);
				List<String> stemWord2 = wordnetStemmer.findStems(word2, POS.NOUN);
				if (stemWord1.isEmpty() || stemWord2.isEmpty())
					return distance;
				
				for (int i = 0; i < stemWord1.size(); i++) {
					synset1 = getSynset(stemWord1.get(i), POS.NOUN);
					if ((synset1 != null))
						for (int j = 0; j < stemWord2.size(); j++) {
							synset2 = getSynset(stemWord2.get(j), POS.NOUN);
							if ((synset2 != null)) {
								d = shortestPathDistance(synset1, synset2);
								if (d<distance) distance= d;
							}
						}
				}
				return distance;
			}
		});
		
		/////////////////////////////////////////////// Topic estimate by Mallet tab ////////////////////////////////////////////////////////////////////////////////////////////

		spinnerNumOfIterations.addChangeListener(new ChangeListener() {
			public void stateChanged(ChangeEvent e) {
				updateAutoDetectTopicsForm();
			}
		});
		
		txtTextdatafolder.addFocusListener(new FocusAdapter() {
			@Override
			public void focusLost(FocusEvent e) {
				textFieldCompositionTable.setText(txtTextdatafolder.getText());
				
				updateAutoDetectTopicsForm();
			}
		});
		
		btnAutoDetectNumber.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				java.awt.EventQueue.invokeLater(new Runnable() {
					public void run() {
						
						// Check data in the "data_text" folder. If it is empty then display a error message.
						if ( CheckNumberFileInTextFolder() <=0) return;
						
						
						boolean consoleVisibleStatus = console.isVisible();
						console.setVisible(false);
						TopicModeling.NoConsoleOutput = true;
						
						autoTopic.setVisible(true);

						TopicModeling.NoConsoleOutput = false;
						console.setVisible(consoleVisibleStatus);
					}
				});
			}
		});

		mntmAutomaticallyDetectAppropriate.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				// Check data in the "data_text" folder. If it is empty then display a error message.
				if ( CheckNumberFileInTextFolder() <=0) return;
				
				tabbedPane.setSelectedIndex(1);

				java.awt.EventQueue.invokeLater(new Runnable() {
					public void run() {
						boolean consoleVisible = console.isVisible();
						console.setVisible(false);
						TopicModeling.NoConsoleOutput = true;

						autoTopic.setVisible(true);

						TopicModeling.NoConsoleOutput = false;
						console.setVisible(consoleVisible);
					}
				});

			}
		});
		
		mntmPrintTheList.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				tabbedPane.setSelectedIndex(1);

				if (JListOfTopics.size() <=1)  { 		 // JListOfTopics has never been created before. Return!
					Outln("ListOfTopics is emply ! Please run 'Estimate topics' to ganerate its list of data first ");
					return;
				}
				
				
				
				
			}
		});
		
		mntmPrintTheComposition.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				tabbedPane.setSelectedIndex(1);
				
				if (compositionTable.getRowCount() <=0 ) {  // compositionTable has never been created before. Return!
					Outln("CompositionTable is emply ! Please run 'Get composition table' to create its data first ");
					return;
				}
				try {
					PrinterJob printer = PrinterJob.getPrinterJob();

					// Set 1/2 " margins and orientation
					PageFormat pf = printer.defaultPage();
					if (generateTopics.numTopics > 10)
						pf.setOrientation(PageFormat.LANDSCAPE);
					else
						pf.setOrientation(PageFormat.PORTRAIT);

					MessageFormat header = new MessageFormat(
							"Composition table of '" + txtTextdatafolder.getText() + "'. Page {0,number,integer}");
					Paper paper = new Paper();
					double margin = 36; // half inch
					paper.setImageableArea(margin, margin, paper.getWidth() - margin * 2,
							paper.getHeight() - margin * 2);
					pf.setPaper(paper);

					Book book = new Book();

					// book.append(table.getPrintable(PrintMode.FIT_WIDTH, header, null), pf);

					// printJob.append(new ListPrinter(listTopics), pf);

					int totalPages = 0;
					// 1st table
					Printable p1 = table.getPrintable(PrintMode.FIT_WIDTH, header, null);
					PrintableWrapper pw1 = new PrintableWrapper(p1, totalPages);
					totalPages += pw1.getNumberOfPages(pf);
					book.append(pw1, pf, pw1.getNumberOfPages(pf));

					// 2nd table      ????????????????????????????????????????????????????????????????????????????????
					//    https://stackoverflow.com/questions/14775753/printing-multiple-jtables-as-one-job-book-object-only-prints-1st-table 
					/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
					Printable p2 = table.getPrintable(PrintMode.FIT_WIDTH, null, null);
					PrintableWrapper pw2 = new PrintableWrapper(p2, totalPages);
					totalPages += pw2.getNumberOfPages(pf);
					book.append(pw2, pf, pw2.getNumberOfPages(pf));

					
					////////////??????????????????????????????????????????????????????????????????????????????????????
					printer.setPageable(book);

					if (printer.printDialog()) {
						printer.print();
						Outln(book.getNumberOfPages() - 1 + " pages were sent to printer");
					} else
						Outln("User cancelled printing. No page was sent to printer");

				} catch (PrinterException e3) {
					Outln("Cannot print !" + e3.getMessage());
				}

			}
		});

		listTopics.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				
				
				
				if ( ( NumberOfTopic <= 0) )
					return; // Not run generateTopics yet

				// Sort the table when click on column title
				TableRowSorter<TableModel> sorter = new TableRowSorter<TableModel>(table.getModel());
				table.setRowSorter(sorter);
				List<RowSorter.SortKey> sortKeys = new ArrayList<>(SizeOfInstance + 3);

				table.setCellSelectionEnabled(true);
				
				table.changeSelection(0,0 , false, false); table.requestFocus(); // focus on 0,0 first before focusing on other cell. Which help to get the better view and feeling.
				
				if (listTopics.getSelectedIndex() == 0) {
					sortKeys.add(new RowSorter.SortKey(0, SortOrder.ASCENDING));
					table.changeSelection(0,0 , false, false);
				} else {
					sortKeys.add(new RowSorter.SortKey(listTopics.getSelectedIndex() + 1, SortOrder.DESCENDING));
					table.changeSelection(0,listTopics.getSelectedIndex() + 1 , false, false);
				}
				sorter.setSortKeys(sortKeys);

				// refresh the table to make sure the cell color take effect
				table.repaint();
				
				table.requestFocus();

			}
		});

		mntmGetCompositionTable.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				tabbedPane.setSelectedIndex(1);
				actionPerformed_GetCompositionTable();
			}
		});

		mntmEstimateTopicsUsing.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				// Check data in the "data_text" folder. If it is empty then display a error message.
				if ( CheckNumberFileInTextFolder() <=0) return;
				
				tabbedPane.setSelectedIndex(1);
				actionPerformed_EstimateTopics();
			}
		});

		btnGetCompositionTable.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				actionPerformed_GetCompositionTable();
			}
		});

		spinnerNumWordsInTopic.addChangeListener(new ChangeListener() {
			public void stateChanged(ChangeEvent e) {

				if ((generateTopics.NumberOfWordsInATopic != (int) spinnerNumWordsInTopic.getValue())
						&& (generateTopics.topicSortedWords != null)) {
					generateTopics.NumberOfWordsInATopic = (int) spinnerNumWordsInTopic.getValue();

					generateTopics.printListOfTopics();
				}

			}
		});

		btnCancelMallet.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				btnCancelMallet.setEnabled(false);

				// if (processPrintCompositionTable_running)
				// processPrintCompositionTable.cancel(true);
				if (processPrintCompositionTable_running)
					processPrintCompositionTable_running = false;
				if (process_GenerateTopics_running)
					process_GenerateTopics.cancel(true);

			}
		});

		btnEstimateTopics.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				actionPerformed_EstimateTopics();
			}
		});

		layeredPane_1.addFocusListener(new FocusAdapter() {
			@Override
			public void focusGained(FocusEvent e) {
				txtTextdatafolder.setText(txtDatatextfolder.getText());
			}
		});

		///////////////////////////////////////////////// Extract text data from
		///////////////////////////////////////////////// documents
		///////////////////////////////////////////////// tab/////////////////////////////////////////////////////////////////////////////
		mntmExtractTextContents.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				tabbedPane.setSelectedIndex(0);
				actionPerformed_createTextDataUsingTika();
			}
		});

		txtDatatextfolder.addFocusListener(new FocusAdapter() {
			@Override
			public void focusLost(FocusEvent e) {
				String s = txtDatatextfolder.getText();
				s = getFoulderNameOf(s);
				if (s == "")
					s = "data_text";
				txtDatatextfolder.setText(s);

			}
		});

		txtrCdata.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseExited(MouseEvent e) {
				if (!tglbtnUsedefaultdatatext.isSelected()) {
					// update text output folder name
					txtDatatextfolder.setText(getFoulderNameOf(txtrCdata.getText()) + "_text");
					txtDatatextfolder.setEditable(true);
				}
			}
		});

		txtrCdata.addKeyListener(new KeyAdapter() {
			@Override
			public void keyTyped(KeyEvent e) {
				if (!tglbtnUsedefaultdatatext.isSelected()) {
					// update text output folder name
					txtDatatextfolder.setText(getFoulderNameOf(txtrCdata.getText()) + "_text");
					txtDatatextfolder.setEditable(true);
				}
			}

		});

		tglbtnUsedefaultdatatext.addChangeListener(new ChangeListener() {
			public void stateChanged(ChangeEvent e) {
				if (tglbtnUsedefaultdatatext.isSelected()) {
					// use defaule data_text folder
					txtDatatextfolder.setText("data_text");
					txtDatatextfolder.setEditable(false);
				} else {
					txtDatatextfolder.setText(getFoulderNameOf(txtrCdata.getText()) + "_text");
					txtDatatextfolder.setEditable(true);
				}
			}
		});

		btnGetFiles.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				actionPerformed_createTextDataUsingTika();
			}
		});

		btnXCancel.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				process_createTextDataUsingTika.cancel(true);
				btnXCancel.setEnabled(false);
			}
		});

		btnExtractTextContents.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				actionPerformed_createTextDataUsingTika();
			}
		});

		mntmExit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				System.exit(1);
			}
		});

		mntmClearConsole.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				console.setText("");
			}
		});
		mntmTurnOff.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (console.isVisible()) {
					console.setVisible(false);
					mntmTurnOff.setText("Turn on");
				} else {
					console.setVisible(true);
					mntmTurnOff.setText("Turn off");
				}
			}
		});

	}
	class ListPrinter implements Printable {

		@SuppressWarnings("rawtypes")
		public ListPrinter(JList listTopics) {
			// TODO Auto-generated constructor stub
		}

		public int print(Graphics g, PageFormat pf, int page) throws PrinterException {

			// We have only one page, and 'page' is zero-based
			if (page > 0) {
				return NO_SUCH_PAGE;
			}

			// User (0,0) is typically outside the image-able area, so we must translate by
			// the X and Y values in the PageFormat to avoid clipping.
			Graphics2D g2d = (Graphics2D) g;
			g2d.translate(pf.getImageableX(), pf.getImageableY());

			// Now we perform our rendering
			g.drawString("Hello world!", 100, 100);

			// tell the caller that this page is part of the printed document
			return PAGE_EXISTS;
		}
	}

	class PrintableWrapper implements Printable {
		private Printable delegate;
		private int offset;

		public PrintableWrapper(Printable delegate, int offset) {
			this.offset = offset;
			this.delegate = delegate;
		}

		@Override
		public int print(Graphics graphics, PageFormat pageFormat, int pageIndex) throws PrinterException {
			return delegate.print(graphics, pageFormat, pageIndex - offset);
		}

		public int getNumberOfPages(PageFormat pageFormat) throws PrinterException {
			Graphics g = new BufferedImage(1, 1, BufferedImage.TYPE_INT_RGB).createGraphics();
			int numPages = 0;
			while (true) {
				int result = delegate.print(g, pageFormat, numPages);
				if (result == Printable.PAGE_EXISTS)
					++numPages;
				else
					break;
			}

			return numPages;
		}
	}

	
	@SuppressWarnings("static-access")
	protected static void updateAutoDetectTopicsForm() {
		// update number of iterations of Topword Loop
		autoDetectNumTopics.spinnerNumIterationTopword.setValue( 	Max (	(int) spinnerNumOfIterations.getValue() /2  , 100,  (int) autoDetectNumTopics.spinnerNumIteration.getValue() *2     ));
		
		// update maximum number of topics
		autoDetectNumTopics.spinnerMaxTopics
					.setValue(Max((int) autoDetectNumTopics.spinnerMinimum.getValue(), NumberFileInTextFolder()));
		
	}

	private static int Max(int value1, int value2) {
		if (value1>value2) return value1; else return value2;
	}
	private static int Max(int value1, int value2, int value3) {
		int tem = Max(value1, value2);
		if (tem>value3) return tem; else return value3;
	}

	public void actionPerformed_GetCompositionTable() {

		compositionTable.setRowCount(0);
		btnCancelMallet.setEnabled(true);
		processPrintCompositionTable = new printCompositionTable(textFieldCompositionTable.getText());
		try {
			btnGetCompositionTable.setEnabled(false);
			btnEstimateTopics.setEnabled(false);
			processPrintCompositionTable.execute();
		} catch (Exception e1) {
			Outln(e1.toString());
		}
	}

	public static void actionPerformed_EstimateTopics() {
	
		// Check data in the "data_text" folder. If it is empty then display a error message.
		if ( CheckNumberFileInTextFolder() <=0) return;
		
		btnCancelMallet.setEnabled(true);
		process_GenerateTopics = null;
		JListOfTopics.removeAllElements();
		try {
			spinnerNumOfIterations.commitEdit();
			spinnerNumTopics.commitEdit();
			spinnerNumThreards.commitEdit();
			spinnerNumWordsInTopic.commitEdit();
		} catch (ParseException e2) {
			Outln(e2.toString());
		}
		process_GenerateTopics = new generateTopics(
				txtTextdatafolder.getText(), (int) spinnerNumTopics.getValue(),
				Double.parseDouble(textAlphaSum.getText()), Double.parseDouble(textBeta.getText()),
				(int) spinnerNumThreards.getValue(), (int) spinnerNumOfIterations.getValue(),
				(int) spinnerNumWordsInTopic.getValue());
		try {
			btnEstimateTopics.setEnabled(false);
			btnGetCompositionTable.setEnabled(false);
			process_GenerateTopics.execute();
		} catch (Exception e1) {
			Outln(e1.toString());
		}

	}

	public void actionPerformed_createTextDataUsingTika() {
		btnXCancel.setEnabled(true);
		process_createTextDataUsingTika = null;
		try {
			process_createTextDataUsingTika = new createTextDataUsingTika(getFoulderNameOf(txtrCdata.getText()),
					getFoulderNameOf(txtDatatextfolder.getText()));
		} catch (IOException e2) {
			e2.printStackTrace();
			Outln(e2.toString());
		} catch (SAXException e2) {
			e2.printStackTrace();
			Outln(e2.toString());
		} catch (TikaException e2) {
			e2.printStackTrace();
			Outln(e2.toString());
		}
		try {
			btnGetFiles.setEnabled(false);
			btnExtractTextContents.setEnabled(false);
			process_createTextDataUsingTika.execute();
		} catch (Exception e1) {
			e1.printStackTrace();
			Outln(e1.toString());
		}
	}

	protected String getFoulderNameOf(String text) {
		String s = text;

		// delete newline and space at the beginning of S
		while ((s.length() > 0) && ((s.charAt(0) == '\n') || (s.charAt(0) == ' ')))
			s = s.substring(1);

		int vtnewline = s.indexOf('\n');
		if (vtnewline < 0)
			vtnewline = s.length();
		String fouldername = s.substring(0, vtnewline);

		// delete newline and space at the ending of fouldername
		while ((fouldername.length() > 0) && ((fouldername.endsWith("\n") || (fouldername.endsWith(" ")))))
			fouldername = fouldername.substring(0, fouldername.length() - 1);

		return fouldername;
	}

	public TopicModeling() throws IOException {
		initComponents();
		createEvents();
		InitWordnet(".");
	}

	public static void Outln(String string) {
		if (NoConsoleOutput) return;
		if (!console.isVisible()) return;
		Out(string + "\n");
		console.setCaretPosition(console.getDocument().getLength());
	}

	public static void Out(String s) {
		if (NoConsoleOutput) return;
		if (!console.isVisible()) return;
		console.setText(console.getText() + s);
		console.setCaretPosition(console.getDocument().getLength());
	}

	public int getNumberOfPages(Printable delegate, PageFormat pageFormat) throws PrinterException {
		Graphics g = new BufferedImage(1, 1, BufferedImage.TYPE_INT_RGB).createGraphics();
		int numPages = 0;
		while (true) {
			int result = delegate.print(g, pageFormat, numPages);
			if (result == Printable.PAGE_EXISTS) {
				++numPages;
			} else {
				break;
			}
		}

		return numPages;
	}


	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					TopicModeling frame = new TopicModeling();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
}
