
package shop.client.net;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.Socket;
import java.util.List;
import java.util.Vector;

import shop.common.exceptions.ArtikelBestandIstKeineVielfacheDerPackungsgroesseException;
import shop.common.exceptions.ArtikelBestandIstZuKleinException;
import shop.common.exceptions.ArtikelExistiertBereitsException;
import shop.common.exceptions.ArtikelExistiertNichtException;
import shop.common.exceptions.KundeExistiertBereitsException;
import shop.common.exceptions.KundeExistiertNichtException;
import shop.common.exceptions.MitarbeiterExistiertBereitsException;
import shop.common.exceptions.MitarbeiterExistiertNichtException;
import shop.common.exceptions.UsernameExistiertBereitsException;
import shop.common.exceptions.WarenkorbIstLeerException;
import shop.common.interfaces.ShopInterface;
import shop.common.valueobjects.Artikel;
import shop.common.valueobjects.Kunde;
import shop.common.valueobjects.Mitarbeiter;
import shop.common.valueobjects.MitarbeiterFunktion;
import shop.common.valueobjects.Person;
import shop.common.valueobjects.PersonTyp;
import shop.common.valueobjects.Rechnung;
import shop.common.valueobjects.WarenkorbArtikel;


/**
 * Klasse mit Fassade des Shops auf Client-Seite.
 * Die Klasse stellt die von der GUI erwarteten Methoden zur Verfügung
 * und realisiert (transparent für die GUI) die Kommunikation mit dem 
 * Server.
 * Anmerkung: Auf dem Server wird dann die eigentliche, von der lokalen
 * Shopversion bekannte Funktionalität implementiert (z.B. Artikel 
 * einfügen und suchen)
 * 
 * @author teschke
 */
public class ShopFassade implements ShopInterface {

	// Datenstrukturen für die Kommunikation
	private Socket socket = null;
	private BufferedReader sin; // server-input stream
	private PrintStream sout; // server-output stream
	
	/**
	 * Konstruktor, der die Verbindung zum Server aufbaut (Socket) und dieser
	 * Grundlage Eingabe- und Ausgabestreams für die Kommunikation mit dem
	 * Server erzeugt.
	 * 
	 * @param host Rechner, auf dem der Server läuft
	 * @param port Port, auf dem der Server auf Verbindungsanfragen warten
	 * @throws IOException
	 */
	public ShopFassade(String host, int port) throws IOException {
		try {
			// Socket-Objekt fuer die Kommunikation mit Host/Port erstellen
			socket = new Socket(host, port);

			// Stream-Objekt fuer Text-I/O ueber Socket erzeugen
			InputStream is = socket.getInputStream();
			sin = new BufferedReader(new InputStreamReader(is));
			sout = new PrintStream(socket.getOutputStream());
		} catch (IOException e) {
			System.err.println("Fehler beim Socket-Stream oeffnen: " + e);
			// Wenn im "try"-Block Fehler auftreten, dann Socket schlieﬂen:
			if (socket != null)
				socket.close();
			System.err.println("Socket geschlossen");
			System.exit(0);
		}
		
		// Verbindung erfolgreich hergestellt: IP-Adresse und Port ausgeben
		System.err.println("Verbunden: " + socket.getInetAddress() + ":"
				+ socket.getPort());	

		// Begrüßungsmeldung vom Server lesen
		String message = sin.readLine();
		System.out.println(message);
	}

