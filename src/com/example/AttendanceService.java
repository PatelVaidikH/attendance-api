package com.example;

import java.util.List;

public class AttendanceService {

    private AttendanceRepository repo;

    public AttendanceService(AttendanceRepository repo) {
        this.repo = repo;
    }

    public boolean markAttendance(int studentId, String date, String status, int facultyId) {
        Attendance att = new Attendance(studentId, date, status, facultyId);
        repo.addAttendance(att);
        return true;
    }

    public List<Attendance> viewAttendance(int studentId) {
        return repo.getAttendanceByStudent(studentId);
    }
}
