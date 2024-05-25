package resources;

import java.util.ArrayList;
import java.util.List;

class Session {
    String date;
    String startTime;
    Room room;
    List<Exam> exams;

    public Session(String date, String startTime, Room room) {
        this.date = date;
        this.startTime = startTime;
        this.room = room;
        this.exams = new ArrayList<>();
    }

    public boolean addExam(Exam exam) {
        if (room.capacity >= getTotalStudentsForExams() + exam.numberOfStudents) {
            exams.add(exam);
            return true;
        }
        return false;
    }

    private int getTotalStudentsForExams() {
        int total = 0;
        for (Exam exam : exams) {
            total += exam.numberOfStudents;
        }
        return total;
    }
}