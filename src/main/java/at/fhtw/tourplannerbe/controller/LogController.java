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
    @PutMapping
    public void updateLogs(@RequestBody Log log) {
        LogController.log.info("Updating logs to database");
        logService.updateLogs(log);
    }

    @DeleteMapping("/{id}")
    public void deleteLogs(@PathVariable long id) {
        log.info("Deleting logs from database");
        logService.deleteLogs(id);
    }

    @GetMapping("/search/{comment}")
    public List<Log> searchLogs(@PathVariable String comment) {
        log.info("Searching logs from database");
        return logService.getSearchLogs(comment);
    }

    @GetMapping("/{tourid}")
    public List<Log> getLogs(@PathVariable long tourid) {
        log.info("Getting logs for tour from database");
        return logService.getLogsForTour(Tour.builder().id(tourid).build());
    }
}
