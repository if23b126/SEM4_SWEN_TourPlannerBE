package at.fhtw.tourplannerbe.service.dtos;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class Coordinate {
    String lat;
    String lon;
}
