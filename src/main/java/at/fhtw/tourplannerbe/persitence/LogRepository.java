package at.fhtw.tourplannerbe.persitence;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface LogRepository extends JpaRepository<LogEntity, Long> {
    @Query(value = "SELECT * FROM public.logs WHERE logs_comment = %:comment%", nativeQuery = true)
    List<LogEntity> searchLogs(@Param("comment") String comment);

    @Query(value = "SELECT * FROM public.logs WHERE logs_tourid=:tourID", nativeQuery = true)
    List<LogEntity> searchLogsForTour(@Param("tourID") Long tourID);
}
