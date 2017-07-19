package cn.tzy.wenda.dao;

import cn.tzy.wenda.WebApplication;
import cn.tzy.wenda.model.User;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;

/**
 * Created by tuzhenyu on 17-7-19.
 * @author tuzhenyu
 */
@SpringApplicationConfiguration(classes = WebApplication.class)
public class UserDaoTest {
    @Autowired
    private UserDao userDao;

    @Test
    public void test(){
        User user = new User("user");
        userDao.insertUser(user);
        User user1 = userDao.seletById(1);
        System.out.println(user1.getName());
    }

}