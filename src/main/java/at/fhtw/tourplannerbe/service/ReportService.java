package at.fhtw.tourplannerbe.service;

import org.springframework.http.ResponseEntity;

import java.io.File;
import java.io.IOException;

public interface ReportService {
    ResponseEntity<byte[]> createTourReport(Long id) throws IOException;
    ResponseEntity<byte[]> createSummarizeReport() throws IOException;
}
