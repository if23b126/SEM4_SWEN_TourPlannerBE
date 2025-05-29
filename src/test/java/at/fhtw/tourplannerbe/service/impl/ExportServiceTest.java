package at.fhtw.tourplannerbe.service.impl;

import at.fhtw.tourplannerbe.service.ExportService;
import at.fhtw.tourplannerbe.service.dtos.Log;
import at.fhtw.tourplannerbe.service.dtos.Tour;
import at.fhtw.tourplannerbe.service.dtos.TourImportExport;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
public class ExportServiceTest {
    @Autowired
    private ExportService exportService;

    @Test
    @Sql(scripts = "/exportTest.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    public void exportTest() {
        TourImportExport tourImportExport = exportService.exportTour(1L);

        Tour tour = tourImportExport.getTour();
        Log log = tourImportExport.getLogs().get(0);

        assertEquals("test", tour.getName());
        assertEquals("test", tour.getDescription());
        assertEquals("test", tour.getStart());
        assertEquals("test", tour.getEnd());
        assertEquals("test", tour.getTransportMode());
        assertEquals(3, tour.getDistance());
        assertEquals("2025-05-22 08:35:00.0", tour.getTimeStart().toString());
        assertEquals("2025-05-22 10:35:00.0", tour.getTimeEnd().toString());
        assertEquals("test", tour.getInformation());

        assertEquals("test", log.getComment());
        assertEquals(1, log.getDifficulty());
        assertEquals(2, log.getDistance());
        assertEquals(3, log.getRating());
        assertEquals("2025-05-22 12:35:00.0", log.getTime().toString());
        assertEquals("2025-05-22 08:35:00.0", log.getTimeStart().toString());
        assertEquals("2025-05-22 10:35:00.0", log.getTimeEnd().toString());
        assertEquals(tour.getId(), log.getTourid());
    }
}
