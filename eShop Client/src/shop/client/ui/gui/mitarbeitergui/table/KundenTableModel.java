package shop.client.ui.gui.mitarbeitergui.table;

import java.util.List;

import javax.swing.table.AbstractTableModel;

import shop.common.valueobjects.Kunde;

/**
 * Diese Methode implementiert das Table-Model von den Kunden.
 * @author Migliosi Angelo
 *
 */
@SuppressWarnings("serial")
public class KundenTableModel extends AbstractTableModel{
	
	private String[] columnNames = {"ID", "Username", "Name", "Stra\u00dfe", "Wohnort", "Postleitzahl", "Blockiert"};
	private List<Kunde> kundenListe;
	
	/**
	 * Konstruktor von KundenTableModel
	 * @param kundenListe
	 */
	public KundenTableModel(List<Kunde> kundenListe) {
		this.kundenListe = kundenListe;
	}
	
	/**
	 * Die Zellen koennen nicht veraendert werden!
	 */
	@Override
	public boolean isCellEditable(int row, int column){
		return false;
	}

	@Override
	public int getRowCount() {
		if(kundenListe == null){
			return 0;
		}else{
			return kundenListe.size();
		}
	}

	@Override
	public int getColumnCount() {
		return 7;
	}
	
	/**
	 * Diese Methode gibt den Wert fuer die Zelle mit den angegebenen Koordinaten zurueck.
	 * Fuer die Kolonne 1 wird die Kunden ID, Kolonne 2 der Username, Kolonne 3 der Name,
	 * Kolonne 4 die Strasse, Kolonne 5 den Wohnort, Kolonne 6 die Postleitzahl, Kolonne 7 
	 * Blockiert oder Aktiv zurueck.
	 */
	@Override
	public Object getValueAt(int rowIndex, int columnIndex) {
		if(kundenListe != null && kundenListe.size() != 0){
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
		}else{
			return null;
		}
		
	}
	
	@Override
	public String getColumnName(int column) {
	    return columnNames[column];
	}
	
	/**
	 * Diese Methode gibt den Datentyp der angegebenen Kolonne zurueck. Wenn keine Daten in 
	 * dieser Kolonne zur Verfuegung stehen wird "Object" zurueckgegeben.
	 */
	@Override
	public Class<?> getColumnClass(int c) {
		if(getValueAt(0, c) != null){
			return getValueAt(0, c).getClass();
		}else{
			return Object.class;
		}
    }
	
	/**
	 * Diese Methode gibt die Kundeninstanz zurueck, die sich in der angegebenen Zeile befindet.
	 * @param row
	 * @return
	 */
	public Kunde getKunde(int row){
		return kundenListe.get(row);
	}
	
}
