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
	public Mitarbeiter sucheMitarbeiter(int id)
			throws MitarbeiterExistiertNichtException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Vector<Mitarbeiter> gibAlleMitarbeiter() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void mitarbeiterLoeschen(Mitarbeiter m) {
		// TODO Auto-generated method stub
		
	}


	@Override
	public void schreibeMitarbeiter() throws IOException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void existiertUsernameSchon(String username, String zusatzMsg)
			throws UsernameExistiertBereitsException {
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
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void schreibeEreignisse() throws IOException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public String gibBestandsHistorie(Artikel artikel) throws IOException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int[] gibBestandsHistorieDaten(Artikel artikel) throws IOException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String gibLogDatei() throws IOException {
		// TODO Auto-generated method stub
		return null;
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

	@Override
	public void fuegeMitarbeiterHinzu(String username, String passwort,
			String name, MitarbeiterFunktion funktion, double gehalt)
			throws MitarbeiterExistiertBereitsException,
			UsernameExistiertBereitsException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mitarbeiterBearbeiten(int id, String passwort, String name,
			MitarbeiterFunktion funktion, double gehalt, boolean blockiert)
			throws MitarbeiterExistiertNichtException {
		// TODO Auto-generated method stub
		
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
	public Kunde loginVergessen(String name, String strasse, int zip,
			String wohnort) {
		// TODO Auto-generated method stub
		return null;
	}

}
