package repos;

import entities.domain.Coordinates;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CoordinatesRepo extends JpaRepository<Coordinates, Integer> {
}
