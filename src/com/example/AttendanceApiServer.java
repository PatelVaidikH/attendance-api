package com.example;

import com.sun.net.httpserver.HttpServer;
import com.sun.net.httpserver.HttpExchange;

import java.io.IOException;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.nio.charset.StandardCharsets;

public class AttendanceApiServer {

    private AttendanceService service;

    public AttendanceApiServer(AttendanceService service) {
        this.service = service;
    }

    public void start() throws IOException {
        HttpServer server = HttpServer.create(new InetSocketAddress(8080), 0);

        // POST /mark
        server.createContext("/mark", exchange -> {
            if ("POST".equals(exchange.getRequestMethod())) {
                String body = new String(exchange.getRequestBody().readAllBytes(), StandardCharsets.UTF_8);

                // Parse simple JSON manually (no Gson needed)
                String[] parts = body.replace("{", "").replace("}", "").replace("\"", "").split(",");

                int studentId = Integer.parseInt(parts[0].split(":")[1].trim());
                String date = parts[1].split(":")[1].trim();
                String status = parts[2].split(":")[1].trim();
                int facultyId = Integer.parseInt(parts[3].split(":")[1].trim());

                service.markAttendance(studentId, date, status, facultyId);

                respond(exchange, "{\"message\": \"attendance saved\"}");
            }
        });

        // GET /view?id=1
        server.createContext("/view", exchange -> {
            if ("GET".equals(exchange.getRequestMethod())) {
                String query = exchange.getRequestURI().getQuery();
                int studentId = Integer.parseInt(query.split("=")[1]);

                var list = service.viewAttendance(studentId);

                StringBuilder json = new StringBuilder("[");
                for (Attendance a : list) {
                    json.append("{")
                        .append("\"studentId\":").append(a.getStudentId()).append(",")
                        .append("\"date\":\"").append(a.getDate()).append("\",")
                        .append("\"status\":\"").append(a.getStatus()).append("\",")
                        .append("\"facultyId\":").append(a.getFacultyId())
                        .append("},");
                }
                if (json.charAt(json.length() - 1) == ',')
                    json.deleteCharAt(json.length() - 1);
                json.append("]");

                respond(exchange, json.toString());
            }
        });

        server.start();
        System.out.println("Server running at http://localhost:8080/");
    }

    private void respond(HttpExchange exchange, String response) throws IOException {
        exchange.sendResponseHeaders(200, response.getBytes().length);
        OutputStream os = exchange.getResponseBody();
        os.write(response.getBytes());
        os.close();
    }
}
