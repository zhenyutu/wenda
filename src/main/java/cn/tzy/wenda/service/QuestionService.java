package cn.tzy.wenda.service;

import cn.tzy.wenda.dao.QuestionDao;
import cn.tzy.wenda.model.Question;

import java.util.List;

/**
 * Created by tuzhenyu on 17-7-19.
 * @author tuzhenyu
 */
public class QuestionService {
    private QuestionDao questionDao;

    public List<Question> getLatestQuestions(int userId,int offset, int limit){
        return questionDao.selectLatestQuestions(userId,offset,limit);
    }
}
