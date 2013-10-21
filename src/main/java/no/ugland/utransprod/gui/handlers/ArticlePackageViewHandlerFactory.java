package no.ugland.utransprod.gui.handlers;

import no.ugland.utransprod.model.ArticleType;
import no.ugland.utransprod.model.Order;

public interface ArticlePackageViewHandlerFactory {
	ArticlePackageViewHandler create(ArticleType articleType,String defaultColliName);
}
