/* ---------------------------------------------------------------------
 * Name der Klasse: ReferenzierteObjekte
 * Beinhaltet Informationen zu den Objekten einer Seite
 * @author Sven Bohnstedt (wi4199), Paul Pohl (wi4628)
 * Erstellt am 18.06.2004
   ---------------------------------------------------------------------*/

public class ReferenzierteObjekte {
	private String name;
	private String verzeichnis;
	private long groesse;
	private boolean vorhanden;
	

/* ---------------------------------------------------------------------
  * Name des Konstruktors : ReferenzierteObjekte
  * Initialisierung der Variablen
  * @author Sven Bohnstedt (wi4199), Paul Pohl (wi4628)
  * @param : String name: Name des Objektes 
  * @param : String verzeichnis: Verzeichnis des Objektes    
  * @param : long groesse: Größe des Objektes 
   ---------------------------------------------------------------------*/	
	public ReferenzierteObjekte(String name,String verzeichnis,long groesse){
		this.name=name;
		this.verzeichnis=verzeichnis;
		this.groesse=groesse;
	}

/* ---------------------------------------------------------------------
  * Name der Funktion : getGroesse
  * Liefert die Größe des Objektes
  * @author Sven Bohnstedt (wi4199), Paul Pohl (wi4628)
  * @param : -
  * @return : long groesse: Größe des Objektes 
   ---------------------------------------------------------------------*/	
	public long getGroesse(){
		return this.groesse;
	}
	
/* ---------------------------------------------------------------------
  * Name der Funktion : getVerzeichnis
  * Liefert das Verzeichnis des Objektes
  * @author Sven Bohnstedt (wi4199), Paul Pohl (wi4628)
  * @param : -
  * @return : String : Verzeichnis
   ---------------------------------------------------------------------*/	
	public String getVerzeichnis(){
		return this.verzeichnis;
	}
	
/* ---------------------------------------------------------------------
  * Name der Funktion : getName
  * Liefert den Namen des Objektes
  * @author Sven Bohnstedt (wi4199), Paul Pohl (wi4628)
  * @param : -
  * @return : String : Name
   ---------------------------------------------------------------------*/	
	public String getName(){
		return this.name;
	}
}
