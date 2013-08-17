package gr.theofilis.parsewebsite;

import java.io.File;
import java.io.IOException;
import java.io.PrintStream;
import java.util.Map.Entry;
import java.util.Scanner;
import java.util.TreeMap;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

public class App {

    public static void main(String[] args) {
        try {
            TreeMap<String, Integer> dictionary = new TreeMap<String, Integer>();
                    
            for (int i = 0; i < args.length - 1; i++) {
                File input = new File(args[i]);
                Document doc = Jsoup.parse(input, "Cp1253", "http://example.com/");

                Element body = doc.body();

                String data = body.text();

                Scanner scan = new Scanner(data);

                PrintStream out = new PrintStream(new File(args[args.length - 1]), "Cp1253");

                String word;

                while (scan.hasNext()) {
                    word = scan.next().trim().replaceAll("[,.:·\"';!’«»—]", "");
                    word = word.replaceAll("[0-9]", "");
                    word = word.replaceAll(" ", "");
                    word = word.toLowerCase();
                    word = replaceAccentuation(word);

                    if (!word.isEmpty()) {

                        if (dictionary.containsKey(word)) {
                            dictionary.put(word, dictionary.get(word) + 1);
                        } else {
                            dictionary.put(word, 1);
                        }
                    }
                }

                out.println("word, #word");
                for (Entry entry : dictionary.entrySet()) {
                   
                    out.println(entry.getKey() + "," + entry.getValue());
                }
            }

        } catch (IOException ex) {
            Logger.getLogger(App.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private static String replaceAccentuation(String word) {
        String accentuation[] = {"ά", "έ", "ή", "ί", "ό", "ύ", "ώ", "Ά", "Έ", "Ή", "Ί", "Ό", "Ύ", "Ώ"};
        String replaceStr[] = {"α", "ε", "η", "ι", "ο", "υ", "ω", "Α", "Ε", "Η", "Ι", "Ο", "Υ", "Ω"};

        for (int i = 0; i < accentuation.length; i++) {
            word = word.replaceAll(accentuation[i], replaceStr[i]);
        }

        return word;
    }
}
