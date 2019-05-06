package zone.blob.service.impl;

import zone.blob.service.ColumnPreferencesService;
import zone.blob.domain.ColumnPreferences;
import zone.blob.repository.ColumnPreferencesRepository;
import zone.blob.service.dto.ColumnPreferencesDTO;
import zone.blob.service.mapper.ColumnPreferencesMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Service Implementation for managing {@link ColumnPreferences}.
 */
@Service
@Transactional
public class ColumnPreferencesServiceImpl implements ColumnPreferencesService {

    private final Logger log = LoggerFactory.getLogger(ColumnPreferencesServiceImpl.class);

    private final ColumnPreferencesRepository columnPreferencesRepository;

    private final ColumnPreferencesMapper columnPreferencesMapper;

    public ColumnPreferencesServiceImpl(ColumnPreferencesRepository columnPreferencesRepository, ColumnPreferencesMapper columnPreferencesMapper) {
        this.columnPreferencesRepository = columnPreferencesRepository;
        this.columnPreferencesMapper = columnPreferencesMapper;
    }

    /**
     * Save a columnPreferences.
     *
     * @param columnPreferencesDTO the entity to save.
     * @return the persisted entity.
     */
    @Override
    public ColumnPreferencesDTO save(ColumnPreferencesDTO columnPreferencesDTO) {
        log.debug("Request to save ColumnPreferences : {}", columnPreferencesDTO);
        ColumnPreferences columnPreferences = columnPreferencesMapper.toEntity(columnPreferencesDTO);
        columnPreferences = columnPreferencesRepository.save(columnPreferences);
        return columnPreferencesMapper.toDto(columnPreferences);
    }

    /**
     * Get all the columnPreferences.
     *
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public List<ColumnPreferencesDTO> findAll() {
        log.debug("Request to get all ColumnPreferences");
        return columnPreferencesRepository.findAll().stream()
            .map(columnPreferencesMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }


    /**
     * Get one columnPreferences by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<ColumnPreferencesDTO> findOne(Long id) {
        log.debug("Request to get ColumnPreferences : {}", id);
        return columnPreferencesRepository.findById(id)
            .map(columnPreferencesMapper::toDto);
    }

    /**
     * Delete the columnPreferences by id.
     *
     * @param id the id of the entity.
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete ColumnPreferences : {}", id);
        columnPreferencesRepository.deleteById(id);
    }
}
