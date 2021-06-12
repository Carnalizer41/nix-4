package ua.com.shyrkov.dao;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ua.com.shyrkov.dto.GroupDto;
import ua.com.shyrkov.entity.Group;
import ua.com.shyrkov.entity.Student;
import ua.com.shyrkov.entity.Teacher;
import ua.com.shyrkov.exception.EntityNotFoundException;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.TypedQuery;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class TeacherDao {

    private final EntityManager entityManager;
    private final Logger logger = LoggerFactory.getLogger(StudentDao.class);

    public TeacherDao(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    public GroupDto getBestGroupByTeacher(Long teacherId) throws EntityNotFoundException {
        EntityTransaction transaction = entityManager.getTransaction();
        transaction.begin();
        try {
            Teacher teacher = entityManager.find(Teacher.class, teacherId);
            if (teacher == null)
                throw new EntityNotFoundException("Can`t find teacher with id = " + teacherId);

            TypedQuery<Group> query =
                    entityManager.createQuery("select g from Group g " +
                                                      "where g.teacher.id = :teacherId", Group.class);
            query.setParameter("teacherId", teacherId);

            if(query.getResultList().isEmpty())
                throw new EntityNotFoundException("There are no group with teacher id = "+teacherId);

            List<Group> resultList = query.getResultList();
            List<GroupDto> groupDtos = resultList.stream().map(TeacherDao::groupToDto)
                                                 .sorted((o1, o2) -> o2.getMedianMark().compareTo(o1.getMedianMark()))
                                                 .collect(Collectors.toList());

            transaction.commit();
            return groupDtos.get(0);
        } catch (RuntimeException e) {
            logger.atError().setCause(e).log("Data layer operation failed");
            transaction.rollback();
            throw e;
        }
    }

    private static GroupDto groupToDto(Group group) {
        Double median = 0.0;
        List<Double> marks = new ArrayList<>();
        for (Student student : group.getStudents()) {
            marks.add(student.getMark().getMark());
        }
        Collections.sort(marks);
        median = marks.get(marks.size()/2);
        return new GroupDto(group.getId(),
                            group.getName(),
                            group.getCourse(),
                            median);
    }
}
