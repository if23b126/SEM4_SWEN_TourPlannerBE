package at.fhtw.tourplannerbe.service.impl;

import at.fhtw.tourplannerbe.service.ExportService;
import at.fhtw.tourplannerbe.service.LogService;
import at.fhtw.tourplannerbe.service.TourService;
import at.fhtw.tourplannerbe.service.dtos.TourImportExport;
import at.fhtw.tourplannerbe.service.dtos.Log;
import at.fhtw.tourplannerbe.service.dtos.Tour;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class ExportServiceImpl implements ExportService {

    private final TourService tourService;
    private final LogService logService;

    @Override
    public TourImportExport exportTour(Long id) {
        Tour tour = tourService.getTourById(id);
        List<Log> logs = logService.getLogsForTour(tour);

        return TourImportExport.builder()
                .tour(tour)
                .logs(logs)
                .build();
    }
}
