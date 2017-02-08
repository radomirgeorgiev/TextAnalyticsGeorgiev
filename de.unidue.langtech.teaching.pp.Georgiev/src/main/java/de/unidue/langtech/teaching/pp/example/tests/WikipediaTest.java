package de.unidue.langtech.teaching.pp.example.tests;

import java.io.IOException;
import java.net.URL;
import java.net.URLConnection;
import java.util.List;
import java.util.Scanner;

/*******************************************************************************
 * Copyright 2016
 * Ubiquitous Knowledge Processing (UKP) Lab
 * Technische Universität Darmstadt
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *******************************************************************************/
import de.tudarmstadt.ukp.wikipedia.api.WikiConstants;
import de.tudarmstadt.ukp.wikipedia.api.exception.WikiApiException;
import de.tudarmstadt.ukp.wikipedia.parser.Paragraph;
import de.tudarmstadt.ukp.wikipedia.parser.ParsedPage;
import de.tudarmstadt.ukp.wikipedia.parser.Template;
import de.tudarmstadt.ukp.wikipedia.parser.mediawiki.FlushTemplates;
import de.tudarmstadt.ukp.wikipedia.parser.mediawiki.MediaWikiParser;
import de.tudarmstadt.ukp.wikipedia.parser.mediawiki.MediaWikiParserFactory;

/**
 * Tutorial 2
 *
 * A page provides a number of informative methods.
 *
 *
 */
public class WikipediaTest implements WikiConstants {

	private static Scanner scanner;

	public static void main(String[] args) throws WikiApiException, IOException {

		String content = null;
		URLConnection connection = null;
		try {
			connection = new URL("https://en.wikipedia.org/w/index.php?action=raw&title=777-200LR").openConnection();
			scanner = new Scanner(connection.getInputStream());
			scanner.useDelimiter("\\Z");
			content = scanner.next();
		} catch (Exception ex) {
			ex.printStackTrace();
		}

		MediaWikiParserFactory pf = new MediaWikiParserFactory();
	//	pf.setTemplateParserClass(FlushTemplates.class);
		MediaWikiParser parser = pf.createParser();
		ParsedPage pp = parser.parse(content);

		System.out.println(pp.getText());
		
		List<Paragraph> templateList = pp.getParagraphs();
		for(Paragraph pa : templateList){
			System.out.println(pa.getText());
		}


	}

}
