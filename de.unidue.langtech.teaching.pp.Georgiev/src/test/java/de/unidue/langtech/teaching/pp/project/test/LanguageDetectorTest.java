/**
 * 
 */
package de.unidue.langtech.teaching.pp.project.test;

import static org.apache.uima.fit.factory.AnalysisEngineFactory.createEngine;
import static org.apache.uima.fit.factory.AnalysisEngineFactory.createEngineDescription;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.io.UnsupportedEncodingException;
import java.util.LinkedList;
import java.util.List;

import org.apache.uima.UIMAException;
import org.apache.uima.analysis_engine.AnalysisEngine;
import org.apache.uima.analysis_engine.AnalysisEngineDescription;
import org.apache.uima.fit.factory.JCasFactory;
import org.apache.uima.fit.util.JCasUtil;
import org.apache.uima.jcas.JCas;
import org.junit.Test;

import de.unidue.langtech.teaching.pp.project.languageDetector.Similarity;
import de.unidue.langtech.teaching.pp.project.languageDetector.BigramAnnotator;
import de.unidue.langtech.teaching.pp.project.languageDetector.Language;
import de.unidue.langtech.teaching.pp.project.languageDetector.LanguageDetector;
import de.unidue.langtech.teaching.pp.project.languageDetector.Tokenizer;
import de.unidue.langtech.teaching.pp.project.languageDetector.TrigramAnnotator;
import de.unidue.langtech.teaching.pp.project.languageDetector.UnigramAnnotator;
import de.unidue.langtech.teaching.pp.project.type.DetectedLanguage;

/**
 * @author Radomir Georgiev
 *
 */

public class LanguageDetectorTest {

	// Initialisiere drei Texte, auf drei verschiedenen Sprachen
	// Englisch
	private String englishText = "Winnie-the-Pooh, also called Pooh Bear, is a fictional anthropomorphic teddy bear created by English author A. A. Milne.";
	// Franzoesisch
	private String frenchText = "Winnie l'ourson est un personnage de la littérature d'enfance créé le 15 octobre 1926 par Alan Alexander Milne.";
	// Deutsch
	private String deutschText = "Pu der Bär ist der Name einer literarischen Figur und eines Kinderbuchs des Autors Alan Alexander Milne.";

	@Test
	public void testLanguageDetection() throws UIMAException, UnsupportedEncodingException {

		String text = "";
		int counter = 0;
		for (int i = 0; i < 3; i++) {
			switch (i) {
			case 0:
				text = englishText;
				break;
			case 1:
				text = frenchText;
				break;
			case 2:
				text = deutschText;
				break;
			}
			// Kreiere ein JCas-Container
			JCas jcas = JCasFactory.createJCas();
			// und speichere den zu analysierenden Text
			jcas.setDocumentText(text);

			// Anfang des Testes

			// Annotiere die Sprache des Textes und pruefe ob sie richtig
			// erkannt wurde.
			testLanguageAnnotation(jcas, i);
			// Annotiere die Tokens des Textes und pruefe ob sie richtg gezaehlt
			// wurden.
			testTokenization(text, i);
			// Annotiere die Unigramen des Textes und pruefe ob sie richtig
			// annotiert wurden.
			testUnigramAnnotation(jcas);
			// Annotiere die Bigramen des Textes und pruefe ob sie richtig
			// annotiert wurden.
			testBigramAnnotation(jcas);
			// Annotiere die Trigramen des Textes und pruefe ob sie richtig
			// annotiert wurden.
			testTrigramAnnotation(jcas);

			counter++;

		}
		// Pruefe ob die Klasse Werte zwischen 0.0 und 1.0 liefert. Similarity
		// darf nicht negativ oder Ueber 1 sein.
		testSimilarity();
		// Sind alle 3 Beispieltexte analysiert worden?
		assertEquals(3, counter);

		// Test ende
	}

	public void testSimilarity() {
		// Kreiere zwei Paare von Zufal-Vektoren
		int randomVectorSize = (int) (Math.random() * 100 + 1);
		double[] testVectorA = null;
		double[] testVectorB = null;
		double[] nullVectorA = null;
		double[] nullVectorB = null;

		if (randomVectorSize != 0) {
			testVectorA = new double[randomVectorSize];
			testVectorB = new double[randomVectorSize];
			nullVectorA = new double[randomVectorSize];
			nullVectorB = new double[randomVectorSize];
		}

		// Faelle die Vektoren mit Zufall-Werten. Das zweite Paar wird mit Nullen
		// gefaellt
		if (testVectorA != null && testVectorB != null) {
			for (int i = 0; i < randomVectorSize; i++) {
				testVectorA[i] = Math.random();
				testVectorB[i] = Math.random();
				nullVectorA[i] = 0.0;
				nullVectorB[i] = 0.0;
			}

		}

		// Pruefe ob die Kosinus-Aehnlichkeit der beiden Vektoren zwischen 0 und
		// 1 liegt
		double cosineSimilarityTestVectors = new Similarity().cosineSimilarity(testVectorA, testVectorB);
		assertTrue(cosineSimilarityTestVectors >= 0.0 && cosineSimilarityTestVectors <= 1.0);
		// Pruefe ob die Kosinus-Aehnlichkeit der beiden Null-Vektoren zwischen
		// 0 und 1 liegt
		double cosineSimilarityNullVectors = new Similarity().cosineSimilarity(nullVectorA, nullVectorB);
		assertTrue(cosineSimilarityNullVectors >= 0.0 && cosineSimilarityNullVectors <= 1.0);
	}

