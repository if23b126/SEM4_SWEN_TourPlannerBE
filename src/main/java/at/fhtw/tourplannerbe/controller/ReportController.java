package at.fhtw.tourplannerbe.controller;

import at.fhtw.tourplannerbe.service.ReportService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.File;
import java.io.IOException;

@RestController
@RequestMapping("report")
@RequiredArgsConstructor
@Slf4j
public class ReportController {

    private final ReportService reportService;

    @CrossOrigin
    @GetMapping("/{id}")
    public ResponseEntity<byte[]> getReport(@PathVariable long id) throws IOException {
        log.info("Getting report");
        return reportService.createReport(id);
    }
}
