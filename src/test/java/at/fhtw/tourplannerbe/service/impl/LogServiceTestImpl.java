package at.fhtw.tourplannerbe.service.impl;

import at.fhtw.tourplannerbe.service.LogService;
import at.fhtw.tourplannerbe.service.TourService;
import at.fhtw.tourplannerbe.service.dtos.Log;
import at.fhtw.tourplannerbe.service.dtos.Tour;
import at.fhtw.tourplannerbe.service.mapper.LogsMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;
import static org.junit.jupiter.api.Assertions.*;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@SpringBootTest
public class LogServiceTestImpl {
    @Autowired
    private LogService logService;
    @Autowired
    private LogsMapper logsMapper;
    @Autowired
    private TourService tourService;

    @Test
    @Sql(scripts = "/logsTest.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    public void getAllLogsTest() {
        Tour tour = Tour.builder().id(1L).build();
        List<Log> logs = logService.getLogsForTour(tour);

        assertEquals(1, logs.size());
        assertEquals(1L, logs.get(0).getId());
        assertEquals("test", logs.get(0).getComment());
        assertEquals(1, logs.get(0).getDifficulty());
        assertEquals(2, logs.get(0).getDistance());
        assertEquals(3, logs.get(0).getRating());
        assertEquals("2025-05-22 12:35:00.0", logs.get(0).getTime().toString());
        assertEquals("2025-05-22 08:35:00.0", logs.get(0).getTimeStart().toString());
        assertEquals("2025-05-22 10:35:00.0", logs.get(0).getTimeEnd().toString());
        assertEquals(1, logs.get(0).getTourid());
    }

    @Test
    @Sql(scripts = "/logsTest.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    public void addLogTest() throws ParseException {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.S");
        String timeString = "2025-05-22 12:35:00.0";
        Date time = formatter.parse(timeString);
        String timeStartString = "2025-05-22 08:35:00.0";
        Date timeStart = formatter.parse(timeStartString);
        String timeEndString = "2025-05-22 10:35:00.0";
        Date timeEnd = formatter.parse(timeEndString);
        Log log = Log.builder()
                .comment("test")
                .difficulty(2)
                .distance(2)
                .rating(2)
                .time(time)
                .timeStart(timeStart)
                .timeEnd(timeEnd)
                .tourid(1L)
                .build();

        logService.addLogs(log);

        List<Log> logs = logService.getLogsForTour(Tour.builder().id(1L).build());

        assertEquals(2, logs.size());
        assertEquals(2L, logs.get(1).getId());
        assertEquals("test", logs.get(1).getComment());
        assertEquals(2, logs.get(1).getDifficulty());
        assertEquals(2, logs.get(1).getDistance());
        assertEquals(2, logs.get(1).getRating());
        assertEquals("2025-05-22 12:35:00.0", logs.get(1).getTime().toString());
        assertEquals("2025-05-22 08:35:00.0", logs.get(1).getTimeStart().toString());
        assertEquals("2025-05-22 10:35:00.0", logs.get(1).getTimeEnd().toString());
        assertEquals(1, logs.get(1).getTourid());
    }

    @Test
    @Sql(scripts = "/logsTest.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    public void updateLogTest() throws ParseException {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.S");
        String timeString = "2025-05-22 12:35:00.0";
        Date time = formatter.parse(timeString);
        String timeStartString = "2025-05-22 08:35:00.0";
        Date timeStart = formatter.parse(timeStartString);
        String timeEndString = "2025-05-22 10:35:00.0";
        Date timeEnd = formatter.parse(timeEndString);
        Log log = Log.builder()
                .id(1L)
                .comment("test")
                .difficulty(2)
                .distance(2)
                .rating(2)
                .time(time)
                .timeStart(timeStart)
                .timeEnd(timeEnd)
                .tourid(1L)
                .build();
        logService.updateLogs(log);

        List<Log> logs = logService.getLogsForTour(Tour.builder().id(1L).build());
        assertEquals(1, logs.size());
        assertEquals(1L, logs.get(0).getId());
        assertEquals("test", logs.get(0).getComment());
        assertEquals(2, logs.get(0).getDifficulty());
        assertEquals(2, logs.get(0).getDistance());
        assertEquals(2, logs.get(0).getRating());
        assertEquals("2025-05-22 12:35:00.0", logs.get(0).getTime().toString());
        assertEquals("2025-05-22 08:35:00.0", logs.get(0).getTimeStart().toString());
        assertEquals("2025-05-22 10:35:00.0", logs.get(0).getTimeEnd().toString());
        assertEquals(1, logs.get(0).getTourid());
    }

    @Test
    @Sql(scripts = "/logsTest.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    public void deleteLogTest() {
        logService.deleteLogs(1L);

        List<Log> logs = logService.getLogsForTour(Tour.builder().id(1L).build());

        assertEquals(0, logs.size());
    }
}
