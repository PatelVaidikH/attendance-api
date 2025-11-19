package com.example;

import static spark.Spark.*;

import com.google.gson.Gson;

public class AttendanceController {

    public static void startServer() {

        port(8080);
        Gson gson = new Gson();

        AttendanceRepository repo = new AttendanceRepository();
        AttendanceService service = new AttendanceService(repo);

        // POST: Mark attendance
        post("/attendance/mark", (req, res) -> {
            Attendance att = gson.fromJson(req.body(), Attendance.class);
            service.markAttendance(att.getStudentId(), att.getDate(), att.getStatus(), att.getFacultyId());
            res.type("application/json");
            return "{\"message\":\"Attendance marked successfully\"}";
        });

        // GET: View attendance
        get("/attendance/student/:id", (req, res) -> {
            int studentId = Integer.parseInt(req.params("id"));
            res.type("application/json");
            return gson.toJson(service.viewAttendance(studentId));
        });
    }
}
