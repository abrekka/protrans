package no.ugland.utransprod.service.impl;

import java.io.Serializable;

import no.ugland.utransprod.dao.CommentTypeDAO;
import no.ugland.utransprod.model.CommentType;
import no.ugland.utransprod.service.CommentTypeManager;

public class CommentTypeManagerImpl extends ManagerImpl<CommentType>
        implements CommentTypeManager {

    @Override
    protected Serializable getObjectId(CommentType object) {
        return object.getCommentTypeId();
    }


}
