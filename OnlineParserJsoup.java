import java.io.*;
import java.util.*;
import java.net.*;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

/* ---------------------------------------------------------------------
 * Name der Klasse: OnlineParserJsoup 
 * Parsen der aktuellen Seite mit Jsoup statt be.arci.html
 * @author Sven Bohnstedt (wi4199), Paul Pohl (wi4628)
 * Modified to use jsoup instead of be.arci.html
   ---------------------------------------------------------------------*/

public class OnlineParserJsoup extends makePfad
{
    
    SeitenElement seite = new SeitenElement();
    long groesse;
    long anzahltags;
    String urlpfad;
    URL url;
    String bildpfad;
    int anzahlwoerter;
    long groesseTags;
    Document doc;
    
    /* ---------------------------------------------------------------------
      * Name des Konstruktors : OnlineParserJsoup
      * Initialisierung der Variablen und Aufruf der Rekursion
      * @author Sven Bohnstedt (wi4199), Paul Pohl (wi4628)
       ---------------------------------------------------------------------*/    
    public OnlineParserJsoup(){}
    
    
 /* ---------------------------------------------------------------------
  * Name der Prozedur : parse
  * Parsen der übergebenen Seite
  * @author Sven Bohnstedt (wi4199), Paul Pohl (wi4628)
  * @param : String urls : zu parsende Seite 
  * @return : void
   ---------------------------------------------------------------------*/
    public void parse(String urls) {    
        HashSet tempbild = new HashSet(); // Hashset fuer die gefundenen bilder ohne test auf vorhandensein
        HashSet tempscript = new HashSet(); // javascript
        HashSet tempcss = new HashSet(); // css
        HashSet tempobject = new HashSet(); // objecte
        HashSet tempapplet = new HashSet();// applets
        HashSet tempbody = new HashSet(); // body
        HashSet templinks = new HashSet(); //links
        HashSet tempframes = new HashSet();// frames
        
        try {
            url = new URL(urls);    
            // Parse the HTML using jsoup
            doc = Jsoup.connect(urls).get();
            
            // Set basic page information
            this.seite.groessetagsbyte = getTagGroesseFormatiert(doc); 
            this.seite.setContentByte(getBytesInhalt(doc));
            this.seite.setWoerter(getAnzWoerter(doc));
            this.seite.setPfad(url.getPath());
            this.seite.setTags(getAnzTags(doc));
            this.seite.setGroesseSeite(url.openConnection().getContentLength());
            this.seite.setGroesseSeite(leseGroesseSeite(url));
            this.urlpfad = url.getHost();
            this.urlpfad = this.urlpfad + url.getPath(); //Pfad der Datei
            
            // Find images
            Elements images = doc.select("img");
            for (Element img : images) {
                String src = img.attr("src");
                if (src != null && !src.isEmpty()) {
                    if (isURL2(src)) tempbild.add(src);
                    else tempbild.add(absPfad(src, urlpfad));
                }
            }
            
            // Find links
            Elements links = doc.select("a");
            for (Element link : links) {
                String href = link.attr("href");
                if (href != null && !href.isEmpty()) {
                    if (isURL2(href)) templinks.add(href);
                    else templinks.add(absPfad(href, urlpfad));
                }
            }
            
            // Find scripts
            Elements scripts = doc.select("script");
            for (Element script : scripts) {
                String src = script.attr("src");
                if (src != null && !src.isEmpty()) {
                    if (isURL2(src)) tempscript.add(src);
                    else tempscript.add(absPfad(src, urlpfad));
                }
            }
            
            // Find CSS
            Elements cssLinks = doc.select("link[rel=stylesheet]");
            for (Element cssLink : cssLinks) {
                String href = cssLink.attr("href");
                if (href != null && !href.isEmpty()) {
                    if (isURL2(href)) tempcss.add(href);
                    else tempcss.add(absPfad(href, urlpfad));
                }
            }
            
            // Find objects
            Elements objects = doc.select("object");
            for (Element object : objects) {
                String data = object.attr("data");
                if (data != null && !data.isEmpty()) {
                    if (isURL2(data)) tempobject.add(data);
                    else tempobject.add(absPfad(data, urlpfad));
                }
            }
            
            // Find applets
            Elements applets = doc.select("applet");
            for (Element applet : applets) {
                String code = applet.attr("code");
                String codebase = applet.attr("codebase");
                if (code != null && !code.isEmpty()) {
                    if (codebase != null && !codebase.isEmpty()) {
                        String t = codebase + code;
                        if (isURL2(t)) tempapplet.add(t);
                        else tempapplet.add(absPfad(t, urlpfad));
                    } else {
                        if (isURL2(code)) tempapplet.add(code);
                        else tempapplet.add(absPfad(code, urlpfad));
                    }
                }
            }
            
            // Find body background
            Elements bodies = doc.select("body");
            for (Element body : bodies) {
                String background = body.attr("background");
                if (background != null && !background.isEmpty()) {
                    if (isURL2(background)) tempbody.add(background);
                    else tempbody.add(absPfad(background, urlpfad));
                }
            }
            
            // Find frames
            Elements frames = doc.select("frame");
            for (Element frame : frames) {
                String src = frame.attr("src");
                if (src != null && !src.isEmpty()) {
                    if (isURL2(src)) tempframes.add(src);
                    else tempframes.add(absPfad(src, urlpfad));
                }
            }
            
        } catch (Exception e) { 
            System.out.println("keine gültige URL angegeben: " + e.getMessage()); 
        }
        
        groesseOrt(tempbild, 1); // Uebergabe zum Testen auf Url vorhanden und groesse  
        groesseOrt(tempscript, 2);
        groesseOrt(tempcss, 3);
        groesseOrt(tempobject, 4);
        groesseOrt(tempapplet, 5);
        groesseOrt(tempbody, 6);
        groesseOrt(templinks, 7);
        groesseOrt(tempframes, 8);
    }
    
