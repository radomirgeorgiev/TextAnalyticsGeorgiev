package de.unidue.langtech.teaching.pp.project.pipeline;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;

import org.apache.uima.analysis_engine.AnalysisEngineDescription;
import org.apache.uima.collection.CollectionReaderDescription;
import org.apache.uima.fit.factory.AnalysisEngineFactory;
import org.apache.uima.fit.factory.CollectionReaderFactory;
import org.apache.uima.fit.pipeline.SimplePipeline;

import de.tudarmstadt.ukp.dkpro.core.opennlp.OpenNlpChunker;
import de.tudarmstadt.ukp.dkpro.core.snowball.SnowballStemmer;
import de.tudarmstadt.ukp.dkpro.core.stanfordnlp.StanfordParser;
import de.tudarmstadt.ukp.dkpro.core.stanfordnlp.StanfordPosTagger;
import de.tudarmstadt.ukp.dkpro.core.stanfordnlp.StanfordSegmenter;
import de.unidue.langtech.teaching.pp.project.entityDetector.EntityDetector;
import de.unidue.langtech.teaching.pp.project.languageDetector.LanguageDetector;
import de.unidue.langtech.teaching.pp.project.reader.CollectionXMLReader;
import de.unidue.langtech.teaching.pp.project.reader.TimeLineXMLParser;

public class Run {
	// Hauptmethode
	public static void main(String[] args) throws Exception {

		String inputPartA = "data/corpus_";
		String inputPartB = "/corpus_trackB_CAT";

		// ruft die Methode zum Löschen von Ergebnissen von bestimmten Files vor
		// Berechnungen

		eraseData();

		for (int i = 1; i < 4; i++) {
			writeCorpusID(i);

			String corpus = inputPartA + i + inputPartB;
			System.out.println("Analysiere Korpus: " + i);
			// Lese alle Files mit einer Endung *.xml, die im inputFolder
			// geladen sind
			CollectionReaderDescription reader = CollectionReaderFactory.createReaderDescription(
					CollectionXMLReader.class, CollectionXMLReader.PARAM_SOURCE_LOCATION, corpus,
					CollectionXMLReader.PARAM_PATTERNS, new String[] { "*.xml" });

			// Lade Komponenten :

			// Selbst geschriebenen Modul,das Raw Text von XML File exrtahiere
			AnalysisEngineDescription tlXML = AnalysisEngineFactory.createEngineDescription(TimeLineXMLParser.class);
			// Selbst geschriebenen Modul,das Language ermitteln
			AnalysisEngineDescription langD = AnalysisEngineFactory.createEngineDescription(LanguageDetector.class);
			// Open NLP Chunker
			AnalysisEngineDescription chun = AnalysisEngineFactory.createEngineDescription(OpenNlpChunker.class);
			// Stanford Segmenter
			AnalysisEngineDescription seg = AnalysisEngineFactory.createEngineDescription(StanfordSegmenter.class);
			// Stanford Parser
			AnalysisEngineDescription parser = AnalysisEngineFactory.createEngineDescription(StanfordParser.class);
			// Stanford POS Tagger
			AnalysisEngineDescription pos = AnalysisEngineFactory.createEngineDescription(StanfordPosTagger.class);
			// Snownall Stemmer
			AnalysisEngineDescription stem = AnalysisEngineFactory.createEngineDescription(SnowballStemmer.class);
			// Selbst geschriebenen Modul,das Entities ermitteln
			AnalysisEngineDescription entDet = AnalysisEngineFactory.createEngineDescription(EntityDetector.class);

			SimplePipeline.runPipeline(reader, tlXML, langD, seg, parser, pos, chun, stem, entDet);
			System.out.println("Analyse Korpus " + i + " fertig!");
		}
		// Kreiere eine neue Instanz zu der PrintEvaluationResult und
		// ruft die methode printResult um die Endergebnisse auszudrucken
		new PrintEvaluationResult().printResult();
	}

	// Schreib in File corpusID, die Nummer von dem Corpus der abgearbeitet
	// wurde
	private static void writeCorpusID(int corpusID) throws FileNotFoundException {
		String filePath = "src/test/resources/storage/";
		String file = "corpusID";
		PrintWriter writer = new PrintWriter(filePath + file);
		writer.print(corpusID);
		writer.close();
	}

	// Löscht die Daten von bestimmten Files
	private static void eraseData() throws IOException {
		String filePath = "src/test/resources/storage/";
		String[] files = { "storageDocuments.ser", "storageEvents.ser", "storageResult1.ser", "storageResult2.ser",
				"storageResult3.ser", "storageEntity.ser" };
		for (int i = 0; i < files.length; i++) {
			PrintWriter writer = new PrintWriter(filePath + files[i]);
			writer.print("");
			writer.close();
		}
	}

}