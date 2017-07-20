package cn.tzy.wenda.service;

import cn.tzy.wenda.dao.UserDao;
import cn.tzy.wenda.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by tuzhenyu on 17-7-19.
 * @author tuzhenyu
 */
@Service
public class UserService {
    @Autowired
    private UserDao userDao;

    public User getUser(int userId){
        return userDao.seletById(userId);
    }

    public void addUser(User user){
        userDao.insertUser(user);
    }
}
