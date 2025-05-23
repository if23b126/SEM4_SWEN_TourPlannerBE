package at.fhtw.tourplannerbe.service.impl;

import at.fhtw.tourplannerbe.persitence.TourEntity;
import at.fhtw.tourplannerbe.service.TourService;
import at.fhtw.tourplannerbe.service.dtos.Tour;
import at.fhtw.tourplannerbe.service.mapper.TourMapper;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class TourServiceTestImpl {

    @Autowired
    private TourService tourService;

    @Autowired
    private TourMapper tourMapper;

    @Test
    public void getToursTest() {
        List<Tour> tours = tourService.getTours();
        TourEntity tourEntity = tourMapper.toEntity(tours.get(0));
        assertEquals(tourEntity.getName(), "test");
        assertEquals(tourEntity.getDescription(), "test");
        assertEquals(tourEntity.getStart(), "test");
        assertEquals(tourEntity.getEnd(), "test");
        assertEquals(tourEntity.getTransportMode(), "test");
        assertEquals(tourEntity.getDistance(), 3);
        assertEquals(tourEntity.getTimeStart(), "2025-05-22 08:35:00.000");
        assertEquals(tourEntity.getTimeEnd(), "2025-05-22 10:35:00.000");
        assertEquals(tourEntity.getInformation(), "test");



    }
}
