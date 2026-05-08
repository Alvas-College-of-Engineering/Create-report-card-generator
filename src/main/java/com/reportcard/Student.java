package com.reportcard;

import java.util.List;

public class Student {
    private int id;
    private String name;
    private List<Subject> subjects;

    public Student(int id, String name, List<Subject> subjects) {
        this.id = id;
        this.name = name;
        this.subjects = subjects;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Subject> getSubjects() {
        return subjects;
    }

    public void setSubjects(List<Subject> subjects) {
        this.subjects = subjects;
    }

    public int getTotalMarks() {
        return subjects.stream().mapToInt(Subject::getMarks).sum();
    }

    public double getAverageMarks() {
        return subjects.isEmpty() ? 0 : (double) getTotalMarks() / subjects.size();
    }

    public String getGrade() {
        double avg = getAverageMarks();
        if (avg >= 90) return "A";
        else if (avg >= 80) return "B";
        else if (avg >= 70) return "C";
        else if (avg >= 60) return "D";
        else return "F";
    }

    @Override
    public String toString() {
        return "ID: " + id + ", Name: " + name;
    }
}