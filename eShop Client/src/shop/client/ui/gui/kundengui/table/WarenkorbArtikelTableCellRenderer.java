package shop.client.ui.gui.kundengui.table;

import java.awt.Color;
import java.awt.Component;
import java.util.Currency;
import java.util.Locale;

import javax.swing.JLabel;
import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;

@SuppressWarnings("serial")
public class WarenkorbArtikelTableCellRenderer extends DefaultTableCellRenderer {
	
	public WarenkorbArtikelTableCellRenderer(JTable table) {
		DefaultTableCellRenderer headerRenderer = (DefaultTableCellRenderer) table.getTableHeader().getDefaultRenderer();
        headerRenderer.setHorizontalAlignment(JLabel.LEFT);
    }
	
	 @Override
     public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
		 Component comp;
		 switch(column) {
		 	case 2:  comp = super.getTableCellRendererComponent(table, String.format(" %.2f ", value) + Currency.getInstance(Locale.GERMANY), isSelected, hasFocus, row, column);
		 			 break;
		 	default: comp = super.getTableCellRendererComponent(table, " " + String.valueOf(value), isSelected, hasFocus, row, column);
		 			 break;
		 }
		 if (isSelected) {
			 comp.setForeground(Color.BLACK);
			 comp.setBackground(Color.ORANGE);
		 } else {
			 comp.setForeground(Color.BLACK);
			 comp.setBackground(Color.WHITE);
		 }
		 return comp;
	 }
	
}
