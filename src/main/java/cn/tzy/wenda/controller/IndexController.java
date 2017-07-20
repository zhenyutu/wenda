package cn.tzy.wenda.controller;

import cn.tzy.wenda.model.Question;
import cn.tzy.wenda.model.User;
import cn.tzy.wenda.model.ViewObject;
import cn.tzy.wenda.service.QuestionService;
import cn.tzy.wenda.service.UserService;
import com.sun.deploy.net.HttpResponse;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import java.util.*;

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
        List<Question> questions = questionService.getLatestQuestions(2,10);
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
    @RequestMapping("/in")
    public String in(){
        return "login";
    }

    @RequestMapping("/login")
    public String login(Model model, HttpServletResponse httpResponse,@RequestParam String username,@RequestParam String password){
        Map<String,String> map = userService.login(username,password);
        if (map.containsKey("ticket")) {
            Cookie cookie = new Cookie("ticket",map.get("ticket"));
            cookie.setPath("/");
            httpResponse.addCookie(cookie);

            return "redirect:/";
        }else {
            model.addAttribute("msg", map.get("msg"));
            return "login";
        }
    }

    @RequestMapping("/logout")
    public String logout(@CookieValue("ticket") String ticket){
        userService.logout(ticket);
        return "redirect:/";
    }

    @RequestMapping(path = "/register",method = RequestMethod.POST)
    public String register(Model model, HttpServletResponse httpResponse, @RequestParam String username, @RequestParam String password){
        Map<String,String> map = userService.register(username,password);
        if (map.containsKey("ticket")) {
            Cookie cookie = new Cookie("ticket",map.get("ticket"));
            cookie.setPath("/");
            httpResponse.addCookie(cookie);

            return "redirect:/";
        }else {
            model.addAttribute("msg", map.get("msg"));
            return "login";
        }
    }

    @RequestMapping("/user/{userId}")
    public String questionByUser(@PathVariable("userId") int userId,  Model model){
        System.out.println(userId);
        List<Question> questions = questionService.getLatestQuestionsByUser(userId,0,10);
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

    @RequestMapping("/user")
    @ResponseBody
    public String user(){
        Random random = new Random();
        for (int k=0;k<10;k++){
            User user = new User();
            user.setName(String.format("USER%d",k));
            user.setHeadUrl(String.format("https://images.nowcoder.com/head/%dm.png",random.nextInt(1000)));
            user.setPassword("xxxxxx");
            user.setSalt("");

            userService.addUser(user);
        }
        return "success";
    }

    @RequestMapping("/question")
    @ResponseBody
    public String question(){
        for (int i=0;i<10;i++){
            Question question = new Question();
            question.setCommentCount(i*10);
            Date date = new Date();
            date.setTime(date.getTime()+ 1000*3600*i);
            question.setCreatedDate(date);
            question.setUserId(2+i);
            question.setTitle(String.format("TITLE%d",i));
            question.setContent(String.format("LALALALALLAALALALALALALLA %d",i));

            questionService.addQuestion(question);
        }

        return "success";
    }
}
