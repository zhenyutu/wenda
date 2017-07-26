package cn.tzy.wenda.service;

import cn.tzy.wenda.dao.CommentDao;
import cn.tzy.wenda.model.Comment;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.util.HtmlUtils;
import sun.security.krb5.internal.PAForUserEnc;

import java.util.List;

/**
 * Created by tuzhenyu on 17-7-23.
 * @author tuzhenyu
 */
@Service
public class CommentService {
    @Autowired
    private CommentDao commentDao;

    @Autowired
    private SensitiveService sensitiveService;

    public void addCommet(Comment comment){
        comment.setContent(HtmlUtils.htmlEscape(comment.getContent()));
        comment.setContent(sensitiveService.filter(comment.getContent()));
        commentDao.insertComment(comment);
    }

    public List<Comment> getCommentsByEntity(int entityId,int entityType){
        return commentDao.selectCommentsByEntity(entityId,entityType);
    }

    public int getCommentsCount(int entityId,int entityType){
        return commentDao.getCommentCount(entityId,entityType);
    }

    public void deleteComment(int commentId){
        commentDao.updateStatus(commentId,1);
    }

    public Comment getCommentById(int commentId){
        return commentDao.seletById(commentId);
    }
}
