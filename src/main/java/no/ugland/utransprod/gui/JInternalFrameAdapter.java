package no.ugland.utransprod.gui;

import java.awt.Component;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Point;
import java.awt.Toolkit;
import java.awt.event.ComponentListener;
import java.beans.PropertyVetoException;
import java.util.Arrays;
import java.util.List;

import javax.swing.JDialog;
import javax.swing.JInternalFrame;
import javax.swing.JRootPane;
import javax.swing.event.InternalFrameEvent;
import javax.swing.event.InternalFrameListener;

/**
 * Klasse som brukes sammen med JDialogAdapter for å kunne brukes om hverandre.
 * Stort sett alle metoder her er metoder som kjøres direkte på den
 * JInternalFrame som blir adoptert
 * @author atle.brekka
 */
public class JInternalFrameAdapter implements WindowInterface {
    private JInternalFrame internalFrame;

    private boolean isAdded;

    /**
     * @param internalFrame
     *            vinduet som skal adopteres
     */
    public JInternalFrameAdapter(final JInternalFrame internalFrame) {
        this.internalFrame = internalFrame;
    }

    /**
     * @see no.ugland.utransprod.gui.WindowInterface#dispose()
     */
    public final void dispose() {
        if (internalFrame != null) {
            internalFrame.dispose();
        }

    }

    /**
     * @see no.ugland.utransprod.gui.WindowInterface#setCursor(java.awt.Cursor)
     */
    public final void setCursor(final Cursor cursor) {
        internalFrame.setCursor(cursor);

    }

    /**
     * @see no.ugland.utransprod.gui.WindowInterface#add(java.awt.Component)
     */
    public final Component add(final Component component) {

        return internalFrame.add(component);
    }

    /**
     * @see no.ugland.utransprod.gui.WindowInterface#pack()
     */
    public final void pack() {
        internalFrame.pack();

    }

    /**
     * @see no.ugland.utransprod.gui.WindowInterface#getSize()
     */
    public final Dimension getSize() {

        return internalFrame.getSize();
    }

    /**
     * @see no.ugland.utransprod.gui.WindowInterface#getToolkit()
     */
    public final Toolkit getToolkit() {
        return internalFrame.getToolkit();
    }

    /**
     * @see no.ugland.utransprod.gui.WindowInterface#setLocation(int, int)
     */
    public final void setLocation(final int x, final int y) {
        internalFrame.setLocation(x, y);

    }

    /**
     * @see no.ugland.utransprod.gui.WindowInterface#setVisible(boolean)
     */
    public final void setVisible(final boolean aFlag) {
        internalFrame.setVisible(aFlag);

        if (!aFlag) {
            fireClosed();
        }

    }

    /**
     * Gir beskjed om at vindu stenges
     */
    private void fireClosed() {
        InternalFrameListener[] listeners = internalFrame
                .getInternalFrameListeners();
        List<InternalFrameListener> list = Arrays.asList(listeners);
        for (InternalFrameListener listener : list) {
            listener.internalFrameClosed(new InternalFrameEvent(internalFrame,
                    0));
        }
    }

    /**
     * @see no.ugland.utransprod.gui.WindowInterface#getComponent()
     */
    public final Component getComponent() {
        return internalFrame;
    }

    /**
     * @see no.ugland.utransprod.gui.WindowInterface#add(java.awt.Component,
     *      java.lang.Object)
     */
    public final void add(final Component comp, final Object constraints) {
        internalFrame.add(comp, constraints);

    }

    /**
     * @see no.ugland.utransprod.gui.WindowInterface#setLocation(java.awt.Point)
     */
    public final void setLocation(final Point point) {
        internalFrame.setLocation(point);

    }

    /**
     * @see no.ugland.utransprod.gui.WindowInterface#setSelected(boolean)
     */
    public final void setSelected(final boolean selected) throws PropertyVetoException {
        internalFrame.setSelected(selected);

    }

    /**
     * @see no.ugland.utransprod.gui.WindowInterface#setName(java.lang.String)
     */
    public final void setName(final String name) {
        internalFrame.setName(name);

    }

    /**
     * @see no.ugland.utransprod.gui.WindowInterface#validate()
     */
    public final void validate() {
        internalFrame.validate();

    }

    /**
     * @see no.ugland.utransprod.gui.WindowInterface#getRootPane()
     */
    public final JRootPane getRootPane() {
        return internalFrame.getRootPane();
    }

    /**
     * @see no.ugland.utransprod.gui.WindowInterface#getTitle()
     */
    public final String getTitle() {
        return internalFrame.getTitle();
    }

    public final void addInternalFrameListener(
            final InternalFrameListener internalFrameListener) {
        internalFrame.addInternalFrameListener(internalFrameListener);

    }

    /**
     * @see no.ugland.utransprod.gui.WindowInterface#setSize(java.awt.Dimension)
     */
    public final void setSize(final Dimension size) {
        internalFrame.setSize(size);

    }

    /**
     * @see no.ugland.utransprod.gui.WindowInterface#isAdded()
     */
    public final boolean isAdded() {
        return isAdded;
    }

    /**
     * @see no.ugland.utransprod.gui.WindowInterface#setAdded(boolean)
     */
    public final void setAdded(final boolean added) {
        isAdded = added;
    }

    /**
     * @see no.ugland.utransprod.gui.WindowInterface#addComponentListener(java.awt.event.ComponentListener)
     */
    public final void addComponentListener(final ComponentListener componentListener) {
        internalFrame.addComponentListener(componentListener);

    }

    /**
     * @see no.ugland.utransprod.gui.WindowInterface#cleanUp()
     */
    public final void cleanUp() {
    }

    /**
     * @see no.ugland.utransprod.gui.WindowInterface#getJDialog()
     */
    public final JDialog getJDialog() {
        return null;
    }

    public final void remove(final Component comp) {
        internalFrame.remove(comp);

    }

    public final void setTitle(final String title) {
        internalFrame.setTitle(title);

    }

}