	@Override
	public void fuegeArtikelEin(Mitarbeiter mitarbeiter, int artikelnummer,
			String bezeichnung, double preis, int bestand)
			throws ArtikelExistiertBereitsException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void fuegeMassengutartikelEin(Mitarbeiter mitarbeiter,
			int artikelnummer, String bezeichnung, double preis,
			int packungsgroesse, int bestand)
			throws ArtikelExistiertBereitsException,
			ArtikelBestandIstKeineVielfacheDerPackungsgroesseException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Artikel gibArtikel(int artikelnummer)
			throws ArtikelExistiertNichtException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Artikel> gibAlleArtikelSortiertNachArtikelnummer() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Artikel> gibAlleArtikelSortiertNachBezeichnung() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Artikel> sucheArtikel(int artikelnummer) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Artikel> sucheArtikel(String bezeichnung) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void entferneArtikel(Mitarbeiter mitarbeiter, int artikelnummer)
			throws ArtikelExistiertNichtException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void disconnect() throws IOException {
		// Kennzeichen fuer gewaehlte Aktion senden
		sout.println("q");
		// (Parameter sind hier nicht zu senden)

		// Antwort vom Server lesen:
		String antwort = "Fehler";
		try {
			antwort = sin.readLine();
		} catch (Exception e) {
			System.err.println(e.getMessage());
			return;
		}
		System.out.println(antwort);
	}

	@Override
	public void schreibeArtikel() throws IOException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Kunde sucheKunde(int id) throws KundeExistiertNichtException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Vector<Kunde> gibAlleKunden() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void kundenLoeschen(Kunde k) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void schreibeKunden() throws IOException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void inDenWarenkorbLegen(Kunde kunde, Artikel artikel, int stueckzahl)
			throws ArtikelBestandIstZuKleinException,
			ArtikelExistiertNichtException,
			ArtikelBestandIstKeineVielfacheDerPackungsgroesseException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void ausDemWarenkorbHerausnehmen(Kunde kunde, Artikel artikel)
			throws ArtikelExistiertNichtException,
			ArtikelBestandIstKeineVielfacheDerPackungsgroesseException {
		// TODO Auto-generated method stub
		
	}
	
	@Override
	public void stueckzahlAendern(Kunde kunde,
			WarenkorbArtikel warenkorbArtikel, int neueStueckzahl)
			throws ArtikelBestandIstZuKleinException,
			ArtikelExistiertNichtException,
			ArtikelBestandIstKeineVielfacheDerPackungsgroesseException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Rechnung kaufen(Kunde kunde) throws IOException,
			WarenkorbIstLeerException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void leeren(Kunde k)
			throws ArtikelBestandIstKeineVielfacheDerPackungsgroesseException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Person pruefeLogin(String username, String password) {
		Person p = null;
		sout.println("pl");
		sout.println(username);
		sout.println(password);
		String antwort = "?";
		try {
			// PersonTyp
			antwort = sin.readLine();
			PersonTyp personTyp = null;
			if(!antwort.equals("Fehler"))
				personTyp = PersonTyp.valueOf(antwort);
			else
				return null;
			// ID
			antwort = sin.readLine();
			int id = Integer.parseInt(antwort);
			// Name
			antwort = sin.readLine();
			String name = antwort;
			switch(personTyp) {
				case Kunde: 
					// Strasse
					antwort = sin.readLine();
					String strasse = antwort;
					// Postleitzahl
					antwort = sin.readLine();
					int plz = Integer.parseInt(antwort);
					// Wohnort
					antwort = sin.readLine();
					String wohnort = antwort;
					//Blockiert
					antwort = sin.readLine();
					boolean kblockiert = Boolean.valueOf(antwort);
					p = new Kunde(id, username, password, name, strasse, plz, wohnort); 
					p.setBlockiert(kblockiert);
					break;
				case Mitarbeiter: 
					// MitarbeiterFunktion
					antwort = sin.readLine();
					MitarbeiterFunktion funktion = MitarbeiterFunktion.valueOf(antwort);
					// Gehalt
					antwort = sin.readLine();
					double gehalt = Double.valueOf(antwort);
					//Blockiert
					antwort = sin.readLine();
					boolean mblockiert = Boolean.valueOf(antwort);
					p = new Mitarbeiter(id, username, password, name, funktion, gehalt);
					p.setBlockiert(mblockiert);
					break;
				default: 
					break;
			}
		} catch (Exception e) {
			System.err.println(e.getMessage());
			return null;
		}
		return p;
	}

