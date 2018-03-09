package Views;
/**
 * @author phong
 *
 */

import java.io.BufferedReader;
import java.io.File;
import java.io.FileFilter;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Formatter;
import java.util.Iterator;
import java.util.Locale;
import java.util.Random;
import java.util.TreeSet;
import java.util.regex.Pattern;

import cc.mallet.pipe.CharSequence2TokenSequence;
import cc.mallet.pipe.FeatureSequence2FeatureVector;
import cc.mallet.pipe.Input2CharSequence;
import cc.mallet.pipe.Pipe;
import cc.mallet.pipe.PrintInputAndTarget;
import cc.mallet.pipe.SerialPipes;
import cc.mallet.pipe.Target2Label;
import cc.mallet.pipe.TokenSequence2FeatureSequence;
import cc.mallet.pipe.TokenSequenceLowercase;
import cc.mallet.pipe.TokenSequenceRemoveStopwords;
import cc.mallet.pipe.iterator.FileIterator;
import cc.mallet.topics.ParallelTopicModel;
import cc.mallet.topics.TopicInferencer;
import cc.mallet.topics.TopicalNGrams;
import cc.mallet.types.Alphabet;
import cc.mallet.types.FeatureSequence;
import cc.mallet.types.IDSorter;
import cc.mallet.types.Instance;
import cc.mallet.types.InstanceList;
import cc.mallet.types.LabelSequence;
import cc.mallet.util.Randoms;

public class P_Mallet {
    Pipe pipe;

    public P_Mallet() {
        pipe = buildPipe();
    }

    public Pipe buildPipe() {
        ArrayList pipeList = new ArrayList();

        pipeList.add(new Input2CharSequence("UTF-8"));
        
     // Regular expression for what constitutes a token.
        //  This pattern includes Unicode letters, Unicode numbers, 
        //   and the underscore character. Alternatives:
        //    "\\S+"   (anything not whitespace)
        //    "\\w+"    ( A-Z, a-z, 0-9, _ )
        //    "[\\p{L}\\p{N}_]+|[\\p{P}]+"   (a group of only letters and numbers OR
        //                                    a group of only punctuation marks)
        //Pattern tokenPattern = Pattern.compile("[\\p{L}\\p{N}_]+");
        Pattern tokenPattern = Pattern.compile("\\w+");
        
        pipeList.add(new CharSequence2TokenSequence(tokenPattern));
        pipeList.add(new TokenSequenceLowercase());
        
      //pipeList.add(new TokenSequenceRemoveStopwords(false, false));
        pipeList.add(new TokenSequenceRemoveStopwords(new File("mallet/stoplists/en.txt"), "UTF-8", false, false, false) );
        
        pipeList.add(new TokenSequence2FeatureSequence());
        pipeList.add(new Target2Label());
        //pipeList.add(new FeatureSequence2FeatureVector());
        //pipeList.add(new PrintInputAndTarget());

        return new SerialPipes(pipeList);
    }

    public InstanceList readDirectory(File directory) {
        return readDirectories(new File[] {directory});
    }

    public InstanceList readDirectories(File[] directories) {
        /*
         * An instance contains four generic fields of predefined name: "data", "target", "name", and "source". "Data" holds the data represented `by the instance, 
         * "target" is often a label associated with the instance, "name" is a short identifying name for the instance (such as a filename), 
         * and "source" is human-readable sourceinformation, (such as the original text).
         */
    	FileIterator iterator = new FileIterator(directories, new TxtFilter(), FileIterator.LAST_DIRECTORY);
        InstanceList instances = new InstanceList(pipe);
        instances.addThruPipe(iterator);
        
        //System.out.println("addThruPipe structure: " + iterator  );
        
        return instances;
    }

    class TxtFilter implements FileFilter {
        public boolean accept(File file) {
            return file.toString().endsWith(".txt");
        }
    }
    
