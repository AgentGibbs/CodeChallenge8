import java.net.MalformedURLException;
import java.net.URL;
import java.util.*;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

/**
 * Created by Christian Gibbs on 9/29/2016.
 */
public class CrawlerProgram {


    private static int maxThreadCount = 5;
    private static HashSet<String> crawledList = new HashSet<String>();
    private static Queue<String> toCrawlList = new LinkedList<String>();
    private static String domainName;

    public static void crawlSite(String initialUrl) throws Exception {
        extractDomainName(initialUrl);

        ConcurrentLinkedQueue<Future<List<String>>> futures = new ConcurrentLinkedQueue<Future<List<String>>>();
        ExecutorService threadPool = Executors.newFixedThreadPool(maxThreadCount);

        Crawl crawl = new Crawl(initialUrl, domainName);
        crawledList.add(initialUrl);
        futures.add(threadPool.submit(crawl));
        while (!futures.isEmpty()) {
            List<Future<List<String>>> completedFutures = new ArrayList<Future<List<String>>>();
            for (Future<List<String>> future : futures) {
                if (future.isDone()) {
                    List<String> newUrls = future.get();
                   // System.out.println("Searching for links...");
                    for (String newUrl : newUrls) {
                        if (UrlIsOkay(newUrl)) {
                            //System.out.println("New URL found: " + newUrl);
                            toCrawlList.add(newUrl);
                        }
                    }
                    completedFutures.add(future);
                }
            }
            System.out.println("Visited URLs: " + crawledList.size());
            System.out.println("URLs to visit: " + futures.size());
            futures.removeAll(completedFutures);
            while (!toCrawlList.isEmpty()) {
                String urlToCrawl = toCrawlList.poll();
                futures.add(threadPool.submit(new Crawl(urlToCrawl, domainName)));
                crawledList.add(urlToCrawl);
            }
            Thread.sleep(300);
        }
       // fileBuilder.buildDictionary();
    }

    private static boolean UrlIsOkay(String href) {
        boolean isOkay = false;
        if (!toCrawlList.contains(href)
                && !crawledList.contains(href)
                && href.contains(domainName)==true
                && !href.contains("@@")
                && !href.contains("&")
                && !href.contains("?")
                && !href.contains("..")
                && !href.contains(",")
                && !href.contains("mobile"))  {
            isOkay = true;
        }
        return isOkay;
    }

    private static void extractDomainName(String initialURL) {
        try {
            URL url = new URL(initialURL);
            domainName = url.getHost();
            System.out.println("Domain name " +domainName);
        } catch (MalformedURLException e) {
            System.out.println(initialURL + " is not a valid web address.");

        }
    }
}
