package de.unidue.langtech.teaching.pp.project.timeLine;

import java.io.File;
import java.io.IOException;
import java.io.StringReader;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Locale;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import edu.stanford.nlp.ling.CoreLabel;
import edu.stanford.nlp.parser.lexparser.LexicalizedParser;
import edu.stanford.nlp.process.CoreLabelTokenFactory;
import edu.stanford.nlp.process.PTBTokenizer;
import edu.stanford.nlp.process.TokenizerFactory;
import edu.stanford.nlp.trees.EnglishGrammaticalRelations;
import edu.stanford.nlp.trees.GrammaticalStructure;
import edu.stanford.nlp.trees.PennTreebankLanguagePack;
import edu.stanford.nlp.trees.Tree;
import edu.stanford.nlp.trees.TreeGraphNode;
import edu.stanford.nlp.trees.TreebankLanguagePack;
import edu.stanford.nlp.trees.TypedDependency;

public class Testing {

	private static String[] date1 = { "2. Januar 2017", "05.01.2016", "2. January 2017", "Oct 06, 2016" };
	private static List<DateTimeFormatter> knownPatterns;
	private static String docText;
	private static String docCreationDate;
	private static List<String> textToCreate;
	

	public static void main(String[] args) throws ClassNotFoundException {
		// TODO Auto-generated method stub

		knownPatterns = new ArrayList<DateTimeFormatter>();

		knownPatterns.add(DateTimeFormatter.ofPattern("d. MMMM yyyy", new Locale("EN")));
		knownPatterns.add(DateTimeFormatter.ofPattern("MMM dd, yyyy", Locale.ENGLISH));
		knownPatterns.add(DateTimeFormatter.ofPattern("d. MMMM yyyy", Locale.GERMAN));
		knownPatterns.add(DateTimeFormatter.ofPattern("dd.MM.yyyy", Locale.GERMAN));

		// Locale.setDefault(new Locale("XR"));
		// System.out.println(Locale.getDefault().toString());
		for (String s : date1) {
			System.out.printf("%s%n", myDate(s));

		}

		// Initialize Standford Tagger
		String test = "In the released emails, Air Force officials responsible for the awarding of contracts appear biased against Boing's main competitor, Airbus and its CEO Ralph Crosby. ";
//		TreebankLanguagePack tlp = new PennTreebankLanguagePack();
//	    GrammaticalStructureFactory gsf = tlp.grammaticalStructureFactory();
		LexicalizedParser lp = LexicalizedParser.loadModel("src/test/resources/tagger/englishPCFG.ser.gz");
		TokenizerFactory<CoreLabel> tokenizerFactory = PTBTokenizer.factory( new CoreLabelTokenFactory(), "");
		List<CoreLabel> wordList = tokenizerFactory.getTokenizer(new StringReader(test)).tokenize();
		Tree tree = lp.apply(wordList);
		tree.pennPrint();
		TreebankLanguagePack languagePack = new PennTreebankLanguagePack();
		GrammaticalStructure structure = languagePack.grammaticalStructureFactory().newGrammaticalStructure(tree);
		Collection<TypedDependency> typedDependencies = structure.typedDependenciesCollapsed();
		for(TypedDependency td : typedDependencies) {
			
			 if(td.reln().getShortName().equals("nsubj")) {
				 System.out.println(td.gov());
				 System.out.println(td.dep(       ));
			    System.out.println(td);
			  }
		}
		try {
			System.in.read();
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		
		try {
			
			textToCreate = new ArrayList<String>();
			docCreationDate = "";

			File file = new File("src/test/resources/xml/1173_Internal_emails_expose_Boeing-Air_Force_contract_discussions.txt.xml");

			DocumentBuilder dBuilder = DocumentBuilderFactory.newInstance().newDocumentBuilder();

			Document doc = dBuilder.parse(file);

			System.out.println("Root element :" + doc.getDocumentElement().getNodeName());

			if (doc.hasChildNodes()) {

				printNote(doc.getChildNodes());

			}

		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		

		
		docText = "";
		for(int i=0; i<textToCreate.size();i++){
			docText +=textToCreate.get(i);
			docText +=" ";
			
		}
		
		System.out.println(docText);
		System.out.println(docCreationDate);


	}

	private static void printNote(NodeList nodeList) {

		for (int count = 0; count < nodeList.getLength(); count++) {

			Node tempNode = nodeList.item(count);

			// make sure it's element node.
			if (tempNode.getNodeType() == Node.ELEMENT_NODE) {

				// get node name and value
				System.out.println("\nNode Name =" + tempNode.getNodeName() + " [OPEN]");
				System.out.println("Node Value =" + tempNode.getTextContent());
				if(tempNode.getNodeName().equals("token")&&tempNode.getTextContent()!=null){
				textToCreate.add(tempNode.getTextContent());
				} 
				if (tempNode.hasAttributes()) {

					// get attributes names and values
					NamedNodeMap nodeMap = tempNode.getAttributes();

					for (int i = 0; i < nodeMap.getLength(); i++) {

						Node node = nodeMap.item(i);
						System.out.println("attr name : " + node.getNodeName());
						System.out.println("attr value : " + node.getNodeValue());
						if(tempNode.getNodeName().equals("TIMEX3")){
							if(node.getNodeName().equals("value")){
								docCreationDate += node.getNodeValue();
							}
						}
					}

				}

				if (tempNode.hasChildNodes()) {

					// loop again if has child nodes
					printNote(tempNode.getChildNodes());

				}

				System.out.println("Node Name =" + tempNode.getNodeName() + " [CLOSE]");

			}

		}
		

	}

	public static LocalDate myDate(String s) {
		for (DateTimeFormatter sl : knownPatterns) {
			try {
				return LocalDate.parse(s, sl);
			} catch (Exception pe) {

			}
		}
		return null;
	}

}
