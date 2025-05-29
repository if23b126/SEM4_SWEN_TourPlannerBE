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
    @GetMapping("/tour/{id}")
    public ResponseEntity<byte[]> getTourReport(@PathVariable long id) throws IOException {
        log.info("Getting tour report");
        return reportService.createTourReport(id);
    }

    @CrossOrigin
    @GetMapping("summary")
    public ResponseEntity<byte[]> getSummarizeReport() throws IOException {
        log.info("Getting summarize report");
        return reportService.createSummarizeReport();
    }
}
