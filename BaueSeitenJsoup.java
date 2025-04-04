import java.util.*;
import java.net.*;
import java.util.regex.*;
import java.io.*;

/* ---------------------------------------------------------------------
 * Name der Klasse: BaueSeitenJsoup
 * Durchlauf durch die übergebene Seitenstruktur und Aufruf der Analyse
 * Modified to use jsoup instead of be.arci.html
 * @author Sven Bohnstedt (wi4199), Paul Pohl (wi4628)
 * Erstellt am 07.06.2004
   ---------------------------------------------------------------------*/

public class BaueSeitenJsoup extends makePfad {
    
     private String urlpfad;
     //Liste der Pfade der analysierten Seiten
     private HashSet seitenpfad = new HashSet();
     //Liste der analysierten Seiten
     private ArrayList seiteninhalte = new ArrayList();         
     //Instanz der Klasse zum Parsen einer Seite 
     private OnlineParserJsoup parser;
     //Liste von Strings, die nicht Teil der Namen der zu analysierenden Seiten sein dürfen  
     private HashSet ohne = new HashSet();
     //Liste von Strings, die Teil der Namen der zu analysierenden Seiten sein müssen  
     private HashSet mit = new HashSet();

 /* ---------------------------------------------------------------------
  * Name des Konstruktors : BaueSeitenJsoup
  * Initialisierung der Variablen und Aufruf der Rekursion
  * @author Sven Bohnstedt (wi4199), Paul Pohl (wi4628)
  * @param : String seite: Adresse der zu analysierenden Seite 
  * @param : HashSet ohne: Liste von Strings, die nicht Teil der Namen der zu analysierenden Seiten sein dürfen    
  * @param : HashSet mit: Liste von Strings, die Teil der Namen der zu analysierenden Seiten sein müssen 
  * @param : int rt: Rekursionstiefe bis zu der maximal analysiert werden soll
  * @param : boolean tiefe : true, wenn eine Rekursionstiefe angegeben ist 
   ---------------------------------------------------------------------*/
     
     public BaueSeitenJsoup(String seite, HashSet ohne, HashSet mit, int rt, boolean tiefe) {
        this.parser = new OnlineParserJsoup();
        this.ohne = ohne;
        this.mit = mit;
        this.seitenpfad = new HashSet();
        Rekursion(seite, rt, tiefe);
    }

     
     /* ---------------------------------------------------------------------
      * Name der Prozedur : Rekursion
      * Durchläuft rekursiv ab der angegebenen Startseite die Verzeichnisstruktur
      * und ruft für jede Seite den OnlineParserJsoup auf um die Seite zu analysieren
      * die Ergebnisse werden in einer Liste SeitenInhalte festgehalten
      * @author Sven Bohnstedt (wi4199), Paul Pohl (wi4628)
      * @param : String url: Adresse der zu analysierenden Seite 
      * @param : int rt: Rekursionstiefe bis zu der maximal analysiert werden soll
      * @param : boolean tiefe : true, wenn eine Rekursionstiefe angegeben ist 
      * @return : void 
           ---------------------------------------------------------------------*/     
    private void Rekursion(String url, int rektiefe, boolean tiefe) {
        
        //Test,ob sie nicht bereits analysiert wurde, ob sie dem regulären Ausdruck entspricht
        //und ob die angegebene url existiert 
        
        if (!isIn(url) && (!testAufRegExpOhne(url)) && (testAufRegExpMit(url)) && (isURL2(url))) {
            OnlineParserJsoup tempparser = new OnlineParserJsoup();
            seitenpfad.add(url);
            tempparser.parse(url); //Parsen der Seite
            tempparser.seite.setName(url);              
            this.seiteninhalte.add(tempparser.seite); //Analyseergebnis in Liste
            
            // Get links from the parsed page
            HashSet links = tempparser.getLinks(url, tempparser.doc);
            Iterator it = links.iterator();
            String temp = "";
            
            while (it.hasNext()) { 
                temp = it.next().toString();
                
                if (!isIn(temp) && (!testAufRegExpOhne(temp)) && (testAufRegExpMit(url)))
                  if (rektiefe > 0 || (!tiefe))
                  //Aufruf der Rekursion    
                  Rekursion(temp, rektiefe-1, tiefe);
                else {
                    while (it.hasNext() && (isIn(temp) || testAufRegExpOhne(temp) || (!testAufRegExpMit(url)))) 
                     temp = it.next().toString();
                      if ((!isIn(temp)) && (!testAufRegExpOhne(temp)) && (testAufRegExpMit(url)))    
                          if ((rektiefe > 0) || (!tiefe)) 
                        //Aufruf der Rekursion    
                        Rekursion(temp, rektiefe-1, tiefe);                    
                }
           
            }
        }
    }
    

