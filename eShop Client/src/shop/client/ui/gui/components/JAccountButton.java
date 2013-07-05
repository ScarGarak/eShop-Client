
package shop.client.ui.gui.components;

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
public class JAccountButton extends JButton {
	
	private BufferedImage image = null;
	private String name = null;
	
	public JAccountButton(String name) {
		super();
		this.name = name;
		try {                
			InputStream input = new FileInputStream("images/account.png");
			image = ImageIO.read(input);
		} catch (IOException e) {
			e.printStackTrace();
		}
		setMinimumSize(new Dimension(100, 70));
		setPreferredSize(new Dimension(100, 70));
		setMaximumSize(new Dimension(100, 70));
		setOpaque(false);
	}
	
	@Override
	public void setText(String text) {
		this.name = text;
		validate();
		repaint();
	}
	
	@Override
	public void paint(Graphics g) {
		int width = (int) this.getSize().getWidth();
		int height = (int) this.getSize().getHeight();
		Font myFont = new Font(Font.SANS_SERIF, Font.PLAIN, 13);
		FontMetrics fm = g.getFontMetrics();
		int textWidth = fm.stringWidth(name);
		int textHeight = fm.getHeight();
		g.drawImage(image, width / 2 - image.getWidth() / 2, height / 2 - (image.getHeight() + textHeight) / 2, null);
		g.setFont(myFont);
		g.drawString(name, width / 2 - textWidth / 2, height / 2 + (image.getHeight() + textHeight) / 2);
	}

}
