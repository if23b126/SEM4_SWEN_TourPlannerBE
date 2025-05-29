package at.fhtw.tourplannerbe.service.impl;

import at.fhtw.tourplannerbe.persitence.LogsEntity;
import at.fhtw.tourplannerbe.persitence.LogsRepository;
import at.fhtw.tourplannerbe.persitence.TourEntity;
import at.fhtw.tourplannerbe.persitence.TourRepository;
import at.fhtw.tourplannerbe.service.LogsService;
import at.fhtw.tourplannerbe.service.TourService;
import at.fhtw.tourplannerbe.service.dtos.Logs;
import at.fhtw.tourplannerbe.service.dtos.Tour;
import at.fhtw.tourplannerbe.service.mapper.LogsMapper;
import at.fhtw.tourplannerbe.service.mapper.TourMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.sql.Time;
import java.util.Date;
import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class TourServiceImpl implements TourService {

    private final TourRepository tourRepository;
    private final TourMapper tourMapper;

    @Override
    public List<Tour> getTours() {
        return tourMapper.toDto(tourRepository.findAll());
    }

    @Override
    public Tour addTour(Tour tour) {
        TourEntity tourEntity = tourRepository.save(tourMapper.toEntity(tour));
        return tourMapper.toDto(tourEntity);
    }

    @Override
    public void updateTour(Tour tour) {
        TourEntity toFindTour = tourMapper.toEntity(tour);
        TourEntity tour1 = tourRepository.findById(toFindTour.getId()).orElse(null);
        if (tour1 != null) {
            tour1.setId(toFindTour.getId() == null ? tour1.getId() : toFindTour.getId());
            tour1.setName(toFindTour.getName() == null ? tour1.getName() : toFindTour.getName());
            tour1.setDescription(toFindTour.getDescription() == null ? tour1.getDescription() : toFindTour.getDescription());
            tour1.setStart(toFindTour.getStart() == null ? tour1.getStart() : toFindTour.getStart());
            tour1.setEnd(toFindTour.getEnd() == null ? tour1.getEnd() : toFindTour.getEnd());
            tour1.setTransportMode(toFindTour.getTransportMode() == null ? tour1.getTransportMode() : toFindTour.getTransportMode());
            tour1.setDistance(toFindTour.getDistance() < 0 ? tour1.getDistance() : toFindTour.getDistance());
            tour1.setTimeStart(toFindTour.getTimeStart() == null ? tour1.getTimeStart() : toFindTour.getTimeStart());
            tour1.setTimeEnd(toFindTour.getTimeEnd() == null ? tour1.getTimeEnd() : toFindTour.getTimeEnd());
            tour1.setInformation(toFindTour.getInformation() == null ? tour1.getInformation() : toFindTour.getInformation());
            tour1.setPopularity(toFindTour.getPopularity() - tour1.getPopularity() != 0 ? tour1.getPopularity() : toFindTour.getPopularity());
            tour1.setChildfriendliness(toFindTour.getChildfriendliness() <= 0 ? tour1.getChildfriendliness() : toFindTour.getChildfriendliness());
            System.out.println("tour1 ende:     " + tour1);

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

    public void createTourPopularity(long id, List<Logs> logs) {
        TourEntity tourEntity = tourRepository.findById(id).orElse(null);
        double rating = 0;
        for (Logs log : logs) {
            rating += log.getRating();
        }
        tourEntity.setPopularity(rating / logs.size());
        System.out.println(tourEntity);
        updateTour(tourMapper.toDto(tourEntity));
    }

    public int createTourChildfriendliness(Tour tour) {
        double time = tour.getTimeEnd().getTime() - tour.getTimeStart().getTime();
        int timeRating = 0;
        time = time/3600000;
        if (time < 0.5) {
            timeRating = 1;
        } else if (time > 0.5 && time < 1.5) {
            timeRating = 2;
        } else if (time > 1.5 && time < 2.5) {
            timeRating = 3;
        } else if (time > 2.5 && time < 3.5) {
            timeRating = 4;
        }else if (time > 3.5){
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

        System.out.println(time);
        System.out.println(timeRating);
        System.out.println(distanceRating);
        double avg = timeRating + distanceRating;
        System.out.println(avg);

        if (avg ==2){
            return 5;
        }else if (avg >2 && avg <=4){
            return 4;
        }else if (avg >4 && avg <=6){
            return 3;
        }else if (avg >6 && avg <=8){
            return 2;
        }else {
            return 1;
        }
    }


    public void createTourChildfriendlinessWithLogs(Tour tour, List<Logs> logs) {
        double time = tour.getTimeEnd().getTime() - tour.getTimeStart().getTime();
        int timeRating = 0;
        if (time < 0.5) {
            timeRating = 1;
        } else if (time > 0.5 && time < 1.5) {
            timeRating = 2;
        } else if (time > 1.5 && time < 2.5) {
            timeRating = 3;
        } else if (time > 2.5 && time < 3.5) {
            timeRating = 4;
        }else if (time > 3.5){
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
            for (Logs log : logs) {
                difficultyRating += log.getDifficulty();
            }

            difficultyRating = difficultyRating / logs.size();

            double childfriendnessRating = 0;
            childfriendnessRating = difficultyRating + timeRating + distanceRating;
            if (childfriendnessRating <=3){
                tour.setChildfriendliness(5);
            } else if (childfriendnessRating > 3 && childfriendnessRating <= 6) {
                tour.setChildfriendliness(4);
            } else if (childfriendnessRating > 6 && childfriendnessRating <= 9) {
                tour.setChildfriendliness(3);
            }else if (childfriendnessRating > 9 && childfriendnessRating <= 12) {
                tour.setChildfriendliness(2);
            }else if (childfriendnessRating > 12) {
                tour.setChildfriendliness(1);
            }

            updateTour(tour);
        }


    }
}