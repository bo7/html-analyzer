/* ---------------------------------------------------------------------
 * Name der Klasse: makePfad
 * Beinhaltet Funktionen zum umwandeln relativer Pfadangaben in absolute
 * @author Sven Bohnstedt (wi4199), Paul Pohl (wi4628)
 * Erstellt am 07.06.2004
   ---------------------------------------------------------------------*/

public class makePfad {


   /* ---------------------------------------------------------------------
    * Name der Funktion : verzWechseln
    * Löscht bei dem übergebenen Verzeichnis so viele letzte Buchstaben
    * bis ein '/' kommt, also die nächste Verzeichnisebene erreicht ist
    * @author Sven Bohnstedt (wi4199), Paul Pohl (wi4628)s
    * @param : int pos 
    * @param : String Verz 
    * @return : String : wenn der Reguläre Ausdruck mit dem Namen der Seite übereinstimmt true 
     ---------------------------------------------------------------------*/	 
	private String verzWechseln (int pos, String verz){
		   pos = verz.length()-1;
   		while (verz.charAt(pos)!='/' && pos!=0){
        	--pos;
        }
        return verz.substring(0,pos);
    }
   /* ---------------------------------------------------------------------
    * Name der Funktion : seitenname
    * Wenn ein Dateiname angegeben ist wir true zurückgegeben. Dies wird getestet
    * indem, beginnend beim letzten Zeichen, geprüft wird ob bis zum ersten 
    * Audtreten eines '/' ein Punkt vorkommt
    * vorkommen dürfen und testet, ob sie mit dem Namen der aktuellen Seite übereinstimmen
    * @author Sven Bohnstedt (wi4199), Paul Pohl (wi4628)
    * @param : String verz: Verzeichnis 
    * @return : boolean : wenn ein Seitenname vorhanden ist wird true zurückgegeben
     ---------------------------------------------------------------------*/	 
	private boolean seitenname (String verz){
		boolean punkt = false;
		int pos = verz.length()-1;
	 while (verz.charAt(pos)!='/' && pos!=0){
		 --pos;
		 if (verz.charAt(pos)=='.') punkt = true;
	 }
	 return punkt;
    }
	
	
	
   /* ---------------------------------------------------------------------
    * Name der Funktion : anzZuWechselndeVerz
    * Gibt die Anzahl der zu wechselnden Verzeichnisse laut dem übergebenen String zurück
    * @author Sven Bohnstedt (wi4199), Paul Pohl (wi4628)
    * @param : String LinkVerz: Verzeichnis 
    * @return : int : Anzahl der zu wechselnden Verzeichnisse
     ---------------------------------------------------------------------*/	 
    private int anzZuWechselndeVerz (String LinkVerz){
    int anz = 0;
        while (LinkVerz.startsWith("../")){
        	anz = anz+1;
        	LinkVerz = LinkVerz.replaceFirst("../","");
        }
    	return anz;
    }
  

    /* ---------------------------------------------------------------------
     * Name der Funktion : absPfad
     * Bildet aus dem Pfad der aktuell analysierten Datei und der Pfadangabe im Quelltext der
     * Datei einen absoluten Pfad
     * @author Sven Bohnstedt (wi4199), Paul Pohl (wi4628)
     * @param : String tempWort: Pfad der aktuell analysierten Datei 
     * @param : String aktVerz: Pfad der im Quelltext der aktuell analysierten Datei angegeben ist 
     * @return : String : Der absolute Pfad
      ---------------------------------------------------------------------*/	 
	public String absPfad (String tempWort, String aktVerz){
		String temp = ""; 
	   	String aktPfad = null;
	   	//Wenn der Pfad im Quelltext keine Datei am Ende stehen hat
	   	if (!(seitenname(aktVerz)))
       	  aktVerz = "http://"+aktVerz+"/";
       	else  aktVerz = "http://"+aktVerz;
	   	//Entfernen des Dateinamen
	   	aktVerz = verzWechseln((aktVerz.length()-1),aktVerz);
        	if (!tempWort.startsWith("..")){   				 
             if (tempWort.startsWith("./"))
            	tempWort = tempWort.replaceFirst("./","");
                while (tempWort.startsWith("/")){
                	tempWort = tempWort.substring(1,tempWort.length());
                }
                temp = (aktVerz+"/" + tempWort);
    	    }
        	else {
        		int i = anzZuWechselndeVerz(tempWort);
           		int pos = aktVerz.length()-1;
                String tempPfad = aktVerz;
                
                while ((i > 0) && (tempPfad.length()>1)){
                 tempPfad = verzWechseln(pos,tempPfad);
                 --i;
                }
	               while (tempWort.startsWith("../")|| tempWort.startsWith("/../")){
                   	if (tempWort.startsWith("../"))
                   	 tempWort = tempWort.substring(2,tempWort.length());
                   	else
                   	 tempWort = tempWort.substring(3,tempWort.length());	
                   }
                   temp = (tempPfad+tempWort);           			
                 }
        return temp;
    }

	
   /* ---------------------------------------------------------------------
    * Name der Funktion : absPfad2
    * Löscht den Dateinamen am Ende des übergebenen Pfades
    * @author Sven Bohnstedt (wi4199), Paul Pohl (wi4628)
    * @param : String pfad: Pfad der aktuell analysierten Datei 
    * @return : String : Pfad ohne Dateinamen
     ---------------------------------------------------------------------*/	 
	public String absPfad2 (String pfad){//String aktVerz){
	 return verzWechseln(1,pfad);
	}
	
}
