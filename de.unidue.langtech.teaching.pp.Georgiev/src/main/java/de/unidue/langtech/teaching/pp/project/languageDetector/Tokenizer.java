package de.unidue.langtech.teaching.pp.project.languageDetector;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Tokenizer {

    private static final String LETTER = "A-Za-zÖÄÜÎÊÔÛÂÀÒÙÈÌÁÓÚÉÍ‗אבגדהוזחטיךכלםמןנסעףפץצרשתûü'";
    private static final String WHITESPACE = "\\n\\s\\t"; // \\s
    private static final String TOKENREGEX = "[" + LETTER + "]+";
    private static final String SEPARATORREGEX = "[" + WHITESPACE + "]*";
    private static List<String> tokens;

    public static List<String> tokenize(String input) throws UnsupportedEncodingException {
    
        tokens = new ArrayList<String>();

        String tokenRegex = "(" + TOKENREGEX + ")";
        String delimiterRegex = "(" + SEPARATORREGEX + ")";

   
        Pattern p = Pattern.compile(tokenRegex + delimiterRegex);
        Matcher m = p.matcher(input);

        while (m.find()) {
            tokens.add(m.group(1).toLowerCase());
        }
        return tokens;
    }


}
