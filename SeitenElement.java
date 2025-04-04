/* ---------------------------------------------------------------------
 * Name der Klasse: SeitenElement
 * Beinhaltet Informationen einer gescannten Seite
 * @author Sven Bohnstedt (wi4199), Paul Pohl (wi4628)
 * Erstellt am 07.06.2004
   ---------------------------------------------------------------------*/

import java.util.*;
import java.net.*;
import java.text.*;

public class SeitenElement {

  //Listen mit Inhalten der Seite	
  private HashSet body   = new HashSet();
  private HashSet links  = new HashSet();
  private HashSet bilder = new HashSet();
  private HashSet css    = new HashSet();
  private HashSet script = new HashSet();
  private HashSet applet = new HashSet();
  private HashSet objekt = new HashSet();
  private HashSet frame  = new HashSet();
  //Größe aller Elemente auf der Seite
  private long groesse;
  //Anzahl der Tags
  private long anzahltags;
  //Pfad der Seite
  private String urlpfad;
  private URL url;
  //Name der Seite
  private String name;
  //Anzahl der Wörter
  private int anzahlwoerter;
  public String inhalt; // Hilfsvariable zum testen
  //Größe der Seite ohne Includierungen
  private long groesseseite;
  // Groesse des Contents in Byte
  private long groesseinhaltbyte;
  // Groesse der Tags in Byte
  public long groessetagsbyte;
  
  /* ---------------------------------------------------------------------
   * Name der Funktion : getBody
   * Liefert Liste der im Body inkludierten Objekte
   * @author Sven Bohnstedt (wi4199), Paul Pohl (wi4628)
   * @param : - 
   * @return : HashSet : Liste der im Body inkludierten Objekte  
    ---------------------------------------------------------------------*/	 
  public HashSet getBody(){
  	return this.body;
  }

  /* ---------------------------------------------------------------------
   * Name der Funktion : getGroesseSeite
   * Liefert die Größe der Seite
   * @author Sven Bohnstedt (wi4199), Paul Pohl (wi4628)
   * @param : - 
   * @return : long : Größe der Seite  
    ---------------------------------------------------------------------*/	 
  public long getGroesseSeite(){
  		return this.groesseseite;
  	}
  
  /* ---------------------------------------------------------------------
   * Name der Funktion : getFrames
   * Liefert Menge der inkludierten Framedateien
   * @author Sven Bohnstedt (wi4199), Paul Pohl (wi4628)
   * @param : - 
   * @return : long : Größe der Seite  
    ---------------------------------------------------------------------*/
  
  public HashSet getFrames(){
	return this.frame;
  }
/* ---------------------------------------------------------------------
 * Name der Funktion : getLinks
 * Liefert Liste der Links dieser Seite
 * @author Sven Bohnstedt (wi4199), Paul Pohl (wi4628)
 * @param : - 
 * @return : HashSet : Liste der Links dieser Seite  
  ---------------------------------------------------------------------*/	 
public HashSet getLinks(){
	return this.links;
  }

/* ---------------------------------------------------------------------
 * Name der Funktion : getBilder
 * Liefert Liste der Bilder dieser Seite
 * @author Sven Bohnstedt (wi4199), Paul Pohl (wi4628)
 * @param : - 
 * @return : HashSet : Liste der Bilder dieser Seite 
  ---------------------------------------------------------------------*/	 
public HashSet getBilder(){
	return this.bilder;
  }

/* ---------------------------------------------------------------------
 * Name der Funktion : getCSS
 * Liefert Liste der CSS Objekte dieser Seite
 * @author Sven Bohnstedt (wi4199), Paul Pohl (wi4628)
 * @param : - 
 * @return : HashSet : Liste der CSS Objekte dieser Seite  
  ---------------------------------------------------------------------*/	 
public HashSet getCss(){
	return this.css;
  }

/* ---------------------------------------------------------------------
 * Name der Funktion : getScript
 * Liefert Liste der CSS Objekte dieser Seite
 * @author Sven Bohnstedt (wi4199), Paul Pohl (wi4628)
 * @param : - 
 * @return : HashSet : Liste der CSS Objekte dieser Seite  
  ---------------------------------------------------------------------*/	 
  public HashSet getScript(){
	return this.script;
  }

