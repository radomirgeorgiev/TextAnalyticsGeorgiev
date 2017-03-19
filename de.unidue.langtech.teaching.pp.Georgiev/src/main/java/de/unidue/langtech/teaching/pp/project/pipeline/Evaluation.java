package de.unidue.langtech.teaching.pp.project.pipeline;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.stream.Collectors;

import de.unidue.langtech.teaching.pp.project.storage.EventDetails;
import de.unidue.langtech.teaching.pp.project.storage.SimpleToken;

/**
 * Klasse Evaluation.
 */
public class Evaluation {

	private String storageFolder = "src/test/resources/storage/";

	private File directory;

	private int corpusNumber;

	private LinkedList<String> listOfString;

	private String entity;

	private String time;

	private HashMap<String, List<String>> mapOfTimeLine;

	private HashMap<String, HashMap<String, List<String>>> mapOfResultA;

	private HashMap<String, HashMap<String, List<String>>> mapOfResultB;

	private List<String> listOfEvents;

	private int entityCounter;

	private int[] truePositive;

	private int[] falsePositive;

	private int[] falseNegative;

	private String[] entityNames;

	private double[][] overallScore;

	/**
	 * Erstellt Inastanz Evaluation.
	 *
	 * @param corpusNumber_
	 * @param directory_
	 */
	public Evaluation(int corpusNumber_, String directory_) {
		this.corpusNumber = corpusNumber_;
		this.directory = new File(directory_);
	}

	/**
	 * Prints the evaluation result.
	 *
	 * @return gibt als double overallScore zurück
	 * @throws FileNotFoundException
	 *             the file not found exception
	 */
	public double[][] printEvaluationResult() throws FileNotFoundException {
		loadGoldStandard();
		// printMapB(loadMapOfResult());
		transformMapOfResult(loadMapOfResult());
		calculateEvaluationResult();
		return overallScore;
	}

	/**
	 * Rechnet die Ergebnisse
	 */
	private void calculateEvaluationResult() {

		overallScore = new double[2][3];
		for (int a = 0; a < 2; a++) {
			if (a == 0) {
				entityEvaluationWithoutDate();
			} else {
				entityEvaluationWithDate();
			}
			double precisionGlobal = 0.0;
			double recallGlobal = 0.0;
			double fmeasureGlobal = 0.0;

			// for (int i = 0; i < entityNames.length; i++) {
			// double precision = 0.0;
			// double recall = 0.0;
			// double fmeasure = 0.0;
			//
			// precision = (double) ((double) truePositive[i]
			// / ((double) truePositive[i] + (double) falsePositive[i]));
			// recall = (double) ((double) truePositive[i] / ((double)
			// truePositive[i] + (double) falseNegative[i]));
			// fmeasure = 2 * ((precision * recall) / (precision + recall));
			//
			// System.out.println("Result for " + entityNames[i] + " :");
			// System.out.println("Precision: " + precision);
			// System.out.println("Recall: " + recall);
			// System.out.println("F-measure: " + fmeasure);
			//
			// }

			for (int i = 0; i < entityNames.length; i++) {

				double testA = (double) ((double) truePositive[i]
						/ ((double) truePositive[i] + (double) falsePositive[i]));
				double testB = (double) ((double) truePositive[i]
						/ ((double) truePositive[i] + (double) falseNegative[i]));

				if (Double.NaN == testA || Double.isNaN(testA)) {
					precisionGlobal += 0.0;
				} else {
					precisionGlobal += (double) ((double) truePositive[i]
							/ ((double) truePositive[i] + (double) falsePositive[i]));
				}

				if (Double.NaN == testB || Double.isNaN(testB)) {
					recallGlobal += 0.0;
				} else {
					recallGlobal += (double) ((double) truePositive[i]
							/ ((double) truePositive[i] + (double) falseNegative[i]));
				}

			}
			fmeasureGlobal += (2 * ((precisionGlobal * recallGlobal) / (precisionGlobal + recallGlobal)));

			if (a == 0) {
				System.out.println("Result without Date for Cospus " + corpusNumber + ":");
			} else {
				System.out.println("Result with Date for Cospus " + corpusNumber + ":");
			}

			double resultPrecision = 0.0;
			double resultRecall = 0.0;
			double resultFmeasure = 0.0;
			if (entityNames.length > 0) {
				resultPrecision = (precisionGlobal / entityNames.length);
				resultRecall = (recallGlobal / entityNames.length);
				resultFmeasure = (fmeasureGlobal / entityNames.length);
			}
			System.out.println("Precision: \t" + MessageFormat.format("{0,number,#.##%}", resultPrecision));
			System.out.println("Recall: \t" + MessageFormat.format("{0,number,#.##%}", resultRecall));
			System.out.println("F-measure: \t" + MessageFormat.format("{0,number,#.##%}", resultFmeasure));

			overallScore[a][0] = resultPrecision;
			overallScore[a][1] = resultRecall;
			overallScore[a][2] = resultFmeasure;
		}

	}

