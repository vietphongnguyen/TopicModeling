
/**
 * @author phong
 *
 */

import java.io.*;
import java.net.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import edu.mit.jwi.*;
import edu.mit.jwi.item.*;
import edu.mit.jwi.morph.WordnetStemmer;

public class P_Wordnet {
	static IDictionary dict;
	public static final int MAX_DEPTH_OF_HIERARCHY = 16;
	// private static final Logger LOG = LoggerFactory.getLogger(WordNet.class);
	// private static final P_Wordnet INSTANCE = new P_Wordnet("C:\\Program Files
	// (x86)\\WordNet\\2.1");

	// private IRAMDictionary m_dict;
	// private File m_wordNetDir;
	private static WordnetStemmer wordnetStemmer;

	// Constructor
	public P_Wordnet(String wnhome) throws IOException {

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

	public static void senseOf(String wordText) {

		try {
			System.out.println("\nSense of the word '" + wordText + "' is: ");

			// look up first sense of the word "dog"
			IIndexWord idxWord = dict.getIndexWord(wordText, POS.NOUN);
			IWordID wordID = idxWord.getWordIDs().get(0);
			IWord word = dict.getWord(wordID);
			System.out.println("Id = " + wordID);
			System.out.println("Lemma = " + word.getLemma());
			System.out.println("Gloss = " + word.getSynset().getGloss());

		} catch (NullPointerException e) {
			// System.out.println("\nWord '" + wordText + "' could NOT be found in wordnet
			// dictionary! ");
			throw e;
		}
	}

	public static void getSynonyms(String wordText) {

		System.out.println("\nSynonyms of '" + wordText + "' is: ");

		// look up first sense of the word " dog "
		IIndexWord idxWord = dict.getIndexWord(wordText, POS.NOUN);
		IWordID wordID = idxWord.getWordIDs().get(0); // 1st meaning
		IWord word = dict.getWord(wordID);
		ISynset synset = word.getSynset();

		// iterate over words associated with the synset
		for (IWord w : synset.getWords())
			System.out.println(w.getLemma());
	}

	public static void getHypernyms(String wordText) {

		System.out.println("\nHypernyms of '" + wordText + "' is: ");

		// get the synset
		IIndexWord idxWord = dict.getIndexWord(wordText, POS.NOUN);
		IWordID wordID = idxWord.getWordIDs().get(0); // 1st meaning
		IWord word = dict.getWord(wordID);
		ISynset synset = word.getSynset();

		// get the hypernyms
		List<ISynsetID> hypernyms = synset.getRelatedSynsets(Pointer.HYPERNYM);

		// print out each h y p e r n y m s id and synonyms
		List<IWord> words;
		for (ISynsetID sid : hypernyms) {
			words = dict.getSynset(sid).getWords();
			System.out.print(sid + " {");
			for (Iterator<IWord> i = words.iterator(); i.hasNext();) {
				System.out.print(i.next().getLemma());
				if (i.hasNext())
					System.out.print(", ");
			}
			System.out.println("}");
		}
	}

	public static void inforAbout(String wordText) {
		try {
			senseOf(wordText);
			getSynonyms(wordText);
			getHypernyms(wordText);
		} catch (NullPointerException e) {
			System.out.println("\nWord '" + wordText + "' could NOT be found in wordnet dictionary! ");
		}
	}

	public static Set<String> getAllWords(POS pos) {
		Set<String> words = new HashSet<String>();

		Iterator<IIndexWord> i = dict.getIndexWordIterator(pos);
		while (i.hasNext()) {
			IIndexWord iw = (IIndexWord) i.next();
			words.add(iw.getLemma());
		}

		return words;
	}

	public boolean isNoun(String word) {
		return dict.getIndexWord(word, POS.NOUN) != null;
	}

	public boolean isAdjective(String word) {
		return dict.getIndexWord(word, POS.ADJECTIVE) != null;
	}

	public boolean isAdverb(String word) {
		return dict.getIndexWord(word, POS.ADVERB) != null;
	}

	public boolean isVerb(String word) {
		return dict.getIndexWord(word, POS.VERB) != null;
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

	/**
	 * Shortest Path Distance
	 * 
	 * Returns the distance of the shortest path linking the two synsets (if one
	 * exists).
	 * 
	 * For each synset, all the ancestor nodes and their distances are recorded and
	 * compared. The ancestor node common to both synsets that can be reached with
	 * the minimum number of traversals is used. If no ancestor nodes are common,
	 * null is returned. If a node is compared with itself 0 is returned.
	 * 
	 * @param synset1
	 * @param synset2
	 * @return The number of edges in the shortest path connecting the two nodes, or
	 *         null if no path exists.
	 */
	public static Integer shortestPathDistance(ISynset synset1, ISynset synset2) {
		Integer distance = null;
		if (synset1.equals(synset2)) {
			return 0;
		}

		ISynset ccp = findClosestCommonParent(synset1, synset2);
		if (ccp != null) {
			distance = maxDepth(synset1) + maxDepth(synset2) - 2 * maxDepth(ccp);

			// Debug
			String w1 = synset1.getWords().get(0).getLemma();
			String w2 = synset2.getWords().get(0).getLemma();
			String w3 = ccp.getWords().get(0).getLemma();
			System.out.println("maxDepth(" + w1 + "): " + maxDepth(synset1));
			System.out.println("maxDepth(" + w2 + "): " + maxDepth(synset2));
			System.out.println("maxDepth(" + w3 + "): " + maxDepth(ccp));
			System.out.println("distance(" + w1 + "," + w2 + "): " + distance);
		}
		return distance;
	}

	/**
	 * Path Distance Similarity
	 * 
	 * Return a score denoting how similar two word senses are, based on the
	 * shortest path that connects the senses in the is-a (hypernym/hypnoym)
	 * taxonomy.
	 * 
	 * The score is in the range 0 to 1, except in those cases where a path cannot
	 * be found (will only be true for verbs as there are many distinct verb
	 * taxonomies), in which case null is returned.
	 * 
	 * A score of 1 represents identity i.e. comparing a sense with itself will
	 * return 1.
	 * 
	 * @param synset1
	 * @param synset2
	 * @return A score denoting the similarity of the two ``Synset`` objects,
	 *         normally between 0 and 1. null is returned if no connecting path
	 *         could be found. 1 is returned if a ``Synset`` is compared with
	 *         itself.
	 */
	public static Double pathSimilarity(ISynset synset1, ISynset synset2) {
		Integer distance = shortestPathDistance(synset1, synset2);
		Double pathSimilarity = null;
		if (distance != null) {
			if (distance < 0) {
				throw new IllegalArgumentException("Distance value is negative!");
			}
			pathSimilarity = 1 / ((double) distance + 1);

			// Debug
			String w1 = synset1.getWords().get(0).getLemma();
			String w2 = synset2.getWords().get(0).getLemma();
			System.out.println("maxDepth(" + w1 + "): " + maxDepth(synset1));
			System.out.println("maxDepth(" + w2 + "): " + maxDepth(synset2));
			System.out.println("distance: " + distance);
			System.out.println("pathSimilarity(" + w1 + "," + w2 + "): " + pathSimilarity);
		} else {
			// TODO simulate_root=True
		}
		return pathSimilarity;
	}

	/**
	 * Leacock Chodorow Similarity
	 * 
	 * Return a score denoting how similar two word senses are, based on the
	 * shortest path that connects the senses and the maximum depth of the taxonomy
	 * in which the senses occur. The relationship is given as -log(p/2d) where p is
	 * the shortest path length and d is the taxonomy depth.
	 * 
	 * lch(c1,c2) = - log(minPathLength(c1,c2) / 2 * depth of the hierarchy)
	 * lch(c1,c2) = - log(minPL(c1,c2) / 2 * depth) = log(2*depth / minPL(c1,c2))
	 * 
	 * minPathLength is measured in nodes, i.e. the distance of a node to itself is
	 * 0! This would cause a logarithm error (or a division by zero)). Thus we
	 * changed the behaviour in order to return a distance of 1, if the nodes are
	 * equal or neighbors.
	 * 
	 * double relatedness = Math.log( (2*depthOfHierarchy) / (pathLength + 1) );
	 * 
	 * @param synset1
	 * @param synset2
	 * @return A score denoting the similarity of the two ``Synset`` objects,
	 *         normally greater than 0. null is returned if no connecting path could
	 *         be found. If a ``Synset`` is compared with itself, the maximum score
	 *         is returned, which varies depending on the taxonomy depth.
	 */
	public static Double lchSimilarity(ISynset synset1, ISynset synset2) {
		Integer distance = shortestPathDistance(synset1, synset2);
		Double lchSimilarity = null;
		if (distance != null) {
			if (distance < 0) {
				throw new IllegalArgumentException("Distance value is negative!");
			}
			if (distance == 0) {
				distance = 1;
			}

			lchSimilarity = Math.log((2 * MAX_DEPTH_OF_HIERARCHY) / ((double) distance));

			// Debug
			String w1 = synset1.getWords().get(0).getLemma();
			String w2 = synset2.getWords().get(0).getLemma();
			System.out.println("lchSimilarity(" + w1 + "," + w2 + "): " + lchSimilarity);
		} else {
			// TODO simulate_root=True
		}
		return lchSimilarity;
	}

	/**
	 * Computes a score denoting how similar two word senses are, based on the
	 * shortest path that connects the senses in the is-a (hypernym/hypnoym)
	 * taxonomy.
	 *
	 * @param indexWord1
	 * @param indexWord2
	 * @return Returns a similarity score is in the range 0 to 1. A score of 1
	 *         represents identity i.e. comparing a sense with itself will return 1.
	 */
	public double similarity(String word1String, POS word1POS, String word2String, POS word2POS) {
		IIndexWord indexWord1 = getIndexWord(word1String, word1POS);
		Set<ISynset> synsets1 = getSynsets(indexWord1);

		IIndexWord indexWord2 = getIndexWord(word2String, word2POS);
		Set<ISynset> synsets2 = getSynsets(indexWord2);

		double maxSim = 0;
		for (ISynset synset1 : synsets1) {
			for (ISynset synset2 : synsets2) {
				double sim = pathSimilarity(synset1, synset2);
				if ((sim > 0) && (sim > maxSim)) {
					maxSim = sim;
				}
			}
		}
		return maxSim;
	}

	public static List<ISynsetID> getAncestors(ISynset synset) {
		List<ISynsetID> list = new ArrayList<ISynsetID>();
		list.addAll(synset.getRelatedSynsets(Pointer.HYPERNYM));
		list.addAll(synset.getRelatedSynsets(Pointer.HYPERNYM_INSTANCE));
		return list;
	}

	public static List<ISynsetID> getChildren(ISynset synset) {
		List<ISynsetID> list = new ArrayList<ISynsetID>();
		list.addAll(synset.getRelatedSynsets(Pointer.HYPONYM));
		list.addAll(synset.getRelatedSynsets(Pointer.HYPONYM_INSTANCE));
		return list;
	}

	public List<String> findStems(String word, POS pos) {
		return wordnetStemmer.findStems(word, pos);
	}

	public IIndexWord getIndexWord(String word, POS pos) {
		List<String> stems = findStems(word, pos);
		if (stems != null) {
			if (stems.size() > 1) {
				// LOG.info("Be careful the word '" + word + "' has several lemmatized forms.");
			}

			for (String stem : stems) {
				return dict.getIndexWord(stem, pos); // return first stem
			}
		}
		return null;
	}

	public String getLemma(ISynset synset) {
		return synset.getWord(0).getLemma();
	}

	public String getLemma(ISynsetID synsetID) {
		return getLemma(dict.getSynset(synsetID));
	}

	public static Set<IIndexWord> getAllIndexWords(String word) {
		Set<IIndexWord> indexWords = new HashSet<IIndexWord>();
		for (POS pos : POS.values()) {
			IIndexWord indexWord = dict.getIndexWord(word, pos);
			if (indexWord != null) {
				indexWords.add(indexWord);
			}
		}
		return indexWords;
	}

	public static ISynset getSynset(String word, POS pos) {
		IIndexWord indexWord = dict.getIndexWord(word, pos);
		if (indexWord != null) {
			IWordID wordID = indexWord.getWordIDs().get(0); // use first WordID
			return dict.getWord(wordID).getSynset();
		}
		return null;
	}

	public Set<ISynset> getSynsets(IIndexWord indexWord) {
		Set<ISynset> synsets = new HashSet<ISynset>();
		for (IWordID wordID : indexWord.getWordIDs()) {
			synsets.add(dict.getSynset(wordID.getSynsetID()));
		}
		return synsets;
	}

	public Set<ISynset> getSynsets(Set<IIndexWord> indexWords) {
		Set<ISynset> synsets = new HashSet<ISynset>();
		for (IIndexWord indexWord : indexWords) {
			for (IWordID wordID : indexWord.getWordIDs()) {
				synsets.add(dict.getSynset(wordID.getSynsetID()));
			}
		}
		return synsets;
	}

	  /**
	   * testWordNet implementation
	   */
	  public static void testWordNet() {
	    System.out.println("\nwn.synsets('dog')");
	    Set<IIndexWord> indexWords = getAllIndexWords("dog");
	    for (IIndexWord indexWord : indexWords) {
	      System.out.println("indexWords: ");
	      for (IWordID wordID : indexWord.getWordIDs()) {
	        IWord word = dict.getWord(wordID);
	        System.out.println(word.getSynset());
	      }
	    }

	    System.out.println("\nwn.synsets('dog', pos=wn.NOUN)");
	    IIndexWord indexWord = dict.getIndexWord("dog", POS.NOUN);
	    IWordID wordID = indexWord.getWordIDs().get(0);
	    IWord word = dict.getWord(wordID);
	    System.out.println("Id = " + wordID);
	    System.out.println("Lemma = " + word.getLemma());
	    System.out.println("Gloss = " + word.getSynset().getGloss());

	    System.out
	        .println("\ndog = wn.synset('dog.n.01') dog.hypernyms - ancestors");
	    List<ISynsetID> ancestors = getAncestors(getSynset("dog", POS.NOUN));
	    for (ISynsetID ancestor : ancestors) {
	      ISynset synset = dict.getSynset(ancestor);
	      System.out.println(synset);
	    }

	    System.out.println("\ndog = wn.synset('dog.n.01') dog.hyponyms - children");
	    List<ISynsetID> children = getChildren(getSynset("dog", POS.NOUN));
	    for (ISynsetID child : children) {
	      ISynset synset = dict.getSynset(child);
	      System.out.println(synset);
	    }

	    // ************************************************************************
	    // Test similarities
	    // ************************************************************************
	    Double pathSimilarity = null;
	    Double lchSimilarity = null;

	    System.out.println("\nwn.path_similarity(dog, dog) = 1.0");
	    pathSimilarity = pathSimilarity(getSynset("dog", POS.NOUN),
	        getSynset("dog", POS.NOUN));
	    System.out.println("pathSimilarity: " + pathSimilarity);

	    System.out.println("\nwn.lch_similarity(dog, dog) = 3.63758");
	    lchSimilarity = lchSimilarity(getSynset("dog", POS.NOUN),
	        getSynset("dog", POS.NOUN));
	    System.out.println("lchSimilarity: " + lchSimilarity);

	    System.out.println("\nwn.path_similarity(dog, cat) = 0.2");
	    pathSimilarity = pathSimilarity(getSynset("dog", POS.NOUN),
	        getSynset("cat", POS.NOUN));
	    System.out.println("pathSimilarity: " + pathSimilarity);

	    System.out.println("\nwn.lch_similarity(dog, cat) = 2.02814");
	    lchSimilarity = lchSimilarity(getSynset("dog", POS.NOUN),
	        getSynset("cat", POS.NOUN));
	    System.out.println("lchSimilarity: " + lchSimilarity);

	    System.out.println("\nwn.path_similarity(hit, slap) = 0.14285");
	    pathSimilarity = pathSimilarity(getSynset("hit", POS.VERB),
	        getSynset("slap", POS.VERB));
	    System.out.println("pathSimilarity: " + pathSimilarity);

	    System.out.println("\nwn.lch_similarity(hit, slap) = 1.31218");
	    lchSimilarity = lchSimilarity(getSynset("hit", POS.VERB),
	        getSynset("slap", POS.VERB));
	    System.out.println("lchSimilarity: " + lchSimilarity);

	    // ************************************************************************
	    // Test stemming
	    // ************************************************************************
	    System.out.println("\nStemming test...");
	    String[] stemmingWords = { "cats", "running", "ran", "cactus", "cactuses",
	        "community", "communities", "going" };
	    POS[] stemmingPOS = { POS.NOUN, POS.VERB, POS.VERB, POS.NOUN, POS.NOUN,
	        POS.NOUN, POS.NOUN, POS.VERB };
	    String[] stemmingResults = { "cat", "run", "run", "cactus", "cactus",
	        "community", "community", "go" };

	    for (int i = 0; i < stemmingWords.length; i++) {
	      List<String> stemResults = wordnetStemmer.findStems(stemmingWords[i],
	          stemmingPOS[i]);
	      for (String stemResult : stemResults) {
	        System.out.println("stems of \"" + stemmingWords[i] + "\": "
	            + stemResult);
	        // verify result
	        if (!stemResult.equals(stemmingResults[i])) {
	          System.err.println("Wrong stemming result of \"" + stemmingWords[i]
	              + "\" result: \"" + stemResult + "\" expected: \""
	              + stemmingResults[i] + "\"");
	        }
	      }
	    }

	  }
	  public static void testWordNet2() {
		  inforAbout("verb");

			// look up full list of the word "computer"
			IIndexWord idxWord = dict.getIndexWord("computer", POS.NOUN);
			IWordID wordID = idxWord.getWordIDs().get(0);
			IWord word = dict.getWord(wordID);
			System.out.println(word.toString());
			System.out.println("Id = " + wordID);
			System.out.println("Lemma = " + word.getLemma());
			System.out.println("Synset words = " + word.getSynset().getWords());
			System.out.println("Related Synsets = " + word.getSynset().getRelatedSynsets());
			System.out.println("Gloss = " + word.getSynset().getGloss());
			System.out.println("Verbframes = " + word.getVerbFrames());
			System.out.println("POS = " + word.getPOS());
			System.out.println("Related MAP = " + word.getRelatedMap());
			List<String> relatedWords = word.getRelatedWords().stream()
					.map((IWordID t) -> (String) dict.getWord(t).getLemma()).collect(Collectors.toList());
			System.out.println("Related words = " + relatedWords);
			System.out.println("Sensekey = " + word.getSenseKey());
			System.out.println("AdjMarker = " + word.getAdjectiveMarker());

			// System.out.println("All word in dictionary: ");
			// System.out.print(getAllWords(POS.NOUN));

	  }
	public static void main(String[] args) {
		// String wnhome = System.getenv("WNHOME");
		String wnhome = "C:\\Program Files (x86)\\WordNet\\2.1";
		try {
			P_Wordnet wordnet = new P_Wordnet(wnhome);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			System.out.println("Can NOT open Wordnet dictionary from the path '" + wnhome + "'");
			e.printStackTrace();
			System.exit(-1);
		}

		testWordNet();
		//testWordNet2();
		
		
	
	}

}
/*
 * 0 0.0021 loop (619) parallel (582) line (387) omp (370) openmp (357) 1 0.0301
 * data (880) mining (691) techniques (311) concepts (292) based (242) 2 0.1467
 * text (647) data (616) system (319) cluster (232) information (200) 3 0.0090
 * network (375) matrix (357) graph (273) networks (237) game (206) 4 0.0042
 * label (204) 11 (181) network (176) goto (166) bayes (153) 5 0.5366 topic
 * (580) word (365) topics (329) words (314) mallet (208) 6 0.0694 secret (314)
 * shares (210) sharing (200) share (163) threshold (159) 7 0.0087 university
 * (426) waikato (378) data (339) weka (252) class (232) 8 0.1080 model (190)
 * answer (134) system (134) question (131) storage (111) 9 0.0851 security
 * (431) model (312) analysis (282) document (215) lda (212)
 * 
 */
