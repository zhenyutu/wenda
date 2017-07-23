package cn.tzy.wenda.dao;

import cn.tzy.wenda.model.Comment;
import cn.tzy.wenda.model.Question;
import org.apache.ibatis.annotations.*;

import java.util.List;

/**
 * Created by tuzhenyu on 17-7-23.
 * @author tuzhenyu
 */
@Mapper
public interface CommentDao {
    String TABLE_NAEM = " comment ";
    String INSERT_FIELDS = " entity_id, entity_type, content, created_date, user_id, status ";
    String SELECT_FIELDS = " id, " + INSERT_FIELDS;

    @Insert({"insert into",TABLE_NAEM,"(",INSERT_FIELDS,") values (#{entityId},#{entityType}," +
            "#{content},#{createdDate},#{userId},#{status})"})
    int insertComment(Comment comment);

    @Select({"select",SELECT_FIELDS,"from",TABLE_NAEM,"where id=#{id}"})
    Comment seletById(int id);

    @Select({"select",SELECT_FIELDS,"from",TABLE_NAEM,"order by id desc limit #{offset},#{limit}"})
    List<Comment> selectLatestComments(@Param("offset") int offset, @Param("limit") int limit);

    @Select({"select",SELECT_FIELDS,"from",TABLE_NAEM,"where entity_id = #{entityId} and entity_type = #{entityType} " +
            "order by created_date desc"})
    List<Comment> selectCommentsByEntity(@Param("entityId") int entityId,@Param("entityType") int entityType);

    @Select({"select count(id) from",TABLE_NAEM,"where entity_id = #{entityId} and entity_type = #{entityType}"})
    int getCommentCount(@Param("entityId") int entityId,@Param("entityType") int entityType);

    @Update({"update",TABLE_NAEM,"set status = #{status} where id = #{commendId}"})
    void updateStatus(@Param("commendId") int commendId,@Param("status") int status);

    @Delete({"delete from",TABLE_NAEM,"where id=#{id}"})
    void deleteById(int id);
}
