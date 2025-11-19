package com.example;

import static org.junit.jupiter.api.Assertions.*;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class AttendanceServiceTest {

    AttendanceRepository repo;
    AttendanceService service;

    @BeforeEach
    void setUp() {
        repo = new AttendanceRepository();
        service = new AttendanceService(repo);
    }

    @Test
    void testMarkAttendance() {
        boolean result = service.markAttendance(1, "2025-01-01", "Present", 101);
        assertTrue(result);
        assertEquals(1, repo.getAttendanceByStudent(1).size());
    }

    @Test
    void testViewAttendance() {
        service.markAttendance(1, "2025-01-01", "Present", 101);
        service.markAttendance(1, "2025-01-02", "Absent", 101);

        List<Attendance> list = service.viewAttendance(1);

        assertEquals(2, list.size());
        assertEquals("Absent", list.get(1).getStatus());
    }
}
