package no.ugland.utransprod.service.impl;

import java.util.List;
import java.util.Map;

import no.ugland.utransprod.ProTransException;
import no.ugland.utransprod.dao.DocumentDAO;
import no.ugland.utransprod.dao.DrawerVDAO;
import no.ugland.utransprod.gui.handlers.CheckObject;
import no.ugland.utransprod.model.DrawerV;
import no.ugland.utransprod.model.ProductArea;
import no.ugland.utransprod.service.DrawerVManager;
import no.ugland.utransprod.util.Periode;
import no.ugland.utransprod.util.Util;
import no.ugland.utransprod.util.excel.ExcelManager;
import no.ugland.utransprod.util.excel.ExcelReportEnum;
import no.ugland.utransprod.util.excel.ExcelReportSetting;
import no.ugland.utransprod.util.report.DrawerGroup;

public class DrawerVManagerImpl implements DrawerVManager,ExcelManager {
    private DrawerVDAO dao;
    private DocumentDAO documentDao;

    /**
     * @param aDao
     */
    public final void setDrawerVDAO(final DrawerVDAO aDao) {
        this.dao = aDao;
    }
    public final void setDocumentDAO(final DocumentDAO aDao) {
        this.documentDao = aDao;
    }

    public List<DrawerGroup> groupByProductAreaPeriode(ProductArea productArea, Periode periode) {
        List<DrawerGroup>  drawerGroups=dao.groupByProductAreaPeriode(productArea.getProductAreaNrList(), periode);
        if(drawerGroups!=null){
            for(DrawerGroup drawerGroup:drawerGroups){
                drawerGroup.setProductArea(productArea);
            }
        }
        return drawerGroups;
    }

    public List<DrawerV> findByProductAreaPeriode(List<Integer> groupIdxList, Periode periode) {
        return dao.findByProductAreaPeriode(groupIdxList, periode);
    }

    public List<?> findByParams(ExcelReportSetting params) throws ProTransException {
        if(params.getExcelReportType()==ExcelReportEnum.DRAWER_REPORT){
            return groupByProductAreaPeriode(params.getProductArea(), params.getPeriode());
        }
            return findByProductAreaPeriode(params.getProductArea().getProductAreaNrList(), params.getPeriode());
        
    }

    public String getInfoButtom(ExcelReportSetting params) throws ProTransException {
        // TODO Auto-generated method stub
        return null;
    }

    public String getInfoTop(ExcelReportSetting params) {
        // TODO Auto-generated method stub
        return null;
    }

    public Map<Object, Object> getReportDataMap(ExcelReportSetting params) {
        // TODO Auto-generated method stub
        return null;
    }

    public CheckObject checkExcel(ExcelReportSetting params) {
        List<Integer> drawerDocumentIdList= dao.getDocumentIdByPeriode(params.getPeriode());
        List<Integer> documentIdList= documentDao.getDocumentIdByPeriode(params.getPeriode());
        if(drawerDocumentIdList.size()!=documentIdList.size()){
            List<Integer> diffList = Util.getDiff(documentIdList,drawerDocumentIdList);
            return new CheckObject("Dokumenter med id "+diffList.toString()+" mangler i rapport",true);
        }
        return null;
    }

}
