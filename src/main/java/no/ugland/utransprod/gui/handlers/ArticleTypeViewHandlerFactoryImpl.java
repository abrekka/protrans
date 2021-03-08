/*    */ package no.ugland.utransprod.gui.handlers;
/*    */ 
/*    */ import com.google.inject.Inject;
/*    */ import java.util.List;
/*    */ import no.ugland.utransprod.gui.Login;
/*    */ import no.ugland.utransprod.model.ArticleType;
/*    */ import no.ugland.utransprod.service.ManagerRepository;
/*    */ 
/*    */ 
/*    */ 
/*    */ public class ArticleTypeViewHandlerFactoryImpl implements ArticleTypeViewHandlerFactory {
/*    */    private Login login;
/*    */    private ManagerRepository managerRepository;
/*    */ 
/*    */    @Inject
/*    */    public ArticleTypeViewHandlerFactoryImpl(Login aLogin, ManagerRepository aManagerRepository) {
/* 17 */       this.login = aLogin;
/* 18 */       this.managerRepository = aManagerRepository;
/* 19 */    }
/*    */ 
/*    */    public ArticleTypeViewHandler create(List<ArticleType> usedArticles, boolean doUpdateConstructionType) {
/* 22 */       return new ArticleTypeViewHandler(this.login, this.managerRepository, usedArticles, doUpdateConstructionType);
/*    */    }
/*    */ 
/*    */    public ArticleTypeViewHandler create(List<ArticleType> usedArticles) {
/* 26 */       return new ArticleTypeViewHandler(this.login, this.managerRepository, usedArticles);
/*    */    }
/*    */ }
