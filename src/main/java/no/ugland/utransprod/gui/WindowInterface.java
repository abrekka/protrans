package no.ugland.utransprod.gui;

import java.awt.Component;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Point;
import java.awt.Toolkit;
import java.awt.event.ComponentListener;
import java.beans.PropertyVetoException;

import javax.swing.JDialog;
import javax.swing.JRootPane;
import javax.swing.event.InternalFrameListener;

/**
 * Interface som brukes for å adoptere JInternalFrame og JDialog slik at disse
 * kan brukes om hverandre. De fleste metodene her blir kjørt direkte på
 * henholdsvis JInternalFrame eller JDialog
 * 
 * @author atle.brekka
 * 
 */
public interface WindowInterface {
	/**
	 * @see javax.swing.JInternalFrame#dispose()
	 * @see javax.swing.JDialog#dispose()
	 */
	void dispose();

	/**
	 * @param cursor
	 * @see javax.swing.JInternalFrame#setCursor(Cursor)
	 * @see javax.swing.JDialog#setCursor(Cursor)
	 */
	void setCursor(Cursor cursor);

	/**
	 * @param component
	 * @return komponent
	 * @see javax.swing.JInternalFrame#add(Component)
	 * @see javax.swing.JDialog#add(Component)
	 */
	Component add(Component component);

	/**
	 * @see javax.swing.JInternalFrame#pack()
	 * @see javax.swing.JDialog#pack()
	 */
	void pack();

	/**
	 * @return størrelse
	 * @see javax.swing.JInternalFrame#getSize()
	 * @see javax.swing.JDialog#getSize()
	 */
	Dimension getSize();

	/**
	 * @return toolkit
	 * @see javax.swing.JInternalFrame#getToolkit()
	 * @see javax.swing.JDialog#getToolkit()
	 */
	Toolkit getToolkit();

	/**
	 * @param x
	 * @param y
	 * @see javax.swing.JInternalFrame#setLocation(int,int)
	 * @see javax.swing.JDialog#setLocation(int,int)
	 */
	void setLocation(int x, int y);

	/**
	 * @param aFlag
	 * @see javax.swing.JInternalFrame#setVisible(boolean)
	 * @see javax.swing.JDialog#setVisible(boolean)
	 */
	void setVisible(boolean aFlag);

	/**
	 * Henter ut komponent som er adopter
	 * 
	 * @return komponent
	 */
	Component getComponent();

	/**
	 * @param comp
	 * @param constraints
	 * @see javax.swing.JInternalFrame#add(Component,Object)
	 * @see javax.swing.JDialog#add(Component,Object)
	 */
	void add(Component comp, Object constraints);

	/**
	 * @param point
	 * @see javax.swing.JInternalFrame#setLocation(Point)
	 * @see javax.swing.JDialog#setLocation(Point)
	 */
	void setLocation(Point point);

	/**
	 * Gjelder ikke for JDialog
	 * 
	 * @param selected
	 * @throws PropertyVetoException
	 * @see javax.swing.JInternalFrame#setSelected(boolean)
	 */
	void setSelected(boolean selected) throws PropertyVetoException;

	/**
	 * @param name
	 * @see javax.swing.JInternalFrame#setName(String)
	 * @see javax.swing.JDialog#setName(String)
	 */
	void setName(String name);

	/**
	 * @see javax.swing.JInternalFrame#validate()
	 * @see javax.swing.JDialog#validate()
	 */
	void validate();

	/**
	 * @return rotpanel
	 */
	JRootPane getRootPane();

	/**
	 * @return tittel
	 */
	String getTitle();

	/**
	 * Legger til lytter på IntenalFrame dersom objekt er av en slik type
	 * 
	 * @param internalFrameListener
	 */
	void addInternalFrameListener(InternalFrameListener internalFrameListener);

	/**
	 * @see javax.swing.JInternalFrame#setSize(Dimension)
	 * @see javax.swing.JDialog#setSize(Dimension)
	 * @param size
	 */
	void setSize(Dimension size);

	/**
	 * @return true dersom vindu er lagt til hovedvindu
	 */
	boolean isAdded();

	/**
	 * Setter at vinu er lagt til hovedvindu
	 * 
	 * @param added
	 */
	void setAdded(boolean added);

	/**
	 * Legger til komponentlytter
	 * 
	 * @param componentListener
	 */
	void addComponentListener(ComponentListener componentListener);

	/**
	 * Rydder opp
	 */
	void cleanUp();

	/**
	 * @return JDialog dersom klasse er av en slik type
	 */
	JDialog getJDialog();
	void remove(Component comp);
    void setTitle(String title);
}	
