package shop.client.net;

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
 * Die Klasse stellt die von der GUI erwarteten Methoden zur Verfuegung
 * und realisiert (transparent fuer die GUI) die Kommunikation mit dem 
 * Server.
 * Anmerkung: Auf dem Server wird dann die eigentliche, von der lokalen
 * Shopversion bekannte Funktionalitaet implementiert (z.B. Artikel 
 * einfuegen und suchen)
 * 
 * @author Torres
 */
public class ShopFassade implements ShopInterface {

	// Datenstrukturen fuer die Kommunikation
	private Socket socket = null;
	private BufferedReader sin; // server-input stream
	private PrintStream sout; // server-output stream
	
	
	/**
	 * Konstruktor, der die Verbindung zum Server aufbaut (Socket) und dieser
	 * Grundlage Eingabe- und Ausgabestreams fuer die Kommunikation mit dem
	 * Server erzeugt.
	 * 
	 * @param host Rechner, auf dem der Server laeuft
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
			// Wenn im "try"-Block Fehler auftreten, dann Socket schlie�en:
			if (socket != null)
				socket.close();
			System.err.println("Socket geschlossen");
			System.exit(0);
		}
		
		// Verbindung erfolgreich hergestellt: IP-Adresse und Port ausgeben
		System.err.println("Verbunden: " + socket.getInetAddress() + ":"
				+ socket.getPort());	
		
		// Begrue�ungsmeldung vom Server lesen
		String message = sin.readLine();
		System.out.println(message);
	}

	@Override
	public void fuegeArtikelEin(Mitarbeiter mitarbeiter, int artikelnummer, String bezeichnung, double preis, int bestand) throws ArtikelExistiertBereitsException {
		// Kennzeichen fuer gewaehlte Aktion senden
		sout.println("fae");
		// Parameter fuer Aktion senden
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

@Override
	public void fuegeMassengutartikelEin(Mitarbeiter mitarbeiter, int artikelnummer, String bezeichnung, double preis, int packungsgroesse, int bestand) throws ArtikelExistiertBereitsException, ArtikelBestandIstKeineVielfacheDerPackungsgroesseException {
		// Kennzeichen fuer gewaehlte Aktion senden
		sout.println("fme");
		// Parameter fuer Aktion senden
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
	 * Diese Methode wird zum Veraendern des Bestands des Artikels auf dem Server genutzt.
	 * Sie sendet dem Server die Informationen gemaess des Protokolls.
	 */
	@Override
	public void artikelBestandVeraendern(Mitarbeiter mitarbeiter, int artikelnummer, int anzahl) throws ArtikelExistiertNichtException, ArtikelBestandIstKeineVielfacheDerPackungsgroesseException {
		// Kennzeichen fuer gewaehlte Aktion senden
		sout.println("abv");
		// Parameter fuer Aktion senden
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
	@Override
	public List<Artikel> gibAlleArtikelSortiertNachArtikelnummer() {
		List<Artikel> liste = new Vector<Artikel>();

		// Kennzeichen fuer gewaehlte Aktion senden
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

	@Override
	public List<Artikel> gibAlleArtikelSortiertNachBezeichnung() {
		List<Artikel> liste = new Vector<Artikel>();

		// Kennzeichen fuer gewaehlte Aktion senden
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
			return null;
		}
		return liste;
	}

	@Override
	public List<Artikel> sucheArtikel(int artikelnummer) {
		List<Artikel> liste = new Vector<Artikel>();

		// Kennzeichen fuer gewaehlte Aktion senden
		sout.println("saa");
		// Parameter fuer Aktion senden
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

	@Override
	public List<Artikel> sucheArtikel(String bezeichnung) {
		List<Artikel> liste = new Vector<Artikel>();
		
		// Kennzeichen fuer gewaehlte Aktion senden
		sout.println("sab");
		// Parameter fuer Aktion senden
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
	 * Diese Methode wird zum Bearbeiten von Artikeln auf dem Server genutzt.
	 * Sie sendet dem Server die Informationen gemaess des Protokolls.
	 */
	@Override
	public void artikelBearbeiten(int artikelnummer, double preis, String bezeichnung) throws ArtikelExistiertNichtException {
		// Kennzeichen fuer gewaehlte Aktion senden
		sout.println("ab");
		// Parameter fuer Aktion senden
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
	 */
	@Override
	public void entferneArtikel(Mitarbeiter mitarbeiter, int artikelnummer) throws ArtikelExistiertNichtException, IOException {
		// Kennzeichen fuer gewaehlte Aktion senden
		sout.println("ea");
		// Parameter fuer Aktion senden
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

	@Override
	public void schreibeArtikel() throws IOException {
		// Kennzeichen fuer gewaehlte Aktion senden
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

	/**
	 * Diese Methode wird zum Suchen von Mitarbeitern genutzt. Sie sendet und
	 * empfaengt Informationen gemaess des Protokolls.
	 */
	@Override
	public Mitarbeiter sucheMitarbeiter(int id)
			throws MitarbeiterExistiertNichtException {
		Mitarbeiter m = null;
		sout.println("mf");
		sout.println(id);
		String antwort = "?";
		try {
			antwort = sin.readLine();
			if(antwort.equals("MitarbeiterExistiertNicht"))
				throw new MitarbeiterExistiertNichtException(id, " - beim Empfangen der Daten!");

			// id
			// Wird nicht gebraucht
			// Username
			String username = sin.readLine();
			// Passwort
			String passwort = sin.readLine();
			// Name
			String name = sin.readLine();
			//Funktion
			MitarbeiterFunktion funktion = MitarbeiterFunktion.valueOf(sin.readLine());
			//Gehalt
			double gehalt = Double.parseDouble(sin.readLine());
			// Blockiert
			boolean blockiert = Boolean.valueOf(sin.readLine());

			m = new Mitarbeiter(id, username, passwort, name, funktion, gehalt);
			m.setBlockiert(blockiert);
		}catch(Exception e){
			System.err.println(e.getMessage());
			return null;
		}
		return m;
	}

	/**
	 * Diese Methode wird dazu genutzt um die Liste der Mitarbeiter auf dem Server zu bekommen.
	 * Sie sendet und empfaengt Informationen gemaess des Protokolls.
	 */
	@Override
	public Vector<Mitarbeiter> gibAlleMitarbeiter() {
		Vector<Mitarbeiter> mitarbeiterListe = new Vector<Mitarbeiter>();
		Mitarbeiter m = null;
		sout.println("ma");

		try {
			int size = Integer.parseInt(sin.readLine());

			for (int i = 0; i < size; i++) {
				// id
				int id = Integer.parseInt(sin.readLine());
				// Username
				String username = sin.readLine();
				// Passwort
				String passwort = sin.readLine();
				// Name
				String name = sin.readLine();
				// Funktion
				MitarbeiterFunktion funktion = MitarbeiterFunktion.valueOf(sin
						.readLine());
				// Gehalt
				double gehalt = Double.parseDouble(sin.readLine());
				// Blockiert
				boolean blockiert = Boolean.valueOf(sin.readLine());

				m = new Mitarbeiter(id, username, passwort, name, funktion,
						gehalt);
				m.setBlockiert(blockiert);
				mitarbeiterListe.add(m);
			}

		} catch (Exception e) {
			System.err.println(e.getMessage());
			return null;
		}
		return mitarbeiterListe;
	}

	/**
	 * Diese Methode wird zum Loeschen von Mitarbeitern genutzt Sie sendet
	 * Informationen gemaess des Protokolls.
	 */
	@Override
	public void mitarbeiterLoeschen(Mitarbeiter m) {
		sout.println("ml");
		sout.println(m.getId());
	}

	/**
	 * Diese Methode sendet den Befehl zum speichern der Mitarbeiterliste auf
	 * dem Server. Sie sendet Informationen gemaess des Protokolls.
	 */
	@Override
	public void schreibeMitarbeiter() throws IOException {
		sout.println("sm");
		String antwort = "?";
		try {
			antwort = sin.readLine();
		} catch (Exception e) {
			System.err.println(e.getMessage());
		}
		if (antwort.equals("IOException")) {
			throw new IOException("Fehler beim schreiben der Mitarbeiterdaten!");
		}
	}

	@Override
	public Kunde sucheKunde(int id) throws KundeExistiertNichtException {

		Kunde k = null;
		sout.println("sk");
		sout.println("" + id);

		try {
			if (sin.readLine().equals("kse")) {
				id = Integer.parseInt(sin.readLine());
				String username = sin.readLine();
				String passwort = sin.readLine();
				String name = sin.readLine();
				String strasse = sin.readLine();
				int plz = Integer.parseInt(sin.readLine());
				String wohnort = sin.readLine();
				k = new Kunde(id, username, passwort, name, strasse, plz,
						wohnort);
			}
		} catch (NumberFormatException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if (k != null) {
			return k;
		} else {
			return null;
		}
	}

	@Override
	public Vector<Kunde> gibAlleKunden() {
		Vector<Kunde> kundenListe = new Vector<Kunde>();
		Kunde k = null;
		sout.println("gak");

		try {
			int size = Integer.parseInt(sin.readLine());

			for (int i = 0; i < size; i++) {
				// id
				int id = Integer.parseInt(sin.readLine());
				// Username
				String username = sin.readLine();
				// Passwort
				String passwort = sin.readLine();
				// Name
				String name = sin.readLine();
				// Strasse
				String strasse = sin.readLine();
				// plz
				int plz = Integer.parseInt(sin.readLine());
				// Wohnort
				String wohnort = sin.readLine();
				// Blockiert
				boolean blockiert = Boolean.valueOf(sin.readLine());

				k = new Kunde(id, username, passwort, name, strasse, plz,
						wohnort);
				k.setBlockiert(blockiert);
				kundenListe.add(k);
			}

		} catch (Exception e) {
			System.err.println(e.getMessage());
			return null;
		}
		return kundenListe;
	}

	@Override
	public void kundenLoeschen(Kunde k) {
		sout.println("kl");
		sout.println(k.getId());
	}

	@Override
	public void schreibeKunden() throws IOException {
		sout.println("sck");
	}

	@Override
	public List<WarenkorbArtikel> gibWarenkorb(Kunde kunde) {
		List<WarenkorbArtikel> liste = new Vector<WarenkorbArtikel>();

		// Kennzeichen fuer gewaehlte Aktion senden
		sout.println("gw");
		// Parameter fuer Aktion senden
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
					artikel = new Massengutartikel(nummer, artikelbezeichnung,
							preis, packungsgroesse, bestand);

				} else {
					// Neues Artikel-Objekt erzeugen
					artikel = new Artikel(nummer, artikelbezeichnung, preis,
							bestand);
				}
				// Stueckzahl von Warenkorb Artikel i einlesen
				antwort = sin.readLine();
				int stueckzahl = Integer.parseInt(antwort);
				// In den Warenkorb hinzufuegen
				liste.add(new WarenkorbArtikel(artikel, stueckzahl));
			}
		} catch (Exception e) {
			System.err.println(e.getMessage());
			return null;
		}
		return liste;
	}

	@Override
	public void inDenWarenkorbLegen(Kunde kunde, int artikelnummer, int stueckzahl) throws ArtikelBestandIstZuKleinException, ArtikelExistiertNichtException, ArtikelBestandIstKeineVielfacheDerPackungsgroesseException {
		// Kennzeichen fuer gewaehlte Aktion senden
		sout.println("idwl");
		// Parameter fuer Aktion senden
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
			throw new ArtikelBestandIstZuKleinException(
					" - in 'inDenWarenkorbLegen()'");
		} else if (antwort.equals("ArtikelExistiertNichtException")) {
			throw new ArtikelExistiertNichtException(
					" - in 'inDenWarenkorbLegen()'");
		} else if (antwort
				.equals("ArtikelBestandIstKeineVielfacheDerPackungsgroesseException")) {
			throw new ArtikelBestandIstKeineVielfacheDerPackungsgroesseException(
					" - in 'inDenWarenkorbLegen()'");
		}
	}

	@Override
	public void ausDemWarenkorbHerausnehmen(Kunde kunde, int artikelnummer) throws ArtikelExistiertNichtException, ArtikelBestandIstKeineVielfacheDerPackungsgroesseException {
		// Kennzeichen fuer gewaehlte Aktion senden
		sout.println("adwh");
		// Parameter fuer Aktion senden
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
			throw new ArtikelExistiertNichtException(
					" - in 'ausDemWarenkorbHerausnehmen()'");
		} else if (antwort
				.equals("ArtikelBestandIstKeineVielfacheDerPackungsgroesseException")) {
			throw new ArtikelBestandIstKeineVielfacheDerPackungsgroesseException(
					" - in 'ausDemWarenkorbHerausnehmen()'");
		}
	}

	@Override
	public void stueckzahlAendern(Kunde kunde, int warenkorbArtikelnummer, int neueStueckzahl) throws ArtikelBestandIstZuKleinException, ArtikelExistiertNichtException, ArtikelBestandIstKeineVielfacheDerPackungsgroesseException {
		// Kennzeichen fuer gewaehlte Aktion senden
		sout.println("sa");
		// Parameter fuer Aktion senden
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
			throw new ArtikelBestandIstZuKleinException(
					" - in 'stueckzahlAendern()'");
		} else if (antwort.equals("ArtikelExistiertNichtException")) {
			throw new ArtikelExistiertNichtException(
					" - in 'stueckzahlAendern()'");
		} else if (antwort
				.equals("ArtikelBestandIstKeineVielfacheDerPackungsgroesseException")) {
			throw new ArtikelBestandIstKeineVielfacheDerPackungsgroesseException(
					" - in 'stueckzahlAendern()'");
		}
	}

	@Override
	public Rechnung kaufen(Kunde kunde) throws IOException, WarenkorbIstLeerException {
		List<WarenkorbArtikel> warenkorb = new Vector<WarenkorbArtikel>();

		// Kennzeichen fuer gewaehlte Aktion senden
		sout.println("k");
		// Parameter fuer Aktion senden
		sout.println(kunde.getId());

		// Antwort vom Server lesen:
		String antwort = "?";
		antwort = sin.readLine();
		if (antwort.equals("IOException")) {
			throw new IOException();
		} else if (antwort.equals("WarenkorbIstLeerException")) {
			throw new WarenkorbIstLeerException(" - in 'kaufen()'");
		} else {
			Date datum = null;
			try {
				// Datum der Rechnung einlesen
				System.out.println(antwort);
				datum = new SimpleDateFormat("EEE MMM dd HH:mm:ss zzz yyyy")
						.parse(antwort);
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
						artikel = new Massengutartikel(nummer,
								artikelbezeichnung, preis, packungsgroesse,
								bestand);

					} else {
						// Neues Artikel-Objekt erzeugen
						artikel = new Artikel(nummer, artikelbezeichnung,
								preis, bestand);
					}
					// Stueckzahl von Warenkorb Artikel i einlesen
					antwort = sin.readLine();
					int stueckzahl = Integer.parseInt(antwort);
					// In den Warenkorb hinzufuegen
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

	@Override
	public void leeren(Kunde kunde) throws ArtikelBestandIstKeineVielfacheDerPackungsgroesseException {
		// Kennzeichen fuer gewaehlte Aktion senden
		sout.println("l");
		// Parameter fuer Aktion senden
		sout.println(kunde.getId());

		// Antwort vom Server lesen:
		String antwort = "?";
		try {
			antwort = sin.readLine();
		} catch (Exception e) {
			System.err.println(e.getMessage());
		}

		if (antwort
				.equals("ArtikelBestandIstKeineVielfacheDerPackungsgroesseException")) {
			throw new ArtikelBestandIstKeineVielfacheDerPackungsgroesseException(
					" - in 'leeren()'");
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
			switch (personTyp) {
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
				p = new Kunde(id, username, password, name, strasse, plz,
						wohnort);
				// Blockiert
				antwort = sin.readLine();
				boolean kBlockiert = Boolean.valueOf(antwort);
				p.setBlockiert(kBlockiert);
				break;
			case Mitarbeiter:
				// MitarbeiterFunktion
				antwort = sin.readLine();
				MitarbeiterFunktion funktion = MitarbeiterFunktion
						.valueOf(antwort);
				// Gehalt
				antwort = sin.readLine();
				double gehalt = Double.valueOf(antwort);
				// Blockiert
				antwort = sin.readLine();
				boolean mBlockiert = Boolean.valueOf(antwort);
				p = new Mitarbeiter(id, username, password, name, funktion,
						gehalt);
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

	/**
	 * Diese Methode sendet den Befehl zum speichern der Ereignisse auf dem
	 * Server. Sie sendet Informationen gemaess des Protokolls.
	 */
	@Override
	public void schreibeEreignisse() throws IOException {
		sout.println("se");
		String antwort = "?";
		try {
			antwort = sin.readLine();
		} catch (Exception e) {
			System.err.println(e.getMessage());
		}

		if (antwort != null && antwort.equals("IOException")) {
			throw new IOException("Fehler beim schreiben der Ereignisse!");
		}
	}

	/**
	 * Diese Methode wird dazu genutzt um die Bestandshistorie des Artikels mit
	 * der angegebenen Artikelnummer zu bekommen. Sie sendet und empfaengt
	 * Informationen gemaess des Protokolls.
	 */
	@Override
	public int[] gibBestandsHistorieDaten(int artikelnummer) throws IOException {
		sout.println("gbhd");
		sout.println(artikelnummer);
		int anzahl = Integer.parseInt(sin.readLine());
		int[] daten = new int[anzahl];
		for (int i = 0; i < anzahl; i++) {
			daten[i] = Integer.parseInt(sin.readLine());
		}

		return daten;
	}

	/**
	 * Diese Methode wird dazu genutzt um die Logdatei zu bekommen. Sie sendet
	 * und empfaengt Informationen gemaess des Protokolls.
	 */
	@Override
	public String gibLogDatei() throws IOException {
		sout.println("gl");
		String antwort = "?";
		try {
			antwort = sin.readLine();
		} catch (Exception e) {
			System.err.println(e.getMessage());
			return null;
		}

		if (antwort.equals("IOException")) {
			throw new IOException("Fehler beim lesen der Logdatei!");
		} else {
			int anzahl = Integer.parseInt(antwort);
			String logDatei = "";
			for (int i = 0; i < anzahl; i++) {
				logDatei += sin.readLine() + "\n";
			}
			return logDatei;
		}
	}

	/**
	 * Diese Methode wird zum Hinzufuegen von Mitarbeitern genutzt. Sie sendet
	 * und empfaengt Informationen gemaess des Protokolls.
	 */
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
		try {
			antwort = sin.readLine();
			if (antwort.equals("MitarbeiterExistiertBereits")) {
				throw new MitarbeiterExistiertBereitsException(
						" - in ShopFassade (Einfuegen von Mitarbeiter)!");
			} else if (antwort.equals("UsernameExistiertBereits")) {
				throw new UsernameExistiertBereitsException(username,
						" - in ShopFassade (Einfugen von Mitarbeiter)!");
			}
			// OK

		} catch (Exception e) {
			System.err.println(e.getMessage());
			return;
		}
	}

	/**
	 * Diese Methode wird zum Bearbeiten von Mitarbeitern genutzt Sie sendet und
	 * empfaengt Informationen gemaess des Protokolls.
	 */
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
		try {
			antwort = sin.readLine();
			if (antwort.equals("MitarbeiterExistiertNicht")) {
				throw new MitarbeiterExistiertNichtException(id,
						" - in ShopFassade (Mitarbeiter Bearbeiten)!");
			}
			// OK

		} catch (Exception e) {
			System.err.println(e.getMessage());
			return;
		}
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
	public void fuegeKundenHinzu(String username, String passwort, String name,
			String strasse, int plz, String wohnort)
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
			// Kunde erfolgreich eingef�gt
			System.out.println("Kunde erfolgreich eingef�gt");
		} else if (ergebnis.equals("keb")) {
			System.out.println("Kunde existiert bereits");
		} else if (ergebnis.equals("ueb")) {
			System.out.println("Username existiert bereits");
		}

	}

	@Override
	public Kunde loginVergessen(String name, String strasse, int plz,
			String wohnort) {
		Kunde k = null;
		sout.println("lv");
		sout.println(name);
		sout.println(strasse);
		sout.println("" + plz);
		sout.println(wohnort);
		// hgf
		String antwort = "?";

		try {
			antwort = sin.readLine();
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		if (antwort.equals("ken")) {
			// do nothing
		} else if (antwort.equals("kse")) {
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

	@Override
	public void disconnect() throws IOException {
		sout.println("q");
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
