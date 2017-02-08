package de.unidue.langtech.teaching.pp.example.tests;

import java.io.IOException;

import org.apache.uima.UIMAException;
import org.apache.uima.analysis_engine.AnalysisEngineDescription;
import org.apache.uima.collection.CollectionReader;
import org.apache.uima.fit.component.CasDumpWriter;
import org.apache.uima.fit.factory.AnalysisEngineFactory;
import org.apache.uima.fit.factory.CollectionReaderFactory;
import org.apache.uima.fit.pipeline.SimplePipeline;

import de.tudarmstadt.ukp.dkpro.core.stanfordnlp.StanfordParser;
import de.tudarmstadt.ukp.dkpro.core.stanfordnlp.StanfordSegmenter;

public class Run_Test {
	private static final String inputXMLFolder = "/Volumes/Macintosh HD/Users/biciani/Desktop/SemEval_Task4_data/corpus_1/corpus_trackA_CAT/";

	public static void main(String[] args) throws UIMAException, IOException {

		CollectionReader reader = CollectionReaderFactory.createReader(
				Reader_Test_Example.class, Reader_Test_Example.PARAM_INPUT_FILE,
				"src/test/resources/collection/test.txt");

        AnalysisEngineDescription seg = AnalysisEngineFactory.createEngineDescription(StanfordSegmenter.class);

        AnalysisEngineDescription parser = AnalysisEngineFactory.createEngineDescription(StanfordParser.class);

        AnalysisEngineDescription dep = AnalysisEngineFactory.createEngineDescription(DependencyExtractor.class);
       
        AnalysisEngineDescription writer = AnalysisEngineFactory.createEngineDescription(CasDumpWriter.class);

        SimplePipeline.runPipeline(reader, seg, parser,  writer, dep);

	}
}
