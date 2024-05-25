package resources;

import java.util.ArrayList;
import java.util.List;

class Exam {
    String id;
    String name;
    int duration;
    String promotionId;
    int numberOfStudents;
    List<Student> students;

    public Exam(String id, String name, int duration, String promotionId, int numberOfStudents) {
        this.id = id;
        this.name = name;
        this.duration = duration;
        this.promotionId = promotionId;
        this.numberOfStudents = numberOfStudents;
        this.students = new ArrayList<>();
    }

    public void setStudents(List<Student> students) {
        this.students = students;
    }

    public String getId() {
        return id;
    }
}