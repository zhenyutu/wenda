package cn.tzy.wenda.service;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.stereotype.Service;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

/**
 * Created by tuzhenyu on 17-7-25.
 * @author tuzhenyu
 */
@Service
public class JedisService implements InitializingBean{

    private JedisPool pool;

    @Override
    public void afterPropertiesSet() throws Exception {
        pool = new JedisPool("redis://localhost:6379/1");
    }

    public void sadd(String key,String value){
        Jedis jedis = pool.getResource();
        jedis.sadd(key,value);
        jedis.close();
    }

    public void srem(String key,String value){
        Jedis jedis = pool.getResource();
        jedis.srem(key,value);
        jedis.close();
    }

    public boolean sismember(String key,String value){
        Jedis jedis = pool.getResource();
        boolean result = jedis.sismember(key,value);
        jedis.close();

        return result;
    }

    public long scard(String key){
        Jedis jedis = pool.getResource();
        long result = jedis.scard(key);
        jedis.close();

        return result;
    }
}
