/*
 * Copyright (c) 2003-2006 JGoodies Karsten Lentzsch. All Rights Reserved.
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are met: o
 * Redistributions of source code must retain the above copyright notice, this
 * list of conditions and the following disclaimer. o Redistributions in binary
 * form must reproduce the above copyright notice, this list of conditions and
 * the following disclaimer in the documentation and/or other materials provided
 * with the distribution. o Neither the name of JGoodies Karsten Lentzsch nor
 * the names of its contributors may be used to endorse or promote products
 * derived from this software without specific prior written permission. THIS
 * SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS" AND
 * ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED
 * WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE
 * DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT OWNER OR CONTRIBUTORS BE LIABLE
 * FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL
 * DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR
 * SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER
 * CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY,
 * OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE
 * OF THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */

package no.ugland.utransprod.util;

import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.LayoutManager;
import java.awt.Point;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.Map;

import javax.swing.Icon;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JLayeredPane;
import javax.swing.JList;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JViewport;
import javax.swing.text.JTextComponent;

import com.jgoodies.validation.ValidationResult;
import com.jgoodies.validation.ValidationResultModel;
import com.jgoodies.validation.view.ValidationComponentUtils;
import com.jgoodies.validation.view.ValidationResultViewFactory;
import com.toedter.calendar.JDateChooser;

/**
 * Panel som ligger på toppen av orinalt panel for å vise eventuelle
 * valideringsfeil Can display validation feedback icons "over" a content panel.
 * It observes a ValidationResultModel and creates icon labels in a feedback
 * layer of a {@link JLayeredPane} on top of the content layer. To position the
 * feedback labels, the content pane is traversed and searched for text
 * components that match a validation message key in this panel's observed
 * ValidationResultModel.
 * <p>
 * <strong>Note:</strong> This panel doesn't reserve space for the portion used
 * to display the overlayed feedback components. It has been designed to not
 * change the layout of the wrapped content. Therefore you must reserve this
 * space, or in other words, you must ensure that the wrapped content provides
 * enough space to display the overlayed components. Since the current
 * implementation positions the overlay components in the lower left, just make
 * sure that there are about 6 pixel to the left and bottom of the input
 * components that can be marked.
 * <p>
 * This panel handles two event types:
 * <ol>
 * <li>the ValidationResultModel changes; in this case the set of visible
 * feedback components shall mark the input components that match the new
 * validation result. This is done by this class' internal
 * <code>ValidationResultChangeHandler</code> which in turn invokes
 * <code>#updateFeedbackComponents</code>.
 * <li>the content layout changes; the feedback components must then be
 * repositioned to reflect the position of the overlayed input components. This
 * is done by overriding <code>#validateTree</code> and invoking
 * <code>#repositionFeedBackComponents</code> after the child tree has been
 * layed out. The current simple but expensive implementation updates all
 * components.
 * </ol>
 * <p>
 */

public final class IconFeedbackPanel extends JLayeredPane {

    private static final long serialVersionUID = 1L;

    private static final int CONTENT_LAYER = 1;

    private static final int FEEDBACK_LAYER = 2;

    /**
     * Holds the ValidationResult and reports changes in that result. Used to
     * update the state of the feedback components.
     */
    private final ValidationResultModel model;

    /**
     * Refers to the content panel that holds the content components.
     */
    private final JComponent content;

    // Instance Creation ******************************************************

    /**
     * Creates an IconFeedbackPanel on the given ValidationResultModel using the
     * specified content panel.
     * <p>
     * <strong>Note:</strong> Typically you should wrap component trees with
     * {@link #getWrappedComponentTree(ValidationResultModel, JComponent)}, not
     * this constructor.
     * <p>
     * <strong>Note:</strong> You must not add or remove components from the
     * content once this constructor has been invoked.
     * 
     * @param aModel
     *            the ValidationResultModel to observe
     * @param aContent
     *            the panel that contains the content components
     * @throws NullPointerException
     *             if model or content is <code>null</code>.
     */
    @SuppressWarnings("synthetic-access")
    public IconFeedbackPanel(final ValidationResultModel aModel, final JComponent aContent) {
	if (aModel == null) {
	    throw new NullPointerException("The validation result model must not be null.");
	}
	if (aContent == null) {
	    throw new NullPointerException("The content must not be null.");
	}

	this.model = aModel;
	this.content = aContent;
	setLayout(new SimpleLayout());
	add(aContent, CONTENT_LAYER);
	initEventHandling();
    }

