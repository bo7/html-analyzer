


import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.net.*;
import java.util.HashSet;
import java.io.*;
import java.util.*;

/* ---------------------------------------------------------------------
 * Name der Klasse: GuiBuilder
 * Gestaltung einer einfachen GUI mittes Swing Komponenten.
 * Die Validierung der eingegebenen Daten erfolgt um anschließend
 * die eingegbenen Daten, dem Analyser zu übergeben
 * @author Sven Bohnstedt (wi4199), Paul Pohl (wi4628)
 * Erstellt am 14.06.2004
   ---------------------------------------------------------------------*/



public class GuiBuilder implements ActionListener {
	
	JTextField tfparse; // Tf = textfield, lb = Label
	JTextField tfoutput; 
	JTextField tfrektiefe;
	JTextField tfregex; 
    JLabel lbparse;  
    JLabel lboutput;
    JLabel lbrektiefe;
    JLabel lbregex;
	JFrame guiFrame; 
    JPanel guipanel;
    JTextField tempCelsius;
    JLabel celsiusLabel, fahrenheitLabel;
    JButton btparse;
    JLabel lbblank; 
    JLabel lbsingle;
    JLabel lbproxy;
    JTextField tfproxy;
    Color farbe; // Farbe füer die Anzeige der richtigen falschen Eingaben
    JLabel lbport;
    JTextField tfport;

    /* ---------------------------------------------------------------------
     * Name des Konstruktors : GuiBuilder
     * Initialisierung des Frames, setzen des Ortes der Gui auf dem Bildschirm,
     * sowie des Bestimmen Gridlayouts. Mittels des X- Buttons kann die Anwendung
     * geschlossen werden.
     * @author Sven Bohnstedt (wi4199), Paul Pohl (wi4628)
     *  
     ---------------------------------------------------------------------*/
    public GuiBuilder() {
        //Erstellen des Fensters
    	guiFrame = new JFrame("Softwarepraktikum 04 HTML Parser");
        guiFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        guiFrame.setSize(20,30);
        guiFrame.setLocation(300,300);
        //Erstellen des Panels
        guipanel = new JPanel(new GridLayout(7,4));
        //Aufsetzen der Labels, Buttons und Textfelder
        addWidgets();
        //Bestimmen des Default Buttons
        guiFrame.getRootPane().setDefaultButton(btparse);
        //Panel zum Fenster hinzufuegen
        guiFrame.getContentPane().add(guipanel, BorderLayout.CENTER);
       //Fenster zeigen
        guiFrame.pack();
        guiFrame.setVisible(true);
    }
    /* ---------------------------------------------------------------------
	  * Name der Prozedur : addWidgets
	  * bindet die optischen Elemente ein 
	  * @return : void 
	   	   ---------------------------------------------------------------------*/
    
    private void addWidgets() {
        //Widgets erzeugen
        tfparse = new JTextField();
        tfoutput = new JTextField();
        tfrektiefe = new JTextField("0",2);
        tfregex = new JTextField();
        lbparse = new JLabel("URL", SwingConstants.LEFT);// links ausrichten
        lboutput = new JLabel("Dateipfad + Name", SwingConstants.LEFT);
        lbrektiefe = new JLabel("Linktiefe",SwingConstants.LEFT);
        lbregex = new JLabel("Regulärer Ausdruck",SwingConstants.LEFT);
        btparse = new JButton("Parse");
        lbblank = new JLabel(" ss ", SwingConstants.LEFT);
        tfproxy = new JTextField();
        lbproxy = new JLabel("Proxy-Name",SwingConstants.LEFT);
        lbport = new JLabel("Portnummer");
        tfport = new JTextField();
        //Listetener für den Button .
        btparse.addActionListener(this);
        guipanel.add(lbparse);
        guipanel.add(tfparse);
        guipanel.add(new JLabel(""));
        guipanel.add(new JLabel(""));//1. Reihe Grid
        guipanel.add(lboutput);
        guipanel.add(tfoutput);
        guipanel.add(new JLabel(""));      
        guipanel.add(new JLabel("")); //2. Reihe Grid
        guipanel.add(lbrektiefe);
        guipanel.add(tfrektiefe);
        guipanel.add(new JLabel("0 => nur Parsen der URL"));     
        guipanel.add(new JLabel(""));//3. Reihe Grid
        guipanel.add(lbregex);
        guipanel.add(tfregex);
        guipanel.add(new JLabel(""));     
        guipanel.add(new JLabel(""));//4.Reihe Grid
        guipanel.add(lbproxy);
        guipanel.add(tfproxy);
        guipanel.add(new JLabel(""));
        guipanel.add(new JLabel("")); //5.Reihe Grid
        guipanel.add(lbport);
        guipanel.add(tfport);
        guipanel.add(new JLabel(""));//6. Reihe Grid
        guipanel.add(new JLabel(""));
        guipanel.add(new JLabel(""));
        guipanel.add(btparse);
      
    }

