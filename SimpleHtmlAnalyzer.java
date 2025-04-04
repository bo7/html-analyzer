import java.io.*;
import java.net.*;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import java.util.*;

/**
 * A simple HTML analyzer that uses jsoup to parse and analyze HTML pages.
 * This is a standalone version that doesn't depend on the other classes.
 */
public class SimpleHtmlAnalyzer {
    
    public static void main(String[] args) {
        if (args.length == 0) {
            System.out.println("Usage: java -cp lib/jsoup.jar:. SimpleHtmlAnalyzer <url>");
            System.exit(1);
        }
        
        String url = args[0];
        try {
            // Parse the HTML using jsoup
            Document doc = Jsoup.connect(url).get();
            
            // Get basic information
            int tagCount = doc.getAllElements().size();
            int wordCount = countWords(doc.text());
            int byteSize = doc.html().length();
            
            // Get links
            Elements links = doc.select("a[href]");
            Set<String> uniqueLinks = new HashSet<>();
            for (Element link : links) {
                String href = link.attr("abs:href");
                if (!href.isEmpty()) {
                    uniqueLinks.add(href);
                }
            }
            
            // Get images
            Elements images = doc.select("img[src]");
            Set<String> uniqueImages = new HashSet<>();
            for (Element image : images) {
                String src = image.attr("abs:src");
                if (!src.isEmpty()) {
                    uniqueImages.add(src);
                }
            }
            
            // Print results
            System.out.println("=== HTML Analysis for " + url + " ===");
            System.out.println("Title: " + doc.title());
            System.out.println("Number of tags: " + tagCount);
            System.out.println("Number of words: " + wordCount);
            System.out.println("Size in bytes: " + byteSize);
            System.out.println("Number of links: " + uniqueLinks.size());
            System.out.println("Number of images: " + uniqueImages.size());
            
            // Print links
            System.out.println("\n=== Links ===");
            int linkCount = 0;
            for (String link : uniqueLinks) {
                System.out.println((++linkCount) + ". " + link);
                if (linkCount >= 10) {
                    System.out.println("... and " + (uniqueLinks.size() - 10) + " more links");
                    break;
                }
            }
            
            // Print images
            System.out.println("\n=== Images ===");
            int imageCount = 0;
            for (String image : uniqueImages) {
                System.out.println((++imageCount) + ". " + image);
                if (imageCount >= 10) {
                    System.out.println("... and " + (uniqueImages.size() - 10) + " more images");
                    break;
                }
            }
            
        } catch (Exception e) {
            System.err.println("Error analyzing URL: " + e.getMessage());
            e.printStackTrace();
        }
    }
    
    /**
     * Count the number of words in a text.
     */
    private static int countWords(String text) {
        if (text == null || text.isEmpty()) {
            return 0;
        }
        
        StringTokenizer tokenizer = new StringTokenizer(text);
        return tokenizer.countTokens();
    }
}
