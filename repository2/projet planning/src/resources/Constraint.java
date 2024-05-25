package resources;

class Constraint {
    String examId1;
    String examId2;
    int numberOfCommonStudents;

    public Constraint(String examId1, String examId2, int numberOfCommonStudents) {
        this.examId1 = examId1;
        this.examId2 = examId2;
        this.numberOfCommonStudents = numberOfCommonStudents;
    }
}