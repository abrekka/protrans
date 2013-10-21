package no.ugland.utransprod.gui;

import java.awt.Color;
import java.awt.Graphics;
import java.util.List;

import javax.swing.JPanel;

import com.jgoodies.forms.debug.FormDebugUtils;
import com.jgoodies.forms.layout.FormLayout;

/**
 * Gridpanel
 * 
 * @author atle.brekka
 * 
 */
public class FormPanelGrid extends JPanel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * The default color used to paint the form's debug grid.
	 */
	private static final Color DEFAULT_GRID_COLOR = Color.red;

	/**
	 * Specifies whether the grid shall be painted in the background. Is off by
	 * default and so the grid is painted in the foreground.
	 */
	private boolean paintInBackground;

	/**
	 * Specifies whether the container's diagonals should be painted.
	 */
	private boolean paintDiagonals;

	/**
	 * Holds the color used to paint the debug grid.
	 */
	private Color gridColor = DEFAULT_GRID_COLOR;

	/**
	 * 
	 */
	private List<Integer> noBorderColumns;

	// Instance Creation ****************************************************

	/**
	 * Constructs a FormDebugPanel with all options turned off.
	 * 
	 * @param noBorderColumnList
	 */
	public FormPanelGrid(List<Integer> noBorderColumnList) {
		this((FormLayout) null);
		noBorderColumns = noBorderColumnList;
	}

	/**
	 * Constructs a FormDebugPanel on the given FormLayout instance that paints
	 * the grid in the foreground and paints no diagonals.
	 * 
	 * @param layout
	 *            the panel's FormLayout instance
	 */
	public FormPanelGrid(FormLayout layout) {
		this(layout, false, false);
	}

	/**
	 * Constructs a FormDebugPanel on the given FormLayout using the specified
	 * settings that are otherwise turned off.
	 * 
	 * @param paintInBackground
	 *            true to paint grid lines in the background, false to paint the
	 *            grid in the foreground
	 * @param paintDiagonals
	 *            true to paint diagonals, false to not paint them
	 */
	public FormPanelGrid(boolean paintInBackground, boolean paintDiagonals) {
		this(null, paintInBackground, paintDiagonals);
	}

	/**
	 * Constructs a FormDebugPanel on the given FormLayout using the specified
	 * settings that are otherwise turned off.
	 * 
	 * @param layout
	 *            the panel's FormLayout instance
	 * @param paintInBackground
	 *            true to paint grid lines in the background, false to paint the
	 *            grid in the foreground
	 * @param paintDiagonals
	 *            true to paint diagonals, false to not paint them
	 */
	public FormPanelGrid(FormLayout layout, boolean paintInBackground,
			boolean paintDiagonals) {
		super(layout);
		setPaintInBackground(paintInBackground);
		setPaintDiagonals(paintDiagonals);
		setGridColor(DEFAULT_GRID_COLOR);
	}

	// Accessors ************************************************************

	/**
	 * Specifies to paint in background or foreground.
	 * 
	 * @param b
	 *            true to paint in the background, false for the foreground
	 */
	public void setPaintInBackground(boolean b) {
		paintInBackground = b;
	}

	/**
	 * Enables or disables to paint the panel's diagonals.
	 * 
	 * @param b
	 *            true to paint diagonals, false to not paint them
	 */
	public void setPaintDiagonals(boolean b) {
		paintDiagonals = b;
	}

	/**
	 * Sets the debug grid's color.
	 * 
	 * @param color
	 *            the color used to paint the debug grid
	 */
	public void setGridColor(Color color) {
		gridColor = color;
	}

	// Painting *************************************************************

	/**
	 * Paints the component and - if background painting is enabled - the grid.
	 * If foreground painting is enabled, the grid will be painted in
	 * <code>#paint</code>.
	 * 
	 * @param g
	 *            the Graphics object to paint on
	 * 
	 * @see #paint(Graphics)
	 */
	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		if (paintInBackground) {
			paintGrid(g);
		}
	}

	/**
	 * Paints the panel. If the panel's layout manager is a FormLayout and
	 * foreground painting is enabled, it paints the form's grid lines. If the
	 * grid shall be painted in the background, the grid will be painted in
	 * <code>#paintComponent</code>.
	 * 
	 * @param g
	 *            the Graphics object to paint on
	 * 
	 * @see #paintComponent(Graphics)
	 */
	@Override
	public void paint(Graphics g) {
		super.paint(g);
		if (!paintInBackground) {
			paintGrid(g);
		}
	}

	/**
	 * Paints the form's grid lines and diagonals.
	 * 
	 * @param g
	 *            the Graphics object used to paint
	 */
	private void paintGrid(Graphics g) {
		if (!(getLayout() instanceof FormLayout)) {
			return;
		}
		FormLayout.LayoutInfo layoutInfo = FormDebugUtils.getLayoutInfo(this);
		int left = layoutInfo.getX();
		int top = layoutInfo.getY();
		int width = layoutInfo.getWidth();
		int height = layoutInfo.getHeight();

		g.setColor(gridColor);
		// Paint the column bounds.
		for (int col = 0; col < layoutInfo.columnOrigins.length; col++) {
			if (!noBorderColumns.contains(col)) {
				g.fillRect(layoutInfo.columnOrigins[col], top, 1, height);
			}
		}

		// Paint the row bounds.
		for (int row = 0; row < layoutInfo.rowOrigins.length; row++) {
			g.fillRect(left, layoutInfo.rowOrigins[row], width, 1);
		}

		if (paintDiagonals) {
			g.drawLine(left, top, left + width, top + height);
			g.drawLine(left, top + height, left + width, top);
		}
	}

}