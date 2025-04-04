
import java.util.*;
import java.net.*;
import java.io.*;

/* ---------------------------------------------------------------------
 * Name der Klasse: ConsolenAnwendung
 * Klasse für die Konsolenanwendung. Es können dem Programm mehrere Parameter
 * mitgegeben werden, die entsprechend validiert werden.
 * Der erste Parameter muss immer eine URL sein. 
 * Weitere Parameter sind: 
 * - setze eines Proxys samt Port
 * - Angabe einer Menge von regulären ausdrücken
 * - Angabe einer Rekursionstiefe
 * @author Sven Bohnstedt (wi4199), Paul Pohl (wi4628)
 * Erstellt am 20.06.2004
   ---------------------------------------------------------------------*/


public class ConsolenAnwendung {
	
	/* ---------------------------------------------------------------------
	  * Name der Funktion : main
	  * Initialisierung der Variablen und Aufruf der Rekursion
	  * @author Sven Bohnstedt (wi4199), Paul Pohl (wi4628)
	  * @param : Stringarray args: Array mitr den übergebenen Parametern
	  * @return : void 
	   ---------------------------------------------------------------------*/	
	public static void main(String[] args) {
		BaueSeiten bs;
		HashSet re=null;// Reguläre Ausdrücke
		String rt=""; // Rekursionstiefer
		String url=""; // 
		String proxy="";
		String port="";
		String dateiort="";
		int iport=0; // umgewandelt zum Test >0
		int irt=0;	// umgewandelt zum Test >0
		String help="";
		File ftest; // gucken, ob file anlegbar ist
		boolean fproxy =true;
		boolean fport =true;
		boolean frt=true; // Flag zum Feststellen, ob ordnungsgemaesse Parameterübergabe
		boolean furl=true;
		boolean fre=true;
		boolean proxyset=false;
		boolean fhelp=false;
		boolean fdatei=true;
		boolean frtvor=false; // Flag zum Feststellen, ob Rekursionstiefe angegeben wurde 
		BaueHtmlAusgabe bhta; // Wenn alles validiert, wird Analyser aufgerufen
		
		if (!(args.length==0)){ // Parameter müssen übergeben werden
		url=args[0]; // erster ist URL
		for(int i=0;i<args.length;i++){
			if (i==0){url=args[0];}
			if (args[i].equals("-rt")){
				try{
					rt=args[i+1];
					if(!(args[i+1].startsWith("-"))){ // nach Rektiefe muss Zahl kommen
						irt= Integer.parseInt(rt);
						frt=true;
						frtvor=true;
					} else{
					System.err.println("Nach der Option -rt bitte eine ganze Zahl>=0 angeben");
					frt=false;
					System.exit(1);
					}
					if(!(irt>=0)){System.err.println("Nach der Option -rt bitte eine ganze Zahl>=0 angeben");frt=false;System.exit(1);}
				}
				catch(ArrayIndexOutOfBoundsException e){
					System.err.println("nach Rekursionstiefe bitte Parameter eingeben");
					frt=false;
					System.exit(1);
				}
				catch(NumberFormatException e){
					System.err.println("Nach der Option -rt bitte eine ganze Zahl>=0 angeben");
					frt=false;
					System.exit(1);
				}
			} // End if	
				if (args[i].equals("-re")){ // nach regulären Ausdrücken parsen
					try{
						re = new HashSet();
						int t=i+1; // erstes Argument nach -re
						while(t<args.length&&!(args[t].startsWith("-"))){ // über Menge der Ausdrücke nach Parameter iterieren
							re.add(args[t]);
							t++;
						}// end while
					} // Ende try
					catch(ArrayIndexOutOfBoundsException e){
						System.err.println("Nach der Option -re mindestens einen regulären Ausdruck angeben");
						fre=false;
						System.exit(1);
					}// Ende Catch
				} // Ende if re
			if (args[i].equals("-pr")){ // Proxy testen, muss von port gefolgt kommen
				try{
					String ports;
					proxy=args[i+1]; // proxy steht nach -pr
					ports=args[i+2];// -p für port
					port=args[i+3];// der port selber nach -p
					if (ports.equals("-p")){
						iport= Integer.parseInt(port);
						if(iport>0){
							fport=true; // flags zum entscheiden, ob richtige Reihenfolge
							fproxy=true;
							proxyset=true; // falg zum erstellen eines SetzeProxys
						} else{
							System.err.println("Port bitte >0 angeben");
							fproxy=false;
							fport=false;
							System.exit(1);
						}// Ende else
							
					} // ende if ports.eq
					fproxy=true; 
					if ((args[i+1].startsWith("-"))){ // weitere Optionen
						System.err.println("Proxy bitte angeben nach -pr");
						fproxy=false;
						fport=false;
						System.exit(1);
					} // Ende if i+1
				}// Ende try
				catch(ArrayIndexOutOfBoundsException e){
					System.err.println("Bitte -pr Proxyname -p Port angeben.");
					fproxy=false;
					fport=false;
					System.exit(1);
				}// ende catch
				catch(NumberFormatException e){
					System.err.println("Port bitte >0 angeben");
					fport=false;
					fproxy=false;
					System.exit(1);
				} // Ende catch
			  } // Ende -pr		
			// Ende port/ proxy
			if (args[i].equals("-h")) fhelp=true; // ausgabe der Hilfe
	      if (args[i].equals("-do")){ // festlegen einer Datei und ihrem Ort
	      	try{
	      		dateiort=args[i+1];
	      		if (args[i+1].startsWith("-")){
	      			System.err.println("Bitte Dateiort angeben");
	      			fdatei=false;
	      			System.exit(1);
	      		}
	      				try{ // file testen, ob geht
	      					dateiort=args[i+1];
	      					ftest = new File(dateiort);
	      					ftest.createNewFile();
	      					fdatei=true;
	      				} // Ende try
	      				catch (Exception e){
	      					fdatei = false;
	      					System.err.println("Fehler beim Erstellen der Datei");
	      					System.exit(1);
	      				}
	      			
	      		
	      	}//Ende try
	      	catch (ArrayIndexOutOfBoundsException e){
	      		System.err.println("Bitte Dateiort angeben");
	      		System.exit(1);
	      	}// ende catch
	      }// ende if do
		} // Ende for
	   if(!fhelp){
	   	//URL urls = new URL(url);
	   	if(fproxy&&fport&&frt&&fre&&fdatei){ // schauen, ob alles validiert wurde
	   		if(proxyset){ // Proxy erzeugen, falls gesetzt
	   			new SetzeProxy(proxy,port);
	   		} // Ende if proxyset
	   		if(isURL(url)){ // url testen,. wenn proxy benoetigt vorher erstellen
	   			if (frtvor) bs =new BaueSeiten(url,null,re,irt,true);
	   				else bs = new BaueSeiten(url,null,re,0,false);
	   			    bhta = new BaueHtmlAusgabe(bs.getSeitenInhalte());
	   				if (dateiort.equals("")){// Wenn keine Datei angeben, wird defaultwert genommen
	   					bhta.schreibeHTML("");}
	   		    	else{
	   		    		bhta.schreibeHTML(dateiort); 
	   		    		}   		
	   		}// Ende url
	   		else{
	   			System.err.println("URL nicht gefunden");
	   			System.exit(1);
	   		}
	   	} // Ende if && && && &&
	   } // Ende !help
	    else{
	    	ausgabeHilfe();
	    }
		}else// Ende if
	  {
	  	System.err.println("zuwenig Parameter");
	  	ausgabeHilfe();
	  }// Ende else
	
		
		
	} // Ende main
	
	
	/* ---------------------------------------------------------------------
	  * Name der Prozedur : isURL
	  * Testet, ob es sich um eine gültige URL handelt
	  * @param : String url : übergebene URL als String
	  * @return : boolean
	   	   ---------------------------------------------------------------------*/
	private static boolean isURL(String url1){
		InputStream is = null; 
		try{
				URL urls =new URL(url1);
				is = urls.openStream();
				is.close();
				return true;
		 }
			catch(Exception e){
				try{is.close();}catch (Exception e1){};
				return false;}
			finally {try{is.close();}catch (Exception e){} }
			}
	
	
	/* ---------------------------------------------------------------------
	  * Name der Prozedur : ausgabeHilfe
	  * gibt die Hilfe aus
	  * @return : void
	   	   ---------------------------------------------------------------------*/
	
	private static void ausgabeHilfe(){
		String ausgabe ="Benutzung:\n";
    	
    	ausgabe+="\terster Parameter: URL, die zu parsen ist.\n";
    	ausgabe+="\t-h -> Hilfe ausgeben\n";
    	ausgabe+="\t-do Name -> Dateiort und Name festlegen\n";
    	ausgabe+="\t-rt Zahl -> Rekursionstiefe festlegen (0-> nur aktuelle URL)\n";
    	ausgabe+="\t-pr Name -p Zahl -> Proxyserver und Port festlegen\n";
    	ausgabe+="\t-re Name, {Name} -> Menge der regulären Ausdrücke";
    	System.out.println(ausgabe);
	} // ausgabe..
	   
} // Ende Klasse
