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
                .duration(entity.getDuration())
                .information(entity.getInformation())
                .timeCreated(entity.getTimeCreated())
                .popularity(entity.getPopularity())
                .childfriendliness(entity.getChildfriendliness())
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
                .duration(dto.getDuration())
                .information(dto.getInformation())
                .timeCreated(dto.getTimeCreated())
                .popularity(dto.getPopularity())
                .childfriendliness(dto.getChildfriendliness())
                .build();
    }
}
