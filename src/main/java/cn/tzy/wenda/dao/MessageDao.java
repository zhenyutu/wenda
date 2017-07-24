package cn.tzy.wenda.dao;

import cn.tzy.wenda.model.Comment;
import cn.tzy.wenda.model.Message;
import org.apache.ibatis.annotations.*;

import java.util.List;

/**
 * Created by tuzhenyu on 17-7-23.
 * @author tuzhenyu
 */
@Mapper
public interface MessageDao {
    String TABLE_NAEM = " message ";
    String INSERT_FIELDS = " from_id, to_id, content, created_date, has_read, conversation_id ";
    String SELECT_FIELDS = " id, " + INSERT_FIELDS;

    @Insert({"insert into",TABLE_NAEM,"(",INSERT_FIELDS,") values (#{fromId},#{toId}," +
            "#{content},#{createdDate},#{hasRead},#{conversationId})"})
    int insertMessage(Message message);

    @Select({"select",SELECT_FIELDS,"from",TABLE_NAEM,"where id=#{id}"})
    Message seletById(int id);

    @Select({"select",SELECT_FIELDS,"from",TABLE_NAEM,"order by created_date desc limit #{offset},#{limit}"})
    List<Message> selectLatestMessages(@Param("offset") int offset, @Param("limit") int limit);

    @Select({"select",SELECT_FIELDS,"from",TABLE_NAEM,"where conversation_id = #{conversationId} " +
            "order by created_date desc limit #{offset},#{limit}"})
    List<Message> selectConversationDetail(@Param("conversationId") String conversationId,@Param("offset") int offset, @Param("limit") int limit);

    @Select({"select count(id) from",TABLE_NAEM,"where entity_id = #{entityId} and entity_type = #{entityType}"})
    int getMessageCount(@Param("entityId") int entityId,@Param("entityType") int entityType);

}
