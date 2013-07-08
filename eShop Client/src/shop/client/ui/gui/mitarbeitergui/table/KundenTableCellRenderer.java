package shop.client.ui.gui.mitarbeitergui.table;

import java.awt.Color;
import java.awt.Component;

import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;

/**
 * Diese Klasse implementiert den DefaultTableCellRenderer der Kundentabelle.
 * @see DefaultTableCellRenderer
 * @author Migliosi Angelo
 *
 */
@SuppressWarnings("serial")
public class KundenTableCellRenderer extends DefaultTableCellRenderer {
	
	/**
	 * Diese Methode veraendert die Hintergrund- und Textfarbe. Per default setzt er die Hintergrundfarbe auf Wei§
	 * und die Textfarbe auf Schwarz. Die Kolonne 6 wird Rot gezeichnet, wenn der gegebene Kunde blockiert ist.
	 * Wenn nicht wird sie Gruen gezeichnet.
	 */
	public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) { 
		Component comp = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
		
		if(isSelected){
			comp.setBackground(Color.ORANGE);
			comp.setForeground(Color.BLACK);
		}else{
			comp.setBackground(Color.WHITE);
			comp.setForeground(Color.BLACK);
			if(column == 6){
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
