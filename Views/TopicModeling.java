package Views;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.awt.FlowLayout;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import org.apache.commons.io.FileUtils;
import org.apache.tika.exception.TikaException;
import org.apache.tika.language.LanguageIdentifier;
import org.apache.tika.metadata.Metadata;
import org.apache.tika.parser.AutoDetectParser;
import org.apache.tika.parser.ParseContext;
import org.apache.tika.sax.BodyContentHandler;
import org.xml.sax.SAXException;

import java.awt.Toolkit;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JMenuBar;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JSeparator;
import javax.swing.JTabbedPane;
import javax.swing.JScrollPane;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.SwingUtilities;
import javax.swing.SwingWorker;
import javax.swing.JTextArea;
import javax.swing.ProgressMonitor;
import javax.swing.RowSorter;
import javax.swing.JLayeredPane;
import javax.swing.JLabel;
import java.awt.Color;
import java.awt.Cursor;

import javax.swing.JButton;
import javax.swing.JList;
import javax.swing.AbstractListModel;
import javax.swing.DefaultListModel;

import java.awt.event.ActionListener;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.text.MessageFormat;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;
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
import java.awt.Panel;
import org.eclipse.wb.swing.FocusTraversalOnArray;
import java.awt.Component;
import javax.swing.JTable;
import javax.swing.JTable.PrintMode;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;
import javax.swing.text.JTextComponent;
import javax.swing.ScrollPaneConstants;
import javax.swing.SortOrder;

