package de.unidue.langtech.teaching.pp.project.test;

import static org.apache.uima.fit.factory.AnalysisEngineFactory.createEngine;
import static org.apache.uima.fit.factory.AnalysisEngineFactory.createEngineDescription;
import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.LinkedList;
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

import de.tudarmstadt.ukp.dkpro.core.api.segmentation.type.Sentence;
import de.tudarmstadt.ukp.dkpro.core.opennlp.OpenNlpChunker;
import de.tudarmstadt.ukp.dkpro.core.snowball.SnowballStemmer;
import de.tudarmstadt.ukp.dkpro.core.stanfordnlp.StanfordParser;
import de.tudarmstadt.ukp.dkpro.core.stanfordnlp.StanfordPosTagger;
import de.tudarmstadt.ukp.dkpro.core.stanfordnlp.StanfordSegmenter;
import de.unidue.langtech.teaching.pp.project.entityDetector.EntityDetector;
import de.unidue.langtech.teaching.pp.project.languageDetector.LanguageDetector;
import de.unidue.langtech.teaching.pp.project.reader.CollectionXMLReader;
import de.unidue.langtech.teaching.pp.project.reader.TimeLineXMLParser;
import de.unidue.langtech.teaching.pp.project.storage.SimpleToken;
import de.unidue.langtech.teaching.pp.project.timeDetector.TimeDetection;

public class EntityDetectorTest {
	
	//Hier ist die .xml Datei, die zu Pruefen ist.
	private String folder = "src/test/resources/test";
	//Der Text des Dokuments.
	private String[] documentText = {"Boeing unveils long-range 777 .\n",
			"February 17 , 2005 .\n",
			"Boeing has unveiled a new ultra-long range version of its 777 airliner , capable of flying from London to Sydney non-stop . \n",
			"The 777-200LR can fly for 9,420 nautical miles ( 17,446 kilometers ) with 301 passengers onboard , giving it a range greater than any other airliner in the world . \n",
			"The aircraft can fly from New York to Singapore and from Los Angeles to Dubai with a full passenger and luggage load non-stop , and has been named the Worldliner by Boeing to mark the fact it can connect almost any two cities on Earth . \n",
			"Although it can fly from London to Sydney non-stop , the 777-200LR will have to break its return journey to refuel due to direction of the prevailing winds in the upper atmosphere . \n",
			"So far , Boeing has received five orders from two customers for the 777-200LR since it was announced in February 2000 . \n",
			"The first of the new airliner will be delivered to Pakistan International Airlines in January 2006 . \n",
			"EVA Airways is the other launch customer . \n",
			"First flight is scheduled in early March . \n",
			"The 777-200LR will be the basis for an all-freight version of the 777 , announced last November . \n",
			"Boeing 's European rival Airbus , which has out-sold Boeing since 2003 , believes that the future of passenger flight is in very large aircraft like its new twin-deck A380 airliner flying up to 840 people between a relatively small number of large hub airports world-wide . \n", 
			"Passengers would then take connecting flights to reach their final destinations . \n",
			"Boeing , by contrast , believes passengers will want to fly direct between more local airports too small to handle aircraft like the A380 . "};
	private JCas jcas;
	//Die von uns erkannten Entitaeten.
	private String[] entityCandidates = {"Boeing 777", "Boeing", "Airbus A380", "Airbus"};
	private EntityDetector ed;
	//Die von uns erkannten Daten.
	private String[] recognizedTime = {"2005-02-17", "XXXX-XX-XX", "2000-02-XX", "2006-01-XX", "2003-XX-XX"};
	//Ereignisse, die schon bekannt gegeben sind.
	private String[] documentEvents = {"unveils", "unveiled", "named", "received", "announced", "delivered", "out-sold", "flying"};
	

