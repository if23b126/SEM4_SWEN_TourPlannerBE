package at.fhtw.tourplannerbe.service;

import at.fhtw.tourplannerbe.persitence.TourEntity;
import at.fhtw.tourplannerbe.service.dtos.Logs;
import org.springframework.web.bind.annotation.RequestBody;

public interface LogsService {
    void addLogs(@RequestBody Logs logs);
    void updateLogs(@RequestBody Logs logs);
    void deleteLogs(long id);
    TourEntity checkIfTourExists(long id);
}
