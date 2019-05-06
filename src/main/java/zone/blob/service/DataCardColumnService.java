package zone.blob.service;

import zone.blob.service.dto.DataCardColumnDTO;

import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing {@link zone.blob.domain.DataCardColumn}.
 */
public interface DataCardColumnService {

    /**
     * Save a dataCardColumn.
     *
     * @param dataCardColumnDTO the entity to save.
     * @return the persisted entity.
     */
    DataCardColumnDTO save(DataCardColumnDTO dataCardColumnDTO);

    /**
     * Get all the dataCardColumns.
     *
     * @return the list of entities.
     */
    List<DataCardColumnDTO> findAll();


    /**
     * Get the "id" dataCardColumn.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<DataCardColumnDTO> findOne(Long id);

    /**
     * Delete the "id" dataCardColumn.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
