package cn.tzy.wenda.controller;

import cn.tzy.wenda.dao.UserDao;
import cn.tzy.wenda.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * Created by tuzhenyu on 17-7-19.
 * @author tuzhenyu
 */
@Controller
public class IndexController {
    @Autowired
    private UserDao userDao;

    @RequestMapping("/")
    @ResponseBody
    public String index(){
        return "Hello World";
    }
}