    /* ---------------------------------------------------------------------
	  * Name der Prozedur : actionPerformed
	  * Aktionen, wenn Button gedrueckt wird. Es geschieht eine Validierung über
	  * alle Felder. Die untereinander Abhaengigkeiten besitzen können. Es muss,
	  * wenn ein Port oder Proxy gesetzt ist, jeweils in beiden Feldern etwas stehen.
	  * Zahlfelder werden validiert.
	  * Es wir geprüft, ob eine gültige URL vorliegt.
	  * Es wird geprüft, ob dioe Dateioperationen funktionieren.
	  * Anschließend Aufruf des Analysers mit den validierten Feldern, oder Abbruch bis zum
	  * nächsten Klick. 
	  * @param : ActionEvent event: Parameter, wenn Buton ausgelöst wurde
	  * @return : void 
	   	   ---------------------------------------------------------------------*/
    public void actionPerformed(ActionEvent event) {
       
    	Color rot = new Color(255,0,0); // falsche Eingaben
        Color schwarz = new Color(0,0,0); // richtige Eingaben
    	String urls= tfparse.getText(); // angegebene URL
    	String dateiort = tfoutput.getText();// Dateipfad 
    	String linktiefe = tfrektiefe.getText();// Rekursionstiefe
    	String port = tfport.getText();// Port
    	String proxy = tfproxy.getText();// Text
    	int ports=0; // enthalten die umgewandelten Strings 
    	int tiefe=0; //
    	BaueHtmlAusgabe bhta=null; // Zur Übergabe der Parameter
    	if(!proxy.equals("")){ // Wenn Proxy nicht gesetzt, dann  darf nichts im Port stehen
    		if(port.equals("")){
    			lbport.setForeground(rot);
    			}
    		}
    	if(!port.equals("")){ //Port überprüfen
    		try{
        	ports = Integer.parseInt(port);
        	if(ports>0){ // Wenn Port, dann >0
        	if(proxy.equals("")){lbproxy.setForeground(rot);}
        	  else lbproxy.setForeground(schwarz);
        	lbport.setForeground(schwarz);}
        	else lbport.setForeground(rot);}
        	catch(NumberFormatException e){
        		lbport.setForeground(rot);
        	}
    	} 	else{
    		if(!port.equals("")) // Wenn Port und Proxy stimmen
    			lbport.setForeground(schwarz);
    			lbproxy.setForeground(schwarz);
    		}
    		if (!linktiefe.equals("")){ // Linktiefe überprüfen >= 0
    			try{
    				tiefe = Integer.parseInt(linktiefe);
    				if(tiefe>=0){
    					lbrektiefe.setForeground(schwarz);}
    				else lbrektiefe.setForeground(rot);} // Wenn kein Integer -> Fehler
    			catch(NumberFormatException e){
    				lbrektiefe.setForeground(rot);
    			}
    		} else lbrektiefe.setForeground(schwarz);
    		File datort = new File(dateiort);
    		if(!(tfproxy.getText().equals(""))&&!(tfport.getText().equals(""))&&(lbproxy.getForeground().equals(schwarz))&&lbport.getForeground().equals(schwarz)){ // Wenn Proxy vorleigt wird dieser gestzt, um die URL zu testen
    			SetzeProxy pro = new SetzeProxy(tfproxy.getText(),tfport.getText());
    		}
    		if (!isURL(urls) ) // keine URL
        		{lbparse.setForeground(rot);}
        			else {lbparse.setForeground(schwarz);}
    		if (dateiort!=null){ // schauen, ob File geschrieben werden kann
    			try{
    				datort.createNewFile();
    				lboutput.setForeground(schwarz);
    			}
    			catch (Exception e){
    				lboutput.setForeground(rot);
    			}
    		}
    		else {lboutput.setForeground(rot);}
    		if(proxy.equals("")&&port.equals("")){
    			lbport.setForeground(schwarz);
    			lbproxy.setForeground(schwarz);
    		}
    		// Wenn alles validiert ist, Analyser aufrufen
    		if(lbproxy.getForeground().equals(schwarz) && lbparse.getForeground().equals(schwarz) && lboutput.getForeground().equals(schwarz) 
    		&& lbrektiefe.getForeground().equals(schwarz) && lbport.getForeground().equals(schwarz))
    		{
    			boolean flag=true;
    			if(lbrektiefe.getText().equals("")) {flag=false;}// Flag, ob Rektiefe gesetzt ist
    			HashSet r1= null;
    			if (!(tfregex.getText().equals(""))){ r1 = new HashSet();r1.add(lbregex.getText());} // Wenn regulärer Ausdruck, dann übernehmen
    			BaueSeiten b1 = new BaueSeiten(urls,null,r1,tiefe,flag);
    			bhta = new BaueHtmlAusgabe(b1.getSeitenInhalte());
    			if (tfoutput.getText().equals("")) bhta.schreibeHTML("");
    				else bhta.schreibeHTML(tfoutput.getText()); // schreiben in Ausgabefile
    			}
    }

    /* ---------------------------------------------------------------------
	  * Name der Prozedur : createAndShowGUI
	  * ruft Konstruktor auf und setzt Fenster Deko 
	  * @return : void 
	   	   ---------------------------------------------------------------------*/
    private static void createAndShowGUI() {
        //
        JFrame.setDefaultLookAndFeelDecorated(true);
        GuiBuilder converter = new GuiBuilder();
    }
    
    /* ---------------------------------------------------------------------
	  * Name der Prozedur : main
	  * ruft  createAndShowGUI() auf
	  * @return : void 
	   	   ---------------------------------------------------------------------*/
    public static void main(String[] args) {
        
        javax.swing.SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                createAndShowGUI();
            }
        });
    }
    
    /* ---------------------------------------------------------------------
	  * Name der Prozedur : isURL
	  * Testet, ob es sich um eine gültige URL handelt
	  * @param : String url : übergebene URL als String
	  * @return : boolean
	   	   ---------------------------------------------------------------------*/
    private boolean isURL(String url){
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
}
    


