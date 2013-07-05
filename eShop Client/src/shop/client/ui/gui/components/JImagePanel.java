package shop.client.ui.gui.components;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

import javax.imageio.ImageIO;
import javax.swing.JPanel;

@SuppressWarnings("serial")
public class JImagePanel extends JPanel {

	private BufferedImage image = null;
	private int borderSize = 8;
	private String path;
	
	public JImagePanel(String path) {
		super();
		setImagePath(path);
		setMinimumSize(new Dimension(150 + borderSize, 150 + borderSize));
		setMaximumSize(new Dimension(150 + borderSize, 150 + borderSize));
		setPreferredSize(new Dimension(150 + borderSize, 150 + borderSize));
	}
	
	public void setImagePath(String path) {
		this.path = path;
		if (this.path != null) {
			try {                
				InputStream input = new FileInputStream(this.path);
				image = ImageIO.read(input);
			} catch (FileNotFoundException e) {
				image = null;
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		validate();
		repaint();
	}
	
	public String getImagePath() {
		return path;
	}
		
	@Override
	public void paint(Graphics g) {
		super.paint(g);
		// Laenge und Breite ermitteln
		Dimension d = this.getSize();
		int width = (int) d.getWidth();
		int height = (int) d.getHeight();
		// Rand zeichnen
		g.setColor(Color.LIGHT_GRAY);
		g.drawRect(borderSize, borderSize, Math.abs(width - borderSize - 1), Math.abs(height - 2 * borderSize - 1));
		g.setColor(Color.WHITE);
		g.fillRect(borderSize + 1, borderSize + 1, Math.abs(width - borderSize - 2), Math.abs(height - 2 * borderSize - 2));
		// Bild zeichnen
		if (image != null) {
			g.drawImage(image, borderSize + 1, borderSize + 1, Math.abs(width - borderSize - 2), image.getHeight(), null);	
		}
	}
	
}
