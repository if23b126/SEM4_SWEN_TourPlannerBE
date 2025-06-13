package at.fhtw.tourplannerbe.service.impl;

import at.fhtw.tourplannerbe.persitence.LogEntity;
import at.fhtw.tourplannerbe.persitence.LogRepository;
import at.fhtw.tourplannerbe.persitence.TourEntity;
import at.fhtw.tourplannerbe.persitence.TourRepository;
import at.fhtw.tourplannerbe.service.LogService;
import at.fhtw.tourplannerbe.service.MapService;
import at.fhtw.tourplannerbe.service.TourService;
import at.fhtw.tourplannerbe.service.dtos.Log;
import at.fhtw.tourplannerbe.service.dtos.Tour;
import at.fhtw.tourplannerbe.service.mapper.LogsMapper;
import at.fhtw.tourplannerbe.service.mapper.TourMapper;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.sql.Time;
import java.util.Arrays;
import java.util.Collections;
import java.util.Date;
import java.util.List;


@Service
@Slf4j
@RequiredArgsConstructor
public class TourServiceImpl implements TourService {

    private final TourRepository tourRepository;
    private final TourMapper tourMapper;
    private final MapService mapService;

    @Override
    public List<Tour> getTours() {
        return tourMapper.toDto(tourRepository.findAll());
    }

    @Override
    public Tour addTour(Tour tour) throws IOException {
        String[] start =  tour.getStart().split(",");

        String[] end =  tour.getEnd().split(",");

        double[] durationDistance = mapService.getDistanceAndDuration(start, end, "foot-walking");

        tour.setDuration(durationDistance[0]);
        tour.setDistance(durationDistance[1]);
        TourEntity tourEntity = tourRepository.save(tourMapper.toEntity(tour));
        return tourMapper.toDto(tourEntity);
    }

    @Override
    public void updateTour(Tour tour) {
        TourEntity toFindTour = tourMapper.toEntity(tour);
        System.out.println("von toFindTour am anfang: " + toFindTour);
        TourEntity tour1 = tourRepository.findById(toFindTour.getId()).orElse(null);
        System.out.println("von tour1 am anfang: " + tour1);

        if (tour1 != null) {
            tour1.setId(toFindTour.getId() == null ? tour1.getId() : toFindTour.getId());
            tour1.setName(toFindTour.getName() == null ? tour1.getName() : toFindTour.getName());
            tour1.setDescription(toFindTour.getDescription() == null ? tour1.getDescription() : toFindTour.getDescription());
            tour1.setStart(toFindTour.getStart() == null ? tour1.getStart() : toFindTour.getStart());
            tour1.setEnd(toFindTour.getEnd() == null ? tour1.getEnd() : toFindTour.getEnd());
            tour1.setTransportMode(toFindTour.getTransportMode() == null ? tour1.getTransportMode() : toFindTour.getTransportMode());
            tour1.setDistance(toFindTour.getDistance() < 0 ? tour1.getDistance() : toFindTour.getDistance());
            tour1.setDuration(toFindTour.getDuration() < 0 ? tour1.getDuration() : toFindTour.getDuration());
            tour1.setInformation(toFindTour.getInformation() == null ? tour1.getInformation() : toFindTour.getInformation());
            //tour1.setPopularity(toFindTour.getPopularity() - tour1.getPopularity() != 0 ? tour1.getPopularity() : toFindTour.getPopularity());
            //tour1.setChildfriendliness(toFindTour.getChildfriendliness() <= 0 ? tour1.getChildfriendliness() : toFindTour.getChildfriendliness());
            //tour1.setPopularity(toFindTour.getPopularity() != tour1.getPopularity() && toFindTour.getPopularity() != 0 ? toFindTour.getPopularity() : tour1.getPopularity());
            tour1.setPopularity(( toFindTour.getPopularity() == tour1.getPopularity() || (tour1.getPopularity() != 0 && toFindTour.getPopularity() == 0)) ? tour1.getPopularity() : toFindTour.getPopularity());
            tour1.setChildfriendliness((toFindTour.getChildfriendliness() == tour1.getChildfriendliness() || (tour1.getChildfriendliness() != 0 && toFindTour.getChildfriendliness() == 0)) ? tour1.getChildfriendliness() : toFindTour.getChildfriendliness());


            System.out.println("von updateTour: " + tour1);
            tourRepository.save(tour1);
        }
    }

