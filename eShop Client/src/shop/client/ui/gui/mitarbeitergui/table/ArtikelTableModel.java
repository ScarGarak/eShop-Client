package shop.client.ui.gui.mitarbeitergui.table;

import java.util.Iterator;
import java.util.List;

import javax.swing.table.AbstractTableModel;

import shop.common.valueobjects.Artikel;
import shop.common.valueobjects.Massengutartikel;

@SuppressWarnings("serial")
public class ArtikelTableModel extends AbstractTableModel {

	private String[] columnNames = {"Artikelnummer", "Bezeichnung", "Preis", "Packungsgr\u00f6\u00dfe", "Bestand"};
	private List<Artikel> artikelListe;
	
	public ArtikelTableModel(List<Artikel> artikelListe) {
		this.artikelListe = artikelListe;
	}
	
	@Override
	public boolean isCellEditable(int row, int column){
		return false;
	}

	@Override
	public int getRowCount() {
		return artikelListe.size();
	}

	@Override
	public int getColumnCount() {
		return 5;
	}

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
	
	@Override
	public Class<?> getColumnClass(int c) {
		if(getValueAt(0, c) != null){
			return getValueAt(0, c).getClass();
		}else{
			return Object.class;
		}
    }
	
	public Artikel getArtikel(int row){
		return artikelListe.get(row);
	}
	
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
