package shop.client.ui.gui.mitarbeitergui;


import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.Window;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.IOException;
import java.text.DecimalFormat;
import java.util.Currency;
import java.util.List;
import java.util.Locale;
import java.util.regex.PatternSyntaxException;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.RowFilter;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.TableRowSorter;

import shop.client.ui.gui.LogInGUI;
import shop.client.ui.gui.components.BestandshistorieGraphik;
import shop.client.ui.gui.components.JAccountButton;
import shop.client.ui.gui.mitarbeitergui.table.ArtikelTableCellRenderer;
import shop.client.ui.gui.mitarbeitergui.table.ArtikelTableModel;
import shop.client.ui.gui.mitarbeitergui.table.KundenTableCellRenderer;
import shop.client.ui.gui.mitarbeitergui.table.KundenTableModel;
import shop.client.ui.gui.mitarbeitergui.table.LogTableModel;
import shop.client.ui.gui.mitarbeitergui.table.MitarbeiterTableCellRenderer;
import shop.client.ui.gui.mitarbeitergui.table.MitarbeiterTableModel;
import shop.common.exceptions.ArtikelBestandIstKeineVielfacheDerPackungsgroesseException;
import shop.common.exceptions.ArtikelExistiertBereitsException;
import shop.common.exceptions.ArtikelExistiertNichtException;
import shop.common.exceptions.KundeExistiertNichtException;
import shop.common.exceptions.MitarbeiterExistiertBereitsException;
import shop.common.exceptions.MitarbeiterExistiertNichtException;
import shop.common.exceptions.UsernameExistiertBereitsException;
import shop.common.interfaces.ShopInterface;
import shop.common.valueobjects.Artikel;
import shop.common.valueobjects.Kunde;
import shop.common.valueobjects.Massengutartikel;
import shop.common.valueobjects.Mitarbeiter;
import shop.common.valueobjects.MitarbeiterFunktion;

@SuppressWarnings("serial")
public class MitarbeiterGUI extends JFrame {
	
	public static double MINDESTLOHN = 1800;
	
	public String standardPasswort = "123";
	
	private Mitarbeiter mitarbeiter;
	private ShopInterface shop;
//	host/port info
	private String host;
	private int port;
	
	private JTabbedPane tabbedPane;
	
	//////////// Artikel Panel ////////////
	private JPanel artikelPanel;
	private ArtikelTableModel artikelTableModel;
	private JScrollPane artikelTableScrollPane;
	private ArtikelTableCellRenderer artikelTableCellRenderer;
	
		//Artikel Center Panel
	private JPanel artikelCenterPanel;
	private JTable artikelTable;
	private JPanel artikelButtonsPanel;
	private JButton artikelHinzufuegen;
	private JButton artikelEntfernen;
	private JButton artikelEinlagern;
	private JButton artikelBearbeiten;
	private JButton artikelAuslagern;
	private JButton artikelBestandshistorie;
	
		//Artikel Footer
	private JPanel artikelFooterWrapper;
	private JLabel errorMsg;
	private JPanel artikelFooterPanel;
	
		//Artikel Eingabe Feld Komponenten
	private JTextField artikelNummerInput;
	private JTextField artikelBezeichnungInput;
	private JTextField artikelPreisInput;
	private JTextField artikelPackungsGroesseInput;
	private JTextField artikelBestandInput;
	
		//Artikel verschiedene Eingabefelder
	private JPanel artikelHinzufuegenEingabeFeld;
	private JPanel artikelBearbeitenEingabeFeld;
	private JPanel artikelEinlagernEingabeFeld;
	private JPanel artikelAuslagernEingabeFeld;
	
		//Verschieden Buttons Panel
	private JPanel artikelHinzufuegenButtonsPanel;
	private JPanel artikelBearbeitenButtonsPanel;
	private JPanel artikelEinlagernButtonsPanel;
	private JPanel artikelAuslagernButtonsPanel;
	
	
	//////////// Mitarbeiter Panel ////////////
	private JPanel mitarbeiterPanel;
	private JTable mitarbeiterTable;
	private JScrollPane mitarbeiterTableScrollPane;
	private MitarbeiterTableModel mitarbeiterTableModel;
	private JPanel mitarbeiterButtonsPanel;
	private JButton mitarbeiterHinzufuegen;
	private JButton mitarbeiterBearbeiten;
	private JButton mitarbeiterEntfernen;
	private JButton mitarbeiterBlockieren;
	private TableRowSorter<MitarbeiterTableModel> mitarbeiterSorter;
	
		//Artikel Eingabe Feld Komponenten
	private JTextField mitarbeiterUsernameInput;
	private JTextField mitarbeiterNameInput;
	private JTextField mitarbeiterGehaltInput;
	private JComboBox mitarbeiterFunktionInput;
	
		//Mitarbeiter Footer
	private JPanel mitarbeiterFooterWrapper;
	private JPanel mitarbeiterFooterPanel;
	
		//Mitarbeiter verschiedene Eingabefelder
	private JPanel mitarbeiterHinzufuegenEingabeFeld;
	private JPanel mitarbeiterBearbeitenEingabeFeld;
	
		//Verschieden Buttons Panel
	private JPanel mitarbeiterHinzufuegenButtonsPanel;
	private JPanel mitarbeiterBearbeitenButtonsPanel;
	
	//////////// Kunden Panel ////////////
	private JPanel kundenPanel;
	private JTable kundenTable;
	private JScrollPane kundenTableScrollPane;
	private KundenTableModel kundenTableModel;
	private JPanel kundenButtonsPanel;
	private JButton kundenEntfernen;
	private JButton kundenBlockieren;
	private TableRowSorter<KundenTableModel> kundenSorter;
	
		//Mitarbeiter Footer
	private JPanel kundenFooterWrapper;
	
	//////////// Log Panel ////////////
	private JPanel logPanel;
	private JScrollPane logScrollPane;
	private JTable logTable;
	private TableRowSorter<LogTableModel> logSorter;
	
	//////////// Header ////////////
	private JPanel headerPanel;
	private JPanel accountButtonPanel;
	private JButton accountButton;
	private JTextField searchField;
	private JButton searchButton;
	private JButton logoutButton;
	
	////////////Account Panel ////////////
	private JPanel accountPanel;
	private JTextField usernameFeld;
	private JTextField altesPasswort;
	private JTextField neuesPasswort;
	private JTextField confirmPasswort;
	private JButton accountAbbrechenButton;
	private JButton accountSpeichernButton;
	private JTextArea errorName;
	private JTextArea errorPasswort;
	
	
	public MitarbeiterGUI(Mitarbeiter mitarbeiter, ShopInterface shop, String host, int port) throws IOException{
		super("eShop - Mitarbeiter");
		this.shop = shop;
		this.mitarbeiter = mitarbeiter;
		this.host = host;
		this.port = port;
		
		initialize();
	}
	
	private void initialize() throws IOException{
		setMinimumSize(new Dimension(700, 500));
		setSize(new Dimension(700, 500));
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setLayout(new BorderLayout());
		addWindowListener(new WindowCloser());
		
		createHeader();
		createTabbedPane();
		createAccountPanel();
		
		add(headerPanel, BorderLayout.NORTH);
		add(tabbedPane, BorderLayout.CENTER);
		setLocationRelativeTo(null);
		setVisible(true);
	}
	
	private void createHeader(){
		accountButton = new JAccountButton(mitarbeiter.getName());
		accountButton.addActionListener(new AccountListener());
		logoutButton = new JButton("Abmelden");
		logoutButton.addActionListener(new LogoutListener());
		accountButtonPanel = new JPanel();
		accountButtonPanel.setLayout(new BoxLayout(accountButtonPanel, BoxLayout.PAGE_AXIS));
		accountButtonPanel.add(accountButton);
		accountButtonPanel.add(logoutButton);
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
		headerPanel = new JPanel();
		headerPanel.setLayout(new BorderLayout());
		headerPanel.add(accountButtonPanel, BorderLayout.WEST);
		headerPanel.add(searchPanel, BorderLayout.CENTER);
	}
	
	private void createTabbedPane() throws IOException{
		tabbedPane = new JTabbedPane();
		
		createArtikelPanel();
		tabbedPane.addTab("Artikel", artikelPanel);

		createMitarbeiterPanel();
		tabbedPane.addTab("Mitarbeiter", mitarbeiterPanel);

		createKundenPanel();
		tabbedPane.addTab("Kunden", kundenPanel);

		createLogPanel();
		if(mitarbeiter.getFunktion().equals(MitarbeiterFunktion.Admin))
			tabbedPane.addTab("Log", logPanel);
		
		tabbedPane.addChangeListener(new TabListener());
	}
	
	
	
	//////////////////////  Artikel Panels  //////////////////////
	