  /* ---------------------------------------------------------------------
   * Name der Funktion : getApplet
   * Liefert Liste der Applets dieser Seite
   * @author Sven Bohnstedt (wi4199), Paul Pohl (wi4628)
   * @param : - 
   * @return : HashSet : Liste der Applets dieser Seite  
    ---------------------------------------------------------------------*/	  
 public HashSet getApplet(){
	return this.applet;
  }
 
 
 /* ---------------------------------------------------------------------
  * Name der Funktion : getObjekt
  * Liefert Liste der Objekte dieser Seite
  * @author Sven Bohnstedt (wi4199), Paul Pohl (wi4628)
  * @param : - 
  * @return : HashSet : Liste der Objekte dieser Seite  
   ---------------------------------------------------------------------*/	 
  public HashSet getObjekt(){
	return this.objekt;
  }

  
  /* ---------------------------------------------------------------------
   * Name der Funktion : getGroesse
   * Liefert die Größe dieser Seite
   * @author Sven Bohnstedt (wi4199), Paul Pohl (wi4628)
   * @param : - 
   * @return : long : Größe dieser Seite 
    ---------------------------------------------------------------------*/	 
  public long getGroesse(){
	return this.groesse;
  }
  /* ---------------------------------------------------------------------
   * Name der Funktion : getTags
   * Liefert Liste der Tags dieser Seite
   * @author Sven Bohnstedt (wi4199), Paul Pohl (wi4628)
   * @param : - 
   * @return : long : Liste der Tags dieser Seite
    ---------------------------------------------------------------------*/	 
  public long getTags(){
	return this.anzahltags;
  }
  
  /* ---------------------------------------------------------------------
   * Name der Funktion : getPfad
   * Liefert den Pfad dieser Seite
   * @author Sven Bohnstedt (wi4199), Paul Pohl (wi4628)
   * @param : - 
   * @return : String : Pfad dieser Seite
    ---------------------------------------------------------------------*/  
  public String getPfad(){
	return this.urlpfad;
  }
  
  /* ---------------------------------------------------------------------
   * Name der Funktion : getTags
   * Liefert den Namen dieser Seite
   * @author Sven Bohnstedt (wi4199), Paul Pohl (wi4628)
   * @param : - 
   * @return : String : Name dieser Seite
    ---------------------------------------------------------------------*/  
  public String getName(){
	return this.name;
  }
  
  /* ---------------------------------------------------------------------
   * Name der Funktion : getWoerter
   * Liefert Anzahl der Woerter
   * @author Sven Bohnstedt (wi4199), Paul Pohl (wi4628)
   * @param : - 
   * @return : HashSet : Anzahl der Wörter
    ---------------------------------------------------------------------*/
  
  
 public int getWoerter(){
 	return this.anzahlwoerter;
 }
 
 /* ---------------------------------------------------------------------
  * Name der Funktion : getWoerter
  * Liefert Anzahl der Woerter
  * @author Sven Bohnstedt (wi4199), Paul Pohl (wi4628)
  * @param : - 
  * @return : long : Groesse des Content in Byte
   ---------------------------------------------------------------------*/
 public long getContentByte(){
 	return this.groesseinhaltbyte;
 }
  
 
 /* ---------------------------------------------------------------------
  * Name der Prozedur : setContentByte
  * Groesse in Byte des Contents
  * @author Sven Bohnstedt (wi4199), Paul Pohl (wi4628)
  * @param : int anzahlwoerter 
  * @return : void
   ---------------------------------------------------------------------*/
 public void setContentByte(long contentbyte){
 	this.groesseinhaltbyte=contentbyte;
 }
 
 /* ---------------------------------------------------------------------
  * Name der Prozedur : setWoerter
  * Initialisieren mit der Anzahl der Wörter
  * @author Sven Bohnstedt (wi4199), Paul Pohl (wi4628)
  * @param : int anzahlwoerter 
  * @return : void
   ---------------------------------------------------------------------*/ 
 public void setWoerter(int anzahlwoerter){
 	this.anzahlwoerter=anzahlwoerter;
 }
 
