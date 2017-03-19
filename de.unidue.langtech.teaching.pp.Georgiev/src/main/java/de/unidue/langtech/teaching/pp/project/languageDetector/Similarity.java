
package de.unidue.langtech.teaching.pp.project.languageDetector;

/**
 * The Class Similarity.
 */
public class Similarity {

	/**
	 * Cosine similarity Bildet die Cosinus-Ähnlichkeit zwischen zwei Vektoren.
	 * 
	 * @param vectorA
	 * @param vectorB
	 * @return gibt die berechnete Cosinus-Ähnlichkeit zwei Vektoren zurück
	 */
	public double cosineSimilarity(double[] vectorA, double[] vectorB) {
		double dotProduct = 0.0;
		double normA = 0.0;
		double normB = 0.0;
		double result = 0.0;
		for (int i = 0; i < vectorA.length; i++) {
			dotProduct += vectorA[i] * vectorB[i];
			normA += Math.pow(vectorA[i], 2);
			normB += Math.pow(vectorB[i], 2);
		}

		if ((normA != 0.0 && !Double.isNaN(normA)) && (normB != 0.0 && !Double.isNaN(normB))
				&& !Double.isNaN(dotProduct)) {
			result = dotProduct / (Math.sqrt(normA) * Math.sqrt(normB));
			return result;
		}
		return result;
	}

	/**
	 * Euclidean distance. Bildet der euklidische Abstand zwischen zwei Vektoren
	 * 
	 * @param vectorA
	 * @param vectorB
	 * @return gibt der berechnete euklidische Abstand zwei Vektoren zurück
	 */
	public double euclideanDistance(double[] vectorA, double[] vectorB) {
		double diff_square_sum = 0.0;
		for (int i = 0; i < vectorA.length; i++) {
			diff_square_sum += (vectorA[i] - vectorB[i]) * (vectorA[i] - vectorB[i]);
		}
		return Math.sqrt(diff_square_sum);
	}

}
