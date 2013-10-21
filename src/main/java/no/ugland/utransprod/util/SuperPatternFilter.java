package no.ugland.utransprod.util;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

import org.apache.commons.lang.StringUtils;
import org.jdesktop.swingx.decorator.Filter;

/**
 * Filter for filtrering av flere kolonner i tabell.
 * @author atle.brekka
 */
public class SuperPatternFilter extends Filter {
    private List<Integer> toPrevious;

    Pattern pattern;

    String filterStr;

    MODE mode;

    private static final String UNKOWN_MODE = "unknown mode";

    /**
     * @author atle.brekka
     */
    public static enum MODE {
        LITERAL_FIND, REGEX_FIND, LITERAL_MATCH, REGEX_MATCH
    }

    public SuperPatternFilter(final int col) {
        super(col);
        setFilterStr(null, MODE.LITERAL_FIND);
    }

    public final boolean isFilterSetTo(final String rack, final MODE matchMode) {
        return StringUtils.equals(filterStr, rack) && mode == matchMode;
    }

    public final void setFilterStr(final String aFilterStr, final MODE aMode) {
        if (StringUtils.equals(this.filterStr, aFilterStr) && this.mode == aMode) {
            return;
        }
        this.filterStr = aFilterStr;
        this.mode = aMode;
        switch (aMode) {
        case LITERAL_FIND:
        case LITERAL_MATCH:
            break;
        case REGEX_FIND:
        case REGEX_MATCH:
            final String filterStr2;
            if (aFilterStr == null || aFilterStr.length() == 0) {
                filterStr2 = ".*";
            } else {
                filterStr2 = aFilterStr;
            }
            pattern = Pattern.compile(filterStr2, 0);
            break;
        default:
            throw new RuntimeException(UNKOWN_MODE);
        }
        refresh();
    }

    @Override
    protected final void reset() {
        toPrevious.clear();
        final int inputSize = getInputSize();
        fromPrevious = new int[inputSize];
        for (int i = 0; i < inputSize; i++) {
            fromPrevious[i] = -1;
        }
    }

    @Override
    protected final void filter() {
        final int inputSize = getInputSize();
        int current = 0;
        for (int i = 0; i < inputSize; i++) {
            if (test(i)) {
                toPrevious.add(i);
                fromPrevious[i] = current++;
            }
        }
    }

    //kan ikke sette final pga arv
    public boolean test(final int row) {
        final int colIdx = getColumnIndex();
        if (!adapter.isTestable(colIdx)) {
            return false;
        }
        return testValue((String) getInputValue(row, colIdx));
    }

    final boolean testValue(final String valueStr) {
        if (valueStr == null) {
            return false;
        }
        switch (mode) {
        case LITERAL_FIND:
            if (filterStr == null || filterStr.length() == 0) {
                return true;
            }
            return valueStr.toUpperCase().contains(filterStr.toUpperCase());

        case LITERAL_MATCH:
            if (filterStr == null || filterStr.length() == 0) {
                return true;
            }
            return filterStr.equals(valueStr);

        case REGEX_FIND:
            return pattern.matcher(valueStr).find();
        case REGEX_MATCH:
            return pattern.matcher(valueStr).matches();
        default:
            throw new RuntimeException(UNKOWN_MODE);
        }
    }

    @Override
    public final int getSize() {
        return toPrevious.size();
    }

    @Override
    protected final int mapTowardModel(final int row) {
        return toPrevious.size()>0?toPrevious.get(row):0;
    }

    @Override
    protected final void init() {
        toPrevious = new ArrayList<Integer>();
    }
}
