package no.ugland.utransprod.gui.model;

import java.awt.Component;

import javax.swing.JTable;
import javax.swing.JTextPane;
import javax.swing.table.TableCellRenderer;

import no.ugland.utransprod.gui.IconEnum;

/**
 * Render for å vise kommentar
 * @author atle.brekka
 */
public class TextPaneRendererOrder extends JTextPane implements TableCellRenderer {

    /**
     * 
     */
    private static final long serialVersionUID = 1L;

    /**
     * @see javax.swing.table.TableCellRenderer#getTableCellRendererComponent(javax.swing.JTable,
     *      java.lang.Object, boolean, boolean, int, int)
     */
    public Component getTableCellRendererComponent(JTable table, Object object,
            boolean isSelected, boolean hasFocus, int row, int column) {
        setText(((TextRenderable) object).getOrderString());
        StringBuffer toolTip = new StringBuffer();

        if (((TextRenderable) object).getComment() != null
                && ((TextRenderable) object).getComment().length() != 0) {
            insertIcon(IconEnum.ICON_COMMENT.getIcon());
            if (((TextRenderable) object).getComment() != null) {
                // setToolTipText(((TextRenderable) object).getComment());
                toolTip.append(((TextRenderable) object).getComment());
            } /*
                 * else { setToolTipText(""); }
                 */
        }
        setToolTipText(toolTip.toString());
        if (isSelected) {
            setBackground(table.getSelectionBackground());
            setForeground(table.getSelectionForeground());

        } else {
            setBackground(table.getBackground());
            setForeground(table.getForeground());
        }

        return this;
    }

}
