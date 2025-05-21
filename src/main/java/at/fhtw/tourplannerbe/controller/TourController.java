package at.fhtw.tourplannerbe.controller;

import at.fhtw.tourplannerbe.service.dtos.Tour;
import at.fhtw.tourplannerbe.service.impl.TourServiceImpl;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("tour")
@RequiredArgsConstructor
@Slf4j
public class TourController {

    private final TourServiceImpl tourService;

    @CrossOrigin
    @GetMapping
    public List<Tour> getAllTours() {
        log.info("Get all tours");
        return tourService.getTours();
    }

    @PostMapping
    public void saveTour(@RequestBody Tour tour) {
        log.info("Save tour");
        tourService.addTour(tour);
    }

    @PutMapping
    public void updateTour(@RequestBody Tour tour) {
        log.info("Update tour");
        tourService.updateTour(tour);
    }

    @DeleteMapping("/{id}")
    public void deleteTour(@PathVariable long id) {
        log.info("Delete tour");
        tourService.deleteTour(id);
    }
}
