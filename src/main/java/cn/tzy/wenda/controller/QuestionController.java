package cn.tzy.wenda.controller;

import cn.tzy.wenda.dao.UserDao;
import cn.tzy.wenda.model.*;
import cn.tzy.wenda.service.CommentService;
import cn.tzy.wenda.service.LikeService;
import cn.tzy.wenda.service.QuestionService;
import cn.tzy.wenda.service.SensitiveService;
import cn.tzy.wenda.util.WendaUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.HtmlUtils;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;


/**
 * Created by tuzhenyu on 17-7-21.
 * @author tuzhenyu
 */
@Controller
public class QuestionController {
    @Autowired
    private QuestionService questionService;

    @Autowired
    private SensitiveService sensitiveService;

    @Autowired
    private HostHolder hostHolder;

    @Autowired
    private UserDao userDao;

    @Autowired
    private CommentService commentService;

    @Autowired
    private LikeService likeService;

    @RequestMapping(path = "/question/add",method = RequestMethod.POST)
    @ResponseBody
    public String addQuestion(@RequestParam("title") String title,@RequestParam("content")String content){
        Question question = new Question();

        question.setTitle(HtmlUtils.htmlEscape(title));
        question.setContent(HtmlUtils.htmlEscape(content));

        question.setTitle(sensitiveService.filter(question.getTitle()));
        question.setContent(sensitiveService.filter(question.getContent()));


        question.setCreatedDate(new Date());
        question.setCommentCount(0);

        if (hostHolder.getUser()==null){
            return WendaUtil.getJSONString(999);
        }else {
            question.setUserId(hostHolder.getUser().getId());
        }

        if (questionService.addQuestion(question)>0){
            return WendaUtil.getJSONString(0);
        }

        return WendaUtil.getJSONString(1);
    }

    @RequestMapping(path = "/question/{qId}")
    public String questionDetail(Model model,@PathVariable("qId")int qId){
        Question question = questionService.getQuestionById(qId);
        model.addAttribute("question",question);
        User user = userDao.seletById(question.getUserId());
        model.addAttribute("user",user);

        List<ViewObject> comments = new ArrayList<>();
        List<Comment> commentList = commentService.getCommentsByEntity(qId,0);
        for (Comment comment:commentList){
            ViewObject vo = new ViewObject();
            vo.set("comment",comment);
            if (hostHolder.getUser()==null)
                vo.set("liked",0);
            else
                vo.set("liked",likeService.getLikeStatus(hostHolder.getUser().getId(),comment.getEntityType(),comment.getId()));
            vo.set("likeCount",likeService.getLikeCount(comment.getEntityType(),comment.getId()));
            vo.set("user",userDao.seletById(comment.getUserId()));
            comments.add(vo);
        }
        model.addAttribute("comments",comments);

        return "detail";
    }
}
