package at.fhtw.tourplannerbe.service.impl;

import at.fhtw.tourplannerbe.service.ImportService;
import at.fhtw.tourplannerbe.service.LogService;
import at.fhtw.tourplannerbe.service.TourService;
import at.fhtw.tourplannerbe.service.dtos.Log;
import at.fhtw.tourplannerbe.service.dtos.Tour;
import at.fhtw.tourplannerbe.service.dtos.TourImportExport;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
public class ImportServiceTest {
    @Autowired
    private ImportService importService;
    @Autowired
    private LogService logService;
    @Autowired
    private TourService tourService;

    @Test
    @Sql(scripts = "/importTest.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    public void importTest() throws ParseException, IOException {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.S");

        String timeStartString = "2025-05-22 08:35:00.00";
        Date date = formatter.parse(timeStartString);

        String timeEndString = "2025-05-22 10:35:00.00";
        Date dateEnd = formatter.parse(timeEndString);

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

        String timeString = "2025-05-22 12:35:00.0";
        Date time = formatter.parse(timeString);

        List<Log> logs = new ArrayList<>();
        logs.add(Log.builder()
                .comment("test")
                .difficulty(2)
                .distance(2)
                .rating(2)
                .time(time)
                .timeStart(date)
                .timeEnd(dateEnd)
                .build());

        TourImportExport tourImportExport = TourImportExport.builder().tour(tour).logs(logs).build();

        importService.importTour(tourImportExport);

        Tour tourInDB = tourService.getTourById(1L);
        List<Log> logsInDB = logService.getLogsForTour(tourInDB);

        assertEquals(tour.getName(), tourInDB.getName());
        assertEquals(tour.getDescription(), tourInDB.getDescription());
        assertEquals(tour.getStart(), tourInDB.getStart());
        assertEquals(tour.getEnd(), tourInDB.getEnd());
        assertEquals(tour.getTransportMode(), tourInDB.getTransportMode());
        assertEquals(tour.getDistance(), tourInDB.getDistance());
        assertEquals(tour.getDuration(), tourInDB.getDuration());
        assertEquals(tour.getInformation(), tourInDB.getInformation());

        assertEquals(logs.size(), logsInDB.size());
        assertEquals(logs.get(0).getComment(), logsInDB.get(0).getComment());
        assertEquals(logs.get(0).getDifficulty(), logsInDB.get(0).getDifficulty());
        assertEquals(logs.get(0).getDistance(), logsInDB.get(0).getDistance());
        assertEquals(logs.get(0).getRating(), logsInDB.get(0).getRating());
        assertEquals(logs.get(0).getTimeStart(), logsInDB.get(0).getTimeStart());
        assertEquals(logs.get(0).getTimeEnd(), logsInDB.get(0).getTimeEnd());
        assertEquals(logs.get(0).getTime(), logsInDB.get(0).getTime());
    }
}