 /* ---------------------------------------------------------------------
  * Name der Prozedur : setWoerter
  * Initialisieren mit der Anzahl der Frames
  * @author Sven Bohnstedt (wi4199), Paul Pohl (wi4628)
  * @param : ReferenzierteObjekte frame
  * @return : void
   ---------------------------------------------------------------------*/
 public void setFrames(ReferenzierteObjekte frame){
 	this.frame.add(frame);
 }
 
 /* ---------------------------------------------------------------------
  * Name der Prozedur : setBody
  * Hinzufügen des inkludierten Objekte im Body zur Liste 
  * @author Sven Bohnstedt (wi4199), Paul Pohl (wi4628)
  * @param : ReferenzierteObjekte body 
  * @return : void
   ---------------------------------------------------------------------*/
 public void setBody(ReferenzierteObjekte body){
  	this.body.add(body);
  }
  
 
 /* ---------------------------------------------------------------------
  * Name der Prozedur : setLinks
  * Hinzufügen der Links zur Liste 
  * @author Sven Bohnstedt (wi4199), Paul Pohl (wi4628)
  * @param : ReferenzierteObjekte links 
  * @return : void
   ---------------------------------------------------------------------*/
  public  void setLinks(ReferenzierteObjekte links){
	this.links.add(links);
  }
  
  /* ---------------------------------------------------------------------
   * Name der Prozedur : setBilder
   * Hinzufügen der Bilder zur Liste 
   * @author Sven Bohnstedt (wi4199), Paul Pohl (wi4628)
   * @param : ReferenzierteObjekte bilder 
   * @return : void
    ---------------------------------------------------------------------*/
  public  void setBilder(ReferenzierteObjekte bilder){
	this.bilder.add(bilder);
  }

  /* ---------------------------------------------------------------------
   * Name der Prozedur : setCSS
   * Hinzufügen der CSS Objekte zur Liste 
   * @author Sven Bohnstedt (wi4199), Paul Pohl (wi4628)
   * @param : ReferenzierteObjekte css 
   * @return : void
    ---------------------------------------------------------------------*/  
  public  void setCss(ReferenzierteObjekte css){
	this.css.add(css);
  }
  
  /* ---------------------------------------------------------------------
   * Name der Prozedur : setScript
   * Hinzufügen der scripte Objekte zur Liste 
   * @author Sven Bohnstedt (wi4199), Paul Pohl (wi4628)
   * @param : ReferenzierteObjekte script 
   * @return : void
    ---------------------------------------------------------------------*/
  public  void setScript(ReferenzierteObjekte script){
	this.script.add(script);
  }
  
  /* ---------------------------------------------------------------------
   * Name der Prozedur : setApplet
   * Hinzufügen der Applets zur Liste 
   * @author Sven Bohnstedt (wi4199), Paul Pohl (wi4628)
   * @param : ReferenzierteObjekte applet 
   * @return : void
    ---------------------------------------------------------------------*/
  public  void setApplet(ReferenzierteObjekte applet){
	this.applet.add(applet);
  }
  
  /* ---------------------------------------------------------------------
   * Name der Prozedur : setObjekt
   * Hinzufügen der Objekte zur Liste 
   * @author Sven Bohnstedt (wi4199), Paul Pohl (wi4628)
   * @param : ReferenzierteObjekte objekt 
   * @return : void
    ---------------------------------------------------------------------*/
  public  void setObjekt(ReferenzierteObjekte objekt){
	this.objekt.add(objekt);
  }
  
  /* ---------------------------------------------------------------------
   * Name der Prozedur : setGroesse
   * Initialisieren mit der Größe der Seite
   * @author Sven Bohnstedt (wi4199), Paul Pohl (wi4628)
   * @param : long groesse
   * @return : void
    ---------------------------------------------------------------------*/
  public  void setGroesse(long groesse){
	this.groesse=groesse;
  }
  
