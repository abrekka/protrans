package no.ugland.utransprod.gui.handlers;

import javax.swing.ListModel;

import no.ugland.utransprod.model.CostType;

import com.jgoodies.binding.adapter.AbstractTableAdapter;

/**
 * Tabellmodell for kostnadstyper
 * 
 * @author atle.brekka
 * 
 */
class CostTypeTableModel extends AbstractTableAdapter {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * 
	 */
	//private static final String[] COLUMNS = { "Navn", "Beskrivelse" };

	/**
	 * @param listModel
	 */
	CostTypeTableModel(ListModel listModel) {
		super(listModel, new String[]{ "Navn", "Beskrivelse" });
	}

	/**
	 * Henter verdi
	 * 
	 * @param rowIndex
	 * @param columnIndex
	 * @return verdi
	 */
	public Object getValueAt(int rowIndex, int columnIndex) {
		CostType costType = (CostType) getRow(rowIndex);
		switch (columnIndex) {
		case 0:
			return costType.getCostTypeName();
		case 1:
			return costType.getDescription();
		default:
			throw new IllegalStateException("Unknown column");
		}

	}

	/**
	 * @see javax.swing.table.AbstractTableModel#getColumnClass(int)
	 */
	@Override
	public Class<?> getColumnClass(int columnIndex) {
		switch (columnIndex) {
		case 0:
		case 1:
			return String.class;
		default:
			throw new IllegalStateException("Unknown column");
		}
	}

}