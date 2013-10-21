package no.ugland.utransprod.service;

import java.util.List;

import no.ugland.utransprod.model.DrawerV;
import no.ugland.utransprod.model.ProductArea;
import no.ugland.utransprod.util.Periode;
import no.ugland.utransprod.util.report.DrawerGroup;

public interface DrawerVManager {
    List<DrawerGroup> groupByProductAreaPeriode(ProductArea productArea,Periode periode);
    List<DrawerV> findByProductAreaPeriode(List<Integer> groupIdxList,Periode periode);
}
