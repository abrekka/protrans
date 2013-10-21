package no.ugland.utransprod.model;

import java.util.List;

public interface IComment {
    String getUserName();
    void setUserName(String userName);
    String getComment();
    void setComment(String comment);
    List<CommentType> getCommentTypes();
    void setCommentTypes(List<CommentType> commentTypes);
    void addCommentType(CommentType commentType);
    void removeCommentType(CommentType commentType);
    //Integer getForTransport();
    //void setForTransport(Integer forTransport);
    //Integer getCommentType();
    //void setCommentType(Integer commentType);
    //Integer getForPackage();
    //void setForPackage(Integer forPackage);
}
