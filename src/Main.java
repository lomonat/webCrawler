import java.io.IOException;
import java.util.Timer;
import java.util.TimerTask;

public class Main {

    public static void main(String[] args) throws IOException {

        // read argument (word) and pass to Crawler
        String word = args[0];
        int delay = 0;
        int repeatInMs = Integer.parseInt(args[1]);
        int interval = repeatInMs * 60000;
        Timer timer = new Timer();

        // set scheduler

        timer.scheduleAtFixedRate(new TimerTask() {

            public void run() {
                try {
                    Crawler crawler = new Crawler(word);

                    // before each new crawler process the arraylist with links will be cleaned
                    crawler.linksOrigin.clear();
                    crawler.links.clear();

                    //the txt file with all links, which was found in previous iteration will be cleaned
                    //if you want to save previous results too - please delete the next line

                    crawler.searchTroughLinks();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }, delay, interval);
    }
}
