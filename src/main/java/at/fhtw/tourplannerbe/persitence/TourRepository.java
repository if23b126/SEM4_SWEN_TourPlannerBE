package at.fhtw.tourplannerbe.persitence;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface TourRepository extends JpaRepository<TourEntity, Long> {
    @Query(value = "SELECT * FROM public.tour WHERE " +
            "tour_name ILIKE CONCAT('%', :name, '%') OR " +
            "tour_description ILIKE CONCAT('%', :name, '%') OR " +
            "CAST(tour_distance AS TEXT) ILIKE CONCAT('%', :name, '%') OR " +
            "tour_information ILIKE CONCAT('%', :name, '%') OR " +
            "tour_start ILIKE CONCAT('%', :name, '%') OR " +
            "tour_end ILIKE CONCAT('%', :name, '%') OR " +
            "tour_transportmode ILIKE CONCAT('%', :name, '%') OR " +
            "CAST(tour_duration AS TEXT) ILIKE CONCAT('%', :name, '%')",
            nativeQuery = true)
    List<TourEntity> searchTour(@Param("name") String name);
}
