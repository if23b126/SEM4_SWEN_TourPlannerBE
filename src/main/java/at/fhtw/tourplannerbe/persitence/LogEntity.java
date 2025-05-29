package at.fhtw.tourplannerbe.persitence;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
@Table(name = "logs", schema = "public")
public class LogEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "log_seq")
    @SequenceGenerator(name = "log_seq", sequenceName = "log_seq", allocationSize = 1)
    //@GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @Column(name = "LOGS_time")
    private Date time;
    @Column(name = "LOGS_comment")
    private String comment;
    @Column(name = "LOGS_difficulty")
    private double difficulty;
    @Column(name = "LOGS_distance")
    private double distance;
    @Column(name = "LOGS_timestart")
    private Date timeStart;
    @Column(name = "LOGS_timeend")
    private Date timeEnd;
    @Column(name = "LOGS_rating")
    private double rating;
    @Column(name = "LOGS_tourid")
    private long tourid;
}
