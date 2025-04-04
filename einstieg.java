/*
 * Created on Jul 9, 2004
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Generation - Code and Comments
 */
/**
 * @author sbohnstedt
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Generation - Code and Comments
 */

//import java.io.*;
//import java.util.*;
import java.net.*;
import be.arci.html.*;

public class einstieg {
	public static void main(String[] args) {
//OnlineParser	op	= new OnlineParser();
 System.out.println("werner "+args[0]);
 
 
 // 	op.parse(args[0]);
//String[] asTagNames = new String[] { null, "IMG", "A", "BODY", "FRAME", "LINK", "SCRIPT", "OBJECT", "APPLET"};
	try {
		
			//Erstellen der url
		URL	url = new URL(args[0]);
		//fetchData(url);
		//System.out.println(urls);	
		HTMLScanner hs = new HTMLScanner(url);
		einstieg ein = new einstieg();
		System.out.println(ein.getTagGroesseFormatiert(hs));
		//ein.getAnzWoerter(hs);
	}
catch (Exception e){}
	
 
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

/* ---------------------------------------------------------------------
 * Name der Funktion : getWoerter
 * Liefert die Anzahl der Wörter der übergebenen Seite
 * @author Sven Bohnstedt (wi4199), Paul Pohl (wi4628)
 * @param : StringBuffer content : Wörter der Seite
 * @return : int : Anzahl der Wörter 
  ---------------------------------------------------------------------*/	
/*	static int getWoerter(StringBuffer content){
		int temp =0;
		content.append(" ");
		for (int i=0;i<content.length()-1;i++){
			if (!(content.charAt(i)==' ')&&content.charAt(i+1)==' '){ temp++;}			
		}
		return temp; 
	  }
	
*/
}
