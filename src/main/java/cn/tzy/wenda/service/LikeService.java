package cn.tzy.wenda.service;

import cn.tzy.wenda.util.RedisKeyUntil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by tuzhenyu on 17-7-25.
 * @#author tuzhenyu
 */
@Service
public class LikeService {
    @Autowired
    private JedisService jedisService;

    public int getLikeStatus(int userId,int entityType,int entityId){
        String likeKey = RedisKeyUntil.getLikeKey(entityType,entityId);
        if (jedisService.sismember(likeKey,String.valueOf(userId))){
           return 1;
        }
        String disLikeKey = RedisKeyUntil.getDisLikeKey(entityType,entityId);
        return jedisService.sismember(disLikeKey,String.valueOf(userId))?-1:0;
    }

    public long like(int userId,int entityType,int entityId){
        String likeKey = RedisKeyUntil.getLikeKey(entityType,entityId);
        jedisService.sadd(likeKey,String.valueOf(userId));

        String disLikeKey = RedisKeyUntil.getDisLikeKey(entityType,entityId);
        jedisService.srem(disLikeKey,String.valueOf(userId));

        return jedisService.scard(likeKey);
    }

    public long dislike(int userId,int entityType,int entityId){
        String disLikeKey = RedisKeyUntil.getDisLikeKey(entityType,entityId);
        jedisService.sadd(disLikeKey,String.valueOf(userId));

        String likeKey = RedisKeyUntil.getLikeKey(entityType,entityId);
        jedisService.srem(likeKey,String.valueOf(userId));

        return jedisService.scard(likeKey);
    }

    public long getLikeCount(int entityType,int entityId){
        String likeKey = RedisKeyUntil.getLikeKey(entityType,entityId);
        return jedisService.scard(likeKey);
    }
}
