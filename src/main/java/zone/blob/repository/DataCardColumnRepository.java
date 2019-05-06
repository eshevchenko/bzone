package zone.blob.repository;

import zone.blob.domain.DataCardColumn;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the DataCardColumn entity.
 */
@SuppressWarnings("unused")
@Repository
public interface DataCardColumnRepository extends JpaRepository<DataCardColumn, Long> {

}
