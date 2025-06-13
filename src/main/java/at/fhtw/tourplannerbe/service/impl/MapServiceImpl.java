package at.fhtw.tourplannerbe.service.impl;

import at.fhtw.tourplannerbe.service.MapService;
import at.fhtw.tourplannerbe.service.dtos.Map;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.*;
import java.net.*;
import java.net.http.HttpClient;
import java.nio.charset.StandardCharsets;
import java.sql.Blob;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

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

    @Override
    public double[] getDistanceAndDuration(String[] coordStart, String[] coordEnd, String transportMode) throws IOException {
        String  urlString = "https://api.openrouteservice.org/v2/matrix/" + transportMode;
        String json = """
                {
                    "locations": [
                        [
                            {{lonStart}},
                            {{latStart}}
                        ],
                        [
                            {{lonEnd}},
                            {{latEnd}}
                        ]
                    ],
                    "metrics": [
                        "distance",
                        "duration"
                    ],
                    "resolve_locations": "false"
                }""";

        json = json.replace("{{lonStart}}",  String.valueOf(coordStart[0]));
        json = json.replace("{{latStart}}",  String.valueOf(coordStart[1]));
        json = json.replace("{{lonEnd}}",  String.valueOf(coordEnd[0]));
        json = json.replace("{{latEnd}}",  String.valueOf(coordEnd[1]));

        URL url = new URL(urlString);
        URLConnection con = url.openConnection();
        HttpURLConnection http = (HttpURLConnection)con;
        http.setRequestMethod("POST");
        http.setDoOutput(true);

        byte[] out = json.getBytes(StandardCharsets.UTF_8);
        int length = out.length;

        http.setFixedLengthStreamingMode(length);
        http.setRequestProperty("Content-Type", "application/json");
        http.setRequestProperty("Authorization", "5b3ce3597851110001cf6248af50fb7a487f4f6ba9996cf902bccb98");
        http.connect();
        try (OutputStream os = http.getOutputStream()) {
            os.write(out);
        }

        InputStream in = http.getInputStream();
        byte[] buffer = in.readAllBytes();
        String result = new String(buffer, StandardCharsets.UTF_8);

        double[] durationDistance = new double[2];
        try {
            JSONObject jsonObject = new JSONObject(result);
            double[] durations = Arrays.stream(jsonObject.get("durations").toString().replace("[", "").replace("]", "").split(",")).mapToDouble(Double::parseDouble).toArray();
            durationDistance[0] = durations[1];

            double[] distances = Arrays.stream(jsonObject.get("distances").toString().replace("[", "").replace("]", "").split(",")).mapToDouble(Double::parseDouble).toArray();
            durationDistance[1] = distances[1];
        } catch(JSONException err) {
            log.info(err.toString());
        }

        return durationDistance;
    }
}
