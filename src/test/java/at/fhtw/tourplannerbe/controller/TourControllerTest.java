package at.fhtw.tourplannerbe.controller;

import at.fhtw.tourplannerbe.service.TourService;
import at.fhtw.tourplannerbe.service.dtos.Tour;
import at.fhtw.tourplannerbe.service.mapper.TourMapper;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MockMvcBuilder;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.mockito.ArgumentMatchers.eq;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

import java.util.List;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
public class TourControllerTest {

    private MockMvc mockMvc;

    @Mock
    private TourService tourService;

    private Tour tour;
    @Autowired
    private ObjectMapper objectMapper;

    @BeforeEach
    public void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(new TourController(tourService)).build();

        tour = Tour.builder()
                .id(1L)
                .name("Tour 1")
                .description("Description 1")
                .start("16.371067064923125,48.23482153369105")
                .end("16.377418510346146,48.239204433836875")
                .transportMode("foot-walking")
                .information("Information 1")
                .build();
    }

    @Test
    public void getAllToursTest() throws Exception {
        when(tourService.getTours()).thenReturn(List.of(tour));

        mockMvc.perform(get("/tour"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().json(objectMapper.writeValueAsString(List.of(tour))));
    }

    @Test
    public void searchTourTest() throws Exception {
    }
}
