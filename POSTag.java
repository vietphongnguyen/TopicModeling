import edu.mit.jwi.item.POS;

public class POSTag {
  // The WordNet Lemmatizer only knows four part of speech tags
  // Noun, Verb, Adjective, Adverb;
  // Only the Noun and Verb rules are interesting.

  public static POS parseString(String tag) {
    switch (tag.charAt(0)) {
      case 'n':
        return POS.NOUN;
      case 'v':
        return POS.VERB;
      case 'a':
        return POS.ADJECTIVE;
      case 'r':
        return POS.ADVERB;
      default:
        throw new IllegalStateException("Unknown POS tag '" + tag + "'!");
    }
  }

  public static String toString(POS posTag) {
    switch (posTag) {
      case NOUN:
        return "n";
      case VERB:
        return "v";
      case ADJECTIVE:
        return "a";
      case ADVERB:
        return "r";
      default:
        throw new IllegalStateException("Unknown POS tag '" + posTag + "'!");
    }
  }

  public static POS convertPTB(String pennTag) {
    if (pennTag.startsWith("NN")) { // includes proper nouns
      return POS.NOUN;
    }
    if (pennTag.startsWith("VB")) {
      return POS.VERB;
    }
    if (pennTag.startsWith("JJ")) {
      return POS.ADJECTIVE;
    }
    if (pennTag.startsWith("RB")) {
      return POS.ADVERB;
    }
    return null;
  }

  // http://www.ark.cs.cmu.edu/TweetNLP/annot_guidelines.pdf
  public static POS convertArk(String arkTag) {
    if (arkTag.equals("N") || arkTag.equals("O") || arkTag.equals("^")
        || arkTag.equals("S") || arkTag.equals("Z")) {
      return POS.NOUN;
    }
    if (arkTag.equals("V")) {
      return POS.VERB;
    }
    if (arkTag.equals("A")) {
      return POS.ADJECTIVE;
    }
    if (arkTag.equals("R")) {
      return POS.ADVERB;
    }
    return null;
  }

}