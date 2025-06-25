package at.fhtw.tourplannerbe.service;

import at.fhtw.tourplannerbe.service.dtos.Log;
import at.fhtw.tourplannerbe.service.dtos.Tour;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

public interface LogService {
    void addLogs(@RequestBody Log log);
    void addLogsInBulk(List<Log> logs);
    void updateLogs(@RequestBody Log log);
    void deleteLogs(long id);
    List<Log> getLogsForTour(Tour tour);
    List<Log> getSearchLogs(String comment);
    Log getLogById(long id);
}
