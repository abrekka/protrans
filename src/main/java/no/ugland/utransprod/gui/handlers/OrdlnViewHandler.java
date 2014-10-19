package no.ugland.utransprod.gui.handlers;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JTable;
import javax.swing.ListModel;

import no.ugland.utransprod.gui.Closeable;
import no.ugland.utransprod.gui.WindowInterface;
import no.ugland.utransprod.gui.buttons.CancelButton;
import no.ugland.utransprod.model.Ordln;
import no.ugland.utransprod.service.OrdlnManager;
import no.ugland.utransprod.util.ModelUtil;

import org.jdesktop.swingx.JXTable;

import com.jgoodies.binding.adapter.AbstractTableAdapter;
import com.jgoodies.binding.list.ArrayListModel;

public class OrdlnViewHandler {
    private final ArrayListModel ordlnList;

    public OrdlnViewHandler(String orderNr) {
	ordlnList = new ArrayListModel();
	initOrdlns(orderNr);
    }

    public JButton getButtonCancel(WindowInterface window) {
	JButton button = new CancelButton(window, new CloseAction(), true);
	button.setName("ButtonCancelOrdlnView");
	return button;
    }

    public JXTable getTableOrderLines() {
	JXTable table = new JXTable(new OrdlnTableModel(ordlnList));
	table.setName("TableOrdln");
	table.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
	table.setColumnControlVisible(true);
	table.setName("TableOrdln");

	// table.setSelectionModel(new
	// SingleListSelectionAdapter(ordlnList.getSelectionIndexHolder()));

	// linjenr
	table.getColumnExt(0).setPreferredWidth(50);
	// Produktnr
	table.getColumnExt(1).setPreferredWidth(100);
	// Beskrivelse
	table.getColumnExt(2).setPreferredWidth(150);
	// Antall
	table.getColumnExt(3).setPreferredWidth(50);
	// Rabatt
	table.getColumnExt(4).setPreferredWidth(50);
	// Monteringspris
	table.getColumnExt(5).setPreferredWidth(100);
	// Kostpris
	table.getColumnExt(6).setPreferredWidth(70);
	// Pris
	table.getColumnExt(7).setPreferredWidth(70);

	return table;
    }

    private void initOrdlns(final String orderNr) {
	OrdlnManager ordlnManager = (OrdlnManager) ModelUtil.getBean("ordlnManager");
	List<Ordln> list = ordlnManager.findByOrderNr(orderNr);
	ordlnList.clear();
	if (list != null) {
	    ordlnList.addAll(list);
	}
    }

    private static final class OrdlnTableModel extends AbstractTableAdapter {

	private static final long serialVersionUID = 1L;

	private static final String[] COLUMNS = { "Linjenr", "Produktnr", "Beskrivelse", "Antall", "Rabatt", "Monteringspris", "Kostpris", "Pris" };

	/**
	 * @param listModel
	 */
	OrdlnTableModel(final ListModel listModel) {
	    super(listModel, COLUMNS);
	}

	/**
	 * Henter verdi
	 * 
	 * @param rowIndex
	 * @param columnIndex
	 * @return verdi
	 */
	public Object getValueAt(final int rowIndex, final int columnIndex) {

	    Ordln ordln = (Ordln) getRow(rowIndex);

	    switch (columnIndex) {
	    case 0:
		return ordln.getLnno();
	    case 1:
		if (ordln.getProd() != null) {
		    return ordln.getProd().getProdNo();
		}
		return null;
	    case 2:
		return ordln.getDescription();
	    case 3:
		if (ordln.getNoinvoab() != null) {
		    return ordln.getNoinvoab().setScale(2, RoundingMode.CEILING);
		}
		return null;
	    case 4:
		if (ordln.getDiscount() != null) {
		    return ordln.getDiscount().setScale(2, RoundingMode.CEILING);
		}
		return null;
	    case 5:
		if (ordln.getFree1() != null) {
		    return ordln.getFree1().setScale(2, RoundingMode.CEILING);
		}
		return null;
	    case 6:
		if (ordln.getCostPrice() != null) {
		    return ordln.getCostPrice().setScale(2, RoundingMode.CEILING);
		}
		return null;
	    case 7:
		if (ordln.getPrice() != null) {
		    return ordln.getPrice().setScale(2, RoundingMode.CEILING);
		}
		return null;
	    default:
		throw new IllegalStateException("Unknown column");
	    }

	}

	/**
	 * Henter kolonneklasse
	 * 
	 * @param columnIndex
	 * @return kolonneklasse
	 */
	@Override
	public Class<?> getColumnClass(final int columnIndex) {
	    switch (columnIndex) {
	    case 0:
	    case 1:
	    case 2:
		return String.class;

	    case 3:
	    case 4:
	    case 5:
	    case 6:
	    case 7:
		return BigDecimal.class;
	    default:
		throw new IllegalStateException("Unknown column");
	    }
	}

    }

    private class CloseAction implements Closeable {

	public boolean canClose(String actionString, WindowInterface window) {
	    return true;
	}

    }

}
