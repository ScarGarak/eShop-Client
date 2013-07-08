package shop.client.ui.gui.kundengui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Window;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.IOException;
import java.util.Currency;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableCellRenderer;

import shop.client.ui.gui.LogInGUI;
import shop.client.ui.gui.components.JAccountButton;
import shop.client.ui.gui.components.JImagePanel;
import shop.client.ui.gui.components.JWarenkorbButton;
import shop.client.ui.gui.kundengui.table.ArtikelTableCellRenderer;
import shop.client.ui.gui.kundengui.table.ArtikelTableModel;
import shop.client.ui.gui.kundengui.table.WarenkorbArtikelTableCellRenderer;
import shop.client.ui.gui.kundengui.table.WarenkorbArtikelTableModel;
import shop.common.exceptions.ArtikelBestandIstKeineVielfacheDerPackungsgroesseException;
import shop.common.exceptions.ArtikelBestandIstZuKleinException;
import shop.common.exceptions.ArtikelExistiertNichtException;
import shop.common.exceptions.KundeExistiertNichtException;
import shop.common.exceptions.WarenkorbIstLeerException;
import shop.common.interfaces.ShopInterface;
import shop.common.valueobjects.Artikel;
import shop.common.valueobjects.Kunde;
import shop.common.valueobjects.Massengutartikel;
import shop.common.valueobjects.Rechnung;
import shop.common.valueobjects.WarenkorbArtikel;

/**
 * Grafische Oberfläche (GUI) die dem Kunden alle benötigten
 * Interaktionsmglichkeiten zur verfügung stellt um den eShop
 * nutzen zu können.
 * 
 * @author Torres
 *
 */
@SuppressWarnings("serial")
public class KundeGUI extends JFrame {
	
	private ShopInterface shop;
	private Kunde kunde;
	private String host;
	private int port;
	
	// Header
	private JPanel headerPanel;
	private JButton accountButton;
	private JButton logoutButton;
	private JPanel logoutPanel;
	private JTextField searchField;
	private JButton searchButton;
	private JPanel warenkorbPanel;
	private JButton warenkorbButton;
	private JPanel kaufenLeerenPanel;
	private JButton kaufenButton;
	private JButton leerenButton;
	
	// Account
	private JPanel accountPanel;
	private JTextField name;
	private JTextField strasse;
	private JTextField plz;
	private JTextField wohnort;
	private JTextField altesPasswort;
	private JTextField neuesPasswort;
	private JTextField confirmPasswort;
	private JTextArea errorName;
	private JLabel errorStrasse;
	private JLabel errorPlz;
	private JLabel errorWohnort;
	private JTextArea errorPasswort;
	private JButton abbrechenButton;
	private JButton speichernButton;
	
	// Search Table & Warenkorb Table
	private JPanel tablePanel;
	private JTable searchTable;
	private ArtikelTableModel artikelTableModel;
	private JScrollPane searchScrollPane;
	private JTable warenkorbTable;
	private WarenkorbArtikelTableModel warenkorbArtikelTableModel;
	private JScrollPane warenkorbScrollPane;
	private JLabel artikelanzahl;
	private JLabel gesamtpreis;
	private JPanel tableFooterPanel;
	private JTextArea rechnung;
	private JPanel rechnungPanel;
	
	// Details
	private JPanel detailsPanel;
	private JPanel bildPanel;
	private JPanel infoPanel;
	private JLabel bezeichnung;
	private JTextArea details;
	private JPanel auswahlPanel;
	private JComboBox<Integer> menge;
	private JPanel mengePanel;
	private JComboBox<Integer> stueckzahl;
	private JPanel stueckzahlPanel;
	private JTextArea errorMessage;
	private JButton inDenWarenkorbButton;
	private JButton entfernenButton;
	
	public KundeGUI(ShopInterface shop, Kunde kunde, String host, int port) throws IOException {
		super("eShop - Kunde");
		this.shop = shop;
		this.kunde = kunde;
		this.host = host;
		this.port = port;
		
		/*
		// ServerUpdateRequestProcessor erstellen und als neuen Thread starten
		ServerUpdateKundenGUIRequestProcessor s = new ServerUpdateKundenGUIRequestProcessor(host, 6790, shop, this);
		Thread t = new Thread(s);
		t.start();
		*/
		
		initialize();
	}
	
	private void initialize() {
		setMinimumSize(new Dimension(600, 400));
		setSize(new Dimension(700, 500));
		setLayout(new BorderLayout());

		setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		addWindowListener(new WindowCloser());
		
		createHeader();
		createAccount();
		createTableSearch();
		createTableWarenkorb();
		createDetails();
		
		add(headerPanel, BorderLayout.NORTH);
		add(tablePanel, BorderLayout.CENTER);
		add(detailsPanel, BorderLayout.SOUTH);
		
		setLocationRelativeTo(null);
		setVisible(true);
	}
	
