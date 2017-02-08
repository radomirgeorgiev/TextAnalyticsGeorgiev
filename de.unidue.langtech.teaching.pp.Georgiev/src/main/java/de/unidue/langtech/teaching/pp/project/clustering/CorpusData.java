package de.unidue.langtech.teaching.pp.project.clustering;

import java.security.InvalidParameterException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

public class CorpusData {

	
	    // List of files analyzed. The results are indexed to this
	    private ArrayList<String> _files;

	    // The TF-IDF values of significant words, indexed by document
	    private ArrayList<DocumentWords> _wordMapping;

	    /* Constructor. Needs the files and the minimum TF-IDF value to be
	       considered significant. It varies with how documents were written, so no
	       single cutoff applies to all sets */
	    public CorpusData(ArrayList<String> files, double minSignificantValue) throws Exception
	    {
	        if (files.size() < 2)
	            throw new InvalidParameterException("Files to analyze word frequencies invalid, " + files + ". At least two must be specified");

	        _files = files;
	        _wordMapping = new ArrayList<DocumentWords>(_files.size());

	        int index = 0;
	        ClusterPrepareing clusterPrepair = new ClusterPrepareing();
	        while (index < _files.size()) {
	            DocumentWords results = null;            
	            // Get the raw word counts for the file. Skip on any exception
	            try {
	                Set<Map.Entry<String, WordCounter>> wordCounts = clusterPrepair.getWordFrequencies(_files.get(index)).entrySet();
	                if (!wordCounts.isEmpty()) { // Found something
	                    /* The raw counts are normalized to augmented term
	                       frequencies so a long document does not dominate the
	                       results. The augmented frequency is roughly the word
	                       count divided by the maximum word count in the document.
	                       Put the results in an array and sort it before doing
	                       the insert. Sorting an array is NlogN, while doing direct
	                       inserts of of unsorted data is N^2, because DocumentWords
	                       does a linear search for inserts */
	                    ArrayList<WordFrequency> tempResults = new ArrayList<WordFrequency>();
	                    
	                    // Find highest count in the document
	                    int highestCount = 0;
	                    Iterator<Map.Entry<String, WordCounter>> count = wordCounts.iterator();
	                    while (count.hasNext()) {
	                        int currCount = count.next().getValue().getCount();
	                        if (currCount > highestCount)
	                            highestCount = currCount;
	                    } // While more entries to go through

	                    // Now, convert and insert
	                    count = wordCounts.iterator();
	                    while (count.hasNext()) {
	                        Map.Entry<String, WordCounter> rawValue = count.next();
	                        double termFrequency = 0.5 + (((double)rawValue.getValue().getCount() * 0.5) / (double)highestCount);
	                        tempResults.add(new WordFrequency(rawValue.getKey(), termFrequency));
	                    } // While more entries to go through
	                    Collections.sort(tempResults);

	                    // Now insert into the actual results
	                    results = new DocumentWords();
	                    Iterator<WordFrequency> tempIndex = tempResults.iterator();
	                    while (tempIndex.hasNext())
	                        results.addWords(tempIndex.next());
	                } // Word counts found processing file
	            } // Try block
	            catch (Exception e) {
	                // Any exception means the file is ignored. 
	                /* WARNING: The get() call can issue its own exception. The
	                   call worked above, so this code assumes it will work here
	                   also */
	                System.out.println("File " + _files.get(index) + " ignored due to error: " + e);
	                // Ensure consistent results
	                results = null;
	            }

	            /* If have results, add them to the overall preliminary results,
	               otherwise, delete the file from the file list */
	            if (results != null) {
	                _wordMapping.add(results);
	                index++; // Move to next entry
	            }
	            else
	                _files.remove(index);
	            /* NOTE: Deleting the entry moves the next one into its place, so
	               the index should NOT be incremented here */
	        } // While files to process

	        /* Iterate through the mapping word by word. The number of documents
	           each word appears becomes the main input into the IDF portion of
	           the frequency value, which is used to scale the TF values already
	           in the results. If the average value falls below the threshold, the
	           word is removed from the corpus results; it adds compute without
	           affecting the final results very much */
	        CorpusByWord wordIndex = new CorpusByWord(_wordMapping);
	        while (wordIndex.hasNext()) {
	            ArrayList<DocumentFrequency> wordData = wordIndex.nextWord();
	            // Calculate IDF from document count for word
	            double invDocFreq = Math.log(((double)_wordMapping.size()) /
	                                         ((double)wordData.size()));
	            /* Find the average TF-IDF value for the word. The TF values are
	               in the current word data, giving an average TF value, which
	               multiplied by the IDF gives the average TF-IDF.
	               NOTE: In therory, it makes no difference whether the average TF
	               value is multiplied by the IDF value, or the individual TF-IDF
	               values are found and averaged. In practice, it could make a
	               difference due to rounding. This code assumes that the rounding
	               error is small enough compared to the double precision to not
	               worry about it */
	            double avgTFIDF = 0.0;
	            Iterator<DocumentFrequency> wordTF = wordData.iterator();
	            while (wordTF.hasNext())
	                avgTFIDF += wordTF.next().getFrequency();
	            avgTFIDF /= (double)wordData.size();
	            avgTFIDF *= invDocFreq;
	            
	            /* Eliminate the word if below the limit, otherwise scale the values
	               by the IDF value to get the final results */
	            if (avgTFIDF < minSignificantValue)
	                wordIndex.deleteProcessedWord();
	            else
	                wordIndex.scaleProcessedWord(invDocFreq);
	        } // While words in the document to pre-process
	    }

	    // Get the data in the object
	    public ArrayList<String> getFiles()
	    {
	        return _files;
	    }

	    public ArrayList<DocumentWords> getWordMapping()
	    {
	        return _wordMapping;
	    }

	    public String toString()
	    {
	        StringBuilder result = new StringBuilder();
	        int index;
	        for (index = 0; index < _files.size(); index++) {
	            try {
	                result.append(_files.get(index));
	            }
	            catch (Exception e) {
	                result.append("ERROR, FILE UNDEFINED");
	            }
	            result.append(": ");
	            try {
	                result.append(_wordMapping.get(index));
	            }
	            catch (Exception e) {
	                result.append("ERROR, UNDEFINED");
	            }
	            result.append("\n");
	        } // for loop
	        return result.toString();
	    }
}
