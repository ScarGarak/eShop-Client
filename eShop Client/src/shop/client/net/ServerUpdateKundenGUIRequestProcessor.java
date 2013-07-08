package shop.client.net;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.Socket;

import shop.client.ui.gui.kundengui.KundeGUI;
import shop.common.interfaces.ShopInterface;

public class ServerUpdateKundenGUIRequestProcessor implements KundenGUIObserver, Runnable {

	// Datenstrukturen fŸr die Kommunikation
	private Socket socket;
	private BufferedReader sin; // server-input stream

	// ShopVerwaltung
//	private ShopInterface shop;
	// KundenGUI
//	private KundeGUI gui;	
	
	public ServerUpdateKundenGUIRequestProcessor(String host, int port, ShopInterface shop, KundeGUI gui) throws IOException {
		
//		this.shop = shop;
//		this.gui = gui;
		
		try {
			// Socket-Objekt fuer die Kommunikation mit Host/Port erstellen
			socket = new Socket(host, port);
			// Stream-Objekt fuer Text-Inputstream ueber Socket erzeugen
			InputStream is = socket.getInputStream();
			sin = new BufferedReader(new InputStreamReader(is));
		} catch (IOException e) {
			System.err.println("Fehler beim Socket-Stream oeffnen: " + e);
			// Wenn im "try"-Block Fehler auftreten, dann Socket schliessen:
			if (socket != null)
				socket.close();
			System.err.println("Socket geschlossen");
			System.exit(0);
		}
	}
	
	/**
	 * Methode zur Abwicklung der Kommunikation mit dem Server gemŠ§ dem
	 * vorgebenen Kommunikationsprotokoll.
	 */
	public void run() {
		String input = "";

		// Hauptschleife zur wiederholten Abwicklung der Kommunikation
		do {
			// Beginn der Benutzerinteraktion:
			// Aktion vom Server einlesen 
			try {
				input = sin.readLine();
			} catch (Exception e) {
				System.out.println("--->Fehler beim Lesen vom Server (Aktion): ");
				System.out.println(e.getMessage());
				continue;
			}

			// Eingabe bearbeiten:
			if (input == null) {
				// input wird von readLine() auf null gesetzt, wenn Server Verbindung abbricht
				// Einfach behandeln wie ein "quit"
				input = "q";
			}
			else if (input.equals("uat")) {
				System.out.println("Update Artikel Table");
				updateArtikelTable();
			}
			// ---
			// weitere Server-Dienste ...
			// ---
		} while (!(input.equals("q")));

		// Verbindung wurde vom Server abgebrochen:
		disconnect();		
	}

	public void updateArtikelTable() {
//		gui.updateSearchTable(shop.gibAlleArtikelSortiertNachBezeichnung());
	}

	public void updateWarenkorbArtikelTable() {
//		gui.updateWarenkorbTable(shop.gibWarenkorb(gui.getKunde()));
	}

	private void disconnect() {
		try {
			socket.close();
			System.out.println("Verbindung zu " + socket.getInetAddress()
					+ ":" + socket.getPort() + " durch Server abgebrochen");
		} catch (Exception e) {
			System.out.println("--->Fehler beim Beenden der Verbindung: ");
			System.out.println(e.getMessage());
		}
	}
	
}
