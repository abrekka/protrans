package no.ugland.utransprod.util;

import java.util.Hashtable;
import java.util.List;
import java.util.Map;
import java.util.Set;

import no.ugland.utransprod.model.CommentType;
import no.ugland.utransprod.service.CommentTypeManager;

public class CommentTypeUtil {
    private static Map<String,CommentType> commentTypeMap;
    public static CommentType getCommentType(String commentTypeName){
        initCommentTypes();
        return commentTypeMap.get(commentTypeName);
    }
    public static boolean hasCommentType(List<CommentType> commentTypes,String commentTypeName){
        CommentType commentType = getCommentType(commentTypeName);
        if(commentType!=null){
            return commentTypes.contains(commentType);
        }
        return false;
    }
    private static void initCommentTypes(){
        if(commentTypeMap==null){
            commentTypeMap=new Hashtable<String, CommentType>();
            CommentTypeManager commentTypeManager=(CommentTypeManager)ModelUtil.getBean("commentTypeManager");
            List<CommentType> commentTypes = commentTypeManager.findAll();
            if(commentTypes!=null){
                for(CommentType commentType:commentTypes){
                    commentTypeMap.put(commentType.getCommentTypeName(), commentType);
                }
            }
        }
    }
}
