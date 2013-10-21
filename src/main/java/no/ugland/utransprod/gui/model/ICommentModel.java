package no.ugland.utransprod.gui.model;

/**
 * Interface for kommentarmodeller.
 * @author atle.brekka
 */
public interface ICommentModel {
    public static final String PROPERTY_USER_NAME = "userName";
    public static final String PROPERTY_COMMENT = "comment";
    int MAX_COMMENT_LENGTH = 1000;

    String getComment();
}
