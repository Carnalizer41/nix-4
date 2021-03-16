package ua.com.shyrkov.task_3;

public class Lesson {

    public final static int duration = 45;
    public final static int shortBreakDuration = 5;
    public final static int longBreakDuration = 15;

    public static void getLessonEnding(int lessonNumber) {
        int startTime = 9;
        int resInMins = lessonNumber * duration + lessonNumber / 2 * shortBreakDuration + (lessonNumber - 1) / 2 * longBreakDuration;
        System.out.println(startTime+resInMins/60+" "+resInMins%60);
    }

}
