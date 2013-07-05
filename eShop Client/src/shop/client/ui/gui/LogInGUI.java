/**
 * 
 */
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
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.IOException;
import java.net.InetAddress;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

import shop.client.net.ShopFassade;
import shop.client.ui.gui.kundengui.KundeGUI;
import shop.common.exceptions.KundeExistiertBereitsException;
import shop.common.exceptions.UsernameExistiertBereitsException;
import shop.common.interfaces.ShopInterface;
import shop.common.valueobjects.Kunde;
import shop.common.valueobjects.Person;

/**
 * @author Mort
 *
 */

@SuppressWarnings("serial")
public class LogInGUI extends JFrame implements ActionListener, KeyListener, MouseListener {
	
	public static final int DEFAULT_PORT = 6789;
	
	private ShopInterface shop;
	private Person person;
	private int key;
	
//	host/port info
	private static String host;
	private static int port;
	
	//	GridBagLayout Variablen
	private int gridx, gridy, gridwidth, gridheight, fill, anchor, ipadx, ipady;
    private double weightx, weighty;
    private Insets insets;
    
    private int panBreite;
    private int ixr;
    private int ixl;
	
//	frame objekte
       	 
	private JPanel frameHeader = new JPanel() {
		@Override
		public void paint(Graphics g) {
//		    Dimension d = this.getPreferredSize();
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
	
//	login objekte hinzufügen
	private JButton logInButton = new JButton("LogIn");
	private JTextField usernameField = new JTextField();
	private JTextField passwordField = new JPasswordField();
	private JLabel usernameLabel = new JLabel("Username");
	private JLabel passwordLabel = new JLabel("Passwort");
	private JLabel forgetLabel = new JLabel("<html><u>Login vergessen?</u></html>");
	
//	links objekte hinzufügen
	private JLabel links = new JLabel("");
	
//	rechts objekte hinzufügen
	private JLabel rechts = new JLabel("");
	private JLabel rechts2 = new JLabel("");
	private JLabel rechts3 = new JLabel("");
	private JLabel rechts4 = new JLabel("");
	private JLabel rechts5 = new JLabel("");
	private JLabel mitte5 = new JLabel("");
	private JLabel mitte6 = new JLabel("");
	private JLabel usernameError = new JLabel("<html><font color=#FF0000>Bitte Usernamen eingeben</font></html>");
	private JLabel passwordError = new JLabel("<html><font color=#FF0000>Bitte Passwort eingeben</font></html>");
	
//	unten objekte hinzufügen
	private JLabel registerLabel = new JLabel("<html><u>Registrieren</u></html>");
	
//	register objekte
	private JLabel backLab = new JLabel("<html><u>zur\u00fcck</u></html>");
	private JLabel regLab = new JLabel("<html><u>Registrieren</u></html>");
	private JLabel changeLab = new JLabel("<html><u>\u00e4ndern</u></html>");
	private JTextField gebDatField = new JTextField("");
	private JTextField nameField = new JTextField("");
	private JTextField streetField = new JTextField("");
	private JTextField zipField = new JTextField("");
	private JTextField cityField = new JTextField("");
	private JTextField pwField = new JTextField("");
	private JTextField wpwField = new JTextField("");
	
//	forget objekte
	private JTextField opwField = new JTextField();
	
	public LogInGUI(String host, int port) throws IOException {
		super("eShop - LogIn");

		// die Bib-Verwaltung erledigt die Aufgaben, 
		// die nichts mit Ein-/Ausgabe zu tun haben
		shop = new ShopFassade(host, port);
		
		initialize();
	}
	
	private void initialize() {
		setTitle("LogIn");
		setResizable(false);
		setSize(new Dimension(700, 500));
		
		setLayout(new GridBagLayout());
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		
//		int xy = ;
//		System.out.println(xy);
		
		zeichneLogin();
		
//		hinzufügen der events
		logInButton.addActionListener(this);
		usernameField.addKeyListener(this);
		passwordField.addKeyListener(this);
		nameField.addKeyListener(this);
		forgetLabel.addMouseListener(this);
		registerLabel.addMouseListener(this);
		regLab.addMouseListener(this);
		backLab.addMouseListener(this);
		
		setLocationRelativeTo(null);
		setVisible(true);
	}
	
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
	
	private void registrierung() {
		usernameField.setBackground(Color.WHITE);
		pwField.setBackground(Color.WHITE);
		wpwField.setBackground(Color.WHITE);
		nameField.setBackground(Color.WHITE);
		streetField.setBackground(Color.WHITE);
		zipField.setBackground(Color.WHITE);
		cityField.setBackground(Color.WHITE);
//		überprüfung der Eingaben des Nutzers
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
			String wPasswort = wpwField.getText();
			String passwort = pwField.getText();
			String strPlz = zipField.getText();
			int plz = Integer.parseInt(strPlz);
			String wohnort = cityField.getText();
			String username = usernameField.getText();
			try {
				shop.fuegeKundenHinzu(username, passwort, name, strasse, plz, wohnort);
				System.out.println("Kunde wurde hinzugefügt!");
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
			nameField.setText("eingaben prüfen!");
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
	
	private void zeichneLogin() {
//		berechnung von ipadx in abhängigkeit zum Label
		panBreite = 1;
		resizePan();
		
//		hinzufügen der objekte zum frame
		addGB(frameHeader, gridx = 1, gridy = 1, gridwidth = 3, ipadx = 400, ipady = 150);
		addGB(linksPan, gridx = 1, gridy = 2, gridwidth = 1, ipadx = 150 - ixl, ipady = 0);
		addGB(mittePan, gridx = 2, gridy = 2, gridwidth = 1, ipadx = 0, ipady = 0);
		addGB(rechtsPan, gridx = 3, gridy = 2, gridwidth = 1, ipadx = 150 - ixr, ipady = 0);
		addGB(untenPan, gridx = 1, gridy = 3, gridwidth = 3, ipadx = 300, ipady = 0);
		
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
		
//		füge mittePan objekte hinzu
		mittePan.setLayout(new GridLayout(5, 1, 0, 2));
		mittePan.add(usernameLabel);
		mittePan.add(usernameField);
		usernameField.enable();
		usernameField.requestFocus();
		mittePan.add(passwordLabel);
		mittePan.add(passwordField);
		passwordField.setText("");
		mittePan.add(mitteUnten);
		mittePan.repaint();
		
//		füge mitteUnten objekte hinzu
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
		usernameField.setText("");
		pwField.setText("");
		wpwField.setText("");
		nameField.setText("");
		streetField.setText("");
		zipField.setText("");
		cityField.setText("");
		
		JLabel nameLab = new JLabel("Name");
		JLabel gebDatLab = new JLabel("Geburtsdatum");
		JLabel streetLab = new JLabel("Strasse/HNr.");
		JLabel zipLab = new JLabel("Postleitzahl/zip");
		JLabel cityLab = new JLabel("Stadt");
		JLabel wpwLab = new JLabel("Passwort wiederholen");
		
		JLabel opwLab =new JLabel("altes Passwort");
		
		addGB(linksPan, gridx = 1, gridy = 2, gridwidth = 1, ipadx = 100 - ixl, ipady = 20);
		addGB(mittePan, gridx = 2, gridy = 2, gridwidth = 1, ipadx = 100, ipady = 20);
		addGB(rechtsPan, gridx = 3, gridy = 2, gridwidth = 1, ipadx = 100 - ixr, ipady = 20);
		
		linksPan.removeAll();
		linksPan.setLayout(new GridLayout(6, 1));
		linksPan.add(nameLab);
		linksPan.add(nameField);
		nameField.requestFocus();
		linksPan.add(gebDatLab);
		linksPan.add(gebDatField);
		linksPan.add(streetLab);
		linksPan.add(streetField);
		linksPan.repaint();
		linksPan.validate();
		
		mittePan.removeAll();
		mittePan.setLayout(new GridLayout(6, 1));
		mittePan.add(zipLab);
		mittePan.add(zipField);
		mittePan.add(cityLab);
		mittePan.add(cityField);
		mittePan.add(usernameLabel);
		mittePan.add(usernameField);
		usernameField.disable();
		mittePan.repaint();
		mittePan.validate();
		
		rechtsPan.removeAll();
		rechtsPan.setLayout(new GridLayout(6, 1));
//		rechtsPan.add(opwLab);
//		rechtsPan.add(opwField);
		rechtsPan.add(passwordLabel);
		rechtsPan.add(pwField);
		pwField.disable();
		rechtsPan.add(wpwLab);
		rechtsPan.add(wpwField);
		wpwField.disable();
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
		usernameField.setText("");
		pwField.setText("");
		wpwField.setText("");
		nameField.setText("");
		streetField.setText("");
		zipField.setText("");
		cityField.setText("");		
		pwField.enable();
		wpwField.enable();
		
		JLabel nameLab = new JLabel("Name");
		JLabel gebDatLab = new JLabel("Geburtsdatum");
		JLabel streetLab = new JLabel("Strasse/HNr.");
		JLabel zipLab = new JLabel("Postleitzahl/zip");
		JLabel cityLab = new JLabel("Stadt");
		JLabel wpwLab = new JLabel("Passwort wiederholen");
		
		addGB(linksPan, gridx = 1, gridy = 2, gridwidth = 1, ipadx = 100 - ixl, ipady = 20);
		addGB(mittePan, gridx = 2, gridy = 2, gridwidth = 1, ipadx = 100, ipady = 20);
		addGB(rechtsPan, gridx = 3, gridy = 2, gridwidth = 1, ipadx = 100 - ixr, ipady = 20);
		
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
	
	private void anmeldeVorgang() {
		try {
			logIn();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
//		if (p != null) {
//			if (p.getPersonTyp().equals(PersonTyp.Mitarbeiter)) {
//				try {
////					dispose();
//					this.setVisible(false);
//					new MitarbeiterGUI((Mitarbeiter)p, shop);
//				} catch (IOException e) {
//					// TODO Auto-generated catch block
//					e.printStackTrace();
//				}
//		} else
				try {
//					dispose();
					this.setVisible(false);
					new KundeGUI(shop, (Kunde) person, host, port);
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
//		}
	}
	
	private void logIn() throws IOException {
		if (!usernameField.getText().equals("")) {
			String username = usernameField.getText();
			if (!passwordField.getText().equals("")) {
				String password = passwordField.getText();
				person = shop.pruefeLogin(username, password);
				if (person == null) {
//					pop up mit Hinweis aus falsche eingabe
//					einfärben der Textfelder, Fokus auf usernameField
//					tf.setText
				}
			} else {
				passwordField.setBackground(new Color(250,240,230));
				passwordField.requestFocus();
				usernameField.setBackground(Color.WHITE);
//				usernameField.repaint();
//				usernameField.validate();
				rechtsPan.removeAll();
				zeichneErrorPw();
				rechtsPan.repaint();
				rechtsPan.validate();
				System.out.println("epw");
			}
		} else {
			if (passwordField.getText().equals("")) {
				passwordField.setBackground(new Color(250,240,230));
				usernameField.setBackground(new Color(250,240,230));
				usernameField.requestFocus();
				rechtsPan.removeAll();
				zeichneError();
				rechtsPan.repaint();
				rechtsPan.validate();
				System.out.println("e");
			} else {
				usernameField.setBackground(new Color(250,240,230));
				usernameField.requestFocus();
				passwordField.setBackground(Color.WHITE);
//				passwordField.repaint();
//				passwordField.validate();
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
	}
	
	public void keyPressed(KeyEvent kp) {
		key = kp.getKeyCode();
//		System.out.println("key in pressed: " + key);
//		System.out.println(usernameField.getWidth());
//		System.out.println(usernameField.getHeight());
	}

	public void keyReleased(KeyEvent arg0) {
		// TODO Auto-generated method stub
		
	}
	
	public void keyTyped (KeyEvent ke) {
//		if (usernameField.getText().equals("Usernamen eingeben!")) {
//			usernameField.setText("");
//		}
		if (ke.getSource() == nameField) {
			System.out.println("vor");
			if (nameField.getText().equals("eingaben prüfen!")) {
				System.out.println("nach");
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
	 * Die main-Methode...
	 */
	public static void main(String[] args) {
		port = 0;
		host = null;
		InetAddress ia = null;

		// ---
		// Hier werden die main-Parameter geprüft:
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
