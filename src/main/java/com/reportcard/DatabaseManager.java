package com.reportcard;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DatabaseManager {
    private static final String DB_URL = "jdbc:h2:~/reportcard";
    private static final String USER = "sa";
    private static final String PASS = "";

    static {
        try {
            Class.forName("org.h2.Driver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(DB_URL, USER, PASS);
    }

    public static void createTables() {
        String createStudentsTable = "CREATE TABLE IF NOT EXISTS students (" +
                "id INT PRIMARY KEY, " +
                "name VARCHAR(255), " +
                "total_marks INT, " +
                "average DOUBLE, " +
                "grade VARCHAR(10))";

        String createSubjectsTable = "CREATE TABLE IF NOT EXISTS subjects (" +
                "student_id INT, " +
                "subject_name VARCHAR(255), " +
                "marks INT, " +
                "FOREIGN KEY (student_id) REFERENCES students(id))";

        try (Connection conn = getConnection();
             Statement stmt = conn.createStatement()) {
            stmt.execute(createStudentsTable);
            stmt.execute(createSubjectsTable);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void saveStudent(Student student) {
        String insertStudent = "INSERT INTO students (id, name, total_marks, average, grade) VALUES (?, ?, ?, ?, ?)";
        String insertSubject = "INSERT INTO subjects (student_id, subject_name, marks) VALUES (?, ?, ?)";

        try (Connection conn = getConnection();
             PreparedStatement pstmtStudent = conn.prepareStatement(insertStudent);
             PreparedStatement pstmtSubject = conn.prepareStatement(insertSubject)) {

            pstmtStudent.setInt(1, student.getId());
            pstmtStudent.setString(2, student.getName());
            pstmtStudent.setInt(3, student.getTotalMarks());
            pstmtStudent.setDouble(4, student.getAverageMarks());
            pstmtStudent.setString(5, student.getGrade());
            pstmtStudent.executeUpdate();

            for (Subject subject : student.getSubjects()) {
                pstmtSubject.setInt(1, student.getId());
                pstmtSubject.setString(2, subject.getName());
                pstmtSubject.setInt(3, subject.getMarks());
                pstmtSubject.executeUpdate();
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static Student getStudent(int id) {
        String selectStudent = "SELECT * FROM students WHERE id = ?";
        String selectSubjects = "SELECT * FROM subjects WHERE student_id = ?";

        try (Connection conn = getConnection();
             PreparedStatement pstmtStudent = conn.prepareStatement(selectStudent);
             PreparedStatement pstmtSubjects = conn.prepareStatement(selectSubjects)) {

            pstmtStudent.setInt(1, id);
            ResultSet rsStudent = pstmtStudent.executeQuery();

            if (rsStudent.next()) {
                String name = rsStudent.getString("name");
                List<Subject> subjects = new ArrayList<>();

                pstmtSubjects.setInt(1, id);
                ResultSet rsSubjects = pstmtSubjects.executeQuery();
                while (rsSubjects.next()) {
                    String subjName = rsSubjects.getString("subject_name");
                    int marks = rsSubjects.getInt("marks");
                    subjects.add(new Subject(subjName, marks));
                }

                return new Student(id, name, subjects);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
}