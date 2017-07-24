package cn.tzy.wenda.controller;

import cn.tzy.wenda.model.HostHolder;
import cn.tzy.wenda.model.Message;
import cn.tzy.wenda.model.User;
import cn.tzy.wenda.model.ViewObject;
import cn.tzy.wenda.service.MessageService;
import cn.tzy.wenda.service.UserService;
import cn.tzy.wenda.util.WendaUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by tuzhenyu on 17-7-24.
 * @author tuzhenyu
 */
@Controller
public class MessageController {
    @Autowired
    private HostHolder hostHolder;

    @Autowired
    private UserService userService;

    @Autowired
    private MessageService messageService;

    @RequestMapping(path = "/msg/addMessage",method = RequestMethod.POST)
    @ResponseBody
    public String addMessage(@RequestParam("toName") String toName,@RequestParam("content") String content){
        Message message = new Message();
        if (hostHolder.getUser()==null)
            return WendaUtil.getJSONString(999,"未登录");
        else
            message.setFromId(hostHolder.getUser().getId());

        User user = userService.getUserByName(toName);
        if (user==null)
            return WendaUtil.getJSONString(1,"用户不存在");
        else
            message.setToId(user.getId());

        message.setContent(content);
        message.setCreatedDate(new Date());
        message.setHasRead(0);
        messageService.addMessage(message);

        return WendaUtil.getJSONString(0);
    }

    @RequestMapping(path = "/msg/list",method = RequestMethod.GET)
    public String getConversationList(){
        return "letter";
    }

    @RequestMapping(path = "/msg/detail",method = RequestMethod.GET)
    public String getConversationDetail(Model model, @RequestParam("conversationId") String conversationId){
        List<Message> messageList = messageService.getConversationDatil(conversationId,0,10);
        List<ViewObject> messages = new ArrayList<>();
        for (Message message : messageList){
            ViewObject vo = new ViewObject();
            vo.set("message",message);
            vo.set("user",userService.getUser(message.getFromId()));
            messages.add(vo);
        }
        model.addAttribute("messages",messages);
        return "letterDetail";
    }
}
