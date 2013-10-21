package no.ugland.utransprod.util;

import org.apache.log4j.Logger;

/**
 * Filter som kan brukes på flere kolonner.
 * @author atle.brekka
 */
public class MultiColPatternFilter extends SuperPatternFilter {
    private static final Logger LOG = Logger
            .getLogger(MultiColPatternFilter.class);

    private final int[] cols;

    /**
     * @param someCols
     */
    public MultiColPatternFilter(final int... someCols) {
        super(0);
        final int numCols = someCols.length;
        this.cols = new int[numCols];
        System.arraycopy(someCols, 0, this.cols, 0, numCols);
    }

    /**
     * @see no.ugland.utransprod.util.SuperPatternFilter#test(int)
     */
    @Override
    public final boolean test(final int row) {
        for (int colIdx : cols) {
            if (adapter.isTestable(colIdx)) {
                final String valueStr = (String) getInputValue(row, colIdx);
                final boolean ret = testValue(valueStr);
                if (ret) {
                    return true;
                }
            } else {
                LOG.warn("column " + colIdx + " not testable");
                return false;
            }
        }
        return false;
    }
}
