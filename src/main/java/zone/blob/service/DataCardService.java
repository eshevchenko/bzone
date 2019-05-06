package zone.blob.service;

import zone.blob.service.dto.DataCardDTO;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing {@link zone.blob.domain.DataCard}.
 */
public interface DataCardService {

    /**
     * Save a dataCard.
     *
     * @param dataCardDTO the entity to save.
     * @return the persisted entity.
     */
    DataCardDTO save(DataCardDTO dataCardDTO);

    /**
     * Get all the dataCards.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<DataCardDTO> findAll(Pageable pageable);


    /**
     * Get the "id" dataCard.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<DataCardDTO> findOne(Long id);

    /**
     * Delete the "id" dataCard.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
