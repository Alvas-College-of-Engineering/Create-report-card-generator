package com.reportcard;

public class ReportCard {
    private Student student;

    public ReportCard(Student student) {
        this.student = student;
    }

    public String generateReport() {
        StringBuilder report = new StringBuilder();
        report.append("=====================================\n");
        report.append("          REPORT CARD\n");
        report.append("=====================================\n");
        report.append("Student ID: ").append(student.getId()).append("\n");
        report.append("Student Name: ").append(student.getName()).append("\n");
        report.append("-------------------------------------\n");
        report.append("Subjects:\n");
        for (Subject subject : student.getSubjects()) {
            report.append("- ").append(subject.toString()).append("\n");
        }
        report.append("-------------------------------------\n");
        report.append("Total Marks: ").append(student.getTotalMarks()).append("\n");
        report.append("Average Marks: ").append(String.format("%.2f", student.getAverageMarks())).append("\n");
        report.append("Grade: ").append(student.getGrade()).append("\n");
        report.append("=====================================\n");
        return report.toString();
    }
}