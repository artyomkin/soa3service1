package repos;
import entities.SpaceMarine;
import entities.domain.MeleeWeapon;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface SpaceMarineRepository extends JpaRepository<SpaceMarine, Long>, JpaSpecificationExecutor<SpaceMarine>{
    List<SpaceMarine> findAllByMeleeWeapon(MeleeWeapon meleeWeapon);
    @Query("select s from SpaceMarine s join s.coordinates c where c.id = (select min(c2.id) from s.coordinates c2) order by c.id ASC")
    List<SpaceMarine> findByMinCoords();
    
    @Query("select count(s) from SpaceMarine s where s.health < :health")
    Integer count(@Param("health") Double health);
}
