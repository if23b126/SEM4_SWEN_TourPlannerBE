package at.fhtw.tourplannerbe.service.mapper;

import at.fhtw.tourplannerbe.persitence.LogEntity;
import at.fhtw.tourplannerbe.service.dtos.Log;
import at.fhtw.tourplannerbe.service.dtos.Tour;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.text.ParseException;
import java.text.SimpleDateFormat;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
public class LogsMapperTest {

    @Autowired
    private LogsMapper mapper;

    private SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");

    @Test
    public void MapDtoToEntityTest() throws ParseException {
        Log log = Log.builder()
                    .id(1L)
                    .time(sdf.parse("2025-06-24 08:00:00"))
                    .comment("Comment 1")
                    .difficulty(1.0)
                    .distance(1.0)
                    .timeStart(sdf.parse("2025-06-24 07:00:00"))
                    .timeEnd(sdf.parse("2025-06-24 08:00:00"))
                    .rating(1.0)
                    .tourid(1L)
                    .build();

        LogEntity entity = mapper.toEntity(log);

        assertEquals(log.getId(), entity.getId());
        assertEquals(log.getTime(), entity.getTime());
        assertEquals(log.getComment(), entity.getComment());
        assertEquals(log.getDifficulty(), entity.getDifficulty());
        assertEquals(log.getDistance(), entity.getDistance());
        assertEquals(log.getTimeStart(), entity.getTimeStart());
        assertEquals(log.getTimeEnd(), entity.getTimeEnd());
        assertEquals(log.getRating(), entity.getRating());
        assertEquals(log.getTourid(), entity.getTourid());
    }

    @Test
    public void MapEntityToDtoTest() throws ParseException {
        LogEntity logEntity = LogEntity.builder()
                .id(1L)
                .time(sdf.parse("2025-06-24 08:00:00"))
                .comment("Comment 1")
                .difficulty(1.0)
                .distance(1.0)
                .timeStart(sdf.parse("2025-06-24 07:00:00"))
                .timeEnd(sdf.parse("2025-06-24 08:00:00"))
                .rating(1.0)
                .tourid(1L)
                .build();

        Log log = mapper.toDto(logEntity);

        assertEquals(logEntity.getId(), log.getId());
        assertEquals(logEntity.getTime(), log.getTime());
        assertEquals(logEntity.getComment(), log.getComment());
        assertEquals(logEntity.getDifficulty(), log.getDifficulty());
        assertEquals(logEntity.getDistance(), log.getDistance());
        assertEquals(logEntity.getTimeStart(), log.getTimeStart());
        assertEquals(logEntity.getTimeEnd(), log.getTimeEnd());
        assertEquals(logEntity.getRating(), log.getRating());
        assertEquals(logEntity.getTourid(), log.getTourid());
    }
}
