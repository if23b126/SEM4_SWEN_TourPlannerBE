package at.fhtw.tourplannerbe.service.impl;

import at.fhtw.tourplannerbe.persitence.LogsEntity;
import at.fhtw.tourplannerbe.persitence.TourEntity;
import at.fhtw.tourplannerbe.persitence.TourRepository;
import at.fhtw.tourplannerbe.service.TourService;
import at.fhtw.tourplannerbe.service.dtos.Tour;
import at.fhtw.tourplannerbe.service.mapper.TourMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

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
    public void addTour(Tour tour) {
        tourRepository.saveAndFlush(tourMapper.toEntity(tour));
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
            tourRepository.save(tour1);
        }
    }

    public void deleteTour(long id) {
        tourRepository.deleteById(id);
    }

    public List<TourEntity> getSearchTour(String name){
        return tourRepository.searchTour(name);
    }
}
