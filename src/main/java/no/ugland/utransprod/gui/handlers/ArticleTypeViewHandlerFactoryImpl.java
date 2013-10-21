package no.ugland.utransprod.gui.handlers;

import java.util.List;

import no.ugland.utransprod.gui.Login;
import no.ugland.utransprod.model.ArticleType;
import no.ugland.utransprod.service.ManagerRepository;

import com.google.inject.Inject;

public class ArticleTypeViewHandlerFactoryImpl implements ArticleTypeViewHandlerFactory{
	private Login login;
	private ManagerRepository managerRepository;

	@Inject
	public ArticleTypeViewHandlerFactoryImpl(final Login aLogin,final ManagerRepository aManagerRepository){
		login=aLogin;
		managerRepository=aManagerRepository;
	}
	public ArticleTypeViewHandler create(List<ArticleType> usedArticles,
			boolean doUpdateConstructionType) {
		return new ArticleTypeViewHandler(login, managerRepository, usedArticles,doUpdateConstructionType);
	}

	public ArticleTypeViewHandler create(List<ArticleType> usedArticles) {
		return new ArticleTypeViewHandler(login, managerRepository, usedArticles);
	}

}
