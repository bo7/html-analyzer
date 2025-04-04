

import java.util.*;

/* ---------------------------------------------------------------------
 * Name der Klasse: SetzeProxy
 * Setzen eines Proxys
 * @author Sven Bohnstedt (wi4199), Paul Pohl (wi4628)
 * Erstellt am 20.06.2004
   ---------------------------------------------------------------------*/

public class SetzeProxy {
	/* ---------------------------------------------------------------------
	  * Name des Konstruktors : SetzeProxy
	  * Zuweisen eines Proxys für die gegebenen Parameter
	  * @author Sven Bohnstedt (wi4199), Paul Pohl (wi4628)
	  * @param : String name: Name des Proxys 
	  * @param : String port: der vorher validierte Port des Proxys      
	   ---------------------------------------------------------------------*/
	
	
	public SetzeProxy(String name, String port){
		Properties systemSettings = System.getProperties();
		systemSettings.put("proxySet","true");
		systemSettings.put("http.proxyHost",name);
		systemSettings.put("http.proxyPort",port);
		System.setProperties(systemSettings);
	}

}
