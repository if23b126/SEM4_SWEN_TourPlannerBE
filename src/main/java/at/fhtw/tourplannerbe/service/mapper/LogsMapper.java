package at.fhtw.tourplannerbe.service.mapper;

import at.fhtw.tourplannerbe.persitence.LogEntity;
import at.fhtw.tourplannerbe.service.dtos.Log;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class LogsMapper extends AbstractMapper<LogEntity, Log> {

    @Override
    public Log toDto(LogEntity entity){
        return Log.builder()
                .id(entity.getId())
                .time(entity.getTime())
                .comment(entity.getComment())
                .difficulty(entity.getDifficulty())
                .distance(entity.getDistance())
                .timeStart(entity.getTimeStart())
                .timeEnd(entity.getTimeEnd())
                .rating(entity.getRating())
                .tourid(entity.getTourid())
                .build();
    }

    @Override
    public LogEntity toEntity(Log dto){
        return LogEntity.builder()
                .id(dto.getId())
                .time(dto.getTime())
                .comment(dto.getComment())
                .difficulty(dto.getDifficulty())
                .distance(dto.getDistance())
                .timeStart(dto.getTimeStart())
                .timeEnd(dto.getTimeEnd())
                .rating(dto.getRating())
                .tourid(dto.getTourid())
                .build();
    }
}
