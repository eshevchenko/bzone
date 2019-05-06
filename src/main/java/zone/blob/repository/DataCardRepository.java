package zone.blob.repository;

import zone.blob.domain.DataCard;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the DataCard entity.
 */
@SuppressWarnings("unused")
@Repository
public interface DataCardRepository extends JpaRepository<DataCard, Long>, JpaSpecificationExecutor<DataCard> {

}
