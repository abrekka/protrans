package no.ugland.utransprod.dao;

import java.util.List;

import no.ugland.utransprod.model.DrawerV;
import no.ugland.utransprod.util.Periode;
import no.ugland.utransprod.util.report.DrawerGroup;

public interface DrawerVDAO extends DAO<DrawerV> {
    List<DrawerGroup> groupByProductAreaPeriode(List<Integer> productAreaNr, Periode periode);

    List<DrawerV> findByProductAreaPeriode(List<Integer> productAreaNrList, Periode periode);

    List<Integer> getDocumentIdByPeriode(Periode periode);
}
