package de.unidue.langtech.teaching.pp.project.pipeline;

import java.io.FileNotFoundException;
import java.text.MessageFormat;

public class PrintEvaluationResult {

	private double[][] overallScore;
	private String inputPartA = "data/corpus_";
	private String inputPartB = "/TimeLines_Gold_Standard";

	// Methode, die das Ergebnis nach der Evaluierung ausdruckt
	public void printResult() throws FileNotFoundException {
		overallScore = new double[2][3];
		for (int i = 1; i < 4; i++) {
			String inputFolder = inputPartA + i + inputPartB;
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
					System.out.println("F-measure: \t" + percentResult);
				}
			}

		}
	}

	// Berechnet Overall-Result nach der Evaluierung
	private void calculateResult(double[][] d) {
		for (int i = 0; i < overallScore.length; i++) {
			for (int j = 0; j < overallScore[i].length; j++) {
				overallScore[i][j] += d[i][j];
			}
		}
	}
}
