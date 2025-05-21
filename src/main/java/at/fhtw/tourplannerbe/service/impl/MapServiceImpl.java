package at.fhtw.tourplannerbe.service.impl;

import at.fhtw.tourplannerbe.service.MapService;
import at.fhtw.tourplannerbe.service.dtos.Map;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.Console;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.sql.Blob;

@Service
@Slf4j
@RequiredArgsConstructor
public class MapServiceImpl implements MapService {


    @Override
    public ResponseEntity getMap(int x, int y, int zoom) throws MalformedURLException {
        String imgUrl = "https://tile.openstreetmap.org/{zoom}/{x}/{y}.png";
        imgUrl = imgUrl.replace("{zoom}", String.valueOf(zoom)).replace("{x}", String.valueOf(x)).replace("{y}", String.valueOf(y));
        //String imgUrl = "https://yt3.googleusercontent.com/PKRBxhCiGa8Y0vPmHa1E2cdjpLhUq2Pl-gESwP7kk2plGgxLdsbjyTd9VjcJwBMiY0HQ8bvx5Q=s900-c-k-c0x00ffffff-no-rj";

        System.setProperty("http.agent", "Java TourPlanner");

        Resource resource = new UrlResource(imgUrl);

        return ResponseEntity
                .ok()
                .contentType(MediaType.IMAGE_PNG)
                .body(resource);
    }
}
