package com.example;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class AttendanceRepository {

    private static final String FILE_PATH = "attendance.json";

    // Read file + convert JSON to List<Attendance>
    private List<Attendance> loadFromFile() {
        try {
            String json = new String(Files.readAllBytes(Paths.get(FILE_PATH)));

            List<Attendance> list = new ArrayList<>();

            if (json.trim().equals("[]")) return list; // empty list

            // Remove [ ]
            json = json.substring(1, json.length() - 1);

            // Split objects by "},"
            String[] items = json.split("},");
            for (int i = 0; i < items.length; i++) {
                if (!items[i].endsWith("}")) items[i] = items[i] + "}";

                String obj = items[i]
                    .replace("{", "")
                    .replace("}", "")
                    .replace("\"", "");

                String[] fields = obj.split(",");

                int studentId = Integer.parseInt(fields[0].split(":")[1]);
                String date = fields[1].split(":")[1];
                String status = fields[2].split(":")[1];
                int facultyId = Integer.parseInt(fields[3].split(":")[1]);

                list.add(new Attendance(studentId, date, status, facultyId));
            }

            return list;

        } catch (Exception e) {
            e.printStackTrace();
            return new ArrayList<>();
        }
    }

    // Write List<Attendance> to JSON file
    private void saveToFile(List<Attendance> list) {
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

        try {
            Files.write(Paths.get(FILE_PATH), json.toString().getBytes());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Add attendance + save
    public void addAttendance(Attendance att) {
        List<Attendance> list = loadFromFile();
        list.add(att);
        saveToFile(list);
    }

    // Load attendance for specific student
    public List<Attendance> getAttendanceByStudent(int studentId) {
        List<Attendance> all = loadFromFile();
        List<Attendance> result = new ArrayList<>();

        for (Attendance a : all) {
            if (a.getStudentId() == studentId) {
                result.add(a);
            }
        }
        return result;
    }

    public void clearAll() {
        saveToFile(new ArrayList<>());
    }
}
