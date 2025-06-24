package at.fhtw.tourplannerbe.controller;

import at.fhtw.tourplannerbe.service.ExportService;
import at.fhtw.tourplannerbe.service.dtos.Log;
import at.fhtw.tourplannerbe.service.dtos.Tour;
import at.fhtw.tourplannerbe.service.dtos.TourImportExport;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
public class ExportControllerTest {

    private MockMvc mockMvc;

    @Mock
    private ExportService exportService;

    private TourImportExport tour;

    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
    @Autowired
    private ObjectMapper objectMapper;

    @BeforeEach
    public void setup() throws ParseException {
        mockMvc = MockMvcBuilders.standaloneSetup(new ExportController(exportService)).build();

        tour = TourImportExport.builder()
                .tour(Tour.builder()
                        .id(1L)
                        .name("Tour 1")
                        .description("Description 1")
                        .start("16.371067064923125,48.23482153369105")
                        .end("16.377418510346146,48.239204433836875")
                        .transportMode("foot-walking")
                        .information("Information 1")
                        .build())
                .logs(List.of(Log.builder()
                        .id(1L)
                        .time(sdf.parse("2025-06-24 08:00:00"))
                        .comment("Comment 1")
                        .difficulty(1.0)
                        .distance(1.0)
                        .timeStart(sdf.parse("2025-06-24 07:00:00"))
                        .timeEnd(sdf.parse("2025-06-24 08:00:00"))
                        .rating(1.0)
                        .tourid(1L)
                        .build()))
                .build();
    }

    @Test
    public void exportTourTest() throws Exception {
        when(exportService.exportTour(eq(tour.getTour().getId()))).thenReturn(tour);

        mockMvc.perform(get("/export/%s".formatted(tour.getTour().getId())))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().json(objectMapper.writeValueAsString(tour)));
    }
}
