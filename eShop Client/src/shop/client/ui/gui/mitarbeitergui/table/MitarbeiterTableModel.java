package shop.client.ui.gui.mitarbeitergui.table;

import java.util.Iterator;
import java.util.List;

import javax.swing.table.AbstractTableModel;

import shop.common.valueobjects.Mitarbeiter;
import shop.common.valueobjects.MitarbeiterFunktion;

/**
 * Diese Methode implementiert das Table-Model von den Mitarbeitern.
 * @author Migliosi Angelo
 *
 */
@SuppressWarnings("serial")
public class MitarbeiterTableModel extends AbstractTableModel{
	
	private String[] columnNames = {"ID", "Username", "Name", "Funktion", "Gehalt", "Blockiert"};
	private List<Mitarbeiter> mitarbeiterListe;
	private int columnCount;
	
	/**
	 * Konstruktor von MitarbeiterTableModel
	 * @param mitarbeiterListe
	 * @param mf
	 */
	public MitarbeiterTableModel(List<Mitarbeiter> mitarbeiterListe, MitarbeiterFunktion mf) {
		this.mitarbeiterListe = mitarbeiterListe;
		if(mf.equals(MitarbeiterFunktion.Admin)){
			columnCount = 6; 
		}else{
			columnCount = 3;
		}
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
		if(mitarbeiterListe == null){
			return 0;
		}else{
			return mitarbeiterListe.size();
		}
	}

	@Override
	public int getColumnCount() {
		return columnCount;
	}

	/**
	 * Diese Methode gibt den Wert fuer die Zelle mit den angegebenen Koordinaten zurueck.
	 * Fuer die Kolonne 1 wird die Mitarbeiter ID, Kolonne 2 der Username, Kolonne 3 der Name,
	 * Kolonne 4 die Funktion, Kolonne 5 das Gehalt und Kolonne 6 Blockiert oder Aktiv zurueck.
	 */
	@Override
	public Object getValueAt(int rowIndex, int columnIndex) {
		if(mitarbeiterListe != null && mitarbeiterListe.size() != 0){
			Mitarbeiter m = mitarbeiterListe.get(rowIndex);
			switch(columnIndex){
			case 0: return m.getId();
			case 1: return m.getUsername();
			case 2: return m.getName();
			case 3: return m.getFunktion().toString();
			case 4: return m.getGehalt();
			case 5: return m.getBlockiert() ? "Blockiert" : "Aktiv";
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
	 * Diese Methode gibt die Mitarbeiterinstanz zurueck, die sich in der angegebenen Zeile befindet.
	 * @param row
	 * @return
	 */
	public Mitarbeiter getMitarbeiter(int row){
		return mitarbeiterListe.get(row);
	}
	
	/**
	 * Diese Methode gibt die Zeilennummer des Mitarbeiters mit dem angegebenen Usernamen zurueck.
	 * @param username
	 * @return
	 */
	public int getRowIndex(String username){
		Iterator<Mitarbeiter> iter = mitarbeiterListe.iterator();
		while(iter.hasNext()){
			Mitarbeiter m = iter.next();
			if(m.getUsername().equals(username)){
				return mitarbeiterListe.indexOf(m);
			}
		}
		
		return -1;
	}

}
