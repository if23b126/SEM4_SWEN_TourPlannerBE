package at.fhtw.tourplannerbe.persitence;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface TourRepository extends JpaRepository<TourEntity, Long> {
    @Query(value = "SELECT * FROM public.tour WHERE tour_name = %:name%", nativeQuery = true)
    List<TourEntity> searchTour(@Param("name") String naem);
}
