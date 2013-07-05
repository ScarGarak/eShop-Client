package shop.client.ui.gui.kundengui.table;

import java.util.List;
import java.util.Vector;

import javax.swing.table.AbstractTableModel;

import shop.common.valueobjects.WarenkorbArtikel;

@SuppressWarnings("serial")
public class WarenkorbArtikelTableModel extends AbstractTableModel {

	private Vector<String> columnNames;
	private Vector<WarenkorbArtikel> data;
	
	public WarenkorbArtikelTableModel(List<WarenkorbArtikel> warenkorbArtikel) {
		super();
		
		columnNames = new Vector<String>();
		columnNames.add("St\u00fcckzahl");
		columnNames.add("Bezeichner");
		columnNames.add("Preis");
		
		data = (Vector<WarenkorbArtikel>) warenkorbArtikel;
	}
	
	@Override
	public int getColumnCount() {
		return columnNames.size();
	}

	@Override
	public int getRowCount() {
		return data.size();
	}
	
	@Override
	public String getColumnName(int column) {
        return columnNames.get(column);
    }
	
	@Override
    public boolean isCellEditable(int row, int column) {
       return false;
    }
	
	@Override
	public Object getValueAt(int row, int col) {
		if (data != null && data.size() != 0) {
			WarenkorbArtikel wa = data.get(row);
			switch(col) {
				case 0: return wa.getStueckzahl();
				case 1: return wa.getArtikel().getBezeichnung();
				case 2: return wa.getArtikel().getPreis();
				default: return null;
			}
		} else {
			return null;
		}
	}
	
	public WarenkorbArtikel getRowValue(int row) {
		return data.get(row);
	}
	
}
