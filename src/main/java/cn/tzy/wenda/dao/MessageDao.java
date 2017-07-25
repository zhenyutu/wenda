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
    String GROUP_FIELDS = " max(from_id)from_id, max(to_id)to_id, max(content)content, max(created_date)created_date, max(has_read)has_read, conversation_id ";
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

    @Select({"select",GROUP_FIELDS,",count(id) as id from (select * from ",TABLE_NAEM,"where from_id=${userId} or to_id=${userId} order by created_date desc) tt group by tt.conversation_id " +
            "order by created_date desc limit #{offset},#{limit}"})
    List<Message> selectConversationList(@Param("userId") int userId,@Param("offset") int offset, @Param("limit") int limit);

    @Select({"select count(id) from",TABLE_NAEM,"where to_id=#{userId} and has_read=0 and conversation_id=#{conversationId}"})
    int getConversationCount(@Param("userId")int userId,@Param("conversationId")String conversationId);
}
