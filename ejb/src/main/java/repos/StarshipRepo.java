package repos;

import entities.Starship;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StarshipRepo extends JpaRepository<Starship, Integer> {
}