	@Override
	public void artikelBestandVeraendern(Mitarbeiter mitarbeiter,
			int artikelnummer, int anzahl)
			throws ArtikelExistiertNichtException,
			ArtikelBestandIstKeineVielfacheDerPackungsgroesseException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void artikelBearbeiten(int artikelnummer, double preis,
			String bezeichnung) throws ArtikelExistiertNichtException {
		// TODO Auto-generated method stub
		
	}

	////////// Mitarbeiter Methoden ////////// 

	@Override
	public Mitarbeiter sucheMitarbeiter(int id) throws MitarbeiterExistiertNichtException {
		Mitarbeiter m = null;
		sout.println("mf");
		sout.println(id);
		String antwort = "?";
		try{
			antwort = sin.readLine();
			if(antwort.equals("MitarbeiterExistiertNicht"))
				throw new MitarbeiterExistiertNichtException(id, " - beim Empfangen der Daten!");

			//id
			// Wird nicht gebraucht
			//Username
			String username = sin.readLine();
			//Passwort
			String passwort = sin.readLine();
			//Name
			String name = sin.readLine();
			//Funktion
			MitarbeiterFunktion funktion = MitarbeiterFunktion.valueOf(sin.readLine());
			//Gehalt
			double gehalt = Double.parseDouble(sin.readLine());
			//Blockiert
			boolean blockiert = Boolean.valueOf(sin.readLine());

			m = new Mitarbeiter(id, username, passwort, name, funktion, gehalt);
			m.setBlockiert(blockiert);
		}catch(Exception e){
			System.err.println(e.getMessage());
			return null;
		}
		return m;
	}

	@Override
	public Vector<Mitarbeiter> gibAlleMitarbeiter() {
		Vector<Mitarbeiter> mitarbeiterListe = new Vector<Mitarbeiter>();
		Mitarbeiter m = null;
		sout.println("ma");

		try{
			int size = Integer.parseInt(sin.readLine());

			for(int i = 0; i < size ; i++){
				//id
				int id = Integer.parseInt(sin.readLine());
				//Username
				String username = sin.readLine();
				//Passwort
				String passwort = sin.readLine();
				//Name
				String name = sin.readLine();
				//Funktion
				MitarbeiterFunktion funktion = MitarbeiterFunktion.valueOf(sin.readLine());
				//Gehalt
				double gehalt = Double.parseDouble(sin.readLine());
				//Blockiert
				boolean blockiert = Boolean.valueOf(sin.readLine());

				m = new Mitarbeiter(id, username, passwort, name, funktion, gehalt);
				m.setBlockiert(blockiert);
				mitarbeiterListe.add(m);
			}

		}catch(Exception e){
			System.err.println(e.getMessage());
			return null;
		}
		return mitarbeiterListe;
	}

	@Override
	public void fuegeMitarbeiterHinzu(String username, String passwort, String name, MitarbeiterFunktion funktion, double gehalt) throws MitarbeiterExistiertBereitsException, UsernameExistiertBereitsException {
		sout.println("me");
		sout.println(username);
		sout.println(passwort);
		sout.println(name);
		sout.println(funktion);
		sout.println(gehalt);

		String antwort = "?";
		try{
			antwort = sin.readLine();
			if(antwort.equals("MitarbeiterExistiertBereits")){
				throw new MitarbeiterExistiertBereitsException(new Mitarbeiter(-1, "?", "?", "?", MitarbeiterFunktion.Mitarbeiter, 0), " - in ShopFassade (Einfugen von Mitarbeiter)!");
			}else if(antwort.equals("UsernameExistiertBereits")){
				throw new UsernameExistiertBereitsException(username, " - in ShopFassade (Einfugen von Mitarbeiter)!");
			}
			// OK

		}catch(Exception e){
			System.err.println(e.getMessage());
			return;
		}
	}

	@Override
	public void mitarbeiterBearbeiten(int id, String passwort, String name, MitarbeiterFunktion funktion, double gehalt, boolean blockiert) throws MitarbeiterExistiertNichtException {
		sout.println("mb");
		sout.println(id);
		sout.println(passwort);
		sout.println(name);
		sout.println(funktion);
		sout.println(gehalt);
		sout.println(blockiert);

		String antwort = "?";
		try{
			antwort = sin.readLine();
			if(antwort.equals("MitarbeiterExistiertNicht")){
				throw new MitarbeiterExistiertNichtException(id, " - in ShopFassade (Mitarbeiter Bearbeiten)!");
			}
			// OK

		}catch(Exception e){
			System.err.println(e.getMessage());
			return;
		}
	}

