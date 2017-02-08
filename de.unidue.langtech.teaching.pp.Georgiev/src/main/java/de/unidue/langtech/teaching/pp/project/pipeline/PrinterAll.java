package de.unidue.langtech.teaching.pp.project.pipeline;

import org.apache.uima.analysis_engine.AnalysisEngineProcessException;
import org.apache.uima.fit.component.JCasAnnotator_ImplBase;
import org.apache.uima.fit.util.JCasUtil;
import org.apache.uima.jcas.JCas;

import de.unidue.langtech.teaching.pp.project.type.DetectedLanguage;

public class PrinterAll extends JCasAnnotator_ImplBase{
	public void process(JCas jcas) throws AnalysisEngineProcessException {
		// This API always returns a collection even if you know that there
		// should be only one
//		Collection<BaseLanguage> letterCount = JCasUtil.select(jcas, BaseLanguage.class);
	

		// There is a special API for the case you know that there is exactly
		// one annotation
		DetectedLanguage detected = JCasUtil.selectSingle(jcas, DetectedLanguage.class);
		//DateFormat curD = JCasUtil.selectSingle(jcas, DateFormat.class);
		
		System.out.println("Detected: " + detected.getLanguage());
		//System.out.println("Date of Document: " +  curD.getCurrentDate());
//		for (BaseLanguage t : letterCount) {
//
////			for (int i = 0; i < t.getIntegerVector().size(); i++) {
////				System.out.println("Number of " + combine[i] + " " + t.getIntegerVector(i));
////			}
////			for (int i = 0; i < t.getDoubleVector().size(); i++) {
////				System.out.println("Number of " + combine[i] + " " + t.getDoubleVector(i));
////			}
//
//		}

	}
}
