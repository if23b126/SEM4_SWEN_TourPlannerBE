package at.fhtw.tourplannerbe.service;

import at.fhtw.tourplannerbe.persitence.TourEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TourServiceTest extends JpaRepository<TourEntity, Long> {
}
