package de.unidue.langtech.teaching.pp.project.entityDetector;

import java.io.IOException;
import java.net.URL;
import java.net.URLConnection;
import java.util.List;
import java.util.Scanner;

import de.tudarmstadt.ukp.wikipedia.parser.Paragraph;
import de.tudarmstadt.ukp.wikipedia.parser.ParsedPage;
import de.tudarmstadt.ukp.wikipedia.parser.mediawiki.FlushTemplates;
import de.tudarmstadt.ukp.wikipedia.parser.mediawiki.MediaWikiParser;
import de.tudarmstadt.ukp.wikipedia.parser.mediawiki.MediaWikiParserFactory;

public class ExtractTextFromWikipedia {

	private Scanner scanner;
	private String pageContent;
	private String text ;
	private URLConnection connectionToWikipedia = null;
	private String url;


	public String getTextFromWikpedia(String language, String page) {
		openConnection(language, page);
		MediaWikiParserFactory pf = new MediaWikiParserFactory();
		//pf.setTemplateParserClass(FlushTemplates.class);
		MediaWikiParser parser = pf.createParser();
		ParsedPage pp = parser.parse(pageContent);
//		List<Paragraph> templateList = pp.getParagraphs();
		text="";
		if(pp.getText()!=null)
		text = pp.getText();
//		for (Paragraph pa : templateList) {
//			text += pa.getText()+"\r\n";
//		}
		return text;
	}

	private void openConnection(String language, String page) {

		
		String temp = page.replace(" ", "%20");
		url = "https://" + language.toLowerCase() + ".wikipedia.org/w/index.php?action=raw&title=" + temp;
		try {
			connectionToWikipedia = new URL(url).openConnection();
			scanner = new Scanner(connectionToWikipedia.getInputStream());
			scanner.useDelimiter("\\Z");
			pageContent = scanner.next();
//			System.out.println("Prepair Token Map for: "+page+"\n");
			} catch (IOException e) {
			// TODO Auto-generated catch block
			pageContent = page;
		}

	}
}
