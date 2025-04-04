import java.io.*;
import java.util.*;

/* ---------------------------------------------------------------------
 * Name der Klasse: BaueHtmlAusgabe
 * Erzeugt eine HTML Seite mit den Ergebnissen der Analyse
 * @author Sven Bohnstedt (wi4199), Paul Pohl (wi4628)
 * Erstellt am 07.06.2004
   ---------------------------------------------------------------------*/

public class BaueHtmlAusgabe {

	
	static final String tueber = "<h1>"; 
	static final String teueber ="</h1>";
	static final String tlink = "<a href = ";
	static final String telink ="</a1>";

	
	private String ausgabe;
	private ArrayList seitenInhalte;

	//Beinhaltet die Gesamtanzahl der analysierten Objekte
	private int anzahlLinks = 0;
	private int anzahlBilder = 0;
	private int anzahlCSS = 0;
	private int anzahlHintergrund = 0;
	private int anzahlObject = 0;
	private int anzahlApplet = 0;
	private int anzahlScript = 0;
	
	//Beinhaltet die Gesamtgroesse der analysierten Objekte
	private long groesseBilder = 0;
	private long groesseCSS = 0;
	private long groesseHintergrund = 0;
	private long groesseObject = 0;
	private long groesseApplet = 0;
	private long groesseScript = 0;
	
	
/* ---------------------------------------------------------------------
  * Name des Konstruktors : BaueSeiten
  * Liste der analysierten Seiten wird übergeben 
  * @author Sven Bohnstedt (wi4199), Paul Pohl (wi4628)
  * @param : ArrayList seitenInhalte: Liste der analysierenden Seiten 
  * @return : void 
   ---------------------------------------------------------------------*/	
	BaueHtmlAusgabe(ArrayList seitenInhalte){
		this.seitenInhalte=seitenInhalte;
		
	}

/* ---------------------------------------------------------------------
  * Name der Prozedur : schreibeHTML
  * Erstellen der HTML Datei 
  * @author Sven Bohnstedt (wi4199), Paul Pohl (wi4628)
  * @param : String ausgabeseite: Pfad an dem die Ergebnisdatei gespeichert werden soll 
  * @return : void 
   ---------------------------------------------------------------------*/		
	public void schreibeHTML(String ausgabeseite){
		BaueHtmlAusgabe b1 = new BaueHtmlAusgabe(seitenInhalte);
		//Beinhaltet das Ergebnis der Analyse
		String ausgabe="";
		ausgabe="<!DOCTYPE html PUBLIC \"-//W3C//DTD HTML 4.01 Transitional//EN\">";
		ausgabe += "<html><head><title>Html Parser</title></head><body bgcolor=\"#FFFFF\" text=\"#000000\"><h1 align=\"center\"" +
				"><u>Analyseergebnis</u></h1>"+AusgabeGesamtwerte()+
				"" + linksErstellen()+
				analyseErgebnis()+
				"</body></html>";
		
		try{
			FileWriter bw ;
			//Wenn kein Pfad angegeben wird im aktuellen Verzeichnis eine Datei erstellt
			if(ausgabeseite.equals("")) 
				 bw = new FileWriter("ausgabe.htm");
			else 
				 bw = new FileWriter(ausgabeseite);
			     bw.write(ausgabe);
			     bw.close();
		}catch (IOException e) {
		  System.out.println("Fehler beim Schreiben in die Datei");
		}
	}
	
/* ---------------------------------------------------------------------
  * Name der Funktion : zaehle
  * Hilfsfunktion für zaehleBilder--> Gibt Anzahl der Listenelemente wieder  
  * @author Sven Bohnstedt (wi4199), Paul Pohl (wi4628)
  * @param :  HashSet liste : Liste deren Elemente gezählt werden 
  * @return : int : Anzahl der Elemente 
   ---------------------------------------------------------------------*/		
	public int zaehle( HashSet liste){
		
		Iterator it= liste.iterator();
		int i=1;
		while(it.hasNext()){
			it.next();
			i++;
		}
		return i;
	}


/* ---------------------------------------------------------------------
  * Name der Funktion : GroesseAllerSeitenohne
  * Berechnet die Größe aller Seiten ohne eingebundene Objekte  
  * @author Sven Bohnstedt (wi4199), Paul Pohl (wi4628)
  * @param :  - 
  * @return : long : Gesamtgröße aller Seiten ohne eingebundene Objekte
   ---------------------------------------------------------------------*/		
	public long GroesseAllerSeitenohne(){
		ArrayList temp = seitenInhalte;
		Iterator it= temp.iterator();
		long i=0;
		while(it.hasNext()){
			i = i+ ((SeitenElement)it.next()).getGroesseSeite();
		}
		return i;
	}

/* ---------------------------------------------------------------------
  * Name der Funktion : GroesseAllerInkludierten
  * Berechnet die Größe aller eingebundenen Objekte  
  * @author Sven Bohnstedt (wi4199), Paul Pohl (wi4628)
  * @param :  - 
  * @return : long : Gesamtgröße aller eingebundenen Objekte
   ---------------------------------------------------------------------*/		
	public long GroesseAllerInkludierten(){
		ArrayList temp = seitenInhalte;
		Iterator it= temp.iterator();
		long i=0;
		while(it.hasNext()){
			i = i+ ((SeitenElement)it.next()).getGroesse();
		}
		return i;
	}
	
/* ---------------------------------------------------------------------
  * Name der Funktion : AnzahlAllerTags
  * Berechnet die Anzahl aller Tags 
  * @author Sven Bohnstedt (wi4199), Paul Pohl (wi4628)
  * @param :  - 
  * @return : long : Anzahl aller Tags
   ---------------------------------------------------------------------*/	
	public long AnzahlAllerTags(){
		ArrayList temp = seitenInhalte;
		Iterator it= temp.iterator();
		long i=0;
		while(it.hasNext()){
			i = i+ ((SeitenElement)it.next()).getTags();
		}
		return i;
	}

/* ---------------------------------------------------------------------
  * Name der Prozedur : GesamtLinks
  * Berechnet die Anzahl aller Links
  * @author Sven Bohnstedt (wi4199), Paul Pohl (wi4628)
  * @param :  Iterator it: Iterator der Liste der Seiten  
  * @return : void
   ---------------------------------------------------------------------*/	
	private void GesamtLinks(Iterator it){
		while(it.hasNext()){
			it.next();
            this.anzahlLinks= this.anzahlLinks+1;}	
	}

/* ---------------------------------------------------------------------
  * Name der Prozedur : GesamtBilder
  * Berechnet die Anzahl aller Bilder und die Gesamtgröße aller Bilder
  * @author Sven Bohnstedt (wi4199), Paul Pohl (wi4628)
  * @param :  Iterator it: Iterator der Liste der Seiten  
  * @return : void
   ---------------------------------------------------------------------*/	
	private void GesamtBilder(Iterator it){
		while(it.hasNext()){
			this.groesseBilder = this.groesseBilder+((ReferenzierteObjekte)it.next()).getGroesse();
			this.anzahlBilder= this.anzahlBilder+1;}
		}
	
/* ---------------------------------------------------------------------
  * Name der Prozedur : GesamtCSS
  * Berechnet die Anzahl aller CSS und die Gesamtgröße aller CSS
  * @author Sven Bohnstedt (wi4199), Paul Pohl (wi4628)
  * @param :  Iterator it: Iterator der Liste der Seiten  
  * @return : void
   ---------------------------------------------------------------------*/	
	private void GesamtCSS(Iterator it){
		while(it.hasNext()){
			this.groesseCSS = this.groesseCSS+((ReferenzierteObjekte)it.next()).getGroesse();
			this.anzahlCSS= this.anzahlCSS+1;}
		}
	
/* ---------------------------------------------------------------------
  * Name der Prozedur : GesamtObjecte
  * Berechnet die Anzahl aller Objekte und die Gesamtgröße aller Objekte
  * @author Sven Bohnstedt (wi4199), Paul Pohl (wi4628)
  * @param :  Iterator it: Iterator der Liste der Seiten  
  * @return : void
   ---------------------------------------------------------------------*/	
	private void GesamtObjecte(Iterator it){
		while(it.hasNext()){
			this.groesseObject = this.groesseObject+((ReferenzierteObjekte)it.next()).getGroesse();
			this.anzahlObject= this.anzahlObject+1;}
		}

/* ---------------------------------------------------------------------
  * Name der Prozedur : GesamtApplet
  * Berechnet die Anzahl aller Applets und die Gesamtgröße aller Applets
  * @author Sven Bohnstedt (wi4199), Paul Pohl (wi4628)
  * @param :  Iterator it: Iterator der Liste der Seiten  
  * @return : void
   ---------------------------------------------------------------------*/	
	private void GesamtApplet(Iterator it){
		while(it.hasNext()){
			this.groesseApplet = this.groesseApplet+((ReferenzierteObjekte)it.next()).getGroesse();
			this.anzahlApplet= this.anzahlApplet+1;}
		}
	
/* ---------------------------------------------------------------------
  * Name der Prozedur : GesamtScript
  * Berechnet die Anzahl aller Skripte, und die Gesamtgröße aller Skripte
  * @author Sven Bohnstedt (wi4199), Paul Pohl (wi4628)
  * @param :  Iterator it: Iterator der Liste der Seiten  
  * @return : void
   ---------------------------------------------------------------------*/	
	private void GesamtScript(Iterator it){
		while(it.hasNext()){
			this.groesseScript = this.groesseScript+((ReferenzierteObjekte)it.next()).getGroesse();
			this.anzahlScript= this.anzahlScript+1;}
		}

/* ---------------------------------------------------------------------
  * Name der Prozedur : GesamtHintergrund
  * Berechnet die Anzahl aller Hintergruende, und die Gesamtgröße aller Hintergruende
  * @author Sven Bohnstedt (wi4199), Paul Pohl (wi4628)
  * @param :  Iterator it: Iterator der Liste der Seiten  
  * @return : void
   ---------------------------------------------------------------------*/	
	private void GesamtHintergrund(Iterator it){
		while(it.hasNext()){
			this.groesseHintergrund = this.groesseHintergrund+((ReferenzierteObjekte)it.next()).getGroesse();
			this.anzahlHintergrund= this.anzahlHintergrund+1;}
		}

	
/* ---------------------------------------------------------------------
  * Name der Prozedur : GesamtwerteBerechnen
  * Durchläuft die Liste der Seiten und ruft Prozeduren auf, um die Gesamtwerte zu berechnen 
  * @author Sven Bohnstedt (wi4199), Paul Pohl (wi4628)
  * @param :  -  
  * @return : void
   ---------------------------------------------------------------------*/	
	public void GesamtwerteBerechnen(){
		ArrayList temp = seitenInhalte;
		Iterator it= temp.iterator();
		int i=0;
		while(it.hasNext()){
		SeitenElement temp2 = ((SeitenElement)it.next());	
        GesamtLinks(temp2.getLinks().iterator());     
        GesamtBilder(temp2.getBilder().iterator());     
        GesamtCSS(temp2.getCss().iterator());     
        GesamtScript(temp2.getScript().iterator());     
        GesamtApplet(temp2.getApplet().iterator());     
        GesamtObjecte(temp2.getObjekt().iterator());     
        GesamtHintergrund(temp2.getBody().iterator());
        }     
	}


/* ---------------------------------------------------------------------
  * Name der Funktion : GesamtgroesseAllerSeiten
  * Addiert die Größe aller Seiten ohne inkludierte Objekte
  * @author Sven Bohnstedt (wi4199), Paul Pohl (wi4628)
  * @param :  -  
  * @return : void
   ---------------------------------------------------------------------*/	
	public long GesamtgroesseAllerSeiten(){
		return GroesseAllerInkludierten()+GroesseAllerSeitenohne();
	}
	

/* ---------------------------------------------------------------------
  * Name der Funktion : zaehleSeiten
  * Zählt die analysierten Seiten
  * @author Sven Bohnstedt (wi4199), Paul Pohl (wi4628)
  * @param : ArrayList liste  
  * @return : int : Anzahl der Seiten
   ---------------------------------------------------------------------*/	
	public int zaehleSeiten( ArrayList liste){
		
		Iterator it= liste.iterator();
		int i=0;
		while(it.hasNext()){
			it.next();
		    // System.out.println(i);
			i++;
		}
		return i;
	}

	
/* ---------------------------------------------------------------------
  * Name der Funktion : AusgabeGesamtwerte
  * Erstellt eine HTML Tabelle mit den berechneten Gesamtwerten
  * @author Sven Bohnstedt (wi4199), Paul Pohl (wi4628)
  * @param :  -
  * @return : String : HTML Tabelle mit berechneten Gesamtwerten
   ---------------------------------------------------------------------*/	
	private String AusgabeGesamtwerte(){
		this.GesamtwerteBerechnen();
    String rueck="<table align=\"center\" border=1 rules=\"none\" summary=\"Designtabelle\">";
    		rueck += "<tr align=\"center\"><td><br><br><br><h2>Gesamtergebnis der analysierten Seiten:</h2></td></tr>";
    		rueck+="<tr align=\"center\"><td><a href=\"#index\">Zur Übersicht der untersuchten Seiten</a></td></tr>";
 			rueck+= "<tr><td><table align=\"center\" summary=\"Designtabelle\" border=1>";
 			rueck = rueck+"<tr><td colspan=2 bgcolor=#415D79 align=\"center\"><h2>Übersicht</h2></td><td></td></tr>";
    		rueck = rueck+"<tr><td><b>Anzahl analysierter Seiten: </b></td><td align=\"right\">"+this.zaehleSeiten(this.seitenInhalte)+"</td></tr>";
    		rueck = rueck+"<tr><td><b>Gesamtgröße der analysierten Seiten exklusive inkludierte Objekte: </b></td><td align=\"right\">"+this.GroesseAllerSeitenohne()+" Byte(s)</td></tr>";
			rueck = rueck+"<tr><td><b>Gesamtgröße der analysierten Seiten inklusive inkludierter Objekte: </b></td><td align=\"right\">"+this.GesamtgroesseAllerSeiten()+" Byte(s)</td></tr>";
    		rueck = rueck+"<tr><td><b>Gesamtanzahl der Tags: </b></td><td align=\"right\">"+this.AnzahlAllerTags()+"</td></tr>";
    		rueck = rueck+"<tr><td><b>Gesamtgröße der inkludierten Objekte: </b></td><td align=\"right\">"+this.GroesseAllerInkludierten()+" Byte(s)</td></tr>";
    		rueck = rueck+"<tr><td colspan=2 bgcolor=#5284A6 align=\"center\">Aufgeteilt in:</td><td></td></tr>";
    		rueck = rueck+"<tr><td><b>Gesamtanzahl der Bilder: </b></td><td align=\"right\">"+this.anzahlBilder+"</td></tr>";
			rueck = rueck+"<tr><td><b>Gesamtgröße der Bilder: </b></td><td align=\"right\">"+this.groesseBilder+" Byte(s)</td></tr>";
			rueck = rueck+"<tr><td><b>Gesamtanzahl der Links: </b></td><td align=\"right\">"+this.anzahlLinks+"</td></tr>";
			rueck = rueck+"<tr><td><b>Gesamtanzahl der CSS Objekte: </b></td><td align=\"right\">"+this.anzahlCSS+"</td></tr>";
			rueck = rueck+"<tr><td><b>Gesamtgröße der CSS Objekte: </b></td><td align=\"right\">"+this.groesseCSS+" Byte(s)</td></tr>";
			rueck = rueck+"<tr><td><b>Gesamtanzahl der Applets: </b></td><td align=\"right\">"+this.anzahlApplet+"</td></tr>";
			rueck = rueck+"<tr><td><b>Gesamtgröße der Applets: </b></td><td align=\"right\">"+this.groesseApplet+" Byte(s)</td></tr>";
			rueck = rueck+"<tr><td><b>Gesamtanzahl der Hintergrundobjekte: </b></td><td align=\"right\">"+this.anzahlHintergrund+"</td></tr>";
			rueck = rueck+"<tr><td><b>Gesamtgröße der Hintergrundobjekte: </b></td><td align=\"right\">"+this.groesseHintergrund+" Byte(s)</td></tr>";
			rueck = rueck+"<tr><td><b>Gesamtanzahl der Skripte: </b></td><td align=\"right\">"+this.anzahlScript+"</td></tr>";
			rueck = rueck+"<tr><td><b>Gesamtgröße der Skripte: </b></td><td align=\"right\">"+this.groesseScript+" Byte(s)</td></tr>";
			rueck = rueck+"<tr><td><b>Gesamtanzahl der Objekte: </b></td><td align=\"right\">"+this.anzahlObject+"</td></tr>";
			rueck = rueck+"<tr><td><b>Gesamtgröße der Objekte: </b></td><td align=\"right\">"+this.groesseObject+" Byte(s)</td></tr>";
			
	 return rueck+"</table></td></tr>";
	}
	
	
/* ---------------------------------------------------------------------
  * Name der Funktion : linksErstellen
  * Erstellt eine HTML Tabelle mit den Links zu den Analyseergebnissen der Seiten
  * @author Sven Bohnstedt (wi4199), Paul Pohl (wi4628)
  * @param :  -
  * @return : String : HTML Tabelle mit den Links zu den Analyseergebnissen der Seiten
   ---------------------------------------------------------------------*/	
	private String linksErstellen(){
		int i=0;
    String rueck = "<tr align=\"center\"><td><a name=index></a><br><br><br><b>Folgende Seiten wurden analysiert:</b><br><br><table align=\"center\" rules=\"none\" summary=\"Designtabelle\" border=1>";

    Iterator it= this.seitenInhalte.iterator();
		while (it.hasNext()){
			i = i+1;
			rueck = rueck+"<tr align=\"center\"><td><a href=\"#Seite"+i+"\""+">"+((SeitenElement)it.next()).getName()+"</a> </td></tr>";

		}	
	 return rueck+"</table></td></tr>";
	}

/* ---------------------------------------------------------------------
  * Name der Funktion : analyseErgebnis
  * Ruft die HTMLAusgabe Funktion für jede analysierte Seite auf
  * @author Sven Bohnstedt (wi4199), Paul Pohl (wi4628)
  * @param :  -
  * @return : String : Analyseergebnisse aller Seiten 
  ---------------------------------------------------------------------*/	
	private String analyseErgebnis(){
		String rueck = "";
		int i = 0;
		Iterator it= this.seitenInhalte.iterator();
		while (it.hasNext()){
			i = i+1;
		
			rueck =rueck+"<tr align=\"center\"><td><a name=Seite"+i+"></a>"+((SeitenElement)it.next()).ausgabe_htmlSeite()+"";
			rueck = rueck+"<a href=#index>zur Übersicht</a></td></tr>";
		}	
      return rueck+"</table>";
	}
	
	
}
