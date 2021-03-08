
// Warning: No line numbers available in class file
package no.ugland.utransprod.gui.handlers;

import java.util.List;
import no.ugland.utransprod.model.ArticleType;

public interface ArticleTypeViewHandlerFactory {
   ArticleTypeViewHandler create(List<ArticleType> var1, boolean var2);

   ArticleTypeViewHandler create(List<ArticleType> var1);
}
