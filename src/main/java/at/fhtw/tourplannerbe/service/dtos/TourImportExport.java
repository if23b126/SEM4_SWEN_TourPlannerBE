package at.fhtw.tourplannerbe.service.dtos;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class TourImportExport {
    private Tour tour;
    private List<Log> logs;
}
