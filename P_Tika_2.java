/**
 * @author phong
 *
 */
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Writer;

import org.apache.tika.exception.TikaException;
import org.apache.tika.language.LanguageIdentifier;
import org.apache.tika.metadata.Metadata;
import org.apache.tika.parser.AutoDetectParser;
import org.apache.tika.parser.ParseContext;
import org.apache.tika.sax.BodyContentHandler;
import org.xml.sax.SAXException;

public class P_Tika_2 {
	public static String identifyLanguage(String text) {
	    LanguageIdentifier identifier = new LanguageIdentifier(text);
	    return identifier.getLanguage();
	}
	public static void createTextDataUsingTika() throws IOException, SAXException, TikaException {
		createTextDataUsingTika("data/");
	}
	public static void createTextDataUsingTika(String folderName) throws IOException, SAXException, TikaException {
		
		folderName = Check_Folder_Name(folderName);
		File folder = new File(folderName);
		if (new File("text_" + folderName).mkdir() ) {
			// Make folder successful 
			
		}
		File[] listOfFiles = folder.listFiles();
		String fileName,fileName2 ;
		
		for (File file : listOfFiles) {
		    if (file.isFile()) {
		        fileName = file.getName();
		        
		        fileName2 = CheckFileName(fileName);
		        if (!fileName.equalsIgnoreCase(fileName2)) {
		        	// Rename the file
		        	File newfile =new File(folderName+fileName2);

		    		if(file.renameTo(newfile)){
		    			System.out.println(fileName + " have been renamed succesfully to : " + fileName2);
		    			fileName = fileName2;
		    		}else{
		    			System.out.println("Warning: " + fileName + " COUNLD NOT rename to : " + fileName2 + ". This file will be igrored");
		    			continue;
		    		}
		        }
		        
		    	System.out.println("\n" + fileName);
		    	
		    	//System.out.println("Parsing using the Auto-Detect Parser:");
		    	BodyContentHandler handler = new BodyContentHandler();
			    AutoDetectParser parser = new AutoDetectParser();
			    Metadata metadata = new Metadata();
			    ParseContext parseContext = new ParseContext();
			    FileInputStream stream = new FileInputStream(new File(folderName + fileName));
		        try {
		        	parser.parse(stream, handler, metadata,parseContext);
		        }
		        catch (Exception e) {
		        	System.out.println("Warning: Error when processing file:" + fileName + " . This file will be igrored! \n" + e.getMessage());
		        	continue;
		        }
		        finally {
		        	stream.close(); 
		        }
				String s= handler.toString();
				/*		
				// getting metadata of the document
				System.out.println("Metadata of the file:");
				String [] metadataNames = metadata.names();
				for (String name : metadataNames) {
					System.out.println(name + " : " + metadata.get(name));
				}
				 */
				String language = identifyLanguage(s);
				System.out.println("Language of text:" +language);
				if (language.equalsIgnoreCase("en") || language.equalsIgnoreCase("et")) {

					//System.out.println(s);
					Writer writer = null;
					try {
					    writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream("text_" + folderName + fileName +".txt"), "utf-8"));
					    writer.write(s);
					} catch (IOException ex) {
						// report
						System.out.println("Warning: Error when saving file:" + fileName + ".txt  . This file had been ignore! \n" + ex.getMessage());
			        	continue;
					} finally {
					   try {writer.close();} catch (Exception ex) {/*ignore*/}
					}
				}
				else System.out.println("Warning: the content of the file:" + fileName + " have NOT been writen in English then this file will be ignored!");
		    }
		}
		

		
	}
	private static String CheckFileName(String fileName) {
		String s = fileName;
		s = s.replaceAll("^\\s+|\\s+$", ""); 	// remove all the space at the beginning and at the end of string S
		s = s.replaceAll("%20", " ");
		return s;
	}
	private static String Check_Folder_Name(String folderName) {
		String s = folderName.replaceAll("^\\s+|\\s+$", ""); 	// remove all the space at the beginning and at the end of string S
		if (!s.endsWith("/") ) s+="/";							// add a '/' character at the end if it's not present.
		return s;
	}
	public static void main(String[] args) throws IOException, SAXException, TikaException {
		createTextDataUsingTika("    data    ");
		
	}

}

