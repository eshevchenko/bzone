package zone.blob.service;

import zone.blob.service.dto.FlowDTO;

import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing {@link zone.blob.domain.Flow}.
 */
public interface FlowService {

    /**
     * Save a flow.
     *
     * @param flowDTO the entity to save.
     * @return the persisted entity.
     */
    FlowDTO save(FlowDTO flowDTO);

    /**
     * Get all the flows.
     *
     * @return the list of entities.
     */
    List<FlowDTO> findAll();


    /**
     * Get the "id" flow.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<FlowDTO> findOne(Long id);

    /**
     * Delete the "id" flow.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
