package ua.com.shyrkov;


import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ua.com.shyrkov.dto.GroupDto;
import ua.com.shyrkov.dto.LessonDto;
import ua.com.shyrkov.service.StudentService;
import ua.com.shyrkov.service.TeacherService;

import javax.persistence.EntityManager;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class App {

    private static final Logger logger = LoggerFactory.getLogger(App.class);
    private static final BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

    public static void main(String[] args) {
        logger.info("Applying configuration");
        Configuration configure = new Configuration().configure();
        try (SessionFactory sessionFactory = configure.buildSessionFactory()) {
            logger.info("Creating entity manager");
            EntityManager entityManager = sessionFactory.createEntityManager();

            logger.info("Creating student service");
            StudentService studentService = new StudentService(entityManager);
            logger.info("Creating teacher service");
            TeacherService teacherService = new TeacherService(entityManager);
            try {


                while (true) {
                    try {
                        System.out.println("Enter student id to find it`s next lesson:");
                        Long id = Long.valueOf(reader.readLine());
                        logger.info("Finding next lesson for student with id = " + id);
                        LessonDto studentsNextLesson = studentService.getStudentsNextLesson(id);
                        System.out.println(studentsNextLesson.toString());
                        break;
                    } catch (IOException | NumberFormatException e) {
                        logger.error("Wrong id");
                    }
                }

                while (true) {
                    try {
                        System.out.println("Enter teacher id to find it`s best group:");
                        Long id = Long.valueOf(reader.readLine());
                        logger.info("Finding best group of teacher with id = " + id);
                        GroupDto bestGroupByTeacher = teacherService.findBestGroupByTeacher(id);
                        System.out.println(bestGroupByTeacher.toString());
                        break;
                    } catch (IOException | NumberFormatException e) {
                        logger.error("Wrong id");
                    }
                }
            } catch (RuntimeException e) {
                logger.error("Error was occurred: " + e.getMessage());
            } finally {
                entityManager.close();
            }
        }
    }
}