	@Test
	public void testEntityDetection() throws Exception{
		//Initialisiere den CollocetionReader
		CollectionReaderDescription reader = getReader();
		//und lade alle jcas-Objekte aus der Mappe.
		List<JCas> casObjects = getCasObjects(reader);
		
		//Fuer jeden jcas-Objekt beginne mit der Annotation. Fuer unsere Zwecke werden folgende dkpro- und selbstentwickelte Komponenten benoetigt:
		//1. Collection Reader - liest alle in einer Mappe forhandenen Dokumenten und gibt den Inhalt zur Verarbeitung weiter.
		//In userem Fall sind es .xml Dokumente, die durch entsprechenden XML-Parser "dekodiert" werden.
		//2. XML-Parser - ist einer von uns entwickelte Komponent, der die .xml Dokumente "dekodiert" und Texte extrahiert.
		//3. Language Detector - erkennt die Sprache des aus der .xml Datei gewonnenem Text
		//4. Stanford Segmenter
		//5. Stanford Parser
		//6. Stanford POS Tagger
		//7. Snowball Stemmer
		//8. Open NLP Chunker
		for(JCas jcas_ : casObjects){
			jcas = jcas_;
			process();
		}
		
		//Kreiere eine neue Instanz zu der Klasse Entity Detector. Sie wird benoetigt um einen Text zu analysieren und Erkenntnisse
		//ueber die Relation von Ereignisse und Entitaeten, sowie Zeitpunkte zu denen sie eingetroffen worden sind, zu gewinnen.
		//Dies ist im Allgemeinen auch die Aufgabe dieser Arbeit.
		ed = new EntityDetector();
		ed.process(jcas);
		
		//Lade die XML-Struktur aus der .xml Datei und extrahiere den Text daraus. Pruefe anschliessend ob dies korrekt geschieht.
		testExtractedTextFromDocument();
		//Wir gehen davon aus, dass es in diesem Dokument um 4 Entitaeten geht. Hier wird geprueft, ob sie richtig erkannt wurden.
		testRecognizedEntitiesForGivenDocument();	
		//Pruefe ob die Ereignisse tatsaechlich vorhanden sind.
		testListOfEventForGivenDocument();
		//Wir gehen davon aus, dass es in diesem Dokument um 5 verschiede Daten geht. Hier wird geprueft, ob sie richtig erkannt wurden.
		testTimeDetection();
		
	}
	
	private void testTimeDetection(){
		//Test den kompleten Dokument auf Datumerkennung
		//Initialisiere alle Saetze, in denen die Ereignisse vornotiert wurden, und die zur Datum-Erkennung gelten.
		String sentence = "";
		for(int i = 0 ; i < recognizedTime.length; i++){
			switch(i){
				case 0:
					sentence = documentText[0];
					break;
				case 1:
					sentence = documentText[4];
					break;
				case 2:
					sentence = documentText[6];
					break;
				case 3:
					sentence = documentText[7];
					break;
				case 4:
					sentence = documentText[11];
					break;
			}
			//Gibt den Satz an die Klasse TimeDetection weiter und erwartet als Ausgebe das annotierte Datum.
			String recTime = testRecognizedTimeForGivenSentence(sentence).getTime();
			//Pruefe ob das Datum, das wir glauben es in diesem Satz vorhanden ist.
			assertEquals(recTime, recognizedTime[i]);
		}
	}
	
	
	private void testListOfEventForGivenDocument(){
		
		//Hinter dieser Struktur verbirgt sich die bereits annotierten Ereignisse des jeweiligen Dokuments.
		//Lade sie also.
		LinkedList<LinkedList<SimpleToken>> llst = ed.getListOfDocumentEvents();
		//Hier werden nur den "covered Text" der Ereignisse gespeichert.
		String[] annotatedDocumentEvents = new String[llst.size()];
		int counter = 0;
		for(LinkedList<SimpleToken> lst : llst){
			for(SimpleToken st : lst){
				annotatedDocumentEvents[counter] = st.getTokenText();
				counter++;
			}
		}
		//Pruefe ob die beiden Arrays äquivalaent sind
		assertArrayEquals(documentEvents, annotatedDocumentEvents);
		
	}
	
	private TimeDetection testRecognizedTimeForGivenSentence(String sentence){
		//Lade alle Saetze des analysierten Textes aus dem jcas-Container
		LinkedList<Sentence> llsent = new LinkedList<Sentence>(JCasUtil.select(jcas, Sentence.class));
		
		int sentenceNumber = 0;
		//Pruefe ob der zur Analyse gegebene Satz mit einem Satz jcas-Container uebereinstimmt
		for(Sentence sen : llsent){
			if(sentence.contains(sen.getCoveredText())){
				//und gebe seine Nummer preis.
				sentenceNumber = llsent.indexOf(sen);
			}
		}
		//Hinter dieser Struktur verbirgt sich die bereits annotierten Ereignisse des jeweiligen Dokuments.
		//Lade sie also.
		LinkedList<LinkedList<SimpleToken>> llst = ed.getListOfDocumentEvents();
		LinkedList<SimpleToken> lst = null;
		//Pruefe ob es in diesem Satz ein bereits annotierten Ereignis vorhanden ist
		for(LinkedList<SimpleToken> tempST : llst){
			if(tempST.getFirst().getSentenceNumber() == sentenceNumber){
				//und gebe ihn weiter.
				lst = tempST;
			}
		}		
		//Kreiere eine neue Instanz zur TimeDetection-Klasse. Dort wird der Satz mit dem Ereignis analysiert.
		//Es wird ein Datum erwartet, das folgendermassen aussieht: yyyy-MM-DD,
		//wobei nicht erkannten Jahr, Monat oder Tag mit "X" ersetzt werden.
		//Folgende Formate: 2005-XX-XX, 2017-02-XX, 2010-11-07 oder XXXX-XX-XX sind zulaessig.
		return new TimeDetection(jcas, llsent.get(sentenceNumber), ed.getDocumentCreationDate(), jcas.getDocumentLanguage(), lst);
	}
	
