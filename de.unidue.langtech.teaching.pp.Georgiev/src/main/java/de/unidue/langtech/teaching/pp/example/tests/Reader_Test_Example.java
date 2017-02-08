package de.unidue.langtech.teaching.pp.example.tests;

import java.io.File;
import java.io.IOException;
import java.util.List;

import org.apache.commons.io.FileUtils;
import org.apache.uima.UimaContext;
import org.apache.uima.collection.CollectionException;
import org.apache.uima.fit.component.JCasCollectionReader_ImplBase;
import org.apache.uima.fit.descriptor.ConfigurationParameter;
import org.apache.uima.jcas.JCas;
import org.apache.uima.resource.ResourceInitializationException;
import org.apache.uima.util.Progress;
import org.apache.uima.util.ProgressImpl;

import de.unidue.langtech.teaching.pp.project.type.GoldLanguage;

public class Reader_Test_Example extends JCasCollectionReader_ImplBase{

    /**
     * Input file
     */
    public static final String PARAM_INPUT_FILE = "InputFile";
    @ConfigurationParameter(name = PARAM_INPUT_FILE, mandatory = true)
  
    private File inputFile;    
    
    private List<String> lines;
    private int currentLine;
    
    /* 
     * initializes the reader
     */
    @Override
    public void initialize(UimaContext context)
        throws ResourceInitializationException
    {
        super.initialize(context);
        
        try {
           lines = FileUtils.readLines(inputFile);
           currentLine = 0;
        }
        catch (IOException e) {
            throw new ResourceInitializationException(e);
        }
    }
    
    
    /* 
     * true, if there is a next document, false otherwise
     */
    public boolean hasNext()
        throws IOException, CollectionException
    {
        return currentLine < lines.size();
    }
    
    
    /* 
     * feeds the next document into the pipeline
     */
    @Override
    public void getNext(JCas jcas)
        throws IOException, CollectionException
    {
        // split line into gold standard language and actual text
        String parts = lines.get(currentLine);
        
        // it is always good to do some sanity checks
//        if (parts.length != 2) {
//            throw new IOException("Wrong line format: " + lines.get(currentLine));
//        }
        
        // add gold standard value as annotation
//        GoldLanguage goldLanguage = new GoldLanguage(jcas);
//        goldLanguage.setLanguage(parts[0]);
//        goldLanguage.addToIndexes();
//        
        // add actual text of the document
        jcas.setDocumentText(parts);
        jcas.setDocumentLanguage("en");
        currentLine++;
    }

    
    /* 
     * informs the pipeline about the current progress
     */
    public Progress[] getProgress()
    {
        return new Progress[] { new ProgressImpl(currentLine, lines.size(), "lines") };
    }

}
