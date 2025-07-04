package at.fhtw.tourplannerbe.service;

import at.fhtw.tourplannerbe.persitence.TourEntity;
import at.fhtw.tourplannerbe.service.dtos.Log;
import at.fhtw.tourplannerbe.service.dtos.Tour;
import org.springframework.web.bind.annotation.RequestBody;

import java.io.IOException;
import java.util.List;

public interface TourService {
    List<Tour> getTours();
    Tour addTour(@RequestBody Tour tour) throws IOException;
    void updateTour(@RequestBody Tour tour);
    void deleteTour(long id);
    List<TourEntity> getSearchTour(String name);
    Tour getTourById(long id);
    Tour checkIfTourExists(long id);
    Tour createTourPopularity(long id, List<Log> logs);
    void createTourChildfriendlinessWithLogs(Tour tour, List<Log> logs);
    Tour getOneTour(long id);
}