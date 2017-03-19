package de.unidue.langtech.teaching.pp.project.languageDetector;

import java.util.List;
import org.apache.uima.jcas.JCas;

/**
 * NGrammAnotator interface
 * 
 * @autho Radomir Georgiev
 *
 */
public interface NGramAnotator {

	public List<Language> process(JCas aJcas, List<String> stringList);
}