 /* ---------------------------------------------------------------------
  * Name der Funktion : testAufRegExpOhne
  * Durchläuf die Liste der Regulären Ausdrücke die nicht im Namen
  * vorkommen dürfen und testet, ob sie mit dem Namen der aktuellen Seite übereinstimmen
  * @author Sven Bohnstedt (wi4199), Paul Pohl (wi4628)
  * @param : String url: Adresse der aktuellen Seite 
  * @return : boolean : wenn der Reguläre Ausdruck mit dem Namen der Seite übereinstimmt true 
   ---------------------------------------------------------------------*/     
    private boolean testAufRegExpOhne(String url) {
        boolean gefunden = false;
        if (ohne == null) return false;
        else {
        Iterator it = ohne.iterator();
        Pattern p;
        while (it.hasNext()) {
        p = Pattern.compile(it.next().toString());
        Matcher m = p.matcher(url);
        if (m.find()) gefunden = true;
        }
        }
        return gefunden;
    }
    
 /* ---------------------------------------------------------------------
  * Name der Funktion : testAufRegExpMit
  * Durchläuf die Liste der Regulären Ausdrücke die im Namen
  * vorkommen müssen und testet, ob sie mit dem Namen der aktuellen Seite übereinstimmen
  * @author Sven Bohnstedt (wi4199), Paul Pohl (wi4628)
  * @param : String url: Adresse der aktuellen Seite 
  * @return : boolean : wenn der Reguläre Ausdruck mit dem Namen der Seite übereinstimmt true 
   ---------------------------------------------------------------------*/     
    private boolean testAufRegExpMit(String url) {
        if (mit == null) return true;
        else {
        boolean gefunden = false;
        Iterator it = mit.iterator();
        Pattern p;
        while (it.hasNext()) {
        p = Pattern.compile(it.next().toString());
        Matcher m = p.matcher(url);
        if (m.find()) gefunden = true;
        }
        return gefunden;

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
             is.close();
            return true;
     }
        catch(Exception e) {
            System.out.println("URL1 nicht gefunden");
            try { is.close(); } catch (Exception e1) {};
            return false;
        }
        finally { try { is.close(); } catch (Exception e) {} }
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
         }
            catch(Exception e) {
                try { is.close(); } catch (Exception e1) {};
            return false; }
            finally { try { is.close(); } catch (Exception e) {} }    
            }
    
/* ---------------------------------------------------------------------
  * Name der Funktion : isIn
  * Testet,ob der übergebene Seitenname in der Liste der bereits analysierten Seiten vorhanden ist
  * @author Sven Bohnstedt (wi4199), Paul Pohl (wi4628)
  * @param : String htmlSeite : Name der zu prüfenden Seite
  * @return : boolean : true, wenn Seite bereits analysiert 
   ---------------------------------------------------------------------*/     
    public boolean isIn(String htmlSeite) {
        Iterator it = this.seitenpfad.iterator();
        boolean drinne = false;
        while (it.hasNext()) {
            if (htmlSeite.equals(it.next().toString()))
             drinne = true;             
        }
            return drinne;
        }
    

/* ---------------------------------------------------------------------
  * Name der Funktion : getSeitenInhalte
  * Gibt die Liste mit den Analyseergebnissen zurück
  * @author Sven Bohnstedt (wi4199), Paul Pohl (wi4628)
  * @param : -
  * @return : ArrayList Liste mit Analyseergebnissen 
   ---------------------------------------------------------------------*/     
    public ArrayList getSeitenInhalte() {
        return this.seiteninhalte;
    }

}
