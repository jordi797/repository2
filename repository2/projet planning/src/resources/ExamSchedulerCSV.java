package resources;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.HashMap;

public class ExamSchedulerCSV {
    public static void main(String[] args) {
        try {
        	List<Promotion> promotions = readPromotions("promotions.csv");
            List<Exam> exams = readExams("examens.csv");
            List<Room> rooms = readRooms("salles.csv");
            List<Constraint> constraints = readConstraints("contraintes.csv");
            Map<String, List<Student>> studentsByExam = readStudents("etudiant.csv");

            for (Exam exam : exams) {
                exam.setStudents(studentsByExam.getOrDefault(exam.getId(), new ArrayList<>()));
            }

            ExamScheduler scheduler = new ExamScheduler(exams, createSessions(rooms));
            if (scheduler.scheduleExams(constraints)) {
                scheduler.printSchedule();
            } else {
                System.out.println("Failed to schedule all exams.");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    static List<Promotion> readPromotions(String filename) throws IOException {
        List<Promotion> promotions = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new InputStreamReader(getFileFromResourceAsStream(filename)))) {
            String line;
            br.readLine(); // Skip header
            while ((line = br.readLine()) != null) {
                String[] values = line.split("\t");
                promotions.add(new Promotion(values[0], values[1], Integer.parseInt(values[2])));
            }
        }
        return promotions;
    }

    static List<Exam> readExams(String filename) throws IOException {
        List<Exam> exams = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new InputStreamReader(getFileFromResourceAsStream(filename)))) {
            String line;
            br.readLine(); // Skip header
            while ((line = br.readLine()) != null) {
                String[] values = line.split("\t");
                exams.add(new Exam(values[0], values[1], Integer.parseInt(values[2]), values[3], Integer.parseInt(values[4])));
            }
        }
        return exams;
    }

    static List<Room> readRooms(String filename) throws IOException {
        List<Room> rooms = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new InputStreamReader(getFileFromResourceAsStream(filename)))) {
            String line;
            br.readLine(); // Skip header
            while ((line = br.readLine()) != null) {
                String[] values = line.split("\t");
                rooms.add(new Room(values[0], values[1], Integer.parseInt(values[2])));
            }
        }
        return rooms;
    }

    static List<Constraint> readConstraints(String filename) throws IOException {
        List<Constraint> constraints = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new InputStreamReader(getFileFromResourceAsStream(filename)))) {
            String line;
            br.readLine(); // Skip header
            while ((line = br.readLine()) != null) {
                String[] values = line.split("\t");
                constraints.add(new Constraint(values[0], values[1], Integer.parseInt(values[2])));
            }
        }
        return constraints;
    }

    static Map<String, List<Student>> readStudents(String filename) throws IOException {
        Map<String, List<Student>> studentsByExam = new HashMap<>();
        try (BufferedReader br = new BufferedReader(new InputStreamReader(getFileFromResourceAsStream(filename)))) {
            String line;
            br.readLine(); // Skip header
            while ((line = br.readLine()) != null) {
                String[] values = line.split("\t");
                Student student = new Student(values[0], values[1], values[2], values[3]);
                studentsByExam.computeIfAbsent(values[3], k -> new ArrayList<>()).add(student);
            }
        }
        return studentsByExam;
    }

    private static InputStream getFileFromResourceAsStream(String fileName) {
        ClassLoader classLoader = ExamSchedulerCSV.class.getClassLoader();
        InputStream inputStream = classLoader.getResourceAsStream(fileName);

        if (inputStream == null) {
            throw new IllegalArgumentException("file not found! " + fileName);
        } else {
            return inputStream;
        }
    }

    static List<Session> createSessions(List<Room> rooms) {
        List<Session> sessions = new ArrayList<>();
        String[] dates = {"2024-05-23", "2024-05-24", "2024-05-25"};
        String[] times = {"09:00", "13:00", "17:00"};
        for (String date : dates) {
            for (String time : times) {
                for (Room room : rooms) {
                    sessions.add(new Session(date, time, room));
                }
            }
        }
        return sessions;
    }
}