  /* ---------------------------------------------------------------------
   * Name der Prozedur : setTags
   * Initialisieren mit der Anzahl der Tags
   * @author Sven Bohnstedt (wi4199), Paul Pohl (wi4628)
   * @param : long tags 
   * @return : void
    ---------------------------------------------------------------------*/
  public  void setTags(long tags){
	this.anzahltags=tags;
  }
  
  /* ---------------------------------------------------------------------
   * Name der Prozedur : GroesseSeite
   * Initialisieren mit der Größe der Seite ohne inkludierte Objekte 
   * @author Sven Bohnstedt (wi4199), Paul Pohl (wi4628)
   * @param : long groesseseite 
   * @return : void
    ---------------------------------------------------------------------*/
 public void setGroesseSeite(long groesseseite){
 	this.groesseseite=groesseseite;
  }
 
 /* ---------------------------------------------------------------------
  * Name der Prozedur : setPfad
  * Setzen des Pfades 
  * @author Sven Bohnstedt (wi4199), Paul Pohl (wi4628)
  * @param : String pfad 
  * @return : void
   ---------------------------------------------------------------------*/
  public  void setPfad(String pfad){
	this.urlpfad=pfad;
  }
  
  /* ---------------------------------------------------------------------
   * Name der Prozedur : setName
   * Initialisieren mit dem Namen der Seite 
   * @author Sven Bohnstedt (wi4199), Paul Pohl (wi4628)
   * @param : String name 
   * @return : void
    ---------------------------------------------------------------------*/
  public  void setName(String name){
	 this.name=name;
  }
  
  /* ---------------------------------------------------------------------
   * Name der Prozedur : setTags
   * Initialisieren mit der Anzahl der Tags der Seite 
   * @author Sven Bohnstedt (wi4199), Paul Pohl (wi4628)
   * @param : int tags 
   * @return : void
    ---------------------------------------------------------------------*/
  public void setTags(int tags){
  	this.anzahltags=tags;
  }
  