    /* ---------------------------------------------------------------------
     * Name der Prozedur : groesseOrt
     * Testet, ob url der Objekte gültig ist. Wenn gültig, dann werden die Informationen zu dem
     * jeweiligen Objekt als Listenelement erfasst
     * @author Sven Bohnstedt (wi4199), Paul Pohl (wi4628)
     * @param : HashSet liste : des übergebenen Objektes 
     * @param : byte art : Art des übergebenen Objektes 
     * @return : void
      ---------------------------------------------------------------------*/    
    private void groesseOrt(HashSet liste, int art) {       
       long filesize = 0;
       Iterator it = liste.iterator();
       while (it.hasNext()) {
           String temp = it.next().toString();
           try { 
               URL url1 = new URL(temp);
               if (isURL(url1)) { 
                    if (art != 7) { // groesse der url bestimmen  
                        filesize = leseGroesseSeite(url1);
                    } else {
                        filesize = 0;
                    }
                    // Erfassung des Objektnamens
                    this.seite.setName(url1.getFile());
                    // Erfassung der Größe des Objektes
                    seite.setGroesse(seite.getGroesse() + filesize);
                    // Erfassung der Informationen zu dem jeweiligen Objekt
                    switch(art) {
                        case 1:
                            this.seite.setBilder(new ReferenzierteObjekte(url1.getFile(), url1.getHost() + absPfad2(url1.getPath()), filesize));
                            break;
                        case 2:
                            this.seite.setScript(new ReferenzierteObjekte(url1.getFile(), url1.getHost() + absPfad2(url1.getPath()), filesize));
                            break;
                        case 3:
                            this.seite.setCss(new ReferenzierteObjekte(url1.getFile(), url1.getHost() + absPfad2(url1.getPath()), filesize));
                            break;
                        case 4:
                            this.seite.setObjekt(new ReferenzierteObjekte(url1.getFile(), url1.getHost() + absPfad2(url1.getPath()), filesize));
                            break;
                        case 5:
                            this.seite.setApplet(new ReferenzierteObjekte(url1.getFile(), url1.getHost() + absPfad2(url1.getPath()), filesize));
                            break;
                        case 6:
                            this.seite.setBody(new ReferenzierteObjekte(url1.getFile(), url1.getHost() + absPfad2(url1.getPath()), filesize));
                            break;
                        case 7:
                            this.seite.setLinks(new ReferenzierteObjekte(url1.getFile(), url1.getHost() + absPfad2(url1.getPath()), 0));
                            break;
                        case 8:
                            this.seite.setFrames(new ReferenzierteObjekte(url1.getFile(), url1.getHost() + absPfad2(url1.getPath()), filesize));    
                    }
                }
           } catch (Exception e) {}
       } 
    } 

    /* ---------------------------------------------------------------------
     * Name der Funktion : isURL
     * Prüft, ob die übergebene url gültig ist
     * @author Sven Bohnstedt (wi4199), Paul Pohl (wi4628)
     * @param : URL url : aktuelle url
     * @return : boolean : true, wenn gültige url 
      ---------------------------------------------------------------------*/     
    private boolean isURL(URL url) {
        InputStream is = null;
        try {
             is = url.openStream();
            return true;
        } catch(Exception e) {
            try { is.close(); } catch (Exception e1) {};
            return false;
        } finally {
            try { is.close(); } catch (Exception e) {} 
        }
    }