	/**
	 * Methode die alle Header Objekte implementiert, diesen Events hinzufügt und dies an den Header anfügt.
	 */
	private void createHeader() {
		accountButton = new JAccountButton(kunde.getName());
		accountButton.addActionListener(new AccountButtonListener());
		logoutButton = new JButton("Abmelden");
		logoutButton.addActionListener(new LogoutListener());
		logoutPanel = new JPanel();
		logoutPanel.setLayout(new BoxLayout(logoutPanel, BoxLayout.PAGE_AXIS));
		logoutPanel.add(accountButton);
		logoutPanel.add(logoutButton);
		JPanel searchPanel = new JPanel();
		searchPanel.setLayout(new BoxLayout(searchPanel, BoxLayout.LINE_AXIS));
		searchPanel.setBorder(BorderFactory.createEmptyBorder(25, 0, 25, 0));
		searchPanel.add(new JLabel("Suche "));
		searchField = new JTextField();
		searchField.setToolTipText("Bitte geben Sie hier einen Suchbegriff ein.");
		searchField.addActionListener(new SearchListener());
		searchPanel.add(searchField);
		searchButton = new JButton("Los");
		searchButton.addActionListener(new SearchListener());
		searchPanel.add(searchButton);
		warenkorbButton = new JWarenkorbButton(0);
		warenkorbButton.addActionListener(new WarenkorbListener());
		kaufenButton = new JButton("Kaufen");
		kaufenButton.addActionListener(new KaufenListener());
		leerenButton = new JButton("Leeren");
		leerenButton.addActionListener(new LeerenListener());
		kaufenLeerenPanel = new JPanel();
		kaufenLeerenPanel.setLayout(new BoxLayout(kaufenLeerenPanel, BoxLayout.PAGE_AXIS));
		kaufenLeerenPanel.setBorder(BorderFactory.createEmptyBorder(10, 0, 10, 0));
		kaufenLeerenPanel.add(new JLabel());
		kaufenLeerenPanel.add(kaufenButton);
		kaufenLeerenPanel.add(leerenButton);
		kaufenLeerenPanel.add(new JLabel());
		warenkorbPanel = new JPanel();
		warenkorbPanel.setLayout(new BorderLayout());
		warenkorbPanel.setBorder(BorderFactory.createEmptyBorder(10, 0, 0, 0));
		warenkorbPanel.add(warenkorbButton, BorderLayout.NORTH);
		headerPanel = new JPanel();
		headerPanel.setLayout(new BorderLayout());
		headerPanel.add(logoutPanel, BorderLayout.WEST);
		headerPanel.add(searchPanel, BorderLayout.CENTER);
		headerPanel.add(warenkorbPanel, BorderLayout.EAST);
	}
	
	/**
	 * Methode die alle Account Objekte implementiert, diesen Events hinzufügt und dies an das Account Panel anfügt.
	 */
	public void createAccount() {
		name = new JTextField();
		JTextField username = new JTextField();
		username.setText(kunde.getUsername());
		username.setEnabled(false);
		JPanel namePanel = new JPanel();
		namePanel.setLayout(new GridLayout(2,2));
		namePanel.setBorder(BorderFactory.createEmptyBorder(0, 0, 30, 0));
		namePanel.add(new JLabel("Name:"));
		namePanel.add(name);
		namePanel.add(new JLabel("Username:"));
		namePanel.add(username);
		errorName = new JTextArea();
		errorName.setBorder(BorderFactory.createEmptyBorder(5, 0, 0, 0));
		errorName.setForeground(Color.RED);
		errorName.setLineWrap(true);
		errorName.setWrapStyleWord(true);
		errorName.setEditable(false);
		errorName.setOpaque(false);
		strasse = new JTextField();
		plz = new JTextField();
		wohnort = new JTextField();
		JPanel adressPanel = new JPanel();
		adressPanel.setLayout(new GridLayout(3,2));
		adressPanel.add(new JLabel("Stra\u00dfe:"));
		adressPanel.add(strasse);
		adressPanel.add(new JLabel("PLZ:"));
		adressPanel.add(plz);
		adressPanel.add(new JLabel("Wohnort:"));
		adressPanel.add(wohnort);
		errorStrasse = new JLabel();
		errorStrasse.setForeground(Color.RED);
		errorPlz = new JLabel();
		errorPlz.setForeground(Color.RED);
		errorWohnort = new JLabel();
		errorWohnort.setForeground(Color.RED);
		JPanel errorPanel = new JPanel();
		errorPanel.setLayout(new GridLayout(3,1));
		errorPanel.setBorder(BorderFactory.createEmptyBorder(0, 5, 0, 0));
		errorPanel.add(errorStrasse);
		errorPanel.add(errorPlz);
		errorPanel.add(errorWohnort);
		altesPasswort = new JPasswordField();
		neuesPasswort = new JPasswordField();
		confirmPasswort = new JPasswordField();
		JPanel passwortPanel = new JPanel();
		passwortPanel.setLayout(new GridLayout(3,2));
		passwortPanel.setBorder(BorderFactory.createEmptyBorder(0, 5, 0, 0));
		passwortPanel.add(new JLabel("Altes Passwort:"));
		passwortPanel.add(altesPasswort);
		passwortPanel.add(new JLabel("Neues Passwort:"));
		passwortPanel.add(neuesPasswort);
		passwortPanel.add(new JLabel("Best\u00e4tige neues Passwort:"));
		passwortPanel.add(confirmPasswort);
		errorPasswort = new JTextArea();
		errorPasswort.setBorder(BorderFactory.createEmptyBorder(5, 5, 0, 0));
		errorPasswort.setForeground(Color.RED);
		errorPasswort.setLineWrap(true);
		errorPasswort.setWrapStyleWord(true);
		errorPasswort.setEditable(false);
		errorPasswort.setOpaque(false);
		abbrechenButton = new JButton("Abbrechen");
		abbrechenButton.addActionListener(new AccountListener());
		speichernButton = new JButton("Speichern");
		speichernButton.addActionListener(new AccountListener());
		JPanel buttonPanel = new JPanel();
		buttonPanel.setLayout(new BoxLayout(buttonPanel, BoxLayout.LINE_AXIS));
		buttonPanel.setBorder(BorderFactory.createEmptyBorder(60, 60, 0, 0));
		buttonPanel.add(abbrechenButton);
		buttonPanel.add(speichernButton);
		accountPanel = new JPanel();
		accountPanel.setLayout(new GridLayout(4,2));
		accountPanel.setBorder(BorderFactory.createCompoundBorder(BorderFactory.createMatteBorder(1, 0, 0, 0, Color.LIGHT_GRAY), BorderFactory.createEmptyBorder(20, 10, 10, 10)));
		accountPanel.add(namePanel);
		accountPanel.add(passwortPanel);
		accountPanel.add(errorName);
		accountPanel.add(errorPasswort);
		accountPanel.add(adressPanel);
		accountPanel.add(errorPanel);
		accountPanel.add(new JLabel());
		accountPanel.add(buttonPanel);
	}
	
