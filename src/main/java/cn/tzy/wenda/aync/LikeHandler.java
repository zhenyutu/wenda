package cn.tzy.wenda.aync;

import cn.tzy.wenda.model.Message;
import cn.tzy.wenda.model.User;
import cn.tzy.wenda.service.MessageService;
import cn.tzy.wenda.service.UserService;
import cn.tzy.wenda.util.WendaUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.Date;
import java.util.List;

/**
 * Created by tuzhenyu on 17-7-26.
 * @author tuzhenyu
 */
@Component
public class LikeHandler implements EventHandler{
    @Autowired
    private MessageService messageService;

    @Autowired
    private UserService userService;

    @Override
    public void doHandler(EventModel model) {
        Message message = new Message();
        message.setFromId(WendaUtil.ANONYMOUS_USER_ID);
        message.setToId(model.getEntityOwnerId());
        message.setCreatedDate(new Date());
        User user = userService.getUser(model.getActorId());
        message.setContent("用户"+user.getName()+"赞了你的评论,http://127.0.0.1:8008/question/"+model.getExts("questionId"));
        messageService.addMessage(message);
    }

    @Override
    public List<EventType> getSupportEventTypes() {
        return Arrays.asList(EventType.LIKE);
    }
}
