package at.fhtw.tourplannerbe.controller;

import at.fhtw.tourplannerbe.service.LogService;
import at.fhtw.tourplannerbe.service.dtos.Log;
import at.fhtw.tourplannerbe.service.dtos.Tour;
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
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
public class LogControllerTest {
    private MockMvc mockMvc;

    @Mock
    private LogService logService;

    private Tour tour;
    private List<Log> logs;
    @Autowired
    private ObjectMapper objectMapper;
    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");

    @BeforeEach
    public void setup() throws ParseException {
        mockMvc = MockMvcBuilders.standaloneSetup(new LogController(logService)).build();

        tour = Tour.builder()
                    .id(1L)
                    .name("Tour 1")
                    .description("Description 1")
                    .start("16.371067064923125,48.23482153369105")
                    .end("16.377418510346146,48.239204433836875")
                    .transportMode("foot-walking")
                    .information("Information 1")
                    .build();

        logs = List.of(Log.builder()
                        .id(1L)
                        .time(sdf.parse("2025-06-24 08:00:00"))
                        .comment("Comment 1")
                        .difficulty(1.0)
                        .distance(1.0)
                        .timeStart(sdf.parse("2025-06-24 07:00:00"))
                        .timeEnd(sdf.parse("2025-06-24 08:00:00"))
                        .rating(1.0)
                        .tourid(1L)
                        .build(),
                Log.builder()
                        .id(2L)
                        .time(sdf.parse("2025-06-24 08:00:00"))
                        .comment("Comment 2")
                        .difficulty(1.0)
                        .distance(1.0)
                        .timeStart(sdf.parse("2025-06-24 07:00:00"))
                        .timeEnd(sdf.parse("2025-06-24 08:00:00"))
                        .rating(1.0)
                        .tourid(1L)
                        .build(),
                Log.builder()
                        .id(3L)
                        .time(sdf.parse("2025-06-24 08:00:00"))
                        .comment("Comment 3")
                        .difficulty(1.0)
                        .distance(1.0)
                        .timeStart(sdf.parse("2025-06-24 07:00:00"))
                        .timeEnd(sdf.parse("2025-06-24 08:00:00"))
                        .rating(1.0)
                        .tourid(1L)
                        .build());
    }

    @Test
    public void getLogsForTourTest() throws Exception {
        when(logService.getLogsForTour(eq(Tour.builder().id(tour.getId()).build()))).thenReturn(logs);

        mockMvc.perform(get("/logs/%s".formatted(tour.getId())))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().json(objectMapper.writeValueAsString(logs)));
    }

    @Test
    public void searchLogTest() throws Exception {

    }
}
