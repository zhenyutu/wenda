package cn.tzy.wenda.dao;

import cn.tzy.wenda.model.User;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

/**
 * Created by tuzhenyu on 17-7-19.
 * @author tuzhenyu
 */
@Mapper
public interface UserDao {
    String TABLE_NAEM = " user ";
    String INSERT_FIELDS = " name, password, salt, head_url ";
    String SELECT_FIELDS = " id, " + INSERT_FIELDS;

    @Insert({"insert into",TABLE_NAEM,"(",INSERT_FIELDS,") values (#{name},#{password},#{salt},#{headUrl})"})
    public void insertUser(User user);

    @Select({"select",SELECT_FIELDS,"from",TABLE_NAEM,"where id=#{id}"})
    public User seletById(int id);

    @Delete({"delete from",TABLE_NAEM,"where id=#{id}"})
    public void deleteById(int id);
}
