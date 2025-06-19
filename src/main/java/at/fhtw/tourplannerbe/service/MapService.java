package at.fhtw.tourplannerbe.service;


import at.fhtw.tourplannerbe.service.dtos.Coordinate;
import org.springframework.http.ResponseEntity;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.util.List;

public interface MapService {
    ResponseEntity getMap(int x, int y, int zoom) throws IOException;
    double[] getDistanceAndDuration(String[] coordStart, String[] coordEnd, String transportMode) throws IOException;
    List<Coordinate> getRoute(List<Coordinate> coordinates, String transportMode) throws IOException;
}