	private void createArtikelPanel(){
		artikelPanel = new JPanel(new BorderLayout());
		
		ArtikelPanelListener listener = new ArtikelPanelListener();
		
		artikelCenterPanel = new JPanel();
		artikelCenterPanel.setLayout(new BoxLayout(artikelCenterPanel, BoxLayout.X_AXIS));
		
		/////////// Artikel Tabelle ///////////
		artikelTableModel = new ArtikelTableModel(shop.gibAlleArtikelSortiertNachBezeichnung());
		
		artikelTable = new JTable(artikelTableModel);
		artikelTable.setShowGrid(true);
		artikelTable.setGridColor(Color.LIGHT_GRAY);
		
		artikelTable.setDefaultRenderer(Object.class, artikelTableCellRenderer);
		artikelTable.getSelectionModel().setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		artikelTable.getTableHeader().setReorderingAllowed(false);
		artikelTable.setAutoCreateRowSorter(true);
		artikelTable.getSelectionModel().addListSelectionListener(new ArtikelSelectionListener());
		artikelTableCellRenderer = new ArtikelTableCellRenderer(artikelTable);
		setTableCellAlignment(artikelTableCellRenderer, artikelTable, JLabel.LEFT);
		
		artikelTableScrollPane = new JScrollPane(artikelTable);
		artikelTableScrollPane.setBorder(BorderFactory.createEtchedBorder());
		artikelTableScrollPane.setAlignmentY(TOP_ALIGNMENT);
		
		
		/////////// Artikel Buttons ///////////
		artikelButtonsPanel = new JPanel(new GridLayout(0,1));
		
		artikelHinzufuegen = new JButton("Hinzuf\u00fcgen");
		artikelHinzufuegen.addActionListener(listener);
		
		artikelBearbeiten = new JButton("Bearbeiten");
		artikelBearbeiten.addActionListener(listener);
		artikelBearbeiten.setEnabled(false);

		artikelEinlagern =  new JButton("Einlagern");
		artikelEinlagern.addActionListener(listener);
		artikelEinlagern.setEnabled(false);

		artikelAuslagern = new JButton("Auslagern");
		artikelAuslagern.addActionListener(listener);
		artikelAuslagern.setEnabled(false);

		artikelBestandshistorie = new JButton("Bestandshistorie");
		artikelBestandshistorie.addActionListener(listener);
		artikelBestandshistorie.setEnabled(false);
		
		artikelEntfernen =   new JButton("Entfernen");
		artikelEntfernen.addActionListener(listener);
		artikelEntfernen.setEnabled(false);
		
		artikelButtonsPanel.setMaximumSize(new Dimension(100, 6*25));
		artikelButtonsPanel.add(artikelHinzufuegen);
		artikelButtonsPanel.add(artikelBearbeiten);
		artikelButtonsPanel.add(artikelEinlagern);
		artikelButtonsPanel.add(artikelAuslagern);
		artikelButtonsPanel.add(artikelBestandshistorie);
		artikelButtonsPanel.add(artikelEntfernen);
		artikelButtonsPanel.setAlignmentY(TOP_ALIGNMENT);
		
		artikelCenterPanel.add(artikelTableScrollPane);
		artikelCenterPanel.add(artikelButtonsPanel);
		
		createArtikelFooterWrapper();
		
		artikelPanel.add(artikelCenterPanel, BorderLayout.CENTER);
		artikelPanel.add(artikelFooterWrapper, BorderLayout.SOUTH);
	}
	
	private void createArtikelFooterWrapper(){
		artikelFooterWrapper = new JPanel();
		artikelFooterWrapper.setLayout(new BoxLayout(artikelFooterWrapper, BoxLayout.Y_AXIS));
		
		errorMsg = new JLabel("");
		errorMsg.setForeground(Color.RED);
		errorMsg.setAlignmentX(LEFT_ALIGNMENT);
		
		createArtikelFooterPanel();

//		artikelFooterWrapper.setBorder(BorderFactory.createMatteBorder(1, 0, 0, 0, Color.LIGHT_GRAY));
		artikelFooterWrapper.setVisible(false);
	}
	
	private void createArtikelFooterPanel(){
		artikelFooterPanel = new JPanel();
		artikelFooterPanel.setLayout(new BoxLayout(artikelFooterPanel, BoxLayout.X_AXIS));
		artikelFooterPanel.setAlignmentX(LEFT_ALIGNMENT);
		
		createArtikelEingabeFeldKomponenten();
		rebuildArtikelHinzufuegenEingabeFeld();
		
		createArtikelHinzufuegenButtonsPanel();
		createArtikelBearbeitenButtonsPanel();
		createArtikelEinlagernButtonsPanel();
		createArtikelAuslagernButtonsPanel();
	}

	private void createArtikelEingabeFeldKomponenten(){
		artikelNummerInput = new JTextField(10);
		artikelBezeichnungInput = new JTextField(30);
		artikelPreisInput = new JTextField(10);
		artikelPackungsGroesseInput = new JTextField(10);
		artikelBestandInput = new JTextField(10);
	}
	
