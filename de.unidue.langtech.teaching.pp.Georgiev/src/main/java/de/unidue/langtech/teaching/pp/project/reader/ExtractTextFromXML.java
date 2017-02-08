package de.unidue.langtech.teaching.pp.project.reader;

import java.io.IOException;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.apache.uima.analysis_engine.AnalysisEngineProcessException;
import org.apache.uima.fit.component.JCasAnnotator_ImplBase;
import org.apache.uima.fit.util.JCasUtil;
import org.apache.uima.jcas.JCas;
import org.w3c.dom.Document;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import de.tudarmstadt.ukp.dkpro.core.api.metadata.type.DocumentMetaData;
import de.unidue.langtech.teaching.pp.project.type.RawTextData;
import de.unidue.langtech.teaching.pp.project.type.RawXMLData;

public class ExtractTextFromXML extends JCasAnnotator_ImplBase {

	private static List<String> textToCreate;
	private static String docText, tempSentence;
	private static String docCreationDate;
	private static String docID, docURL, docTitle;
	private static int sentenceNumber;
	private static String docDate;
	private Document doc;

	@Override
	public void process(JCas jcas) throws AnalysisEngineProcessException {

//        //add empty string as language value for detected language
//        DetectedLanguage detectedLanguage = new DetectedLanguage(jcas);
//        detectedLanguage.setSuggestion(new StringArray(jcas, 0));
//        detectedLanguage.addToIndexes();
		
		RawXMLData rXMLdata = JCasUtil.selectSingle(jcas, RawXMLData.class);
		String str = rXMLdata.getRawXMLData();
		sentenceNumber = 0;
		try {
			doc = DocumentBuilderFactory.newInstance().newDocumentBuilder()
					.parse(new InputSource(new StringReader(str)));
		} catch (SAXException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ParserConfigurationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		extractRawTextFromXML(doc);
		writeIn(jcas);

	}

	public static void writeIn(JCas jCas) {
				
		RawTextData raw = new RawTextData(jCas);
		raw.setDocumentRawText(docText);
		raw.setDocumentCreationDate(docCreationDate);
		raw.setDocumentID(Integer.parseInt(docID));
		raw.addToIndexes();	
		DocumentMetaData docMeta = DocumentMetaData.create(jCas);
		docMeta.setDocumentId(docID);
		docMeta.setDocumentTitle(docTitle);
		docMeta.setDocumentUri(docURL);
		docMeta.addToIndexes();
		System.out.println("DocID : "+ docMeta.getDocumentId() + "\nDoc title: " + docMeta.getDocumentTitle() + "\nDoc URL: "+docMeta.getDocumentUri());
		String temp = docTitle + "\n" + docDate + "\n" + docText;
		System.out.println(temp);
		jCas.setDocumentText(temp);

	}

	public static void extractRawTextFromXML(Document doc) {
		try {
			docTitle ="";
			docText = "";
			tempSentence = "";
			docDate="";
			textToCreate = new ArrayList<String>();
			if (doc.hasChildNodes()) {
				extractData(doc.getChildNodes());
			}
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		docText += tempSentence;
	}

	static void extractData(NodeList nodeList) {

		for (int count = 0; count < nodeList.getLength(); count++) {

			Node tempNode = nodeList.item(count);

			// make sure it's element node.
			if (tempNode.getNodeType() == Node.ELEMENT_NODE) {

				
				if (tempNode.getNodeName().equals("token") && tempNode.getTextContent() != null) {
					textToCreate.add(tempNode.getTextContent());
				}
				if (tempNode.hasAttributes()) {

					// get attributes names and values
					NamedNodeMap nodeMap = tempNode.getAttributes();

					for (int i = 0; i < nodeMap.getLength(); i++) {

						Node node = nodeMap.item(i);

						if (tempNode.getNodeName().equals("TIMEX3")) {
							if (node.getNodeName().equals("value")) {
								System.err.println("Date: " + node.getNodeValue());
								docCreationDate = node.getNodeValue();
							}
						}
						if (tempNode.getNodeName().equals("Document")) {
							if (node.getNodeName().equals("doc_id")) {
								System.err.println("DocID: " + node.getNodeValue());
								docID = node.getNodeValue();
							}else if(node.getNodeName().equals("src_url")){
								System.err.println("Doc URL: " + node.getNodeValue());
								docURL = node.getNodeValue();
							}

						}
						if (tempNode.getNodeName().equals("token")) {
							if (node.getNodeName().equals("sentence")) {
								if (sentenceNumber == Integer.parseInt(node.getNodeValue())) {
									tempSentence += tempNode.getTextContent() + " ";
								} else {
									if(sentenceNumber<2){
										if(sentenceNumber==0){
											docTitle += tempSentence+".";
											}else{
												docDate += tempSentence+".";
											}
										tempSentence = "";
										sentenceNumber++;
										tempSentence += tempNode.getTextContent() + " ";
									} else {
									docText += tempSentence + "\n";
									tempSentence = "";
									sentenceNumber++;
									tempSentence += tempNode.getTextContent() + " ";
									}
								}
							}
						}

					}

				}

				if (tempNode.hasChildNodes()) {

					// loop again if has child nodes
					extractData(tempNode.getChildNodes());

				}

			}

		}

	}

}
