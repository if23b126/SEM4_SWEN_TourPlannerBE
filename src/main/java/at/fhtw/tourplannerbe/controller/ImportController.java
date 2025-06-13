package at.fhtw.tourplannerbe.controller;

import at.fhtw.tourplannerbe.service.dtos.TourImportExport;
import at.fhtw.tourplannerbe.service.impl.ImportServiceImpl;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@RestController
@RequestMapping("import")
@RequiredArgsConstructor
@Slf4j
public class ImportController {

    private final ImportServiceImpl importService;

    @CrossOrigin
    @PostMapping
    public void importTour(@RequestBody TourImportExport tourImportExport) throws IOException {
        log.info("Import Tour " + tourImportExport);
        importService.importTour(tourImportExport);
    }
}
