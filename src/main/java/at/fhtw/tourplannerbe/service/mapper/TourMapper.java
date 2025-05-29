package at.fhtw.tourplannerbe.service.mapper;

import at.fhtw.tourplannerbe.persitence.TourEntity;
import at.fhtw.tourplannerbe.service.dtos.Tour;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class TourMapper extends AbstractMapper<TourEntity, Tour> {


    @Override
    public Tour toDto(TourEntity entity) {
        return Tour.builder()
                .id(entity.getId())
                .name(entity.getName())
                .description(entity.getDescription())
                .start(entity.getStart())
                .end(entity.getEnd())
                .transportMode(entity.getTransportMode())
                .distance(entity.getDistance())
                .timeStart(entity.getTimeStart())
                .timeEnd(entity.getTimeEnd())
                .information(entity.getInformation())
                .timeCreated(entity.getTimeCreated())
                .popularity(entity.getPopularity())
                .build();
    }

    @Override
    public TourEntity toEntity(Tour dto) {
        return TourEntity.builder()
                .id(dto.getId())
                .name(dto.getName())
                .description(dto.getDescription())
                .start(dto.getStart())
                .end(dto.getEnd())
                .transportMode(dto.getTransportMode())
                .distance(dto.getDistance())
                .timeStart(dto.getTimeStart())
                .timeEnd(dto.getTimeEnd())
                .information(dto.getInformation())
                .timeCreated(dto.getTimeCreated())
                .popularity(dto.getPopularity())
                .build();
    }
}
