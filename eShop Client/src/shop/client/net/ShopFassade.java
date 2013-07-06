package shop.client.net;
// push comment
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.Socket;
import java.text.SimpleDateFormat;
import java.util.Date;
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
import shop.common.valueobjects.Massengutartikel;
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

	/**
	 * Methode zum Einfügen eines neuen Artikels in den Bestand. 
	 * Wenn der Artikel bereits im Bestand ist, wird der Bestand nicht geändert.
	 * 
	 * @param mitarbeiter Mitarbeiter der den Artikel in den Bestand einfügen will
	 * @param artikelnummer Artikelnummer des neuen Artikels
	 * @param bezeichnung Bezeichnung des neuen Artikels
	 * @param preis Preis des neuen Artikels
	 * @param bestand Bestand des neuen Artikels
	 * @throws ArtikelExistiertBereitsException
	 */
	public void fuegeArtikelEin(Mitarbeiter mitarbeiter, int artikelnummer, String bezeichnung, double preis, int bestand) throws ArtikelExistiertBereitsException {
		// Kennzeichen für gewählte Aktion senden
		sout.println("fae");
		// Parameter für Aktion senden
		sout.println(mitarbeiter.getId());
		sout.println(artikelnummer);
		sout.println(bezeichnung);
		sout.println(preis);
		sout.println(bestand);
		
		// Antwort vom Server lesen:
		String antwort = "?";
		try {
			antwort = sin.readLine();
		} catch (Exception e) {
			System.err.println(e.getMessage());
		}
		
		if (antwort.equals("ArtikelExistiertBereitsException")) {
			throw new ArtikelExistiertBereitsException(" - in 'fuegeArtikelEin()'");
		} 
	}

	/**
	 * Methode zum Einfügen eines neuen Massengutartikels in den Bestand. 
	 * Wenn der Massengutartikel bereits im Bestand ist, wird der Bestand nicht geändert.
	 * 
	 * @param mitarbeiter Mitarbeiter der den Massengutartikel in den Bestand einfügen will
	 * @param artikelnummer Artikelnummer des neuen Massengutartikels
	 * @param bezeichnung Bezeichnung des neuen Massengutartikels
	 * @param preis Preis des neuen Massengutartikels
	 * @param packungsgroesse Packungsgröße des neuen Massengutartikels
	 * @param bestand Bestand des neuen Massengutartikels
	 * @throws ArtikelExistiertBereitsException
	 * @throws ArtikelBestandIstKeineVielfacheDerPackungsgroesseException
	 */
	public void fuegeMassengutartikelEin(Mitarbeiter mitarbeiter, int artikelnummer, String bezeichnung, double preis, int packungsgroesse, int bestand) throws ArtikelExistiertBereitsException, ArtikelBestandIstKeineVielfacheDerPackungsgroesseException {
		// Kennzeichen für gewählte Aktion senden
		sout.println("fme");
		// Parameter für Aktion senden
		sout.println(mitarbeiter.getId());
		sout.println(artikelnummer);
		sout.println(bezeichnung);
		sout.println(preis);
		sout.println(packungsgroesse);
		sout.println(bestand);
		
		// Antwort vom Server lesen:
		String antwort = "?";
		try {
			antwort = sin.readLine();
		} catch (Exception e) {
			System.err.println(e.getMessage());
		}
		
		if (antwort.equals("ArtikelExistiertBereitsException")) {
			throw new ArtikelExistiertBereitsException(" - in 'fuegeMassengutartikelEin()'");
		} else
		if (antwort.equals("ArtikelBestandIstKeineVielfacheDerPackungsgroesseException")) {
			throw new ArtikelBestandIstKeineVielfacheDerPackungsgroesseException(" - in 'fuegeMassengutartikelEin()'");
		}
	}

	/**
	 * Methode zum verändern des Bestands eines Artikels.
	 * 
	 * @param mitarbeiter Mitarbeiter der den Bestand eines Artikels verändern will
	 * @param artikelnummer Artikelnummer des zu verändernden Artikels
	 * @param anzahl Anzahl des neuen Bestands
	 * @throws ArtikelExistiertNichtException
	 * @throws ArtikelBestandIstKeineVielfacheDerPackungsgroesseException
	 */
	public void artikelBestandVeraendern(Mitarbeiter mitarbeiter, int artikelnummer, int anzahl) throws ArtikelExistiertNichtException, ArtikelBestandIstKeineVielfacheDerPackungsgroesseException {
		// Kennzeichen für gewählte Aktion senden
		sout.println("abv");
		// Parameter für Aktion senden
		sout.println(mitarbeiter.getId());
		sout.println(artikelnummer);
		sout.println(anzahl);
		
		// Antwort vom Server lesen:
		String antwort = "?";
		try {
			antwort = sin.readLine();
		} catch (Exception e) {
			System.err.println(e.getMessage());
		}
		
		if (antwort.equals("ArtikelExistiertNichtException")) {
			throw new ArtikelExistiertNichtException(" - in 'artikelBestandVeraendern()'");
		} else
		if (antwort.equals("ArtikelBestandIstKeineVielfacheDerPackungsgroesseException")) {
			throw new ArtikelBestandIstKeineVielfacheDerPackungsgroesseException(" - in 'artikelBestandVeraendern()'");
		}
	}
	
	/**
	 * Methode, die eine Liste aller im Bestand befindlichen Artikel, 
	 * nach ihrer Artikelnummer sortiert, zurückgibt.
	 * 
	 * @return Liste aller Artikel sortiert nach Artikelnummer
	 */
	public List<Artikel> gibAlleArtikelSortiertNachArtikelnummer() {
		List<Artikel> liste = new Vector<Artikel>();

		// Kennzeichen für gewählte Aktion senden
		sout.println("gaasna");

		// Antwort vom Server lesen:
		String antwort = "?";
		try {
			// Anzahl gefundener Artikel einlesen
			antwort = sin.readLine();
			int anzahl = Integer.parseInt(antwort);
			for (int i = 0; i < anzahl; i++) {
				// Artikeltyp von Artikel i einlesen 
				antwort = sin.readLine();
				String artikeltyp = antwort;
				// Nummer von Artikel i einlesen
				antwort = sin.readLine();
				int nummer = Integer.parseInt(antwort);
				// Bezeichnung von Artikel i einlesen
				antwort = sin.readLine();
				String bezeichnung = antwort;
				// Preis von Artikel i einlesen
				antwort = sin.readLine();
				double preis = Double.valueOf(antwort);
				// Bestand von Artikel i einlesen
				antwort = sin.readLine();
				int bestand = Integer.parseInt(antwort);
				if (artikeltyp.equals("Massengutartikel")) {
					// Packungsgroesse von Massengutartikel i einlesen
					antwort = sin.readLine();
					int packungsgroesse = Integer.parseInt(antwort);
					// Neues Massengutartikel-Objekt erzeugen...
					Artikel artikel = new Massengutartikel(nummer, bezeichnung, preis, packungsgroesse, bestand);
					// ... und in Liste eintragen
					liste.add(artikel);
				} else {
					// Neues Artikel-Objekt erzeugen...
					Artikel artikel = new Artikel(nummer, bezeichnung, preis, bestand);
					// ... und in Liste eintragen
					liste.add(artikel);
				}
			}
		} catch (Exception e) {
			System.err.println(e.getMessage());
			return null;
		}
		return liste;
	}

	/**
	 * Methode, die eine Liste aller im Bestand befindlichen Artikel, 
	 * nach ihrer Bezeichnung sortiert, zurückgibt.
	 * 
	 * @return Liste aller Artikel sortiert nach Bezeichnung
	 */
	public List<Artikel> gibAlleArtikelSortiertNachBezeichnung() {
		List<Artikel> liste = new Vector<Artikel>();

		// Kennzeichen für gewählte Aktion senden
		sout.println("gaasnb");

		// Antwort vom Server lesen:
		String antwort = "?";
		try {
			// Anzahl gefundener Artikel einlesen
			antwort = sin.readLine();
			int anzahl = Integer.parseInt(antwort);
			for (int i = 0; i < anzahl; i++) {
				// Artikeltyp von Artikel i einlesen 
				antwort = sin.readLine();
				String artikeltyp = antwort;
				// Nummer von Artikel i einlesen
				antwort = sin.readLine();
				int nummer = Integer.parseInt(antwort);
				// Bezeichnung von Artikel i einlesen
				antwort = sin.readLine();
				String bezeichnung = antwort;
				// Preis von Artikel i einlesen
				antwort = sin.readLine();
				double preis = Double.valueOf(antwort);
				// Bestand von Artikel i einlesen
				antwort = sin.readLine();
				int bestand = Integer.parseInt(antwort);
				if (artikeltyp.equals("Massengutartikel")) {
					// Packungsgroesse von Massengutartikel i einlesen
					antwort = sin.readLine();
					int packungsgroesse = Integer.parseInt(antwort);
					// Neues Massengutartikel-Objekt erzeugen...
					Artikel artikel = new Massengutartikel(nummer, bezeichnung, preis, packungsgroesse, bestand);
					// ... und in Liste eintragen
					liste.add(artikel);
				} else {
					// Neues Artikel-Objekt erzeugen...
					Artikel artikel = new Artikel(nummer, bezeichnung, preis, bestand);
					// ... und in Liste eintragen
					liste.add(artikel);
				}
			}
		} catch (Exception e) {
			System.err.println(e.getMessage());
			e.printStackTrace();
			return null;
		}
		return liste;
	}

	/**
	 * Methode zum Suchen von Artikeln anhand der Artikelnummer. Es wird eine Liste von Artikeln
	 * zurückgegeben, die alle Artikel die Artikelnummer enthalten.
	 * 
	 * @param artikelnummer Artikelnummer des gesuchten Artikels
	 * @return Liste der gefundenen Artikel (evtl. leer)
	 */
	public List<Artikel> sucheArtikel(int artikelnummer) {
		List<Artikel> liste = new Vector<Artikel>();
		
		// Kennzeichen für gewählte Aktion senden
		sout.println("saa");
		// Parameter für Aktion senden
		sout.println(artikelnummer);

		// Antwort vom Server lesen:
		String antwort = "?";
		try {
			// Anzahl gefundener Artikel einlesen
			antwort = sin.readLine();
			int anzahl = Integer.parseInt(antwort);
			for (int i = 0; i < anzahl; i++) {
				// Artikeltyp von Artikel i einlesen 
				antwort = sin.readLine();
				String artikeltyp = antwort;
				// Nummer von Artikel i einlesen
				antwort = sin.readLine();
				int nummer = Integer.parseInt(antwort);
				// Bezeichnung von Artikel i einlesen
				antwort = sin.readLine();
				String artikelbezeichnung = antwort;
				// Preis von Artikel i einlesen
				antwort = sin.readLine();
				double preis = Double.valueOf(antwort);
				// Bestand von Artikel i einlesen
				antwort = sin.readLine();
				int bestand = Integer.parseInt(antwort);
				if (artikeltyp.equals("Massengutartikel")) {
					// Packungsgroesse von Massengutartikel i einlesen
					antwort = sin.readLine();
					int packungsgroesse = Integer.parseInt(antwort);
					// Neues Massengutartikel-Objekt erzeugen...
					Artikel artikel = new Massengutartikel(nummer, artikelbezeichnung, preis, packungsgroesse, bestand);
					// ... und in Liste eintragen
					liste.add(artikel);
				} else {
					// Neues Artikel-Objekt erzeugen...
					Artikel artikel = new Artikel(nummer, artikelbezeichnung, preis, bestand);
					// ... und in Liste eintragen
					liste.add(artikel);
				}
			}
		} catch (Exception e) {
			System.err.println(e.getMessage());
			return null;
		}
		return liste;
	}

	/**
	 * Methode zum Suchen von Artikeln anhand der Bezeichnung. Es wird eine Liste von Artikeln
	 * zurückgegeben, die alle Artikel die Bezeichnung enthalten.
	 * 
	 * @param bezeichnung Bezeichnung des gesuchten Artikels
	 * @return Liste der gefundenen Artikel (evtl. leer)
	 */
	public List<Artikel> sucheArtikel(String bezeichnung) {
		List<Artikel> liste = new Vector<Artikel>();
		
		// Kennzeichen für gewählte Aktion senden
		sout.println("sab");
		// Parameter für Aktion senden
		sout.println(bezeichnung);

		// Antwort vom Server lesen:
		String antwort = "?";
		try {
			// Anzahl gefundener Artikel einlesen
			antwort = sin.readLine();
			int anzahl = Integer.parseInt(antwort);
			for (int i = 0; i < anzahl; i++) {
				// Artikeltyp von Artikel i einlesen 
				antwort = sin.readLine();
				String artikeltyp = antwort;
				// Nummer von Artikel i einlesen
				antwort = sin.readLine();
				int nummer = Integer.parseInt(antwort);
				// Bezeichnung von Artikel i einlesen
				antwort = sin.readLine();
				String artikelbezeichnung = antwort;
				// Preis von Artikel i einlesen
				antwort = sin.readLine();
				double preis = Double.valueOf(antwort);
				// Bestand von Artikel i einlesen
				antwort = sin.readLine();
				int bestand = Integer.parseInt(antwort);
				if (artikeltyp.equals("Massengutartikel")) {
					// Packungsgroesse von Massengutartikel i einlesen
					antwort = sin.readLine();
					int packungsgroesse = Integer.parseInt(antwort);
					// Neues Massengutartikel-Objekt erzeugen...
					Artikel artikel = new Massengutartikel(nummer, artikelbezeichnung, preis, packungsgroesse, bestand);
					// ... und in Liste eintragen
					liste.add(artikel);
				} else {
					// Neues Artikel-Objekt erzeugen...
					Artikel artikel = new Artikel(nummer, artikelbezeichnung, preis, bestand);
					// ... und in Liste eintragen
					liste.add(artikel);
				}
			}
		} catch (Exception e) {
			System.err.println(e.getMessage());
			return null;
		}
		return liste;
	}

	/**
	 * Methode zum bearbeiten eines Artikels.
	 * 
	 * @param artikelnumme Artikelnummer des Artikels
	 * @param preis Preis des Artikels
	 * @param bezeichnung Bezeichnung des Artikels
	 * @thorws ArtikelExistiertNichtException
	 */
	public void artikelBearbeiten(int artikelnummer, double preis, String bezeichnung) throws ArtikelExistiertNichtException {
		// Kennzeichen für gewählte Aktion senden
		sout.println("ab");
		// Parameter für Aktion senden
		sout.println(artikelnummer);
		sout.println(preis);
		sout.println(bezeichnung);
		
		// Antwort vom Server lesen:
		String antwort = "?";
		try {
			antwort = sin.readLine();
		} catch (Exception e) {
			System.err.println(e.getMessage());
		}
		
		if (antwort.equals("ArtikelExistiertNichtException")) {
			throw new ArtikelExistiertNichtException(" - in 'artikelBearbeiten()'");
		} 
	}
	
	/**
	 * Methode zum Entfernen eines Artikels aus dem Bestand.
	 * 
	 * @param mitarbeiter Mitarbeiter der den Artikel aus dem Bestand entfernen will
	 * @param artikelnummer Artikelnummer des zu entfernenden Artikels
	 * @throws ArtikelExistiertNichtException
	 * @throws IOException
	 */
	public void entferneArtikel(Mitarbeiter mitarbeiter, int artikelnummer) throws ArtikelExistiertNichtException, IOException {
		// Kennzeichen für gewählte Aktion senden
		sout.println("ea");
		// Parameter für Aktion senden
		sout.println(mitarbeiter.getId());
		sout.println(artikelnummer);

		// Antwort vom Server lesen:
		String antwort = "?";
		try {
			antwort = sin.readLine();
		} catch (Exception e) {
			System.err.println(e.getMessage());
		}
		
		if (antwort.equals("ArtikelExistiertNichtException")) {
			throw new ArtikelExistiertNichtException(" - in 'entferneArtikel()'");
		} else
		if (antwort.equals("IOException")) {
			throw new IOException();
		}
	}

	/**
	 * Methode zum Speichern des Buchbestands in einer Datei.
	 * 
	 * @throws IOException
	 */
	public void schreibeArtikel() throws IOException {
		// Kennzeichen für gewählte Aktion senden
		sout.println("scha");
		// (Parameter sind hier nicht zu senden)

		// Antwort vom Server lesen:
		String antwort = "?";
		try {
			antwort = sin.readLine();
		} catch (Exception e) {
			System.err.println(e.getMessage());
		}
		
		if (antwort.equals("IOException")) {
			throw new IOException();
		}
	}

	@Override
	public Mitarbeiter sucheMitarbeiter(int id)
			throws MitarbeiterExistiertNichtException {
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
	public void mitarbeiterLoeschen(Mitarbeiter m) {
		sout.println("ml");
		sout.println(m.getId());
	}

	@Override
	public void schreibeMitarbeiter() throws IOException {
		sout.println("sm");
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

	/**
	 * Methode, die eine Liste aller im Warenkorb befindlichen Artikel zurückgibt.
	 * 
	 * @return Liste aller Warenkorb Artikel des Kunden
	 */
	public List<WarenkorbArtikel> gibWarenkorb(Kunde kunde) {
		List<WarenkorbArtikel> liste = new Vector<WarenkorbArtikel>();

		// Kennzeichen für gewählte Aktion senden
		sout.println("gw");
		// Parameter für Aktion senden
		sout.println(kunde.getId());

		// Antwort vom Server lesen:
		String antwort = "?";
		try {
			// Anzahl gefundener Warenkorb Artikel einlesen
			antwort = sin.readLine();
			int anzahl = Integer.parseInt(antwort);
			Artikel artikel = null;
			for (int i = 0; i < anzahl; i++) {
				// Artikeltyp von Artikel i einlesen 
				antwort = sin.readLine();
				String artikeltyp = antwort;
				// Nummer von Artikel i einlesen
				antwort = sin.readLine();
				int nummer = Integer.parseInt(antwort);
				// Bezeichnung von Artikel i einlesen
				antwort = sin.readLine();
				String artikelbezeichnung = antwort;
				// Preis von Artikel i einlesen
				antwort = sin.readLine();
				double preis = Double.valueOf(antwort);
				// Bestand von Artikel i einlesen
				antwort = sin.readLine();
				int bestand = Integer.parseInt(antwort);
				if (artikeltyp.equals("Massengutartikel")) {
					// Packungsgroesse von Massengutartikel i einlesen
					antwort = sin.readLine();
					int packungsgroesse = Integer.parseInt(antwort);
					// Neues Massengutartikel-Objekt erzeugen
					artikel = new Massengutartikel(nummer, artikelbezeichnung, preis, packungsgroesse, bestand);
					
				} else {
					// Neues Artikel-Objekt erzeugen
					artikel = new Artikel(nummer, artikelbezeichnung, preis, bestand);
				}
				// Stückzahl von Warenkorb Artikel i einlesen
				antwort = sin.readLine();
				int stueckzahl = Integer.parseInt(antwort);
				// In den Warenkorb hinzufügen
				liste.add(new WarenkorbArtikel(artikel, stueckzahl));
			}
		} catch (Exception e) {
			System.err.println(e.getMessage());
			return null;
		}
		return liste;
	}
	
	/**
	 * Methode zum in den Warenkorb legen eines Artikels anhand eines Kunden.
	 * 
	 * @param kunde Kunde der den Artikel in seinen Warenkorb legen will
	 * @param artikelnummer Artikelnummer des Artikels der in den Warenkorb zu legen ist
	 * @param stueckzahl Stückzahl des Artikels
	 * @throws ArtikelBestandIstZuKleinException
	 * @throws ArtikelExistiertNichtException
	 * @throws ArtikelBestandIstKeineVielfacheDerPackungsgroesseException
	 */
	public void inDenWarenkorbLegen(Kunde kunde, int artikelnummer, int stueckzahl) throws ArtikelBestandIstZuKleinException, ArtikelExistiertNichtException, ArtikelBestandIstKeineVielfacheDerPackungsgroesseException {
		// Kennzeichen für gewählte Aktion senden
		sout.println("idwl");
		// Parameter für Aktion senden
		sout.println(kunde.getId());
		sout.println(artikelnummer);
		sout.println(stueckzahl);
		
		// Antwort vom Server lesen:
		String antwort = "?";
		try {
			antwort = sin.readLine();
		} catch (Exception e) {
			System.err.println(e.getMessage());
		}
		
		if (antwort.equals("ArtikelBestandIstZuKleinException")) {
			throw new ArtikelBestandIstZuKleinException(" - in 'inDenWarenkorbLegen()'");
		} else
		if (antwort.equals("ArtikelExistiertNichtException")) {
			throw new ArtikelExistiertNichtException(" - in 'inDenWarenkorbLegen()'");
		} else
		if (antwort.equals("ArtikelBestandIstKeineVielfacheDerPackungsgroesseException")) {
			throw new ArtikelBestandIstKeineVielfacheDerPackungsgroesseException(" - in 'inDenWarenkorbLegen()'");
		}		
	}

	/**
	 * Methode um einen Artikel aus dem Warenkorb heraus zu nehmen anhand eines Kunden.
	 * 
	 * @param kunde Kunde der den Artikel aus seinem Warenkorb herausnehmen will
	 * @param artikelnummer Artikelnummer eines Artikels der aus dem Warenkorb heraus zu nehmen ist
	 * @throws ArtikelExistiertNichtException
	 * @throws ArtikelBestandIstKeineVielfacheDerPackungsgroesseException
	 */
	public void ausDemWarenkorbHerausnehmen(Kunde kunde, int artikelnummer) throws ArtikelExistiertNichtException, ArtikelBestandIstKeineVielfacheDerPackungsgroesseException {
		// Kennzeichen für gewählte Aktion senden
		sout.println("adwh");
		// Parameter für Aktion senden
		sout.println(kunde.getId());
		sout.println(artikelnummer);
		
		// Antwort vom Server lesen:
		String antwort = "?";
		try {
			antwort = sin.readLine();
		} catch (Exception e) {
			System.err.println(e.getMessage());
		}
		
		if (antwort.equals("ArtikelExistiertNichtException")) {
			throw new ArtikelExistiertNichtException(" - in 'ausDemWarenkorbHerausnehmen()'");
		} else
		if (antwort.equals("ArtikelBestandIstKeineVielfacheDerPackungsgroesseException")) {
			throw new ArtikelBestandIstKeineVielfacheDerPackungsgroesseException(" - in 'ausDemWarenkorbHerausnehmen()'");
		}		
	}
	
	/**
	 * Methode um die Stückzahl eines Warenkorb Artikels im Warenkorb eines Kunden zu ändern.
	 * 
	 * @param kunde Kunde der die Stückzahl des Warenkorb Artikels ändern will
	 * @param warenkorbArtikelnummer Warenkorb Artikelnummer des Warenkorb Artikels dessen Stückzahl zu ändern ist
	 * @param neueStueckzahl NeueStückzahl des Warenkorb Artikels
	 * @throws ArtikelBestandIstZuKleinException
	 * @throws ArtikelExistiertNichtException
	 * @throws ArtikelBestandIstKeineVielfacheDerPackungsgroesseException
	 */
	public void stueckzahlAendern(Kunde kunde, int warenkorbArtikelnummer, int neueStueckzahl) throws ArtikelBestandIstZuKleinException, ArtikelExistiertNichtException, ArtikelBestandIstKeineVielfacheDerPackungsgroesseException {
		// Kennzeichen für gewählte Aktion senden
		sout.println("sa");
		// Parameter für Aktion senden
		sout.println(kunde.getId());
		sout.println(warenkorbArtikelnummer);
		sout.println(neueStueckzahl);
		
		// Antwort vom Server lesen:
		String antwort = "?";
		try {
			antwort = sin.readLine();
		} catch (Exception e) {
			System.err.println(e.getMessage());
		}
		
		if (antwort.equals("ArtikelBestandIstZuKleinException")) {
			throw new ArtikelBestandIstZuKleinException(" - in 'stueckzahlAendern()'");
		} else
		if (antwort.equals("ArtikelExistiertNichtException")) {
			throw new ArtikelExistiertNichtException(" - in 'stueckzahlAendern()'");
		} else
		if (antwort.equals("ArtikelBestandIstKeineVielfacheDerPackungsgroesseException")) {
			throw new ArtikelBestandIstKeineVielfacheDerPackungsgroesseException(" - in 'stueckzahlAendern()'");
		}	
	}

	/**
	 * Methode zum Kaufen von Artikeln anhand eines Kunden. Es wird eine Rechnung
	 * zurückgegeben.
	 * 
	 * @param kunde Kunde der die einzelnen Artikel, die sich in seinem Warenkorb befinden, kaufen will
	 * @return Rechnung
	 * @throws IOException
	 * @throws WarenkorbIstLeerException
	 */
	public Rechnung kaufen(Kunde kunde) throws IOException, WarenkorbIstLeerException {
		List<WarenkorbArtikel> warenkorb = new Vector<WarenkorbArtikel>();
		
		// Kennzeichen für gewählte Aktion senden
		sout.println("k");
		// Parameter für Aktion senden
		sout.println(kunde.getId());

		// Antwort vom Server lesen:
		String antwort = "?";
		antwort = sin.readLine();
		if (antwort.equals("IOException")) {
			throw new IOException();
		} else
		if (antwort.equals("WarenkorbIstLeerException")) {
			throw new WarenkorbIstLeerException(" - in 'kaufen()'");
		} else {
			Date datum = null;
			try {
				// Datum der Rechnung einlesen
				System.out.println(antwort);
				datum = new SimpleDateFormat("EEE MMM dd HH:mm:ss zzz yyyy").parse(antwort);
				// Anzahl gefundener Warenkorb Artikel einlesen
				antwort = sin.readLine();
				int anzahl = Integer.parseInt(antwort);
				Artikel artikel = null;
				for (int i = 0; i < anzahl; i++) {
					// Artikeltyp von Artikel i einlesen 
					antwort = sin.readLine();
					String artikeltyp = antwort;
					// Nummer von Artikel i einlesen
					antwort = sin.readLine();
					int nummer = Integer.parseInt(antwort);
					// Bezeichnung von Artikel i einlesen
					antwort = sin.readLine();
					String artikelbezeichnung = antwort;
					// Preis von Artikel i einlesen
					antwort = sin.readLine();
					double preis = Double.valueOf(antwort);
					// Bestand von Artikel i einlesen
					antwort = sin.readLine();
					int bestand = Integer.parseInt(antwort);
					if (artikeltyp.equals("Massengutartikel")) {
						// Packungsgroesse von Massengutartikel i einlesen
						antwort = sin.readLine();
						int packungsgroesse = Integer.parseInt(antwort);
						// Neues Massengutartikel-Objekt erzeugen
						artikel = new Massengutartikel(nummer, artikelbezeichnung, preis, packungsgroesse, bestand);
						
					} else {
						// Neues Artikel-Objekt erzeugen
						artikel = new Artikel(nummer, artikelbezeichnung, preis, bestand);
					}
					// Stückzahl von Warenkorb Artikel i einlesen
					antwort = sin.readLine();
					int stueckzahl = Integer.parseInt(antwort);
					// In den Warenkorb hinzufügen
					warenkorb.add(new WarenkorbArtikel(artikel, stueckzahl));
				}
			} catch (Exception e) {
				System.err.println(e.getMessage());
				e.printStackTrace();
				return null;
			}
			return new Rechnung(kunde, datum, warenkorb);
		}
	}

	/**
	 * Methode zum Leeren aller Artikeln aus dem Warenkorb eines Kunden. 
	 * 
	 * @param kunde Kunde der alle Artikel, die sich in seinem Warenkorb befinden, entfernen will
	 * @throws ArtikelBestandIstKeineVielfaceDerPackungsgroesseException
	 */
	public void leeren(Kunde kunde) throws ArtikelBestandIstKeineVielfacheDerPackungsgroesseException {
		// Kennzeichen für gewählte Aktion senden
		sout.println("l");
		// Parameter für Aktion senden
		sout.println(kunde.getId());

		// Antwort vom Server lesen:
		String antwort = "?";
		try {
			antwort = sin.readLine();
		} catch (Exception e) {
			System.err.println(e.getMessage());
		}
		
		if (antwort.equals("ArtikelBestandIstKeineVielfacheDerPackungsgroesseException")) {
			throw new ArtikelBestandIstKeineVielfacheDerPackungsgroesseException(" - in 'leeren()'");
		}		
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
			PersonTyp personTyp = PersonTyp.valueOf(antwort);
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
					p = new Kunde(id, username, password, name, strasse, plz, wohnort); 
					// Blockiert
					antwort = sin.readLine();
					boolean kBlockiert = Boolean.valueOf(antwort);
					p.setBlockiert(kBlockiert);
					break;
				case Mitarbeiter: 
					// MitarbeiterFunktion
					antwort = sin.readLine();
					MitarbeiterFunktion funktion = MitarbeiterFunktion.valueOf(antwort);
					// Gehalt
					antwort = sin.readLine();
					double gehalt = Double.valueOf(antwort);
					// Blockiert
					antwort = sin.readLine();
					boolean mBlockiert = Boolean.valueOf(antwort);
					p = new Mitarbeiter(id, username, password, name, funktion, gehalt);
					p.setBlockiert(mBlockiert);
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
	public void schreibeEreignisse() throws IOException {
		sout.println("se");
	}

	@Override
	public String gibBestandsHistorie(int artikelnummer) throws IOException {
		sout.println("gbh");
		return sin.readLine();
	}

	@Override
	public int[] gibBestandsHistorieDaten(int artikelnummer) throws IOException {
		sout.println("gbhd");
		sout.println(artikelnummer);
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
	public void fuegeMitarbeiterHinzu(String username, String passwort,
			String name, MitarbeiterFunktion funktion, double gehalt)
			throws MitarbeiterExistiertBereitsException,
			UsernameExistiertBereitsException {
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
	public void mitarbeiterBearbeiten(int id, String passwort, String name,
			MitarbeiterFunktion funktion, double gehalt, boolean blockiert)
			throws MitarbeiterExistiertNichtException {
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
	public void kundenBearbeiten(int id, String passwort, String name,
			String strasse, int plz, String wohnort, boolean blockiert)
			throws KundeExistiertNichtException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void fuegeKundenHinzu(String username, String passwort, String name,
			String strasse, int plz, String wohnort)
			throws KundeExistiertBereitsException,
			UsernameExistiertBereitsException {
		// TODO Auto-generated method stub
		
	}
	
	@Override
	public Kunde loginVergessen(String name, String strasse, int plz,
			String wohnort) {
		// TODO Auto-generated method stub
		return null;
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

}


