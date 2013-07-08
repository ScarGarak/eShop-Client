package shop.client.ui.gui.mitarbeitergui.table;

import java.awt.Color;
import java.awt.Component;
import java.util.Currency;
import java.util.Locale;

import javax.swing.JLabel;
import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;

/**
 * Diese Klasse implementiert den DefaultTableCellRenderer der Artikeltabelle.
 * @see DefaultTableCellRenderer
 * @author Migliosi Angelo
 *
 */
@SuppressWarnings("serial")
public class ArtikelTableCellRenderer extends DefaultTableCellRenderer{
	
	/**
	 * Konstruktor von ArtikelTableCellRenderer
	 * @param table
	 */
	public ArtikelTableCellRenderer(JTable table) {
		DefaultTableCellRenderer renderer = (DefaultTableCellRenderer) table.getTableHeader().getDefaultRenderer();
        renderer.setHorizontalAlignment(JLabel.LEFT);
    }
	
	/**
	 * Diese Methode veraendert die Hintergrund- und Textfarbe. Per default setzt er die Hintergrundfarbe auf Wei§
	 * und die Textfarbe auf Schwarz. Wenn die Zeile ausgewaehlt wurde, wird die Hintergrundfarbe auf Orange gesetzt.
	 * Fuer die Kolonne 4 (= Bestand) wird die Hintergrundfarbe auf Gruen gesetzt, wenn der Wert groesser als 0 ist,
	 * ist der Wert 0 wird sie auf Rot gesetzt.
	 * Hinzu kommt noch, dass der Wert der Kolonne 2 (= Preis) formatiert und die Currency hinzugefuegt wird.
	 */
	public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) { 
		Component comp = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
		
		if(!isSelected){
			switch(column){
			case 2: comp = super.getTableCellRendererComponent(table, String.format(" %.2f ", value) + Currency.getInstance(Locale.GERMANY), isSelected, hasFocus, row, column);
					break;
			case 4: if((Integer)value == 0){
						comp.setBackground(Color.RED);
					}else{
						comp.setBackground(new Color(34, 139, 34));
					}
					break;
			default: comp.setBackground(null);
			}
			comp.setForeground(Color.BLACK);
		}else{
			if(column == 2){
				comp = super.getTableCellRendererComponent(table, String.format(" %.2f ", value) + Currency.getInstance(Locale.GERMANY), isSelected, hasFocus, row, column);
			}
			comp.setBackground(Color.ORANGE);
			comp.setForeground(Color.BLACK);
		}
		return comp; 
	} 
}
