package Views;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileFilter;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Formatter;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.TreeSet;
import java.util.Vector;
import java.util.regex.Pattern;

import javax.swing.DefaultListModel;
import javax.swing.JTable;
import javax.swing.RowSorter;
import javax.swing.SortOrder;
import javax.swing.SwingWorker;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;

import cc.mallet.pipe.CharSequence2TokenSequence;
import cc.mallet.pipe.Input2CharSequence;
import cc.mallet.pipe.Pipe;
import cc.mallet.pipe.SerialPipes;
import cc.mallet.pipe.Target2Label;
import cc.mallet.pipe.TokenSequence2FeatureSequence;
import cc.mallet.pipe.TokenSequenceLowercase;
import cc.mallet.pipe.TokenSequenceRemoveStopwords;
import cc.mallet.pipe.iterator.FileIterator;
import cc.mallet.topics.ParallelTopicModel;
import cc.mallet.topics.TopicInferencer;
import cc.mallet.types.Alphabet;
import cc.mallet.types.FeatureSequence;
import cc.mallet.types.IDSorter;
import cc.mallet.types.Instance;
import cc.mallet.types.InstanceList;
import cc.mallet.types.LabelSequence;

public class generateTopics extends SwingWorker {

	Pipe pipe;
	static String dataFolder;
	static int numTopics;
	double AlphaSum;
	double Beta;
	int NumThreads;
	public static int numIterations;
	static int NumberOfWordsInATopic;
	public static long startTime;
	long currentTime;
	long traveredTime;
	public static int count;
	static ArrayList<TreeSet<IDSorter>> topicSortedWords = null;
	static Formatter out;
	static Formatter outTopicsTextBox;
	static double[] topicDistribution;
	static Alphabet dataAlphabet;
	static InstanceList instances;
	static ParallelTopicModel model;
	public static List<RowSorter.SortKey> sortKeys;
	public static TableRowSorter<TableModel> sorter;
	static String resultForConsole = "";

	// Constructor
	public generateTopics(String data_text, int numtopics, double alphaSum, double beta, int numThreads,
			int numiterations, int numberOfWordsInATopic) {


		dataFolder = data_text;
		numTopics = numtopics;
		Beta = beta;
		AlphaSum = alphaSum;
		NumThreads = numThreads;
		numIterations = numiterations;
		NumberOfWordsInATopic = numberOfWordsInATopic;
		pipe = buildPipe();
	}

	public Pipe buildPipe() {
		ArrayList pipeList = new ArrayList();

		pipeList.add(new Input2CharSequence("UTF-8"));

		// Regular expression for what constitutes a token.
		// This pattern includes Unicode letters, Unicode numbers,
		// and the underscore character. Alternatives:
		// "\\S+" (anything not whitespace)
		// "\\w+" ( A-Z, a-z, 0-9, _ )
		// "[\\p{L}\\p{N}_]+|[\\p{P}]+" (a group of only letters and numbers OR
		// a group of only punctuation marks)
		// Pattern tokenPattern = Pattern.compile("[\\p{L}\\p{N}_]+");
		Pattern tokenPattern = Pattern.compile("\\w+");

		pipeList.add(new CharSequence2TokenSequence(tokenPattern));
		pipeList.add(new TokenSequenceLowercase());

		// pipeList.add(new TokenSequenceRemoveStopwords(false, false));
		pipeList.add(new TokenSequenceRemoveStopwords(new File("enlish_stopwords.txt"), "UTF-8", false, false, false) );
		//pipeList.add(new TokenSequenceRemoveStopwords(new File(getClass().getResource("/Resources/en.txt").getFile()), "UTF-8", false, false, false));

		pipeList.add(new TokenSequence2FeatureSequence());
		pipeList.add(new Target2Label());
		// pipeList.add(new FeatureSequence2FeatureVector());
		// pipeList.add(new PrintInputAndTarget());

		return new SerialPipes(pipeList);
	}

	public InstanceList readDirectory(File directory) {
		return readDirectories(new File[] { directory });
	}

	public InstanceList readDirectories(File[] directories) {
		/*
		 * An instance contains four generic fields of predefined name: "data",
		 * "target", "name", and "source". "Data" holds the data represented `by the
		 * instance, "target" is often a label associated with the instance, "name" is a
		 * short identifying name for the instance (such as a filename), and "source" is
		 * human-readable sourceinformation, (such as the original text).
		 */
		FileIterator iterator = new FileIterator(directories, new TxtFilter(), FileIterator.LAST_DIRECTORY);
		InstanceList instances = new InstanceList(pipe);
		instances.addThruPipe(iterator);

		// System.out.println("addThruPipe structure: " + iterator );

		return instances;
	}

	class TxtFilter implements FileFilter {
		public boolean accept(File file) {
			return file.toString().endsWith(".txt");
		}
	}

