package no.ugland.utransprod.gui.model;

import java.awt.Component;

import javax.swing.BorderFactory;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.TableCellRenderer;

import no.ugland.utransprod.util.Util;

public class TextPaneRendererTransportSent extends JTextField implements//extends JTextPane implements
TableCellRenderer {
    public final Component getTableCellRendererComponent(final JTable table,
            final Object object, final boolean isSelected,
            final boolean hasFocus, final int row, final int column) {
        Transportable transportable =(Transportable)object;
        setBorder(BorderFactory.createEmptyBorder());
        if(transportable.getSent()!=null){
            setText("Ja");
            setToolTipText(Util.SHORT_DATE_FORMAT.format(transportable.getSent()));
        }else{
            setText("Nei");
            setToolTipText("");
        }

        setBackgrounAndForeground(table, isSelected);
        return this;
    }
    private void setBackgrounAndForeground(final JTable table,
            final boolean isSelected) {
        if (isSelected) {
            setBackground(table.getSelectionBackground());
            setForeground(table.getSelectionForeground());

        } else {
            setBackground(table.getBackground());
            setForeground(table.getForeground());
        }
    }

}
