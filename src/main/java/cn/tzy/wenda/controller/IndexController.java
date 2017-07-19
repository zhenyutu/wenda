package cn.tzy.wenda.controller;

import cn.tzy.wenda.dao.UserDao;
import cn.tzy.wenda.model.Question;
import cn.tzy.wenda.model.User;
import cn.tzy.wenda.model.ViewObject;
import cn.tzy.wenda.service.QuestionService;
import cn.tzy.wenda.service.UserService;
import com.sun.javafx.sg.prism.NGShape;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.swing.text.View;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by tuzhenyu on 17-7-19.
 * @author tuzhenyu
 */
@Controller
public class IndexController {

    private UserService userService;
    private QuestionService questionService;

    @Autowired
    public IndexController(UserService userService,QuestionService questionService){
        this.userService = userService;
        this.questionService = questionService;
    }

    @RequestMapping(path = {"/","index"})
    public String index(Model model){
        List<Question> questions = questionService.getLatestQuestions(1,0,10);
        List<ViewObject> vos = new ArrayList<>();
        for (Question question: questions){
            ViewObject vo = new ViewObject();
            vo.set("question",question);
            vo.set("user",userService.getUser(question.getUserId()));
            vos.add(vo);
        }
        model.addAttribute("vos",vos);

        return "index";
    }
}
