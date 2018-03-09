package Views;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import java.awt.Toolkit;

import javax.swing.ButtonGroup;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JLabel;
import javax.swing.JSpinner;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.SpinnerNumberModel;
import javax.swing.JSlider;
import javax.swing.SwingConstants;
import javax.swing.JRadioButton;
import javax.swing.JButton;
import javax.swing.JSeparator;
import javax.swing.event.ChangeListener;
import javax.swing.event.ChangeEvent;
import java.awt.event.ActionListener;
import java.text.ParseException;
import java.awt.event.ActionEvent;
import java.awt.Font;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.DefaultComboBoxModel;

public class autoDetectNumTopics extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private JPanel contentPane;
	public JSlider sliderMinThreshold;
	public JSlider sliderPercentBigTopics;
	public JSlider sliderPercentTotal;
	public static JSpinner spinnerMaxTopics;
	public static JSpinner spinnerMinimum;
	public static JSpinner spinnerMaxNumLoop;
	private JRadioButton rdbtnIncreaseOf;
	private JRadioButton rdbtnIncreaseOrDecrease;
	private static JLabel labelMinThreshold;
	private JLabel labelPercentBigTopics;
	private JLabel labelPercentTotal;
	private JButton btnDetectNumberOf;
	public static JSpinner spinnerNumIteration;
	public static JComboBox comboBoxNumTopword;
	public static JSpinner spinnerNumIterationTopword;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					autoDetectNumTopics frame = new autoDetectNumTopics( (int) spinnerMaxTopics.getValue() );
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
					TopicModeling.Outln(e.toString());
				}
			}
		});
	}

	// Constructor
	public autoDetectNumTopics(int maxNumTopics) {
		initComponents();
		createEvents();

		spinnerMaxTopics.setValue(maxNumTopics);
		//spinnerMaxTopics.setModel(new SpinnerNumberModel(maxNumTopics, 1, 5000, 1));

		TopicModeling.updateAutoDetectTopicsForm() ;
		
	}
	
	public static int getMinimumTopics() {
		return (int) spinnerMinimum.getValue();
	}
	
	private void createEvents() {
		
		spinnerNumIteration.addChangeListener(new ChangeListener() {
			public void stateChanged(ChangeEvent e) {
				TopicModeling.updateAutoDetectTopicsForm() ;
			}
		});
		
		sliderMinThreshold.addChangeListener(new ChangeListener() {
			public void stateChanged(ChangeEvent e) {
				labelMinThreshold.setText(Float.toString((float) sliderMinThreshold.getValue() /100 ));
				
			}
		});
		
		sliderPercentBigTopics.addChangeListener(new ChangeListener() {
			public void stateChanged(ChangeEvent e) {
				labelPercentBigTopics.setText(Float.toString(sliderPercentBigTopics.getValue()));
			}
		});
		sliderPercentTotal.addChangeListener(new ChangeListener() {
			public void stateChanged(ChangeEvent e) {
				labelPercentTotal.setText(Float.toString(sliderPercentTotal.getValue()));
			}
		});
		btnDetectNumberOf.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				setVisible(false);
				boolean consoleVisible = TopicModeling.console.isVisible();
				
				TopicModeling.console.setVisible(false);
				try {
					spinnerMaxTopics.commitEdit();
				} catch (ParseException e2) {
					spinnerMaxTopics.setValue(spinnerMaxTopics.getMaximumSize());
				}
				
				doAutoDetectNumberOfTopics();
				
				TopicModeling.console.setVisible(consoleVisible);
			}
		});
		
	}

	protected void doAutoDetectNumberOfTopics() {
		
		int numTopicsID = (int) spinnerMaxTopics.getValue();
		
		// 
		TopicModeling.btnCancelMallet.setEnabled(true);
		TopicModeling.process_GenerateTopics = null;
		TopicModeling.JListOfTopics.removeAllElements();
		try {
			spinnerNumIteration.commitEdit();
			TopicModeling.spinnerNumThreards.commitEdit();
			TopicModeling.spinnerNumWordsInTopic.commitEdit();
		} catch (ParseException e2) {
			TopicModeling.Outln(e2.toString());
		}
		
		TopicModeling.threadProcessAutoDetectNumberOfTopics = new processAutoDetectNumberOfTopics( 
				TopicModeling.txtTextdatafolder.getText(), numTopicsID,
				Double.parseDouble(TopicModeling.textAlphaSum.getText()), Double.parseDouble(TopicModeling.textBeta.getText()),
				(int) TopicModeling.spinnerNumThreards.getValue(), (int) spinnerNumIteration.getValue(),
				(int) TopicModeling.spinnerNumWordsInTopic.getValue());
		try {
			TopicModeling.btnEstimateTopics.setEnabled(false);
			TopicModeling.btnGetCompositionTable.setEnabled(false);
			TopicModeling.threadProcessAutoDetectNumberOfTopics.execute();
		} catch (Exception e1) {
			TopicModeling.Outln(e1.toString());
		}

		
		
	}

	/**
	 * Create the frame.
	 */
	private void initComponents() {
		setTitle("Auto detect appropriate number of topics");
		setResizable(false);
		setIconImage(Toolkit.getDefaultToolkit().getImage(autoDetectNumTopics.class.getResource("/Resources/icon2.png")));
		setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE );
		setBounds(100, 100, 533, 370);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		
		JLabel lblMaxTopics = new JLabel("Max # topics");
		lblMaxTopics.setFont(new Font("Tahoma", Font.PLAIN, 11));
		lblMaxTopics.setHorizontalAlignment(SwingConstants.RIGHT);
		lblMaxTopics.setBounds(30, 45, 78, 14);
		
		spinnerMaxTopics = new JSpinner();
		spinnerMaxTopics.setBounds(112, 42, 68, 20);
		spinnerMaxTopics.setModel(new SpinnerNumberModel(50, 1, 5000, 1));
		
		JLabel lblRemoveNonsenceTopics = new JLabel("Remove nonsense topics");
		lblRemoveNonsenceTopics.setFont(new Font("Tahoma", Font.BOLD, 12));
		lblRemoveNonsenceTopics.setHorizontalAlignment(SwingConstants.CENTER);
		lblRemoveNonsenceTopics.setBounds(10, 16, 241, 14);
		
		JLabel lblNormalDistributions = new JLabel("Normal distribution");
		lblNormalDistributions.setFont(new Font("Tahoma", Font.BOLD, 12));
		lblNormalDistributions.setBounds(279, 16, 219, 14);
		lblNormalDistributions.setHorizontalAlignment(SwingConstants.CENTER);
		
		JLabel lblMinTopics = new JLabel("Min # topics");
		lblMinTopics.setFont(new Font("Tahoma", Font.PLAIN, 11));
		lblMinTopics.setHorizontalAlignment(SwingConstants.RIGHT);
		lblMinTopics.setBounds(30, 71, 78, 14);
		
		spinnerMinimum = new JSpinner();
		spinnerMinimum.setBounds(112, 68, 68, 20);
		spinnerMinimum.setModel(new SpinnerNumberModel(2, 1, 1000, 1));
		
		JLabel lblMinThreadholdOf = new JLabel("Min threshold of assignment probability");
		lblMinThreadholdOf.setHorizontalAlignment(SwingConstants.CENTER);
		lblMinThreadholdOf.setFont(new Font("Tahoma", Font.PLAIN, 11));
		lblMinThreadholdOf.setBounds(10, 99, 241, 14);
		
		sliderMinThreshold = new JSlider();
		sliderMinThreshold.setValue(60);
		
		sliderMinThreshold.setMinorTickSpacing(10);
		sliderMinThreshold.setPaintTicks(true);
		sliderMinThreshold.setBounds(28, 124, 182, 26);
		sliderMinThreshold.setMinimum(10);
		sliderMinThreshold.setMaximum(80);
		
		JLabel lblOfBiggest = new JLabel("% of biggest topics");
		lblOfBiggest.setFont(new Font("Tahoma", Font.PLAIN, 11));
		lblOfBiggest.setBounds(335, 71, 116, 14);
		lblOfBiggest.setHorizontalAlignment(SwingConstants.LEFT);
		
		sliderPercentBigTopics = new JSlider();
		
		sliderPercentBigTopics.setMinorTickSpacing(5);
		sliderPercentBigTopics.setPaintTicks(true);
		sliderPercentBigTopics.setBounds(291, 36, 200, 26);
		sliderPercentBigTopics.setMinimum(5);
		sliderPercentBigTopics.setMaximum(40);
		sliderPercentBigTopics.setValue(20);
		
		JLabel lblHaveToBe = new JLabel("have to be less than");
		lblHaveToBe.setFont(new Font("Tahoma", Font.PLAIN, 11));
		lblHaveToBe.setBounds(279, 99, 219, 14);
		lblHaveToBe.setHorizontalAlignment(SwingConstants.CENTER);
		
		sliderPercentTotal = new JSlider();
		
		sliderPercentTotal.setMajorTickSpacing(10);
		sliderPercentTotal.setPaintTicks(true);
		sliderPercentTotal.setBounds(291, 119, 200, 26);
		sliderPercentTotal.setMinimum(20);
		sliderPercentTotal.setMaximum(95);
		sliderPercentTotal.setValue(80);
		
		JLabel lblTotalTopics = new JLabel("% total topics");
		lblTotalTopics.setFont(new Font("Tahoma", Font.PLAIN, 11));
		lblTotalTopics.setBounds(333, 151, 102, 14);
		lblTotalTopics.setHorizontalAlignment(SwingConstants.LEFT);
		
		rdbtnIncreaseOf = new JRadioButton("Increase # of topics only");
		rdbtnIncreaseOf.setFont(new Font("Tahoma", Font.PLAIN, 11));
		rdbtnIncreaseOf.setBounds(290, 170, 172, 23);
		rdbtnIncreaseOf.setHorizontalAlignment(SwingConstants.CENTER);
		
		rdbtnIncreaseOrDecrease = new JRadioButton("Increase or decrease");
		rdbtnIncreaseOrDecrease.setFont(new Font("Tahoma", Font.PLAIN, 11));
		rdbtnIncreaseOrDecrease.setSelected(true);
		rdbtnIncreaseOrDecrease.setHorizontalAlignment(SwingConstants.CENTER);
		rdbtnIncreaseOrDecrease.setBounds(290, 197, 172, 23);
		
		ButtonGroup group = new ButtonGroup();
		group.add(rdbtnIncreaseOf);
		group.add(rdbtnIncreaseOrDecrease);
		
		JLabel lblMaxOf = new JLabel("Max # of loop topic adjustment");
		lblMaxOf.setFont(new Font("Tahoma", Font.PLAIN, 11));
		lblMaxOf.setHorizontalAlignment(SwingConstants.RIGHT);
		lblMaxOf.setBounds(259, 258, 192, 14);
		
		spinnerMaxNumLoop = new JSpinner();
		spinnerMaxNumLoop.setModel(new SpinnerNumberModel(20, 1, 100, 1));
		spinnerMaxNumLoop.setBounds(454, 255, 44, 20);
		
		btnDetectNumberOf = new JButton("Detect number of topics");
		btnDetectNumberOf.setFont(new Font("Tahoma", Font.BOLD, 12));
		
		btnDetectNumberOf.setBounds(279, 286, 217, 23);
		
		JSeparator separator = new JSeparator();
		separator.setBounds(10, 25, 2, 174);
		separator.setOrientation(SwingConstants.VERTICAL);
		
		JSeparator separator_1 = new JSeparator();
		separator_1.setBounds(252, 25, 14, 174);
		separator_1.setOrientation(SwingConstants.VERTICAL);
		
		JSeparator separator_2 = new JSeparator();
		separator_2.setBounds(276, 24, 18, 206);
		separator_2.setOrientation(SwingConstants.VERTICAL);
		
		JSeparator separator_3 = new JSeparator();
		separator_3.setBounds(501, 24, 14, 206);
		separator_3.setOrientation(SwingConstants.VERTICAL);
		contentPane.setLayout(null);
		contentPane.add(separator);
		contentPane.add(lblRemoveNonsenceTopics);
		contentPane.add(lblMaxTopics);
		contentPane.add(spinnerMaxTopics);
		contentPane.add(lblMinTopics);
		contentPane.add(spinnerMinimum);
		contentPane.add(lblMinThreadholdOf);
		contentPane.add(sliderMinThreshold);
		contentPane.add(lblMaxOf);
		contentPane.add(spinnerMaxNumLoop);
		contentPane.add(btnDetectNumberOf);
		contentPane.add(separator_1);
		contentPane.add(separator_2);
		contentPane.add(lblNormalDistributions);
		contentPane.add(sliderPercentBigTopics);
		contentPane.add(lblOfBiggest);
		contentPane.add(lblHaveToBe);
		contentPane.add(sliderPercentTotal);
		contentPane.add(lblTotalTopics);
		contentPane.add(rdbtnIncreaseOf);
		contentPane.add(rdbtnIncreaseOrDecrease);
		contentPane.add(separator_3);
		
		JSeparator separator_4 = new JSeparator();
		separator_4.setBounds(10, 25, 34, 14);
		contentPane.add(separator_4);
		
		JSeparator separator_5 = new JSeparator();
		separator_5.setBounds(217, 25, 34, 14);
		contentPane.add(separator_5);
		
		JSeparator separator_6 = new JSeparator();
		separator_6.setBounds(10, 197, 243, 14);
		contentPane.add(separator_6);
		
		JSeparator separator_7 = new JSeparator();
		separator_7.setBounds(275, 228, 225, 20);
		contentPane.add(separator_7);
		
		JSeparator separator_8 = new JSeparator();
		separator_8.setBounds(276, 25, 44, 14);
		contentPane.add(separator_8);
		
		JSeparator separator_9 = new JSeparator();
		separator_9.setBounds(454, 25, 48, 14);
		contentPane.add(separator_9);
		
		labelMinThreshold = new JLabel("0.6");
		labelMinThreshold.setBounds(218, 124, 24, 14);
		contentPane.add(labelMinThreshold);
		
		labelPercentBigTopics = new JLabel("20");
		labelPercentBigTopics.setHorizontalAlignment(SwingConstants.RIGHT);
		labelPercentBigTopics.setBounds(291, 71, 37, 14);
		contentPane.add(labelPercentBigTopics);
		
		labelPercentTotal = new JLabel("80");
		labelPercentTotal.setHorizontalAlignment(SwingConstants.RIGHT);
		labelPercentTotal.setBounds(282, 151, 46, 14);
		contentPane.add(labelPercentTotal);
		
		JLabel lblOfIterations = new JLabel("# of iterations");
		lblOfIterations.setFont(new Font("Tahoma", Font.PLAIN, 11));
		lblOfIterations.setHorizontalAlignment(SwingConstants.RIGHT);
		lblOfIterations.setBounds(30, 164, 78, 14);
		contentPane.add(lblOfIterations);
		
		spinnerNumIteration = new JSpinner();
		
		spinnerNumIteration.setModel(new SpinnerNumberModel(50, 20, 1000, 1));
		spinnerNumIteration.setBounds(112, 161, 68, 20);
		contentPane.add(spinnerNumIteration);
		
		JLabel lblRemoveTopicsWith = new JLabel("Remove duplicate TopWord");
		lblRemoveTopicsWith.setFont(new Font("Tahoma", Font.BOLD, 12));
		lblRemoveTopicsWith.setToolTipText("Remove topics with duplicated TopWord");
		lblRemoveTopicsWith.setHorizontalAlignment(SwingConstants.CENTER);
		lblRemoveTopicsWith.setBounds(10, 222, 241, 14);
		contentPane.add(lblRemoveTopicsWith);
		
		comboBoxNumTopword = new JComboBox();
		comboBoxNumTopword.setToolTipText("How many words in TopWord list to be considered to remove topics with duplicated Topword: 0 -> NoRemove, 1 -> First word only (recommended), 2 -> First 2 words (Not recommended)");
		comboBoxNumTopword.setModel(new DefaultComboBoxModel(new String[] {"0", "1", "2"}));
		comboBoxNumTopword.setSelectedIndex(1);
		comboBoxNumTopword.setBounds(187, 239, 44, 20);
		contentPane.add(comboBoxNumTopword);
		
		JLabel lblHowManyWords = new JLabel("How many words in the list");
		lblHowManyWords.setFont(new Font("Tahoma", Font.PLAIN, 11));
		lblHowManyWords.setHorizontalAlignment(SwingConstants.RIGHT);
		lblHowManyWords.setBounds(10, 245, 168, 14);
		contentPane.add(lblHowManyWords);
		
		JLabel label = new JLabel("# of iterations");
		label.setFont(new Font("Tahoma", Font.PLAIN, 11));
		label.setHorizontalAlignment(SwingConstants.RIGHT);
		label.setBounds(81, 273, 78, 14);
		contentPane.add(label);
		
		spinnerNumIterationTopword = new JSpinner();
		spinnerNumIterationTopword.setModel(new SpinnerNumberModel(100, 50, 2000, 1));
		spinnerNumIterationTopword.setBounds(163, 270, 68, 20);
		contentPane.add(spinnerNumIterationTopword);
		
		JSeparator separator_10 = new JSeparator();
		separator_10.setBounds(10, 228, 24, 14);
		contentPane.add(separator_10);
		
		JSeparator separator_11 = new JSeparator();
		separator_11.setOrientation(SwingConstants.VERTICAL);
		separator_11.setBounds(10, 228, 14, 81);
		contentPane.add(separator_11);
		
		JSeparator separator_12 = new JSeparator();
		separator_12.setBounds(227, 228, 24, 14);
		contentPane.add(separator_12);
		
		JSeparator separator_13 = new JSeparator();
		separator_13.setOrientation(SwingConstants.VERTICAL);
		separator_13.setBounds(252, 228, 14, 81);
		contentPane.add(separator_13);
		
		JSeparator separator_14 = new JSeparator();
		separator_14.setBounds(10, 308, 243, 20);
		contentPane.add(separator_14);
	}

	public static float getThreshold() {
		
		return Float.parseFloat(labelMinThreshold.getText() );
	}
}
