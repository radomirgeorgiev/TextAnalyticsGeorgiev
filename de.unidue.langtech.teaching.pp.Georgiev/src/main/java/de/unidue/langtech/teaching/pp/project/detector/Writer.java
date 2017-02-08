package de.unidue.langtech.teaching.pp.project.detector;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

import org.apache.commons.io.FileUtils;
import org.apache.uima.UimaContext;
import org.apache.uima.analysis_engine.AnalysisEngineProcessException;
import org.apache.uima.cas.CAS;
import org.apache.uima.fit.component.CasConsumer_ImplBase;
import org.apache.uima.fit.descriptor.ConfigurationParameter;
import org.apache.uima.resource.ResourceInitializationException;

public class Writer extends CasConsumer_ImplBase {

	/**
	 * Output file
	 */
	public static final String PARAM_INPUT_FILE = "OutputFile";
	@ConfigurationParameter(name = PARAM_INPUT_FILE, mandatory = true)
	private File outputFile;

	private List<String> lines;

	private String myPlace = "src/test/resources/evoluation/lang/";
	private String[] myStringArray;
	FileWriter fw;

	/*
	 * initializes the writer
	 */
	@Override
	public void initialize(UimaContext context) throws ResourceInitializationException {
		super.initialize(context);

		try {
			FileUtils.writeLines(outputFile, lines);

		} catch (IOException e) {
			throw new ResourceInitializationException(e);
		}
	}

	@Override
	public void process(CAS arg0) throws AnalysisEngineProcessException {
		// TODO Auto-generated method stub

	}

	public Writer(String[] myStringArray_, String myString_) throws IOException {
		try {
			fw = new FileWriter(myPlace + myString_);
			this.myStringArray = myStringArray_;
			writeInFile(myStringArray);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public FileWriter getFw() {
		return fw;
	}

	public void writeInFile(String[] myStringArray) throws IOException {

		try {
			for (String value : myStringArray) {
				value += System.lineSeparator();
				fw.write(value);
			}

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			fw.flush();
			fw.close();
		}

	}

}
