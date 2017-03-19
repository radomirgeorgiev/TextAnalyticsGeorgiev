package de.unidue.langtech.teaching.pp.project.test;

import static org.apache.uima.fit.factory.AnalysisEngineFactory.createEngine;
import static org.apache.uima.fit.factory.AnalysisEngineFactory.createEngineDescription;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;

import java.util.ArrayList;
import java.util.List;

import org.apache.uima.UIMAException;
import org.apache.uima.analysis_engine.AnalysisEngine;
import org.apache.uima.analysis_engine.AnalysisEngineDescription;
import org.apache.uima.collection.CollectionReaderDescription;
import org.apache.uima.fit.factory.CollectionReaderFactory;
import org.apache.uima.fit.pipeline.JCasIterable;
import org.apache.uima.fit.util.JCasUtil;
import org.apache.uima.jcas.JCas;
import org.apache.uima.resource.ResourceInitializationException;
import org.junit.Test;

import de.tudarmstadt.ukp.dkpro.core.api.metadata.type.DocumentMetaData;
import de.unidue.langtech.teaching.pp.project.reader.CollectionXMLReader;
import de.unidue.langtech.teaching.pp.project.reader.TimeLineXMLParser;
import de.unidue.langtech.teaching.pp.project.type.RawXMLData;

public class ReaderTest {

	private String inputFolderPartOne = "data/corpus_";
	private String inputFolderPartTwo = "/corpus_trackB_CAT";
	private String corpus;

	@Test
	public void testReader() throws Exception {

		for (int i = 1; i < 4; i++) {
			corpus = inputFolderPartOne + i + inputFolderPartTwo;
			testCollectionXMLReader();
		}
	}

	public void testCollectionXMLReader() throws Exception {
		CollectionReaderDescription reader = getReader();

		List<JCas> casObjects = getCasObjects(reader);
		int counter = 0;
		for (JCas jcas : casObjects) {

			RawXMLData rawXML = JCasUtil.selectSingle(jcas, RawXMLData.class);

			// Pruefe ob der bereits ausgelesene Dokument XML-Struktur enthaelt
			assertNotNull(rawXML.getRawXMLData());
			assertFalse(rawXML.getRawXMLData().isEmpty());

			// Pruefe ob die XML-Struktur umgewangelt werden kann
			testXMLParser(jcas);
			counter++;
		}
		// Pruefe ob der aktuelle Korpus 30 Dokumenten enth�lt
		assertEquals(30, counter);

	}

	private void testXMLParser(JCas jcas) throws UIMAException {
		// Analysiere die im JCas Container vorhandene XML-Struktur
		AnalysisEngineDescription timeLineXMLParser = createEngineDescription(TimeLineXMLParser.class);
		AnalysisEngine segEngine = createEngine(timeLineXMLParser);
		segEngine.process(jcas);

		// Pruefe ob der Parser die XML-Struktur gelesen und interpretiert hat
		String documentText = jcas.getDocumentText();

		// Der Text des Dokumentes (die Nachricht) darf nicht leer sein
		assertNotNull(documentText);
		assertFalse(documentText.isEmpty());

		DocumentMetaData docMetaData = DocumentMetaData.get(jcas);

		// Dokument-ID darf nicht leer sein
		assertNotNull(docMetaData.getDocumentId());
		assertFalse(docMetaData.getDocumentId().isEmpty());
		// Dokument-Title darf nicht leer sein
		assertNotNull(docMetaData.getDocumentTitle());
		assertFalse(docMetaData.getDocumentTitle().isEmpty());
		// Dokument-URI darf nicht leer sein (Obwohl in vorstehender Analyse
		// nicht gebraucht wird).
		assertNotNull(docMetaData.getDocumentUri());
		assertFalse(docMetaData.getDocumentUri().isEmpty());

	}

	private List<JCas> getCasObjects(CollectionReaderDescription aReader) throws ResourceInitializationException {

		List<JCas> casObjects = new ArrayList<JCas>();

		// Iteriere ueber den ganzen Dokumenten-Korpus. Jeder Korpus enthaelt
		// exact 30 Dokumenten.
		for (JCas jcas : new JCasIterable(aReader)) {
			casObjects.add(jcas);
		}

		return casObjects;
	}

	private CollectionReaderDescription getReader() throws Exception {
		return CollectionReaderFactory.createReaderDescription(CollectionXMLReader.class,
				CollectionXMLReader.PARAM_SOURCE_LOCATION, corpus, CollectionXMLReader.PARAM_PATTERNS,
				new String[] { "*.xml" });
	}

}
