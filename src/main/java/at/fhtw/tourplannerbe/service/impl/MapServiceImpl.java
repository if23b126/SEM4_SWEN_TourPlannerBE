package at.fhtw.tourplannerbe.service.impl;

import at.fhtw.tourplannerbe.service.MapService;
import at.fhtw.tourplannerbe.service.dtos.Coordinate;
import at.fhtw.tourplannerbe.service.dtos.OsmMap;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.io.*;
import java.net.*;
import java.nio.charset.StandardCharsets;
import java.util.*;

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
            log.error(err.toString());
        }

        return durationDistance;
    }

    @Override
    public List<Coordinate> getRoute(List<Coordinate> coordinates, String transportMode) throws IOException {
        StringBuilder result = new StringBuilder();
        List<Coordinate> coordinatesList = new ArrayList<>();
        String orsUrl = "https://api.openrouteservice.org/v2/directions/" + transportMode + "?";


        Map<String, String> parameters = new HashMap<>();

        parameters.put("start", coordinates.get(0).getLon() + "," + coordinates.get(0).getLat());
        parameters.put("end", coordinates.get(1).getLon() + "," + coordinates.get(1).getLat());

        orsUrl += getParamsString(parameters);

        URL url = new URL(orsUrl);
        HttpURLConnection http = (HttpURLConnection) url.openConnection();
        http.setRequestMethod("GET");
        http.setRequestProperty("Authorization", "5b3ce3597851110001cf6248af50fb7a487f4f6ba9996cf902bccb98");
        BufferedReader in = new BufferedReader(new InputStreamReader(http.getInputStream()));
        String line;
        while ((line = in.readLine()) != null) {
            result.append(line);
        }

        http.disconnect();

        try {
            JSONObject jsonObject = new JSONObject(result.toString());
            JSONObject features = new JSONObject(jsonObject.get("features").toString().substring(1, jsonObject.get("features").toString().length() - 1));
            String coordinateString = features.getJSONObject("geometry").get("coordinates").toString();
            List<String> coordinateList = Arrays.stream(coordinateString.split("\\],\\[")).toList();
            for (String coordinate : coordinateList) {
                coordinate = coordinate.replace("[", "").replace("]", "");
                coordinatesList.add(Coordinate.builder()
                        .lat(coordinate.split(",")[1])
                        .lon(coordinate.split(",")[0])
                        .build());
            }
        } catch (JSONException err) {
            log.error(err.toString());
        }

        return coordinatesList;
    }

    public static String getParamsString(Map<String, String> params)
            throws UnsupportedEncodingException{
        StringBuilder result = new StringBuilder();

        for (Map.Entry<String, String> entry : params.entrySet()) {
            result.append(entry.getKey());
            result.append("=");
            result.append(entry.getValue());
            result.append("&");
        }

        String resultString = result.toString();
        return resultString.length() > 0
                ? resultString.substring(0, resultString.length() - 1)
                : resultString;
    }
}