		//Hinzufuegen
	private void createArtikelHinzufuegenButtonsPanel(){
		artikelHinzufuegenButtonsPanel = new JPanel();
		artikelHinzufuegenButtonsPanel.setLayout(new BoxLayout(artikelHinzufuegenButtonsPanel, BoxLayout.Y_AXIS));

		JButton bestaetigen = new JButton(" Hinzuf\u00fcgen ");
		bestaetigen.addActionListener(new ActionListener() {
			///////////// Artikel Hinzufuegen /////////////
			@Override
			public void actionPerformed(ActionEvent e) {
				boolean success = false;
				int artikelnummer = -1;

				try{
					artikelnummer = Integer.parseInt(artikelNummerInput.getText());
					String bezeichnung = artikelBezeichnungInput.getText();
					double preis = Double.parseDouble(artikelPreisInput.getText());
					int bestand = Integer.parseInt(artikelBestandInput.getText());
					if(bestand < 0){
						// Wird zum Catch Block mit NumberFormatException weitergeleitet
						// weil -1 kein gültiger Wert ist
						throw new NumberFormatException();
					}
					if(bezeichnung != null && !bezeichnung.equals("")){
						if(artikelPackungsGroesseInput.getText().equals("") || artikelPackungsGroesseInput.getText().equals("1")){
							shop.fuegeArtikelEin(mitarbeiter, artikelnummer, bezeichnung, preis, bestand);
							updateArtikelTableModel(shop.gibAlleArtikelSortiertNachBezeichnung());
						}else{
							int packungsgroesse = Integer.parseInt(artikelPackungsGroesseInput.getText());
							shop.fuegeMassengutartikelEin(mitarbeiter, artikelnummer, bezeichnung, preis, packungsgroesse, bestand);
							updateArtikelTableModel(shop.gibAlleArtikelSortiertNachBezeichnung());
						}
						success = true;
					}else{
						setErrorMsg("Sie m\u00fcssen eine Artikelbezeichnung angeben!", artikelFooterWrapper);
					}
					
				}catch (NumberFormatException nfe){
					setErrorMsg("Bitte f\u00fcgen Sie richtige Werte ein!", artikelFooterWrapper);
				} catch (ArtikelBestandIstKeineVielfacheDerPackungsgroesseException ex) {
					setErrorMsg("Bestand ist keine Vielfache der Packungsgr\u00f6\u00dfe!", artikelFooterWrapper);
				} catch (ArtikelExistiertBereitsException ex) {
					setErrorMsg("Die angegebene ID existiert bereits!", artikelFooterWrapper);
				}


				if(success){
					artikelFooterWrapper.setVisible(false);
					artikelFooterWrapper.remove(artikelFooterPanel);
					
					// Wähle die hinzugefügte Zeile aus
					int index = artikelTableModel.getRowIndex(artikelnummer);
					
					if(index != -1){
						artikelTable.setRowSelectionInterval(index, index);
					}

					// Reset von allen Input Felden
					clearEingabeFelder();
					clearErrorMsg();
				}
			}
		});

		JButton abbrechen = new JButton(" Abbrechen  ");
		abbrechen.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				artikelFooterWrapper.setVisible(false);
				artikelFooterWrapper.remove(artikelFooterPanel);

				// Resetten
				clearErrorMsg();
				clearEingabeFelder();
			}
		});

		artikelHinzufuegenButtonsPanel.add(bestaetigen);
		artikelHinzufuegenButtonsPanel.add(abbrechen);
	}
	
	private void rebuildArtikelHinzufuegenEingabeFeld(){
		artikelHinzufuegenEingabeFeld = new JPanel(new GridLayout(0, 4));
		artikelHinzufuegenEingabeFeld.setPreferredSize(new Dimension(500, 90));
		artikelHinzufuegenEingabeFeld.setMaximumSize(new Dimension(500, 90));
		artikelHinzufuegenEingabeFeld.setMinimumSize(new Dimension(500, 90));
		
		artikelHinzufuegenEingabeFeld.add(new JLabel("   Artikelnummer:"));
		artikelHinzufuegenEingabeFeld.add(artikelNummerInput);
		
		artikelHinzufuegenEingabeFeld.add(new JLabel("   Bezeichnung:"));
		artikelHinzufuegenEingabeFeld.add(artikelBezeichnungInput);
		
		artikelHinzufuegenEingabeFeld.add(new JLabel("   Preis:"));
		artikelHinzufuegenEingabeFeld.add(artikelPreisInput);
		
		artikelHinzufuegenEingabeFeld.add(new JLabel("   Packungsgr\u00f6\u00dfe:"));
		artikelHinzufuegenEingabeFeld.add(artikelPackungsGroesseInput);
		
		artikelHinzufuegenEingabeFeld.add(new JLabel("   Bestand:"));
		artikelHinzufuegenEingabeFeld.add(artikelBestandInput);
	}
	
		//Bearbeiten
	private void createArtikelBearbeitenButtonsPanel(){
		artikelBearbeitenButtonsPanel = new JPanel();
		artikelBearbeitenButtonsPanel.setLayout(new BoxLayout(artikelBearbeitenButtonsPanel, BoxLayout.Y_AXIS));
		
		JButton bestaetigen = new JButton(" Speichern ");
		bestaetigen.addActionListener(new ActionListener() {
			//////// Speichern ////////
			@Override
			public void actionPerformed(ActionEvent e) {
				boolean success = false;
				Artikel a = null;
				String bezeichnung = "";
				double preis = 0;
				
				int row = artikelTable.getSelectedRow();
				if(row != -1){
					a = artikelTableModel.getArtikel(artikelTable.convertRowIndexToModel(row));
					try{
						bezeichnung = artikelBezeichnungInput.getText();
						preis = Double.parseDouble(artikelPreisInput.getText());
						if(bezeichnung.equals("")){
							setErrorMsg("Die Bezeichnung muss einen g\u00fcltigen Namen haben!", artikelFooterWrapper);
						}else{
							success = true;
						}
					} catch (NumberFormatException nfe){
						setErrorMsg("Geben Sie bitte richtege Werte ein!", artikelFooterWrapper);
					}
				}else{
					setErrorMsg("Ups... Keine Zeile ausgew\u00e4hlt!", artikelFooterWrapper);
				}
				
				if(a == null){
					setErrorMsg("Interner Fehler: 'Kein Artikel gefunden' !", artikelFooterWrapper);
				}else if(success){
					try {
						shop.artikelBearbeiten(a.getArtikelnummer(), preis, bezeichnung);
					} catch (ArtikelExistiertNichtException e1) {
						setErrorMsg("Interner Fehler! Artikel existiert nicht..." , artikelFooterWrapper);
						return;
					}
					
					updateArtikelTableModel(shop.gibAlleArtikelSortiertNachBezeichnung());
					clearArtikelTableSelection();
					
					artikelFooterWrapper.setVisible(false);
					// Resetten
					clearErrorMsg();
					clearEingabeFelder();
				}
				
			}
		});
		
		JButton abbrechen = new JButton("Abbrechen");
		abbrechen.addActionListener(new ActionListener() {
			//////// Abbrechen ////////
			@Override
			public void actionPerformed(ActionEvent e) {
				artikelFooterWrapper.setVisible(false);
				artikelFooterWrapper.remove(artikelFooterPanel);

				// Resetten
				clearErrorMsg();
				clearEingabeFelder();
			}
		});
		
		artikelBearbeitenButtonsPanel.add(bestaetigen);
		artikelBearbeitenButtonsPanel.add(abbrechen);
	}
	
	private void rebuildArtikelBearbeitenEingabeFeld(Artikel a){
		artikelBearbeitenEingabeFeld = new JPanel(new GridLayout(0, 2));
		artikelBearbeitenEingabeFeld.setPreferredSize(new Dimension(250, 80));
		artikelBearbeitenEingabeFeld.setMaximumSize(new Dimension(250, 100));
		artikelBearbeitenEingabeFeld.setMinimumSize(new Dimension(250, 70));
		
		artikelBearbeitenEingabeFeld.add(new JLabel("   Bezeichnung:"));
		artikelBearbeitenEingabeFeld.add(artikelBezeichnungInput);
		artikelBezeichnungInput.setText(""+a.getBezeichnung());
		
		artikelBearbeitenEingabeFeld.add(new JLabel("   Preis:"));
		artikelBearbeitenEingabeFeld.add(artikelPreisInput);
		artikelPreisInput.setText(new DecimalFormat("##.00").format(a.getPreis())+"");
		
		if(a instanceof Massengutartikel){
			artikelBearbeitenEingabeFeld.add(new JLabel("   Packungsgr\u00f6\u00dfe:"));
			artikelBearbeitenEingabeFeld.add(artikelPackungsGroesseInput);
			artikelPackungsGroesseInput.setText(((Massengutartikel) a).getPackungsgroesse()+"");
		}
	}
	
		//Einlagern
	private void createArtikelEinlagernButtonsPanel(){
		artikelEinlagernButtonsPanel = new JPanel();
		artikelEinlagernButtonsPanel.setLayout(new BoxLayout(artikelEinlagernButtonsPanel, BoxLayout.Y_AXIS));
		
		JButton bestaetigen = new JButton(" Einlagern ");
		bestaetigen.addActionListener(new ActionListener() {
			//////// Einlagern ////////
			@Override
			public void actionPerformed(ActionEvent e) {
				boolean success = false;
				int row = artikelTable.getSelectedRow();
				Artikel a = null;
				int anzahl = 0;
				
				if(row != -1){
					a = artikelTableModel.getArtikel(artikelTable.convertRowIndexToModel(row));
					try{
						anzahl = Integer.parseInt(artikelBestandInput.getText());
						if(anzahl < 0){
							setErrorMsg("Sie k\u00f6nnen nur eine positive Zahl eingeben!", artikelFooterWrapper);
						}else{
							success = true;
						}
					} catch (NumberFormatException nfe){
						setErrorMsg("Geben Sie einen g\u00fcltigen Wert ein!", artikelFooterWrapper);
					}
				}else{
					setErrorMsg("Ups... Keine Zeile ausgew\u00e4hlt!", artikelFooterWrapper);
				}
				
				if(a == null){
					setErrorMsg("Interner Fehler: 'Kein Artikel gefunden' !", artikelFooterWrapper);
				}else if(success){
					try {
						shop.artikelBestandVeraendern(mitarbeiter, a.getArtikelnummer(), anzahl);
					} catch (ArtikelBestandIstKeineVielfacheDerPackungsgroesseException e1) {
						setErrorMsg("Die Anzahl ist keine Vielfache der Packungsgroesse!", artikelFooterWrapper);
						return;
					} catch (ArtikelExistiertNichtException e1) {
						setErrorMsg("Interner Fehler! Das Artikel existiert nicht...", artikelFooterWrapper);
						return;
					}
					
					updateArtikelTableModel(shop.gibAlleArtikelSortiertNachBezeichnung());
					clearArtikelTableSelection();
					
					artikelFooterWrapper.setVisible(false);
					// Resetten
					clearErrorMsg();
					clearEingabeFelder();
				}
			}
		});
		
		JButton abbrechen = new JButton("Abbrechen");
		abbrechen.addActionListener(new ActionListener() {
			//////// Abbrechen ////////
			@Override
			public void actionPerformed(ActionEvent e) {
				artikelFooterWrapper.setVisible(false);
				artikelFooterWrapper.remove(artikelFooterPanel);

				// Resetten
				clearErrorMsg();
				clearEingabeFelder();
			}
		});
		
		artikelEinlagernButtonsPanel.add(bestaetigen);
		artikelEinlagernButtonsPanel.add(abbrechen);
	}
	
	private void rebuildEinlagernEingabeFeld(){
		artikelEinlagernEingabeFeld = new JPanel(new GridLayout(0, 2));
		artikelEinlagernEingabeFeld.setPreferredSize(new Dimension(150, 20));
		artikelEinlagernEingabeFeld.setMaximumSize(new Dimension(150, 40));
		artikelEinlagernEingabeFeld.setMinimumSize(new Dimension(150, 15));
		
		artikelEinlagernEingabeFeld.add(new JLabel("   Anzahl:"));
		artikelEinlagernEingabeFeld.add(artikelBestandInput);
	}
	
		//Auslagern
	private void createArtikelAuslagernButtonsPanel(){
		artikelAuslagernButtonsPanel = new JPanel();
		artikelAuslagernButtonsPanel.setLayout(new BoxLayout(artikelAuslagernButtonsPanel, BoxLayout.Y_AXIS));
		
		JButton bestaetigen = new JButton(" Auslagern");
		bestaetigen.addActionListener(new ActionListener() {
			//////// Auslagern ////////
			@Override
			public void actionPerformed(ActionEvent e) {
				boolean success = false;
				int row = artikelTable.getSelectedRow();
				Artikel a = null;
				int anzahl = 0;
				
				if(row != -1){
					a = artikelTableModel.getArtikel(artikelTable.convertRowIndexToModel(row));
					try{
						anzahl = Integer.parseInt(artikelBestandInput.getText());
						if(anzahl < 0){
							setErrorMsg("Sie k\u00f6nnen nur eine positive Zahl eingeben!", artikelFooterWrapper);
						}else if(anzahl > a.getBestand()){
							setErrorMsg("Sie k\u00f6nnen nicht soviel auslagern!", artikelFooterWrapper);
						}else{
							success = true;
						}
					} catch (NumberFormatException nfe){
						setErrorMsg("Geben Sie einen g\u00fcltigen Wert ein!", artikelFooterWrapper);
					}
				}else{
					setErrorMsg("Ups... Keine Zeile ausgew\u00e4hlt!", artikelFooterWrapper);
				}
				
				if(a == null){
					setErrorMsg("Interner Fehler: 'Kein Artikel gefunden' !", artikelFooterWrapper);
				}else if(success){
					try {
						shop.artikelBestandVeraendern(mitarbeiter, a.getArtikelnummer(), -anzahl);
					} catch (ArtikelBestandIstKeineVielfacheDerPackungsgroesseException e1) {
						setErrorMsg("Die Anzahl ist keine Vielfache der Packungsgroesse!", artikelFooterWrapper);
						return;
					} catch (ArtikelExistiertNichtException e1) {
						setErrorMsg("Interner Fehler! Das Artikel existiert nicht...", artikelFooterWrapper);
						return;
					}
					
					updateArtikelTableModel(shop.gibAlleArtikelSortiertNachBezeichnung());
					clearArtikelTableSelection();
					
					artikelFooterWrapper.setVisible(false);
					// Resetten
					clearErrorMsg();
					clearEingabeFelder();
				}
			}
		});
		
		JButton abbrechen = new JButton("Abbrechen");
		abbrechen.addActionListener(new ActionListener() {
			//////// Abbrechen ////////
			@Override
			public void actionPerformed(ActionEvent e) {
				artikelFooterWrapper.setVisible(false);
				artikelFooterWrapper.remove(artikelFooterPanel);

				// Resetten
				clearErrorMsg();
				clearEingabeFelder();
			}
		});
		
		artikelAuslagernButtonsPanel.add(bestaetigen);
		artikelAuslagernButtonsPanel.add(abbrechen);
	}
	
	private void rebuildAuslagernEingabeFeld(){
		artikelAuslagernEingabeFeld = new JPanel(new GridLayout(0, 2));
		artikelAuslagernEingabeFeld.setPreferredSize(new Dimension(150, 20));
		artikelAuslagernEingabeFeld.setMaximumSize(new Dimension(150, 40));
		artikelAuslagernEingabeFeld.setMinimumSize(new Dimension(150, 15));
		
		artikelAuslagernEingabeFeld.add(new JLabel("   Anzahl:"));
		artikelAuslagernEingabeFeld.add(artikelBestandInput);
	}
	
	
	//Artikel Helper Methoden
	private void clearEingabeFelder(){
		artikelNummerInput.setText("");
		artikelBezeichnungInput.setText("");
		artikelPreisInput.setText("");
		artikelPackungsGroesseInput.setText("");
		artikelBestandInput.setText("");
	}
	
	private void clearArtikelTableSelection(){
		artikelTable.clearSelection();
		
		artikelBearbeiten.setEnabled(false);
		artikelEinlagern.setEnabled(false);
		artikelAuslagern.setEnabled(false);
		artikelBestandshistorie.setEnabled(false);
		artikelEntfernen.setEnabled(false);
	}
	
	private void updateArtikelTableModel(List<Artikel> artikelListe){
		artikelTableModel = new ArtikelTableModel(artikelListe);
		artikelTable.setModel(artikelTableModel);
		artikelTableModel.fireTableDataChanged();
		artikelTableScrollPane.setViewportView(artikelTable);
	}
	
	//////////////////////  Mitarbeiter Panels  //////////////////////
	
	private void createMitarbeiterPanel(){
		mitarbeiterPanel = new JPanel(new BorderLayout());
		
		JPanel north = new JPanel();
		north.setLayout(new BoxLayout(north, BoxLayout.X_AXIS));
		
		mitarbeiterTableModel = new MitarbeiterTableModel(shop.gibAlleMitarbeiter(), mitarbeiter.getFunktion());
		mitarbeiterSorter = new TableRowSorter<MitarbeiterTableModel>(mitarbeiterTableModel);
		
		mitarbeiterTable = new JTable(mitarbeiterTableModel);
		mitarbeiterTable.setRowSorter(mitarbeiterSorter);
		mitarbeiterTable.setShowGrid(true);
		mitarbeiterTable.setGridColor(Color.LIGHT_GRAY);
		
		mitarbeiterTable.getSelectionModel().setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		mitarbeiterTable.getTableHeader().setReorderingAllowed(false);
		mitarbeiterTable.getSelectionModel().addListSelectionListener(new MitarbeiterSelectionListener());
		MitarbeiterTableCellRenderer mitarbeiterTableCellRenderer = new MitarbeiterTableCellRenderer(artikelTable);
		setTableCellAlignment(mitarbeiterTableCellRenderer, mitarbeiterTable, JLabel.LEFT);
		
		mitarbeiterTableScrollPane = new JScrollPane(mitarbeiterTable);
		mitarbeiterTableScrollPane.setBorder(BorderFactory.createEtchedBorder());
		mitarbeiterTableScrollPane.setAlignmentY(TOP_ALIGNMENT);

		/////////// Mitarbeiter Buttons ///////////
		mitarbeiterButtonsPanel = new JPanel(new GridLayout(0,1));

		MitarbeiterPanelListener listener = new MitarbeiterPanelListener();

		mitarbeiterHinzufuegen = new JButton("Hinzuf\u00fcgen");
		mitarbeiterHinzufuegen.addActionListener(listener);

		mitarbeiterBearbeiten = new JButton("Bearbeiten");
		mitarbeiterBearbeiten.addActionListener(listener);
		mitarbeiterBearbeiten.setEnabled(false);

		mitarbeiterBlockieren =   new JButton("Blockieren");
		mitarbeiterBlockieren.addActionListener(listener);
		mitarbeiterBlockieren.setEnabled(false);

		mitarbeiterEntfernen =   new JButton("Entfernen");
		mitarbeiterEntfernen.addActionListener(listener);
		mitarbeiterEntfernen.setEnabled(false);


		mitarbeiterButtonsPanel.setMaximumSize(new Dimension(100, 4*25));
		mitarbeiterButtonsPanel.add(mitarbeiterHinzufuegen);
		mitarbeiterButtonsPanel.add(mitarbeiterBearbeiten);
		mitarbeiterButtonsPanel.add(mitarbeiterBlockieren);
		mitarbeiterButtonsPanel.add(mitarbeiterEntfernen);
		mitarbeiterButtonsPanel.setAlignmentY(TOP_ALIGNMENT);
		
		north.add(mitarbeiterTableScrollPane);
		
		if(!mitarbeiter.getFunktion().equals(MitarbeiterFunktion.Mitarbeiter))
			north.add(mitarbeiterButtonsPanel);
		createMitarbeiterFooterWrapper();
		mitarbeiterPanel.add(mitarbeiterFooterWrapper, BorderLayout.SOUTH);
		
		mitarbeiterPanel.add(north, BorderLayout.CENTER);
	}
	
	private void createMitarbeiterFooterWrapper(){
		mitarbeiterFooterWrapper = new JPanel();
		mitarbeiterFooterWrapper.setLayout(new BoxLayout(mitarbeiterFooterWrapper, BoxLayout.Y_AXIS));
		
		createMitarbeiterFooterPanel();

//		mitarbeiterFooterWrapper.setBorder(BorderFactory.createMatteBorder(1, 0, 0, 0, Color.LIGHT_GRAY));
		mitarbeiterFooterWrapper.setVisible(false);
	}
	
	private void createMitarbeiterFooterPanel(){
		mitarbeiterFooterPanel = new JPanel();
		mitarbeiterFooterPanel.setLayout(new BoxLayout(mitarbeiterFooterPanel, BoxLayout.X_AXIS));
		mitarbeiterFooterPanel.setAlignmentX(LEFT_ALIGNMENT);
		
		createMitarbeiterEingabeFeldKomponenten();
		rebuildArtikelHinzufuegenEingabeFeld();
		
		createMitarbeiterHinzufuegenButtonsPanel();
		createMitarbeiterBearbeitenButtonsPanel();
	}
	
	private void createMitarbeiterEingabeFeldKomponenten(){
		mitarbeiterUsernameInput = new JTextField(10);
		mitarbeiterNameInput = new JTextField(10);
		mitarbeiterGehaltInput = new JTextField(10);
		mitarbeiterFunktionInput = new JComboBox();
		MitarbeiterFunktion[] funktionWerte = MitarbeiterFunktion.values();
		for(MitarbeiterFunktion mf : funktionWerte){
			mitarbeiterFunktionInput.addItem(mf);
		}
	}
	
	
		//Hinzufuegen
	private void rebuildMitarbeiterHinzufuegenEingabeFeld(){
		mitarbeiterHinzufuegenEingabeFeld = new JPanel(new GridLayout(0, 4));
		mitarbeiterHinzufuegenEingabeFeld.setPreferredSize(new Dimension(500, 60));
		mitarbeiterHinzufuegenEingabeFeld.setMaximumSize(new Dimension(500, 70));
		mitarbeiterHinzufuegenEingabeFeld.setMinimumSize(new Dimension(500, 40));
		
		mitarbeiterHinzufuegenEingabeFeld.add(new JLabel("   Username:"));
		mitarbeiterHinzufuegenEingabeFeld.add(mitarbeiterUsernameInput);
		
		mitarbeiterHinzufuegenEingabeFeld.add(new JLabel("   Name:"));
		mitarbeiterHinzufuegenEingabeFeld.add(mitarbeiterNameInput);
		
		mitarbeiterHinzufuegenEingabeFeld.add(new JLabel("   Gehalt:"));
		mitarbeiterHinzufuegenEingabeFeld.add(mitarbeiterGehaltInput);

		mitarbeiterHinzufuegenEingabeFeld.add(new JLabel("   Funktion:"));
		mitarbeiterHinzufuegenEingabeFeld.add(mitarbeiterFunktionInput);
		
	}
	
	private void createMitarbeiterHinzufuegenButtonsPanel(){
		mitarbeiterHinzufuegenButtonsPanel = new JPanel(new GridLayout(0,1));
		mitarbeiterHinzufuegenButtonsPanel.setMaximumSize(new Dimension(100, 2*25));

		JButton bestaetigen = new JButton(" Hinzuf\u00fcgen ");
		bestaetigen.addActionListener(new ActionListener() {
			///////////// Mitarbeiter Hinzufuegen /////////////
			@Override
			public void actionPerformed(ActionEvent e) {
				boolean success = false;
				String username = "";
				
				try{
					username = mitarbeiterUsernameInput.getText();
					String name = mitarbeiterNameInput.getText();
					double gehalt = 0;

					if(!mitarbeiterGehaltInput.getText().equals("")){
						gehalt = Double.parseDouble(mitarbeiterGehaltInput.getText());
						if(gehalt < MINDESTLOHN){
							setErrorMsg("Der Mindestlohn betr\u00e4gt "+MINDESTLOHN+" EUR!", mitarbeiterFooterWrapper);
							return;
						}
					}else{
						gehalt = MINDESTLOHN;
					}
					
					MitarbeiterFunktion mf = (MitarbeiterFunktion)mitarbeiterFunktionInput.getSelectedItem();

					if(username.equals("") || name.equals("")){
						setErrorMsg("Sie m\u00fcssen 'Username' und 'Name' angeben!", mitarbeiterFooterWrapper);
					}else{
						shop.fuegeMitarbeiterHinzu(username, standardPasswort , name, mf, gehalt);
						success = true;
					}
				} catch (NumberFormatException nfe){
					setErrorMsg("Bitte geben Sie nur g\u00fcltige Werte an!", mitarbeiterFooterWrapper);
				} catch (MitarbeiterExistiertBereitsException ex) {
					setErrorMsg("Es existiert bereits ein Mitarbeiter mit dieser ID!", mitarbeiterFooterWrapper);
				} catch (UsernameExistiertBereitsException ex) {
					setErrorMsg("Dieser Username ist bereits vergeben!", mitarbeiterFooterWrapper);
				}
				
				if(success){
					updateMitarbeiterTableModel(shop.gibAlleMitarbeiter());
					
					// Resette Search
					searchField.setText("");
					mitarbeiterSorter.setRowFilter(null);
					
					int index = mitarbeiterTableModel.getRowIndex(username);
					
					if(index != -1){
						mitarbeiterTable.setRowSelectionInterval(index, index);
					}
					
					//Setze alles zurück
					clearErrorMsg();
					clearMitarbeiterEingabeFelder();
					mitarbeiterFooterPanel.removeAll();
					mitarbeiterFooterWrapper.setVisible(false);
				}
			}
		});

		JButton abbrechen = new JButton(" Abbrechen  ");
		abbrechen.addActionListener(new ActionListener() {
			///////////// Abbrechen ///////////// 
			@Override
			public void actionPerformed(ActionEvent e) {
				clearErrorMsg();
				clearMitarbeiterEingabeFelder();
				mitarbeiterFooterPanel.removeAll();
				mitarbeiterFooterWrapper.setVisible(false);
			}
		});

		mitarbeiterHinzufuegenButtonsPanel.add(bestaetigen);
		mitarbeiterHinzufuegenButtonsPanel.add(abbrechen);
	}
	
		//Bearbeiten
	private void rebuildMitarbeiterBearbeitenEingabeFeld(Mitarbeiter m){
		mitarbeiterBearbeitenEingabeFeld = new JPanel(new GridLayout(0, 4));
		mitarbeiterBearbeitenEingabeFeld.setPreferredSize(new Dimension(500, 30));
		mitarbeiterBearbeitenEingabeFeld.setMaximumSize(new Dimension(500, 35));
		mitarbeiterBearbeitenEingabeFeld.setMinimumSize(new Dimension(500, 20));
		
		mitarbeiterBearbeitenEingabeFeld.add(new JLabel("   Gehalt:"));
		mitarbeiterBearbeitenEingabeFeld.add(mitarbeiterGehaltInput);
		mitarbeiterGehaltInput.setText(new DecimalFormat("##.00").format(m.getGehalt())+"");
		
		mitarbeiterBearbeitenEingabeFeld.add(new JLabel("   Funktion:"));
		mitarbeiterBearbeitenEingabeFeld.add(mitarbeiterFunktionInput);
		mitarbeiterFunktionInput.setSelectedItem(m.getFunktion());
	}
	
	private void createMitarbeiterBearbeitenButtonsPanel(){
		mitarbeiterBearbeitenButtonsPanel = new JPanel(new GridLayout(0,1));
		mitarbeiterBearbeitenButtonsPanel.setMaximumSize(new Dimension(100, 2*25));

		JButton bestaetigen = new JButton("Speichern");
		bestaetigen.addActionListener(new ActionListener() {
			///////////// Mitarbeiter Bearbeiten /////////////
			@Override
			public void actionPerformed(ActionEvent e) {
				boolean success = false;
				
				int row = mitarbeiterTable.getSelectedRow();
				
				if(row != -1){
					Mitarbeiter m = mitarbeiterTableModel.getMitarbeiter(mitarbeiterTable.convertRowIndexToModel(row));
					double gehalt = 0;
					try{
						gehalt = Double.parseDouble(mitarbeiterGehaltInput.getText());
						
						if(gehalt < 0){
							setErrorMsg("Sie k\u00f6nnen keinen negativen Wert eingeben!", mitarbeiterFooterWrapper);
						}else if(gehalt < MINDESTLOHN){
							setErrorMsg(String.format("Der Mindestlohn f\u00fcr Mitarbeiter betr\u00e4gt: %.2f "+ Currency.getInstance(Locale.GERMANY), MINDESTLOHN) , mitarbeiterFooterWrapper);
						}else{
							shop.mitarbeiterBearbeiten(m.getId(), m.getPasswort(), m.getName(), (MitarbeiterFunktion)mitarbeiterFunktionInput.getSelectedItem(), gehalt, m.getBlockiert());
							updateMitarbeiterTableModel(shop.gibAlleMitarbeiter());
							success = true;
						}
					} catch (NumberFormatException nfe){
						setErrorMsg("Bitte geben Sie einen g\u00fcltigen Wert an!", mitarbeiterFooterWrapper);
						return;
					} catch (MitarbeiterExistiertNichtException e1) {
						setErrorMsg("Interner Fehler! Mitarbeiter existiert nicht...", mitarbeiterFooterWrapper);
						return;
					}
				}else{
					setErrorMsg("Keine Zeile ausgew\u00e4hlt!", mitarbeiterFooterWrapper);
				}
				
				if(success){
					clearErrorMsg();
					clearMitarbeiterEingabeFelder();
					mitarbeiterFooterPanel.removeAll();
					mitarbeiterFooterWrapper.setVisible(false);
				}
			}
		});

		JButton abbrechen = new JButton("Abbrechen");
		abbrechen.addActionListener(new ActionListener() {
			///////////// Abbrechen /////////////
			@Override
			public void actionPerformed(ActionEvent e) {
				clearErrorMsg();
				clearMitarbeiterEingabeFelder();
				mitarbeiterFooterPanel.removeAll();
				mitarbeiterFooterWrapper.setVisible(false);
			}
		});

		mitarbeiterBearbeitenButtonsPanel.add(bestaetigen);
		mitarbeiterBearbeitenButtonsPanel.add(abbrechen);
	}
	
	//Mitarbeiter Helper Methoden
	private void clearMitarbeiterEingabeFelder(){
		mitarbeiterUsernameInput.setText("");
		mitarbeiterNameInput.setText("");
		mitarbeiterGehaltInput.setText("");
	}

	private void clearMitarbeiterTableSelection(){
		mitarbeiterTable.clearSelection();

		mitarbeiterBearbeiten.setEnabled(false);
		mitarbeiterEntfernen.setEnabled(false);
		mitarbeiterBlockieren.setEnabled(false);
		mitarbeiterBlockieren.setText("Blockieren");
	}

	private void updateMitarbeiterTableModel(List<Mitarbeiter> mitarbeiterListe){
		mitarbeiterTableModel = new MitarbeiterTableModel(mitarbeiterListe, mitarbeiter.getFunktion());
		mitarbeiterSorter.setModel(mitarbeiterTableModel);
		mitarbeiterTable.setModel(mitarbeiterTableModel);
		mitarbeiterTableScrollPane.setViewportView(mitarbeiterTable);
	}
	
	//////////////////////  Kunden Panels  //////////////////////
	
	private void createKundenPanel(){
		kundenPanel = new JPanel(new BorderLayout());
		
		JPanel centerPanel = new JPanel();
		centerPanel.setLayout(new BoxLayout(centerPanel, BoxLayout.X_AXIS));
		
		kundenTableModel = new KundenTableModel(shop.gibAlleKunden());
		kundenSorter = new TableRowSorter<KundenTableModel>(kundenTableModel); 
		
		kundenTable = new JTable(kundenTableModel);
		kundenTable.setRowSorter(kundenSorter);
		kundenTable.setShowGrid(true);
		kundenTable.setGridColor(Color.LIGHT_GRAY);
		
		kundenTable.getSelectionModel().setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		kundenTable.getTableHeader().setReorderingAllowed(false);
		kundenTable.getSelectionModel().addListSelectionListener(new KundenSelectionListener());
		setTableCellAlignment(new KundenTableCellRenderer(), kundenTable, JLabel.LEFT);
		
		kundenTableScrollPane = new JScrollPane(kundenTable);
		kundenTableScrollPane.setBorder(BorderFactory.createEtchedBorder());
		kundenTableScrollPane.setAlignmentY(TOP_ALIGNMENT);
		
		/////////// Kunden Buttons ///////////
		kundenButtonsPanel = new JPanel(new GridLayout(0,1));
		int buttonCounter = 0;
		
		KundenPanelListener listener = new KundenPanelListener();
		
		
		kundenEntfernen =  new JButton("Entfernen");
		kundenEntfernen.addActionListener(listener);
		kundenEntfernen.setEnabled(false);
		
		if(!mitarbeiter.getFunktion().equals(MitarbeiterFunktion.Mitarbeiter)){
			kundenButtonsPanel.add(kundenEntfernen);
			buttonCounter++;
		}
		
		kundenBlockieren = new JButton("Blockieren");
		kundenBlockieren.addActionListener(listener);
		kundenBlockieren.setEnabled(false);
		kundenButtonsPanel.add(kundenBlockieren);
		buttonCounter++;
		
		kundenButtonsPanel.setMaximumSize(new Dimension(100, buttonCounter*25));
		kundenButtonsPanel.setAlignmentY(TOP_ALIGNMENT);
		
		centerPanel.add(kundenTableScrollPane);
		centerPanel.add(kundenButtonsPanel);
		
		createKundenFooterWrapper();
		
		kundenPanel.add(centerPanel, BorderLayout.CENTER);
		kundenPanel.add(kundenFooterWrapper, BorderLayout.SOUTH);
	}

	private void createKundenFooterWrapper(){
		kundenFooterWrapper = new JPanel();
		kundenFooterWrapper.setLayout(new BoxLayout(kundenFooterWrapper, BoxLayout.Y_AXIS));
		
//		kundenFooterWrapper.setBorder(BorderFactory.createMatteBorder(1, 0, 0, 0, Color.LIGHT_GRAY));
		kundenFooterWrapper.setVisible(false);
	}
	
		//Kunden Helper Methoden
	
	private void updateKundenTableModel(List<Kunde> kundenListe){
		kundenTableModel = new KundenTableModel(kundenListe);
		kundenSorter.setModel(kundenTableModel);
		kundenTable.setModel(kundenTableModel);
		kundenTableScrollPane.setViewportView(kundenTable);
	}
	
	private void clearKundenTableSelection(){
		kundenTable.clearSelection();

		kundenEntfernen.setEnabled(false);
		kundenBlockieren.setEnabled(false);
		kundenBlockieren.setText("Blockieren");
	}
	
	//////////////////////  Log Panels  //////////////////////
	
	private void createLogPanel() throws IOException{
		logPanel = new JPanel(new BorderLayout());
		
		LogTableModel logTableModel = new LogTableModel(shop.gibLogDatei());
		logSorter = new TableRowSorter<LogTableModel>(logTableModel); 
		
		logTable = new JTable(logTableModel);
		logTable.setRowSorter(logSorter);
		logTable.setShowGrid(true);
		logTable.setGridColor(Color.LIGHT_GRAY);
		
		logTable.getSelectionModel().setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		logTable.getTableHeader().setReorderingAllowed(false);
		
		setTableCellAlignment(new DefaultTableCellRenderer(), logTable, JLabel.LEFT);
		
		logScrollPane = new JScrollPane(logTable);
		
		logPanel.add(logScrollPane, BorderLayout.CENTER);
	}

	private void updateLogTableModel(String logDatei){
		LogTableModel logTableModel = new LogTableModel(logDatei);
		logSorter.setModel(logTableModel);
		logTable.setModel(logTableModel);
		logScrollPane.setViewportView(logTable);
	}
	
	
	////////////////////// Account Panel //////////////////////
	
	private void createAccountPanel() {
		usernameFeld = new JTextField();
		usernameFeld.setText(mitarbeiter.getUsername());
		usernameFeld.setEnabled(false);
		JPanel namePanel = new JPanel();
		namePanel.setLayout(new GridLayout(2,2));
		namePanel.setBorder(BorderFactory.createEmptyBorder(0, 0, 30, 0));
		namePanel.add(new JLabel("Name:"));
		namePanel.add(mitarbeiterNameInput);
		namePanel.add(new JLabel("Username:"));
		namePanel.add(usernameFeld);
		errorName = new JTextArea();
		errorName.setBorder(BorderFactory.createEmptyBorder(5, 0, 0, 0));
		errorName.setForeground(Color.RED);
		errorName.setLineWrap(true);
		errorName.setWrapStyleWord(true);
		errorName.setEditable(false);
		errorName.setOpaque(false);
		JPanel errorPanel = new JPanel();
		errorPanel.setLayout(new GridLayout(3,1));
		errorPanel.setBorder(BorderFactory.createEmptyBorder(0, 5, 0, 0));
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
		accountAbbrechenButton = new JButton("Abbrechen");
		accountAbbrechenButton.addActionListener(new AccountListener());
		accountSpeichernButton = new JButton("Speichern");
		accountSpeichernButton.addActionListener(new AccountListener());
		JPanel buttonPanel = new JPanel();
		buttonPanel.setLayout(new BoxLayout(buttonPanel, BoxLayout.LINE_AXIS));
		buttonPanel.setBorder(BorderFactory.createEmptyBorder(60, 60, 0, 0));
		buttonPanel.add(accountAbbrechenButton);
		buttonPanel.add(accountSpeichernButton);
		accountPanel = new JPanel();
		accountPanel.setLayout(new GridLayout(4,2));
		accountPanel.setBorder(BorderFactory.createCompoundBorder(BorderFactory.createMatteBorder(1, 0, 0, 0, Color.LIGHT_GRAY), BorderFactory.createEmptyBorder(20, 10, 10, 10)));
		accountPanel.add(namePanel);
		accountPanel.add(passwortPanel);
		accountPanel.add(errorName);
		accountPanel.add(errorPasswort);
		accountPanel.add(errorPanel);
		accountPanel.add(new JLabel());
		accountPanel.add(buttonPanel);
	}
	
	////////////////////// Listener //////////////////////
	
	class ArtikelPanelListener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			// Säubere zuerst alle nötigen Komponenten
			clearErrorMsg();
			clearEingabeFelder();
			artikelFooterPanel.removeAll();
			
			int row = artikelTable.getSelectedRow();

			if(e.getSource().equals(artikelHinzufuegen)){				// Artikel Hinzufuegen

				clearArtikelTableSelection();

				// Bilde das Panel zum Hinzufuegen eines Artikels neu. Dies ist notwendig, da die Eingabefelder auch beim
				// Bearbeiten benutzt werden. Somit gehen wir sicher, dass sich die Eingabefelder jetzt auch im richtigen
				// Panel befinden.
				rebuildArtikelHinzufuegenEingabeFeld();

				// Fuege jetzt noch die entsprechenden Panels zum Artikelfooter hinzu
				artikelFooterPanel.add(Box.createGlue());
				artikelFooterPanel.add(artikelHinzufuegenEingabeFeld);
				artikelFooterPanel.add(Box.createRigidArea(new Dimension(50, 50)));
				artikelFooterPanel.add(artikelHinzufuegenButtonsPanel);
				artikelFooterPanel.add(Box.createGlue());

				// Setze das Artikelfoote in den Wrapper
				artikelFooterWrapper.add(artikelFooterPanel);
				artikelFooterWrapper.setVisible(true);


			}else if(row != -1){
				Artikel a = artikelTableModel.getArtikel(artikelTable.convertRowIndexToModel(row));
				

				if(e.getSource().equals(artikelBearbeiten)){					// Artikel Bearbeiten

					// Bilde das Panel zum Bearbeiten eines Artikels neu. Dies ist notwendig, da die Eingabefelder auch beim
					// Hinzufuegen benutzt werden. Somit gehen wir sicher, dass sich die Eingabefelder jetzt auch im richtigen
					// Panel befinden.Gleichzeitig werden die Felder initialisiert
					rebuildArtikelBearbeitenEingabeFeld(a);

					// Fuege jetzt noch die entsprechenden Panels zum Artikelfooter hinzu
					artikelFooterPanel.add(Box.createGlue());
					artikelFooterPanel.add(artikelBearbeitenEingabeFeld);
					artikelFooterPanel.add(Box.createRigidArea(new Dimension(50, 50)));
					artikelFooterPanel.add(artikelBearbeitenButtonsPanel);
					artikelFooterPanel.add(Box.createGlue());
					artikelFooterPanel.revalidate();
					artikelFooterPanel.repaint();

					// Setze das Artikelfooter in den Wrapper
					artikelFooterWrapper.add(artikelFooterPanel);
					artikelFooterWrapper.setVisible(true);

				}else if(e.getSource().equals(artikelEinlagern)){					// Artikel Einlagern

					rebuildEinlagernEingabeFeld();

					// Fuege jetzt noch die entsprechenden Panels zum Artikelfooter hinzu
					artikelFooterPanel.add(Box.createGlue());
					artikelFooterPanel.add(artikelEinlagernEingabeFeld);
					artikelFooterPanel.add(Box.createRigidArea(new Dimension(75, 50)));
					artikelFooterPanel.add(artikelEinlagernButtonsPanel);
					artikelFooterPanel.add(Box.createGlue());
					artikelFooterPanel.revalidate();
					artikelFooterPanel.repaint();

					// Setze das Artikelfooter in den Wrapper
					artikelFooterWrapper.add(artikelFooterPanel);
					artikelFooterWrapper.setVisible(true);


				}else if(e.getSource().equals(artikelAuslagern)){					// Artikel Auslagern

					rebuildAuslagernEingabeFeld();

					// Fuege jetzt noch die entsprechenden Panels zum Artikelfooter hinzu
					artikelFooterPanel.add(Box.createGlue());
					artikelFooterPanel.add(artikelAuslagernEingabeFeld);
					artikelFooterPanel.add(Box.createRigidArea(new Dimension(75, 50)));
					artikelFooterPanel.add(artikelAuslagernButtonsPanel);
					artikelFooterPanel.add(Box.createGlue());
					artikelFooterPanel.revalidate();

					// Setze das Artikelfooter in den Wrapper
					artikelFooterWrapper.add(artikelFooterPanel);
					artikelFooterWrapper.setVisible(true);

				}else if(e.getSource().equals(artikelBestandshistorie)){			// Artikel Bestandshistorie
					
					int[] yWerte = null;
					try {
						yWerte = shop.gibBestandsHistorieDaten(a.getArtikelnummer());
					} catch (IOException e1) {
						setErrorMsg("Fehler beim Lesen der Log Datei aufgetreten!", artikelFooterWrapper);
						return;
					} catch (ArtikelExistiertNichtException e1) {
						setErrorMsg("Dieser Artikel existiert nicht!", artikelFooterWrapper);
						return;
					}
					
					if(yWerte != null){
						BestandshistorieGraphik g = new BestandshistorieGraphik(yWerte, a);
						g.setPreferredSize(new Dimension(300,180));
						
						JButton vergroessern = new JButton("Verg\u00f6\u00dfern");
						vergroessern.addActionListener(new BestandshistorieVergroessernListener(g));
						
						JPanel tmp = new JPanel();
						tmp.add(vergroessern);
						
						
						// Fuege jetzt noch die entsprechenden Panels zum Artikelfooter hinzu
						artikelFooterPanel.add(Box.createGlue());
						artikelFooterPanel.add(g);
						artikelFooterPanel.add(Box.createGlue());
						artikelFooterPanel.add(tmp);
						artikelFooterPanel.add(Box.createRigidArea(new Dimension(0,0)));
						artikelFooterPanel.revalidate();
						artikelFooterPanel.repaint();

						// Setze das Artikelfooter in den Wrapper
						artikelFooterWrapper.add(artikelFooterPanel);
						artikelFooterWrapper.setVisible(true);
					}

				}else if(e.getSource().equals(artikelEntfernen)){					// Artikel Entfernen

					try {
						int choice = JOptionPane.showConfirmDialog(new JFrame(),
								"Sind Sie sicher, dass Sie das Artikel '"+a.getBezeichnung()+"'\n"
										+"mit der ID "+a.getArtikelnummer()+" l\u00f6schen m\u00f6chten?", "Sicher?!",
										JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE);

						if(choice == 0){
							shop.entferneArtikel(mitarbeiter, a.getArtikelnummer());
							updateArtikelTableModel(shop.gibAlleArtikelSortiertNachBezeichnung());
							updateLogTableModel(shop.gibLogDatei());
							clearArtikelTableSelection();
						}
						artikelFooterWrapper.setVisible(false);
					} catch (ArtikelExistiertNichtException e1) {
						setErrorMsg("Das Artikel existiert nicht!", artikelFooterWrapper);
						artikelFooterWrapper.setVisible(true);
					} catch (IOException e1) {
						setErrorMsg("Fehler beim anpassen der Logdatei!", artikelFooterWrapper);
						artikelFooterWrapper.setVisible(true);
					}

				}else{
					// do nothing
				}
			}else{ //row == -1
				setErrorMsg("Keine Zeile ausgew\u00e4hlt!", artikelFooterWrapper);
				artikelFooterWrapper.setVisible(true);
			}
		}
	}
	
	class BestandshistorieVergroessernListener implements ActionListener{

		private BestandshistorieGraphik g;
		
		public BestandshistorieVergroessernListener(BestandshistorieGraphik g){
			this.g = g;
		}
		
		@Override
		public void actionPerformed(ActionEvent e) {
			JButton zurueck = new JButton("Zur\u00fcck");
			zurueck.addActionListener(new ActionListener() {
				//////////// Zurueck ////////////
				@Override
				public void actionPerformed(ActionEvent e) {
					artikelPanel.removeAll();
					artikelFooterWrapper.setVisible(false);
					
					artikelPanel.add(artikelCenterPanel, BorderLayout.CENTER);
					artikelPanel.add(artikelFooterWrapper, BorderLayout.SOUTH);
					artikelPanel.revalidate();
					artikelPanel.repaint();
				}
			});

			JPanel tmp = new JPanel();
			tmp.add(zurueck);

			artikelPanel.removeAll();
			artikelPanel.add(g, BorderLayout.CENTER);
			artikelPanel.add(tmp, BorderLayout.EAST);
			artikelPanel.revalidate();
		}
		
	}
	
	class MitarbeiterPanelListener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {

			// Setze alles zurueck
			mitarbeiterFooterWrapper.setVisible(false);
			clearErrorMsg();
			clearMitarbeiterEingabeFelder();
			mitarbeiterFooterPanel.removeAll();
			
			int row = mitarbeiterTable.getSelectedRow();
			
			if(e.getSource().equals(mitarbeiterHinzufuegen)){					// Mitarbeiter Hinzufuegen

				clearMitarbeiterTableSelection();

				rebuildMitarbeiterHinzufuegenEingabeFeld();

				mitarbeiterFooterPanel.add(Box.createGlue());
				mitarbeiterFooterPanel.add(mitarbeiterHinzufuegenEingabeFeld);
				mitarbeiterFooterPanel.add(Box.createRigidArea(new Dimension(50,50)));
				mitarbeiterFooterPanel.add(mitarbeiterHinzufuegenButtonsPanel);
				mitarbeiterFooterPanel.add(Box.createGlue());
				mitarbeiterFooterPanel.revalidate();

				mitarbeiterFooterWrapper.add(mitarbeiterFooterPanel);
				mitarbeiterFooterWrapper.setVisible(true);

			}else if(row != -1){

				Mitarbeiter m = mitarbeiterTableModel.getMitarbeiter(mitarbeiterTable.convertRowIndexToModel(row));

				if(!mitarbeiter.equals(m)){

					if(e.getSource().equals(mitarbeiterBearbeiten)){						// Mitarbeiter Bearbeiten

						rebuildMitarbeiterBearbeitenEingabeFeld(m);

						mitarbeiterFooterPanel.add(Box.createGlue());
						mitarbeiterFooterPanel.add(mitarbeiterBearbeitenEingabeFeld);
						mitarbeiterFooterPanel.add(Box.createRigidArea(new Dimension(50,50)));
						mitarbeiterFooterPanel.add(mitarbeiterBearbeitenButtonsPanel);
						mitarbeiterFooterPanel.add(Box.createGlue());
						mitarbeiterFooterPanel.revalidate();

						mitarbeiterFooterWrapper.add(mitarbeiterFooterPanel);
						mitarbeiterFooterWrapper.setVisible(true);

					}else if(e.getSource().equals(mitarbeiterBlockieren)) {					// Mitarbeiter Blockieren

						try {
							shop.mitarbeiterBearbeiten(m.getId(), m.getPasswort(), m.getName(), m.getFunktion(), m.getGehalt(), !m.getBlockiert());
						} catch (MitarbeiterExistiertNichtException e1) {
							setErrorMsg("Interner Fehler! Mitarbeiter existiert nicht...", mitarbeiterFooterWrapper);
							mitarbeiterFooterWrapper.setVisible(true);
							return;
						}
						updateMitarbeiterTableModel(shop.gibAlleMitarbeiter());
						
						mitarbeiterTable.setRowSelectionInterval(row, row);

					}else if(e.getSource().equals(mitarbeiterEntfernen)){					// Mitarbeiter Entfernen
						
						int choice = JOptionPane.showConfirmDialog(new JFrame(), "Sind Sie sicher, dass Sie Mitarbeiter '" + m.getName() + "'\n"
								+"(ID: "+m.getId()+") l\u00f6schen m\u00f6chten?", "Sicher?!", JOptionPane.YES_NO_OPTION, 
								JOptionPane.WARNING_MESSAGE);

						if(choice == JOptionPane.YES_OPTION){
							shop.mitarbeiterLoeschen(m);
							updateMitarbeiterTableModel(shop.gibAlleMitarbeiter());
							clearMitarbeiterTableSelection();
						}
					
					}else{
						//do nothing
					}
				}else{
					setErrorMsg("Sie k\u00f6nnen sich hier nicht selbst ver\u00e4ndern! Bitte gehen Sie daf\u00fcr in Ihre Account Einstellungen!", mitarbeiterFooterWrapper);
					mitarbeiterFooterWrapper.setVisible(true);
				}
			}else{
				setErrorMsg("Keine Zeile ausgew\u00e4hlt!", mitarbeiterFooterWrapper);
				mitarbeiterFooterWrapper.setVisible(true);
			}
		}
	}
	
	class KundenPanelListener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			kundenFooterWrapper.setVisible(false);
			clearErrorMsg();

			int row = kundenTable.getSelectedRow();

			if(row != -1){
				Kunde k = kundenTableModel.getKunde(kundenTable.convertRowIndexToModel(row));

				if(e.getSource().equals(kundenEntfernen)){					//Kunde Entfernen

					int choice = JOptionPane.showConfirmDialog(new JFrame(), "Sind Sie sicher, dass Sie Kunde '"+k.getName()+"'\n"
							+ "(ID: "+k.getId()+") l\u00f6schen m\u00f6chten!", "Sicher?!",
							JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE);

					if(choice == JOptionPane.YES_OPTION){
						shop.kundenLoeschen(k);
						updateKundenTableModel(shop.gibAlleKunden());
						clearKundenTableSelection();
					}
					
				}
				if(e.getSource().equals(kundenBlockieren)){			// Kunde Blockieren

					try {
						shop.kundenBearbeiten(k.getId(), k.getPasswort(), k.getName(), k.getStrasse(), k.getPlz(), k.getWohnort(), !k.getBlockiert());
					} catch (KundeExistiertNichtException e1) {
						setErrorMsg("Interner Fehler! Kunde existiert nicht...", kundenFooterWrapper);
						kundenFooterWrapper.setVisible(true);
						return;
					}
					updateKundenTableModel(shop.gibAlleKunden());
					
					kundenTable.setRowSelectionInterval(row, row);

				}else{
					// do nothing
				}
			}else{
				setErrorMsg("Keine Zeile ausgew\u00e4hlt!", kundenFooterWrapper);
				kundenFooterWrapper.setVisible(true);
			}
		}
	}
	
	class AccountListener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			if(e.getSource().equals(accountButton)){
				mitarbeiterNameInput.setText(mitarbeiter.getName());
				remove(tabbedPane);
				add(accountPanel, BorderLayout.CENTER);
				//revalidate();
				repaint();
			}else if (e.getSource().equals(accountSpeichernButton)){
				//Clear Error Messages
				errorName.setText("");
				mitarbeiterNameInput.setBackground(Color.WHITE);
				errorPasswort.setText("");
				altesPasswort.setBackground(Color.WHITE);
				neuesPasswort.setBackground(Color.WHITE);
				confirmPasswort.setBackground(Color.WHITE);
				
				boolean ok = true;
				if (mitarbeiterNameInput.getText().isEmpty()) {
					errorName.setText("Der Name darf nicht leer sein. Bitte geben Sie einen g\u00fcltigen Namen ein.");
					mitarbeiterNameInput.setBackground(new Color(250,240,230));
					ok = false;
				}
				
				if (!(altesPasswort.getText().isEmpty() && neuesPasswort.getText().isEmpty() && confirmPasswort.getText().isEmpty())) {
					if (!altesPasswort.getText().equals(mitarbeiter.getPasswort())) {
						errorPasswort.setText("Das alte Passwort ist falsch.");
						altesPasswort.setBackground(new Color(250, 240, 230));
						ok = false;
					} else
					if (neuesPasswort.getText().equals(altesPasswort.getText())) {
						errorPasswort.setText("Das neue Passwort darf nicht das Gleiche wie das alte Passwort sein.");
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
					try {
						if ((altesPasswort.getText().isEmpty() && neuesPasswort.getText().isEmpty() && confirmPasswort.getText().isEmpty())){
							shop.mitarbeiterBearbeiten(mitarbeiter.getId(), mitarbeiter.getPasswort(), mitarbeiterNameInput.getText(), mitarbeiter.getFunktion(), mitarbeiter.getGehalt(), mitarbeiter.getBlockiert());
							mitarbeiter.setName(mitarbeiterNameInput.getText());
						}else{
							shop.mitarbeiterBearbeiten(mitarbeiter.getId(), neuesPasswort.getText(), mitarbeiterNameInput.getText(), mitarbeiter.getFunktion(), mitarbeiter.getGehalt(), mitarbeiter.getBlockiert());
							mitarbeiter.setName(mitarbeiterNameInput.getText());
							mitarbeiter.setPasswort(neuesPasswort.getText());
						}
						accountButtonPanel.remove(accountButton);
						accountButton = new JAccountButton(mitarbeiter.getName());
						accountButton.addActionListener(new AccountListener());
						accountButtonPanel.add(accountButton, 0);
						accountButtonPanel.revalidate();
						accountButtonPanel.repaint();
					} catch (MitarbeiterExistiertNichtException e1) {
						errorName.setText("Sie existieren nicht mehr...");
					}
					remove(accountPanel);
					add(tabbedPane, BorderLayout.CENTER);
					//revalidate();
					repaint();

					//Clear Error Messages
					errorName.setText("");
					mitarbeiterNameInput.setText("");
					mitarbeiterNameInput.setBackground(Color.WHITE);
					errorPasswort.setText("");
					altesPasswort.setText("");
					altesPasswort.setBackground(Color.WHITE);
					neuesPasswort.setText("");
					neuesPasswort.setBackground(Color.WHITE);
					confirmPasswort.setText("");
					confirmPasswort.setBackground(Color.WHITE);

				}
				
			}else if (e.getSource().equals(accountAbbrechenButton)){
				remove(accountPanel);
				add(tabbedPane, BorderLayout.CENTER);
				//revalidate();
				repaint();
			}else if (e.getSource().equals(accountSpeichernButton)){
				//Clear Error Messages
				errorName.setText("");
				mitarbeiterNameInput.setBackground(Color.WHITE);
				errorPasswort.setText("");
				altesPasswort.setBackground(Color.WHITE);
				neuesPasswort.setBackground(Color.WHITE);
				confirmPasswort.setBackground(Color.WHITE);
				
				boolean ok = true;
				if (mitarbeiterNameInput.getText().isEmpty()) {
					errorName.setText("Der Name darf nicht leer sein. Bitte geben Sie einen g\u00fcltigen Namen ein.");
					mitarbeiterNameInput.setBackground(new Color(250,240,230));
					ok = false;
				}
				
				if (!(altesPasswort.getText().isEmpty() && neuesPasswort.getText().isEmpty() && confirmPasswort.getText().isEmpty())) {
					if (!altesPasswort.getText().equals(mitarbeiter.getPasswort())) {
						errorPasswort.setText("Das alte Passwort ist falsch.");
						altesPasswort.setBackground(new Color(250, 240, 230));
						ok = false;
					} else
					if (neuesPasswort.getText().equals(altesPasswort.getText())) {
						errorPasswort.setText("Das neue Passwort darf nicht das Gleiche wie das alte Passwort sein.");
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
					try {
						if ((altesPasswort.getText().isEmpty() && neuesPasswort.getText().isEmpty() && confirmPasswort.getText().isEmpty())){
							shop.mitarbeiterBearbeiten(mitarbeiter.getId(), mitarbeiter.getPasswort(), mitarbeiterNameInput.getText(), mitarbeiter.getFunktion(), mitarbeiter.getGehalt(), mitarbeiter.getBlockiert());
							mitarbeiter.setName(mitarbeiterNameInput.getText());
						}else{
							shop.mitarbeiterBearbeiten(mitarbeiter.getId(), neuesPasswort.getText(), mitarbeiterNameInput.getText(), mitarbeiter.getFunktion(), mitarbeiter.getGehalt(), mitarbeiter.getBlockiert());
							mitarbeiter.setName(mitarbeiterNameInput.getText());
							mitarbeiter.setPasswort(neuesPasswort.getText());
						}
						accountButtonPanel.remove(accountButton);
						accountButton = new JAccountButton(mitarbeiter.getName());
						accountButton.addActionListener(new AccountListener());
						accountButtonPanel.add(accountButton, 0);
						accountButtonPanel.revalidate();
						accountButtonPanel.repaint();
					} catch (MitarbeiterExistiertNichtException e1) {
						errorName.setText("Sie existieren nicht mehr...");
					}
					remove(accountPanel);
					add(tabbedPane, BorderLayout.CENTER);
					//revalidate();
					repaint();

					//Clear Error Messages
					errorName.setText("");
					mitarbeiterNameInput.setText("");
					mitarbeiterNameInput.setBackground(Color.WHITE);
					errorPasswort.setText("");
					altesPasswort.setText("");
					altesPasswort.setBackground(Color.WHITE);
					neuesPasswort.setText("");
					neuesPasswort.setBackground(Color.WHITE);
					confirmPasswort.setText("");
					confirmPasswort.setBackground(Color.WHITE);

				}
				
			}else if (e.getSource().equals(accountAbbrechenButton)){
				remove(accountPanel);
				add(tabbedPane, BorderLayout.CENTER);
				//revalidate();
				repaint();
				
				//Clear Error Messages
				errorName.setText("");
				mitarbeiterNameInput.setText("");
				mitarbeiterNameInput.setBackground(Color.WHITE);
				errorPasswort.setText("");
				altesPasswort.setText("");
				altesPasswort.setBackground(Color.WHITE);
				neuesPasswort.setText("");
				neuesPasswort.setBackground(Color.WHITE);
				confirmPasswort.setText("");
				confirmPasswort.setBackground(Color.WHITE);
			}
		}
	}
	
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
	
	class SearchListener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent ae) {
			if (ae.getSource().equals(searchButton) || ae.getSource().equals(searchField)) {
				int index = tabbedPane.getSelectedIndex();
				if(index != -1){
					switch(index){
					case 0: updateArtikelTableModel(shop.sucheArtikel(searchField.getText()));
							clearArtikelTableSelection();
							break;
					case 1: String mitarbeiterText = searchField.getText();
							RowFilter<MitarbeiterTableModel, Object> mitarbeiterRf = null;
							if(!mitarbeiterText.equals("")){
								try {
									mitarbeiterRf = RowFilter.regexFilter(mitarbeiterText);
								} catch (PatternSyntaxException e) {
									//do nothing
								}
							}
							mitarbeiterSorter.setRowFilter(mitarbeiterRf);
							break;
					case 2: String kundenText = searchField.getText();
							RowFilter<KundenTableModel, Object> kundenRf = null;
							if(!kundenText.equals("")){
								try {
									kundenRf = RowFilter.regexFilter(kundenText);
								} catch (PatternSyntaxException e) {
									//do nothing
								}
							}
							kundenSorter.setRowFilter(kundenRf);
							break;
					case 3: String logText = searchField.getText();
							RowFilter<LogTableModel, Object> logRf = null;
							if(!logText.equals("")){
								try {
									logRf = RowFilter.regexFilter(logText);
								} catch (PatternSyntaxException e) {
									//do nothing
								}
							}
							logSorter.setRowFilter(logRf);
							break;
					default:System.err.println("Interner Fehler: 'Anzahl der Tabs \u00fcberschritten'!");
					}
				}
				
			}
		}
	}
	
	class TabListener implements ChangeListener{

		@Override
		public void stateChanged(ChangeEvent e) {
			int index = tabbedPane.getSelectedIndex();
			
			switch(index){
			case 0: searchField.setEnabled(true);
					searchButton.setEnabled(true);
					
					// Alles von den anderen Tabs zurücksetzen
					clearMitarbeiterTableSelection();

					mitarbeiterFooterWrapper.setVisible(false);
					
					clearKundenTableSelection();
					kundenFooterWrapper.setVisible(false);
					break;
					
			case 1: searchField.setEnabled(true);
					searchButton.setEnabled(true);
					
					// Alles von den anderen Tabs zurücksetzen
					clearArtikelTableSelection();
					artikelFooterWrapper.setVisible(false);
					clearKundenTableSelection();
					kundenFooterWrapper.setVisible(false);
					break;
					
			case 2: searchField.setEnabled(true);
					searchButton.setEnabled(true);
					
					// Alles von den anderen Tabs zurücksetzen
					clearArtikelTableSelection();
					artikelFooterWrapper.setVisible(false);
					clearMitarbeiterTableSelection();

					mitarbeiterFooterWrapper.setVisible(false);
					break;
					
			case 3: searchField.setEnabled(true);
					searchButton.setEnabled(true);
					
					// Alles von den anderen Tabs zurücksetzen
					clearArtikelTableSelection();
					artikelFooterWrapper.setVisible(false);
					clearMitarbeiterTableSelection();

					mitarbeiterFooterWrapper.setVisible(false);
					
					clearKundenTableSelection();
					kundenFooterWrapper.setVisible(false);
					break;
					
			default:System.err.println("Zu viele Tabs!"); 
					break;
			}
			
		}
		
	}
	
	class ArtikelSelectionListener implements ListSelectionListener{

		@Override
		public void valueChanged(ListSelectionEvent e) {
			if(!e.getValueIsAdjusting()){
				artikelFooterWrapper.setVisible(false);
				artikelEinlagern.setEnabled(true);
				artikelBearbeiten.setEnabled(true);
				artikelAuslagern.setEnabled(true);
				artikelBestandshistorie.setEnabled(true);
				artikelEntfernen.setEnabled(true);
			}
		}
		
	}
	
	class MitarbeiterSelectionListener implements ListSelectionListener{

		@Override
		public void valueChanged(ListSelectionEvent e) {
			if(!e.getValueIsAdjusting()){
				mitarbeiterFooterWrapper.setVisible(false);
				mitarbeiterBearbeiten.setEnabled(true);
				mitarbeiterEntfernen.setEnabled(true);
				mitarbeiterBlockieren.setEnabled(true);
				int row = mitarbeiterTable.getSelectedRow();
				if(row != -1){
					Mitarbeiter m = mitarbeiterTableModel.getMitarbeiter(mitarbeiterTable.convertRowIndexToModel(row));
					if(m.getBlockiert()){
						mitarbeiterBlockieren.setText("Aktivieren");
					}else{
						mitarbeiterBlockieren.setText("Blockieren");
					}
				}

			}
		}
		
	}

	class KundenSelectionListener implements ListSelectionListener{

		@Override
		public void valueChanged(ListSelectionEvent e) {
			if(!e.getValueIsAdjusting()){
				kundenFooterWrapper.setVisible(false);
				kundenEntfernen.setEnabled(true);
				kundenBlockieren.setEnabled(true);
				int row = kundenTable.getSelectedRow();
				if(row != -1){
					Kunde k = kundenTableModel.getKunde(kundenTable.convertRowIndexToModel(row));
					if(k.getBlockiert()){
						kundenBlockieren.setText("Aktivieren");
					}else{
						kundenBlockieren.setText("Blockieren");
					}
				}
			}
		}
		
	}

	////////////////////// Helper Methods //////////////////////
	
	private void setTableCellAlignment(DefaultTableCellRenderer renderer, JTable table, int alignment) {
		renderer.setHorizontalAlignment(alignment);
		for (int i=0; i < table.getColumnCount();i++){
			table.setDefaultRenderer(table.getColumnClass(i),renderer);
		}
		
		table.updateUI();
	} 
	
	private void setErrorMsg(String text, JPanel p){
		errorMsg.setText("   "+text);
		if(errorMsg.getParent() == null)
			p.add(errorMsg);
		p.revalidate();
	}
	
	private void clearErrorMsg(){
		if(errorMsg.getParent() != null){
			JPanel p = (JPanel)errorMsg.getParent();
			p.remove(errorMsg);
			p.revalidate();
		}
	}
	
	/////////////////////// Window Closer ///////////////////////
	
	class WindowCloser extends WindowAdapter {
		@Override
		public void windowClosing(WindowEvent we) {
			boolean close = true;
			
			Window w = we.getWindow();
			w.setVisible(false);
			
			try {
				shop.schreibeArtikel();
				shop.schreibeMitarbeiter();
				shop.schreibeKunden();
				shop.schreibeEreignisse();
			} catch (IOException e) {
				if(JOptionPane.showConfirmDialog(null, "Fehler beim speichern der Daten!\n"
								+"Wollen Sie trotzdem die Anwendung beenden?", "Fehler beim Speichern",
								JOptionPane.YES_NO_OPTION, JOptionPane.ERROR_MESSAGE) == JOptionPane.NO_OPTION){
					close = false;
				}
//				System.err.println(e.getMessage());
			} finally {
				try {
					shop.disconnect();
				} catch (IOException e) {
					JOptionPane.showConfirmDialog(null, "IOException: " + e.getMessage(), "eShop", JOptionPane.PLAIN_MESSAGE);
				}
			}
			
			if(close){
				w.dispose();
				System.exit(0);
			}
		}
	}
}
