package shop.client.ui.gui.mitarbeitergui.table;

import java.util.List;

import javax.swing.table.AbstractTableModel;

import shop.common.valueobjects.Kunde;

@SuppressWarnings("serial")
public class KundenTableModel extends AbstractTableModel{
	
	private String[] columnNames = {"ID", "Username", "Name", "Stra\u00dfe", "Wohnort", "Postleitzahl", "Blockiert"};
	private List<Kunde> kundenListe;
	
	public KundenTableModel(List<Kunde> kundenListe) {
		this.kundenListe = kundenListe;
	}
	
	@Override
	public boolean isCellEditable(int row, int column){
		return false;
	}

	@Override
	public int getRowCount() {
		return kundenListe.size();
	}

	@Override
	public int getColumnCount() {
		return 7;
	}

	@Override
	public Object getValueAt(int rowIndex, int columnIndex) {
		Kunde k = kundenListe.get(rowIndex);
		switch(columnIndex){
		case 0: return k.getId();
		case 1: return k.getUsername();
		case 2: return k.getName();
		case 3: return k.getStrasse();
		case 4: return k.getWohnort();
		case 5: return k.getPlz();
		case 6: return k.getBlockiert() ? "Blockiert" : "Aktiv";
		default: return null;
		}
		
	}
	
	@Override
	public String getColumnName(int column) {
	    return columnNames[column];
	}
	
	@Override
	public Class<?> getColumnClass(int c) {
        return getValueAt(0, c).getClass();
    }
	
	public Kunde getKunde(int row){
		return kundenListe.get(row);
	}
	
}
