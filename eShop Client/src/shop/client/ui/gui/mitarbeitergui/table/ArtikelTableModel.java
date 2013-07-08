package shop.client.ui.gui.mitarbeitergui.table;

import java.util.Iterator;
import java.util.List;

import javax.swing.table.AbstractTableModel;

import shop.common.valueobjects.Artikel;
import shop.common.valueobjects.Massengutartikel;

/**
 * Diese Methode implementiert das Table-Model von den Artikeln.
 * @author Migliosi Angelo
 *
 */
@SuppressWarnings("serial")
public class ArtikelTableModel extends AbstractTableModel {

	private String[] columnNames = {"Artikelnummer", "Bezeichnung", "Preis", "Packungsgr\u00f6\u00dfe", "Bestand"};
	private List<Artikel> artikelListe;
	
	/**
	 * Konstruktor von ArtikelTableModel
	 * @param artikelListe
	 */
	public ArtikelTableModel(List<Artikel> artikelListe) {
		this.artikelListe = artikelListe;
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
		if(artikelListe == null){
			return 0;
		}else{
			return artikelListe.size();
		}
	}

	@Override
	public int getColumnCount() {
		return 5;
	}

	/**
	 * Diese Methode gibt den Wert fuer die Zelle mit den angegebenen Koordinaten zurueck.
	 * Fuer die Kolonne 1 wird die Artikelnummer, Kolonne 2 die Artikelbezeichnung, Kolonne 
	 * 3 den Preis, Kolonne 4 die Packungsgroesse und Kolonne 5 den Bestand.
	 */
	@Override
	public Object getValueAt(int rowIndex, int columnIndex) {
		if(artikelListe != null && artikelListe.size() != 0){
			Artikel a = artikelListe.get(rowIndex);
			switch(columnIndex){
			case 0: return a.getArtikelnummer();
			case 1: return a.getBezeichnung();
			case 2: return a.getPreis();
			case 3: if(a instanceof Massengutartikel){
				return ((Massengutartikel)a).getPackungsgroesse();
			}else{
				return 1;
			}
			case 4: return a.getBestand();
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
	 * Diese Methode gibt die Artikelinstanz zurueck, die sich in der angegebenen Zeile befindet.
	 * @param row
	 * @return
	 */
	public Artikel getArtikel(int row){
		return artikelListe.get(row);
	}
	
	/**
	 * Diese Methode gibt die Zeilennummer des Artikels mit der angegebenen Artikelnummer zurueck.
	 * @param artikelnummer
	 * @return
	 */
	public int getRowIndex(int artikelnummer){
		Iterator<Artikel> iter = artikelListe.iterator();
		while(iter.hasNext()){
			Artikel a = iter.next();
			if(a.getArtikelnummer() == artikelnummer){
				return artikelListe.indexOf(a);
			}
		}
		
		return -1;
	}
	
}
