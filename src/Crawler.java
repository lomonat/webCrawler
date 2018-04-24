/**
 * Created by Lomova Natalia.
 */
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.io.File;
import java.util.Scanner;
import java.util.List;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Crawler {
    public String word;
    private static final int MAX_DEPTH = 2;
    List<String> linksOrigin = new ArrayList<>();
    List<String> links = new ArrayList<>();
    Writer writer = new Writer();

    public Crawler(String word) throws IOException {
        this.word = word;
    }

    public void searchTroughLinks () throws IOException {
        System.out.println("Begin the process");
        writer.writeProcessTofile("Starting the process of crawling");

        String fs = System.getProperty("file.separator");
        try {
            Scanner s = new Scanner(new File("files"+ fs + "links.txt"));
            while (s.hasNext()){
                linksOrigin.add(s.next());
            }
            s.close();
            System.out.println(linksOrigin.toString());
        } catch (IOException e) {
            System.out.println(e);
        }
        for(Object o : linksOrigin) {
            System.out.println ("Searching..." + o);
            getPageLinks((String) o, 0);
        }
        writer.writeProcessTofile("The end");
        System.out.println("The end");
        writer.cleanUp();
    }

    public void getPageLinks(String URL, int depth)  {
        //4. Check if you have already crawled the URLs

        if ((!links.contains(URL))  && (depth < MAX_DEPTH) && URL != null && URL.length() != 0) {
            try {
                //4. (i) If not add it to the index
                Document document = Jsoup.connect(URL).get();
                if (links.add(URL)) {
                    if(searchForWord(document, this.word)){
                        writer.writeTofile(this.word, URL);
                    };
                }

                //3. Parse the HTML to extract links to other URLs
                Elements linksOnPage = document.select("a[href]");
                depth++;

                //5. For each extracted URL... go back to Step 4.
                for (Element page : linksOnPage) {
                    getPageLinks(page.attr("abs:href"), depth);
                }
            } catch (IOException e) {
                System.err.println("For '" + URL + "': " + e.getMessage());
            }
        }
    }

    // check if text of document contains word and set the flag
    public boolean searchForWord(Document text, String searchWord)
    {
        boolean flag = false;
        String bodyText = text.body().text();
        if (bodyText != null) {
           // flag =  bodyText.toLowerCase().contains(searchWord.toLowerCase());
            flag = isContain( bodyText.toLowerCase(), searchWord.toLowerCase());
        }
        return flag;
    }
    // Please note, that with help of Regex and word boundary it will be searched for exact word
    //it means if your search for "elephants", all results with "elephant" won' be shown (plural)
    //if you want to find all form of words and words, which are containing subItem, please comment out the line in the method
    //searchForWord and comment the next line after (invert lines)
    private static boolean isContain(String source, String subItem){
        String pattern = "\\b"+subItem+"\\b";
        Pattern p=Pattern.compile(pattern);
        Matcher m=p.matcher(source);
        return m.find();
    }
}
