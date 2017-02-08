package de.unidue.langtech.teaching.pp.project.clustering;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;

public class DocumentCluster {
    // Files that were clustered
    private ArrayList<String> _files;

    // Analysis data of files
    private ArrayList<DocumentWords> _wordMapping;
    
    /* Documents with insufficient words in common with other documents, or
       only words found in all other documents, which can't be clustered */
    private HashSet<String> _notClustered;

    // Constructor. Takes a file list and creates the initial data list
    public DocumentCluster(ArrayList<String> files) throws Exception
    {
        // Create a document copus. The significance level comes from experiment
        // NOTE: Documents that do not exist or have no data are filtered out
        CorpusData data = new CorpusData(files, 0.4);

        /* Extract the data from it. Now filter it for words that appear in too
           FEW documents for clustering. In a regular document distribution,
           they add lots of compute without affecting the results much
           WARNING: In truly disjoint documents, removing these words, as well
           as the elimination of common words done by the CorpusData object,
           may leave no data to cluster on. Extract these documents and report
           them as 'unclusterable'. They should be incredibly rare in
           practice */
        _wordMapping = data.getWordMapping();
        _files = data.getFiles();

        /* The percentage of documents that couts as 'too few' is somewhat
           arbitrary. Different papers use different cutoffs, with around a
           quarter being the median.
           WARNING: This code assumes that documents will rarely be eliminated
           due to having only the most common words, otherwise this cout will
           skew too high accordingly */
        int minDocCount = _files.size() / 4;
        // The similarity measure ignores words in only one document
        if (minDocCount < 1) 
            minDocCount = 1;

        CorpusByWord wordIndex = new CorpusByWord(_wordMapping);
        while (wordIndex.hasNext()) {
            if (wordIndex.nextWord().size() <= minDocCount)
                wordIndex.deleteProcessedWord();
        } // While words in the document to pre-process

        // Any file with no data at this point can't be clustered
        _notClustered = new HashSet<String>();
        int index = 0;
        while (index < _wordMapping.size()) {
            if (_wordMapping.get(index).isEmpty()) {
                // Document with no words to cluster
                _wordMapping.remove(index);
                _notClustered.add(_files.get(index));
                _files.remove(index);
                /* Do not increment the index, the removal moves the next
                   entry into its place */
            } // Document has no words for clustering
            else {
                /* Normalize the vector to have similarity value 1 with itself,
                   so the clustering algorithm works properly */
                _wordMapping.get(index).normalize();
                index++;
            }
        } // While documents to test
    }

    // Cluster files in the object
    public ArrayList<HashSet<String>> cluster(int clusterCount,
                                              boolean traceOutput) throws Exception
    {
        /* This method uses the classic simple k-means algorithm for
           clustering. Its an iterative algorithm based on centroids. Since they
           are normalized, each document represents a vector within an
           N-dimensional sphere. Pick a number of vectors equal to the number
           of clusters, and sort the remainder into clusters based on which
           vector they are most similiar to. These form the initial partition
           into clusters. Averaging the vectors within each cluster gives the
           centroid of each cluster.
              Now, the vectors are repartitioned based on the centroids, again
           grouping them by similarity. This will give different cluster
           contents, more similiar than last time. Find new centroids and repeat
           again. The algorithm terminates when either no vector moves to
           another cluster, or a certain number of iterations is reached
           DATA DESIGN NOTE: This code tracks the cluster number per document,
           because its faster then creating explicit clusters, and constantly
           inserting and deleting members. The actual clustes are only
           reported at the end */

        /* Max cluster count is half the nummber of documents that can be
           clustered */
        if (clusterCount > _files.size() / 2)
            clusterCount = _files.size() / 2;
        if (traceOutput) {
            System.out.println("Files:");
            System.out.println(_wordMapping);
            System.out.println("Clusters wanted: " + clusterCount);
        }

        ArrayList<HashSet<String>> result = new ArrayList<HashSet<String>>();
        if (clusterCount < 2)
            // Can't cluster. Put everything in one cluster and return
            result.add(new HashSet<String>(_files));
        else {
            int[] clusterMapping = new int[_files.size()];
            ArrayList<DocumentWords> currClusters = new ArrayList<DocumentWords>();
            /* Seed the list with documents. This assumes relatively random
               distribution of words between documents, otherwise the results
               will be skewed */
            int index;
            for (index = 0; index < clusterCount; index++)
                currClusters.add(_wordMapping.get(index));
            int iteration = 0;
            boolean docMoved = true;
            while (docMoved && (iteration < 10)) {
                docMoved = false;
                /* For each document, find the closest cluster. If not equal to
                   the current location, move it there */
                for (index = 0; index < _wordMapping.size(); index++) {
                    int bestCluster = findClosestCluster(_wordMapping.get(index),
                                                         currClusters);
                    if (bestCluster != clusterMapping[index]) {
                        clusterMapping[index] = bestCluster;
                        docMoved = true;
                    } // Document belongs in a different cluster
                } // For each document

                if (docMoved) {
                    // Find centroids of the resulting clusters
                    for (index = 0; index < currClusters.size(); index++) {
                        // Assemble a vector of all documents in the cluster
                        ArrayList<DocumentWords> clusterDocs = new ArrayList<DocumentWords>();
                        int index2;
                        for (index2 = 0; index2 < clusterMapping.length; index2++)
                            if (clusterMapping[index2] == index)
                                clusterDocs.add(_wordMapping.get(index2));
                        /* If cluster has no documents, leave its centroid where
                           it was; documents may move back to it after the
                           centroid of their new clusters move */
                        if (!clusterDocs.isEmpty())
                            currClusters.set(index, findCentroid(clusterDocs));
                    } // For each cluster
                } // At least one document changed clusters
                if (traceOutput) {
                    System.out.println("Current cluster assignments:");
                    System.out.println(Arrays.toString(clusterMapping));
                    System.out.println("Current cluster centroids:");
                    for(DocumentWords dW : currClusters){
                    	System.out.println(dW.toString());
                    }
                    
                } // Trace output
                iteration++;
            } // While clustering can be improved and reason to do so

            // Build actual clusters from the final data
            /* NOTE: This is harder than it appears. The final number of
               clusters may be smaller than the number asked for, so the
               cluster numbers in the final array may not be consecutive. Most
               performant solution is another array to translate the original
               cluster numbers into positions in the final array set */
            int[] clusterPositions = new int[clusterCount];
            Arrays.fill(clusterPositions, -1); // 0 is a valid index
            for (index = 0; index < clusterMapping.length; index++) {
                /* If given cluster number does not already exist in final
                   results, create it and add to the mapping array */
                if (clusterPositions[clusterMapping[index]] == -1) {
                    clusterPositions[clusterMapping[index]] = result.size();
                    result.add(new HashSet<String>());
                }
                result.get(clusterPositions[clusterMapping[index]]).add(_files.get(index));
            } // For each document in final results
        } // Files can be clustered
        return result;
    }