	/**
	 * Methode die eine neue JTable kreiert, dieser funktionen hinzufuegt und zum TablePanel hinzufügt.
	 */
	private void createTableSearch() {
		searchTable = new JTable(new ArtikelTableModel(shop.gibAlleArtikelSortiertNachBezeichnung()));
		searchTable.getSelectionModel().setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		searchTable.getSelectionModel().addListSelectionListener(new SelectionDetailListener());
		setTableCellAlignment(new ArtikelTableCellRenderer(searchTable), searchTable, JLabel.LEFT);
		searchTable.setAutoCreateRowSorter(true);
		searchTable.setShowGrid(true);
		searchTable.setGridColor(Color.LIGHT_GRAY);
		searchTable.getTableHeader().setReorderingAllowed(false);
		searchScrollPane = new JScrollPane(searchTable);
		searchScrollPane.setBorder(BorderFactory.createEtchedBorder());
		tablePanel = new JPanel();
		tablePanel.setLayout(new BorderLayout());
		tablePanel.add(searchScrollPane, BorderLayout.CENTER);
	}
	
	/**
	 * Methode die die alle Warenkorb Objekte kreiert und der Warenkorb ansich hinzufügt.
	 */
	private void createTableWarenkorb() {
		warenkorbTable = new JTable(new WarenkorbArtikelTableModel(shop.gibWarenkorb(kunde)));
		warenkorbTable.getSelectionModel().setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		warenkorbTable.getSelectionModel().addListSelectionListener(new SelectionDetailListener());
		setTableCellAlignment(new WarenkorbArtikelTableCellRenderer(warenkorbTable), warenkorbTable, JLabel.LEFT);
		warenkorbTable.setAutoCreateRowSorter(true);
		warenkorbTable.setShowGrid(true);
		warenkorbTable.setGridColor(Color.LIGHT_GRAY);
		warenkorbTable.getTableHeader().setReorderingAllowed(false);
		warenkorbScrollPane = new JScrollPane(warenkorbTable);
		warenkorbScrollPane.setBorder(BorderFactory.createEtchedBorder());
		artikelanzahl = new JLabel("Ihr Warenkorb ist leer.");
		artikelanzahl.setBorder(BorderFactory.createEmptyBorder(4, 8, 4, 8));
		gesamtpreis = new JLabel(String.format("Gesamtpreis: %.2f ", getGesamtpreis(kunde)) + Currency.getInstance(Locale.GERMANY));
		gesamtpreis.setHorizontalAlignment(JLabel.RIGHT);
		gesamtpreis.setFont(new Font("Arial", Font.BOLD, 14));
		gesamtpreis.setBorder(BorderFactory.createEmptyBorder(4, 8, 4, 8));
		tableFooterPanel = new JPanel();
		tableFooterPanel.setLayout(new BorderLayout());
		tableFooterPanel.add(artikelanzahl, BorderLayout.WEST);
		tableFooterPanel.add(gesamtpreis, BorderLayout.EAST);
		rechnung = new JTextArea();
		rechnung.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
		rechnung.setEditable(false);
		JScrollPane rechnungPane = new JScrollPane(rechnung);
		rechnungPane.setBorder(BorderFactory.createCompoundBorder(BorderFactory.createEmptyBorder(10, 0, 0, 0), BorderFactory.createCompoundBorder(BorderFactory.createTitledBorder("Rechnung"), BorderFactory.createEmptyBorder(0, 5, 10, 5))));
		rechnungPane.setOpaque(false);
		rechnungPanel = new JPanel();
		rechnungPanel.setLayout(new BorderLayout());
		rechnungPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
		rechnungPanel.add(new JLabel("Vielen Dank f\u00fcr ihren Einkauf und besuchen Sie uns bald wieder!"), BorderLayout.NORTH);
		rechnungPanel.add(rechnungPane, BorderLayout.CENTER);
	}
	
