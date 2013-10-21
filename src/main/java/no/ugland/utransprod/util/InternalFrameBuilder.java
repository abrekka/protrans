package no.ugland.utransprod.util;

import java.awt.Dimension;

import javax.swing.JInternalFrame;

import no.ugland.utransprod.gui.IconEnum;
import no.ugland.utransprod.gui.JInternalFrameAdapter;
import no.ugland.utransprod.gui.WindowInterface;

/**
 * Bygger interne vinduer.
 * @author abr99
 */
public final class InternalFrameBuilder {
    private InternalFrameBuilder() {

    }

    /**
     * Bygger internt vindu.
     * @param title
     * @param dimension
     * @param maximizable
     * @return internt vindu
     */
    public static WindowInterface buildInternalFrame(final String title,
            final Dimension dimension, final boolean maximizable) {
        JInternalFrame internalFrame = new JInternalFrame();
        internalFrame.setTitle(title);
        internalFrame.setClosable(false);
        internalFrame.setFrameIcon(IconEnum.ICON_UGLAND.getIcon());

        internalFrame.setResizable(true);
        internalFrame.setMaximizable(maximizable);
        internalFrame.setIconifiable(maximizable);
        if (dimension != null) {
            internalFrame.setPreferredSize(dimension);
        }

        internalFrame.setVisible(true);
        internalFrame.pack();
        JInternalFrameAdapter adapter = new JInternalFrameAdapter(internalFrame);
        return adapter;
    }

}
