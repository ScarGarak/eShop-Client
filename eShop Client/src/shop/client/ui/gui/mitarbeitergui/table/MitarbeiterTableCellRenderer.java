package shop.client.ui.gui.mitarbeitergui.table;

import java.awt.Color;
import java.awt.Component;
import java.util.Currency;
import java.util.Locale;

import javax.swing.JLabel;
import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;

/**
 * Diese Klasse implementiert den DefaultTableCellRenderer der Mitarbeitertabelle.
 * @see DefaultTableCellRenderer
 * @author Migliosi Angelo
 *
 */
@SuppressWarnings("serial")
public class MitarbeiterTableCellRenderer extends DefaultTableCellRenderer {

	public MitarbeiterTableCellRenderer(JTable table) {
		DefaultTableCellRenderer renderer = (DefaultTableCellRenderer) table.getTableHeader().getDefaultRenderer();
        renderer.setHorizontalAlignment(JLabel.LEFT);
    }
	
	/**
	 * Diese Methode veraendert die Hintergrund- und Textfarbe. Per default setzt er die Hintergrundfarbe auf Wei§
	 * und die Textfarbe auf Schwarz. Die Kolonne 4 wird Rot gezeichnet, wenn der gegebene Mitarbeiter blockiert ist.
	 * Wenn nicht wird sie Gruen gezeichnet.
	 * Der Wert der Kolonne 4 (= Gehalt) wird formatiert und die Currency wird hinzugefuegt.
	 */
	public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) { 
		Component comp = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
		if(column == 4){
			comp = super.getTableCellRendererComponent(table, String.format(" %.2f ", value) + Currency.getInstance(Locale.GERMANY), isSelected, hasFocus, row, column); 
		}
		
		if(isSelected){
			comp.setBackground(Color.ORANGE);
			comp.setForeground(Color.BLACK);
		}else{
			comp.setBackground(Color.WHITE);
			comp.setForeground(Color.BLACK);
			if(column == 5){
				if(value.equals("Blockiert")){
					comp.setBackground(Color.RED);
				}else{
					comp.setBackground(new Color(34, 139, 34));
				}
			}
		}
		
		return comp;
	} 
}
