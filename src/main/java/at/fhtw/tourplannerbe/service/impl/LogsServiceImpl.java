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

import java.util.List;
import java.util.Optional;

@Service
@Slf4j
@RequiredArgsConstructor
public class LogsServiceImpl implements LogsService {
    private final LogsRepository logsRepository;
    private final LogsMapper logsMapper;
    private final TourRepository tourRepository;
    private final TourMapper tourMapper;
    private final TourService tourService;

    @Override
    public void addLogs(Logs logs){
        LogsEntity toAddLogs = logsMapper.toEntity(logs);
        Tour tour = tourService.checkIfTourExists(toAddLogs.getTourid());
        if(tour != null){
            List<Logs> allLogs = getLogsForTour(tour);
            tourService.createTourPopularity(toAddLogs.getTourid(), allLogs);
            tourService.createTourChildfriendlinessWithLogs(tour, allLogs);
            logsRepository.save(toAddLogs);
        }

    }

    @Override
    public void updateLogs(Logs logs){
        LogsEntity toFindLogs = logsMapper.toEntity(logs);
        LogsEntity log = logsRepository.findById(toFindLogs.getId()).orElse(null);
        if(log != null){
            log.setId(toFindLogs.getId() == null ? log.getId() : toFindLogs.getId());
            log.setTime(toFindLogs.getTime() == null ? log.getTime() : toFindLogs.getTime());
            log.setComment(toFindLogs.getComment() == null ? log.getComment() : toFindLogs.getComment());
            log.setDifficulty(toFindLogs.getDifficulty() < 0 ? log.getDifficulty() : toFindLogs.getDifficulty());
            log.setDistance(toFindLogs.getDistance() < 0 ? log.getDistance() : toFindLogs.getDistance());
            log.setTimeStart(toFindLogs.getTimeStart() == null ? log.getTimeStart() : toFindLogs.getTimeStart());
            log.setTimeEnd(toFindLogs.getTimeEnd() == null ? log.getTimeEnd() : toFindLogs.getTimeEnd());
            log.setRating(toFindLogs.getRating() < 0 ? log.getRating() : toFindLogs.getRating());
            log.setTourid(toFindLogs.getTourid() < 0 ? log.getTourid() : toFindLogs.getTourid());

            Tour tour = tourService.checkIfTourExists(log.getTourid());
            if(tour != null){
                System.out.println(tour);
                List<Logs> allLogs = getLogsForTour(tour);
                tourService.createTourPopularity(log.getTourid(), allLogs);
                tourService.createTourChildfriendlinessWithLogs(tour, allLogs);
            }
            logsRepository.save(log);
        }
    }

    @Override
    public List<Logs> getLogsForTour(Tour tour) {
        List<Logs> logs = logsMapper.toDto(logsRepository.searchLogsForTour(tour.getId()));
        return logs;
    }

    @Override
    public void deleteLogs(long id){
        LogsEntity log = logsRepository.findById(id).orElse(null);
        TourEntity tour = tourRepository.findById(log.getTourid()).orElse(null);
        logsRepository.deleteById(id);
    }

    public List<Logs> getSearchLogs(String comment){
       return logsMapper.toDto(logsRepository.searchLogs(comment));
    }
}
