package at.fhtw.tourplannerbe.controller;

import at.fhtw.tourplannerbe.service.MapService;
import at.fhtw.tourplannerbe.service.dtos.Coordinate;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.List;

import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
public class MapControllerTest {
    private MockMvc mockMvc;

    @Mock
    private MapService mapService;

    private List<Coordinate> route;
    private List<Coordinate> startEnd;
    private String transportMode;
    @Autowired
    private ObjectMapper objectMapper;

    @BeforeEach
    public void setup() {
        mockMvc = MockMvcBuilders.standaloneSetup(new MapController(mapService)).build();

        route = List.of(Coordinate.builder()
                        .lat("48.200871")
                        .lon("16.370018")
                        .build(),
                Coordinate.builder()
                        .lat("48.200762")
                        .lon("16.370123")
                        .build(),
                Coordinate.builder()
                        .lat("16.370201")
                        .lon("48.200735")
                        .build());

        startEnd = List.of(Coordinate.builder()
                        .lat("48.200871")
                        .lon("16.370018")
                        .build(),
                Coordinate.builder()
                        .lat("16.370201")
                        .lon("48.200735")
                        .build());

        transportMode = "foot-walking";
    }

    @Test
    public void getRouteTest() throws Exception {
        when(mapService.getRoute(eq(startEnd), eq(transportMode))).thenReturn(route);

        mockMvc.perform(post("/osm/%s".formatted(transportMode))
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(startEnd)))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().string(objectMapper.writeValueAsString(route)));
    }
}
