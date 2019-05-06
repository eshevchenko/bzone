package zone.blob.service;

import zone.blob.service.dto.ColumnPreferencesDTO;

import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing {@link zone.blob.domain.ColumnPreferences}.
 */
public interface ColumnPreferencesService {

    /**
     * Save a columnPreferences.
     *
     * @param columnPreferencesDTO the entity to save.
     * @return the persisted entity.
     */
    ColumnPreferencesDTO save(ColumnPreferencesDTO columnPreferencesDTO);

    /**
     * Get all the columnPreferences.
     *
     * @return the list of entities.
     */
    List<ColumnPreferencesDTO> findAll();


    /**
     * Get the "id" columnPreferences.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<ColumnPreferencesDTO> findOne(Long id);

    /**
     * Delete the "id" columnPreferences.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
