import java.io.*;
import java.util.*;
import java.net.*;
import be.arci.html.*;

/* ---------------------------------------------------------------------
 * Name der Klasse: OnlineParser 
 * Parsen der aktuellen Seite
 * @author Sven Bohnstedt (wi4199), Paul Pohl (wi4628)
 * Erstellt am 10.06.2004
   ---------------------------------------------------------------------*/

public class OnlineParser extends makePfad
{
    
	SeitenElement seite = new SeitenElement();
	long groesse;
	long anzahltags;
	String urlpfad;
	URL url;
	String bildpfad;
	int anzahlwoerter;
	long groesseTags;
	HTMLScanner hs;
	/* ---------------------------------------------------------------------
	  * Name des Konstruktors : OnlineParser
	  * Initialisierung der Variablen und Aufruf der Rekursion
	  * @author Sven Bohnstedt (wi4199), Paul Pohl (wi4628)
	   ---------------------------------------------------------------------*/	
	public OnlineParser(){}
	
	
 /* ---------------------------------------------------------------------
  * Name der Prozedur : parse
  * Parsen der übergebenen Seite
  * @author Sven Bohnstedt (wi4199), Paul Pohl (wi4628)
  * @param : String urls : zu parsende Seite 
  * @return : void
   ---------------------------------------------------------------------*/
	public void parse (String urls){	
	HashSet	tempbild= new HashSet(); // Hashset fuer die gefundenen bilder ohne test auf vorhandensein
	HashSet tempscript = new HashSet(); // javascript
	HashSet tempcss = new HashSet(); // css
	HashSet tempobject = new HashSet(); // objecte
	HashSet tempapplet = new HashSet();// applets
	HashSet tempbody = new HashSet(); // body
	HashSet templinks = new HashSet(); //links
	HashSet tempframes = new HashSet();// frames
	
	
	String[] asTagNames = new String[] { null, "IMG", "A", "BODY", "FRAME", "LINK", "SCRIPT", "OBJECT", "APPLET"};
		try {
			url = new URL(urls);	
			//HTMLScanner hs = new HTMLScanner(url); rausgenommen zum testen
			this. hs = new HTMLScanner(url);
			this.seite.groessetagsbyte=getTagGroesseFormatiert(hs); 
			this.seite.setContentByte(getBytesInhalt(hs));
			this.seite.setWoerter(getAnzWoerter(hs));
			this.seite.setPfad(url.getPath());
			this.seite.setTags(getAnzTags(hs));// Tags der aktuellen Seite
		    this.seite.setGroesseSeite(url.openConnection().getContentLength());// groesse der aktuellen Seite
			this.seite.setGroesseSeite(leseGroesseSeite(url));
			this.urlpfad = url.getHost();
			this.urlpfad = this.urlpfad + url.getPath(); //Pfad der Datei
			HTMLTag[] tags = hs.getTags(asTagNames, false);
				for (int j = 0; j < tags.length; j++)
				{
					String link = null;
					switch (tags[j].iID)
					{
						case 1: //IMG
							if (tags[j] !=null && tags[j].getAttribute("src")!=null) {  //Bilder der Seite
								if (isURL2(tags[j].getAttribute("src"))) tempbild.add(tags[j].getAttribute("src"));
									else tempbild.add(absPfad(tags[j].getAttribute("src"),urlpfad));
							}	break;
						case 2: // links
							if (tags[j] !=null && tags[j].getAttribute("href")!=null){  //Links der Seite
								if (isURL2(tags[j].getAttribute("href"))) templinks.add(tags[j].getAttribute("href"));
									else templinks.add(absPfad(tags[j].getAttribute("href"),urlpfad));}
								break;
						case 6: // javascript
							if (tags[j] !=null && tags[j].getAttribute("src")!=null){
								if (isURL2(tags[j].getAttribute("src"))) tempscript.add(tags[j].getAttribute("src"));
									else tempscript.add(absPfad(tags[j].getAttribute("src"),urlpfad));}
								break;
						case 5: //css
							if (tags[j] !=null && tags[j].getAttribute("href")!=null)
							{								
								if (isURL2(tags[j].getAttribute("href"))) tempcss.add(tags[j].getAttribute("href"));
									else tempcss.add(absPfad(tags[j].getAttribute("href"),urlpfad));}
								break;
						case 7:// multimediaobjekte
							if (tags[j] !=null && tags[j].getAttribute("data")!=null){
								if (isURL2(tags[j].getAttribute("data"))) tempobject.add(tags[j].getAttribute("data"));
									else tempobject.add(absPfad(tags[j].getAttribute("data"),urlpfad));}								
							break;
						case 8: //applets
							if (tags[j] !=null && tags[j].getAttribute("code")!=null)
							 if (tags[j].getAttribute("codebase")!=null){
							 	String t = tags[j].getAttribute("codebase");
							 	t+= tags[j].getAttribute("code");
								if (isURL2(t)) tempapplet.add(t);
								else tempapplet.add(absPfad(t,urlpfad));								
							 }	else{
								if (isURL2(tags[j].getAttribute("code"))) tempapplet.add(tags[j].getAttribute("code"));
								else tempapplet.add(absPfad(tags[j].getAttribute("code"),urlpfad));}															 	
							break;
						case 3: // background
							if (tags[j] !=null && tags[j].getAttribute("background")!=null){
								if (isURL2(tags[j].getAttribute("background"))) tempbody.add(tags[j].getAttribute("background"));
									else tempbody.add(absPfad(tags[j].getAttribute("background"),urlpfad));}															 									
								break;
						case 4:	// Frames	
							if (tags[j] !=null && tags[j].getAttribute("src")!=null) {  //Frames der Seite
								if (isURL2(tags[j].getAttribute("src"))) tempframes.add(tags[j].getAttribute("src"));
								else tempframes.add(absPfad(tags[j].getAttribute("src"),urlpfad));
					}	break;
						default: 
						break;
					}
				
				} // Ende for
		} catch (Exception e) { System.out.println( "keine gültige URL angegeben"); } //URL exceptions or IO exceptions
	 //  }
		
	groesseOrt(tempbild,1); // Uebergabe zum  Testen auf Url vorhanden und groesse  
	groesseOrt(tempscript,2);
	groesseOrt(tempcss,3);
	groesseOrt(tempobject,4);
	groesseOrt(tempapplet,5);
	groesseOrt(tempbody,6);
	groesseOrt(templinks,7);
	groesseOrt(tempframes,8);
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
	private void groesseOrt(HashSet liste, int art){	   
	   long filesize=0;
	   Iterator it = liste.iterator();
	   while (it.hasNext()){
	   	String temp = it.next().toString();
	   	try{ 
	   		URL url1 = new URL(temp);
	   		if (isURL(url1)){ 
                if (art!=7){// groesse der url bestimmen  
	   			 //filesize=url1.openConnection().getContentLength();} !!!! rausgenommen, weil .php nicht ging ersetzt durch
	   			filesize=leseGroesseSeite(url1);
                	}
                	//System.out.println("filesize1: "+filesize);}
                else filesize =0;
                //Erfassung des Objektnamens
	   			this.seite.setName(url1.getFile());
	   			//Erfassung der Größe des Objektes
	   			seite.setGroesse(seite.getGroesse()+filesize);
	   			//Erfassung der Informationen zu dem jeweiligen Objekt
	   			switch(art){
	   				case 1:
	   					this.seite.setBilder(new ReferenzierteObjekte(url1.getFile(),url1.getHost()+absPfad2(url1.getPath()),filesize));
	   					break;
	   				case 2:
	   					this.seite.setScript(new ReferenzierteObjekte(url1.getFile(),url1.getHost()+absPfad2(url1.getPath()),filesize));
	   					break;
	   				case 3:
	   					this.seite.setCss(new ReferenzierteObjekte(url1.getFile(),url1.getHost()+absPfad2(url1.getPath()),filesize));
	   					break;
	   				case 4:
	   					this.seite.setObjekt(new ReferenzierteObjekte(url1.getFile(),url1.getHost()+absPfad2(url1.getPath()),filesize));
	   					break;
	   				case 5:
	   					this.seite.setApplet(new ReferenzierteObjekte(url1.getFile(),url1.getHost()+absPfad2(url1.getPath()),filesize));
	   					break;
	   				case 6:
	   					this.seite.setBody(new ReferenzierteObjekte(url1.getFile(),url1.getHost()+absPfad2(url1.getPath()),filesize));
	   					break;
	   				case 7:
	   					this.seite.setLinks(new ReferenzierteObjekte(url1.getFile(),url1.getHost()+absPfad2(url1.getPath()),0));// null als groesse fuer referenzierte links, da sie nicht eingebunden werden
	   					break;
	   				case 8:
	   					this.seite.setFrames(new ReferenzierteObjekte(url1.getFile(),url1.getHost()+absPfad2(url1.getPath()),filesize));	
	   			} // Ende switch
	   		}
	   	//else {}	
	   	}
	   catch (Exception e){}
	   } 
	    
	} 

	
/* ---------------------------------------------------------------------
  * Name der Funktion : isURL
  * Prüft, ob die übergebene url gültig ist
  * @author Sven Bohnstedt (wi4199), Paul Pohl (wi4628)
  * @param : URL url : aktuelle url
  * @return : boolean : true, wenn gültige url 
   ---------------------------------------------------------------------*/	 
	
	private boolean isURL(URL url){
		InputStream is = null;
		try{
			 is = url.openStream();
			//is.close();
			return true;
	 }
		catch(Exception e){{/*System.out.println("URL1 nicht gefunden");*/
		try{is.close();}catch (Exception e1){};
		return false;}
			
		}
		finally {try{is.close();}catch (Exception e){} }
   }

 /* ---------------------------------------------------------------------
  * Name der Funktion : isURL2
  * Erzeugt aus dem übergebenen url-Namen eine url und prüft,ob sie gültig ist
  * @author Sven Bohnstedt (wi4199), Paul Pohl (wi4628)
  * @param : String url : Name der aktuellen url
  * @return : boolean : true, wenn gültige url 
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
  * Name der Funktion : getAnzTags
  * Liefert die Anzahl der Tags der übergebenen Seite
  * @author Sven Bohnstedt (wi4199), Paul Pohl (wi4628)
  * @param : String htmlSeite : Name der aktuellen url
  * @return : int : Anzahl der Tags 
   ---------------------------------------------------------------------*/	 	
	private int getAnzTags(HTMLScanner htmlseite){
		String[] asTagNames1 = new String[] {null};
		   try {
		    HTMLTag[] tags = htmlseite.getTags(asTagNames1, false);    	
		    int i=0; // anzahl der tags
		    int j=0;//zaehlt Anzahl der Elemente im Array
		    int k=1; // zaehlt laenge in bytes eines tags
		    int l =0;// kumulierte bytes der tags
            String temp;
		    while( j<tags.length){	
                		temp=tags[i].toString(); // tag auslesen
                		while(k<=temp.length()){ // bytes zaehlen
                			k++;
                		}
                		l+=k; // kumulieren
                		k=0; // zruecksetzen
                		if (temp.startsWith("<")) i++; // tags zaehlen
            	j++;//else j++;
		    } 	
		    //this.seite.groessetagsbyte=l;
		    return i;
		   } catch (Exception e) { e.printStackTrace(); } //URL exceptions oder IO exceptions
		     return 0;
	}
	
 /* ---------------------------------------------------------------------
  * Name der Funktion : getAnzWoerter
  * Liefert die Anzahl der Wörter der übergebenen Seite
  * @author Sven Bohnstedt (wi4199), Paul Pohl (wi4628)
  * @param : String htmlSeite : Name der aktuellen url
  * @return : int : Anzahl der Wörter 
   ---------------------------------------------------------------------*/	 	
	private int getAnzWoerter(HTMLScanner htmlseite){		
		 String[] asTagNames = new String[] {""};
		 try {
		   	
		   	//HTMLScanner hs = new HTMLScanner(htmlseite);
		    HTMLTag[] tags = htmlseite.getTags(asTagNames, true);
		    StringBuffer content = new StringBuffer();
		    
		    for (int j = 0; j < tags.length; j++){
		     tags[j].accumulateContent(content);
		     }
		    return getWoerter(content);
		   } catch (Exception e) {return -1;} 
	  	  
	}

/* ---------------------------------------------------------------------
  * Name der Funktion : getWoerter
  * Liefert die Anzahl der Wörter der übergebenen Seite
  * @author Sven Bohnstedt (wi4199), Paul Pohl (wi4628)
  * @param : StringBuffer content : Wörter der Seite
  * @return : int : Anzahl der Wörter 
   ---------------------------------------------------------------------*/	
	private int getWoerter(StringBuffer content){
		int temp =0;
		content.append(" ");
		for (int i=0;i<content.length()-1;i++){
			if (!(content.charAt(i)==' ')&&content.charAt(i+1)==' '){ temp++;}			
		}
		this.groesseTags = content.length()-1;
		return temp; 
	  }
	
//---------------------------------------------------------------------------------	
	
	private long getBytesInhalt(HTMLScanner sc){
		String[] asTagNames = new String[] {""};
		 try {
		   	HTMLTag[] tags = sc.getTags(asTagNames, true);
		    StringBuffer content = new StringBuffer();
		    for (int j = 0; j < tags.length; j++){
		     tags[j].accumulateContent(content);
		     }
		   // System.out.println(content.length());
		    return content.length()-1; //
		   } catch (Exception e) {System.out.println("Fehler beim Parsen");return -1;}
		
	}

// -----------------------------------------------------------------------------------
 private long leseGroesseSeite(URL url4){
	InputStream in = null;
	try{
		in = url4.openStream();
		//StringBuffer content = new StringBuffer();
		int bytes_read=0;int i=0;
		while ((bytes_read = in.read())!= -1){
			i++;
		}
		in.close();
		return i;
	}
	catch(Exception e){System.out.println("Streamfehler");return -1;}
	finally {
		try{
			in.close();
		} catch(Exception e){}
	}
  }

 // ----------------------------------------------------------------------------------------------------------
 
 public HashSet getLinks(String urls,HTMLScanner hs2){
	
	HashSet temp = new HashSet();
	String[] asTagNames = new String[] { null, "A", "FRAME"};
	String urlpfad;
	
	try {
	   URL	url = new URL(urls);
	//	HTMLScanner hs1 = new HTMLScanner(url);
		if (isURL(url)){
	   urlpfad = url.getHost();
		urlpfad = urlpfad + url.getPath();
		HTMLTag[] tags = hs2.getTags(asTagNames, false);//true: discard tags we are not interested in
		for (int j = 0; j < tags.length; j++)
		{
			String link = null;
			switch (tags[j].iID)
			{
				case 1: // links & frames
					if (tags[j] !=null && tags[j].getAttribute("href")!=null){
						String temp2 = absPfad(tags[j].getAttribute("href"),urlpfad);
						if (isURL2(temp2)) temp.add(temp2);}
							//else temp.add(absPfad(tags[j].getAttribute("href"),urlpfad));}							
					break;
				case 2:
					if (tags[j]!=null && tags[j].getAttribute("src")!=null){
						String temp2 = absPfad(tags[j].getAttribute("src"),urlpfad);
						 if (isURL2(temp2)) temp.add(temp2);}
					break;
				}
			}
		}	
		return temp;
	}
  	catch (Exception e) { System.out.println( "keine gültige URL angegeben "+urls); } //URL exceptions or IO exceptions
  	return temp;
}
 
 private int getTagGroesseFormatiert(HTMLScanner htmlseite){
	String[] asTagNames1 = new String[] {null,"!DOCTYPE","HEAD","!--","HTML"};
	 int i=0;
	 int j=0;
		try {
	    HTMLTag[] tags = htmlseite.getTags(asTagNames1, false);
	    for (int h=0;h<tags.length;h++){
	    	switch(tags[h].iID){
	    		case 1: 
	    		case 2:
	    		case -2:
	    		case 3:
	    		case 4:
	    		case -4:	
	    			break;
    			default:
    				if(tags[h]!=null){
    					String temp=tags[h].toString();
    					//System.out.println("temp "+temp);
    					for(int k=0;k<temp.length();k++){
    						i++;
    					}
    					j+=i;
    					i=0;
    				}
    				break;	
	    	}
	    }	
	    return j;  
	} catch (Exception e) { e.printStackTrace();return -1; }
}		
}