  /* ---------------------------------------------------------------------
   * Name der Prozedur : ausgabe
   * Ausgabe der Analyseergebnisse auf der Konsole
   * @author Sven Bohnstedt (wi4199), Paul Pohl (wi4628)
   * @param : -
   * @return : void
    ---------------------------------------------------------------------*/
  public void ausgabe(){
  	

  	String art = "";
	Iterator it=null;
	System.out.println("\n\n---------------------------------------------");
	System.out.println("---------------------------------------------");
	System.out.println("Seite: "+this.name);;
	System.out.println("Anzahl Tags: "+this.anzahltags);
	System.out.println("Anzahl Wörter: "+this.anzahlwoerter);
	System.out.println("Groesse der Seite pur: "+ this.groesseseite);
	for(int i=1;i<8;i++) {
		
	switch(i){
		
		case 1:
			if (this.bilder!=null) it = this.bilder.iterator();
			art="Bilder";
			
			break;
		case 2: 
			art="Javascript";
			if(this.script!=null) it = this.script.iterator();
			break;
		case 3:
			art="CSS";
			if(this.css!=null) it = this.css.iterator();
			break;
		case 4:
			art="Objekt";
			if(this.objekt!=null) it = this.objekt.iterator();
			break;
		case 5:
			art="Applet";
			if(this.applet!=null) it = this.applet.iterator();
			break;
		case 6:
			art="Hintergrund";
			if(this.body!=null) it = this.body.iterator();
			break;
		case 7:
			art="Links";
			if(this.links!=null) it = this.links.iterator();
			break;
	}
	
	if (it!=null){	
	System.out.println("\n" +art+ " dieser Seite:");
	while (it.hasNext()){
		ReferenzierteObjekte temp = (ReferenzierteObjekte)(it).next();
		System.out.println("Name       : "+temp.getName());
		System.out.println("Verzeichnis: "+temp.getVerzeichnis());
		System.out.println("Groesse    : "+temp.getGroesse()+"\n");
		System.out.println("---------------------------------------------");
    }
	}
  } 
	System.out.println("Gesamtgroesse: "+ this.groesse);
  
  
  }

  
  /* ---------------------------------------------------------------------
   * Name der Funktion : ausgabe_htmlSeite
   * Aufbereitung der Analyseergebnisse einer Seite in einer HTML Tabelle
   * @author Sven Bohnstedt (wi4199), Paul Pohl (wi4628)
   * @param : -
   * @return : String : HTML Tabelle mit den Analyseergebnissen einer Seite
    ---------------------------------------------------------------------*/  
  public String ausgabe_htmlSeite(){
  	
    String rueck = "";
  	String art = "";
	Iterator it=null;
	rueck = rueck+ "<br><br><h3 align=\"center\">Ergebnisse der Seite: "+" "+this.name+"</h3>";	
	rueck = rueck + "<table align=\"center\" border=1 summary=\"Designtabelle\" rules=\"none\">";
	rueck = rueck+"<tr><td colspan=2 bgcolor=#415D79 align=\"center\" valign=\"middle\"><h3> Übersicht</h3></td><td></td></tr>";
	rueck = rueck+"<tr><td>Größe des Inhalts in Byte: </td><td align=\"right\">"+this.groesseinhaltbyte+"</td></tr>";
	rueck = rueck+"<tr><td>prozentualer Anteil Inhalt an der Gesamtgröße: </td><td align=\"right\">"+berechneContentZuGroesse()+"</td></tr>";
	rueck = rueck+"<tr><td>Anzahl der Tags: </td><td align=\"right\">"+this.anzahltags+"</td></tr>";
	rueck = rueck+"<tr><td>Anzahl der Wörter: </td><td align=\"right\">"+this.anzahlwoerter+"</td></tr>";
	rueck = rueck+"<tr><td>Verhältnis Inhalt/Tags:</td><td align=\"right\">"+rechenVerhältnis(this.anzahlwoerter,this.anzahltags)+"</td></tr>";
	rueck = rueck+"<tr><td>Größe der Seite ohne Includierungen:</td><td align=\"right\">"+this.groesseseite+" Byte(s)</td></tr> ";
	rueck = rueck+"<tr><td>Gesamtgröße mit allen Includierungen:</td><td align=\"right\">"+(this.groesse+this.groesseseite)+" Byte(s)</td></tr>";
	rueck = rueck+"<tr><td colspan=2  align=\"center\" valign=\"middle\"><hr width=\"100%\"></td><td></td></tr>";
	rueck = rueck+"<tr><td>Übertragungsgeschwindigkeit Modem (33,6 kbit/s)</td><td align=\"right\">"+rechenZeit(this.groesse+this.groesseseite,1)+" s</td></tr> ";
	rueck = rueck+"<tr><td>Übertragungsgeschwindigkeit Modem (56 kbit/s)</td><td align=\"right\">"+rechenZeit(this.groesse+this.groesseseite,2)+" s</td></tr> ";
	rueck = rueck+"<tr><td>Übertragungsgeschwindigkeit Modem (ISDN)</td><td align=\"right\">"+rechenZeit(this.groesse+this.groesseseite,3)+" s</td></tr> ";
	rueck = rueck+"<tr><td>Übertragungsgeschwindigkeit Modem (DSL 768 kbit/s)</td><td align=\"right\">"+rechenZeit(this.groesse+this.groesseseite,4)+" s</td></tr>";
	rueck = rueck+"<tr><td colspan=2  align=\"center\" valign=\"middle\"><hr width=\"100%\"></td><td></td></tr>";
	for(int i=1;i<9;i++) {
		
	switch(i){
		
		case 1:
			if (this.bilder!=null) it = this.bilder.iterator();
			art="Bilder";
			
			break;
		case 2: 
			art="Javascript";
			if(this.script!=null) it = this.script.iterator();
			break;
		case 3:
			art="CSS";
			if(this.css!=null) it = this.css.iterator();
			break;
		case 4:
			art="Objekt";
			if(this.objekt!=null) it = this.objekt.iterator();
			break;
		case 5:
			art="Applet";
			if(this.applet!=null) it = this.applet.iterator();
			break;
		case 6:
			art="Hintergrund";
			if(this.body!=null) it = this.body.iterator();
			break;
		case 7:
			art="Links";
			if(this.links!=null) it = this.links.iterator();
			break;
		case 8:
			art="Frames";
			if(this.frame!=null) it = this.frame.iterator();
			break;
	}
	
	if (it!=null){	
		if (it.hasNext()){	
		rueck = rueck+"<tr><td colspan=2 bgcolor=#5284A6 align=\"center\">"+art+" dieser Seite:</td><td></td></tr>";
		//rueck = rueck+"<tr><td>  </td><td>  </td></tr>";
		int j =1;
		if (art.equals("Links")) art = "Link";
		if (art.equals("Bilder")) art = "Bild";
		
	while (it.hasNext()){
		ReferenzierteObjekte temp = (ReferenzierteObjekte)(it).next();
		rueck = rueck+"<tr><td colspan=2 bgcolor=#777777 align=\"center\">"+art+" "+j+"</td><td></td></tr>";
		rueck = rueck+"<tr ><td>Name: </td><td align=\"right\">"+temp.getName()+"</td></tr>";
		rueck = rueck+"<tr ><td>Verzeichnis: </td><td align=\"right\">"+temp.getVerzeichnis()+"</td></tr>";
		if(!art.equals("Link"))rueck = rueck+"<tr ><td>Größe:</td><td align=\"right\">"+(temp.getGroesse())+"</td></tr>";
	 j= j+1;
	}
	rueck = rueck+"<tr><td colspan=2 bgcolor=#777777 align=\"center\">&nbsp;</td><td></td></tr>";
		}
	else rueck = rueck+"<tr><td>"+art+" dieser Seite</td><td align=\"right\"> keine vorhanden</td></tr>";

	}
	
  } 
	
	rueck =rueck+"</table><br><a href="+this.name+" target=\"_blank\"><b>analysierte Seite anschauen</b></a><br>";
	return rueck;  
  }


