package no.ugland.utransprod.gui.model;

import java.awt.Component;

import javax.swing.JTable;
import javax.swing.JTextPane;
import javax.swing.table.TableCellRenderer;

import no.ugland.utransprod.gui.IconEnum;
import no.ugland.utransprod.model.Customer;
import no.ugland.utransprod.util.Util;

/**
 * Render for å vise kunde
 * @author atle.brekka
 */
public class TextPaneRendererCustomer extends JTextPane implements
        TableCellRenderer {

    private static final long serialVersionUID = 1L;
    private static final int MAX_LENGHT = 50;

    /**
     * @see javax.swing.table.TableCellRenderer#getTableCellRendererComponent(javax.swing.JTable,
     *      java.lang.Object, boolean, boolean, int, int)
     */
    public final Component getTableCellRendererComponent(final JTable table,
            final Object object, final boolean isSelected,
            final boolean hasFocus, final int row, final int column) {

        Transportable transportable = (Transportable) object;

        Customer customer = transportable.getCustomer();
        setText(customer.getFirstName() + " " + customer.getLastName() + " "
                + customer.getCustomerNr());

        StringBuffer toolTip = new StringBuffer();
        
        setCommentAsTooltip(object, toolTip);

        setWarningAsTooltip(object, toolTip);

        /*if (transportable.getComment() != null
                && transportable.getComment().length() != 0) {
            insertIcon(IconEnum.ICON_COMMENT.getIcon());

            toolTip.append(transportable.getComment());
        }
        if (transportable.getSpecialConcern() != null) {
            insertIcon(IconEnum.ICON_WARNING.getIcon());
            toolTip.append(transportable.getSpecialConcern());
        }*/
        //setToolTipText(toolTip.toString());
        if (toolTip.length() > 0) {
            toolTip.insert(0, "<html>");
            toolTip.append("</html>");
            setToolTipText(toolTip.toString());
        } else {
            setToolTipText("");
        }
        if (isSelected) {
            setBackground(table.getSelectionBackground());
            setForeground(table.getSelectionForeground());

        } else {
            setBackground(table.getBackground());
            setForeground(table.getForeground());
        }
        return this;
    }
    private void setCommentAsTooltip(final Object object,
            final StringBuffer toolTip) {
        String comment = ((Transportable) object).getComment();
        if (comment != null && comment.length() != 0) {
            addCommentToTooltip(toolTip, comment);
        }
    }
    private void setWarningAsTooltip(final Object object,
            final StringBuffer toolTip) {
        if (((Transportable) object).getSpecialConcern() != null) {
            insertIcon(IconEnum.ICON_WARNING.getIcon());
            toolTip.append(((Transportable) object).getSpecialConcern());
        }
    }
    private void addCommentToTooltip(final StringBuffer toolTip,
            final String comment) {
        insertIcon(IconEnum.ICON_COMMENT.getIcon());

        String multilineComment = Util.splitLongStringIntoLinesWithBr(comment,
                MAX_LENGHT);

        toolTip.append(multilineComment);
    }

}