	/**
	 * Rechnet die Ergebnisse mit Bezug des Datums
	 */
	private void entityEvaluationWithDate() {
		truePositive = new int[mapOfResultA.keySet().size()];
		falsePositive = new int[mapOfResultA.keySet().size()];
		falseNegative = new int[mapOfResultA.keySet().size()];
		entityNames = new String[mapOfResultA.keySet().size()];

		entityCounter = 0;

		for (String entity : mapOfResultA.keySet()) {

			int hit = 0;
			int volumeEventDetailsGoldStandard = 0;
			int volumeEventDatailsOurResults = 0;

			HashMap<String, List<String>> mapOfTimeLineA = new HashMap<String, List<String>>();
			HashMap<String, List<String>> mapOfTimeLineB = new HashMap<String, List<String>>();

			try {
				mapOfTimeLineA = new HashMap<String, List<String>>(mapOfResultA.get(entity));
				mapOfTimeLineB = new HashMap<String, List<String>>(mapOfResultB.get(entity));
			} catch (NullPointerException npe) {

			}

			for (String timeA : mapOfTimeLineA.keySet()) {
				volumeEventDetailsGoldStandard += mapOfTimeLineA.get(timeA).size();
			}
			for (String timeB : mapOfTimeLineB.keySet()) {
				volumeEventDatailsOurResults += mapOfTimeLineB.get(timeB).size();
			}

			for (String timeA : mapOfTimeLineA.keySet()) {
				for (String timeB : mapOfTimeLineB.keySet()) {
					if (timeA.equals(timeB)) {
						List<String> timeLineA = new ArrayList<String>(mapOfTimeLineA.get(timeA));
						List<String> timeLineB = new ArrayList<String>(mapOfTimeLineB.get(timeB));
						for (String tlA : timeLineA) {
							for (String tlB : timeLineB) {
								if (tlA.equals(tlB)) {
									// System.out.println(timeA+"\t"+tlA);
									hit++;
								}
							}
						}
					}
				}
			}
			entityNames[entityCounter] = entity;
			truePositive[entityCounter] = hit;
			falsePositive[entityCounter] = volumeEventDetailsGoldStandard - hit;
			falseNegative[entityCounter] = volumeEventDatailsOurResults - hit;
			entityCounter++;
		}

	}