	@Override
	protected Object doInBackground() throws Exception {
		
		TopicModeling.process_GenerateTopics_running = true;

		TopicModeling.Outln("Generating topics ... ");

		instances = this.readDirectory(new File(dataFolder));

		instances.save(new File(dataFolder + ".mallet"));

		model = new ParallelTopicModel(numTopics, AlphaSum, Beta);

		model.addInstances(instances);

		model.setNumThreads(NumThreads);

		model.setNumIterations(numIterations);

		TopicModeling.progressBarMallet.setMaximum(numIterations);
		TopicModeling.progressBarMallet.setValue(0);
		TopicModeling.progressBarMallet.setStringPainted(true);
		count = 0;
		startTime = System.nanoTime(); // start counting time
		try {
			model.estimate();
		} catch (Exception e) {
			TopicModeling.Outln(e.toString());
			e.printStackTrace();
			TopicModeling.Outln("Warning ***** Estimating topics from document files fail at the first try !");
			try {
				TopicModeling.Outln("Second time trying to estimate topics from document files ... ");
				model.estimate();
			} catch (Exception e2) {
				TopicModeling.btnCancelMallet.setEnabled(false);
				TopicModeling.btnEstimateTopics.setEnabled(true);
				TopicModeling.Outln("Error ***** Estimating topics from document files fail at the second time also !");
				TopicModeling.Outln(e2.toString());
				e2.printStackTrace();
				return -1;
			}
		}

		// Show the words and topics in the first instance

		// The data alphabet maps word IDs to strings
		dataAlphabet = instances.getDataAlphabet();

		FeatureSequence tokens = (FeatureSequence) model.getData().get(0).instance.getData();
		LabelSequence topics = model.getData().get(0).topicSequence;

		out = new Formatter(new StringBuilder(), Locale.US);
		outTopicsTextBox = new Formatter(new StringBuilder(), Locale.US);

		for (int position = 0; position < tokens.getLength(); position++) {
			out.format("%s-%d ", dataAlphabet.lookupObject(tokens.getIndexAtPosition(position)),
					topics.getIndexAtPosition(position));
		}
		System.out.println("\nDataAlphabet - Topics: \n" + out + "\n\n");
		resultForConsole += "\nDataAlphabet - Topics: \n" + out + "\n\n";

		// Estimate the topic distribution of the first instance,
		// given the current Gibbs state.
		topicDistribution = model.getTopicProbabilities(0);

		// Get an array of sorted sets of word ID/count pairs
		topicSortedWords = model.getSortedWords();

		printListOfTopics();

		printCompositionTableFromInstances();

		/*
		 * long currentTime,traveredTime;
		 * TopicModeling.progressBarMallet.setValue(generateTopics.count); double
		 * percent = 100* generateTopics.count/ generateTopics.MaxProgress;
		 * 
		 * currentTime = System.nanoTime(); // get current time traveredTime =
		 * (currentTime - generateTopics.startTime) ;
		 * 
		 * long remainingTime = (long) (traveredTime * ((100-percent) /percent ));
		 * remainingTime = remainingTime / 1000000000; // converted from nanoseconds to
		 * second generateTopics.count++;
		 * TopicModeling.progressBarMallet.setString("Doing " + (generateTopics.count) +
		 * " / " + generateTopics.MaxProgress + " ( " + percent +
		 * " % )  Time remaining: " + remainingTime/60 +" m " + remainingTime % 60 +
		 * " s" );
		 */

		/*
		 * // Cancel the process by user if
		 * (TopicModeling.process_GenerateTopics.isCancelled()) { TopicModeling.
		 * Out("The process of generating topics had been canceled by user  \n");
		 * TopicModeling.btnEstimateTopics.setEnabled(true);
		 * TopicModeling.process_GenerateTopics_running = false; return -1; }
		 * 
		 */

		// Completed
		TopicModeling.progressBarMallet
				.setString("Done " + (count) + " / " + numIterations + " ( Estimating topics finished )");
		TopicModeling.btnCancelMallet.setEnabled(false);
		TopicModeling.btnEstimateTopics.setEnabled(true);
		TopicModeling.Outln("Generating topics from document files has been completed successfully !");
		TopicModeling.btnGetCompositionTable.setEnabled(true);

		TopicModeling.process_GenerateTopics_running = false;
		TopicModeling.Outln(resultForConsole);
		return null;
	}

	public static void printCompositionTableFromInstances() throws InterruptedException {

		// Print out the composition of the InstanceList

		// reset the TopicModeling.compositionTable. delete all the old value from previous run.
		TopicModeling.compositionTable.setRowCount(0);
		TopicModeling.compositionTable.setColumnCount(3);

		TopicModeling.table.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);

		for (int i = 1; i < numTopics; i++)
			TopicModeling.compositionTable.addColumn("Topic " + i);
		