	public void testUnigramAnnotation(JCas jcas) throws UnsupportedEncodingException {
		// Der Text, der zum Pruefen gilt, wird initialisiert
		String inputText = jcas.getDocumentText();
		// und darf nicht leer sein.
		assertNotNull(inputText);
		assertFalse(inputText.isEmpty());

		// Tokaniziere den Text und zerteile ihn anschliessend zur einen Liste
		// aus Unigrammen.
		// Die Liste wird analysiert, ein Vektor gebildet, und am Ende mit Hilfe
		// von bereits gespeicherten GoldStandard-Vektor verglichen.
		// Jede von der drei Sprachen (DE, EN, FR) hat ein solcher Vektor, der
		// durch Analyse des Daniel Defoes Buches "Robinson Crusoe"
		// in jeweiligen Sprache gewonnen wurde. Die Distanz zwischen den beiden
		// Vektoren gibt uns die Aehnlichkeit der zur untersuchenden Sprache
		// mit der Sprache des Buches. Ein Wert von 0 bedeutet absolut keine
		// Aehnlichkeit zwischen den beiden Texten,
		// eine 1 hingegen - absolute Uebereinstimmung.
		List<Language> listOfDetectedLanguages = new UnigramAnnotator().process(jcas, Tokenizer.tokenize(inputText));
		// Pruefe ob es Werte fuer alle drei Sprachen vorhanden sind.
		assertEquals(3, listOfDetectedLanguages.size());

		// Pruefe ob die Werte zwischen 0 und 1 sind
		for (Language languageToCheck : listOfDetectedLanguages) {
			double cosineSimilarity = 0.0;
			cosineSimilarity = languageToCheck.getValue();
			assertTrue(cosineSimilarity >= 0.0 && cosineSimilarity <= 1.0);
		}

	}

	public void testBigramAnnotation(JCas jcas) throws UnsupportedEncodingException {
		// Der Text, der zum Pruefen gilt, wird initialisiert
		String inputText = jcas.getDocumentText();
		// und darf nicht leer sein.
		assertNotNull(inputText);
		assertFalse(inputText.isEmpty());

		// Tokaniziere den Text und zerteile ihn anschliessend zur einen Liste
		// aus Bigrammen.
		// Der Vorgang ist analog zur Unigramm Annotation
		List<Language> listOfDetectedLanguages = new BigramAnnotator().process(jcas, Tokenizer.tokenize(inputText));
		// Pruefe ob es Werte fuer alle drei Sprachen vorhanden sind.
		assertEquals(3, listOfDetectedLanguages.size());

		// Pruefe ob die Werte zwischen 0 und 1 sind
		for (Language languageToCheck : listOfDetectedLanguages) {
			double cosineSimilarity = 0.0;
			cosineSimilarity = languageToCheck.getValue();
			assertTrue(cosineSimilarity >= 0.0 && cosineSimilarity <= 1.0);
		}

	}

	public void testTrigramAnnotation(JCas jcas) throws UnsupportedEncodingException {
		// Der Text, der zum Pruefen gilt, wird initialisiert
		String inputText = jcas.getDocumentText();
		// und darf nicht leer sein.
		assertNotNull(inputText);
		assertFalse(inputText.isEmpty());

		// Tokaniziere den Text und zerteile ihn anschliessend zur einen Liste
		// aus Trigrammen.
		// Der Vorgang ist analog zur Unigramm Annotation
		List<Language> listOfDetectedLanguages = new TrigramAnnotator().process(jcas, Tokenizer.tokenize(inputText));
		assertEquals(3, listOfDetectedLanguages.size());

		// Pruefe ob die Werte zwischen 0 und 1 sind
		for (Language languageToCheck : listOfDetectedLanguages) {
			double cosineSimilarity = 0.0;
			cosineSimilarity = languageToCheck.getValue();
			assertTrue(cosineSimilarity >= 0.0 && cosineSimilarity <= 1.0);
		}

	}

	public void testTokenization(String inputText, int numberOfText) throws UnsupportedEncodingException {
		// Der Text, der zum Pruefen gilt, wird initialisiert
		List<String> tokenizedText = new LinkedList<String>();

		// und tokaniziert.
		new Tokenizer();
		tokenizedText = Tokenizer.tokenize(inputText);

		// Pruefe ob der Text tatsaechlich soviel Tokens enthaelt.
		int numberOfTextTokens = tokenizedText.size();
		switch (numberOfText) {
		case 0:
			assertEquals(20, numberOfTextTokens);
			break;
		case 1:
			assertEquals(18, numberOfTextTokens);
			break;
		case 2:
			assertEquals(17, numberOfTextTokens);
			break;
		}

	}

	public void testLanguageAnnotation(JCas jcas, int numberOfText) throws UIMAException {
		// Analysiere und annotiere den Text auf Sprache
		AnalysisEngineDescription languageDetector = createEngineDescription(LanguageDetector.class);
		AnalysisEngine segEngine = createEngine(languageDetector);
		segEngine.process(jcas);

		// Lade die erkannte Sprache
		DetectedLanguage detectedLanguage = JCasUtil.selectSingle(jcas, DetectedLanguage.class);

		// und pruefe ob sie tatsaechlich die original Textsprache ist
		switch (numberOfText) {
		case 0:
			assertEquals("EN", detectedLanguage.getLanguage());
			break;
		case 1:
			assertEquals("FR", detectedLanguage.getLanguage());
			break;
		case 2:
			assertEquals("DE", detectedLanguage.getLanguage());
			break;
		}
	}

}