	/**
	 * Methode die alle footer Objekte erstellt und hinzufügt.
	 */
	private void createDetails() {
		bildPanel = new JImagePanel(null);
		bezeichnung = new JLabel();
		bezeichnung.setFont(new Font("Arial", Font.BOLD, 16));
		bezeichnung.setBorder(BorderFactory.createEmptyBorder(0, 0, 8, 0));
		details = new JTextArea();
		details.setFont(new Font("Arial", Font.PLAIN, 12));
		details.setEditable(false);
		details.setOpaque(false);
		infoPanel = new JPanel();
		infoPanel.setLayout(new BorderLayout());
		infoPanel.setBorder(BorderFactory.createEmptyBorder(8, 10, 8, 10));
		infoPanel.add(bezeichnung, BorderLayout.NORTH);
		infoPanel.add(details, BorderLayout.CENTER);
		errorMessage = new JTextArea();
		errorMessage.setFont(new Font("Arial", Font.PLAIN, 12));
		errorMessage.setForeground(Color.RED);
		errorMessage.setLineWrap(true);
		errorMessage.setWrapStyleWord(true);
		errorMessage.setEditable(false);
		errorMessage.setOpaque(false);
		errorMessage.setPreferredSize(new Dimension(150, 50));
		menge = new JComboBox<Integer>();
		mengePanel = new JPanel();
		mengePanel.setLayout(new GridLayout(1,2));
		mengePanel.add(new JLabel("  Menge:"));
		mengePanel.add(menge);
		JTextArea stueckzahlLabel = new JTextArea(" St\u00fcckzahl \u00e4ndern:");
		stueckzahlLabel.setEditable(false);
		stueckzahlLabel.setLineWrap(true);
		stueckzahlLabel.setOpaque(false);
		stueckzahlLabel.setPreferredSize(new Dimension(60, 20));
		stueckzahl = new JComboBox<Integer>();
		stueckzahl.addItemListener(new StueckzahlListener());
		stueckzahlPanel = new JPanel();
		stueckzahlPanel.setLayout(new GridLayout(1,2));
		stueckzahlPanel.add(stueckzahlLabel);
		stueckzahlPanel.add(stueckzahl);
		inDenWarenkorbButton = new JButton("In den Warenkorb");
		inDenWarenkorbButton.addActionListener(new InDenWarenkorbListener());
		entfernenButton = new JButton("Artikel entfernen");
		entfernenButton.addActionListener(new EntfernenListener());
		auswahlPanel = new JPanel();
		auswahlPanel.setLayout(new BorderLayout());
		auswahlPanel.setBorder(BorderFactory.createEmptyBorder(20, 8, 20, 8));
		auswahlPanel.add(errorMessage, BorderLayout.NORTH);
		auswahlPanel.add(mengePanel, BorderLayout.CENTER);
		auswahlPanel.add(inDenWarenkorbButton, BorderLayout.SOUTH);
		detailsPanel = new JPanel();
		detailsPanel.setLayout(new BorderLayout());
	}
	
	/**
	 * Methode welche die searchTable updated.
	 * @param artikel Artikel die an die searchTable übergeben werden
	 */
	private void updateSearchTable(List<Artikel> artikel) {
		artikelTableModel = new ArtikelTableModel(artikel);
		searchTable.setModel(artikelTableModel);
		artikelTableModel.fireTableDataChanged();
	}
	
	/**
	 * Methode welche die warenkorbTable updated.
	 * @param warenkorbArtikel die an die warenkorbTable übergeben werden
	 */
	private void updateWarenkorbTable(List<WarenkorbArtikel> warenkorbArtikel) {
		warenkorbArtikelTableModel = new WarenkorbArtikelTableModel(warenkorbArtikel);
		warenkorbTable.setModel(warenkorbArtikelTableModel);
		warenkorbArtikelTableModel.fireTableDataChanged();
	}
	
	/**
	 * Methode welche die Zellen der Spalten anpasst
	 * 
	 * @param renderer
	 * @param table
	 * @param alignment
	 */
	private void setTableCellAlignment(DefaultTableCellRenderer renderer, JTable table, int alignment) {
		renderer.setHorizontalAlignment(alignment);
		for (int i = 0; i < table.getColumnCount(); i++) {
			table.setDefaultRenderer(table.getColumnClass(i), renderer);
		}
		table.updateUI();
	} 
	
	/**
	 * Methode zum berechnen des Gesamtpreises aller Warenkorb Artikel.
	 * 
	 * @param kunde Der Kunde der den Gesamtpreis haben mšchte.
	 * @return double Den Gesamtpreis aller Warenkorb Artikel.
	 */
	private double getGesamtpreis(Kunde kunde) {
		Iterator<WarenkorbArtikel> iter = shop.gibWarenkorb(kunde).iterator();
		double summe = 0.0;
		while (iter.hasNext()) {
			WarenkorbArtikel warenkorbArtikel = iter.next();
			summe += warenkorbArtikel.getStueckzahl() * warenkorbArtikel.getArtikel().getPreis();
		}
		return summe;
	}
	
	/**
	 * Methode zum updaten der ArtikelMenge in abhängigkeit davon ob es sich um einen
	 * Massengutartikel handelt oder nicht.
	 * 
	 * @param a Artikel dessen Anzahl angepasst werden soll
	 */
	private void updateArtikelMenge(Artikel a) {
		menge.removeAllItems();
		for (int i = 1; i <= a.getBestand(); i++) {
			if (a instanceof Massengutartikel) {
				if (i % ((Massengutartikel) a).getPackungsgroesse() == 0) {
					menge.addItem(i);
				}
			} else {
				menge.addItem(i);
			}
		}
	}
	
	/**
	 * Methode zum updaten der ArtikelStueckzahl im Warenkorb in abhängigkeit davon ob es sich um einen
	 * Massengutartikel handelt oder nicht.
	 * 
	 * @param wa Warenkorb in dem die veränderung vorgenommen werden solls
	 */
	private void updateWarenkorbArtikelStueckzahl(WarenkorbArtikel wa) {
		stueckzahl.removeAllItems();
		for (int i = 0, j = 1; j <= wa.getArtikel().getBestand() + wa.getStueckzahl(); j++) {
			if (wa.getArtikel() instanceof Massengutartikel) {
				if (j % ((Massengutartikel) wa.getArtikel()).getPackungsgroesse() == 0) {
					stueckzahl.insertItemAt(j, i);
					i++;
				}
			} else {
				stueckzahl.insertItemAt(j, i);
				i++;
			}
		}
		stueckzahl.getModel().setSelectedItem(wa.getStueckzahl());
	}
	
	/**
	 * Methode zur Anzeige der aktuellen Anzahl der Artikel im Warenkorb
	 */
	private void updateArtikelanzahl() {
		((JWarenkorbButton) warenkorbButton).setArtikelanzahl(shop.gibWarenkorb(kunde).size());
		if (((JWarenkorbButton) warenkorbButton).getArtikelanzahl() == 0) {
			artikelanzahl.setText("Ihr Warenkorb ist leer.");
		} else 
		if (((JWarenkorbButton) warenkorbButton).getArtikelanzahl() == 1) {
			artikelanzahl.setText("Es befindet sich " + ((JWarenkorbButton) warenkorbButton).getArtikelanzahl() + " Artikel in ihrem Warenkorb.");
		} else {
			artikelanzahl.setText("Es befinden sich " + ((JWarenkorbButton) warenkorbButton).getArtikelanzahl() + " Artikel in ihrem Warenkorb.");
		}
	}
	
