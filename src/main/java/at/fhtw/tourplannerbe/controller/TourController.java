package at.fhtw.tourplannerbe.controller;

import at.fhtw.tourplannerbe.persitence.TourEntity;
import at.fhtw.tourplannerbe.service.TourService;
import at.fhtw.tourplannerbe.service.dtos.Tour;
import at.fhtw.tourplannerbe.service.impl.TourServiceImpl;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("tour")
@RequiredArgsConstructor
@Slf4j
public class TourController {

    private final TourService tourService;

    @CrossOrigin
    @GetMapping
    public List<Tour> getAllTours() {
        log.info("Get all tours");
        return tourService.getTours();
    }

    @CrossOrigin
    @PostMapping
    public void saveTour(@RequestBody Tour tour) throws IOException {
        log.info("Save tour");
        tourService.addTour(tour);
    }

    @CrossOrigin
    @PutMapping("/{id}")
    public void updateTour(@RequestBody Tour tour, @PathVariable long id) {
        System.out.println(tour);
        log.info("Update tour");
        tour.setId(id);
        System.out.println(id);
        System.out.println(tour);
        tourService.updateTour(tour);
    }

    @CrossOrigin
    @DeleteMapping("/{id}")
    public void deleteTour(@PathVariable long id) {
        log.info("Delete tour");
        tourService.deleteTour(id);
    }

    @GetMapping("/search/{name}")
    public List<TourEntity> searchTour(@PathVariable String name) {
        log.info("Searching tour from database");
        return tourService.getSearchTour(name);
    }
}
