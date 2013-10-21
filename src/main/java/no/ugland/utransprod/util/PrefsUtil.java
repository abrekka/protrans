package no.ugland.utransprod.util;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.prefs.Preferences;

import javax.swing.table.TableColumn;

import no.ugland.utransprod.gui.handlers.TableEnum;
import no.ugland.utransprod.model.ProductAreaGroup;

import org.apache.commons.lang.StringUtils;
import org.jdesktop.swingx.JXTable;
import org.jdesktop.swingx.table.TableColumnExt;

public final class PrefsUtil {
    private PrefsUtil() {

    }

    public static void setPrefArray(final String nodeName,
            final String variableName, final String[] array) {
        Preferences prefs = Preferences.userRoot().node(nodeName);
        prefs.put(variableName, arrayToString(array));

    }

    public static void clearUserPrefs(final String nodeName,
            final String variableName) {
        if (nodeName != null && variableName != null) {
            Preferences prefs = Preferences.userRoot().node(nodeName);
            prefs.remove(variableName);

        }
    }

    public static void setSystemPrefArray(final String nodeName,
            final String variableName, final String[] array) {
        Preferences prefs = Preferences.systemRoot().node(nodeName);
        prefs.put(variableName, arrayToString(array));

    }

    public static List<String> getPreferenceList(final String nodeName,
            final String variableName) {
        Preferences prefs = Preferences.userRoot().node(nodeName);
        String value = prefs.get(variableName, "");
        if (value.length() != 0) {
            return stringToList(value);
        }
        prefs = Preferences.systemRoot().node(nodeName);
        value = prefs.get(variableName, "");
        if (value.length() != 0) {
            return stringToList(value);
        }
        return null;
    }

    public static List<String> getSystemList(final String nodeName,
            final String variableName) {
        Preferences prefs = Preferences.systemRoot().node(nodeName);
        String value = prefs.get(variableName, "");
        if (value.length() != 0) {
            return stringToList(value);
        }
        return null;
    }

    public static void setInvisibleColumns(final String productAreaGroupName,
            final String tableName, final JXTable table) {
        
        List<TableColumn> columnList = table!=null?table.getColumns(true):new ArrayList<TableColumn>();
        List<String> invisibleColumns = getInvisibleColumns(productAreaGroupName,tableName);
                //.getInvisibleColumns(tableName);
        int columnCounter = 0;

        for (TableColumn column : columnList) {
            if (invisibleColumns.contains(column.getHeaderValue())) {
                ((TableColumnExt) column).setVisible(false);

            } else {
                ((TableColumnExt) column).setVisible(true);
            }
            columnCounter++;
        }
    }
    
    private static List<String> getInvisibleColumns(final String productAreaGroupName,
            final String tableName){
    	List<String> columnNames = getPreferenceList(productAreaGroupName, tableName);
    	
    	if(columnNames==null){
    		columnNames = TableEnum.valueOf(StringUtils.upperCase(tableName)).getInvisibleColumns();
    	}
    	return columnNames!=null?columnNames:new ArrayList<String>();
    }

    public static void putUserInvisibleColumns(final JXTable table,
            final ProductAreaGroup productAreaGroup) {
        if(table!=null){
        List<TableColumn> columns = table.getColumns(true);
        List<String> columnNames = new ArrayList<String>();

        if (columns != null) {
            for (TableColumn column : columns) {
                if (!((TableColumnExt) column).isVisible()) {
                    columnNames.add((String) column.getHeaderValue());
                }
            }
            //if (columnNames.size() != 0) {
            
                setPrefArray(productAreaGroup.getProductAreaGroupName(), table
                        .getName(), columnNames.toArray(new String[columnNames
                        .size()]));
            //}
        }
        }
    }

    private static String arrayToString(final String[] array) {
        if (array != null) {
            return Arrays.toString(array);
        }
        return "";
    }

    private static List<String> stringToList(final String stringList) {
        List<String> integerList = new ArrayList<String>();
        String tmpStringList = stringList;
        if (stringList != null && stringList.length() != 0) {
            tmpStringList = stringList.substring(1, stringList.length() - 1);
            List<String> strings = Arrays.asList(tmpStringList.split(","));
            for (String string : strings) {
                if (string.length() != 0) {
                    integerList.add(string.trim());
                }
            }
        }
        return integerList;
    }
}
