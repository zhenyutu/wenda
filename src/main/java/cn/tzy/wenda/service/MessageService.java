package cn.tzy.wenda.service;

import cn.tzy.wenda.dao.MessageDao;
import cn.tzy.wenda.model.Message;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.util.HtmlUtils;

import java.util.List;

/**
 * Created by tuzhenyu on 17-7-24.
 * @author tuzhenyu
 */
@Service
public class MessageService {
    @Autowired
    private MessageDao messageDao;

    @Autowired
    private SensitiveService sensitiveService;

    public void addMessage(Message message){
        message.setContent(HtmlUtils.htmlEscape(message.getContent()));
        message.setContent(sensitiveService.filter(message.getContent()));
        messageDao.insertMessage(message);
    }

    public List<Message> getConversationDatil(String conversationId,int offset,int limit){
        return messageDao.selectConversationDetail(conversationId,offset,limit);
    }

    public List<Message> getConversationList(int userId, int offset, int limit){
        return messageDao.selectConversationList(userId,offset, limit);
    }
}
