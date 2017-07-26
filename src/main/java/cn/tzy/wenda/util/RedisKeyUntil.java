package cn.tzy.wenda.util;

/**
 * Created by tuzhenyu on 17-7-25.
 * @author tuzhenyu
 */
public class RedisKeyUntil {
    private static String SPLITE = ":";
    private static String BIZ_LIKE = "LIKE";
    private static String BIZ_DISLIKE = "DISLIKE";
    private static String BIZ_EVENTQUEUE = "EVENTQUEUE";

    public static String getLikeKey(int entityType,int entityId){
        return BIZ_LIKE+SPLITE+String.valueOf(entityType)+SPLITE+String.valueOf(entityId);
    }

    public static String getDisLikeKey(int entityType,int entityId){
        return BIZ_DISLIKE+SPLITE+String.valueOf(entityType)+SPLITE+String.valueOf(entityId);
    }

    public static String getEventQueue(){
        return BIZ_EVENTQUEUE;
    }
}
