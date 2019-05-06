package zone.blob.service;

import zone.blob.service.dto.DataSourceDTO;

import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing {@link zone.blob.domain.DataSource}.
 */
public interface DataSourceService {

    /**
     * Save a dataSource.
     *
     * @param dataSourceDTO the entity to save.
     * @return the persisted entity.
     */
    DataSourceDTO save(DataSourceDTO dataSourceDTO);

    /**
     * Get all the dataSources.
     *
     * @return the list of entities.
     */
    List<DataSourceDTO> findAll();


    /**
     * Get the "id" dataSource.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<DataSourceDTO> findOne(Long id);

    /**
     * Delete the "id" dataSource.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
