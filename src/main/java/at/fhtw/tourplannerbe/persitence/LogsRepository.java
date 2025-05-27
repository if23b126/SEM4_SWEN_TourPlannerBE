package at.fhtw.tourplannerbe.persitence;

import at.fhtw.tourplannerbe.service.dtos.Logs;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

public interface LogsRepository extends JpaRepository<LogsEntity, Long> {
    @Query(value = "SELECT * FROM public.logs WHERE logs_comment = %:comment%", nativeQuery = true)
    List<LogsEntity> searchLogs(@Param("comment") String comment);

    @Query(value = "SELECT * FROM public.logs WHERE logs_tourid=:tourID", nativeQuery = true)
    List<LogsEntity> searchLogsForTour(@Param("tourID") Long tourID);
}
