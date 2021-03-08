
// Warning: No line numbers available in class file
package no.ugland.utransprod.gui.model;

public interface ICommentModel {
   String PROPERTY_USER_NAME = "userName";
   String PROPERTY_COMMENT = "comment";
   int MAX_COMMENT_LENGTH = 1000;

   String getComment();
}
