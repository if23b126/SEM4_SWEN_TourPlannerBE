package at.fhtw.tourplannerbe.service.impl;

import at.fhtw.tourplannerbe.service.MapService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
public class MapServiceTest {

    @Autowired
    private MapService mapService;

    @Test
    public void getParamStringTest() throws UnsupportedEncodingException {
        Map<String, String> parameters = new HashMap<>();

        parameters.put("start", "1234");
        parameters.put("end", "5678");

        String paramString = mapService.getParamsString(parameters);

        assertEquals("start=1234&end=5678", paramString);
    }
}
