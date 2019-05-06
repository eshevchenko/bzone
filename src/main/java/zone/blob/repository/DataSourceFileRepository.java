package zone.blob.repository;

import zone.blob.domain.DataSourceFile;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the DataSourceFile entity.
 */
@SuppressWarnings("unused")
@Repository
public interface DataSourceFileRepository extends JpaRepository<DataSourceFile, Long> {

}
