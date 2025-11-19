package com.example;

public class Attendance {
    private int studentId;
    private String date;
    private String status;
    private int facultyId;

    public Attendance(int studentId, String date, String status, int facultyId) {
        this.studentId = studentId;
        this.date = date;
        this.status = status;
        this.facultyId = facultyId;
    }

    public int getStudentId() { return studentId; }
    public String getDate() { return date; }
    public String getStatus() { return status; }
    public int getFacultyId() { return facultyId; }
}
