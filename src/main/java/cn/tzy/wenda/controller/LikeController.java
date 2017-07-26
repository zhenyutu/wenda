package cn.tzy.wenda.controller;

import cn.tzy.wenda.aync.EventModel;
import cn.tzy.wenda.aync.EventProducer;
import cn.tzy.wenda.aync.EventType;
import cn.tzy.wenda.model.Comment;
import cn.tzy.wenda.model.HostHolder;
import cn.tzy.wenda.model.User;
import cn.tzy.wenda.service.CommentService;
import cn.tzy.wenda.service.LikeService;
import cn.tzy.wenda.util.WendaUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * Created by tuzhenyu on 17-7-25.
 * @author tuzhenyu
 */
@Controller
public class LikeController {
    @Autowired
    private HostHolder hostHolder;

    @Autowired
    private LikeService likeService;

    @Autowired
    private EventProducer eventProducer;

    @Autowired
    private CommentService commentService;

    @RequestMapping(path = "/like",method = RequestMethod.POST)
    @ResponseBody
    public String like(@RequestParam("commentId")int commentId){
        User user = hostHolder.getUser();
       if (user==null){
           return WendaUtil.getJSONString(999);
       }
       Comment comment = commentService.getCommentById(commentId);
       eventProducer.fireEvent(new EventModel().setType(EventType.LIKE).setActorId(hostHolder.getUser().getId()
       ).setEntityId(commentId).setEntityType(0).setExts("questionId",String.valueOf(comment.getEntityId())).setEntityOwnerId(comment.getUserId()));

       long likeCount = likeService.like(user.getId(),0,commentId);
       return WendaUtil.getJSONString(0,String.valueOf(likeCount));
    }

    @RequestMapping(path = "/dislike",method = RequestMethod.POST)
    @ResponseBody
    public String dislike(@RequestParam("commentId")int commentId){
        User user = hostHolder.getUser();
        if (user==null){
            return WendaUtil.getJSONString(999);
        }
        long dislikeCount = likeService.dislike(user.getId(),0,commentId);
        return WendaUtil.getJSONString(0,String.valueOf(dislikeCount));
    }
}
