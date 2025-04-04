import java.util.*;
import java.net.*;
import java.util.regex.*;
import java.io.*;
//import be.arci.html.*;

/* ---------------------------------------------------------------------
 * Name der Klasse: BaueSeiten
 * Durchlauf durch die �bergebene Seitenstruktur und Aufruf der Analyse
 * @author Sven Bohnstedt (wi4199), Paul Pohl (wi4628)
 * Erstellt am 07.06.2004
   ---------------------------------------------------------------------*/

public class BaueSeiten extends makePfad{
	
	 private String urlpfad;
	 //Liste der Pfade der analysierten Seiten
	 private  HashSet seitenpfad = new HashSet();
     //Liste der analysierten Seiten
	 private  ArrayList seiteninhalte = new ArrayList(); 	      
     //Instanz der Klasse zum Parsen einer Seite 
	 private OnlineParser parser;
     //Liste von Strings, die nicht Teil der Namen der zu analysierenden Seiten sein d�rfen  
	 private HashSet ohne = new HashSet();
     //Liste von Strings, die Teil der Namen der zu analysierenden Seiten sein m�ssen  
	 private HashSet mit = new HashSet();
	 //OnlineParser tempparser = new OnlineParser();

 /* ---------------------------------------------------------------------
  * Name des Konstruktors : BaueSeiten
  * Initialisierung der Variablen und Aufruf der Rekursion
  * @author Sven Bohnstedt (wi4199), Paul Pohl (wi4628)
  * @param : String seite: Adresse der zu analysierenden Seite 
  * @param : HashSet ohne: Liste von Strings, die nicht Teil der Namen der zu analysierenden Seiten sein d�rfen    
  * @param : HashSet mit: Liste von Strings, die Teil der Namen der zu analysierenden Seiten sein m�ssen 
  * @param : int rt: Rekursionstiefe bis zu der maximal analysiert werden soll
  * @param : boolean tiefe : true, wenn eine Rekursionstiefe angegeben ist 
   ---------------------------------------------------------------------*/
	 
	 public BaueSeiten(String seite, HashSet ohne, HashSet mit, int rt, boolean tiefe){
	 	this.parser = new OnlineParser();
		this.ohne = ohne;
		this.mit = mit;
		this.seitenpfad = new HashSet();
		Rekursion(seite, rt, tiefe);
	}

	 
	 /* ---------------------------------------------------------------------
	  * Name der Prozedur : Rekursion
	  * Durchl�uft rekursiv ab der angegebenen Startseite die Verzeichnisstruktur
	  * und ruft f�r jede Seite den OnlineParser auf um die Seite zu analysieren
	  * die Ergebnisse werden in einer Liste SeitenInhalte festgehalten
	  * @author Sven Bohnstedt (wi4199), Paul Pohl (wi4628)
	  * @param : String url: Adresse der zu analysierenden Seite 
	  * @param : int rt: Rekursionstiefe bis zu der maximal analysiert werden soll
	  * @param : boolean tiefe : true, wenn eine Rekursionstiefe angegeben ist 
	  * @return : void 
	   	   ---------------------------------------------------------------------*/	 
	private void Rekursion(String url, int rektiefe, boolean tiefe){
		
        //Test,ob sie nicht bereits analysiert wurde, ob sie dem regul�ren Ausdruck entspricht
		//und ob die angegebene url existiert 
		
		if (!isIn(url)&&(!testAufRegExpOhne(url))&&(testAufRegExpMit(url))&&(isURL2(url))){
			OnlineParser tempparser = new OnlineParser();
			seitenpfad.add(url);
	        //System.out.println("Rek: "+rektiefe+" url: "+url);
			//System.out.println("Rek1");
			tempparser.parse(url); //Parsen der Seite
			//System.out.println("Rek1");
	        //System.out.println("Rek2: "+rektiefe);
	    	tempparser.seite.setName(url);		    	
	    	this.seiteninhalte.add(tempparser.seite); //Analyseergebnis in Liste
            //Liste der Links dieser Seite       
	    	//System.out.println("Rek1");
	    //	Iterator it = getLinks(url).iterator();// !!!!!!Achtung vorher rausgenommen, keine Ahnung, ob es laeuft
	    //	System.out.println("Rek1");
	    	//  Iterator it= tempparser.seite.getLinks().iterator();// Hierdurch ersetzt, da keine 2. Instanz vom parser noetig
	    	Iterator it= tempparser.getLinks(url,tempparser.hs).iterator();
	    	String temp= "";
			while (it.hasNext()){ 
				temp = it.next().toString();
		    	
		    	if (!isIn(temp)&&(!testAufRegExpOhne(temp))&&(testAufRegExpMit(url)))
		    	  if (rektiefe >0 ||(!tiefe))
		    	  //Aufruf der Rekursion	
				  Rekursion(temp, rektiefe-1, tiefe);
			    else{
				while (it.hasNext()&& (isIn(temp)|| testAufRegExpOhne(temp)||(!testAufRegExpMit(url)))) 
				 temp = it.next().toString();
				      if ((!isIn(temp))&& (!testAufRegExpOhne(temp))&&(testAufRegExpMit(url)))	
				    	  if ((rektiefe >0) ||(!tiefe)) 
				    	//Aufruf der Rekursion	
			    	    Rekursion(temp, rektiefe-1, tiefe);		            
		    	}
	       
	}
	}
	
	}
	

