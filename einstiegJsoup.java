import java.net.*;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

/**
 * @author sbohnstedt
 * Modified to use jsoup instead of be.arci.html
 */

public class einstiegJsoup {
    public static void main(String[] args) {
        System.out.println("werner " + args[0]);
        
        try {
            // Create the URL
            URL url = new URL(args[0]);
            
            // Parse the HTML using jsoup
            Document doc = Jsoup.connect(args[0]).get();
            
            einstiegJsoup ein = new einstiegJsoup();
            System.out.println(ein.getTagGroesseFormatiert(doc));
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    private int getTagGroesseFormatiert(Document doc) {
        String[] excludeTags = {"!doctype", "head", "!--", "html"};
        int i = 0;
        int j = 0;
        
        // Get all elements
        for (org.jsoup.nodes.Element element : doc.getAllElements()) {
            boolean skip = false;
            
            // Check if the element should be excluded
            for (String excludeTag : excludeTags) {
                if (element.tagName().equalsIgnoreCase(excludeTag)) {
                    skip = true;
                    break;
                }
            }
            
            if (!skip) {
                String temp = element.outerHtml();
                for (int k = 0; k < temp.length(); k++) {
                    i++;
                }
                j += i;
                i = 0;
            }
        }
        
        return j;
    }
}
