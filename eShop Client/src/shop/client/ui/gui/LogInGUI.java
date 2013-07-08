
package shop.client.ui.gui;

import static java.awt.GridBagConstraints.CENTER;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.Window;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.IOException;
import java.net.InetAddress;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

import shop.client.net.ShopFassade;
import shop.client.ui.gui.kundengui.KundeGUI;
import shop.client.ui.gui.mitarbeitergui.MitarbeiterGUI;
import shop.common.exceptions.KundeExistiertBereitsException;
import shop.common.exceptions.KundeExistiertNichtException;
import shop.common.exceptions.UsernameExistiertBereitsException;
import shop.common.interfaces.ShopInterface;
import shop.common.valueobjects.Kunde;
import shop.common.valueobjects.Mitarbeiter;
import shop.common.valueobjects.Person;
import shop.common.valueobjects.PersonTyp;

/**
 * Klasse die LoginGUI auf der Clientseite zur verfuegung stellt
 * 
 * @author Thummerer
 *
 */

@SuppressWarnings("serial")
public class LogInGUI extends JFrame implements ActionListener, KeyListener, MouseListener {
	
	public static final int DEFAULT_PORT = 6790;
	
	private ShopInterface shop;
	private Person p;
	private int key;
//	host / port info
	private static String host;
	private static int port;
	
	private int panBreite;
    private int ixr;
    private int ixl;
    
    
	
//	frame objekte
       	 
	private JPanel frameHeader = new JPanel() {
		
		/**
		 * Ueberschreibt die Paint Methode um das Logo in der GUI zu zeichnen
		 */
		@Override
		public void paint(Graphics g) {
		    String header = new String("eShop");
		    int fontSize = 110;
		    Font f = new Font("Verdana", Font.PLAIN, fontSize);
		    FontMetrics fm   = g.getFontMetrics(f);
		    java.awt.geom.Rectangle2D rect = fm.getStringBounds(header, g);
		    int stringWidth = (int)(rect.getWidth());
		    int stringHeight = (int)(rect.getHeight());
		    int panWidth = frameHeader.getWidth();
		    int panHeight = frameHeader.getHeight();
		    int x = (panWidth  - stringWidth)  / 2;
		    int y = (panHeight - stringHeight) / 2  + fm.getAscent();

		    g.setFont(f);
		    g.setColor(Color.BLACK);
		    g.drawString(header, x, y);
		  }
	};

	private JPanel linksPan = new JPanel();
	private JPanel mittePan = new JPanel();
	private JPanel mitteUnten = new JPanel();
	private JPanel rechtsPan = new JPanel();
	private JPanel untenPan = new JPanel();
	
//	login objekte hinzufuegen
	private JButton logInButton = new JButton("LogIn");
	private JTextField usernameField = new JTextField();
	private JTextField passwordField = new JPasswordField();
	private JLabel usernameLabel = new JLabel("Username");
	private JLabel passwordLabel = new JLabel("Passwort");
	private JLabel forgetLabel = new JLabel("<html><u>Login vergessen?</u></html>");
	
//	links objekte hinzufuegen
	private JLabel links = new JLabel("");
	
//	rechts objekte hinzufuegen
	private JLabel rechts = new JLabel("");
	private JLabel rechts2 = new JLabel("");
	private JLabel rechts3 = new JLabel("");
	private JLabel rechts4 = new JLabel("");
	private JLabel rechts5 = new JLabel("");
	private JLabel mitte5 = new JLabel("");
	private JLabel mitte6 = new JLabel("");
	private JLabel usernameError = new JLabel("<html><font color=#FF0000>Bitte Usernamen eingeben</font></html>");
	private JLabel passwordError = new JLabel("<html><font color=#FF0000>Bitte Passwort eingeben</font></html>");
	
//	unten objekte hinzufuegen
	private JLabel registerLabel = new JLabel("<html><u>Registrieren</u></html>");
	
//	register objekte
	private JLabel backLab = new JLabel("<html><u>zur\u00fcck</u></html>");
	private JLabel regLab = new JLabel("<html><u>Registrieren</u></html>");
	private JLabel changeLab = new JLabel("<html><u>\u00e4ndern</u></html>");
	private JTextField nameField = new JTextField("");
	private JTextField streetField = new JTextField("");
	private JTextField zipField = new JTextField("");
	private JTextField cityField = new JTextField("");
	private JTextField pwField = new JTextField("");
	private JTextField wpwField = new JTextField("");
		
