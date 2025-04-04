import java.io.*;
import java.net.*;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import java.util.*;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * A simple HTML analyzer that uses jsoup to parse and analyze HTML pages.
 * This version also generates an HTML report file.
 */
public class SimpleHtmlAnalyzerWithReport {
    
    public static void main(String[] args) {
        if (args.length == 0) {
            System.out.println("Usage: java -cp lib/jsoup.jar:. SimpleHtmlAnalyzerWithReport <url> [output_file]");
            System.exit(1);
        }
        
        String url = args[0];
        String outputFile = "analysis_report.html";
        if (args.length > 1) {
            outputFile = args[1];
        }
        
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
            
            // Get CSS
            Elements cssLinks = doc.select("link[rel=stylesheet]");
            Set<String> uniqueCss = new HashSet<>();
            for (Element cssLink : cssLinks) {
                String href = cssLink.attr("abs:href");
                if (!href.isEmpty()) {
                    uniqueCss.add(href);
                }
            }
            
            // Get scripts
            Elements scripts = doc.select("script[src]");
            Set<String> uniqueScripts = new HashSet<>();
            for (Element script : scripts) {
                String src = script.attr("abs:src");
                if (!src.isEmpty()) {
                    uniqueScripts.add(src);
                }
            }
            
            // Print results to console
            System.out.println("=== HTML Analysis for " + url + " ===");
            System.out.println("Title: " + doc.title());
            System.out.println("Number of tags: " + tagCount);
            System.out.println("Number of words: " + wordCount);
            System.out.println("Size in bytes: " + byteSize);
            System.out.println("Number of links: " + uniqueLinks.size());
            System.out.println("Number of images: " + uniqueImages.size());
            System.out.println("Number of CSS files: " + uniqueCss.size());
            System.out.println("Number of JavaScript files: " + uniqueScripts.size());
            
            // Generate HTML report
            generateHtmlReport(url, doc.title(), tagCount, wordCount, byteSize, 
                              uniqueLinks, uniqueImages, uniqueCss, uniqueScripts, outputFile);
            
            System.out.println("\nHTML report generated: " + outputFile);
            
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
    
    /**
     * Generate an HTML report file with the analysis results.
     */
    private static void generateHtmlReport(String url, String title, int tagCount, int wordCount, 
                                          int byteSize, Set<String> links, Set<String> images, 
                                          Set<String> css, Set<String> scripts, String outputFile) 
                                          throws IOException {
        
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String currentTime = dateFormat.format(new Date());
        
        StringBuilder html = new StringBuilder();
        html.append("<!DOCTYPE html>\n");
        html.append("<html lang=\"en\">\n");
        html.append("<head>\n");
        html.append("    <meta charset=\"UTF-8\">\n");
        html.append("    <meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0\">\n");
        html.append("    <title>HTML Analysis Report for ").append(url).append("</title>\n");
        html.append("    <style>\n");
        html.append("        body { font-family: Arial, sans-serif; margin: 20px; }\n");
        html.append("        h1, h2 { color: #333; }\n");
        html.append("        table { border-collapse: collapse; width: 100%; margin-bottom: 20px; }\n");
        html.append("        th, td { border: 1px solid #ddd; padding: 8px; text-align: left; }\n");
        html.append("        th { background-color: #f2f2f2; }\n");
        html.append("        tr:nth-child(even) { background-color: #f9f9f9; }\n");
        html.append("        .section { margin-bottom: 30px; }\n");
        html.append("    </style>\n");
        html.append("</head>\n");
        html.append("<body>\n");
        
        // Header
        html.append("    <h1>HTML Analysis Report</h1>\n");
        html.append("    <p>Generated on: ").append(currentTime).append("</p>\n");
        
        // Overview section
        html.append("    <div class=\"section\">\n");
        html.append("        <h2>Overview</h2>\n");
        html.append("        <table>\n");
        html.append("            <tr><th>URL</th><td><a href=\"").append(url).append("\">").append(url).append("</a></td></tr>\n");
        html.append("            <tr><th>Title</th><td>").append(title).append("</td></tr>\n");
        html.append("            <tr><th>Number of Tags</th><td>").append(tagCount).append("</td></tr>\n");
        html.append("            <tr><th>Number of Words</th><td>").append(wordCount).append("</td></tr>\n");
        html.append("            <tr><th>Size in Bytes</th><td>").append(byteSize).append("</td></tr>\n");
        html.append("            <tr><th>Number of Links</th><td>").append(links.size()).append("</td></tr>\n");
        html.append("            <tr><th>Number of Images</th><td>").append(images.size()).append("</td></tr>\n");
        html.append("            <tr><th>Number of CSS Files</th><td>").append(css.size()).append("</td></tr>\n");
        html.append("            <tr><th>Number of JavaScript Files</th><td>").append(scripts.size()).append("</td></tr>\n");
        html.append("        </table>\n");
        html.append("    </div>\n");
        
        // Links section
        if (!links.isEmpty()) {
            html.append("    <div class=\"section\">\n");
            html.append("        <h2>Links (").append(links.size()).append(")</h2>\n");
            html.append("        <table>\n");
            html.append("            <tr><th>#</th><th>URL</th></tr>\n");
            int count = 1;
            for (String link : links) {
                html.append("            <tr><td>").append(count++).append("</td><td><a href=\"").append(link).append("\">").append(link).append("</a></td></tr>\n");
            }
            html.append("        </table>\n");
            html.append("    </div>\n");
        }
        
        // Images section
        if (!images.isEmpty()) {
            html.append("    <div class=\"section\">\n");
            html.append("        <h2>Images (").append(images.size()).append(")</h2>\n");
            html.append("        <table>\n");
            html.append("            <tr><th>#</th><th>URL</th><th>Preview</th></tr>\n");
            int count = 1;
            for (String image : images) {
                html.append("            <tr><td>").append(count++).append("</td><td><a href=\"").append(image).append("\">").append(image).append("</a></td><td><img src=\"").append(image).append("\" alt=\"Image\" style=\"max-width: 100px; max-height: 100px;\"></td></tr>\n");
            }
            html.append("        </table>\n");
            html.append("    </div>\n");
        }
        
        // CSS section
        if (!css.isEmpty()) {
            html.append("    <div class=\"section\">\n");
            html.append("        <h2>CSS Files (").append(css.size()).append(")</h2>\n");
            html.append("        <table>\n");
            html.append("            <tr><th>#</th><th>URL</th></tr>\n");
            int count = 1;
            for (String cssFile : css) {
                html.append("            <tr><td>").append(count++).append("</td><td><a href=\"").append(cssFile).append("\">").append(cssFile).append("</a></td></tr>\n");
            }
            html.append("        </table>\n");
            html.append("    </div>\n");
        }
        
        // JavaScript section
        if (!scripts.isEmpty()) {
            html.append("    <div class=\"section\">\n");
            html.append("        <h2>JavaScript Files (").append(scripts.size()).append(")</h2>\n");
            html.append("        <table>\n");
            html.append("            <tr><th>#</th><th>URL</th></tr>\n");
            int count = 1;
            for (String script : scripts) {
                html.append("            <tr><td>").append(count++).append("</td><td><a href=\"").append(script).append("\">").append(script).append("</a></td></tr>\n");
            }
            html.append("        </table>\n");
            html.append("    </div>\n");
        }
        
        html.append("</body>\n");
        html.append("</html>");
        
        // Write to file
        try (FileWriter writer = new FileWriter(outputFile)) {
            writer.write(html.toString());
        }
    }
}
