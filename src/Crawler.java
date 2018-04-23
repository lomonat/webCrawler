/**
 * Created by Lomova Natalia.
 */
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.HashSet;

public class Crawler {
    public String word;
    private static final int MAX_DEPTH = 2;
    public static HashSet<String> links = new HashSet<String>();
    Reader reader = new Reader();
    Writer writer = new Writer();

    public Crawler(String word) throws IOException {
        this.word = word;
    }

    public void searchTroughLinks () throws IOException {
        String fs = System.getProperty("file.separator");
        String[] links = new String[0];
        try {
            links = reader.readLines("files"+ fs + "links.txt");
            System.out.println(links.toString());
        } catch (IOException e) {
            System.out.println(e);
        }
      //  for (String o : links) {
        for (int i=0; i< links.length; i++) {
              //  getPageLinks(o, 0);
                getPageLinks(links[i], 0);

                System.out.println ("Searching...");

        }
      //  writer.cleanUp();
        System.out.println("The end");
    }

    public void getPageLinks(String URL, int depth)  {
        //4. Check if you have already crawled the URLs
        if ((!links.contains(URL))  && (depth < MAX_DEPTH) && URL != null && URL.length() != 0) {
            try {
                //4. (i) If not add it to the index
                Document document = Jsoup.connect(URL).get();
                if (links.add(URL)) {
                    if(searchForWord(this.word, document)){
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

    // check if text of document contains word
    public boolean searchForWord(String searchWord, Document text)
    {
        boolean flag = false;
        String bodyText = text.body().text();
        if (bodyText != null) {
            flag =  bodyText.toLowerCase().contains(searchWord.toLowerCase());
        }
        return flag;
    }
}
