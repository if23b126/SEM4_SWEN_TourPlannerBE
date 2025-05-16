package at.fhtw.tourplannerbe.persitence;

import org.springframework.data.jpa.repository.JpaRepository;

public interface TourRepository extends JpaRepository<TourEntity, Long> {
}
