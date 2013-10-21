package no.ugland.utransprod.dao;

import no.ugland.utransprod.model.ProductArea;
import no.ugland.utransprod.model.SaleStatusOrderReserveV;

public interface SaleStatusOrderReserveVDAO {

	SaleStatusOrderReserveV findByProductArea(ProductArea productArea);

}
