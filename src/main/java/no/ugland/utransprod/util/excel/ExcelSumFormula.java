package no.ugland.utransprod.util.excel;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class ExcelSumFormula {
    private List<Integer> sumColumns = new ArrayList<Integer>();

    private Integer fromRow;

    private Integer toRow;

    private String infoText;
    private String sumLineFormula;// G$ROW/C$ROW
    private Integer sumLineFormulaColumn;
    private Integer infoColumn;

    public ExcelSumFormula(final Integer[] aSumColumnArray, final String info, final String aSumLineFormula,
            final Integer aSumLineFormulaColumn,final Integer aInfoColumn) {
        boolean added = aSumColumnArray != null ? Collections.addAll(sumColumns, aSumColumnArray) : false;

        // sumColumn = aSumColumn;
        infoText = info;
        infoColumn=aInfoColumn;
        sumLineFormula = aSumLineFormula;
        sumLineFormulaColumn = aSumLineFormulaColumn;
    }

    public final Integer getFromRow() {
        if (fromRow != null) {
            return fromRow;
        }
        return 0;
    }

    public final void setFromRow(final Integer aFromRow) {
        this.fromRow = aFromRow;
    }

    public final Integer getToRow() {
        if (toRow != null) {
            return toRow;
        }
        return 0;
    }

    public final void setToRow(final Integer aToRow) {
        this.toRow = aToRow;
    }

    public final List<Integer> getSumColumns() {
        return sumColumns;
    }

    public final String getInfoText() {
        return infoText;
    }
    public final int getInfoColumn(){
        return infoColumn!=null?infoColumn:0;
    }

    public String getSumLineFormula() {
        if (toRow != null) {
            return sumLineFormula.replaceAll("\\$ROW", String.valueOf(toRow + 1));
        }
        return sumLineFormula;
    }

    public Integer getSumLineFormulaColumn() {
        return sumLineFormulaColumn;
    }

}
