package at.fhtw.tourplannerbe.service;

import at.fhtw.tourplannerbe.service.dtos.Tour;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

public interface TourService {
    List<Tour> getTours();
    void addTour(@RequestBody Tour tour);
}
