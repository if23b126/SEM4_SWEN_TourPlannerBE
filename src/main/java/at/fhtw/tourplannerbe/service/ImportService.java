package at.fhtw.tourplannerbe.service;

import at.fhtw.tourplannerbe.service.dtos.TourImportExport;

import java.io.IOException;

public interface ImportService {
    void importTour(TourImportExport tourImportExport) throws IOException;
}