    // Convenience Code *******************************************************

    /**
     * Wraps the components in the given component tree with instances of
     * IconFeedbackPanel where necessary. Such a wrapper is required for all
     * JScrollPanes that contain multiple children and for the root - unless
     * it's a JScrollPane with multiple children.
     * 
     * @param model
     * @param root
     *            the root of the component tree to wrap
     * @return the wrapped component tree
     */
    public static JComponent getWrappedComponentTree(final ValidationResultModel model, final JComponent root) {
	wrapComponentTree(model, root);

	if (isScrollPaneWithUnmarkableView(root)) {
	    return root;
	}
	return new IconFeedbackPanel(model, root);
    }

    /**
     * @param model
     * @param container
     */
    private static void wrapComponentTree(final ValidationResultModel model, final Container container) {
	if (!(container instanceof JScrollPane)) {
	    int componentCount = container.getComponentCount();
	    for (int i = 0; i < componentCount; i++) {
		Component child = container.getComponent(i);
		if (child instanceof Container) {
		    wrapComponentTree(model, (Container) child);
		}
	    }
	    return;
	}
	JScrollPane scrollPane = (JScrollPane) container;
	JViewport viewport = scrollPane.getViewport();
	JComponent view = (JComponent) viewport.getView();
	if (isMarkable(view)) {
	    return;
	}
	Component wrappedView = new IconFeedbackPanel(model, view);
	viewport.setView(wrappedView);
	wrapComponentTree(model, view);
    }

    /**
     * @param c
     * @return true or false
     */
    private static boolean isScrollPaneWithUnmarkableView(final Component c) {
	if (!(c instanceof JScrollPane)) {
	    return false;
	}
	JScrollPane scrollPane = (JScrollPane) c;
	JViewport viewport = scrollPane.getViewport();
	JComponent view = (JComponent) viewport.getView();
	return !isMarkable(view);
    }

    // Initialization *********************************************************

    /**
     * Registers a listener with the validation result model that updates the
     * feedback components.
     */
    private void initEventHandling() {
	model.addPropertyChangeListener(ValidationResultModel.PROPERTYNAME_RESULT, new ValidationResultChangeHandler());
    }

    // Abstract Behavior ******************************************************

    /**
     * Creates and returns a validation feedback component that shall overlay
     * the specified content component.
     * <p>
     * This implementation returns a JLabel. The validation result's severity is
     * used to lookup the label's icon; the result's message text is set as the
     * label's tooltip text.
     * <p>
     * abstract superclass of general feedback overlay panels.
     * 
     * @param result
     *            determines the label's icon and tooltip text
     * @param contentComponent
     *            the component to get overlayed feedback
     * @return the feedback component that overlays the content component
     * @throws NullPointerException
     *             if the result is <code>null</code>
     */
    private JComponent createFeedbackComponent(final ValidationResult result, final Component contentComponent) {
	Icon icon = ValidationResultViewFactory.getSmallIcon(result.getSeverity());
	JLabel label = new JLabel(icon);
	label.setToolTipText(result.getMessagesText());
	label.setSize(label.getPreferredSize());
	return label;
    }

    /**
     * Computes and returns the origin of the given feedback component using the
     * content component's origin.
     * <p>
     * This implementation returns a JLabel. The validation result's severity is
     * used to lookup the label's icon; the result's message text is set as the
     * label's tooltip text.
     * <p>
     * abstract superclass of general feedback overlay panels.
     * 
     * @param feedbackComponent
     *            the component that overlays the content
     * @param contentComponent
     *            the component to get overlayed feedback
     * @return the feedback component's origin
     * @throws NullPointerException
     *             if the feedback component or content component is
     *             <code>null</code>
     */
    private Point getFeedbackComponentOrigin(final JComponent feedbackComponent, final Component contentComponent) {
	boolean isLTR = contentComponent.getComponentOrientation().isLeftToRight();
	int addX;
	if (isLTR) {
	    addX = 0;
	} else {
	    addX = contentComponent.getWidth() - 1;
	}
	int x = contentComponent.getX() + addX - feedbackComponent.getWidth() / 2;
	int y = contentComponent.getY() + contentComponent.getHeight() - feedbackComponent.getHeight() + 2;

	return new Point(x, y);
    }

