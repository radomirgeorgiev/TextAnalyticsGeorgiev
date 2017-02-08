package de.unidue.langtech.teaching.pp.project.reader;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.apache.uima.UimaContext;
import org.apache.uima.collection.CollectionException;
import org.apache.uima.fit.descriptor.ConfigurationParameter;
import org.apache.uima.jcas.JCas;
import org.apache.uima.jcas.cas.StringArray;
import org.apache.uima.resource.ResourceInitializationException;
import org.apache.uima.util.Progress;

import de.tudarmstadt.ukp.dkpro.core.api.io.JCasResourceCollectionReader_ImplBase;
import de.unidue.langtech.teaching.pp.project.type.RawXMLData;

public class CollectionXMLReader extends JCasResourceCollectionReader_ImplBase {

	public static final String PARAM_ENCODING = "encoding";
	@ConfigurationParameter(name = PARAM_ENCODING, mandatory = true, defaultValue = "UTF-8")
	private String encoding;

	private int currentReaderIdx = 0;
	private static String[] filePaths = null;
	private static List<String> entities;
	private static int collectionSize, tempIndex;
	private static List<String> lines;
	private BufferedReader reader = null;
	private RawXMLData raw;
	private static StringArray strA, strB;

	@Override
	public void initialize(UimaContext context) throws ResourceInitializationException {
		super.initialize(context);
		
		try {
			initFileWithEntities();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		initFileStreamsForAllFiles();
	}

	private void initFileWithEntities() throws IOException {
		entities = new EntityReader().readEntities();

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
		collectionSize = filePaths.length;
	}

	private BufferedReader initReader(String s) throws IOException {
		File file = new File(s);
		InputStreamReader isr = null;
		if (isXML(file)) {
			isr = initXMLStream(file);
		}
		return new BufferedReader(isr);
	}

	private boolean isXML(File file) {
		return file.getAbsolutePath().endsWith(".xml");
	}

	private InputStreamReader initXMLStream(File file)
			throws UnsupportedEncodingException, FileNotFoundException, IOException {
		return new InputStreamReader(new FileInputStream(file), encoding);
	}

	public boolean hasNext() throws IOException, CollectionException {
		tempIndex = currentReaderIdx;
		if (currentReaderIdx == filePaths.length) {
			return false;
		}
		BufferedReader reader = getReader(currentReaderIdx);
		lines = new ArrayList<String>();
		lines = reader.lines().collect(Collectors.toList());
		return takeNextReader();

	}

	private BufferedReader getReader(int idx) throws IOException {
		if (reader == null) {
			reader = initReader(filePaths[idx]);
		}

		return reader;
	}

	private boolean takeNextReader() throws IOException, CollectionException {
		reader.close();
		reader = null;
		filePaths[currentReaderIdx] = null;
		currentReaderIdx++;
		return true;
	}

	public Progress[] getProgress() {
		return null;
	}

	@Override
	public void getNext(JCas jcas) throws IOException, CollectionException {

		// annotate raw-xml in an own type we

		raw = new RawXMLData(jcas);
		entityExtract(jcas);
		raw.setListOfEntities(strA);
		raw.setListOfClusterCandidates(strB);
		raw.setRawXMLData(toText(lines));
		raw.setCollectionSize(collectionSize);
		raw.setTempIndex(tempIndex);
		raw.addToIndexes();
		// Set Document Language as "unspecifed"
		jcas.setDocumentLanguage("x-unspecified");
	}

	private static void entityExtract(JCas jcas) {
		strA = new StringArray(jcas, entities.size());
		strB = new StringArray(jcas, collectionSize);
		for (int i = 0; i < entities.size(); i++) {
			strA.set(i, entities.get(i));
		}
	}

	private String toText(List<String> list) {
		String temp = "";
		for (String str : list) {
			temp += str.concat("\n");
		}
		return temp;
	}

}