    public static void main (String[] args) throws IOException {

    	P_Mallet importer = new P_Mallet();
    	String dataFolder = "data_text";
        InstanceList instances = importer.readDirectory(new File(dataFolder));
        
        instances.save(new File(dataFolder+ ".mallet"));

        int numTopics = 10;
        ParallelTopicModel model = new ParallelTopicModel(numTopics, 1.0, 0.01);
        
        model.addInstances(instances);
        
        // Use two parallel samplers, which each look at one half the corpus and combine
        //  statistics after every iteration.
        model.setNumThreads(2);

        // Run the model for 50 iterations and stop (this is for testing only, 
        //  for real applications, use 1000 to 2000 iterations)
        int numIterations =50;
        model.setNumIterations(numIterations);
        model.estimate();

        // Show the words and topics in the first instance

        // The data alphabet maps word IDs to strings
        Alphabet dataAlphabet = instances.getDataAlphabet();
        
        FeatureSequence tokens = (FeatureSequence) model.getData().get(0).instance.getData();
        LabelSequence topics = model.getData().get(0).topicSequence;
        
        Formatter out = new Formatter(new StringBuilder(), Locale.US);
        for (int position = 0; position < tokens.getLength(); position++) {
            out.format("%s-%d ", dataAlphabet.lookupObject(tokens.getIndexAtPosition(position)), topics.getIndexAtPosition(position));
        }
        System.out.println("\nDataAlphabet - Topics: \n" + out + "\n" );
                
        // Estimate the topic distribution of the first instance, 
        //  given the current Gibbs state.
        double[] topicDistribution = model.getTopicProbabilities(0);

        // Get an array of sorted sets of word ID/count pairs
        ArrayList<TreeSet<IDSorter>> topicSortedWords = model.getSortedWords();
        
        // Show top 5 words in topics with proportions for the first document
        for (int topic = 0; topic < numTopics; topic++) {
            Iterator<IDSorter> iterator = topicSortedWords.get(topic).iterator();
            
            out = new Formatter(new StringBuilder(), Locale.US);
            out.format("%d\t%.4f\t", topic, topicDistribution[topic]);
            int rank = 0;
            while (iterator.hasNext() && rank < 5) {
                IDSorter idCountPair = iterator.next();
                out.format("%s (%.0f) ", dataAlphabet.lookupObject(idCountPair.getID()), idCountPair.getWeight());
                rank++;
            }
            System.out.println(out);
        }
        System.out.println("\n");
        
       // System.exit(0);
        
        // Print out the composition of the InstanceList
        System.out.printf("\t%80.75s","Composition of the InstanceList:");
        for (int i=0; i<numTopics; i++)
       	System.out.printf("\t%3d", i);
        System.out.println("\n");
        
        int count=0;
        for (Instance I : instances) {
        	System.out.print(count + "\t");
        	System.out.printf("%80.75s",
        	
        			//"\t" +I.getData().toString()  +										// Content of the file
        			// "\t" +I.getTarget().toString()  +									// Folder name 
        			 Get_File_Name(I.getName().toString().replaceAll("%20", " ") ) 	// Full file name and directory path
        			//+ "\t"
        			 //+ "\t" + I.getSource()==null ? "null" :I.getSource().toString() 
        		);
      	 
        	 BufferedReader br = new BufferedReader(new FileReader(new File(dataFolder+ "/" + Get_File_Name(I.getName().toString().replaceAll("%20", " ")) )    ));
        	 String st ="";
        	 String line;
        	 while ((line = br.readLine()) != null) st += line;
        	 br.close();
        	
        	 StringBuilder topicZeroText  = new StringBuilder();
             topicZeroText.append(st);
             InstanceList testing = new InstanceList(instances.getPipe());
             testing.addThruPipe(new Instance(topicZeroText.toString(),"", I.getName().toString(), null));
             TopicInferencer inferencer = model.getInferencer();
             double[] testProbabilities = inferencer.getSampledDistribution(testing.get(0), numIterations, 1, 5);
             for (int i=0; i<numTopics; i++)
            	 System.out.printf("\t%.3f", testProbabilities[i]);
                     	
             count++;
             System.out.println("");
        }
        System.out.println();
        
        
        // TopicalNGrams
        //TopicalNGrams topicalNGram = new TopicalNGrams(numTopics);
        /*
         * public void estimate (InstanceList documents, int numIterations, int showTopicsInterval, int outputModelInterval, String outputModelFilename, Randoms r)
         */
        //topicalNGram.estimate(instances, numIterations, 10, 10, "", new Randoms(4));
        //topicalNGram.printDocumentTopics(new File("DocumentTopics.txt"));
       
        
        // Create a new instance with high probability of topic 0
        StringBuilder topicZeroText  = new StringBuilder();
        Iterator<IDSorter> iterator = topicSortedWords.get(0).iterator();
        topicZeroText.append("them rat nhieu noi dung khong co trong data test thu xem the nao nhi ? ");
        int rank = 0;
        while (iterator.hasNext() && rank < 5) {
            IDSorter idCountPair = iterator.next();
            topicZeroText.append(dataAlphabet.lookupObject(idCountPair.getID()) + " ");
            rank++;
        }
        
        
        
        /*
        	Create a new instance named "test instance" with empty target and source fields. 
        	"data", "target", "name", and "source"
        	instances.addThruPipe(new FileIterator(directories, new TxtFilter(), FileIterator.LAST_DIRECTORY));
         */
        InstanceList testing = new InstanceList(instances.getPipe());
        testing.addThruPipe(new Instance(topicZeroText.toString(),"", "test instance", null));

        TopicInferencer inferencer = model.getInferencer();
        double[] testProbabilities = inferencer.getSampledDistribution(testing.get(0), numIterations, 1, 5);
        for (int i=0; i<numTopics; i++)
        	System.out.printf("0\t%.3f", testProbabilities[i]);
 
        System.out.println();
        
        
    }

	private static String Get_File_Name(String string) {
		int vt = string.lastIndexOf('/');
		String filename = string.substring(vt+1);
		return filename;
	}
}
