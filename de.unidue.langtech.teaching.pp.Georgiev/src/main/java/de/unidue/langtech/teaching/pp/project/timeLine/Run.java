package de.unidue.langtech.teaching.pp.project.timeLine;

import java.io.IOException;

import org.apache.uima.UIMAException;
import org.apache.uima.collection.CollectionReaderDescription;
import org.apache.uima.fit.factory.CollectionReaderFactory;
import org.apache.uima.fit.pipeline.SimplePipeline;

public class Run {

	private static final String inputXMLFolder = "/Volumes/Macintosh HD/Users/biciani/Desktop/SemEval_Task4_data/corpus_1/corpus_trackA_CAT/";

	public static void main(String[] args) throws UIMAException, IOException {

		CollectionReaderDescription corpusXmlReader = CollectionReaderFactory.createReaderDescription(
				RawXMLReader.class, RawXMLReader.PARAM_SOURCE_LOCATION, inputXMLFolder, RawXMLReader.PARAM_PATTERNS,
				new String[] { "*.xml" });

		SimplePipeline.runPipeline(corpusXmlReader);

	}

}
