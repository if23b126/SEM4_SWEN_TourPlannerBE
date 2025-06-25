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
import java.io.IOException;
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
        assertEquals("test", tourEntity.getName());
        assertEquals("test", tourEntity.getDescription());
        assertEquals("test", tourEntity.getStart());
        assertEquals("test", tourEntity.getEnd());
        assertEquals("test", tourEntity.getTransportMode());
        assertEquals(3, tourEntity.getDistance());
        assertEquals(2, tourEntity.getDuration());
        assertEquals("test", tourEntity.getInformation());
        assertEquals(0, tourEntity.getPopularity());
        assertEquals(0, tourEntity.getChildfriendliness());
    }


    @Test
    @Sql(scripts = "/tourTest.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    public void getOneTour() {
        Tour tour = tourService.getOneTour(1L);
        TourEntity tourEntity = tourMapper.toEntity(tour);
        assertEquals("test", tourEntity.getName());
        assertEquals("test", tourEntity.getDescription());
        assertEquals("test", tourEntity.getStart());
        assertEquals("test", tourEntity.getEnd());
        assertEquals("test", tourEntity.getTransportMode());
        assertEquals(3, tourEntity.getDistance());
        assertEquals(2, tourEntity.getDuration());
        assertEquals("test", tourEntity.getInformation());
        assertEquals(0, tourEntity.getPopularity());
        assertEquals(0, tourEntity.getChildfriendliness());
    }




    @Test
    @Sql(scripts = "/saveTourTest.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    public void saveTourTest() throws IOException {

        Tour tour = Tour.builder()
                .name("new_tour")
                .description("new_description")
                .start("16.37038797323949,48.2010386430652")
                .end("16.37367838085627,48.23761334405697")
                .transportMode("foot-walking")
                .information("information")
                .build();

        tourService.addTour(tour);

        List<Tour> tours = tourService.getTours();
        TourEntity tourEntity = tourMapper.toEntity(tours.get(0));

        assertEquals("new_tour", tourEntity.getName());
        assertEquals("new_description", tourEntity.getDescription());
        assertEquals("16.37038797323949,48.2010386430652", tourEntity.getStart());
        assertEquals("16.37367838085627,48.23761334405697", tourEntity.getEnd());
        assertEquals("foot-walking", tourEntity.getTransportMode());
        assertEquals(5036.75, tourEntity.getDistance());
        assertEquals(3626.38, tourEntity.getDuration());
        assertEquals("information", tourEntity.getInformation());
        assertEquals(0, tourEntity.getPopularity());
        assertEquals(0, tourEntity.getChildfriendliness());
    }


    @Test
    @Sql(scripts = "/tourTest.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    public void saveSecondTourTest() throws ParseException, IOException {

        Tour tour = Tour.builder()
                .name("new_tour")
                .description("new_description")
                .start("16.37038797323949,48.2010386430652")
                .end("16.37367838085627,48.23761334405697")
                .transportMode("foot-walking")
                .information("information")
                .build();

        tourService.addTour(tour);

        List<Tour> tours = tourService.getTours();
        TourEntity tourEntity = tourMapper.toEntity(tours.get(0));

        assertEquals(2, tours.size());


        assertEquals("test", tourEntity.getName());
        assertEquals("test", tourEntity.getDescription());
        assertEquals("test", tourEntity.getStart());
        assertEquals("test", tourEntity.getEnd());
        assertEquals("test", tourEntity.getTransportMode());
        assertEquals(3, tourEntity.getDistance());
        assertEquals(2, tourEntity.getDuration());
        assertEquals("test", tourEntity.getInformation());
        assertEquals(0, tourEntity.getPopularity());
        assertEquals(0, tourEntity.getChildfriendliness());


        TourEntity secondTour = tourMapper.toEntity(tours.get(1));

        assertEquals("new_tour", secondTour.getName());
        assertEquals("new_description", secondTour.getDescription());
        assertEquals("16.37038797323949,48.2010386430652", secondTour.getStart());
        assertEquals("16.37367838085627,48.23761334405697", secondTour.getEnd());
        assertEquals("foot-walking", secondTour.getTransportMode());
        assertEquals(5036.75, secondTour.getDistance());
        assertEquals(3626.38, secondTour.getDuration());
        assertEquals("information", secondTour.getInformation());
        assertEquals(0, secondTour.getPopularity());
        assertEquals(0, secondTour.getChildfriendliness());
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
        tourEntity.setDuration(3);
        tourEntity.setInformation("information");

        tourService.updateTour(tourMapper.toDto(tourEntity));

        tours = tourService.getTours();
        tourEntity = tourMapper.toEntity(tours.get(0));
        assertEquals("new_name", tourEntity.getName());
        assertEquals("new_description", tourEntity.getDescription());
        assertEquals("start", tourEntity.getStart());
        assertEquals("end", tourEntity.getEnd());
        assertEquals("transport_mode", tourEntity.getTransportMode());
        assertEquals(13, tourEntity.getDistance());
        assertEquals(3, tourEntity.getDuration());
        assertEquals("information", tourEntity.getInformation());
        assertEquals(0, tourEntity.getPopularity());
        assertEquals(0, tourEntity.getChildfriendliness());

    }

    @Test
    @Sql(scripts = "/tourTest.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    public void noUpdateTourTest(){
        List<Tour> tours = tourService.getTours();
        TourEntity tourEntity = tourMapper.toEntity(tours.get(0));
        tourService.updateTour(tourMapper.toDto(tourEntity));
        tours = tourService.getTours();
        tourEntity = tourMapper.toEntity(tours.get(0));
        assertEquals("test", tourEntity.getName());
        assertEquals("test", tourEntity.getDescription());
        assertEquals("test", tourEntity.getStart());
        assertEquals("test", tourEntity.getEnd());
        assertEquals("test", tourEntity.getTransportMode());
        assertEquals(3, tourEntity.getDistance());
        assertEquals(2, tourEntity.getDuration());
        assertEquals("test", tourEntity.getInformation());
        assertEquals(0, tourEntity.getPopularity());
        assertEquals(0, tourEntity.getChildfriendliness());
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
        assertEquals("new_name", tourEntity.getName());
        assertEquals("new_description", tourEntity.getDescription());
        assertEquals("start", tourEntity.getStart());
        assertEquals("test", tourEntity.getEnd());
        assertEquals("test", tourEntity.getTransportMode());
        assertEquals(3, tourEntity.getDistance());
        assertEquals(2, tourEntity.getDuration());
        assertEquals("test", tourEntity.getInformation());
        assertEquals(0, tourEntity.getPopularity());
        assertEquals(0, tourEntity.getChildfriendliness());
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
        tourEntity.setDuration(2);
        tourEntity.setInformation(null);

        tourService.updateTour(tourMapper.toDto(tourEntity));

        tours = tourService.getTours();
        tourEntity = tourMapper.toEntity(tours.get(0));
        assertEquals("new_name", tourEntity.getName());
        assertEquals("test", tourEntity.getDescription());
        assertEquals("test", tourEntity.getStart());
        assertEquals("test", tourEntity.getEnd());
        assertEquals("test", tourEntity.getTransportMode());
        assertEquals(13, tourEntity.getDistance());
        assertEquals(2, tourEntity.getDuration());
        assertEquals("test", tourEntity.getInformation());
        assertEquals(0, tourEntity.getPopularity());
        assertEquals(0, tourEntity.getChildfriendliness());
    }

    @Test
    @Sql(scripts = "/tourTest.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    public void deleteTourTest() {
        List<Tour> tours = tourService.getTours();
        TourEntity tourEntity = tourMapper.toEntity(tours.get(0));
        assertEquals(1, tours.size());
        tourService.deleteTour(tourEntity.getId());
        tours = tourService.getTours();
        assertEquals(0, tours.size());
    }

    @Test
    @Sql(scripts = "/saveTourTest.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    public void addDeleteFirstTourTest() throws ParseException, IOException {
        String timeStart = "2025-05-22 08:35:00.00";
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.S");
        Date date = formatter.parse(timeStart);

        String timeEnd = "2025-05-22 10:35:00.00";
        SimpleDateFormat formatterEnd = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.S");
        Date dateEnd = formatterEnd.parse(timeEnd);

        Tour tour = Tour.builder()
                .name("new_tour")
                .description("new_description")
                .start("16.37038797323949,48.2010386430652")
                .end("16.37367838085627,48.23761334405697")
                .transportMode("foot-walking")
                .distance(3)
                .duration(2)
                .information("information")
                .build();

        tourService.addTour(tour);

        List<Tour> tours = tourService.getTours();
        TourEntity secondTour = tourMapper.toEntity(tours.get(0));
        assertEquals("new_tour", secondTour.getName());
        assertEquals("new_description", secondTour.getDescription());
        assertEquals("16.37038797323949,48.2010386430652", secondTour.getStart());
        assertEquals("16.37367838085627,48.23761334405697", secondTour.getEnd());
        assertEquals("foot-walking", secondTour.getTransportMode());
        assertEquals(5036.75, secondTour.getDistance());
        assertEquals(3626.38, secondTour.getDuration());
        assertEquals("information", secondTour.getInformation());
        assertEquals(0, secondTour.getPopularity());
        assertEquals(0, secondTour.getChildfriendliness());

        assertEquals(1, tours.size());

        tourService.deleteTour(secondTour.getId());
        tours = tourService.getTours();
        assertEquals(0, tours.size());
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
