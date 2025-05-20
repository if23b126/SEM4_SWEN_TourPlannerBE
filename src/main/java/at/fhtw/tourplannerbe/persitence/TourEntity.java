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
@Table(name = "tour", schema = "public")

public class TourEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @Column(name = "TOUR_name")
    private String name;
    @Column(name = "TOUR_description")
    private String description;
    @Column(name = "TOUR_start")
    private String start;
    @Column(name = "TOUR_end")
    private String end;
    @Column(name = "TOUR_transportmode")
    private String transportMode;
    @Column(name = "TOUR_distance")
    private double distance;
    @Column(name = "TOUR_timestart")
    private Date timeStart;
    @Column(name = "TOUR_timeend")
    private Date timeEnd;
    @Column(name = "TOUR_information")
    private String information;
    @Column(name = "TOUR_timecreated")
    private Date timeCreated;
}
