package de.unidue.langtech.teaching.pp.project.pipeline;

import org.apache.uima.analysis_component.JCasAnnotator_ImplBase;
import org.apache.uima.analysis_engine.AnalysisEngineProcessException;
import org.apache.uima.cas.FSIterator;
import org.apache.uima.cas.FeatureStructure;
import org.apache.uima.fit.util.JCasUtil;
import org.apache.uima.jcas.JCas;
import org.apache.uima.jcas.cas.TOP;

import de.tudarmstadt.ukp.dkpro.core.api.segmentation.type.Sentence;
import de.tudarmstadt.ukp.dkpro.core.api.segmentation.type.Token;
import de.tudarmstadt.ukp.dkpro.core.api.syntax.type.dependency.Dependency;

public class DependencyExtractor extends JCasAnnotator_ImplBase{

	public int index;
	@Override
	public void process(JCas jcas) throws AnalysisEngineProcessException {
		
//		for(Sentence sentence : JCasUtil.select(jcas, Sentence.class)){
//			for(Dependency dependency : JCasUtil.selectCovered(jcas, Dependency.class, sentence)){
//				System.out.println(dependency.getCoveredText());
//				System.out.println("/");
//				System.out.println(dependency.getDependencyType());
//				System.out.println(" ");
//			}
//		}
		
//		for(Sentence sentence : JCasUtil.select(jcas, Sentence.class)){
//			for(Dependency dep : JCasUtil.selectCovered(jcas, Dependency.class, sentence)){
//				System.out.println(dep.getCoveredText() + " Dependency Type: " + dep.getDependencyType());
//				System.out.println("Dependent :" +dep.getDependent().getCoveredText() + " Governor: " + dep.getGovernor().getCoveredText());
//				System.out.println(" ");
//			}
//		}
		index=0;
		String st = jcas.getDocumentText();
		System.out.println(st);
		FSIterator<FeatureStructure> sentence = jcas.getFSIndexRepository().getAllIndexedFS(jcas.getCasType(Sentence.type));
		while(sentence.hasNext()){
			Sentence sent = (Sentence) sentence.next();
			System.out.println(sent.getCoveredText());
			System.out.println(sent.getSofa());
			System.out.println(index);
			index++;
			extract(jcas);
		}
		
	}
	
	public void extract(JCas jcas){
		FSIterator<FeatureStructure> dependencies = jcas.getFSIndexRepository().getAllIndexedFS(jcas.getCasType(Dependency.type));
		while(dependencies.hasNext()){
			Dependency dep = (Dependency) dependencies.next();
			FSIterator<FeatureStructure> fs = dep.getCAS().getIndexRepository().getAllIndexedFS(jcas.getCasType(Sentence.type));
			while(fs.hasNext()){
				System.out.println(fs);
			}
//			System.out.println(dep.getDependencyType().toString());
//			System.out.println(dep.getDependent().getCoveredText());
//			System.out.println("Dependent: " + dep.getDependent().getCoveredText());
//			System.out.println("Governor: " + dep.getGovernor().getCoveredText());
			if(dep.getDependencyType().equals("nsubj")){
				Token t =dep.getDependent();
				Token s =dep.getGovernor();
				System.out.println("Dependent: " + t.getCoveredText());
				System.out.println("Governor: " + s.getCoveredText());
			} 
		}
	}

}
