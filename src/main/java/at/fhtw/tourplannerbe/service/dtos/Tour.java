package at.fhtw.tourplannerbe.service.dtos;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Builder;
import lombok.Data;

import java.util.Date;

@Data
@Builder
public class Tour {
    private Long id;
    private String name;
    private String description;
    private String start;
    private String end;
    private String transportMode;
    private double distance;
    private double duration;
    private String information;
    @JsonFormat(pattern = "yyyy-MM-dd hh:mm:SS")
    private Date timeCreated;
    private double popularity;
    private double childfriendliness;
}
