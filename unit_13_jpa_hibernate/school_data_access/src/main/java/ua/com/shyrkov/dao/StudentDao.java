package ua.com.shyrkov.dao;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ua.com.shyrkov.dto.LessonDto;
import ua.com.shyrkov.entity.Lesson;
import ua.com.shyrkov.entity.Student;
import ua.com.shyrkov.exception.EntityNotFoundException;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.TypedQuery;
import java.sql.Date;

public class StudentDao {

    private final EntityManager entityManager;
    private final Logger logger = LoggerFactory.getLogger(StudentDao.class);

    public StudentDao(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    public LessonDto getNextLesson(Long studentId) throws EntityNotFoundException {
        EntityTransaction transaction = entityManager.getTransaction();
        transaction.begin();
        try {
            Student student = entityManager.find(Student.class, studentId);
            if (student == null)
                throw new EntityNotFoundException("Can`t find student with id = " + studentId);
            TypedQuery<Lesson> query =
                    entityManager.createQuery("select l from Lesson l " +
                                                      "where l.group.id = :groupId " +
                                                      "and l.date >= :currentDate " +
                                                      "order by l.date asc, l.time asc", Lesson.class);
            query.setMaxResults(1);
            query.setParameter("groupId", student.getGroup().getId());
            query.setParameter("currentDate", new Date(System.currentTimeMillis()));

            LessonDto lessonDto = lessonToDto(query.getSingleResult());
            transaction.commit();
            return lessonDto;
        } catch (RuntimeException e) {
            logger.atError().setCause(e).log("Data layer operation failed");
            transaction.rollback();
            throw e;
        }
    }

    private static LessonDto lessonToDto(Lesson lesson) {
        return new LessonDto(lesson.getId(),
                             lesson.getGroup().getCourse(),
                             lesson.getGroup(),
                             lesson.getSubject(),
                             lesson.getTeacher(),
                             lesson.getDate(),
                             lesson.getTime());
    }
}
