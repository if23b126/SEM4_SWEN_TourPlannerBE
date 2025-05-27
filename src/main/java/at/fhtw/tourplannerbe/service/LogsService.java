package at.fhtw.tourplannerbe.service;

import at.fhtw.tourplannerbe.persitence.LogsEntity;
import at.fhtw.tourplannerbe.persitence.TourEntity;
import at.fhtw.tourplannerbe.service.dtos.Logs;
import at.fhtw.tourplannerbe.service.dtos.Tour;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

public interface LogsService {
    void addLogs(@RequestBody Logs logs);
    void updateLogs(@RequestBody Logs logs);
    void deleteLogs(long id);
    List<Logs> getLogsForTour(Tour tour);
    Tour checkIfTourExists(long id);
    List<Logs> getSearchLogs(String comment);
}
