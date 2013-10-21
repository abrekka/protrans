package no.ugland.utransprod.gui;

import no.ugland.utransprod.gui.model.ApplyListInterface;
import no.ugland.utransprod.model.ArticleType;
import no.ugland.utransprod.model.Order;

public interface ArticlePackageViewFactory {
	ArticleProductionPackageView create(ArticleType articleType,ApplyListInterface applyListInterface,String defaultColliName);
}
