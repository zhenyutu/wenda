package cn.tzy.wenda.controller;

import cn.tzy.wenda.model.HostHolder;
import cn.tzy.wenda.model.Question;
import cn.tzy.wenda.service.QuestionService;
import cn.tzy.wenda.util.WendaUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.util.WebUtils;

import java.util.Date;


/**
 * Created by tuzhenyu on 17-7-21.
 * @author tuzhenyu
 */
@Controller
public class QuestionController {
    @Autowired
    private QuestionService questionService;

    @Autowired
    private HostHolder hostHolder;

    @RequestMapping(path = "/question/add",method = RequestMethod.POST)
    @ResponseBody
    public String addQuestion(@RequestParam("title") String title,@RequestParam("content")String content){
        Question question = new Question();

        question.setTitle(title);
        question.setContent(content);
        question.setCreatedDate(new Date());
        question.setCommentCount(0);

        if (hostHolder.getUser()==null){
            question.setUserId(WendaUtil.ANONYMOUS_USER_ID);
        }else {
            question.setUserId(hostHolder.getUser().getId());
        }

        if (questionService.addQuestion(question)>0){
            return WendaUtil.getJSONString(0);
        }

        return WendaUtil.getJSONString(1);
    }
}
