package de.unidue.langtech.teaching.pp.project.pipeline;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.HashMap;
import java.util.List;

import de.unidue.langtech.teaching.pp.project.storage.EventDetails;

/**
 * Klasse PrintResult.
 */
public class PrintResult {

	private static String storageFolder = "src/test/resources/storage/";

	private static HashMap<String, HashMap<String, List<EventDetails>>> mapOfResult;

	/**
	 * The main method.
	 *
	 * @param args
	 */
	public static void main(String[] args) {

		readResult();

		for (String keyEntity : mapOfResult.keySet()) {
			System.out.println("Entity: " + keyEntity);
			HashMap<String, List<EventDetails>> tempMap = mapOfResult.get(keyEntity);
			int i = 0;
			for (String keyTimeLine : tempMap.keySet()) {
				List<EventDetails> listOfEventDetails = tempMap.get(keyTimeLine);
				for (EventDetails ed : listOfEventDetails) {
					if (ed.getEvent().size() > 1) {
						System.out.println(i + "\t" + keyTimeLine + "\t" + ed.getDocID() + "-" + ed.getSentenceNumber()
								+ "-" + ed.getEvent().getFirst().getTokenText() + "_"
								+ ed.getEvent().getLast().getTokenText());
					} else {
						System.out.println(i + "\t" + keyTimeLine + "\t" + ed.getDocID() + "-" + ed.getSentenceNumber()
								+ "-" + ed.getEvent().getFirst().getTokenText());
					}

				}
				i++;
			}
		}

	}

	/**
	 * Liest Hashmap mapOfResult vom File
	 */
	@SuppressWarnings("unchecked")
	public static void readResult() {
		String fileName = "storageResult.ser";
		if (!isFileEmpty(fileName)) {
			try {
				FileInputStream fileIn = new FileInputStream(storageFolder + fileName);
				ObjectInputStream in = new ObjectInputStream(fileIn);
				mapOfResult = (HashMap<String, HashMap<String, List<EventDetails>>>) in.readObject();
				in.close();
				fileIn.close();
			} catch (IOException i) {
				i.printStackTrace();
			} catch (ClassNotFoundException c) {
				System.out.println("File not Found\n");
				c.printStackTrace();
			}
		}
	}

	/**
	 * Prueft ob der File leer ist
	 *
	 * @param fileName
	 * @return true, wenn der File leer ist, sonst false
	 */
	private static boolean isFileEmpty(String fileName) {
		File file = new File(storageFolder + fileName);
		return file.length() == 0;
	}
}
