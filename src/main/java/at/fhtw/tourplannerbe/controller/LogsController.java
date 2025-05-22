package at.fhtw.tourplannerbe.controller;

import at.fhtw.tourplannerbe.persitence.LogsEntity;
import at.fhtw.tourplannerbe.service.LogsService;
import at.fhtw.tourplannerbe.service.dtos.Logs;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.jpa.repository.Query;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("logs")
@RequiredArgsConstructor
@Slf4j
public class LogsController {
    private final LogsService logsService;

    @CrossOrigin
    @PostMapping
    public void saveLogs(@RequestBody Logs logs) {
        log.info("Saving logs to database");
        logsService.addLogs(logs);
    }

    @CrossOrigin
    @PutMapping
    public void updateLogs(@RequestBody Logs logs) {
        log.info("Updating logs to database");
        logsService.updateLogs(logs);
    }

    @DeleteMapping("/{id}")
    public void deleteLogs(@PathVariable long id) {
        log.info("Deleting logs from database");
        logsService.deleteLogs(id);
    }

    @GetMapping("/search/{comment}")
    public List<LogsEntity> searchLogs(@PathVariable String comment) {
        log.info("Searching logs from database");
        return logsService.getSearchLogs(comment);
    }
}
