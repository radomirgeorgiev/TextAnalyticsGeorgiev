package de.unidue.langtech.teaching.pp.example.tests;

import java.io.File;
import java.io.IOException;
import java.util.Scanner;

public class Test {

	public static void main(String args[]) {

		String fileName = "src/test/resources/xml/1173_Internal_emails_expose_Boeing-Air_Force_contract_discussions.txt.xml";

		try (Scanner scanner = new Scanner(new File(fileName))) {

			while (scanner.hasNext()){
				System.out.println(scanner.nextLine());
			}

		} catch (IOException e) {
			e.printStackTrace();
		}

	}

}