import java.awt.ComponentOrientation;
import java.awt.event.InputMethodListener;
import java.awt.event.InputMethodEvent;

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
	public static JList list;

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
	public static JList listTopics;

	public static DefaultListModel JListOfTopics;
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

		JMenu mnImportLectures = new JMenu("Import documents");
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
		gl_contentPane.setHorizontalGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
				.addGroup(Alignment.TRAILING, gl_contentPane.createSequentialGroup()
						.addGroup(gl_contentPane.createParallelGroup(Alignment.TRAILING)
								.addComponent(scrollPane, Alignment.LEADING, GroupLayout.DEFAULT_SIZE, 1173,
										Short.MAX_VALUE)
								.addComponent(tabbedPane, GroupLayout.DEFAULT_SIZE, 1173, Short.MAX_VALUE))
						.addGap(1)));
		gl_contentPane.setVerticalGroup(gl_contentPane.createParallelGroup(Alignment.LEADING).addGroup(gl_contentPane
				.createSequentialGroup().addComponent(tabbedPane, GroupLayout.DEFAULT_SIZE, 490, Short.MAX_VALUE)
				.addPreferredGap(ComponentPlacement.UNRELATED)
				.addComponent(scrollPane, GroupLayout.PREFERRED_SIZE, 109, GroupLayout.PREFERRED_SIZE).addGap(20)));

		JLayeredPane layeredPane = new JLayeredPane();
		tabbedPane.addTab("Extract documents using Tika", null, layeredPane, null);

		JLabel lblFilesAndDocuments = new JLabel("Files and documents");
		lblFilesAndDocuments.setFont(new Font("Tahoma", Font.BOLD, 11));
		lblFilesAndDocuments.setBounds(10, -1, 121, 26);
		layeredPane.add(lblFilesAndDocuments);

		JScrollPane scrollPane_1 = new JScrollPane();
		scrollPane_1.setBounds(10, 36, 294, 321);
		layeredPane.add(scrollPane_1);

		txtrCdata = new JTextArea();

		scrollPane_1.setViewportView(txtrCdata);
		txtrCdata.setBackground(Color.WHITE);
		txtrCdata.setToolTipText("Please input the the folder where you want to get documents and files");
		txtrCdata.setText("data");

		JButton btnNew = new JButton("New");
		btnNew.setBounds(10, 371, 72, 23);
		layeredPane.add(btnNew);

		JButton btnCheck = new JButton("Check");
		btnCheck.setBounds(102, 371, 72, 23);
		layeredPane.add(btnCheck);

		JButton btnBrowse = new JButton("Browse...");
		btnBrowse.setBounds(215, 371, 89, 23);
		layeredPane.add(btnBrowse);

		JButton btnSave = new JButton("Save");
		btnSave.setBounds(10, 405, 72, 23);
		layeredPane.add(btnSave);

		JScrollPane scrollPane_2 = new JScrollPane();
		scrollPane_2.setBounds(352, 36, 304, 325);
		layeredPane.add(scrollPane_2);

		JListOfFiles = new DefaultListModel();
		list = new JList(JListOfFiles);
		scrollPane_2.setViewportView(list);

		btnGetFiles = new JButton("Get files >>");
		btnGetFiles.setBounds(205, 405, 99, 23);
		layeredPane.add(btnGetFiles);

		btnExtractTextContents = new JButton("Extract text contents");
		btnExtractTextContents.setBounds(352, 372, 304, 23);
		layeredPane.add(btnExtractTextContents);

		progressBar = new JProgressBar();

		progressBar.setBounds(0, 439, 1069, 23);
		layeredPane.add(progressBar);

		btnXCancel = new JButton("Cancel");
		btnXCancel.setEnabled(false);

		btnXCancel.setIcon(new ImageIcon(TopicModeling.class.getResource("/Resources/cancel6_16x16.png")));
		btnXCancel.setBounds(1069, 439, 99, 23);
		layeredPane.add(btnXCancel);

		txtDatatextfolder = new JTextField();

		txtDatatextfolder.setEditable(false);
		txtDatatextfolder.setText("data_text");
		txtDatatextfolder.setBounds(352, 3, 304, 20);
		layeredPane.add(txtDatatextfolder);
		txtDatatextfolder.setColumns(10);

		tglbtnUsedefaultdatatext = new JToggleButton("");

		tglbtnUsedefaultdatatext.setSelected(true);
		tglbtnUsedefaultdatatext.setBounds(314, 3, 28, 23);
		layeredPane.add(tglbtnUsedefaultdatatext);

		JLabel lblDefaultOutputText = new JLabel("Ues default output text folder:");
		lblDefaultOutputText.setFont(new Font("Tahoma", Font.PLAIN, 10));
		lblDefaultOutputText.setHorizontalAlignment(SwingConstants.RIGHT);
		lblDefaultOutputText.setBounds(142, 6, 162, 14);
		layeredPane.add(lblDefaultOutputText);

		layeredPane_1 = new JLayeredPane();

		tabbedPane.addTab("Estimate topics using Mallet", null, layeredPane_1, null);

		JLabel lblNewLabel = new JLabel("Text data folder:");
		lblNewLabel.setHorizontalAlignment(SwingConstants.RIGHT);
		lblNewLabel.setBounds(10, 11, 121, 14);
		layeredPane_1.add(lblNewLabel);

		txtTextdatafolder = new JTextField();
		
		txtTextdatafolder.setText("data_text");
		txtTextdatafolder.setBounds(141, 8, 205, 20);
		layeredPane_1.add(txtTextdatafolder);
		txtTextdatafolder.setColumns(10);

		JLabel lblNumberOfTopics = new JLabel("Number of topics:");
		lblNumberOfTopics.setHorizontalAlignment(SwingConstants.RIGHT);
		lblNumberOfTopics.setBounds(10, 39, 121, 14);
		layeredPane_1.add(lblNumberOfTopics);

		spinnerNumTopics = new JSpinner();
		spinnerNumTopics.setModel(new SpinnerNumberModel(10, 1, 5000, 1));
		spinnerNumTopics.setBounds(142, 36, 66, 20);
		layeredPane_1.add(spinnerNumTopics);

		JLabel lblParalleltopicmodel = new JLabel("Parallel Topic Model");
		lblParalleltopicmodel.setHorizontalAlignment(SwingConstants.RIGHT);
		lblParalleltopicmodel.setBounds(10, 60, 129, 14);
		layeredPane_1.add(lblParalleltopicmodel);

		JSeparator separator_3 = new JSeparator();
		separator_3.setBounds(10, 135, 336, 14);
		layeredPane_1.add(separator_3);

		JSeparator separator_4 = new JSeparator();
		separator_4.setBounds(141, 67, 205, 7);
		layeredPane_1.add(separator_4);

		JSeparator separator_5 = new JSeparator();
		separator_5.setOrientation(SwingConstants.VERTICAL);
		separator_5.setBounds(345, 67, 13, 68);
		layeredPane_1.add(separator_5);

		JSeparator separator_6 = new JSeparator();
		separator_6.setOrientation(SwingConstants.VERTICAL);
		separator_6.setBounds(10, 67, 13, 68);
		layeredPane_1.add(separator_6);

		JSeparator separator_11 = new JSeparator();
		separator_11.setBounds(10, 67, 13, 7);
		layeredPane_1.add(separator_11);

		JLabel lblThreads = new JLabel("# threads:");
		lblThreads.setFont(new Font("Tahoma", Font.PLAIN, 10));
		lblThreads.setHorizontalAlignment(SwingConstants.RIGHT);
		lblThreads.setBounds(10, 85, 76, 14);
		layeredPane_1.add(lblThreads);

		spinnerNumThreards = new JSpinner();
		spinnerNumThreards.setToolTipText("Example, if we use 2 parallel samplers, which each look at one half the corpus and combine");
		spinnerNumThreards.setModel(new SpinnerNumberModel(2, 1, 20, 1));
		spinnerNumThreards.setBounds(87, 82, 35, 20);
		layeredPane_1.add(spinnerNumThreards);

		JLabel lblAlphasum = new JLabel("alphaSum:");
		lblAlphasum.setFont(new Font("Tahoma", Font.PLAIN, 10));
		lblAlphasum.setHorizontalAlignment(SwingConstants.RIGHT);
		lblAlphasum.setBounds(127, 85, 66, 14);
		layeredPane_1.add(lblAlphasum);

		textAlphaSum = new JTextField();
		textAlphaSum.setToolTipText("parameter is passed as the sum over topics");
		textAlphaSum.setText("1.0");
		textAlphaSum.setColumns(10);
		textAlphaSum.setBounds(192, 82, 35, 20);
		layeredPane_1.add(textAlphaSum);

		JLabel lblBeta = new JLabel("beta:");
		lblBeta.setFont(new Font("Tahoma", Font.PLAIN, 10));
		lblBeta.setHorizontalAlignment(SwingConstants.RIGHT);
		lblBeta.setBounds(231, 85, 35, 14);
		layeredPane_1.add(lblBeta);

		textBeta = new JTextField();
		textBeta.setToolTipText("the parameter for a single dimension of the Dirichlet prior");
		textBeta.setText("0.01");
		textBeta.setColumns(10);
		textBeta.setBounds(266, 82, 35, 20);
		layeredPane_1.add(textBeta);

		JLabel lblNumIterations = new JLabel("Numbe of iterations:");
		lblNumIterations.setFont(new Font("Tahoma", Font.PLAIN, 12));
		lblNumIterations.setHorizontalAlignment(SwingConstants.RIGHT);
		lblNumIterations.setBounds(10, 142, 121, 25);
		layeredPane_1.add(lblNumIterations);

		spinnerNumOfIterations = new JSpinner();
		
		spinnerNumOfIterations.setToolTipText("for testing only: you could use about 50 iterations , for real applications: use 1000 to 2000 iterations");
		spinnerNumOfIterations.setModel(new SpinnerNumberModel(500, 1, 10000, 1));
		spinnerNumOfIterations.setBounds(141, 142, 66, 25);
		layeredPane_1.add(spinnerNumOfIterations);

		btnEstimateTopics = new JButton("Estimate topics");

		btnEstimateTopics.setFont(new Font("Tahoma", Font.BOLD, 12));
		btnEstimateTopics.setBounds(10, 178, 129, 23);
		layeredPane_1.add(btnEstimateTopics);

		progressBarMallet = new JProgressBar();
		progressBarMallet.setBounds(0, 439, 1069, 23);
		layeredPane_1.add(progressBarMallet);

		btnCancelMallet = new JButton("Cancel");

		btnCancelMallet.setIcon(new ImageIcon(TopicModeling.class.getResource("/Resources/cancel6_16x16.png")));
		btnCancelMallet.setEnabled(false);
		btnCancelMallet.setBounds(1069, 439, 99, 23);
		layeredPane_1.add(btnCancelMallet);

		JLabel lblNumberOfTop = new JLabel("Number of Top Words in a topic:");
		lblNumberOfTop.setFont(new Font("Tahoma", Font.PLAIN, 10));
		lblNumberOfTop.setHorizontalAlignment(SwingConstants.RIGHT);
		lblNumberOfTop.setBounds(127, 187, 174, 14);
		layeredPane_1.add(lblNumberOfTop);

		spinnerNumWordsInTopic = new JSpinner();

		spinnerNumWordsInTopic.setModel(new SpinnerNumberModel(5, 1, 20, 1));
		spinnerNumWordsInTopic.setBounds(301, 179, 45, 20);
		layeredPane_1.add(spinnerNumWordsInTopic);

		JSeparator separator_7 = new JSeparator();
		separator_7.setBounds(368, 165, 347, 14);
		layeredPane_1.add(separator_7);

		JSeparator separator_8 = new JSeparator();
		separator_8.setOrientation(SwingConstants.VERTICAL);
		separator_8.setBounds(368, 19, 13, 146);
		layeredPane_1.add(separator_8);

		JSeparator separator_9 = new JSeparator();
		separator_9.setBounds(492, 15, 223, 11);
		layeredPane_1.add(separator_9);

		JSeparator separator_10 = new JSeparator();
		separator_10.setOrientation(SwingConstants.VERTICAL);
		separator_10.setBounds(714, 15, 13, 151);
		layeredPane_1.add(separator_10);

		JLabel lblBuildPipe = new JLabel("Build the Serial Pipe");
		lblBuildPipe.setHorizontalAlignment(SwingConstants.CENTER);
		lblBuildPipe.setBounds(368, 11, 136, 14);
		layeredPane_1.add(lblBuildPipe);

		JScrollPane scrollPane_5 = new JScrollPane();
		scrollPane_5.setBounds(373, 38, 297, 116);
		layeredPane_1.add(scrollPane_5);

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
		scrollPane_4.setBounds(367, 221, 791, 207);
		layeredPane_1.add(scrollPane_4);

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
		scrollPane_3.setBounds(10, 221, 336, 207);
		layeredPane_1.add(scrollPane_3);

		JListOfTopics = new DefaultListModel();
		listTopics = new JList(JListOfTopics);

		listTopics.setSelectedIndex(0);
		scrollPane_3.setViewportView(listTopics);

		btnGetCompositionTable = new JButton("Get composition table from new folder");
		btnGetCompositionTable.setFont(new Font("Tahoma", Font.PLAIN, 11));

		btnGetCompositionTable.setEnabled(false);
		btnGetCompositionTable.setBounds(367, 179, 230, 23);
		layeredPane_1.add(btnGetCompositionTable);

		JButton button = new JButton("");
		button.setIcon(new ImageIcon(TopicModeling.class.getResource("/Resources/Up4.png")));
		button.setBounds(678, 39, 26, 44);
		layeredPane_1.add(button);

		JButton button_1 = new JButton("");
		button_1.setIcon(new ImageIcon(TopicModeling.class.getResource("/Resources/down4.png")));
		button_1.setBounds(678, 94, 26, 44);
		layeredPane_1.add(button_1);
		
		btnAutoDetectNumber = new JButton("Auto detect");
		btnAutoDetectNumber.setFont(new Font("Tahoma", Font.PLAIN, 11));
		
		btnAutoDetectNumber.setBounds(218, 35, 128, 23);
		layeredPane_1.add(btnAutoDetectNumber);
		
		textFieldCompositionTable = new JTextField();
		textFieldCompositionTable.setText("data_text");
		textFieldCompositionTable.setColumns(10);
		textFieldCompositionTable.setBounds(607, 180, 106, 20);
		layeredPane_1.add(textFieldCompositionTable);
		
		JLabel lblOptimizeinterval = new JLabel("optimize-interval");
		lblOptimizeinterval.setHorizontalAlignment(SwingConstants.RIGHT);
		lblOptimizeinterval.setBounds(37, 110, 94, 14);
		layeredPane_1.add(lblOptimizeinterval);
		
		JSpinner spinner = new JSpinner();
		spinner.setModel(new SpinnerNumberModel(10, 0, 1000, 1));
		spinner.setToolTipText("--optimize-interval [NUMBER]\u00A0This option turns on hyperparameter optimization, which allows the model to better fit the data by allowing some topics to be more prominent than others. Optimization every 10 iterations is reasonable.");
		spinner.setBounds(137, 108, 39, 20);
		layeredPane_1.add(spinner);
		
		JSpinner spinner_1 = new JSpinner();
		spinner_1.setModel(new SpinnerNumberModel(20, 0, 2000, 1));
		spinner_1.setToolTipText("--optimize-burn-in [NUMBER]\u00A0The number of iterations before hyperparameter optimization begins. Default is twice the optimize interval.");
		spinner_1.setBounds(259, 108, 45, 20);
		layeredPane_1.add(spinner_1);
		
		JLabel lblBurnin = new JLabel("burn-in");
		lblBurnin.setHorizontalAlignment(SwingConstants.RIGHT);
		lblBurnin.setBounds(179, 110, 76, 14);
		layeredPane_1.add(lblBurnin);
		
		JButton button_2 = new JButton("...");
		button_2.setToolTipText("Full range of possible parameters of train-topics ");
		button_2.setBounds(317, 81, 13, 43);
		layeredPane_1.add(button_2);
		layeredPane_1.setFocusTraversalPolicy(new FocusTraversalOnArray(new Component[] { txtTextdatafolder,
				spinnerNumTopics, spinnerNumThreards, textAlphaSum, textBeta, spinnerNumOfIterations,
				spinnerNumWordsInTopic, btnEstimateTopics, btnCancelMallet, btnGetCompositionTable, list_1 }));

		JLayeredPane layeredPane_2 = new JLayeredPane();
		tabbedPane.addTab("Formulate topics using Wordnet", null, layeredPane_2, null);

		JLabel lblGenerateTopicsUsing = new JLabel("Generate topics using Wordnet");
		lblGenerateTopicsUsing.setBounds(0, 0, 186, 29);
		layeredPane_2.add(lblGenerateTopicsUsing);
		contentPane.setLayout(gl_contentPane);
		
		
		// Application start initiating  value ////////////////////////////////////////////////////////////////////////////////////////////////////////////////
		NoConsoleOutput = false;
		//console.setVisible(false);
		String InputFolder = txtTextdatafolder.getText();
		File[] files = new File(InputFolder).listFiles(); 		// If this pathname does not denote a directory, then listFiles() returns null.
		//System.out.println(files.length);
		
		if (files != null) {
			//if (files.length > 0)
				autoTopic = new autoDetectNumTopics(files.length);
		} else
			autoTopic = new autoDetectNumTopics(200); // if the data_text folder is not exit then you will init the
														// default autotopics = 200

		updateAutoDetectTopicsForm() ;
		tabbedPane.setSelectedIndex(1);
	}

	class ListPrinter implements Printable {

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

		/////////////////////////////////////////////// Topic estimate by Mallet tab
		/////////////////////////////////////////////// //////////////////////////////////////////////////////////////////////

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
					int pages;
					
					

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

	@SuppressWarnings("static-access")
	protected static void updateAutoDetectTopicsForm() {
		// update number of iterations of Topword Loop
		autoDetectNumTopics.spinnerNumIterationTopword.setValue( 	Max (	(int) spinnerNumOfIterations.getValue() /2  , 100,  (int) autoDetectNumTopics.spinnerNumIteration.getValue() *2     ));
		
		// update maximum number of topics
		String InputFolder = txtTextdatafolder.getText();
		File[] files = new File(InputFolder).listFiles(); 		// If this pathname does not denote a directory, then listFiles() returns null.
		
		if (files != null) {
			autoDetectNumTopics.spinnerMaxTopics
					.setValue(Max((int) autoDetectNumTopics.spinnerMinimum.getValue(), files.length));
		} else

			// if the data_text folder is not exit then you will init the
			// default autotopics = 200
			autoDetectNumTopics.spinnerMaxTopics
					.setValue(Max((int) autoDetectNumTopics.spinnerMinimum.getValue(), 200));	
		
		
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

	public TopicModeling() {
		initComponents();
		createEvents();
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