		TopicModeling.table.getColumnModel().getColumn(0).setPreferredWidth(30);
		TopicModeling.table.getColumnModel().getColumn(0).setMaxWidth(100);
		TopicModeling.table.getColumnModel().getColumn(1).setPreferredWidth(250);
		TopicModeling.table.getColumnModel().getColumn(1).setMinWidth(100);
		TopicModeling.table.getColumnModel().getColumn(1).setMaxWidth(1000);
		for (int i = 0; i < numTopics; i++) {
			TopicModeling.table.getColumnModel().getColumn(2 + i).setPreferredWidth(60);
			TopicModeling.table.getColumnModel().getColumn(2 + i).setMinWidth(30);
			TopicModeling.table.getColumnModel().getColumn(2 + i).setMaxWidth(100);
		}

		System.out.printf(" %80.75s", "Composition of the InstanceList:");
		resultForConsole += " Composition of the InstanceList:  ";

		for (int i = 0; i < numTopics; i++) {
			System.out.printf(" %3d", i);
			resultForConsole += Integer.toString(i) + "  ";
		}
		System.out.println("\n");
		resultForConsole += "\n";

		String s;
		int instanceID = 0;
		for (Instance I : instances) {

			Vector<String> row = new Vector<String>();
			System.out.print(instanceID + "  ");
			resultForConsole +=Integer.toString(instanceID) + ":  ";
			row.add(StringWidthAlign(instances.size(), Integer.toString(instanceID)));

			s = Get_File_Name(I.getName().toString().replaceAll("%20", " "));
			System.out.printf(" %80.75s ",
					// "\t" +I.getData().toString() + // Content of the file
					// "\t" +I.getTarget().toString() + // Folder name
					// Get_File_Name(I.getName().toString().replaceAll("%20", " ") ) // Full file
					// name and directory path
					s
			// + "\t"
			// + "\t" + I.getSource()==null ? "null" :I.getSource().toString()
			);
			resultForConsole += s + "  ";
			row.add(s);

			/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
			double[] Probabilities = model.getTopicProbabilities(instanceID);
			for (int topicID = 0; topicID < numTopics; topicID++) {
				s = String.format("%.3f", Probabilities[topicID]);
				System.out.printf(s + "  ");
				resultForConsole += s + "  ";
				row.add(s);
			}
			System.out.println("");
			resultForConsole += "\n";
			TopicModeling.compositionTable.addRow(row);
			instanceID++;
		}
		System.out.println();

		// Highline the big value in the column
		for (int i = 0; i < numTopics; i++)
			TopicModeling.table.getColumnModel().getColumn(i + 2).setCellRenderer(new HighlineCellRenderer(0.6f, 0.19f));

		// Sort the table when click on column title
		sorter = new TableRowSorter<TableModel>(TopicModeling.table.getModel());
		TopicModeling.table.setRowSorter(sorter);
		sortKeys = new ArrayList<>(numTopics + 3);
		sortKeys.add(new RowSorter.SortKey(0, SortOrder.ASCENDING));
		sorter.setSortKeys(sortKeys);

		// Completed
		resultForConsole += "\nPrinting composition table has been completed successfully !\n";

		// refresh the table to make sure the cell color take effect
		TopicModeling.table.repaint();
		TopicModeling.SizeOfInstance = instances.size();

	}

	private static String Get_File_Name(String string) {
		int vt = string.lastIndexOf('/');
		String filename = string.substring(vt + 1);
		return filename;
	}

	private static String StringWidthAlign(int maxProgress, String string) {
		String s = string;

		int sopt = 0;
		int bac10 = 1;
		do {
			bac10 *= 10;
			sopt++;
		} while (bac10 < maxProgress); // Example: maxProgress = 120 => bac10 = 1000 => sopt= 3 => length of string
										// will be fixed to 3.

		while (s.length() < sopt)
			s = "0" + s; // s="0xx"

		return s;
	}

	public static void printListOfTopics() {

		// Delete the old value of this Topics list
		TopicModeling.JListOfTopics.clear();

		TopicModeling.JListOfTopics.add(0, "#   Alphabet_ID \n");
		resultForConsole += "# Distribution Alphabet_ID(Weight) \n";

		// Show top words in topics with proportions for the first document
		for (int topic = 0; topic < numTopics; topic++) {
			Iterator<IDSorter> iterator = topicSortedWords.get(topic).iterator();

			out = new Formatter(new StringBuilder(), Locale.US);
			outTopicsTextBox = new Formatter(new StringBuilder(), Locale.US);
			out.format("%d    %.4f    ", topic, topicDistribution[topic]);
			outTopicsTextBox.format("%d   ", topic);
			int rank = 0;

			while (iterator.hasNext() && rank < NumberOfWordsInATopic) {
				IDSorter idCountPair = iterator.next();
				out.format("%s (%.0f) ", dataAlphabet.lookupObject(idCountPair.getID()), idCountPair.getWeight());
				outTopicsTextBox.format("%s  ", dataAlphabet.lookupObject(idCountPair.getID()));
				rank++;
			}
			System.out.println(out);
			resultForConsole += out.toString() + "\n";
			TopicModeling.JListOfTopics.add(topic + 1, outTopicsTextBox);
		}
		System.out.println("");
		resultForConsole += "\n\n";
		TopicModeling.NumberOfTopic = numTopics; 
		TopicModeling.listTopics.repaint();

	}

}
