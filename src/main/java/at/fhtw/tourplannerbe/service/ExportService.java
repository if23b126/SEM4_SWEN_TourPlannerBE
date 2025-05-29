package at.fhtw.tourplannerbe.service;

import at.fhtw.tourplannerbe.service.dtos.TourImportExport;

public interface ExportService {
    TourImportExport exportTour(Long id);
}
