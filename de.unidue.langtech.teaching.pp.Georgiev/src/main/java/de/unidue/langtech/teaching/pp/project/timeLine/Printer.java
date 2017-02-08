package de.unidue.langtech.teaching.pp.project.timeLine;

import java.util.Collection;

import org.apache.uima.analysis_engine.AnalysisEngineProcessException;
import org.apache.uima.fit.component.JCasAnnotator_ImplBase;
import org.apache.uima.fit.util.JCasUtil;
import org.apache.uima.jcas.JCas;

import de.unidue.langtech.teaching.pp.project.type.BaseLanguage;
import de.unidue.langtech.teaching.pp.project.type.DetectedLanguage;
import de.unidue.langtech.teaching.pp.project.type.GoldLanguage;

public class Printer extends JCasAnnotator_ImplBase {


	@Override
	public void process(JCas jcas) throws AnalysisEngineProcessException {
		// This API always returns a collection even if you know that there
		// should be only one
		Collection<BaseLanguage> letterCount = JCasUtil.select(jcas, BaseLanguage.class);
	

		// There is a special API for the case you know that there is exactly
		// one annotation
		GoldLanguage gold = JCasUtil.selectSingle(jcas, GoldLanguage.class);
		DetectedLanguage detected = JCasUtil.selectSingle(jcas, DetectedLanguage.class);

		for (BaseLanguage t : letterCount) {
			System.out.println("Detected: " + detected.getLanguage() + " Gold:" + gold.getLanguage());
//			for (int i = 0; i < t.getIntegerVector().size(); i++) {
//				System.out.println("Number of " + combine[i] + " " + t.getIntegerVector(i));
//			}
//			for (int i = 0; i < t.getDoubleVector().size(); i++) {
//				System.out.println("Number of " + combine[i] + " " + t.getDoubleVector(i));
//			}

		}

	}

}