    // Given a document, finds the most similiar cluster
    private int findClosestCluster(DocumentWords testDoc,
                                   ArrayList<DocumentWords> clusters) throws Exception
    {
        int index;
        int bestCluster = 0;
        double bestSimilarity = testDoc.findSimilarity(clusters.get(0));
        for (index = 1; index < clusters.size(); index++) {
            double newSimilarity = testDoc.findSimilarity(clusters.get(index));
            if (newSimilarity > bestSimilarity) {
                bestSimilarity = newSimilarity;
                bestCluster = index;
            } // New cluster better than those tested so far
        } // Loop through clusters
        return bestCluster;
    }
    
    /* Given a collection of documents, finds their centroid. This is defined
       as treating each document as a vector within word space, and finding
       their average. */
    private DocumentWords findCentroid(ArrayList<DocumentWords> documents) throws Exception
    {
        /* The documents are vectors within a space of words. Iterate through
           each word dimension, and average the values. If the word does not
           appear in a particular document, its value for that document is
           zero */

        // If documents is empty, this fails
        CorpusByWord index = new CorpusByWord(documents); 
        DocumentWords result = new DocumentWords();
        double docCount = (double)documents.size();
        while (index.hasNext()) {
            ArrayList<DocumentFrequency> wordData = index.nextWord();
            int tempIndex;
            double sum = 0.0;
            for (tempIndex = 0; tempIndex < wordData.size(); tempIndex++)
                sum += wordData.get(tempIndex).getFrequency();
            result.addWords(index.getProcessedWord(), sum / docCount);
        } // While word dimensions to process
        return result;
    }

    public ArrayList<HashSet<String>> cluster(int clusterCount) throws Exception
    {
        return cluster(clusterCount, false);
    }

    public String toString()
    {
        StringBuilder result = new StringBuilder();
        result.append("Files to cluster:\n");
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
        result.append("\n Not clusterable fles:");
        result.append(_notClustered.toString());
        return result.toString();
    }

    // Main program. Read in the specified input files and cluster them
    public static void main(String[] args) throws Exception
    {
        try {
            if (args.length < 3)
                System.out.println("A cluster count followed by at least two files must be specified");
            else {
                // First argument is the number of clusters
                int clusterCount = Integer.parseInt(args[0]);
                // Assemble the file list from the rest
                /* NOTE: All methods that assemble an ArrayList from an array
                   iterate through the array, so this really is the most
                   efficient method available */
                ArrayList<String> files = new ArrayList<String>();
                int index;
                for (index = 1; index < args.length; index++)
                    files.add(args[index]);
                DocumentCluster clusterMaker = new DocumentCluster(files);
                ArrayList<HashSet<String>> result = clusterMaker.cluster(clusterCount, false);
                System.out.println(result);
            }
        }
        catch (Exception e) {
            System.out.println("Exception " + e + " caught");
            throw e; // Rethrow so improper temination is obvious
        }
    }
}
