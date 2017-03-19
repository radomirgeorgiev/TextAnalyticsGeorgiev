package de.unidue.langtech.teaching.pp.project.pipeline;

import java.io.FileNotFoundException;
import java.text.MessageFormat;

import de.unidue.langtech.teaching.pp.project.pipeline.Evaluation;

/**
 * Klasse TestEvaluation.
 */
public class TestEvaluation {

	private static double[][] overallScore;

	/**
	 * The main method.
	 *
	 * @param args
	 * @throws FileNotFoundException
	 *             the file not found exception
	 */
	public static void main(String[] args) throws FileNotFoundException {

		overallScore = new double[2][3];
		for (int i = 3; i < 4; i++) {
			String inputFolder = "";
			switch (i) {
			case 1:
				inputFolder = "data/corpus_1/TimeLines_Gold_Standard";
				break;
			case 2:
				inputFolder = "data/corpus_2/TimeLines_Gold_Standard";
				break;
			case 3:
				inputFolder = "data/corpus_3/TimeLines_Gold_Standard";
				break;
			}
			Evaluation ev = new Evaluation(i, inputFolder);
			calculateResult(ev.printEvaluationResult());
		}
		for (int i = 0; i < 2; i++) {
			if (i == 0) {
				System.out.println("Overall score without time:");
			} else {
				System.out.println("Overall score with time:");
			}
			for (int j = 0; j < 3; j++) {
				double doubleResult = overallScore[i][j] / overallScore[i].length;
				String percentResult = MessageFormat.format("{0,number,#.##%}", doubleResult);

				if (j == 0) {
					System.out.println("Precision: \t" + percentResult);
				} else if (j == 1) {
					System.out.println("Recall: \t" + percentResult);
				} else {
					System.out.println("F-messure: \t" + percentResult);
				}
			}

		}

	}

	/**
	 * Berechnet das Ergebniss
	 *
	 * @param d
	 *            the d
	 */
	public static void calculateResult(double[][] d) {
		for (int i = 0; i < overallScore.length; i++) {
			for (int j = 0; j < overallScore[i].length; j++) {
				overallScore[i][j] += d[i][j];
			}
		}
	}
}
