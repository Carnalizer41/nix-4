package ua.com.shyrkov.dto;

import ua.com.shyrkov.entity.Course;
import ua.com.shyrkov.entity.Group;
import ua.com.shyrkov.entity.Subject;
import ua.com.shyrkov.entity.Teacher;

import java.sql.Date;
import java.sql.Time;

public class LessonDto {

    private Long id;
    private Course course;
    private Group group;
    private Subject subject;
    private Teacher teacher;
    private Date date;
    private Time time;

    public LessonDto(Long id, Course course, Group group, Subject subject, Teacher teacher, Date date, Time time) {
        this.id = id;
        this.course = course;
        this.group = group;
        this.subject = subject;
        this.teacher = teacher;
        this.date = date;
        this.time = time;
    }

    @Override
    public String toString() {
        return "LessonDto{" +
                "course=" + course.getName() +
                ", group=" + group.getName() +
                ", subject=" + subject.getName() +
                ", teacher=" + teacher.getFirstName()+" "+teacher.getLastName() +
                ", date=" + date.toString() +
                ", time=" + time.toString() +
                '}';
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Course getCourse() {
        return course;
    }

    public void setCourse(Course course) {
        this.course = course;
    }

    public Group getGroup() {
        return group;
    }

    public void setGroup(Group group) {
        this.group = group;
    }

    public Subject getSubject() {
        return subject;
    }

    public void setSubject(Subject subject) {
        this.subject = subject;
    }

    public Teacher getTeacher() {
        return teacher;
    }

    public void setTeacher(Teacher teacher) {
        this.teacher = teacher;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public Time getTime() {
        return time;
    }

    public void setTime(Time time) {
        this.time = time;
    }
}
