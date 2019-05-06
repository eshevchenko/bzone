package zone.blob.service;

import zone.blob.service.dto.DataSourceColumnDTO;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing {@link zone.blob.domain.DataSourceColumn}.
 */
public interface DataSourceColumnService {

    /**
     * Save a dataSourceColumn.
     *
     * @param dataSourceColumnDTO the entity to save.
     * @return the persisted entity.
     */
    DataSourceColumnDTO save(DataSourceColumnDTO dataSourceColumnDTO);

    /**
     * Get all the dataSourceColumns.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<DataSourceColumnDTO> findAll(Pageable pageable);


    /**
     * Get the "id" dataSourceColumn.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<DataSourceColumnDTO> findOne(Long id);

    /**
     * Delete the "id" dataSourceColumn.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
