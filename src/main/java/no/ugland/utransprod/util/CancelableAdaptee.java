package no.ugland.utransprod.util;

import java.awt.event.ActionListener;

import javax.swing.JComponent;

/**
 * A common interface that the CancelableProgressAdapter can use to hook into.
 * <p>
 * There are multiple panels that handle progress, the CancelableProgressAdapter
 * can hook into any panel that implements this interface.
 * @author Michael Bushe michael@bushe.com
 */
public interface CancelableAdaptee {
    /**
     * Starts the animation.
     */
    void start();

    /**
     * Stops the animation.
     */
    void stop();

    /**
     * Sets the text in the animation.
     * @param text
     */
    void setText(String text);

    /**
     * Gets the interface as a JComponent (usually returns "this").
     * @return komponent
     */
    JComponent getComponent();

    /**
     * Adds a listener to the cancel button. Usually delegated to the
     * CancelableProgressAdapter.
     * @param listener
     */
    void addCancelListener(ActionListener listener);

    /**
     * Removes a listener from the cancel button. Usually delegated to the
     * CancelableProgressAdapter.
     * @param listener
     */
    void removeCancelListener(ActionListener listener);
}
