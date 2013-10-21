package no.ugland.utransprod.gui;

import java.awt.Component;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Point;
import java.awt.Toolkit;
import java.awt.event.ComponentListener;

import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JRootPane;
import javax.swing.event.InternalFrameListener;

/**
 * Klasse som implemnterer WindowInterface og wrapper en JFrame som dette
 * @author atle.brekka
 */
public class JFrameAdapter implements WindowInterface {
    private JFrame frame;

    public JFrameAdapter(final JFrame aFrame) {
        frame = aFrame;
    }

    /**
     * @see no.ugland.utransprod.gui.WindowInterface#add(java.awt.Component)
     */
    public final Component add(final Component component) {
        return frame.add(component);
    }

    /**
     * @see no.ugland.utransprod.gui.WindowInterface#add(java.awt.Component,
     *      java.lang.Object)
     */
    public final void add(final Component comp, final Object constraints) {
        frame.add(comp, constraints);

    }

    /**
     * @see no.ugland.utransprod.gui.WindowInterface#dispose()
     */
    public final void dispose() {
        frame.dispose();

    }

    /**
     * @see no.ugland.utransprod.gui.WindowInterface#getComponent()
     */
    public final Component getComponent() {
        return frame;
    }

    /**
     * @see no.ugland.utransprod.gui.WindowInterface#getSize()
     */
    public final Dimension getSize() {
        return frame.getSize();
    }

    /**
     * @see no.ugland.utransprod.gui.WindowInterface#getToolkit()
     */
    public final Toolkit getToolkit() {
        return frame.getToolkit();
    }

    /**
     * @see no.ugland.utransprod.gui.WindowInterface#pack()
     */
    public final void pack() {
        frame.pack();

    }

    /**
     * @see no.ugland.utransprod.gui.WindowInterface#setCursor(java.awt.Cursor)
     */
    public final void setCursor(final Cursor cursor) {
        frame.setCursor(cursor);

    }

    /**
     * @see no.ugland.utransprod.gui.WindowInterface#setLocation(int, int)
     */
    public final void setLocation(final int x, final int y) {
        frame.setLocation(x, y);

    }

    /**
     * @see no.ugland.utransprod.gui.WindowInterface#setLocation(java.awt.Point)
     */
    public final void setLocation(final Point point) {
        frame.setLocation(point);

    }

    /**
     * @see no.ugland.utransprod.gui.WindowInterface#setName(java.lang.String)
     */
    public final void setName(final String name) {
        frame.setName(name);

    }

    /**
     * @see no.ugland.utransprod.gui.WindowInterface#setSelected(boolean)
     */
    public final void setSelected(final boolean selected) {

    }

    /**
     * @see no.ugland.utransprod.gui.WindowInterface#setVisible(boolean)
     */
    public final void setVisible(final boolean aFlag) {
        frame.setVisible(aFlag);

    }

    /**
     * @see no.ugland.utransprod.gui.WindowInterface#validate()
     */
    public final void validate() {
        frame.validate();

    }

    /**
     * @see no.ugland.utransprod.gui.WindowInterface#getRootPane()
     */
    public final JRootPane getRootPane() {
        return frame.getRootPane();
    }

    /**
     * @see no.ugland.utransprod.gui.WindowInterface#getTitle()
     */
    public final String getTitle() {
        return frame.getTitle();
    }

    public final void addInternalFrameListener(
            final InternalFrameListener internalFrameListener) {

    }

    /**
     * @see no.ugland.utransprod.gui.WindowInterface#setSize(java.awt.Dimension)
     */
    public final void setSize(final Dimension size) {
        frame.setSize(size);

    }

    /**
     * @see no.ugland.utransprod.gui.WindowInterface#isAdded()
     */
    public final boolean isAdded() {
        return false;
    }

    /**
     * @see no.ugland.utransprod.gui.WindowInterface#setAdded(boolean)
     */
    public final void setAdded(final boolean added) {
    }

    /**
     * @see no.ugland.utransprod.gui.WindowInterface#addComponentListener(java.awt.event.ComponentListener)
     */
    public final void addComponentListener(final ComponentListener componentListener) {
        frame.addComponentListener(componentListener);

    }

    /**
     * @see no.ugland.utransprod.gui.WindowInterface#cleanUp()
     */
    public final void cleanUp() {
        frame.dispose();
        

    }

    /**
     * @see no.ugland.utransprod.gui.WindowInterface#getJDialog()
     */
    public final JDialog getJDialog() {
        return null;
    }

    public final void remove(final Component comp) {
        frame.remove(comp);

    }

    public final void setTitle(final String title) {
        frame.setTitle(title);

    }

}
