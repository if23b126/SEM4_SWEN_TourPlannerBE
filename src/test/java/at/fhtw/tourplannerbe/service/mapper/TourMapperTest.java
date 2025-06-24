package at.fhtw.tourplannerbe.service.mapper;

import at.fhtw.tourplannerbe.persitence.TourEntity;
import at.fhtw.tourplannerbe.service.dtos.Tour;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.text.ParseException;
import java.text.SimpleDateFormat;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
public class TourMapperTest {

    @Autowired
    private TourMapper tourMapper;

    private SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");

    @Test
    public void MapDtoToEntityTest() throws ParseException {
        Tour tour = Tour.builder()
                .id(1L)
                .name("Tour 1")
                .description("Description 1")
                .start("16.371067064923125,48.23482153369105")
                .end("16.377418510346146,48.239204433836875")
                .transportMode("foot-walking")
                .distance(1)
                .duration(1)
                .information("Information 1")
                .timeCreated(sdf.parse("2025-06-24 08:00:00"))
                .popularity(1)
                .childfriendliness(1)
                .build();

        TourEntity entity = tourMapper.toEntity(tour);

        assertEquals(tour.getId(), entity.getId());
        assertEquals(tour.getName(), entity.getName());
        assertEquals(tour.getDescription(), entity.getDescription());
        assertEquals(tour.getStart(), entity.getStart());
        assertEquals(tour.getEnd(), entity.getEnd());
        assertEquals(tour.getTransportMode(), entity.getTransportMode());
        assertEquals(tour.getDistance(), entity.getDistance());
        assertEquals(tour.getDuration(), entity.getDuration());
        assertEquals(tour.getInformation(), entity.getInformation());
        assertEquals(tour.getTimeCreated(), entity.getTimeCreated());
        assertEquals(tour.getPopularity(), entity.getPopularity());
        assertEquals(tour.getChildfriendliness(), entity.getChildfriendliness());
    }

    @Test
    public void MapEntityToDtoTest() throws ParseException {
        TourEntity entity = TourEntity.builder()
                .id(1L)
                .name("Tour 1")
                .description("Description 1")
                .start("16.371067064923125,48.23482153369105")
                .end("16.377418510346146,48.239204433836875")
                .transportMode("foot-walking")
                .distance(1)
                .duration(1)
                .information("Information 1")
                .timeCreated(sdf.parse("2025-06-24 08:00:00"))
                .popularity(1)
                .childfriendliness(1)
                .build();

        Tour tour = tourMapper.toDto(entity);

        assertEquals(entity.getId(), tour.getId());
        assertEquals(entity.getName(), tour.getName());
        assertEquals(entity.getDescription(), tour.getDescription());
        assertEquals(entity.getStart(), tour.getStart());
        assertEquals(entity.getEnd(), tour.getEnd());
        assertEquals(entity.getTransportMode(), tour.getTransportMode());
        assertEquals(entity.getDistance(), tour.getDistance());
        assertEquals(entity.getDuration(), tour.getDuration());
        assertEquals(entity.getInformation(), tour.getInformation());
        assertEquals(entity.getTimeCreated(), tour.getTimeCreated());
        assertEquals(entity.getPopularity(), tour.getPopularity());
        assertEquals(entity.getChildfriendliness(), tour.getChildfriendliness());
    }
}
