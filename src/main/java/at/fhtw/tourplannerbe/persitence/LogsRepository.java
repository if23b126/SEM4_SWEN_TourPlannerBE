package at.fhtw.tourplannerbe.persitence;

import at.fhtw.tourplannerbe.service.dtos.Logs;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.web.bind.annotation.RequestBody;

public interface LogsRepository extends JpaRepository<LogsEntity, Long> {
}
