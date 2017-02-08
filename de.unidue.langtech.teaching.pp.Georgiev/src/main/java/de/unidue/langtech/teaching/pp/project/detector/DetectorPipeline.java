package de.unidue.langtech.teaching.pp.project.detector;

import org.apache.uima.fit.factory.AnalysisEngineFactory;
import org.apache.uima.fit.factory.CollectionReaderFactory;
import org.apache.uima.fit.pipeline.SimplePipeline;

public class DetectorPipeline {
	public static void main(String[] args) throws Exception {
		// SimplePipeline.runPipeline(CollectionReaderFactory.createReader(ReaderG.class,
		// ReaderG.PARAM_INPUT_FILE,
		// "src/test/resources/test/detector.txt"),
		//
		// AnalysisEngineFactory.createEngineDescription(BreakIteratorSegmenter.class),
		// AnalysisEngineFactory.createEngineDescription(BaselineExample.class),
		// AnalysisEngineFactory.createEngineDescription(LetterAnnotator.class),
		// AnalysisEngineFactory.createEngineDescription(Printer.class));
		SimplePipeline.runPipeline(
				CollectionReaderFactory.createReader(ReaderG.class, ReaderG.PARAM_INPUT_FILE,
						"src/test/resources/test/en.txt"),
//				AnalysisEngineFactory.createEngineDescription(UnigrammAnnotator.class),
//				AnalysisEngineFactory.createEngineDescription(BigrammAnnotator.class),
				AnalysisEngineFactory.createEngineDescription(TrigrammAnnotator.class),
//				AnalysisEngineFactory.createEngineDescription(Detector.class),
				AnalysisEngineFactory.createEngineDescription(Printer.class));
	}
}
