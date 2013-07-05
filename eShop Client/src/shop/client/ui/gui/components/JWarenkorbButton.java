package shop.client.ui.gui.components;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

import javax.imageio.ImageIO;
import javax.swing.JButton;

@SuppressWarnings("serial")
public class JWarenkorbButton extends JButton {

	private BufferedImage image = null;
	private int artikelanzahl = 0;
	
	public JWarenkorbButton(int artikelanzahl) {
		super();
		this.artikelanzahl = artikelanzahl;
		try {                
			InputStream input = new FileInputStream("images/warenkorb.png");
			image = ImageIO.read(input);
		} catch (IOException e) {
			e.printStackTrace();
		}
		setMinimumSize(new Dimension(80, 60));
		setPreferredSize(new Dimension(80, 60));
		setMaximumSize(new Dimension(80, 60));
		setOpaque(false);
	}
	
	public void setArtikelanzahl(int artikelanzahl) {
		this.artikelanzahl = artikelanzahl;
		validate();
		repaint();
	}
	
	public int getArtikelanzahl() {
		return artikelanzahl;
	}
	
	@Override
	public void paint(Graphics g) {
		int width = (int) this.getSize().getWidth();
		int height = (int) this.getSize().getHeight();
		Font myFont = new Font(Font.SANS_SERIF, Font.BOLD, 18);
		FontMetrics fm = g.getFontMetrics();
		int textWidth = fm.stringWidth(String.valueOf(artikelanzahl));
		int textHeight = fm.getHeight();
		g.drawImage(image, width / 2 - (image.getWidth() + textWidth) / 2, height / 2 - image.getHeight() / 2, null);
		textWidth = fm.stringWidth("Warenkorb");
		g.drawString("Warenkorb", (width - textWidth) / 2, height);
		textWidth = fm.stringWidth(String.valueOf(artikelanzahl));
		g.setColor(Color.ORANGE);
		g.setFont(myFont);
		g.drawString(String.valueOf(artikelanzahl), (width / 2 - (image.getWidth() + textWidth) / 2) + image.getWidth(), height / 2 + textHeight / 2);			
	}
	
}