	private void testExtractedTextFromDocument(){
		//Lade den bereits "dekodierten" Text aus dem jcas-Container.
		String extractedText = jcas.getDocumentText();
		String actualText = "";
		//Lade den Text, den wir per Hand annotiert haben
		for(int i = 0; i < documentText.length; i++){
			actualText += documentText[i];
		}
		//und Pruefe ob sie uebereinstimmen.
		assertEquals(actualText, extractedText);
	}
	
	private void testRecognizedEntitiesForGivenDocument(){
		//Lade alle Entitaeten, die unser Model fuer aktuellen Dokument erkannt hat.
		List<String> tempList = ed.getListOfEntityCandidatesForGivenDocument();
		String[] recognizedEntityCandidates = new String[tempList.size()];
		recognizedEntityCandidates = tempList.toArray(recognizedEntityCandidates);
		//Pruefe ob die Entitaeten, die wir glauben in diesem Dokument vorhanden sind, mit den Entitaeten
		//aus unserem Model uebereinstimmen.
		assertArrayEquals(entityCandidates, recognizedEntityCandidates);
	}
	

    private void process() throws UIMAException{
    	//Lade alle Komponenten
    	//XML Parser
		AnalysisEngineDescription timeLineXMLParser = createEngineDescription(TimeLineXMLParser.class);
		AnalysisEngine tmlEngine = createEngine(timeLineXMLParser);
		tmlEngine.process(jcas);
		//Language Detector
		AnalysisEngineDescription languageDetector = createEngineDescription(LanguageDetector.class);
		AnalysisEngine langDEngine = createEngine(languageDetector);
		langDEngine.process(jcas);
		//Stanford Segmenter
		AnalysisEngineDescription stanfordSegmenter = createEngineDescription(StanfordSegmenter.class);
		AnalysisEngine stanSegEngine = createEngine(stanfordSegmenter);
		stanSegEngine.process(jcas);
		//Stanford Parser
		AnalysisEngineDescription stanfordParser = createEngineDescription(StanfordParser.class);
		AnalysisEngine stanParEngine = createEngine(stanfordParser);
		stanParEngine.process(jcas);
		//Stanford POS Tagger
		AnalysisEngineDescription stanfordPosTagger = createEngineDescription(StanfordPosTagger.class);
		AnalysisEngine stanPosTEngine = createEngine(stanfordPosTagger);
		stanPosTEngine.process(jcas);
		//Snownall Stemmer
		AnalysisEngineDescription snowballStemmer = createEngineDescription(SnowballStemmer.class);
		AnalysisEngine snowBEngine = createEngine(snowballStemmer);
		snowBEngine.process(jcas);
		//Open NLP Chunker
		AnalysisEngineDescription openNlpChunker = createEngineDescription(OpenNlpChunker.class);
		AnalysisEngine opNlpEngine = createEngine(openNlpChunker);
		opNlpEngine.process(jcas);
		
    }
    
    private List<JCas> getCasObjects(CollectionReaderDescription aReader) throws ResourceInitializationException
    {

        List<JCas> casObjects = new ArrayList<JCas>();
        
        
        for(JCas jcas :  new JCasIterable(aReader)){
            casObjects.add(jcas);
        }

        return casObjects;
    }
	
    private CollectionReaderDescription getReader()
            throws Exception
        {
            return CollectionReaderFactory.createReaderDescription(CollectionXMLReader.class, CollectionXMLReader.PARAM_SOURCE_LOCATION, folder,
	                CollectionXMLReader.PARAM_PATTERNS, new String[] { "*.xml"});
        }
	
}
