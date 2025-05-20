package at.fhtw.tourplannerbe.service.impl;

import at.fhtw.tourplannerbe.service.MapService;
import at.fhtw.tourplannerbe.service.dtos.Map;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.sql.Blob;

@Service
@Slf4j
@RequiredArgsConstructor
public class MapServiceImpl implements MapService {


    @Override
    public String getMap(int x, int y, int zoom) {
        RestClient client = RestClient.create();

        return client.get()
                .uri("https://tile.openstreetmap.org/{zoom}/{x}/{y}.png", zoom, x, y)
                .retrieve()
                .body(String.class);
    }
}
