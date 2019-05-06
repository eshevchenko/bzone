package zone.blob.service;

import zone.blob.service.dto.DataSourceFileDTO;

import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing {@link zone.blob.domain.DataSourceFile}.
 */
public interface DataSourceFileService {

    /**
     * Save a dataSourceFile.
     *
     * @param dataSourceFileDTO the entity to save.
     * @return the persisted entity.
     */
    DataSourceFileDTO save(DataSourceFileDTO dataSourceFileDTO);

    /**
     * Get all the dataSourceFiles.
     *
     * @return the list of entities.
     */
    List<DataSourceFileDTO> findAll();


    /**
     * Get the "id" dataSourceFile.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<DataSourceFileDTO> findOne(Long id);

    /**
     * Delete the "id" dataSourceFile.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
