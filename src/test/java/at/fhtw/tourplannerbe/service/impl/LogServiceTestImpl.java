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

import java.io.IOException;
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

    private SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

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
        assertEquals(0, tour.getPopularity());
        assertEquals(0, tour.getChildfriendliness());
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

        Tour tour = tourService.getTours().get(0);
        assertEquals(2.5, tour.getPopularity());
        assertEquals(3, tour.getChildfriendliness());
    }

    @Test
    @Sql(scripts = "/logsTest.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    public void addLogUpdateTourTest() throws ParseException {
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

        Tour tour = tourService.getTours().get(0);
        assertEquals(2.5, tour.getPopularity());
        assertEquals(3, tour.getChildfriendliness());

        tour = Tour.builder()
                .id(1L)
                .name("newTest")
                .description("newDescription")
                .start("newStart")
                .end("newEnd")
                .transportMode("newTransportMode")
                .distance(25)
                .build();

        tourService.updateTour(tour);
        tour = tourService.getTours().get(0);
        assertEquals(2.5, tour.getPopularity());
        assertEquals(3, tour.getChildfriendliness());
    }



    @Test
    @Sql(scripts = "/logsTest.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    public void addTwoLogTest() throws ParseException {
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

        Tour tour = tourService.getTours().get(0);
        assertEquals(2.5, tour.getPopularity());
        assertEquals(3, tour.getChildfriendliness());

        Log logTwo = Log.builder()
                .comment("test")
                .difficulty(5)
                .distance(20)
                .rating(5)
                .time(time)
                .timeStart(timeStart)
                .timeEnd(timeEnd)
                .tourid(1L)
                .build();
        logService.addLogs(logTwo);

        logs = logService.getLogsForTour(Tour.builder().id(1L).build());

        assertEquals(3, logs.size());
        Tour tourNew = tourService.getTourById(1L);;
        assertEquals(3.33, tourNew.getPopularity(), 0.01);
        assertEquals(3, tourNew.getChildfriendliness());


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

        Tour tourNew = tourService.getTourById(1L);;
        assertEquals(2, tourNew.getPopularity());
        assertEquals(3, tourNew.getChildfriendliness());
    }

    @Test
    @Sql(scripts = "/logsTest.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    public void deleteLogTest() {
        logService.deleteLogs(1L);

        List<Log> logs = logService.getLogsForTour(Tour.builder().id(1L).build());

        assertEquals(0, logs.size());
    }

    @Test
    @Sql(scripts = "/logsTest.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    public void addDeleteLogTest() throws ParseException{
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

        Tour tourNew = tourService.getTourById(1L);;
        assertEquals(2.5, tourNew.getPopularity());
        assertEquals(3, tourNew.getChildfriendliness());

        logService.deleteLogs(1L);

        List<Log> logs = logService.getLogsForTour(Tour.builder().id(1L).build());

        assertEquals(1, logs.size());
        assertEquals(2L, logs.get(0).getId());
        assertEquals("test", logs.get(0).getComment());
        assertEquals(2, logs.get(0).getDifficulty());
        assertEquals(2, logs.get(0).getDistance());
        assertEquals(2, logs.get(0).getRating());
        assertEquals("2025-05-22 12:35:00.0", logs.get(0).getTime().toString());
        assertEquals("2025-05-22 08:35:00.0", logs.get(0).getTimeStart().toString());
        assertEquals("2025-05-22 10:35:00.0", logs.get(0).getTimeEnd().toString());
        assertEquals(1, logs.get(0).getTourid());

        tourNew = tourService.getTourById(1L);;

        assertEquals(2, tourNew.getPopularity());
        assertEquals(3, tourNew.getChildfriendliness());
    }

    @Test
    @Sql(scripts = "/logsTest.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    public void updateChildfriendlinessTest(){

        Tour tour = tourService.getTourById(1L);
        assertEquals(0, tour.getPopularity());
        assertEquals(0, tour.getChildfriendliness());

        Tour updateTour = Tour.builder()
                .id(1L)
                .duration(2)
                .build();
        tourService.updateTour(updateTour);

        Log log = Log.builder()
                .id(1L)
                .comment("test")
                .difficulty(1)
                .distance(2)
                .rating(2)
                .tourid(1L)
                .build();
        logService.updateLogs(log);
        tour = tourService.getTourById(1L);
        assertEquals(2, tour.getPopularity());
        assertEquals(4, tour.getChildfriendliness());
    }

    @Test
    @Sql(scripts = "/logsTest.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    public void updateChildfriendlinessPopulatityTest(){

        Tour tour = tourService.getTourById(1L);
        assertEquals(0, tour.getPopularity());
        assertEquals(0, tour.getChildfriendliness());

        Tour updateTour = Tour.builder()
                .id(1L)
                .duration(2)
                .build();
        tourService.updateTour(updateTour);

        Log log = Log.builder()
                .id(1L)
                .comment("test")
                .difficulty(1)
                .distance(2)
                .rating(5)
                .tourid(1L)
                .build();
        logService.updateLogs(log);
        tour = tourService.getTourById(1L);
        assertEquals(5, tour.getPopularity());
        assertEquals(4, tour.getChildfriendliness());
    }

    @Test
    @Sql(scripts = "/logsTest.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    public void updatePopularityTest() throws ParseException {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.S");
        String timeString = "2025-05-22 12:35:00.0";
        Date time = formatter.parse(timeString);
        String timeStartString = "2025-05-22 08:35:00.0";
        Date timeStart = formatter.parse(timeStartString);
        String timeEndString = "2025-05-22 10:35:00.0";
        Date timeEnd = formatter.parse(timeEndString);

        Tour tour = tourService.getTourById(1L);
        assertEquals(0, tour.getPopularity());
        assertEquals(0, tour.getChildfriendliness());

        Tour updateTour = Tour.builder()
                .id(1L)
                .duration(2)
                .build();
        tourService.updateTour(updateTour);

        for (int i = 2; i < 7; i++) {
            Log log = Log.builder()
                    .time(time)
                    .timeStart(timeStart)
                    .timeEnd(timeEnd)
                    .rating(5)
                    .tourid(1L)
                    .build();
            logService.addLogs(log);
        }
        tour = tourService.getTourById(1L);
        assertEquals((double) 28/6, tour.getPopularity());

    }

    @Test
    @Sql(scripts = "/logsTestWithMoreLogs.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    public void getLogsForTour() {
        List<Log> logs = logService.getLogsForTour(Tour.builder().id(1L).build());

        assertEquals(1, logs.size());
        assertEquals(1L, logs.get(0).getId());
        assertEquals("test", logs.get(0).getComment());
        assertEquals(1, logs.get(0).getDifficulty());
        assertEquals(2, logs.get(0).getDistance());
        assertEquals(3, logs.get(0).getRating());
        assertEquals("2025-05-22 12:35:00.0", logs.get(0).getTime().toString());
        assertEquals("2025-05-22 08:35:00.0", logs.get(0).getTimeStart().toString());
        assertEquals("2025-05-22 10:35:00.0", logs.get(0).getTimeEnd().toString());
        assertEquals(1L, logs.get(0).getTourid());
    }

    @Test
    @Sql(scripts = "/importTest.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    public void addLogsInBulkTest() throws ParseException, IOException {
        Tour tour = Tour.builder()
                .name("new_tour")
                .description("new_description")
                .start("16.37038797323949,48.2010386430652")
                .end("16.37367838085627,48.23761334405697")
                .transportMode("foot-walking")
                .information("information")
                .build();

        Tour addedTour = tourService.addTour(tour);

        List<Log> logs = List.of(Log.builder()
                        .comment("Comment 1")
                        .difficulty(1)
                        .distance(1)
                        .rating(1)
                        .time(sdf.parse("2025-06-25 10:00:00"))
                        .timeStart(sdf.parse("2025-06-25 8:00:00"))
                        .timeEnd(sdf.parse("2025-06-25 09:00:00"))
                        .tourid(addedTour.getId())
                        .build(),
                Log.builder()
                        .comment("Comment 2")
                        .difficulty(1)
                        .distance(1)
                        .rating(1)
                        .time(sdf.parse("2025-06-25 10:00:00"))
                        .timeStart(sdf.parse("2025-06-25 8:00:00"))
                        .timeEnd(sdf.parse("2025-06-25 09:00:00"))
                        .tourid(addedTour.getId())
                        .build());

        logService.addLogsInBulk(logs);

        List<Log> newLogs = logService.getLogsForTour(addedTour);

        assertEquals(2, newLogs.size());
        for(int i = 0; i < newLogs.size(); i++) {
            assertEquals((long) i+1, newLogs.get(i).getId());
            assertEquals(logs.get(i).getComment(), newLogs.get(i).getComment());
            assertEquals(logs.get(i).getDifficulty(), newLogs.get(i).getDifficulty());
            assertEquals(logs.get(i).getDistance(), newLogs.get(i).getDistance());
            assertEquals(logs.get(i).getRating(), newLogs.get(i).getRating());
            assertEquals(logs.get(i).getTime(), newLogs.get(i).getTime());
            assertEquals(logs.get(i).getTimeStart(), newLogs.get(i).getTimeStart());
            assertEquals(logs.get(i).getTimeEnd(), newLogs.get(i).getTimeEnd());
            assertEquals(logs.get(i).getTourid(), newLogs.get(i).getTourid());
        }
    }
}
