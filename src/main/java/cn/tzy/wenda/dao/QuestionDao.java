package cn.tzy.wenda.dao;

import cn.tzy.wenda.model.Question;
import cn.tzy.wenda.model.User;
import org.apache.ibatis.annotations.*;

import java.util.List;

/**
 * Created by tuzhenyu on 17-7-19.
 * @author tuzhenyu
 */
@Mapper
public interface QuestionDao {
    String TABLE_NAEM = " question ";
    String INSERT_FIELDS = " title, content, created_Date, user_Id, comment_Count ";
    String SELECT_FIELDS = " id, " + INSERT_FIELDS;

    @Insert({"insert into",TABLE_NAEM,"(",INSERT_FIELDS,") values (#{title},#{content}," +
            "#{createdDate},#{userId},#{commentCount})"})
    public void insertQuestion(Question question);

    @Select({"select",SELECT_FIELDS,"from",TABLE_NAEM,"where id=#{id}"})
    public User seletById(int id);

    @Select({"select",SELECT_FIELDS,"from",TABLE_NAEM,"order by id desc limit #{offset},#{limit}"})
    public List<Question> selectLatestQuestions(@Param("offset") int offset,@Param("limit") int limit);

    @Select({"select",SELECT_FIELDS,"from",TABLE_NAEM,"where user_id = #{userId} order by id desc limit #{offset},#{limit}"})
    public List<Question> selectLatestQuestionsByUser(@Param("userId") int userId,@Param("offset") int offset,@Param("limit") int limit);

    @Delete({"delete from",TABLE_NAEM,"where id=#{id}"})
    public void deleteById(int id);
}
