package at.fhtw.tourplannerbe.service.impl;

import at.fhtw.tourplannerbe.persitence.TourEntity;
import at.fhtw.tourplannerbe.service.TourService;
import at.fhtw.tourplannerbe.service.dtos.Tour;
import at.fhtw.tourplannerbe.service.mapper.TourMapper;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;

import javax.xml.crypto.Data;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class TourServiceTestImpl {

    @Autowired
    private TourService tourService;

    @Autowired
    private TourMapper tourMapper;

    @Test
    @Sql(scripts = "/tourTest.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    public void getToursTest() {
        List<Tour> tours = tourService.getTours();
        TourEntity tourEntity = tourMapper.toEntity(tours.get(0));
        assertEquals(tourEntity.getName(), "test");
        assertEquals(tourEntity.getDescription(), "test");
        assertEquals(tourEntity.getStart(), "test");
        assertEquals(tourEntity.getEnd(), "test");
        assertEquals(tourEntity.getTransportMode(), "test");
        assertEquals(tourEntity.getDistance(), 3);
        assertEquals(tourEntity.getTimeStart().toString(), "2025-05-22 08:35:00.0");
        assertEquals(tourEntity.getTimeEnd().toString(), "2025-05-22 10:35:00.0");
        assertEquals(tourEntity.getInformation(), "test");
        assertEquals(tourEntity.getPopularity(), 0);
        assertEquals(tourEntity.getChildfriendliness(), 0);
    }

    @Test
    @Sql(scripts = "/saveTourTest.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    public void saveTourTest() throws ParseException {
        String timeStart = "2025-05-22 08:35:00.00";
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.S");
        Date date = formatter.parse(timeStart);

        String timeEnd = "2025-05-22 10:35:00.00";
        SimpleDateFormat formatterEnd = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.S");
        Date dateEnd = formatterEnd.parse(timeEnd);

        Tour tour = Tour.builder()
                .name("new_tour")
                .description("new_description")
                .start("start")
                .end("end")
                .transportMode("transport_mode")
                .distance(3)
                .timeStart(date)
                .timeEnd(dateEnd)
                .information("information")
                .build();

        tourService.addTour(tour);

        List<Tour> tours = tourService.getTours();
        TourEntity tourEntity = tourMapper.toEntity(tours.get(0));

        assertEquals(tourEntity.getName(), "new_tour");
        assertEquals(tourEntity.getDescription(), "new_description");
        assertEquals(tourEntity.getStart(), "start");
        assertEquals(tourEntity.getEnd(), "end");
        assertEquals(tourEntity.getTransportMode(), "transport_mode");
        assertEquals(tourEntity.getDistance(), 3);
        assertEquals(tourEntity.getTimeStart().toString(), "2025-05-22 08:35:00.0");
        assertEquals(tourEntity.getTimeEnd().toString(), "2025-05-22 10:35:00.0");
        assertEquals(tourEntity.getInformation(), "information");
        assertEquals(tourEntity.getPopularity(), 0);
        assertEquals(tourEntity.getChildfriendliness(), 0);
    }


    @Test
    @Sql(scripts = "/tourTest.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    public void saveSecondTourTest() throws ParseException {
        String timeStart = "2025-05-22 08:35:00.00";
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.S");
        Date date = formatter.parse(timeStart);

        String timeEnd = "2025-05-22 10:35:00.00";
        SimpleDateFormat formatterEnd = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.S");
        Date dateEnd = formatterEnd.parse(timeEnd);

        Tour tour = Tour.builder()
                .name("new_tour")
                .description("new_description")
                .start("start")
                .end("end")
                .transportMode("transport_mode")
                .distance(3)
                .timeStart(date)
                .timeEnd(dateEnd)
                .information("information")
                .build();

        tourService.addTour(tour);

        List<Tour> tours = tourService.getTours();
        tours.forEach(System.out::println);
        TourEntity tourEntity = tourMapper.toEntity(tours.get(0));

        assertEquals(tours.size(), 2);


        assertEquals(tourEntity.getName(), "test");
        assertEquals(tourEntity.getDescription(), "test");
        assertEquals(tourEntity.getStart(), "test");
        assertEquals(tourEntity.getEnd(), "test");
        assertEquals(tourEntity.getTransportMode(), "test");
        assertEquals(tourEntity.getDistance(), 3);
        assertEquals(tourEntity.getTimeStart().toString(), "2025-05-22 08:35:00.0");
        assertEquals(tourEntity.getTimeEnd().toString(), "2025-05-22 10:35:00.0");
        assertEquals(tourEntity.getInformation(), "test");
        assertEquals(tourEntity.getPopularity(), 0);
        assertEquals(tourEntity.getChildfriendliness(), 0);


        TourEntity secondTour = tourMapper.toEntity(tours.get(1));

        assertEquals(secondTour.getName(), "new_tour");
        assertEquals(secondTour.getDescription(), "new_description");
        assertEquals(secondTour.getStart(), "start");
        assertEquals(secondTour.getEnd(), "end");
        assertEquals(secondTour.getTransportMode(), "transport_mode");
        assertEquals(secondTour.getDistance(), 3);
        assertEquals(secondTour.getTimeStart().toString(), "2025-05-22 08:35:00.0");
        assertEquals(secondTour.getTimeEnd().toString(), "2025-05-22 10:35:00.0");
        assertEquals(secondTour.getInformation(), "information");
        assertEquals(secondTour.getPopularity(), 0);
        assertEquals(secondTour.getChildfriendliness(), 0);
    }

    @Test
    @Sql(scripts = "/tourTest.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    public void updateTourTest()throws ParseException {
        String timeStart = "2025-05-22 18:35:00.00";
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.S");
        Date date = formatter.parse(timeStart);

        String timeEnd = "2025-05-22 20:35:00.00";
        SimpleDateFormat formatterEnd = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.S");
        Date dateEnd = formatterEnd.parse(timeEnd);

        List<Tour> tours = tourService.getTours();
        TourEntity tourEntity = tourMapper.toEntity(tours.get(0));
        tourEntity.setName("new_name");
        tourEntity.setDescription("new_description");
        tourEntity.setStart("start");
        tourEntity.setEnd("end");
        tourEntity.setTransportMode("transport_mode");
        tourEntity.setDistance(13);
        tourEntity.setTimeStart(date);
        tourEntity.setTimeEnd(dateEnd);
        tourEntity.setInformation("information");

        tourService.updateTour(tourMapper.toDto(tourEntity));

        tours = tourService.getTours();
        tourEntity = tourMapper.toEntity(tours.get(0));
        assertEquals(tourEntity.getName(), "new_name");
        assertEquals(tourEntity.getDescription(), "new_description");
        assertEquals(tourEntity.getStart(), "start");
        assertEquals(tourEntity.getEnd(), "end");
        assertEquals(tourEntity.getTransportMode(), "transport_mode");
        assertEquals(tourEntity.getDistance(), 13);
        assertEquals(tourEntity.getTimeStart().toString(), "2025-05-22 18:35:00.0");
        assertEquals(tourEntity.getTimeEnd().toString(), "2025-05-22 20:35:00.0");
        assertEquals(tourEntity.getInformation(), "information");
        assertEquals(tourEntity.getPopularity(), 0);
        assertEquals(tourEntity.getChildfriendliness(), 0);

    }

    @Test
    @Sql(scripts = "/tourTest.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    public void noUpdateTourTest(){
        List<Tour> tours = tourService.getTours();
        TourEntity tourEntity = tourMapper.toEntity(tours.get(0));
        tourService.updateTour(tourMapper.toDto(tourEntity));
        tours = tourService.getTours();
        tourEntity = tourMapper.toEntity(tours.get(0));
        assertEquals(tourEntity.getName(), "test");
        assertEquals(tourEntity.getDescription(), "test");
        assertEquals(tourEntity.getStart(), "test");
        assertEquals(tourEntity.getEnd(), "test");
        assertEquals(tourEntity.getTransportMode(), "test");
        assertEquals(tourEntity.getDistance(), 3);
        assertEquals(tourEntity.getTimeStart().toString(), "2025-05-22 08:35:00.0");
        assertEquals(tourEntity.getTimeEnd().toString(), "2025-05-22 10:35:00.0");
        assertEquals(tourEntity.getInformation(), "test");
        assertEquals(tourEntity.getPopularity(), 0);
        assertEquals(tourEntity.getChildfriendliness(), 0);
    }

    @Test
    @Sql(scripts = "/tourTest.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    public void someUpdateTourTest(){
        List<Tour> tours = tourService.getTours();
        TourEntity tourEntity = tourMapper.toEntity(tours.get(0));
        tourEntity.setName("new_name");
        tourEntity.setDescription("new_description");
        tourEntity.setStart("start");

        tourService.updateTour(tourMapper.toDto(tourEntity));
        tours = tourService.getTours();
        tourEntity = tourMapper.toEntity(tours.get(0));
        assertEquals(tourEntity.getName(), "new_name");
        assertEquals(tourEntity.getDescription(), "new_description");
        assertEquals(tourEntity.getStart(), "start");
        assertEquals(tourEntity.getEnd(), "test");
        assertEquals(tourEntity.getTransportMode(), "test");
        assertEquals(tourEntity.getDistance(), 3);
        assertEquals(tourEntity.getTimeStart().toString(), "2025-05-22 08:35:00.0");
        assertEquals(tourEntity.getTimeEnd().toString(), "2025-05-22 10:35:00.0");
        assertEquals(tourEntity.getInformation(), "test");
        assertEquals(tourEntity.getPopularity(), 0);
        assertEquals(tourEntity.getChildfriendliness(), 0);
    }

    @Test
    @Sql(scripts = "/tourTest.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    public void nullUpdateTourTest() {

        List<Tour> tours = tourService.getTours();
        TourEntity tourEntity = tourMapper.toEntity(tours.get(0));
        tourEntity.setName("new_name");
        tourEntity.setDescription(null);
        tourEntity.setStart(null);
        tourEntity.setEnd(null);
        tourEntity.setTransportMode(null);
        tourEntity.setDistance(13);
        tourEntity.setTimeStart(null);
        tourEntity.setTimeEnd(null);
        tourEntity.setInformation(null);

        tourService.updateTour(tourMapper.toDto(tourEntity));

        tours = tourService.getTours();
        tourEntity = tourMapper.toEntity(tours.get(0));
        assertEquals(tourEntity.getName(), "new_name");
        assertEquals(tourEntity.getDescription(), "test");
        assertEquals(tourEntity.getStart(), "test");
        assertEquals(tourEntity.getEnd(), "test");
        assertEquals(tourEntity.getTransportMode(), "test");
        assertEquals(tourEntity.getDistance(), 13);
        assertEquals(tourEntity.getTimeStart().toString(), "2025-05-22 08:35:00.0");
        assertEquals(tourEntity.getTimeEnd().toString(), "2025-05-22 10:35:00.0");
        assertEquals(tourEntity.getInformation(), "test");
        assertEquals(tourEntity.getPopularity(), 0);
        assertEquals(tourEntity.getChildfriendliness(), 0);
    }

    @Test
    @Sql(scripts = "/tourTest.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    public void deleteTourTest() {
        List<Tour> tours = tourService.getTours();
        TourEntity tourEntity = tourMapper.toEntity(tours.get(0));
        assertEquals(tours.size(), 1);
        tourService.deleteTour(tourEntity.getId());
        tours = tourService.getTours();
        assertEquals(tours.size(), 0);
    }

    @Test
    @Sql(scripts = "/saveTourTest.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    public void addDeleteFirstTourTest() throws ParseException{
        String timeStart = "2025-05-22 08:35:00.00";
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.S");
        Date date = formatter.parse(timeStart);

        String timeEnd = "2025-05-22 10:35:00.00";
        SimpleDateFormat formatterEnd = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.S");
        Date dateEnd = formatterEnd.parse(timeEnd);

        Tour tour = Tour.builder()
                .name("new_tour")
                .description("new_description")
                .start("start")
                .end("end")
                .transportMode("transport_mode")
                .distance(3)
                .timeStart(date)
                .timeEnd(dateEnd)
                .information("information")
                .build();

        tourService.addTour(tour);

        List<Tour> tours = tourService.getTours();
        TourEntity secondTour = tourMapper.toEntity(tours.get(0));
        assertEquals(secondTour.getName(), "new_tour");
        assertEquals(secondTour.getDescription(), "new_description");
        assertEquals(secondTour.getStart(), "start");
        assertEquals(secondTour.getEnd(), "end");
        assertEquals(secondTour.getTransportMode(), "transport_mode");
        assertEquals(secondTour.getDistance(), 3);
        assertEquals(secondTour.getTimeStart().toString(), "2025-05-22 08:35:00.0");
        assertEquals(secondTour.getTimeEnd().toString(), "2025-05-22 10:35:00.0");
        assertEquals(secondTour.getInformation(), "information");
        assertEquals(secondTour.getPopularity(), 0);
        assertEquals(secondTour.getChildfriendliness(), 0);

        assertEquals(tours.size(), 1);

        tourService.deleteTour(secondTour.getId());
        tours = tourService.getTours();
        assertEquals(tours.size(), 0);
    }

    @Test
    @Sql(scripts = "/logsTest.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    public void checkIfTourExistsTrueTest() {
        Tour tour = tourService.checkIfTourExists(1L);

        assertNotNull(tour);
    }

    @Test
    @Sql(scripts = "/logsTest.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    public void checkIfTourExistsFalseTest() {
        Tour tour = tourService.checkIfTourExists(2L);
        assertNull(tour);
    }
}
