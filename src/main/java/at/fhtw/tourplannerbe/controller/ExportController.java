package at.fhtw.tourplannerbe.controller;


import at.fhtw.tourplannerbe.service.ExportService;
import at.fhtw.tourplannerbe.service.dtos.TourImportExport;
import at.fhtw.tourplannerbe.service.impl.ExportServiceImpl;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("export")
@RequiredArgsConstructor
@Slf4j
public class ExportController {

    private final ExportService exportService;

    @CrossOrigin
    @GetMapping("/{id}")
    public TourImportExport exportTours(@PathVariable Long id) {
        log.info("Exporting tours with id {}", id);
        return exportService.exportTour(id);
    }

}
