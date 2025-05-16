package at.fhtw.tourplannerbe.service.impl;

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
        tourRepository.save(tourMapper.toEntity(tour));
    }
}
