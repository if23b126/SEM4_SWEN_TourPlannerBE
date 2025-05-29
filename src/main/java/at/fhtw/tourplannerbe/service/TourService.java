package at.fhtw.tourplannerbe.service;

import at.fhtw.tourplannerbe.persitence.TourEntity;
import at.fhtw.tourplannerbe.service.dtos.Tour;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

public interface TourService {
    List<Tour> getTours();
    Tour addTour(@RequestBody Tour tour);
    void updateTour(@RequestBody Tour tour);
    void deleteTour(long id);
    List<TourEntity> getSearchTour(String name);
    Tour getTourById(long id);
    Tour checkIfTourExists(long id);
}
