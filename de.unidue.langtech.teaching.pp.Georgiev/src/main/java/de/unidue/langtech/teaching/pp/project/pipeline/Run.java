package de.unidue.langtech.teaching.pp.project.pipeline;

import org.apache.uima.analysis_engine.AnalysisEngineDescription;
import org.apache.uima.collection.CollectionReaderDescription;
import org.apache.uima.fit.component.CasDumpWriter;
import org.apache.uima.fit.factory.AnalysisEngineFactory;
import org.apache.uima.fit.factory.CollectionReaderFactory;
import org.apache.uima.fit.pipeline.SimplePipeline;

import de.tudarmstadt.ukp.dkpro.core.stanfordnlp.StanfordCoreferenceResolver;
import de.tudarmstadt.ukp.dkpro.core.stanfordnlp.StanfordLemmatizer;
import de.tudarmstadt.ukp.dkpro.core.stanfordnlp.StanfordNamedEntityRecognizer;
import de.tudarmstadt.ukp.dkpro.core.stanfordnlp.StanfordParser;
import de.tudarmstadt.ukp.dkpro.core.stanfordnlp.StanfordPosTagger;
import de.tudarmstadt.ukp.dkpro.core.stanfordnlp.StanfordSegmenter;
import de.unidue.langtech.teaching.pp.project.detector.BigrammAnnotator;
import de.unidue.langtech.teaching.pp.project.detector.Detector;
import de.unidue.langtech.teaching.pp.project.detector.ReaderG;
import de.unidue.langtech.teaching.pp.project.detector.TrigrammAnnotator;
import de.unidue.langtech.teaching.pp.project.detector.UnigrammAnnotator;
import de.unidue.langtech.teaching.pp.project.entityDetector.EntityDetector;
import de.unidue.langtech.teaching.pp.project.eventDetector.EventDetector;
import de.unidue.langtech.teaching.pp.project.languageDetector.LanguageDetector;
import de.unidue.langtech.teaching.pp.project.reader.ExtractTextFromXML;
import de.unidue.langtech.teaching.pp.project.reader.CollectionXMLReader;


public class Run {
	
	  public static void main(String[] args)
		        throws Exception
		    {
		    	 
		    	
		    	//DetectorFactory.loadProfile("../seminar2016_uni-due/profiles");
		        String inputFolder = "/Volumes/Macintosh HD/Users/biciani/Desktop/SemEval_Task4_data/corpus_1/corpus_trackA_CAT";
		    
		        // Read all files with file ending *.xml that are located in the inputFolder
		        CollectionReaderDescription reader = CollectionReaderFactory.createReaderDescription(
		                CollectionXMLReader.class, CollectionXMLReader.PARAM_SOURCE_LOCATION, inputFolder,
		                CollectionXMLReader.PARAM_PATTERNS, new String[] { "*.xml"});

		        //Extracting the Raw Text from XML File
		        
		        CollectionReaderDescription simpleR = CollectionReaderFactory.createReaderDescription(ReaderG.class, ReaderG.PARAM_INPUT_FILE,
						"src/test/resources/test/test.txt");
		        AnalysisEngineDescription xmlR = AnalysisEngineFactory.createEngineDescription(ExtractTextFromXML.class);
		        AnalysisEngineDescription langD = AnalysisEngineFactory.createEngineDescription(LanguageDetector.class);

		        AnalysisEngineDescription printA = AnalysisEngineFactory.createEngineDescription(PrinterAll.class);
		        AnalysisEngineDescription seg = AnalysisEngineFactory.createEngineDescription(StanfordSegmenter.class);
		        AnalysisEngineDescription parser = AnalysisEngineFactory.createEngineDescription(StanfordParser.class);
		        AnalysisEngineDescription pos = AnalysisEngineFactory.createEngineDescription(StanfordPosTagger.class);
		        AnalysisEngineDescription lem = AnalysisEngineFactory.createEngineDescription(StanfordLemmatizer.class);
		        AnalysisEngineDescription namER = AnalysisEngineFactory.createEngineDescription(StanfordNamedEntityRecognizer.class);
		        AnalysisEngineDescription sCorR = AnalysisEngineFactory.createEngineDescription(StanfordCoreferenceResolver.class);
		        AnalysisEngineDescription depE = AnalysisEngineFactory.createEngineDescription(DependencyExtractor.class);
		        AnalysisEngineDescription evDet = AnalysisEngineFactory.createEngineDescription(EventDetector.class);
		        AnalysisEngineDescription writer = AnalysisEngineFactory.createEngineDescription(CasDumpWriter.class);
		      


		        // A demo that shows some basic accessing of the framework API and how to get access to the
		        // information in the tweets

		        //This framework has many more modules that might become useful for you (depending on your task)
		        //An entire list can be found here https://code.google.com/p/dkpro-core-asl/wiki/ComponentList_1_6_2

		        SimplePipeline.runPipeline(reader, xmlR, langD, seg, parser, pos, namER, evDet, printA);
		    }


}
