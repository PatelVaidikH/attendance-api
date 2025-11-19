package com.example;

public class App {
    public static void main(String[] args) throws Exception {

        AttendanceRepository repo = new AttendanceRepository();
        AttendanceService service = new AttendanceService(repo);

        AttendanceApiServer api = new AttendanceApiServer(service);
        api.start();
    }
}
