package de.unidue.langtech.teaching.pp.project.timeLine;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.logging.Level;
import java.util.logging.Logger;

public class MyFileReader {

	  private static final Logger LOG = Logger.getLogger(MyFileReader.class.getName());
	 
	  // File path: src\main\resources\articles\test.md
	  public static void main(String[] args) {
	    String fileSeparator = System.getProperty("file.separator ", "/");
	    String filePath = "desc/type" + fileSeparator + "MyType.xml";
	 
	    ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
	    InputStream stream = classLoader.getResourceAsStream(filePath);
	 
	    if (stream != null) {
	      LOG.log(Level.INFO, "File found: {0}", filePath);
	      LOG.log(Level.INFO, "File content: {0}", readFileContent(stream));
	    } else {
	      LOG.log(Level.WARNING, "File could not be found: {0}", filePath);
	    }
	  }
	 
	  private static String readFileContent(InputStream stream) {
	    StringBuilder sb = new StringBuilder();
	    String line;
	 
	    // try-with-resources
	    try (BufferedReader reader = new BufferedReader(new InputStreamReader(stream))) {
	      while ((line = reader.readLine()) != null) {
	        sb.append(line);
	        sb.append(System.getProperty("line.separator", "\r\n"));
	      }
	    } catch (IOException ex) {
	      logError(ex);
	    }
	 
	    return sb.toString();
	  }
	 
	  private static void logError(Exception ex) {
	    LOG.log(Level.WARNING, ex.getLocalizedMessage());
	  }
}
