package de.unidue.langtech.teaching.pp.project.clustering;

import java.io.IOException;

import org.apache.uima.UIMAException;
import org.apache.uima.fit.factory.AnalysisEngineFactory;
import org.apache.uima.fit.factory.CollectionReaderFactory;
import org.apache.uima.fit.pipeline.SimplePipeline;

import de.unidue.langtech.teaching.pp.project.reader.CollectionXMLReader;

public class RunClustering {

	static String inputFolder = "C:\\Users\\Nivelin Stoyanov\\Desktop\\SemEval_Task4_data\\corpus_1\\corpus_trackA_CAT\\";

	public static void main(String[] args) throws UIMAException, IOException {
		SimplePipeline.runPipeline(CollectionReaderFactory.createReaderDescription(CollectionXMLReader.class,
				CollectionXMLReader.PARAM_SOURCE_LOCATION, inputFolder, CollectionXMLReader.PARAM_PATTERNS,
				new String[] { "*.xml" }),

				
				AnalysisEngineFactory.createEngineDescription(Clustering.class)

		);

	}

}