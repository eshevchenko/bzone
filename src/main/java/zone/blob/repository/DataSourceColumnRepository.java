package zone.blob.repository;

import zone.blob.domain.DataSourceColumn;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the DataSourceColumn entity.
 */
@SuppressWarnings("unused")
@Repository
public interface DataSourceColumnRepository extends JpaRepository<DataSourceColumn, Long>, JpaSpecificationExecutor<DataSourceColumn> {

}
