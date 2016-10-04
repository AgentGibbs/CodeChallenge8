package HTMLUtilities;

import org.jsoup.nodes.*;
import org.jsoup.select.Elements;

import java.util.*;

public class HtmlImageScraper {

    public Document doc;
    private ArrayList<String> pageLinks;
    private String domainName;

    public HtmlImageScraper(Document docToScrape, String domainNameToScrape) {
        // System.out.println("Scraping page");
        doc = docToScrape;
        domainName = domainNameToScrape;
    }


    public String[] scrapePageTextOld() {
        String[] values = new String[1];
        if (doc != null) {
            values = doc.body().text().replaceAll("\\p{P}", "").toUpperCase().split("\\s+");
        }
        return values;
    }


    public ArrayList<String> scrapeImages() {
        ArrayList<String> images = new ArrayList<String>();

        Elements imageTags = doc.select("img");
        for (Element image : imageTags
                ) {
            String src = image.attr("src").toLowerCase();
            images.add(src);

        }

        return images;

    }

    public String[] scrapePageText() {

        String[] wordList = new String[1];
        if (doc != null) {
            wordList = doc.body().text().replaceAll("\\p{P}", "").toUpperCase().split("\\s+");
        }
        return wordList;
    }


    public ArrayList<String> getPageLinks() {
        populatePageLinks();
        return pageLinks;
    }

    private void populatePageLinks() {
        Elements anchorTags = doc.select("a");
        if (pageLinks == null) {
            pageLinks = new ArrayList<String>();
        }
        for (Element link : anchorTags
                ) {
            String href = link.attr("href").toLowerCase();

            pageLinks.add(href);

        }

    }
}