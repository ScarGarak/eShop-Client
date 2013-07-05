package shop.client.ui.gui.mitarbeitergui.table;

import java.awt.Color;
import java.awt.Component;
import java.util.Currency;
import java.util.Locale;

import javax.swing.JLabel;
import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;

@SuppressWarnings("serial")
public class MitarbeiterTableCellRenderer extends DefaultTableCellRenderer {

	public MitarbeiterTableCellRenderer(JTable table) {
		DefaultTableCellRenderer renderer = (DefaultTableCellRenderer) table.getTableHeader().getDefaultRenderer();
        renderer.setHorizontalAlignment(JLabel.LEFT);
    }
	
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
