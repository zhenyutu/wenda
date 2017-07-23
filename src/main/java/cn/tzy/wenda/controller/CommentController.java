package cn.tzy.wenda.controller;

import cn.tzy.wenda.model.Comment;
import cn.tzy.wenda.model.HostHolder;
import cn.tzy.wenda.service.CommentService;
import cn.tzy.wenda.service.QuestionService;
import cn.tzy.wenda.util.WendaUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Date;

/**
 * Created by tuzhenyu on 17-7-23.
 * @author tuzhenyu
 */
@Controller
public class CommentController {
    @Autowired
    private CommentService commentService;

    @Autowired
    private QuestionService questionService;

    @Autowired
    private HostHolder hostHolder;

    @RequestMapping(path = "/addComment",method = RequestMethod.POST)
    public String addComment(@RequestParam("questionId") int questionId,@RequestParam("content")String content){
        Comment comment = new Comment();
        comment.setContent(content);
        comment.setCreatedDate(new Date());
        if (hostHolder.getUser()==null)
            comment.setUserId(WendaUtil.ANONYMOUS_USER_ID);
        else
            comment.setUserId(hostHolder.getUser().getId());
        comment.setEntityType(0);
        comment.setEntityId(questionId);
        comment.setStatus(0);
        commentService.addCommet(comment);

        int count = commentService.getCommentsCount(comment.getEntityId(),comment.getEntityType());
        questionService.updateCommentCount(questionId,count);

        return "redirect:/question/"+questionId;
    }
}
