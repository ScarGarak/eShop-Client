package shop.client.ui.gui.mitarbeitergui.table;

import javax.swing.table.AbstractTableModel;

/**
 * Diese Methode implementiert das Table-Model von der Logdatei.
 * @author Migliosi Angelo
 *
 */
@SuppressWarnings("serial")
public class LogTableModel extends AbstractTableModel{

	private String[] columnNames = {"Datum", "Uhrzeit", "Personentyp", "Person ID", "St\u00fcckzahl", "Artikel ID", "Aktion"};
	private String[] logDatei;
	
	/**
	 * Konstruktor von LogTableModel
	 * @param logDatei
	 */
	public LogTableModel(String logDatei) {
		if(logDatei != null)
			this.logDatei = logDatei.split("\n");
		else
			this.logDatei = new String[0];
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
		return logDatei.length;
	}

	@Override
	public int getColumnCount() {
		return 7;
	}

	@Override
	public Object getValueAt(int rowIndex, int columnIndex) {
		if(logDatei != null && logDatei.length != 0){
			String[] eintrag = logDatei[rowIndex].split(" ");
			switch(columnIndex){
			case 0: return eintrag[0];
			case 1: return eintrag[1];
			case 2: return eintrag[2];
			case 3: return Integer.parseInt(eintrag[3]);
			case 4: return Integer.parseInt(eintrag[4]);
			case 5: return eintrag[7];
			case 6: return eintrag[8];
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
}
