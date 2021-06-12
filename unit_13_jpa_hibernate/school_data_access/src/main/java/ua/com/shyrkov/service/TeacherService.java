package ua.com.shyrkov.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ua.com.shyrkov.dao.TeacherDao;
import ua.com.shyrkov.dto.GroupDto;
import ua.com.shyrkov.exception.EntityNotFoundException;

import javax.persistence.EntityManager;

public class TeacherService {

    private final TeacherDao teacherDao;
    private final Logger logger = LoggerFactory.getLogger(TeacherService.class);

    public TeacherService(EntityManager entityManager) {
        this.teacherDao = new TeacherDao(entityManager);
    }

    public GroupDto findBestGroupByTeacher(Long teacherId) {
        try {
            logger.info("Finding the best group of teacher with id = " + teacherId);
            return teacherDao.getBestGroupByTeacher(teacherId);
        } catch (EntityNotFoundException e) {
            logger.error("Error was occurred during finding group: "+e.getMessage());
        }
        return null;
    }
}