    // Updating the Overlay Components ****************************************

    private void removeAllFeedbackComponents() {
	int componentCount = getComponentCount();
	for (int i = componentCount - 1; i >= 0; i--) {
	    Component child = getComponent(i);
	    int layer = getLayer(child);
	    if (layer == FEEDBACK_LAYER) {
		remove(i);
	    }
	}
    }

    /**
     * Traverses the component tree starting at the given container and creates
     * a feedback component for each JTextComponent that is associated with a
     * message in the specified <code>keyMap</code>.
     * <p>
     * The arguments passed to the feedback component creation method are the
     * visited component and its associated validation subresult. This subresult
     * is requested from the specified <code>keyMap</code> using the visited
     * component's message key.
     * 
     * @param container
     *            the component tree root
     * @param keyMap
     *            maps messages keys to associated validation results
     * @param xOffset
     * @param yOffset
     */
    @SuppressWarnings("unchecked")
    private void visitComponentTree(final Container container, final Map keyMap, final int xOffset, final int yOffset) {
	int componentCount = container.getComponentCount();
	for (int i = 0; i < componentCount; i++) {
	    Component child = container.getComponent(i);
	    if (!child.isVisible()) {
		continue;
	    }
	    if (isMarkable(child)) {
		if (isScrollPaneView(child)) {
		    Component containerParent = container.getParent();
		    addFeedbackComponent(containerParent, (JComponent) child, keyMap, xOffset - containerParent.getX(),
			    yOffset - containerParent.getY());
		} else {
		    addFeedbackComponent(child, (JComponent) child, keyMap, xOffset, yOffset);
		}

	    } else if (child instanceof Container) {
		visitComponentTree((Container) child, keyMap, xOffset + child.getX(), yOffset + child.getY());
	    }
	}
    }

    /**
     * @param c
     * @return true dersom scrollpaneview
     */
    private static boolean isScrollPaneView(final Component c) {
	Container container = c.getParent();
	Container containerParent = container.getParent();
	return (container instanceof JViewport) && (containerParent instanceof JScrollPane);
    }

    /**
     * Checks and answers if the given component can be marked or not.
     * <p>
     * <p>
     * 
     * @param component
     *            the component to be checked
     * @return true if the given component can be marked, false if not
     */
    private static boolean isMarkable(final Component component) {
	return component instanceof JTextComponent || component instanceof JComboBox || component instanceof JDateChooser
		|| component instanceof JList || component instanceof JRadioButton;
    }

    /**
     * @param contentComponent
     * @param messageComponent
     * @param keyMap
     * @param xOffset
     * @param yOffset
     */
    @SuppressWarnings("unchecked")
    private void addFeedbackComponent(final Component contentComponent, final JComponent messageComponent, final Map keyMap, final int xOffset,
	    final int yOffset) {
	ValidationResult result = getAssociatedResult(messageComponent, keyMap);
	JComponent feedbackComponent = createFeedbackComponent(result, contentComponent);
	if (feedbackComponent == null) {
	    return;
	}
	add(feedbackComponent, new Integer(FEEDBACK_LAYER));
	Point overlayPosition = getFeedbackComponentOrigin(feedbackComponent, contentComponent);
	overlayPosition.translate(xOffset, yOffset);
	feedbackComponent.setLocation(overlayPosition);
    }

