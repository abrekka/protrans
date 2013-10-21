package no.ugland.utransprod.gui.action;

import no.ugland.utransprod.gui.WindowInterface;
import no.ugland.utransprod.gui.model.ApplyListInterface;
import no.ugland.utransprod.model.ArticleType;

public interface SetProductionUnitActionFactory {
	SetProductionUnitAction create(ArticleType aArticleType,ProduceableProvider aProduceableProvider,WindowInterface aWindow);
}