  /* ---------------------------------------------------------------------
   * Name der Funktion : rechenZeit
   * Liefert die Übertragungszeit je nach Verbindungsart
   * @author Sven Bohnstedt (wi4199), Paul Pohl (wi4628)
   * @param : long groesse 
   * @param : int art 
   * @return : String
    ---------------------------------------------------------------------*/    
  private String rechenZeit(long groesse, int art){
    double help=0;
   	switch(art){
   		case 1: //Modem 33.6 kbit/s
   			help= (double)groesse/(33600/8);
   			break;
   		case 2: // Modem 56 kbit/s
   			help= (double)groesse/(56000/8);
   			break;
   		case 3: // isdn
   			 help= (double)groesse/(64000/8);
   			 break;
   		case 4: // dsl 768	
   			help= (double)groesse/(768000);
   			break;
   	}
   	DecimalFormat df = new DecimalFormat( "###,##0.00" );
   	return df.format(help);
   }

  
  /* ---------------------------------------------------------------------
   * Name der Funktion : rechenVerhältnis
   * Liefert das Verhältnis Wörter zu Tags
   * @author Sven Bohnstedt (wi4199), Paul Pohl (wi4628)
   * @param : long woerter 
   * @param : long tags 
   * @return : String
    ---------------------------------------------------------------------*/      
  private String rechenVerhältnis(long woerter, long tags){
   	  double help=0;
   	 	help= (double)woerter/tags;
   	 	DecimalFormat df = new DecimalFormat( "###,##0.00" );
   	 	return df.format(help);
   	 }  
  private String  berechneContentZuGroesse(){
  	double temp=0; //groesse des anteils content in %
  	temp=(double)groesseinhaltbyte/(groessetagsbyte);
  	//System.out.println("Groesse: "+this.groesse);
  	//System.out.println("Groesse Byte: "+this.groessebyte);
  	//System.out.println(this.groesse);
  	DecimalFormat df = new DecimalFormat("###,##0.00");
  	return df.format(temp*100)+" %";
  }

}
