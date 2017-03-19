package de.unidue.langtech.teaching.pp.project.entityDetector;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.ArrayUtils;

import de.unidue.langtech.teaching.pp.project.languageDetector.Similarity;


/**
 * Klasse MatrixMaker.
 */
public class MatrixMaker {

	private final static char[] alpha = "0123456789abcdefghijklmnopqrstuvwxyz".toCharArray();
	
	private final static char[] uml = { '\u00df', '\u00e0', '\u00e1', '\u00e2', '\u00e3', '\u00e4', '\u00e5', '\u00e6',
			'\u00e7', '\u00e8', '\u00e9', '\u00ea', '\u00eb', '\u00ec', '\u00ed', '\u00ee', '\u00ef', '\u00f0',
			'\u00f1', '\u00f2', '\u00f3', '\u00f4', '\u00f5', '\u00f6', '\u00f8', '\u00f9', '\u00fa', '\u00fb',
			'\u00fc' };
	
	private int[][][] intCube;
	
	private double[][][] doubleCube;
	
	private int[][] intMatrix;
	
	private double[][] doubleMatrix;

	/**
	 * Gets the cube matrix.
	 *
	 * @param listOfWords the list of wiki words
	 * @return the cube matrix
	 */
	public double[][][] getCubeMatrix(List<String> listOfWords) {
		List<String> tempList = new ArrayList<String>();
		for (String str : listOfWords) {
			String[] temp = str.split(" ");
			for (int i = 0; i < temp.length; i++) {
				tempList.add(temp[i]);
			}
		}
		processCube(tempList);
		return doubleCube;
	}

	/**
	 * Gets the matrix.
	 *
	 * @param listOfWikiWords the list of wiki words
	 * @return the matrix
	 */
	public double[][] getMatrix(List<String> listOfWords) {
		List<String> tempList = new ArrayList<String>();
		for (String str : listOfWords) {
			String[] temp = str.split(" ");
			for (int i = 0; i < temp.length; i++) {
				tempList.add(temp[i]);
			}
		}
		processMatrix(tempList);
		return doubleMatrix;
	}

	/**
	 * Process matrix.
	 *
	 * @param stringList the string list
	 */
	public void processMatrix(List<String> stringList) {
		char[] combine = (char[]) ArrayUtils.addAll(alpha, uml);
		intMatrix = new int[combine.length][combine.length];
		doubleMatrix = new double[combine.length][combine.length];

		int counterAll = 0;

		for (int i = 0; i < combine.length; i++) {
			for (int j = 0; j < combine.length; j++) {
				String s1 = "" + combine[i] + combine[j];
				int temp = 0;
				for (String str : stringList) {
					String s2 = " " + str + " ";
					for (int a = 0; a < str.length() + 1; a++) {
						String s3 = "";
						s3 = s2.substring(a, a + 2);
						if (s1.equals(s3)) {
							temp++;
							counterAll++;
						}
					}
				}
				intMatrix[i][j] = temp;
			}
		}

		relativeValueOfMatrix(intMatrix, counterAll);
	}

	/**
	 * Process cube.
	 *
	 * @param stringList 
	 */
	public void processCube(List<String> stringList) {
		char[] combine = (char[]) ArrayUtils.addAll(alpha, uml);
		intCube = new int[combine.length][combine.length][combine.length];
		doubleCube = new double[combine.length][combine.length][combine.length];

		int counterAll = 0;

		for (int i = 0; i < combine.length; i++) {
			for (int j = 0; j < combine.length; j++) {
				for (int k = 0; k < combine.length; k++) {
					String s1 = "" + combine[i] + combine[j] + combine[k];
					int temp = 0;
					for (String str : stringList) {
						String s2 = "  " + str + "  ";
						for (int a = 0; a < str.length() + 2; a++) {
							String s3 = "";
							s3 = s2.substring(a, a + 3);
							if (s1.equals(s3)) {
								temp++;
								counterAll++;
							}
						}
						intCube[i][j][k] = temp;
					}
				}
			}
		}

		relativeValueOfCubeMatrix(intCube, counterAll);
	}

	/**
	 * Relative value of matrix.
	 *
	 * @param myArray 
	 * @param value 
	 */
	private void relativeValueOfMatrix(int[][] myArray, int value) {

		int temp = 0;
		for (int i = 0; i < myArray.length; i++) {
			for (int j = 0; j < myArray.length; j++) {
				double result = 0.0;
				temp = myArray[i][j];
				if (value != 0) {
					result = temp / (double) value;
				}
				doubleMatrix[i][j] = result;
			}
		}

	}

	/**
	 * Relative value of cube matrix.
	 *
	 * @param myArray 
	 * @param value 
	 */
	public void relativeValueOfCubeMatrix(int[][][] myArray, int value) {

		int temp = 0;
		for (int i = 0; i < myArray.length; i++) {
			for (int j = 0; j < myArray.length; j++) {
				for (int k = 0; k < myArray.length; k++) {
					double result = 0.0;
					temp = myArray[i][j][k];
					if (value != 0) {
						result = temp / (double) value;
					}
					doubleCube[i][j][k] = result;
				}
			}
		}
	}

	/**
	 * Bildet die Cosinus-Ähnlichkeit zwischen zwei Vektoren. Als Resultat
	 * liefert die Methode ein Cosinus-Werten.
	 *
	 * @param matrix 
	 * @param chunks 
	 * @return gibt score zurück
	 */
	public double similarityCube(double[][][] matrix, double[][][] chunks) {

		double[] aVector = new double[matrix.length * matrix.length * matrix.length];
		double[] bVector = new double[matrix.length * matrix.length * matrix.length];
		int x = 0;
		for (int i = 0; i < matrix.length; i++) {
			for (int j = 0; j < matrix.length; j++) {
				for (int k = 0; k < matrix.length; k++) {
					aVector[x] = matrix[i][j][k];
					bVector[x] = chunks[i][j][k];
					x++;
				}

			}
		}
		double score = 0.0;
		score = new Similarity().cosineSimilarity(aVector, bVector);
		return score;
	}

	/**
	 * Bildet die Cosinus-Ähnlichkeit zwischen zwei Vektoren. Als Resultat
	 * liefert die Methode ein Cosinus-Werten
	 *  
	 * @param matrix 
	 * @param chunks 
	 * @return gibt score zurück
	 */
	public double similarityMatrix(double[][] matrix, double[][] chunks) {

		double[] aVector = new double[matrix.length * matrix.length];
		double[] bVector = new double[matrix.length * matrix.length];
		int x = 0;
		for (int i = 0; i < matrix.length; i++) {
			for (int j = 0; j < matrix.length; j++) {
				aVector[x] = matrix[i][j];
				bVector[x] = chunks[i][j];
				x++;
			}
		}
		double score = 0.0;
		score = new Similarity().cosineSimilarity(aVector, bVector);
		return score;
	}

	/**
	 * Bildet die Quadratwurzel zweier Ähnlichkeitswerte und und gibt diesen Wert zurück
	 * 
	 * @param matrix 
	 * @param chunks 
	 * @param cubeMatrix 
	 * @param cubeChunks 
	 * @return gibt Quadratwurzewert zurück
	 */
	public double similarity(double[][] matrix, double[][] chunks, double[][][] cubeMatrix, double[][][] cubeChunks) {
		return Math.sqrt(similarityMatrix(matrix, chunks)*similarityCube(cubeMatrix, cubeChunks)); 
	}

}
