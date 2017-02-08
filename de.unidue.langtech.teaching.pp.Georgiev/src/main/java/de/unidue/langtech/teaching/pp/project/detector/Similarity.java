package de.unidue.langtech.teaching.pp.project.detector;

public class Similarity {

	public double cosineSimilarity(double[] vectorA, double[] vectorB){
		double dotProduct = 0.0;
		double normA = 0.0;
		double normB = 0.0;
		for (int i = 0; i < vectorA.length; i++) {
	        dotProduct += vectorA[i] * vectorB[i];
	        normA += Math.pow(vectorA[i], 2);
	        normB += Math.pow(vectorB[i], 2);
	    }
		
		return dotProduct / (Math.sqrt(normA) * Math.sqrt(normB));
	}
	
    public double euclideanDistance(double[] vectorA, double[] vectorB) {
        double diff_square_sum = 0.0;
        for (int i = 0; i < vectorA.length; i++) {
            diff_square_sum += (vectorA[i] - vectorB[i]) * (vectorA[i] - vectorB[i]);
        }
        return Math.sqrt(diff_square_sum);
    }
    
}
