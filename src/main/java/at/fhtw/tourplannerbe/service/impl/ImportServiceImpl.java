package at.fhtw.tourplannerbe.service.impl;

import at.fhtw.tourplannerbe.persitence.TourRepository;
import at.fhtw.tourplannerbe.service.ImportService;
import at.fhtw.tourplannerbe.service.LogService;
import at.fhtw.tourplannerbe.service.TourService;
import at.fhtw.tourplannerbe.service.dtos.Log;
import at.fhtw.tourplannerbe.service.dtos.Tour;
import at.fhtw.tourplannerbe.service.dtos.TourImportExport;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class ImportServiceImpl implements ImportService {

    private final TourService tourService;
    private final LogService logService;

    @Override
    public void importTour(TourImportExport tourImportExport) {
        Tour tour = tourImportExport.getTour();
        List<Log> logs = tourImportExport.getLogs();

        Tour tmp = tourService.addTour(tour);
        logs.forEach(log -> {log.setTourid(tmp.getId());});
        logService.addLogsInBulk(logs);
    }
}