    @Override
    public Tour checkIfTourExists(long id){
        TourEntity tourEntity = tourRepository.findById(id).orElse(null);
        return tourEntity != null ? tourMapper.toDto(tourEntity) : null;
    }

    @Override
    public Tour getTourById(long id) {
        return checkIfTourExists(id);
    }

    public void deleteTour(long id) {
        tourRepository.deleteById(id);
    }

    public List<TourEntity> getSearchTour(String name){
        return tourRepository.searchTour(name);
    }

    public Tour createTourPopularity(long id, List<Log> logs) {
        TourEntity tourEntity = tourRepository.findById(id).orElse(null);
        double rating = 0;
        System.out.println("from createPopularity: " + logs);
        System.out.println("from createPopularity: " + tourEntity);
        for (Log log : logs) {
            rating += log.getRating();
            System.out.println("rating:     " + rating);
        }
        tourEntity.setPopularity(rating / logs.size());
        System.out.println("rating / logs.size(): " + rating / logs.size());
        updateTour(tourMapper.toDto(tourEntity));
        System.out.println("tourEntity" + tourEntity);

        return tourMapper.toDto(tourEntity);
    }

    public void createTourChildfriendlinessWithLogs(Tour tour, List<Log> logs) {
        double timeResult = 0;
        for (Log log : logs) {
            double time = log.getTimeEnd().getTime() - log.getTimeStart().getTime();
            time = time/3600000;
            timeResult += time;
        }
        timeResult = timeResult / logs.size();
//        double time = tour.getTimeEnd().getTime() - tour.getTimeStart().getTime();
//        time = time/3600000;
        int timeRating = 0;
        if (timeResult < 0.5) {
            timeRating = 1;
        } else if (timeResult > 0.5 && timeResult < 1.5) {
            timeRating = 2;
        } else if (timeResult > 1.5 && timeResult < 2.5) {
            timeRating = 3;
        } else if (timeResult > 2.5 && timeResult < 3.5) {
            timeRating = 4;
        }else if (timeResult > 3.5){
            timeRating = 5;
        }

        int distanceRating = 0;
        if(tour.getDistance()<1.5){
            distanceRating = 1;
        }else if(tour.getDistance()>1.5 && tour.getDistance()<2.5){
            distanceRating = 2;
        }else if(tour.getDistance()>2.5 && tour.getDistance()<3.5){
            distanceRating = 3;
        }else if(tour.getDistance()>3.5 && tour.getDistance()<4.5){
            distanceRating = 4;
        } else if (tour.getDistance()>4.5) {
            distanceRating = 5;
        }

        if (!logs.isEmpty()) {
            double difficultyRating = 0;
            for (Log log : logs) {
                difficultyRating += log.getDifficulty();
            }


            System.out.println("difficultyRating:    " + difficultyRating);
            difficultyRating = difficultyRating / logs.size();
            System.out.println("logs.size():    " + logs.size());
            System.out.println("distanceRating:    " + distanceRating);
            System.out.println("timeRating:    " + timeRating);
            System.out.println("time:    " + timeResult);

            double childfriendnessRating = 0;
            childfriendnessRating = difficultyRating + timeRating + distanceRating;
            if (childfriendnessRating <=3){
                tour.setChildfriendliness(5.0);
            } else if (childfriendnessRating > 3 && childfriendnessRating <= 6) {
                tour.setChildfriendliness(4.0);
            } else if (childfriendnessRating > 6 && childfriendnessRating <= 9) {
                tour.setChildfriendliness(3.0);
            }else if (childfriendnessRating > 9 && childfriendnessRating <= 12) {
                tour.setChildfriendliness(2.0);
            }else if (childfriendnessRating > 12) {
                tour.setChildfriendliness(1.0);
            }

            updateTour(tour);
        }


    }
}