	/**
	 * Rechnet die Ergebnisse ohne Bezug des Datums.
	 */
	private void entityEvaluationWithoutDate() {
		truePositive = new int[mapOfResultA.keySet().size()];
		falsePositive = new int[mapOfResultA.keySet().size()];
		falseNegative = new int[mapOfResultA.keySet().size()];
		entityNames = new String[mapOfResultA.keySet().size()];

		entityCounter = 0;

		for (String entity : mapOfResultA.keySet()) {
			int hit = 0;
			int volumeEventDetailsGoldStandard = 0;
			int volumeEventDatailsOurResults = 0;
			HashMap<String, List<String>> mapOfTimeLineA = new HashMap<String, List<String>>();
			HashMap<String, List<String>> mapOfTimeLineB = new HashMap<String, List<String>>();
			List<List<String>> listOfEventDetailsA = new ArrayList<List<String>>();
			List<List<String>> listOfEventDetailsB = new ArrayList<List<String>>();

			try {
				mapOfTimeLineA = new HashMap<String, List<String>>(mapOfResultA.get(entity));

				listOfEventDetailsA = new ArrayList<List<String>>(
						mapOfTimeLineA.entrySet().stream().map(Map.Entry::getValue).collect(Collectors.toList()));

			} catch (NullPointerException npe) {

			}

			try {

				mapOfTimeLineB = new HashMap<String, List<String>>(mapOfResultB.get(entity));

				listOfEventDetailsB = new ArrayList<List<String>>(
						mapOfTimeLineB.entrySet().stream().map(Map.Entry::getValue).collect(Collectors.toList()));
			} catch (NullPointerException npe) {

			}

			for (List<String> listOfLinesEDB : listOfEventDetailsB) {
				volumeEventDatailsOurResults += listOfLinesEDB.size();
			}

			for (List<String> listOfLinesEDA : listOfEventDetailsA) {
				for (String eventDetailsA : listOfLinesEDA) {
					volumeEventDetailsGoldStandard++;
					for (List<String> listOfLinesEDB : listOfEventDetailsB) {
						for (String eventDetailsB : listOfLinesEDB) {
							if (eventDetailsA.equals(eventDetailsB)) {
								// System.out.println(eventDetailsA);
								hit++;
							}
						}
					}
				}
			}

			entityNames[entityCounter] = entity;
			truePositive[entityCounter] = hit;
			falsePositive[entityCounter] = volumeEventDetailsGoldStandard - hit;
			falseNegative[entityCounter] = volumeEventDatailsOurResults - hit;
			entityCounter++;
		}
	}

	/**
	 * Liest die handgeschriebene Ergebnisse (GoldStandard) vom File
	 *
	 * @throws FileNotFoundException
	 *             the file not found exception
	 */
	private void loadGoldStandard() throws FileNotFoundException {
		mapOfResultA = new HashMap<String, HashMap<String, List<String>>>();
		for (File file : directory.listFiles()) {
			@SuppressWarnings("resource")
			Scanner s = new Scanner(file);
			listOfString = new LinkedList<String>();
			while (s.hasNextLine()) {
				listOfString.add(s.nextLine());
			}
			fill();
		}
		// System.err.println("Gold Standard:");
		// printMapA();
	}

	/**
	 * Fill.
	 */
	private void fill() {

		mapOfTimeLine = new LinkedHashMap<String, List<String>>();
		String[] linesToInterpret = new String[listOfString.size()];
		for (int i = 0; i < linesToInterpret.length; i++) {
			linesToInterpret[i] = listOfString.get(i);
		}
		entity = linesToInterpret[0];
		for (int i = 1; i < linesToInterpret.length; i++) {
			listOfEvents = new LinkedList<String>();
			String[] timeLine = linesToInterpret[i].split("\t");
			for (int j = 1; j < timeLine.length; j++) {
				if (j == 1) {
					time = timeLine[j];
				} else {
					listOfEvents.add(timeLine[j]);
				}
			}

			if (mapOfTimeLine.containsKey(time)) {
				mapOfTimeLine.get(time).addAll(listOfEvents);
			} else {
				mapOfTimeLine.put(time, listOfEvents);
			}

		}
		mapOfResultA.put(entity, mapOfTimeLine);
	}

	/**
	 * Prints the map A.
	 */
	private void printMapA() {
		for (String entity : mapOfResultA.keySet()) {
			System.out.println("Entity: " + entity);
			for (String time : mapOfResultA.get(entity).keySet()) {
				List<String> timeLine = mapOfResultA.get(entity).get(time);
				for (String ed : timeLine) {
					System.out.println(time + "\t" + ed);
				}

			}
		}
	}

