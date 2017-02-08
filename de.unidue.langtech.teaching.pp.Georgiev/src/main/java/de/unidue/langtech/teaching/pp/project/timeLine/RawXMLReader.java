package de.unidue.langtech.teaching.pp.project.timeLine;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.apache.uima.UimaContext;
import org.apache.uima.collection.CollectionException;
import org.apache.uima.fit.descriptor.ConfigurationParameter;
import org.apache.uima.jcas.JCas;
import org.apache.uima.resource.ResourceInitializationException;
import org.apache.uima.util.Progress;
import org.w3c.dom.Document;
import org.xml.sax.SAXException;

import de.tudarmstadt.ukp.dkpro.core.api.io.JCasResourceCollectionReader_ImplBase;

public class RawXMLReader extends JCasResourceCollectionReader_ImplBase {

	public static final String PARAM_ENCODING = "encoding";
	@ConfigurationParameter(name = PARAM_ENCODING, mandatory = true, defaultValue = "UTF-8")
	private String encoding;

	private int currentReaderIdx = 0;
	private boolean eOF = false;
	private String[] filePaths = null;
	private Document doc = null;
	private static String docCreationDate;
	private static List<String> textToCreate;
	private static List<String> listOfEntyties;
	private static HashMap<String, Integer> tempMapOfCandidateEntities;
	private static HashMap<String, List<Integer>> mapOfCandidateEntities;

	@Override
	public void initialize(UimaContext context) throws ResourceInitializationException {
		super.initialize(context);
		mapOfCandidateEntities = new HashMap<String, List<Integer>>();
		initFileStreamsForAllFiles();

	}

	private void initFileStreamsForAllFiles() {
		List<String> files = new ArrayList<String>();
		try {
			for (Resource r : getResources()) {
				files.add(r.getResource().getFile().getAbsolutePath());
			}
		} catch (Exception e) {
			throw new IllegalStateException(e);
		}
		filePaths = files.toArray(new String[0]);
	}

	private Document initReader(String path) throws IOException, SAXException, ParserConfigurationException {
		File file = new File(path);

		if (isXML(file)) {
			return DocumentBuilderFactory.newInstance().newDocumentBuilder().parse(file);
		}
		return null;
	}

	private boolean isXML(File file) {
		return file.getAbsolutePath().endsWith(".xml");
	}

	public boolean hasNext() throws IOException, CollectionException {

		tempMapOfCandidateEntities = new HashMap<String, Integer>();
		if (eOF) {
			resetDocument();
		}
		if (currentReaderIdx < filePaths.length) {
			try {
				doc = getReader(currentReaderIdx);
				tempMapOfCandidateEntities = EventDetection.extractCandidate(doc, currentReaderIdx);
				putTempMapCandidateEntities(tempMapOfCandidateEntities);

			} catch (SAXException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (ParserConfigurationException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (ResourceInitializationException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (ClassNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return takeNextReader();
		}

		return takeNextReader();
	}
	
	private void putTempMapCandidateEntities(HashMap<String, Integer> temp){
			
		if(!mapOfCandidateEntities.isEmpty()){
			for(Map.Entry<String, Integer> item : temp.entrySet()){
				boolean flag = true;
				for(Map.Entry<String, List<Integer>> item1 : mapOfCandidateEntities.entrySet()){
					if(item.getKey().equals(item1.getKey())){
						item1.getValue().add(item.getValue());
						flag = false;
						
					} 
				}
				if(flag){
					List<Integer> tempIntList = new ArrayList<Integer>();
					tempIntList.add(item.getValue());
					mapOfCandidateEntities.put(item.getKey(), tempIntList);
				}
			}
		}else{
			for(Map.Entry<String, Integer> item : temp.entrySet()){
				List<Integer> tempIntList = new ArrayList<Integer>();
				tempIntList.add(item.getValue());
				mapOfCandidateEntities.put(item.getKey(), tempIntList);
			}
		}
	}

	private Document getReader(int idx) throws IOException, SAXException, ParserConfigurationException {
		if (doc == null) {
			doc = initReader(filePaths[idx]);
		}

		return doc;
	}

	private void resetDocument() {
		doc = null;
		filePaths[currentReaderIdx] = null;
		currentReaderIdx++;
	}

	private boolean takeNextReader() throws IOException, CollectionException {

		if (currentReaderIdx == filePaths.length) {
			return false;
		}
		return true;

	}

	public Progress[] getProgress() {
		return null;
	}

	@Override
	public void getNext(JCas jCas) throws IOException, CollectionException {
		// annotate raw-tweet in an own type we

//		for (String s : tempMapOfCandidateEntities.keySet()){
//			System.out.println(s + tempMapOfCandidateEntities.get(s));
//		}
		for (Map.Entry<String, List<Integer>> temp: mapOfCandidateEntities.entrySet()) {
			String str = " ";
			for(int i : temp.getValue()){
				 str+=String.valueOf(i)+" ";
			}
			System.out.println("Key : " +temp.getKey()  +str);
		}

//		RawText raw = new RawText(jCas);
//		raw.setDocumentRawText(str);
//		raw.setDocumentCreationDate(docCreationDate);
//		raw.addToIndexes();

		// set payload as the document text

		// unescaping makes the unicode characters (\\uXXXX) readable


		System.out.println(docCreationDate);
		System.out.println(currentReaderIdx);
		eOF = true;
	}

	private static void logWarn(String message) {
		Logger.getLogger(RawXMLReader.class.getName()).warning(message);
	}

}
