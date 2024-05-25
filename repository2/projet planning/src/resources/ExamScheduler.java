package resources;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

class ExamScheduler {
    private Map<String, Set<String>> graph;
    private Map<String, Session> examToSessionMap;
    private List<Session> sessions;

    public ExamScheduler(List<Exam> exams, List<Session> sessions) {
        this.graph = new HashMap<>();
        this.examToSessionMap = new HashMap<>();
        this.sessions = sessions;

        for (Exam exam : exams) {
            graph.put(exam.getId(), new HashSet<>());
        }
    }

    public boolean scheduleExams(List<Constraint> constraints) {
        for (Constraint constraint : constraints) {
            graph.get(constraint.examId1).add(constraint.examId2);
            graph.get(constraint.examId2).add(constraint.examId1);
        }

        for (String examId : graph.keySet()) {
            if (!assignExamToSession(examId)) {
                return false;
            }
        }
        return true;
    }

    private boolean assignExamToSession(String examId) {
        for (Session session : sessions) {
            if (canAssignExamToSession(examId, session)) {
                examToSessionMap.put(examId, session);
                session.addExam(findExamById(examId));
                return true;
            }
        }
        return false;
    }

    private boolean canAssignExamToSession(String examId, Session session) {
        for (Exam scheduledExam : session.exams) {
            if (graph.get(examId).contains(scheduledExam.getId())) {
                return false;
            }
        }
        return true;
    }

    private Exam findExamById(String examId) {
       
        return null;
    }

    public void printSchedule() {
        for (Session session : sessions) {
            System.out.println("Session on " + session.date + " at " + session.startTime + " in " + session.room.name);
            for (Exam exam : session.exams) {
                System.out.println("  Exam: " + exam.name);
            }
        }
    }
}