	public LogInGUI(String host, int port) throws IOException {
		super("eShop - LogIn");

		shop = new ShopFassade(host, port);
		
		initialize();
	}
	
	private void initialize() {
		setTitle("LogIn");
		setResizable(false);
		setSize(new Dimension(700, 500));
		
		setLayout(new GridBagLayout());
		
//		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		addWindowListener(new WindowCloser());
		
		
//		int xy = ;
//		System.out.println(xy);
		
		zeichneLogin();
		
//		hinzufuegen der events
		logInButton.addActionListener(this);
		usernameField.addKeyListener(this);
		passwordField.addKeyListener(this);
		nameField.addKeyListener(this);
		forgetLabel.addMouseListener(this);
		registerLabel.addMouseListener(this);
		regLab.addMouseListener(this);
		backLab.addMouseListener(this);
		changeLab.addMouseListener(this);
		
		setLocationRelativeTo(null);
		setVisible(true);
	}
	
	/**
	 * Initialisierung der constraints Variablen fuer das GridBagLayout. Vorbelegung der Variablen mit standard Werten.
	 * @param component
	 * @param gridx
	 * @param gridy
	 * @param gridwidth
	 * @param gridheight
	 * @param fill
	 * @param weightx
	 * @param weighty
	 * @param anchor
	 * @param insets
	 * @param ipadx
	 * @param ipady
	 */
	private void addGB(Component component, int gridx, int gridy, int gridwidth, int gridheight,
            int fill, double weightx, double weighty, int anchor, Insets insets,
            int ipadx, int ipady) {
        GridBagConstraints constraints = new GridBagConstraints();
        constraints.gridx = gridx;
        constraints.gridy = gridy;
        constraints.gridwidth = gridwidth;
        constraints.gridheight = gridheight;
        constraints.fill = fill;
        constraints.weightx = weightx;
        constraints.weighty = weighty;
        constraints.anchor = anchor;
        constraints.insets = insets;
        constraints.ipadx = ipadx;
        constraints.ipady = ipady;
        add(component, constraints);
    }
	
	private void addGB(Component component, int gridx, int gridy, int width, int ipadx, int ipady) {
        addGB(component, gridx, gridy, width, 1, GridBagConstraints.BOTH, 0.0, 0.0, CENTER, new Insets(5, 5, 5, 5), ipadx, ipady);
    }
	
