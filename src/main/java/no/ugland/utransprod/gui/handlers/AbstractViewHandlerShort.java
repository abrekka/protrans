package no.ugland.utransprod.gui.handlers;

import java.awt.Dimension;

import javax.swing.table.TableModel;

import no.ugland.utransprod.gui.WindowInterface;
import no.ugland.utransprod.model.AccidentParticipant;
import no.ugland.utransprod.model.UserType;
import no.ugland.utransprod.service.OverviewManager;

import org.jdesktop.swingx.JXTable;

import com.jgoodies.binding.PresentationModel;

/**
 * AbstractViewHandler som har implementert en del av metodene slik at klasser
 * som arver ikke trenger å ha masse metoder som er tomme
 * @author atle.brekka
 * @param <T>
 * @param <E>
 */
public abstract class AbstractViewHandlerShort<T, E> extends
        AbstractViewHandler<T, E> {

    /**
     * @param aHeading
     * @param managerName
     * @param oneObject
     * @param aUserType
     * @param useDisposeOnClose
     */
    public AbstractViewHandlerShort(final String aHeading, final OverviewManager<T> aOverviewManager,
            final boolean oneObject, final UserType aUserType, final boolean useDisposeOnClose) {
        super(aHeading, aOverviewManager, oneObject, aUserType, useDisposeOnClose);
    }

   

    /**
     * Tom implementasjon
     * @param object
     * @param presentationModel
     * @param window1
     * @return feilmelding
     * @see no.ugland.utransprod.gui.handlers.AbstractViewHandler#checkSaveObject(java.lang.Object,
     *      com.jgoodies.binding.PresentationModel,
     *      no.ugland.utransprod.gui.WindowInterface)
     */
    @Override
    public final CheckObject checkSaveObject(final E object,
            final PresentationModel presentationModel, final WindowInterface window1) {
        return null;
    }



    /**
     * @see no.ugland.utransprod.gui.handlers.AbstractViewHandler#getNewObject()
     */
    @Override
    public final T getNewObject() {
        return null;
    }

    @Override
    public final TableModel getTableModel(WindowInterface window1) {
        return null;
    }

    /**
     * @see no.ugland.utransprod.gui.handlers.AbstractViewHandler#getTableWidth()
     */
    @Override
    public final String getTableWidth() {
        return null;
    }

    /**
     * @see no.ugland.utransprod.gui.handlers.AbstractViewHandler#getTitle()
     */
    @Override
    public final String getTitle() {
        return null;
    }

    /**
     * @see no.ugland.utransprod.gui.handlers.AbstractViewHandler#getWindowSize()
     */
    @Override
    public final Dimension getWindowSize() {
        return null;
    }

    /**
     * @see no.ugland.utransprod.gui.handlers.AbstractViewHandler#setColumnWidth(org.jdesktop.swingx.JXTable)
     */
    @Override
    public final void setColumnWidth(final JXTable table) {
    }

    @Override
    String getAddString() {
        return null;
    }
    
    @Override
    public String getAddRemoveString() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public String getClassName() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    void afterSaveObject(T object, WindowInterface window) {
        // TODO Auto-generated method stub
        
    }
}
