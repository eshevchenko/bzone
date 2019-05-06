package zone.blob.repository;

import zone.blob.domain.ColumnPreferences;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the ColumnPreferences entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ColumnPreferencesRepository extends JpaRepository<ColumnPreferences, Long> {

}