	/**
	 * Prints the map B.
	 *
	 * @param map
	 *            the map
	 */
	@SuppressWarnings("unused")
	private void printMapB(HashMap<String, HashMap<String, List<EventDetails>>> map) {
		if (!map.isEmpty()) {
			for (String entity : map.keySet()) {
				System.out.println("Entity: " + entity);
				HashMap<String, List<EventDetails>> tempMap = new HashMap<String, List<EventDetails>>(map.get(entity));
				for (String time : tempMap.keySet()) {
					List<EventDetails> led = new ArrayList<EventDetails>(tempMap.get(time));
					for (EventDetails ed : led) {
						System.out.println(time + "\t" + ed.getDocID() + "-" + ed.getSentenceNumber() + "-"
								+ ed.getEvent().getFirst().getTokenText());
					}
				}
			}
		}
	}

	/**
	 * Liest die Ergebnisse von dem gewählten File
	 *
	 * @return hash map
	 */
	@SuppressWarnings("unchecked")
	private HashMap<String, HashMap<String, List<EventDetails>>> loadMapOfResult() {
		String fileName = "";
		switch (corpusNumber) {
		case 1:
			fileName = "storageResult1.ser";
			break;
		case 2:
			fileName = "storageResult2.ser";
			break;
		case 3:
			fileName = "storageResult3.ser";
			break;
		}
		HashMap<String, HashMap<String, List<EventDetails>>> tempMapOfResult = new HashMap<String, HashMap<String, List<EventDetails>>>();
		if (!isFileEmpty(fileName)) {
			try {
				FileInputStream fileIn = new FileInputStream(storageFolder + fileName);
				ObjectInputStream in = new ObjectInputStream(fileIn);
				tempMapOfResult = (HashMap<String, HashMap<String, List<EventDetails>>>) in.readObject();
				in.close();
				fileIn.close();
			} catch (IOException i) {
				i.printStackTrace();
			} catch (ClassNotFoundException c) {
				System.out.println("File not Found\n");
				c.printStackTrace();
			}
		}
		return tempMapOfResult;
	}

	/**
	 * Transform map of result.
	 *
	 * @param hash
	 *            map
	 */
	private void transformMapOfResult(HashMap<String, HashMap<String, List<EventDetails>>> map) {
		mapOfResultB = new HashMap<String, HashMap<String, List<String>>>();
		for (String entity : map.keySet()) {
			mapOfTimeLine = new HashMap<String, List<String>>();
			for (String time : map.get(entity).keySet()) {
				List<EventDetails> timeLine = new LinkedList<EventDetails>(map.get(entity).get(time));
				listOfEvents = new LinkedList<String>();
				for (EventDetails ed : timeLine) {
					LinkedList<SimpleToken> llst = new LinkedList<SimpleToken>(ed.getEvent());
					String event = "";
					for (int i = 0; i < llst.size(); i++) {
						if (i == 1) {
							event += "_" + llst.get(i).getTokenText();
						} else {
							event = llst.get(i).getTokenText();
						}
					}
					String tempString = ed.getDocID() + "-" + ed.getSentenceNumber() + "-" + event;
					listOfEvents.add(tempString);

				}
				if (mapOfTimeLine.containsKey(time)) {
					mapOfTimeLine.get(time).addAll(listOfEvents);
				} else {
					mapOfTimeLine.put(time, listOfEvents);
				}

			}
			mapOfResultB.put(entity, mapOfTimeLine);
		}
	}

	/**
	 * Prueft ob der File leer ist
	 *
	 * @param fileName
	 *            the file name
	 * @return true, wenn der File leer ist, sonst false
	 */
	private boolean isFileEmpty(String fileName) {
		File file = new File(storageFolder + fileName);
		return file.length() == 0;
	}
}
