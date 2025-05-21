package at.fhtw.tourplannerbe.service;


import org.springframework.http.ResponseEntity;

import java.io.IOException;

public interface MapService {
    ResponseEntity getMap(int x, int y, int zoom) throws IOException;
}
