package de.unidue.langtech.teaching.pp.project.clustering;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

import org.apache.commons.io.FileUtils;
import org.apache.uima.UIMAException;
import org.apache.uima.analysis_engine.AnalysisEngineProcessException;
import org.apache.uima.fit.component.JCasAnnotator_ImplBase;
import org.apache.uima.fit.factory.AnalysisEngineFactory;
import org.apache.uima.fit.pipeline.SimplePipeline;
import org.apache.uima.fit.util.JCasUtil;
import org.apache.uima.jcas.JCas;
import org.apache.uima.resource.ResourceInitializationException;

import de.tudarmstadt.ukp.dkpro.core.api.segmentation.type.Token;
import de.tudarmstadt.ukp.dkpro.core.stanfordnlp.StanfordLemmatizer;
import de.tudarmstadt.ukp.dkpro.core.stanfordnlp.StanfordPosTagger;
import de.tudarmstadt.ukp.dkpro.core.stanfordnlp.StanfordSegmenter;
import de.tudarmstadt.ukp.dkpro.core.stopwordremover.StopWordRemover;
import de.unidue.langtech.teaching.pp.project.languageDetector.LanguageDetector;
import de.unidue.langtech.teaching.pp.project.reader.ExtractTextFromXML;
import de.unidue.langtech.teaching.pp.project.type.RawXMLData;

public class Clustering extends JCasAnnotator_ImplBase{

	String inputFolder = "C:\\Users\\Nivelin Stoyanov\\Desktop\\SemEval_Task4_data\\test";
	String inputStopword = "src/test/resources/collection/stopwordsLong.txt";
	private String regex =  "^[\'#.,;:?!&`%-]+$";
	private String inputCorpusFolder ="src/test/resources/collection/collection";

	
	public void process(JCas jcas) throws AnalysisEngineProcessException {
		
		try {
			prepairData(jcas);
		} catch (ResourceInitializationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (UIMAException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	
	public void prepairData(JCas jcas) throws Exception{
		
		List<String> tempList = new ArrayList<String>();
		
		SimplePipeline.runPipeline(
				jcas,
				AnalysisEngineFactory.createEngineDescription(ExtractTextFromXML.class),
				AnalysisEngineFactory.createEngineDescription(LanguageDetector.class),
				AnalysisEngineFactory.createEngineDescription(StanfordSegmenter.class),	
				AnalysisEngineFactory.createEngineDescription(StopWordRemover.class, StopWordRemover.PARAM_MODEL_LOCATION, new String[]{inputStopword}),
				AnalysisEngineFactory.createEngineDescription(StanfordPosTagger.class),
				AnalysisEngineFactory.createEngineDescription(StanfordLemmatizer.class));
		
		for(Token t : JCasUtil.select(jcas, Token.class)){
			
			if(!t.getCoveredText().matches(regex)){
				System.out.println(t.getLemma().getValue());
				tempList.add(t.getLemma().getValue());
			}
					
		}
		RawXMLData rawX = JCasUtil.selectSingle(jcas, RawXMLData.class);
		int temp1 = rawX.getCollectionSize();
		int temp2 = rawX.getTempIndex();
		int temp3 = temp1-temp1+temp2;
			
		String tempString="";
		for(int i = 0; i<tempList.size();i++){
			if((tempList.size()-i)==1){
				tempString += tempList.get(i)+"\n";
			}else{
				tempString += tempList.get(i)+" ";
			}
			
		}
		System.out.println("Index: "+temp3 + " "+tempString);

		writeIntoFile(inputCorpusFolder, tempString);
		
		if(temp3==temp1-1){
			ArrayList<String> myList = readFromFile(inputCorpusFolder);
            DocumentCluster clusterMaker = new DocumentCluster(myList);
            ArrayList<HashSet<String>> result = clusterMaker.cluster(15, true);
            for(HashSet<String> temp : result){
            	System.out.println(temp.toString());
            }
            clearFile(inputCorpusFolder);    
                     
		}
		
	}
	
	private static void writeIntoFile(String path, String collection){
		try {
		    Files.write(Paths.get(path), collection.getBytes(), StandardOpenOption.APPEND);
		}catch (IOException e) {
		    //exception handling left as an exercise for the reader
		}
		
	}
	private static ArrayList<String> readFromFile(String path) throws IOException{
		List<String> tempList = FileUtils.readLines(new File(path));
		ArrayList<String> ff = new ArrayList<String>();
		for(String str : tempList){
			ff.add(str);
		}
		return ff;
	}
	private static void clearFile(String path) throws FileNotFoundException{
		PrintWriter writer = new PrintWriter(path);
		writer.print("");
		writer.close();
	}

}
