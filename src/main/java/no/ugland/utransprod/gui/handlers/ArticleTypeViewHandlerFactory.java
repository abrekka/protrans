package no.ugland.utransprod.gui.handlers;

import java.util.List;

import no.ugland.utransprod.model.ArticleType;

import com.google.inject.assistedinject.Assisted;

public interface ArticleTypeViewHandlerFactory {
	ArticleTypeViewHandler create(List<ArticleType> usedArticles,
			boolean doUpdateConstructionType);
	
	ArticleTypeViewHandler create(List<ArticleType> usedArticles);
}
