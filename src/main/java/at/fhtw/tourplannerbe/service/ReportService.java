package at.fhtw.tourplannerbe.service;

import org.springframework.http.ResponseEntity;

import java.io.File;
import java.io.IOException;

public interface ReportService {
    ResponseEntity<byte[]> createReport(Long id) throws IOException;
}
