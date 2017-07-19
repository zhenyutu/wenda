package cn.tzy.wenda.service;

import cn.tzy.wenda.dao.UserDao;
import cn.tzy.wenda.model.User;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * Created by tuzhenyu on 17-7-19.
 * @author tuzhenyu
 */
public class UserService {
    @Autowired
    private UserDao userDao;

    public User getUser(int userId){
        return userDao.seletById(userId);
    }
}
