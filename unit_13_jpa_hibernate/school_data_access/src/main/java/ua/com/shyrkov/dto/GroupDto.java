package ua.com.shyrkov.dto;

import ua.com.shyrkov.entity.Course;

public class GroupDto {

    private Long id;
    private String name;
    private Course course;
    private Double medianMark;

    public GroupDto(Long id, String name, Course course, Double medianMark) {
        this.id = id;
        this.name = name;
        this.course = course;
        this.medianMark = medianMark;
    }

    @Override
    public String toString() {
        return "GroupDto{" +
                "name='" + name + '\'' +
                ", course=" + course.getName() +
                ", averageMark=" + medianMark +
                '}';
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Course getCourse() {
        return course;
    }

    public void setCourse(Course course) {
        this.course = course;
    }

    public Double getMedianMark() {
        return medianMark;
    }

    public void setMedianMark(Double medianMark) {
        this.medianMark = medianMark;
    }
}
