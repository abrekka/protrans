package no.ugland.utransprod.util.excel;

import java.util.List;
import java.util.Map;

import javax.swing.ListModel;
import javax.swing.table.TableModel;

import no.ugland.utransprod.ProTransException;
import no.ugland.utransprod.util.ModelUtil;

import org.jdesktop.swingx.JXTable;

import com.jgoodies.binding.adapter.AbstractTableAdapter;
import com.jgoodies.binding.list.ArrayListModel;

public class ExcelReportGenerator {
    private final ArrayListModel dataList;

    private TableModel tableModel;

    final ExcelReportEnum excelReportEnum;

    public ExcelReportGenerator(final ExcelReportEnum reportEnum) {
        dataList = new ArrayListModel();
        excelReportEnum = reportEnum;
    }

    public final JXTable getReportData(final ExcelReportSetting params) throws ProTransException{
        fillDataList(params);
        tableModel = new ExcelTableModel(dataList);

        return new JXTable(tableModel);
    }

    public final String getInfoButtom(final ExcelReportSetting params) throws ProTransException{
        ExcelManager excelManager = (ExcelManager) ModelUtil
                .getBean(excelReportEnum.getExcelManagerName());

        String info = excelManager.getInfoButtom(params);
        return info;
    }
    
    public final String getInfoTop(final ExcelReportSetting params) {
        ExcelManager excelManager = (ExcelManager) ModelUtil
                .getBean(excelReportEnum.getExcelManagerName());

        String info = excelManager.getInfoTop(params);
        return info;
    }

    private void fillDataList(final ExcelReportSetting params) throws ProTransException{
        ExcelManager excelManager = (ExcelManager) ModelUtil
                .getBean(excelReportEnum.getExcelManagerName());

        dataList.clear();
        List<?> data = excelManager.findByParams(params);
        if (data != null) {
            dataList.addAll(data);
        }
    }

    public final Map<Object, Object> getReportDataMap(
            final ExcelReportSetting params) throws ProTransException {
        ExcelManager excelManager = (ExcelManager) ModelUtil
                .getBean(excelReportEnum.getExcelManagerName());

        return excelManager.getReportDataMap(params);
    }

    private class ExcelTableModel extends AbstractTableAdapter {

        public ExcelTableModel(final ListModel listModel) {
            super(listModel, excelReportEnum.getColumnNames((List) listModel));
        }

        public Object getValueAt(final int row, final int column) {
            Object object = getRow(row);
            try {
                return excelReportEnum.getValueForColumn(object, column);
            } catch (Exception e) {
                e.printStackTrace();
            }
            return null;
        }

    }

}
