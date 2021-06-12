package ua.com.shyrkov.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ua.com.shyrkov.dao.StudentDao;
import ua.com.shyrkov.dto.LessonDto;
import ua.com.shyrkov.exception.EntityNotFoundException;

import javax.persistence.EntityManager;

public class StudentService {

    private final StudentDao studentDao;
    private final Logger logger = LoggerFactory.getLogger(StudentService.class);

    public StudentService(EntityManager entityManager) {
        this.studentDao = new StudentDao(entityManager);
    }

    public LessonDto getStudentsNextLesson(Long studentId){
        try {
            logger.info("Finding next lesson for student with id = " + studentId);
            return studentDao.getNextLesson(studentId);
        } catch (EntityNotFoundException e) {
            logger.error("Error was occurred during finding lesson: "+e.getMessage());
        }
        return null;
    }
}
