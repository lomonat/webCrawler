import java.io.IOException;
import java.util.Timer;
import java.util.TimerTask;

public class Main {

    public static void main(String[] args) throws IOException {
        // read argument (word) and pass to Crawler
        int delay = 0;
        int repeatInMs = Integer.parseInt(args[1]);
        int interval = repeatInMs * 60000;
        Timer timer = new Timer();
        String word = args[0];
        timer.scheduleAtFixedRate(new TimerTask() {

            public void run() {
                try {
                    Crawler crawler = new Crawler(word);
                    System.out.println("Begin the process");
                    crawler.searchTroughLinks();

                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }, delay, interval);
    }
}