	@Override
	public void mitarbeiterLoeschen(Mitarbeiter m) {
		sout.println("ml");
		sout.println(m.getId());
	}
	

	@Override
	public void schreibeMitarbeiter() throws IOException {
		sout.println("sm");
	}

	////////// Ereignis Methoden ////////// 
	
	@Override
	public void schreibeEreignisse() throws IOException {
		sout.println("se");
	}

	@Override
	public String gibBestandsHistorie(Artikel artikel) throws IOException {
		sout.println("gbh");
		return sin.readLine();
	}

	@Override
	public int[] gibBestandsHistorieDaten(Artikel artikel) throws IOException {
		sout.println("gbhd");
		sout.println(artikel.getArtikelnummer());
		sin.readLine();
		int anzahl = Integer.parseInt(sin.readLine());
		int[] daten = new int[anzahl];
		for(int i = 0; i < anzahl; i++){
			daten[i] = Integer.parseInt(sin.readLine());
		}
		
		return daten;
		
	}

	@Override
	public String gibLogDatei() throws IOException {
		sout.println("gl");
		int anzahl = Integer.parseInt(sin.readLine());
		String logDatei = "";
		for(int i = 0; i < anzahl; i++){
			logDatei += sin.readLine()+"\n";
		}
		return logDatei;
	}
	
	
	@Override
	public void kundenBearbeiten(int id, String passwort, String name,
			String strasse, int plz, String wohnort, boolean blockiert)
			throws KundeExistiertNichtException {
		sout.println("kb");
		sout.println(id);
		sout.println(passwort);
		sout.println(name);
		sout.println(strasse);
		sout.println("" + plz);
		sout.println(wohnort);
		sout.println(blockiert);		
	}

	@Override
	public void fuegeKundenHinzu(String username, String passwort, String name, String strasse, int plz, String wohnort)
			throws KundeExistiertBereitsException,
			UsernameExistiertBereitsException {
		sout.println("ke");
		sout.println(username);
		sout.println(passwort);
		sout.println(name);
		sout.println(strasse);
		sout.println("" + plz);
		sout.println(wohnort);
		String ergebnis = "?";
		
		try {
			ergebnis = sin.readLine();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if (ergebnis.equals("kee")) {
//			Kunde erfolgreich eingef¸gt
			System.out.println("Kunde erfolgreich eingef¸gt");
		} else if (ergebnis.equals("keb")) {
			System.out.println("Kunde existiert bereits");
		} else if (ergebnis.equals("ueb")) {
			System.out.println("Username existiert bereits");
		}
	}

	
	@Override
	public Kunde loginVergessen(String name, String strasse, int zip, String wohnort) {
		Kunde k = null;
		sout.println("lv");
		sout.println(name);
		sout.println(strasse);
		sout.println("" + zip);
		sout.println(wohnort);
//		hgf
		String antwort = "?";
		
			try {
				antwort = sin.readLine();
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			if(antwort.equals("ken")) {
//				System.out.println("ken");
			} else if (antwort.equals("kse")) {
//				System.out.println("kse");
				k = empfangeKunde();
				System.out.println("kunde: " + k);
				return k;
			}
		return null;
	}
	
	public Kunde empfangeKunde() {
		Kunde k = null;
			try {
				int id = Integer.parseInt(sin.readLine());
				String username = sin.readLine();
				String passwort = sin.readLine();
				String name = sin.readLine();
				String strasse = sin.readLine();
				int plz = Integer.parseInt(sin.readLine());
				String wohnort = sin.readLine();
				k = new Kunde(id, username, passwort, name, strasse, plz, wohnort);
			} catch (NumberFormatException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		return k;
	}

}
