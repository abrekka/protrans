package no.ugland.utransprod.dao;

import no.ugland.utransprod.model.ImportOrderV;

public interface ImportOrderVDAO extends DAO<ImportOrderV> {
    ImportOrderV findByNumber1(String number1);
}
