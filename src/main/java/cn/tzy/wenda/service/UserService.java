package cn.tzy.wenda.service;

import cn.tzy.wenda.dao.UserDao;
import cn.tzy.wenda.model.User;
import cn.tzy.wenda.util.WendaUtil;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.UUID;

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

    public Map<String,String> register(String username, String password){
        Map<String,String> map = new HashMap<>();
        Random random = new Random();
        if (StringUtils.isBlank(username)){
            map.put("msg","用户名不能为空");
            return map;
        }

        if (StringUtils.isBlank(password)){
            map.put("msg","密码不能为空");
            return map;
        }

        User u = userDao.seletByName(username);
        if (u!=null){
            map.put("msg","用户名已经被占用");
            return map;
        }

        User user = new User();
        user.setName(username);
        user.setSalt(UUID.randomUUID().toString().substring(0,5));
        user.setHeadUrl(String.format("https://images.nowcoder.com/head/%dm.png",random.nextInt(1000)));
        user.setPassword(WendaUtil.MD5(password+user.getSalt()));
        userDao.insertUser(user);

        return map;
    }
}
