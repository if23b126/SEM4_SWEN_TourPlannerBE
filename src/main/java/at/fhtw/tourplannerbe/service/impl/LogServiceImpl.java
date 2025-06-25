package at.fhtw.tourplannerbe.service.impl;

import at.fhtw.tourplannerbe.persitence.LogEntity;
import at.fhtw.tourplannerbe.persitence.LogRepository;
import at.fhtw.tourplannerbe.persitence.TourEntity;
import at.fhtw.tourplannerbe.persitence.TourRepository;
import at.fhtw.tourplannerbe.service.LogService;
import at.fhtw.tourplannerbe.service.TourService;
import at.fhtw.tourplannerbe.service.dtos.Log;
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
public class LogServiceImpl implements LogService {
    private final LogRepository logRepository;
    private final LogsMapper logsMapper;
    private final TourRepository tourRepository;
    private final TourMapper tourMapper;
    private final TourService tourService;

    @Override
    public void addLogs(Log log){

        LogEntity toAddLogs = logsMapper.toEntity(log);

        if(toAddLogs.getId() != null && toAddLogs.getId() == 0){
            toAddLogs.setId(null);
        }

        Tour tour = tourService.checkIfTourExists(toAddLogs.getTourid());
        logRepository.save(toAddLogs);

        if(tour != null){
            List<Log> allLogs = getLogsForTour(tour);
            tour = tourService.createTourPopularity(toAddLogs.getTourid(), allLogs);
            tourService.createTourChildfriendlinessWithLogs(tour, allLogs);
            //logRepository.save(toAddLogs);
        }
    }

    @Override
    public void addLogsInBulk(List<Log> logs) {
        List<LogEntity> logsEntities = logsMapper.toEntity(logs);
        Tour tour = tourService.checkIfTourExists(logsEntities.get(0).getTourid());
        if(tour != null){
            logRepository.saveAll(logsEntities);
        }
    }

    @Override
    public void updateLogs(Log logs){
        LogEntity toFindLogs = logsMapper.toEntity(logs);
        LogEntity log = logRepository.findById(toFindLogs.getId()).orElse(null);
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

            logRepository.save(log);
            Tour tour = tourService.checkIfTourExists(log.getTourid());
            if(tour != null){
                List<Log> allLogs = getLogsForTour(tour);
                tour = tourService.createTourPopularity(log.getTourid(), allLogs);
                tourService.createTourChildfriendlinessWithLogs(tour, allLogs);
            }
            logRepository.save(log);
        }
    }

    @Override
    public List<Log> getLogsForTour(Tour tour) {
        List<Log> logs = logsMapper.toDto(logRepository.searchLogsForTour(tour.getId()));
        return logs;
    }

    @Override
    public void deleteLogs(long id){
        LogEntity log = logRepository.findById(id).orElse(null);
        TourEntity tour = tourRepository.findById(log.getTourid()).orElse(null);
        List<Log> logs = getLogsForTour(tourMapper.toDto(tour));
        logs.removeIf(l -> l.getId() == log.getId());
        logRepository.deleteById(id);
        if(logs.size() > 0){
            Tour newTour = tourService.createTourPopularity(log.getTourid(), logs);
            tourService.createTourChildfriendlinessWithLogs(newTour, logs);
        }
    }

    public List<Log> getSearchLogs(String comment){
       return logsMapper.toDto(logRepository.searchLogs(comment));
    }

    public Log getLogById(long id){
        Log log = logsMapper.toDto(logRepository.findById(id).orElse(null));
        return log;
    }
}