 /* ---------------------------------------------------------------------
  * Name der Funktion : testAufRegExpOhne
  * Durchl�uf die Liste der Regul�ren Ausdr�cke die nicht im Namen
  * vorkommen d�rfen und testet, ob sie mit dem Namen der aktuellen Seite �bereinstimmen
  * @author Sven Bohnstedt (wi4199), Paul Pohl (wi4628)
  * @param : String url: Adresse der aktuellen Seite 
  * @return : boolean : wenn der Regul�re Ausdruck mit dem Namen der Seite �bereinstimmt true 
   ---------------------------------------------------------------------*/	 
	private boolean testAufRegExpOhne(String url){
        boolean gefunden = false;
		if (ohne == null) return false;
		else{
		Iterator it = ohne.iterator();
		Pattern p;
		while (it.hasNext()){
		p = Pattern.compile(it.next().toString());
		Matcher m = p.matcher(url);
       	if (m.find()) gefunden = true;
       	//else gefunden = false;
		}
		}
		return gefunden;
	}
	
 /* ---------------------------------------------------------------------
  * Name der Funktion : testAufRegExpMit
  * Durchl�uf die Liste der Regul�ren Ausdr�cke die im Namen
  * vorkommen m�ssen und testet, ob sie mit dem Namen der aktuellen Seite �bereinstimmen
  * @author Sven Bohnstedt (wi4199), Paul Pohl (wi4628)
  * @param : String url: Adresse der aktuellen Seite 
  * @return : boolean : wenn der Regul�re Ausdruck mit dem Namen der Seite �bereinstimmt true 
   ---------------------------------------------------------------------*/	 
	private boolean testAufRegExpMit(String url){
		if (mit == null) return true;
		else{
	    boolean gefunden = false;
		Iterator it = mit.iterator();
		Pattern p;
		while (it.hasNext()){
		p = Pattern.compile(it.next().toString());
		Matcher m = p.matcher(url);
       	if (m.find()) gefunden = true;
       	//else gefunden = false;
		}
		return gefunden;

		}
	}
	
	
/* ---------------------------------------------------------------------
  * Name der Funktion : isURL
  * Pr�ft, ob die �bergebene url g�ltig ist
  * @author Sven Bohnstedt (wi4199), Paul Pohl (wi4628)
  * @param : URL url : aktuelle url
  * @return : boolean : true, wenn g�ltige url 
   ---------------------------------------------------------------------*/	 
	private boolean isURL(URL url){
		InputStream is = null;
		try{
			 is = url.openStream();
			 is.close();
			return true;
	 }
		catch(Exception e){{System.out.println("URL1 nicht gefunden");
		try{is.close();}catch (Exception e1){};
		return false;}
			
		}
		finally {try{is.close();}catch (Exception e){} }
   }


/* ---------------------------------------------------------------------
  * Name der Funktion : isURL2
  * Erzeugt aus dem �bergebenen url-Namen eine url und pr�ft,ob sie g�ltig ist
  * @author Sven Bohnstedt (wi4199), Paul Pohl (wi4628)
  * @param : String url : Name der aktuellen url
  * @return : boolean : true, wenn g�ltige url 
   ---------------------------------------------------------------------*/	 
	private boolean isURL2(String url){
		InputStream is =null;
			try{
				URL url1 = new URL(url);
				 is = url1.openStream();
				is.close();
				return true;
		 }
			catch(Exception e){
				try{is.close();}catch (Exception e1){};
			return false;}
			finally {try{is.close();}catch (Exception e){} }	
			}
	
/* ---------------------------------------------------------------------
  * Name der Funktion : isIn
  * Testet,ob der �bergebene Seitenname in der Liste der bereits analysierten Seiten vorhanden ist
  * @author Sven Bohnstedt (wi4199), Paul Pohl (wi4628)
  * @param : String htmlSeite : Name der zu pr�fenden Seite
  * @return : boolean : true, wenn Seite bereits analysiert 
   ---------------------------------------------------------------------*/	 
	public boolean isIn(String htmlSeite){
		Iterator it = this.seitenpfad.iterator();
	    boolean drinne = false;
		while (it.hasNext()){
	    	if (htmlSeite.equals(it.next().toString()))
	    	 drinne = true;	    	
	    }
	       	return drinne;
	    }
	

/* ---------------------------------------------------------------------
  * Name der Funktion : getSeitenInhalte
  * Gibt die Liste mit den Analyseergebnissen zur�ck
  * @author Sven Bohnstedt (wi4199), Paul Pohl (wi4628)
  * @param : -
  * @return : ArrayList Liste mit Analyseergebnissen 
   ---------------------------------------------------------------------*/	 
	public ArrayList getSeitenInhalte(){
		return this.seiteninhalte;
	}

}
