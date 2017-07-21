package cn.tzy.wenda.service;

import cn.tzy.wenda.dao.QuestionDao;
import cn.tzy.wenda.model.Question;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by tuzhenyu on 17-7-19.
 * @author tuzhenyu
 */
@Service
public class QuestionService {
    @Autowired
    private QuestionDao questionDao;

    public int addQuestion(Question question){
        return questionDao.insertQuestion(question)>0?question.getId():0;
    }

    public List<Question> getLatestQuestions(int offset, int limit){
        return questionDao.selectLatestQuestions(offset,limit);
    }

    public List<Question> getLatestQuestionsByUser(int userId,int offset, int limit){
        return questionDao.selectLatestQuestionsByUser(userId,offset,limit);
    }
}
