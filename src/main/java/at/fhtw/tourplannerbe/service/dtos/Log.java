package at.fhtw.tourplannerbe.service.dtos;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Builder;
import lombok.Data;

import java.util.Date;

@Data
@Builder
public class Log {
    private Long id;
    @JsonFormat(pattern = "yyyy-MM-dd hh:mm:SS")
    private Date time;
    private String comment;
    private double difficulty;
    private double distance;
    @JsonFormat(pattern = "yyyy-MM-dd hh:mm:SS")
    private Date timeStart;
    @JsonFormat(pattern = "yyyy-MM-dd hh:mm:SS")
    private Date timeEnd;
    private double rating;
    private Long tourid;

}
