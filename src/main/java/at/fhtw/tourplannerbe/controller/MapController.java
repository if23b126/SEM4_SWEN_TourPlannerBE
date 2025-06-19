package at.fhtw.tourplannerbe.controller;

import at.fhtw.tourplannerbe.service.MapService;
import at.fhtw.tourplannerbe.service.dtos.Coordinate;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("osm")
@RequiredArgsConstructor
@Slf4j
public class MapController {

    private final MapService mapService;

    @CrossOrigin
    @GetMapping("/{zoom}/{x}/{y}")
    public ResponseEntity getMap(@PathVariable("x") int x, @PathVariable("y") int y, @PathVariable("zoom") int zoom) throws IOException {
        log.info("getMap");
        return mapService.getMap(x, y, zoom);
    }

    @CrossOrigin
    @PostMapping("/{transportMode}")
    public List<Coordinate> getRoute(@RequestBody List<Coordinate> coordinates, @PathVariable String transportMode) throws IOException {
        log.info("get Route from ");
        return mapService.getRoute(coordinates, transportMode);
    }
}
