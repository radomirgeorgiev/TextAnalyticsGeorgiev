package de.unidue.langtech.teaching.pp.example.tests;

import org.apache.uima.analysis_component.JCasAnnotator_ImplBase;
import org.apache.uima.analysis_engine.AnalysisEngineProcessException;
import org.apache.uima.cas.FSIterator;
import org.apache.uima.cas.FeatureStructure;
import org.apache.uima.jcas.JCas;
import org.apache.uima.jcas.cas.TOP;

import de.tudarmstadt.ukp.dkpro.core.api.segmentation.type.Sentence;
import de.tudarmstadt.ukp.dkpro.core.api.syntax.type.dependency.Dependency;
import edu.stanford.nlp.scoref.FeatureExtractor;

public class DependencyExtractor extends JCasAnnotator_ImplBase   {

	@Override
	public void process(JCas jcas) throws AnalysisEngineProcessException {
		// TODO Auto-generated method stub
		
		TOP text = jcas.getCas().getDocumentAnnotation();
		System.out.println(text.toString());
		
		FSIterator<FeatureStructure> sentence = jcas.getFSIndexRepository().getAllIndexedFS(jcas.getCasType(Sentence.type));
		while(sentence.hasNext()){
			Sentence sent = (Sentence) sentence.next();
			System.out.println(sent);
		}
		
		FSIterator<FeatureStructure> dependencies = jcas.getFSIndexRepository().getAllIndexedFS(jcas.getCasType(Dependency.type));
		while(dependencies.hasNext()){
			Dependency dep = (Dependency) dependencies.next();
			System.out.println(dep);
		}
	}

}