    /**
     * Returns the ValidationResult associated with the given component using
     * the specified validation result key map.
     * 
     * @param comp
     *            the component may be marked with a validation message key
     * @param keyMap
     *            maps validation message keys to ValidationResults
     * @return the ValidationResult associated with the given component as
     *         provided by the specified validation key map
     */
    @SuppressWarnings("unchecked")
    private static ValidationResult getAssociatedResult(final JComponent comp, final Map keyMap) {
	Object messageKey = ValidationComponentUtils.getMessageKey(comp);
	if ((messageKey == null) || (keyMap == null)) {
	    return ValidationResult.EMPTY;
	}
	ValidationResult result = (ValidationResult) keyMap.get(messageKey);
	if (result == null) {
	    return ValidationResult.EMPTY;
	}
	return result;
    }

    // Event Handling *********************************************************

    private void updateFeedbackComponents() {
	removeAllFeedbackComponents();
	visitComponentTree(content, model.getResult().keyMap(), 0, 0);
	repaint();
    }

    /**
     * Ensures that the feedback components are repositioned. Invoked by
     * <code>#validate</code>, i. e. if this panel is layed out.
     * <p>
     * implementation removes all components and re-adds them later.
     */
    private void repositionFeedbackComponents() {
	updateFeedbackComponents();
    }

    /**
     * Recursively descends the container tree and recomputes the layout for any
     * subtrees marked as needing it (those marked as invalid). In addition to
     * the superclass behavior, we reposition the feedback components after the
     * child components have been validated.
     * <p>
     * We reposition the feedback components only, if this panel is visible; if
     * it becomes visible, #validateTree will be invoked.
     * 
     * @see Container#validateTree()
     * @see #validate()
     * @see #invalidate()
     * @see #doLayout()
     * @see Component#setVisible(boolean)
     * @see LayoutManager
     */
    @Override
    protected void validateTree() {
	super.validateTree();
	if (isVisible()) {
	    repositionFeedbackComponents();
	}
    }

    /**
     * Gets notified when the ValidationResult changed and updates the feedback
     * components.
     */
    final class ValidationResultChangeHandler implements PropertyChangeListener {

	/**
	 * @see java.beans.PropertyChangeListener#propertyChange(java.beans.PropertyChangeEvent)
	 */
	@SuppressWarnings("synthetic-access")
	public void propertyChange(final PropertyChangeEvent evt) {
	    updateFeedbackComponents();
	}

    }

    // Layout *****************************************************************

    /**
     * Used to lay out the content layer in the icon feedback JLayeredPane. The
     * content fills the parent's space; minimum and preferred size of this
     * layout are requested from the content panel.
     */
    private final class SimpleLayout implements LayoutManager {

	/**
	 * If the layout manager uses a per-component string, adds the component
	 * <code>comp</code> to the layout, associating it with the string
	 * specified by <code>name</code>.
	 * 
	 * @param name
	 *            the string to be associated with the component
	 * @param comp
	 *            the component to be added
	 */
	public void addLayoutComponent(final String name, final Component comp) {
	    // components are well known by the container
	}

	/**
	 * Removes the specified component from the layout.
	 * 
	 * @param comp
	 *            the component to be removed
	 */
	public void removeLayoutComponent(final Component comp) {
	    // components are well known by the container
	}

	/**
	 * Calculates the preferred size dimensions for the specified container,
	 * given the components it contains.
	 * 
	 * @param parent
	 *            the container to be laid out
	 * @return the preferred size of the given container
	 * @see #minimumLayoutSize(Container)
	 */
	@SuppressWarnings("synthetic-access")
	public Dimension preferredLayoutSize(final Container parent) {
	    return content.getPreferredSize();
	}

	/**
	 * Calculates the minimum size dimensions for the specified container,
	 * given the components it contains.
	 * 
	 * @param parent
	 *            the component to be laid out
	 * @return the minimum size of the given container
	 * @see #preferredLayoutSize(Container)
	 */
	@SuppressWarnings("synthetic-access")
	public Dimension minimumLayoutSize(final Container parent) {
	    return content.getMinimumSize();
	}

	/**
	 * Lays out the specified container.
	 * 
	 * @param parent
	 *            the container to be laid out
	 */
	@SuppressWarnings("synthetic-access")
	public void layoutContainer(final Container parent) {
	    Dimension size = parent.getSize();
	    content.setBounds(0, 0, size.width, size.height);
	}

    }

}