    /* ---------------------------------------------------------------------
     * Name der Funktion : isURL2
     * Erzeugt aus dem übergebenen url-Namen eine url und prüft,ob sie gültig ist
     * @author Sven Bohnstedt (wi4199), Paul Pohl (wi4628)
     * @param : String url : Name der aktuellen url
     * @return : boolean : true, wenn gültige url 
      ---------------------------------------------------------------------*/     
    private boolean isURL2(String url) {
        InputStream is = null;
        try {
            URL url1 = new URL(url);
            is = url1.openStream();
            is.close();
            return true;
        } catch(Exception e) {
            try { is.close(); } catch (Exception e1) {};
            return false;
        } finally {
            try { is.close(); } catch (Exception e) {} 
        }
    }
       
    /* ---------------------------------------------------------------------
     * Name der Funktion : getAnzTags
     * Liefert die Anzahl der Tags der übergebenen Seite
     * @author Sven Bohnstedt (wi4199), Paul Pohl (wi4628)
     * @param : Document doc : Das jsoup Document
     * @return : int : Anzahl der Tags 
      ---------------------------------------------------------------------*/     
    private int getAnzTags(Document doc) {
        return doc.getAllElements().size();
    }
    
    /* ---------------------------------------------------------------------
     * Name der Funktion : getAnzWoerter
     * Liefert die Anzahl der Wörter der übergebenen Seite
     * @author Sven Bohnstedt (wi4199), Paul Pohl (wi4628)
     * @param : Document doc : Das jsoup Document
     * @return : int : Anzahl der Wörter 
      ---------------------------------------------------------------------*/     
    private int getAnzWoerter(Document doc) {
        String text = doc.text();
        StringBuffer content = new StringBuffer(text);
        return getWoerter(content);
    }

    /* ---------------------------------------------------------------------
     * Name der Funktion : getWoerter
     * Liefert die Anzahl der Wörter der übergebenen Seite
     * @author Sven Bohnstedt (wi4199), Paul Pohl (wi4628)
     * @param : StringBuffer content : Wörter der Seite
     * @return : int : Anzahl der Wörter 
      ---------------------------------------------------------------------*/    
    private int getWoerter(StringBuffer content) {
        int temp = 0;
        content.append(" ");
        for (int i = 0; i < content.length() - 1; i++) {
            if (!(content.charAt(i) == ' ') && content.charAt(i + 1) == ' ') { 
                temp++;
            }            
        }
        this.groesseTags = content.length() - 1;
        return temp; 
    }
    
    private long getBytesInhalt(Document doc) {
        String text = doc.text();
        return text.length();
    }

    private long leseGroesseSeite(URL url4) {
        InputStream in = null;
        try {
            in = url4.openStream();
            int bytes_read = 0;
            int i = 0;
            while ((bytes_read = in.read()) != -1) {
                i++;
            }
            in.close();
            return i;
        } catch(Exception e) {
            System.out.println("Streamfehler");
            return -1;
        } finally {
            try {
                in.close();
            } catch(Exception e) {}
        }
    }
 
    public HashSet getLinks(String urls, Document doc) {
        HashSet temp = new HashSet();
        try {
            URL url = new URL(urls);
            if (isURL(url)) {
                urlpfad = url.getHost();
                urlpfad = urlpfad + url.getPath();
                
                // Find links
                Elements links = doc.select("a");
                for (Element link : links) {
                    String href = link.attr("href");
                    if (href != null && !href.isEmpty()) {
                        String temp2 = absPfad(href, urlpfad);
                        if (isURL2(temp2)) temp.add(temp2);
                    }
                }
                
                // Find frames
                Elements frames = doc.select("frame");
                for (Element frame : frames) {
                    String src = frame.attr("src");
                    if (src != null && !src.isEmpty()) {
                        String temp2 = absPfad(src, urlpfad);
                        if (isURL2(temp2)) temp.add(temp2);
                    }
                }
            }    
            return temp;
        } catch (Exception e) { 
            System.out.println("keine gültige URL angegeben " + urls); 
        }
        return temp;
    }
 
    private int getTagGroesseFormatiert(Document doc) {
        int j = 0;
        
        // Get all elements except specific ones we want to exclude
        Elements elements = doc.getAllElements();
        elements.removeIf(el -> 
            el.tagName().equals("!doctype") || 
            el.tagName().equals("head") || 
            el.tagName().equals("html") || 
            el.tagName().equals("!--")
        );
        
        for (Element element : elements) {
            String temp = element.outerHtml();
            j += temp.length();
        }
        
        return j;
    }        
}