	/**
	 * Zeichnen der betroffenen Panels bei auswahl von loginVergessen
	 */
	private void loginVergessen(){
		pwField.setBackground(Color.WHITE);
		wpwField.setBackground(Color.WHITE);
		nameField.setBackground(Color.WHITE);
		streetField.setBackground(Color.WHITE);
		zipField.setBackground(Color.WHITE);
		cityField.setBackground(Color.WHITE);
		Kunde k = null;
		if (nameField.getText().equals(""))
			nameField.setBackground(new Color(250,240,230));
		if (streetField.getText().equals(""))
			streetField.setBackground(new Color(250,240,230));
		if (zipField.getText().equals(""))
			zipField.setBackground(new Color(250,240,230));
		if (cityField.getText().equals(""))
			cityField.setBackground(new Color(250,240,230));
		if (!nameField.getText().equals("") && !streetField.getText().equals("") && !zipField.getText().equals("") &&
				!cityField.getText().equals("")) {
			String name = nameField.getText();
			String strasse = streetField.getText();
			String zipStr = zipField.getText();
			String stadt = cityField.getText();
		
			String passwort = pwField.getText();
			String passwortWiederholung = wpwField.getText();
		
			try{
				int zip = Integer.parseInt(zipStr);
				k = shop.loginVergessen(name, strasse, zip, stadt);
			} catch (NumberFormatException nfe){
				//TODO
				System.err.println("Bitte geben Sie für die Postleitzahl nur Ziffern ein!");
				zipField.setBackground(new Color(250,240,230));
			}
		
			if(k != null){
				usernameField.setText(k.getUsername());
				if(passwort.equals(passwortWiederholung)){
					try {
						shop.kundenBearbeiten(k.getId(), pwField.getText(), k.getName(), k.getStrasse(), k.getPlz(), k.getWohnort(), k.getBlockiert());
					} catch (KundeExistiertNichtException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					zeichneLogin();
				}else{
					//TODO
					System.err.println("Das Passwort und die Passwort Wiederholung m/u00fcssen gleich sein!");
					pwField.setBackground(new Color(250,240,230));
					wpwField.setBackground(new Color(250,240,230));
				}
			}else{
				JOptionPane.showConfirmDialog(null, "Kein Account mit diesen Angaben gefunden!", "Error", JOptionPane.PLAIN_MESSAGE, JOptionPane.ERROR_MESSAGE);
				nameField.setBackground(new Color(250,240,230));
				streetField.setBackground(new Color(250,240,230));
				zipField.setBackground(new Color(250,240,230));
				cityField.setBackground(new Color(250,240,230));
				pwField.setBackground(new Color(250,240,230));
				wpwField.setBackground(new Color(250,240,230));
			}
		}
		
	}
	
	
	/**
	 * Ueberpruefung der Eingaben bei Registrierung. Bei korrekter Eingabe erfolgt ein aufruf der zum Registrieren noetigen Methoden.
	 */
	private void registrierung() {
		usernameField.setBackground(Color.WHITE);
		pwField.setBackground(Color.WHITE);
		wpwField.setBackground(Color.WHITE);
		nameField.setBackground(Color.WHITE);
		streetField.setBackground(Color.WHITE);
		zipField.setBackground(Color.WHITE);
		cityField.setBackground(Color.WHITE);
//		ueberpruefung der Eingaben des Nutzers
		if (usernameField.getText().equals(""))
			usernameField.setBackground(new Color(250,240,230));
		if (pwField.getText().equals(""))
			pwField.setBackground(new Color(250,240,230));
		if (wpwField.getText().equals(""))
			wpwField.setBackground(new Color(250,240,230));
		if (nameField.getText().equals(""))
			nameField.setBackground(new Color(250,240,230));
		if (streetField.getText().equals(""))
			streetField.setBackground(new Color(250,240,230));
		if (zipField.getText().equals(""))
			zipField.setBackground(new Color(250,240,230));
		if (cityField.getText().equals(""))
			cityField.setBackground(new Color(250,240,230));
//		kundenHinzu();
		if (!usernameField.getText().equals("") && !pwField.getText().equals("") && !wpwField.getText().equals("") &&
				!nameField.getText().equals("") && !streetField.getText().equals("") && !zipField.getText().equals("") &&
				!cityField.getText().equals("")) {
			String strasse = streetField.getText();
			String name = nameField.getText();
//			String wPasswort = wpwField.getText();
			String passwort = pwField.getText();
			String strPlz = zipField.getText();
			int plz = Integer.parseInt(strPlz);
			String wohnort = cityField.getText();
			String username = usernameField.getText();
			try {
				shop.fuegeKundenHinzu(username, passwort, name, strasse, plz, wohnort);
				System.out.println("Kunde wurde hinzugefuegt!");
				zeichneLogin();
				try {
					shop.schreibeKunden();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			} catch (KundeExistiertBereitsException e) {
				System.err.println(e.getMessage());
			} catch (UsernameExistiertBereitsException e) {
				System.err.println(e.getMessage());
			}
		} else {
			nameField.setText("eingaben pruefen!");
		}
	}
	
	private void zeichneIO() {
		rechtsPan.add(rechts);
		rechtsPan.add(rechts2);
		rechtsPan.add(rechts3);
		rechtsPan.add(rechts4);
		rechtsPan.add(rechts5);
	}
	
	private void zeichneError() {
		rechtsPan.add(rechts);
		rechtsPan.add(usernameError);
		rechtsPan.add(rechts3);
		rechtsPan.add(passwordError);
		rechtsPan.add(rechts5);
	}
	
	private void zeichneErrorPw() {
		rechtsPan.add(rechts);
		rechtsPan.add(rechts2);
		rechtsPan.add(rechts3);
		rechtsPan.add(passwordError);
		rechtsPan.add(rechts5);
	}
	
	private void zeichneErrorUn() {
		rechtsPan.add(rechts);
		rechtsPan.add(usernameError);
		rechtsPan.add(rechts3);
		rechtsPan.add(rechts4);
		rechtsPan.add(rechts5);
	}
	
	/**
	 * Zeichnen der betroffenen Panels fuer den Login vorgang.
	 */
	private void zeichneLogin() {
//		berechnung von ipadx in abh‰ngigkeit zum Label
		panBreite = 1;
		resizePan();
		usernameField.setBackground(Color.WHITE);
		pwField.setBackground(Color.WHITE);
		wpwField.setBackground(Color.WHITE);
		nameField.setBackground(Color.WHITE);
		streetField.setBackground(Color.WHITE);
		zipField.setBackground(Color.WHITE);
		cityField.setBackground(Color.WHITE);
		
//		hinzufuegen der objekte zum frame
		addGB(frameHeader, 1, 1, 3, 400, 150);
		addGB(linksPan, 1, 2, 1, 150 - ixl, 0);
		addGB(mittePan, 2, 2, 1, 0, 0);
		addGB(rechtsPan, 3, 2, 1, 150 - ixr, 0);
		addGB(untenPan, 1, 3, 3, 300, 0);
		
		linksPan.removeAll();
		mittePan.removeAll();
		rechtsPan.removeAll();
		untenPan.removeAll();
		
//		nur zum testen
		linksPan.add(links);
		linksPan.repaint();
		rechtsPan.setLayout(new GridLayout(5, 1));
		zeichneIO();
		rechtsPan.repaint();
		untenPan.add(registerLabel);
		untenPan.repaint();
		
//		fuege mittePan objekte hinzu
		mittePan.setLayout(new GridLayout(5, 1, 0, 2));
		mittePan.add(usernameLabel);
		mittePan.add(usernameField);
		usernameField.setEditable(true);
		usernameField.requestFocus();
		mittePan.add(passwordLabel);
		mittePan.add(passwordField);
		passwordField.setText("");
		mittePan.add(mitteUnten);
		mittePan.repaint();
		
//		fuege mitteUnten objekte hinzu
		mitteUnten.setLayout(new GridLayout(1, 2, 10, 0));
		mitteUnten.add(forgetLabel);
		mitteUnten.add(logInButton);
		
		linksPan.validate();
		mittePan.validate();
		rechtsPan.validate();
		untenPan.validate();
	}
	
	private void zeichneForget() {
		
		panBreite = 2;
		resizePan();
		usernameField.setBackground(Color.WHITE);
		passwordField.setBackground(Color.WHITE);
		usernameField.setText("");
		pwField.setText("");
		wpwField.setText("");
		nameField.setText("");
		streetField.setText("");
		zipField.setText("");
		cityField.setText("");
		
		JLabel nameLab = new JLabel("Name");
		JLabel streetLab = new JLabel("Strasse/HNr.");
		JLabel zipLab = new JLabel("Postleitzahl/zip");
		JLabel cityLab = new JLabel("Stadt");
		JLabel wpwLab = new JLabel("Passwort wiederholen");
				
		addGB(linksPan, 1, 2, 1, 100 - ixl, 20);
		addGB(mittePan, 2, 2, 1, 100, 20);
		addGB(rechtsPan, 3, 2, 1, 100 - ixr, 20);
		
		linksPan.removeAll();
		linksPan.setLayout(new GridLayout(6, 1));
		linksPan.add(nameLab);
		linksPan.add(nameField);
		nameField.requestFocus();
//		linksPan.add(gebDatLab);
//		linksPan.add(gebDatField);
		linksPan.add(streetLab);
		linksPan.add(streetField);
		linksPan.add(zipLab);
		linksPan.add(zipField);
		linksPan.repaint();
		linksPan.validate();
		
		mittePan.removeAll();
		mittePan.setLayout(new GridLayout(6, 1));
//		mittePan.add(zipLab);
//		mittePan.add(zipField);
		mittePan.add(cityLab);
		mittePan.add(cityField);
		mittePan.add(usernameLabel);
		mittePan.add(usernameField);
		mittePan.add(new JLabel (""));
		mittePan.add(new JLabel (""));
		usernameField.setEditable(false);
		mittePan.repaint();
		mittePan.validate();
		
		rechtsPan.removeAll();
		rechtsPan.setLayout(new GridLayout(6, 1));
//		rechtsPan.add(opwLab);
//		rechtsPan.add(opwField);
		rechtsPan.add(passwordLabel);
		rechtsPan.add(pwField);
//		pwField.disable();
		rechtsPan.add(wpwLab);
		rechtsPan.add(wpwField);
//		wpwField.disable();
		rechtsPan.add(rechts3);
		rechtsPan.add(rechts4);
		rechtsPan.repaint();
		rechtsPan.validate();
		
		untenPan.removeAll();
		untenPan.add(backLab);
		untenPan.add(changeLab);
		untenPan.repaint();
		untenPan.validate();
		
	}
	
	private void resizePan() {
		switch (panBreite) {
		case 1:
			ixr = (int) rechts2.getPreferredSize().getWidth();
			ixl = (int) links.getPreferredSize().getWidth();
			break;
		case 2:
			ixr = (int) usernameField.getPreferredSize().getWidth();
			ixl = (int) usernameField.getPreferredSize().getWidth();
			break;
		}
	}

	private void zeichneRegister() {
		
		panBreite = 2;
		resizePan();
		usernameField.setBackground(Color.WHITE);
		passwordField.setBackground(Color.WHITE);
		usernameField.setText("");
		pwField.setText("");
		wpwField.setText("");
		nameField.setText("");
		streetField.setText("");
		zipField.setText("");
		cityField.setText("");		
//		pwField.enable();
//		wpwField.enable();
		
		JLabel nameLab = new JLabel("Name");
		JLabel streetLab = new JLabel("Strasse/HNr.");
		JLabel zipLab = new JLabel("Postleitzahl/zip");
		JLabel cityLab = new JLabel("Stadt");
		JLabel wpwLab = new JLabel("Passwort wiederholen");
		
		addGB(linksPan, 1, 2, 1, 100 - ixl, 20);
		addGB(mittePan, 2, 2, 1, 100, 20);
		addGB(rechtsPan, 3, 2, 1, 100 - ixr, 20);
		
		linksPan.removeAll();
		linksPan.setLayout(new GridLayout(6, 1));
		linksPan.add(nameLab);
		linksPan.add(nameField);
		nameField.requestFocus();
		linksPan.add(streetLab);
		linksPan.add(streetField);
		linksPan.add(zipLab);
		linksPan.add(zipField);
		linksPan.repaint();
		linksPan.validate();
		
		mittePan.removeAll();
		mittePan.setLayout(new GridLayout(6, 1));
//		mittePan.add(zipLab);
//		mittePan.add(zipField);
		mittePan.add(cityLab);
		mittePan.add(cityField);
		mittePan.add(usernameLabel);
		mittePan.add(usernameField);
		mittePan.add(mitte5);
		mittePan.add(mitte6);
		mittePan.repaint();
		mittePan.validate();
		
		rechtsPan.removeAll();
		rechtsPan.setLayout(new GridLayout(6, 1));
		rechtsPan.add(passwordLabel);
		rechtsPan.add(pwField);
		rechtsPan.add(wpwLab);
		rechtsPan.add(wpwField);
		rechtsPan.add(rechts3);
		rechtsPan.add(rechts4);
		rechtsPan.repaint();
		rechtsPan.validate();
		
		untenPan.removeAll();
		untenPan.add(backLab);
		untenPan.add(regLab);
		untenPan.repaint();
		untenPan.validate();
		
	}
	
	/**
	 * Aufruf der Methode fuer den Login und anschlieﬂendes oeffnen der jeweiligen GUI
	 * abh‰ngig davon ob sich ein Mitarbeiter oder Kunde einloggt.
	 */
	private void anmeldeVorgang() {
		try {
			logIn();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if (p != null) {
			if (p.getPersonTyp().equals(PersonTyp.Mitarbeiter)) {
				try {
					if(!p.getBlockiert()){
						this.setVisible(false);
						new MitarbeiterGUI((Mitarbeiter) p, shop, host, port);
					}
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			} else {
				try {
					if(!p.getBlockiert()){
						this.setVisible(false);
						new KundeGUI(shop, (Kunde) p, host, port);
					}
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
	}
	
	/**
	 * Pruefung der Eingaben des Login Dialoges mit entsprechenden reaktionen auf falsche/keine Eingaben.
	 * @throws IOException
	 */
	private void logIn() throws IOException {
		if (!usernameField.getText().equals("")) {
			String username = usernameField.getText();
			if (!passwordField.getText().equals("")) {
				String password = passwordField.getText();
				p = shop.pruefeLogin(username, password);
				if (p == null) {
					passwordError.setText("<html><font color=#FF0000>Bitte richtiges Passwort eingeben</font></html>");
					usernameError.setText("<html><font color=#FF0000>Bitte richtiger Usernamen eingeben</font></html>");
					passwordField.setBackground(new Color(250,240,230));
					usernameField.setBackground(new Color(250,240,230));
					usernameField.requestFocus();
					rechtsPan.removeAll();
					zeichneError();
					rechtsPan.repaint();
					rechtsPan.validate();
				}else if(p.getBlockiert()){
					JOptionPane.showMessageDialog(null, "Sie sind gesperrt!", "Blockiert", JOptionPane.ERROR_MESSAGE);
				}
			} else {
				passwordError.setText("<html><font color=#FF0000>Bitte Passwort eingeben</font></html>");
				passwordField.setBackground(new Color(250,240,230));
				passwordField.requestFocus();
				usernameField.setBackground(Color.WHITE);
				rechtsPan.removeAll();
				zeichneErrorPw();
				rechtsPan.repaint();
				rechtsPan.validate();
				System.out.println("epw");
			}
		} else {
			if (passwordField.getText().equals("")) {
				passwordError.setText("<html><font color=#FF0000>Bitte Passwort eingeben</font></html>");
				usernameError.setText("<html><font color=#FF0000>Bitte Usernamen eingeben</font></html>");
				passwordField.setBackground(new Color(250,240,230));
				usernameField.setBackground(new Color(250,240,230));
				usernameField.requestFocus();
				rechtsPan.removeAll();
				zeichneError();
				rechtsPan.repaint();
				rechtsPan.validate();
				System.out.println("e");
			} else {
				usernameError.setText("<html><font color=#FF0000>Bitte Usernamen eingeben</font></html>");
				usernameField.setBackground(new Color(250,240,230));
				usernameField.requestFocus();
				passwordField.setBackground(Color.WHITE);
				rechtsPan.removeAll();
				zeichneErrorUn();
				rechtsPan.repaint();
				rechtsPan.validate();
				System.out.println("eun");
			}
		}
	}
	
	@Override
	public void mouseClicked(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseEntered(MouseEvent me) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseExited(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mousePressed(MouseEvent mp) {
		// TODO Auto-generated method stub

	}

	/**
	 * Aufruf der entsprechenden Methoden wenn Label in der LoginGUI angeklickt werden.
	 */
	@Override
	public void mouseReleased(MouseEvent mr) {
		// TODO Auto-generated method stub
		Component comp = mr.getComponent();
		if (comp.equals(forgetLabel)) {
			zeichneForget();
		}
		if (comp.equals(registerLabel)) {
			System.out.println(registerLabel);
			zeichneRegister();
		}
		if (comp.equals(regLab)) {
			registrierung();
		}
		if (comp.equals(backLab)) {
			zeichneLogin();
		}
		if (comp.equals(changeLab)) {
			loginVergessen();		}
	}
	
	public void keyPressed(KeyEvent kp) {
		key = kp.getKeyCode();
	}

	public void keyReleased(KeyEvent arg0) {
		// TODO Auto-generated method stub
		
	}
	
	public void keyTyped (KeyEvent ke) {
		if (ke.getSource() == nameField) {
			if (nameField.getText().equals("eingaben pruefen!")) {
				nameField.setText("");
			}
		}
		if (ke.getSource() == usernameField) {
			if (key == KeyEvent.VK_ENTER) {
				passwordField.requestFocus();
			}
		} if (ke.getSource() == passwordField) {
			if (key == KeyEvent.VK_ENTER) {
				anmeldeVorgang();
			}
		}
	}
	
	public void actionPerformed(ActionEvent b) {
		anmeldeVorgang();		
	}
	
	/**
	 * ueberschreiben der Methode WindowClosing um bei schlieﬂen der LoginGUI die Verbindung zum server trennt.
	 * 
	 * @author Thummerer
	 *
	 */
	class WindowCloser extends WindowAdapter {
		@Override
		public void windowClosing(WindowEvent we) {
			
			Window w = we.getWindow();
			w.setVisible(false);
			
			try {
				shop.disconnect();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
				w.dispose();
				System.exit(0);
		}
	}
	
	/**
	 * Die main-Methode...
	 */
	public static void main(String[] args) {
		port = 0;
		host = null;
		InetAddress ia = null;

		// ---
		// Hier werden die main-Parameter geprueft:
		// ---

		// Host- und Port-Argument einlesen, wenn angegeben
		if (args.length > 2) {
			System.out.println("Aufruf: java <Klassenname> [<hostname> [<port>]]");
			System.exit(0);
		}
		if (args.length == 0) {
			try {
				ia = InetAddress.getLocalHost();
			} catch (Exception e) {
				System.out.println("XXX InetAdress-Fehler: " + e);
				System.exit(0);
			}
			host = ia.getHostName(); // host ist lokale Maschine
			port = DEFAULT_PORT;
		}
		if (args.length == 1) {
			port = DEFAULT_PORT;
			host = args[0];
		}

		if (args.length == 2) {
			host = args[0];
			try {
				port = Integer.parseInt(args[1]);
			} catch (NumberFormatException e) {
				System.out
						.println("Aufruf: java BibClientGUI [<hostname> [<port>]]");
				System.exit(0);
			}
		}

		try {
			new LogInGUI(host, port);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
}
