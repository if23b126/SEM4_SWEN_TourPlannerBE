package at.fhtw.tourplannerbe.controller;

import at.fhtw.tourplannerbe.service.LogService;
import at.fhtw.tourplannerbe.service.dtos.Log;
import at.fhtw.tourplannerbe.service.dtos.Tour;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("logs")
@RequiredArgsConstructor
@Slf4j
public class LogController {
    private final LogService logService;

    @CrossOrigin
    @PostMapping
    public void saveLogs(@RequestBody Log log) {
        LogController.log.info("Saving logs to database");
        logService.addLogs(log);
    }

    @CrossOrigin
    @PutMapping("/{id}")
    public void updateLogs(@RequestBody Log log, @PathVariable long id) {
        log.setId(id);
        LogController.log.info("Updating logs to database");
        logService.updateLogs(log);
    }

    @CrossOrigin
    @DeleteMapping("/{id}")
    public void deleteLogs(@PathVariable long id) {
        log.info("Deleting logs from database");
        logService.deleteLogs(id);
    }

    @CrossOrigin
    @GetMapping("/search/{comment}")
    public List<Log> searchLogs(@PathVariable String comment) {
        log.info("Searching logs from database");
        return logService.getSearchLogs(comment);
    }

    @CrossOrigin
    @GetMapping("/{tourId}")
    public List<Log> getLogs(@PathVariable long tourId) {
        log.info("Getting logs for tour from database");
        return logService.getLogsForTour(Tour.builder().id(tourId).build());
    }

    @CrossOrigin
    @GetMapping("/findOneLog/{id}")
    public Log getOneLog(@PathVariable long id) {
        log.info("Getting log from database");
        return logService.getLogById(id);
    }
}
