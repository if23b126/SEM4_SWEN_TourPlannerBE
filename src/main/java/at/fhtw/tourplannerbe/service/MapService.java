package at.fhtw.tourplannerbe.service;


import org.springframework.http.ResponseEntity;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.ProtocolException;

public interface MapService {
    ResponseEntity getMap(int x, int y, int zoom) throws IOException;
    double[] getDistanceAndDuration(String[] coordStart, String[] coordEnd, String transportMode) throws IOException;
}