	/**
	 * Methode die den Gesamtpreis der Artikel im Warenkorb aktualisiert
	 */
	private void updateGesamtpreis() {
		gesamtpreis.setText(String.format("Gesamtpreis: %.2f ", getGesamtpreis(kunde)) + Currency.getInstance(Locale.GERMANY));
	}
	
	/**
	 * Methode zum zurücksetzen von errorMessages
	 */
	private void clearErrorMessages() {
		errorMessage.setText("");
		errorName.setText("");
		name.setBackground(Color.WHITE);
		errorStrasse.setText("");
		strasse.setBackground(Color.WHITE);
		errorPlz.setText("");
		plz.setBackground(Color.WHITE);
		errorWohnort.setText("");
		wohnort.setBackground(Color.WHITE);
		errorPasswort.setText("");
		altesPasswort.setBackground(Color.WHITE);
		neuesPasswort.setBackground(Color.WHITE);
		confirmPasswort.setBackground(Color.WHITE);
	}
	
	/**
	 * Eine eigene Klasse die einen ActionListener für den searchButton & das searchField implementiert
	 * 
	 * @author Torres
	 *
	 */
	class SearchListener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent ae) {
			if (ae.getSource().equals(searchButton) || ae.getSource().equals(searchField)) {
				kaufenButton.setEnabled(true);
				leerenButton.setEnabled(true);
				warenkorbPanel.remove(kaufenLeerenPanel);
				warenkorbPanel.add(warenkorbButton, BorderLayout.NORTH);
				headerPanel.validate();
				headerPanel.repaint();
				remove(accountPanel);
				add(tablePanel, BorderLayout.CENTER);
				add(detailsPanel, BorderLayout.SOUTH);
				tablePanel.remove(warenkorbScrollPane);
				tablePanel.remove(tableFooterPanel);
				tablePanel.remove(rechnungPanel);
				warenkorbTable.clearSelection();
				tablePanel.add(searchScrollPane, BorderLayout.CENTER);
				updateSearchTable(shop.sucheArtikel(searchField.getText()));
				searchTable.clearSelection();
				tablePanel.setBorder(null);
				tablePanel.validate();
				tablePanel.repaint();
				validate();
				repaint();
			}
		}
	}
	
	
	/**
	 * Eine eigene Klasse die einen ActionListener für den warenkorbButton implementiert
	 * 
	 * @author Torres
	 *
	 */
	class WarenkorbListener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent ae) {
			if (ae.getSource().equals(warenkorbButton)) {
				kaufenButton.setEnabled(true);
				leerenButton.setEnabled(true);
				warenkorbPanel.remove(warenkorbButton);
				warenkorbPanel.add(kaufenLeerenPanel, BorderLayout.CENTER);
				headerPanel.validate();
				headerPanel.repaint();
				remove(accountPanel);
				add(tablePanel, BorderLayout.CENTER);
				add(detailsPanel, BorderLayout.SOUTH);
				tablePanel.remove(searchScrollPane);
				searchTable.clearSelection();
				tablePanel.add(warenkorbScrollPane, BorderLayout.CENTER);
				warenkorbTable.clearSelection();
				updateArtikelanzahl();
				updateWarenkorbTable(shop.gibWarenkorb(kunde));
				updateGesamtpreis();
				tablePanel.add(tableFooterPanel, BorderLayout.SOUTH);
				tablePanel.setBorder(BorderFactory.createMatteBorder(0, 0, 1, 0, Color.LIGHT_GRAY));
				tablePanel.validate();
				tablePanel.repaint();
			}
		}
	}
	
	/**
	 * Eine eigene Klasse die einen ActionListener für den kaufenButton implementiert
	 * 
	 * @author Torres
	 *
	 */
	class KaufenListener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent ae) {
			if (ae.getSource().equals(kaufenButton)) {
				try {
					Rechnung r = shop.kaufen(kunde);
					rechnung.setText(r.toString());
					updateArtikelanzahl();
					updateWarenkorbTable(shop.gibWarenkorb(kunde));
					warenkorbPanel.remove(kaufenLeerenPanel);
					warenkorbTable.clearSelection();
					tablePanel.remove(warenkorbScrollPane);
					tablePanel.remove(tableFooterPanel);
					tablePanel.add(rechnungPanel, BorderLayout.CENTER);
					tablePanel.setBorder(null);
					tablePanel.validate();
					tablePanel.repaint();
				} catch (IOException e) {
					JOptionPane.showConfirmDialog(null,
							e.getMessage(), "Kaufen",
			                JOptionPane.PLAIN_MESSAGE);
				} catch (WarenkorbIstLeerException e) {
					JOptionPane.showConfirmDialog(null,
							"Ihr Warenkorb ist leer.\n" +
							"Bitte f\u00fcgen Sie zuerst einige Artikel in ihren Warenkorb.", "Kaufen",
			                JOptionPane.PLAIN_MESSAGE);
				}
			}
		}
	}
	
	/**
	 * Eine eigene Klasse die einen ActionListener für den leerenButton implementiert
	 * 
	 * @author Torres
	 *
	 */
	class LeerenListener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent ae) {
			if (ae.getSource().equals(leerenButton)) {
				if (shop.gibWarenkorb(kunde).isEmpty()) {
					JOptionPane.showConfirmDialog(null,
						"Ihr Warenkorb ist leer.\n" +
						"Bitte f\u00fcgen Sie zuerst einige Artikel in ihren Warenkorb.", "Warenkorb leeren",
		                JOptionPane.PLAIN_MESSAGE);
				} else
				if (JOptionPane.showConfirmDialog(null,
						"Sind Sie sich sicher dass Sie den Warenkorb leeren wollen?\n" +
						"Sie werden alle Artikel die sich in ihrem Warenkorb befinden verlieren.", "Warenkorb leeren",
		                JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE) == JOptionPane.YES_OPTION) {		
					try {
						shop.leeren(kunde);
						updateArtikelanzahl();
						updateWarenkorbTable(shop.gibWarenkorb(kunde));
						updateGesamtpreis();
						warenkorbTable.clearSelection();
						tablePanel.validate();
						tablePanel.repaint();
					} catch (ArtikelBestandIstKeineVielfacheDerPackungsgroesseException e) {
						JOptionPane.showConfirmDialog(null,
								"Der Bestand eines Artikels ist keine Vielfache der Packungsgr\u00f6\u00dfe.", "Warenkorb leeren",
				                JOptionPane.PLAIN_MESSAGE);
					}
				}
			}
		}
	}
	
	/**
	 * Eine eigene Klasse die einen ListSelectionListener für die searchTable implementiert
	 * 
	 * @author Torres
	 *
	 */
	class SelectionDetailListener implements ListSelectionListener {
		@Override
		public void valueChanged(ListSelectionEvent lse) {
			int index = -1;
			if (lse.getSource().equals(searchTable.getSelectionModel()) && searchTable.getSelectedRow() != -1) {
				index = searchTable.convertRowIndexToModel(searchTable.getSelectedRow());
				ArtikelTableModel atm = (ArtikelTableModel) searchTable.getModel();
				Artikel a = atm.getRowValue(index);
				((JImagePanel) bildPanel).setImagePath("images/" + a.getArtikelnummer() + ".jpg");
				bezeichnung.setText(a.getBezeichnung());
				details.setText("");
				details.append("Preis: " + String.format("%.2f ", a.getPreis()) + Currency.getInstance(Locale.GERMANY) + "\n");
				details.append("Bestand: " + a.getBestand() + "\n");
				auswahlPanel.remove(stueckzahlPanel);
				auswahlPanel.add(mengePanel);
				updateArtikelMenge(a);
				auswahlPanel.remove(entfernenButton);
				auswahlPanel.add(inDenWarenkorbButton, BorderLayout.SOUTH);
			} else 
			if (lse.getSource().equals(warenkorbTable.getSelectionModel()) && warenkorbTable.getSelectedRow() != -1) {
				index = warenkorbTable.convertRowIndexToModel(warenkorbTable.getSelectedRow());	
				WarenkorbArtikelTableModel watm = (WarenkorbArtikelTableModel) warenkorbTable.getModel();
				WarenkorbArtikel wa = watm.getRowValue(index);
				((JImagePanel) bildPanel).setImagePath("images/" + wa.getArtikel().getArtikelnummer() + ".jpg");
				bezeichnung.setText(wa.getArtikel().getBezeichnung());
				details.setText("");
				details.append("St\u00fcckzahl: " + wa.getStueckzahl() + "\n");
				details.append("Preis: " + String.format("%.2f ", wa.getArtikel().getPreis()) + Currency.getInstance(Locale.GERMANY) + "\n");
				auswahlPanel.remove(mengePanel);
				auswahlPanel.add(stueckzahlPanel);
				updateWarenkorbArtikelStueckzahl(wa);
				auswahlPanel.remove(inDenWarenkorbButton);
				auswahlPanel.add(entfernenButton, BorderLayout.SOUTH);
			}
			if (index != -1) {
				detailsPanel.add(bildPanel, BorderLayout.WEST);
				detailsPanel.add(infoPanel, BorderLayout.CENTER);
				detailsPanel.add(auswahlPanel, BorderLayout.EAST);
				detailsPanel.validate();
				detailsPanel.repaint();
			} else {
				detailsPanel.remove(bildPanel);
				detailsPanel.remove(infoPanel);
				detailsPanel.remove(auswahlPanel);
				detailsPanel.validate();
				detailsPanel.repaint();
			}
			clearErrorMessages();
		}
	}
	
	/**
	 * Eine eigene Klasse die einen ActionListener für den inDenWarenkorbButton implementiert
	 * 
	 * @author Torres
	 *
	 */
	class InDenWarenkorbListener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent ae) {
			if (ae.getSource().equals(inDenWarenkorbButton)) {
				ArtikelTableModel atm = (ArtikelTableModel) searchTable.getModel();
				Artikel a = atm.getRowValue(searchTable.convertRowIndexToModel(searchTable.getSelectedRow()));
				try {
					shop.inDenWarenkorbLegen(kunde, a.getArtikelnummer(), (Integer) menge.getItemAt(menge.getSelectedIndex()));
					updateArtikelanzahl();
					updateSearchTable(shop.gibAlleArtikelSortiertNachBezeichnung());
					remove(detailsPanel);
					tablePanel.validate();
					tablePanel.repaint();
					updateArtikelMenge(a);
					//revalidate();
					repaint();
				} catch (NullPointerException e) {
					errorMessage.setText("Bitte w\u00e4hlen Sie unten eine g\u00fcltige Menge aus.");
				} catch (ArtikelBestandIstZuKleinException e) {
					errorMessage.setText("Der Bestand dieses Artikels ist zu klein oder leer.");
				} catch (ArtikelExistiertNichtException e) {
					errorMessage.setText("Dieser Artikel existiert nicht.");
				} catch (ArtikelBestandIstKeineVielfacheDerPackungsgroesseException e) {
					errorMessage.setText("Die gew\u00e4hlte Menge ist keine Vielfache der Packungsgr\u00f6\u00dfe dieses Artikels.");
				}
			}
		}
	}
	
	/**
	 * Eine eigene Klasse die einen ActionListener für den entfernenButton implementiert
	 * 
	 * @author Torres
	 *
	 */
	class EntfernenListener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent ae) {
			if (ae.getSource().equals(entfernenButton)) {
				WarenkorbArtikelTableModel watm = (WarenkorbArtikelTableModel) warenkorbTable.getModel();
				WarenkorbArtikel wa = watm.getRowValue(warenkorbTable.convertRowIndexToModel(warenkorbTable.getSelectedRow()));
				try {
					shop.ausDemWarenkorbHerausnehmen(kunde, wa.getArtikel().getArtikelnummer());
					updateArtikelanzahl();
					updateWarenkorbTable(shop.gibWarenkorb(kunde));
					updateGesamtpreis();
					warenkorbTable.clearSelection();
					tablePanel.validate();
					tablePanel.repaint();
				} catch (ArtikelExistiertNichtException e) {
					errorMessage.setText("Dieser Artikel existiert nicht.");
				} catch (ArtikelBestandIstKeineVielfacheDerPackungsgroesseException e) {
					errorMessage.setText("Die gew\u00e4hlte St\u00fcckzahl ist keine Vielfache der Packungsgr\u00f6\u00dfe dieses Artikels.");
				}
			}
		}
	}
	
	/**
	 * Eine eigene Klasse die einen ItemListener für änderungen der Stückzahl implementiert
	 * 
	 * @author Torres
	 *
	 */
	class StueckzahlListener implements ItemListener {
		@Override
	    public void itemStateChanged(ItemEvent event) {
	       if (event.getStateChange() == ItemEvent.SELECTED) {
				WarenkorbArtikelTableModel watm = (WarenkorbArtikelTableModel) warenkorbTable.getModel();
				WarenkorbArtikel wa = watm.getRowValue(warenkorbTable.convertRowIndexToModel(warenkorbTable.getSelectedRow()));
				try {
					if (stueckzahl.getSelectedIndex() != -1) {
						shop.stueckzahlAendern(kunde, wa.getArtikel().getArtikelnummer(), (Integer) stueckzahl.getItemAt(stueckzahl.getSelectedIndex()));
						updateGesamtpreis();
						tablePanel.validate();
						tablePanel.repaint();
						details.setText("");
						details.append("St\u00fcckzahl: " + wa.getStueckzahl() + "\n");
						details.append("Preis: " + String.format("%.2f ", wa.getArtikel().getPreis()) + Currency.getInstance(Locale.GERMANY) + "\n");
						detailsPanel.validate();
						detailsPanel.repaint();
					}
				} catch (ArtikelBestandIstZuKleinException e) {
					errorMessage.setText("Der Bestand dieses Artikels ist zu klein oder leer.");
				} catch (ArtikelExistiertNichtException e) {
					errorMessage.setText("Dieser Artikel existiert nicht.");
				} catch (ArtikelBestandIstKeineVielfacheDerPackungsgroesseException e) {
					errorMessage.setText("Die gew\u00e4hlte St\u00fcckzahl ist keine Vielfache der Packungsgr\u00f6\u00dfe dieses Artikels.");
				}
			}
		}
	}
	
	/**
	 * Eine eigene Klasse die einen ActionListener für den accountButton implementiert
	 * 
	 * @author Torres
	 *
	 */
	class AccountButtonListener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent ae) {
			if (ae.getSource().equals(accountButton)) {
				clearErrorMessages();
				name.setText(kunde.getName());
				strasse.setText(kunde.getStrasse());
				plz.setText(String.valueOf(kunde.getPlz()));
				wohnort.setText(kunde.getWohnort());
				altesPasswort.setText("");
				neuesPasswort.setText("");
				confirmPasswort.setText("");
				kaufenButton.setEnabled(false);
				leerenButton.setEnabled(false);
				remove(tablePanel);
				remove(detailsPanel);
				add(accountPanel, BorderLayout.CENTER);
				accountPanel.revalidate();
				accountPanel.repaint();
			}
		}
	}
	
	/**
	 * Eine eigene Klasse die einen ActionListener für den logoutButton implementiert
	 * 
	 * @author Torres
	 *
	 */
	class LogoutListener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent ae) {
			if (ae.getSource().equals(logoutButton)) {
				try {
					shop.disconnect();
					dispose();
					new LogInGUI(host, port);
				} catch (IOException e) {
					JOptionPane.showConfirmDialog(null, "IOException: " + e.getMessage(), "eShop", JOptionPane.PLAIN_MESSAGE);
				}
			}
		}
	}
	
	/**
	 * Eine eigene Klasse die einen ActionListener für den abbrechenButton implementiert
	 * 
	 * @author Torres
	 *
	 */
	class AccountListener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent ae) {
			if (ae.getSource().equals(abbrechenButton)) {
				kaufenButton.setEnabled(true);
				leerenButton.setEnabled(true);
				remove(accountPanel);
				add(tablePanel, BorderLayout.CENTER);
				add(detailsPanel, BorderLayout.SOUTH);
				tablePanel.revalidate();
				tablePanel.repaint();
				detailsPanel.revalidate();
				detailsPanel.repaint();
				clearErrorMessages();
			}
			if (ae.getSource().equals(speichernButton)) {
				clearErrorMessages();
				boolean ok = true;
				if (name.getText().isEmpty()) {
					errorName.setText("Der Name darf nicht leer sein. Bitte geben Sie einen g\u00fcltigen Namen ein.");
					name.setBackground(new Color(250,240,230));
					ok = false;
				}
				if (strasse.getText().isEmpty()) {
					errorStrasse.setText("Die Stra\u00dfe darf nicht leer sein.");
					strasse.setBackground(new Color(250,240,230));
					ok = false;
				}
				if (plz.getText().isEmpty()) {
					errorPlz.setText("Die Postleitzahl darf nicht leer sein.");
					plz.setBackground(new Color(250,240,230));
					ok = false;
				}
				if (!plz.getText().isEmpty()) {
					try {  
						Integer.parseInt(plz.getText());
					} catch(NumberFormatException nfe) {  
						errorPlz.setText("Bitte geben Sie eine g\u00fcltige Postleitzahl ein.");  
						plz.setBackground(new Color(250,240,230));
						ok = false;
					}
				} 
				if (wohnort.getText().isEmpty()) {
					errorWohnort.setText("Der Wohnort darf nicht leer sein.");
					wohnort.setBackground(new Color(250,240,230));
					ok = false;
				} 
				if (!(altesPasswort.getText().isEmpty() && neuesPasswort.getText().isEmpty() && confirmPasswort.getText().isEmpty())) {
					if (!altesPasswort.getText().equals(kunde.getPasswort())) {
						errorPasswort.setText("Das alte Passwort ist falsch.");
						altesPasswort.setBackground(new Color(250, 240, 230));
						ok = false;
					} else
					if (neuesPasswort.getText().equals(altesPasswort.getText())) {
						errorPasswort.setText("Das neue Passwort darf nicht gleich das alte Passwort sein.");
						neuesPasswort.setBackground(new Color(250, 240, 230));
						ok = false;
					} else
					if (!confirmPasswort.getText().equals(neuesPasswort.getText())) {
						errorPasswort.setText("Bitte best\u00e4tigen Sie das neue Passwort.");
						confirmPasswort.setBackground(new Color(250, 240, 230));
						ok = false;
					} else
					if (altesPasswort.getText().isEmpty() || neuesPasswort.getText().isEmpty() || confirmPasswort.getText().isEmpty()) {
						errorPasswort.setText("Bitte geben Sie das alte Passwort, das neue Passwort und dessen Best\u00e4tigung ein.");
						if (altesPasswort.getText().isEmpty()) altesPasswort.setBackground(new Color(250, 240, 230));
						if (neuesPasswort.getText().isEmpty()) neuesPasswort.setBackground(new Color(250, 240, 230));
						if (confirmPasswort.getText().isEmpty()) confirmPasswort.setBackground(new Color(250, 240, 230));
						ok = false;
					}
				}
				if (ok) {
					if (!kunde.getName().equals(name.getText())) {
						kunde.setName(name.getText());
						accountButton.setText(kunde.getName());
					}
					if (!kunde.getStrasse().equals(strasse.getText())) {
						kunde.setStrasse(strasse.getText());
					}
					if (kunde.getPlz() != Integer.parseInt(plz.getText())) {
						kunde.setPlz(Integer.parseInt(plz.getText()));
					}
					if (!kunde.getWohnort().equals(wohnort.getText())) {
						kunde.setWohnort(wohnort.getText());
					}
					if (!neuesPasswort.getText().isEmpty()) {
						kunde.setPasswort(neuesPasswort.getText());
					}
					try {
						shop.kundenBearbeiten(kunde.getId(), kunde.getPasswort(), kunde.getName(), kunde.getStrasse(), kunde.getPlz(), kunde.getWohnort(), kunde.getBlockiert());
					} catch (KundeExistiertNichtException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					kaufenButton.setEnabled(true);
					leerenButton.setEnabled(true);
					remove(accountPanel);
					add(tablePanel, BorderLayout.CENTER);
					add(detailsPanel, BorderLayout.SOUTH);
					tablePanel.revalidate();
					tablePanel.repaint();
					detailsPanel.revalidate();
					detailsPanel.repaint();
				}
			}
		}
	}
	
	/**
	 * Überschreiben der Methode WindowClosing um bei schließen der LoginGUI die Verbindung zum server trennt.
	 * 
	 * @author Torres
	 *
	 */
	class WindowCloser extends WindowAdapter {
		@Override
		public void windowClosing(WindowEvent we) {
			Window w = we.getWindow();
			if (((JWarenkorbButton) warenkorbButton).getArtikelanzahl() == 0) {
				try {
					shop.disconnect();
					w.setVisible(false);
					w.dispose();
					System.exit(0);
				} catch (IOException e) {
					JOptionPane.showConfirmDialog(null, "IOException: " + e.getMessage(), "eShop", JOptionPane.PLAIN_MESSAGE);
				}
			} else {
				if (JOptionPane.showConfirmDialog(null,
					"Sind Sie sich sicher dass Sie die Anwendung beenden wollen?\n" +
					"Sie werden alle Artikel die sich in ihrem Warenkorb befinden verlieren.", "Anwendung beenden",
	                JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE) == JOptionPane.YES_OPTION) {		
					try {
						shop.leeren(kunde);
					} catch (ArtikelBestandIstKeineVielfacheDerPackungsgroesseException e) {
						JOptionPane.showConfirmDialog(null,
								"Der Bestand eines Artikels ist keine Vielfache der Packungsgr\u00f6\u00dfe.", "Anwendung beenden",
				                JOptionPane.PLAIN_MESSAGE);
					}
					try {
						shop.disconnect();
						w.setVisible(false);
						w.dispose();
						System.exit(0);
					} catch (IOException e) {
						JOptionPane.showConfirmDialog(null, "IOException: " + e.getMessage(), "eShop", JOptionPane.PLAIN_MESSAGE);
					}
				} else {
					w.setVisible(true);
				}
			}
		}
	}
	
}
